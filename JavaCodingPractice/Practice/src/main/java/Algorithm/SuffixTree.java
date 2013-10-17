package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuffixTree {
	public class SuffixTreeNode {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		Map<Character, SuffixTreeNode> charMap = new HashMap<Character, SuffixTreeNode>();
		public void insertString(String input, int index) {
			indexes.add(index);
			if (input == null || input.length() == 0) {
				return;
			}
			char first = input.charAt(0);
			SuffixTreeNode tmp = null;
			if (charMap.containsKey(first)) {
				tmp = charMap.get(first);
			} else {
				tmp = new SuffixTreeNode();
				charMap.put(first, tmp);
			}
			tmp.insertString(input.substring(1), index);
		}

		public ArrayList<Integer> search(String input) {
			if (input == null || input.length() == 0)
				return indexes;
			char first = input.charAt(0);
			if (charMap.containsKey(first)) {
				return charMap.get(first).search(input.substring(1));
			}
			return null;
		}
	}

	private SuffixTreeNode root = new SuffixTreeNode();

	public SuffixTree(String str) {
		for (int i = 0; i < str.length(); ++i) {
			String suffix = str.substring(i);
			root.insertString(suffix, i);
		}
	}

	public ArrayList<Integer> search(String str) {
		return root.search(str);
	}
}
