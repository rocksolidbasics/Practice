package threads.general;

public class SequencedThreadRun {

	public static void main(String[] args) {
		SequencedThreadRun r = new SequencedThreadRun();
		TestThread tt1 = new TestThread(null);
		Thread t1 = new Thread(tt1);
		t1.setName("T1");
		TestThread tt2 = new TestThread(tt1);
		Thread t2 = new Thread(tt2);
		t2.setName("T2");
		TestThread tt3 = new TestThread(tt2);
		Thread t3 = new Thread(tt3);
		t3.setName("T3");
		
		t3.start();

		t2.start();
		
		t1.start();
	}
	
	private static class TestThread implements Runnable {
		
		private TestThread prevThread;
		public volatile boolean prevRan = false;

		private TestThread(TestThread prevThread) {
			this.prevThread = prevThread;
		}
		
		public void run() {
			if(prevThread == null) {
				synchronized(this) {
					System.out.println(Thread.currentThread().getName());
					this.notify();
					prevRan = true;
				}
			} else {
				synchronized(prevThread) {
					try {
						System.out.println(Thread.currentThread().getName() + " waiting...");
						if(!prevThread.prevRan)
							prevThread.wait();
						
						System.out.println(Thread.currentThread().getName());
						synchronized(this) {
							this.notify();
							prevRan = true;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
