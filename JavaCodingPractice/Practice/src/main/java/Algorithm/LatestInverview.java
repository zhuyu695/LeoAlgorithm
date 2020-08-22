package Algorithm;

import java.util.*;

public class LatestInverview {
    //Frog jump
    public boolean canCross(int[] stones) {
        Map<Integer, Boolean> tracker = new HashMap();
        return helper(stones, 0, 0, tracker);
    }

    private boolean helper(int[] stones, int pos, int jump, Map<Integer, Boolean> tracker) {
        int n = stones.length;
        if (pos >= n-1) {
            return true;
        }
        int key = pos | jump << 11;
        if (tracker.containsKey(key)) {
            return tracker.get(key);
        }
        for (int i=pos+1;i<n;++i) {
            int dist = stones[i] - stones[pos];
            if (dist < jump-1) {
                continue;
            }
            if (dist > jump+1) {
                tracker.put(key, false);
                return false;
            }
            if (helper(stones, i, dist, tracker)) {
                tracker.put(key, true);
                return true;
            }
        }
        return false;
    }

    public boolean canCrossDP(int[] stones) {
        //Map to track jumping from current stone index
        Map<Integer, Set<Integer>> tracker = new HashMap();
        //dp[i]: jumping dist from stone i
        //dp[i] = dp[k]+1/dp[k]/dp[k-1] if (stones[i]- stones[k]) = dp[k]+ 1 || dp[k] || dp[k-1]
        if (stones.length == 0) {
            return true;
        }
        int[] dp = new int[stones.length];
        dp[0] = 0;
        tracker.put(0, new HashSet());
        tracker.get(0).add(1);
        int start = 0;
        for (int i=1; i<stones.length; ++i) {
            while(dp[start] + 1 < stones[i] - stones[start]) ++start;
            for(int k=start; k<i; ++k) {
                int dist = stones[i] - stones[k];
                Set<Integer> stoneSet = tracker.getOrDefault(k, new HashSet());
                if (stoneSet.contains(dist - 1) || stoneSet.contains(dist) || stoneSet.contains(dist + 1)) {
                    tracker.putIfAbsent(i, new HashSet());
                    tracker.get(i).add(dist);
                    dp[i] = Math.max(dp[i], dist);
                }
            }
        }
        return dp[stones.length-1] > 0;
    }

    //Reverse Nodes in K-Group
    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) { return null; }
        ListNode dummy = new ListNode();


        dummy.next = head;
        ListNode current = dummy;
        ListNode next = current.next;

        while(next != null) {
            Stack<ListNode> stack = new Stack();
            for (int i=0; i<k && next != null; ++i) {
                stack.push(next);
                next = next.next;
            }
            if (stack.size() < k) {
                return dummy.next;
            }
            while(stack.size() > 0) {
                current.next = stack.pop();
                current = current.next;
            }
            current.next = next;
        }

        return dummy.next;
    }

    //First Missing Positive
    /** Bucket sorting
    Index:
    [0, 1, 2, 3]
    Value:
    [3, 1, 4, -1]
    =>[4, 1, 3, -1]
    =>[-1, 1, 3, 4]
    =>[1, -1. 3. 4]
     */
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;
        for (int i=0; i<nums.length; ++i) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                //nums[nums[i] - 1] is supposed position of nums[i] value;
                int tmp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = tmp;
            }
        }
        for (int i=0; i<nums.length; ++i) {
            if (nums[i] != i+1) {
                return i+1;
            }
        }
        return nums.length + 1;
    }

    //Trapping Rain Water
