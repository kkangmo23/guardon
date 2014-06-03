package test;

public class splitTest {

	public static void main(String[] args) {
		String str = "as,df<><>sdf<>,fff";
		String[] arr = str.split("<><>");
				
		System.out.println(str);
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}

	}

}
