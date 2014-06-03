<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="<%=cp %>/js/jquery/1.4.2/jquery.min.js"></script>


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
			}, 5000);	
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

/*
		function musicOff() { 
		document.midi.stop();
		}
		 
		function musicOn() {
			document.player.Play();
		}
		*/
</script>
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
    <h1>&nbsp;</h1>
    
    <font size=5px>총 ${totalCount}건 중 ${successCount } 건에 대한 비밀번호가 변경되었습니다.</font>
	</center>
	<audio src="<%=cp%>/media/output.wav" id="beep" autostart="false"></audio>
    <!-- end .content --></div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
</body>
</html>