package Algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StrongConnectedComponent {
	public List<List<Integer>> scc(int[][] graph) {
        boolean[] visited = new boolean[graph.length];
        int[] lowLink = new int[graph.length];
        Stack<Integer> stack = new Stack<Integer>();
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        for (int i=0; i<graph.length; ++i) {
            if (!visited[i]) {
                dfs(i, graph, lowLink, 0, visited, stack, res);
            }
        }
        return res;
    }

    public void dfs(int u, int[][] graph, int[] low, int time, boolean[] visited, Stack<Integer> stack, List<List<Integer>> res) {
        low[u] = time;
        time++;
        visited[u] = true;
        stack.add(u);
        boolean isComponentRoot = true;

        for (int i=0; i<graph[u].length; ++i) {
            if (graph[u][i] == 1) {
                if (!visited[i]) {
                    dfs(i, graph, low, time, visited, stack, res);
                }
                if (low[u] > low[i]) {
                    low[u] = low[i];
                    isComponentRoot = false;
                }
            }
        }

        if (isComponentRoot) {
            List<Integer> component = new ArrayList<Integer>();
            while(true) {
                int x = stack.pop();
                component.add(x);
                low[x] = Integer.MAX_VALUE;
                if (x == u) {
                    break;
                }
            }
            res.add(component);
        }
    }
}
