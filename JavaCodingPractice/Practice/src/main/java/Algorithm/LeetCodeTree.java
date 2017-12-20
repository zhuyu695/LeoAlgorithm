package Algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class LeetCodeTree {
public class TreeNode {
   int val;
   TreeNode left;
   TreeNode right;
   TreeNode(int x) { val = x; }
}
	
//156. Binary Tree Upside Down
//Total Accepted: 11053 Total Submissions: 28531 Difficulty: Medium
//Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.
//
//For example:
//Given a binary tree {1,2,3,4,5},
//    1
//   / \
//  2   3
// / \
//4   5
//return the root of the binary tree [4,5,2,#,#,3,1].
//   4
//  / \
// 5   2
//    / \
//   3   1  
//confused what "{1,#,2,3}" means? > read more on how binary tree is serialized on OJ.
//
//
//OJ's Binary Tree Serialization:
//The serialization of a binary tree follows a level order traversal, where '#' signifies a path terminator where no node exists below.
//
//Here's an example:
//   1
//  / \
// 2   3
//    /
//   4
//    \
//     5
//The above binary tree is serialized as "{1,2,3,#,#,4,#,#,5}".
public class Solution156 {
    TreeNode parent = null;
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = null;
        root.right = null;
        if (left != null) {
            TreeNode newRoot = upsideDownBinaryTree(left);
            left.left = right;
            left.right = root;
            return newRoot;
        } else {
            return root;
        }
    }
}

//298. Binary Tree Longest Consecutive Sequence
//Total Accepted: 9021 Total Submissions: 24179 Difficulty: Medium
//Given a binary tree, find the length of the longest consecutive sequence path.
//
//The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).
//
//For example,
//   1
//    \
//     3
//    / \
//   2   4
//        \
//         5
//Longest consecutive sequence path is 3-4-5, so return 3.
//   2
//    \
//     3
//    / 
//   2    
//  / 
// 1
//Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
public class Solution298 {
    public int longestConsecutive(TreeNode root) {
        if(root == null){
            return 0;
        }
        return findLongest(root, 0, root.val - 1);
    }
    
    private int findLongest(TreeNode root, int length, int preVal){
        if(root == null){
            return length;
        }
        // 判断当前是否连续
        int currLen = preVal + 1 == root.val ? length + 1 : 1;
        // 返回当前长度，左子树长度，和右子树长度中较大的那个
        return Math.max(currLen, Math.max(findLongest(root.left, currLen, root.val), findLongest(root.right, currLen, root.val)));  
    }
}

//250. Count Univalue Subtrees
//Total Accepted: 6589 Total Submissions: 17848 Difficulty: Medium
//Given a binary tree, count the number of uni-value subtrees.
//
//A Uni-value subtree means all nodes of the subtree have the same value.
//
//For example:
//Given binary tree,
//              5
//             / \
//            1   5
//           / \   \
//          5   5   5
//return 4.
public class Solution250 {
    private int result = 0;
    public int countUnivalSubtrees(TreeNode root) {
        if (root == null) {
            return 0;
        }
        helper(root);
        return result;
    }
    
    public boolean helper(TreeNode root) {
        if (root == null)
            return true;
        if (root.left == null && root.right == null) {
            result++;
            return true;
        }
        
        if (root.right == null) {
            if (helper(root.left) && root.val == root.left.val) {
                result++;
                return true;
            } else {
                return false;
            }
        } else if (root.left == null) {
            if (helper(root.right) && root.val == root.right.val) {
                result++;
                return true;
            } else {
                return false;
            }
        } else {
            boolean left = helper(root.left);
            boolean right = helper(root.right);
            if (left && right && root.val == root.right.val && root.left.val == root.val){
                 result++;
                 return true;
            } else {
                 return false;
            }
        }
    }
}

//270. Closest Binary Search Tree Value
//Total Accepted: 13129 Total Submissions: 37708 Difficulty: Easy
//Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.
//
//Note:
//Given target value is a floating point.
//You are guaranteed to have only one unique value in the BST that is closest to the target.
public class Solution270 {
    private int closest = 0;
    public int closestValue(TreeNode root, double target) {
        if (root == null)
            return -1;
        helper(root, target);
        return closest;
    }
    
    public void helper(TreeNode root, double target) {
        if (target < root.val) {
            if (root.left == null) {
                closest =  root.val;
                return;
            } 
            helper(root.left, target);
        } else {
            if (root.right == null) {
                closest = root.val;
                return;
            }
            helper(root.right, target);
        }
        if (Math.abs(closest - target) > Math.abs(root.val - target)) {
            closest = root.val;
        }
    }
}

