package Algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class GoogleInterview {
//    Longest Substring with At Most Two Distinct Characters
//    Input: "eceba"
//    Output: 3
//    Explanation: t is "ece" which its length is 3.
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int start = 0;
        int runner = 0;
        int maxLen = 0;

        Map<Character, Integer> cMap = new LinkedHashMap<>();
        while (runner < s.length()) {
            cMap.put(s.charAt(runner), runner);
            if (cMap.size() > 2) {
                int length = runner - start;
                maxLen = length > maxLen ? length : maxLen;
                Map.Entry<Character, Integer> entry = cMap.entrySet().iterator().next();
                start = entry.getValue() + 1;
                cMap.remove(entry.getKey());
            } else {
                maxLen++;
            }
            runner++;
        }
        return maxLen;
    }

    public static void main(String[] args) {
        GoogleInterview g = new GoogleInterview();
        System.out.println(g.lengthOfLongestSubstringTwoDistinct("sedffefeffe"));
    }
}
