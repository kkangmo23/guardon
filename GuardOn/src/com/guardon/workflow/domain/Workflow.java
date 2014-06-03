package com.guardon.workflow.domain;

public class Workflow {

	private String workflowName;
	private String workflowDesc;
	private String workflowStep;
	private String userId;
	private String updateDate;
	private int involveServerCount;
	
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public String getWorkflowDesc() {
		return workflowDesc;
	}
	public void setWorkflowDesc(String workflowDesc) {
		this.workflowDesc = workflowDesc;
	}
	public String getWorkflowStep() {
		return workflowStep;
	}
	public void setWorkflowStep(String workflowStep) {
		this.workflowStep = workflowStep;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getInvolveServerCount() {
		return involveServerCount;
	}
	public void setInvolveServerCount(int involveServerCount) {
		this.involveServerCount = involveServerCount;
	}
	
	
	
}
