package Algorithm;

import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

/*
["gab", "cat", "bag", "alpha"] => [["gab", "bag"], ["bag", "gab"]]

["gab", "cat", "bag", "alpha", "nurses", "race", "car", "run"] => [["gab", "bag"], ["bag", "gab"], ["race", "car"], ["nurses", "run"]]

  ["cat", "dog", "elephant", "tac", "aire", "gigeria"] => [["cat", "tac"], ["tac", "cat"], ["aire", "gigeria"]]

  ["cat", "dog", "elephant", "tac", "cath", "aire", "gigeria"] => [["cat", "tac"], ["tac", "cat"], ["cath", "tac"], ["aire", "gigeria"]]

*/

class AirbnbFindPalindromPair {
  public List<List<String>> findPalindrom(String[] inputs) {
    List<List<String>> res = new ArrayList<List<String>>();
    Set<String> dict = new HashSet<String>();
    for (String s: inputs) {
      dict.add(s);
    }
    for (String s : inputs) {
      for (int j=0; j<s.length(); ++j) {
        String frontStr = s.substring(0, j);
        if (isPalindrom(frontStr)) {
          String rest = s.substring(j);
          String target = reverseStr(rest);
          if (dict.contains(target)) {
            List<String> curRes = new ArrayList<String>();
            curRes.add(s);
            curRes.add(target);
            if (!res.contains(curRes)) {
              res.add(curRes);
            }
          }
        }
        
        String backStr = s.substring(s.length() - j);
        if (isPalindrom(backStr)) {
          String rest = s.substring(0, s.length() - j);
          String target = reverseStr(rest);
          if (dict.contains(target)) {
            List<String> curRes = new ArrayList<String>();
            curRes.add(s);
            curRes.add(target);
            if (!res.contains(curRes)) {
              res.add(curRes);
            }
          }
        }
      }
    }
    return res;
  }
  
  public boolean isPalindrom(String s) {
    for (int i=0; i<s.length()/2; ++i) {
      if (s.charAt(i) != s.charAt(s.length()-1-i)) {
        return false;
      }
    }
    return true;
  }
  
  public String reverseStr(String s) {
    StringBuilder sb = new StringBuilder(s);
    return sb.reverse().toString();
  }
  
  public static void main(String[] args) {
    AirbnbFindPalindromPair sol = new AirbnbFindPalindromPair();
    String[] inputs = {"cat", "dog", "elephant", "tac", "cath", "aire", "gigeria"};
    List<List<String>> res = sol.findPalindrom(inputs);
    for (List<String> l : res) {
      System.out.println(l.toString());
    }
  }
}

