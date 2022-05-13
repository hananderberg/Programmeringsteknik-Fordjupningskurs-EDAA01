package bst;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMethods {
	private BinarySearchTree tree;
	private BinarySearchTree secondtree;
	
	@Before 
	public void setUp() throws Exception {
		tree = new BinarySearchTree();
		secondtree = new BinarySearchTree(((s1, s2) -> ((String) s1).compareTo((String) s2)));
	}
	
	@After
	public void tearDown() throws Exception {
		tree.root = null;
		tree.size = 0;
		
		secondtree.root = null;
		secondtree.size = 0;
	}
	
	@Test
	public void testHeight() {
		//första konstruktorn
		assertEquals(tree.height(),0);
		tree.add(80);		
		assertEquals(tree.height(),1);
		tree.add(81);
		tree.add(8);
		assertEquals(tree.height(),2);
		
		//andra konstruktorn
		assertEquals(secondtree.height(),0);
		secondtree.add("Klara");		
		secondtree.add("Anna");
		secondtree.add("Lovisa");
		assertEquals(secondtree.height(),2);
	}
	
	@Test
	public void testAdd() {
		//första konstruktorn
		tree.add(9);
		assertEquals(tree.root.element, 9);
		assertEquals(tree.size,1);
		
		tree.add(80);		
		tree.add(81);
		tree.add(8);
		
		assertEquals(tree.root.right.element, 80);
		assertEquals(tree.root.right.right.element, 81);
		assertEquals(tree.root.left.element, 8);
		
		tree.add(80);
		assertEquals(tree.size, 4);
		
		tree.clear();
		
		tree.add("Hanna");
		tree.add("Elisabeth");
		tree.add("Klara");
		assertTrue(tree.root.right.element.equals("Klara"));
		
		//andra konstruktorn
		secondtree.add("Hanna");
		assertEquals(secondtree.root.element, "Hanna");
		
		secondtree.add("Klara");		
		secondtree.add("Anna");
		secondtree.add("Lovisa");
		
		assertEquals(secondtree.root.left.element, "Anna");
		assertEquals(secondtree.root.right.right.element, "Lovisa");
		assertEquals(secondtree.root.right.element, "Klara");
	}
	
	@Test
	public void testSize() {
		//första konstruktorn
		assertEquals(tree.size, 0);
		tree.add(80);		
		tree.add(81);
		tree.add(8);
		assertEquals(tree.size, 3);
		
		tree.clear();
		
		tree.add("Hanna");
		tree.add("Elisabeth");
		assertEquals(tree.size, 2);
		
		//andra konstruktorn
		
		secondtree.add(90);
		assertEquals(secondtree.size, 1);
		secondtree.add(00);
		secondtree.add(5);
		secondtree.add(6);
		assertEquals(secondtree.size, 4);
	}
	
	@Test
	public void testClear() {
		//första konstruktorn
		tree.add(9);
		tree.add(10);
		tree.add(50);
		tree.clear();
		
		assertTrue(tree.root == null && tree.size == 0);
		
		//andra konstruktorn
		secondtree.add("Klara");		
		secondtree.add("Anna");
		secondtree.add("Lovisa");
		secondtree.clear();
		
		assertTrue(secondtree.root == null && tree.size == 0);
	}
	
}
