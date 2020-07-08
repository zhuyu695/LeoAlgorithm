package Algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
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

	public boolean solveSudokuCheck(char grid[][]) {
		int row = 0, col = 0;
		SudokuChecking schecking = findUnassignedLocation(grid);
		if (!schecking.result) {
			return true;
		} else {
			row = schecking.row;
			col = schecking.col;
		}
		for (int num = 1; num <= 9; ++num) {
			if (isSafe(grid, row, col, (char)(num+'0'))) {
				grid[row][col] = (char)(num+'0');
				if (solveSudokuCheck(grid)) {
					return true;
				}
			}
			grid[row][col] = '.';
		}
		return false;
	}

	private SudokuChecking findUnassignedLocation(char grid[][]){
		SudokuChecking sc = new SudokuChecking();
		for (int row = 0; row < sudokuRange; ++row) {
			for (int col = 0; col < sudokuRange; ++col) {
				if (grid[row][col] == '.') {
					sc.row = row;
					sc.col = col;
					sc.result = true;
					return sc;
				}
			}
		}
		sc.result = false;
		return sc;
	}

	private boolean isSafe(char grid[][], int row, int col, char num) {
		return !usedInRow(grid, row, num)
				&& !usedInCol(grid, col, num)
				&& !usedInBox(grid, row - row % 3, col - col % 3, num);
	}

	private boolean usedInRow(char grid[][], int row, char num) {
		for (int i = 0; i < sudokuRange; ++i) {
			if (grid[row][i] == num)
				return true;
		}
		return false;
	}

	private boolean usedInCol(char grid[][], int col, char num) {
		for (int i = 0; i < sudokuRange; ++i) {
			if (grid[i][col] == num)
				return true;
		}
		return false;
	}

	private boolean usedInBox(char grid[][], int boxStartRow, int boxStartCol, char num) {
		for (int i = boxStartRow; i < 3 + boxStartRow; ++i) {
			for (int j = boxStartCol; j < 3 + boxStartCol; ++j) {
				if (grid[i][j] == num)
					return true;
			}
		}
		return false;
	}

	public void solveSudoku(char[][] board) {
		solveSudokuCheck(board);
	}

	/*-------------------4. Word Ladder-----------------*/
	/* Length are same.
	 * Only one letter can be changed at a time
     * Each intermediate word must exist in the dictionary*/
	public LinkedList<String> findLadder(String startWord, String stopWord, Set<String> dict) {
		Set<String> visitedSet = new HashSet<String>();
		Map<String, String> backTrackMap = new HashMap<String, String>();
		Queue<String> q = new LinkedList<String>();
		q.add(startWord);

		while (q.size() > 0) {
			String wd = q.poll();
			for (String v : getOneEditWords(wd)) {
				if (v.equals(stopWord)) {
					LinkedList<String> resultList = new LinkedList<String>();
					resultList.add(v);
					while(wd != null) {
						resultList.add(0, wd);
						wd = backTrackMap.get(wd);
					}
					return resultList;
				}
				if (dict.contains(v) && !visitedSet.contains(v)) {
					q.add(v);
					visitedSet.add(v);
					backTrackMap.put(v, wd);
				}
			}
		}
		return null;
	}

	public Set<String> getOneEditWords(String w) {
		Set<String> words = new HashSet<String>();
		for (int i = 0; i < w.length(); ++i) {
			char[] wArray = w.toCharArray();
			for (char c = 'a'; c <= 'z'; ++c) {
				if (c != w.charAt(i)) {
					wArray[i] = c;
					words.add(new String(wArray));
				}
			}
		}
		return words;
	}

	//---------------------- Test ----------------------
	public static void main(String[] args) {
		BackTracking bt = new BackTracking();
		//Soduku
		String[][] boardStr = {{"5","3",".",".","7",".",".",".","."},
				{"6",".",".","1","9","5",".",".","."},
				{".","9","8",".",".",".",".","6","."},
				{"8",".",".",".","6",".",".",".","3"},
				{"4",".",".","8",".","3",".",".","1"},
				{"7",".",".",".","2",".",".",".","6"},
				{".","6",".",".",".",".","2","8","."},
				{".",".",".","4","1","9",".",".","5"},
				{".",".",".",".","8",".",".","7","9"}};
		char[][] board = new char[boardStr.length][boardStr[0].length];
		for(int i=0; i<boardStr.length; ++i) {
			for(int j=0; j<boardStr[0].length; ++j) {
				board[i][j] = boardStr[i][j].charAt(0);
			}
		}

		bt.solveSudoku(board);
		System.out.println(board);

	}
}
