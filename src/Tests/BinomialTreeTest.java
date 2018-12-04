package Tests;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Binomial.BinomialTree;

public class BinomialTreeTest {

	private BinomialTree<Integer> btInt1;
	private BinomialTree<Integer> btInt2;
	private BinomialTree<String> btStr1;
	private BinomialTree<String> btStr2;
	
	@Before
	public void beforeMethod(){
		btInt1 = new BinomialTree<Integer>(1);
		btInt2 = new BinomialTree<Integer>(2);
		btStr1 = new BinomialTree<String>("muy largo");
		btStr2 = new BinomialTree<String>("corto");
	}
	
	@Test
	public void degreesAreSetTest(){
		assertEquals(1, btInt1.getDegree());
		assertEquals(1, btInt2.getDegree());
		assertEquals(1, btStr1.getDegree());
		assertEquals(1, btStr2.getDegree());
	}
	
	@Test
	public void intValuesJoinTest(){
		int res = btInt1.joinBinomialTree(btInt2);
		// Result tree is 1
		//              2/
		assertEquals(1, res);
		assertEquals(2, btInt1.getDegree());
		assertEquals(1, btInt2.getDegree());
	}
	
	@Test
	public void createBTDegreeThreeTest(){
		btInt1 = new BinomialTree<Integer>(1);
		btInt2 = new BinomialTree<Integer>(2);
		BinomialTree<Integer> btInt3 = new BinomialTree<Integer>(3);
		BinomialTree<Integer> btInt4 = new BinomialTree<Integer>(4);
		
		btInt1.joinBinomialTree(btInt2); // 1 - 2
		btInt3.joinBinomialTree(btInt4); // 3 - 4
		
		btInt1.joinBinomialTree(btInt3); 
		// 1 - 2
		//  \3 - 4
		assertEquals(3, btInt1.getDegree());
		BinomialTree<Integer>[] ch = btInt1.getChildren();
		assertEquals(2, (int)ch[0].getValue());
		assertEquals(3, (int)ch[1].getValue());
	}
}
