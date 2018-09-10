package q6.AtomicInteger;

import java.util.concurrent.atomic.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
//import java.util.concurrent.Future;

public class PIncrement implements Runnable{
    
	static final long m = 1200000;
	static volatile AtomicInteger atomicInt;
	
	PIncrement(int start){
		atomicInt = new AtomicInteger(start);
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
		return atomicInt.get();
    }
	
	public void run() {
		int a = atomicInt.get();
		while(a < m) {
			int b = a + 1;
			atomicInt.compareAndSet(a, b);
			a = atomicInt.get();
			//System.out.println("Thread " + Thread.currentThread().getId() + " is at " + atomicInt.get());
		}
		return;
	}
	
}