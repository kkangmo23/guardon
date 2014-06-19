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
import com.guardon.report.domain.ApprovalInfo;
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

	@RequestMapping("userList.do")
	public String userList(HttpServletRequest request) throws Exception {

		return "/Admin/userList";
	}
	
	@RequestMapping("getUserList.do")
	public String getUserList(HttpServletRequest request) throws Exception {
		ArrayList<User> userList = new ArrayList<User>();
		Map<String, String> map = new HashMap<>();
		String admin="";
		String user="";
		String outUser="";
		
		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		
		map.put("page", pageParam);		
		
		String[] temp  = request.getParameterValues("userType");
		
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].equals("admin"))
				admin="admin";
			else if (temp[i].equals("user"))
				user="user";
			else if (temp[i].equals("outUser"))
				outUser="outUser";
		}
		
		map.put("admin", admin);
		map.put("user", user);
		map.put("outUser", outUser);
		

		switch (request.getParameter("token")) {
		case "all":
			userList = userService.getUserListAll(map);

			break;
		case "userId":
			map.put("keyValue", request.getParameter("keyValue"));
			userList = userService.getUserListByUserId(map);

			break;
		case "userName":
			map.put("keyValue", request.getParameter("keyValue"));
			userList = userService.getUserListByUserName(map);

			break;
		case "companyNumber":
			map.put("keyValue", request.getParameter("keyValue"));
			userList = userService.getUserListByCompanyNumber(map);

			break;		

		default:
			break;
		}
		
		request.setAttribute("userList", userList);
		request.setAttribute("userListCount",userService.getUserListCount());
		
		return "/Admin/userList";
	}
	
	@RequestMapping("findId.do")
	public String findId(HttpServletRequest request) throws Exception {

		return "/Common/findId";
	}

	@RequestMapping("getId.do")
	public String getId(HttpServletRequest request) {
		String userName, companyNumber, userId = null;
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
			if (userService.countId(map) == 1) {
				userId = userService.getId(map);
				System.out.println("aaaaaaaaaaa : " + userId);
				request.setAttribute("userId", userId);
				return "/Common/getId";
			} else {
				request.setAttribute("message", "해당 아이디가 존재하지 않습니다.");
				return "/Common/interPage";
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "요청을 처리할 수 없습니다.");
			return "/Common/interPage";
		}
	}

	@RequestMapping("findPwd.do")
	public String findPwd(HttpServletRequest request) throws Exception {
		return "/Common/findPwd";
	}

	@RequestMapping("getPwd.do")
	public String getPwd(HttpServletRequest request) throws Exception {
		String userId, userEmail, userPwd;
		Map<String, String> map = new HashMap<>();
		userId = request.getParameter("userId");
		userEmail = request.getParameter("userEmail");
		map.put("userId", userId);
		map.put("userEmail", userEmail);

		System.out.println(userId);
		System.out.println(userEmail);

		try {
			if (userService.countPwd(map) == 1) {
				userPwd = userService.getPwd(map);
				request.setAttribute("userPwd", userPwd);
				return "/Common/getPwd";
			} else {
				request.setAttribute("message",
						"요청 실패! userId 또는 Email을 확인 하십시오.");
				return "/Common/interPage";
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "요청을 처리할 수 없습니다.");
			return "/Common/interPage";
		}
	}

	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, HttpSession session)
			throws Exception {

		if (session.getAttribute("userId") != null) {
			String userId = (String) session.getAttribute("userId");
			String userType = userService.getUserType(userId);

			switch (userType) {
			case "admin":
				return "/Admin/adminHome";

			case "user":
				return "/User/nomalUserHome";

			case "outUser":
				return "/Out/outUserHome";
			case "super":
				return "/Admin/superHome";

			default:
				break;
			}
		}
		return "/Common/userLogin";
	}

	@RequestMapping("/userJoin.do")
	public String userJoin(HttpServletRequest request) throws Exception {
		return "/Common/joinUser";
	}

	@RequestMapping("/insertUser.do")
	public String insertUser(HttpServletRequest request) {
		String userName, userId, userPwd, userDepartment, userLevel, companyNumber, userEmail, phoneNumber, userType;
		User user = new User();
		userName = request.getParameter("userName");
		userId = request.getParameter("userId");
		userPwd = request.getParameter("userPwd");
		userType = request.getParameter("userType");
		userDepartment = request.getParameter("userDepartment");
		userLevel = request.getParameter("userLevel");
		companyNumber = request.getParameter("companyNumber");
		userEmail = request.getParameter("userEmail");
		phoneNumber = request.getParameter("phoneNumber")
				+ request.getParameter("userCp2");

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
			if (userType.equals("admin")) {
				userService.insertJoinRequest(user);
				request.setAttribute("message", "회원 가입 요청이 완료되었습니다.");
				return "/Common/interPage";
			} else {
				userService.insertUser(user);
				request.setAttribute("message", "회원 가입이 완료되었습니다.");
				return "/Common/interPage";
			}
		} catch (NestedSQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "해당 아이디는 사용하실 수 없습니다.");
			return "/Common/errorPage";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
			return "/Common/errorPage";

		}
	}

	@RequestMapping("/joinUserList.do")
	public String joinUserList(HttpServletRequest request, HttpSession session)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}

		String userId = (String) session.getAttribute("userId");
		if (!(userService.getUserType(userId).equals("super"))) {
			request.setAttribute("errorMessage", "최고 관리자만 접근할 수 있습니다.");
			return "/Common/errorPage";
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

		return "/Admin/joinUserList";
	}

	@RequestMapping("/approvalJoinReq.do")
	public String approvalJoinReq(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}

		String[] userId;

		userId = request.getParameterValues("temp");

		for (int i = 0; i < userId.length; i++) {
			System.out.println(userId[i]);
			userService.setJoinApproved(userId[i]);
		}

		request.setAttribute("message", "총 " + userId.length
				+ "건의 요청을 수락하였습니다.");
		return "/Admin/adminInterPage";
	}

	@RequestMapping("/rejectJoinReq.do")
	public String rejectJoinReq(HttpServletRequest request, HttpSession session)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}
		String[] userId;

		userId = request.getParameterValues("temp");

		for (int i = 0; i < userId.length; i++) {
			System.out.println(userId[i]);
			userService.deleteUser(userId[i]);
		}

		request.setAttribute("message", "총 " + userId.length
				+ "건의 요청을 반려하였습니다.");
		return "/Admin/adminInterPage";
	}

	@RequestMapping("/updateUser.do")
	public String updateUser(HttpServletRequest request, HttpSession session)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
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
		// String userCp3 = fullPhoneNumber.substring(7);
		request.setAttribute("phoneNumber", phoneNumber);
		request.setAttribute("userCp2", userCp2);
		// request.setAttribute("userCp3", userCp3);

		if (userType.equals("admin"))
			return "/Admin/updateAdmin";
		else if (userType.equals("user"))
			return "/User/updateUser";
		else if (userType.equals("outUser"))
			return "/Out/updateOutUser";
		else if (userType.equals("super"))
			return "/Admin/updateAdmin";
		else {
			request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
			return "/Common/errorPage";
		}
	}

	@RequestMapping("/updateUserInfo.do")
	public String updateUserInfo(HttpServletRequest request, HttpSession session)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
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
		phoneNumber = request.getParameter("phoneNumber")
				+ request.getParameter("userCp2");

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

		if (userType.equals("admin") || userType.equals("super")) {
			request.setAttribute("message", "개인 정보가 수정되었습니다.");
			return "/Admin/adminInterPage";
		} else {
			request.setAttribute("message", "개인 정보가 수정되었습니다.");
			return "/Common/interPage";
		}

	}

	@RequestMapping("/userLogin.do")
	public String userLogin(HttpServletRequest request, HttpSession session)
			throws Exception {
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
				return "/Common/errorLogin";

			/*
			 * if (userService.isActive(userId)) return "errorLogin";
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
					return "/Admin/superHome";
				} else {
					System.out.println("failure");
				}
			}

			else if (userType.equals("admin")) {
				if (userPwd.equals(userService.getUserPwd(userId).toString())) {
					if (user.getJoinApproved().equals("approved")) {
						System.out.println("success");

						userService.setActive(userId);

						logService.setLoginTime(log);

						System.out.println(userType);
						// ArrayList<String> loginInfo = new ArrayList<>();

						// String userIdType = userId + "," + userType;
						// loginInfo.add(userIdType);

						session.setAttribute("userId", userId);
						session.setMaxInactiveInterval(-1);
						return "/Admin/adminHome";
					} else if (user.getJoinApproved().equals("unchecked")) {
						return "/Common/errorLogin";
					}

				} else {
					System.out.println("failure");
				}

			} else if (userType.equals("user")) {
				if (userPwd.equals(userService.getUserPwd(userId))) {
					if (user.getJoinApproved().equals("approved")) {
						System.out.println("success");
						// userService.setActive(userId);

						logService.setLoginTime(log);

						session.setAttribute("userId", userId);
						session.setMaxInactiveInterval(-1);

						return "/User/nomalUserHome";
					}
				} else if (user.getJoinApproved().equals("unchecked")) {
					return "/Common/errorLogin";
				} else {
					System.out.println("failure");
				}

			} else if (userType.equals("outUser")) {
				if (userPwd.equals(userService.getUserPwd(userId))) {
					System.out.println("success");
					// userService.setActive(userId);

					logService.setLoginTime(log);

					session.setAttribute("userId", userId);
					session.setMaxInactiveInterval(-1);
					return "/Out/outUserHome";
				} else {
					System.out.println("failure");
				}
			}

		} catch (NullPointerException e) {
			loginFailed = "failure";
			request.setAttribute("failure", loginFailed);
			return "/Common/errorLogin";
		} catch (Exception e) {
			e.printStackTrace();
			return "/Common/errorLogin";
		}
		return "/Common/errorLogin";
	}

	@RequestMapping("/userLogout.do")
	public String userLogout(HttpServletRequest request, HttpSession session) {

		String userId;

		userId = (String) session.getAttribute("userId");
		session.invalidate();
		// request.setAttribute("message", "정상적으로 로그아웃 되었습니다.");

		try {
			userService.setDeactive(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/Common/logoutSuccess";

	}

	@ResponseBody
	@RequestMapping(value = "/checkUserId.do", method = RequestMethod.POST)
	public void checkUserId(String userId, HttpServletResponse response)
			throws Exception {
		System.out.println("userId : " + userId);
		System.out.println(userService.getUserId(userId));
		// userId = userService.getUserId(userId);

		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<result>");
		if ("0".equals(userService.getUserId(userId))) {
			buffer.append("사용가능");
		} else {
			buffer.append("사용불가");
		}

		buffer.append("</result>");
		response.setContentType("text/xml;charset=utf-8");
		response.getWriter().write(buffer.toString());
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="/checkUserId.do",method=RequestMethod.GET) public
	 * void checkUserId (HttpServletRequest request, HttpServletResponse
	 * response,String uid) throws Exception{
	 * 
	 * uid = request.getParameter("userId");
	 * 
	 * System.out.println(uid);
	 * 
	 * StringBuffer str = new StringBuffer();
	 * 
	 * str.append("<?xml version='1.0' encoding='utf-8'?>");
	 * 
	 * str.append("<root>");
	 * 
	 * if(uid.equals(userService.getUserId(uid))) {
	 * 
	 * str.append("true");
	 * 
	 * } else {
	 * 
	 * str.append("false");
	 * 
	 * }
	 * 
	 * str.append("<id>" + uid + "</id>");
	 * 
	 * str.append("</root>");
	 * 
	 * 
	 * 
	 * response.setContentType("text/xml;charset=utf-8");
	 * 
	 * response.getWriter().write(str.toString());
	 * 
	 * 
	 * 
	 * 
	 * StringBuffer buffer = new StringBuffer();
	 * buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	 * buffer.append("<result>"); if(uid != null){ buffer.append("no"); }else{
	 * buffer.append("ok"); }
	 * 
	 * buffer.append("</result>");
	 * response.setContentType("text/xml;charset=utf-8");
	 * response.getWriter().write(buffer.toString());
	 * 
	 * }
	 */
	@ResponseBody
	@RequestMapping(value = "tcpCheck", method = RequestMethod.GET)
	public void tcpCheck(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		String ip = request.getParameter("ip");
		boolean checkResult = false;
		JSONObject jsonObj = new JSONObject();

		try {
			if (ip != null && !ip.equals("")) {
				InetAddress pingCheck;
				pingCheck = InetAddress.getByName(ip);
				checkResult = pingCheck.isReachable(1000);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (checkResult) {
			jsonObj.put("result", "SUCCESS");
		} else {
			jsonObj.put("result", "FAILURE");
		}
		response.getOutputStream().write(jsonObj.toString().getBytes("UTF-8"));
	}

} // end of class