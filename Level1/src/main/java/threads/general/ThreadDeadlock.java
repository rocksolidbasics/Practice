package threads.general;

public class ThreadDeadlock {
	
	public static void main(String[] args) {
		ThreadDeadlock tl = new ThreadDeadlock();
		tl.threadDeadlock();
	}

	private void threadDeadlock() {
		Integer i = new Integer(1);
		Object elock = new Object();
		Object olock = new Object();
		TestClass cEven = new TestClass(i, olock, elock, false);
		TestClass cOdd = new TestClass(i, olock, elock, true);
		Thread evenT = new Thread(cEven);
		Thread oddT = new Thread(cOdd);
		evenT.start();
		oddT.start();
	}
	
	private class TestClass implements Runnable {
		
		private Object eLock = null;
		private Object oLock = null;
		private Integer c = null;
		private boolean isOdd = false;
		
		public TestClass(Integer counter, Object oLock, Object eLock, boolean isOdd) {
			this.eLock = eLock;
			this.oLock = oLock;
			this.c = counter;
		}
		
		public void run() {
			while(true) {
				if(isOdd) {
					synchronized(eLock) {
						System.out.println(c++);
						synchronized(oLock) {
							oLock.notify();
						}
						
						try {
							eLock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					synchronized(oLock) {
						System.out.println(c++);
						synchronized(eLock) {
							eLock.notify();
						}
						try {
							oLock.wait();
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
