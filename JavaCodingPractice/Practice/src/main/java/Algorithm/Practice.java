package Algorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    /*---------------------------------7.1 String multiplication--------------------------*/
    public char[] reverseCharArr(char[] charArr) {
    	int i = 0;
    	while (i < charArr.length / 2) {
    		char tmp = charArr[i];
    		charArr[i] = charArr[charArr.length - 1 - i];
    		charArr[charArr.length - 1 - i] = tmp;
    		++i;
    	}
    	return charArr;
    }

    public char[] StringMultiply(char[] num1, char[] num2) {
    	if (num1.length < num2.length) {
    		return StringMultiply(num2, num1);
    	}
    	num1 = reverseCharArr(num1);
    	num2 = reverseCharArr(num2);

    	char[] result = new char[num1.length + num2.length + 1];
    	for (int k = 0; k < result.length; ++k) {
    		result[k] = '0';
    	}

    	for (int i = 0; i < num2.length; ++i) {
    		if (num2[i] == '0') {
    			continue;
    		}
    		int mul = toInt(num2[i]);
    		int carry = 0;
    		int j = 0;
    		for (; j < num1.length; ++j) {
    			int no1 = toInt(num1[j]);
    			int sum = no1 * mul + carry + toInt(result[i + j]);
    			result[i + j] = toChar(sum % 10);
    			carry = sum / 10;
    		}
    		j = j + i;
        	while (carry > 0) {
        		result[j] = toChar(carry % 10);
        		carry = carry / 10;
        		++j;
        	}
    	}
    	int finalLen = result.length;
    	while (result[finalLen - 1] == '0') {
    		--finalLen;
    	}
    	char[] finalResult = new char[finalLen];
    	for (int i = 0; i < finalLen; ++i) {
    		finalResult[i] = result[i];
    	}
    	return reverseCharArr(finalResult);
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

    /*-----------------------------11. string to integer----------------------------------
     * 1. The function first discards as many whitespace characters as necessary until the first
     * non-whitespace character is found. Then, starting from this character, takes an optional
     * initial plus or minus sign followed by as many numerical digits as possible, and interprets
     * them as a numerical value.
     * 2. The string can contain additional characters after those that form the integral number,
     * which are ignored and have no effect on the behavior of this function.
     * 3. If the first sequence of non-whitespace characters in str is not a valid integral number,
     * or if no such sequence exists because either str is empty or it contains only whitespace
     * characters, no conversion is performed.
     * 4. If no valid conversion could be performed, a zero value is returned. If the correct value
     * is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is
     * returned.*/
    public int stringToInt(String input) {
    	input = input.trim();
    	boolean negative = false;
    	int pivot = 0;
    	if (input.charAt(0) == '-') {
    		negative = true;
    		pivot = 1;
    	} else if (input.charAt(0) == '+') {
    		pivot = 1;
    	}
    	long result = 0;
    	for (; pivot < input.length(); ++pivot) {
    		if (input.charAt(pivot) < '0' || input.charAt(pivot) > '9') {
    			if (result > 0) {
    				return (int) result;
    			}
    			throw new NumberFormatException("Invalid number: " + input.charAt(pivot));
    		}
    		result *= 10;
    		result += (input.charAt(pivot) - '0');
    		if (result > Integer.MAX_VALUE) {
    			throw new NumberFormatException("Out of bound of integer");
    		}
    	}
    	if (negative) {
    		result *= -1;
    	}
    	return (int) result;
    }

    /*-------------------------------12. validate string is numeric using finite automata-------------------------------*/
    public enum INPUT {
    	SPACE(0), DIGIT(1), SIGN(2), E(3), DOT(4), OTHERS(5);
    	private int value;
    	private INPUT(int val) {
    		value = val;
    	}
    	public int getVal() {
    		return value;
    	}
    }
    public boolean validateNumeric(String input) {
    	input = input.trim();
    	ArrayList<ArrayList<Integer>> status = new ArrayList<ArrayList<Integer>>();
    	for (int i = 0; i < 6; ++i) {
    		status.add(new ArrayList<Integer>(Arrays.asList(-1, -1, -1, -1 , -1, -1)));
    	}

    	status.get(0).set(INPUT.SPACE.getVal(), 0);
    	status.get(0).set(INPUT.DIGIT.getVal(), 1);
    	status.get(0).set(INPUT.DOT.getVal(), 1);
    	status.get(0).set(INPUT.OTHERS.getVal(), -1);
    	status.get(0).set(INPUT.SIGN.getVal(), 1);
    	status.get(0).set(INPUT.E.getVal(), 2);

    	status.get(1).set(INPUT.SPACE.getVal(), 3);
    	status.get(1).set(INPUT.DIGIT.getVal(), 1);
    	status.get(1).set(INPUT.DOT.getVal(), 2);
    	status.get(1).set(INPUT.OTHERS.getVal(), -1);
    	status.get(1).set(INPUT.SIGN.getVal(), -1);
    	status.get(1).set(INPUT.E.getVal(), 2);

    	status.get(2).set(INPUT.SPACE.getVal(), 3);
    	status.get(2).set(INPUT.DIGIT.getVal(), 2);
    	status.get(2).set(INPUT.DOT.getVal(), -1);
    	status.get(2).set(INPUT.E.getVal(), -1);
    	status.get(2).set(INPUT.OTHERS.getVal(), -1);
    	status.get(2).set(INPUT.SIGN.getVal(), -1);

    	status.get(3).set(INPUT.SPACE.getVal(), 3);
    	status.get(3).set(INPUT.DIGIT.getVal(), -1);
    	status.get(3).set(INPUT.DOT.getVal(), -1);
    	status.get(3).set(INPUT.E.getVal(), -1);
    	status.get(3).set(INPUT.OTHERS.getVal(), -1);
    	status.get(3).set(INPUT.SIGN.getVal(), -1);

    	int stage = 0;
    	for (char ch : input.toCharArray()) {
    		INPUT cur = checkInput(ch);
    		if (status.get(stage).get(cur.getVal()) == -1) {
    			return false;
    		} else {
    			stage = status.get(stage).get(cur.getVal());
    		}
    	}
    	return true;
    }
    public INPUT checkInput(char ch) {
    	if (ch >= '0' && ch <= '9') return INPUT.DIGIT;
    	if (ch ==' ') return INPUT.SPACE;
    	if (ch == '+' || ch == '-') return INPUT.SIGN;
    	if (ch == 'e' || ch == 'E') return INPUT.E;
    	if (ch == '.') return INPUT.DOT;
    	return INPUT.OTHERS;
    }

    /*--------------------------13. Merge Sorted Array, assume one array have enough space------------------------*/
    public int[] mergeSortedArray(int a[], int b[]) {
    	int k = a.length - 1 - b.length, j = b.length - 1, i = a.length - 1;
    	while (i >= 0 && j >= 0) {
    		if (a[i] < b[j]) {
    			a[k] = b[j];
    			--j;
    		} else {
    			a[k] = a[i];
    			--i;
    		}
    		--k;
    	}
    	while (j >= 0) {
    		a[k] = a[j];
    		--j; --k;
    	}
    	return a;
    }

    /*-------------------------14. Plus one for int array------------------------------*/
    public ArrayList<Integer> plusOneForArray(ArrayList<Integer> origin) {
    	if (origin == null || origin.size() == 0) {
    		return null;
    	}
    	int carry = 1;
    	for (int len = origin.size() - 1; len >= 0; --len) {
    		int sum = origin.get(len) + carry;
    		origin.set(len, sum % 10);
    		carry = sum / 10;
    	}
    	if (carry > 1) {
    		origin.add(0, carry);
    	}
    	return origin;
    }

    /*-------------------------15. Remove Duplicates of sorted array in place----------------------------*/
    public int[] removeDuplicate(int in[]) {
    	if (in.length == 0)
    		return null;
    	int runner = 1;
    	int lastElm = 0;
    	while (runner < in.length) {
    		if (in[runner] != in[lastElm]) {
    			++lastElm;
    			in[lastElm] = in[runner];
    			++runner;
    		} else {
    			++runner;
    		}
    	}
    	return Arrays.copyOf(in, lastElm + 1);
    }

    /*-------------------------16. Remove all values equal to given value-----------------------*/
    public int removeSpecValue(int in[], int val) {
    	if (in.length == 0) {
    		return 0;
    	}
    	int runner = 0;
    	int lastInd = 0;
    	while (runner < in.length) {
    		if (in[runner] != val) {
    			in[lastInd] = in[runner];
    			++lastInd;
    			++runner;
    		} else {
    			++runner;
    		}
    	}
    	return lastInd;
    }

    /*17. Given a sorted array of integers, find the starting and ending position of a given target value.
     * For example,
	 * Given [5, 7, 7, 8, 8, 10] and target value 8,
	 * return [3, 4].
     */
    public class Pair {
    	public int elm1;
    	public int elm2;
    }

    public Pair getRangeForSpec(int in[], int val) {
    	Pair p = new Pair();
    	p.elm1 = -1;
    	p.elm2 = -1;

    	int low = 0;
    	int high = in.length - 1;
    	while (low < high) {
    		int mid = (high + low) / 2;
    		if (in[mid] < val) {
    			low = mid + 1;
    		} else {
    			high = mid;
    		}
    	}
    	if (in[low] != val) {
    		return p;
    	}

    	high = in.length;
    	p.elm1 = low;

    	while(low < high) {
    		int mid = (high + low) / 2;
    		if (in[mid] > val) {
    			high = mid;
    		} else {
    			low = mid + 1;
    		}
    	}
    	p.elm2 = high - 1;
    	return p;
    }

    /*----------------18. Permutation of phone numbers-----------------*/
    /*Given a digit string, return all possible letter combinations that the number could represent.*/
    public void printNumberStringPermutation() {
    	Map<Character, String> nMap = new HashMap<Character, String>();
    	nMap.put('2', "abc");
    	nMap.put('3', "def");
    	nMap.put('4', "ghi");
    	nMap.put('5', "jkl");
    	nMap.put('6', "mno");
    	numberStringPermuteHelper(nMap, 0, 5, new StringBuilder());
    }

    public void numberStringPermuteHelper(Map<Character, String> nMap, int startNum, int totalLen, StringBuilder sbuilder) {
    	if (startNum == totalLen) {
    		System.out.println(sbuilder.toString());
    		return;
    	}
    	String str = nMap.get(startNum + 2 + '0');
    	for(int i = 0; i < 3; ++i) {
    		sbuilder.append(str.charAt(i));
    		numberStringPermuteHelper(nMap, startNum + 1, totalLen, sbuilder);
    		sbuilder.setLength(sbuilder.length() - 1);
    	}
    }

    /*----------19. reverse integer-----------*/
    public int reverseInt(int a) {
    	boolean neg = false;
    	if (a < 0) {
    		a = 0 - a;
    		neg = true;
    	}
    	int b = 0;
    	while(a > 0) {
    		b *= 10;
    		b += a % 10;
    		a = a / 10;
    	}
    	if (neg)
    		return 0 - b;
    	return b;
    }
}