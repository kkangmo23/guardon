<%@ page contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
    //중요한 사항 : "attachment; filename=excel.xls" 로 적으면 excel.xls 파일이 생성되고 다운로드된다.
    //의심하지 말고 아래줄은 그냥 적어요
    //모든 HTML은 Excel 파일형식으로 변환됨 (편하지 않나요?)
    
    response.setHeader("Content-Disposition", "attachment; filename=excel.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
%>
<html>
<head>
<title>HTML코드가 엑셀파일변환</title>
</head>
<body>
    <table border=1> <!-- border=1은 필수 excel 셀의 테두리가 생기게함 -->        
        <tr bgcolor=#CACACA>
            <td>사용자 유형</td>
            <td>사용자 ID</td>
            <td>사용자 이름</td>
            <td>업체 / 사번</td>
            <td>부서</td>
            <td>직책</td>
            <td>로그인 시간</td>
        </tr>
        <c:forEach var="i" items="${logList}">
         	<tr>
              <td>
              <c:if test='${i.userType == "admin" }'>관리자</c:if>
              <c:if test='${i.userType == "super" }'>최고관리자</c:if>
              <c:if test='${i.userType == "user" }'>내부사용자</c:if>
              <c:if test='${i.userType == "outUser" }'>외부사용자</c:if>
              </td>
              <td>${i.userId}</td>
              <td>${i.userName}</td>
              <td>${i.companyNumber}</td>
              <td>${i.userDepartment}</td>
              <td>${i.userLevel}</td>
              <td>${i.loginTime}</td>
              
           </tr>   
         </c:forEach>
    </table>
</body>
</html>