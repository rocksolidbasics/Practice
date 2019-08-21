package general;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.NumberFormatter;

public class HackerRankTests {

	public static void main(String[] args) {
		// Test 1
		int sum = simpleArraySum(new int[] { 10, 7, 1 });
		System.out.println(sum);
		
		//Test 2 - Compare Triplets
		
		// Test 3
		initTest3();
		
		//Test 4
		plusMinus(new int[] {1, 1, 0, -1, -1});
		
		//Test 5
		staircase(4);
		
		//Test 6
		miniMaxSum(new int[] {1, 2, 3, 4, 5});
		miniMaxSum(new int[] {91, 2, 33, 74, 25});
		miniMaxSum(new int[] {9, 2, 3});
		miniMaxSum(new int[] {9, 2});
		miniMaxSum(new int[] {9});
	
		//Test 7 - Time conversion
		String time = timeConversion("07:05:45PM");
		time = timeConversion("07:05:45AM");
		System.out.println(time);
		time = timeConversion("12:05:45AM");
		time = timeConversion("12:05:45PM");
	}
	
	//Time conversion
	static String timeConversion(String s) {
		String part = s.substring(0, s.length()-2);
		String partSansHr = part.substring(part.indexOf(":"));
		int h = Integer.parseInt(s.substring(0, s.indexOf(":")));

		if(s.substring(s.length()-2).equals("AM")) {
			if(h == 12)
				return "00" + partSansHr; 
			return part;
		} else if(s.substring(s.length()-2).equals("PM")) {
			if(h != 12 ) {
				return (h+12) + partSansHr;
			}
		}
		
		return part;
    }
	
	//Test 6
	static void miniMaxSum(int[] arr) {
		long small = 0, big = 0;
		
		if(arr.length == 1) {
			System.out.println(arr[0] + " " + arr[0]);
			return;
		}
		
		if(arr[0] < arr[1]) {
			small = arr[0];
			big = arr[1];
		} else {
			big = arr[0];
			small = arr[1];
		}
		
		long sum = 0;
		
		for(int i=2; i<arr.length; i++) {
			if(arr[i] < small) {
				sum += small;
				small = arr[i];
			} else if(arr[i] > big) {
				sum += big;
				big = arr[i];
			} else {
				sum += arr[i];
			}
		}
		
		System.out.println((small+sum) + " " + (sum+big));
    }
	
	//Test 5
	static void staircase(int n) {
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n-i; j++)
				System.out.print(" ");
			for(int k=1; k<=i; k++)
				System.out.print("#");
			System.out.println("");
		}
    }
	
	//Test 4
	static void plusMinus(int[] arr) {
		double p = 0, z = 0, n = 0;
		for(int i : arr) {
			if(i > 0) p++;
			else if(i < 0) n++;
			else z++;
		}
		
		System.out.printf("%.6f\n", (p/arr.length));
		System.out.printf("%.6f\n", (n/arr.length));
		System.out.printf("%.6f\n", (z/arr.length));
    }
	
	private static void initTest3() {
		List<List<Integer>> arr = new ArrayList<List<Integer>>();
		arr.add(Arrays.asList(new Integer[]{3, 2, 1}));
		arr.add(Arrays.asList(new Integer[]{2, 1, 3}));
		arr.add(Arrays.asList(new Integer[]{1, 2, 3}));
		int dd = diagonalDifference(arr);
		System.out.println("Diagonal difference: " + dd);
	}

	//Test 3
	static int diagonalDifference(List<List<Integer>> arr) {
		int l = 0, r = 0;
		System.out.println(arr.size());
		
		for(int i = 0; i<arr.size(); i++) {
			int j = arr.size()-1-i;
			
			l += arr.get(i).get(i);		//i=0 -> 0,0 / i=1 -> 1,1 / i=2 -> 2,2
			r += arr.get(i).get(j);		//j=2 -> 0,2 / j=1 -> 1,1 / j=0 -> 2,0
		}
		
		return Math.abs(l - r);
	}

	//Test 1
	static int simpleArraySum(int[] ar) {
		int i = 0, t = 0;
		while (i < ar.length && (t += ar[i++]) > -1);

		return t;
	}

	//Test 2
	static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
		List<Integer> results = new ArrayList<>();
		results.add(0);
		results.add(0);

		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) == b.get(i))
				continue;
			else if (a.get(i) > b.get(i))
				results.set(0, results.get(0) + 1);
			else
				results.set(1, results.get(1) + 1);
		}

		return results;
	}

}
