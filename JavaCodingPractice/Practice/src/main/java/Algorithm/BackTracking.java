package Algorithm;

import java.util.HashSet;
import java.util.Set;

public class BackTracking {
	/*---------------1. generate parenthesis---------------*/
	public void printParenthesis(int num) {
		generateParenthesis(0, 0, num, new StringBuilder());
	}

	public void generateParenthesis(int left, int right, int thresh, StringBuilder sbuilder) {
		if (left == thresh && right == thresh) {
			System.out.println(sbuilder.toString());
			return;
		}
		if (left < thresh) {
			sbuilder.append('(');
			generateParenthesis(left + 1, right, thresh, sbuilder);
			sbuilder.setLength(sbuilder.length() - 1);
		}
		if (right < left) {
			sbuilder.append(')');
			generateParenthesis(left, right + 1, thresh, sbuilder);
			sbuilder.setLength(sbuilder.length() - 1);
		}
	}

	/*-----------------2. Permutation of num arr-----------------*/
	boolean[] rec = {false, false, false, false, false, false, false, false};
	Set<Integer> dupSet = new HashSet<Integer>();

	public void getPermutationNum(int[] arr, int length, int level, StringBuilder sbuilder) {
		if (level == length) {
			System.out.println(sbuilder.toString());
			return;
		}
		for (int i = 0; i < length; ++i) {
			if (rec[i] || dupSet.contains(arr[i])) {
				continue;
			}
			sbuilder.append(arr[i]);
			rec[i] = true;
			dupSet.add(arr[i]);
			getPermutationNum(arr, length, level + 1, sbuilder);
			sbuilder.setLength(sbuilder.length() - 1);
			rec[i] = false;
			dupSet.remove(arr[i]);
		}
	}

	/*----------------3. Sudoku solver----------------*/
	public class SudokuChecking {
		public int row;
		public int col;
		public boolean result;
	}

	int sudokuRange = 9;
	public boolean solveSudoku(int grid[][]) {
		int row = 0, col = 0;
		SudokuChecking schecking = findUnassignedLocation(grid);
		if (!schecking.result) {
			return true;
		} else {
			row = schecking.row;
			col = schecking.col;
		}
		for (int num = 1; num <= 9; ++num) {
			if (isSafe(grid, row, col, num)) {
				if (solveSudoku(grid)) {
					return true;
				}
			}
			grid[row][col] = 0;
		}
		return false;
	}

	private SudokuChecking findUnassignedLocation(int grid[][]){
		SudokuChecking sc = new SudokuChecking();
		for (int row = 0; row < sudokuRange; ++row) {
			for (int col = 0; col < sudokuRange; ++col) {
				if (grid[row][col] == 0) {
					sc.row = row;
					sc.col = col;
					sc.result = true;
				}
			}
		}
		sc.result = false;
		return sc;
	}

	private boolean isSafe(int grid[][], int row, int col, int num) {
		return !usedInRow(grid, row, num)
				&& !usedInCol(grid, col, num)
				&& !usedInBox(grid, row - row % 3, col - col % 3, num);
	}

	private boolean usedInRow(int grid[][], int row, int num) {
		for (int i = 0; i < sudokuRange; ++i) {
			if (grid[row][i] == num)
				return true;
		}
		return false;
	}

	private boolean usedInCol(int grid[][], int col, int num) {
		for (int i = 0; i < sudokuRange; ++i) {
			if (grid[i][col] == num)
				return true;
		}
		return false;
	}

	private boolean usedInBox(int grid[][], int boxStartRow, int boxStartCol, int num) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++i) {
				if (grid[i][j] == num)
					return true;
			}
		}
		return false;
	}
}
