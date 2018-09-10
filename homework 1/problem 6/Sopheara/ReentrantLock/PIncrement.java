package q6.ReentrantLock;

import java.util.ArrayList;
import java.util.concurrent.locks.*;


public class PIncrement implements Runnable{
    
	final ReentrantLock lock = new ReentrantLock();
	static final long m = 1200000;
	static volatile int count;
	
	PIncrement(int start){
		count = start;
	}
	
	public static int parallelIncrement(int c, int numThreads){
		PIncrement pi = new PIncrement(c);
		try {
			ArrayList<Thread> t = new ArrayList<Thread>();
			for(int i = 0; i < numThreads; i++) {
				Thread t1 = new Thread(pi);
				t.add(t1);
				t.get(i).start();
			}
			for(int i = 0; i< numThreads; i++) {
				t.get(i).join();
			}
		}catch(Exception e) {
			System.err.println(e);
		}
		return count;
    }
	
	public void run() {
		while(count < m) {
			lock.lock();
			try {
				count = (count < m) ? count+1: count;
				//System.out.println("Thread " + Thread.currentThread().getId() + " is at " + atomicInt.get());
			}finally {
				lock.unlock();
			}
		}
		return;
	}
	
}