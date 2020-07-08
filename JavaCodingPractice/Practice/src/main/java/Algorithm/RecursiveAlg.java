package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class RecursiveAlg {
    /*---------------------------------1.Given a Tuple for eg. (a, b, c)..----------------------------------*/
    /*---------------------------------Output : (*, *, *), (*, *, c), (*, b, *), (*, b, c),-----------------*/
    /*---------------------------------(a, *, *), (a, *, c), (a, b, *), (a, b, c)---------------------------*/
    public void populateSequence(char[] src, int rec, StringBuilder out) {
    	if (rec == src.length) {
    		System.out.println(out);
    		return;
    	}
    	out.append('*');
    	populateSequence(src, rec + 1, out);
    	out.setLength(out.length() - 1);
    	out.append(src[rec]);
    	populateSequence(src, rec + 1, out);
    	out.setLength(out.length() - 1);
    }

    /*2.1 Distinct Subsequences
     * Given a string S and a string T, count the number of distinct subsequences of T in S.
     * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE"while "AEC" is not).
     * Here is an example:
     * S = "rabbbit", T = "rabbit"
     * Return 3.*/
    public int numOfDistinct(String S, String T) {
    	if (S.length() == 0) return 0;
    	if (T.length() == 0) return 1;
    	if (S.length() == 1 && T.length() == 1) {
    		if (S.charAt(0) == T.charAt(0)) {
    			return 1;
    		} else {
    			return 0;
    		}
    	}
    	if (S.charAt(0) == T.charAt(0))
    		return numOfDistinct(S.substring(1), T) + numOfDistinct(S.substring(1), T.substring(1));
    	else
    		return numOfDistinct(S.substring(1), T);
    }

    /*2.2 DP*/
    /*子串的长度为 i，我们要求的就是长度为 i 的字串在长度为 j 的母串中出现的次数，设为 t[i][j]，
     * 若母串的最后一个字符与子串的最后一个字符不同，则长度为 i 的子串在长度为 j 的母串中出现的次数就是母串的前 j - 1 个字符中子串出现的次数，
     * 即 t[i][j] = t[i][j - 1]，若母串的最后一个字符与子串的最后一个字符相同，那么除了前 j - 1 个字符出现字串的次数外，
     * 还要加上子串的前 i - 1 个字符在母串的前 j - 1 个字符中出现的次数，即 t[i][j] = t[i][j - 1] + t[i - 1][j - 1].
     *http://fisherlei.blogspot.com/2012/12/leetcode-distinct-subsequences_19.html*/
    public int numOfDistinctDP(String S, String T) {
    	if (S.length() < T.length())
    		return 0;
    	int t[][] = new int[T.length() + 1][S.length() + 1];
    	for (int j = 0; j <= S.length(); ++j) {
    		t[0][j] = 1;
    	}
    	for (int i = 0; i <= T.length(); ++i) {
    		t[i][0] = 0;
		}
    	for (int j = 1; j <= S.length(); ++j) {
    		for (int i = T.length(); i >= 1; --i) {
    			if (S.charAt(j) == T.charAt(i)) {
    				t[i][j] = t[i - 1][j - 1] + t[i][j - 1];
    			} else {
    				t[i][j] = t[i][j - 1];
    			}
    		}
    	}
    	return t[T.length()][S.length()];
    }

    /*3. Subsets
     *Given a set of distinct integers, S, return all possible subsets.
     * Note:
     * Elements in a subset must be in non-descending order.
     * The solution set must not contain duplicate subsets.*/
    public ArrayList<ArrayList<Integer>> getAllSubSets(int a[]) {
    	Arrays.sort(a);
    	ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    	ArrayList<Integer> first = new ArrayList<Integer>();
    	first.add(a[0]);
    	result.add(first);
    	int start = 0;
    	for (int i = 1; i < a.length; ++i) {
    		for (int j = start; j < result.size(); ++j) {
    			result.add((ArrayList<Integer>) result.get(j).clone());
    			result.get(result.size() - 1).add(a[i]);
    		}
    		if (i < a.length - 1 && a[i] == a[i + 1])
    			start = result.size();
    		else
    			start = 0;
    	}
    	return result;
    }
}
