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
	public String alienOrder(String[] words) {
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
