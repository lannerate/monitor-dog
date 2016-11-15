 //折线图 初始化start
//function ajaxRequest(urls){
//$.ajax({
//   type: "POST",
//   url: urls,
//   data: {"asda":108998},
//   dataType: 'html',
//   success: function(data){
//	   var json = eval('(' + data + ')');
//	   var canvases = $('canvas.process');
//	   for(i=0;i<json.percent.length;i++){
//          drawNum(json.percent[i], canvases[i]);
//	   };
//	   var canvass = $('canvas.process1');
//	   for(i=0;i<json.numerical.length;i++){
//          numeRical(json.numerical[i], canvass[i]);
//	   }
//   }
//});
//};

function drawNum(json, el){  // 有百分比
	$(el).text(json.status);  
	var text = $(el).text();
	var canvas = el; 
	var process = text.substring(0, text.length-1);    
	var context = canvas.getContext('2d');
    context.clearRect(0,0,122,122); //再次绘制图形前清除之前的图形
    context.beginPath();
	context.lineWidth = 1.5;
	context.strokeStyle = '#24c6db';  
	context.arc(60, 60, 59, 0,Math.PI * 2, true);  
	context.stroke();  


	// 画内部空白  
	context.beginPath();  
  //  context.moveTo(60, 60);  
   
 //   context.closePath();  
	context.lineWidth = 1.5;
	context.strokeStyle = '#263049';  
	context.arc(60, 60, 59, 0,Math.PI * 2 * process / 100, true);  
	context.stroke();   

	context.font = "16px Arial,微软雅黑";  
	context.fillStyle = '#d5d8dd';  
	context.textAlign = 'center';  
	context.textBaseline = 'middle';  
	context.moveTo(60, 60);  
	context.fillText(text, 60, 50);  
	context.fillText(json.label, 60, 70); 
};

function numeRical(json, el){  //纯数字
		$(el).text(json.status);  
	    var text = $(el).text();
	    var canvas = el; 
		var process = text.substring(0, text.length-1);    
		var context = canvas.getContext('2d');
        context.clearRect(0,0,122,122);
        context.beginPath();
		context.lineWidth = 1.5;
        context.strokeStyle = '#263049';  
		
		context.arc(60, 60, 59, 0,Math.PI * 2, true);  
        context.stroke();  
	

	    context.font = "16px Arial,微软雅黑";  
        context.fillStyle = '#d5d8dd';  
        context.textAlign = 'center';  
        context.textBaseline = 'middle';  
        context.moveTo(60, 60);  
        context.fillText(text, 60, 50);  
		context.fillText(json.label, 60, 70);  
};