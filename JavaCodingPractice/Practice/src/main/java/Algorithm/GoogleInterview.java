package Algorithm;

import java.util.*;

public class GoogleInterview {
//    Longest Substring with At Most Two Distinct Characters
//    Input: "eceba"
//    Output: 3
//    Explanation: t is "ece" which its length is 3.
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() < 3) {
            return s.length();
        }
        int start = 0;
        int runner = 0;
        int maxLen = 0;

        Map<Character, Integer> cMap = new HashMap<>();
        while (runner < s.length()) {
            cMap.put(s.charAt(runner), runner);
            if (cMap.size() > 2) {
                int minIndex = Collections.min(cMap.values());
                start = minIndex + 1;
                cMap.remove(s.charAt(minIndex));
            }
            runner++;
            int length = runner - start;
            maxLen = length > maxLen ? length : maxLen;
        }
        return maxLen;
    }

    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        if(nums.length == 0){
            if(upper - lower == 0){
                result.add(upper + "");
            }
            else{
                result.add(lower + "->" + upper);
            }
            return result;
        }
        int start = nums[0];
        if((long)start - lower > 0 ){
            if((long)start - lower <= 1){
                result.add(lower + "");
            }
            else{
                result.add(lower + "->" + (start - 1));
            }
        }
        for(int i = 0; i < nums.length - 1; i ++){
            if(nums[i + 1] - nums[i] == 2){
                result.add((nums[i] + 1) + "");
            }
            else if((long)nums[i + 1] - nums[i] > 2){
                result.add((nums[i] + 1) + "->" + (nums[i + 1] - 1));
            }
        }
        int end = nums[nums.length - 1];
        if((long)upper - end > 0 ){
            if((long)upper - end <= 1){
                result.add(upper + "");
            }
            else{
                result.add((end + 1) + "->" + upper);
            }
        }
        return result;
    }

//    Next Closest Time
//    Input: "19:34"
//    Output: "19:39"
//    Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.  It is not 19:33, because this occurs 23 hours and 59 minutes later.
    public String nextClosestTime(String time) {
        int start = 60 * Integer.parseInt(time.substring(0, 2));
        start += Integer.parseInt(time.substring(3));
        int res = start;
        int elapsed = 24 * 60;
        Set<Integer> allowed = new HashSet();
        for (char c: time.toCharArray()) if (c != ':') {
            allowed.add(c - '0');
        }

        for (int h1: allowed) {
            for (int h2: allowed) if (h1 * 10 + h2 < 24) {
                for (int m1: allowed) {
                    for (int m2: allowed) if (m1 * 10 + m2 < 60) {
                        int cur = 60 * (h1 * 10 + h2) + (m1 * 10 + m2);
                        int candElapsed = Math.floorMod(cur - start, 24 * 60);
                        if (0 < candElapsed && candElapsed < elapsed) {
                            res = cur;
                            elapsed = candElapsed;
                        }
                    }
                }
            }
        }

        return String.format("%02d:%02d", res / 60, res % 60);
    }

    public static void main(String[] args) {
        GoogleInterview g = new GoogleInterview();
//        System.out.println(g.lengthOfLongestSubstringTwoDistinct("abaccc"));
//        List<String> res = g.findMissingRanges(new int[] {0,1,3,50,75}, 0, 99);
        List<String> res = g.findMissingRanges(new int[] {-1}, -2, -1);
        System.out.println(res);
    }
}
