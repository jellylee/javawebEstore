<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>生成订单</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/style.css"
	type="text/css" media="screen" />
<style>
cursor:pointer 
</style>
</head>

<body>
	<br>
	<div align="center">
		<font size="5">订单信息</font>
	</div>
	<br>
	<form action="${pageContext.request.contextPath}/order"
		method="post">
		<input type="hidden" name="method" value="add">
		<table align="center" width="400px" border="1">
			<tr>
				<td>收货地址</td>
				<td><input type="text" name="receiverinfo">
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<table border="1" width="400px">
						<tr>
							<td>商品名称</td>
							<td>单价</td>
							<td>数量</td>
						</tr>
						<c:set value="0" var="all" />
						<c:forEach items="${cart}" var="entry">
							<tr>
								<td>${entry.key.name}</td>
								<td>${entry.key.price}</td>
								<td>${entry.value}</td>
							</tr>
							<c:set value="${entry.key.price*entry.value+all}" var="all" />
						</c:forEach>
					</table></td>
			</tr>
			<tr>
				<td>订单总价:${all}元 <input type="hidden" value="${all}"
					name="money"></td>
				<td align="right"><input type="submit" value="生成订单" style="cursor:pointer">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>