//255. Verify Preorder Sequence in Binary Search Tree
//Given an array of numbers, verify whether it is the correct preorder traversal sequence of a binary search tree.
//
//You may assume each number in the sequence is unique.
//
//Follow up:
//Could you do it using only constant space complexity?
//
//Show Company Tags
//Show Tags
//Show Similar Problems
public class Solution255 {
    public boolean verifyPreorder(int[] preorder) {
        
        //kinda simulate the traversal, keeping a stack of nodes (just their values) of which we're still in the left subtree. If the next number is smaller than the last stack value, then we're still in the left subtree of all stack nodes, so just push the new one onto the stack. But before that, pop all smaller ancestor values, as we must now be in their right subtrees (or even further, in the right subtree of an ancestor). Also, use the popped values as a lower bound, since being in their right subtree means we must never come across a smaller number anymore.
        if (preorder == null || preorder.length == 0) 
            return true;  
        LinkedList<Integer> stack = new LinkedList<Integer>();  
        int low = Integer.MIN_VALUE;  
        for (int i = 0; i < preorder.length; i++) {  
            if (preorder[i] < low) 
                return false;  
            while (!stack.isEmpty() && preorder[i] > stack.peek()) {  
                low = stack.pop();  
            }  
            stack.push(preorder[i]);  
        }  
        return true;  
    }
}

//272. Closest Binary Search Tree Value II
//Given a non-empty binary search tree and a target value, find k values in the BST that are closest to the target.
//
//Note:
//Given target value is a floating point.
//You may assume k is always valid, that is: k ≤ total nodes.
//You are guaranteed to have only one unique set of k values in the BST that are closest to the target.
//Follow up:
//Assume that the BST is balanced, could you solve it in less than O(n) runtime (where n = total nodes)?
//		Hint:
//
//			Consider implement these two helper functions:
//			getPredecessor(N), which returns the next smaller node to N.
//			getSuccessor(N), which returns the next larger node to N.
public class Solution272 {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        Queue<Integer> klist = new LinkedList<Integer>();
        Stack<TreeNode> stk = new Stack<TreeNode>();
        // 迭代中序遍历二叉搜索树的代码
        while(root != null){
            stk.push(root);
            root = root.left;
        }
        while(!stk.isEmpty()){
            TreeNode curr = stk.pop();
            // 维护一个大小为k的队列
            // 队列不到k时直接加入
            if(klist.size() < k){
                klist.offer(curr.val);
            } else {
            // 队列到k时，判断下新的数是否更近，更近就加入队列并去掉队头
                int first = klist.peek();
                if(Math.abs(first - target) > Math.abs(curr.val - target)){
                    klist.poll();
                    klist.offer(curr.val);
                } else {
                // 如果不是更近则直接退出，后面的数只会更大
                    break;
                }
            }
            // 中序遍历的代码
            if(curr.right != null){
                curr = curr.right;
                while(curr != null){
                    stk.push(curr);
                    curr = curr.left;
                }
            }
        }
        // 强制转换成List，是用LinkedList实现的所以可以转换
        return (List<Integer>)klist;
    }
}

//333. Largest BST Subtree
//Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with largest number of nodes in it.
//
//Note:
//A subtree must include all of its descendants.
//Here's an example:
//    10
//    / \
//   5  15
//  / \   \ 
// 1   8   7
//The Largest BST Subtree in this case is the highlighted one. 
//The return value is the subtree's size, which is 3.
//Hint:
//
//You can recursively use algorithm similar to 98. Validate Binary Search Tree at each node of the tree, which will result in O(nlogn) time complexity.
//Follow up:
//Can you figure out ways to solve it with O(n) time complexity?
public class Solution333 {
    public class RecordNode {
        int res;
        int small, large;
        boolean isBST;
        public RecordNode() {
            res = 0;
            isBST = true;
            small = Integer.MAX_VALUE;
            large = Integer.MIN_VALUE;
        }
    }
    public int largestBSTSubtree(TreeNode root) {
        return dfs(root).res;
    }
    public RecordNode dfs(TreeNode node) {
        if (node == null) {
            return new RecordNode();
        }
        RecordNode cur = new RecordNode();
        RecordNode left = dfs(node.left);
        RecordNode right = dfs(node.right);
        cur.small = Math.min(left.small, node.val);
        cur.large = Math.max(right.large,node.val);
        if (left.isBST && right.isBST && left.large <= node.val && right.small >= node.val) {
            cur.res = left.res + right.res +1;
            cur.isBST = true;
        } else {
            cur.res=Math.max(left.res,right.res);
            cur.isBST = false;
        }
        return cur;
    }
}

}
