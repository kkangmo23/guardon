package test;

public class workflowTest {

	public static void main(String[] args) {

		String step = "admin,user|out|administrator";
		System.out.println(step);
		String workflowStep = "first";
		
		String[] arr1 = step.split("[|]");
		String[] arr2;

		for (int i = 0; i < arr1.length; i++) {
			arr2 = arr1[i].split(",");
			System.out.println(arr1[i]);
			
			for (int j = 0; j < arr2.length; j++) {
				
				System.out.println(arr2[j]);
				
				switch (i) {
				case 0:
					workflowStep = "first";
					break;
				case 1:
					workflowStep = "second";
					break;
				case 2:
					workflowStep = "third";
					break;
				case 3:
					workflowStep = "fourth";
					break;
				case 4:
					workflowStep = "fifth";
					break;
				default:
					break;
				}
			}
		}

	}

}
