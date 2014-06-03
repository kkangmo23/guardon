package com.guardon.view;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.guardon.server.ServerService;
import com.guardon.server.domain.Server;
import com.guardon.user.domain.User;

@Controller
@RequestMapping("/*")
@SessionAttributes("server")
public class ServerController {

	@Inject
	@Named("serverService")
	ServerService serverService;

	@RequestMapping("/dbList.do")
	public String dbList(HttpServletRequest request) throws Exception {
		String pageParam = request.getParameter("page");
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		ArrayList<Server> serverList = serverService.getServerList(Integer
				.parseInt(pageParam));
		int serverCount = serverService.getServerListCount();

		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "/Server/dbList";
	}

	@RequestMapping("/serverHome.do")
	public String serverHome(HttpServletRequest request) throws Exception {

		return "/Server/serverHome";
	}

	@RequestMapping("/dbInstall.do")
	public String dbInstall(HttpServletRequest request) throws Exception {

		return "/Server/dbInstall";
	}

	@RequestMapping("/serverInstall.do")
	public String serverInstall(HttpServletRequest request) throws Exception {

		return "/Server/serverInstall";
	}

	@RequestMapping("/updateServerSelect.do")
	public String updateServerSelect(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		ArrayList<Server> serverList = new ArrayList<Server>();
		int serverCount;
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		try {
			serverList = serverService.getServerList(Integer
					.parseInt(pageParam));
			serverCount = serverService.getServerListCount();
		} catch (Exception e) {
			request.setAttribute("message", "서버 정보를 불러오는데 실패하였습니다.");
			return "/Server/serverServicePage";
		}
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "/Server/updateServerSelect";
	}

	@RequestMapping("/updateServerPretreatment.do")
	public String updateServerPretreatment(HttpServletRequest request,
			HttpSession session) throws Exception {
		String serverName;
		Server server = new Server();

		serverName = request.getParameter("temp");
		System.out.println(serverName);
		server = serverService.getServerInfo(serverName);

		if (server.getServerType().equals("db")) {
			request.setAttribute("serverName", serverName);
			request.setAttribute("ipAddress", server.getIpAddress());
			request.setAttribute("port", server.getPort());
			request.setAttribute("serverDesc", server.getServerDesc());
			request.setAttribute("serverId", server.getServerId());
			request.setAttribute("serverPwd", server.getServerPwd());
			request.setAttribute("dbName", server.getDbName());
			request.setAttribute("connectType", server.getConnectType());
			request.setAttribute("connectIdDupl", server.isConnectIdDupl());
			return "/Server/updateDB";
		} else if (server.getServerType().equals("server")) {
			request.setAttribute("serverName", serverName);
			request.setAttribute("ipAddress", server.getIpAddress());
			request.setAttribute("port", server.getPort());
			request.setAttribute("serverDesc", server.getServerDesc());
			request.setAttribute("serverId", server.getServerId());
			request.setAttribute("serverPwd", server.getServerPwd());
			request.setAttribute("serverOS", server.getServerOS());
			request.setAttribute("connectType", server.getConnectType());
			request.setAttribute("connectIdDupl", server.isConnectIdDupl());
			return "/Server/updateServer";
		}
		request.setAttribute("message", "서버 정보를 불러오는데 실패하였습니다.");
		return "/Server/serverServicePage";

	}

	@RequestMapping("/updateDB")
	public String updateDB(HttpServletRequest request, HttpSession session) {

		Server server = new Server();

		server.setServerName(request.getParameter("serverName"));
		server.setIpAddress(request.getParameter("ipAddress"));
		server.setPort(request.getParameter("port"));
		server.setServerDesc(request.getParameter("serverDesc"));
		server.setServerId(request.getParameter("serverId"));
		server.setServerPwd(request.getParameter("serverPwd"));
		server.setDbName(request.getParameter("dbName"));
		server.setConnectType(request.getParameter("connectType"));
		if (request.getParameter("connectIdDupl").equals("true"))
			server.setConnectIdDupl(true);
		else
			server.setConnectIdDupl(false);

		try {
			serverService.updateDB(server);
		} catch (Exception e) {
			request.setAttribute("message", "서버 정보 수정에 실패하였습니다.");
			return "/Server/serverServicePage";
		}

		request.setAttribute("message", "성공적으로 서버 정보를 수정하였습니다.");
		return "/Server/serverServicePage";
	}

	@RequestMapping("/updateServer")
	public String updateServer(HttpServletRequest request, HttpSession session) {

		Server server = new Server();

		server.setServerName(request.getParameter("serverName"));
		server.setIpAddress(request.getParameter("ipAddress"));
		server.setPort(request.getParameter("port"));
		server.setServerDesc(request.getParameter("serverDesc"));
		server.setServerId(request.getParameter("serverId"));
		server.setServerPwd(request.getParameter("serverPwd"));
		server.setServerOS(request.getParameter("serverOS"));
		server.setConnectType(request.getParameter("connectType"));
		if (request.getParameter("connectIdDupl").equals("true"))
			server.setConnectIdDupl(true);
		else
			server.setConnectIdDupl(false);

		try {
			serverService.updateDB(server);
		} catch (Exception e) {
			request.setAttribute("message", "서버 정보 수정에 실패하였습니다.");
			return "/Server/serverServicePage";
		}

		request.setAttribute("message", "성공적으로 서버 정보를 수정하였습니다.");
		return "/Server/serverServicePage";
	}

