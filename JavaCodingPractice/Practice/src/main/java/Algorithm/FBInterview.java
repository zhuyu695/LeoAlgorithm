package Algorithm;

import java.util.*;

public class FBInterview {
//    Longest Substring Without Repeating Characters
//
//            Solution
//    Given a string, find the length of the longest substring without repeating characters.
//
//    Example 1:
//
//    Input: "abcabcbb"
//    Output: 3
//    Explanation: The answer is "abc", with the length of 3.
//    Example 2:
//
//    Input: "bbbbb"
//    Output: 1
//    Explanation: The answer is "b", with the length of 1.
//    Example 3:
//
//    Input: "pwwkew"
//    Output: 3
//    Explanation: The answer is "wke", with the length of 3.
//    Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> cMap = new HashMap<Character, Integer>();
        int start = 0;
        int end = 0;
        int maxLength = 0;
        char[] cArray = s.toCharArray();
        while (end < s.length()) {
            if (cMap.containsKey(cArray[end])) {
                int curLen = end - start;
                if (curLen > maxLength) {
                    maxLength = curLen;
                }
                //abba, start been overwrite by a's previous index
                start = Math.max(cMap.get(cArray[end]) + 1, start);
                cMap.remove(cArray[end]);
            }
            cMap.put(cArray[end], end);
            ++end;
        }
        int curLen = end - start;
        if (curLen > maxLength) {
            maxLength = curLen;
        }
        return maxLength;
    }

//    Implement atoi which converts a string to an integer.
//    Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.
//    Example 1:
//
//    Input: "42"
//    Output: 42
//    Example 2:
//
//    Input: "   -42"
//    Output: -42
//    Explanation: The first non-whitespace character is '-', which is the minus sign.
//    Then take as many numerical digits as possible, which gets 42.
//    Example 3:
//
//    Input: "4193 with words"
//    Output: 4193
//    Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
//            Example 4:
//
//    Input: "words and 987"
//    Output: 0
//    Explanation: The first non-whitespace character is 'w', which is not a numerical
//    digit or a +/- sign. Therefore no valid conversion could be performed.
//            Example 5:
//
//    Input: "-91283472332"
//    Output: -2147483648
//    Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
//            Thefore INT_MIN (−231) is returned.
    public int myAtoi(String str) {
        String strNew = str.trim();
        if (strNew.length() == 0) {
            return 0;
        }
        int runner = 0;
        boolean isNegative = false;
        if (strNew.charAt(0) == '-') {
            isNegative = true;
            ++runner;
        } else if(strNew.charAt(0) == '+') {
            ++runner;
        } else if (!isNum(strNew.charAt(0))) {
            return 0;
        }
        long curVal = 0;
        while(runner < strNew.length() && isNum(strNew.charAt(runner))) {
            int value = toInt(strNew.charAt(runner));
            curVal *= 10;
            curVal = curVal + value;
            if (curVal > Integer.MAX_VALUE) {
                if (isNegative) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
            ++runner;
        }
        if (isNegative) {
            return (int) (curVal * -1);
        } else {
            return (int) curVal;
        }
    }

    public int toInt(char c) {
        return (int) (c - '0');
    }

    public boolean isNum(char c) {
        if (c > '9' || c < '0') {
            return false;
        } else {
            return true;
        }
    }

//    Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
//    The solution set must not contain duplicate triplets.
//    Example:
//    Given array nums = [-1, 0, 1, 2, -1, -4],
//    A solution set is:
//            [
//            [-1, 0, 1],
//            [-1, -1, 2]
//            ]
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> result = new HashSet<List<Integer>>();
        Arrays.sort(nums);
        Map<Integer, Integer> numMap = new HashMap<Integer, Integer>();
        for (int i = 0; i<nums.length; ++i) {
            numMap.put(nums[i], i);
        }
        for(int i = 0; i < nums.length - 2; ++i) {
            int expectedFirst = 0 - nums[i];
            for (int j = i + 1; j < nums.length - 1; ++j) {
                int expectedSec = expectedFirst - nums[j];
                if (numMap.containsKey(expectedSec) && numMap.get(expectedSec) > j) {
                    List<Integer> tmp = new ArrayList<Integer>();
                    tmp.add(nums[i]);
                    tmp.add(nums[j]);
                    tmp.add(expectedSec);
                    result.add(tmp);
                }
            }
        }
        return new ArrayList<>(result);
    }

