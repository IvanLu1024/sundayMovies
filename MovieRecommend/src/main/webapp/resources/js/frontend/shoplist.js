$(function() {

	var userId = localStorage.getItem("userId");
	
	// 从地址栏URL里尝试获取电影 categoryId.
	var categoryId = getQueryString('categoryId');
	if(categoryId>=1 && categoryId<=8){
		//有categoryId，说明是从电影分类表中获取电影
		addCategoryItems(categoryId);
		//将  标签 (Movies You Might Like Recently:) 隐藏  
		$('#real-time-label').hide();
	}else{
		//没有categoryId
		if(userId){
		//如果userId不为null，说明当前处在用户以登录状态，需要从推荐电影表中读取为用户推荐的电影
			addItemsForUser(userId);
			addItemsRealTimeForUser(userId);
		}else{
		//userId为null， 说明当前处在游客状态，需要从综合电影表中读取电影
			addItems();
			$('#real-time-label').hide();
		}
	}

	/**
	 * 添加综合电影列表，用户登录模式中的今日推荐，为用户推荐电影
	 * 离线推荐部分
	 */
	function addItemsRealTimeForUser(userId) {
		// 获取json数据的url
		var url = serverAddr+"/MovieRecommend/movieadmin/getrecommendmovie?userId="+userId;

		//开始获取json数据
		$.getJSON(url, function(data) {
			if (data.success) {
				var movieList = data.movieList;
				var html = '';
				// 遍历json中电影list，拼接出卡片集合
				movieList.map(function(item, index) {
					if(item){
						html += '' + '<div class="card" data-shop-id="'
							+ item.movieId + '">' + '<div class="card-header">'
							+ item.movieName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.movieImgAddr + '" width="150" height="100">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.movieInfo
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>'+ '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ "Release Time:"+item.releaseTime
							+ '</p>' + '<span>Infomation</span>' + '</div>'
							+ '</div>';
					}
				});
		
				// 将卡片集合添加到目标HTML组件里
				$('.list-div').append(html);
				
				saveJsonAsLocalStorage(data);
			}
		});
	}
	
	/**
	 * 为用户试实时推荐电影
	 */
	function addItemsForUser(userId) {
		// 获取json数据的url
		var url = serverAddr+"/MovieRecommend/movieadmin/getrecommendmovierealtime?userId="+userId;

		//开始获取json数据
		$.getJSON(url, function(data) {
			if (data.success) {
				var movieList = data.movieList;
				var html = '';
				// 遍历json中电影list，拼接出卡片集合
				movieList.map(function(item, index) {
					if(item){
						html += '' + '<div class="card" data-shop-id="'
							+ item.movieId + '">' + '<div class="card-header">'
							+ item.movieName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.movieImgAddr + '" width="150" height="100">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.movieInfo
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>'+ '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ "Release Time:"+item.releaseTime
							+ '</p>' + '<span>Infomation</span>' + '</div>'
							+ '</div>';
					}
					
				});
		
				// 将卡片集合添加到目标HTML组件里
				$('.list-div-realtime').append(html);
				
				//由于与离线推荐部相冲突，需要重新存储
				var str = JSON.stringify(data);
				localStorage.setItem("realTimeData",str);
			}
		});
	}
	
	/**
	 * 添加综合电影列表，电影不分类，游客模式中的今日推荐
	 *
	 */
	function addItems() {
		// 获取json数据的url
		var url = serverAddr+"/MovieRecommend/movieadmin/top10movie";

		//开始获取json数据
		$.getJSON(url, function(data) {
			if (data.success) {
				var movieList = data.movieList;
				var html = '';
				// 遍历json中电影list，拼接出卡片集合
				movieList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
							+ item.movieId + '">' + '<div class="card-header">'
							+ item.movieName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.movieImgAddr + '" width="150" height="100">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.movieInfo
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>'+ '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ "Release Time:"+item.releaseTime
							+ '</p>' + '<span>Infomation</span>' + '</div>'
							+ '</div>';
				});
		
				// 将卡片集合添加到目标HTML组件里
				$('.list-div').append(html);
				
				
				saveJsonAsLocalStorage(data);
			}
		});
	}
	
	/**
	 * 根据传入的categoryId，选择添加不同种类的电影
	 */
	function addCategoryItems(categoryId) {
		// 获取json数据的url
		var url = serverAddr+"/MovieRecommend/movieadmin/getmoviebycategory?categoryId="+categoryId;

		//开始获取json数据
		$.getJSON(url, function(data) {
			if (data.success) {
				var movieList = data.movieList;
				var html = '';
				// 遍历json中电影list，拼接出卡片集合
				movieList.map(function(item, index) {
					if(item){
						html += '' + '<div class="card" data-shop-id="'
							+ item.movieId + '">' + '<div class="card-header">'
							+ item.movieName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.movieImgAddr + '" width="150" height="100">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.movieInfo
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>'+ '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ "Release Time:"+item.releaseTime
							+ '</p>' + '<span>Infomation</span>' + '</div>'
							+ '</div>';
					}
					
				});
		
				// 将卡片集合添加到目标HTML组件里
				$('.list-div').append(html);
				
				saveJsonAsLocalStorage(data);
			}
		});
	}
	
	
	//将json转换成字符串
	//存储到cookie中
	function saveJsonAsLocalStorage(data){
		var str = JSON.stringify(data);
		localStorage.setItem("data", str);
	}

	// 点击卡片进入该电影的详情页
	$('.shop-list').on('click', '.card', function(e) {
		var movieId = e.currentTarget.dataset.shopId;
		//从LocalStorage中读取数据，只需要将movieId进行传递
		window.location.href = serverAddr+'/MovieRecommend/frontend/moviedetail?movieId=' + movieId;
	});

	//若点击"我的"，则显示侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	// 初始化页面
	$.init();
})
