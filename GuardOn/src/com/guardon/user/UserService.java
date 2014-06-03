package com.guardon.user;

import java.util.ArrayList;
import java.util.Map;

import com.guardon.user.domain.User;

public interface UserService {
	
	public void insertJoinRequest(User user) throws Exception;

	public void setJoinApproved(String userId) throws Exception;

	public void deleteUser(String userId) throws Exception;

	public ArrayList<User> getJoinUserList(int page) throws Exception;

	public void insertUser(User user) throws Exception;

	public User getUserBasicInfo(String userId) throws Exception;

	public String getUserId(String userId) throws Exception;

	public void updateUser(User user) throws Exception;

	public String getUserPwd(String userId) throws Exception;

	public String getUserDepartment(String userId) throws Exception;

	public String getUserType(String userId) throws Exception;

	public String getUserName(String userId) throws Exception;

	public ArrayList<User> getUserList(int page) throws Exception;

	public ArrayList<User> getWfUserList(int page) throws Exception;

	public void setActive(String userId) throws Exception;

	public void setDeactive(String userId) throws Exception;

	public boolean isActive(String userId) throws Exception;
	
	public String getId(Map<String, String> map) throws Exception;
	
	public int countId(Map<String, String> map) throws Exception;
	
	public int countPwd(Map<String, String> map) throws Exception;
	
	public String getPwd(Map<String, String> map) throws Exception;

}
