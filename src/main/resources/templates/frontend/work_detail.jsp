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
		<script src="<%=request.getContextPath()%>/static/js/front/work_detail.js" type="text/javascript" charset="utf-8"></script>	
	</head>

	<body>
		<div class="layui-fluid">
			<!--导航栏-->
			<ul class="layui-nav" layui-filter="">
				<li class="layui-nav-item logo">
					<h2>任务详情- 大学生校园互助平台</h2>
				</li>
				<!--页面链接-->
				<li class="layui-nav-item"><a href="publish">发布信息</a></li>
				<li class="layui-nav-item"><a href="mypublish">我的发布</a></li>
				<li class="layui-nav-item"><a href="mywork">我的任务</a></li>
				<li class="layui-nav-item"><a href="works">最新发布</a></li>
				<!--//页面链接-->
				<li class="layui-nav-item">
					<a href="center">个人中心<span class="layui-badge-dot"></span></a>
				</li>
				<li class="layui-nav-item">
					<a href="center"><img src="<%=request.getContextPath()%>/static/img/login/avtar.png" class="layui-nav-img" />${userPrimInfo.neckname }</a>
				</li>
			</ul>
			<!--//导航栏-->
			<!--主要内容-->
			<div class="layui-row">
				<!--标题-->
				<div class="site-title">
					<fieldset>
						<legend>
							<a name="use" style="text-decoration: none; color: gainsboro;">任务详情</a>
						</legend>
					</fieldset>
				</div>
				<div class="site-text site-block mypublish-block">
					<!--基础信息内容-->
					<div class="content">
						<input type="text" id="infoid" value="${helpInfo.id }" style="display: none;">
						<input type="text" id="rootPath" value="<%= request.getContextPath() %>" style="display: none;">
						<h1 style="width: 100%; text-align: center;">${helpInfo.title }<small> - 备注:${helpInfo.tip }</small></h1>
						<hr />
						<h2 style="width: 100%; text-align: center;">赏金: <big style="color: red;">${helpInfo.reward }</big>元</h2>
						<hr />
						<p style="text-align: center;">${helpInfo.content }</p>
						<hr />
						<p>创建时间:<b style="color:#555555"><fmt:formatDate value="${helpInfo.crettime }" pattern="yyyy-MM-dd"/></b> | 截止时间:<b style="color:#555555"><jsp:setProperty name="dateValue" property="time" value="${helpInfo.timeout }"/><fmt:formatDate value="${dateValue }" pattern="yyyy-MM-dd"/></b></p>
						<h3 style="display: inline-block;">联系电话<big><a style="color: red;">
							<c:if test="${helpState.receiverid == user.id }">${userPrimInfo.userphone }</c:if>
							<c:if test="${helpState.receiverid != user.id }">***********</c:if>
							</a></big></h3>[<a style="display: inline-block;" href="">接收任务以查看完整联系方式</a>]
					</div>
					<button class="layui-btn layui-btn-lg" onclick="receive()">接受任务</button>
				</div>
			</div>
		</div>
	</body>

</html>