package general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class HackerRankTests {
	
	private static boolean debug = false;

    public static void main(String[] args) {
    	debug = false;
    	
        // Test 1
        int sum = simpleArraySum(new int[] { 10, 7, 1 });
        debug(sum);
        
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
        debug(time);
        time = timeConversion("12:05:45AM");
        time = timeConversion("12:05:45PM");
        
        //Test 8
        List<Integer> grades = new ArrayList<Integer>();
        grades.add(20);
        grades.add(37);
        grades.add(38);
        grades.add(45);
        grades.add(44);
        grades.add(43);
        grades.add(42);
        List<Integer> r = gradingStudents(grades);
        debug("Grades: " + r);
        
        //Helper
        int[] a = new int[] {23,98,14,14,6,0};
        int[] b = new int[] {46,56,15,21,6,0};
        isCoPrime(a, b);
        
        //Test 9
        a = new int[] {2,5,6,7};
        b = new int[] {4,9,10,12};
		/*
		 * a = new int[100000]; b = new int[100000];
		 * 
		 * Random rnd = new Random(5);
		 * 
		 * for(int i=0; i<100000; i++) { a[i] = rnd.nextInt(1000000000); b[i] =
		 * rnd.nextInt(1000000000); }
		 */
        computerGame(a, b);
        
        //Test 10
        int[] apples = {-2, 2, 1};
        int[] oranges = {5, -6};
        countApplesAndOranges(7, 10, 5, 15, apples, oranges);
        
        //Test 11
        debug = true;
        debug(kangaroo(0, 3, 4, 2));	//YES
        debug(kangaroo(0, 2, 4, 2));	//N
        debug(kangaroo(0, 2, 0, 2));	//N
        debug(kangaroo(0, 10000, 0, 10000));	//N
        debug(kangaroo(10000, 1, 10000, 0));	//N
        debug(kangaroo(10000, 1, 10000, 1));	//N
        debug(kangaroo(10000, 0, 10000, 1));	//N
        //debug(kangaroo(5, 2, 3, 2));	//N
        //debug(kangaroo(5, 2, 3, 3));	//Y
        debug(kangaroo(2, 3, 5, 1));	//N
        debug("---");
        debug(kangaroo(0, 2, 0, 2));	//y
        debug(kangaroo(0,2,1,1));		//y
        debug(kangaroo(0,2,2,1));		//n
        debug(kangaroo(0,3,1,3));		//n
        debug(kangaroo(0,3,1,1));		//n
        debug(kangaroo(0,3,2,1));		//y
    }
    
    //Test 11
    static String kangaroo(int x1, int v1, int x2, int v2) {
		/*
		 * if((x1 > x2 && v1 > v2) || (x2 > x1 && v2 > v1) || (x1+v1 == x2) || (x2+v2 ==
		 * x1) || ((x2 > x1 && v2 > v1) && (x1+v2 > x2)) || ((x1 > x2 && v1 > v2) &&
		 * (x2+v1 > x1)) || (x1 != x2 && v1 == v2) || (x1+v1 > 10000 || x2+v2 > 10000))
		 * return "NO"; return "YES";
		 */
    	if((v2 > v1) 
    			|| (x1+v1 == x2)
                || (x1+v1 > x2+v2)
                || (x1+v1 < x2+v2)
           )
            return "NO";
        return "YES";
    }
    
    //Test 10
    static void countApplesAndOranges(int s, int t, int a, int b, int[] apples, int[] oranges) {
    	int app = 0;
    	int orn = 0;
    	for(int i=0; i<apples.length; i++) {
    		if(apples[i] > 0 && apples[i]+a >= s && apples[i]+a <= t)
    			app++;
    	}
    	for(int j=0; j<oranges.length; j++) {
    		if(oranges[j] < 0 && b + oranges[j] <= t && b + oranges[j] >= s)
    			orn++;
    	}
    	
    	debug(app);
    	debug(orn);
    }
    
    //Test 9 - Find non co-primes
    //Scenario 1:
    //2 5 6 7
    //4 9 10 12
    //Scenario 2:
    //2 5 6 17
    //10 25 3 7  (5-10) match won't maximize the result
    //Scenario 3:
    //20 5 6 17
    //10 25 3 7  (5-10) match won't maximize the result
    
    private static void computerGame(int[] a, int[] b) {
    	HashMap<Integer,List<Integer>> uniGraph = new HashMap<>();
    	HashMap<Integer, Integer> matchCounter = new HashMap<>();
    	
    	//Collect the combination of non co-prime pairs
		for(int i=0; i<a.length; i++) {
			List<Integer> nonCoPrimeList = new ArrayList<>();
			for(int j=0; j<b.length; j++) {
				//Check non co-prime
				if(!isCoPrime(a[i], b[j])) {
					//debug("a=" + a[i] + ", " + b[j]);
					nonCoPrimeList.add(b[j]);
					//Maintain count of matched pairs for each item on second array
					Integer cnt = matchCounter.get(b[j]);
					if(cnt == null) {
						matchCounter.put(b[j], 1);
					} else {
						matchCounter.put(b[j], cnt+1);
					}
				}
			}
			
			if(nonCoPrimeList.size() > 0)
				uniGraph.put(a[i], nonCoPrimeList);
		}
		
		List<Entry<Integer,List<Integer>>> sortedUniGraph = new ArrayList<Entry<Integer,List<Integer>>>();
		uniGraph.entrySet().forEach(e -> sortedUniGraph.add(e));
		
		Collections.sort(sortedUniGraph, new Comparator<Entry<Integer, List<Integer>>>() {
			@Override
			public int compare(Entry<Integer, List<Integer>> o1, Entry<Integer, List<Integer>> o2) {
				Entry<Integer,List<Integer>> a = (Entry<Integer,List<Integer>>)o1;
				Entry<Integer,List<Integer>> b = (Entry<Integer,List<Integer>>)o2;
				List<Integer> aL = a.getValue();
				List<Integer> bL = b.getValue();
				
				if(aL == null || bL == null)
					return 0;
				else if(aL.size() == bL.size())
					return 0;
				else if(aL.size() > bL.size())
					return 1;
				else
					return -1;
			}
			
		});
		
		sortedUniGraph.forEach(e -> {
			Collections.sort(e.getValue(), new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					Integer mc1 = matchCounter.get(o1);
					Integer mc2 = matchCounter.get(o2);
					
					if(mc1 != null && mc2 == null)
						return 1;
					else if(mc1 == null && mc2 != null)
						return -1;
					else if(mc1 == null || mc2 == null)
						return 0;
					else if(mc1 == mc2)
						return 0;
					else if(mc1 > mc2)
						return 1;
					else
						return -1;
				}
			});
		});
		
		debug(sortedUniGraph);
		debug(matchCounter);
		
		int resultCounter = 0;
		
		for(Entry<Integer,List<Integer>> ue : sortedUniGraph) {
			for(Integer v : ue.getValue()) {
				if(matchCounter.containsKey(v)) {
					resultCounter++;
					matchCounter.remove(v);
					break;
				}
			}
		}
		
		debug("Result: " + resultCounter);
	}
    
    static boolean isCoPrime(int a, int b) {
    	while(a > 1 && b > 1) {
    		if(a > b) {
    			a = a - b;
    		}
    		if(b > a) {
    			b = b - a;
    		}
    		if(a == b)
    			break;
    	}
    	
    	if(a > 1 && a == b)
    		return false;
    	else
    		return true;
    }

	//Test 9 - Co-primes
    static void isCoPrime(int[] a, int[] b) {
    	for(int i=0; i<a.length; i++) {
    		int aa = a[i];
    		int bb = b[i];
    		
    		while(aa > 1 && bb > 1) {
	    		if(aa > bb) {
	    			aa = aa-bb;
	    		}
	    		if(bb > aa) {
	    			bb = bb-aa;
	    		}
	    		if(aa == bb) {
	    			break;
	    		}
    		}
    		
    		//if(aa != bb || aa == 1 || bb == 1)
    		if(aa > 1 && aa == bb)
    			debug("N");
    		else
    			debug("Y");
    	}
    }
    
    //Test 8 - Grading students
    public static List<Integer> gradingStudents(List<Integer> grades) {
        List<Integer> rGrades = new ArrayList<Integer>();
        for(int i=0; i<grades.size(); i++) {
            int g = grades.get(i);
            if(g < 38)
                rGrades.add(g);
            else {
                if((g%5) > 2)
                    rGrades.add(g-(g%5)+5);
                else
                    rGrades.add(g);
            }
        }
        
        return rGrades;
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
            debug(arr[0] + " " + arr[0]);
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
        
        debug((small+sum) + " " + (sum+big));
    }
    
    //Test 5
    static void staircase(int n) {
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=n-i; j++)
                debug(" ");
            for(int k=1; k<=i; k++)
                debug("#");
            debug("");
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
        
        debugf("%.6f\n", (p/arr.length));
        debugf("%.6f\n", (n/arr.length));
        debugf("%.6f\n", (z/arr.length));
    }
    
    private static void initTest3() {
        List<List<Integer>> arr = new ArrayList<List<Integer>>();
        arr.add(Arrays.asList(new Integer[]{3, 2, 1}));
        arr.add(Arrays.asList(new Integer[]{2, 1, 3}));
        arr.add(Arrays.asList(new Integer[]{1, 2, 3}));
        int dd = diagonalDifference(arr);
        debug("Diagonal difference: " + dd);
    }

    //Test 3
    static int diagonalDifference(List<List<Integer>> arr) {
        int l = 0, r = 0;
        debug(arr.size());
        
        for(int i = 0; i<arr.size(); i++) {
            int j = arr.size()-1-i;
            
            l += arr.get(i).get(i);     //i=0 -> 0,0 / i=1 -> 1,1 / i=2 -> 2,2
            r += arr.get(i).get(j);     //j=2 -> 0,2 / j=1 -> 1,1 / j=0 -> 2,0
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
    
    private static void debug(Object message) {
    	if(debug) {
    		System.out.println(message);
    	}
    }
    
    private static void debugf(String format, Object message) {
    	if(debug) {
    		System.out.printf(format, message);
    	}
    }

}