//    Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
//
//            Symbol       Value
//    I             1
//    V             5
//    X             10
//    L             50
//    C             100
//    D             500
//    M             1000
//    For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
//
//    Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
//
//    I can be placed before V (5) and X (10) to make 4 and 9.
//    X can be placed before L (50) and C (100) to make 40 and 90.
//    C can be placed before D (500) and M (1000) to make 400 and 900.
//    Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
    static Map<Character, Integer> charMap = new HashMap<>();

    static {
        charMap.put('I', 1);
        charMap.put('V', 5);
        charMap.put('X', 10);
        charMap.put('L', 50);
        charMap.put('C', 100);
        charMap.put('D', 500);
        charMap.put('M', 1000);
    }

    public int romanToInt(String s) {

        char[] cArr = s.toCharArray();
        int curValue = 0;
        for (int i = 0; i < cArr.length; ++i) {
            if (isSpecVal(cArr[i]) && i + 1 < cArr.length) {
                int specVal = getSpecValue(cArr[i], cArr[i+1]);
                if (specVal != -1) {
                    curValue += specVal;
                    i++;
                } else {
                    curValue += charMap.get(cArr[i]);
                }
            } else {
                curValue += charMap.get(cArr[i]);
            }
        }
        return curValue;
    }

    public int getSpecValue(char c, char l) {
        switch(c) {
            case 'I':
                if (l == 'V' || l == 'X') return charMap.get(l) - 1;
            case 'X':
                if (l == 'L' || l == 'C') return charMap.get(l) - 10;
            case 'C':
                if (l == 'D' || l == 'M') return charMap.get(l) - 100;
            default:
                return -1;
        }
    }

    public boolean isSpecVal(char c) {
        if (c == 'I' || c == 'X' || c == 'C') {
            return true;
        } else {
            return false;
        }
    }

//    Given an array of strings, group anagrams together.
//    Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
//    Output:
//            [
//            ["ate","eat","tea"],
//            ["nat","tan"],
//            ["bat"]
//            ]
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> tracker = new HashMap<>();
        for (String s : strs) {
            char[] cArr = s.toCharArray();
            Arrays.sort(cArr);
            String tmp = String.valueOf(cArr);
            if (tracker.containsKey(tmp)) {
                List<String> l = tracker.get(tmp);
                l.add(s);
            } else {
                List<String> l = new ArrayList<String>();
                l.add(s);
                tracker.put(tmp, l);
            }
        }
        List<List<String>> res = new ArrayList<List<String>>(tracker.values());
        return res;
    }

//    Read N Characters Given Read4
    public int read4(char buf[]) {
        return buf.length;
    }

    public int read(char[] buf, int n) {
        char[] buffer = new char[4];
        int offset = 0;
        int bufferIndex = 0;
        int bufferCnt = 0;
        while (offset < n) {
            if (bufferIndex == bufferCnt) {
                bufferCnt = read4(buffer);
                bufferIndex = 0;
            }
            if (bufferCnt == 0) {
                break;
            }
            while (offset < n && bufferIndex < bufferCnt) {
                buf[offset++] = buffer[bufferIndex++];
            }
        }

        return offset;
    }

    //call multiple times
    private char[] buffer = new char[4];
    private int bufferOffset = 0;
    private int bufferCnt = 0;

    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    public int read2(char[] buf, int n) {
        int offset = 0;
        while (offset < n) {
            if (bufferOffset == bufferCnt) {
                bufferCnt = read4(buffer);
                bufferOffset = 0;
            }
            if(bufferCnt == 0) {
                break;
            }
            while (offset < n && bufferOffset < bufferCnt) {
                buf[offset++] = buffer[bufferOffset++];
            }
        }
        return offset;
    }

