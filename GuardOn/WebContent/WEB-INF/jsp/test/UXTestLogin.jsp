<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String cp=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	
	<link rel="stylesheet" type="text/css" href="<%=cp %>/style/main.css" media="screen" />
	<link rel="stylesheet" href="<%=cp %>/style/images/fancybox/jquery.fancybox-1.3.2.css" type="text/css" media="screen" />
</head>
<body>
<div id="content">
	
		<!-- top -->
		<div id="top">
			<h1 id="logo"><a href="UXTest.do">Guard<span>On</span></a></h1>
					
		</div>
		<!-- /top -->
			
		<br/><br/><br/><br/>
		
		
		<form action="userLogin.do" method="post" autocomplete="off">
			<div id="login">
				<h2>Log In</h2><br/>
				<table>
				<tr>
				<td>아 이 디</td>
				<td><input type="text" name="userId"></td>
				<td rowspan="2"><input type="submit" value="로 그 인"/> <br/></td>				
				</tr>
				<tr>				
				<td>비밀번호</td>
				<td><input type="password" name="userPwd"></td>
				</tr>
				<tr>
				<td colspan="3" style="text-align: center;"><a href="findId.do">아이디 찾기 </a>/<a href="findPwd.do"> 비밀번호 찾기 </a>/<a href="userJoin.do"> 회원가입</a></td>
				</tr>
				</table>				
			</div>
		</form>
		
		
		<!-- footer -->
		<div id="footer">
			<p>&copy; 2014 GuardOn &middot; All Rights Reserved &middot; Onsol IT</p>
		</div>
		<!-- /footer -->
	</div>
</body>
</html>