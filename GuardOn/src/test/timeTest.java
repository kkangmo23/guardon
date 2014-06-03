package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class timeTest {

	public static void main(String[] args) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();		
		GregorianCalendar gc = new GregorianCalendar();		
		int approvedTimeLimit, atlMinute, atlHour;
		String time, now;
		
		try {
			approvedTimeLimit = 5;
			
			atlMinute = approvedTimeLimit % 60;
			atlHour = approvedTimeLimit / 60;

			now = df.format(date);
			
			gc.add(gc.MINUTE, atlMinute*(-1));
			gc.add(gc.HOUR, atlHour*(-1));
			date = gc.getTime();
			
			time = df.format(date);
			
			System.out.println(atlMinute);
			System.out.println(atlHour);
			System.out.println(now);
			System.out.println(time);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
