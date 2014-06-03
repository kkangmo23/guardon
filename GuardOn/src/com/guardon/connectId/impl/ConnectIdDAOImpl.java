package com.guardon.connectId.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Repository;

import com.guardon.connectId.ConnectIdDAO;
import com.guardon.connectId.domain.ConnectId;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("connectIdDAO")
public class ConnectIdDAOImpl implements ConnectIdDAO{
	@Inject
	@Named ("sqlMapClient")
	SqlMapClient sqlMapClient;

	@Override
	public ArrayList<ConnectId> getConnectIdList()
			throws Exception {
		return (ArrayList<ConnectId>) sqlMapClient.queryForList("ConnectId.getConnectIdList");
	}

	@Override
	public int countConnectId(Map<String, String> map) throws Exception {
		return (int) sqlMapClient.queryForObject("ConnectId.countConnectId", map);
	}

	@Override
	public void insertConnectId(ConnectId connectId) throws Exception {
		sqlMapClient.insert("ConnectId.insertConnectId", connectId);
	}

	@Override
	public void updateConnectId(Map<String, String> map) throws Exception {
		sqlMapClient.update("ConnectId.updateConnectId", map);
	}

	@Override
	public void deleteConnectId(Map<String, String> map) throws Exception {
		sqlMapClient.delete("ConnectId.deleteConnectId", map);
		
	}
	
	
}
