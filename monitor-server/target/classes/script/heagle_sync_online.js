//Step 3: analyze and sync online users
var s_time = new Date();
var interval = 1;
var myCoursor = db.monitor_monitor_log.find({
    className: "com.monitor.cometd.MessageScanJob",
    message: { $regex: 'logintime=(?!null).*?$' },
    timestamp: { $gte: new Date(s_time - interval * 60 * 1000) }
});
myCoursor.forEach(function(o) {
    result = o.message.match(".*operno=(\\w+).*ip=(\\d+\\.\\d+).*logintime=(\\d+)");
    if (result) {
        var now = new Date;
        userid = result[1];
        ip = result[2];
        logintime = parseInt(result[3]);
        online_flag = 1;
        //validate clients of hangzhou city
        if (ip == "178.86" || ip == "178.128" || ip == "178.84") {
            ip = "178.78";
        }
        db.monitor_online.save({ "ip": ip, "userid": userid, "logintime": logintime, "online_flag": online_flag, "timeStamp": now.getTime() });
        print("ip=" + ip + " userid=" + userid + " logintime=" + logintime);
    }
});