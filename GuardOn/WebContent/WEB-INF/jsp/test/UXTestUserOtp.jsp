<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String cp=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	
<link rel="stylesheet" type="text/css" href="<%=cp %>/style/main.css" media="screen" />

<script src="<%=cp%>/js/jquery-ui-1.8.18/jquery-1.7.1.js"></script>
<script src="<%=cp%>/js/jquery-ui-1.8.18/ui/jquery-ui.js"></script>
<link rel="stylesheet" href="<%=cp%>/js/jquery-ui-1.8.18/themes/base/jquery-ui.css" />

<script language="javascript">
		function abc(){
			var el = document.getElementById('btn1');
			el.disabled = 'true';
			var form = document.forms['userOtp'];
			form.action='userOtp.do';
			form.submit();
		}

		function onLoadEvent() {
			var el = document.getElementById('btn1');
			el.disabled = '';
		}

  		$(function() {
			var percent = 0;
			var intervalSecond = parseInt($('#intervalSecond').val());
			if(isNaN(intervalSecond)){
				intervalSecond = 10;
			}
					
			
			var interval = setInterval(function(){
				$( "#progressbar" ).progressbar({
	      			value: percent
	    		});
				percent = percent + 0.1;
				if(percent >= 100){
					clearInterval(interval);
					onLoadEvent();
					//document.userOtp.submit();
				}
			}, intervalSecond); // 0.01s
  		});
</script>
<script type="text/javascript">

var timeout	= 500;
var closetimer	= 0;
var ddmenuitem	= 0;

// open hidden layer
function mopen(id)
{	
	// cancel close timer
	mcancelclosetime();

	// close old layer
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';

	// get new layer and show it
	ddmenuitem = document.getElementById(id);
	ddmenuitem.style.visibility = 'visible';

}
// close showed layer
function mclose()
{
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
}

// go close timer
function mclosetime()
{
	closetimer = window.setTimeout(mclose, timeout);
}

// cancel close timer
function mcancelclosetime()
{
	if(closetimer)
	{
		window.clearTimeout(closetimer);
		closetimer = null;
	}
}

// close layer when click-out
document.onclick = mclose; 

</script>
</head>
<body>
<div id="content">
	
		<!-- top -->
		<div id="top">
			<h1 id="logo"><a href="#">Guard<span>On</span></a></h1>
			<ul id="menu">
				<li><a class="current" href="#" onmouseover="mopen('menu2')" onmouseout="mclosetime()">일회용 비밀번호</a>
					<div id="menu2" onmouseover="mcancelclosetime();" onmouseout="mclosetime();">
						<a href="#">일회용 비밀번호 발급</a>
					</div>
				</li>
				<li><a href="#" onmouseover="mopen('menu3')" onmouseout="mclosetime()">주기적 비밀번호</a>
					<div id="menu3" onmouseover="mcancelclosetime();" onmouseout="mclosetime();">
						<a href="#">주기적 비밀번호 요청</a><br/>
						<a href="#">주기적 비밀번호 현황</a>						
					</div>
				</li>									
			</ul>						
		</div>
		<!-- /top -->
		
		<!-- logInfo -->
		<div id="logInfo">
		<span style="float: right;padding-top: 10px;">
		<font style="font-weight: bold;">${sessionScope.userId }</font>(내부사용자)&nbsp;
		<a href="updateUser.do"><input type="button" value="개인정보수정"></a>
		<a href="userLogout.do"><input type="button" value="로그아웃"></a>
		</span>		
		</div>
		<!-- /logInfo -->
		
		<br/>
		<br/>
		<br/>
		<br/>
		<br/>
		<div>
		<center>
    		<strong>
    
    <!-- 
    		<font size="+3">OTP :
    		<% String userOtp = request.getParameter("userOtp"); %>
   			${userOtp}
   			</font>
   	-->
   	<!-- 테스트용 하드코딩 -->
   			<font size="+3">OTP : fj94*j!0jdkf</font>
   	<!-- 테스트용 하드코딩 끝-->
    		</strong>
    		<br/>
    		<br/>
    		<br/>
    		<br/>
    		<form action="userOtp.do" name="userOtp" method="post">
   				<input type="submit" id="btn1" value="재발급" onclick="abc()"	disabled="disabled"/>
    		</form>
    	</center>
		</div>
		<br/>
		<br/>
		<div id="progressbar"></div>
		<br/>
		<br/>
		<br/>
		<br/>
		<!-- footer -->
		<div id="footer">
			<p>&copy; 2014 GuardOn &middot; All Rights Reserved &middot; Onsol IT</p>
		</div>
		<!-- /footer -->
	</div>
</body>
</html>