package Algorithm;

public class LeetCodeBinarySearch {
//	270. Closest Binary Search Tree Value
//	Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.
//
//	Note:
//	Given target value is a floating point.
//	You are guaranteed to have only one unique value in the BST that is closest to the target.
//	Show Company Tags
//	Show Tags
//	Show Similar Problems

  public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
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

//167. Two Sum II - Input array is sorted
//Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
//
//The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
//
//You may assume that each input would have exactly one solution.
//
//Input: numbers={2, 7, 11, 15}, target=9
//Output: index1=1, index2=2
public class Solution167 {
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length == 0)
            return new int[0];
        int left = 0, right = numbers.length - 1;
        
        int[] result = new int[2];
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                result[0] = left + 1;
                result[1] = right + 1;
                return result;
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return result;
    }
}

//302. Smallest Rectangle Enclosing Black Pixels
//An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel. The black pixels are connected, i.e., there is only one black region. Pixels are connected horizontally and vertically. Given the location (x, y) of one of the black pixels, return the area of the smallest (axis-aligned) rectangle that encloses all black pixels.
//
//For example, given the following image:
//
//[
//  "0010",
//  "0110",
//  "0100"
//]
//and x = 0, y = 2,
//Return 6.
public class Solution {
    public int minArea(char[][] image, int x, int y) {
        int m = image.length, n = image[0].length;
        int colMin = binarySearch(image, true, 0, y, 0, m, true);
        int colMax = binarySearch(image, true, y + 1, n, 0, m, false);
        int rowMin = binarySearch(image, false, 0, x, colMin, colMax, true);
        int rowMax = binarySearch(image, false, x + 1, m, colMin, colMax, false);
        return (rowMax - rowMin) * (colMax - colMin);
    }

    public int binarySearch(char[][] image, boolean horizontal, int lower, int upper, int min, int max, boolean goLower) {
        while(lower < upper) {
            int mid = lower + (upper - lower) / 2;
            boolean inside = false;
            for(int i = min; i < max; i++) {
                if((horizontal ? image[i][mid] : image[mid][i]) == '1') {
                    inside = true; 
                    break;
                }
            }
            if(inside == goLower) {
                upper = mid;
            } else {
                lower = mid + 1;
            }
        }
        return lower;
    }
}
}
