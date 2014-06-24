<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
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

<script src="<%=cp%>/js/jquery-ui-1.8.18/jquery-1.7.1.js"></script>
<script src="<%=cp%>/js/jquery-ui-1.8.18/ui/jquery-ui.js"></script>
<link rel="stylesheet" href="<%=cp%>/js/jquery-ui-1.8.18/themes/base/jquery-ui.css" />

<script type="text/javascript" src="<%=cp%>/js/plugin/asyncPaging.js"></script>

<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>

<script type="text/javascript">

function emptyCheck(){
	var form = document.forms['form1'];
	var userType = document.form1.userType;
	var token = document.form1.token.value;
	var keyValue = document.form1.keyValue.value;
	var checked = false;
	
	for (var i = 0; i < userType.length; i++) {
		if(userType[i].checked==true)
			checked = true;		
	}
	
	if(checked == false){
		alert("최소한 하나 이상의 사용자 유형을 체크해 주십시오");
	} else if (keyValue == "" && token != "all") {
		alert("검색어를 입력해 주십시오");
	} else {
		form.action = 'getUserList.do';
		form.submit();
	}
}
</script>
 <script type="text/javascript">

	$(document).ready(function(){

		asyncPaging.title.previous = "<";
		asyncPaging.title.next = ">";
		asyncPaging.onclick = "pageMove()";
		asyncPaging.init("paging", Number($("#userListCount").val()), 15, Number($("#currentPage").val()));		
	});
	
	function pageMove(){
		var token = document.form1.hiddenToken.value;
		var keyValue = document.form1.hiddenKeyValue.value;
		var userListCount = document.form1.userListCount.value;		
		
		location.href = "./getUserList.do?userListCount="+userListCount+"&access=paging&page="+asyncPaging.options.currentPage+
				"&userType=admin&userType=user&userType=outUser&token="+token+"&keyValue="+keyValue;
	}
</script>

<script type="text/javascript">
$(function() {		
	$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
	$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
});	

</script>

<title>Insert title here</title>
</head>
<body>
<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong>관리자</div>
		<div class="header"></div>
		<div class="sidebar1">
			<ul class="nav">
				<li><a href="index.do">Home</a></li>				
				<li><a href="approvalUserList.do">비밀번호 발급 요청 리스트</a></li>
				<li><a href="changeAllList.do">사용자 일괄-선택 비밀번호 발급</a></li>
				<li><a href="updateUser.do">개인정보 수정</a></li>
				<li><a href="option.do">관리자 설정</a></li>
				<li><a href="workflow.do">워크플로우 생성</a></li>
				<li><a href="approvalReportPretreatment.do">승인/반려 보고서</a></li>
				<li><a href="logReportPretreatment.do">로그 보고서</a></li>
				<li><a href="userList.do">회원정보 조회</a></li>
				<li><a href="joinUserList.do">회원가입 요청 리스트</a></li>
				<li><a href="userLogout.do">로그아웃</a></li>
			</ul>
			<p>&nbsp;</p>
			<!-- end .sidebar1 -->
		</div>
		<div class="content">
    <h1>&nbsp;</h1>
    <form action="getUserList.do" method="post" name="form1">
			<center>
				<table>
						<tr>							
							<td>
							<input type="checkbox" name="userType" value="admin" checked="checked">관리자&nbsp;
							<input type="checkbox" name="userType" value="user" checked="checked">내부 사용자&nbsp;
							<input type="checkbox" name="userType" value="outUser" checked="checked">외부 사용자&nbsp;
							</td>
						</tr>
						<tr>
						<td>
						<br/>
						</td>
						</tr>

						<tr>							
							<td>
							<input type="radio" name="token" value="all" <c:if test='${token == "all" }'>checked="checked"</c:if>>전체 조회&nbsp; 
							<input type="radio" name="token" value="userId" <c:if test='${token == "userId" }'>checked="checked"</c:if>>ID&nbsp; 
							<input type="radio" name="token" value="userName" <c:if test='${token == "userName" }'>checked="checked"</c:if>>이름&nbsp; 
							<input type="radio" name="token" value="companyNumber" <c:if test='${token == "companyNumber" }'>checked="checked"</c:if>>사번/업체명&nbsp;&nbsp;&nbsp; 							
							<input type="text" name="keyValue" value="${keyValue}">&nbsp;&nbsp;&nbsp;
							<input type="button" value="조회" onclick="emptyCheck();">
							</td>
						</tr>
					</table>	
						<br/><br/>
						<table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
        <tr>
            <th>사용자 유형</th>
            <th>ID</th>
            <th>이름</th>
            <th>사번/업체명</th>
            <th>부서</th>
            <th>직책</th>
            <th>E-mail</th>
            <th>연락처</th>
            
        </tr>
        </thead>
        <c:forEach var="i" items="${userList}">
         	<tr>
              <td>
              <c:if test='${i.userType == "admin" }'>관리자</c:if>
              <c:if test='${i.userType == "user" }'>내부사용자</c:if>
              <c:if test='${i.userType == "outUser" }'>외부사용자</c:if>
              </td>
              <td>${i.userId}</td>
              <td>${i.userName}</td>
              <td>${i.companyNumber}</td>
              <td>${i.userDepartment}</td>
              <td>${i.userLevel}</td>
              <td>${i.userEmail}</td>
              <td>${i.phoneNumber}</td>
              
           </tr>   
         </c:forEach>
    </table>
    <div id="paging" style="height:100px;"></div>
    <input id="currentPage" type="hidden" value="${page}" />
    <input name="userListCount" id="userListCount" type="hidden" value="${userListCount}" />
    <input name="hiddenToken" type="hidden" value="${token}" />
    <input name="hiddenKeyValue" type="hidden" value="${keyValue}" />
    <input name="access" type="hidden" value="search" />    
		
			</center>
	</form>
	
			<!-- end .content --></div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
  
</body>
</html>