$(function() {
	//定义访问后台，获取头条列表以及一级类别列表的URL
	var url = '/MovieRecommend/movieadmin/gethottestmovie';
	//访问后台，获取头条列表以及一级类别列表
	$.getJSON(url, function(data) {
		if (data.success) {
			//获取后台传递过来的头条列表
			var headLineList = data.top5Moive;
			var swiperHtml = '';
			//遍历头条列表，并拼接出轮播图组
			headLineList.map(function(item, index) {
				swiperHtml += '' + '<div class="swiper-slide img-wrap">'
						+ '<a href="' + serverAddr + "/MovieRecommend/frontend/moviedetail?hottestMovieId="+item.movieId
						+ '" external><img class="banner-img" src="' + item.movieImgAddr
						+ '" alt="' + item.movieName + '"></a>' + '</div>';
			});
			//将轮播图组赋值给前端HTML控件
			$('.swiper-wrapper').html(swiperHtml);
			//设定轮播图轮换时间为3秒
			$(".swiper-container").swiper({
				autoplay : 3000,
				//用户对轮播图进行操作时，是否自动停止autoplay
				autoplayDisableOnInteraction : false
			});
		}
	});
	
	//若点击"我的"，则显示侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

//	电影分类区域点击之后，响应的事件
	$('.row').on('click', '.movie-classify', function(e) {
		var movieCategoryId = e.currentTarget.dataset.category;
		var newUrl = serverAddr+'/MovieRecommend/frontend/shoplist?categoryId=' + movieCategoryId;
		window.location.href = newUrl;
	});
	
});
