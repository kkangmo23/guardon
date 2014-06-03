package com.guardon.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.connectId.ConnectIdService;
import com.guardon.connectId.domain.ConnectId;
import com.guardon.server.ServerService;
import com.guardon.server.domain.Server;

@Controller
@RequestMapping("/*")
@SessionAttributes("connectId")
public class ConnectIdController {

	@Inject
	@Named("connectIdService")
	ConnectIdService connectIdService;

	@Inject
	@Named("serverService")
	ServerService serverService;

	@RequestMapping("deleteConnectId.do")
	public String deleteConnectId(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String[] temp = request.getParameterValues("checkList");
		String[] temp2 = null;

		for (int i = 0; i < temp.length; i++) {
			temp2 = temp[i].split(",");
			// temp2[0]=temp2[0].replaceAll(" ", "");
			System.out.println("띄어쓰기 확인" + temp2[0]);
			System.out.println("띄어쓰기 확인" + temp2[1]);
			map.put("connectId", temp2[0]);
			map.put("serverName", temp2[1]);

			connectIdService.deleteConnectId(map);
			map.clear();
		}

		request.setAttribute("message", "성공적으로 접속아이디를 삭제하였습니다.");
		return "serverServicePage";
	}

	@RequestMapping("connectIdList.do")
	public String connectIdList(HttpServletRequest request) throws Exception {
		ArrayList<ConnectId> connectIdList = new ArrayList<ConnectId>();
		connectIdList = connectIdService.getConnectIdList();

		request.setAttribute("connectIdList", connectIdList);

		return "deleteConnectId";
	}

	@RequestMapping("connectIdSelect.do")
	public String connectIdSelect(HttpServletRequest request) throws Exception {

		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		ArrayList<Server> serverList = serverService.getServerList(Integer
				.parseInt(pageParam));
		request.setAttribute("serverList", serverList);

		return "insertConnectId";
	}

	@RequestMapping("/insertConnectId.do")
	public String insertConnectId(HttpServletRequest request) throws Exception {
		String connectId, serverName, connectIdDesc;
		ConnectId conn = new ConnectId();
		Map<String, String> map = new HashMap<String, String>();

		connectId = request.getParameter("connectId");
		serverName = request.getParameter("checkList");
		connectIdDesc = request.getParameter("connectIdDesc");

		map.put("connectId", connectId);
		map.put("serverName", serverName);

		conn.setConnectId(connectId);
		conn.setServerName(serverName);
		conn.setConnectIdDesc(connectIdDesc);

		if (connectIdService.countConnectId(map) > 0) {
			request.setAttribute("message", "해당 서버에 중복된 접속 아이디가 있습니다.");
			return "serverServicePage";
		} else {
			connectIdService.insertConnectId(conn);
			request.setAttribute("message", "성공적으로 접속 아이디를 등록하였습니다.");
			return "serverServicePage";
		}
	}

}
