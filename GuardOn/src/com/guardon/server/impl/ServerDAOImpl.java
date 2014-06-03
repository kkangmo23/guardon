package com.guardon.server.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Repository;

import com.guardon.server.ServerDAO;
import com.guardon.server.domain.Server;
import com.ibatis.sqlmap.client.SqlMapClient;


@Repository("serverDAO")
public class ServerDAOImpl implements ServerDAO{

	@Inject
	@Named("sqlMapClient")
	SqlMapClient sqlMapClient;
	
	@Override
	public Server getServerInfo(String serverName) throws Exception {
		return (Server)sqlMapClient.queryForObject("Server.getServerInfo", serverName);
	}
	
	@Override
	public void insertServer(Server server) throws Exception {
		sqlMapClient.insert("Server.insertServer", server);		
	}
	
	@Override
	public void insertDBServer(Server server) throws Exception {
		sqlMapClient.insert("Server.insertDBServer", server);		
	}

	@Override
	public void updateServer(Server server) throws Exception {
		sqlMapClient.update("Server.updateServer", server);
	}
	
	@Override
	public void updateDB(Server server) throws Exception {
		sqlMapClient.update("Server.updateDB", server);
	}

	@Override
	public int delServer(String serverName) throws Exception {
		return 0;
	}

	@Override
	public String getServerUrl(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getServerUrl", serverName).toString();
	}

	@Override
	public String getServerDesc(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getServerDesc", serverName).toString();
	}

	@Override
	public String getServerId(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getServerId", serverName).toString();
	}

	@Override
	public String getServerPwd(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getServerPwd", serverName).toString();
	}

	@Override
	public String getConnectType(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getConnectType", serverName).toString();
	}
	
	@Override
	public String getServerOS(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getServerOS", serverName).toString();
	}
	
	@Override
	public String getWorkflowName (String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getWorkflowName", serverName).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Server> getServerList(int startIndex) throws Exception {
		return ((ArrayList<Server>)sqlMapClient.queryForList("Server.getServerList", startIndex));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Server> getWfServerList(int startIndex) throws Exception {
		return ((ArrayList<Server>)sqlMapClient.queryForList("Server.getWfServerList", startIndex));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Server> getPeriodServerList(int startIndex) throws Exception {
		return ((ArrayList<Server>)sqlMapClient.queryForList("Server.getPeriodServerList", startIndex));
	}

	@Override
	public String getServerIpAddress(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getServerIpAddress", serverName).toString();
	}

	@Override
	public String getPort(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getPort", serverName).toString();
	}

	@Override
	public String getServerName(String serverName) throws Exception {
		return sqlMapClient.queryForObject("Server.getServerName", serverName).toString()	;
	}

	@Override
	public int getServerListCount() throws Exception {
		return Integer.parseInt(sqlMapClient.queryForObject("Server.getServerListCount").toString());
	}
	
	@Override
	public void setWorkflowName(Map<String, String> map) throws Exception {
		sqlMapClient.update("Server.setWorkflowName", map);
	}	
	
	@Override
	public boolean getConnectIdDupl(String serverName) throws Exception {
		return (boolean)sqlMapClient.queryForObject("Server.getConnectIdDupl", serverName);
	}
	
	@Override
	public void deleteServer(String serverName) throws Exception {
		sqlMapClient.delete("Server.deleteServer", serverName);
	}

	@Override
	public int countServerName(String serverName) throws Exception {
		return (int) sqlMapClient.queryForObject("Server.countServerName", serverName);
	}

}
