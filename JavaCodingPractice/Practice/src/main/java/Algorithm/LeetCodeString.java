package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LeetCodeString {
//	161. One Edit Distance
//	Given two strings S and T, determine if they are both one edit distance apart.
public class Solution161 {
    public boolean isOneEditDistance(String s, String t) {
        if (s == null || t == null || s.equals(t))
            return false;
        if (Math.abs(s.length() - t.length()) > 1)
            return false;
        if (s.length() > t.length())
            return oneDelete(s, t);
        else if (s.length() == t.length())
            return oneModify(s, t);
        return oneDelete(t, s);
    }
    
    private boolean oneDelete(String s, String t) {
        for (int i = 0; i < s.length(); i++) {
            if (i >= t.length() || s.charAt(i) != t.charAt(i) ) {
                return delete(s, i).equals(t);
            }
        }
        return true;
    }
    
    private boolean oneModify(String s, String t) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                return modify(s, t, i).equals(t);
            }
        }
        return true;
    }
    
    private String modify(String s, String t, int index) {
        return s.substring(0, index) + t.charAt(index) + s.substring(index + 1, s.length());
    }
    
    private String delete(String s, int index) {
        return s.substring(0, index) + s.substring(index + 1, s.length());
    }    
}

//157. Read N Characters Given Read4
//The API: int read4(char *buf) reads 4 characters at a time from a file.
//
//The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.
//
//By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.
//
//Note:
//The read function will only be called once for each test case.
public class Reader4 {
}

public int read4(char[] tmp) {
	return 0;
}

public class Solution157 extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    public int read(char[] buf, int n) {
        for(int i = 0; i < n; i += 4){
            char[] tmp = new char[4];
            // 将数据读入临时数组
            int len = read4(tmp);
            // 将临时数组拷贝至buf数组，这里拷贝的长度是本次读到的个数和剩余所需个数中较小的
            System.arraycopy(tmp, 0, buf, i, Math.min(len, n - i));
            // 如果读不满4个，说明已经读完了，返回总所需长度和目前已经读到的长度的较小的
            if(len < 4) return Math.min(i + len, n);
        }
        // 如果循环内没有返回，说明读取的字符是4的倍数
        return n;
    }
}

//158. Read N Characters Given Read4 II - Call multiple times
//The API: int read4(char *buf) reads 4 characters at a time from a file.
//
//The return value is the actual number of characters read. For example, it returns 3 if there is only 3 characters left in the file.
//
//By using the read4 API, implement the function int read(char *buf, int n) that reads n characters from the file.
//
//Note:
//The read function may be called multiple times.
//
//Show Company Tags
//Show Tags
//Show Similar Problems
public class Solution158 extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    Queue<Character> remain = new LinkedList<Character>();
    
    public int read(char[] buf, int n) {
        int i = 0;
        // 队列不为空时，先读取队列中的暂存字符
        while(i < n && !remain.isEmpty()){
            buf[i] = remain.poll();
            i++;
        }
        for(; i < n; i += 4){
            char[] tmp = new char[4];
            int len = read4(tmp);
            // 如果读到字符多于我们需要的字符，需要暂存这些多余字符
            if(len > n - i){
                System.arraycopy(tmp, 0, buf, i, n - i);
                // 把多余的字符存入队列中
                for(int j = n - i; j < len; j++){
                    remain.offer(tmp[j]);
                }
            // 如果读到的字符少于我们需要的字符，直接拷贝
            } else {
                System.arraycopy(tmp, 0, buf, i, len);
            }
            // 同样的，如果读不满4个，说明数据已经读完，返回总所需长度和目前已经读到的长度的较小的
            if(len < 4) return Math.min(i + len, n);
        }
        // 如果到这里，说明都是完美读取，直接返回n
        return n;
    }
}

//159. Longest Substring with At Most Two Distinct Characters
//Given a string, find the length of the longest substring T that contains at most 2 distinct characters.
//
//For example, Given s = “eceba”,
//
//T is "ece" which its length is 3.
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

//186. Reverse Words in a String II
//Given an input string, reverse the string word by word. A word is defined as a sequence of non-space characters.
//
//The input string does not contain leading or trailing spaces and the words are always separated by a single space.
//
//For example,
//Given s = "the sky is blue",
//return "blue is sky the".
//
//Could you do it in-place without allocating extra space?
public class Solution186 {
    public void reverseWords(char[] s) {
        reverse(s, 0, s.length-1);
        for (int i=0, j=0; j<=s.length; j++) {
            if (j==s.length || s[j]==' ') {
                reverse(s, i, j-1);
                i =  j + 1;
            }
        }
    }

    private void reverse(char [] s, int begin, int end) {
        while (begin < end) {
            char temp = s[end];
            s[end] = s[begin];
            s[begin] = temp;
            begin ++;
            end --;
        }
    }
}

//271. Encode and Decode Strings
//Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is decoded back to the original list of strings.
//
//Machine 1 (sender) has the function:
//
//string encode(vector<string> strs) {
//  // ... your code
//  return encoded_string;
//}
//Machine 2 (receiver) has the function:
//vector<string> decode(string s) {
//  //... your code
//  return strs;
//}
//So Machine 1 does:
//
//string encoded_string = encode(strs);
//and Machine 2 does:
//
//vector<string> strs2 = decode(encoded_string);
//strs2 in Machine 2 should be the same as strs in Machine 1.
//
//Implement the encode and decode methods.
//
//Note:
//The string may contain any possible characters out of 256 valid ascii characters. Your algorithm should be generalized enough to work on any possible characters.
//Do not use class member/global/static variables to store states. Your encode and decode algorithms should be stateless.
//Do not rely on any library method such as eval or serialize methods. You should implement your own encode/decode algorithm.

public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if (strs == null || strs.size() == 0) 
            return "";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strs.size(); i++) {
            int count = strs.get(i).length();
            result.append(count).append('#').append(strs.get(i));
        }
        return result.toString();
        
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> result = new ArrayList<String>();
        int index = 0;
        while(index < s.length()) {
            int start = index;
            while (s.charAt(index) != '#') {
                index++;
            }
            
            Integer count = new Integer(s.substring(start, index));
            String cur = s.substring(index + 1, index + 1 + count);
            result.add(cur);
            index = index + 1 + count;
        }
        
        return result;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));

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
//Note: For the return value, each inner list's elements must follow the lexicographic order.
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

//293. Flip Game
//You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.
//
//Write a function to compute all possible states of the string after one valid move.
//
//For example, given s = "++++", after one move, it may become one of the following states:
//
//[
//  "--++",
//  "+--+",
//  "++--"
//]
//If there is no valid move, return an empty list [].
public class Solution293 {
    public List<String> generatePossibleNextMoves(String s) {  
        List<String> res = new ArrayList<String>();
        char[] cArray = s.toCharArray();
        for (int i=0; i<cArray.length-1; ++i) {
            if (cArray[i] == '+' && cArray[i+1] == '+') {
                res.add(s.substring(0, i) + "--" + s.substring(i+2));
            }
        }
        return res;
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
