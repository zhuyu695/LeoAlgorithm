package Algorithm;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

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
    public ArrayList<Integer> processKnapsack() {
		int[] Weight = {0, 2, 3, 4, 5, 7, 8}; // 7elements
		int[] Price = {0, 3, 4, 5, 6, 8, 9};
		int DPMatrix[][] = new int[7][11];
		generateMatrix(DPMatrix, Weight, Price);
		ArrayList<Integer> result = getItem(DPMatrix, Weight);
		return result;
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

    private ArrayList<Integer> getItem(int[][] matrix, int[] W) {
	int j = 8, i = 6;
	ArrayList<Integer> resultList = new ArrayList<Integer>();
	while(i > 0) {
	    if (matrix[i][j] != matrix[i-1][j]) {
	        resultList.add(i);
	        j = j - W[i];
	    }
	    i = i - 1;
	}
	return resultList;
    }

    /*------------------------------------5. coin problem------------------------------------------*/
    /*--------Recursive----------*/
    /*-------To count total number solutions, we can divide all set solutions in two sets.---------*/
    /*-------1) Solutions that do not contain mth coin (or Sm).------------------------------------*/
    /*-------2) Solutions that contain at least one Sm.--------------------------------------------*/
    // Returns the count of ways we can sum  S[0...m-1] coins to get sum n
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
    
    /*----------------------------------6. evaluate string------------------------------------------*/
    /*----------------String could be (3+1)+(1+0)*2-------------------------------------------------*/
    /*----------------Doesn't work for 2 or more digit number---------------------------------------*/
    public char toChar(int val) {
    	return (char) (val + '0');
    }
    
    public int toInt(char val) {
    	return (int) (val - '0');
    }
    
    public int evaluateExpression(char[] src) {
    	Stack<Character> numStack = new Stack<Character>();
    	for (int i = 0; i < src.length; ++i) {
    		if (src[i] == ')') {
    			int tmpNum = getStackValue(numStack);
    			char test = toChar(tmpNum);
    			numStack.add(test);
    		}
    		else {
    			numStack.add(src[i]);
    		}
    	}
    	return getStackValue(numStack);
    }
    
    public int getStackValue(Stack<Character> st) {
    	Stack<Integer> fst = new Stack<Integer>();
    	int prevRand = 0;
    	while (st.size() > 0) {
    		char curChar = st.pop();
    		if (curChar != '(') {
	    		if (curChar == '*') {
	    			fst.pop();
	    			fst.add(prevRand * toInt(st.pop()));
	    		} else {
	    			if (curChar != '+') {
	    				prevRand = toInt(curChar);
		    			fst.add(prevRand);
	    			}
	    		}
    		} else {
    			break;
    		}
    	}
    	int sum = 0;
    	while (fst.size() > 0) {
    		sum += fst.pop();
    	}
    	return sum;
    }
    
    /*---------------------------------7. Big Number Multiplication----------------------------*/
    public char[] BigIntMultiply(char[] num1, char[] num2) {
    	if (num1.length < num2.length) {
    		return BigIntMultiply(num2, num1);
    	}
    	ArrayList<char[]> resultArray = new ArrayList<char[]>();
    	int trailingZero = 0;
    	for (int i = num2.length - 1; i >= 0 ; --i, ++trailingZero) {
    		String tmpResult = getMultiplySingleDigit(num1, num2[i]);
    		int curTrainlingZero = trailingZero;
    		while (curTrainlingZero-- > 0) {
    			tmpResult += '0';
    		}
    		resultArray.add(tmpResult.toCharArray());
    	}
    	char[] result = resultArray.get(0);
    	for (int i = 1; i < resultArray.size(); ++i) {
    		result  = BigIntAdd(result, resultArray.get(i));
    	}
    	return result;
    }
    
    public String getMultiplySingleDigit(char[] num1, char mulChar) {
    	String result = new String();
    	int mul = toInt(mulChar);
    	int carry = 0;
    	int cur = 0;
    	int val = 0;
    	for (int i = num1.length - 1; i >= 0; --i) {
    		cur = toInt(num1[i]);
    		int sum = cur * mul + carry;
    		val = sum % 10;
    		carry = sum / 10;
    		result = toChar(val) + result;
    	}
    	if (carry > 0) {
    		result = toChar(carry) + result;
    	}
    	return result;
    }
    
    public char[] BigIntAdd(char[] num1, char[] num2) {
    	if (num1.length < num2.length) {
    		return BigIntAdd(num2, num1);
    	}
    	int minlength = num2.length - 1;
    	int maxlength = num1.length - 1;
    	String result = new String();
    	int sum = 0;
    	int carry = 0;
    	while (minlength >= 0) {
    		int curNum1 = toInt(num1[maxlength]);
    		int curNum2 = toInt(num2[minlength]);
    		sum = curNum1 + curNum2 + carry;
    		carry = sum / 10;
    		result = toChar(sum % 10) + result;
    		--minlength;
    		--maxlength;
    	}
    	while (maxlength >= 0) {
    		int cur = toInt(num1[maxlength]);
    		sum = cur + carry;
    		carry = sum / 10;
    		result = toChar(sum % 10) + result;
    		--maxlength;
    	}
    	if (carry > 0) {
    		result = toChar(carry) + result;
    	}
    	return result.toCharArray();
    }
    
    /*---------------------------------8. get Sentence----------------------------*/
    /* getSentence("iamastudentfromwaterloo", {"from, "waterloo", "hi", "am", "yes", "i", "a", "student"}) */
    /*-> "i am a student from waterloo"*/    
    public boolean getSentence(String text, Set<String> dictionary, StringBuilder sbuilder) {
    	if (text.length() == 0) {
    		System.out.println(sbuilder.toString());
    		return true;
    	}
    	for (int i = 1; i <= text.length(); ++i) {
    		String tmp = text.substring(0, i);
    		if (dictionary.contains(tmp) && getSentence(text.substring(i), dictionary, sbuilder)) {
    			sbuilder.insert(0, tmp);
    			sbuilder.insert(0, ' ');
    			return true;
    		}
    	}
    	return false;
    }
    
    /*-------------------------------9. magic index, found A[i] = i in sorted array---------------------------*/
    public int foundMagicIndex(int[] arr, int start, int end) {
    	if (start < 0 || start > end || end >= arr.length)
    		return -1;
    	int mid = (end + start) / 2;
    	if (arr[mid] == mid)
    		return mid;
    	int leftInd = Math.min(arr[mid], mid + 1);
    	int left = foundMagicIndex(arr, start, leftInd);
    	if (left != -1) {
    		return left;
    	}
    	int rightInd = Math.max(arr[mid], mid + 1);
    	return foundMagicIndex(arr, rightInd, end);
    }
    
    /*------------------------------10. find element in rotated sorted array-----------------------------------*/
    public int foundElemInRotated(int[] arr, int left, int right, int elem) {
    	if (left < right) {
    		return -1;
    	}
    	int mid = (left + right) / 2;
    	if (arr[mid] == elem) {
    		return mid;
    	}
    	if (arr[left] < arr[mid]) {
    		if (arr[mid] > elem) {
    			return foundElemInRotated(arr, left, mid - 1, elem);
    		} else {
    			return foundElemInRotated(arr, mid + 1, right, elem);
    		}
    	} else if (arr[mid] < arr[left]) {
    		if (elem > arr[left]) {
    			return foundElemInRotated(arr, left, mid - 1, elem);
    		} else {
    			return foundElemInRotated(arr, mid + 1, right, elem);
    		}
    	} else {
    		if (arr[mid] != arr[right]) {
    			return foundElemInRotated(arr, mid + 1, right ,elem);
    		}
    		int result = foundElemInRotated(arr, left, mid - 1, elem);
    		if (result == -1) {
    			result = foundElemInRotated(arr, mid + 1, right ,elem);
    		}
    		return result;
    	}
    }
}