//    Input: [0,1,0,2,1,0,1,3,2,1,2,1]
//    Output: 6
    public int trap(int[] height) {
        if (height == null || height.length == 0) return 0;
        int[] left = new int[height.length];
        int[] right = new int[height.length];

        left[0] = 0;
        for (int i=1; i<height.length-1; ++i) {
            left[i] = Math.max(left[i-1], height[i-1]);
        }
        right[height.length - 1] = 0;
        for (int i=height.length-2; i>=0; --i) {
            right[i] = Math.max(right[i+1], height[i+1]);
        }
        int sum = 0;
        for (int i=0; i<height.length; ++i) {
            int cur = Math.min(left[i], right[i]) - height[i];
            if (cur > 0) {
                sum += cur;
            }
        }
        return sum;
    }

    //Trapping Rain Water II
    private int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};

    class Cell implements Comparable<Cell> {
        int row;
        int col;
        int height;

        public Cell(int r, int c, int h) {
            row = r;
            col = c;
            height = h;
        }

        @Override
        public int compareTo(Cell other) {
            if (this.height == other.height) return 0;
            if (this.height > other.height) return 1;
            return -1;
        }
    }

    public int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0) {
            return 0;
        }

        int m = heightMap.length;
        int n = heightMap[0].length;

        PriorityQueue<Cell> queue = new PriorityQueue();

        int res = 0;
        boolean[][] visited = new boolean[m][n];

        for (int i=0; i<m; ++i) {
            visited[i][0] = true;
            visited[i][n-1] = true;
            queue.add(new Cell(i, 0, heightMap[i][0]));
            queue.add(new Cell(i, n-1, heightMap[i][n-1]));
        }

        for (int i=1; i<n-1; ++i) {
            visited[0][i] = true;
            visited[m-1][i] = true;
            queue.add(new Cell(0, i, heightMap[0][i]));
            queue.add(new Cell(m-1, i, heightMap[m-1][i]));
        }

        while(queue.size() > 0) {
            Cell cur = queue.poll();
            int row = cur.row, col = cur.col, h = cur.height;

            for (int[] d : dirs) {
                int r = d[0] + row;
                int c = d[1] + col;
                if (r >0 && c>0 && r<m-1 && c <n-1 && !visited[r][c]) {
                    visited[r][c] = true;
                    res += Math.max(0, h-heightMap[r][c]);
                    queue.offer(new Cell(r, c, Math.max(h, heightMap[r][c])));
                }
            }
        }
        return res;
    }

    //Minimum Remove to Make Valid Parentheses
//    Input: s = "lee(t(c)o)de)"
//    Output: "lee(t(c)o)de"
//    Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
    public String minRemoveToMakeValid(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<s.length(); ++i) {
            if (s.charAt(i) == '(') {
                cnt++;
            } else if (s.charAt(i) == ')') {
                if (cnt == 0) { continue; }
                --cnt;
            }
            sb.append(s.charAt(i));
        }

        StringBuilder res = new StringBuilder();
        for (int i=sb.length()-1; i>=0; --i) {
            if (sb.charAt(i) == '(' && cnt>0) {
                --cnt;
                continue;
            }
            res.append(sb.charAt(i));
        }

        return res.reverse().toString();
    }

    //Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit
    class Checker {
        int start;
        int index;
        Checker(int s, int i) {
            start = s;
            index = i;
        }

        public boolean equals(Object obj) {
            Checker otherChecker = (Checker) obj;
            if (otherChecker.start == start && otherChecker.index == index) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return start << 11 + index;
        }
    }
    Map<Checker, Integer> tracker = new HashMap<>();

    public int longestSubarray(int[] nums, int limit) {
        int max = Integer.MIN_VALUE;
        for (int i=0; i<nums.length; ++i) {
            int cnt = traverse(nums, limit, i, 1);
            max = cnt > max ? cnt : max;
        }
        return max;
    }

    private int traverse(int[] nums, int limit, int start, int index) {
        if (index + start >= nums.length) {
            return 1;
        }
        if (tracker.containsKey(new Checker(start, index))) {
            return tracker.get(new Checker(start, index));
        }
        for (int i=0; i<=index; ++i) {
            for (int j=i+1; j<=index; ++j) {
                if (Math.abs(nums[start+i]-nums[start+j]) > limit) {
                    return 1;
                }
            }
        }
        int cur = 1 + traverse(nums, limit, start, index+1);
        tracker.put(new Checker(start, index), cur);
        return cur;
    }

    //2nd O(nlogN) tree map
    public int longestSubarray1(int[] nums, int limit) {
        if (nums.length == 0) { return 0; }
        // (value, index)
        TreeMap<Integer, Integer> tracker = new TreeMap<>();
        tracker.put(nums[0], 0);
        int max = 1;
        int j=0;
        for (int i=0; i<nums.length; ++i) {
            while (Math.abs(tracker.firstKey() - tracker.lastKey()) <= limit) {
                max = Math.max(max, j-i+1);
                ++j;
                if (j<nums.length) {
                    tracker.put(nums[j], j);
                } else {
                    break;
                }
            }
            if (j==nums.length) {
                return max;
            }
            // solve duplicated sequence 4, 2, 2, 2, 4
            while(i+1 <j && nums[i+1] == nums[i]) {
                ++i;
            }
            // solve same sequence appear again later 3, 3, 4, 3, 3, 9, 6, 6
            // in this case, don't remove from tracker.
            if (tracker.get(nums[i]) > i) {
                continue;
            }
            tracker.remove(nums[i]);
        }
        return max;
    }

    //3nd O(n) deque
    public int longestSubarray2(int[] nums, int limit) {
        if (nums.length == 0) { return 0; }
        // (value, index)
        Deque<Integer> dequeMax = new LinkedList<>();
        Deque<Integer> dequeMin = new LinkedList<>();
        dequeMax.add(0);
        dequeMin.add(0);
        int res = 1;
        int j = 0;
        int max = nums[0], min = nums[0];

        for (int i=0; i<nums.length; ++i) {
            while(Math.abs(max - min) <= limit) {
                res = Math.max(res, j-i+1);
                ++j;
                if (j == nums.length) break;
                //update max queue
                while(dequeMax.size()>0 && nums[j] >= nums[dequeMax.getLast()]) {
                    dequeMax.removeLast();
                }
                dequeMax.addLast(j);
                max = nums[dequeMax.getFirst()];
                //update min queue
                while(dequeMin.size()>0 && nums[j] <= nums[dequeMin.getLast()]) {
                    dequeMin.removeLast();
                }
                dequeMin.addLast(j);
                min = nums[dequeMin.getFirst()];
            }
            if (j == nums.length) {
                return res;
            }

            if (dequeMax.size()>0 && dequeMax.getFirst() == i) {
                dequeMax.removeFirst();
                max = nums[dequeMax.getFirst()];
            }

            if (dequeMin.size()>0 && dequeMin.getFirst() == i) {
                dequeMin.removeFirst();
                min = nums[dequeMin.getFirst()];
            }
        }
        return res;
    }

    //Reverse Pairs
