package q6.Tournament;

import java.util.concurrent.atomic.AtomicInteger;

public class TournamentLock implements Lock {
    
	static int threads;
	AtomicInteger[] level;
	AtomicInteger[] last;
	

	public TournamentLock(int numThreads){
        threads = numThreads;
        last = new AtomicInteger[threads];
		level = new AtomicInteger[threads];
		for(int i = 0; i < threads; i++) {
			last[i] = new AtomicInteger();
			level[i] = new AtomicInteger();
		}
		for(int i = 0; i < threads; i++) {
			level[i].set(0);
			last[i].set(0);
		}
    }
    
    public void lock(int pid) {
    	for(int i = 0; i < threads; i++) {
    		level[pid].set(i);
    		last[i].set(pid);
    		for(int k = 0; k < threads; k++) {
    			while((last[i].get() == pid) && (k != pid) && (level[k].get() >= i)) {
    				if(!PIncrement.check()) {
    					break;
    				}
    			}
    		}
    	}
    }
    
    public void unlock(int pid) {
    	level[pid].set(0);
    }
    
}