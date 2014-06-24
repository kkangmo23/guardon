<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />

</head>

<body>

<div class="container">
  <div class="header"><strong>
    <!-- end .header -->
  Guard-ON</strong>관리자</div>
    <div class="header">
   <ul>
   설  정
  </ul></div>
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
  <!-- end .sidebar1 --></div>
  <div class="content">
  <center>
  <form action="updateOption.do" method="post">
  <table> 
  <tr>
  <td width="676">
	<h2>비밀번호 설정</h2>
  </td>
    </tr>
    <tr>
    <td height="47">
    &nbsp;&nbsp;&nbsp;비밀번호 자릿수 : <select name="pwdLength" id="pwdLength" >
    <option
    <c:if test='${pwdLength == "10" }'>selected="selected"</c:if>
    value="10" > 10 </option>
    <option 
    <c:if test='${pwdLength == "11" }'>selected="selected"</c:if>
    value="11" > 11 </option>
    <option
    <c:if test='${pwdLength == "12" }'>selected="selected"</c:if>
    value="12" > 12 </option>
    <option
    <c:if test='${pwdLength == "13" }'>selected="selected"</c:if>
    value="13" > 13 </option>
    <option 
    <c:if test='${pwdLength == "14" }'>selected="selected"</c:if>
    value="14" > 14 </option>
    <option 
    <c:if test='${pwdLength == "15" }'>selected="selected"</c:if>
    value="15" > 15 </option>
    <option 
    <c:if test='${pwdLength == "16" }'>selected="selected"</c:if>
    value="16" > 16 </option>
    <option 
    <c:if test='${pwdLength == "17" }'>selected="selected"</c:if>
    value="17" > 17 </option>
    <option 
    <c:if test='${pwdLength == "18" }'>selected="selected"</c:if>
    value="18" > 18 </option>
    <option 
    <c:if test='${pwdLength == "19" }'>selected="selected"</c:if>
    value="19" > 19 </option>
    <option 
    <c:if test='${pwdLength == "20" }'>selected="selected"</c:if>
    value="20" > 20 </option>
    </select>
    (10~20 자리까지 설정)</td>
    </tr>
    <tr>
    <td height="48">
    &nbsp;&nbsp;&nbsp;OTP 적용시간 : 
    <select name="otpTimeLimit" id="otpTimeLimit">
    <option 
    <c:if test='${otpTimeLimit == "30" }'>selected="selected"</c:if>
    value="30" > 30 </option>
    <option 
    <c:if test='${otpTimeLimit == "40" }'>selected="selected"</c:if>
    value="40" > 40 </option>
    <option 
    <c:if test='${otpTimeLimit == "50" }'>selected="selected"</c:if>
    value="50" > 50 </option>
    <option 
    <c:if test='${otpTimeLimit == "60" }'>selected="selected"</c:if>
    value="60" > 60 </option>
    <option 
    <c:if test='${otpTimeLimit == "70" }'>selected="selected"</c:if>
    value="70" > 70 </option>
    <option 
    <c:if test='${otpTimeLimit == "80" }'>selected="selected"</c:if>
    value="80" > 80 </option>
    <option 
    <c:if test='${otpTimeLimit == "90" }'>selected="selected"</c:if>
    value="90" > 90 </option>
    <option 
    <c:if test='${otpTimeLimit == "100" }'>selected="selected"</c:if>
    value="100" > 100 </option>
    <option 
    <c:if test='${otpTimeLimit == "110" }'>selected="selected"</c:if>
    value="110" > 110 </option>
    <option 
    <c:if test='${otpTimeLimit == "120" }'>selected="selected"</c:if>
    value="120" > 120 </option>
    <option 
    <c:if test='${otpTimeLimit == "130" }'>selected="selected"</c:if>
    value="130" > 130 </option>
    <option 
    <c:if test='${otpTimeLimit == "140" }'>selected="selected"</c:if>
    value="140" > 140 </option>
    <option 
    <c:if test='${otpTimeLimit == "150" }'>selected="selected"</c:if>
    value="150" > 150 </option>
    <option 
    <c:if test='${otpTimeLimit == "160" }'>selected="selected"</c:if>
    value="160" > 160 </option>
    <option 
    <c:if test='${otpTimeLimit == "170" }'>selected="selected"</c:if>
    value="170" > 170 </option>
    <option 
    <c:if test='${otpTimeLimit == "180" }'>selected="selected"</c:if>
    value="180" > 180 </option>
    
    </select> 
     (30초~180초 까지 설정)</td>
    </tr>
    <!-- 
    <tr>
    <td height="48">
    &nbsp;&nbsp;&nbsp;로그인 입력실패 횟수 : <select name="loginFailedCount" id="loginFailedCount" >
    <option 
    <c:if test='${loginFailedCount == "2" }'>selected="selected"</c:if>
    value="2" > 2 </option>
    <option 
    <c:if test='${loginFailedCount == "3" }'>selected="selected"</c:if>
    value="3" > 3 </option>
    <option 
    <c:if test='${loginFailedCount == "4" }'>selected="selected"</c:if>
    value="4" > 4 </option>
    <option 
    <c:if test='${loginFailedCount == "5" }'>selected="selected"</c:if>
    value="5" > 5 </option>
    <option 
    <c:if test='${loginFailedCount == "6" }'>selected="selected"</c:if>
    value="6" > 6 </option>
    <option 
    <c:if test='${loginFailedCount == "7" }'>selected="selected"</c:if>
    value="7" > 7 </option>
    <option 
    <c:if test='${loginFailedCount == "8" }'>selected="selected"</c:if>
    value="8" > 8 </option>
    <option 
    <c:if test='${loginFailedCount == "9" }'>selected="selected"</c:if>
    value="9" > 9 </option>
    <option 
    <c:if test='${loginFailedCount == "10" }'>selected="selected"</c:if>
    value="10" >10 </option>
    </select>
     (2회~10회 까지 설정)</td>
    </tr>
     -->
    <tr>
    	<td height="48">
    		&nbsp;&nbsp;&nbsp;비밀번호 복잡도 : <select name="pwdComplexity" id="pwdComplexity" >
    			<option 
    			<c:if test='${pwdComplexity == "Low" }'>selected="selected"</c:if>
    			value="Low" > Low </option>
    			<option 
    			<c:if test='${pwdComplexity == "High" }'>selected="selected"</c:if>
    			value="High" > High </option>
    			</select>
    	</td>
    
    </tr>
    <tr>
    <td height="47">
    &nbsp;&nbsp;&nbsp;외부 사용자 OTP 허용 시간 : <select name="approvedTimeLimit" id="approvedTimeLimit" >
    <option
    <c:if test='${approvedTimeLimit == "30" }'>selected="selected"</c:if>
    value="30" > 30 분</option>
    <option
    <c:if test='${approvedTimeLimit == "60" }'>selected="selected"</c:if>
    value="60" > 1 시간</option>
    <option
    <c:if test='${approvedTimeLimit == "90" }'>selected="selected"</c:if>
    value="90" > 1 시간 30 분</option>
    <option 
    <c:if test='${approvedTimeLimit == "120" }'>selected="selected"</c:if>
    value="120" > 2 시간 </option>
    <option
    <c:if test='${approvedTimeLimit == "180" }'>selected="selected"</c:if>
    value="180" > 3 시간</option>
    <option
    <c:if test='${approvedTimeLimit == "240" }'>selected="selected"</c:if>
    value="240" > 4 시간 </option>
    <option
    <c:if test='${approvedTimeLimit == "360" }'>selected="selected"</c:if>
    value="360" > 6 시간 </option>
    <option 
    <c:if test='${approvedTimeLimit == "720" }'>selected="selected"</c:if>
    value="720" > 12 시간 </option>
    <option 
    <c:if test='${approvedTimeLimit == "1440" }'>selected="selected"</c:if>
    value="1440" > 24 시간 </option>
    <option 
    <c:if test='${approvedTimeLimit == "2160" }'>selected="selected"</c:if>
    value="2160" > 36 시간 </option>
    <option 
    <c:if test='${approvedTimeLimit == "2880" }'>selected="selected"</c:if>
    value="2880" > 48 시간 </option>     
    
    </select>
    </td>
    </tr>
    
    </table>
<br>
<br>
    <input type="submit" value="확인">
<br>
<br>
<br>
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
