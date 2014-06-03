<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />


</head>

<body>

<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong> 서버 관리 </div>
  <div class="sidebar1">
    <ul class="nav">
      <li><a href="serverInstall.do">일반 서버 등록</a></li>
      <li><a href="dbInstall.do">DB 서버 등록</a></li>
      <li><a href="updateServerSelect.do">서버 정보 수정</a></li>
      <li><a href="dbList.do">서버 리스트</a></li>
      <li><a href="connectIdSelect.do">접속 아이디 등록</a></li>
      <li><a href="connectIdList.do">접속 아이디 삭제</a></li>
      <!-- <li><a href="deleteServerSelect.do">서버 삭제</a></li> -->
      <li><a href="userLogout.do">로그아웃</a></li>
    </ul>
    <p>&nbsp;</p>
  <!-- end .sidebar1 --></div>
  <div>
   	<br/>
	<br/>

 	<br/>
	<br/>
	

	
    <!-- end .content --></div>
  <div class="footer">
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
</body>
</html>
