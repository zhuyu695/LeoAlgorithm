package Algorithm;

import java.util.Arrays;

public class DynamicProgramming {
	/*1.1Minimum Path Sum*/
	/*Analysis:
	 * This is a DP problem.
	 * Init: A[0][i] = A[0][i-1]+grid[0][i];
	 * A[i][0] = A[i-1][0]+grid[i][0];
	 * State Change func:
	 * A[i][j] = min(A[i-1][j]+grid[i][j], A[i][j-1]+grid[i][j]);*/
	public int getMinPathSum(int a[][]) {
		int dp[][] = new int[a.length][a[0].length];
		dp[0][0] = a[0][0];
		for (int i = 1; i < a[0].length; ++i) {
			dp[0][i] = dp[0][i - 1] + a[0][i];
		}
		for (int i = 1; i < a.length; ++i) {
			dp[i][0] = dp[i - 1][0] + a[i][0];
		}
		int i = 1, j = 1;
		for (; i < a.length; ++i) {
			for (; j < a[0].length; ++j) {
				dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + a[i][j];
			}
		}
		return dp[i][j];
	}

	/*1.2 Optimized Minimum Path Sum*/
	public int optMinPathSum(int a[][]) {
		int dp[] = new int[a[0].length];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		for (int i = 0; i < a.length; ++i) {
			dp[0] = dp[0] + a[i][0];
			for (int j = 1; j < a[0].length; ++j) {
				//dp[j] stands for path coming from top, dp[j-1] stands for path coming from left
				dp[j] = Math.min(dp[j - 1],dp[j]) + a[i][j];
			}
		}
		return dp[a[0].length - 1];
	}

	/*2.Decode Ways*/
	/*A message containing letters from A-Z is being encoded to numbers using the following mapping:
	 * 'A' -> 1
	 * 'B' -> 2
	 * ...
	 * 'Z' -> 26*/
	public int decodeWays(int s[]) {
		//This can be think as a simple DP problem, res[i]= res[i-1] if s[i] is valid +  res[i-2] if s[i-1:i] is valid.
		int res[] = new int[s.length];
		Arrays.fill(res, 0);
		if (isValid(s[0]))
			res[0] = 1;
		if (isValid(s[0], s[1]))
			res[1] = 2;
		int i = 2;
		for (; i < s.length; ++i) {
			if (isValid(s[i]))
				res[i] += res[i - 1];
			if (isValid(s[i - 1], s[i]))
				res[i] += res[i - 2];
		}
		return res[i - 1];
	}

	private boolean isValid(int c) {
		if (c >= 1 && c <= 26)
			return true;
		return false;
	}

	private boolean isValid(int prev, int cur) {
		int val = prev * 10 + cur;
		if (isValid(val))
			return true;
		return false;
	}

	/*3.Jump Game
	 *Given an array of non-negative integers, you are initially positioned at the first index of the array.
	 * Each element in the array represents your maximum jump length at that position.
	 * Determine if you are able to reach the last index.
	 * For example:
	 * A = [2,3,1,1,4], return true.
	 * A = [3,2,1,0,4], return false. */
	public boolean jumpGame(int a[]) {
		if (a.length == 0)
			return false;
		int curMax = a[0];
		int i = 0;
		while (i < curMax) {
			if (a[i] + i > curMax) {
				curMax = a[i] + i;
			}
			if (curMax >= a.length - 1) {
				return true;
			}
			++i;
		}
		return false;
	}
}
