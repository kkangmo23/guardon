package com.guardon.server;

import java.util.ArrayList;
import java.util.Map;

import com.guardon.server.domain.Server;

public interface ServerService {
	
	public Server getServerInfo(String serverName) throws Exception;

	public void insertServer(Server server) throws Exception;
	
	public void insertDBServer(Server Server) throws Exception;
	
	public void updateServer(Server server) throws Exception;
	
	public void updateDB(Server server) throws Exception;
	
	public int delServer(String serverName)throws Exception;
	
	public String getServerUrl(String serverName)throws Exception;
	
	public String getServerDesc(String serverName)throws Exception;
	
	public String getServerId(String serverName)throws Exception;
	
	public String getServerPwd(String serverName)throws Exception;
	
	public String getConnectType(String serverName)throws Exception;
	
	public String getServerOS(String serverName)throws Exception;
	
	public String getWorkflowName(String serverName)throws Exception;

	public ArrayList<Server> getServerList(int page) throws Exception; // 서버설치시 서버리스트
	
	public ArrayList<Server> getWfServerList(int page) throws Exception;
	
	public ArrayList<Server> getPeriodServerList(int page) throws Exception;
	
	public int getServerListCount() throws Exception;
	
	public String getServerIpAddress(String serverName) throws Exception;
	
	public String getPort(String serverName) throws Exception;
	
	public String getServerName(String serverName) throws Exception;
	
	public void setWorkflowName(Map<String, String> map) throws Exception;
	
	public boolean getConnectIdDupl(String serverName) throws Exception;
	
	public void deleteServer(String serverName) throws Exception;
	
	public int countServerName(String serverName) throws Exception;
}
