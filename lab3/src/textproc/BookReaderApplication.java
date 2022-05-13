package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookReaderApplication {

	public static void main(String[] args) throws Exception {
		
		File fr = new File("undantagsord.txt");
		Scanner scan = new Scanner(fr);
		
		Set<String> stopwords = new HashSet<String>();
		
		while (scan.hasNext()) {
			stopwords.add(scan.next());
		} 
		
		GeneralWordCounter gw = new GeneralWordCounter(stopwords);
		ArrayList<TextProcessor> ord = new ArrayList<TextProcessor>();
		ord.add(gw);
		
		File frs = new File("nilsholg.txt");
		Scanner s = new Scanner(frs);
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		while (s.hasNext()) {
			String word = s.next().toLowerCase();

			for (TextProcessor t : ord) {
				t.process(word);
			}
		}
		s.close();
		
		BookReaderController brc = new BookReaderController(gw);

	}

}
