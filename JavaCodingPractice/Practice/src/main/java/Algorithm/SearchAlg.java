package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SearchAlg {
	/*1.Given a 2D board containing ‘X’ and ‘O’, capture all regions surrounded by ‘X’.
	 * A region is captured by flipping all ‘O’s into ‘X’s in that surrounded region .*/
	public class Point {
		public int x;
		public int y;
		public Point (int xx, int yy) {
			x = xx;
			y = yy;
		}
	}

	public void solveRegion(char board[][]) {
		int rows = board.length;
		int cols = board[0].length;

		//Flip all open 'O' into 'Y', flipping starts from 4 boarders.
		for (int i = 0; i < cols; ++i) {
			if (board[0][i] == 'O')
				//when converting to Point, y is row, x is col
				flip(board, i, 0, 'Y');
			if (rows != 1 && board[rows - 1][i] == 'O')
				flip(board, i, rows - 1, 'Y');
		}
		for (int i = 0; i < rows; ++i) {
			if (board[i][0] == 'O')
				flip(board, 0, i, 'Y');
			if (cols != 1 && board[i][cols - 1] == 'O')
				flip(board, cols - 1, i, 'Y');
		}

		//final flip
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				if (board[i][j] == 'Y')
					board[i][j] = 'O';
				if (board[i][j] == 'O')
					board[i][j] = 'X';
			}
		}
	}

	private void flip(char board[][], int x, int y, char des) {
		char src = board[y][x];
		int rows = board.length;
		int cols = board[0].length;

		Point root = new Point(x, y);
		Queue<Point> q = new LinkedList<Point>();
		q.add(root);
		while (q.size() > 0) {
			Point tmp = q.poll();
			int mX = tmp.x;
			int mY = tmp.y;

			board[mY][mX] = des;

			if (mY < rows - 1 && board[mY + 1][mX] == src)
				q.add(new Point(mY + 1, mX));
			if (mY > 0 && board[mY - 1][mX] == src)
				q.add(new Point(mY - 1, mX));
			if (mX < cols - 1 && board[mY][mX + 1] == src)
				q.add(new Point(mY, mX + 1));
			if (mX > 0 && board[mY][mX - 1] == src)
				q.add(new Point(mY, mX - 1));
		}
	}

	/*2.Given an array of non-negative integers, you are initially positioned at the first index of the array.
	 * Each element in the array represents your maximum jump length at that position.
	 * Your goal is to reach the last index in the minimum number of jumps.
	 * For example:
	 * Given array A = [2,3,1,1,4]
	 * The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.) */
	public int getJump(int a[]) {
		int jump = 0;
		int curMax = 0;

		for (int i = 0; i < a.length; ++i) {
			if (a[i] > 0)
				++jump;
			else
				return 0;
			curMax = a[i] + i;
			if (curMax >= a.length - 1) {
				return jump;
			}

			int tmpMax = 0;
			//greedy alg
			for (int j = i; j < curMax; ++j) {
				if (tmpMax < a[j] + j) {
					tmpMax = a[j] + j;
					i = j;
				}
			}
		}
		return jump;
	}

	/*3.Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target?
	 * Find all unique quadruplets in the array which gives the sum of target.*/
	public ArrayList<ArrayList<Integer>> fourSum(int a[], int target) {
		Arrays.sort(a);
		int i = 0, j = 0, k = 0;
		int len = a.length;
		int sum = 0;
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		for (; i < len - 3; ++i) {
			if (i > 0 && a[i] == a[i - 1])
				continue;
			for (; j < len - 2; ++j) {
				if (j > 0 && a[j] == a[j - 1])
					continue;
				for (; k < len - 1; ++k) {
					if (k > 0 && a[k] == a[k - 1])
						continue;
					sum = a[i] + a[j] + a[k];

					int l = k + 1;
					int r = len - 1;
					int diff = target - sum;
					while (l <= r) {
						int mid = (l + r) >> 1;
						if (a[mid] == diff) {
							ArrayList<Integer> tmp = new ArrayList<Integer>();
							tmp.add(a[i]);
							tmp.add(a[j]);
							tmp.add(a[k]);
							tmp.add(a[mid]);
							result.add(tmp);
							break;
						} else if (a[mid] < diff) {
							r = mid - 1;
						} else {
							l = mid + 1;
						}
					}
				}
			}
		}
		return result;
	}

	/*4.Given a 2D board and a word, find if the word exists in the grid.
	 * The word can be constructed from letters of sequentially adjacent cell,
	 * where “adjacent” cells are those horizontally or vertically neighboring.
	 * The same letter cell may not be used more than once.*/
	public boolean wordExist(char[][] board, char[] wd) {
		int rows = board.length;
		int cols = board[0].length;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				if (exist(board, j, i, wd, 0))
					return true;
			}
		}
		return false;
	}

	private boolean exist(char[][] board, int x, int y, char[] wd, int pos) {
		if (board[y][x] == wd[pos]) {
			if (pos == wd.length - 1)
				return true;
			board[y][x] = '.';
			++pos;
			if (y > 0 && exist(board, x, y - 1, wd, pos))
					return true;
			if (y < board.length - 1 && exist(board, x, y + 1, wd, pos))
					return true;
			if (x > 0 && exist(board, x - 1, y, wd, pos))
					return true;
			if (x < board[0].length - 1 && exist(board, x + 1, y, wd, pos))
					return true;
			board[y][x] = wd[pos];
		}
		return false;
	}

	/*5. N Queues Problem*/
	public void printNQuenesSolution(int[][] board) {
		int rows = board.length;
		int colArr[] = new int[rows];
		placeQueneHelper(board, 0, colArr);
	}

	private void placeQueneHelper(int[][] board, int row, int[] colArr) {
		if (row == board.length) {
			for (int i = 0; i < colArr.length; ++i) {
				System.out.println("Quene in row: " + i + " col: " + colArr[i]);
			}
		}
		for (int i = 0; i < board[0].length; ++i) {
			if (isAvailable(row, i, colArr)) {
				colArr[row] = i;
				placeQueneHelper(board, row + 1, colArr);
			}
			colArr[row] = 0;
		}
	}

	private boolean isAvailable(int row, int col, int[] colArr) {
		for (int i = 0; i < row; ++i) {
			if (colArr[i] == col)
				return false;
			if (Math.abs(colArr[i] - col) == i - row)
				return false;
		}
		return true;
	}
}
