package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * given numbers as preferred location for each person:
 * Ben: {3, 1, 4, 9, 6}
 * Laura: {3, 2, 1, 4}
 * Jean: {1, 5, 4, 7, 9}
 * return final list for destinations or null:
 * {3, 2, 1, 5, 4, 7, 9, 6}
 * It's a graph problem.
 */
public class AirbnbFindNumberOrder {
    public List<Integer> getOrder(int[][] input) {
        List<Integer> res = new ArrayList<Integer>();
        Map<Integer, Set<Integer>> adj = new HashMap<Integer, Set<Integer>>();
        Map<Integer, Integer> visited = new HashMap<Integer, Integer>();

        for (int[] personList : input) {
            for (int i=0; i<personList.length-1; ++i) {
                int cur = personList[i];
                if (adj.containsKey(cur)) {
                    adj.get(cur).add(personList[i+1]);
                } else {
                    Set<Integer> tmpList = new HashSet<Integer>();
                    tmpList.add(personList[i+1]);
                    adj.put(cur, tmpList);
                }
            }
        }

        for (int i : adj.keySet()) {
            if (!visited.containsKey(i) || visited.get(i) == 0) {
                if (isCycle(i, adj, visited, res)) {
                    System.out.println("No solution");
                    return res;
                }
            }
        }
        return res;
    }

    public boolean isCycle(int v, Map<Integer, Set<Integer>> adj, Map<Integer, Integer> visited, List<Integer> res) {
        visited.put(v, 1);
        if (adj.containsKey(v)) {
            for (int neighbour : adj.get(v)) {
                if (visited.containsKey(neighbour)) {
                    if ((visited.get(neighbour) == 1)
                            || (visited.get(neighbour) == 0 && isCycle(neighbour, adj, visited, res))) {
                        res.clear();
                        return true;
                    }
                } else {
                    if (isCycle(neighbour, adj, visited, res)) {
                        res.clear();
                        return true;
                    }
                }
            }
        }
        visited.put(v, 2);
        res.add(0, v);
        return false;
    }

    public static void main(String[] args) {
    	AirbnbFindNumberOrder it = new AirbnbFindNumberOrder();
        int[][] inputs = {{3 ,10, 5}, {2, 10, 5, 6}};

        List<Integer> res = it.getOrder(inputs);
        System.out.println(res.toString());
    }
}
