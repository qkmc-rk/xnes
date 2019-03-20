
var rootPath = $("input[name=contextPath]").val();



//定义上传的函数
function ajaxPublish() {
	//首先获取用户名和密码
	var title = $("input[name=title]").val();
	var timeout = $("input[name=timeout]").val();
	var tip = $("input[name=tip]").val();
	var reward = $("input[name=reward]").val();
	var userid = $("input[name=userid]").val();

	var content = editor.txt.html();
	//alert(title + timeout + tip + reward + content + userid);

	if(title == null || timeout == null || tip == null || 
			reward == null || userid == null || title == '' ||
			timeout == '' || tip == '' || reward == '' || userid == ''){
		stillNull();
		return;
	}
	
	//发送ajax请求进行登录
	$.ajax({
		type : "post",
		url : rootPath + "/task/publish",
		async : true,
		dataType : 'json',
		data : {
			'title' : title,
			'timeout' : timeout,
			'tip' : tip,
			'reward' : reward,
			'userid' : userid,
			'content' : content
		},
		//根据返回值
		success : function(data) {
			//var obj = JSON.parse(data);
			if (data.result == 'false') {
				publishfailed();
			} else {
				publishsuccess();
				window.location.href = "index";
			}
		},
		error : function() {
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
function serviceerror(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('服务器错误');
	});
}
function publishsuccess(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('发布成功');
	});
}
function publishfailed(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('发布失败');
	});
}
