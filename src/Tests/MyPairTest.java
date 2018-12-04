package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Utils.MyPair;

public class MyPairTest {
	
	private MyPair<Integer,Integer> pair1;
	private MyPair<Integer,Integer> pair2;
	
	@Before
	public void beforeMethod(){
		pair1 = new MyPair<Integer, Integer>(1, 1);
		pair2 = new MyPair<Integer, Integer>(2, 2);
	}
	
	@Test
	public void testComparison(){
		assertTrue(pair1.compareTo(pair2) < 0);
	}
}
