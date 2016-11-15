// JavaScript Document
  $(function(){
	function mapshowFun (){
        $(".map p").each(function(){
		var ranks = $(this).attr("data-rank");
        //控制显示top10
		if(ranks > 10){
		  $(this).hide();
		};

		$(this).hover(
		    function(){
			  var text = $(this).text();
			  var lefter = $(this).offset().left - 40;
			  var toper = $(this).offset().top - 75;
			  var rank = $(this).attr("data-rank");
			  var value = $(this).attr("data-value");
			  var str = text + "：排名" + rank + ",人数:" + value;
			  $(".openBox p").text(str);
			  $(".openBox").css({left:lefter+"px",top:toper + "px"});
			 // console.log(toper);
			  $(".openBox").show();

			},
			function(){
			  $(".openBox").hide();
			}
            );
        });
    }

	function ajaxRequest(urls){
        $.ajax({
		   type: "GET",
		   url: urls,
		   data: {"asda":108998},
		   dataType: 'json',
		   success: function(data){
//			   var json = eval('(' + data + ')');
//			   var json = eval(data);
               var json = data;
			   var canvases = $('canvas.process');
			   for(i=0;i<json.percent.length;i++){
				  drawNum(json.percent[i], canvases[i]);
			   };
			   var canvass = $('canvas.process1');
			   for(i=0;i<json.numerical.length;i++){
				  numeRical(json.numerical[i], canvass[i]);
			   }
		   }
		});
	};
   // ajaxRequest("pie/render");


    function ajaxRequestParam(urls,params){
          $.ajax({
              type: "GET",
              url: urls,
              data:params,
              dataType: 'json',
              success: function(data){
//			   var json = eval('(' + data + ')');
//			   var json = eval(data);
                  var json = data;
                  var canvases = $('canvas.process');
                  for(i=0;i<json.percent.length;i++){
                      drawNum(json.percent[i], canvases[i]);
                  };
                  var canvass = $('canvas.process1');
                  for(i=0;i<json.numerical.length;i++){
                      numeRical(json.numerical[i], canvass[i]);
                  }
              }
          });
      };

      //查询综合项
      var now = new Date();
      var start =now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" 00:00:00";
      var end =now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" 23:59:59";
      var params = jQuery.param({
          start:start,
          end:end,
          keyWord:'',
          level:'ERROR'});
      ajaxRequestParam("heagle_monitor/pie/render",params);


      $.ajax({
	   type: "GET",
	   url: "rank/render",
	   data: "",
	   dataType: 'json',
	   success: function(data){
//		   ar json = eval('(' + data + ')');v
//		   var json = eval(data);
		  //var json = $.JSON.parse(data);
           var json = data;
//           console.log(json["percent"]  )
		   ranks(json);
	   }
	});
 
	 function ranks(json){
        // console.log(json);
         var dataPercent = json.percent;
//         console.log(dataPercent);
         var totalOnlineUsers = 0;
		 for(i=0;i<dataPercent.length;i++){
            var _curRank = dataPercent[i].rank;
            var _curLabel = dataPercent[i].label;
            var _curClass = dataPercent[i].pos;
            var _curVal = dataPercent[i].values;
            var _curMax = dataPercent[i].maxs;
            totalOnlineUsers += _curVal;
            var $p = $("<p data-rank='" + _curRank + "' data-value='" + _curVal + "' class='" + _curClass + "'>" + _curLabel + "</p>")
            $('.map').append($p);
             //渲染省份和数据
             mapshowFun();

			var $li = '<li><em> ' + json.percent[i].rank +  ' </em> ' + json.percent[i].label + ' <progress max=' + json.percent[i].maxs + ' value=' + json.percent[i].values + '></progress></li>'; 
			$(".frontTop ul").append($li); 
		 }
         var onlineJson = {status:totalOnlineUsers,label:'今日在线'};
         numeRical(onlineJson, $('canvas.process1')[1]);

         $(".frontTop ul li:lt(3)").addClass("progress");

	 };
 
	
});




