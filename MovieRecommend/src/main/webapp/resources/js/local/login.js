$(function(){
	var loginUrl=serverAddr+'/MovieRecommend/local/logincheck';

	var loginCount=0;
	
	$('#register').click(function(){
		window.location.href='/MovieRecommend/local/register';
	})
	$('#submit').click(function(){
		var userName=$('#username').val();
		var password =$('#pwd').val();
		var verifyCodeActual=$('#j_captcha').val();
		var needVerify=false;
		
		if(loginCount>=3){
			if(!verifyCodeActual){
				$.toast('please input vertification code！');
			}else{
				needVerify=true;
			}
		}
		$.ajax({
			url:loginUrl,
			async:false,
			cache:false,
			type:"post",
			dataType:'json',
			data:{
				userName:userName,
				password:password,
				verifyCodeActual:verifyCodeActual,
				needVerify:needVerify
			},
			success:function(data){
				if(data.success){
//					$.toast('登录成功！');
					alert('sign in succeed！');
					//登录成功之后，将userId存储在本地，以表明当前处在用户登录状态
					localStorage.setItem("userId",data.userId);
					localStorage.setItem("userName",data.userName);
					var Id = localStorage.getItem("userId");
					setTimeout("window.location.href='/MovieRecommend/frontend/index'",1000);
				}else{
//					$.toast('登录失败！');
					alert('sign in failed！');
					loginCount++;
					if(loginCount>=3){
						$('#verifyPart').show();
					}
				}
			}
		});
	});
});