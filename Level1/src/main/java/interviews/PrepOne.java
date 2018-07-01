package interviews;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class PrepOne {

	public static void main(String[] args) {
		PrepOne prepOne = new PrepOne();
		prepOne.isPowerOf10(10000);
		prepOne.firstNonRepeatingChar("abcadebcd");
		prepOne.secondSmallestInteger(new int[] {32, 12, 8, 24, 6, 1, 34});
		prepOne.fibonacciSeries(new LinkedList<Integer>(), 10);
		prepOne.isPalindrome("malab lam");
	}
	
	/**
	 * Check number of palindrome in the input string
	 */
	private int checkPalindromes(String input) {
		return 0;
	}
	
	/**
	 * Check if the input is a palindrome or not
	 * > Check for even and odd axis of the palindrome
	 */
	private boolean isPalindrome(String input) {
		boolean isEven = (input.length() % 2 == 0);
		int centerIdx = isEven ? (input.length() / 2) : ((input.length() - 1) / 2);
		boolean isPalindrome = true;
		
		char[] chArr = input.toCharArray();
		for(int i=0; i<centerIdx; i++) {
			if(chArr[i] != chArr[input.length()-1-i]) {
				isPalindrome = false;
				break;
			}
		}
		
		System.out.println("Is Palindrome => " + isPalindrome);
		return isPalindrome;
	}
	
	private int[] primeFactors(int num) {
		return null;
	}
	
	/**
	 * Fibonacci numbers
	 * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55
	 */
	private List<Integer> fibonacciSeries(List<Integer> list, int depth) {
		if(list.size() == depth) {
			System.out.println("Fibonacci series => " + list);
			return list;
		}
		
		if(list.size() == 0) {
			list.add(1);
			list.add(1);
		} else {
			list.add(list.get(list.size()-2) + list.get(list.size()-1));
		}
		
		return this.fibonacciSeries(list, depth);
	}
	
	/**
	 * Second smallest integer in a given list.
	 * Sort and get second in the list
	 */
	
	private void secondSmallestInteger(int[] inputArr) {
		for(int i=0; i<inputArr.length; i++) {
			int smallIdx = i;
			for(int j=inputArr.length-1; j>i; j--) {
				if(inputArr[j] < inputArr[smallIdx])
					smallIdx = j;
			}
			
			int temp = inputArr[i];
			inputArr[i] = inputArr[smallIdx];
			inputArr[smallIdx] = temp;
		}
		
		System.out.print("Sorted array => ");
		for(int i=0; i<inputArr.length; i++)
			System.out.print(inputArr[i] + ",");
		
		System.out.println("\nSecond smallest integer => " + inputArr[1]);
	}
	
	/**
	 * Find the first non-repeating character in an array
	 * Array = [a, b, c, a, d, e, b, c]
	 */
	private char firstNonRepeatingChar(String inputString) {
		LinkedHashMap<Character, Integer> lMap = new LinkedHashMap<Character, Integer>();
		char[] charArr = inputString.toCharArray();
		
		for(int i=0; i<charArr.length; i++) {
			if(lMap.containsKey(charArr[i]))
				lMap.put(charArr[i], lMap.get(charArr[i]) + 1);
			else
				lMap.put(charArr[i], 1);
		}
		
		Character c = null;
		Iterator<Character> keyIter = lMap.keySet().iterator();
		while(keyIter.hasNext() && lMap.get(c = keyIter.next()) != 1);

		System.out.println("First non-repeating character is => " + c);
		return (c != null) ? c : (char)-1;
	}

	/**
	 * Check if the given number is a power of 10
	 */
	
	private boolean isPowerOf10(int input) {
		while(input%10 == 0 && input > 10)
			input = input/10;
		
		boolean isPowerOf10 = (input == 10);
		
		System.out.println("Is power of 10 => " + isPowerOf10);
		return isPowerOf10;
	}
}
