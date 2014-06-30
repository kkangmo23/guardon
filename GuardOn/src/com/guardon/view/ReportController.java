package com.guardon.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.report.ReportService;
import com.guardon.report.domain.ApprovalInfo;
import com.guardon.report.domain.GraphInfo;
import com.guardon.report.domain.LogInfo;
import com.guardon.user.UserService;

@Controller
@RequestMapping("/*")
@SessionAttributes("report")
public class ReportController {
	@Inject
	@Named("reportService")
	ReportService reportService;
	
	@Inject
	@Named("userService")
	UserService userService;
	
	@RequestMapping("UXTest.do")
	public String UXTest(HttpServletRequest request) throws Exception {
		return "/test/UXTest2";
	}
	
	@RequestMapping("graphTest.do")
	public String graphTest(HttpServletRequest request) throws Exception {
	
		ArrayList<GraphInfo> line1 = new ArrayList<GraphInfo>();
		GraphInfo g1 = new GraphInfo();
		//GraphInfo g2 = new GraphInfo();
		
		g1.setDate("2014-05-21");
		g1.setCount(14);
		//line1.add(g1);
		line1.add(0, g1);
		
		g1.setDate("2014-05-22");
		g1.setCount(54);
		//line1.add(g1);
		line1.add(1, g1);
		
		System.out.println(line1.size());
		for (int i = 0; i < line1.size(); i++) {
			System.out.println(line1.get(i).getDate());
		}
		//System.out.println(line1.get(0).getDate());
		
		request.setAttribute("line1", line1);
		
		return "/Admin/graphTest";
	}
	
	@RequestMapping("approvalReportPretreatment.do")
	public String approvalReportPretreatment(HttpServletRequest request, HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}
		String userId = (String) session.getAttribute("userId");
		if (!(userService.getUserType(userId).equals("super") || userService.getUserType(userId).equals("admin"))) {
			request.setAttribute("errorMessage", "관리자만 접근할 수 있습니다.");
			return "/Common/errorPage";
		}
		return "/Admin/approvalReportPretreatment";
	}
	@RequestMapping("saveApprovalReport.do")
	public String saveApprovalReport(HttpServletRequest request, HttpSession session) throws Exception {	
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}
		String userId = (String) session.getAttribute("userId");
		if (!(userService.getUserType(userId).equals("super") || userService.getUserType(userId).equals("admin"))) {
			request.setAttribute("errorMessage", "관리자만 접근할 수 있습니다.");
			return "/Common/errorPage";
		}
		return "/Admin/saveApprovalReport";
	}	
	@RequestMapping("approvalReport.do")
	public String approvalReport(HttpServletRequest request, HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}
		String userId = (String) session.getAttribute("userId");
		if (!(userService.getUserType(userId).equals("super") || userService.getUserType(userId).equals("admin"))) {
			request.setAttribute("errorMessage", "관리자만 접근할 수 있습니다.");
			return "/Common/errorPage";
		}
		
		ArrayList<ApprovalInfo> approvalList = new ArrayList<ApprovalInfo>();
		Map<String, String> map = new HashMap<>();
		
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));

		switch (request.getParameter("token")) {
		case "all":
			approvalList = reportService.getApprovalInfoList(map);

			break;
		case "userId":
			map.put("keyValue", request.getParameter("keyValue"));
			approvalList = reportService.getApprovalInfoListByUserId(map);

			break;
		case "approvalId":
			map.put("keyValue", request.getParameter("keyValue"));
			approvalList = reportService.getApprovalInfoListByApprovalId(map);

			break;
		case "connectId":
			map.put("keyValue", request.getParameter("keyValue"));
			approvalList = reportService.getApprovalInfoListByConnectId(map);

			break;
		case "serverName":
			map.put("keyValue", request.getParameter("keyValue"));
			approvalList = reportService.getApprovalInfoListByServerName(map);

			break;

		default:
			break;
		}
		//request.setAttribute("approvalList", approvalList);
		session.setAttribute("approvalList", approvalList);
		
		return "/Admin/approvalReport";
	}
	
	@RequestMapping("logReportPretreatment.do")
	public String logReportPretreatment(HttpServletRequest request, HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}
		String userId = (String) session.getAttribute("userId");
		if (!(userService.getUserType(userId).equals("super") || userService.getUserType(userId).equals("admin"))) {
			request.setAttribute("errorMessage", "관리자만 접근할 수 있습니다.");
			return "/Common/errorPage";
		}
		return "/Admin/logReportPretreatment";
	}
	@RequestMapping("saveLogReport.do")
	public String saveLogReport(HttpServletRequest request, HttpSession session) throws Exception {	
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}
		String userId = (String) session.getAttribute("userId");
		if (!(userService.getUserType(userId).equals("super") || userService.getUserType(userId).equals("admin"))) {
			request.setAttribute("errorMessage", "관리자만 접근할 수 있습니다.");
			return "/Common/errorPage";
		}
		return "/Admin/saveLogReport";
	}	
	@RequestMapping("logReport.do")
	public String logReport(HttpServletRequest request, HttpSession session) throws Exception {
		if (session.getAttribute("userId") == null) {
			request.setAttribute("errorMessage", "세션이 만료되었습니다.");
			return "/Common/errorPage";
		}
		String userId = (String) session.getAttribute("userId");
		if (!(userService.getUserType(userId).equals("super") || userService.getUserType(userId).equals("admin"))) {
			request.setAttribute("errorMessage", "관리자만 접근할 수 있습니다.");
			return "/Common/errorPage";
		}
		
		ArrayList<LogInfo> logList = new ArrayList<LogInfo>();
		Map<String, String> map = new HashMap<>();
		
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));

		switch (request.getParameter("token")) {
		case "all":
			logList = reportService.getLogInfoList(map);

			break;
		case "userId":
			map.put("keyValue", request.getParameter("keyValue"));
			logList = reportService.getLogInfoListByUserId(map);
			
			break;

		default:
			break;
		}
		//request.setAttribute("approvalList", approvalList);
		session.setAttribute("logList", logList);
		
		return "/Admin/logReport";
	}
	

}
