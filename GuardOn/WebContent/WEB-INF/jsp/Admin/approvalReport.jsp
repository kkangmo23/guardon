<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>

<title>Insert title here</title>
</head>
<body>
<form action = "saveApprovalReport.do" method="post">
	<table border=1> <!-- border=1은 필수 excel 셀의 테두리가 생기게함 -->        
        <tr bgcolor=#CACACA>
            <td>사용자 유형</td>
            <td>사용자 ID</td>
            <td>사용자 이름</td>
            <td>업체 / 사번</td>
            <td>부서</td>
            <td>직책</td>
            <td>패스워드 유형</td>
            <td>요청 서버</td>
            <td>사용 ID</td>
            <td>요청 시간</td>
            <td>승인/반려 시간</td>
            <td>승인 여부</td>
            <td>관리자 ID</td>
            <td>요청 내용</td>
        </tr>
        <c:forEach var="i" items="${approvalList}">
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
              <td>
              <c:if test='${i.pwdType == "OTP" }'>일회용 비밀번호</c:if>
              <c:if test='${i.pwdType == "period" }'>주기적 비밀번호</c:if>
              </td>
              <td>${i.serverName}</td>
              <td>${i.connectId}</td>
              <td>${i.requestDate}</td>
              <td>${i.approvalDate}</td>
              <td>
              <c:if test='${i.approved == "approved" || i.approved == "expired"}'>승인</c:if>
              <c:if test='${i.approved == "rejected" }'>반려</c:if>             
              <c:if test='${i.approved == "unchecked" }'>진행중</c:if>
              </td>
              <td>${i.approvalId}</td>
              <td>${i.requestDesc}</td>
           </tr>   
         </c:forEach>
    </table>
    <br/><br/>
    <center>
    <input type="submit" value="저장">
    </center>
	</form>
</body>
</html>