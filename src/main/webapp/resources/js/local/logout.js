$(function() {
	$('#log-out').click(function() {
		//消除session
		$.ajax({
			url : "/o2o/local/logout",
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					var usertype = $("#log-out").attr("usertype");
					//清楚成功后退出到登录界面
					window.location.href='/o2o/local/login?usertype='+usertype;
					return false;
				}
			},
			error:function(data,error){
				alert(error);
			}
		});
	});
});