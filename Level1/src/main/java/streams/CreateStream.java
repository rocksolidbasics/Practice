package streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public class CreateStream {

	public static void main(String[] args) {
		CreateStream cs = new CreateStream();
		cs.createFromArray();
		cs.createFromList();
		cs.createUsingStreamBuilder();
		cs.forEachIteration();
		cs.mapOperation();
		cs.flatMapping();
		cs.filter();
		//cs.findFirst();
		//cs.toArray();
		cs.peek();
		cs.subractOperation();
		//Short-circuiting operations
		cs.skipAndLimit();
		cs.infiniteStreamGeneration();
		cs.sort();
		cs.minMax();
		cs.distinct();
		cs.matchOps();
		//Specialized streams - IntStream, LongStream, and DoubleStream
		cs.intStreamViaMap();
		cs.viaIntStreamOf();
		cs.viaIntStreamRange();
		//Reduce operation
		cs.reduceSum();
	}
	
	private void reduceSum() {
		List<Integer> intList = this._getSampleIntData();
		Integer reduceSum = intList.stream().reduce(0, Integer::sum);
	}

	/**
	 * Creates an IntStream object
	 */
	private void viaIntStreamRange() {
		IntStream range = IntStream.range(5, 21);
		long count = range.count();
	}

	/**
	 * Creates an IntSream object
	 */
	private void viaIntStreamOf() {
		IntStream intStream = IntStream.of(1,2,3,4,5,6,7,8,9,10);
		IntSummaryStatistics summaryStatistics = intStream.summaryStatistics();
		long sum = summaryStatistics.getSum();
	}

	/**
	 * Returns a Stream<Integer>, not an IntStream
	 */
	private void intStreamViaMap() {
		List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6");
		list.stream().mapToInt(s -> Integer.parseInt(s)).average();
	}


	private void matchOps() {
		List<Integer> intList = this._getSampleIntData();
		boolean anyMatch = intList.stream().anyMatch(i -> i%2 == 0);
		//Similarly, allMatch() and noneMatch()
	}


	private void distinct() {
		List<Integer> intList = this._getSampleIntData();
		intList.add(2);
		intList.stream().distinct().collect(Collectors.toList());
	}


	private void minMax() {
		List<String> list = this._getSampleStringData();
		Optional<String> minVal = list.stream().sorted().min((e1, e2) -> e1.compareTo(e2));
		minVal.orElseThrow(NoSuchElementException::new);
		
		System.out.println("------ Minimum value ------ \n" + minVal.get());
	}


	private void sort() {
		List<String> list = this._getSampleStringData();
		List<String> sList = list.stream().sorted((e1, e2) -> e1.compareTo(e2)).collect(Collectors.toList());
		System.out.println("------ Sorting ------ \n" + sList);
	}


	private void infiniteStreamGeneration() {
		Stream<Integer> infStream = Stream.iterate(2, i -> i * 2);
		List<Integer> outList = infStream.skip(10).limit(20).collect(Collectors.toList());
		System.out.println("------- Infinite list with skip and limit ------");
		System.out.println(outList);
	}


	private void skipAndLimit() {
		List<String> list = this._getSampleStringData();
		List<String> outList = list.stream().skip(2).limit(5).collect(Collectors.toList());
		System.out.println("----- Skip and Limit ------");
		System.out.println(outList);
	}


	//TODO: How to do windowed subtract opreation
	private void subractOperation() {
		List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5 , 6);
	}



	private void peek() {
		System.out.println("------ Peek output ------");
		int sum = this._getSampleStringData().stream().mapToInt(e -> { return e.length(); }).sum();
		System.out.println("Sum => " + sum);
	}

	private void flatMapping() {
		List<List<String>> listOfList = Arrays.asList(Arrays.asList("Name1", "sn1"),
				Arrays.asList("Name2", "sn2"), Arrays.asList("Name3", "sn3"));
		List<String> flatList = listOfList.stream().flatMap(Collection::stream).collect(Collectors.toList());
		System.out.println(flatList);
	}
	
	private void filter() {
		List<String> newList = this._getSampleStringData().stream().filter(v -> v.equals("Two")).collect(Collectors.toList());
		System.out.println(newList);
	}

	/**
	 * Converting TypeA -> TypeB and generating a new Stream of objects from the 
	 * source stream. The collect() method collects a list from the newly generated
	 * stream
	 */
	private void mapOperation() {
		List<String> lst = new LinkedList<String>();
		lst.add("1");
		lst.add("2");
		lst.add("3");
		
		EmployeeRepo employeeRepo = new EmployeeRepo();
		List<String> employees = lst.stream().map(s -> { return Integer.parseInt(s); }).map(employeeRepo::findById).collect(Collectors.toList());
		System.out.println(employees);
	}
	
	private List<Integer> _getSampleIntData() {
		List<Integer> lst = new LinkedList<Integer>();
		lst.add(1);
		lst.add(2);
		lst.add(3);
		lst.add(4);
		lst.add(5);
		lst.add(6);
		lst.add(7);
		lst.add(8);
		lst.add(9);
		lst.add(10);
		lst.add(11);
		lst.add(12);
		lst.add(13);
		lst.add(14);
		lst.add(15);
		return lst;
	}

	private List<String> _getSampleStringData() {
		List<String> lst = new LinkedList<String>();
		lst.add("One");
		lst.add("Two");
		lst.add("Three");
		lst.add("Four");
		lst.add("Five");
		lst.add("Six");
		lst.add("Seven");
		lst.add("Eight");
		lst.add("Nine");
		lst.add("Ten");
		lst.add("Eleven");
		lst.add("Twelve");
		lst.add("Thirteen");
		lst.add("Fourteen");
		lst.add("Fifteen");
		return lst;
	}

	/**
	 * - forEach takes in a Consumer function as its argument and does
	 * some operations using the input value, but won't return back
	 * any results. However, it can modify the input value
	 * - A stream pipe can't be re-used after a forEach operation 
	 * has executed on the stream
	 */
	private void forEachIteration() {
		List<String> list = this._getSampleStringData();
		list.forEach(m -> System.out.println(m));
	}

	private void createUsingStreamBuilder() {
		Builder<String> builder = Stream.builder();
		builder.accept("One");
		builder.accept("Two");
		builder.accept("Three");
		Stream<String> build = builder.build();
	}

	private void createFromList() {
		List<String> lst = new LinkedList<String>();
		lst.add("One");
		lst.add("Two");
		lst.add("Three");
		Stream<String> stream = lst.stream();
	}

	private void createFromArray() {
		String[] strArr = { "One", "Two", "Three" };
		Stream<String> strStream = Stream.of(strArr);
	}
	
	private static class EmployeeRepo {
		
		private Map<Integer, String> repo = new HashMap<Integer, String>();
		
		public EmployeeRepo() {
			repo.put(1, "E1");
			repo.put(2, "E2");
			repo.put(3, "E3");
			repo.put(4, "E4");
		}
		
		public String findById(Integer id) {
			return repo.get(id);
		}
	}

}
