<%@ page contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
    //�߿��� ���� : "attachment; filename=excel.xls" �� ������ excel.xls ������ �����ǰ� �ٿ�ε�ȴ�.
    //�ǽ����� ���� �Ʒ����� �׳� �����
    //��� HTML�� Excel ������������ ��ȯ�� (������ �ʳ���?)
    
    response.setHeader("Content-Disposition", "attachment; filename=excel.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
%>
<html>
<head>
<title>HTML�ڵ尡 �������Ϻ�ȯ</title>
</head>
<body>
    <table border=1> <!-- border=1�� �ʼ� excel ���� �׵θ��� ������� -->        
        <tr bgcolor=#CACACA>
            <td>����� ����</td>
            <td>����� ID</td>
            <td>����� �̸�</td>
            <td>��ü / ���</td>
            <td>�μ�</td>
            <td>��å</td>
            <td>�α��� �ð�</td>
        </tr>
        <c:forEach var="i" items="${logList}">
         	<tr>
              <td>
              <c:if test='${i.userType == "admin" }'>������</c:if>
              <c:if test='${i.userType == "super" }'>�ְ������</c:if>
              <c:if test='${i.userType == "user" }'>���λ����</c:if>
              <c:if test='${i.userType == "outUser" }'>�ܺλ����</c:if>
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