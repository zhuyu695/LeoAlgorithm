package Algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CountComponent {
	public int countComponents(int n, int[][] edges) {
        Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
        for (int i=0; i<edges.length; ++i) {
            if (graph.containsKey(edges[i][0])) {
                graph.get(edges[i][0]).add(edges[i][1]);
            } else {
                Set<Integer> neighbours = new HashSet<Integer>();
                neighbours.add(edges[i][1]);
                graph.put(edges[i][0], neighbours);
            }
        }

        boolean[] visited = new boolean[n];
        int count = 0;
        for (Map.Entry<Integer, Set<Integer>> entry: graph.entrySet()) {
            if (visited[entry.getKey()] == false) {
                count++;
                dfs(entry.getKey(), graph, visited);
            }
        }
        for (int i=0; i<n; ++i) {
            if (!visited[i]) {
                ++count;
            }
        }
        return count;
    }

    public void dfs(int vertex, Map<Integer, Set<Integer>> graph, boolean[] visited) {
        visited[vertex] = true;
        if (graph.containsKey(vertex)) {
            Set<Integer> neighbour = graph.get(vertex);
            for (int nb : neighbour) {
                if (!visited[nb]) {
                    dfs(nb, graph, visited);
                }
            }
        }
    }
}
