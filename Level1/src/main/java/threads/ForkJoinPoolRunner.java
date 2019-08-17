package threads;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import threads.forkjoin1.ForkJoinTask;

public class ForkJoinPoolRunner {

	/**
	 * Using the RecursiveAction (extends ForkJoinTask class) to create the
	 * tasks recursively using the task breaking by half criteria
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stream<Integer> numStream = Stream.generate(() -> (new Random()).nextInt(100))
				.limit(100000000);
		List<Integer> numList = numStream.collect(Collectors.toList());
		
		numList.stream().limit(10).forEach(n -> System.out.print(n + ","));
		System.out.println("");
		
		ForkJoinTask rootTask = new ForkJoinTask(numList, 0, numList.size(), 2);
		
		ForkJoinPool pool = new ForkJoinPool();
		System.out.println("Waiting...");

		//Execute method will run the root task asynchronously, whereas the invoke
		//method will cause the main thread to wait and hence the program will show
		//the 'waiting' message till the rootTask is completed
		pool.execute(rootTask);
		//pool.invoke(rootTask);
		
		do {
			System.out.printf("Main: Thread count: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Thread steal: %d\n", pool.getStealCount());
			System.out.printf("Main: Prallelism: %d\n", pool.getParallelism());
			
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		} while(!rootTask.isDone());
		
		pool.shutdown();
		
		if(rootTask.isCompletedNormally()) {
			System.out.println("Main: The process has completed normally.\n");
		}
		
		numList.stream().limit(10).forEach(n -> System.out.print(n+","));
		System.out.println("");
		
		System.out.println("Main: End of program. \n");
	}

}