//    Given two strings s and t, determine if they are both one edit distance apart.
//    There are 3 possiblities to satisify one edit distance apart:
//
//    Insert a character into s to get t
//    Delete a character from s to get t
//    Replace a character of s to get t
    public boolean isOneEditDistance(String s, String t) {
        if (s.length() < t.length()) {
            return isOneEditDistance(t, s);
        }
        if (s.equals(t)) {
            return false;
        }
        for(int i=0; i<s.length(); ++i) {
            //insert or delete
            if ((s.substring(0, i) + s.substring(i+1)).equals(t)) {
                return true;
            }
            //replace
            if (i < t.length() &&
                    (s.substring(0, i) + s.substring(i+1)).equals(t.substring(0, i) + t.substring(i+1))) {
                return true;
            }
        }
        return false;
    }

//    Given a string, find the length of the longest substring T that contains at most k distinct characters.
//
//            Example 1:
//
//    Input: s = "eceba", k = 2
//    Output: 3
//    Explanation: T is "ece" which its length is 3.
//    Example 2:
//
//    Input: s = "aa", k = 1
//    Output: 2
//    Explanation: T is "aa" which its length is 2.
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s.length() * k == 0) return 0;

        int start = 0;
        int end = 0;
        int maxLength = 0;
        LinkedHashMap<Character, Integer> cMap = new LinkedHashMap();
        while(end < s.length()) {
            Character c = s.charAt(end);
            cMap.remove(c);
            cMap.put(c, end);
            ++end;
            if (cMap.size() == k + 1) {
                Map.Entry<Character, Integer> entry = cMap.entrySet().iterator().next();
                System.out.println("char: " + entry.getKey() + " value: " + entry.getValue());
                cMap.remove(entry.getKey());
                start = entry.getValue() + 1;
                System.out.println("start: " + start);
            }
            maxLength = Math.max(maxLength, end - start);
        }
        return maxLength;
    }

    //Shortest Distance from All Buildings
    public int shortestDistance(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int[][] distance = new int[grid.length][grid[0].length];
        int[][] rank = new int[grid.length][grid[0].length];
        int rankScore = 0;

        for(int i=0; i<grid.length; ++i) {
            for(int j=0; j<grid[0].length; ++j) {
                if (grid[i][j] == 1) {
                    ++rankScore;
                    bfs(grid, i, j, distance, rank);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for(int i=0; i<grid.length; ++i) {
            for(int j=0; j<grid[0].length; ++j) {
                if (grid[i][j] == 0 && rank[i][j] == rankScore) {
                    min = min > distance[i][j] ? distance[i][j] : min;
                }
            }
        }
        return min;
    }

    public void bfs(int[][] grid, int r, int c, int[][] distance, int[][] rank) {
        int colSize = grid[0].length;
        int id = r*colSize + c;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(id);
        int[][] visited = new int[grid.length][colSize];
        int level = 0;

        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0; i<size; ++i) {
                int value = queue.poll();
                int row = value / colSize;
                int col = value % colSize;
                distance[row][col] += level;
                rank[row][col]++;

                if(row - 1 >= 0 && grid[row-1][col] == 0 && visited[row-1][col] == 0) {
                    queue.offer((row-1) * colSize + col);
                    visited[row-1][col] = 1;
                }
                if(row + 1 < grid.length && grid[row+1][col] == 0 && visited[row+1][col] == 0) {
                    queue.offer((row+1) * colSize + col);
                    visited[row+1][col] = 1;
                }
                if(col - 1 >= 0 && grid[row][col-1] == 0 && visited[row][col-1] == 0) {
                    queue.offer(row * colSize + col - 1);
                    visited[row][col-1] = 1;
                }
                if(col + 1 < colSize && grid[row][col+1] == 0 && visited[row][col+1] == 0) {
                    queue.offer(row * colSize + col + 1);
                    visited[row][col+1] = 1;
                }
            }
            level++;
        }
    }

    //Convert Binary Search Tree to Sorted Doubly Linked List
    public class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return root;
        }
        Node tmp = root;
        inorderTraverse(root);
        while(root.right != null) {
            root = root.right;
        }
        Node end = root;
        while(tmp.left != null) {
            tmp = tmp.left;
        }
        Node start = tmp;
        start.left = root;
        root.right = start;
        return start;
    }

    private Node inorderTraverse(Node root) {
        if (root.left == null && root.right == null) {
            return root;
        }
        if (root.left != null) {
            Node n = inorderTraverse(root.left);
            while(n.right != null) {
                n = n.right;
            }
            n.right = root;
            root.left = n;
        }
        if (root.right != null) {
            Node n = inorderTraverse(root.right);
            while(n.left != null) {
                n = n.left;
            }
            n.left = root;
            root.right = n;
        }
        return root;
    }

    //Convert Binary Search Tree to Sorted Doubly Linked List1
    Node first = null;
    Node last = null;

    public void traverseHelper(Node node) {
        if (node != null) {
            traverseHelper(node.left);
            if (last != null) {
                last.right = node;
                node.left = last;
            } else {
                first = node;
            }
            last = node;
            traverseHelper(node.right);
        }
    }

    public Node treeToDoublyList1(Node root) {
        if (root == null) return null;

        traverseHelper(root);
        // close DLL
        last.right = first;
        first.left = last;
        return first;
    }

    //Binary Tree Vertical Order Traversal
     public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
     }
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if(root == null) {
            return res;
        }
        Map<Integer, List<Integer>> levelMap = new TreeMap<>();
        traverse(root, levelMap, 0);
        for (Map.Entry<Integer, List<Integer>> entry: levelMap.entrySet()) {
            res.add(entry.getValue());
        }
        return res;
    }

    public void traverse(TreeNode root, Map<Integer, List<Integer>> levelMap, int level) {
        if (levelMap.containsKey(level)) {
            levelMap.get(level).add(root.val);
        } else {
            List<Integer> levelList = new ArrayList<>();
            levelList.add(root.val);
            levelMap.put(level, levelList);
        }
        if (root.left != null) {
            traverse(root.left, levelMap, level-1);
        }
        if (root.right != null) {
            traverse(root.right, levelMap, level+1);
        }
    }

    //number of islands
    public int numIslands(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int[][] visited = new int[grid.length][grid[0].length];

        int islands = 0;
        for(int i=0; i<grid.length; ++i) {
            for(int j=0; j<grid[0].length; ++j) {
                if (grid[i][j] == '1' && visited[i][j] == 0) {
                    traverse(grid, visited, i , j);
                    islands++;
                }
            }
        }
        return islands;
    }

    private void traverse(char[][] grid, int[][] visited, int r, int c) {
        if (visited[r][c] == 1) {
            return;
        }
        visited[r][c] = 1;
        if (r-1 >= 0 && grid[r-1][c] == '1') {
            traverse(grid, visited, r-1, c);
        }
        if (r+1 < grid.length && grid[r+1][c] == '1') {
            traverse(grid, visited, r+1, c);
        }
        if (c-1 >= 0 && grid[r][c-1] == '1') {
            traverse(grid, visited, r, c-1);
        }
        if (c+1 < grid[0].length && grid[r][c+1] == '1') {
            traverse(grid, visited, r, c+1);
        }
        return;
    }