	@RequestMapping("/deleteServerSelect.do")
	public String deleteServerSelect(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		ArrayList<Server> serverList = new ArrayList<Server>();
		int serverCount;
		if (pageParam == null || pageParam.equals("")) {
			pageParam = "1";
		}
		try {
			serverList = serverService.getServerList(Integer
					.parseInt(pageParam));
			serverCount = serverService.getServerListCount();
		} catch (Exception e) {
			request.setAttribute("message", "서버 정보를 불러오는데 실패하였습니다.");
			return "/Server/serverServicePage";
		}
		request.setAttribute("serverListCnt", serverCount);
		request.setAttribute("serverList", serverList);
		request.setAttribute("page", Integer.parseInt(pageParam));

		return "/Server/deleteServerSelect";
	}

	@RequestMapping("/deleteServer.do")
	public String deleteServer(HttpServletRequest request) {

		String[] deleteServerName = request.getParameterValues("temp");
		for (int i = 0; i < deleteServerName.length; i++) {
			try {
				serverService.deleteServer(deleteServerName[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("message", "성공적으로 서버를 삭제하였습니다.");
		return "/Server/serverServicePage";
	}

	@ResponseBody
	@RequestMapping(value = "/checkServerName.do", method = RequestMethod.POST)
	public void checkServerName(String serverName, HttpServletResponse response)
			throws Exception {

		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<result>");
		// if("0".equals(serverService.countServerName(serverName))){
		if (serverService.countServerName(serverName) == 0) {
			buffer.append("사용가능");
		} else {
			buffer.append("사용불가");
		}

		buffer.append("</result>");
		response.setContentType("text/xml;charset=utf-8");
		response.getWriter().write(buffer.toString());
	}

	@RequestMapping("/insertServer.do")
	public String insertServer(HttpServletRequest request, HttpSession session)
			throws Exception {
		String serverName, ipAddress, serverDesc, serverId, serverPwd, connectType, serverOS, port;
		boolean connectIdDupl = false;
		Server server = new Server();
		serverName = request.getParameter("serverName");
		ipAddress = request.getParameter("ipAddress");
		port = request.getParameter("port");
		serverDesc = request.getParameter("serverDesc");
		serverId = request.getParameter("serverId");
		serverPwd = request.getParameter("serverPwd");
		connectType = request.getParameter("connectType");
		serverOS = request.getParameter("serverOS");
		if (request.getParameter("connectIdDupl").equals("true"))
			connectIdDupl = true;
		else if (request.getParameter("connectIdDupl").equals("false"))
			connectIdDupl = false;

		server.setServerName(serverName);
		server.setIpAddress(ipAddress);
		server.setServerDesc(serverDesc);
		server.setServerId(serverId);
		server.setServerPwd(serverPwd);
		server.setConnectType(connectType);
		server.setServerOS(serverOS);
		server.setPort(port);
		server.setConnectIdDupl(connectIdDupl);

		serverService.insertServer(server);

		request.setAttribute("message", "성공적으로 서버를 등록하였습니다.");
		return "/Server/serverServicePage";

	}

	@RequestMapping("/insertDBServer.do")
	public String insertDBServer(HttpServletRequest request) throws Exception {

		String serverName, ipAddress, serverDesc, serverId, serverPwd, connectType, port, dbName;
		boolean connectIdDupl = false;
		Server server = new Server();
		serverName = request.getParameter("serverName");
		ipAddress = request.getParameter("ipAddress");
		serverDesc = request.getParameter("serverDesc");
		serverId = request.getParameter("serverId");
		serverPwd = request.getParameter("serverPwd");
		connectType = request.getParameter("connectType");
		port = request.getParameter("port");
		dbName = request.getParameter("dbName");
		if (request.getParameter("connectIdDupl").equals("true"))
			connectIdDupl = true;
		else if (request.getParameter("connectIdDupl").equals("false"))
			connectIdDupl = false;

		server.setServerName(serverName);
		server.setIpAddress(ipAddress);
		server.setServerDesc(serverDesc);
		server.setServerId(serverId);
		server.setServerPwd(serverPwd);
		server.setConnectType(connectType);
		server.setPort(port);
		server.setDbName(dbName);
		server.setConnectIdDupl(connectIdDupl);

		serverService.insertDBServer(server);

		/*
		 * ArrayList<Server> serverList = serverService.getServerList(1);
		 * InetAddress pingCheck; boolean checkResult; for(int i=0;
		 * i<serverList.size(); i++){ pingCheck =
		 * InetAddress.getByName(serverList
		 * .get(i).getIpAddress().split(":")[0]); checkResult =
		 * pingCheck.isReachable(1000); if(checkResult){
		 * serverList.get(i).setPingCheck("SUCCESS"); } else {
		 * serverList.get(i).setPingCheck("FAILURE"); } }
		 * request.setAttribute("serverList", serverList); return "dbList";
		 */

		request.setAttribute("message", "성공적으로 서버를 등록하였습니다.");
		return "/Server/serverServicePage";
	}

}
