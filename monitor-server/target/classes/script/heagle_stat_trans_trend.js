 //Step 2: 交易趋势(per 5 min)
 var trans_stat_time = new Date;
 var trans_stat_interval = 5;
 var trans_type = "stat_all";
 var trans_stat_count = 0;
 trans_stat_count = db.runCommand({
     distinct: "monitor_trans",
     key: "trans_jnls_no",
     query: { process_code: { $ne: "null" }, timeStamp: { $gte: new Date(trans_stat_time - trans_stat_interval * 60 * 1000).getTime() } }
 }).values.length;

 db.monitor_trans_trend.save({
     "trans_type": trans_type,
     "trans_stat_count": trans_stat_count,
     "trans_stat_interval": trans_stat_interval,
     "trans_stat_time": trans_stat_time.getTime(),
     "timeStamp": trans_stat_time.getTime()
 });
 print("trans_stat_count=" + trans_stat_count);