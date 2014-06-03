package com.guardon.view;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.guardon.log.LogService;
import com.guardon.option.OptionService;
import com.guardon.report.ReportService;
import com.guardon.report.domain.ApprovalInfo;
import com.guardon.report.domain.LogInfo;
import com.guardon.request.RequestService;
import com.guardon.request.domain.Request;
import com.guardon.server.ServerService;
import com.guardon.user.UserService;

@Service
public class DailyWork {

	@Autowired
	UserService userService;

	@Autowired
	ServerService serverService;

	@Autowired
	ReportService reportService;

	@Autowired
	RequestService requestService;

	@Autowired
	LogService logService;
	
	@Autowired
	OptionService optionService;
	
	
	public static int randomRange(int n1, int n2){
		return (int)(Math.random() * (n2-n1+1))+n1;
	}
	

	// @Scheduled(fixedDelay=5000)
	
	@Scheduled(cron="0 */10 * * * *")
	public void checkApproved() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();		
		GregorianCalendar gc = new GregorianCalendar();		
		int approvedTimeLimit, atlMinute, atlHour;
		String time;
		
		try {
			approvedTimeLimit = optionService.getApprovedTimeLimit();
			
			atlMinute = approvedTimeLimit % 60;
			atlHour = approvedTimeLimit / 60;

			gc.add(gc.MINUTE, atlMinute*(-1));
			gc.add(gc.HOUR, atlHour*(-1));
			date = gc.getTime();
			
			time = df.format(date);
			
			requestService.expireOtpPwdByTime(time);
			
			
			System.out.println(approvedTimeLimit);
			System.out.println(time);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0 0 1 * * *")
	public void test(){		
		
////////////////////////////expire task//////////////////////////
		try{
			String serverName, connectId;
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
			 Date date = new Date();
			 String today = df.format(date);
			 
			 ArrayList<Request> al = requestService.getExpirePeriodPwdTarget(today);
			 
			 for (int i = 0; i < al.size(); i++) {
				 serverName = al.get(i).getServerName();
				 connectId = al.get(i).getConnectId();				 
				 
				 makeOtpAll(serverName, connectId);		
			}
			 
			 requestService.expirePeriodPwd(today);
			 //requestService.expireOtpPwd();
		}
		catch (Exception e){
			e.printStackTrace();
		}
////////////////////end of expire task//////////////////////////
			 
			 
			 
			 
////////////////////report task//////////////////////////
			try{
					//String dirNameYear;
					//String dirNameMonth;
					String excelFileName;
					
					//String dirPath = "/home/onsol/바탕화면/Reports/";
					String dirPath = "/home/guardon01/Desktop/Reports/";
					
					SimpleDateFormat sdFormat;
					//Date nowYear;
					//Date nowMonth;
					Date nowDate;
					WritableWorkbook workbook;				
				
					/*
					sdFormat = new SimpleDateFormat("yyyy");
					nowYear = new Date();
					dirNameYear = sdFormat.format(nowYear);			
					
					sdFormat = new SimpleDateFormat("MM");
					nowMonth = new Date();
					dirNameMonth = sdFormat.format(nowMonth)+"월";							
					*/
					
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					 GregorianCalendar gc = new GregorianCalendar();
					 gc.add(gc.DATE, -1);
					 Date date = gc.getTime();
					 excelFileName = df.format(date);
					/*
					sdFormat = new SimpleDateFormat("yyyy.MM.dd");
					nowDate = new Date();
					excelFileName = sdFormat.format(nowDate);
					*/
					excelFileName += ".xls";
					
					File reportFile = new File(dirPath + excelFileName);				
					
					//File dir = new File("Reports\\"+dirNameYear);
					//dir.mkdir();
					//dir = new File("Reports\\"+dirNameYear+"\\"+dirNameMonth);
					//dir.mkdir();
					//File reportFile = new File("Reports\\"+dirNameYear+"\\"+dirNameMonth+"\\"+excelFileName);
					
							
						workbook = Workbook.createWorkbook(reportFile);
						
						//logReport sheet
						logReport(workbook);
						
						//approveReport sheet		
						approvalReport(workbook);
						
						//rejectReport sheet	
						//rejectReport(workbook);
									
						workbook.write();
						workbook.close();
					
			}
					catch(Exception e){
						e.printStackTrace();
					}		
					
	}

	public String firstOtp() throws Exception {

		String pwdComplexity = optionService.getPwdComplexity();
		int pwdLength = optionService.getPwdLength();

		if(pwdComplexity.equals("Low")){
			 String otp;
			 otp = UUID.randomUUID().toString().replace("-", "").substring(0,pwdLength);
			 char[] array = otp.toCharArray();
			 array[randomRange(1, otp.length()-1)] = (char)randomRange(33, 47);
			 otp="";
			 for (int i = 0; i < array.length; i++) {
				otp+=array[i];
			}
			 
			 System.out.println(otp);
			 if (otp.contains("@"))
					otp = otp.replaceAll("@", "\\$");
				if (otp.contains("/"))
					otp = otp.replaceAll("/", "#");
				if (otp.contains("\""))
					otp = otp.replaceAll("\"", "_");
				if (otp.contains("<"))
					otp = otp.replaceAll("<", "#");
				if (otp.contains("'"))
					otp = otp.replaceAll("'", "#");
				if (otp.contains("("))
					otp = otp.replaceAll("\\(", "%");
				if (otp.contains(")"))
					otp = otp.replaceAll("\\)", "!");
			 return otp;
		 } else {
			String otp = "";
			for (int i = 0; i < pwdLength; i++) {
				String buf;
				buf = "" + (char) ((Math.random() * 94) + 33);
				otp = otp + buf;
			}
			if (otp.contains("@"))
				otp = otp.replaceAll("@", "\\$");
			if (otp.contains("/"))
				otp = otp.replaceAll("/", "#");
			if (otp.contains("\""))
				otp = otp.replaceAll("\"", "_");
			if (otp.contains("<"))
				otp = otp.replaceAll("<", "#");
			if (otp.contains("'"))
				otp = otp.replaceAll("'", "#");
			if (otp.contains("("))
				otp = otp.replaceAll("\\(", "%");
			if (otp.contains(")"))
				otp = otp.replaceAll("\\)", "!");
			return otp;
		}
	}

	public void makeOtpAll(String serverName, String connectId) throws Exception{
		String userOtp, ipAddress, serverId, serverPwd, connectType, serverOS, port;
		String query, str;
		  
		  userOtp = firstOtp();	  
		  
		  Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			TelnetSample telnet = new TelnetSample();
		  
		  ipAddress = serverService.getServerIpAddress(serverName);
		  serverId = serverService.getServerId(serverName);
		  serverPwd = serverService.getServerPwd(serverName);
		  connectType = serverService.getConnectType(serverName);
		  port = serverService.getPort(serverName);
		  
		  System.out.println(serverName);
		  System.out.println(connectId);
		  System.out.println(connectType);
		  System.out.println(userOtp);
		  
		  try{
			  
			  switch (connectType) {
				
				case "oracle":				
					
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					con = DriverManager.getConnection("jdbc:oracle:thin:@"+ipAddress+":"+port+":orcl", serverId, serverPwd);
									
					query = "alter user "+connectId + " identified by \""+ userOtp + "\"";

					stmt = (Statement) con.createStatement();
					rs = stmt.executeQuery("select count(*) from dba_users where username=upper('" + connectId + "')");
					rs.next();
					str = rs.getString(1);
					System.out.println(str);

					if (str.equals("0"))
					{
						System.out.println("there is no such Id");
						//request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");	
					}
					else {
						stmt.executeUpdate(query);							
					}			
					
					rs.close();
					stmt.close();
					con.close();
					
					break;
				
				case "mssql":		
								
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");		
					
					query = "SP_PASSWORD NULL," + "'" + userOtp + "', '"+ connectId + "'";

					con = DriverManager.getConnection("jdbc:sqlserver://"+ipAddress+":"+port, serverId, serverPwd);								

					stmt = con.createStatement();
					
					rs = stmt.executeQuery("select count(*) from syslogins where name='"+connectId+"'");

					rs.next();
					str = rs.getString(1);
					System.out.println(str);

					if(str.equals("0"))
					{
						System.out.println("there is no such Id");
						//request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");				
					}
					else {
						stmt.executeUpdate(query);						
					}
					
					rs.close();
					stmt.close();
					con.close();
					
					break;
					
				case "mysql":			
					
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://"+ipAddress+"/mysql", serverId, serverPwd);
										
					query = "update user set password=password('" + userOtp + "') where user='" + connectId + "';";
					
					stmt = (Statement) con.createStatement();
					rs = stmt.executeQuery("select count(*) from user where user='" + connectId +"'");
					rs.next();
					str = rs.getString(1);
					System.out.println(str);
					
					if(str.equals("0"))
					{
						System.out.println("there is no such Id");
						//request.setAttribute("errorMessage", "서버에 해당 아이디가 존재하지 않습니다.");
						break;
					}
					
					else {
						stmt.executeUpdate(query);
						stmt.executeUpdate("flush privileges");
					}			
					
					rs.close();
					stmt.close();
					con.close();
					
					break;
					  
				case "telnet":			
					serverOS=serverService.getServerOS(serverName);
					if(serverOS.equals("windows")){						
											
						telnet.setHostPrompt(">");
						telnet.connect(ipAddress, serverId, serverPwd);
						telnet.changePwd(connectId, "1234", userOtp);
										
						telnet.disconnect();
										
						System.out.println(userOtp);
						
						break;
					}

				default:
					break;
				}
			  }
			  catch (Exception e){
				  throw e;
			  }
			  finally{
				 if(rs!=null)try{rs.close(); System.out.println("rs closed!!");} catch(Exception e){}
				 if(stmt!=null)try{stmt.close(); System.out.println("stmt closed!!");} catch(Exception e){}
				 if(con!=null)try{con.close(); System.out.println("con closed!!");} catch(Exception e){}	
				 if(telnet.isConnected())try{telnet.disconnect(); System.out.println("telnet disconnect!!");} catch(Exception e){}
			  }
			 
	  }
	
	public void logReport(WritableWorkbook workbook) throws Exception{
		
		 String sheetPwd="f54hdadsfgsdg";
		 String userType="";
		 
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 GregorianCalendar gc = new GregorianCalendar();
		 gc.add(gc.DATE, -1);
		 Date date = gc.getTime();
		 String yesterday = df.format(date);	 
		 
		 ArrayList<LogInfo> ALLI = new ArrayList<LogInfo>();
		 ALLI = reportService.getLogInfo(yesterday);
		
		 WritableSheet sheet;
			
		 WritableCellFormat menuFormat = new WritableCellFormat();
		 WritableCellFormat dataFormat = new WritableCellFormat();		
			
		 Label label;
		 //Number number;
		 
		 workbook.createSheet("LogReport", 0);
		 sheet = workbook.getSheet(0);
			
			//cell format///////////////////////////////////////////////////////
		 menuFormat.setAlignment(Alignment.CENTRE);
		 menuFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		 menuFormat.setBorder(Border.BOTTOM, BorderLineStyle.THICK);
		 menuFormat.setBackground(Colour.ICE_BLUE);
		
		 dataFormat.setAlignment(Alignment.CENTRE);
		 dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		
			//column /////////////////////////////////////////////////////
		 sheet.setColumnView(0, 15);
		 sheet.setColumnView(1, 15);
		 sheet.setColumnView(2, 10);
		 sheet.setColumnView(3, 15);
		 sheet.setColumnView(4, 10);
		 sheet.setColumnView(5, 10);
		 sheet.setColumnView(6, 20);
		 /*
		 sheet.setColumnView(6, 20);	 
		 sheet.setColumnView(7, 10);
		 sheet.setColumnView(8, 10);
		 sheet.setColumnView(9, 10);		    
		 */
			
		 label = new Label(0, 0, "사용자 유형", menuFormat);
		 sheet.addCell(label);
		 label = new Label(1, 0, "사용자 ID", menuFormat);
		 sheet.addCell(label);
		 label = new Label(2, 0, "사용자 이름", menuFormat);
		 sheet.addCell(label);
		 label = new Label(3, 0, "업체 / 사번", menuFormat);
		 sheet.addCell(label);
		 label = new Label(4, 0, "부서", menuFormat);
		 sheet.addCell(label);
		 label = new Label(5, 0, "직책", menuFormat);
		 sheet.addCell(label);
		 label = new Label(6, 0, "로그인 시간", menuFormat);
		 sheet.addCell(label);
		 /*
		 label = new Label(6, 0, "로그아웃 시간", menuFormat);
		 sheet.addCell(label);		 
		 label = new Label(7, 0, "요청 횟수", menuFormat);
		 sheet.addCell(label);
		 label = new Label(8, 0, "승인 횟수", menuFormat);
		 sheet.addCell(label);
		 label = new Label(9, 0, "반려 횟수", menuFormat);
		 sheet.addCell(label);
		 */

			for (int i = 0; i < ALLI.size(); i++) {
				
				switch (ALLI.get(i).getUserType()) {
				case "super":
					userType="최고관리자";
					break;
				case "admin":
					userType="관리자";
					break;
				case "user":
					userType="내부사용자";
					break;
				case "outUser":
					userType="외부사용자";
					break;

				default:
					break;
			}
		 	label = new Label(0, i+1, userType, dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(1, i+1, ALLI.get(i).getUserId(), dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(2, i+1, ALLI.get(i).getUserName(), dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(3, i+1, ALLI.get(i).getCompanyNumber(), dataFormat);
			sheet.addCell(label);
		 	label = new Label(4, i+1, ALLI.get(i).getUserDepartment(), dataFormat);
			sheet.addCell(label);
			label = new Label(5, i+1, ALLI.get(i).getUserLevel(), dataFormat);
			sheet.addCell(label);				
			label = new Label(6, i+1, ALLI.get(i).getLoginTime(), dataFormat);
			sheet.addCell(label);
			
			/*
			label = new Label(6, i+1, ALLI.get(i).getLogoutTime(), dataFormat);
			sheet.addCell(label);		
			number = new Number(7, i+1, ALLI.get(i).getRequestCount(), dataFormat);
			sheet.addCell(number);
			number = new Number(8, i+1, ALLI.get(i).getApprovalCount(), dataFormat);
			sheet.addCell(number);
			number = new Number(9, i+1, ALLI.get(i).getRejectCount(), dataFormat);
			sheet.addCell(number);
			*/			
		 }	
			//////sheet protect	
		 //sheet.getSettings().setProtected(true);
		 //sheet.getSettings().setPassword(sheetPwd);
	 }
	 
	 public void approvalReport(WritableWorkbook workbook) throws Exception{
		 
		 String sheetPwd="f54hdadsfgsdg";
		 String userType="", pwdType="", approved="";
		 
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 GregorianCalendar gc = new GregorianCalendar();
		 gc.add(gc.DATE, -1);
		 Date date = gc.getTime();
		 String yesterday = df.format(date);	
		 
		 ArrayList<ApprovalInfo> ALAI = new ArrayList<ApprovalInfo>();
		 ALAI = reportService.getApprovalInfo(yesterday);
		
		 WritableSheet sheet;
			
		 WritableCellFormat menuFormat = new WritableCellFormat();
		 WritableCellFormat dataFormat = new WritableCellFormat();		
			
		 Label label;	 
		 
		 workbook.createSheet("ApprovalReport", 0);
		 sheet = workbook.getSheet(0);
			
			//cell format///////////////////////////////////////////////////////
		 menuFormat.setAlignment(Alignment.CENTRE); 
		 menuFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
		 menuFormat.setBorder(Border.BOTTOM, BorderLineStyle.THICK);
		 menuFormat.setBackground(Colour.ICE_BLUE);
		
		 dataFormat.setAlignment(Alignment.CENTRE);
		 dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
		
			//column /////////////////////////////////////////////////////
		 sheet.setColumnView(0, 15);
		 sheet.setColumnView(1, 15);
		 sheet.setColumnView(2, 10);
		 sheet.setColumnView(3, 15);
		 sheet.setColumnView(4, 10);
		 sheet.setColumnView(5, 10);
		 sheet.setColumnView(6, 15);
		 sheet.setColumnView(7, 15);
		 sheet.setColumnView(8, 15);
		 sheet.setColumnView(9, 20);
		 sheet.setColumnView(10, 20);
		 sheet.setColumnView(11, 15);
		 sheet.setColumnView(12, 15);
		 //sheet.setColumnView(12, 10);
		 sheet.setColumnView(13, 20);
				
		 label = new Label(0, 0, "사용자 유형", menuFormat);
		 sheet.addCell(label);
		 label = new Label(1, 0, "사용자 ID", menuFormat);
		 sheet.addCell(label);
		 label = new Label(2, 0, "사용자 이름", menuFormat);
		 sheet.addCell(label);
		 label = new Label(3, 0, "업체 / 사번", menuFormat);
		 sheet.addCell(label);
		 label = new Label(4, 0, "부서", menuFormat);
		 sheet.addCell(label);
		 label = new Label(5, 0, "직책", menuFormat);
		 sheet.addCell(label);
		 label = new Label(6, 0, "패스워드 유형", menuFormat);
		 sheet.addCell(label);
		 label = new Label(7, 0, "요청 서버", menuFormat);
		 sheet.addCell(label);
		 label = new Label(8, 0, "사용 ID", menuFormat);
		 sheet.addCell(label);
		 label = new Label(9, 0, "요청 시간", menuFormat);
		 sheet.addCell(label);
		 label = new Label(10, 0, "승인/반려 시간", menuFormat);
		 sheet.addCell(label);
		 label = new Label(11, 0, "승인 여부", menuFormat);
		 sheet.addCell(label);
		 label = new Label(12, 0, "관리자 ID", menuFormat);
		 sheet.addCell(label);
		 /*
		 label = new Label(12, 0, "관리자 이름", menuFormat);
		 sheet.addCell(label);
		 */
		 label = new Label(13, 0, "요청 내용", menuFormat);
		 sheet.addCell(label);	 
		 
		 for (int i = 0; i < ALAI.size(); i++)
		 {				
			 switch (ALAI.get(i).getUserType()) {
			 case "super":
					userType="최고관리자";
					break;
				case "admin":
					userType="관리자";
					break;
				case "user":
					userType="내부사용자";
					break;
				case "outUser":
					userType="외부사용자";
					break;

				default:
					break;
			}
			 switch (ALAI.get(i).getPwdType()) {
				case "OTP":
					pwdType="일회용 비밀번호";
					break;
				case "period":
					pwdType="주기적 비밀번호";
					break;
				
				default:
					break;
			}
			 switch (ALAI.get(i).getApproved()) {
				case "unchecked":
					approved="승인 대기중";
					break;
				case "approved":
					approved="승인";
					break;
				case "rejected":
					approved="반려";
					break;
				case "expired":
					approved="만료됨";
					break;

				default:
					break;
			}
		 	label = new Label(0, i+1, userType, dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(1, i+1, ALAI.get(i).getUserId(), dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(2, i+1, ALAI.get(i).getUserName(), dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(3, i+1, ALAI.get(i).getCompanyNumber(), dataFormat);
			sheet.addCell(label);
		 	label = new Label(4, i+1, ALAI.get(i).getUserDepartment(), dataFormat);
			sheet.addCell(label);
			label = new Label(5, i+1, ALAI.get(i).getUserLevel(), dataFormat);
			sheet.addCell(label);				
			label = new Label(6, i+1, pwdType, dataFormat);
			sheet.addCell(label);
			label = new Label(7, i+1, ALAI.get(i).getServerName(), dataFormat);
			sheet.addCell(label);
			label = new Label(8, i+1, ALAI.get(i).getConnectId(), dataFormat);
			sheet.addCell(label);
			label = new Label(9, i+1, ALAI.get(i).getRequestDate(), dataFormat);
			sheet.addCell(label);
			label = new Label(10, i+1, ALAI.get(i).getApprovalDate(), dataFormat);
			sheet.addCell(label);
			label = new Label(11, i+1, approved, dataFormat);
			sheet.addCell(label);
			label = new Label(12, i+1, ALAI.get(i).getApprovalId(), dataFormat);
			sheet.addCell(label);
			/*
			String approverId = userService.getUserName(ALAI.get(i).getApprovalId());
			System.out.println(approverId);
			label = new Label(12, i+1, approverId, dataFormat);
			
			System.out.println(userService.getUserName(ALAI.get(i).getApprovalId()));
			
			sheet.addCell(label);
			*/
			label = new Label(13, i+1, ALAI.get(i).getRequestDesc(), dataFormat);
			sheet.addCell(label);
				
		 }	
			//////sheet protect	
		 //sheet.getSettings().setProtected(true);
		 //sheet.getSettings().setPassword(sheetPwd);
		 
	 }
	 
	 /*
	 public void rejectReport(WritableWorkbook workbook) throws Exception{
		
		 ArrayList<RejectInfo> ALRI = new ArrayList<RejectInfo>();
		 ALRI = reportService.getRejectInfo();
		
		 WritableSheet sheet;
			
		 WritableCellFormat menuFormat = new WritableCellFormat();
		 WritableCellFormat dataFormat = new WritableCellFormat();		
			
		 Label label;	
		 
		 workbook.createSheet("RejectReport", 0);
		 sheet = workbook.getSheet(0);
			
			//cell format///////////////////////////////////////////////////////
		 menuFormat.setAlignment(Alignment.CENTRE); 
		 menuFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
		 menuFormat.setBorder(Border.BOTTOM, BorderLineStyle.THICK); 
		 menuFormat.setBackground(Colour.ICE_BLUE);
		 
		 dataFormat.setAlignment(Alignment.CENTRE);
		 dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
		
			//column /////////////////////////////////////////////////////
		 sheet.setColumnView(0, 15);
		 sheet.setColumnView(1, 15);
		 sheet.setColumnView(2, 10);
		 sheet.setColumnView(3, 10);
		 sheet.setColumnView(4, 10);
		 sheet.setColumnView(5, 20);
		 sheet.setColumnView(6, 15);
		 sheet.setColumnView(7, 10);
		 sheet.setColumnView(8, 20);			    
		
		 label = new Label(0, 0, "사용자 유형", menuFormat);
		 sheet.addCell(label);
		 label = new Label(1, 0, "사용자 ID", menuFormat);
		 sheet.addCell(label);
		 label = new Label(2, 0, "사용자 이름", menuFormat);
		 sheet.addCell(label);
		 label = new Label(3, 0, "부서", menuFormat);
		 sheet.addCell(label);
		 label = new Label(4, 0, "직책", menuFormat);
		 sheet.addCell(label);
		 label = new Label(5, 0, "반려 시간", menuFormat);
		 sheet.addCell(label);
		 label = new Label(6, 0, "패스워드 유형", menuFormat);
		 sheet.addCell(label);			
		 label = new Label(7, 0, "반려자Id", menuFormat);
		 sheet.addCell(label);
		 label = new Label(8, 0, "반려 사유", menuFormat);
		 sheet.addCell(label);	 
		 
		 for (int i = 0; i < ALRI.size(); i++)
		 {				
		 	label = new Label(0, i+1, ALRI.get(i).getUserType(), dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(1, i+1, ALRI.get(i).getUserId(), dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(2, i+1, ALRI.get(i).getUserName(), dataFormat);
		 	sheet.addCell(label);
		 	label = new Label(3, i+1, ALRI.get(i).getUserDepartment(), dataFormat);
			sheet.addCell(label);
			label = new Label(4, i+1, ALRI.get(i).getUserLevel(), dataFormat);
			sheet.addCell(label);				
			label = new Label(5, i+1, ALRI.get(i).getApprovalTime(), dataFormat);
			sheet.addCell(label);
			label = new Label(6, i+1, ALRI.get(i).getPwdType(), dataFormat);
			sheet.addCell(label);
			label = new Label(7, i+1, ALRI.get(i).getApprovalId(), dataFormat);
			sheet.addCell(label);
			label = new Label(8, i+1, ALRI.get(i).getRejectReason(), dataFormat);
			sheet.addCell(label);
				
		 }	
			//////sheet protect	
		 sheet.getSettings().setProtected(true);
		 sheet.getSettings().setPassword("1111");/////hard coding/////////////
		
	 }
	 */
}
