<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/style/plugin/asyncPaging.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/js/__jquery.tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />
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

</script>

</head>
<body>

<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong> 서버 리스트</div>
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
 <center>
  	<table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
     	<thead>
     	<tr>        	
            <th>서버명</th>
            <th>IP주소</th>
            <th>서버설명</th>
            <th>Health Check</th>
        </tr>
        </thead>
         <c:forEach var="i" items="${serverList}">
         	<tr>              
              <td>${i.serverName}</td>
              <td class="server-ip">${i.ipAddress}</td>
              <td>${i.serverDesc}</td>
              <td class="tcp-check"></td>
           </tr>   
         </c:forEach>
     </table>
     <br/>
     <br/>
     <center>
     <div id="paging" style="height:100px; margin-left: 200px" class="content"></div>
    
     <input id="currentPage" type="hidden" value="${page}" />
     <input id="serverListCount" type="hidden" value="${serverListCnt}" />
      </center> 	
	<br/>
	<br/>	
</center>
</div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
  <script>
	$(document).ready(function(){
		
		//paging 관련
		asyncPaging.title.previous = "<";
		asyncPaging.title.next = ">";
		asyncPaging.onclick = "pageMove()";

		asyncPaging.init("paging", Number($("#serverListCount").val()), 15, Number($("#currentPage").val()));
		var serverIps = $(".server-ip");
		for(var i=0; i<serverIps.length; i++){
			tcpCheck({ ip: serverIps[i].innerHTML.split(":")[0] }, function(resp, index){
				var result = JSON.parse(resp).result;
				$(".tcp-check")[index].innerHTML = result;
			}, i);
		}
		
		
	});
	
	var tcpCheckCallCount = 10;
	function tcpCheck(data, callback, index){
		setTimeout(function(){
			$.ajax({
				type: "GET",
				cache: false,
				url: "./tcpCheck.do",
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				data: data,
				success: function(resp, textStatus, jqXHR){
					callback(resp, index);
				}, 
				failure: function(resp, textStatus, jqXHR){
					$(".tcp-check")[index].innerHTML = "FAILURE(ERROR)";
					/*
					if(tcpCheckCallCount>0){
						tcpCheck(data, callback, index);
						tcpCheckCallCount--;
					} else {
						$(".tcp-check")[index].innerHTML = "FAILURE(ERROR)";
					}
					*/
				}
			});	
		}, index*500);
	}
	
	function pageMove(){
		location.href = "./dbList.do?page="+asyncPaging.options.currentPage;
	}
</script>
</body>
</html>
