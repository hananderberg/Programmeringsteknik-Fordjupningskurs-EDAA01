package testqueue;

import static org.junit.Assert.*;

import java.util.Queue;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import queue_singlelinkedlist.FifoQueue;

public class TestAppendFifoQueue {
	private static final boolean IllegalArgumentException = false;
	private FifoQueue<Integer> queue1;
	private FifoQueue<Integer> queue2;
	
	@Before 
	public void setUp() throws Exception {
		queue1 = new FifoQueue<Integer>();
		queue2 = new FifoQueue<Integer>(); 
	}
	
	@After
	public void tearDown() throws Exception {
		queue1 = null;
		queue2 = null;
	}

	
	@Test
	public void testAppendTwoEmptyQueues() {
		try {
			queue1.append(queue2);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			//successfull test
		}
	}
	
	@Test
	public void testAppendEmptyQueueToNonEmptyQueue() {
		int i = 1;
		while (i < 6) {
			queue1.offer(i);
			i++;
		}
		try {
			queue1.append(queue2);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			//successfull test
		}
	}
	
	@Test
	public void testAppendNonEmptyQueueToEmptyQueue() {
		int i = 0;
		while (i < 6) {
			queue2.offer(i);
			i++;
		}
		queue2.append(queue1);
		Iterator itr = queue1.iterator();
		
		for (int k = 0; k < 6; k++) {
			assertEquals("Borde bli " + k, Integer.valueOf(k), itr.next()) ;
		}
		assertTrue(queue2.size() == 0);
		//assertTrue(queue1.size() == 4);
	}
	
	@Test
	public void testAppendTwoQueues() {
		int i = 0;
		while (i < 5) {
			queue1.offer(i);
			i++;
		}
		
		int k = 5;
		while (k < 10) {
			queue2.offer(k);
			k++;
		}
		
		Iterator itr = queue1.iterator();
		int p = 0;
		
		while(itr.hasNext()) {
			assertEquals("Borde bli" + p, Integer.valueOf(p), itr.next());
			p++;
		}
		
		queue1.append(queue2);
		assertTrue(queue1.size() == 10);
	}
	
	@Test 
	public void testAppendToSelf() {
		int i = 1;
		while (i < 6) {
			queue1.add(i);
			i++;
		}
		
		try {
			queue1.append(queue1);
			fail("Should raise IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			//successfull test
		}
	}

}