//    Strobogrammatic Number II
//    A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
//
//    Find all strobogrammatic numbers that are of length = n.
//
//            Example:
//
//    Input:  n = 2
//    Output: ["11","69","88","96"]
    char[] tArr = {'0', '1', '8', '6', '9'};
    char[] lArr = {'0', '1', '8'};
    public List<String> findStrobogrammatic(int n) {
        Map<Character, Character> tracker = new HashMap<>();
        tracker.put('0', '0');
        tracker.put('1', '1');
        tracker.put('8', '8');
        tracker.put('6', '9');
        tracker.put('9', '6');

        List<String> res = new ArrayList<>();
        List<Character> cur = new ArrayList<>();
        generate(n, 0, res, cur, tracker);
        return res;
    }

    private void generate(int n, int index, List<String> res, List<Character> cur, Map<Character, Character> tracker) {
        if(index == n / 2) {
            StringBuilder sbuilder = new StringBuilder();
            if (n % 2 == 0) {
                for(Character c : cur) {
                    sbuilder.append(c);
                }
                for(int i=cur.size()-1; i>=0; --i) {
                    sbuilder.append(tracker.get(cur.get(i)));
                }
                res.add(sbuilder.toString());
                return ;
            } else {
                for (char c: lArr) {
                    for(Character ct : cur) {
                        sbuilder.append(ct);
                    }
                    sbuilder.append(c);
                    for(int i=cur.size()-1; i>=0; --i) {
                        sbuilder.append(tracker.get(cur.get(i)));
                    }
                    res.add(sbuilder.toString());
                    sbuilder = new StringBuilder();
                }
            }
        } else {
            for(int i=0; i<tArr.length; ++i) {
                if (index == 0 && n > 1 && tArr[i] == '0') {
                    continue;
                }
                cur.add(tArr[i]);
                generate(n, index+1, res, cur, tracker);
                cur.remove(cur.size() - 1);
            }
        }
    }

    // Strobogrammatic Number III
