package q6.Synchronized;

import java.util.ArrayList;


public class PIncrement implements Runnable{
    
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
			increment();
			//System.out.println("Thread " + Thread.currentThread().getId() + " is at " + atomicInt.get());
		}
		return;
	}
	
	synchronized void increment() {
		count = (count < m) ? count+1: count;
	}
	
}