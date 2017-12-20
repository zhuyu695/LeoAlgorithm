package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LeetCodeBackTracking {
//	291. Word Pattern II My Submissions QuestionEditorial Solution
//	Total Accepted: 5551 Total Submissions: 15685 Difficulty: Hard
//	Given a pattern and a string str, find if str follows the same pattern.
//
//	Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.
//
//	Examples:
//	pattern = "abab", str = "redblueredblue" should return true.
//	pattern = "aaaa", str = "asdasdasdasd" should return true.
//	pattern = "aabb", str = "xyzabcxzyabc" should return false.
//	Notes:
//	You may assume both pattern and str contains only lowercase letters.
//
//	Show Company Tags
//	Show Tags
//	Show Similar Problems
public class Solution291 {
    Map<Character, String> map;
    Set<String> set;
    boolean res;
    
    public boolean wordPatternMatch(String pattern, String str) {
        // 和I中一样，Map用来记录字符和字符串的映射关系
        map = new HashMap<Character, String>();
        // Set用来记录哪些字符串被映射了，防止多对一映射
        set = new HashSet<String>();
        res = false;
        // 递归回溯
        helper(pattern, str, 0, 0);
        return res;
    }
    
    public void helper(String pattern, String str, int i, int j){
        // 如果pattern匹配完了而且str也正好匹配完了，说明有解
        if(i == pattern.length() && j == str.length()){
            res = true;
            return;
        }
        // 如果某个匹配超界了，则结束递归
        if(i >= pattern.length() || j >= str.length()){
            return;
        }
        char c = pattern.charAt(i);
        // 尝试从当前位置到结尾的所有划分方式
        for(int cut = j + 1; cut <= str.length(); cut++){
            // 拆出一个子串
            String substr = str.substring(j, cut);
            // 如果这个子串没有被映射过，而且当前pattern的字符也没有产生过映射
            // 则新建一对映射，并且继续递归求解
            if(!set.contains(substr) && !map.containsKey(c)){
                map.put(c, substr);
                set.add(substr);
                helper(pattern, str, i + 1, cut);
                map.remove(c);
                set.remove(substr);
            // 如果已经有映射了，但是是匹配的，也继续求解
            } else if(map.containsKey(c) && map.get(c).equals(substr)){
                helper(pattern, str, i + 1, cut);
            }
            // 否则跳过该子串，尝试下一种拆分
        }
    }
}

//320. Generalized Abbreviation My Submissions QuestionEditorial Solution
//Total Accepted: 6877 Total Submissions: 16729 Difficulty: Medium
//Write a function to generate the generalized abbreviations of a word.
//
//Example:
//Given word = "word", return the following list (order does not matter):
//["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
public class Solution320 {
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        if (word == null || word.length() == 0) {
            res.add("");
            return res;
        }
        findAbbreviations(word, res, 0, 0, "");
        return res;
    }

    public void findAbbreviations(String word, List<String> res, int index, int counter, String curStr) {
        if (index == word.length()) {
            if (counter != 0) {
                curStr = curStr + counter;
            }
            res.add(curStr);
            return;
        }
        findAbbreviations(word, res, index+1, counter + 1, curStr);
        if (counter != 0) {
            curStr = curStr + counter;
        }
        findAbbreviations(word, res, index+1, 0, curStr+word.charAt(index));
    }
}

//254. Factor Combinations My Submissions QuestionEditorial Solution
//Total Accepted: 8853 Total Submissions: 25066 Difficulty: Medium
//Numbers can be regarded as product of its factors. For example,
//
//8 = 2 x 2 x 2;
//  = 2 x 4.
//Write a function that takes an integer n and return all possible combinations of its factors.
//
//Note: 
//You may assume that n is always positive.
//Factors should be greater than 1 and less than n.
//Examples: 
//input: 1
//output: 
//[]
//input: 37
//output: 
//[]
//input: 12
//output:
//[
//  [2, 6],
//  [2, 2, 3],
//  [3, 4]
//]
//input: 32
//output:
//[
//  [2, 16],
//  [2, 2, 8],
//  [2, 2, 2, 4],
//  [2, 2, 2, 2, 2],
//  [2, 4, 4],
//  [4, 8]
//]
public class Solution254 {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        if (n == 1) {
            return result;
        }
        
        helper(n, n / 2, new ArrayList<Integer>(), result);
        return result;
    }
    
    private void helper(int n, int largestFactor, List<Integer> temp, List<List<Integer>> result) {
        if (n == 1) {
            Collections.sort(temp);
            result.add(new ArrayList<Integer>(temp));
            return;
        }
        
        for (int i = largestFactor; i > 1; i--) {
            if ( n % i == 0) {
                temp.add(i);
                helper(n / i, i, temp, result);
                temp.remove(new Integer(i));
            }
        }
    }
}

