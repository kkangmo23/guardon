package com.guardon.report.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Repository;

import com.guardon.report.ReportDAO;
import com.guardon.report.domain.ApprovalInfo;
import com.guardon.report.domain.LogInfo;
import com.guardon.report.domain.RejectInfo;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("reportDAO")
public class ReportDAOImpl implements ReportDAO{
	@Inject
	@Named("sqlMapClient")
	SqlMapClient sqlMapClient;

	@Override
	public ArrayList<LogInfo> getLogInfo(String today) throws Exception {
		return (ArrayList<LogInfo>)sqlMapClient.queryForList("Report.getLogInfo", today);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfo(String yesterday) throws Exception {
		return (ArrayList<ApprovalInfo>)sqlMapClient.queryForList("Report.getApprovalInfo", yesterday);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoAll() throws Exception {
		return (ArrayList<ApprovalInfo>)sqlMapClient.queryForList("Report.getApprovalInfoAll");
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoList(Map<String, String> map) throws Exception{
		return (ArrayList<ApprovalInfo>)sqlMapClient.queryForList("Report.getApprovalInfoList", map);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoListByUserId(Map<String, String> map) throws Exception{
		return (ArrayList<ApprovalInfo>)sqlMapClient.queryForList("Report.getApprovalInfoListByUserId", map);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoListByConnectId(Map<String, String> map) throws Exception{
		return (ArrayList<ApprovalInfo>)sqlMapClient.queryForList("Report.getApprovalInfoListByConnectId", map);
	}
	@Override
	public ArrayList<ApprovalInfo> getApprovalInfoListByServerName(Map<String, String> map) throws Exception{
		return (ArrayList<ApprovalInfo>)sqlMapClient.queryForList("Report.getApprovalInfoListByServerName", map);
	}
	@Override
	public ArrayList<LogInfo> getLogInfoList(Map<String, String> map) throws Exception{
		return (ArrayList<LogInfo>)sqlMapClient.queryForList("Report.getLogInfoList", map);
	}
	@Override
	public ArrayList<LogInfo> getLogInfoListByUserId(Map<String, String> map) throws Exception{
		return (ArrayList<LogInfo>)sqlMapClient.queryForList("Report.getLogInfoListByUserId", map);
	}
	
	/*
	@Override
	public ArrayList<RejectInfo> getRejectInfo() throws Exception {
		return (ArrayList<RejectInfo>)sqlMapClient.queryForList("Report.getRejectInfo", null);
	}
	*/
	
}
