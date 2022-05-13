package textproc;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class GeneralWordCounter implements TextProcessor {
	private Map <String, Integer> m;
	private Set<String> stopwords;
	
	public GeneralWordCounter(Set<String> stopwords) {		
		m = new TreeMap<>();
		this.stopwords = stopwords;
	}
	
	public List<Map.Entry<String, Integer>> getWordList() {
		return new ArrayList<>(m.entrySet());
	}
	
	public void process(String w) {
		if (!m.containsKey(w) && !stopwords.contains(w)) { //första gången det upptäcks
			m.put(w, 1);
		} else if (m.containsKey(w) && !stopwords.contains(w)) { //om ordet upptäcks flera gånger
			int count = m.get(w);
			m.put(w, count+1);
		}
	}
	
	public void report() {
//		for (String key : m.keySet()) {
//			if (m.get(key) > 200) {
//				System.out.println(key + ": " + m.get(key));
//			}
//		}
		
		Set<Map.Entry<String, Integer>> wordSet = m.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
		wordList.sort(new WordCountComparator());
		
		for (int i = 0; i < 5; i++) {
			System.out.println(wordList.get(i));
		}
	}
}
