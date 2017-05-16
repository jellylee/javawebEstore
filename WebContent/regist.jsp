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
	function change() {
		document.getElementById("im").src = "${pageContext.request.contextPath}/checkcode?time"
				+ new Date().getTime();
	};

	function checkForm() {
		
		//先得到所有的span,将其内容清空。
		var spans=document.getElementsByTagName("span");
		
		for(var i=0;i<spans.length;i++){
			spans[i].innerHTML="";
		}

		//验证用户名
		var flag1=checkNull("username");
		//验证密码
		var flag2=checkNull("password");
		
		//验证确认密码
		var flag3=checkNull("repassword");
		
		var flag4=checkNull("nickname");
		var flag5=checkNull("email");
		var flag6=checkNull("checkcode");
		
		
		//对确认密码进行操作
		var flag7=repasswordValidate();

		return flag1&&flag2&&flag3&&flag4&&flag5&&flag6&&flag7;
	};
	
	function repasswordValidate(){
		
		var value1=document.getElementById("password").value;
		var value2=document.getElementById("repassword").value;
		
		if(value1==value2){
			return true;
		}else{
			document.getElementById("repassword_span").innerHTML="两次密码不一致";
			return false;
		}
	}
	
	//非空操作
	function checkNull(field){
		var reg = /^\s*$/; //代表0个或多个空白符
		
		var value = document.getElementById(field).value;
		
		if (reg.test(value)) {
			document.getElementById(field+"_span").innerHTML=(field+"不能为空");
			return false;
		}
		
		return true;
	}
</script>

<style type="text/css">
span {
	font-color: red;
}
</style>

</head>

<body>
	${requestScope["regist.message"] }
	<br>
	<c:forEach items="${map}" var="m">
		${m.value }<br>
	</c:forEach>
	<form action="${pageContext.request.contextPath }/user" method="post"
		onsubmit="return checkForm();">
		<input type="hidden" name="method" value="regist"><!-- 我们当前是一个注册操作 -->
		<table border="1" align="center" width="65%">
			<caption>用户注册</caption>
			<tr>
				<td>用户名</td>
				<td><input type="text" name="username" id="username"><span
					id="username_span"></span></td>
			</tr>


			<tr>
				<td>密码</td>
				<td><input type="password" name="password" id="password"><span
					id="password_span"></span></td>
			</tr>

			<tr>
				<td>确认密码</td>
				<td><input type="password" name="repassword" id="repassword"><span
					id="repassword_span"></span></td>
			</tr>

			<tr>
				<td>昵称</td>
				<td><input type="text" name="nickname" id="nickname"><span
					id="nickname_span"></span></td>
			</tr>

			<tr>
				<td>邮箱</td>
				<td><input type="text" name="email" id="email"><span
					id="email_span"></span></td>
			</tr>

			<tr>
				<td>验证码</td>
				<td><input type="text" name="checkcode" id="checkcode"><img
					src='${pageContext.request.contextPath}/checkcode' id="im"
					onclick="change();"><span id="checkcode_span"><a
						href="javascript:void(0)" onclick="change();"><font
							color='black'>看不清，换一张</font>
					</a>
				</span></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="注册">&nbsp;&nbsp;&nbsp;&nbsp;<input
					type="reset" value="取消"></td>
			</tr>

		</table>
	</form>
</body>
</html>
