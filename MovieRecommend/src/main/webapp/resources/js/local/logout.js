$(function(){
	$('#log-out').click(function(){
		$.ajax({
			url:'/xqt/local/logout',
			type:"post",
			async:false,
			cache:false,
			dataType:'json',
			success:function(data){
				if(data.success){
					var usertype=$("#log-out").attr("usertype");
					/*window.location.href="/xqt/local/login?usertype="+usertype;*/
					window.location.href="/xqt/local/login";
					return false;
				}
			},
			error:function(data,error){
				alert(error);
			}
		});
	});
});