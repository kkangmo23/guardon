<%@ page contentType="text/html; charset=UTF-8"%>
<%
 String cp = request.getContextPath();
%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>무제 문서</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/style/portal/magic_common.css" />
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript">

	function sub() {
		var form = document.forms['findId'];
			form.action = 'index.do';
			form.submit();
	}
		
	function sub1() {
		var form = document.forms['findId'];
		var userName = document.findId.userId.value;
		var companyNumber = document.findId.userEmail.value;
		
		if (userName.length<=0 || companyNumber.length<=0) {
			alert("입력사항을 모두 입력 하십시오");
		}else{
			form.action = 'getPwd.do';
			form.method = 'POST';
			form.submit();
		}
		/*
		var check = false;
		
		for (var i = 0; i < userId.length; i++) {
			if(userId[i].checked!=""){
				check=true;
				break;
			}
		}
		
		if (check==false) {
			alert("하나 이상의 서버를 체크해 주십시오");
		}
		if(check==true){
			form.action = 'approvalJoinReq.do';
			form.submit();
		}*/
		}
</script>
</head>

<body>

<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong> 비밀번호 찾기</div>

  <div>
  <br/>
<br/>
<br/>
  <center>
  <h1>찾은 비밀번호 : ${userPwd}</h1>
  <h1>로그인</h1>

<center>
<form action="userLogin.do" method="post">
<br/>
<pre>
아 이 디	<input type="text" name="userId" id="userId" />
<br/>
비밀번호	<input type="password" name="userPwd" id="userPwd" />
</pre>
<br/>
<br/>
<center>
<input type="submit" value="로 그 인" style="height:50px; width:100px;"/> 
<a href="userJoin.do">
<input type="button" value="회원가입" style="height:50px; width:100px;"/>
</a>

</center>
<br/>
<br/>
<a href="findId.do" style="color: green;">아이디 찾기 </a>/<a href="findPwd.do" style="color: green;"> 비밀번호 찾기</a>
</form>
<br/>
<br/>
</center>
<br/>
<br/>
</center>
    <!-- end .content --></div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
</body>
</html>