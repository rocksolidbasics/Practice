package general;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a string, find the characters which come at a given index, during each
 * sort iteration of the string
 *
 */

public class ItemPositionDuringSort {

	public static void main(String[] args) {
		ItemPositionDuringSort pSort = new ItemPositionDuringSort();
		pSort.doSortAndPrint(1, "COMPUTERO");
	}

	private void doSortAndPrint(int loc, String string) {
		//Using selection sort
		Set<Character> charSet = new HashSet<Character>();
		char[] chArr = string.toCharArray();
		int curIdx;
		
		System.out.print("Character at location " + loc + " during sort: ");
		
		for(int i=0; i<chArr.length; i++) {
			curIdx = i;
			//Matcher
			for(int j=chArr.length-1; j>i; j--) {
				if(chArr[curIdx] > chArr[j])
					curIdx = j;
			}
			
			//Swap
			char tmpChar = chArr[i];
			chArr[i] = chArr[curIdx];
			chArr[curIdx] = tmpChar;
			
			//Positional check
			if(!charSet.contains(chArr[loc])) {
				charSet.add(chArr[loc]);
				System.out.print(chArr[loc] + ", ");
			}
		}
		
		System.out.println("\nSorted result: " + new String(chArr));
	}
}
