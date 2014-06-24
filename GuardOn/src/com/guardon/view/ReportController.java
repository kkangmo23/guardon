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
import com.guardon.report.domain.LogInfo;

@Controller
@RequestMapping("/*")
@SessionAttributes("report")
public class ReportController {
	@Inject
	@Named("reportService")
	ReportService reportService;
	
	@RequestMapping("graphTest.do")
	public String graphTest(HttpServletRequest request) throws Exception {
		String str[] = {"aaa","bbb"};
		
		request.setAttribute("line1", str);
		
		return "/Admin/graphTest";
	}
	
	@RequestMapping("approvalReportPretreatment.do")
	public String approvalReportPretreatment(HttpServletRequest request) throws Exception {
		return "/Admin/approvalReportPretreatment";
	}
	@RequestMapping("saveApprovalReport.do")
	public String saveApprovalReport(HttpServletRequest request) throws Exception {		
		return "/Admin/saveApprovalReport";
	}	
	@RequestMapping("approvalReport.do")
	public String approvalReport(HttpServletRequest request, HttpSession session) throws Exception {
		
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
	public String logReportPretreatment(HttpServletRequest request) throws Exception {
		return "/Admin/logReportPretreatment";
	}
	@RequestMapping("saveLogReport.do")
	public String saveLogReport(HttpServletRequest request) throws Exception {		
		return "/Admin/saveLogReport";
	}	
	@RequestMapping("logReport.do")
	public String logReport(HttpServletRequest request, HttpSession session) throws Exception {
		
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
