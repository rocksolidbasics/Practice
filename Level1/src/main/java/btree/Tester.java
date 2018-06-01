package btree;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Tester {
	
    /**
     * Complete the function below. abcde
     * DO NOT MODIFY anything outside this method. 
     */
    static boolean[] twins(String[] a, String[] b) {
        boolean[] result = new boolean[a.length];
        
        //Constraint 1 and looping through the input arrays
        for(int i=0; i<a.length && i<10*10*10; i++) {
        	//Constraint 2
        	if((a[i].length() > 100 || b[i].length() > 100) ||
        			a[i].length() != b[i].length()) {
        		result[i] = false;
        		continue;
        	}
        	
        	//For two character strings
        	if(a[i].length() == 2) {
        		result[i] = a[i].equals(b[i]);
        		continue;
        	}
        	
        	//Get the characters in each array
        	char[] aChars = a[i].toCharArray();
        	char[] bChars = b[i].toCharArray();

        	//Create arrays for odd and even strings
        	char[] evenCharsA = new char[aChars.length/2];
            char[] oddCharsA = new char[(aChars.length%2==0) ? (aChars.length/2) : (aChars.length/2)+1];
            char[] evenCharsB = new char[evenCharsA.length];
            char[] oddCharsB = new char[oddCharsA.length];
            
            //Segregate even and odd characters into separate arrays for both strings
            oddCharsA[0] = aChars[0];
            evenCharsA[0] = aChars[1];
            oddCharsB[0] = bChars[0];
            evenCharsB[0] = bChars[1];
            
        	for(int j=2; j<aChars.length; j+=2) {
        		oddCharsA[j/2] = aChars[j];
        		oddCharsB[j/2] = bChars[j];
        		if(j+1 < aChars.length) {
        			evenCharsA[j/2] = aChars[j+1];
        			evenCharsB[j/2] = bChars[j+1];
        		}
        	}

        	//Sort the odd and even strings
        	Arrays.sort(oddCharsA);
        	Arrays.sort(evenCharsA);
        	Arrays.sort(oddCharsB);
        	Arrays.sort(evenCharsB);
        	
        	//Match the odd and even string for both string a and string b
        	result[i] = (String.valueOf(oddCharsA).equals(String.valueOf(oddCharsB)) &&
        			String.valueOf(evenCharsA).equals(String.valueOf(evenCharsB)));
        }
        
        return result;
    }
    
    /**
     * DO NOT MODIFY THIS METHOD!
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        
        int n = Integer.parseInt(in.nextLine().trim());
        String[] a = new String[n];
        for(int i = 0; i < n; i++) {
            a[i] = in.nextLine();
        }
        
        int m = Integer.parseInt(in.nextLine().trim());
        String[] b = new String[m];
        for(int i = 0; i < m; i++) {
            b[i] = in.nextLine();
        }
        
        // call twins function
        boolean[] results = twins(a, b);
        
        for(int i = 0; i < results.length; i++) {
            System.out.println(results[i]? "Yes" : "No");
        }
    }
}
