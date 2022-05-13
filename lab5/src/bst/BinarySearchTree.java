package bst;

import java.util.ArrayList;
import java.util.Comparator;


public class BinarySearchTree<E> {
  BinaryNode<E> root;  // Används också i BSTVisaulizer
  int size;            // Används också i BSTVisaulizer
  private Comparator<E> ccomparator;
    
	/**
	 * Constructs an empty binary search tree.
	 */
	public BinarySearchTree() {
		this.root = null;
		this.size = 0;
		ccomparator = null;
	}
	
	public static void main (String[] args) {
		BSTVisualizer bstv = new BSTVisualizer ("Binärt träd", 600, 600);
		BinarySearchTree tree = new BinarySearchTree();
		tree.add("Hanna");
		tree.add("Johanna");
		tree.add("Hilda");
		tree.add("Hugo");
		tree.add("Anna");
		tree.add("Lovisa");
		tree.add("Klara");
		tree.add("Pappa");
		bstv.drawTree(tree);
		//tree.rebuild();
		bstv.drawTree(tree);
		
		int[] k = new int[8];
		int i = k.length;
		System.out.println(i);
		
	}
	
	/**
	 * Constructs an empty binary search tree, sorted according to the specified comparator.
	 */
	public BinarySearchTree(Comparator<E> comparator) {
		this.ccomparator = comparator;
		this.root = null;
		this.size = 0;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * @param x element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if (find(x)) {
			return false;
		} if (root == null) {
			root = new BinaryNode<E>(x);
			size++;
			return true;
		}
		insert(root, x);
		size++;
		return true;
	}
	
	private void insert(BinaryNode<E> e, E x) { 
		int compResult = ((Comparable<E>) e.element).compareTo(x); 
		
		if (compResult > 0) {
			if (e.left != null) {
				insert(e.left, x);
			} else {
				e.left = new BinaryNode<E>(x);
			}
		} else {
			if (e.right != null) {
				insert(e.right, x);
			} else {
				e.right = new BinaryNode<E>(x);
			}
		}

	}
	
	//returnernar falskt om den inte finns, annars true
	public boolean find(E x) {
		return find(root, x);
	}
		
	private boolean find(BinaryNode<E> n, E x) {
		if (n == null) {
			return false;
		}
		
		int compResult = ((Comparable<E>) x).compareTo(n.element);
		
		if (compResult == 0) {
			return true;
		} else if (compResult < 0) {
			return find(n.left, x);
		} else {
			return find(n.right, x);
		}
	}
	
	
	/**
	 * Computes the height of tree.
	 * @return the height of the tree
	 */
	public int height() {
		return calcHeight(root);
	}
	
	private int calcHeight(BinaryNode<E> root) {
		if (root == null) {
			return 0;
		} 
		
		int leftheight = calcHeight(root.left);
		int rightheight = calcHeight(root.right);
		
		return 1 + Math.max(leftheight, rightheight);
	}
	
	/**
	 * Returns the number of elements in this tree.
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Removes all of the elements from this list.
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Print tree contents in inorder.
	 */
	public void printTree() {
		printInOrder(root);
	}
	
	private void printInOrder(BinaryNode<E> n) {
        if (n == null) 
            return; 
  
        printInOrder(n.left); 
        System.out.print(n.element + " "); 
        printInOrder(n.right);
	}

	/** 
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		ArrayList<E> sorted = new ArrayList<E>();
		toArray(root, sorted);
		root = buildTree(sorted, 0, size-1);
	}
	
	/*
	 * Adds all elements from the tree rooted at n in inorder to the list sorted.
	 */
	private void toArray(BinaryNode<E> n, ArrayList<E> sorted) {
		if (n == null) {
		    return;
		} else {
			toArray(n.left, sorted);
		    sorted.add((E) n);
		    toArray(n.right, sorted);
		}
	}
	
	/*
	 * Builds a complete tree from the elements from position first to 
	 * last in the list sorted.
	 * Elements in the list a are assumed to be in ascending order.
	 * Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(ArrayList<E> sorted, int first, int last) {
		if (first > last) {
			return null;
		} 

		int mid = first + ((last - first) / 2);
		BinaryNode<E> rot = (BinaryNode<E>) sorted.get(mid);
		rot.left = null;
		rot.right = null;
		
		//elementen till vänster
		rot.left = buildTree(sorted, first, mid-1);
		
		//elementen till höger
		rot.right = buildTree(sorted, mid+1, last);
		
		return rot;
		
	}

	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}	
	}
	
}
