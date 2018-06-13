package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

public class PhaserRunner {

	public static void main(String[] args) throws InterruptedException {

		List<Runnable> tasks = new ArrayList<Runnable>();

		for (int i = 0; i < 2; i++) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					int a = 0, b = 1;
					for (int i = 0; i < 2000000000; i++) {
						a = a + b;
						b = a - b;
					}
				}
			};

			tasks.add(runnable);

		}

		new PhaserRunner().runTasks(tasks);

	}

	void runTasks(List<Runnable> tasks) throws InterruptedException {

		final Phaser phaser = new Phaser(1) {
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("onAdvance - Phase => " + phase + ", registeredParties => " + registeredParties);
				System.out.println("onAdvance <= " + (phase >= 1 || registeredParties == 0));
				
				return phase >= 1 || registeredParties == 0;
			}
		};

		int counter = 0;
		for (final Runnable task : tasks) {
			counter++;
			phaser.register();
			System.out.println("Register called " + counter);
			new Thread("T" + counter) {
				public void run() {
					do {
						System.out.println(Thread.currentThread().getName() + " called phaser arriveAndAwaitAdvance()");
						phaser.arriveAndAwaitAdvance();
						System.out.println(Thread.currentThread().getName() + " about to run");
						task.run();
					} while (!phaser.isTerminated());
				}
			}.start();
			Thread.sleep(500);
		}

		System.out.println("About to deregister");
		phaser.arriveAndDeregister();
		//phaser.arriveAndDeregister();
	}

}