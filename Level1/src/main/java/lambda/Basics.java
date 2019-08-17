package lambda;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class Basics {
	
	public static void main(String[] args) {
		Basics b = new Basics();
		b.typeInference();
		
		System.out.println(1 << 10);
		System.out.println(1 << 25);
		System.out.println(0b10000);
	}
	
	public void lambdaRunnable() {
		Basics b = new Basics();
		
		int counter = 0;
		
		for(;counter <= 10; counter++) {
			//The final keyword can be omitted, but is implicit (its required)
			final int threadVal = counter;
			b.runner(() -> {
				String s = threadVal+"";
				System.out.println("Hello " + threadVal);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		
		//2nd way to declare lambda
		Runnable r2 = () -> System.out.println("Hello2");
		
		//Input type inference
		BinaryOperator<Long> add = (x, y) -> x + y;
		
		//Explicit values specified for the input type
		BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
	}
	
	public void runner(Runnable r) {
		Thread t = new Thread(r);
		t.setPriority(5);
		t.start();
	}
	
	//Type inference
	public void typeInference() {
		Predicate<Integer> atLeast5 = x -> x > 5;
		long count = Arrays.asList(4, 5, 6, 4).stream().filter(atLeast5).count();
		System.out.println("Filter count = " + count);
		
		//Long is inferred on the arguments x and y based on the type for the variable
		//addLongs. As BinaryOperator class only has one generic formal type parameter <T>
		//and the declaration below is having <Long>, compiler can statically type the value
		//of x and y as of type Long
		BinaryOperator<Long> addLongs = (x, y) -> x + y;
		long addVal = addLongs.apply(3l, 4l);
		System.out.println("BinaryOperator implementation (lambda) called: " + addVal);
		
		//The example below won't compile because the compiler can't guess the type of x and
		//y from the variable declaration
		//BinaryOperator addLongErrored = (x, y) -> x + y;
	}

}
