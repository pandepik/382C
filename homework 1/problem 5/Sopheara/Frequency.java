package q5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.security.*;
import java.util.*;

public class Frequency implements Callable<Integer> {
	
	int num;
	int[] array;
	
	Frequency(int a, int[]b){
		num = a;
		array = b;
	}
	
	public static int parallelFreq(int x, int[] A, int numThreads){
        //your implementation goes here, return -1 if the input is not valid.
		if(A == null) {
			return -1;
		}
		
		if(A.length >= numThreads) {
			try {
				ExecutorService exe = Executors.newFixedThreadPool(numThreads);
				int count = 0;
				for(int i = 0; i < numThreads; i++) {
					Frequency freq = new Frequency(x, Arrays.copyOfRange(A, (i)*(A.length/numThreads), (i+1)*(A.length/numThreads)));
					Future<Integer> f = exe.submit(freq);
					count += f.get();
				}
				return count;
			}catch(Exception e) {
				System.err.println(e);
			}
		}else {
			try {
				ExecutorService exe = Executors.newSingleThreadExecutor();
				Frequency freq = new Frequency(x, A);
				Future<Integer> f = exe.submit(freq);
				return f.get();
			}catch(Exception e) {
				System.err.println(e);
			}
		}
		return 0;
    }  

	public Integer call() {
		try {
			int count = 0;
			for(int i = 0; i < array.length; i++) {
				count = (num == array[i]) ? count+1 : count;
			}
			return count;
		}catch (Exception e) { 
			System.err.println(e);
			return -1;
		}
	}
	
}

//for(int i = 0; i < B.size(); i++) {
//	if(num == B.get(i)) {
//		increment();
//	}
//}
//return count;

