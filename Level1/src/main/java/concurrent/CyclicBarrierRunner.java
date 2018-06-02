package concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Notes:
 * > CyclicBarrier.reset() can be used to reset the barrier back to zero while
 * the barrier is somewhere in between. This can be done based on some condition
 * > Set of CyclicBarriers can be used to implement a reducing cascade set like
 * 		T1 -> | 
 * 		T2 -> | -> CBA(3) -> CBFinisherThread -> await() ->  |
 * 		T3 -> | 											 |
 * 															 | CB(2) -> CBFinisherThread
 * 		T4 -> |												 |
 * 		T5 -> | -> CBB(3) -> CBFinisherThread -> await() ->  |
 * 		T6 -> |
 */

public class CyclicBarrierRunner {

	public static void main(String[] args) {
		CyclicBarrierRunner cbr = new CyclicBarrierRunner();
		cbr.demoCBR();
	}
	
	private void demoCBR() {
		CyclicBarrier cascBar = new CyclicBarrier(5);
		
		Runnable bfa = new Runnable() {
			@Override
			public void run() {
				System.out.println("Barrier 1 action executed");
				
				try {
					cascBar.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		};
		
		CyclicBarrier cbar = new CyclicBarrier(10, bfa);
		
		for(int i=1; i<=10; i++) {
			FileSorter fs = new FileSorter("T"+i, cbar);
			Thread t1 = new Thread(fs);
			t1.start();
		}
	}
	
	private static class FileSorter implements Runnable {
		private String name;
		private CyclicBarrier cbar;
		
		public FileSorter(String name, CyclicBarrier cb) {
			this.name = name;
			this.cbar = cb;
		}
		
		@Override
		public void run() {
			System.out.println("Finished work on thread => " + name);
			try {
				Random r = new Random(10);
				Thread.sleep(r.nextInt(1000));
				
				cbar.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}

			System.out.println("Barrier released => " + name);
		}
	}

}
