$(function(){
	var bindUrl='/xqt/local/bindlocalauth';
	//var usertype=getQueryString('usertype');
	$('#submit').click(function(){
		$.toast("click");
		var userName=$('#username').val();
		var password=$('#pwd').val();
		var verifyCodeActual=$('#j_captcha').val();
		var needVertify=false;
		alert(password);
		if(!verifyCodeActual){
			$.toast("请输入验证码");
			return;
		}
		$.ajax({
			url:bindUrl,
			async:false,
			cache:false,
			type:"post",
			dataType:'json',
			data:{
				userName:userName,
				password:password,
				verifyCodeActual:verifyCodeActual
			},
			success:function(data){
				if(data.success){
					$.toast("绑定成功!");
					window.location.href='/xqt/frontend/index';
					/*if(usertype==1){
						window.location.href='/xqt/frontend/index';
					}else{
						window.location.href='/xqt/shopadmin/shoplist';
					}*/
				}else{
					$.toast("提交失败！"+data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});
});