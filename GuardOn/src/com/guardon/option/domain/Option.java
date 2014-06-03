package com.guardon.option.domain;

public class Option {
	
	private int pwdLength;
	private int autoLogout;
	private int otpTimeLimit;
	private int loginFailedCount;
	private String pwdComplexity;
	private int approvedTimeLimit;
	
	
	
	public int getApprovedTimeLimit() {
		return approvedTimeLimit;
	}
	public void setApprovedTimeLimit(int approvedTimeLimit) {
		this.approvedTimeLimit = approvedTimeLimit;
	}
	public int getPwdLength() {
		return pwdLength;
	}
	public void setPwdLength(int pwdLength) {
		this.pwdLength = pwdLength;
	}	
	public int getAutoLogout() {
		return autoLogout;
	}
	public void setAutoLogout(int autoLogout) {
		this.autoLogout = autoLogout;
	}
	public int getOtpTimeLimit() {
		return otpTimeLimit;
	}
	public void setOtpTimeLimit(int otpTimeLimit) {
		this.otpTimeLimit = otpTimeLimit;
	}
	public int getLoginFailedCount() {
		return loginFailedCount;
	}
	public void setLoginFailedCount(int loginFailedCount) {
		this.loginFailedCount = loginFailedCount;
	}
	public String getPwdComplexity() {
		return pwdComplexity;
	}
	public void setPwdComplexity(String pwdComplexity) {
		this.pwdComplexity = pwdComplexity;
	}
	
	
}
