package textproc;

import java.awt.Container;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;


public class BookReaderController {
	public BookReaderController(GeneralWordCounter counter) {
		SwingUtilities.invokeLater(() -> createWindow(counter, "BookReader", 500, 700));
	}

	private void createWindow(GeneralWordCounter counter, String title, int width, int height) {
	    JFrame frame = new JFrame(title);
	    frame.setSize(width, height);
	    Container pane = frame.getContentPane();
		JRadioButton firstbutton = new JRadioButton("Alphabetic", false);
		firstbutton.setPreferredSize(new Dimension(width/4-10, 50));
		JRadioButton secondbutton = new JRadioButton("Frequency", false);
		secondbutton.setPreferredSize(new Dimension(width/4-10, 50));
		JButton thirdbutton = new JButton("Find");
	    JTextField textfield = new JTextField();
	    textfield.setPreferredSize(new Dimension(width/4, 50));
		thirdbutton.setPreferredSize(new Dimension(width/4-10, 50));
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(firstbutton);
		bg.add(secondbutton);
		
		JOptionPane optionpane = new JOptionPane();

	    JPanel panel = new JPanel();
	    FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
	    panel.setLayout(fl);
	    panel.add(firstbutton);
	    panel.add(secondbutton);
	    panel.add(textfield);
	    panel.add(thirdbutton);
	    	    
		SortedListModel listModel = new SortedListModel(counter.getWordList());		
		
		JList list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(width, height-100));
		
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		
		layout.putConstraint(SpringLayout.NORTH, listScroller, 1, SpringLayout.NORTH, pane);
		layout.putConstraint(SpringLayout.SOUTH, panel, 1, SpringLayout.SOUTH, pane);

		pane.add(listScroller);
		pane.add(panel);
		
		//firstbutton.addActionListener((ActionEvent ae) -> {System.out.println("Du har klickat");});
		
		sortByName(firstbutton, listModel, list);
		sortByFrequency(secondbutton, listModel, list);
		
		thirdbutton.addActionListener((ActionEvent ae) -> {
				String input = textfield.getText();	
				input = input.toLowerCase();
								
				if (input.contains(" ")) {
					StringBuilder bs = new StringBuilder(input);
					
					if (bs.charAt(bs.length()-1) == ' ') {
						bs.deleteCharAt(bs.length()-1);
					} if (bs.charAt(0) == ' ') {
						bs.deleteCharAt(0);
					}
					
					input = bs.toString();
				} 
					
				for (int i = 0; i < listModel.getSize(); i++) {
					if (((Entry<String, Integer>) listModel.getElementAt(i)).getKey().equals(input) ) {
						System.out.println("Ordet har hittats");
						list.setSelectedIndex(i);
						list.ensureIndexIsVisible(i);
						return;
					}
				}
				optionpane.showMessageDialog(null, "Ordet kunde inte hittas");
				
		});
		
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
	
	private void sortByName(JRadioButton firstbutton, SortedListModel listModel, JList list) {
		Comparator<Map.Entry<String, Integer>> byName = new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		};
		
		firstbutton.addActionListener((ActionEvent ae) -> {listModel.sort(byName);});
		list.setModel(listModel);
	}
	
	private void sortByFrequency(JRadioButton secondbutton, SortedListModel listModel, JList list) {
		Comparator<Map.Entry<String, Integer>> byFrequency = new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		};
		secondbutton.addActionListener((ActionEvent ae) -> {listModel.sort(byFrequency);});
		list.setModel(listModel);
	}

}