package Algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LeetCodeBreadthFirstSearch {
//286. Walls and Gates
//You are given a m x n 2D grid initialized with these three possible values.
//
//-1 - A wall or an obstacle.
//0 - A gate.
//INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
//Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
//
//For example, given the 2D grid:
//INF  -1  0  INF
//INF INF INF  -1
//INF  -1 INF  -1
//  0  -1 INF INF
//After running your function, the 2D grid should be:
//  3  -1   0   1
//  2   2   1  -1
//  1  -1   2  -1
//  0  -1   3   4
public class Solution286 {
    public void wallsAndGates(int[][] rooms) {
        if(rooms.length == 0) return;
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[0].length; j++){
                // 如果遇到一个门，从门开始广度优先搜索，标记连通的节点到自己的距离
                if(rooms[i][j] == 0) bfs(rooms, i, j);
            }
        }
    }
    
    public void bfs(int[][] rooms, int i, int j){
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(i * rooms[0].length + j);
        int dist = 0;
        // 用一个集合记录已经访问过的点
        Set<Integer> visited = new HashSet<Integer>();
        visited.add(i * rooms[0].length + j);
        while(!queue.isEmpty()){
            int size = queue.size();
            // 记录深度的搜索
            for(int k = 0; k < size; k++){
                Integer curr = queue.poll();
                int row = curr / rooms[0].length;
                int col = curr % rooms[0].length;
                // 选取之前标记的值和当前的距离的较小值
                rooms[row][col] = Math.min(rooms[row][col], dist);
                int up = (row - 1) * rooms[0].length + col;
                int down = (row + 1) * rooms[0].length + col;
                int left = row * rooms[0].length + col - 1;
                int right = row * rooms[0].length + col + 1;
                if(row > 0 && rooms[row - 1][col] > 0 && !visited.contains(up)){
                    queue.offer(up);
                    visited.add(up);
                }
                if(col > 0 && rooms[row][col - 1] > 0 && !visited.contains(left)){
                    queue.offer(left);
                    visited.add(left);
                }
                if(row < rooms.length - 1 && rooms[row + 1][col] > 0 && !visited.contains(down)){
                    queue.offer(down);
                    visited.add(down);
                }
                if(col < rooms[0].length - 1 && rooms[row][col + 1] > 0 && !visited.contains(right)){
                    queue.offer(right);
                    visited.add(right);
                }
            }
            dist++;
        }
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

//317. Shortest Distance from All Buildings
//You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:
//
//Each 0 marks an empty land which you can pass by freely.
//Each 1 marks a building which you cannot pass through.
//Each 2 marks an obstacle which you cannot pass through.
//For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):
//
//1 - 0 - 2 - 0 - 1
//|   |   |   |   |
//0 - 0 - 0 - 0 - 0
//|   |   |   |   |
//0 - 0 - 1 - 0 - 0
//The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.
//
//Note:
//There will be at least one building. If it is not possible to build such house according to the above rules, return -1.
//
//Show Company Tags
//Show Tags
//Show Similar Problems
public class Solution317 {
    public int shortestDistance(int[][] grid) {
        int rows = grid.length;
        if (rows == 0) {
            return -1;
        }
        int cols = grid[0].length;
 
        // 记录到各个building距离和
        int[][] dist = new int[rows][cols];
        
        // 记录到能到达的building的数量
        int[][] nums = new int[rows][cols];            
        int buildingNum = 0;
        
        // 从每个building开始BFS
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    buildingNum++;
                    bfs(grid, i, j, dist, nums);
                }
            }
        }
        
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0 && dist[i][j] != 0 && nums[i][j] == buildingNum)
                    min = Math.min(min, dist[i][j]);
            }
        }
        if (min < Integer.MAX_VALUE)
            return min;
        return -1;
    }
    
    public void bfs(int[][] grid, int r, int c, int[][] dist, int[][] nums) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(r*grid[0].length + c);
        
        // 记录访问过的点
        boolean[][] visited = new boolean[rows][cols];
        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int curNum = q.poll();
                
                int row = curNum / grid[0].length;
                int col = curNum % grid[0].length;
                dist[row][col] += level;
                nums[row][col]++;
                
                if (row>0 && !visited[row-1][col] && grid[row-1][col] == 0) {
                    q.add((row-1) * grid[0].length + col);
                    visited[row-1][col] = true;
                }
                if (row < grid.length-1 && !visited[row+1][col] && grid[row+1][col] == 0) {
                    q.add((row+1) * grid[0].length + col);
                    visited[row+1][col] = true;
                }
                if (col>0 && !visited[row][col-1] && grid[row][col-1] == 0) {
                    q.add(row*grid[0].length + col - 1);
                    visited[row][col-1] = true;
                }
                if (col< grid[0].length-1 && !visited[row][col+1] && grid[row][col+1] == 0) {
                    q.add(row*grid[0].length + col + 1);
                    visited[row][col+1] = true;
                }
            }
            level++;
        }
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
public class Solution {
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
