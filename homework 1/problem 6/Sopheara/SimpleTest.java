package q6;

import org.junit.Test;
import static org.junit.Assert.*;


public class SimpleTest {

	@Test
	public void TestTournament() {
		int res = q6.Tournament.PIncrement.parallelIncrement(0, 4);
		assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
	}

//	@Test
//	public void TestAtomicInteger8() {
//    	int res = q6.AtomicInteger.PIncrement.parallelIncrement(0, 8);
//    	assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
//	}
//	
//	@Test
//	public void TestAtomicInteger7() {
//		int res7 = q6.AtomicInteger.PIncrement.parallelIncrement(0, 7);
//    	assertTrue("Result is " + res7 + ", expected result is 1200000.", res7 == 1200000);
//	}
//	@Test
//	public void TestAtomicInteger6() {
//		int res6 = q6.AtomicInteger.PIncrement.parallelIncrement(0, 6);
//    	assertTrue("Result is " + res6 + ", expected result is 1200000.", res6 == 1200000);
//	}
//	@Test
//	public void TestAtomicInteger5() {
//		int res5 = q6.AtomicInteger.PIncrement.parallelIncrement(0, 5);
//    	assertTrue("Result is " + res5 + ", expected result is 1200000.", res5 == 1200000);
//	}
//	@Test
//	public void TestAtomicInteger4() {
//		int res4 = q6.AtomicInteger.PIncrement.parallelIncrement(0, 4);
//    	assertTrue("Result is " + res4 + ", expected result is 1200000.", res4 == 1200000);
//	}
//	@Test
//	public void TestAtomicInteger3() {
//		int res3 = q6.AtomicInteger.PIncrement.parallelIncrement(0, 3);
//    	assertTrue("Result is " + res3 + ", expected result is 1200000.", res3 == 1200000);
//	}
//	@Test
//	public void TestAtomicInteger2() {
//		int res2 = q6.AtomicInteger.PIncrement.parallelIncrement(0, 2);
//    	assertTrue("Result is " + res2 + ", expected result is 1200000.", res2 == 1200000);
//	}
//	@Test
//	public void TestAtomicInteger1() {
//		int res1 = q6.AtomicInteger.PIncrement.parallelIncrement(0, 1);
//    	assertTrue("Result is " + res1 + ", expected result is 1200000.", res1 == 1200000);
//	}
//	
//
//	@Test
//	public void TestSynchronized8() {
//    	int res = q6.Synchronized.PIncrement.parallelIncrement(0, 8);
//    	assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
//	}
//	
//	@Test
//	public void TestSynchronized7() {
//		int res7 = q6.Synchronized.PIncrement.parallelIncrement(0, 7);
//    	assertTrue("Result is " + res7 + ", expected result is 1200000.", res7 == 1200000);
//	}
//	@Test
//	public void TestSynchronized6() {
//		int res6 = q6.Synchronized.PIncrement.parallelIncrement(0, 6);
//    	assertTrue("Result is " + res6 + ", expected result is 1200000.", res6 == 1200000);
//	}
//	@Test
//	public void TestSynchronized5() {
//		int res5 = q6.Synchronized.PIncrement.parallelIncrement(0, 5);
//    	assertTrue("Result is " + res5 + ", expected result is 1200000.", res5 == 1200000);
//	}
//	@Test
//	public void TestSynchronized4() {
//		int res4 = q6.Synchronized.PIncrement.parallelIncrement(0, 4);
//    	assertTrue("Result is " + res4 + ", expected result is 1200000.", res4 == 1200000);
//	}
//	@Test
//	public void TestSynchronized3() {
//		int res3 = q6.Synchronized.PIncrement.parallelIncrement(0, 3);
//    	assertTrue("Result is " + res3 + ", expected result is 1200000.", res3 == 1200000);
//	}
//	@Test
//	public void TestSynchronized2() {
//		int res2 = q6.Synchronized.PIncrement.parallelIncrement(0, 2);
//    	assertTrue("Result is " + res2 + ", expected result is 1200000.", res2 == 1200000);
//	}
//	@Test
//	public void TestSynchronized1() {
//		int res1 = q6.Synchronized.PIncrement.parallelIncrement(0, 1);
//    	assertTrue("Result is " + res1 + ", expected result is 1200000.", res1 == 1200000);
//	}
//	
//
//	@Test
//	public void TestReentrantLock8() {
//		int res = q6.ReentrantLock.PIncrement.parallelIncrement(0,8);
//		assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
//	}
//	@Test
//	public void TestReentrantLock7() {
//		int res7 = q6.ReentrantLock.PIncrement.parallelIncrement(0, 7);
//    	assertTrue("Result is " + res7 + ", expected result is 1200000.", res7 == 1200000);
//	}
//	@Test
//	public void TestReentrantLock6() {
//		int res6 = q6.ReentrantLock.PIncrement.parallelIncrement(0, 6);
//    	assertTrue("Result is " + res6 + ", expected result is 1200000.", res6 == 1200000);
//	}
//	@Test
//	public void TestReentrantLock5() {
//		int res5 = q6.ReentrantLock.PIncrement.parallelIncrement(0, 5);
//    	assertTrue("Result is " + res5 + ", expected result is 1200000.", res5 == 1200000);
//	}
//	@Test
//	public void TestReentrantLock4() {
//		int res4 = q6.ReentrantLock.PIncrement.parallelIncrement(0, 4);
//    	assertTrue("Result is " + res4 + ", expected result is 1200000.", res4 == 1200000);
//	}
//	@Test
//	public void TestReentrantLock3() {
//		int res3 = q6.ReentrantLock.PIncrement.parallelIncrement(0, 3);
//    	assertTrue("Result is " + res3 + ", expected result is 1200000.", res3 == 1200000);
//	}
//	@Test
//	public void TestReentrantLock2() {
//		int res2 = q6.ReentrantLock.PIncrement.parallelIncrement(0, 2);
//    	assertTrue("Result is " + res2 + ", expected result is 1200000.", res2 == 1200000);
//	}
//	@Test
//	public void TestReentrantLock1() {
//		int res1 = q6.ReentrantLock.PIncrement.parallelIncrement(0, 1);
//    	assertTrue("Result is " + res1 + ", expected result is 1200000.", res1 == 1200000);
//	}
	
}