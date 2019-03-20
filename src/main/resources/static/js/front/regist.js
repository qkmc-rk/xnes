
function ajax_regist(){
	//首先获取信息
	var firstname = $("#firstname").val();
	var lastname = $("#lastname").val();
	var stuid = $("#stuid").val();
	var account = $("#account").val();
	var password = $("#password").val();
	var password2 = $("#password2").val();
	
	var usermail = $("#usermail").val();
	var question1 = $("#question1").val();
	var answer1 = $("#answer1").val();
	var question2 = $("#question2").val();
	var answer2 = $("#answer2").val();
	var question3 = $("#question3").val();
	var answer3 = $("#answer3").val();
	
	
	
	if(password != password2){
		alert("两次输入密码不一致");
		return;
	}
	if(firstname == null || lastname == null || stuid==null || 
			account == null || password == null || password2 == null || firstname == '' ||
			lastname == '' || stuid == '' || account == '' || password == '' || password2 == ''){
		stillNull();
		return;
	}
	if(usermail == null || question1 == null || answer1 ==null || question2 == null || 
			answer2 == null || question3 == null || answer3 == null || usermail == '' || question1 == '' || answer1 == '' || question2 == '' || 
			answer2 == '' || question3 == '' || answer3 == ''){
		stillNull();
		return;
	}
	
	//开始注册
	$.ajax({
		type:"post",
		url:"user/regist",
		async:true,
		data:{
			'firstname':firstname,
			'lastname':lastname,
			'stuid':stuid,
			'account':account,
			'password':password,
			'usermail':usermail,
			'question1':question1,
			'answer1':answer1,
			'question2':question2,
			'answer2':answer2,
			'question3':question3,
			'answer3':answer3,
		},
		//根据返回值
		success:function(data){
			var obj = JSON.parse(data);
			if(obj.result == 'false'){
				registfailed();
			}else{
				registsuccess();
				//暂时这样设置,会出问题
				window.location.href = "/campusMutual/logpage";
			}
		},
		error:function(){
			serviceerror();
		}
	});
}

function stillNull(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('请填写完整!');
	});
}
function registsuccess(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('注册成功,请返回登录');
	});
}
function registfailed(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('注册失败');
	});
}
function serviceerror(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('服务器错误');
	});
}