//    A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
//    Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.
//            For example,
//    Given low = "50", high = "100", return 3. Because 69, 88, and 96 are three strobogrammatic numbers.
//    Note: Because the range might be a large number, the low and high numbers are represented as string.
//    Tips:
//    Construct char array from lenLow to lenHigh and increase count when s is between low and high. Add the stro pairs from outside to inside until left > right
    public int strobogrammaticInRange(String low, String high) {
        int m = low.length(), n = high.length();
        List<String> result = new ArrayList<String>();
        for(int i=m; i<=n; i++){
            result.addAll(findStrobogrammatic1(i));
        }
        int i=0;
        int count=result.size();
        while(i<result.size() && result.get(i).length()==low.length()){
            if(result.get(i).compareTo(low)<0){
                count--;
            }
            i++;
        }
        i=result.size()-1;
        while(i>=0 && result.get(i).length()==high.length()){
            if(result.get(i).compareTo(high)>0){
                count--;
            }
            i--;
        }
        return count;
    }
    //------
    public List<String> findStrobogrammatic1(int n) {
        HashMap<Character, Character> map = new HashMap<>();
        map.put('1','1');
        map.put('0','0');
        map.put('6','9');
        map.put('9','6');
        map.put('8','8');
        List<String> list = new ArrayList<>();
        char[] arr = new char[n];
        generateNum(list, arr, 0, n, map);
        Collections.sort(list);
        return list;
    }
    public void generateNum(List<String> list, char[] buffer, int idx, int n, Map<Character, Character> map){
        if(idx==(n+1)/2){
            list.add( String.valueOf(buffer));
            return;
        }
        for(char key:map.keySet()){
            if(idx==0 && n>1 && key=='0'){
                continue;
            }
            if(idx==(n/2) && (key=='6'||key=='9')){
                continue;
            }
            buffer[idx] = key;
            buffer[n-idx-1] = map.get(key);
            generateNum(list, buffer, idx+1, n, map);
        }
    }

    //Longest Palindrome
    public String longestPalindrome1(String s) {
        if (s.isEmpty()) {
            return "";
        }

        if (s.length() == 1) {
            return s;
        }

        String longest = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            // get longest palindrome with center of i
            String tmp = helper(s, i, i);
            if (tmp.length() > longest.length()) {
                longest = tmp;
            }

            // get longest palindrome with center of i, i+1
            tmp = helper(s, i, i + 1);
            if (tmp.length() > longest.length()) {
                longest = tmp;
            }
        }

        return longest;
    }

    // Given a center, either one letter or two letter,
