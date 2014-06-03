	<%@ page contentType="text/html; charset=UTF-8"%>
	<%
		String cp = request.getContextPath();
	%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>무제 문서</title>
	<link rel="stylesheet" type="text/css"
		href="<%=cp%>/style/portal/magic_common.css" />
	<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css"
		media="print, projection, screen" />
	<script type="text/javascript">
		function sub() {
			var form = document.forms['findId'];
			form.action = 'index.do';
			form.submit();
		}
	
		function sub1() {
			var form = document.forms['findId'];
			var userName = document.findId.userName.value;
			var companyNumber = document.findId.companyNumber.value;
	
			if (userName.length <= 0 || companyNumber.length <= 0) {
				alert("입력사항을 모두 입력 하십시오");
			} else {
				form.action = 'getId.do';
				form.method = 'POST';
				form.submit();
			}
			/*
			var check = false;
			
			for (var i = 0; i < userId.length; i++) {
				if(userId[i].checked!=""){
					check=true;
					break;
				}
			}
			
			if (check==false) {
				alert("하나 이상의 서버를 체크해 주십시오");
			}
			if(check==true){
				form.action = 'approvalJoinReq.do';
				form.submit();
			}*/
		}
	</script>
	</head>
	
	<body>
	
		<div class="container">
			<div class="header">
				<strong> <!-- end .header --> Guard-ON
				</strong> 아이디 찾기
			</div>
	
			<div>
				<br /> <br /> <br />
				<center>
					<h1>입력 사항</h1>
					<form name="findId">
	
						<center>
							<pre>
	이	름	<input type="text" name="userName" id="userName" value="" />
	<br />
	사 번(업체명)	<input type="text" name="companyNumber" id="companyNumber"
									value="" />
	</pre>
	
							<br /> <br />
						</center>
					</form>
					<input type="button" onclick="sub()" value="뒤로"
						style="height: 50px; width: 100px;" /> <input type="button"
						onclick="sub1()" value="아이디 찾기" style="height: 50px; width: 100px;" />
					<a href="userJoin.do"> <input type="button" value="회원가입"
						style="height: 50px; width: 100px;" />
					</a> <br /> <br />
				</center>
				<!-- end .content -->
			</div>
			<div class="footer">
				<center>GuardOn V1.01</center>
				<br>
				<p>Trust. Technology. Service</p>
				<!-- end .footer -->
			</div>
			<!-- end .container -->
		</div>
	</body>
	</html>