//    Input: [1,3,2,3,1]
//    Output: 2
    public int reversePairs0(int[] nums) {
        int total = 0;
        for (int i=0; i<nums.length; ++i) {
            for (int j=i+1; j<nums.length; ++j) {
                if (i<j && (float) nums[i] / 2 - nums[j] > 0) {
                    ++total;
                }
            }
        }
        return total;
    }

    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int lo, int hi) {
        if (lo >= hi) return 0;
        int mid = lo + (hi - lo) / 2;
        int res = 0;
        res += mergeSort(nums, lo, mid);
        res += mergeSort(nums, mid + 1, hi);
        res += merge(nums, lo, mid, hi);
        return res;
    }

    private int merge(int[] nums, int lo, int mid, int hi) {
        int[] tmp = new int[hi-lo+1];

        int count = 0;
        int l=lo;
        int r=mid+1;

        while(l<=mid && r<=hi) {
            if ((long) nums[l] > 2 * (long) nums[r]) {
                count += mid - l + 1;
                ++r;
            } else {
                ++l;
            }
        }

        l=lo;
        r=mid+1;
        int index=0;

        while(l<=mid && r<=hi) {
            if (nums[l] > nums[r]) {
                tmp[index++] = nums[r++];
            } else {
                tmp[index++] = nums[l++];
            }
        }
        while(l<=mid) {
            tmp[index++] = nums[l++];
        }
        while(r<=hi) {
            tmp[index++] = nums[r++];
        }
        for(int i=lo; i<=hi; ++i) {
            nums[i] = tmp[i-lo];
        }
        return count;
    }

    public static void main(String[] args) {
        LatestInverview li = new LatestInverview();
//        ListNode e = new ListNode(5);
//        ListNode d = new ListNode(4, e);
//        ListNode c = new ListNode(3, d);
//        ListNode b = new ListNode(2, c);
//        ListNode a = new ListNode(1, b);
//        li.reverseKGroup(a, 2);

//        int[] a = {4,2,2,2,4,4,2,2};
//        System.out.println(li.longestSubarray2(a, 0));

        int[] a = {1, 3, 2, 3, 1};
        li.reversePairs(a);
    }
}
