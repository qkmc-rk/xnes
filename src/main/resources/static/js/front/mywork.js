function finish(obj){
	var infoid = $(obj).attr('title');
	//alert(infoid);
	var root = $("#rootPath").val();
	
	//开始注册
	$.ajax({
		type:"post",
		url:root +"/task/finish",
		async:true,
		data:{
			'infoid':infoid
		},
		//根据返回值
		success:function(data){
			if(data.result == 'false'){
				finishfailed();
			}else{
				finishsuccess();
			}
		},
		error:function(){
			serviceerror();
		}
	});
}

function finishfailed(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('失败了');
	});
}
function finishsuccess(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('成功提交了!');
	});
}
function serviceerror(){
	layui.use('layer',function(){
		 var layer = layui.layer;
		 layer.msg('服务器错误');
	});
}