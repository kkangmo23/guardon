package com.guardon.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.server.ServerService;
import com.guardon.user.UserService;
import com.guardon.workflow.WorkflowService;
import com.guardon.workflow.domain.Workflow;

@Controller
@RequestMapping("/*")
@SessionAttributes("workflow")
public class WorkflowController {

	@Inject
	@Named("userService")
	UserService userService;

	@Inject
	@Named("workflowService")
	WorkflowService workflowService;

	@Inject
	@Named("serverService")
	ServerService serverService;

	@RequestMapping("/insertWorkflow.do")
	// 워크플로우 페이지 작성
	public String insertWorkflow(HttpServletRequest request) throws Exception {
		String step, workflowName, workflowDesc, workflowStep = "first";
		int involveServerCount;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String updateDate = df.format(now);
		Workflow workflow = new Workflow();
		Map<String, String> map = new HashMap<>();

		step = request.getParameter("step");
		workflowName = request.getParameter("workflowName");
		workflowDesc = request.getParameter("workflowDesc");
		map.put("workflowName", workflowName);

		String serverNameList[] = request.getParameterValues("serverNameList");
		for (int i = 0; i < serverNameList.length; i++) {
			map.put("serverName", serverNameList[i]);
			serverService.setWorkflowName(map);
		}

		involveServerCount = serverNameList.length;

		workflow.setWorkflowName(workflowName);
		workflow.setWorkflowDesc(workflowDesc);
		workflow.setUpdateDate(updateDate);
		workflow.setInvolveServerCount(involveServerCount);

		System.out.println(involveServerCount);

		String[] arr1 = step.split("[|]");
		String[] arr2;

		for (int i = 0; i < arr1.length; i++) {
			arr2 = arr1[i].split(",");
			for (int j = 0; j < arr2.length; j++) {
				switch (i) {
				case 0:
					workflowStep = "first";
					break;
				case 1:
					workflowStep = "second";
					break;
				case 2:
					workflowStep = "third";
					break;
				case 3:
					workflowStep = "fourth";
					break;
				case 4:
					workflowStep = "fifth";
					break;
				default:
					break;
				}
				workflow.setUserId(arr2[j]);
				workflow.setWorkflowStep(workflowStep);

				workflowService.insertWorkflow(workflow);
			}
		}

		System.out.println("ssssssssssssss : " + step);
		System.out.println("ssssssssssssss : " + workflowName);
		System.out.println("ssssssssssssss : " + workflowDesc);
		request.setAttribute("message", "워크플로우가 성공적으로 등록되었습니다.");
		return "/Admin/adminInterPage";
	}

	@RequestMapping("workflow.do")
	public String workflow(HttpServletRequest request) throws Exception {

		int page = 1;
		request.setAttribute("serverList", serverService.getWfServerList(page));

		return "/Admin/workflowServerSelect";
	}

	@RequestMapping("/createWorkFlow.do")
	public String createWorkFlow(HttpServletRequest request) throws Exception {

		String serverName[] = request.getParameterValues("temp");
		for (int i = 0; i < serverName.length; i++) {
			System.out.println(serverName[i]);
		}
		request.setAttribute("serverNameList", serverName);

		int page = 1;
		request.setAttribute("userList", userService.getWfUserList(page));

		return "/Admin/workflow";
	}

}
