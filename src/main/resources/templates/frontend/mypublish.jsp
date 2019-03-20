<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title>大学生校园互助平台</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/layui/css/layui.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/public.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/bootstrap.css"/>
		<script src="<%=request.getContextPath()%>/static/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/static/layui/layui.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/static/js/wangEditor.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/static/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>	
	</head>

	<body>
		<div class="layui-fluid">
			
			<!--导航栏-->
			<ul class="layui-nav" layui-filter="">
				<li class="layui-nav-item logo">
					<h2>我的 - 大学生校园互助平台</h2>
				</li>
				<!--页面链接-->
				<li class="layui-nav-item"><a href="publish">发布信息</a></li>
				<li class="layui-nav-item"><a href="mypublish">我的发布</a></li>
				<li class="layui-nav-item"><a href="mywork">我的任务</a></li>
				<li class="layui-nav-item"><a href="works">最新发布</a></li>
				<!--//页面链接-->
				<li class="layui-nav-item"><a href="center">个人中心<span
						class="layui-badge-dot"></span></a></li>
				<li class="layui-nav-item"><a href="center"><img
						src="<%=request.getContextPath()%>/static/img/login/avtar.png"
						class="layui-nav-img" />${userPrimInfo.neckname }</a></li>
			</ul>
			<!--//导航栏-->
			<!--主要内容-->
			<div class="layui-row">
				<!--标题-->
				<div class="site-title">
					<fieldset>
						<legend>
							<a name="use" style="text-decoration: none; color: gainsboro;">我发布的任务</a>
						</legend>
					</fieldset>
				</div>
				<div class="site-text site-block mypublish-block">
					<!--基础信息内容-->
					<c:forEach items="${helpInfoList }" var="helpInfo" varStatus="status">
						<div class="layui-elem-quote mypublish1">
							<p>
								<i style="color: red;">${helpInfo.title }</i> 
								| 创建时间:<b style="color:#555555">
								<fmt:formatDate value="${helpInfo.crettime }" pattern="yyyy-MM-dd"/></b>
								| 截止时间:<b style="color:#555555"><jsp:setProperty name="dateValue" property="time" value="${helpInfo.timeout }"/>
								<fmt:formatDate value="${dateValue }" pattern="yyyy-MM-dd"/></b> | 赏金<b>${helpInfo.reward }</b>元
								<!--任务状态:  -->
								<c:if test="${helpStateList[status.index].received > 0}">
									<c:if test="${helpStateList[status.index].achieved > 0}">
										| <big style="color:green"> 已完成</big>
									</c:if>
									<c:if test="${helpStateList[status.index].achieved < 1}">
										| <big style="color:red"> 已被接受</big>
									</c:if>
									
								</c:if>
								<c:if test="${helpStateList[status.index].received < 1 && helpStateList[status.index].achieved < 1}">
									| <big style="color:gray"> 等待接受</big>
								</c:if>
							</p>
							<p>
							<!--已完成!  -->
							<c:if test="${helpStateList[status.index].achieved > 0}">
								[<a href="my_detail?infoid=${helpInfo.id }">查看</a>]
							</c:if>
							<!--未完成  -->
							<c:if test="${helpStateList[status.index].achieved < 1}">
								<!--已被接受  -->
								<c:if test="${helpStateList[status.index].received == 1}">
									[<a href="my_detail?infoid=${helpInfo.id }">查看</a>]
								</c:if>
								<!--未被接收  -->
								<c:if test="${helpStateList[status.index].received == 0}">
									[
									<a href="javascript:delete_(${helpInfo.id });">删除</a>] | [
									<a href="javascript:showchangeMoney(${helpInfo.id });">更改赏金</a>] | [
									<a href="javascript:showchangeDate(${helpInfo.id });">更改时间</a>] | [
									<a href="my_detail?infoid=${helpInfo.id }">查看</a>]
								</c:if>
							</c:if>
							</p>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- modal -->
		<div style="display:none">
			<div id="chosseMoney">
				<input type="number" id="mymoney" name="money" placeholder="请输入赏金" class="layui-input">
			</div>
			<div id="chosseDate">
				<input type="text" name="mydate" class="layui-input" id="mydate">
				<script>
					layui.use('laydate', function(){
					  var laydate = layui.laydate;
					  
					  //执行一个laydate实例
					  laydate.render({
					    elem: '#mydate' //指定元素
					  });
					});
				</script>
			</div>
		</div>
		<!--modal结束  -->
		<!-- js -->
		<script type="text/javascript">
		
			function delete_(objs){
				var infoid = objs;

				$.ajax({
					type:"post",
					url:'<%= request.getContextPath() %>/task/' + 'delete',
					async:true,
					data:{
						'infoid':infoid
					},
					//根据返回值
					success:function(data){
						var obj = JSON.parse(data);
						if(obj != null && obj.result == 'true' || data.result == 'true'){
							succ();
							setInterval(function() {
								window.location.reload();
							}, 500)
						}else if(obj == null || data.result == 'false'){
							fail();
						}
					},
					error:function(){
						fail();
					}
				});
			}
			/* 更改赏金 */
			
			function changereward(objs){
				var infoid = objs;
				$.ajax({
					type:"post",
					url:"<%= request.getContextPath() %>/task/changereward",
					async:true,
					data:{
						'infoid':infoid,
						'reward':$('#layer-money').val()
					},
					//根据返回值
					success:function(data){
						var obj = JSON.parse(data);
						if(obj != null && obj.result == 'true'){
							succ();
						}else{
							fail();
						}
					},
					error:function(){
						fail();
					}
				});
			}
			
			function changetime(objs){
				
				alert($('#layer-date').val());
				
				var infoid = objs;
				$.ajax({
					type:"post",
					url:"<%= request.getContextPath() %>/task/changetimeout",
					async:true,
					data:{
						'infoid':infoid,
						'timeout':$('#layer-date').val()
					},
					//根据返回值
					success:function(data){
						var obj = JSON.parse(data);
						if(obj != null && obj.result == 'true'){
							succ();
						}else{
							fail();
						}
					},
					error:function(){
						fail();
					}
				});
			}

			/*modal  */
			function showchangeMoney(obj){
				layui.use('layer',function(){
					 var layer = layui.layer;
					 layer.open({
						title: '更改赏金'
						,content:'<input type="number" id="layer-money" name="layer-money" placeholder="请输入赏金" class="layui-input">'
						,btn:['更改']
						,yes:function(index,layero){
							changereward(obj);
						}
					});    
				});
			}
			function showchangeDate(obj){
				layui.use('layer',function(){
					 var layer = layui.layer;
					 layer.open({
						title: '更改截止日期'
						,content: '<input type="text" id="layer-date" name="layer-date" placeholder="时间格式:yyyy-MM-dd" class="layui-input">'
						,btn:['更改']
						,yes:function(index,layero){
							changetime(obj);
						}
					});    
				});
			}
			/*提示框  */
			function succ(){
				layui.use('layer',function(){
					 var layer = layui.layer;
					 layer.msg('成功');
				});
			}
			function fail(){
				layui.use('layer',function(){
					 var layer = layui.layer;
					 layer.msg('失败');
				});
			}
		</script>
	</body>

</html>