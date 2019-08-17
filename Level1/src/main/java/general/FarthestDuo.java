package general;

public class FarthestDuo {

	private static int[] arr = {4, 6, 8, 20,3, 5, 7, 6, 3, 7, 9, 5, 7, 5, 7, 5, 12};
	//Same on both sides number scenario problem
	//private static int[] arr = {4, 5, 8, 20,3, 5, 7, 6, 3, 7, 9, 5, 7, 5, 7, 5, 12}; 
	public static void main(String[] args) {
		boolean stopLeft = false;
		int leftVal = 0;
		int rightVal = 0;
		boolean stopRight = false;
		int i=0, j=arr.length-1;
		int mid = arr.length/2;
		int high = 0;
		
		System.out.println("Array length: " + arr.length + ", Mid = " + mid);
		while(true) {
			System.out.println(i + ", " + j);
			if((arr[i]==5 || arr[i]==7)) {
				stopLeft = true;
				if(stopRight && arr[j]==arr[i]) {
					j=arr.length-1;
					stopLeft = false;
					stopRight = false;
				}
			}
			
			if(!stopLeft && i<mid) i++;
			
			if(arr[j]==5 || arr[j]==7) {
				stopRight = true;
				if(stopLeft && arr[j]==arr[i]) {
					i=0;
					stopLeft = false;
					
				} else {
					System.out.println("Found match with index: " + i + ", " + j);
					stopLeft = false;
				}
			}
			
			if(!stopRight && j>mid) j--;
			
			if(i==j) break;
		}
	}
}
