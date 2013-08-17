package Algorithm;

public class TreeAlg {
	public class BinaryTreeNode {
		public int value;
		public BinaryTreeNode left;
		public BinaryTreeNode right;
	}
	
	/*------------------------check if it is binary search tree------------------------*/
	static int lastVisit = 0;
	public boolean checkBST(BinaryTreeNode root) {
		if (root == null) {
			return true;
		}
		if (!checkBST(root.left)) return false;
		lastVisit = root.value;
		if(!checkBST(root.right)) return false;
		return true;
	}
	
	public boolean checkBST(BinaryTreeNode root, int min, int max) {
		if (root == null) {
			return true;
		}
		if (root.value < min || root.value > max) {
			return false;
		}
		if (!checkBST(root.left, min, root.value) || !checkBST(root.right, root.value, max)) {
			return false;
		}
		return true;
	}
}
