package Algorithm;

import java.util.List;

public class LeetCodeDepthFirstSearch {
//339. Nested List Weight Sum
//Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
//
//		Each element is either an integer, or a list -- whose elements may also be integers or other lists.
//
//		Example 1:
//		Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)
//
//		Example 2:
//		Given the list [1,[4,[6]]], return 27. (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)
	
// This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
public interface NestedInteger {
 
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();
 
    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();
 
    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return null if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
}
	  
public class Solution339 {
    public int depthSum(List<NestedInteger> nestedList) {
        int sum = 0;
        for (NestedInteger ni : nestedList) {
            sum += helper(ni, 1);
        }
        return sum;
    }
    
    public int helper(NestedInteger in, int level) {
        if (in.isInteger()) {
            return in.getInteger() * level;
        }
        List<NestedInteger> subList = in.getList();
        int sum = 0;
        for (NestedInteger n : subList) {
            sum += helper(n, level+1);
        }
        return sum;
    }
}

//261. Graph Valid Tree
//Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.
//
//For example:
//
//Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
//
//Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
//
//Hint:
//
//Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], what should your return? Is this case a valid tree?
//According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
//Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
public class Solution261 {
    public boolean validTree(int n, int[][] edges) {
        int[] root = new int[n];
        for(int i = 0; i < n; i++)
            root[i] = i;
        for(int i = 0; i < edges.length; i++) {
            int root1 = find(root, edges[i][0]);
            int root2 = find(root, edges[i][1]);
            if(root1 == root2)
                return false;
            root[root2] = root1;
        }
        return edges.length == n - 1;
    }

    private int find(int[] root, int e) {
        if(root[e] == e)
            return e;
        else
            return find(root, root[e]);
    }
}

//323. Number of Connected Components in an Undirected Graph
//Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.
//
//Example 1:
//     0          3
//     |          |
//     1 --- 2    4
//Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
//
//Example 2:
//     0           4
//     |           |
//     1 --- 2 --- 3
//Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
//
//Note:
//You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
public class Solution323 {
    public int countComponents(int n, int[][] edges) {
       int[] id = new int[n];
       
       // 初始化
       for (int i = 0; i < n; i++) {
           id[i] = i;
       }
       
       // union
       for (int[] edge : edges) {              
           int i = root(id, edge[0]);
           int j = root(id, edge[1]);
           id[i] = j;
       }
       
       // 统计根节点个数
       int count = 0;
       for (int i = 0; i < n; i++) {
           if (id[i] == i)
               count++;
       }
       return count;
   }
   
   // 找根节点
   public int root(int[] id, int i) {
       while (i != id[i]) {
           id[i] = id[id[i]];
           i = id[i];
       }
       return i;
   }
}
}
