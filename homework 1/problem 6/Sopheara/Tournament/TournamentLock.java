package q6.Tournament;

import java.util.concurrent.atomic.AtomicInteger;

public class TournamentLock implements Lock {
    
	static int threads;
	AtomicInteger[] last;
	AtomicInteger[] levels;
	
	public TournamentLock(int numThreads){
        threads = numThreads;
        last = new AtomicInteger[numThreads];
		levels = new AtomicInteger[numThreads];
		for(int i = 0; i < threads; i++) {
			last[i] = new AtomicInteger();
			levels[i] = new AtomicInteger();
		}
    }
       
    public void unlock(int pid) {
    	levels[pid].set(0);
    }
    
    public void lock(int pid) {
    	for(int i = 0; i < threads; i++) {
    		levels[pid].set(i);
    		last[i].set(pid);
    		for(int k = 0; k < threads; k++) {
    			while(last[i].get() == pid && (k != pid && levels[k].get() >= i)) {
    				if(!PIncrement.check()) {
    					break;
    				}
    			}
    		}
    	}
    }
    
}