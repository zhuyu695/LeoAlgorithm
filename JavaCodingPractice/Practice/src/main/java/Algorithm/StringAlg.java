package Algorithm;

import java.util.Arrays;

public class StringAlg {
	/*1. Implement wildcard pattern matching with support for ‘?’ and ‘*’.
	  ‘?’ Matches any single character.
	  ‘*’ Matches any sequence of characters (including the empty sequence).
	  The matching should cover the entire input string (not partial).*/
	boolean isMatch(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 == null || str2 == null)
			return false;
		char[] a1 = str1.toCharArray();
		char[] a2 = str2.toCharArray();
		int i = 0;
		int j = 0;
		int foundStar = 0; //0 means not found '*'; 1 means found '*' in a1, 2 means found '*' in a2
		while (i < a1.length && j < a2.length) {
			if (a1[i] == a2[j] || a2[j] == '?' || a1[i] == '?') {
				++i;
				++j;
			} else if (a1[i] == '*') {
				while (a1[i] == '*' && i < a1.length)
					++i;
				foundStar = 1;
			} else if (a2[j] == '*') {
				while (a2[j] == '*' && j < a2.length)
					++j;
				foundStar = 2;
			} else if (foundStar == 1) {
				while (a2[j] != a1[i] && j < a2.length)
					++j;
				foundStar = 0;
			} else if (foundStar == 2) {
				while (a1[i] != a2[j] && i < a1.length)
					++i;
				foundStar = 0;
			}
		}

		if (i < a1.length) {
			while (a1[i] == '*' && i < a1.length)
				++i;
			if (i < a1.length)
				return false;
		}
		if (j < a2.length) {
			while (a2[j] == '*' && j < a2.length)
				++j;
			if (j < a2.length)
				return false;
		}
		return true;
	}

	/*2. Scramble String*/
	/*http://blog.unieagle.net/2012/10/23/leetcode%E9%A2%98%E7%9B%AE%EF%BC%9Ascramble-string%EF%BC%8C%E4%B8%89%E7%BB%B4%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92/*/
	/*http://blog.theliuy.com/scramble-string/*/
	public boolean isScrambleStr(String s1, String s2) {
		if (s1.length() != s2.length())
			return false;
		if (s1.equals(s2))
			return true;
		//check each character occurance
		int[] charArr = new int[26];
		for (int i = 0; i < s1.length(); ++i) {
			charArr[s1.charAt(i) - 'a'] += 1;
			charArr[s2.charAt(i) - 'a'] -= 1;
		}
		for (int i = 0; i < s1.length(); ++i) {
			if (charArr[i] != 0)
				return false;
		}

		for (int i = 0; i < s1.length(); ++i) {
			String s11 = s1.substring(0, i);
			String s12 = s1.substring(i);

			String s21 = s2.substring(0, i);
			String s22 = s2.substring(i);

			if (isScrambleStr(s11, s21) && isScrambleStr(s12, s22))
				return true;
			else if (isScrambleStr(s11, s22) && isScrambleStr(s12, s21))
				return true;
		}
		return false;
	}

	/*3. Given a 2D board and a word, find if the word exists in the grid.*/
	/*The word can be constructed from letters of sequentially adjacent cell,
	 * where “adjacent” cells are those horizontally or vertically neighboring.
	 * The same letter cell may not be used more than once.*/
	public boolean isInBoard(char[][] board, char[] s) {
		if(board.length == 0 || board[0].length == 0 || s.length == 0)
			return false;
		int i = 0 , j = 0;
		int rows = board.length;
		int cols = board[0].length;

		for (i = 0; i < rows; ++i) {
			for (j = 0; j < cols; ++j) {
				if (isInBoardHelper(board, j, i, s, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isInBoardHelper(char[][] board, int x, int y, char[] s, int start) {
		if (board[y][x] != s[start])
			return false;

		//prevent circle back to starting letter
		board[y][x] = '.';

		if (start == s.length - 1)
			return true;
		int rows = board.length;
		int cols = board[0].length;

		start = start + 1;
		if (x < cols - 1 && isInBoardHelper(board, x + 1, y, s, start))
			return true;
		if (y < rows - 1 && isInBoardHelper(board, x, y + 1, s, start))
			return true;
		if (x > 0 && isInBoardHelper(board, x - 1, y, s, start))
			return true;
		if (y > 0 && isInBoardHelper(board, x, y - 1, s, start))
			return true;

		board[y][x] = s[start];
		return false;
	}

	/*4.Given a string S and a string T, find the minimum window in S which will contain
	 * all the characters in T in complexity O(n).*/
	public String minWindow(char[] s, char[] t) {
		int need_to_find[] = new int[256];
		int has_found[] = new int[256];

		for (char c : t) {
			++need_to_find[c];
		}

		int begin = 0, end = 0;
		int count = 0;
		int min_len = Integer.MAX_VALUE;
		int min_begin = 0;

		for (; end < s.length; ++ end) {
			char curCh = s[end];
			if (need_to_find[curCh] == 0)
				continue;
			++has_found[curCh];

			if (has_found[curCh] <= need_to_find[curCh])
				++count;

			char begCh = s[begin];
			if (count == t.length) {
				while (need_to_find[begCh] == 0 || has_found[begCh] > need_to_find[begCh]) {
					if (has_found[begCh] > need_to_find[begCh]) {
						--has_found[begCh];
					}
					++begin;
					begCh = s[begin];
				}
				if (min_len > end - begin + 1) {
					min_len = end - begin + 1;
					min_begin = begin;
				}
			}
		}
		if (count < t.length)
			return null;
		else
			return s.toString().substring(min_begin, min_begin + min_len);
	}

	/*5. Given a string containing just the characters ‘(‘ and ‘)’,
	 * find the length of the longest valid (well-formed) parentheses substring.
	 * example is “)()())”, where the longest valid parentheses substring is “()()”, which has length = 4*/
	/*answer: http://blog.csdn.net/abcbc/article/details/8826782*/
	public int validLongestParathesis(char[] s) {
		if (s == null || s.length < 2)
			return 0;

		int dp[] = new int[s.length];
		Arrays.fill(dp, 0);
		int max = 0;

		for (int i = s.length - 2; i >= 0; --i) {
			if (s[i] == '(') {
				//dp[i] is storing longest matching starts from i
				int j = i + dp[i + 1] + 1;
				if (j < s.length && s[j] == ')') {
					dp[i] = dp[i + 1] + 2;
					if (j + 1 < s.length)
						dp[i] += dp[j + 1];
				}
			}
			max = Math.max(dp[i], max);
		}
		return max;
	}

}
