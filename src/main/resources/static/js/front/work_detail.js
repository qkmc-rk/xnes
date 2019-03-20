function receive(){
	var infoid = $("#infoid").val();
	
	var root = $("#rootPath").val();
	//开始注册
	$.ajax({
		type:"post",
		url:root +"/task/receive",
		async:true,
		data:{
			'infoid':infoid
		},
		//根据返回值
		success:function(data){
			var obj = JSON.parse(data);
			if(obj.result == 'true'){
				receivesuccess();
				window.location.href='mywork';
			}else{
				receivefailed();
			}
		},
		error:function(){
			serviceerror();
		}
	});
}

function receivefailed(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('失败了(注意:自己发布的项目无法自己接受)');
	});
}
function receivesuccess(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('成功!');
	});
}
function serviceerror(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('服务器错误');
	});
}
