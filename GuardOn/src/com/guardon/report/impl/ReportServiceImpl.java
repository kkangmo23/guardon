package com.guardon.report.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.guardon.report.ReportDAO;
import com.guardon.report.ReportService;
import com.guardon.report.domain.ApprovalInfo;
import com.guardon.report.domain.LogInfo;

@Service("reportService")
public class ReportServiceImpl implements ReportService{
	
	@Inject
	@Named("reportDAO")
	ReportDAO reportDAO;
	
	@Override
	public ArrayList<LogInfo> getLogInfo(String today) throws Exception{
		return reportDAO.getLogInfo(today);		
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfo(String yesterday) throws Exception{
		return reportDAO.getApprovalInfo(yesterday);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoAll() throws Exception{
		return reportDAO.getApprovalInfoAll();
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoList(Map<String, String> map) throws Exception{
		return reportDAO.getApprovalInfoList(map);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoListByUserId(Map<String, String> map) throws Exception{
		return reportDAO.getApprovalInfoListByUserId(map);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoListByConnectId(Map<String, String> map) throws Exception{
		return reportDAO.getApprovalInfoListByConnectId(map);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoListByServerName(Map<String, String> map) throws Exception{
		return reportDAO.getApprovalInfoListByServerName(map);
	}
	@Override
	public ArrayList<LogInfo> getLogInfoList(Map<String, String> map) throws Exception{
		return reportDAO.getLogInfoList(map);
	}
	@Override
	public ArrayList<LogInfo> getLogInfoListByUserId(Map<String, String> map) throws Exception{
		return reportDAO.getLogInfoListByUserId(map);
	}
	
	
	/*
	@Override
	public ArrayList<RejectInfo> getRejectInfo() throws Exception{
		return reportDAO.getRejectInfo();
	}
	*/

}