// Find longest palindrome
    public String helper(String s, int begin, int end) {
        while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
            begin--;
            end++;
        }
        return s.substring(begin + 1, end);
    }

    //Longest Palindrome DP:
    public String longestPalindrome(String s) {
        if(s==null || s.length()<=1)
            return s;

        int len = s.length();
        int maxLen = 1;
        boolean [][] dp = new boolean[len][len];

        String longest = null;
        for(int l=0; l<s.length(); l++){
            for(int i=0; i<len-l; i++){
                int j = i+l;
                if(s.charAt(i)==s.charAt(j) && (j-i<=2||dp[i+1][j-1])){
                    dp[i][j]=true;

                    if(j-i+1>maxLen){
                        maxLen = j-i+1;
                        longest = s.substring(i, j+1);
                    }
                }
            }
        }

        return longest;
    }

    //word break
//    Input: s = "leetcode", wordDict = ["leet", "code"]
//    Output: true
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        Set<String> wSet = new HashSet<>(wordDict);
        dp[0] = true;
        for (int i = 0; i < s.length() + 1; ++i) {
            for (int j = 0; j < i; ++j) {
                if (wSet.contains(s.substring(j, i)) && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }


    private int[][] dp;

    //Range Sum Query 2D - Immutable
    public void populateNumMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;
        dp = new int[matrix.length + 1][matrix[0].length + 1];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                dp[r + 1][c + 1] = dp[r + 1][c] + dp[r][c + 1] + matrix[r][c] - dp[r][c];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
    }

    //Remove Invalid Parentheses
//    Input: "()())()"
//    Output: ["()()()", "(())()"]
    public List<String> removeInvalidParentheses(String s) {
        int leftCount = 0;
        int rightCount = 0;

        for (int i=0; i<s.length(); ++i) {
            if(s.charAt(i) == '(') {
                leftCount++;
            } else if (s.charAt(i) == ')' && leftCount > 0) {
                leftCount--;
            } else if (s.charAt(i) == ')') {
                rightCount++;
            }
        }

        Set<String> res = new HashSet<>();
        traversePar(s, leftCount, rightCount, 0, 0, res, 0, new StringBuilder());
        return new ArrayList<>(res);
    }

    //lCount and rCount are total num of paranthesis need to be removed.
    //lCur and rCur are counter of current paranthesis
    private void traversePar(String s, int lCount, int rCount, int lCur, int rCur, Set<String> res, int index, StringBuilder sbuilder) {
        if (index == s.length() && lCount == 0 && rCount == 0 && lCur == rCur) {
            res.add(sbuilder.toString());
        }
        if (index == s.length()) {
            return;
        }
        if (s.charAt(index) == '(') {
            if (lCount > 0) {
                //ignore (
                traversePar(s, lCount-1, rCount, lCur, rCur, res, index+1, sbuilder);
            }
            sbuilder.append(s.charAt(index));
            traversePar(s, lCount, rCount, lCur+1, rCur, res, index+1, sbuilder);
            sbuilder.setLength(sbuilder.length()-1);
        } else if (s.charAt(index) == ')') {
            if (rCount > 0 || lCount <= rCount) {
                //ignore (
                traversePar(s, lCount, rCount-1, lCur, rCur, res, index+1, sbuilder);
            }
            if (lCur > rCur) {
                sbuilder.append(s.charAt(index));
                traversePar(s, lCount, rCount, lCur, rCur + 1, res, index + 1, sbuilder);
                sbuilder.setLength(sbuilder.length() - 1);
            }
        } else {
            sbuilder.append(s.charAt(index));
            traversePar(s, lCount, rCount, lCur, rCur, res, index+1, sbuilder);
            sbuilder.setLength(sbuilder.length()-1);
        }
    }


//    Letter Combinations of a Phone Number
//    Input: "23"
//    Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
    public List<String> letterCombinations(String digits) {
        Map<Character, char[]> digitMap = new HashMap<>();

        digitMap.put('1', new char[] {});
        digitMap.put('2', new char[] {'a','b','c'});
        digitMap.put('3', new char[] {'d','e','f'});
        digitMap.put('4', new char[] {'g','h','i'});
        digitMap.put('5', new char[] {'j','k','l'});
        digitMap.put('6', new char[] {'m','n','o'});
        digitMap.put('7', new char[] {'p','q','r', 's'});
        digitMap.put('8', new char[] {'t','u','v'});
        digitMap.put('9', new char[] {'w','x','y','z'});
        digitMap.put('0', new char[] {});

        List<String> res = new ArrayList<>();
        traverseLetter(digits, 0, res, new StringBuilder(), digitMap);
        return res;
    }

    public void traverseLetter(String digits, int index, List<String> res, StringBuilder sbuilder, Map<Character, char[]> digitMap) {
        if (index == digits.length()) {
            res.add(sbuilder.toString());
            return;
        }
        char digitArr[] = digitMap.get(digits.charAt(index)) ;
        for(int i=0; i<digitArr.length; ++i) {
            sbuilder.append(digitArr[i]);
            traverseLetter(digits, index+1, res, sbuilder, digitMap);
            sbuilder.deleteCharAt(sbuilder.length()-1);
        }
    }

    // Encodes a tree to a single string.
//    You may serialize the following tree:
//
//            1
//            / \
//            2   3
//            / \
//            4   5
//
//    as "[1,2,3,null,null,4,5]"
    public String serialize(TreeNode root) {
        return serializeHelper(root, "");
    }

    public String serializeHelper(TreeNode root, String s) {
        if (root == null) {
            s += "null,";
        } else {
            s += String.valueOf(root.val) + ",";
            s = serializeHelper(root.left, s);
            s = serializeHelper(root.right, s);
        }
        return s;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] sArr = data.split(",");
        List<String> sList = new LinkedList<>(Arrays.asList(sArr));
        return deserializeHelper(sList);
    }

    public TreeNode deserializeHelper(List<String> src) {
        if (src.size() == 0) {
            return null;
        }
        if (src.get(0).equals("null")) {
            src.remove(0);
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(src.get(0)));
        src.remove(0);
        root.left = deserializeHelper(src);
        root.right = deserializeHelper(src);
        return root;
    }

    //Binary Search Tree Iterator
    class BSTIterator {
        Stack<TreeNode> stack;

        public BSTIterator(TreeNode root) {
            stack = new Stack<>();
            while (root != null) {
                stack.add(root);
                root = root.left;
            }
        }

        /** @return the next smallest number */
        public int next() {
            TreeNode cur = stack.pop();
            if (cur.right != null) {
                TreeNode tmp = cur.right;
                while (tmp != null) {
                    stack.add(tmp);
                    tmp = tmp.left;
                }
            }
            return cur.val;
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return stack.size() > 0;
        }
    }

    //Add Binary
