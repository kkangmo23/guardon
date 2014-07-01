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
		
		function onlyNumber(event) {
		    var key = window.event ? event.keyCode : event.which;    
		
		    if ((event.shiftKey == false) && ((key  > 47 && key  < 58) || (key  > 95 && key  < 106)
		    || key  == 35 || key  == 36 || key  == 37 || key  == 39  // 방향키 좌우,home,end  
		    || key  == 8  || key  == 46 ) // del, back space
		    ) {
		        return true;
		    }else {
		        return false;
		    }    
		};
		
		function submitForm2() {
			var form = document.forms['password_form'];
			form.action = 'index.do';
			form.submit();
		}

		function submitForm() {
			var form = document.forms['password_form'];
			var userPwd=document.password_form.userPwd.value;
			var confirmPwd=document.password_form.confirmPwd.value;
			var userType=document.password_form.userType.value;
			var userDepartment=document.password_form.userDepartment.value;
			var userLevel=document.password_form.userLevel.value;
			var companyNumber=document.password_form.companyNumber.value;
			var userEmail=document.password_form.userEmail.value;
			var userCp2=document.password_form.userCp2.value;
			
			if(userType==""||userDepartment==""||userLevel==""||companyNumber==""||userEmail==""||userCp2==""){
				alert("빈항목이 있는지 체크 하십시오!");
			}
			else if (userPwd.length < 5 && userPwd.length > 12 ) {
				alert("비밀번호는 6자 이상 12자 미만 입력해야 합니다.");
			}
			else if(!userPwd.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)){
				alert("비밀번호 조합확인");
			}
			else if(userPwd==confirmPwd&&userPwd!=""&&confirmPwd!=""&&userType!=""&&userDepartment!=""&&userLevel!=""&&
					companyNumber!=""&&userEmail!=""&&userCp2!=""){
				form.action = 'insertUser.do';
				form.submit();
			}
			else
				alert("비밀번호 또는 아이디 중복 체크를 확인하십시오");
			}

		function idduplicate(url){
			var sId = document.getElementById("userId").value;

			
			if(sId.length < 4){
				alert("4글자이상 입력하셔야 합니다.");
				duplicate.close();
			} else if(sId.length > 15){
				alert("15글자를 넘지 않아야 합니다.");
				duplicate.close();
			}
			
			httpRequest = new XMLHttpRequest();
			httpRequest.onreadystatechange=CheckId;
			httpRequest.open("POST", url, true);
			httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			httpRequest.send("userId="+sId); 
		}	
		
		
		function CheckId(){
				if(httpRequest.readyState ==4){
					if(httpRequest.status==200){
						var span = document.getElementById("userIdCheck");
						var xml = httpRequest.responseXML;
						var result = xml.getElementsByTagName("result")[0].firstChild.nodeValue;
						span.innerText=result;
					}
				}
			}


</script>
<SCRIPT LANGUAGE="JavaScript">

function verifynotify(field1, field2, result_id, match_html, nomatch_html) {
  this.field1 = field1;
  this.field2 = field2;
  this.result_id = result_id;
  this.match_html = match_html;
  this.nomatch_html = nomatch_html;

  this.check = function() {
    // Make sure we don't cause an error
    // for browsers that do not support getElementById
    if (!this.result_id) { return false; }
    if (!document.getElementById){ return false; }
    r = document.getElementById(this.result_id);
    if (!r){ return false; }
    if (this.field1.value != "" && this.field1.value == this.field2.value) {
      r.innerHTML = this.match_html;
    } else {
      r.innerHTML = this.nomatch_html;
    }
  }
}

function verifyInput() {
  verify = new verifynotify();
  verify.field1 = document.password_form.userPwd;
  verify.field2 = document.password_form.confirmPwd;
  verify.result_id = "password_result";
  verify.match_html = "<span style=\"color:blue\">패스워드가 일치합니다!<\/span>";
  verify.nomatch_html = "<span style=\"color:red\">귀하의 비밀 번호가 일치하는지 확인하십시오!<\/span>";

  // Update the result message
  verify.check();
}

// Multiple onload function created by: Simon Willison
// http://simonwillison.net/2004/May/26/addLoadEvent/
function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      if (oldonload) {
        oldonload();
      }
      func();
    }
  }
}

addLoadEvent(function() {
  verifyInput();
});

</SCRIPT>
</head>
<body>
<div id="content">
	
		<!-- top -->
		<div id="top">
			<h1 id="logo"><a href="UXTest.do">Guard<span>On</span></a></h1>
					
		</div>
		<!-- /top -->
			
		<br/>		
		<h2>회원 가입</h2>
		<br/><br/>
		
