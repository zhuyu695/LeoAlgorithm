import java.util.ArrayList;

public class Practice {
    
    /*----------------1. Print permutation of a string----------------*/
    public ArrayList<String> getPermuation(String str) {
	if (str == null)
	    return null;

	ArrayList<String> permutations = new ArrayList<String> ();
	if (str.length() == 0) {
	    permutations.add("");            // add empty string
	    return permutations;
	}

	char first  = str.charAt(0);
	String remainder = str.substring(1);

	ArrayList<String> words  = getPermuation (remainder);
	for (String word: words) {
	    for (int j =0; j <=word.length();  j++) {
		String s = insertCharAt(word, first, j);
		permutations.add(s);
	    }
	}
	return permutations;
    }
    
    private String insertCharAt (String word, char c, int pos) {
	String start = word.substring (0,pos);
	String end  = word.substring(pos);
	return start + c + end;
    }
    
    /*----------------2. Print Permutation of string----------------*/
    public void permutation(String str) {
	permutation("", str);
    }

    private void permutation(String prefix, String str) {
	int n = str.length();
	if (n == 0) System.out.println(prefix);
	else {
	    for (int i = 0; i < n; i++) {
		permutation(prefix + str.charAt(i), str.substring(0,i) + str.substring(i+1, n));
	    }
	}
    }

    /*----------------3. Find binary tree is sub tree of another---------------*/
    private class Node {
	private String mValue;
	private Node leftNode;
	private Node rightNode;
	public String value() {
	    return mValue;
	}
	public Node getLeft() {
	    return leftNode;
	}
	public Node getRight() {
	    return rightNode;
	}
    }

    public boolean isSubTree(Node nodeBig, Node nodeSmall) {
	if (nodeBig == null && nodeSmall == null) {
	    return true;
	} else if (nodeBig == null) {
	    return false;
	} else if (nodeSmall == null) {
	    return true;
	} else if (nodeBig.value().equals(nodeSmall.value())){ 
	    return treeMatch(nodeBig.getLeft(), nodeSmall) || treeMatch(nodeBig.getRight(), nodeSmall);
	} else {
	    return false;
	}
	
    }

    public boolean treeMatch(Node nodeBig, Node nodeSmall) {
	if (nodeSmall == null) {
	    return true;
	} else if (nodeSmall.value().equals(nodeSmall.value())) {
	    return treeMatch(nodeBig.getLeft(), nodeSmall.getLeft()) && treeMatch(nodeBig.getRight(), nodeSmall.getRight());
	} else {
	    return false;
	}
    }

    /*--------------------------4. Knapsack Problem--------------------------*/
    /*items(weight, price): (2,3) (3,4) (4,5) (5,6) (7,8) (8,9)*/    /*max capacity is 10, total items is 6*/
    public void processKnapsack() {
	int[] Weight = {0, 2, 3, 4, 5, 7, 8}; // 7elements
	int[] Price = {0, 3, 4, 5, 6, 8, 9};
	int DPMatrix[][] = new int[7][11];
	generateMatrix(DPMatrix, Weight, Price);
	ArrayList result = getItem(DPMatrix, Weight);
    }
    
    private void generateMatrix(int[][] matrix, int[] W, int[] P) {
	for (int i = 0; i < 9; ++i) {
	    matrix[0][i] = 0;
	}
	for (int i = 0; i < 7; ++i) {
	    matrix[i][0] = 0;
	}
	for (int i = 0; i < 7; ++i) {
	    //largest weight is 10, start with 0
	    for (int j = 0; j< 11; ++j) {
		if (W[i] > j) {
		    matrix[i][j] = matrix[i-1][j];
		}
		else if (matrix[i-1][j-W[i]] + P[i] > matrix[i-1][j]) {
		    matrix[i][j] = matrix[i-1][j-W[i]] + P[i];
		} else {
		    matrix[i][j] = matrix[i-1][j];
		}
	    }
	}
    }

    private ArrayList getItem(int[][] matrix, int[] W) {
	int j = 8, i = 6;
	ArrayList<Integer> resultList = new ArrayList();
	while(i > 0) {
	    if (matrix[i][j] != matrix[i-1][j]) {
	        resultList.add(i);
	        j = j - W[i];
	    }
	    i = i - 1;
	}
	return resultList;
    }

    public void main() {
	String test = "abcdef";
        ArrayList<String> myList = getPermuation(test);
        System.out.println(myList);
    }

    /*------------------------------------4. coin problem------------------------------------------*/
    /*--------Recursive----------*/
    /*-------To count total number solutions, we can divide all set solutions in two sets.---------*/
    /*-------1) Solutions that do not contain mth coin (or Sm).------------------------------------*/
    /*-------2) Solutions that contain at least one Sm.--------------------------------------------*/
    / Returns the count of ways we can sum  S[0...m-1] coins to get sum n
    int count( int S[], int m, int n )
    {
	    // If n is 0 then there is 1 solution (do not include any coin)
	    if (n == 0)
		return 1;
     
	    // If n is less than 0 then no solution exists
	    if (n < 0)
		return 0;
 
	    // If there are no coins and n is greater than 0, then no solution exist
	    if (m <=0 && n >= 1)
		return 0;
 
	    // count is sum of solutions (i) excluding S[m-1] (ii) including S[m-1]
	    return count( S, m - 1, n ) + count( S, m, n-S[m-1] );
     }
}