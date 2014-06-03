package test;

import java.util.UUID;

public class password {
	public static int randomRange(int n1, int n2){
		return (int)(Math.random() * (n2-n1+1))+n1;
	}
	public static void main(String[] args) {
		/*
		int pwdLevel=10;
		String pwd="";
		for (int i = 0; i <pwdLevel ; i++) {
			String buf;
			buf = ""+(char)((Math.random() * 94) + 33);
			pwd = pwd+buf;
		}
		System.out.println(pwd);
		*/
		
		
		
		String otp;
		 otp = UUID.randomUUID().toString().replace("-", "").substring(0,15);		 
		 char[] array = otp.toCharArray();
		 array[randomRange(1, otp.length()-1)] = (char)randomRange(33, 47);
		 otp="";
		 for (int i = 0; i < array.length; i++) {
			otp+=array[i];
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
			
			System.out.println(otp);
		
	}

}
