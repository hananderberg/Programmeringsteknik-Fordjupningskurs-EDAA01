package sudoku_game;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SudokuWindow {
	private JTextField [][] textgrid;
	private SudokuSolv ss;
	
	public SudokuWindow(SudokuSolver ss) {
		createWindow();
		textgrid = new JTextField[9][9];
		this.ss = (SudokuSolv) ss;
	}
	
	private void createWindow() {
		JTextField [][] textgrid = new JTextField[9][9];
		JFrame frame = new JFrame("Sudoku");
		frame.setSize(new Dimension(540,600));
		
	    Container pane = frame.getContentPane();
		JOptionPane optionpane = new JOptionPane();
	    
	    JPanel gridPanel = new JPanel();
	    createGrid(gridPanel, textgrid);
	    
	    gridPanel.setSize(new Dimension(540,600));
	    GridLayout sudokulayout = new GridLayout(9,9);
	    gridPanel.setLayout(sudokulayout);
	    
	    JPanel menuPanel = new JPanel();
	    menuPanel.setSize(new Dimension(540,60));
	    JButton solve = new JButton("Solve");
	    JButton clear = new JButton("Clear");
	    JButton game = new JButton("New Game");
	    
	    game.addActionListener((ActionEvent ae) -> {
	    	ss.nyttSudoku();
	    	for (int i = 0; i < 9; i++) {
	    		for (int k = 0; k < 9; k++) {
	    			textgrid[i][k].setText(null);
	    			if (ss.getNumber(i, k) != 0) {
		    			textgrid[i][k].setText(Integer.toString(ss.getNumber(i, k))); 
	    			}
	    		}
	    	}
	    });
	    
	    clear.addActionListener((ActionEvent ae) -> {
	    	ss.clear();
	    	for (int i = 0; i < 9; i++) {
	    		for (int k = 0; k < 9; k++) {
	    			textgrid[i][k].setText(null);
	    		}
	    	}
	    });
	    
	    solve.addActionListener((ActionEvent ae) -> {
	    	for (int i = 0; i < 9; i++) {
	    		for (int k = 0; k < 9; k++) {
	    			String number = textgrid[i][k].getText();
	    			if (!number.equals("")) {
		    			int num = Integer.parseInt(number);
		    			ss.setNumber(i, k, num);
	    			} else {
	    				ss.setNumber(i, k, 0);
	    			}
	    		}
	    	}
	    	
	    	if (ss.solve()) { //om sudokut går att lösa, skriv ut det
				for (int i = 0; i < 9; i++) {
			    	for (int k = 0; k < 9; k++) {
			    		textgrid[i][k].setText(Integer.toString(ss.getNumber(i, k)));
			    	}
				}
	    	} else {
				optionpane.showMessageDialog(null, "Det finns ingen lösning!");
	    	}
	    });
	    
	    menuPanel.add(solve);
	    menuPanel.add(clear);
	    menuPanel.add(game);
	    pane.add(menuPanel);
	    pane.add(gridPanel);
	    
	    SpringLayout layout = new SpringLayout();
	    pane.setLayout(layout);
	    
		layout.putConstraint(SpringLayout.SOUTH, menuPanel, 1, SpringLayout.SOUTH, pane);
		layout.putConstraint(SpringLayout.WEST, gridPanel, 1, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.EAST, gridPanel, 1, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.SOUTH, gridPanel, 1, SpringLayout.NORTH, menuPanel);
		layout.putConstraint(SpringLayout.NORTH, gridPanel, 1, SpringLayout.NORTH, pane);
			    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
	
	private void createGrid(JPanel gridPanel, JTextField [][] textgrid) {
	    for (int i = 0; i < 9; i++) {
	    	for (int k = 0; k < 9; k++) {
		    	JTextField ruta = new JTextField(1);
		    	ruta.setHorizontalAlignment(JTextField.CENTER);
		    	ruta.setFont(new Font("Verdana",Font.PLAIN, 40));
		    	ruta.setSize(new Dimension(63,60));
		    	ruta.addKeyListener(new KeyAdapter() {
		    		public void keyTyped(KeyEvent e) {
		    			char input = e.getKeyChar();
		    			String input2 = ruta.getText();
		    			if ((input < '1' || input > '9') && input != '\b' || input2.length() > 0) {
		    				e.consume();
		    			} 
		    		}

		    	});
		    	gridPanel.add(ruta);
		    	textgrid[i][k] = ruta;
		    	
				if ((i / 3 + k / 3) % 2 == 0) {
					textgrid[i][k].setBackground(Color.PINK);
				}

	    	}
	    }
	}
}


