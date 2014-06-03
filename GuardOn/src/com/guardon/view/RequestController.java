package com.guardon.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.connectId.ConnectIdService;
import com.guardon.connectId.domain.ConnectId;
import com.guardon.option.OptionService;
import com.guardon.request.RequestService;
import com.guardon.request.domain.History;
import com.guardon.request.domain.Request;
import com.guardon.server.ServerService;
import com.guardon.server.domain.Server;
import com.guardon.user.UserService;
import com.guardon.user.domain.User;

@Controller
@RequestMapping("/*")
@SessionAttributes("request")
public class RequestController {

	@Inject
	@Named("userService")
	UserService userService;

	@Inject
	@Named("requestService")
	RequestService requestService;

	@Inject
	@Named("serverService")
	ServerService serverService;

	@Inject
	@Named("optionService")
	OptionService optionService;

	@Inject
	@Named("connectIdService")
	ConnectIdService connectIdService;

	public static int randomRange(int n1, int n2) {
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}

	@Async
	public String firstOtp() throws Exception {

		String pwdComplexity = optionService.getPwdComplexity();
		int pwdLength = optionService.getPwdLength();

		if (pwdComplexity.equals("Low")) {
			String otp;
			otp = UUID.randomUUID().toString().replace("-", "")
					.substring(0, pwdLength);
			char[] array = otp.toCharArray();
			array[randomRange(1, otp.length() - 1)] = (char) randomRange(33, 47);
			otp = "";
			for (int i = 0; i < array.length; i++) {
				otp += array[i];
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
		} else {
			String otp = "";
			for (int i = 0; i < pwdLength; i++) {
				String buf;
				buf = "" + (char) ((Math.random() * 94) + 33);
				otp = otp + buf;
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

	public boolean checkConnectId(String serverName, String connectId)
			throws Exception {

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

				con = DriverManager
						.getConnection("jdbc:oracle:thin:@" + ipAddress + ":"
								+ port + ":orcl", serverId, serverPwd);
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("select count(*) from dba_users where username=upper('"
								+ connectId + "')");
				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if (str.equals("0")) {
					System.out.println("there is no such Id");
					return false;
				}

				rs.close();
				stmt.close();
				con.close();

				break;
			case "mssql":

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

				con = DriverManager.getConnection("jdbc:sqlserver://"
						+ ipAddress + ":" + port, serverId, serverPwd);
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("select count(*) from syslogins where name='"
								+ connectId + "'");
				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if (str.equals("0")) {
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
				rs = stmt.executeQuery("select count(*) from user where user='"
						+ connectId + "'");
				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if (str.equals("0")) {
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
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
					System.out.println("rs closed!!");
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
					System.out.println("stmt closed!!");
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
					System.out.println("con closed!!");
				} catch (Exception e) {
				}
		}
		return true;
	}

	public String makeOtpAll(String serverName, String connectId)
			throws Exception {

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

		try {

			switch (connectType) {

			case "oracle":

				Class.forName("oracle.jdbc.driver.OracleDriver");

				con = DriverManager
						.getConnection("jdbc:oracle:thin:@" + ipAddress + ":"
								+ port + ":orcl", serverId, serverPwd);

				query = "alter user " + connectId + " identified by \""
						+ userOtp + "\"";

				stmt = (Statement) con.createStatement();
				rs = stmt
						.executeQuery("select count(*) from dba_users where username=upper('"
								+ connectId + "')");
				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if (str.equals("0")) {
					System.out.println("there is no such Id");
					// request.setAttribute("errorMessage",
					// "서버에 해당 아이디가 존재하지 않습니다.");
				} else {
					stmt.executeUpdate(query);
				}

				rs.close();
				stmt.close();
				con.close();

				break;

			case "mssql":

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

				query = "SP_PASSWORD NULL," + "'" + userOtp + "', '"
						+ connectId + "'";

				con = DriverManager.getConnection("jdbc:sqlserver://"
						+ ipAddress + ":" + port, serverId, serverPwd);

				stmt = con.createStatement();

				rs = stmt
						.executeQuery("select count(*) from syslogins where name='"
								+ connectId + "'");

				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if (str.equals("0")) {
					System.out.println("there is no such Id");
					// request.setAttribute("errorMessage",
					// "서버에 해당 아이디가 존재하지 않습니다.");
				} else {
					stmt.executeUpdate(query);
				}

				rs.close();
				stmt.close();
				con.close();

				break;

			case "mysql":

				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://" + ipAddress
						+ "/mysql", serverId, serverPwd);

				query = "update user set password=password('" + userOtp
						+ "') where user='" + connectId + "';";

				stmt = (Statement) con.createStatement();
				rs = stmt.executeQuery("select count(*) from user where user='"
						+ connectId + "'");
				rs.next();
				str = rs.getString(1);
				System.out.println(str);

				if (str.equals("0")) {
					System.out.println("there is no such Id");
					// request.setAttribute("errorMessage",
					// "서버에 해당 아이디가 존재하지 않습니다.");
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
				serverOS = serverService.getServerOS(serverName);
				if (serverOS.equals("windows")) {

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
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				try {
					rs.close();
					System.out.println("rs closed!!");
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
					System.out.println("stmt closed!!");
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
					System.out.println("con closed!!");
				} catch (Exception e) {
				}
			if (telnet.isConnected())
				try {
					telnet.disconnect();
					System.out.println("telnet disconnect!!");
				} catch (Exception e) {
				}
		}
		return userOtp;
	}

	@RequestMapping("outUserHistory.do")
	public String outUserHistory(HttpServletRequest request, HttpSession session)
			throws Exception {

		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String userId;
		ArrayList<History> expiredHistories = new ArrayList<History>();
		ArrayList<History> approvedHistories = new ArrayList<History>();
		ArrayList<History> uncheckedHistories = new ArrayList<History>();
		userId = session.getAttribute("userId").toString();

		approvedHistories = requestService.getApprovedUserHistory(userId);
		request.setAttribute("approvedHistories", approvedHistories);
		System.out.println("승인된 요청이력");
		uncheckedHistories = requestService.getUncheckedUserHistory(userId);
		request.setAttribute("uncheckedHistories", uncheckedHistories);
		System.out.println("대기중인 요청이력");
		System.out.println(uncheckedHistories.size());
		/*
		 * for (int i = 0; i < uncheckedHistories.size(); i++) {
		 * 
		 * System.out.println(uncheckedHistories.size());
		 * System.out.println(uncheckedHistories.get(i).getApprovalId());
		 * System.out.println(uncheckedHistories.get(i).getUserName());
		 * System.out.println(uncheckedHistories.get(i).getUserLevel()); }
		 */

		return "outUserHistory";
	}

	@RequestMapping("outSendRequest.do")
	public String outSendRequest(HttpServletRequest request, HttpSession session)
			throws Exception {
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
		userId = (String) session.getAttribute("userId");
		requestDesc = request.getParameter("requestDesc");
		pwdType = "OTP";

		map.put("serverName", serverName);
		map.put("connectId", connectId);
		map.put("userId", userId);

		if (connectIdService.countConnectId(map) <= 0) {
			System.out.println("접속아이디 체크 : "
					+ connectIdService.countConnectId(map));
			request.setAttribute("errorMessage", "서버에 해당 접속아이디가 존재하지 않습니다.");
			return "errorPage";
		}
		if (requestService.countOtpApproved(map) > 0) {
			approvedTemp = requestService.getOtpApproved(map);
		} else {
			approvedTemp = "";
		}
		/*
		 * try { approvedTemp = requestService.getOtpApproved(map); } catch
		 * (Exception e) { e.printStackTrace(); approvedTemp = ""; }
		 */
		try {
			if (serverService.getWorkflowName(serverName).equals("none"))
				approved = "unchecked";
			else
				approved = "first";

			if (connectId.equals("administrator")
					|| connectId.equals("Administrator")
					|| connectId.equals(serverService.getServerId(serverName))) {
				request.setAttribute("errorMessage", "해당 아이디는 사용이 불가능합니다.");
				return "errorPage";
			}

			/*
			 * if (!requestService.checkDuplReq(map).equals("0")) {
			 * request.setAttribute("errorMessage",
			 * "해당 서버와 아이디에 대해 이미 발급 된 비밀번호가 있거나 처리중인 요청이 있습니다."); return
			 * "errorPage"; }
			 */

			if (approvedTemp.equals("unchecked")) {
				request.setAttribute("errorMessage",
						"이미 진행중인 요청이 있습니다. 관리자가 처리할 때까지 기다려주십시오.");
				return "errorPage";
			} else if (approvedTemp.equals("approved")) {
				request.setAttribute("errorMessage", "이미 승인 된 요청입니다.");
				return "errorPage";
			}

			if (!checkConnectId(serverName, connectId)) {
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
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "요청을 처리할 수 없습니다.");
			return "errorPage";
		}

		return "outUserOtpRequestDone";
	}

	@RequestMapping("outPeriodPwdList.do")
	public String outPeriodPwdList(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
		String userId = session.getAttribute("userId").toString();

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
	public String periodPwdList(HttpServletRequest request, HttpSession session)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
		String userId = session.getAttribute("userId").toString();

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
	public String outPeriodPwdApproval(HttpServletRequest request,
			HttpSession session) {
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
		String serverName = request.getParameter("checkList");
		String connectId = request.getParameter("connectId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String pwdType = "period";
		String userId = session.getAttribute("userId").toString();
		String requestDesc = request.getParameter("requestDesc");
		try {

			map.put("serverName", serverName);
			map.put("connectId", connectId);

			if (connectId.equals("administrator")
					|| connectId.equals("Administrator")
					|| connectId.equals(serverService.getServerId(serverName))) {
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
	public String periodPwdApproval(HttpServletRequest request,
			HttpSession session) {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
		Request rq = new Request();
		Map<String, String> map = new HashMap<>();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String requestDate = df.format(now);
		String serverName = request.getParameter("checkList");
		String connectId = request.getParameter("connectId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String pwdType = "period";
		String userId = session.getAttribute("userId").toString();
		String requestDesc = request.getParameter("requestDesc");
		String approved;

		try {
			map.put("serverName", serverName);
			map.put("connectId", connectId);

			if (connectId.equals("administrator")
					|| connectId.equals("Administrator")
					|| connectId.equals(serverService.getServerId(serverName))) {
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
		return "userOtpRequestDone";
	}

	@RequestMapping("outPeriodPwdServerSelect.do")
	public String outPeriodServerList(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		ArrayList<Server> serverList = serverService
				.getPeriodServerList(Integer.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();

		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "outPeriodPwdServerSelect";
	}

	@RequestMapping("periodPwdServerSelect.do")
	public String periodServerList(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		ArrayList<Server> serverList = serverService
				.getPeriodServerList(Integer.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();

		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));
		request.setAttribute("message", "1234asd");

		return "periodPwdServerSelect";
	}

	@RequestMapping("changeAllList.do")
	public String changeAllList(HttpServletRequest request, HttpSession session)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
		ArrayList<ConnectId> changeAllList = new ArrayList<ConnectId>();
		changeAllList = connectIdService.getConnectIdList();

		request.setCharacterEncoding("utf-8");
		request.setAttribute("changeAllList", changeAllList);

		return "changeAllList";
	}

	@RequestMapping("changeAllPwd.do")
	public String changeAllPwd(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String temp[] = request.getParameterValues("temp");
		/*
		 * try {
		 * 
		 * for (int i = 0; i < temp.length; i++) { temp[i] = new
		 * String(temp[i].getBytes("ISO-8859-1"), "UTF-8");
		 * System.out.println("변경 후 : "+temp[i]); } System.out.println("변경전 :"+
		 * temp[0]); temp[0] = new String(temp[0].getBytes("ISO-8859-1"),"UTF-8"
		 * ); System.out.println("변경 후 "+ temp[0]);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		String arr[];
		HashMap<String, String> map = new HashMap<>();

		int successCount = 0;
		int totalCount = 0;

		for (int i = 0; i < temp.length; i++) {
			arr = temp[i].split(",");
			System.out.println("서버명 : " + arr[0]);
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

		totalCount = temp.length;

		request.setAttribute("totalCount", totalCount);
		request.setAttribute("successCount", successCount);

		return "changeAllPwdResult";

	}

	@RequestMapping("/userOtp.do")
	public String makeOtp(HttpServletRequest request, HttpSession session) {
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
		// String dbName;
		String serverOS;
		String connectType;
		String port;
		String userType;
		String userId;
		String str;
		String query;

		if (request.getParameter("connectId") == null) {
			connectId = session.getAttribute("connectId").toString();
			System.out.println("session connectId : " + connectId);
		} else {
			session.setAttribute("connectId", request.getParameter("connectId"));
			connectId = request.getParameter("connectId");
		}
		if (request.getParameter("checkList") == null) {
			serverName = session.getAttribute("serverName").toString();
			System.out.println("session serverName : " + serverName);
		} else {
			session.setAttribute("serverName",
					request.getParameter("checkList"));
			serverName = request.getParameter("checkList");
		}

		try {

			int otpTimeLimit = optionService.getOtpTimeLimit();
			String pwdComplexity = optionService.getPwdComplexity();
			int pwdLength = optionService.getPwdLength();

			userId = session.getAttribute("userId").toString();
			userType = userService.getUserType(userId);

			map.put("serverName", serverName);
			map.put("connectId", connectId);
			map.put("userId", userId);

			System.out.println("serverName : " + serverName);
			System.out.println("connectId : " + connectId);
			System.out.println("userId : " + userId);
			// System.out.println(requestService.getOtpApproved(map));
			System.out.println("userType : " + userType);
			// System.out.println(requestService.getOtpApproved(map).toString());

			if ((userType.equals("outUser") && requestService.getOtpApproved(
					map).equals("approved"))
					|| userType.equals("user")) {

				userOtp = firstOtp();
				System.out.println(userOtp);

				// dbName = serverService.getDbName(serverName);
				// serverName = request.getParameter("checkList");
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

					con = DriverManager.getConnection("jdbc:oracle:thin:@"
							+ ipAddress + ":" + port + ":orcl", serverId,
							serverPwd);

					query = "alter user " + connectId + " identified by \""
							+ userOtp + "\"";

					stmt = (Statement) con.createStatement();
					rs = stmt
							.executeQuery("select count(*) from dba_users where username=upper('"
									+ connectId + "')");
					rs.next();
					str = rs.getString(1);
					System.out.println(str);

					if (str.equals("0")) {
						System.out.println("there is no such Id");
						request.setAttribute("errorMessage",
								"서버에 해당 아이디가 존재하지 않습니다.");
						return "errorPage";
					} else {
						stmt.executeUpdate(query);
					}

					timer.schedule(makeOTP, otpTimeLimit * 1000);

					rs.close();
					stmt.close();
					con.close();

					if (userType.equals("user"))
						return "userOtp";
					else if (userType.equals("outUser"))
						return "outUserOtp";

				case "mssql":

					request.setAttribute("userOtp", userOtp);

					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

					query = "SP_PASSWORD NULL," + "'" + userOtp + "', '"
							+ connectId + "'";

					con = DriverManager.getConnection("jdbc:sqlserver://"
							+ ipAddress + ":" + port, serverId, serverPwd);

					stmt = con.createStatement();

					rs = stmt
							.executeQuery("select count(*) from syslogins where name='"
									+ connectId + "'");

					rs.next();
					str = rs.getString(1);
					System.out.println(str);

					if (str.equals("0")) {
						System.out.println("there is no such Id");
						request.setAttribute("errorMessage",
								"서버에 해당 아이디가 존재하지 않습니다.");
						return "errorPage";
					} else {
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

					timer.schedule(makeOTP, otpTimeLimit * 1000);

					rs.close();
					stmt.close();
					con.close();

					if (userType.equals("user"))
						return "userOtp";
					else if (userType.equals("outUser"))
						return "outUserOtp";

				case "mysql":

					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://"
							+ ipAddress + "/mysql", serverId, serverPwd);

					query = "update user set password=password('" + userOtp
							+ "') where user='" + connectId + "';";

					stmt = (Statement) con.createStatement();
					rs = stmt
							.executeQuery("select count(*) from user where user='"
									+ connectId + "'");
					rs.next();
					str = rs.getString(1);
					System.out.println(str);

					if (str.equals("0")) {
						System.out.println("there is no such Id");
						request.setAttribute("errorMessage",
								"서버에 해당 아이디가 존재하지 않습니다.");
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

					timer.schedule(makeOTP, otpTimeLimit * 1000);

					rs.close();
					stmt.close();
					con.close();

					if (userType.equals("user"))
						return "userOtp";
					else if (userType.equals("outUser"))
						return "outUserOtp";

				case "telnet":
					serverOS = serverService.getServerOS(serverName);
					if (serverOS.equals("windows")) {

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

						timer.schedule(makeOTP, otpTimeLimit * 1000);

						System.out.println(userOtp);

						if (userType.equals("user"))
							return "userOtp";
						else if (userType.equals("outUser"))
							return "outUserOtp";
					}

				default:
					break;
				}
			} else {
				if (userType.equals("user"))
					return "errorUserAuthority";
				if (userType.equals("outUser"))
					return "errorOutAuthority";
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "OTP를 발급받을 수 없습니다.");
			return "errorPage";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "서버에 접속할 수 없습니다.");
			return "errorPage";
		} finally {
			if (rs != null)
				try {
					rs.close();
					System.out.println("rs closed!!");
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
					System.out.println("stmt closed!!");
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
					System.out.println("con closed!!");
				} catch (Exception e) {
				}
			if (telnet.isConnected())
				try {
					telnet.disconnect();
					System.out.println("telnet disconnect!!");
				} catch (Exception e) {
				}
		}
		request.setAttribute("errorMessage", "OTP발급에 실패하였습니다.");
		return "errorPage";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/updateRejected.do")
	public String updateRejected(HttpSession session, HttpServletRequest request)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		Map<String, String> map = new HashMap<>();
		String[] array = request.getParameterValues("temp");
		String[] array2;
		int approvalSuccessCount = 0;

		for (int i = 0; i < array.length; i++) {
			array[i] = new String(array[i].getBytes("ISO-8859-1"), "UTF-8");
		}

		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();

		String approvalId = (String) session.getAttribute("userId");
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

	@RequestMapping(method = RequestMethod.GET, value = "/updateApproved.do")
	public String updateApproved(HttpSession session, HttpServletRequest request)
			throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}
		int approvalSuccessCount = 0;
		int approvalCount = 0;
		String userOtp = null, serverName, connectId, approved;
		Map<String, String> map = new HashMap<>();
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();

		String[] array = request.getParameterValues("temp");
		String[] array2;
		// String[] failRequest=null;

		for (int i = 0; i < array.length; i++) {
			array[i] = new String(array[i].getBytes("ISO-8859-1"), "UTF-8");
		}

		String approvalId = (String) session.getAttribute("userId");
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
				approved = "approved";
				serverName = map.get("serverName");
				connectId = map.get("connectId");

				try {
					userOtp = makeOtpAll(serverName, connectId);
					map.put("password", userOtp);
					map.put("approved", approved);
					System.out.println("check : " + userOtp);

					requestService.updatePassword(map);
					requestService.updateApproved(map);

					approvalSuccessCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
				userOtp = null;
				array2 = null;
				map.clear();
				break;

			case "OTP":
				approved = "approved";
				map.put("approved", approved);
				requestService.updateApproved(map);
				map.clear();
				approvalSuccessCount++;
				break;

			case "first":
				approved = "second";
				map.put("approved", approved);
				requestService.updateApproved(map);
				approvalSuccessCount++;
				break;
			case "second":
				approved = "third";
				map.put("approved", approved);
				requestService.updateApproved(map);
				approvalSuccessCount++;
				break;
			case "third":
				approved = "fourth";
				map.put("approved", approved);
				requestService.updateApproved(map);
				approvalSuccessCount++;
				break;
			case "fourth":
				approved = "fifth";
				map.put("approved", approved);
				requestService.updateApproved(map);
				approvalSuccessCount++;
				break;
			case "fifth":

				try {
					if (array2[3].equals("period")) {
						serverName = map.get("serverName");
						connectId = map.get("connectId");
						userOtp = makeOtpAll(serverName, connectId);
						map.put("password", userOtp);

						System.out.println("check : " + userOtp);

						requestService.updatePassword(map);
						requestService.updateApproved(map);

						userOtp = null;

					} else if (array2[3].equals("OTP")) {
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
			 * if(array2[3].equals("period")) {
			 * serverName=map.get("serverName"); connectId=map.get("connectId");
			 * userOtp=makeOtpAll(serverName, connectId); map.put("password",
			 * userOtp);
			 * 
			 * System.out.println("check : "+ userOtp);
			 * 
			 * requestService.updatePassword(map);
			 * requestService.updateApproved(map);
			 * 
			 * userOtp=null;
			 * 
			 * } else if (array2[3].equals("OTP")) {
			 * requestService.updateApproved(map); map.clear(); }
			 */
		}

		approvalCount = array.length;

		request.setAttribute("approvalSuccessCount", approvalSuccessCount);
		request.setAttribute("approvalCount", approvalCount);

		// request.setAttribute("failRequest", failRequest);

		return "approvalResult";
	}

	@RequestMapping("/outUserOtpPretreatment.do")
	public String outUserOtpPretreatment(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}

		ArrayList<Server> serverList = serverService.getServerList(Integer
				.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();

		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "outUserOtpPretreatment";
	}

	@RequestMapping("/userOtpPretreatment.do")
	public String userOtpPretreatment(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}

		ArrayList<Server> serverList = serverService.getServerList(Integer
				.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();

		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "userOtpPretreatment";
	}

	@RequestMapping("/approvalUserList.do")
	public String approvalUserList(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		ArrayList<Request> requestList = new ArrayList<Request>();
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		requestList = requestService.getApprovedList(page);
		request.setAttribute("requestList", requestList);
		return "approvalUserList";
	}

	@RequestMapping("outUserOtpRequest.do")
	public String outUserOtpRequest(HttpServletRequest request,
			HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "errorPage";
		}

		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		ArrayList<Server> serverList = serverService.getServerList(Integer
				.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();

		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "outUserOtpRequest";
	}

}