//267. Palindrome Permutation II My Submissions QuestionEditorial Solution
//Total Accepted: 5654 Total Submissions: 20099 Difficulty: Medium
//Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.
//
//For example:
//
//Given s = "aabb", return ["abba", "baab"].
//
//Given s = "abc", return [].
//
//Hint:
//
//If a palindromic permutation exists, we just need to generate the first half of the string.Show More Hint 
//Show Tags
//Show Similar Problems
public class Solution267 {
    public List<String> generatePalindromes(String s) {  
        List<String> results = new ArrayList<String>();  
        if(s.length() == 0)  
            return results;  
              
        HashMap<Character, Integer> d = new HashMap<Character, Integer>();  
        for(int i = 0; i < s.length(); i++) {  
            if(d.containsKey(s.charAt(i)))  
                d.put(s.charAt(i), d.get(s.charAt(i)) + 1);  
            else  
                d.put(s.charAt(i), 1);  
        }  
          
        String candidate = "";  
        String single = "";  
        boolean already = false;  
          
        for(Character c : d.keySet()) {  
            int num = d.get(c) / 2;  
            for(int i = 0; i < num; i++)  
                candidate += c;  
            if(d.get(c) % 2 != 0) {  
                if(already)  
                    return results;  
                else {  
                    already = true;  
                    single += c;  
                }  
            }  
        }  
          
        if(candidate.length() == 0 && single.length() != 0) {  
            results.add(single);  
            return results;  
        }  
          
        boolean[] visited = new boolean[candidate.length()];
        recursion("", candidate, single, results, visited);  
        return results;  
    }  
      
    private void recursion(String cur, String candidate, String single, List<String> results, boolean[] visited) {
        if(cur.length() == candidate.length()) {
            String right = new StringBuffer(cur).reverse().toString();
            if (!results.contains(cur + single + right)) {
                results.add(cur + single + right);
            }
        }
        for(int i = 0; i < candidate.length(); i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            recursion(cur + candidate.charAt(i), candidate, single, results, visited);
            visited[i] = false;
        }
    }  
}

//294. Flip Game II My Submissions QuestionEditorial Solution
//Total Accepted: 9866 Total Submissions: 23648 Difficulty: Medium
//You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.
//
//Write a function to determine if the starting player can guarantee a win.
//
//For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".
//
//Follow up:
//Derive your algorithm's runtime complexity.
//
//Show Company Tags
//Show Tags
//Show Similar Problems
public class Solution294 {
    public boolean canWin(String s) {
        char[] cs = s.toCharArray();
        for(int i=0;i<cs.length-1;i++) {  
            if(cs[i] == cs[i+1] && cs[i]=='+') {  
                cs[i] = '-';  
                cs[i+1] = '-';  
                boolean win = !canWin(new String(cs));  
                cs[i] = '+';  
                cs[i+1] = '+';  
                if(win) return true;  
            }  
        }  
        return false;
    }
}

//247. Strobogrammatic Number II
//A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
//
//Find all strobogrammatic numbers that are of length = n.
//
//For example,
//Given n = 2, return ["11","69","88","96"].
//
//Hint:
//
//Try to use recursion and notice that it should recurse with n - 2 instead of n - 1.
//Show Company Tags
//Show Tags
//Show Similar Problems
public class Solution {
    private char[] validNumbers = new char[]{'0', '1', '6', '8', '9'};
    private char[] singleable = new char[]{'0', '1', '8'};

    public List<String> findStrobogrammatic(int n) {
        assert n > 0;
        List<String> result = new ArrayList<String>();

        if (n == 1) {
            for (char c : singleable) {
                result.add(String.valueOf(c));
            }
            return result;
        }

        if (n % 2 == 0) {
            helper(n, new StringBuilder(), result);
        } else {
            helper(n - 1, new StringBuilder(), result);
            List<String> tmp = new ArrayList<String>();
            for (String s : result) {
                for (char c : singleable) {
                    tmp.add(new StringBuilder(s).insert(s.length() / 2, c).toString());
                }
            }
            result = tmp;
        }
        return result;
    }

    private void helper(int n, StringBuilder sb, List<String> result) {
        if (sb.length() > n) return;

        if (sb.length() == n) {
            if (sb.length() > 0 && sb.charAt(0) != '0') {
                result.add(sb.toString());
            }
            return;
        }

        for (char c : validNumbers) {
            StringBuilder tmp = new StringBuilder(sb);
            String s = "" + c + findMatch(c);
            tmp.insert(tmp.length() / 2, s);
            helper(n, tmp, result);
        }
    }

    private char findMatch(char c) {
        switch (c) {
            case '1':
                return '1';
            case '6':
                return '9';
            case '9':
                return '6';
            case '8':
                return '8';
            case '0':
                return '0';
            default:
                return 0;
        }
    }
}
}
