//Step 1: 分解、同步交易信息
var s_time = new Date();
var interval = 1;
var myCoursor=db.heagle_monitor_log.find({className:"com.hzbank.heagle.tools.ctrl.security.interceptor.HzBankTradeInterceptor",
    message:{$regex:'transEnd=(?!null).*?$'},timestamp:{$gte:new Date(s_time - interval*60*1000)}});
myCoursor.forEach(function(o){
    result = o.message.match(".*transJnslNo=(\\w+).*onlineNo=(\\w+).*sessionId=(\\w+\\-\\w+\\-\\w+\\-\\w+\\-\\w+).*operatorNo=(\\w+).*transCode=(\\w+).*transBegin=(\\d+\\-\\d+\\-\\d+ \\d+\\:\\d+\\:\\d+).*transEnd=(\\d+\\-\\d+\\-\\d+ \\d+\\:\\d+\\:\\d+)");
    if(result){
        var now = new Date;
        trans_jnls_no = result[1];
        online_jnls_no = result[2];
        session_id = result[3];
        oper_no = result[4];
        process_code = result[5];
        trans_begin_time = Date.parse(result[6]);
        trans_end_time= Date.parse(result[7]);
        trans_duration= (trans_end_time -  trans_begin_time)/1000 ;
        trans_flag = 1;
        db.heagle_trans.save({"trans_jnls_no":trans_jnls_no,"online_jnls_no":online_jnls_no,"session_id":session_id,"oper_no":oper_no,"process_code":process_code,"trans_begin_time":trans_begin_time,"trans_end_time":trans_end_time,"trans_duration":trans_duration,"trans_flag":trans_flag,timeStamp:now.getTime()});
        print("process_code="+process_code+" oper_no="+oper_no+" session_id="+session_id+" trans_jnls_no="+trans_jnls_no+" online_jnls_no="+online_jnls_no+" trans_end_time="+trans_end_time+" trans_begin_time="+trans_begin_time+" trans_duration="+trans_duration);
    }
 });
