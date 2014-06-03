package com.guardon.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.aspectj.weaver.loadtime.Options;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.connectId.ConnectIdService;
import com.guardon.connectId.domain.ConnectId;
import com.guardon.log.LogService;
import com.guardon.log.domain.Log;
import com.guardon.option.OptionService;
import com.guardon.option.domain.Option;
import com.guardon.report.ReportService;
import com.guardon.request.RequestService;
import com.guardon.request.domain.History;
import com.guardon.request.domain.Request;
import com.guardon.server.ServerService;
import com.guardon.server.domain.Server;
import com.guardon.user.UserService;
import com.guardon.user.domain.User;
import com.guardon.workflow.WorkflowService;
import com.guardon.workflow.domain.Workflow;
import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


@Controller
@RequestMapping("/*")
@SessionAttributes("user")
public class UserController {
 
 @Inject
 @Named("userService") 
 UserService userService;
 
 @Inject
 @Named("serverService") 
 ServerService serverService;
 
 @Inject
 @Named("reportService") 
 ReportService reportService;
 
 @Inject
 @Named("requestService") 
 RequestService requestService;
 
 @Inject
 @Named("logService") 
 LogService logService;

 @Inject
 @Named("optionService")
 OptionService optionService;
 
 @Inject
 @Named("workflowService")
 WorkflowService workflowService;
 
 @Inject
 @Named("connectIdService")
 ConnectIdService connectIdService;
 

 
 private boolean serverLock = false;
 
 public boolean isServerLock() {
	return serverLock;
}

public void setServerLock(boolean serverLock) {
	this.serverLock = serverLock;
}

public static int randomRange(int n1, int n2){
	return (int)(Math.random() * (n2-n1+1))+n1;
}

@Async
 public String firstOtp() throws Exception{
  
	 String pwdComplexity = optionService.getPwdComplexity();
	 int pwdLength = optionService.getPwdLength();	 
	 
	 if(pwdComplexity.equals("Low")){
		 String otp;
		 otp = UUID.randomUUID().toString().replace("-", "").substring(0,pwdLength);
		 char[] array = otp.toCharArray();
		 array[randomRange(1, otp.length()-1)] = (char)randomRange(33, 47);
		 otp="";
		 for (int i = 0; i < array.length; i++) {
			otp+=array[i];
		}
		 
		 System.out.println(otp);
		 if (otp.contains("@"))
				otp = otp.replaceAll("@", "\\$");
			if (otp.contains("/"))
				otp = otp.replaceAll("/", "#");
			if (otp.contains("\""))
				otp = otp.replaceAll("\"", "_");
			if (otp.contains("<"))
				otp = otp.replaceAll("<", "#");
			if (otp.contains("'"))
				otp = otp.replaceAll("'", "#");
			if (otp.contains("("))
				otp = otp.replaceAll("\\(", "%");
			if (otp.contains(")"))
				otp = otp.replaceAll("\\)", "!");
		 return otp;
	 }
	 else{		 
		 String otp="";
		 for (int i = 0; i <pwdLength ; i++) {
			 String buf;
			 buf = ""+(char)((Math.random() * 94) + 33);
			 otp = otp+buf;
			}
		 if (otp.contains("@"))
				otp = otp.replaceAll("@", "\\$");
			if (otp.contains("/"))
				otp = otp.replaceAll("/", "#");
			if (otp.contains("\""))
				otp = otp.replaceAll("\"", "_");
			if (otp.contains("<"))
				otp = otp.replaceAll("<", "#");
			if (otp.contains("'"))
				otp = otp.replaceAll("'", "#");
			if (otp.contains("("))
				otp = otp.replaceAll("\\(", "%");
			if (otp.contains(")"))
				otp = otp.replaceAll("\\)", "!");
		 return otp;
		} 
	 }
 
 public static int GetDifferenceOfDate ( int nYear1, int nMonth1, int nDate1, int nYear2, int nMonth2, int nDate2 )
 {
     Calendar cal = Calendar.getInstance ( );
     int nTotalDate1 = 0, nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;
     if ( nYear1 > nYear2 )
     {
         for ( int i = nYear2; i < nYear1; i++ )
         {
             cal.set ( i, 12, 0 );
             nDiffOfYear += cal.get ( Calendar.DAY_OF_YEAR );
         }
         nTotalDate1 += nDiffOfYear;
     }
     else if ( nYear1 < nYear2 )
     {
         for ( int i = nYear1; i < nYear2; i++ )
         {
             cal.set ( i, 12, 0 );
             nDiffOfYear += cal.get ( Calendar.DAY_OF_YEAR );
         }
         nTotalDate2 += nDiffOfYear;
     }
      
     cal.set ( nYear1, nMonth1-1, nDate1 );
     nDiffOfDay = cal.get ( Calendar.DAY_OF_YEAR );
     nTotalDate1 += nDiffOfDay;
     cal.set ( nYear2, nMonth2-1, nDate2 );
     nDiffOfDay = cal.get ( Calendar.DAY_OF_YEAR );
     nTotalDate2 += nDiffOfDay;
      
     return (nTotalDate1-nTotalDate2)*60*60*24;
 }
 
 public int calculateDate(String startDate, String endDate) throws Exception{
	 int resultTime;
	 
	 startDate=startDate.replaceAll("-", "");
	 endDate=endDate.replaceAll("-", "");
	 
	 System.out.println(endDate);
	 System.out.println(startDate);
	 
	 int startYear, endYear;
	 int startMonth, endMonth;
	 int startDay, endDay;
	 
	 startYear = Integer.parseInt(startDate.substring(0, 4));
	 endYear = Integer.parseInt(endDate.substring(0, 4));
	 
	 startMonth = Integer.parseInt(startDate.substring(5, 6));
	 endMonth = Integer.parseInt(endDate.substring(5, 6));
	 
	 startDay = Integer.parseInt(startDate.substring(7, 8));
	 endDay = Integer.parseInt(endDate.substring(7, 8));
	 
	 resultTime=GetDifferenceOfDate(endYear, endMonth, endDay, startYear, startMonth, startDay);
	 
	 
	 return resultTime;
 }
 
 public boolean checkConnectId(String serverName, String connectId) throws Exception{
		
		String connectType, ipAddress, port, serverId, serverPwd;
		String str;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		connectType = serverService.getConnectType(serverName);
		ipAddress = serverService.getServerIpAddress(serverName);
		port = serverService.getPort(serverName);
		serverId = serverService.getServerId(serverName);
		serverPwd = serverService.getServerPwd(serverName);
		
		try {
		switch (connectType) {
		case "oracle":
			
			Class.forName("oracle.jdbc.driver.OracleDriver");												
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@"+ipAddress+":"+port+":orcl", serverId, serverPwd);
			stmt = con.createStatement();
			rs = stmt.executeQuery("select count(*) from dba_users where username=upper('" + connectId + "')");
			rs.next();
			str = rs.getString(1);
			System.out.println(str);

			if (str.equals("0"))
			{
				System.out.println("there is no such Id");				
				return false;
			}
			
			rs.close();
			stmt.close();
			con.close();

			break;
		case "mssql":
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");		
			
			con = DriverManager.getConnection("jdbc:sqlserver://"+ipAddress+":"+port, serverId, serverPwd);
			stmt = con.createStatement();			
			rs = stmt.executeQuery("select count(*) from syslogins where name='"+connectId+"'");
			rs.next();
			str = rs.getString(1);
			System.out.println(str);
		
			if (str.equals("0"))
			{
				System.out.println("there is no such Id");				
				return false;
			}
			
			rs.close();
			stmt.close();
			con.close();

			break;
		case "mysql":
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://" + ipAddress
					+ "/mysql", serverId, serverPwd);

			stmt = con.createStatement();
			rs = stmt.executeQuery("select count(*) from user where user='" + connectId +"'");
			rs.next();
			str = rs.getString(1);
			System.out.println(str);
		
			if (str.equals("0"))
			{
				System.out.println("there is no such Id");				
				return false;
			}
			
			rs.close();
			stmt.close();
			con.close();

			break;

		default:
			break;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally{
			if(rs!=null)try{rs.close(); System.out.println("rs closed!!");} catch(Exception e){}
			if(stmt!=null)try{stmt.close(); System.out.println("stmt closed!!");} catch(Exception e){}
			if(con!=null)try{con.close(); System.out.println("con closed!!");} catch(Exception e){}
		}
		return true;
 }
 
 @RequestMapping("invalidSession.do")
 public String invalidSession(HttpServletRequest request, HttpSession session) throws Exception {
	 String userId = (String)session.getAttribute("userId");
	 String userType = userService.getUserType(userId);
	 session.invalidate();
	 if (userType.equals("admin") || userType.equals("super"))
		 return "adminHome";
	 else if (userType.equals("user"))
		 return "nomalUserHome";
	 else if (userType.equals("outUser"))
		 return "outUserHome";
	 else
		 return "";
 }
 
 @RequestMapping("outUserHistory.do")
 public String outUserHistory(HttpServletRequest request, HttpSession session) throws Exception{
	 
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 String userId;
	 ArrayList<History> expiredHistories = new ArrayList<History>();
	 ArrayList<History> approvedHistories = new ArrayList<History>();
	 ArrayList<History> uncheckedHistories = new ArrayList<History>();
	 userId=session.getAttribute("userId").toString();
	 
	 approvedHistories = requestService.getApprovedUserHistory(userId);
	 request.setAttribute("approvedHistories", approvedHistories);
	 System.out.println("승인된 요청이력");
	 uncheckedHistories = requestService.getUncheckedUserHistory(userId);
	 request.setAttribute("uncheckedHistories", uncheckedHistories);
	 System.out.println("대기중인 요청이력");
	 System.out.println(uncheckedHistories.size());
		/*
		for (int i = 0; i < uncheckedHistories.size(); i++) {
			
			System.out.println(uncheckedHistories.size());
			System.out.println(uncheckedHistories.get(i).getApprovalId());
			System.out.println(uncheckedHistories.get(i).getUserName());
			System.out.println(uncheckedHistories.get(i).getUserLevel());
		}*/
	
	
	 
	 
	 return "outUserHistory";
 }
 
 @RequestMapping("findId.do")
	public String findId(HttpServletRequest request)	throws Exception {
		
	 return "findId";
 }
 
 @RequestMapping("getId.do")
 public String getId(HttpServletRequest request){
	 String userName, companyNumber, userId=null;
	 Map<String, String> map = new HashMap<>();
	 userName = request.getParameter("userName");
	 companyNumber = request.getParameter("companyNumber");
	 
	 map.put("userName", userName);
	 map.put("companyNumber", companyNumber);
	 System.out.println(userName);
	 System.out.println(companyNumber);
	 

		try {
			userId = userService.getId(map);
			System.out.println(userId);
			request.setAttribute("userId", userId);
			if (userService.countId(map)==1) {
				userId = userService.getId(map);
				System.out.println("aaaaaaaaaaa : "+userId);
				request.setAttribute("userId", userId);
				return "getId";	
			}else{
				request.setAttribute("message", "해당 아이디가 존재하지 않습니다.");
				return "interPage";
			}		

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "요청을 처리할 수 없습니다.");
			return "interPage";
		}
 }
 
