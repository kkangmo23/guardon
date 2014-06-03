<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/js/__jquery.tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="<%=cp%>/js/plugin/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/plugin/asyncPaging.js"></script>


<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery-latest.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>


<script type="text/javascript">
function delete1(){
	var form = document.forms['check'];
	form.action='deleteConnectId.do';
	form.submit();

}
</script>

<script type="text/javascript">
$(function() {		
	$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
	$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
});	

</script>

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
  <div class="content">
   	<br/>
	<br/>
		<form method="post" name="check">
		 <table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
     	<thead>
     	<tr>
        	<th></th>
            <th>서버명</th>
            <th>접속 아이디</th>
            <th>접속 아이디 설명</th>
        </tr>
        </thead>
         <c:forEach var="i" items="${connectIdList}">
         	<tr>
              <td><input type="checkbox" name="checkList" value="${i.connectId},${i.serverName}" /></td>
              <td>${i.serverName}</td>
              <td>${i.connectId}</td>
              <td>${i.connectIdDesc}</td>
           </tr>   
         </c:forEach>
     </table>
     <center>

 	<br/>
 	<br/>
 	     <input type="button" value="삭 제" onclick="delete1()" />
 	      	</center>
     

     </form>
     <center>
     </center>
 	<br/>
	<br/>
	

	
    <!-- end .content --></div>
  <div class="footer">
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
</body>
</html>
