package com.guardon.connectId;

import java.util.ArrayList;
import java.util.Map;

import com.guardon.connectId.domain.ConnectId;

public interface ConnectIdDAO {
	
	public ArrayList<ConnectId> getConnectIdList() throws Exception;
	public int countConnectId(Map<String, String> map) throws Exception;
	public void insertConnectId(ConnectId connectId) throws Exception;
	public void updateConnectId(Map<String, String> map) throws Exception;
	public void deleteConnectId(Map<String, String> map) throws Exception;
}
