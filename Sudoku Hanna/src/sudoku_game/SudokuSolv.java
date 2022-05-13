package sudoku_game;

import java.util.HashSet;
import java.util.Random;

public class SudokuSolv implements SudokuSolver {
	private int [][] grid;
	
	/** Creates the SudokuSolver with a grid with 9*9 squares */
	public SudokuSolv() {
		grid = new int[getDimension()][getDimension()];
	}
	
	/**
	 * Sets the number nbr in box r, c.
	 * 
	 * @param r
	 *            The row
	 * @param c
	 *            The column
	 * @param nbr
	 *            The number to insert in box r, c
	 * @throws IllegalArgumentException        
	 *             if r or c is outside [0..getDimension()-1] or
	 *             number is outside [1..9] 
	 */
	public void setNumber(int r, int c, int nbr) {
		if (r < 0 || r > getDimension()-1 || c < 0 || c > getDimension()-1) {
			throw new IllegalArgumentException("Värdet bör vara mellan 0-9");
		} else {
			grid[r][c] = nbr;
		}
	}
	
	/**
	 * Returns the number in box r,c. If the box i empty 0 is returned.
	 * 
	 * @param r
	 *            The row
	 * @param c
	 *            The column
	 * @param number
	 *            The number to insert in r, c
	 * @return the number in box r,c or 0 if the box is empty.
	 * @throws IllegalArgumentException
	 *             if r or c is outside [0..getDimension()-1]
	 */
	public int getNumber(int r, int c) {
		if (r < 0 || r > getDimension()-1 || c < 0 || c > getDimension()-1) {
			throw new IllegalArgumentException("Värdet på r eller c är inom fel gränser");
		} else {
			return grid[r][c];
		}
	}
	
	/** Removes a number from the sudoku at row r och col c .
	 * 	@throws IllegalArgumentException om fel vÃ¤rde pÃ¥ r eller c */
	public void clearNumber(int r, int c) {
		if (r < 0 || r > getDimension()-1 || c < 0 || c > getDimension()-1 ) {
			throw new IllegalArgumentException("Värdet på r eller c är inom fel gränser");
		} else {
			grid[r][c] = 0;
		}
	}
	
	/**Kontrollerar om vÃ¤rdet nbr i rutan r,c Ã¤r ok enligt reglerna.
	 throws IllegalArgumentException om fel vÃ¤rde pÃ¥ r, c eller nbr */
	public boolean isValid(int r, int c, int nbr) {
		if (r < 0 || r > getDimension()-1 || c < 0 || c > getDimension()-1) {
			throw new IllegalArgumentException("Värdet på r eller c är inom fel gränser");
		} if (nbr < 0 || nbr > 9) {
			throw new IllegalArgumentException("Värdet bör vara mellan 0-9");
		} else {
			
		for (int k = 0; k < 9; k++) {
			if (grid[r][k] == nbr && k != c) {
				return false;
			}
		}
		for (int i = 0; i < 9; i++) {
			if (grid[i][c] == nbr && i != r) {
				return false;
			}
		}
		int innerRow = r - r % 3;
		int innerCol = c - c % 3;
		for (int row = innerRow; row < innerRow + 3; row++) {
			for (int col = innerCol; col < innerCol + 3; col++) {
				if (grid[row][col] == nbr && row != r && col != c) {
					return false;
				}
			}
		}
		}
		
		return true;
	}
	
	/**Kontrollerar att alla ifyllda siffrorna uppfyller reglerna.*/
	public boolean isAllValid() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (!isValid(r,c,grid[r][c])) {
					return false;
				}
			}
		}
		return true;
	}
	
	/** Försker lösa sudokut och returnerar true om det var lösbart, annars false.*/
	public boolean solve() {
		return recSolve(0, 0);
	}
	
	 private boolean recSolve(int r, int c) {
	    	if (grid[r][c] == 0) {
				for (int i = 1; i <= 9; i++) {
					if (isValid(r, c,i)) {
						grid[r][c] = i;
						if (c != 8) {
							if (recSolve(r, c + 1)) {
								return true;
							}
						} else if (r != 8) {
							if (recSolve(r + 1, 0)) {
								return true;
							}
						} else {
							return true;
						}
					}
				}
				grid[r][c] = 0;
				return false;
			}
			if (isValid(r, c, grid[r][c])) {
				if (c != 8) {
					return recSolve(r, c + 1);
				} else if (r != 8) {
					return recSolve(r + 1, 0);
				} else {
					return true;
				}
			}
			return false;
	    }

	/** Tömmer alla rutorna i sudokut. */
	public void clear() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				clearNumber(r,c);
			}
		}
	}
	
	/** Undersöker om sudokut är tomt eller ej
	 * @return true om sudokut är tomt, annars false. */
	public boolean isEmpty() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (grid[r][c] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the numbers in the grid. An empty box i represented
	 * by the value 0.
	 * 
	 * @return the numbers in the grid
	 */
	public int[][] getMatrix() {
		return grid; 
	}

	/**
	 * Fills the grid with the numbers in nbrs.
	 * 
	 * @param nbrs the matrix with the numbers to insert
	 * @throws IllegalArgumentException
	 *             if nbrs have wrong dimension or containing values not in [0..9] 
	 */
	public void setMatrix(int[][] nbrs) {
		if (nbrs[0].length != 9 || nbrs.length != 9) {
			throw new IllegalArgumentException("Fel dimension på matrisen");
		} else {
			for (int r = 0; r < 9; r++) {
				for (int c = 0; c < 9; c++) {
					if (nbrs[r][c] > 9 || nbrs[r][c] < 0) {
						throw new IllegalArgumentException("Värden i matrisen bör vara mellan 0-9");
					} else {
						setNumber(r,c, nbrs[r][c]);
					}
				}
			}
		}		
	}
	
	/** Randomizes a new sudoku to solve */
	public void nyttSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int k = 0; k < 9; k++) {
				grid[i][k] = 0;
			}
		}

		int count = 0; 
		Random rand = new Random();
		
		while (count < 20) {
			int i = rand.nextInt(9) + 1;
			int r = rand.nextInt(9);
			int c = rand.nextInt(9);
			if (isValid(r,c,i) && grid[r][c] == 0) {
				setNumber(r,c,i);
				count++;
			} 
		}
	}
	
	/**
	 * Returns the dimension of the grid
	 * 
	 * @return the dimension of the grid
	 */
	@Override
	public int getDimension() {
		return 9;
	}
}
