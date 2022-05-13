package sudoku_game;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Before;

//Automatisk testning Skriv testfall med JUnit för metoderna i din sudokuklass. 
//Bland testmetoderna ska ingå lösning av ett tomt sudoku, lösning av sudokut i figur 1 
//samt försök att lösa lämpliga fall av olösliga sudokun.

public class TestSudoku  {
	private SudokuSolv ss;

	@BeforeEach
	public void start() throws Exception {
		ss = new SudokuSolv();
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		ss = null;
	}
	
	@Test
	public void testSudoku() {
		for (int i = 0; i < 9; i++) { //check if row 8 has duplicates
			for (int k = 0; k < 9; k++) {
				if (ss.getNumber(i, k) != 0) {
					fail("Sudokut implementeras inte på rätt sätt");
				}
			}
		}
	}
	
	@Test
	public void testNyttSudoku() {
		ss.nyttSudoku();
		assertEquals(ss.isEmpty(), false);
		
		//eller
		int count = 0;
		for (int i = 0; i < 9; i++) { //check if row 8 has duplicates
			for (int k = 0; k < 9; k++) {
				if (ss.getNumber(i, k) != 0) {
					count++;
				}
			}
		}
		if (count == 0) {
			fail("NyttSudoku genererar inget nytt sudoku");
		}
	}
	
	@Test
	public void testGetMatrix() {
		int[][] matrix = ss.getMatrix();
		assertEquals(ss.getNumber(4, 4), matrix[4][4], "error");
	}
	
	@Test 
	public void testSetMatrix() {
		int[][] matrix = new int[ss.getDimension()+1][ss.getDimension()-1];
		assertThrows(IllegalArgumentException.class, () -> ss.setMatrix(matrix));
		
		int [][] matrix2 = {{0,20,0,0,0,4,3,0,0},
					{9,0,0,0,2,0,0,0,8},
					{0,0,0,6,0,9,0,5,0},
					{0,0,0,0,0,0,0,0,1},
					{0,7,2,5,0,3,6,8,0},
					{6,0,0,0,0,0,0,0,0},
					{0,8,0,2,0,5,0,0,0},
					{1,0,0,0,9,0,0,0,3},
					{0,0,0,0,0,0,0,0,0}};
					
		assertThrows(IllegalArgumentException.class, () -> ss.setMatrix(matrix2));
		
		int [][] matrix3 = {{0,0,0,0,0,4,3,0,0},
				{9,0,0,0,2,0,0,0,8},
				{0,0,0,6,0,9,0,5,0},
				{0,0,0,0,0,0,0,0,1},
				{0,7,2,5,0,3,6,8,0},
				{6,0,0,0,0,0,0,0,0},
				{0,8,0,2,0,5,0,0,0},
				{1,0,0,0,9,0,0,0,3},
				{0,0,0,0,0,0,0,0,0}};
		ss.setMatrix(matrix3);
		assertEquals(4, ss.getNumber(0, 5));
		assertEquals(3, ss.getNumber(0, 6));
	}
	
	@Test
	public void testIsEmpty() {
		ss.setNumber(0, 0, 9);
		ss.setNumber(7, 6, 8);
		if (ss.isEmpty()) {
			fail("");
		}
		ss.clear();
		if (!ss.isEmpty()) {
			fail("");
		}
	}
	
	@Test
	public void testGetAndSetValue() {
		ss.setNumber(0, 0, 9);
		ss.setNumber(7, 6, 8);
		ss.setNumber(2, 3, 5);
		assertEquals(ss.getNumber(0, 0),9);
		assertEquals(ss.getNumber(7, 6),8);
		assertEquals(ss.getNumber(2, 3),5);
	}
	
	@Test
	public void testClear() {
		ss.setNumber(0, 0, 9);
		ss.setNumber(7, 6, 8);
		ss.setNumber(2, 3, 5);
		ss.clear();
		
		for (int i = 0; i < 9; i++) { //check if row 8 has duplicates
			for (int k = i+1; k < 9; k++) {
				if (ss.getNumber(i, k) != 0) {
					fail("Clear fungerar inte");
				}
			}
		}
	}
	
