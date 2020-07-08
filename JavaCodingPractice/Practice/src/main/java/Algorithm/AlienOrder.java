package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of words from the dictionary, where words are sorted lexicographically by the rules of this new language.Derive the order of letters in this language.

For example,
Given the following words in dictionary,

[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]
The correct order is: "wertf".
 */
public class AlienOrder {
    //new
    private Map<Character, List<Character>> adjList = new HashMap<>();
    private Map<Character, Boolean> seen = new HashMap<>();
    private StringBuilder output = new StringBuilder();

    public String alienOrder(String[] words) {

        // Step 0: Put all unique letters into reverseAdjList as keys.
        for (String word : words) {
            for (char c : word.toCharArray()) {
                adjList.putIfAbsent(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges and add reverse edges to reverseAdjList.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    adjList.get(word1.charAt(j)).add(word2.charAt(j));
                    break;
                }
            }
        }

        // Step 2: DFS to build up the output list.
        for (Character c : adjList.keySet()) {
            boolean result = dfs(c);
            if (!result) return "";
        }
        return output.toString();
    }

    // Return true iff no cycles detected.
    private boolean dfs(Character c) {
        if (seen.containsKey(c)) {
            return seen.get(c); // If this node was grey (false), a cycle was detected.
        }
        seen.put(c, false);
        for (Character next : adjList.get(c)) {
            boolean result = dfs(next);
            if (!result) return false;
        }
        seen.put(c, true);
        output.insert(0, c);
        return true;
    }

    // old solution
	public String alienOrderOld(String[] words) {
        String res = "";
        Map<Character, List<Character>> graph = new HashMap<Character, List<Character>>();
        Set<Character> used = new HashSet<Character>();

        String prev = "";
        String cur = "";
        for (int i=1; i<words.length; ++i) {
            int j = 0;
            prev =words[i - 1];
            cur = words[i];
            while (j < prev.length() && j < cur.length()) {
                char pc = prev.charAt(j);
                char cc = cur.charAt(j);
                if (pc != cc) {
                    if (!graph.containsKey(pc)) {
                        List<Character> l = new ArrayList<Character>();
                        l.add(cc);
                        graph.put(pc, l);
                    } else {
                        List<Character> l = graph.get(pc);
                        l.add(cc);
                        graph.put(pc, l);
                    }
                }
                ++j;
            }
        }

        StringBuffer sb = new StringBuffer();
        for (Character c : graph.keySet()) {
            if (!used.contains(c)) {
                Set<Character> loop = new HashSet<Character>();
                if (!topologicalSort(c, loop, sb, graph, used)) {
                    return res;
                }
            }
        }
        for (Character c : graph.keySet()) {
            if (!used.contains(c)) {
                sb.insert(0, c);
            }
        }
        res = sb.toString();
        return res;
    }

    public boolean topologicalSort(Character c, Set<Character> loop, StringBuffer sb
            , Map<Character, List<Character>> graph, Set<Character> used) {
        used.add(c);
        if (loop.contains(c)) {
            return false;
        }
        loop.add(c);
        if (graph.containsKey(c)) {
            for (Character a : graph.get(c)) {
                if(loop.contains(a)) {
                    return false;
                }
                if (!used.contains(a)) {
                    if (!topologicalSort(a, loop, sb, graph, used)) {
                        return false;
                    }
                }
            }
        }
        sb.insert(0, c);
        return true;
    }
}
