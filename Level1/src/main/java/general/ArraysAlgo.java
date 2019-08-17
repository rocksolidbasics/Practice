package general;

public class ArraysAlgo {

	public static void main(String[] args) {
		ArraysAlgo aa = new ArraysAlgo();
		aa.subarrayWithGivenSum();
	}

	private void subarrayWithGivenSum() {
		int[] arr = {2,3,9,1,4,3,2,3,8,2,5};
		int find = 7;
		
		int tot = 0;
		int next = 0;
		boolean reset = false;
		
		for(int j=0; j<arr.length; j++) {
			for(int i=j; i<arr.length; i++) {
 				if(arr[i] == find) {
					System.out.println(arr[i]);
					j=i-1;
					break;
				} else if(arr[i] > find) {
					tot = 0;
					j=i;
					break;
				} else {
					tot += arr[i];
					if(tot > find) {
						tot = 0;
						break;
					} else if(tot == find) {
						System.out.println(tot);
						tot = 0;
					}
				}
			}
		}
	}

}
