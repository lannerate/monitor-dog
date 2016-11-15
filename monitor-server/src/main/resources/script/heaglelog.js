Date.prototype.format = function(format){

    var o = {
        "M+":this.getMonth() + 1 , // month
        "d+":this.getDate(),
        "h+":this.getHours(),
        "m+":this.getMinutes(),
        "s+":this.getSeconds(),
        "q+":Math.floor((this.getMonth()+3)/3),
        "S":this.getMilliseconds()
    }

    if(/(y+)/.test(format))
        format = format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
       if(new RegExp("("+k+")").test(format))
          format = format.replace(RegExp.$1,RegExp.$1.length == 1 ? o[k]: ("0"+o[k]).substr(""+o[k].length));

    return format;
}

function doHandleMonth(month){
    var m = month;
    if(month.toString().length == 1)
       m = "0"+month;

    return m;
}

function getDay(day){
    var today = new Date();
    var targetday_millisecondes = today.getTime() + day*24*60*60*1000;
    today.setTime(targetday_millisecondes);
    var tYear = today.getFullYear();
    var tMonth = today.getMonth();
    var tDate = today.getDate();

    tMonth = doHandleMonth(tMonth+1);
    tDate = doHandleMonth(tDate);
    return tYear+"-"+tMonth +"-"+tDate+" 00:00:00";
}

var myDate = new Date();
var datetime = myDate.format("yyyy-MM-dd 00:00:00");
var preday = getDay(-7);
print("today="+datetime+" deleteday="+preday);
datetime = new Date(datetime);
preday = new Date(preday);
var count = db.heagle_monitor_log.find({'timestamp':{'$lt':preday}}).count();
print("count="+count);
db.heagle_monitor_log.remove({'timestamp':{'$lt':preday}});
print("前7天的日志数据清理完毕...");
