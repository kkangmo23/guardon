package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class javaHttpPostTest {

	String userId = "admin";
	String userPwd = "1234";
	URL u;
	
	public static void openUrl(String url) {
		String os = System.getProperty("os.name");
		Runtime runtime = Runtime.getRuntime();
		try {
			// Block for Windows Platform
			if (os.startsWith("Windows")) {
				String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
				Process p = runtime.exec(cmd);
			}
			// Block for Mac OS
			else if (os.startsWith("Mac OS")) {
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL",
						new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			}
			// Block for UNIX Platform
			else {
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (runtime.exec(new String[] { "which", browsers[count] }).waitFor() == 0)
						browser = browsers[count];
				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					runtime.exec(new String[] { browser, url });
			}
		} catch (Exception x) {
			System.err.println("Exception occurd while invoking Browser!");
			x.printStackTrace();
		}
	}

	public static void main(String args[]) throws UnsupportedEncodingException {
		String s;
		try {
			s = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			s = "http://localhost:8080/GuardOn/userLogin.do";
		}

		try {
			javaHttpPostTest pf = new javaHttpPostTest();
			pf.u = new URL(s);
			pf.submitData();
			openUrl(s);
		} catch (MalformedURLException e) {
			System.err.println(args[0] + "is not URL");
		}
	}

	void submitData() throws UnsupportedEncodingException {
		// 질의 문자열을 보내기 전에 encoding 한다...
		String query = "userId=" + URLEncoder.encode(userId, "euc-kr");
		query += "&";
		query += "userPwd=" + URLEncoder.encode(userPwd, "euc-kr");

		try {
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true); // 네트워크 출력 작업을 위해서
			uc.setDoInput(true); // 네트웍크 입력 작업을 위해서 true로 설정
			uc.setAllowUserInteraction(false); // 사용자와의 대화 가능여부를 false로 설정

			BufferedWriter dos = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream()));
			// id와 password 값을 전송 .. writeBytes() 메쏘드를 사용
			dos.write(query, 0, query.length());
			dos.flush();
			dos.close();

			// 질의 문자열을 보낸 후, 다시 그에 대한 데이터를 받음 ..
			BufferedReader dis = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String nextline;
			while ((nextline = dis.readLine()) != null) {
				System.out.println(nextline);
			}
			dis.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
