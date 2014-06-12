package com.guardon.view;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.report.ReportService;
import com.guardon.report.domain.ApprovalInfo;

@Controller
@RequestMapping("/*")
@SessionAttributes("report")
public class ReportController {
	@Inject
	@Named("reportService")
	ReportService reportService;
	
	@RequestMapping("approvalReport.do")
	public String approvalReport(HttpServletRequest request) throws Exception {
		return "/Admin/approvalReport";
	}
	
	@RequestMapping("excelTest.do")
	public String excelTest(HttpServletRequest request) throws Exception {
		ArrayList<ApprovalInfo> ai = reportService.getApprovalInfoAll();
		request.setAttribute("approvalInfo", ai);
		return "/Admin/excelTest_excel";
	}

}
