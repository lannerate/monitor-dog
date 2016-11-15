package org.log4mongo.contrib;

import org.apache.log4j.Logger;
import org.hyperic.sigar.*;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;




public class JvmMonitor {
	private static JvmMonitor uniqueInstance = null;
    private static Logger logger = Logger.getLogger(JvmMonitor.class);
    private long lastProcessCpuTime = 0;
    private long lastUptime = 0;
    public static final int DEFAULT_REFRESH_SECONDS = 20;

    static{
        getInstance();
    }

    public static void main(String args[]){

    }

    public synchronized static  JvmMonitor getInstance(int periodSeconds){
    	if(uniqueInstance==null)
    		uniqueInstance=new JvmMonitor(periodSeconds);
    	return uniqueInstance;
    }
    
    public synchronized static  JvmMonitor getInstance(){
    	if(uniqueInstance==null)
    		uniqueInstance=new JvmMonitor();
    	return uniqueInstance;
    }
    
    private JvmMonitor() {
        this(DEFAULT_REFRESH_SECONDS);
    }

    private JvmMonitor(int periodSeconds) {

        logger.info("jvm monitor start  ...");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                record();
            }
        }, periodSeconds, periodSeconds, TimeUnit.SECONDS);
    }

    public void record() {
        String message = "memoryUsed=" + getMemoryUsed() + "k "
                + " cpuUsed=" + getCpu() + " threadCount=" + getThreadCount()+" diskUsedRate="+getDiskUsedRate()
                +" heapMemoryUsed="+getHeapMemoryUsed()+"k noheapMemoryUsed="+getNoHeapMemoryUsed()+"k ";
        message +=getMemoryPoolInfo()+getNetwokInfo()+" ip="+getIp()+" pid="+getPid();
        logger.info(message);
        System.out.println(message);

    }

    protected int getThreadCount() {
        return ManagementFactory.getThreadMXBean().getThreadCount();
    }

    protected long getMemoryUsed() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024);
    }

 /*   protected double getMemoryUsedRate() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (Runtime.getRuntime().totalMemory());
    }*/

    protected long getHeapMemoryUsed(){
        return (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()) / (1024);
    }

/*    protected double getHeapMemoryUsedRate(){
        return (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()) / (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());
    }*/

    protected long getNoHeapMemoryUsed(){
        return (ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed()) / (1024);
    }
/*    protected double getNoHeapMemoryUsedRa
te(){
        return (ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed()) / (ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getMax());
    }*/

    /**
     * get Eden space,Survivor space,Tenured Gen,Perm Gen
     * @return  MemoryPoolInfo
     */
    protected String getMemoryPoolInfo(){
       List<MemoryPoolMXBean> list = ManagementFactory.getMemoryPoolMXBeans();
       StringBuffer info= new StringBuffer();
       for (MemoryPoolMXBean memoryPoolMXBean :list){
           info.append(memoryPoolMXBean.getName() + "=" + ((memoryPoolMXBean.getUsage().getUsed()) / (1024))+"k ");
           /*if(memoryPoolMXBean.isUsageThresholdSupported()&&memoryPoolMXBean.isCollectionUsageThresholdSupported()){

           }*/
       }
        return info.toString().trim();
    }


    // c)CPU的用户使用量、系统使用剩余量、总的剩余量、总的使用占用量等（单位：100%）
    protected double getCpu() {
        Sigar sigar = new Sigar();

        // 方式二，不管是单块CPU还是多CPU都适用
        CpuPerc cpuList[] = null;
        try {
            cpuList = sigar.getCpuPercList();
        } catch (SigarException e) {
            e.printStackTrace();
            return 0.0;
        }

        int cupSize = cpuList.length;
        double totalCpuRatio = 0.0;
        for (int i = 0; i < cpuList.length; i++) {
            totalCpuRatio +=cpuList[i].getCombined();
        }
//       System.out.println("cpu avg cpu ratio="+(totalCpuRatio/cupSize));
        return  (totalCpuRatio/cupSize)*100;
    }

    // d)获取网络流量等信息
    public String getNetwokInfo(){
        Sigar sigar = new Sigar();
        long totalNetworkRx = 0;
        long totalNetworkTx = 0;
      try {
        String ifNames[] = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                continue;
            }

                NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
                totalNetworkRx += ifstat.getRxBytes();
                totalNetworkTx += ifstat.getTxBytes();
            }
        } catch (Exception e) {
           logger.error("获取网络流量等信息出错！");
        }
        return  " totalNetworkRx="+(totalNetworkRx/1024)+"k totalNetworkTx="+(totalNetworkTx/1024)+"k ";
    }

    protected double getDiskUsedRate(){
        File[] roots = File.listRoots();
        double total=0,used=0;
        for(File root : roots){
            total += root.getTotalSpace();
            used  += root.getUsableSpace();
        }
        return  ((used)/(total))*100;
    }

    protected String getPid(){
        return ManagementFactory.getRuntimeMXBean().getName();
    }

    protected String getIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e){
            return "unkonw host";
        }
    }
}
