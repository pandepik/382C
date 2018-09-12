package q6.Tournament;

import java.util.ArrayList;

public class PIncrement implements Runnable{
    
	static final long m = 1200000;
	
	static int count;
	static TournamentLock tlock;
	static ArrayList<Thread> t;
//	static int[] countBythread;
	
	PIncrement(int start, int numThreads){
		count = start;
	}
	
	public static int parallelIncrement(int c, int numThreads){
//        countBythread = new int[numThreads];
//        for(int i = 0; i < numThreads; i++) {
//        	countBythread[i] = 0;
//        }
		PIncrement pi = new PIncrement(c, numThreads);
        tlock = new TournamentLock(numThreads);
        try {
        	t = new ArrayList<Thread>();
        	for(int i = 0; i < numThreads; i++) {
        		Thread t1 = new Thread(pi);
        		t.add(t1);
        		t.get(i).start();
        	}
        	for(int i = 0; i < numThreads; i++) {
        		t.get(i).join();
        	}
        }catch(Exception e) {
        	System.err.println(e);
        }
    	return count;
    }
    
	public void run() {
		//int currentT = (int)Thread.currentThread().getId();
		int currentT = t.indexOf(Thread.currentThread());
		while(count < m) {
			tlock.lock(currentT);
			try {
				if(count < m) {
					count++;
//					countBythread[currentT]++;
//					System.out.println("Thread " + currentT + " incremented to " + count);
				}
			}finally {
				tlock.unlock(currentT);
			}
		}
		//System.out.println("Thread " + currentT + " counted " + countBythread[currentT]);
		return;
	}
	
	public static boolean check() {
		return (count < m);
	}
	
}