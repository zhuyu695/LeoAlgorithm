package AlgorithmTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestSuite;

import org.junit.Test;

import Algorithm.BitManipulation;
import Algorithm.LinkedListAlg;
import Algorithm.BitManipulation.Pair;
import Algorithm.LinkedListAlg.Node;
import Algorithm.Practice;
import Algorithm.RecursiveAlg;
import Algorithm.SortingAlg;
import Algorithm.TreeAlg;
import Algorithm.TreeAlg.BinaryTreeNode;

public class GeneralPractice extends TestSuite {
	Practice prac = new Practice();
	LinkedListAlg list = new LinkedListAlg();
	BitManipulation bitm = new BitManipulation();
	RecursiveAlg recur = new RecursiveAlg();
	SortingAlg sort = new SortingAlg();
	TreeAlg tree = new TreeAlg();
	
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
	
	@Test
	public void testStringToInt() {
		String strIn = "56987615";
		assertEquals(56987615, prac.stringToInt(strIn));
		String strIn1 = "-9765";
		assertEquals(-9765, prac.stringToInt(strIn1));
		String strIn2 = "  9876esc ";
		assertEquals(9876, prac.stringToInt(strIn2));
	}
	
	@Test
	public void testValidNum() {
		String in = " -879.125";
		assertTrue(prac.validateNumeric(in));
		String in1 = " 879e125";
		assertTrue(prac.validateNumeric(in1));
		String in2 = " 879e1e25";
		assertFalse(prac.validateNumeric(in2));
		String in3 = " 8-79e125";
		assertFalse(prac.validateNumeric(in3));
		String in4 = ".125";
		assertTrue(prac.validateNumeric(in4));
		String in5 = "-5.125";
		assertTrue(prac.validateNumeric(in5));
		String in6 = "e5125";
		assertTrue(prac.validateNumeric(in6));
	}
	
	@Test
	public void testArrayToBST() {
		int inArr[] = {1, 2, 3, 5, 6, 9, 20, 55, 100};
		BinaryTreeNode node = tree.convertToBST(inArr, 0, inArr.length - 1);
		assertEquals(6, node.value);
		assertEquals(2, node.left.value);
	}
	
	@Test
	public void testReverseCharArr() {
		char chArr[] = {'a', 'b', 'c', 'd', 'e'};
		assertEquals("edcba", new String(prac.reverseCharArr(chArr)));
	}
	
	@Test
	public void testStringMul() {
		String num1 = "987";
		String num2 = "6585";
		assertEquals("6499395", new String(prac.StringMultiply(num1.toCharArray(), num2.toCharArray())));
	}
	
	@Test
	public void testPlusOne() {
		ArrayList<Integer> testArr = new ArrayList<Integer>();
		testArr.add(9);
		testArr.add(8);
		testArr.add(9);
		ArrayList<Integer> result = prac.plusOneForArray(testArr);
		assertEquals(9, result.get(0).intValue());
		assertEquals(9, result.get(1).intValue());
		assertEquals(0, result.get(2).intValue());
	}
	
	@Test
	public void testRemoveDup() {
		int a[] = {1, 2, 3, 3, 3, 4, 5, 5, 6};
		int b[] = prac.removeDuplicate(a);
		assertTrue("testRemoveDup failed: ", 6 == b[5]);
	}
	
	@Test
	public void testRemoveSpecVal() {
		int a[] = {1, 2, 3, 3, 3, 4, 5, 5, 6};
		assertEquals(6, prac.removeSpecValue(a, 3));
	}
	
	@Test
	public void testRange() {
		int a[] = {1, 2, 3, 3, 3, 4, 5, 5, 6, 6};
		Practice.Pair p = prac.getRangeForSpec(a, 6);
		assertEquals(8, p.elm1);
		assertEquals(9, p.elm2);
	}
}
