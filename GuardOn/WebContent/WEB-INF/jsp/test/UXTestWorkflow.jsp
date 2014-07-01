<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <% String cp=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	
	<link rel="stylesheet" type="text/css" href="<%=cp %>/style/main.css" media="screen" />
	
	<link rel="stylesheet" type="text/css" href="<%=cp %>/style/drag1.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=cp %>/style/drag2.css" media="screen" />
<link rel="stylesheet" href="<%=cp%>/style/plugin/asyncPaging.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="<%=cp%>/js/__jquery.tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />

<script src="<%=cp%>/js/jquery-ui-1.8.18/jquery-1.7.1.js"></script>
<script src="<%=cp%>/js/jquery-ui-1.8.18/ui/jquery-ui.js"></script>
<link rel="stylesheet" href="<%=cp%>/js/jquery-ui-1.8.18/themes/base/jquery-ui.css" />

<script type="text/javascript" src="<%=cp%>/js/plugin/asyncPaging.js"></script>

<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=cp%>/js/__jquery.tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>

<script type="text/javascript">

function test1(tr){
	var id = tr.cells[3].innerHTML;
	var name = tr.cells[0].innerHTML;
	var userLevel = tr.cells[2].innerHTML;
	var workflowSteps = document.getElementsByName("workflow-step");
	var item = document.getElementsByName("item");
	var checkIndex = 0;
	for(var i=0; i<workflowSteps.length; i++){
		if(workflowSteps[i].checked){
			checkIndex = i;
			break;
		}
	}
	
	document.getElementById("userName"+(checkIndex+1)).value = name+" "+userLevel;
	document.getElementById("userId"+(checkIndex+1)).value = id;
	document.getElementById("item"+(checkIndex+1)).value =item;
	
	

	/*
	for (var j = 1; j <= 5; j++) {
		for (var k = 1; k <= 5 ; k++) {
			
		}
	}
	if (document.getElementById("userId"+(checkIndex+(j-1)).value==document.getElementById("userId"+(checkIndex+(j)).value))) {
		alert("중복된 사용자가 있습니다.");
	} else {
		alert("1111111");
	}*/

	
}

</script>

