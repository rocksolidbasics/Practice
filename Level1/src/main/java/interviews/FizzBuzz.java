package interviews;

import java.util.Scanner;
import java.util.StringTokenizer;

public class FizzBuzz {

	public static void main(String args[]) throws Exception {
		Scanner s = new Scanner(System.in);
		String tStr = s.nextLine();
		int nCount = Integer.parseInt(tStr);

		String numStr = s.nextLine();
		StringTokenizer strTokenizer = new StringTokenizer(numStr, " ");

		while (strTokenizer.hasMoreTokens()) {
			int i = Integer.parseInt(strTokenizer.nextToken());

			for (int j = 1; j <= i; j++) {
				System.out.println((j % 3 == 0) ? "Fizz" : ((j % 5 == 0) ? "Buzz" : ((j % 15 == 0) ? "FizzBuzz" : j)));
			}
		}
	}
}
