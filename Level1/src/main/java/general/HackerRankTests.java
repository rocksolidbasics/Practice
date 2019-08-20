package general;

import java.util.ArrayList;
import java.util.List;

public class HackerRankTests {

	public static void main(String[] args) {
		// Test 2
		int sum = simpleArraySum(new int[] { 10, 7, 1 });
		System.out.println(sum);

		// Test 3

	}

	static int diagonalDifference(List<List<Integer>> arr) {
		return -1;
	}

	static int simpleArraySum(int[] ar) {
		int i = 0, t = 0;
		while (i < ar.length && (t += ar[i++]) > -1)
			;

		return t;
	}

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
