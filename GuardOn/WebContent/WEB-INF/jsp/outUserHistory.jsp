<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/js/__jquery.tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/style/plugin/asyncPaging.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="<%=cp%>/js/plugin/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/plugin/asyncPaging.js"></script>

<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery-latest.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>

<script type="text/javascript">
$(function() {		
	$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
	$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
});	

$(function() {		
	$("#tablesorter-demo1").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
	$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
});	

</script>

</head>

<body>

<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong> 외부 사용자 승인내역 확인</div>
  <div class="sidebar1">
    <ul class="nav">
      <li><a href="outUserHistory.do">비밀번호 발급요청 현황 / 이력 조회</a></li>
      <li><a href="outUserOtpRequest.do">일회용 비밀번호 발급요청</a></li>
      <li><a href="outUserOtpPretreatment.do">일회용 비밀번호 발급</a></li>
      <li><a href="outPeriodPwdServerSelect.do">주기적 비밀번호 발급요청</a></li>
      <li><a href="outPeriodPwdList.do">주기적 비밀번호 발급현황</a></li>
      <li><a href="updateUser.do">개인정보 수정</a></li>
      <li><a href="userLogout.do">로그아웃</a></li>
    </ul>
    <p>&nbsp;</p>
  <!-- end .sidebar1 --></div>
  <div class="content">
  <br/>
  <h1>대기중인 요청</h1>
    	<center>
			<table id="tablesorter-demo1" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="780px">
     	<thead>
     	<tr>
            <th width="100">서버명</th>
            <th width="350">요청내용</th>
            <th width="100">접속아이디</th>
            <th width="100">요청시간</th>
            <th width="100">승인시간</th>
            <th width="100">승인자이름</th>
            <th width="100">승인자 직책</th>
            
            <!-- 
            <th>비밀번호 타입</th>
            <th>시작일</th>
            <th>종료일</th>
             -->
        </tr>
        </thead>
         <c:forEach var="i" items="${uncheckedHistories}">
         	<tr>
              <td style="word-break:break-all">${i.serverName}</td>
              <td style="word-break:break-all">${i.requestDesc}</td>
              <td>${i.connectId}</td>
              <td>${i.requestDate}</td>
              <td>${i.approvalDate}</td>
              <td>${i.userName}</td>
              <td>${i.userLevel}</td>
              <!-- 
              <td>${i.pwdType}</td>
              <td>${i.startDate}</td>
              <td>${i.endDate}</td>
               -->
           </tr>   
         </c:forEach>
     </table>
     <div id="paging" style="height:100px;"></div>
     <input id="currentPage" type="hidden" value="${page}" />
     <input id="serverListCount" type="hidden" value="${serverListCnt}" />
       </center>
  
  <h1>승인된 요청</h1>
    	<center>
			<table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="780px">
     	<thead>
     	<tr>
            <th width="100">서버명</th>
            <th width="350">요청내용</th>
            <th width="100">접속아이디</th>
            <th width="100">요청시간</th>
            <th width="100">승인시간</th>
            <th width="100">승인자이름</th>
            <th width="100">승인자 직책</th>
            
            <!-- 
            <th>비밀번호 타입</th>
            <th>시작일</th>
            <th>종료일</th>
             -->
        </tr>
        </thead>
         <c:forEach var="i" items="${approvedHistories}">
         	<tr>
              <td style="word-break:break-all">${i.serverName}</td>
              <td style="word-break:break-all">${i.requestDesc}</td>
              <td>${i.connectId}</td>
              <td>${i.requestDate}</td>
              <td>${i.approvalDate}</td>
              <td>${i.userName}</td>
              <td>${i.userLevel}</td>
              <!-- 
              <td>${i.pwdType}</td>
              <td>${i.startDate}</td>
              <td>${i.endDate}</td>
               -->
           </tr>   
         </c:forEach>
     </table>
     <div id="paging" style="height:100px;"></div>
     <input id="currentPage" type="hidden" value="${page}" />
     <input id="serverListCount" type="hidden" value="${serverListCnt}" />
       </center>
  
         <br/>
		 	<br/>
        
    <!-- end .content --></div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
</body>
</html>
