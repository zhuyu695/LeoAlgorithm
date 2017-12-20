package Algorithm;

import java.util.ArrayList;
import java.util.List;

public class TopologicalSort {
	public List<Integer> topologicalSort(int[][] graph) {
        boolean[] visited = new boolean[graph.length];
        List<Integer> res = new ArrayList<Integer>();
        for (int i=0; i<graph.length; ++i) {
            if (!visited[i]) {
                traverse(graph, i, visited, res);
            }
        }
        return res;
    }

    public void traverse(int[][] graph, int v, boolean[] visited, List<Integer> res) {
        visited[v] = true;
        for (int i=0; i<graph[v].length; ++i) {
            if (graph[v][i]==1 && !visited[i]) {
                traverse(graph, i, visited, res);
            }
        }
        res.add(0, v);
    }
	
//	public void topologicalSort(int[][] graph) {
//        Stack<Integer> stack = new Stack<Integer>();
//        boolean[] visited = new boolean[graph.length];
//
//        for (int i=0; i<graph.length; ++i) {
//            if (!visited[i]) {
//                topologicalSortUtil(i, graph, visited, stack);
//            }
//        }
//
//        while(!stack.isEmpty()) {
//            System.out.println(stack.pop());
//        }
//    }
//
//    public void topologicalSortUtil(int i, int[][] graph, boolean[] visited, Stack<Integer> stack) {
//        visited[i] = true;
//        int[] neighbours = graph[i];
//        for (int j=0; j<neighbours.length; ++j) {
//            if (neighbours[j] == 1) {
//                if (!visited[j]) {
//                    topologicalSortUtil(j, graph, visited, stack);
//                }
//            }
//        }
//        stack.add(i);
//    }
//    
    public void strongComponent(int[][] graph) {
        List<Integer> cur = new ArrayList<Integer>();
        boolean[] visited = new boolean[graph.length];
        List<List<Integer>> strongComponent = new ArrayList<List<Integer>>();

        for (int i=0; i<graph.length; ++i) {
            if (!visited[i]) {
                strongComponentUtil(i, graph, visited, cur, strongComponent);
            }
        }

        for (List<Integer> l : strongComponent) {
            System.out.println(l.toString());
        }
    }

    public void strongComponentUtil(int i, int[][] graph, boolean[] visited, List<Integer> cur, List<List<Integer>> strongComponent) {
        visited[i] = true;
        cur.add(i);
        int[] neighbours = graph[i];
        for (int j=0; j<neighbours.length; ++j) {
            if (neighbours[j] == 1) {
                if (!visited[j]) {
                    strongComponentUtil(j, graph, visited, cur, strongComponent);
                } else {
                    strongComponent.add(new ArrayList<Integer>(cur));
                    cur.clear();
                    return;
                }
            }
        }
        if (cur.size() > 0) {
            strongComponent.add(new ArrayList<Integer>(cur));
            cur.clear();
        }
    }
}
