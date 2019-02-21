$(function() {
	
	// 从地址栏里获取movieId和categoryId
	var Request = new Object();
	Request = GetRequest();
	var hottestMovieId = Request["hottestMovieId"];
	var movieId = Request["movieId"];
	
	//如果hottestMovieId不为null，说明从首页轮播图跳转过来
	//需要将评分部分隐藏
	if(hottestMovieId){
		$('#star').hide();
		$('#submitRating').hide();
	}else{
		$('#star').show();
		$('#submitRating').show();
	}
	
	
	var url = serverAddr+'/MovieRecommend/movieadmin/gethottestmovieinfo?hottestMovieId='+hottestMovieId;
	getHottestMovieId(url);
	
	
	//设置变量值，标明在推荐（离线，实时）两部分中，点击哪一个部分的卡片进行跳转到moviedetail页面
	var offline = false;
	
	//从localStorage中获取电影的详细信息
	var strOffline = localStorage.getItem('data');
	if(strOffline){
		var data = JSON.parse(strOffline);
		if(data.success){
			var movieList = data.movieList;
			movieList.map(function(item, index) {
				if(item){                        
					if(item.movieId == movieId){
						offline = true;
						$('#movie-image').attr('src', item.movieImgAddr);
						$('#movie-name').html(item.movieName);
						$('#movie-info').html(item.movieInfo);
						$('#movie-play-url').attr('href',item.moviePlayUrl);
						$('#movie-release-time').html("上映时间:"+item.releaseTime);
					}
				}
			});
		}
	}
	
	//如果离线数据中没有该movieId， 则在实时推荐的数据中寻找
	if(!offline){
		var strOnline = localStorage.getItem('realTimeData');
		if(strOnline){
			var data = JSON.parse(strOnline);
			if(data.success){
				var movieList = data.movieList;
				movieList.map(function(item, index) {
					if(item){                        
						if(item.movieId == movieId){
							$('#movie-image').attr('src', item.movieImgAddr);
							$('#movie-name').html(item.movieName);
							$('#movie-info').html(item.movieInfo);
							$('#movie-play-url').attr('href',item.moviePlayUrl);
							$('#movie-release-time').html("上映时间:"+item.releaseTime);
						}
					}
				});
			}
		}
	}
	
	
	//从localStorage中获取userId
	//如果获取得到，说明用户处在登录状态
	//获取不到，说明当前处在游客模式
	var userId = localStorage.getItem("userId");
	
	
	// 获取综合电影详细信息，json格式
	function getHottestMovieId(url) {
		$.getJSON(
				url,
				function(data) {
					if (data.success) {
						var movieInfo = data.movieInfo;
						$('#movie-image').attr('src', movieInfo.movieImgAddr);
						$('#movie-name').html(movieInfo.movieName);
						$('#movie-info').html(movieInfo.movieInfo);
						$('#movie-play-url').attr('href',movieInfo.moviePlayUrl); 
						$('#movie-release-time').html("Release Time :"+movieInfo.releaseTime);
					}
				});
	}
	
	//星级评分
	$('#star').raty({
		width: 210,
		starOn:  '../resources/images/img_star_on.png',
		starOff: '../resources/images/img_star_off.png',
	});

	
	//从url中获取传递过来的movieId和categoryId
	function GetRequest() {
		   var url = location.search; //获取url中"?"符后的字串
		   var theRequest = new Object();
		   if (url.indexOf("?") != -1) {
		      var str = url.substr(1);
		      strs = str.split("&");
		      for(var i = 0; i < strs.length; i ++) {
		         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
		      }
		   }
		   return theRequest;
		}
	
	//提交评分按钮事件绑定
	$('#submitRating').click(function(){
		if(!userId){
			alert("please sign in first!");
		}else{
			//获取用户的评分
			//将userId, movieId, rate, timestamp传递到后台
			jQuery("#star input[type='hidden']").each(function(){
				var registerUrl="/MovieRecommend/movieadmin/usermovierate";
			    var rate = $(this).val();
			    $.ajax({
					url : registerUrl,
					cache : false,
					type : 'post',
					dataType:'json',
					data : {
						userId:userId,
			    		movieId:movieId,
			    		rate:rate,
					},
					success : function(data) {
						if (data.success) {
							alert("rating succeed！");
						} else {
							alert("rating failed！");
							$('#captcha_img').click();
						}
					}
				});
			  });
		}
	});
	
	// 点击后打开右侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
//	$.init();
});
