$(function() {	
	
	var registerUrl = '/MovieRecommend/local/registercheck';
	
	$('#back').click(function() {
		window.location.href='/MovieRecommend/local/login';
	});
	
	
	$('#submit').click(function() {
		//获取用户填写的信息
		var userName=$.trim($('#userName').val());
		var password=$.trim($('#password').val());
		var confirmPassword=$.trim($('#confirmPassword').val());
		var email = $.trim($('#email').val());
		var verifyCodeActual = $('#j_captcha').val();
		
		//获取复选框--用户喜欢的电影种类
		var chk_perefence=new Array();  
		$('input[name="interest"]:checked').each(function(){  
			chk_perefence.push($(this).val());//向数组中添加元素  
		});  
		var perefenceStr=chk_perefence.join(',');//将数组元素连接起来以构建一个字符串
		
		if (!userName) {
			$.toast('请输入用户名！');
			return;
		}
		if (!password) {
			$.toast('请输入密码！');
			return;
		}
		if (password != confirmPassword) {
			$.toast('两次输入的密码不一致');
			return;
		}
		if (!email) {
			$.toast('请输入email');
			return;
		}
		var reg = /\w+[@]{1}\w+[.]\w+/;
		if(!reg.test(email)){
			$.toast('email格式不正确');
			return;
		}
			
		
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		
		//向后台    /local/registercheck
		//发送用户填写的信息
		$.ajax({
			url : registerUrl,
			cache : false,
			type : 'post',
			dataType:'json',
			data : {
				userName:userName,
				password:password,
				email:email,
				gender:null,
				verifyCodeActual:verifyCodeActual,
				perefenceStr:perefenceStr
			},
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					setTimeout(function(){window.location.href='/MovieRecommend/frontend/index';},2000) ;

					
				} else {
					$.toast('提交失败！'+data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});

	
});
