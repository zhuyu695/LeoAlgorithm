package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LeetCodeHashTable {
//170. Two Sum III - Data structure design
//	Design and implement a TwoSum class. It should support the following operations: add and find.
//
//	add - Add the number to an internal data structure.
//	find - Find if there exists any pair of numbers which sum is equal to the value.
//
//	For example,
//	add(1); add(3); add(5);
//	find(4) -> true
//	find(7) -> false
//	Show Company Tags
//	Show Tags
//	Show Similar Problems
public class TwoSum {

	private HashMap<Integer, Integer> elements = new HashMap<Integer, Integer>();
 
	public void add(int number) {
		if (elements.containsKey(number)) {
			elements.put(number, elements.get(number) + 1);
		} else {
			elements.put(number, 1);
		}
	}
 
	public boolean find(int value) {
		for (Integer i : elements.keySet()) {
			int target = value - i;
			if (elements.containsKey(target)) {
				if (i == target && elements.get(target) < 2) {
					continue;
				}
				return true;
			}
		}
		return false;
	}
}
	
//	311. Sparse Matrix Multiplication
//	Given two sparse matrices A and B, return the result of AB.
//
//			You may assume that A's column number is equal to B's row number.
//
//			Example:
//
//			A = [
//			  [ 1, 0, 0],
//			  [-1, 0, 3]
//			]
//
//			B = [
//			  [ 7, 0, 0 ],
//			  [ 0, 0, 0 ],
//			  [ 0, 0, 1 ]
//			]
//
//
//			     |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
//			AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
//			                  | 0 0 1 |
public class Solution311 {
    public int[][] multiply(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, nB = B[0].length;
        int[][] C = new int[m][nB];

        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < nB; j++) {
                        if (B[k][j] != 0) C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;   
    }
}
	
//	159. Longest Substring with At Most Two Distinct Characters
//	Given a string, find the length of the longest substring T that contains at most 2 distinct characters.
//
//	For example, Given s = “eceba”,
//
//	T is "ece" which its length is 3.
public class Solution159 {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int j = 0, n = s.length(), best = 0;
        for(int i = 0; i < n; i++) {
            while(j < n && map.size() <= 2) {
            	if (map.containsKey(s.charAt(j))) {
            		map.put(s.charAt(j), map.get(s.charAt(j)) + 1);
            	} else {
            		map.put(s.charAt(j), 1);
            	}
                if(map.size() <= 2) {
                    best = Math.max(best, j-i+1);
                }
                j++;
            }
            int count = map.get(s.charAt(i));
            if(count == 1) {
                map.remove(s.charAt(i));
            } else {
                map.put(s.charAt(i), count - 1);
            }
        }
        return best;
    }
}

//266. Palindrome Permutation
//Given a string, determine if a permutation of the string could form a palindrome.
//
//For example,
//"code" -> False, "aab" -> True, "carerac" -> True.
//Consider the palindromes of odd vs even length. What difference do you notice?
public class Solution266 {
    public boolean canPermutePalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        
        int count = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                count++;
                if (count > 1) {
                    return false;
                }
            }
        }
        
        return true;
    }
}

//249. Group Shifted Strings
//Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:
//
//"abc" -> "bcd" -> ... -> "xyz"
//Given a list of strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.
//
//For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"], 
//Return:
//
//[
//  ["abc","bcd","xyz"],
//  ["az","ba"],
//  ["acef"],
//  ["a","z"]
//]
public class Solution249 {
    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> result = new ArrayList<List<String>>();
        if (strings == null || strings.length == 0)
            return result;
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strings) {
            String code = encode(s);
            if (!map.containsKey(code)) {
                map.put(code, new ArrayList<String>());
            }
            map.get(code).add(s);
        }
        
        for(List<String> v : map.values()){
            Collections.sort(v);
            result.add(new ArrayList<String>(v));
        }
        return result;
    }
    
    private String encode(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < s.length(); i++) {
            int tmp = ((s.charAt(i) - s.charAt(i - 1)) + 26) % 26;
            sb.append(tmp);
        }
        return sb.toString();
    }
}

//244. Shortest Word Distance II
//This is a follow up of Shortest Word Distance. The only difference is now you are given the list of words and your method will be called repeatedly many times with different parameters. How would you optimize it?
//
//Design a class which receives a list of words in the constructor, and implements a method that takes two words word1 and word2 and return the shortest distance between these two words in the list.
//
//For example,
//Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
//
//Given word1 = “coding”, word2 = “practice”, return 3.
//Given word1 = "makes", word2 = "coding", return 1.
//
//Note:
//You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
public class WordDistance {

    private Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

    public WordDistance(String[] words) {

        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            List<Integer> list;
            if (map.containsKey(s)) {
                list = map.get(s);
            } else {
                list = new ArrayList<Integer>();
            }
            list.add(i);
            map.put(s, list);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);

