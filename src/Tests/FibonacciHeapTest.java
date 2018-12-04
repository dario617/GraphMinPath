package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import MyHeap.FibonacciHeap;;

public class FibonacciHeapTest {

	private FibonacciHeap<Integer> fh;
	
	public void resetFH(){
		fh = new FibonacciHeap<>();
	}
	
	@Before
	public void beforeMethod(){
		fh = new FibonacciHeap<>();
	}
	
	@Test
	public void minIsConsistentTest(){
		fh.insert(2);
		assertEquals(2, (int)fh.peekMin());
		fh.insert(3);
		assertEquals(2, (int)fh.peekMin());
		fh.insert(1);
		assertEquals(1, (int)fh.peekMin());
		resetFH();
	}
	
	
	@Test
	public void extractMinTest1(){
		fh.insert(2);
		fh.insert(3);
		fh.insert(1);
		int min = fh.extractMin();
		assertEquals(1, min);
		resetFH();
	}
	
	@Test
	public void extractMinTest2(){
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
	
}
