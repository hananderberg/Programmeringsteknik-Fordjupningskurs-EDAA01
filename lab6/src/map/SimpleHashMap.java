package map;

public class SimpleHashMap<K, V> implements Map<K, V> {
	private Entry[] table;
	private int capacity;
	private double loadfactor;
	private int size;
	
	/** Constructs an empty hashmap with the default initial capacity (16)
	and the default load factor (0.75). */
	public SimpleHashMap() {
		this.capacity = 16;
		this.size = 0;
		this.loadfactor = 0.75;
		table = (Entry<K,V>[]) new Entry[capacity];
	}
	
	/** Constructs an empty hashmap with the specified initial capacity
	and the default load factor (0.75). */
	public SimpleHashMap(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.loadfactor = 0.75;
		table = (Entry<K,V>[]) new Entry[capacity];
	}
	
	public static void main(String [] args) {
		Map<String, Integer> m = new SimpleHashMap<String, Integer>(4);
		m.put("Hanna", 10);
		m.put("Hannah", 1);
		m.put("Hannai", 2);
		m.put("Hannaa", 4);
		m.put("Svenn", 9);
		m.put("Hej", 1);
		m.remove("Hej");

		System.out.println(m.show());
		System.out.println(m.size());
	}
	
	@Override
	public String show() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < table.length; i++) {
			sb.append(i + " "); 
			
			if (table[i]!= null) {
				sb.append(table[i].toString());
				Entry<K,V> e = table[i];
				while(e.next != null) {
					sb.append(" " + e.next.toString());
					e = e.next;
				}
			} else {
				sb.append("---------");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	@Override
	public V get(Object arg0) {
		K object = (K) arg0;
		
		for (int i = 0; i < table.length; i++) {
			if (find(i, object) != null) {
				Entry<K,V> e = find(i, object);
				return e.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V put(K arg0, V arg1) {
		if ((double)(size+1)/capacity >= 0.75) {
			rehash();
		}
				
		Entry<K,V> nytt = new Entry(arg0, arg1);
		int index = index(arg0);

		if (table[index] == null) {
			table[index] = nytt;
		} else {			
			if (find(index, arg0) != null) { //returnera det gamla men sätt det nya värdet
				Entry<K,V> hittad = find(index, arg0);
				V värde = find(index, arg0).getValue();
				hittad.setValue(arg1);
				return värde;
			} else {
				Entry<K,V> e = table[index(arg0)];
				
				while(e.next != null) {
					e = e.next;
				}
				
				e.next = nytt;
			}
		}
		
		size++;
		return null;
	}
	
	private void rehash() {
		int nycapacitet = capacity * 2;
		capacity = nycapacitet;
		Entry [] temp = table;
		Entry [] table = (Entry<K,V>[]) new Entry[nycapacitet];
		
		size = 0;
		
		for (int i = 0; i < temp.length; i++) {
			Entry<K, V> head = temp[i];
			
			while(head != null) {
				K key = head.getKey();
				V value = head.getValue();
				put(key, value);
				size++;
				head = head.next;
			}
		}

	}

	@Override
	public V remove(Object arg0) {
		K key = (K) arg0;
		int index = index(key);
		
		if (table[index] == null) { //listan är null
			return null;
		} else if (find(index, key) != null) { //key finns i listan		
			Entry<K,V> e = find(index,key); 
			
			if (table[index].getKey().equals(key)) { //key finns i det första elementet i listan
				
				if (e.next == null) {
					table[index] = null;
				} else {
					table[index] = e.next;
				}
				
			} else { //key finns senare i listan
				Entry<K,V> first = table[index];
				Entry<K,V> second = first.getNext();				
				
				while (!second.getKey().equals(key)) {
					first = first.getNext();
					second = second.getNext();
				}
				
					if (second.getNext() == null) { //elementet som ska tas bort är sist
						first.setNext(null);
					} else {
						first.setNext(second.getNext());
					}			
				}
			
			size--;
			return e.getValue();
		} else { //key finns inte i listan
			return null;
		}
		 
	}

	@Override
	public int size() {
		return size;
	}
	
	private int index(K key) {
		int index = key.hashCode() % table.length;
		if (index < 0 ) {
			index = index + table.length;
		}
		return index;
	}

	private Entry<K,V> find(int index, K key) {
		if (table[index] == null) {
			return null;
		} else { 
			Entry<K,V> e = table[index];
			
			while (e != null) {
				if (e.getKey().equals(key)) {
					return e;
				} else {
					e = e.next;
				}
			}
			
		}
		return null;
	}
	
	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K,V> next;
		
		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		public String toString() {
			return key.toString() + " = " + value.toString();
		}
		
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}
		 
		public void setNext(Entry<K,V> e) {
			this.next = e;
		}
		
		public Entry<K, V> getNext() {
			return next;
		}

	}

}
