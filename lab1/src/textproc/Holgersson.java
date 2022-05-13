package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File("undantagsord.txt"));
		Set<String> stopwords = new HashSet<String>();
		
		while (scan.hasNext()) {
			stopwords.add(scan.next());
		} 
		
		ArrayList<TextProcessor> ord = new ArrayList<TextProcessor>();
//		ord.add(new SingleWordCounter("nils"));
//		ord.add(new SingleWordCounter("norge"));
//		ord.add(new MultiWordCounter(REGIONS));
		ord.add(new GeneralWordCounter(stopwords));
		
		
		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		while (s.hasNext()) {
			String word = s.next().toLowerCase();

			for (TextProcessor t : ord) {
				t.process(word);
			}
		}

		s.close();
		
		for (TextProcessor t : ord) {
			t.report();
		}
	}
}