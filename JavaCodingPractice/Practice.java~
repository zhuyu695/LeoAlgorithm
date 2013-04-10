import java.util.ArrayList;

public class Practice {
    
    /*Print permutation of a string*/
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


    public void main() {
	String test = "abcdef";
        ArrayList<String> myList = getPermuation(test);
        System.out.println(myList);
    }
}