<div>
   <div>
    <div>
    <form action="insertUser.do" method="post" name="password_form" autocomplete="off">

      <table width="950" height="702" class="table" border="0">
       <tr>
       	<td width="150" class="table2"><font class="h2" color="red">&nbsp;전체 항목 필수</font></td>
       </tr>

        <tr>
          <td width="150" class="table2"><font class="h2">&nbsp;이 름</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input type="text" name="userName" class="format" style="width:200px; height:20px;"/></td>
          <td width="150" rowspan="2" class="table3"></td>
        </tr>
        <tr>
          <td class="table2" ><font class="h2">&nbsp;아이디</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input type="text" name="userId" id="userId" class="format" style="width:200px; height:20px;" onkeyup="this.value=this.value.replace(/[^a-zA-Z0-9]/g,'')"/>&nbsp; <!-- onkeyup="idduplicate('./checkUserId.do')" -->
          <input type="button" value="중복체크" class="button" style="width:100px; height:22px;" onClick="idduplicate('./checkUserId.do')"/>
				<span id="userIdCheck"></span>
          </tr>

        <tr>
          <td class="table2"><font class="h2">&nbsp;비밀번호</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input type="password" name="userPwd" class="format" style="width:200px; height:20px;"
          onkeyup="verify.check()"/><font class="h2" color="gray">&nbsp;특수문자, 숫자, 영어를 포함해 6~12자를 입력하세요.</font></td>
          <td class="table3">&nbsp;</td>           
        </tr>
        <tr>
          <td class="table2"><font class="h2">&nbsp;비밀번호 확인</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input type="password" name="confirmPwd" class="format" style="width:200px; height:20px;"
          onkeyup="verify.check()"/></td>
          <td class="table3">&nbsp;</td>
        </tr>
        
        <tr>
        <td class="table2"></td>
        <td id="password_result"></td>
        </tr>
        
        
        
        <tr>
          <td class="table2"><font class="h2">&nbsp;가입자 속성</font> <font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input type="radio" name="userType" value="admin" /> 관리자
          						<input type="radio" name="userType" value="user" checked="checked"/> 내부사용자
							 	 <input type="radio" name="userType" value="outUser" /> 외부사용자
		   </td>
          <td class="table3">&nbsp;</td>
        </tr>
        
        <tr>
          <td class="table2" ><font class="h2">&nbsp;부서</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input type="text" name="userDepartment" id="userDepartment" class="format"/></td>
        </tr>
        <tr>
          <td class="table2" ><font class="h2">&nbsp;직급</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input type="text" name="userLevel" id="userLevel" class="format"/></td>
        </tr>
        <tr>
         <td class="table2" ><font class="h2">&nbsp;사번</font><font class="h2" color="red">&nbsp;(*)</font></td>
         <td class="table3"><input type="text" name="companyNumber" id="companyNumber" class="format" style="width:200px; height:20px;"/>  
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
         <font color="blue">(KT직원은 사번, 외부직원은 업체명을 입력해 주세요.)</font>
        </tr>
        <tr>
          <td class="table2"><font class="h2">&nbsp;E-MAIL</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><input name="userEmail" type="text" style="width:200px; height:20px;" value="" class="format"/>
     </td>
          <td class="table3">&nbsp;</td>
        </tr>
        <tr>
        <td class="table2"><font class="h2">&nbsp;전화번호</font><font class="h2" color="red">&nbsp;(*)</font></td>
          <td class="table3"><select name="phoneNumber" class="select" style="width:70px; height:25px;">
        <option value="010" >&nbsp; &nbsp;010&nbsp; &nbsp;</option>
        <option value="011" >&nbsp; &nbsp;011&nbsp; &nbsp;</option>
        <option value="016" >&nbsp; &nbsp;016&nbsp; &nbsp;</option>
        <option value="017" >&nbsp; &nbsp;017&nbsp; &nbsp;</option>
        <option value="018" >&nbsp; &nbsp;018&nbsp; &nbsp;</option>
        <option value="019" >&nbsp; &nbsp;019&nbsp; &nbsp;</option>
        </select>&nbsp; &nbsp;-&nbsp; &nbsp;
       <input name="userCp2" type="text" maxlength="8" style="width:100px; height:20px;" value="" class="format" style="ime-mode:disabled;" onkeydown="return onlyNumber(event)"/>
       &nbsp; &nbsp; <font class="h2" color="gray">&nbsp;'-'없이 입력해주세요</font>&nbsp; &nbsp; 
       <!-- 
       <input name="userCp3" type="text" maxlength="4" style="width:100px; height:20px;" value="" class="format" style="ime-mode:disabled;" onkeydown="return onlyNumber(event)"/></td>
          <td class="table3">&nbsp;</td>
           -->
        </tr>
      </table>
      <br>
   <div id="apDiv1">
  <input type="button" value="가입" class="button" onclick="submitForm()"/>
  <input type="button" value="취소" class="button" onclick="submitForm2()"/>
  <br>
  <br>
   </div>
      </form> 
    </div>
  </div>
 
</div>
		
		
		<!-- footer -->
		<div id="footer">
			<p>&copy; 2014 GuardOn &middot; All Rights Reserved &middot; Onsol IT</p>
		</div>
		<!-- /footer -->
	</div>
</body>
</html>