        int min = Integer.MAX_VALUE;

        for (int a : l1) {
            for (int b : l2) {
                min = Math.min(Math.abs(b - a), min);
            }
        }
        return min;
    }
}

// Your WordDistance object will be instantiated and called as such:
// WordDistance wordDistance = new WordDistance(words);
// wordDistance.shortest("word1", "word2");
// wordDistance.shortest("anotherWord1", "anotherWord2");

//314. Binary Tree Vertical Order Traversal
//Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
//
//If two nodes are in the same row and column, the order should be from left to right.
//
//Examples:
//
//Given binary tree [3,9,20,null,null,15,7],
//   3
//  /\
// /  \
// 9  20
//    /\
//   /  \
//  15   7
//return its vertical order traversal as:
//[
//  [9],
//  [3,15],
//  [20],
//  [7]
//]
//Given binary tree [3,9,8,4,0,1,7],
//     3
//    /\
//   /  \
//   9   8
//  /\  /\
// /  \/  \
// 4  01   7
//return its vertical order traversal as:
//[
//  [4],
//  [9],
//  [3,0,1],
//  [8],
//  [7]
//]
//Given binary tree [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5),
//     3
//    /\
//   /  \
//   9   8
//  /\  /\
// /  \/  \
// 4  01   7
//    /\
//   /  \
//   5   2
//return its vertical order traversal as:
//[
//  [4],
//  [9,5],
//  [3,0,1],
//  [8,2],
//  [7]
//]
 public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
public class Solution314 {
    private class TreeColumnNode{
        public TreeNode treeNode;
        int col;
        public TreeColumnNode(TreeNode node, int col) {
            this.treeNode = node;
            this.col = col;
        }
    }
    
    public List<List<Integer>> verticalOrder(TreeNode root) {    
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if(root == null) {
            return res;
        }
        Queue<TreeColumnNode> queue = new LinkedList<TreeColumnNode>();
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        queue.offer(new TreeColumnNode(root, 0));
        int min = 0;
        int max = 0;
        
        while(!queue.isEmpty()) {
            TreeColumnNode node = queue.poll();
            if(map.containsKey(node.col)) {
                map.get(node.col).add(node.treeNode.val);
            } else {
                map.put(node.col, new ArrayList<Integer>(Arrays.asList(node.treeNode.val)));
            }
            
            if(node.treeNode.left != null) {
                queue.offer(new TreeColumnNode(node.treeNode.left, node.col - 1));
                min = Math.min(node.col - 1, min);
            }
            if(node.treeNode.right != null) {
                queue.offer(new TreeColumnNode(node.treeNode.right, node.col + 1));
                max = Math.max(node.col + 1, max);
            }
        }
        
        for(int i = min; i <= max; i++) {
            res.add(map.get(i));
        }
        
        return res;
    }
}

//246. Strobogrammatic Number
//A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
//
//Write a function to determine if a number is strobogrammatic. The number is represented as a string.
//
//For example, the numbers "69", "88", and "818" are all strobogrammatic.
public class Solution246 {
    public boolean isStrobogrammatic(String num) {
        HashMap<Character, Character> map = new HashMap<Character, Character>();
        map.put('1','1');
        map.put('0','0');
        map.put('6','9');
        map.put('9','6');
        map.put('8','8');
        int left = 0, right = num.length() - 1;
        while(left <= right){
            // 如果字母不存在映射或映射不对，则返回假
            if(!map.containsKey(num.charAt(right)) || num.charAt(left) != map.get(num.charAt(right))){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

//340. Longest Substring with At Most K Distinct Characters
//Given a string, find the length of the longest substring T that contains at most k distinct characters.
//
//For example, Given s = “eceba” and k = 2,
//
//T is "ece" which its length is 3.
public class Solution340 {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;
        Map<Character, Integer> visited = new HashMap<Character, Integer>();
        int start = 0, end = 0;
        int counter = 0;
        int maxLen = 0;
        while (end < s.length()) {
            char c = s.charAt(end);
            if (visited.containsKey(c)) {
                visited.put(c, visited.get(c) + 1);
                maxLen = Math.max(maxLen, end-start+1);
            } else {
                counter++;
                visited.put(c, 1);
                if (counter > k) {
                    while(counter > k && start < end) {
                        int ct = visited.get(s.charAt(start));
                        ct--;
                        if (ct == 0) {
                            --counter;
                            visited.remove(s.charAt(start));
                        } else if (ct > 0) {
                            visited.put(s.charAt(start), ct);
                        }
                        start++;
                    }
                }
                maxLen = Math.max(maxLen, end-start+1);
            }
            end++;
        }
        return maxLen;
    }
}

}