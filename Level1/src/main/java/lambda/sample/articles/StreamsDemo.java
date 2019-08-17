/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda.sample.articles;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author mmueller
 */
public class StreamsDemo {

  public static void main(String[] args) {

    //showParallelProblem();
    System.out.println("started");
    Persons persons = Persons.getInstance();
    System.out.println("created " + persons.getPersons().size() + " persons.");
    //countVendors(persons.getPersons());
    listing7(persons.getPersons());
  }

  private static void showParallelProblem() {
    long[] result = new long[1];

    for (int i = 0; i < 10; i++) {
      result[0] = 0;
      LongStream.range(0, 1000).forEach(n -> result[0] = (result[0] + n) * n);
      System.out.println("serial: " + result[0]);
    }

    for (int i = 0; i < 10; i++) {
      result[0] = 0;
      LongStream.range(0, 1000).parallel().forEach(n -> result[0] = (result[0] + n) * n);
      System.out.println("parallel: " + result[0]);
    }

    for (int i = 0; i < 10; i++) {
      result[0] = 0;
      LongStream.range(0, 1000).parallel().forEachOrdered(n -> result[0] = (result[0] + n) * n);
      System.out.println("parallel ordered: " + result[0]);
    }

    long reduce = LongStream.range(0, 1000).reduce(0, (a, c) -> (a + c) * c);
    System.out.println("reduce: " + reduce);
  }

  private static void listing7(List<Person> person) {
	  //All females age below 20
	  Stream<Person> filterAge = person.stream().filter(p -> p.getAge() < 20);
	  Stream<Person> filterGender = filterAge.filter(p -> p.isFemale());
	  List<Person> filterPerson = filterGender.collect(Collectors.toList());
	  System.out.println("Female age below 20: " + filterPerson.size());
	  
	  //Average age
	  filterAge = person.stream().filter(p -> p.getAge() < 20);
	  filterGender = filterAge.filter(p -> p.isFemale());
	  IntStream mapToInt = filterGender.mapToInt(p -> p.getAge());
	  OptionalDouble average = mapToInt.average();
	  System.out.println(average.getAsDouble());
	  
	  //Total count of selling
	  long countSelling = person.stream().filter(Person::isVendor)
	  	.mapToLong(p -> p.getSelling().values().stream()
	  			.mapToLong(ArticleInfo::getQuantity).sum())
	  	.sum();
	  System.out.println("Total selling: " + countSelling);
	  long countFlatMap = person.stream().filter(Person::isVendor)
			  .flatMapToLong(p -> p.getSelling().values().stream()
					  .mapToLong(ArticleInfo::getQuantity))
			  .sum();
	  System.out.println("Total selling flat map: " + countFlatMap);
	  
	  Supplier<Long> c1 = () -> person.parallelStream().filter(Person::isVendor)
			  .mapToLong(p -> p.getSelling().values().stream()
					  .mapToLong(ArticleInfo::getQuantity).sum()).sum();
	  invokeMethod("Time withouth flat map => ", c1);
	  
	  Supplier<Long> c2 =() -> person.parallelStream().filter(Person::isVendor)
			  .flatMapToLong(p -> p.getSelling().values().stream()
					  .mapToLong(ArticleInfo::getQuantity)).sum();
	  invokeMethod("Time with flat map => ", c2);
  }
  
  private static void countVendors(List<Person> persons) {
    invokeMethod("Vendors by counting list", () -> getVendorCount(persons));
    
    Supplier<Long> countByStream = () -> persons.stream().filter(p -> p.isVendor()).collect(Collectors.counting());
    invokeMethod("Vendors by Stream", countByStream);
    Supplier<Long> countByParallelStream = () -> persons.parallelStream().filter(p -> p.isVendor()).collect(Collectors.counting());
    invokeMethod("Vendors by ParallelStream", countByParallelStream);
  }

  private static long getVendorCount(List<Person> persons) {
    int counter = 0;
    for (Person person : persons) {
      if (person.isVendor()) {
        counter++;
      }
    }
    return counter;
  }

  private static <T> T invokeMethod(String info, Supplier<T> method) {
    long start = System.nanoTime();
    T result = method.get();
    long elapsedTime = System.nanoTime() - start;
    System.out.println(info + ": " + elapsedTime/1000);
    return result;
  }

}
