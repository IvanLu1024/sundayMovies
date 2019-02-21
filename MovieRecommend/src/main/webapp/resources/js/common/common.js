Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
/**
 * 
 */
function changeVerifyCode(img) {
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}

//toast显示
function showtoastFromDiv(text) {
	 $('.card').show();
	 $('.card').toast({
	   content: text,
	   duration: 1000
	 })
	 setTimeout(() => {
	   $('.toast_div').hide();
	 }, 2000);
}

//服务器地址
var serverAddr = "";

//设置用户登录状态
var userName = localStorage.getItem("userName");
if(userName){
	$('#login-state').html("hello, "+userName);
	$('#signin').hide();
	$('#signout').show();
}else{
	$('#login-state').html("sign in please")
	$('#signin').show();
	$('#signout').hide();
}

//sign out 绑定事件
var signoutUrl="/MovieRecommend/local/logout"
	$('#signout').click(function() {
		//$.openPanel('#panel-right-demo');
		$.ajax({
			url : signoutUrl,
			cache : false,
			type : 'post',
			dataType:'json',
			data : {
			},
			success : function(data) {
				if (data.success) {
					$.toast('sign out succeed！');
					localStorage.removeItem("userId");
					localStorage.removeItem("userName");
					setTimeout(function(){window.location.href='/MovieRecommend/frontend/index';},2000) ;
				} else {
					$.toast('sign out failed！'+data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

