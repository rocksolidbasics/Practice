package threads.general;

public class ThreadOddEven {

	public static void main(String[] args) {
		ThreadOddEven toe = new ThreadOddEven();
		toe.oddEvenPrint();
	}
	
	private void oddEvenPrint() {
		Integer i = new Integer(0);
		Object lock = new Object();
		Counter co = new Counter(i, lock, true);
		Counter ce = new Counter(i, lock, false);
		Thread to = new Thread(co);
		Thread te = new Thread(ce);
		to.setName("Odd-thread");
		te.setName("Even thread");
		to.start();
		te.start();
	}

	private static class Counter implements Runnable {
		
		private Object lock = null;
		private static volatile int c = 0;
		private boolean isOdd = false;
		private boolean isOddDone = false;
		private boolean isEvenDone = false;
		
		public Counter(int counter, Object lock, boolean isOdd) {
			this.lock = lock;
			c = counter;
			this.isOdd = isOdd;
		}
		
		public void run() {
			while(true) {
				if(c > 9)
					break;
				
				synchronized(this.lock) {
					System.out.println(isOdd?"O":"E");
					if(isOdd && (c%2==0 || c == 0)) {
						if(isOddDone) {
							try {
								this.lock.wait();
								isOddDone = false;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println("O" + (++c));
						isOddDone = true;
						this.lock.notify();
						continue;
					}
					
					if(!isOdd && (c%2!=0 || c == 1)) {
						if(isEvenDone) {
							try {
								this.lock.wait();
								isEvenDone = false;
							} catch(InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println("E" + (++c));
						isEvenDone = true;
						this.lock.notify();
					}
				}
			}
		}
	}
	
}
