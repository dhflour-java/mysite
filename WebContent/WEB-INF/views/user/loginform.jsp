<%@ page contentType="text/html;charset=UTF-8" %>
<%
	String result = request.getParameter( "r" );
%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="user">
				<form id="login-form" name="loginform" method="post" action="/mysite/user">
					<input type='hidden' name="a" value="login">
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<label class="block-label" >패스워드</label>
					<input name="password" type="password" value="">
					<%
						if( "fail".equals(result) ){
					%>
					<p>
						로그인이 실패 했습니다.
					</p>
					<%
						}
					%>
					<input type="submit" value="로그인">
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/include/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>