<script type="text/javascript">
		$(function() {
			$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
			$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
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
				<li><a class="current" href="#" onmouseover="mopen('menu5')" onmouseout="mclosetime()">워크플로우</a>
					<div id="menu5" onmouseover="mcancelclosetime();" onmouseout="mclosetime();">
						<a href="#">워크플로우 생성</a><br/>
						<a href="#">워크플로우 수정</a>						
					</div>
				</li>
				<li><a href="#">설정</a></li>								
			</ul>						
		</div>
		<!-- /top -->
		
		<!-- logInfo -->
		<div id="logInfo">
		<span style="float: right;padding-top: 10px;">
		<font style="font-weight: bold;">${sessionScope.userId }</font>(관리자)&nbsp;
		<a href="updateUser.do"><input type="button" value="개인정보수정"></a>
		<a href="userLogout.do"><input type="button" value="로그아웃"></a>
		</span>		
		</div>
		<!-- logInfo -->
		
		<br/>
		<div>
		직원명단
		<table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>이 름</th>
						<th>부서명</th>
						<th>직급</th>
						<th>사용자 아이디</th>
						<th>전화번호</th>
						<th>E-mail</th>
					</tr>
				</thead>
				<tbody>
				<!-- 
					<c:forEach var="i" items="${userList}">
			         	<tr onclick="test1(this)">
			            	<td>${i.userName}</td>
			            	<td>${i.userDepartment}</td>
			              <td>${i.userLevel}</td>
			              <td>${i.userId}</td>
			              <td>${i.phoneNumber}</td>
			              <td>${i.userEmail}</td>
			          </tr>   
		         	</c:forEach>
		         -->
		         <!--  테스트용 하드코딩 -->
					<tr onclick="test1(this)">
						<td>홍길동</td>
						<td>개발</td>
						<td>부장</td>
						<td>hgd</td>
						<td>01084587654</td>
						<td>hgd@naver.com</td>
					</tr>
					<tr onclick="test1(this)">
						<td>정도전</td>
						<td>경영</td>
						<td>이사</td>
						<td>jdj</td>
						<td>01043426654</td>
						<td>jdj@naver.com</td>
					</tr>
					<tr onclick="test1(this)">
						<td>이성계</td>
						<td>경영</td>
						<td>회장</td>
						<td>lsg</td>
						<td>01087865135</td>
						<td>lsg@naver.com</td>
					</tr>
					<tr onclick="test1(this)">
						<td>정몽주</td>
						<td>보안</td>
						<td>과장</td>
						<td>jmj</td>
						<td>01087652147</td>
						<td>jmj@naver.com</td>
					</tr>
					<!--  테스트용 하드코딩 끝 -->
				</tbody>
			</table>
		승인순서
		<form action="insertWorkflow.do" method="post" id="center-wrapper" name="workflow" autocomplete="off">
	
	<div id="workflow" class="workflow">
				<input type="hidden" name="step" id="step" value="">

				<div class="column left first">

					<ul class="sortable-list">
						<li class="sortable-item" id="item1" >
							<input type="radio" name="workflow-step" checked="checked"/>
							<br/>
							<input type="text" id="userName1" name="userName"/>
							<input type="hidden" id="userId1" name="userId">
						</li>
						<li class="sortable-item" id="item2">
							<input type="radio" name="workflow-step" />
							<br/>
							<input type="text" id="userName2" name="userName"/>
							<input type="hidden" id="userId2" name="userId">
						</li>
					</ul>

				</div>

				<div class="column left">

					<ul class="sortable-list">
						<li class="sortable-item" id="item3">
							<input type="radio" name="workflow-step" />
							<br/>
							<input type="text" id="userName3" name="userName"/>
							<input type="hidden" id="userId3" name="userId">
						</li>
						<li class="sortable-item" id="item4">
							<input type="radio" name="workflow-step" />
							<br/>
							<input type="text" id="userName4" name="userName"/>
							<input type="hidden" id="userId4" name="userId">
						</li>
					</ul>

				</div>

				<div class="column left">

					<ul class="sortable-list">
						<li class="sortable-item" id="item5">
							<input type="radio" name="workflow-step" />
							<br/>
							<input type="text" id="userName5" name="userName"/>
							<input type="hidden" id="userId5" name="userId">
						</li>
					</ul>

				</div>
				
				<div class="column left">

					<ul class="sortable-list">
					</ul>

				</div>
				
				<div class="column left">

					<ul class="sortable-list">
					</ul>

				</div>

			</div>
			
					<div class="clearer">&nbsp;</div>
			
			<!-- <p><input type="button" class="input-button" id="btn-get" value="Get items" /></p> -->
			<br/>
			<br/>
			<br/>
		<div>
		새 워크플로우 이름 : <input type="text" name="workflowName"/>
		<br/>
		<br/>		
		워크플로우 설명 : <input type="text" name="workflowDesc"/>
		<br/>
		<br/>
		<br/>
		</div>
		<c:forEach var="i" items="${serverNameList}">
		<input type="hidden" value="${i}" name="serverNameList">
		</c:forEach>
		<input type="submit" value="워크플로우 등록" onclick="getItem()" class="input-button" id="btn-get">
		<br/>
		<br/>
		
	</form>
		</div>
		
		<!-- footer -->
		<div id="footer">
			<p>&copy; 2014 GuardOn &middot; All Rights Reserved &middot; Onsol IT</p>
		</div>
		<!-- /footer -->
</div>
<script language="javascript">
function getItems(exampleNr){
		var columns = [];
		var userIdEls = document.getElementsByName('userId');
		var userIds = [];
		var columnNumber;
		var resultMsg = '';
		var arrResult = [];
		
		for(var i=0; i<userIdEls.length; i++){
			userIds.push(userIdEls[i].value);
		}
		
		$(exampleNr + ' ul.sortable-list').each(function(){
			columns.push($(this).sortable('toArray'));
		});
		
	    
		for(var i=0; i<columns.length; i++){
			for(var j=0; j<columns[i].length; j++){
				columnNumber = Number(columns[i][j].replace('item', ''));
				if($('#userId'+columnNumber) && $('#userId'+columnNumber).val()!=''){
					arrResult.push({ userId: $('#userId'+columnNumber).val(), column: i });
				}
			}
		}

		var currentColumn = 0;
		for(var i=0; i<arrResult.length; i++){
			if(resultMsg!=''){
				if(currentColumn==arrResult[i].column){
					resultMsg += ',';	
				} else {
					resultMsg += '|';
					currentColumn = arrResult[i].column;
				}
			} else {
				currentColumn = arrResult[i].column;
			}
			resultMsg += arrResult[i].userId;
		}
		
		console.log(arrResult);
		insertMsg(resultMsg);
		return resultMsg;
	}

	function insertMsg(resultMsg){
		var msg = resultMsg;
		document.workflow.step.value = msg;
	}

	$('#workflow .sortable-list').sortable({	
	connectWith: '#workflow .sortable-list'
	});

	$('#btn-get').click(function(){
		getItems('#workflow');
	});


</script>
</body>
</html>