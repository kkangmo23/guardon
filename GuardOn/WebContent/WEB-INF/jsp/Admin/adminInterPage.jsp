<%@ page contentType="text/html; charset=UTF-8"%>
<%
 String cp = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/style/portal/magic_common.css" />
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<script src="<%=cp%>/js/jquery-ui-1.8.18/jquery-1.7.1.js"></script>

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
  Guard-ON</strong></div>

  <div>
  <center>
<form action="index.do" method="post">
<br/>
<br/>
<br/>
<br/>
<center>
${message }
<br/>
<br/>
<br/>
<br/>
<input type="submit" value="홈으로" style="height:50px; width:100px;"/>
</center>
<br/>
<br/>
</form>
<br/>
<br/>
<audio src="<%=cp%>/media/output.wav" id="beep" autostart="false"></audio>
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