package AlgorithmTest;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestSuite;

import org.junit.Test;

import Algorithm.BitManipulation;
import Algorithm.LinkedListAlg;
import Algorithm.LinkedListAlg.Node;
import Algorithm.Practice;
import Algorithm.RecursiveAlg;
import Algorithm.SortingAlg;

public class GeneralPractice extends TestSuite {
	Practice prac = new Practice();
	LinkedListAlg list = new LinkedListAlg();
	BitManipulation bitm = new BitManipulation();
	RecursiveAlg recur = new RecursiveAlg();
	SortingAlg sort = new SortingAlg();
	
	@Test
	public void testRevertBit() {
		assertEquals(10, bitm.reverseBit(5, 4));
	}
	
	@Test
	public void testEvaluateExpression() {
		String src = "(3+1)+(1+0)*2";
		assertEquals(6, prac.evaluateExpression(src.toCharArray()));
	}
	
	@Test
	public void testBigIntMul() {
		String num1 = "666";
		String num2 = "35";
		assertEquals("23310", new String(prac.BigIntMultiply(num1.toCharArray(), num2.toCharArray())));
	}
	
	@Test
	public void testReverseList() {
		Node n5 = list.new Node(66);
		Node n4 = list.new Node(9, n5);
		Node n3 = list.new Node(8, n4);
		Node n2 = list.new Node(6, n3);
		Node n1 = list.new Node(5, n2);
		Node root = list.new Node(10, n1);
		
		Node newHead = list.reverseListNonRecur(root);
		assertEquals(66, newHead.value);
		assertEquals(9, newHead.next.value);
		
		newHead = list.reverseListRecur(newHead);
		assertEquals(10, newHead.value);
		assertEquals(5, newHead.next.value);
	}
	
	@Test
	//Print out on console
	public void testTuple() {
		char[] src = {'a','b','c','d','e'};
		StringBuilder sbuilder = new StringBuilder();
		recur.populateSequence(src, 0, sbuilder);
	}
	
	@Test
	public void testGetSentence() {
		Set<String> dic = new HashSet<String>();
    	dic.add("from");
    	dic.add("waterloo");
    	dic.add("hi");
    	dic.add("am");
    	dic.add("yes");
    	dic.add("i");
    	dic.add("a");
    	dic.add("student");
    	StringBuilder sbuilder = new StringBuilder();
    	prac.getSentence("iamastudentfromwaterloo", dic,sbuilder);
    	assertEquals("i am a student from waterloo", sbuilder.substring(1));
	}
	
	@Test
	public void testQuickSorting() {
		int src[] = {9, 2, 6, 1, 3, 5};
		sort.QuickSort(src, 0, src.length - 1);
		System.out.println(src);
	}
}
