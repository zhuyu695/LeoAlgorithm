package Algorithm;

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

}
