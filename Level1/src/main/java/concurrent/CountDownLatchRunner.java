package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Countdown latch is different from cyclic barrier in the sense that 
 * > Cyclic barrier can be reset back to count zero, where latch cannot.
 * > In Cyclic barrier the 'await()' does an implicit countdown, whereas in latch an explicit countdown()
 * has to be called by the worker methods to release any waiting threads on the await()
 * > In latch there can be multiple notifying threads (which may call the countdown()) as well as multiple
 * waiting threads which carry on when count is zero. In a barrier there can only be one thread which gets
 * executed after the countdown reaches zero.
 *
 */

public class CountDownLatchRunner {

	public static void main(String[] args) {
		CountDownLatchRunner cdl = new CountDownLatchRunner();
		cdl.runLatchRunner(10);
	}

	/**
	 * Start N threads, and wait till they finish a task, then carry on with another task
	 */
	private void runLatchRunner(int nTasks) {
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch stopSignal = new CountDownLatch(nTasks);
		
		for(int i=0; i<nTasks; i++) {
			Thread t = new Thread(new Worker(startSignal, stopSignal));
			t.setName("T" + i);
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
		}
		
		System.out.println("Workers started, ready to go");
		try {
			System.out.println("Main doing some setup");
			startSignal.countDown();
			System.out.println("Done sending start signal to workers");
			stopSignal.await();
			System.out.println("Main doing finishing tasks");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All workers finished");
	}
	
	private class Worker implements Runnable {
		private CountDownLatch startSig;
		private CountDownLatch stopSig;
		
		public Worker(CountDownLatch start, CountDownLatch stop) {
			this.startSig = start;
			this.stopSig = stop;
		}
		
		@Override
		public void run() {
			System.out.println("Thread " + Thread.currentThread().getName() + " waiting for start signal");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			try {
				startSig.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Thread " + Thread.currentThread().getName() + " working");
			
			stopSig.countDown();
		}
	}
}
