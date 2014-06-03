package com.guardon.connectId.domain;

public class ConnectId {
	private String connectId;
	private String serverName;
	private String connectIdDesc;
	private String ipAddress;
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getConnectId() {
		return connectId;
	}
	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getConnectIdDesc() {
		return connectIdDesc;
	}
	public void setConnectIdDesc(String connectIdDesc) {
		this.connectIdDesc = connectIdDesc;
	}
	
}
