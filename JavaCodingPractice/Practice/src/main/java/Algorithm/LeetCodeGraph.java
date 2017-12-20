package Algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LeetCodeGraph {
//269. Alien Dictionary
//There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.
//
//For example,
//Given the following words in dictionary,
//
//[
//  "wrt",
//  "wrf",
//  "er",
//  "ett",
//  "rftt"
//]
//The correct order is: "wertf".
//
//Note:
//You may assume all letters are in lowercase.
//If the order is invalid, return an empty string.
//There may be multiple valid order of letters, return any one of them is fine.
public class Solution269 {
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = buildGraph(words);
        Map<Character, Integer> visited = new HashMap<Character, Integer>();

        for (char c : graph.keySet()) {
            visited.put(c, 0);
        }

        StringBuilder sbuilder = new StringBuilder();
        for (Character c: graph.keySet()) {
            if (!visited.containsKey(c) || visited.get(c) == 0) {
                if (isCycle(c, graph, visited, sbuilder)) {
                    return "";
                }
            }
        }
        return sbuilder.toString();
    }

    public Map<Character, Set<Character>> buildGraph(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
        for (int i=0; i<words.length; ++i) {
            for (int j=0; j<words[i].length(); ++j) {
                if(!graph.containsKey(words[i].charAt(j))){
                    graph.put(words[i].charAt(j), new HashSet<Character>());
                }
            }
            if (i > 0) {
                order(graph, words[i - 1], words[i]);
            }
        }
        return graph;
    }

    public void order(Map<Character, Set<Character>> graph, String cur, String next) {
        int minLen = Math.min(cur.length(), next.length());
        for (int j=0; j < minLen; ++j) {
            char c = cur.charAt(j);
            char n = next.charAt(j);
            if (c != n) {
                graph.get(c).add(n);
                break;
            }
        }
    }

    public boolean isCycle(char c, Map<Character, Set<Character>> graph, Map<Character, Integer> visited, StringBuilder sbuiler) {
        if (graph.containsKey(c)) {
            visited.put(c, 1);
            for (char v : graph.get(c)) {
                if (visited.get(v) == 1 || (visited.get(v) == 0 && isCycle(v, graph, visited, sbuiler))) {
                    return true;
                }
            }
        }
        visited.put(c, 2);
        sbuiler.insert(0, c);
        return false;
    }
}

	
}
