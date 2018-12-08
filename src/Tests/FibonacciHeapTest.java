package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import MyHeap.FibonacciHeap;
import Binomial.BinomialTree;
import java.util.HashMap;

public class FibonacciHeapTest {

	private FibonacciHeap<Integer> fh;

	public void resetFH() {
		fh = new FibonacciHeap<>();
	}

	@Before
	public void beforeMethod() {
		fh = new FibonacciHeap<>();
	}

	@Test
	public void minIsConsistentTest() {
		fh.insert(2);
		assertEquals(2, (int) fh.peekMin());
		fh.insert(3);
		assertEquals(2, (int) fh.peekMin());
		fh.insert(1);
		assertEquals(1, (int) fh.peekMin());
		resetFH();
	}

	@Test
	public void extractMinTest1() {
		fh.insert(2);
		fh.insert(3);
		fh.insert(1);
		int min = fh.extractMin();
		assertEquals(1, min);
		resetFH();
	}

	@Test
	public void extractMinTest2() {
		fh.insert(2);
		fh.insert(3);
		fh.insert(1);
		fh.insert(4);
		fh.insert(5);
		fh.insert(6);
		fh.insert(9);
		fh.insert(0);
		fh.insert(-1);
		int min = fh.extractMin();
		assertEquals(-1, min);
		resetFH();
	}

	@Test
	public void extractMinTest3() {
		fh.insert(2);
		fh.insert(3);
		fh.insert(1);
		fh.insert(4);
		fh.insert(5);
		fh.insert(6);
		fh.insert(9);
		fh.insert(0);
		fh.insert(-1);
		int min = fh.extractMin();
		min = fh.extractMin();
		assertEquals(0, min);
		resetFH();
	}
	
	@Test
	public void decreaseKeyTest1(){
		HashMap<Integer,BinomialTree<Integer>> ptrs = new HashMap<>();
		ptrs.put(2,fh.insert(2));
		ptrs.put(3,fh.insert(3));
		ptrs.put(1,fh.insert(1));
		ptrs.put(4,fh.insert(4));
		fh.insert(5);
		fh.insert(6);
		fh.insert(9);
		fh.insert(0);
		fh.insert(-1);
		int min = fh.extractMin(); // -1
		fh.decreaseKey(ptrs.get(4), 0);
		// Now the result is a single bt with 0 and the original with root 0
		fh.decreaseKey(ptrs.get(2), -1);
		min = fh.extractMin(); // -1
		assertEquals(-1, min);
	}
}
