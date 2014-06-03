package com.guardon.view;

/*

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guardon.request.domain.Request;
import com.guardon.user.domain.User;
import com.guardon.workflow.domain.Workflow;

public class deprecatedMethods {
	
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

	 @RequestMapping("periodPwd.do")
		public String periodPwd(HttpServletRequest request, HttpSession session)
				throws Exception {
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
	 
	 public static int GetDifferenceOfDate(int nYear1, int nMonth1, int nDate1,
				int nYear2, int nMonth2, int nDate2) {
			Calendar cal = Calendar.getInstance();
			int nTotalDate1 = 0, nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;
			if (nYear1 > nYear2) {
				for (int i = nYear2; i < nYear1; i++) {
					cal.set(i, 12, 0);
					nDiffOfYear += cal.get(Calendar.DAY_OF_YEAR);
				}
				nTotalDate1 += nDiffOfYear;
			} else if (nYear1 < nYear2) {
				for (int i = nYear1; i < nYear2; i++) {
					cal.set(i, 12, 0);
					nDiffOfYear += cal.get(Calendar.DAY_OF_YEAR);
				}
				nTotalDate2 += nDiffOfYear;
			}

			cal.set(nYear1, nMonth1 - 1, nDate1);
			nDiffOfDay = cal.get(Calendar.DAY_OF_YEAR);
			nTotalDate1 += nDiffOfDay;
			cal.set(nYear2, nMonth2 - 1, nDate2);
			nDiffOfDay = cal.get(Calendar.DAY_OF_YEAR);
			nTotalDate2 += nDiffOfDay;

			return (nTotalDate1 - nTotalDate2) * 60 * 60 * 24;
		}

		public int calculateDate(String startDate, String endDate) throws Exception {
			int resultTime;

			startDate = startDate.replaceAll("-", "");
			endDate = endDate.replaceAll("-", "");

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

			resultTime = GetDifferenceOfDate(endYear, endMonth, endDay, startYear,
					startMonth, startDay);

			return resultTime;
		}

		@RequestMapping("invalidSession.do")
		public String invalidSession(HttpServletRequest request, HttpSession session)
				throws Exception {
			String userId = (String) session.getAttribute("userId");
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
}
*/
