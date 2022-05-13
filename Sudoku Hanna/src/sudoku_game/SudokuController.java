package sudoku_game;

public class SudokuController {

	public static void main (String[] args) {
		SudokuSolver ss = new SudokuSolv();
		SudokuWindow window = new SudokuWindow(ss);
	}
}