//    Input: a = "1010", b = "1011"
//    Output: "10101"
    public String addBinary(String a, String b) {
        if (b.length() > a.length()) {
            return addBinary(b, a);
        }
        String aRev = (new StringBuilder(a)).reverse().toString();
        String bRev = (new StringBuilder(b)).reverse().toString();
        int carry = 0;
        StringBuilder sbuilder = new StringBuilder();
        for(int i = 0; i<aRev.length(); ++i) {
            int sum = 0;
            sum += carry;
            if (i<bRev.length()) {
                sum += (int)(bRev.charAt(i) - '0');
            }
            sum += (int) (aRev.charAt(i) - '0');
            carry = sum / 2 ;
            sum = sum % 2;
            sbuilder.insert(0, String.valueOf(sum));
        }
        if (carry > 0) {
            sbuilder.insert(0, String.valueOf(carry));
        }
        return sbuilder.toString();
    }

//    Product of Array Except Self
//    Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
//
//    Example:
//
//    Input:  [1,2,3,4]
//    Output: [24,12,8,6]
    public int[] productExceptSelf(int[] nums) {
        if (nums.length == 0) {
            return nums;
        }
        int[] lPower = new int[nums.length];
        lPower[0] = 1;
        for (int i=1; i<nums.length; ++i) {
            lPower[i] = lPower[i-1] * nums[i-1];
        }

        int rCum = 1;
        for(int i=nums.length-1; i>=0; --i) {
            lPower[i] = lPower[i]* rCum;
            rCum = rCum * nums[i];
        }
        return lPower;
    }

    //Integer to English Words
    public String one(int num) {
        switch(num) {
            case 1: return "One";
            case 2: return "Two";
            case 3: return "Three";
            case 4: return "Four";
            case 5: return "Five";
            case 6: return "Six";
            case 7: return "Seven";
            case 8: return "Eight";
            case 9: return "Nine";
        }
        return "";
    }

    public String twoLessThan20(int num) {
        switch(num) {
            case 10: return "Ten";
            case 11: return "Eleven";
            case 12: return "Twelve";
            case 13: return "Thirteen";
            case 14: return "Fourteen";
            case 15: return "Fifteen";
            case 16: return "Sixteen";
            case 17: return "Seventeen";
            case 18: return "Eighteen";
            case 19: return "Nineteen";
        }
        return "";
    }

    public String ten(int num) {
        switch(num) {
            case 2: return "Twenty";
            case 3: return "Thirty";
            case 4: return "Forty";
            case 5: return "Fifty";
            case 6: return "Sixty";
            case 7: return "Seventy";
            case 8: return "Eighty";
            case 9: return "Ninety";
        }
        return "";
    }

    public String two(int num) {
        if (num == 0) return "";
        else if (num < 10) return one(num);
        else if (num < 20) return twoLessThan20(num);
        else {
            int tenner = num / 10;
            int rest = num - tenner * 10;
            if (rest != 0) {
                return ten(tenner) + " " + one(rest);
            } else {
                return ten(tenner);
            }
        }
    }

    public String three(int num) {
        int hundred = num / 100;
        int rest = num - hundred * 100;
        if (hundred * rest != 0) {
            return one(hundred) + " Hundred " + two(rest);
        } else if ((hundred == 0) && (rest != 0)) {
            return two(rest);
        } else {
            return one(hundred) + " Hundred";
        }
    }

    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        int billion = num / 1000000000;
        int million = (num - billion * 1000000000) / 1000000;
        int thousand = (num - billion * 1000000000 - million * 1000000) / 1000;
        int rest = num - billion * 1000000000 - million * 1000000 - thousand * 1000;

        String result = "";
        if (billion != 0) {
            result = three(billion) + " Billion";
        }
        if (million != 0) {
            if (!result.isEmpty()) result += " ";
            result += three(million) + " Million";
        }
        if (thousand != 0) {
            if (!result.isEmpty()) result += " ";
            result += three(thousand) + " Thousand";
        }
        if (rest != 0) {
            if (!result.isEmpty()) result += " ";
            result += three(rest);
        }
        return result;
    }

    public static void main(String[] args) {
        FBInterview p = new FBInterview();
//        int[] input = {-1,0,1,2,-1,-4};
        int[][] input = {{1,0},{0,1}};
//        p.findStrobogrammatic1(2).forEach(System.out::println);
//        p.longestPalindrome1("babad");
//        p.removeInvalidParentheses("()())()");
//        p.letterCombinations("23");
//        TreeNode s = p.deserialize("1,2,3,null,null,4,5");
//        p.serialize(s);
          p.addBinary("1010", "1011");
    }
}