	@Test
	public void testSolveEmptySudoku() {
		if (!ss.solve()) {
			fail(" ");
		}				
		for (int i = 0; i < 9; i++) { //check if row 8 has duplicates
			for (int k = i+1; k < 9; k++) {
				if (ss.getNumber(7, i) == ss.getNumber(7, k)) {
					fail("Row 8 contains duplicates");
				}
			}
		}
		
		for (int i = 0; i < 9; i++) { //check if col 5 has duplicates
			for (int k = i+1; k < 9; k++) {
				if (ss.getNumber(i, 4) == ss.getNumber(k, 4)) {
					fail("Col 5 contains duplicates");
				}
			}
		}
		
		//check if the middle box has duplicates
		HashSet<Integer> m = new HashSet<Integer>();
		int nbrOfZeros = 0;
		
		for (int i = 3; i < 6; i++) {
			for (int k = 3; k < 6; k++) {
				if (ss.getNumber(i, k) == 0) {
					nbrOfZeros++;
				} else {
					m.add(ss.getNumber(i, k));
				}
			}
		}
		
		assertEquals(nbrOfZeros + m.size(), 9);
	}
	
	@Test 
	public final void testSolveSudokuFigure1() {
		ss.setNumber(0, 2, 8);
		ss.setNumber(0, 5, 9);
		ss.setNumber(0, 7, 6);
		ss.setNumber(0, 8, 2);
		ss.setNumber(1, 8, 5);
		ss.setNumber(2, 0, 1);
		ss.setNumber(2, 2, 2);
		ss.setNumber(2, 3, 5);
		ss.setNumber(3, 3, 2);
		ss.setNumber(3, 4, 1);
		ss.setNumber(3, 7, 9);
		ss.setNumber(4, 1, 5);
		ss.setNumber(4, 6, 6);
		ss.setNumber(5, 0, 6);
		ss.setNumber(5, 7, 2);
		ss.setNumber(5, 8, 8);
		ss.setNumber(6, 0, 4);
		ss.setNumber(6, 1, 1);
		ss.setNumber(6, 3, 6);
		ss.setNumber(6, 5, 8);
		ss.setNumber(7, 0, 8);
		ss.setNumber(7, 1, 6);
		ss.setNumber(7, 4, 3);
		ss.setNumber(7, 6, 1);
		ss.setNumber(8, 6, 4);
		
		assertEquals("A solveable sudoku is seen as unsolveable", ss.solve(), true);
		assertEquals(ss.getNumber(0, 0),5);
		assertEquals(ss.getNumber(8, 0),9);
		assertEquals(ss.getNumber(8, 8),6);
		assertEquals(ss.getNumber(3, 8),3);
	}
	
	@Test
	public void testOneNumber() {
		int matrix[][] = { 
				{0,0,0,5,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0}}; 
		ss.setMatrix(matrix);
		assertEquals(true, ss.solve(), "error");
	}
	
	@Test
	public void testSolveUnsolveableSudoku() {
		ss.setNumber(0, 0, 5);
		ss.setNumber(0, 1, 5);
		assertEquals("An unsolveable sudoku is seen as solveable", ss.solve(), false);
	}
	
	@Test
	public void testIsValidEmpty() {
		assertEquals(true, ss.isValid(1,1,1), "fel");
		assertThrows(IllegalArgumentException.class, () -> ss.isValid(1,1,10));
		assertThrows(IllegalArgumentException.class, () -> ss.isValid(11,1,1));
	}
	
	@Test
	public void testIsValidNonEmpty() {
		ss.setNumber(1, 2, 3);
		ss.setNumber(1, 3, 4);
		assertEquals(false, ss.isValid(1,1,3), "fel");
		assertEquals(true, ss.isValid(1,4,5), "fel");
	}
	

}
