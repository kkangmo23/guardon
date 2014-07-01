<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String cp=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	
	<link rel="stylesheet" type="text/css" href="<%=cp %>/style/main.css" media="screen" />
	<link rel="stylesheet" href="<%=cp %>/style/images/fancybox/jquery.fancybox-1.3.2.css" type="text/css" media="screen" />

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
			<h1 id="logo"><a href="UXTest.do">Guard<span>On</span></a></h1>
			<ul id="menu">
				<li><a href="#" onmouseover="mopen('menu2')" onmouseout="mclosetime()">비밀번호 관리</a>
					<div id="menu2" onmouseover="mcancelclosetime();" onmouseout="mclosetime();">
						<a href="#">요청 승인/반려</a><br/>
						<a href="#">일괄/선택 발급</a>
					</div>
				</li>
				<li><a href="#" onmouseover="mopen('menu3')" onmouseout="mclosetime()">회원 관리</a>
					<div id="menu3" onmouseover="mcancelclosetime();" onmouseout="mclosetime();">
						<a href="#">회원 정보 조회</a><br/>
						<a href="#">회원 가입 요청 승인/반려</a>						
					</div>
				</li>
				<li><a href="#" onmouseover="mopen('menu4')" onmouseout="mclosetime()">보고서</a>
					<div id="menu4" onmouseover="mcancelclosetime();" onmouseout="mclosetime();">
						<a href="#">승인/반려 보고서</a><br/>
						<a href="#">로그 보고서</a>						
					</div>
				</li>
				<li><a href="#" onmouseover="mopen('menu5')" onmouseout="mclosetime()">워크플로우</a>
					<div id="menu5" onmouseover="mcancelclosetime();" onmouseout="mclosetime();">
						<a href="#">워크플로우 생성</a><br/>
						<a href="#">워크플로우 수정</a>						
					</div>
				</li>
				<li><a href="#">설정</a></li>								
			</ul>						
		</div>
		<!-- /top -->
		
		<div id="logInfo">
		<span style="float: right;padding-top: 10px;">
		<font style="font-weight: bold;">${sessionScope.userId }</font>(관리자)&nbsp;
		<a href="updateUser.do"><input type="button" value="개인정보수정"></a>
		<a href="userLogout.do"><input type="button" value="로그아웃"></a>
		</span>		
		</div>
		
		<br/>
		<div>
		Contents!!!!!!!!!!
		<br/><br/><br/><br/>
		</div>
		
		<!-- footer -->
		<div id="footer">
			<p>&copy; 2014 GuardOn &middot; All Rights Reserved &middot; Onsol IT</p>
		</div>
		<!-- /footer -->
	</div>
</body>
</html>