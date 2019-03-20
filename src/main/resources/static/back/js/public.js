/* 用于存放登录代码, */
function tologin(){
	var account = $("input[name=account]").val();
	var password = $("input[name=password]").val();

	if(account == '' || password == '' || account == null || password == null){
		nullMsg();
		return;
	}

	// 使用ajax进行登录操作
	$.ajax({
		type:"post",
		url:"login",
		async:true,
		data:{
			'account':account,
			'password':password
		},
		// 根据返回值
		success:function(data){
			var obj = JSON.parse(data);
			if(obj.result == 'false'){
				failedMsg();
			}else{
				// 将token和useri存到localStorage
				var ls = window.localStorage;
				ls.setItem("userid", obj.userid);
				ls.setItem("token", obj.token);
				// 登陆成功
				successloginMsg();
				// 跳转到index.
				setInterval(() => {
					if(ls.getItem('userid') != null && ls.getItem('token')){
						window.location.href="index";
					}
					else{
						failedLSMsg();
					}
				}, 1500);
			}
		},
		error:function(){
			failedMsg();
		}
	});
}


function toreset(){
	var account = $("input[name=account]").val("");
	var password = $("input[name=password]").val("");
	resetMsg();
}

function nullMsg(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('必填字段未填写');
	});
}
function resetMsg(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('重置成功');
	});
}
function failedMsg(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('失败了!');
	});
}
function successloginMsg(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('成功');
	});
}
function failedLSMsg(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('登录获取的信息有误,请重新登录尝试!');
	});
}

/*退出登录  */
function loginoff(){
	
	/* 取消localStorage */
	var ls = window.localStorage;
	ls.clear();
	successMsg();
	window.location.href="loginpage";
}


function successMsg(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('已经注销');
	});
}