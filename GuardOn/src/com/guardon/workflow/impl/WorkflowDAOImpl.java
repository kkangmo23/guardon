package com.guardon.workflow.impl;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Repository;

import com.guardon.workflow.WorkflowDAO;
import com.guardon.workflow.domain.Workflow;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("workflowDAO")
public class WorkflowDAOImpl implements WorkflowDAO {

	@Inject
	@Named("sqlMapClient")
	SqlMapClient sqlMapClient;
	
	@Override
	public void insertWorkflow(Workflow workflow) throws Exception {
		sqlMapClient.insert("Workflow.insertWorkflow", workflow);
	}
	
	@Override
	public ArrayList<Workflow> getWorkflowList(String userId) throws Exception {
		return (ArrayList<Workflow>)sqlMapClient.queryForList("Workflow.getWorkflowList", userId);
	}

}
