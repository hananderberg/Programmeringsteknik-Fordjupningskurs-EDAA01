package textproc;

import java.util.Map;
import java.util.HashMap;

public class MultiWordCounter implements TextProcessor {
	private Map <String, Integer> m;
	
	public MultiWordCounter(String[] strängar) {
		m = new HashMap<>();
		for (int i = 0; i < strängar.length; i++) {
			m.put(strängar[i], 0);
		}
	}
	
	public void process(String w) {
		if (m.containsKey(w)) {
			int count = m.get(w);
			m.put(w, count+1);
		}
	}
	
	public void report() {
		for (String key : m.keySet()) {
			System.out.println(key + ": " + m.get(key));
		}
	}
	
}
