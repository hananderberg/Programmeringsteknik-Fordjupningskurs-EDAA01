package queue_singlelinkedlist;
import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}
	
	/**
	* Appends the specified queue to this queue
	* post: all elements from the specified queue are appended
	* to this queue. The specified queue (q) is empty after the call.
	* @param q the queue to append
	* @throws IllegalArgumentException if this queue and q are identical
	*/
	public void append(FifoQueue<E> q) {
		if (this == q) {
			throw new IllegalArgumentException("Can not append queue to itself");
		} else if (q.last == null) {
			throw new IllegalArgumentException("Can not append an empty queue");
		} else if (this.last == null && q.last != null) { //this kön är tom
			this.last = q.last;
			this.size += q.size;
			q.size = 0;
			q.last = null;
		} else { //båda listorna är fyllda
			QueueNode<E> node = q.last.next;
			size += q.size();
			q.last.next = this.last.next;
			this.last.next = node;
			this.last = q.last;
			q.last = null;
			q.size = 0;
		}
		
	}
	
	/**	
	 * Inserts the specified element into this queue, if possible
	 * post:	The specified element is added to the rear of this queue
	 * @param	e the element to insert
	 * @return	true if it was possible to add the element 
	 * 			to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> node = new QueueNode<E>(e);
				
		if (size == 0) { //tom lista
			this.last = node; 
			last.next = node;
		} else {
			node.next = last.next;
			last.next = node;
			this.last = node;
		}
		size++;
		return true;
	}
	
	/**	
	 * Returns the number of elements in this queue
	 * @return the number of elements in this queue
	 */
	public int size() {		
		return size;
	}
	
	/**	
	 * Retrieves, but does not remove, the head of this queue, 
	 * returning null if this queue is empty
	 * @return 	the head element of this queue, or null 
	 * 			if this queue is empty
	 */
	public E peek() {
		if (size == 0) { 
			return null;
		} else { 
			return last.next.element;
		}
	}

	/**	
	 * Retrieves and removes the head of this queue, 
	 * or null if this queue is empty.
	 * post:	the head of the queue is removed if it was not empty
	 * @return 	the head of this queue, or null if the queue is empty 
	 */
	public E poll() {
		if (size == 0) { 
			return null;
		} 
		E el = last.next.element;
		if (size == 1) {
			last = null;
		} else {
			last.next = last.next.next;
		}
		size--;
		return el;
	}
	
	/**	
	 * Returns an iterator over the elements in this queue
	 * @return an iterator over the elements in this queue
	 */	
	public Iterator<E> iterator () {
		return new QueueIterator();
	}
	

	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> pos;
		private boolean ettvarv;

		private QueueIterator() {
			if (size == 0) {
				pos = null;
				ettvarv = true;
			} else {
				pos = last.next;
				ettvarv = false;
			}
		}

		public boolean hasNext() {// kan skippa "varv", sätt pos till null när vi är på last!
			if (pos == null) {
				return false;
			}
			if (ettvarv && pos == last.next) {
				return false;
			}
			return pos.next != null;
		}

		public E next() {
			if (pos == null || ettvarv) {
				throw new NoSuchElementException("No such element");
			}
			E el = pos.element;
			pos = pos.next;
			if (pos == last.next && !ettvarv) {
				ettvarv = true;
			}
			return el;
		}

	}
	
	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

}