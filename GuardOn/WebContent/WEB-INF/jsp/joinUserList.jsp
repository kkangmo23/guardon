<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" /></head>
<link rel="stylesheet" href="<%=cp%>/js/__jquery.tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />
<script src="http://code.jquery.com/jquery-1.7.1.js"></script>
<link rel="stylesheet" href="<%=cp%>/style/plugin/asyncPaging.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="<%=cp%>/js/plugin/asyncPaging.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery-latest.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>

<script type="text/javascript">
	function submit() {	
	var form = document.forms['userList'];
	var userId = document.getElementsByName("temp");
	var check = false;
	
	for (var i = 0; i < userId.length; i++) {
		if(userId[i].checked!=""){
			check=true;
			break;
		}
	}
	
	if (check==false) {
		alert("하나 이상의 항목을 체크해 주십시오");
	}
	if(check==true){		
		form.action = 'approvalJoinReq.do';
		form.submit();
	}
	}
	
	
	function submit1() {
	var form = document.forms['userList'];
	var userId = document.getElementsByName("temp");
	var check = false;
	
	for (var i = 0; i < userId.length; i++) {
		if(userId[i].checked!=""){
			check=true;
			break;
		}
	}
	
	if (check==false) {
		alert("하나 이상의 항목을 체크해 주십시오");
	}
	if(check==true){
		form.action = 'rejectJoinReq.do';
		form.submit();
	}	
	} 
</script>
 
<script type="text/javascript">

function checkAll(value) {
    var checkboxList = document.getElementsByName("temp");
    for (var i = 0; i < checkboxList.length; i++) {
        checkboxList[i].checked = value;
    }
}

</script>

<script type="text/javascript">
		$(function() {
			$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
			$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
		});	
		</script>

<!--
<script language="javascript">
	$(document).ready(function(){
		check();
			
		function check(){
			setInterval(function(){
				$.ajax({
					type: "GET",
					url: "./checkApproval.do",
					data: {
						
					},
					contentType : "charset=UTF-8",
					success: function(response, textStatus, jqXHR){
						var uncheckString = "승인해야함";
						//var message ="승인 요청이 도착 했습니다.";
						
						if(uncheckString==response){
							//console.log('특정함수');
							//console.log(musicOn());
							playSound ( 'beep');
							//alert(message,self.setInterval(check(), 20*1000));
							//confirm(message);
						} else {
							console.log('승인 완료됨');
						}
					}, 
					failure: function(response, textStatus, jqXHR){
						alert("failure");
					}
				});	
			}, 10000);	
		}
	});
</script>

<script type="text/javascript">

	function playSound ( soundname )
	{
	  var thissound = document.getElementById( soundname );
	  thissound.play();
	  alert( "비밀번호 발급 요청이 도착했습니다." );
	}
</script>
 -->
<body>
<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong>관리자</div>
    <div class="header"></div>
  <div class="sidebar1">
    <ul class="nav">
      <li><a href="index.do">Home</a></li>
    <li><a href="joinUserList.do">회원가입 요청 리스트</a></li>
      <li><a href="approvalUserList.do">비밀번호 발급 요청 리스트</a></li>
      <li><a href="changeAllList.do">사용자 일괄-선택 비밀번호 발급</a></li>
      <li><a href="updateUser.do">개인정보 수정</a></li>
      <li><a href="option.do">관리자 설정</a></li>
      <li><a href="workflow.do">워크플로우 생성</a></li>
      <li><a href="userLogout.do">로그아웃</a></li>
    </ul>
    <p>&nbsp;</p>


  <!-- end .sidebar1 --></div>
  <div class="content">
    		<center>
		  <h1>직원명단</h1>
		<form  name="userList" method="post">
		 <table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkboxAll" onchange="javascript:checkAll(this.checked);" class="test"></th>
						<th>이 름</th>
						<th>부서명</th>
						<th>직급</th>
						<th>사용자 유형</th>
						<th>사용자 아이디</th>
						<th>전화번호</th>
						<th>E-mail</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" items="${joinUserList}">
			         	<tr>
			  <td><input type="checkbox" name="temp" value="${i.userId}" /></td>
			            	<td>${i.userName}</td>
			            	<td>${i.userDepartment}</td>
			              <td>${i.userLevel}</td>
			              <td><c:if test='${i.userType == "user" }'>내부사용자</c:if>
			              <c:if test='${i.userType == "admin" }'>관리자</c:if></td>
			              <td>${i.userId}</td>
			              <td>${i.phoneNumber}</td>
			              <td>${i.userEmail}</td>
			          </tr>   
		         	</c:forEach>
				</tbody>
			</table>
			<div id="paging" style="height:100px;"></div>
     <input id="currentPage" type="hidden" value="${page}" />
     <input id="serverListCount" type="hidden" value="${serverListCnt}" />
</form>

	<input type="button" value="승  인" onclick="submit()">
			<input type="button" value="반  려" onclick="submit1()">

  	</center>
 <!-- <audio src="<%=cp%>/media/output.wav" id="beep" autostart="false"></audio> -->
    <!-- end .content --></div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
  
</body>
</html>
