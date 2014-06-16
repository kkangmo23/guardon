<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String cp=request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>무제 문서</title>
<link rel="stylesheet" href="<%=cp%>/style/basic.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/js/__jquery.tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />

<script type="text/javascript" src="<%=cp%>/js/plugin/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/plugin/asyncPaging.js"></script>

<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery-latest.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>

<link rel="stylesheet" href="<%=cp%>/js/jquery/1.10.4/jquery-ui.css" />

<script src="<%=cp%>/js/jquery/1.10.4/jquery.js"></script>
<script src="<%=cp%>/js/jquery/1.10.4/jquery-ui.js"></script>
<script type="text/javascript">

function popupOpen(){

var popUrl = "approvalReport.do";	//팝업창에 출력될 페이지 URL

var popOption = "width=1080, height=360, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

window.open("","pop",popOption);

document.form1.action = popUrl;
document.form1.target = "pop";
document.form1.method = "post";
document.form1.submit();

}


</script>
<script language="javascript">
$(function() {
  var dates = $( "#from, #to " ).datepicker({
  prevText: '이전 달',
  nextText: '다음 달',
  monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
  monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
  dayNames: ['일','월','화','수','목','금','토'],
  dayNamesShort: ['일','월','화','수','목','금','토'],
  dayNamesMin: ['일','월','화','수','목','금','토'],
  dateFormat: 'yy-mm-dd',
  showMonthAfterYear: true,
  yearSuffix: '년',
  //minDate: '0',
  onSelect: function( selectedDate ) {
    var option = this.id == "from" ? "minDate" : "maxDate",
      instance = $( this ).data( "datepicker" ),
      date = $.datepicker.parseDate(
        instance.settings.dateFormat ||
        $.datepicker._defaults.dateFormat,
        selectedDate, instance.settings );
    dates.not( this ).datepicker( "option", option, date );
  }
  });
});
</script>
<title>Insert title here</title>
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
    <h1>&nbsp;</h1>
    <form action="approvalReport.do" method="post" name="form1">
			<center>
				<table>
					<tr>
						<th>조회 기간:</th>
						<td><input type="text" id="from" name="startDate" readonly="readonly"> ~ <input type="text" id="to" name="endDate" readonly="readonly"></td>
					</tr>					
				</table>
				<br/><br/>
				<input type="radio" name="token" value="all">All&nbsp;
				<input type="radio" name="token" value="userId">유저 ID&nbsp;
				<input type="radio" name="token" value="connectId">사용 ID&nbsp;
				<input type="radio" name="token" value="serverName">서버 이름&nbsp;&nbsp;&nbsp;
				<input type="text" name="keyValue">
				<br/><br/>				
				<input type="button" value="생성" onclick="popupOpen();">
				<br/><br/>				
			</center>
	</form>
			<!-- end .content --></div>
  <div class="footer">
    <center>GuardOn V1.01</center>
  <br>
    <p>Trust. Technology. Service</p>
    <!-- end .footer --></div>
  <!-- end .container --></div>
  
</body>
</html>