package Algorithm;

//Union Find
//Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.
//To check whether a graph is a tree, we should determine:
//whether there is a cycle, if there is cycle, it is not a tree.
//whether the nodes are connected. If it is not connected, it is not tree.
//For example:
//Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
//Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
public class GraphValidTree {
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

    public static void main(String[] args) {
        GraphValidTree vt = new GraphValidTree();
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}};
        System.out.println( vt.validTree(5, edges));

    }
}