 @RequestMapping("findPwd.do")
 public String findPwd(HttpServletRequest request) throws Exception{
	 return "findPwd";
 }
 
 @RequestMapping("getPwd.do")
 public String getPwd(HttpServletRequest request) throws Exception{
	 String userId, userEmail, userPwd;
	 Map<String, String> map = new HashMap<>();
	 userId = request.getParameter("userId");
	 userEmail = request.getParameter("userEmail");
	 map.put("userId", userId);
	 map.put("userEmail", userEmail);
	 
	 System.out.println(userId);
	 System.out.println(userEmail);
	 

		try {
			if (userService.countPwd(map)==1) {
				userPwd = userService.getPwd(map);
				request.setAttribute("userPwd", userPwd);
				return "getPwd";	
			}else{
				request.setAttribute("message", "요청 실패! userId 또는 Email을 확인 하십시오.");
				return "interPage";
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "요청을 처리할 수 없습니다.");
			return "interPage";
		}
	  }
 
 @RequestMapping("test.do")
 //@Scheduled(fixedDelay=5000)
 public void test(HttpServletRequest request) throws Exception{
	 String userId="user";
	 ArrayList<Workflow> workflowList = workflowService.getWorkflowList(userId);
	 for (int i = 0; i < workflowList.size(); i++) {
		System.out.println(workflowList.get(i).getWorkflowName());
		System.out.println(workflowList.get(i).getWorkflowDesc());
		System.out.println(workflowList.get(i).getWorkflowStep());
		System.out.println(workflowList.get(i).getUpdateDate());
		System.out.println(workflowList.get(i).getInvolveServerCount());
		System.out.println("==============================================");
	}
 }
 
 @RequestMapping("option.do")
 public String option(HttpServletRequest request, HttpSession session){
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 String loginFailedCount=null, otpTimeLimit=null, pwdLength=null, pwdComplexity=null, autoLogout=null, approvedTimeLimit=null;
	 	
	 try {
			// autoLogout=String.valueOf(optionService.getAutoLogout());
			loginFailedCount = String.valueOf(optionService.getLoginFailedCount());
			otpTimeLimit = String.valueOf(optionService.getOtpTimeLimit());
			pwdLength = String.valueOf(optionService.getPwdLength());
			pwdComplexity = optionService.getPwdComplexity();
			approvedTimeLimit = String.valueOf(optionService.getApprovedTimeLimit());
		 
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "설정 정보를 불러오는데 실패했습니다.");
			return "errorPage";
		}
		// request.setAttribute("autoLogout", autoLogout);
		request.setAttribute("loginFailedCount", loginFailedCount);
		request.setAttribute("otpTimeLimit", otpTimeLimit);
		request.setAttribute("pwdLength", pwdLength);
		request.setAttribute("pwdComplexity", pwdComplexity);
		request.setAttribute("approvedTimeLimit", approvedTimeLimit);

