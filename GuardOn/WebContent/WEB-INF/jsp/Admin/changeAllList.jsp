<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" /></head>
<script src="<%=cp%>/js/jquery-ui-1.8.18/jquery-1.7.1.js"></script>

<link rel="stylesheet" href="<%=cp%>/js/__jquery.tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />

<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery-latest.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>

<script type="text/javascript">
function sub() {
	var form = document.forms['change'];
	var serverName = document.getElementsByName("temp");
	
	var check = false;
		
	for (var i = 0; i < serverName.length; i++) {
		if(serverName[i].checked!=""){
			check=true;
			break;
		}else{
			check=false;
		}
	}
	
	if (check==false) {
		alert("하나 이상의 서버를 체크해 주십시오");
	}
	if (check==true) {
	form.action = 'changeAllPwd.do';
	form.submit();
	}
	}
</script>
<script type="text/javascript">
$(function() {		
		$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
		$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
	});	
</script>
<script type="text/javascript">
    function checkAll(value) {
        var checkboxList = document.getElementsByName("temp");
        for (var i = 0; i < checkboxList.length; i++) {
            checkboxList[i].checked = value;
        }
    }
</script>

</head>
<body>

<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong>관리자</div>

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
 <form action="changeAllPwd.do" method="post" name="change">
    
    <table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
				<th><input type="checkbox" id="checkboxAll" onchange="javascript:checkAll(this.checked);" /></th>
				<th>서 버 명</th>
				<th>서버 IP 주소</th>
				<th>접속 아이디</th>
				<th>접속 아이디 설명</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" items="${changeAllList}">
         	<tr>
              <td width="10px"><input type="checkbox" name="temp" value="${i.serverName},${i.connectId}" /></td>
              <td>${i.serverName}</td>
              <td>${i.ipAddress}</td>
              <td>${i.connectId}</td>
              	<td>${i.connectIdDesc}</td>

           </tr>   
         </c:forEach>
		</tbody>
	</table>
	 <input type="button" value="발급"  onclick="sub()"/>
    </form>
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
