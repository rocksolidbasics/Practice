package threads.general;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiThreadFileSearch {
	
	ThreadPool pool;
	private Pattern searchPattern;
	private long fileCount;
	private long timeStart;

	public static void main(String[] args) throws FileNotFoundException {
		MultiThreadFileSearch mfs = new MultiThreadFileSearch();
		//mfs.init("C:\\Windows", "hosts");	//63 secs to 120 secs.
		//mfs.init("C:\\Users\\sesa513057", "v32.cab");	//225 secs.
		
		//mfs.initParallelSearch("C:\\Windows", "hosts");	//47 secs
		mfs.initParallelSearch("C:\\Users\\sesa513057", "v32.cab"); //42 secs.
	}

	private void initParallelSearch(String path, String searchRegex) throws FileNotFoundException {
		File f = new File(path);
		if(f == null || !f.exists() || f.isFile())
			throw new FileNotFoundException("Root folder not found");
		
		searchPattern = Pattern.compile(searchRegex);
		
		List<String> results = new ArrayList<>();
		pool = new ThreadPool(4);
		
		timeStart = System.currentTimeMillis();
		pool.addWork(new SearchJob(f));
	}

	private void init(String path, String searchRegex) throws FileNotFoundException {
		File f = new File(path);
		if(f == null || !f.exists() || f.isFile())
			throw new FileNotFoundException("Root folder not found");
		
		searchPattern = Pattern.compile(searchRegex);
		
		List<String> results = new ArrayList<>();
		
		long timeStart = System.currentTimeMillis();
		this.searchFile(f, results);
		timeStart = System.currentTimeMillis() - timeStart;
		
		System.out.println("Search results: " + results);
		System.out.println("Total file count: " + fileCount);
		System.out.println("Total time to search: " + (timeStart/1000) + " secs.");
	}
	
	private void searchFile(File rootFile, List<String> results) {
		File[] childFiles = rootFile.listFiles();
		
		if(childFiles == null) {
			//System.err.println("Null child files for - " + rootFile);
			return;
		}

		fileCount += childFiles.length;
		
		for(File f : childFiles) {
			if(f.isDirectory()) {
				searchFile(f, results);
			} else {
				Matcher matcher = searchPattern.matcher(f.getName());
				if(matcher.matches())
					results.add(f.getAbsolutePath());
			}
		}
	}
	
	private class ThreadPool {
		private boolean free = true;
		LinkedList<Worker> freeWorkers = new LinkedList<Worker>();
		ArrayBlockingQueue<SearchJob> jobQueue = new ArrayBlockingQueue<>(40);
		CountDownLatch cl = new CountDownLatch(4);
		
		public ThreadPool(int count) {
			for(int i=0; i<count; i++) {
				Worker w = new Worker(cl);
				Thread t = new Thread(w);
				t.setName("T-"+i);
				freeWorkers.add(w);
				t.start();
			}
			
			Thread monitoringThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while(true) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) { e.printStackTrace(); }
	
						/*System.out.println(freeWorkers.get(0).getUsage() + ", " +
								freeWorkers.get(1).getUsage() + ", " +
								freeWorkers.get(2).getUsage() + ", " +
								freeWorkers.get(3).getUsage());*/
						
						//System.out.println("Queue length: " + jobQueue.size());
						System.out.println("Search progress: " + fileCount);
					}
				}
			});
			monitoringThread.setDaemon(true);
			monitoringThread.start();
			
			Thread shutdownHook = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						cl.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					timeStart = System.currentTimeMillis() - timeStart;
					System.out.println("Total time to search: " + (timeStart/1000) + " secs.");
				}
			});
			shutdownHook.start();
			
			System.out.println("Monitoring thread started...");
		}
		
		public void addWork(SearchJob job) {
			synchronized(jobQueue) {
				jobQueue.add(job);
			}
		}
	
		private class Worker implements Runnable {
			private volatile boolean free = true;
			private volatile long usageCount = 0;
			private volatile long idleCount = 0;
			private CountDownLatch cl;
			
			public Worker(CountDownLatch cl) {
				this.cl = cl;
			}

			public void run() {
				while(true) {
					if(Thread.currentThread().isInterrupted())
						break;
					
					try {
						SearchJob job = ThreadPool.this.jobQueue.poll(1, TimeUnit.SECONDS);
						usageCount++;
						if(job != null) {
							idleCount = 0;
							job.search();
						} else {
							if(idleCount > 10) {
								System.out.println("Idle count reached. Stopping thread " + 
										Thread.currentThread().getName());
								cl.countDown();
								break;
							}
							
							if(ThreadPool.this.jobQueue.size() == 0) {
								idleCount++;
								//System.out.println("Idle count for thread " + Thread.currentThread().getName() +
										//" = "+ idleCount);
							}
						}
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
			
			public long getUsage() {
				return usageCount;
			}
		}
	}
	
	private class SearchJob {
		private File rootFile;
		private List<String> results;
		
		public SearchJob(File root) {
			this.rootFile = root;
		}

		public List<String> getResults() {
			return results;
		}

		public void setRootFile(File rootFile) {
			this.rootFile = rootFile;
		}
		
		public void search() {
			this._search(this.rootFile);
		}
		
		private void _search(File rootFile) {
			File[] childFiles = rootFile.listFiles();
			
			List<String> results = new ArrayList<String>();
			
			if(childFiles == null) {
				//System.err.println("Null child files for - " + rootFile);
				return;
			}
			
			fileCount += childFiles.length;
			
			for(File f : childFiles) {
				if(f.isDirectory()) {
					if(!pool.jobQueue.offer(new SearchJob(f)))
						_search(f);
				} else {
					Matcher matcher = searchPattern.matcher(f.getName());
					if(matcher.matches()) {
						System.out.println("Found match: " + f.getAbsolutePath());
						results.add(f.getAbsolutePath());
					}
				}
			}
		}
	}
}
