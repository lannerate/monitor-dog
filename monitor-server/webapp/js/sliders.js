function sliders(){
		var liw$ = $(".data-list-box canvas").width() + 30;
		var lisum = $(".data-list-box canvas").length;
		$(".data-list-ul").width(liw$*lisum);
       alert('555');
		$(".prev").click(function(){
			var ulLeft = parseInt($(".data-list-ul").css("left"));
			var bleft = ulLeft +liw$;
			if(ulLeft == 0){
				return;
			}else{
				$(".data-list-ul").animate({left:bleft},500);
			}
		});
		
		$(".next").click(function(){
			var ulLeft = parseInt($(".data-list-ul").css("left"));
			var ulWidth = parseInt($(".data-list-ul").css("width"));
			var bleft = ulLeft - liw$;
			var lwidth = ulWidth - liw$*7;
			console.log(bleft +','+lwidth+','+ulWidth);
			if(ulLeft <= -lwidth){
				return;
			}else{
		    	$(".data-list-ul").animate({left:bleft},500)
			}

		});
};