		return "option";

	}
 
 @RequestMapping("updateOption.do")
 public String updateOption(HttpServletRequest request, HttpSession session){
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 String pwdComplexity;
	 int pwdLength, otpTimeLimit, loginFailedCount, approvedTimeLimit;
	 Option option = new Option();

		try {
			pwdLength = Integer.parseInt(request.getParameter("pwdLength"));
			otpTimeLimit = Integer.parseInt(request.getParameter("otpTimeLimit"));
			//loginFailedCount = Integer.parseInt(request.getParameter("loginFailedCount"));
			pwdComplexity = request.getParameter("pwdComplexity");		
			approvedTimeLimit = Integer.parseInt(request.getParameter("approvedTimeLimit"));

			option.setPwdLength(pwdLength);
			option.setOtpTimeLimit(otpTimeLimit);
			//option.setLoginFailedCount(loginFailedCount);
			option.setPwdComplexity(pwdComplexity);
			option.setApprovedTimeLimit(approvedTimeLimit);

			optionService.updateOption(option);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "설정 수정에 실패하였습니다.");
			return "adminInterPage";
		}
		request.setAttribute("message", "성공적으로 설정을 수정하였습니다.");
		return "adminInterPage";
	}
 
 @RequestMapping("outSendRequest.do")
 public String outSendRequest(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 Request rq = new Request();
	 Map<String, String> map = new HashMap<>();
	 
	 String serverName, connectId, userId, requestDesc, pwdType, approved;
	 String approvedTemp;
	
	 serverName = request.getParameter("checkList");
	 connectId = request.getParameter("connectId");
	 userId = (String)session.getAttribute("userId");
	 requestDesc = request.getParameter("requestDesc");
	 pwdType = "OTP";
	 
	 map.put("serverName", serverName);
	 map.put("connectId", connectId);
	 map.put("userId", userId);
	 
	 
	 if (connectIdService.countConnectId(map)<=0)
	 {	 
		 System.out.println("접속아이디 체크 : "+connectIdService.countConnectId(map));
		 request.setAttribute("errorMessage", "서버에 해당 접속아이디가 존재하지 않습니다.");
		 return "errorPage";
	 }
	 if (requestService.countOtpApproved(map)>0) {
		approvedTemp=requestService.getOtpApproved(map);
	}else{
		approvedTemp="";
	}
	 /*
	 try {
		 approvedTemp = requestService.getOtpApproved(map);
	 } catch (Exception e) {
		 e.printStackTrace();
		 approvedTemp = "";
	 }
	 */
	 try {
	 if (serverService.getWorkflowName(serverName).equals("none"))
		 approved = "unchecked";
	 else
		 approved = "first";
	 
	 if (connectId.equals("administrator") || connectId.equals("Administrator") || connectId.equals(serverService.getServerId(serverName)))
	 {
		 request.setAttribute("errorMessage", "해당 아이디는 사용이 불가능합니다.");
		 return "errorPage";
	 }
	 
	 
	 
	 /*
	 if (!requestService.checkDuplReq(map).equals("0"))
	 {
		 request.setAttribute("errorMessage", "해당 서버와 아이디에 대해 이미 발급 된 비밀번호가 있거나 처리중인 요청이 있습니다.");
		 return "errorPage";
	 }
	 */
	 
	 if (approvedTemp.equals("unchecked"))
	 {
		 request.setAttribute("errorMessage", "이미 진행중인 요청이 있습니다. 관리자가 처리할 때까지 기다려주십시오.");
		 return "errorPage";
	 }
	 else if (approvedTemp.equals("approved"))
	 {
		 request.setAttribute("errorMessage", "이미 승인 된 요청입니다.");
		 return "errorPage";
	 }
	 
	 if (!checkConnectId(serverName, connectId))
	 {
		 request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
		 return "errorPage";
	 }
	 
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date now = new Date();
	 String requestDate = df.format(now);
	 
	 rq.setServerName(serverName);
	 rq.setConnectId(connectId);
	 rq.setRequestDate(requestDate);
	 rq.setUserId(userId);
	 rq.setRequestDesc(requestDesc);
	 rq.setPwdType(pwdType);
	 rq.setApproved(approved);
	 
	 requestService.insertRequest(rq);
	 }
	 catch (Exception e){
		 e.printStackTrace();
		 request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
		 return "errorPage";
	 }
	 
	 return "outUserOtpRequestDone";
 }
 
 @RequestMapping("outPeriodPwdList.do")
 public String outPeriodPwdList(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 String userId=session.getAttribute("userId").toString();
	 
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 Date now = new Date();
	 String requestDate = df.format(now);
	 
	 Map<String, String> map = new HashMap<>();
	 ArrayList<Request> periodPwdList = new ArrayList<>();
	 map.put("userId", userId);
	 map.put("endDate", requestDate);
	 periodPwdList = requestService.getPeriodPwd(map);
	 
	 request.setAttribute("periodPwdList", periodPwdList);
	 return "outPeriodPwdList";
 }
 
 @RequestMapping("periodPwdList.do")
 public String periodPwdList(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 String userId=session.getAttribute("userId").toString();
	 
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 Date now = new Date();
	 String requestDate = df.format(now);
	 
	 Map<String, String> map = new HashMap<>();
	 ArrayList<Request> periodPwdList = new ArrayList<>();
	 map.put("userId", userId);
	 map.put("endDate", requestDate);
	 periodPwdList = requestService.getPeriodPwd(map);
	 
	 request.setAttribute("periodPwdList", periodPwdList);
	 return "periodPwdList";
 }
 
 
 @RequestMapping("outPeriodPwdApproval.do")
 public String outPeriodPwdApproval(HttpServletRequest request, HttpSession session){
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 Request rq = new Request();
	 Map<String, String> map = new HashMap<>();
	 String approved;
	 
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date now = new Date();
	 String requestDate = df.format(now);
	 String serverName=request.getParameter("checkList");
	 String connectId=request.getParameter("connectId");
	 String startDate = request.getParameter("startDate");
	 String endDate = request.getParameter("endDate");
	 String pwdType = "period";
	 String userId = session.getAttribute("userId").toString();
	 String requestDesc = request.getParameter("requestDesc");
		try {		
			
			map.put("serverName", serverName);
			map.put("connectId", connectId);
			
			
			if (connectId.equals("administrator") || connectId.equals("Administrator") || connectId.equals(serverService.getServerId(serverName)))
			 {
				 request.setAttribute("errorMessage", "해당 아이디는 사용이 불가능합니다.");
				 return "errorPage";
			 }
			if (!requestService.checkDuplReq(map).equals("0")) {
				request.setAttribute("errorMessage",
						"해당 서버와 아이디에 대해 이미 발급 된 비밀번호가 있거나 처리중인 요청이 있습니다.");
				return "errorPage";
			}
			if (!checkConnectId(serverName, connectId)) {
				request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
				return "errorPage";
			}

			if (serverService.getWorkflowName(serverName).equals("none"))
				approved = "unchecked";
			else
				approved = "first";		
			
			rq.setServerName(serverName);
			rq.setConnectId(connectId);
			rq.setRequestDate(requestDate);
			rq.setUserId(userId);
			rq.setRequestDesc(requestDesc);
			rq.setPwdType(pwdType);
			rq.setStartDate(startDate);
			rq.setEndDate(endDate);
			rq.setApproved(approved);

			requestService.insertRequest(rq);
		} catch (Exception e) {
			request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
			return "errorPage";
		}

		return "outUserOtpRequestDone";
 }
 
 @RequestMapping("periodPwdApproval.do")
 public String periodPwdApproval(HttpServletRequest request, HttpSession session){
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 Request rq = new Request();
	 Map<String, String> map = new HashMap<>();
	 
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date now = new Date();
	 String requestDate = df.format(now);
	 String serverName=request.getParameter("checkList");
	 String connectId=request.getParameter("connectId");
	 String startDate = request.getParameter("startDate");
	 String endDate = request.getParameter("endDate");
	 String pwdType = "period";
	 String userId = session.getAttribute("userId").toString();
	 String requestDesc = request.getParameter("requestDesc");
	 String approved;
	 
	 try{		 
		 map.put("serverName", serverName);
		 map.put("connectId", connectId);
		 
		 if (connectId.equals("administrator") || connectId.equals("Administrator") || connectId.equals(serverService.getServerId(serverName)))
		 {
			 request.setAttribute("errorMessage", "해당 아이디는 사용이 불가능합니다.");
			 return "errorPage";
		 }
			if (!requestService.checkDuplReq(map).equals("0")) {
				request.setAttribute("errorMessage",
						"해당 서버와 아이디에 대해 이미 발급 된 비밀번호가 있거나 처리중인 요청이 있습니다.");
				return "errorPage";
			}
			if (!checkConnectId(serverName, connectId)) {
				request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
				return "errorPage";
			}
	 
	 if (serverService.getWorkflowName(serverName).equals("none"))
		 approved = "unchecked";
	 else
		 approved = "first"; 	 
	 
	 rq.setServerName(serverName);
	 rq.setConnectId(connectId);
	 rq.setRequestDate(requestDate);
	 rq.setUserId(userId);
	 rq.setRequestDesc(requestDesc);
	 rq.setPwdType(pwdType);
	 rq.setStartDate(startDate);
	 rq.setEndDate(endDate);
	 rq.setApproved(approved);
	 
	 requestService.insertRequest(rq);
	 }
	 catch (Exception e){
		 request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
		 return "errorPage";
	 }
	 return "userOtpRequestDone";
 }
 
 @RequestMapping("periodPwd.do")
 public String periodPwd(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

	 String startDate, endDate;

	 startDate = request.getParameter("startDate");
	 endDate = request.getParameter("endDate");
	 
	 System.out.println(calculateDate(startDate, endDate));
	 
	 String[] str = request.getParameterValues("checkList");
	 for (int i = 0; i < str.length; i++) {
		System.out.println(str[i]);
	}

	 return "periodPwd";
 }
 
 
 @RequestMapping ("outPeriodPwdServerSelect.do")
 public String outPeriodServerList(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 String pageParam = request.getParameter("page");
		if(pageParam==null || pageParam.equals("")){
			pageParam="1";
		}
		ArrayList<Server> serverList = serverService.getPeriodServerList(Integer.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();
		
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));
		
	 return "outPeriodPwdServerSelect";
 }
 
 @RequestMapping ("periodPwdServerSelect.do")
 public String periodServerList(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 String pageParam = request.getParameter("page");
		if(pageParam==null || pageParam.equals("")){
			pageParam="1";
		}
		ArrayList<Server> serverList = serverService.getPeriodServerList(Integer.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();
		
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));
		request.setAttribute("message", "1234asd");
		
	 return "periodPwdServerSelect";
 }
 
 /*
  * otp 한 번 만드는 함수
  * 
  * 
  * 
  */ 
	public String makeOtpAll(String serverName, String connectId) throws Exception {
		
		String userOtp, ipAddress, serverId, serverPwd, connectType, serverOS, port;
		String query, str;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		TelnetSample telnet = new TelnetSample();

		userOtp = firstOtp();	  
	  
	  ipAddress = serverService.getServerIpAddress(serverName);
	  serverId = serverService.getServerId(serverName);
	  serverPwd = serverService.getServerPwd(serverName);
	  connectType = serverService.getConnectType(serverName);
	  port = serverService.getPort(serverName);
	  
	  System.out.println(serverName);
	  System.out.println(connectId);
	  System.out.println(connectType);
	  System.out.println(userOtp);
	  
	  try{
		  
	  switch (connectType) {
		
		case "oracle":				
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@"+ipAddress+":"+port+":orcl", serverId, serverPwd);
							
			query = "alter user "+connectId + " identified by \""+ userOtp + "\"";

			stmt = (Statement) con.createStatement();
			rs = stmt.executeQuery("select count(*) from dba_users where username=upper('" + connectId + "')");
			rs.next();
			str = rs.getString(1);
			System.out.println(str);

			if (str.equals("0"))
			{
				System.out.println("there is no such Id");
				//request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");	
			}
			else {
				stmt.executeUpdate(query);							
			}			
			
			rs.close();
			stmt.close();
			con.close();
			
			break;
		
		case "mssql":		
						
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");		
			
			query = "SP_PASSWORD NULL," + "'" + userOtp + "', '"+ connectId + "'";

			con = DriverManager.getConnection("jdbc:sqlserver://"+ipAddress+":"+port, serverId, serverPwd);								

			stmt = con.createStatement();
			
			rs = stmt.executeQuery("select count(*) from syslogins where name='"+connectId+"'");

			rs.next();
			str = rs.getString(1);
			System.out.println(str);

			if(str.equals("0"))
			{
				System.out.println("there is no such Id");
				//request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");				
			}
			else {
				stmt.executeUpdate(query);						
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			break;
			
		case "mysql":			
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+ipAddress+"/mysql", serverId, serverPwd);
								
			query = "update user set password=password('" + userOtp + "') where user='" + connectId + "';";
			
			stmt = (Statement) con.createStatement();
			rs = stmt.executeQuery("select count(*) from user where user='" + connectId +"'");
			rs.next();
			str = rs.getString(1);
			System.out.println(str);
			
			if(str.equals("0"))
			{
				System.out.println("there is no such Id");
				//request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
				break;
			}
			
			else {
				stmt.executeUpdate(query);
				stmt.executeUpdate("flush privileges");
			}			
			
			rs.close();
			stmt.close();
			con.close();
			
			break;
			  
		case "telnet":			
			serverOS=serverService.getServerOS(serverName);
			if(serverOS.equals("windows")){						
									
				telnet.setHostPrompt(">");
				telnet.connect(ipAddress, serverId, serverPwd);
				telnet.changePwd(connectId, "1234", userOtp);
								
				telnet.disconnect();
								
				System.out.println(userOtp);
				
				break;
			}

		default:
			break;
		}
	  }
	  catch (Exception e){
		  throw e;
	  }
	  finally{
		 if(rs!=null)try{rs.close(); System.out.println("rs closed!!");} catch(Exception e){}
		 if(stmt!=null)try{stmt.close(); System.out.println("stmt closed!!");} catch(Exception e){}
		 if(con!=null)try{con.close(); System.out.println("con closed!!");} catch(Exception e){}	
		 if(telnet.isConnected())try{telnet.disconnect(); System.out.println("telnet disconnect!!");} catch(Exception e){}
	  }
	  return userOtp;
  }
  
  @RequestMapping("changeAllList.do")
  public String changeAllList(HttpServletRequest request, HttpSession session) throws Exception{
	  if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	  ArrayList<ConnectId> changeAllList = new ArrayList<ConnectId>();
	  changeAllList=connectIdService.getConnectIdList();
	  
	  request.setCharacterEncoding("utf-8");
	  request.setAttribute("changeAllList", changeAllList);
	  
	  return "changeAllList";
  }
  
  @RequestMapping("changeAllPwd.do")
  public String changeAllPwd(HttpServletRequest request, HttpSession session){
	  if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	  
	  String temp[]=request.getParameterValues("temp");
	  /*
	try {
	
		for (int i = 0; i < temp.length; i++) {
			 temp[i] = new String(temp[i].getBytes("ISO-8859-1"), "UTF-8");
			 System.out.println("변경 후 : "+temp[i]);
		}
		System.out.println("변경전 :"+ temp[0]);
		temp[0] = new String(temp[0].getBytes("ISO-8859-1"),"UTF-8"	);
		System.out.println("변경 후 "+ temp[0]);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	*/
	  String arr[];
	  HashMap<String, String> map = new HashMap<>();
	  
	  int successCount=0;
	  int totalCount=0;
	  
		for (int i = 0; i < temp.length; i++) {
			arr = temp[i].split(",");
			System.out.println("서버명 : "+arr[0]);
			try {
				makeOtpAll(arr[0], arr[1]);
				successCount++;
				map.put("serverName", arr[0]);
				System.out.println(map.get("serverName"));
				map.put("connectId", arr[1]);
				requestService.updateExpiration(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.clear();
		}
	  
	  totalCount=temp.length;	 	
	  
	  request.setAttribute("totalCount", totalCount);
	  request.setAttribute("successCount", successCount);
	  
	  return "changeAllPwdResult";
	  
  }
 
 @RequestMapping("/userOtp.do")
 public String makeOtp(HttpServletRequest request, HttpSession session){
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		 User user = new User();
		 MakeOTP makeOTP = new MakeOTP();
		 Timer timer = new Timer();
		 Map<String, String> map = new HashMap<>();
		 
		 Connection con = null;
		 Statement stmt = null;
		 ResultSet rs = null;
		 TelnetSample telnet = new TelnetSample();
  
		 String userOtp;
		 String serverName;
		 String ipAddress;
		 String serverId;
		 String serverPwd;
		 String connectId;
		 //String dbName;
		 String serverOS;
		 String connectType;
		 String port;
		 String userType;
		 String userId;
		 String str;
		 String query;
		 
		 			 
	  if(request.getParameter("connectId")==null){
		  connectId=session.getAttribute("connectId").toString();
		  System.out.println("session connectId : "+connectId);
	  }else {
		  session.setAttribute("connectId", request.getParameter("connectId"));
		  connectId=request.getParameter("connectId");
	  }
	  if(request.getParameter("checkList")==null){
		  serverName=session.getAttribute("serverName").toString();
		  System.out.println("session serverName : "+serverName);
	  }else {
		  session.setAttribute("serverName", request.getParameter("checkList"));
		  serverName=request.getParameter("checkList");
	  }
	  
	 try{
		 
		 int otpTimeLimit = optionService.getOtpTimeLimit();
		 String pwdComplexity = optionService.getPwdComplexity();
		 int pwdLength = optionService.getPwdLength();
		 
		 userId=session.getAttribute("userId").toString();
		 userType=userService.getUserType(userId);
		 
		 map.put("serverName", serverName);
		 map.put("connectId", connectId);
		 map.put("userId", userId);	
		 
		 
		 System.out.println("serverName : "+ serverName);
		 System.out.println("connectId : "+ connectId);
		 System.out.println("userId : "+ userId);
		 //System.out.println(requestService.getOtpApproved(map));
		 System.out.println("userType : " + userType);
		 //System.out.println(requestService.getOtpApproved(map).toString());
		 
	  if ((userType.equals("outUser") && requestService.getOtpApproved(map).equals("approved")) || userType.equals("user")) {
		 
	  userOtp = firstOtp();  
	  System.out.println(userOtp);
	  	  
	  //dbName = serverService.getDbName(serverName);
	  //serverName = request.getParameter("checkList");
	  System.out.println(request.getParameter("checkList"));
	  ipAddress = serverService.getServerIpAddress(serverName);
	  serverId = serverService.getServerId(serverName);
	  serverPwd = serverService.getServerPwd(serverName);
	  connectType = serverService.getConnectType(serverName);
	  port = serverService.getPort(serverName);
	  request.setAttribute("second", otpTimeLimit);
	  
	  System.out.println(connectType);
	

			switch (connectType) {
			
			case "oracle":			
									
				request.setAttribute("userOtp", userOtp);
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				makeOTP.setIpAddress(ipAddress);
				makeOTP.setServerId(serverId);
				makeOTP.setServerPwd(serverPwd);
				makeOTP.setConnectType(connectType);
				makeOTP.setConnectId(connectId);
				makeOTP.setPort(port);
				makeOTP.setPwdComplexity(pwdComplexity);
				makeOTP.setPwdLength(pwdLength);
												
				con = DriverManager.getConnection("jdbc:oracle:thin:@"+ipAddress+":"+port+":orcl", serverId, serverPwd);
								
				query = "alter user "+connectId + " identified by \""+ userOtp + "\"";

				stmt = (Statement) con.createStatement();
				rs = stmt.executeQuery("select count(*) from dba_users where username=upper('" + connectId + "')");
				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if (str.equals("0"))
				{
					System.out.println("there is no such Id");
					request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
					return "errorPage";
				}
				else {
					stmt.executeUpdate(query);							
				}			
				
				timer.schedule(makeOTP, otpTimeLimit*1000);	
				
				rs.close();
				stmt.close();
				con.close();
				
				
				if(userType.equals("user"))
					return "userOtp";
				else if(userType.equals("outUser"))
					return "outUserOtp";
			
			case "mssql":		
								
				request.setAttribute("userOtp", userOtp);
				
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");		
				
				query = "SP_PASSWORD NULL," + "'" + userOtp + "', '"+ connectId + "'";
	
				con = DriverManager.getConnection("jdbc:sqlserver://"+ipAddress+":"+port, serverId, serverPwd);								
	
				stmt = con.createStatement();
				
				rs = stmt.executeQuery("select count(*) from syslogins where name='"+connectId+"'");

				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if(str.equals("0"))
				{
					System.out.println("there is no such Id");
					request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
					return "errorPage";
				}
				else {
					stmt.executeUpdate(query);						
				}
	
				makeOTP.setIpAddress(ipAddress);
				makeOTP.setServerId(serverId);
				makeOTP.setServerPwd(serverPwd);
				makeOTP.setConnectType(connectType);
				makeOTP.setConnectId(connectId);
				makeOTP.setPort(port);
				makeOTP.setPwdComplexity(pwdComplexity);
				makeOTP.setPwdLength(pwdLength);
				
				timer.schedule(makeOTP, otpTimeLimit*1000);
				
				rs.close();
				stmt.close();
				con.close();
				
				if(userType.equals("user"))
					return "userOtp";
				else if(userType.equals("outUser"))
					return "outUserOtp";
				
			case "mysql":			
				
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://"+ipAddress+"/mysql", serverId, serverPwd);
							
				query = "update user set password=password('" + userOtp + "') where user='" + connectId + "';";
				
				stmt = (Statement) con.createStatement();
				rs = stmt.executeQuery("select count(*) from user where user='" + connectId +"'");
				rs.next();
				str = rs.getString(1);
				System.out.println(str);
				
				if(str.equals("0"))
				{
					System.out.println("there is no such Id");
					request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
					return "errorPage";
				}
				
				else {
					stmt.executeUpdate(query);
					stmt.executeUpdate("flush privileges");
				}
				
				request.setAttribute("userOtp", userOtp);
				  
				makeOTP.setIpAddress(ipAddress);
				makeOTP.setServerId(serverId);
				makeOTP.setServerPwd(serverPwd);
				makeOTP.setConnectType(connectType);
				makeOTP.setConnectId(connectId);
				makeOTP.setPort(port);
				makeOTP.setPwdComplexity(pwdComplexity);
				makeOTP.setPwdLength(pwdLength);
				  				
				timer.schedule(makeOTP, otpTimeLimit*1000);
				
				rs.close();
				stmt.close();
				con.close();
				  
				if(userType.equals("user"))
					return "userOtp";
				else if(userType.equals("outUser"))
					return "outUserOtp";
				  
			case "telnet":			
				serverOS=serverService.getServerOS(serverName);
				if(serverOS.equals("windows")){									
					
					makeOTP.setIpAddress(ipAddress);
					makeOTP.setServerId(serverId);
					makeOTP.setServerPwd(serverPwd);
					makeOTP.setConnectType(connectType);
					makeOTP.setConnectId(connectId);
					makeOTP.setPort(port);
					makeOTP.setServerOS(serverOS);
					makeOTP.setPwdComplexity(pwdComplexity);
					makeOTP.setPwdLength(pwdLength);
										
					telnet.setHostPrompt(">");
					telnet.connect(ipAddress, serverId, serverPwd);
					telnet.changePwd(connectId, "1234", userOtp);
					user.setUserOtp(userOtp);
					request.setAttribute("userOtp", userOtp);
					
					telnet.disconnect();
					
					timer.schedule(makeOTP, otpTimeLimit*1000);
					
					
					System.out.println(userOtp);
					
					if(userType.equals("user"))
						return "userOtp";
					else if(userType.equals("outUser"))
						return "outUserOtp";
				}
	
			default:
				break;
			}		 
	  }
	  else{
		if(userType.equals("user")) return "errorUserAuthority";
		if(userType.equals("outUser")) return "errorOutAuthority";
	  }
			
	  }
	  catch (NullPointerException e) {
		  e.printStackTrace();
		  request.setAttribute("errorMessage", "OTP를 발급받을 수 없습니다.");
		  return "errorPage";
	  }
	  catch (Exception e) {
		  e.printStackTrace();
		  request.setAttribute("errorMessage", "서버에 접속할 수 없습니다.");
		  return "errorPage";
	  }
	 finally {
		if(rs!=null)try{rs.close(); System.out.println("rs closed!!");} catch(Exception e){}
		if(stmt!=null)try{stmt.close(); System.out.println("stmt closed!!");} catch(Exception e){}
		if(con!=null)try{con.close(); System.out.println("con closed!!");} catch(Exception e){}	
		if(telnet.isConnected())try{telnet.disconnect(); System.out.println("telnet disconnect!!");} catch(Exception e){}
	 }	  
	 request.setAttribute("errorMessage", "OTP발급에 실패하였습니다.");
		return "errorPage";
}

 
 @RequestMapping(method=RequestMethod.GET, value="/updateRejected.do")
 public String updateRejected(HttpSession session, HttpServletRequest request) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 Map<String, String> map = new HashMap<>();
	 String[] array = request.getParameterValues("temp");
	 String[] array2;
	 int approvalSuccessCount=0;
	 
	 for (int i = 0; i < array.length; i++) {
		 array[i] = new String(array[i].getBytes("ISO-8859-1"), "UTF-8");
	}
	 
	 DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date now = new Date();
	 
	 String approvalId = (String)session.getAttribute("userId");
	 String approvalDate = df2.format(now);
	 
	 for (int i = 0; i < array.length; i++) {
		 array2 = array[i].split(",");
		 map.put("serverName", array2[0]);
		 map.put("connectId", array2[1]);
		 map.put("userId", array2[2]);
		 map.put("approvalId", approvalId);
		 map.put("approvalDate", approvalDate);
		 requestService.updateRejected(map);
		 approvalSuccessCount++;
		 map.clear();
	 }
	 int approvalCount = array.length;
	 request.setAttribute("approvalCount", approvalCount);
	 request.setAttribute("approvalSuccessCount", approvalSuccessCount);
	 
	 return "approvalResult";
 }
 
 @RequestMapping(method=RequestMethod.GET, value="/updateApproved.do")
 public String updateApproved(HttpSession session, HttpServletRequest request) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 int approvalSuccessCount=0;
	 int approvalCount=0;
	 String userOtp=null, serverName, connectId, approved;
	 Map<String, String> map = new HashMap<>();	 	 
	 DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date now = new Date();	 	 
	 
	 String[] array = request.getParameterValues("temp");
	 String[] array2;
	 //String[] failRequest=null;
	 
	 for (int i = 0; i < array.length; i++) {
		 array[i] = new String(array[i].getBytes("ISO-8859-1"), "UTF-8");
	}
	 
	 String approvalId = (String)session.getAttribute("userId");
	 String approvalDate = df2.format(now);
	 
	 for (int i = 0; i < array.length; i++) {
		 array2 = array[i].split(",");
		 map.put("serverName", array2[0]);
		 map.put("connectId", array2[1]);
		 map.put("userId", array2[2]);
		 map.put("approvalId", approvalId);
		 map.put("approvalDate", approvalDate);
		 
		 switch (array2[3]) {
		case "period":
			approved="approved";
			serverName=map.get("serverName");
			connectId=map.get("connectId");

				try {
			userOtp=makeOtpAll(serverName, connectId);
			map.put("password", userOtp);		 
			map.put("approved", approved); 
			System.out.println("check : "+ userOtp);
			 
			requestService.updatePassword(map);
			requestService.updateApproved(map);
			 
					approvalSuccessCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
				userOtp = null;
				array2=null;
				map.clear();
				break;
		
		case "OTP":
			approved="approved";
			map.put("approved", approved); 
			requestService.updateApproved(map);
			map.clear();
			approvalSuccessCount++;
			break;
			
		case "first":
			approved="second";
			map.put("approved", approved);
			requestService.updateApproved(map);
			approvalSuccessCount++;
			break;
		case "second":
			approved="third";
			map.put("approved", approved);
			requestService.updateApproved(map);
			approvalSuccessCount++;
			break;
		case "third":
			approved="fourth";
			map.put("approved", approved);
			requestService.updateApproved(map);
			approvalSuccessCount++;
			break;
		case "fourth":
			approved="fifth";
			map.put("approved", approved);
			requestService.updateApproved(map);
			approvalSuccessCount++;
			break;
		case "fifth":
			
			try{
			if(array2[3].equals("period"))
			 {
			 serverName=map.get("serverName");
			 connectId=map.get("connectId");
			 userOtp=makeOtpAll(serverName, connectId);
			 map.put("password", userOtp);		 
			 
			 System.out.println("check : "+ userOtp);
			 
			 requestService.updatePassword(map);
			 requestService.updateApproved(map);
			 
			 userOtp=null;
			 
			 }
			 else if (array2[3].equals("OTP"))
			 {
				 requestService.updateApproved(map);
				 map.clear();
			 }
			 
			approvalSuccessCount++;
			requestService.updateApproved(map);
			} catch (Exception e) {
					e.printStackTrace();
			}			
			
			break;

		default:
			break;
		}
		 
		 
		 /*
		 if(array2[3].equals("period"))
		 {
		 serverName=map.get("serverName");
		 connectId=map.get("connectId");
		 userOtp=makeOtpAll(serverName, connectId);
		 map.put("password", userOtp);		 
		 
		 System.out.println("check : "+ userOtp);
		 
		 requestService.updatePassword(map);
		 requestService.updateApproved(map);
		 
		 userOtp=null;
		 
		 }
		 else if (array2[3].equals("OTP"))
		 {
			 requestService.updateApproved(map);
			 map.clear();
		 }
		 */
	 }
	 
	 approvalCount = array.length;
	 
	 request.setAttribute("approvalSuccessCount", approvalSuccessCount);
	 request.setAttribute("approvalCount", approvalCount);
	 
	 //request.setAttribute("failRequest", failRequest);
	 
	 return "approvalResult";
 }
 
 
 @RequestMapping("/outUserOtpPretreatment.do")
 public String outUserOtpPretreatment(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
		String pageParam = request.getParameter("page");
		if(pageParam==null || pageParam.equals("")){
			pageParam="1";
		}
		
		ArrayList<Server> serverList = serverService.getServerList(Integer.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();
		
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));
		
	 
	 return "outUserOtpPretreatment";
 }
 
 @RequestMapping("/userOtpPretreatment.do")
 public String userOtpPretreatment(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
		String pageParam = request.getParameter("page");
		if(pageParam==null || pageParam.equals("")){
			pageParam="1";
		}
		
		ArrayList<Server> serverList = serverService.getServerList(Integer.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();
		
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));
		
	 
	 return "userOtpPretreatment";
 }
 
 
 @RequestMapping("/dbList.do")
 public String dbList(HttpServletRequest request) throws Exception{
	String pageParam = request.getParameter("page");
	if(pageParam==null || pageParam.equals("")){
		pageParam="1";
	}
	ArrayList<Server> serverList = serverService.getServerList(Integer.parseInt(pageParam));
	int serverCount = serverService.getServerListCount();
	
	request.setAttribute("serverListCnt", serverCount);
	request.setAttribute("serverList", serverList);
	request.setAttribute("page", Integer.parseInt(pageParam));
	
	return "dbList";
 }
 
 @RequestMapping("/insertWorkflow.do") // 워크플로우 페이지 작성 
 public String insertWorkflow(HttpServletRequest request) throws Exception{
	 String step, workflowName, workflowDesc, workflowStep="first";
	 int involveServerCount;
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date now = new Date();
	 String updateDate = df.format(now);
	 Workflow workflow = new Workflow();
	 Map<String, String> map = new HashMap<>();
	 
	 step = request.getParameter("step");
	 workflowName = request.getParameter("workflowName");
	 workflowDesc = request.getParameter("workflowDesc");
	 map.put("workflowName", workflowName);
	 
	 String serverNameList[] = request.getParameterValues("serverNameList");
	 for (int i = 0; i < serverNameList.length; i++) {
		 map.put("serverName", serverNameList[i]);
		 serverService.setWorkflowName(map);		
	}
	 
		involveServerCount = serverNameList.length;

		workflow.setWorkflowName(workflowName);
		workflow.setWorkflowDesc(workflowDesc);
		workflow.setUpdateDate(updateDate);
		workflow.setInvolveServerCount(involveServerCount);
		
		System.out.println(involveServerCount);

	 String[] arr1 = step.split("[|]");
	 String[] arr2;
	 
	 for (int i = 0; i < arr1.length; i++) {
			arr2 = arr1[i].split(",");
			for (int j = 0; j < arr2.length; j++) {
				switch (i) {
				case 0:
					workflowStep = "first";
					break;
				case 1:
					workflowStep = "second";
					break;
				case 2:
					workflowStep = "third";
					break;
				case 3:
					workflowStep = "fourth";
					break;
				case 4:
					workflowStep = "fifth";
					break;
				default:
					break;
				}
				workflow.setUserId(arr2[j]);
				workflow.setWorkflowStep(workflowStep);
				
				workflowService.insertWorkflow(workflow);
			}
	}
	 
	 System.out.println("ssssssssssssss : "+step);
	 System.out.println("ssssssssssssss : "+workflowName);
	 System.out.println("ssssssssssssss : "+workflowDesc);
	 request.setAttribute("message", "워크플로우가 성공적으로 등록되었습니다.");
	 return "adminInterPage";
 }

 @RequestMapping("/serverHome.do")
 public String serverHome(HttpServletRequest request) throws Exception{
	 
	 return "serverHome";	 
 } 
 
 @RequestMapping("/dbInstall.do")
 public String dbInstall(HttpServletRequest request) throws Exception{
	 
	 return "dbInstall";	 
 }
 
 @RequestMapping("/serverInstall.do")
 public String serverInstall(HttpServletRequest request) throws Exception{
	 
	 return "serverInstall";	 
 }
 
	@RequestMapping("/updateServerSelect.do")
	public String updateServerSelect(HttpServletRequest request){
		String pageParam = request.getParameter("page");
		ArrayList<Server> serverList = new ArrayList<Server>();
		int serverCount;
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		try {
		serverList = serverService.getServerList(Integer	.parseInt(pageParam));
		serverCount = serverService.getServerListCount();
		} catch (Exception e) {
			request.setAttribute("message", "서버 정보를 불러오는데 실패하였습니다.");
			return "serverServicePage";
		}
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "updateServerSelect";
	}

	@RequestMapping("/updateServerPretreatment.do")
	public String updateServerPretreatment(HttpServletRequest request, HttpSession session)throws Exception {
		String serverName;
		Server server = new Server();
		
		serverName = request.getParameter("temp");			
		System.out.println(serverName);
		server = serverService.getServerInfo(serverName);		
		
		if(server.getServerType().equals("db"))
		{			
			request.setAttribute("serverName", serverName);
			request.setAttribute("ipAddress", server.getIpAddress());
			request.setAttribute("port", server.getPort());
			request.setAttribute("serverDesc", server.getServerDesc());
			request.setAttribute("serverId", server.getServerId());
			request.setAttribute("serverPwd", server.getServerPwd());
			request.setAttribute("dbName", server.getDbName());
			request.setAttribute("connectType", server.getConnectType());
			request.setAttribute("connectIdDupl", server.isConnectIdDupl());
			return "updateDB";
		}
		else if (server.getServerType().equals("server"))
		{
			request.setAttribute("serverName", serverName);
			request.setAttribute("ipAddress", server.getIpAddress());
			request.setAttribute("port", server.getPort());
			request.setAttribute("serverDesc", server.getServerDesc());
			request.setAttribute("serverId", server.getServerId());
			request.setAttribute("serverPwd", server.getServerPwd());			
			request.setAttribute("serverOS", server.getServerOS());
			request.setAttribute("connectType", server.getConnectType());
			request.setAttribute("connectIdDupl", server.isConnectIdDupl());
			return "updateServer";
		}
		request.setAttribute("message", "서버 정보를 불러오는데 실패하였습니다.");
		return "serverServicePage";
		
	}
	
	@RequestMapping("/updateDB")
	public String updateDB(HttpServletRequest request, HttpSession session) {
		
		Server server = new Server();
		
		server.setServerName(request.getParameter("serverName"));
		server.setIpAddress(request.getParameter("ipAddress"));
		server.setPort(request.getParameter("port"));
		server.setServerDesc(request.getParameter("serverDesc"));
		server.setServerId(request.getParameter("serverId"));
		server.setServerPwd(request.getParameter("serverPwd"));
		server.setDbName(request.getParameter("dbName"));
		server.setConnectType(request.getParameter("connectType"));				
		if (request.getParameter("connectIdDupl").equals("true"))
			server.setConnectIdDupl(true);
		else
			server.setConnectIdDupl(false);
		
		try{		
			serverService.updateDB(server);
		} catch (Exception e){
			request.setAttribute("message", "서버 정보 수정에 실패하였습니다.");
			return "serverServicePage";
		}
		
		request.setAttribute("message", "성공적으로 서버 정보를 수정하였습니다.");
		return "serverServicePage";
	}
	
	@RequestMapping("/updateServer")
	public String updateServer(HttpServletRequest request, HttpSession session) {
		
		Server server = new Server();
		
		server.setServerName(request.getParameter("serverName"));
		server.setIpAddress(request.getParameter("ipAddress"));
		server.setPort(request.getParameter("port"));
		server.setServerDesc(request.getParameter("serverDesc"));
		server.setServerId(request.getParameter("serverId"));
		server.setServerPwd(request.getParameter("serverPwd"));
		server.setServerOS(request.getParameter("serverOS"));
		server.setConnectType(request.getParameter("connectType"));				
		if (request.getParameter("connectIdDupl").equals("true"))
			server.setConnectIdDupl(true);
		else
			server.setConnectIdDupl(false);
		
		try{		
			serverService.updateDB(server);
		} catch (Exception e){
			request.setAttribute("message", "서버 정보 수정에 실패하였습니다.");
			return "serverServicePage";
		}
		
		request.setAttribute("message", "성공적으로 서버 정보를 수정하였습니다.");
		return "serverServicePage";
	}
	
	@RequestMapping("/deleteServerSelect.do")
	public String deleteServerSelect(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		ArrayList<Server> serverList = new ArrayList<Server>();
		int serverCount;
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		try {
		serverList = serverService.getServerList(Integer	.parseInt(pageParam));
		serverCount = serverService.getServerListCount();
		} catch (Exception e) {
			request.setAttribute("message", "서버 정보를 불러오는데 실패하였습니다.");
			return "serverServicePage";
		}
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "deleteServerSelect";		
	}
	
	@RequestMapping("/deleteServer.do")
	public String deleteServer(HttpServletRequest request) {
		
		String[] deleteServerName = request.getParameterValues("temp");
		for (int i = 0; i < deleteServerName.length; i++) {
			try {
				serverService.deleteServer(deleteServerName[i]);
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
		request.setAttribute("message", "성공적으로 서버를 삭제하였습니다.");
		return "serverServicePage";
	}
	
	@RequestMapping("deleteConnectId.do")
	public String deleteConnectId(HttpServletRequest request) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String[] temp = request.getParameterValues("checkList");
		String[] temp2=null;
		
		for (int i = 0; i < temp.length; i++) {
		   temp2=temp[i].split(",");
		   //temp2[0]=temp2[0].replaceAll(" ", "");
		   System.out.println("띄어쓰기 확인"+temp2[0]);
		   System.out.println("띄어쓰기 확인"+temp2[1]);
			map.put("connectId", temp2[0]);
			map.put("serverName", temp2[1]);
			
			connectIdService.deleteConnectId(map);
			map.clear();
		}
		
		
		request.setAttribute("message", "성공적으로 접속아이디를 삭제하였습니다.");
		return "serverServicePage";
	}
	
	
	@RequestMapping("connectIdList.do")
	public String connectIdList(HttpServletRequest request) throws Exception{
		ArrayList<ConnectId> connectIdList = new ArrayList<ConnectId>();
		connectIdList =  connectIdService.getConnectIdList();
		
		request.setAttribute("connectIdList", connectIdList);
		
		return "deleteConnectId";
	}
	
	@RequestMapping("connectIdSelect.do")
	public String connectIdSelect(HttpServletRequest request) throws Exception{
		
		String pageParam = request.getParameter("page");
		if(pageParam==null || pageParam.equals("")){
			pageParam="1";
		}
		ArrayList<Server> serverList = serverService.getServerList(Integer.parseInt(pageParam));
		request.setAttribute("serverList", serverList);
		
		return "insertConnectId";
	}
	
	@RequestMapping("/insertConnectId.do")
	public String insertConnectId(HttpServletRequest request) throws Exception{
		String connectId, serverName, connectIdDesc;
		ConnectId conn = new ConnectId();
		Map<String, String> map = new HashMap<String, String>();
		
		connectId=request.getParameter("connectId");
		serverName=request.getParameter("checkList");
		connectIdDesc=request.getParameter("connectIdDesc");
		
		map.put("connectId", connectId);
		map.put("serverName", serverName);
		
		conn.setConnectId(connectId);
		conn.setServerName(serverName);
		conn.setConnectIdDesc(connectIdDesc);

		if (connectIdService.countConnectId(map)>0) {
			request.setAttribute("message", "해당 서버에 중복된 접속 아이디가 있습니다.");
			return "serverServicePage";
		}else{
		connectIdService.insertConnectId(conn);
		request.setAttribute("message", "성공적으로 접속 아이디를 등록하였습니다.");
		return "serverServicePage";
		}
	}
	
	@RequestMapping("/insertServer.do")
	public String insertServer(HttpServletRequest request, HttpSession session) throws Exception{
	 String serverName, ipAddress, serverDesc, serverId, serverPwd, connectType, serverOS, port;
	 boolean connectIdDupl = false;
	 Server server = new Server();
	 serverName=request.getParameter("serverName");
	 ipAddress = request.getParameter("ipAddress");
	 port = request.getParameter("port");
	 serverDesc = request.getParameter("serverDesc");
	 serverId = request.getParameter("serverId");
	 serverPwd = request.getParameter("serverPwd");
	 connectType = request.getParameter("connectType");
	 serverOS = request.getParameter("serverOS");
	 if (request.getParameter("connectIdDupl").equals("true"))
		 connectIdDupl = true;
	 else if (request.getParameter("connectIdDupl").equals("false"))
		 connectIdDupl = false;
	 
	 server.setServerName(serverName);
	 server.setIpAddress(ipAddress);
	 server.setServerDesc(serverDesc);
	 server.setServerId(serverId);
	 server.setServerPwd(serverPwd);
	 server.setConnectType(connectType);
	 server.setServerOS(serverOS);
	 server.setPort(port);
	 server.setConnectIdDupl(connectIdDupl);
	 	 
	 serverService.insertServer(server);
	 
	 
	 request.setAttribute("message", "성공적으로 서버를 등록하였습니다.");
	 return "serverServicePage";
		
	}
 
 @RequestMapping("/insertDBServer.do")
 public String insertDBServer(HttpServletRequest request) throws Exception{
	 
	 String serverName, ipAddress, serverDesc, serverId, serverPwd, connectType, port, dbName;
	 boolean connectIdDupl = false;
	 Server server = new Server();
	 serverName=request.getParameter("serverName");
	 ipAddress = request.getParameter("ipAddress");
	 serverDesc = request.getParameter("serverDesc");
	 serverId = request.getParameter("serverId");
	 serverPwd = request.getParameter("serverPwd");
	 connectType = request.getParameter("connectType");	
	 port = request.getParameter("port");
	 dbName = request.getParameter("dbName");	 
	 if (request.getParameter("connectIdDupl").equals("true"))
		 connectIdDupl = true;
	 else if (request.getParameter("connectIdDupl").equals("false"))
		 connectIdDupl = false;
	 
	 server.setServerName(serverName);
	 server.setIpAddress(ipAddress);
	 server.setServerDesc(serverDesc);
	 server.setServerId(serverId);
	 server.setServerPwd(serverPwd);
	 server.setConnectType(connectType);
	 server.setPort(port);
	 server.setDbName(dbName);
	 server.setConnectIdDupl(connectIdDupl);
	 
	 serverService.insertDBServer(server);
	 
	 /*
	 ArrayList<Server> serverList = serverService.getServerList(1);
	 InetAddress pingCheck;
	 boolean checkResult;
	 for(int i=0; i<serverList.size(); i++){
		 pingCheck = InetAddress.getByName(serverList.get(i).getIpAddress().split(":")[0]);
		 checkResult = pingCheck.isReachable(1000);
		 if(checkResult){
			 serverList.get(i).setPingCheck("SUCCESS");
		 } else {
			 serverList.get(i).setPingCheck("FAILURE");
		 }
	 }
	 request.setAttribute("serverList", serverList);
	return "dbList";	
	*/
	
	request.setAttribute("message", "성공적으로 서버를 등록하였습니다.");
	return "serverServicePage";
 }
 
 
 @RequestMapping("workflow.do")
 public String workflow(HttpServletRequest request) throws Exception{
	 
	 int page=1;	 
	 request.setAttribute("serverList", serverService.getWfServerList(page));
	 
	 return "workflowServerSelect";
 }
 
 @RequestMapping("createWorkFlow.do")
 public String createWorkFlow(HttpServletRequest request) throws Exception{
	 
	 String serverName[] = request.getParameterValues("temp");
	 for (int i = 0; i < serverName.length; i++) {
		 System.out.println(serverName[i]);
	} 
	 request.setAttribute("serverNameList", serverName);
	 
	 int page=1;	 
	 request.setAttribute("userList", userService.getWfUserList(page));
	 
	 return "workflow";
 }
 
 @RequestMapping("email.do")
 public String email() throws Exception{
	  Properties properties = new Properties();
	  properties.put("mail.transport.protocol", "smtp");
	  properties.put("mail.smtp.host", "127.0.0.1");
	  properties.put("mail.smtp.port", "25");
	  
	  Session mailSession = Session.getInstance(properties);
	  Message message = new MimeMessage(mailSession);
	  
	  try {
	   message.setFrom(new InternetAddress("보내는 사람 주소"));
	   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("받는 사람 주소"));
	   message.setSentDate(new Date());
	   message.setSubject("메일 제목이 들어갈 부분");
	   
	   message.setText("메일 내용이 들어갈 부분");
	   
	   Transport.send(message);
	   System.out.println("E-mail successfully sent!");
	  } catch (AddressException e) {
	   e.printStackTrace();
	   System.out.println("AddressException : " + e);
	  } catch (MessagingException e) {
	   e.printStackTrace();
	   System.out.println("MessagingException : " + e);
	  }
	  
	  return "email";
 }

 
 @RequestMapping("userOtpDBSelect.do")
 public String userOtpDBSelect(HttpServletRequest request) throws Exception{
	  ArrayList<User> DBlist = new ArrayList<User>();
	  
	  for (int i = 0; i < 6; i++) {
	   User user = new User();
	   DBlist.add(user);
	  }
	  
	  request.setAttribute("DBList",DBlist);
	 
	 return "userOtpDBSelect";
 }
 
 @RequestMapping("/adminLogin.do")
 public String adminLogin(HttpServletRequest request) throws Exception{
	 
	 return "adminLogin";

 }
 
 @RequestMapping("/approvalResult.do")
 public String approvalResult(HttpServletRequest request, HttpSession session)throws Exception{
  
  ArrayList<User> list = new ArrayList<User>();
  
  for (int i = 0; i < 4; i++) {
   User user = new User();
   user.setUserId("asd"+i);
   user.setUserName("김동진"+i);
   user.setUserDepartment("개발"+i+"부");
   list.add(user);
  }
  request.setAttribute("resultList", list);
  return "approvalResult";
 }
 @RequestMapping("/approvalUserList.do")
 public String approvalUserList(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
  

  ArrayList<Request> requestList = new ArrayList<Request>();
  int page=1;
  try {
	page=Integer.parseInt(request.getParameter("page"));
  } catch(Exception e){
	page=1;  
  }
  requestList=requestService.getApprovedList(page);  
  request.setAttribute("requestList", requestList);
  return "approvalUserList";
 }
 
 @ResponseBody
 @RequestMapping("/checkApproval.do")
 public void checkApproval(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
  ArrayList<Request> requestList = new ArrayList<Request>();
  int page=1;
  try {
	page=Integer.parseInt(request.getParameter("page"));
  } catch(Exception e){
	page=1;  
  }
  String userId = session.getAttribute("userId").toString();
  requestList=requestService.getApprovedList(page);
  
  ArrayList<Workflow> workflowList = new ArrayList<Workflow>();
  
  //workflowList = workflowService.getWorkflowList(userId);
  
  
  
  if(requestList.size()>0){
	  response.getOutputStream().write("승인해야함".getBytes("UTF-8"));
  } else {
	  response.getOutputStream().write("승인완료됨".getBytes("UTF-8"));
  }
 }
 
 @RequestMapping("/outUserOtp.do")
 public String outUserOtp(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
  User user = new User();
  String userOtp;
  Boolean approval = false;
  String message="관리자 승인을 기다리십시오.";
  
  if(approval=true){
   userOtp = firstOtp(); //OTP set  
   user.setUserOtp(userOtp);
   request.setAttribute("userOtp", userOtp);
   System.out.println(userOtp);
   return "outUserOtp";
  }else if (approval=false) {
   request.setAttribute("message", message);
   return "outUserApproval";
  }
  
  
  return "outUserOtp";
 }
 
 
 @RequestMapping("/outUserApproval.do")
 public String outUserApproval(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
  
  String message = "승인요청 되었습니다.";
  User user = new User();
  user.setApproval(true);
  request.setAttribute("message", message);
  
  return "outUserApproval";
 }
 
 @RequestMapping("/index.do")
 public String index(HttpServletRequest request, HttpSession session) throws Exception {	 
		
	 if (session.getAttribute("userId")!=null)
	 {
		 String userId = (String)session.getAttribute("userId");
		 String userType = userService.getUserType(userId);
		 
		 switch (userType) {
		case "admin":
			return "adminHome";
			
		case "user":
			return "nomalUserHome";
		
		case "outUser":
			return "outUserHome";	
		case "super":
			return "superHome";
			
		default:
			break;
		}
	 }
	 return "userLogin";
 }
 
 @RequestMapping("/CheckPeriodPwd.do")
 public String ChechPeriodPwd(HttpServletRequest request, HttpSession session) throws Exception {
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
  User user = new User();
  String userOtp;
  String outEndPerdate;
  
  userOtp = firstOtp(); //OTP set
  //outEndPerdate = user.getOutEndPerDate();
  outEndPerdate = "2013년 12월 03일";
  
  user.setUserOtp(userOtp);
  request.setAttribute("userOtp", userOtp);
  request.setAttribute("periodPwd",outEndPerdate);
  System.out.println(userOtp);
  System.out.println(outEndPerdate);
  
  return "periodPwd";
 }
 
 
 @RequestMapping("/changePwd.do")
 public String changePwd(HttpServletRequest request, HttpSession session)throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
  
  ArrayList<User> list = new ArrayList<User>();
  
  for (int i = 0; i < 2; i++) {
   User user = new User();
   user.setUserName("김동진"+i);
   user.setUserDepartment("개발"+i+"부");
   user.setUserOtp(firstOtp());
   list.add(user);
  }
  request.setAttribute("resultList", list);
  return "changePwd";
 }
 
 @RequestMapping("/userJoin.do")
 public String userJoin(HttpServletRequest request) throws Exception{	 
	 return "joinUser";
 }
 
 @RequestMapping("/insertUser.do")
 public String insertUser(HttpServletRequest request){
	 String userName, userId, userPwd, userDepartment, userLevel, companyNumber, userEmail, phoneNumber, userType;
	 User user = new User();
	 userName=request.getParameter("userName");
	 userId = request.getParameter("userId");
	 userPwd = request.getParameter("userPwd");
	 userType = request.getParameter("userType");
	 userDepartment = request.getParameter("userDepartment");
	 userLevel = request.getParameter("userLevel");
	 companyNumber = request.getParameter("companyNumber");
	 userEmail = request.getParameter("userEmail");
	 phoneNumber = request.getParameter("phoneNumber")+request.getParameter("userCp2");
	 
	 GregorianCalendar gc = new GregorianCalendar();
	 long now = gc.getTimeInMillis();
	 Timestamp joinDate = new Timestamp(now);
	 
	 user.setUserName(userName);
	 user.setUserId(userId);
	 user.setUserPwd(userPwd);
	 user.setUserType(userType);
	 user.setUserDepartment(userDepartment);
	 user.setUserLevel(userLevel);
	 user.setCompanyNumber(companyNumber);
	 user.setUserEmail(userEmail);
	 user.setPhoneNumber(phoneNumber);
	 user.setJoinDate(joinDate);
	 	 
	 try {
	 if (userType.equals("admin"))
	 {
		 userService.insertJoinRequest(user);
		 request.setAttribute("message", "회원 가입 요청이 완료되었습니다.");
		 return "interPage";		 
	 }
	 else
	 {
		 userService.insertUser(user);	 
		 request.setAttribute("message", "회원 가입이 완료되었습니다.");
		 return "interPage";
	 }
	 }catch (NestedSQLException e) {
		 e.printStackTrace();
		 request.setAttribute("errorMessage", "해당 아이디는 사용하실 수 없습니다.");
			return "errorPage";
		 
	 }catch (Exception e) {
		 e.printStackTrace();
		 request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
			return "errorPage";
		 
	 }
 }
 
 @RequestMapping("/joinUserList.do")
	public String joinUserList(HttpServletRequest request, HttpSession session)throws Exception {
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 String userId = (String)session.getAttribute("userId");
	 if(!(userService.getUserType(userId).equals("super")))
	 {
		 request.setAttribute("errorMessage", "최고 관리자만 접근할 수 있습니다.");
			return "errorPage";
	 }
	 
	 
		ArrayList<User> joinUserList = new ArrayList<User>();

		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}

		joinUserList = userService.getJoinUserList(Integer.parseInt(pageParam));
		
		// 혹시 안될 경우 getJoinUserListCount 구현해야함
		int count = joinUserList.size();

		request.setAttribute("joinUserListCnt", count);
		request.setAttribute("joinUserList", joinUserList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "joinUserList";
	}
 
 @RequestMapping("/approvalJoinReq.do")
	public String approvalJoinReq(HttpServletRequest request, HttpSession session)throws Exception {
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 String[] userId;
	 
	 userId = request.getParameterValues("temp");
	 
	 for (int i = 0; i < userId.length; i++) {
		 System.out.println(userId[i]);
		userService.setJoinApproved(userId[i]);
	}
	 
	 request.setAttribute("message", "총 "+userId.length+"건의 요청을 수락하였습니다.");
	 return "adminInterPage";
 }
 
	@RequestMapping("/rejectJoinReq.do")
	public String rejectJoinReq(HttpServletRequest request, HttpSession session)	throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
		String[] userId;
		
		userId = request.getParameterValues("temp");
		
		for (int i = 0; i < userId.length; i++) {
			System.out.println(userId[i]);
			userService.deleteUser(userId[i]);
		}

		request.setAttribute("message", "총 " + userId.length	+ "건의 요청을 반려하였습니다.");
		return "adminInterPage";
	}
 
 @RequestMapping("/updateUser.do")
 public String updateUser(HttpServletRequest request, HttpSession session) throws Exception{	
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 String userId, userType;
	 User user = new User();
	 
	 userId = session.getAttribute("userId").toString();
	 userType = userService.getUserType(userId);
	 
	 user = userService.getUserBasicInfo(userId);
	 
	 request.setAttribute("userName", user.getUserName());
	 request.setAttribute("userDepartment", user.getUserDepartment());
	 request.setAttribute("userLevel", user.getUserLevel());
	 request.setAttribute("companyNumber", user.getCompanyNumber());
	 request.setAttribute("userEmail", user.getUserEmail());	 
	
	 String fullPhoneNumber = user.getPhoneNumber();
	 String phoneNumber = fullPhoneNumber.substring(0, 3);	 
	 String userCp2 = fullPhoneNumber.substring(3);
	 //String userCp3 = fullPhoneNumber.substring(7);
	 request.setAttribute("phoneNumber", phoneNumber);
	 request.setAttribute("userCp2", userCp2);
	 //request.setAttribute("userCp3", userCp3);	 
	 
	 if (userType.equals("admin"))
		 return "updateAdmin";
	 else if (userType.equals("user"))
		 return "updateUser";
	 else if (userType.equals("outUser"))
		 return "updateOutUser";
	 else if (userType.equals("super"))
		 return "updateAdmin"; 
	 else{
		 request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
		return "errorPage";
	 }
 }
 
 @RequestMapping("/updateUserInfo.do")
 public String updateUserInfo(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
	 String userId, userName, userPwd, userDepartment, userLevel, companyNumber, userEmail, phoneNumber;
	 User user = new User();
	 userId = session.getAttribute("userId").toString();	
	 userName = request.getParameter("userName");
	 userPwd = request.getParameter("userPwd");
	 userDepartment = request.getParameter("userDepartment");
	 userLevel = request.getParameter("userLevel");
	 companyNumber = request.getParameter("companyNumber");
	 userEmail = request.getParameter("userEmail");
	 phoneNumber = request.getParameter("phoneNumber")+request.getParameter("userCp2");
	 	
	 user.setUserId(userId);
	 user.setUserName(userName);
	 user.setUserPwd(userPwd);
	 user.setUserDepartment(userDepartment);
	 user.setUserLevel(userLevel);
	 user.setCompanyNumber(companyNumber);
	 user.setUserEmail(userEmail);
	 user.setPhoneNumber(phoneNumber);	 	
	 
	 userService.updateUser(user);
	 
	 String userType = userService.getUserType(userId);
	 
	 if (userType.equals("admin") || userType.equals("super"))
	 {
		 request.setAttribute("message", "개인 정보가 수정되었습니다.");
		 return "adminInterPage";
	 }
	 else
	 {
		 request.setAttribute("message", "개인 정보가 수정되었습니다.");
		 return "interPage";
	 }
	 
 }
 
 public void executeMakeOTP() throws Exception {
	 MakeOTP makeOTP = new MakeOTP();
	 String serverName = "onsol";
	 makeOTP.setConnectId("onsol");
	 makeOTP.setIpAddress(serverService.getServerIpAddress(serverName));
	 makeOTP.setServerId(serverService.getServerId(serverName));
	 makeOTP.setServerPwd(serverService.getServerPwd(serverName));
	 makeOTP.setConnectId(serverService.getConnectType(serverName));
	 makeOTP.run();
	 
 }
 
 @RequestMapping("outUserOtpRequest.do")
 public String outUserOtpRequest(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 
		String pageParam = request.getParameter("page");
		if(pageParam==null || pageParam.equals("")){
			pageParam="1";
		}
		ArrayList<Server> serverList = serverService.getServerList(Integer.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();
		
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

	 
	 return "outUserOtpRequest";
 }
 
 @RequestMapping("sendRequest.do")
 public String sendRequest(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
	 Request rq = new Request();
	 String serverName, connectId, userId, requestDesc, pwdType;
	
	 serverName = request.getParameter("checkList");
	 connectId = request.getParameter("connectId");
	 userId = (String)session.getAttribute("userId");
	 requestDesc = request.getParameter("requestDesc");
	 System.out.println(serverService.getWorkflowName(serverName));
	 
	 pwdType = "OTP";
	 
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date now = new Date();
	 String requestDate = df.format(now);
	 
	 rq.setServerName(serverName);
	 rq.setConnectId(connectId);
	 rq.setRequestDate(requestDate);
	 rq.setUserId(userId);
	 rq.setRequestDesc(requestDesc);
	 rq.setPwdType(pwdType);
	 
	 requestService.insertRequest(rq);
	 
	 
	 return "";
 }
  
 @RequestMapping("userList.do")
 public String userList(HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
    
  ArrayList<User> list = new ArrayList<User>();
  
  for (int i = 0; i < 6; i++) {
   User user = new User();
   user.setUserId("asd"+i);
   user.setUserName("김동진"+i);
   user.setUserDepartment("개발"+i+"팀");
   list.add(user);
  }
  
  request.setAttribute("userList",list);
  
  
  return "userList";
 }
 
 
 
 @RequestMapping("/userPeriodPwd.do")
 public String scheduled(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception{
	 if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
  String userPeriodPwd;
  userPeriodPwd = firstOtp(); //OTP set
  return userPeriodPwd;
 }
 
	@RequestMapping(method = RequestMethod.POST, value = "/userLogin.do")
	public String userLogin(HttpServletRequest request, HttpSession session) throws Exception{
		String userPwd, userId, userType;
		String loginFailed = null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String loginTime = df.format(now);

		userPwd = request.getParameter("userPwd");
		userId = request.getParameter("userId");
		User user = new User();

		try {
			user = userService.getUserBasicInfo(userId);
			
			if (userPwd == null || userId == null)
				return "errorLogin";
			
			/*
			if (userService.isActive(userId))
				return "errorLogin";
			*/
			
			userType = userService.getUserType(userId);
			System.out.println(userType);
			Log log = new Log();
			log.setLoginTime(loginTime);
			log.setUserId(userId);
			
			if (userType.equals("super")) {
				if (userPwd.equals(userService.getUserPwd(userId).toString())) {
					System.out.println("success");

					userService.setActive(userId);
					
					logService.setLoginTime(log);

					System.out.println(userType);
					
					session.setAttribute("userId", userId);
					session.setMaxInactiveInterval(-1);
					return "superHome";
				} else {
					System.out.println("failure");
				}
			}

				else if (userType.equals("admin")) {
				if (userPwd.equals(userService.getUserPwd(userId).toString())) {
					if ( user.getJoinApproved().equals("approved")) {
						System.out.println("success");

						userService.setActive(userId);
						
						logService.setLoginTime(log);

						System.out.println(userType);
						// ArrayList<String> loginInfo = new ArrayList<>();

						//String userIdType = userId + "," + userType;
						// loginInfo.add(userIdType);

						session.setAttribute("userId", userId);
						session.setMaxInactiveInterval(-1);
						return "adminHome";
					}else if(user.getJoinApproved().equals("unchecked")){
						return "errorLogin";
					}
					
					}
					else {
					System.out.println("failure");
				}

			} else if (userType.equals("user")) {
				if (userPwd.equals(userService.getUserPwd(userId))) {
					if ( user.getJoinApproved().equals("approved")) {
					System.out.println("success");
					//userService.setActive(userId);	
					
					logService.setLoginTime(log);
					
					session.setAttribute("userId", userId);
					session.setMaxInactiveInterval(-1);

					return "nomalUserHome";
					}
				} else if(user.getJoinApproved().equals("unchecked")){
					return "errorLogin";
				}else {
					System.out.println("failure");
				}

			} else if (userType.equals("outUser")) {
				if (userPwd.equals(userService.getUserPwd(userId))) {
					System.out.println("success");
					//userService.setActive(userId);		
					
					logService.setLoginTime(log);
					
					session.setAttribute("userId", userId);
					session.setMaxInactiveInterval(-1);
					return "outUserHome";
				} else {
					System.out.println("failure");
				}
			}

		} catch (NullPointerException e) {
			loginFailed = "failure";
			request.setAttribute("failure", loginFailed);
			return "errorLogin";
		} catch (Exception e) {
			e.printStackTrace();
			return "errorLogin";
		}
		return "errorLogin";
	}
 
 @RequestMapping("/userLogout.do")
 public String userLogout(HttpServletRequest request, HttpSession session){
	 
	 String userId;
	 
	 userId = (String)session.getAttribute("userId");
	 session.invalidate();
	 //request.setAttribute("message", "정상적으로 로그아웃 되었습니다.");
	 
	 try {
		 userService.setDeactive(userId);		 
	 }
	 catch (Exception e) {
		 e.printStackTrace();
	 }
	 return "logoutSuccess";
	 
 }

 @ResponseBody
 @RequestMapping(value="/checkServerName.do",method=RequestMethod.POST)
 public void checkServerName (String serverName, HttpServletResponse response) throws Exception{
  
  StringBuffer buffer = new StringBuffer();
  buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
  buffer.append("<result>");
 // if("0".equals(serverService.countServerName(serverName))){
  if(serverService.countServerName(serverName)==0){
	  buffer.append("사용가능");
  }else{
	  buffer.append("사용불가");
  }
  
  buffer.append("</result>");
  response.setContentType("text/xml;charset=utf-8");
  response.getWriter().write(buffer.toString());
 }
 
 @ResponseBody
 @RequestMapping(value="/checkUserId.do",method=RequestMethod.POST)
 public void checkUserId (String userId, HttpServletResponse response) throws Exception{
	 System.out.println("userId : "+userId);
	 System.out.println(userService.getUserId(userId));
	 //userId = userService.getUserId(userId);
  
  
  
  StringBuffer buffer = new StringBuffer();
  buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
  buffer.append("<result>");
  if("0".equals(userService.getUserId(userId))){
	  buffer.append("사용가능");
  }else{
	  buffer.append("사용불가");
  }
  
  buffer.append("</result>");
  response.setContentType("text/xml;charset=utf-8");
  response.getWriter().write(buffer.toString());
 }
 

 
 /*
 @ResponseBody
 @RequestMapping(value="/checkUserId.do",method=RequestMethod.GET)
 public void checkUserId (HttpServletRequest request, HttpServletResponse response,String uid) throws Exception{
  
	 		 uid = request.getParameter("userId");
	 
	 		 System.out.println(uid);

 	        StringBuffer str = new StringBuffer();

	        str.append("<?xml version='1.0' encoding='utf-8'?>");

	        str.append("<root>");

	        if(uid.equals(userService.getUserId(uid))) {

	            str.append("true");

	        } else {

	           str.append("false");

	       }

	       str.append("<id>" + uid + "</id>");

	       str.append("</root>");

	       

	       response.setContentType("text/xml;charset=utf-8");

	       response.getWriter().write(str.toString());
	  
	  
	  

	  StringBuffer buffer = new StringBuffer();
	  buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	  buffer.append("<result>");
	  if(uid != null){
		  buffer.append("no");
	  }else{
		  buffer.append("ok");
	  }
	  
	  buffer.append("</result>");
	  response.setContentType("text/xml;charset=utf-8");
	  response.getWriter().write(buffer.toString());

 }
 */
 @ResponseBody
 @RequestMapping(value="tcpCheck", method=RequestMethod.GET)
 public void tcpCheck(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
	 String ip = request.getParameter("ip");
	 boolean checkResult=false;
	 JSONObject jsonObj = new JSONObject();
	 
	 try {
		if (ip!=null && !ip.equals("")) {
			InetAddress pingCheck;
			pingCheck = InetAddress.getByName(ip);
			checkResult = pingCheck.isReachable(1000);
			
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	 if(checkResult){
		 jsonObj.put("result", "SUCCESS");
	 }else {
		jsonObj.put("result", "FAILURE");
	}
	 response.getOutputStream().write(jsonObj.toString().getBytes("UTF-8"));
 }
 
 
 

} // end of class