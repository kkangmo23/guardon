package com.guardon.view;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.report.ReportService;

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

}
