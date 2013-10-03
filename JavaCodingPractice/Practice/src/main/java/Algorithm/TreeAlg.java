package Algorithm;

import java.util.ArrayList;
import java.util.Stack;

public class TreeAlg {
	public class BinaryTreeNode {
		public int value;
		public BinaryTreeNode left;
		public BinaryTreeNode right;
	}

	/*------------------------1. check if it is binary search tree------------------------*/
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

	/*-------------------------2. convert sorted array to binary tree-------------------------*/
	public BinaryTreeNode convertToBST(int[] sortedArr, int start, int end) {
		if (start < 0 || start >= end || end > sortedArr.length)
			return null;
		int mid = (end + start) / 2;
		BinaryTreeNode root = new BinaryTreeNode();
		root.value = sortedArr[mid];
		root.left = convertToBST(sortedArr, start, mid - 1);
		root.right = convertToBST(sortedArr, mid + 1, end);
		return root;
	}

	/*--------------3. Two elements of a binary search tree (BST) are swapped by mistake.-----*/
    /*------------------Recover the tree without changing its structure.----------------------*/
	public void recoverBinaryTree(BinaryTreeNode root) {
		BinaryTreeNode node1 = inorderCheck(root);
		BinaryTreeNode node2 = inorderCheck(node1);
		if (node1 != null && node2 != null) {
			int val = node1.value;
			node1.value = node2.value;
			node2.value = val;
		}
	}

	int lastVisited = 0;

	public BinaryTreeNode inorderCheck(BinaryTreeNode node) {
		if (node == null)
			return null;
		BinaryTreeNode result = inorderCheck(node.left);
		if (result != null)
			return result;
		if (node.value < lastVisited)
			return node;
		else
			lastVisited = node.value;
		result = inorderCheck(node.right);
		if (result != null)
			return result;
		return null;
	}

	/*-----------4. generate all uniq binary search trees-----------*/
	public ArrayList<BinaryTreeNode> generateUniqBST(int start, int end) {
		if (start > end)
			return null;
		ArrayList<BinaryTreeNode> res = new ArrayList<BinaryTreeNode>();
        for (int i = start; i <= end; ++i) {
        	ArrayList<BinaryTreeNode> left = generateUniqBST(start, i - 1);
        	ArrayList<BinaryTreeNode> right = generateUniqBST(i + 1, end);
        	for (int j = 0; j < left.size(); ++j) {
        		for (int k = 0; k < right.size(); ++k) {
        			BinaryTreeNode root = new BinaryTreeNode();
        			root.value = i;
        			root.left = left.get(j);
        			root.right = right.get(k);
        			res.add(root);
        		}
        	}
        }
        return res;
	}

	/*-----------5. Check Balanced Binary Tree------------*/
	public boolean checkBalancedBST(BinaryTreeNode root) {
		if (checkHeight(root) != -1)
			return true;
		return false;
	}

	public int checkHeight(BinaryTreeNode root) {
		if (root == null) return 0;
		int left = checkHeight(root.left);
		int right = checkHeight(root.right);
		if (left == -1 || right == -1) {
			return -1;
		}
		if (Math.abs(left - right) > 1)
			return -1;
		return left > right ? left + 1 : right + 1;
	}

	/*----------6. Inorder Traversal--------------*/
	public ArrayList<BinaryTreeNode> inorderTraversal(BinaryTreeNode root) {
		ArrayList<BinaryTreeNode> list = new ArrayList<BinaryTreeNode>();
		Stack<BinaryTreeNode> st = new Stack<BinaryTreeNode>();
		BinaryTreeNode cur = root;
		boolean done = false;
		while (!done) {
			if (cur != null) {
				st.add(cur);
				cur = cur.left;
			} else if (st.size() == 0) {
				done = true;
			} else {
				BinaryTreeNode tmp = st.pop();
				list.add(tmp);
				cur = tmp.right;
			}
		}
		return list;
	}
}
