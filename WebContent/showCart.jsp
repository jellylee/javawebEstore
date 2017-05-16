<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'index.jsp' starting page</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/style.css"
	type="text/css" media="screen" />


<script type="text/javascript">
	function numbText(e) {

		//考虑浏览器兼容问题
		if (e && e.stopPropagation) { //e存在
			code = e.which; //firefox获取键码
		} else {
			//不存在 --对应ie浏览器
			code = window.event.keyCode;  //id浏览器中获取键码值
		}

		//alert(code);
		//48--57代表的是数字0-9    8与46代表的是delete 与backspace
		if (!(code >= 48 && code <= 57 || code == 8 || code == 46)) {
			//阻止事务的默认行为执行.

			if (e && e.stopPropagation) { //e存在
				e.preventDefault(); //firefox阻止默认事务执行			

			} else {
				//不存在 --对应ie浏览器
				window.event.returnValue = false; //ie浏览器阻止默认事件执行.

			}

		}
	}

	//id代表要修改的商品    count代表商品的数量
	function changeCount(id, count, max) {

		if (parseInt(count) < 0) {
			count = 1;
		}

		if (parseInt(count) > parseInt(max)) {
			count = max;
		}
		
		if(parseInt(count)==0){
			
			var flag=window.confirm("确认删除商品吗");
			if(!flag){
				return ;
			}
		}

		//向一个servlet发送请求

		location.href = "${pageContext.request.contextPath}/cart?method=update&id=" + id
				+ "&count=" + count;

	}
	
	function delConfirm(e){
		var flag=window.confirm("确认删除商品吗");
		
		if(!flag){
			//不删除商品		
			//要想不删除商品，要阻止事件的默认行为执行.
			if(e&&e.preventDefault){
				// e对象存在，preventDefault方法存在 ---- 火狐浏览器
				e.preventDefault();
			}else{
				// 不支持e对象，或者没有preventDefault方法 ---- IE
				window.event.returnValue = false;
			}

		}
		
	}
	
	function gotoorder(){
		
		location.href="${pageContext.request.contextPath}/order.jsp";	
	}
</script>
</head>

<body>
	<br>
	<br>
	<div align="center">
		<c:if test="${empty cart}">
  			购物车无商品
  		</c:if>

		<c:if test="${not empty cart}">
			<table align="center" border="1">

				<c:set var="all" value="0" />

				<c:forEach items="${cart}" var="entry">

					<tr>
						<td>商品名称:${entry.key.name }</td>
						<td>商品单价:${entry.key.price }</td>
						<td>
						<input type="button" value="-"
							onclick="changeCount('${entry.key.id }','${entry.value-1}','${entry.key.pnum}')">

						<input type='text' value="${entry.value}"
							style="text-align:center" onkeydown="numbText(event);"
							onblur="changeCount('${entry.key.id}',this.value,'${entry.key.pnum}')">
						
						<input type="button" value='+'
							onclick="changeCount('${entry.key.id}','${entry.value+1}','${entry.key.pnum }')">
						</td>


						<td>可购买数量:${entry.key.pnum}</td>
						<td><a
							href="${pageContext.request.contextPath}/cart?method=remove&id=${entry.key.id}"
							onclick="delConfirm(event)">删除</a>					
						
						</td>
					</tr>

					<c:set var="all" value="${all+(entry.key.price*entry.value)}" />
				</c:forEach>
				<tr>
					<td colspan="5" align="right">总价:￥${all}元</td>
				</tr>
				<tr>
					<td colspan="5" align="right"><img
						src="${pageContext.request.contextPath}/images/gotoorder.bmp"
						onclick="gotoorder()" onmousemove="this.style.cursor='hand'"></td>
				</tr>
			</table>


		</c:if>
	</div>
</body>
</html>
