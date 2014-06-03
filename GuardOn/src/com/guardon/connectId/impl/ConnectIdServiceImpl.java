package com.guardon.connectId.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.guardon.connectId.ConnectIdDAO;
import com.guardon.connectId.ConnectIdService;
import com.guardon.connectId.domain.ConnectId;

@Service("connectIdService")
public class ConnectIdServiceImpl implements ConnectIdService{

	@Inject
	@Named("connectIdDAO")
	ConnectIdDAO connectIdDAO;
	
	@Override
	public ArrayList<ConnectId> getConnectIdList()
			throws Exception {
		return connectIdDAO.getConnectIdList();
	}

	@Override
	public int countConnectId(Map<String, String> map) throws Exception {
		return connectIdDAO.countConnectId(map);
	}

	@Override
	public void insertConnectId(ConnectId connectId) throws Exception {
		connectIdDAO.insertConnectId(connectId);
	}

	@Override
	public void updateConnectId(Map<String, String> map) throws Exception {
		connectIdDAO.updateConnectId(map);
	}

	@Override
	public void deleteConnectId(Map<String, String> map) throws Exception {
		connectIdDAO.deleteConnectId(map);
		//System.out.println("connectId :"+map.get("connectId"));
		//System.out.println("serverName :"+map.get("serverName"));
	}

}
