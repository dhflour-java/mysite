<%@page import="kr.co.dhflour.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String result = request.getParameter( "r" );
	UserVo vo = (UserVo)request.getAttribute( "userVo" );
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

				<form id="join-form" name="joinForm" method="post" action="/mysite/user">
					<input type="hidden" name="a" value="modify">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="<%=vo.getName() %>">

					<label class="block-label" for="email">이메일</label>
					<h4><%=vo.getEmail() %></h4>
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<%
							if("female".equals(vo.getGender())){
						%>
						<label>여</label>
						<input type="radio" name="gender" value="female" checked="checked">
						<label>남</label>
						<input type="radio" name="gender" value="male">
						<%
							} else {
						%>
						<label>여</label>
						<input type="radio" name="gender" value="female" >
						<label>남</label>
						<input type="radio" name="gender" value="male" checked="checked">			
						<%
							}
						%>
					</fieldset>
					
					<input type="submit" value="수정하기">
					
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/include/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>
<%
	if( "success".equals(result) ){
%>
<script>
	alert( "성공적으로 수정 하였습니다." );
</script>
<%
	}
%>