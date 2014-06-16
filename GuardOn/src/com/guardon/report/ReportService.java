package com.guardon.report;

import java.util.ArrayList;
import java.util.Map;

import com.guardon.report.domain.ApprovalInfo;
import com.guardon.report.domain.LogInfo;

public interface ReportService {
	public ArrayList<LogInfo> getLogInfo(String today) throws Exception;
	public ArrayList<ApprovalInfo> getApprovalInfo(String yesterday) throws Exception;
	public ArrayList<ApprovalInfo> getApprovalInfoAll() throws Exception;
	public ArrayList<ApprovalInfo> getApprovalInfoList(Map<String, String> map) throws Exception;
	public ArrayList<ApprovalInfo> getApprovalInfoListByUserId(Map<String, String> map) throws Exception;
	public ArrayList<ApprovalInfo> getApprovalInfoListByConnectId(Map<String, String> map) throws Exception;
	public ArrayList<ApprovalInfo> getApprovalInfoListByServerName(Map<String, String> map) throws Exception;
	//public ArrayList<RejectInfo> getRejectInfo() throws Exception;

}
