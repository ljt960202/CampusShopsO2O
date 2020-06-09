$(function() {
	//登录验证的url
	var loginUrl = '/o2o/local/logincheck';
	var usertype = getQueryString("usertype");

	var loginCount = 0;
	$('#submit').click(function() {
		//获取输入的账号
		var userName = $('#username').val();
		//获取输入的密码
		var password = $('#psw').val();
		//获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		//是否需要验证码验证。默认为false。即不需要
		var needVerify = false;
		if (loginCount >= 3) {
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					if(usertype==1){
						//若用户在前端展示系统页面则自动链接到前端展示首页
						window.location.href = '/o2o/frontend/index';
					}else{
						//若用户在店家管理系统页面则自动链接到店铺列表页中
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast('登录失败！'+data.errMsg);
					loginCount++;
					if (loginCount >= 3) {
						$('#verifyPart').show();
					}
				}
			}
		});
	});
//
//	$('#register').click(function() {
//		window.location.href = '/o2o/shop/register';
//	});
});