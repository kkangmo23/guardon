<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript">
function idduplicate(url){
	var sId = document.getElementById("serverName").value;
	
	if(sId.length < 3){
		alert("3글자이상 입력하셔야 합니다.");
		duplicate.close();
	} else if(sId.length > 40){
		alert("40글자를 넘지 않아야 합니다.");
		duplicate.close();
	}
	
	httpRequest = new XMLHttpRequest();
	httpRequest.onreadystatechange=CheckId;
	httpRequest.open("POST", url, true);
	httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	httpRequest.send("serverName="+sId); 
}	


function CheckId(){
		if(httpRequest.readyState ==4){
			if(httpRequest.status==200){
				var span = document.getElementById("serverNameCheck");
				var xml = httpRequest.responseXML;
				var result = xml.getElementsByTagName("result")[0].firstChild.nodeValue;
				span.innerText=result;
			}
		}
	}
</script>

</head>

<body>

<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong> 서버추가</div>
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
 	<form action="insertServer.do" method="post">
 	<table>
<tr style="height: 80px">
<td>
서 버 명	<input type="text" name="serverName" id="serverName" />  <input type="button" value="중복체크" class="button" style="width:100px; height:22px;" onClick="idduplicate('./checkServerName.do')"/>
</td>
<td>
<span id="serverNameCheck"></span>
</td>
</tr>
<tr style="height: 80px">
<td>
IP 주소 	<input type="text" name="ipAddress" id="ipAddress" />
</td>
</tr>
<tr style="height: 80px">
<td>
포트 번호 	<input type="text" name="port" id="port" />
</td>
</tr>
<tr style="height: 80px">
<td>
서버 설명 	<input type="text" name="serverDesc" id="serverDesc" />
</td>
</tr>
<tr style="height: 80px">
<td>
서버 아이디 	<input type="text" name="serverId" id="serverId" />
</td>
</tr>
<tr style="height: 80px">
<td>
서버 비밀번호 <input type="password" name="serverPwd" id="serverPwd" />
</td>
</tr>
<tr style="height: 80px">
<td>
서버 통신타입 : 
<input type="radio" name="connectType" value="telnet" /> Telnet
<input type="radio" name="connectType" value="ssh" /> SSH
<input type="radio" name="connectType" value="ftp" /> FTP
</td>
</tr>
<tr style="height: 80px">
<td>
서버 운영체제 : 
<input type="radio" name="serverOS" value="windows" /> Windows
<input type="radio" name="serverOS" value="linux" /> Linux
<td>
</tr>
<tr style="height: 80px">
<td>
중복ID 사용 여부 : 
<input type="radio" name="connectIdDupl" value="true" /> 사용함
<input type="radio" name="connectIdDupl" value="false" /> 사용하지 않음
</td>
</tr>
<tr>
<td>
<center>
<input type="submit" value="서버등록" />
</center>
</td>
</tr>
</table>
</form>
</center>
</div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
</body>
</html>
