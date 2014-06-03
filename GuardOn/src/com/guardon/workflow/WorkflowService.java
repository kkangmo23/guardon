package com.guardon.workflow;

import java.util.ArrayList;

import com.guardon.workflow.domain.Workflow;

public interface WorkflowService {
	public void insertWorkflow(Workflow workflow) throws Exception;
	
	public ArrayList<Workflow> getWorkflowList(String userId) throws Exception;


}
