package Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class TreeAlg {
	public class BinaryTreeNode {
		public int value;
		public BinaryTreeNode left;
		public BinaryTreeNode right;
		public BinaryTreeNode next; //sibling nodes
		public BinaryTreeNode(int val) {
			value = val;
		}
		public BinaryTreeNode() {
		}
	}

	/*------------------------1. check if it is binary search tree------------------------*/
	static int lastVisit = 0;
	public boolean checkBST(BinaryTreeNode root) {
		if (root == null) {
			return true;
		}
		if (!checkBST(root.left)) return false;
		if (root.value < lastVisit) return false;
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
		BinaryTreeNode node1 = inorderCheck(root, null);
		BinaryTreeNode node2 = inorderCheck(root, node1);
		if (node1 != null && node2 != null) {
			int val = node1.value;
			node1.value = node2.value;
			node2.value = val;
		}
	}

	int lastVisited = 0;

	public BinaryTreeNode inorderCheck(BinaryTreeNode node, BinaryTreeNode foundNode) {
		if (node == null)
			return null;
		BinaryTreeNode result = inorderCheck(node.left, foundNode);
		if (result != null)
			return result;
		if (node.value < lastVisited && !node.equals(foundNode))
			return node;
		else
			lastVisited = node.value;
		result = inorderCheck(node.right, foundNode);
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

	/*-----------7. Traverse level by level------------*/
	public ArrayList<LinkedList<BinaryTreeNode>> traverseByLevel(BinaryTreeNode root) {
		LinkedList<BinaryTreeNode> cur = new LinkedList<BinaryTreeNode>();
		ArrayList<LinkedList<BinaryTreeNode>> result = new ArrayList<LinkedList<BinaryTreeNode>>();
		if (root != null)
			cur.add(root);
		while (cur.size() > 0) {
			result.add(cur);
			LinkedList<BinaryTreeNode> parent = cur;
			cur = new LinkedList<BinaryTreeNode>();
			for (BinaryTreeNode btn : parent) {
				if (btn.left != null) {
					cur.add(btn.left);
				}
				if (btn.right != null) {
					cur.add(btn.right);
				}
			}
		}
		return result;
	}

	/*-------------8. find max sum path in binary tree------------*/
	int maxVal = Integer.MIN_VALUE;

	public int findMaxSumPath(BinaryTreeNode root) {
		if (root == null)
			return Integer.MIN_VALUE;
		int left = findMaxSumPath(root.left);
		int right = findMaxSumPath(root.right);
		int cur = root.value;
		int curMax = Math.max(cur, Math.max(cur + left, cur + right));
		maxVal = Math.max(maxVal, Math.max(curMax, cur + left + right));
		return curMax;
	}

	/*------------9. check symmetric tree------------*/
    public boolean isSymmetric(BinaryTreeNode root) {
        if(root==null) return true;
        return isSymmetric(root.left,root.right);
    }

    public boolean isSymmetric(BinaryTreeNode a, BinaryTreeNode b){
        if(a == null) return b == null;
        if(b == null) return false;

        if(a.value != b.value) return false;

        if(!isSymmetric(a.left, b.right)) return false;
        if(!isSymmetric(a.right, b.left)) return false;

        return true;
    }

    /*--------------10. check completed binary tree--------------*/
    public boolean isCompleteBT(BinaryTreeNode root) {
    	if (root == null)
    		return true;
    	Queue<BinaryTreeNode> queue = new LinkedList<BinaryTreeNode>();
    	boolean flag = false;
    	queue.add(root);
    	while (queue.size() > 0) {
    		BinaryTreeNode cur = queue.poll();
    		if (cur.left != null) {
    			if (flag)
    				return false;
    			queue.add(cur.left);
    		} else {
    			flag = true;
    		}
    		if (cur.right != null) {
    			if (flag = true)
    				return false;
    			queue.add(cur.right);
    		}
    		else {
    			flag = true;
    		}
    	}
    	return true;
    }

    /*--------------11. find total sum of all root to leaf numbers----------------*/
    /*An example is one root-to-leaf path 1->2->3 which represents the number 123.*/
    public int sum = 0;
    public void findRootToLeafSum(BinaryTreeNode root, int cur) {
    	if (root == null)
    		return;
    	cur = cur * 10 + root.value;
    	if (root.left == null && root.right == null) {
    		sum += cur;
    		return;
    	}
    	findRootToLeafSum(root.left, cur);
    	findRootToLeafSum(root.right, cur);
    }

    /*12. Perfect Binary Tree: Populating Next Right Pointers in Each Node*/
    public void connectPerfectBinaryTree(BinaryTreeNode root) {
		BinaryTreeNode runner = root;
    	while(runner != null) {
    		BinaryTreeNode across = runner;
    		while(across != null) {
    			if (across.left != null)
    				across.left.next = across.right;
    			if (across.right != null && across.next != null)
    				across.right.next = across.next.left;
    			across = across.next;
    		}
    		runner = runner.left;
    	}
    }

    /*12.1 Any Binary Tree: Populating Next Right Pointers in Each Node*/
    public void connectBinaryTree(BinaryTreeNode root) {
    	if (root == null)
    		return;
    	if (root.next == null) {
    		if (root.right != null) root.right.next = null;
    		if (root.left != null) root.left = root.right != null ? root.right : null;
    	} else {
    		BinaryTreeNode across = root.next; //connect root's left or right to root's siblings left or right
    		while (across != null) {
    			if (across.left != null) {
    				across = across.left;
    				break;
    			}
    			if (across.right != null) {
    				across = across.right;
    				break;
    			}
    			across = across.next;
    		}
    		if (across != null) {
    			if (root.right != null)
    				root.right.next = across;
    			if (root.left != null)
    				root.left = root.right != null ? root.right : across;
    		}
    	}
    	connectBinaryTree(root.right); //traverse order is important
    	connectBinaryTree(root.left);
    }
    
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    public class Solution {
        public List<List<Integer>> pathSum(BinaryTreeNode root, int sum) {
            List<List<Integer>> sums = new ArrayList<List<Integer>>();
            pathSumHelper(root, sum, sums);
            return sums;
        }
        
        private boolean pathSumHelper(BinaryTreeNode root, int sum, List<List<Integer>> sums) {
            if (root == null)
                return false;
                
            sum -= root.value;
            
            if ( root.left == null && root.right == null) {
                if (sum == 0) {
                    List<Integer> allSum = new ArrayList<Integer>();
                    allSum.add(root.value);
                    sums.add(allSum);
                    return true;
                }
                else 
                    return false;
            }
            
            boolean isCorrect = false;
            List<List<Integer>> rightSums = new ArrayList<List<Integer>>(sums);
            if (pathSumHelper(root.left, sum, sums)) { 
                isCorrect = true;
            }
            
            if (pathSumHelper(root.right, sum, rightSums)) { 
                isCorrect = true;
            }
            sums.addAll(rightSums);
            if (isCorrect) {
                for (int i = 0; i < sums.size(); i++) {
                    List<Integer> allSum = sums.get(i);
                    allSum.add(0, root.value);
                } 
                return true;
            } else 
                return false;
        }
    }
}
