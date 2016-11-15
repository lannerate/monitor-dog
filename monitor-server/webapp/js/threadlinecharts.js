

	function initChart(json,title,label){
		var lineChart = $('#lineChart').highcharts({//chartNo1合同发生额 曲线初始化
			title: {
				text: '',
				style:{
					color:'#fff',
					fontSize:'16px',
					fontWeight:'bold',
					textAlign:'left'
					},
				align:'left'
				
			},
			chart: {
				backgroundColor:'#4e5567',
				borderRadius:0
			},
			colors:[
					'#e8676b'
				
					
					
			],
		
		 plotOptions: {
			series: {
				marker: {
					enabled:true,
					lineWidth:0,
					radius:0
					
				}
			}
		},
		xAxis: {
			categories: json.categories,
			maxPadding:0.5,
			tickLength:0,
			gridLineWidth:1,
			gridLineColor:'#555c6e', //网格线颜色
			lineColor:'#565d6f',
			//labels:{rotation:30,}
			labels:{
				 style:{
					fontSize:'12px',
					color:'#979eae'
				 }
			}
			
		},
		yAxis: {
			lineWidth:1,
			lineColor:'#565d6f',
			gridLineWidth:1,//中间灰色的多条横线  网格线,
			gridLineColor:'#555c6e', //网格线颜色
			maxPadding:0.5,
			labels:{
				enabled:true,
				style:{
					color:'#979eae'
				}
				},
			title:{
				enabled:false
			},
			startOnTick:false,
			endOnTick:false,
			min:0
		},
		legend: {  
			enabled:false,
			floating:false,
			borderWidth:0,
			floating:true,
			align:'right',
			y:'-100'
			
		},
		tooltip: {
			shared: false,
			useHTML: true,
			headerFormat: '<small  style="color:#f7cbca">{point.key}</small><table>',
			pointFormat: '<tr style="color:#f7cbca"><td style="font-size:14px;"><b>{series.name}:</b> </td>' +
			'<td style="text-align: right"><b>{point.y}</b></td></tr>',
			footerFormat: '</table>', 
			valueDecimals: 0,
			valueSuffix:' '+label,
			backgroundColor:'#e6686b'
		},
		series: [ {
			name: title,
			data: json.series
		}
		]
		});//chartNo1合同发生额 曲线初始化 结束
	}


