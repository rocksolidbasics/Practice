package lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Scratchpad {

	private List<Person> ps;
	private List<Integer> numList = Arrays.asList(1,34,5,6,7,4,677,2);
	
	{
		Person p1 = new Person("A1", "B1", 20);
		Person p2 = new Person("A2", "B2", 30);
		Person p3 = new Person("A3", "B3", 10);
		Person p4 = new Person("A4", "B4", 10);
		Person p5 = new Person("A5", "B5", 13);
		Person p6 = new Person("A6", "B6", 15);
		Person p7 = new Person("A7", "B7", 15);
		Person p8 = new Person("A8", "B8", 62);
		ps = new ArrayList<Person>();
		ps.add(p1);
		ps.add(p2);
		ps.add(p3);
		ps.add(p4);
		ps.add(p5);
		ps.add(p6);
		ps.add(p7);
		ps.add(p8);
	}
	
	public static void main(String[] args) {
		Scratchpad s1 = new Scratchpad();
		s1.getSpliteratorFromCollectionIf();
		s1.sortValues();
		s1.sortValuesNonnatural();
		s1.collectTest();
		s1.minmax();
		s1.reduce();
		s1.mergeStreams();
	}

	private void getSpliteratorFromCollectionIf() {
		Spliterator<Integer> spliterator = numList.spliterator();
		Stream<Integer> stream = StreamSupport.stream(spliterator, true);
		Stream<Integer> filter = stream.filter(i -> i > 6);
		filter.forEach(i -> System.out.print(i + ","));
		System.out.println();
	}
	
	private void sortValues() {
		IntStream distinct = numList.stream().mapToInt(i -> i + 2).distinct();
		IntStream sorted = distinct.sorted();
		sorted.forEach(i -> System.out.print(i + ","));
		System.out.println();
	}
	
	private void sortValuesNonnatural() {
		ps.stream().sorted(Comparator.comparing(p -> p.getAge()))
			.forEach(p -> System.out.println(p));
		System.out.println();
	}
	
	private void collectTest() {
		ps.stream().filter(p -> p.getAge() > 20)
				.collect(Collectors.toList())
				.forEach(p -> System.out.println(p.getName()));
	}
	
	private void minmax() {
		Optional<Person> min = ps.stream().min(Comparator.comparing(p -> p.getAge()));
		System.out.println(min.get().age);
	}
	
	private void reduce() {
		BinaryOperator<String> bo = (p1, p2) -> p1 + "-" + p2;
		Stream<String> map = ps.stream().map(p -> p.getName());
		Optional<String> reduce = map.reduce(bo);
		System.out.println(reduce.get());
	}
	
	private void infiniteStream() {
		IntStream iterate = IntStream.iterate(0, i -> i + 2);
		Stream<Integer> limit = iterate.mapToObj(Integer::valueOf).limit(10);
		limit.collect(Collectors.toList());
		
		Stream<Integer> iterate2 = Stream.iterate(0, i -> i+2);
		Stream<Integer> limit2 = iterate2.map(Integer::valueOf).limit(10);
		limit2.collect(Collectors.toList());
		
		Stream<Integer> generate = Stream.generate(() -> (new Random()).nextInt(100));
		Stream<Integer> limit3 = generate.limit(10);
		List<Integer> collect = limit3.collect(Collectors.toList());
	}
	
	private void fileReader() {
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get("C:/temp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Optional<String> findFirst = lines.filter(s -> s.contains("password")).findFirst();
		if(findFirst.isPresent())
			System.out.println(findFirst.get());
		
		lines.close();
	}
	
	private void mergeStreams() {
		Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5);
		Stream<Integer> stream2 = Stream.of(6, 7, 8);
		
		Stream<Integer> concatStream = Stream.concat(stream1, stream2);
		System.out.println();
		concatStream.forEach(i -> System.out.print(i));
	}
	
	private class Person {
		private String name;
		private String surname;
		private int age;
		
		public Person(String name, int age) {
			this.setName(name);
			this.setAge(age);
		}
		
		public Person(String name, String surname, int age) {
			this(name, age);
			this.surname = surname;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
		public String toString() {
			return "" + age;
		}
	}

}
