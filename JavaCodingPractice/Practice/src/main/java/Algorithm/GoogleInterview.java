package Algorithm;

import javafx.util.Pair;

import java.util.*;

public class GoogleInterview {
//    Longest Substring with At Most Two Distinct Characters
//    Input: "eceba"
//    Output: 3
//    Explanation: t is "ece" which its length is 3.
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() < 3) {
            return s.length();
        }
        int start = 0;
        int runner = 0;
        int maxLen = 0;

        Map<Character, Integer> cMap = new HashMap<>();
        while (runner < s.length()) {
            cMap.put(s.charAt(runner), runner);
            if (cMap.size() > 2) {
                int minIndex = Collections.min(cMap.values());
                start = minIndex + 1;
                cMap.remove(s.charAt(minIndex));
            }
            runner++;
            int length = runner - start;
            maxLen = length > maxLen ? length : maxLen;
        }
        return maxLen;
    }

    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        if(nums.length == 0){
            if(upper - lower == 0){
                result.add(upper + "");
            }
            else{
                result.add(lower + "->" + upper);
            }
            return result;
        }
        int start = nums[0];
        if((long)start - lower > 0 ){
            if((long)start - lower <= 1){
                result.add(lower + "");
            }
            else{
                result.add(lower + "->" + (start - 1));
            }
        }
        for(int i = 0; i < nums.length - 1; i ++){
            if(nums[i + 1] - nums[i] == 2){
                result.add((nums[i] + 1) + "");
            }
            else if((long)nums[i + 1] - nums[i] > 2){
                result.add((nums[i] + 1) + "->" + (nums[i + 1] - 1));
            }
        }
        int end = nums[nums.length - 1];
        if((long)upper - end > 0 ){
            if((long)upper - end <= 1){
                result.add(upper + "");
            }
            else{
                result.add((end + 1) + "->" + upper);
            }
        }
        return result;
    }

//    Next Closest Time
//    Input: "19:34"
//    Output: "19:39"
//    Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.  It is not 19:33, because this occurs 23 hours and 59 minutes later.
    public String nextClosestTime(String time) {
        int start = 60 * Integer.parseInt(time.substring(0, 2));
        start += Integer.parseInt(time.substring(3));
        int res = start;
        int elapsed = 24 * 60;
        Set<Integer> allowed = new HashSet();
        for (char c: time.toCharArray()) if (c != ':') {
            allowed.add(c - '0');
        }

        for (int h1: allowed) {
            for (int h2: allowed) if (h1 * 10 + h2 < 24) {
                for (int m1: allowed) {
                    for (int m2: allowed) if (m1 * 10 + m2 < 60) {
                        int cur = 60 * (h1 * 10 + h2) + (m1 * 10 + m2);
                        int candElapsed = Math.floorMod(cur - start, 24 * 60);
                        if (0 < candElapsed && candElapsed < elapsed) {
                            res = cur;
                            elapsed = candElapsed;
                        }
                    }
                }
            }
        }

        return String.format("%02d:%02d", res / 60, res % 60);
    }

//    Meeting Rooms II
//    Input: [[0, 30],[5, 10],[15, 20]]
//    Output: 2
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int a1[], int a2[]) {
                return a1[0] - a2[0];
            }
        });

        if (intervals.length == 0 || intervals[0].length == 0) {
            return 0;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(intervals[0][1]);
        for (int i=1; i<intervals.length; ++i) {
            int endTime = queue.peek();
            if (intervals[i][0] >= endTime) {
                queue.poll();
            }
            queue.add(intervals[i][1]);
        }

        return queue.size();
    }

//    Backspace String Compare
//    Input: S = "ab##", T = "c#d#"
//    Output: true
//    Explanation: Both S and T become "".
    public boolean backspaceCompare(String S, String T) {
        return reprocess(S).equals(reprocess(T));
    }

    private String reprocess(String s) {
        StringBuilder sbuilder = new StringBuilder();

        int end = s.length() -1;
        int deleteCounter = 0;
        while (end >= 0) {
            if (deleteCounter == 0 && s.charAt(end) != '#') {
                sbuilder.append(s.charAt(end));
            } else if (s.charAt(end) == '#') {
                deleteCounter++;
            } else if (s.charAt(end) != '#') {
                deleteCounter--;
            }
            end--;
        }

        return sbuilder.toString();
    }

//    Minimum Cost to Hire K Workers
//    Input: quality = [10,20,5], wage = [70,50,30], K = 2
//    Output: 105.00000
//    Explanation: We pay 70 to 0-th worker and 35 to 2-th worker.
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        double minWage = Double.MAX_VALUE;
        PriorityQueue<Double> topK = new PriorityQueue<>(10, Collections.reverseOrder());
        for (int i=0; i<quality.length; ++i) {
            double curRatio = ((double) wage[i]) / quality[i];
            topK.clear();
            for (int j=0; j<quality.length; ++j) {
                if (wage[j] > quality[j] * curRatio) {
                    continue;
                } else {
                    topK.offer(quality[j] * curRatio);
                    if (topK.size() > K) {
                        topK.poll();
                    }
                    if (topK.size() == K) {
                        Iterator<Double> iter = topK.iterator();
                        double total = 0.0;
                        while(iter.hasNext()) {
                            total += iter.next();
                        }
                        if (total < minWage) {
                            minWage = total;
                        }
                    }
                }
            }
        }
        return minWage;
    }

    //Solution2
    class Worker implements Comparable<Worker> {
        public int quality, wage;
        public Worker(int q, int w) {
            quality = q;
            wage = w;
        }

        public double ratio() {
            return (double) wage / quality;
        }

        public int compareTo(Worker other) {
            return Double.compare(ratio(), other.ratio());
        }
    }

    public double mincostToHireWorkers1(int[] quality, int[] wage, int K) {
        int N = quality.length;
        Worker[] workers = new Worker[N];
        for (int i = 0; i < N; ++i)
            workers[i] = new Worker(quality[i], wage[i]);
        Arrays.sort(workers);

        double ans = Double.MAX_VALUE;
        int sumq = 0;
        PriorityQueue<Integer> pool = new PriorityQueue(10, Collections.reverseOrder());
        for (Worker worker: workers) {
            pool.offer(worker.quality);
            sumq += worker.quality;
            if (pool.size() > K)
                sumq -= pool.poll();
            if (pool.size() == K)
                ans = Math.min(ans, sumq * worker.ratio());
        }

        return ans;
    }

    //K Closest Points to Origin
//    Input: points = [[3,3],[5,-1],[-2,4]], K = 2
//    Output: [[3,3],[-2,4]]
    public int[][] kClosest(int[][] points, int K) {
        Point[] pArr = new Point[points.length];
        for (int i=0; i<points.length; ++i) {
            pArr[i] = new Point(points[i][0], points[i][1]);
        }
        Arrays.sort(pArr);
        int[][] res = new int[K][2];
        for (int i=0; i<K; ++i) {
            res[i][0] = pArr[i].x;
            res[i][1] = pArr[i].y;
        }
        return res;
    }

    class Point implements Comparable<Point> {
        public int x;
        public int y;
        double distance;

        public Point(int xV, int yV) {
            x = xV;
            y = yV;
            distance = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
        }

        public int compareTo(Point b) {
            return Double.compare(distance, b.distance);
        }
    }


     interface Robot {
         // Returns true if the cell in front is open and robot moves into the cell.
         // Returns false if the cell in front is blocked and robot stays in the current cell.
         public boolean move();
         // Robot will stay in the same cell after calling turnLeft/turnRight.
         // Each turn will be 90 degrees.
         public void turnLeft();
         public void turnRight();
         // Clean the current cell.
         public void clean();
    }

    public void cleanRoom(Robot robot) {
        Set<Pair<Integer, Integer>> pointSet = new HashSet<>();
        backtrack(robot, 0, 0, 0, pointSet, getDirections());
    }

    private List<Pair<Integer, Integer>> getDirections() {
        List<Pair<Integer, Integer>> dirs = new ArrayList<>();
        dirs.add(new Pair(-1, 0));
        dirs.add(new Pair(0, 1));
        dirs.add(new Pair(1, 0));
        dirs.add(new Pair(0, -1));
        return dirs;
    }

    private void back(Robot robot) {
        robot.turnRight();
        robot.turnRight();
        robot.move();
        robot.turnRight();
        robot.turnRight();
    }

    private void backtrack(Robot robot, int x, int y, int d, Set<Pair<Integer, Integer>> pSet, List<Pair<Integer, Integer>> dirs) {
        pSet.add(new Pair(x, y));
        robot.clean();
        for (int i=0; i<dirs.size(); ++i) {
            int newD = (d + i) % 4;
            Pair<Integer, Integer> dir = dirs.get(newD);
            int newX = x + dir.getKey();
            int newY = y + dir.getValue();
            if (!pSet.contains(new Pair(newX, newY)) && robot.move()) {
                backtrack(robot, newX, newY, newD, pSet, dirs);
                back(robot);
            }
            robot.turnRight();
        }
    }

//    Word Ladder
//    Input:
//    beginWord = "hit",
//    endWord = "cog",
//    wordList = ["hot","dot","dog","lot","log","cog"]
//
//    Output: 5
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>();
        Set<String> visited = new HashSet<>();

        Queue<String> queue = new LinkedList<String>();
        queue.add(beginWord);

        int steps = 0;
        for (String wd : wordList) {
            dict.add(wd);
        }

        if (!dict.contains(endWord)) {
            return steps;
        }

        while (queue.size() > 0) {
            int curSize = queue.size();
            for (int i=0; i<curSize; ++i) {
                String curStr = queue.poll();
                visited.add(curStr);
                if (curStr.equals(endWord)) {
                    return ++steps;
                } else {
                    List<String> words = getOneEditAway(curStr, visited, dict);
                    for (String word: words) {
                        queue.add(word);
                    }
                }
            }
            ++steps;
        }
        return 0;
    }

    public List<String> getOneEditAway(String curStr, Set<String> visited, Set<String> dict) {
        List<String> res = new ArrayList<>();
        for (int i=0; i<curStr.length(); ++i) {
            for (int j=0; j<26; ++j) {
                char replaceLetter = (char)('a' + j);
                String newStr = curStr.substring(0,i) + replaceLetter + curStr.substring(i+1);
                if (dict.contains(newStr) && !visited.contains(newStr)) {
                    res.add(newStr);
                }
            }
        }
        return res;
    }

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }

    //Count complete tree nodes
    public int countNodes1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return count(root);
    }

    private int count(TreeNode r) {
        if (r.left == null && r.right == null) {
            return 1;
        }
        int left = 0;
        int right = 0;
        if (r.left != null) {
            left = count(r.left);
        }
        if (r.right != null) {
            right = count(r.right);
        }
        return left + right + 1;
    }

    //Evaluate Division (Graph problem)
//    equations = [ ["a", "b"], ["b", "c"] ],
//    values = [2.0, 3.0],
//    queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (int i=0; i<equations.size(); ++i) {
            String x = equations.get(i).get(0);
            String y = equations.get(i).get(1);
            double rate = values[i];
            Map<String, Double> kNeighbours;
            if (graph.containsKey(x)) {
                kNeighbours = graph.get(x);
            } else {
                kNeighbours = new HashMap();
            }
            kNeighbours.put(y, rate);
            graph.put(x, kNeighbours);

            Map<String, Double> vNeighbours;
            if (graph.containsKey(y)) {
                vNeighbours = graph.get(y);
            } else {
                vNeighbours = new HashMap();
            }
            vNeighbours.put(x, 1/rate);
            graph.put(y, vNeighbours);
        }

        for (Map.Entry<String, Map<String,Double>> entries : graph.entrySet()) {
            System.out.print("Key: " + entries.getKey());
            for (Map.Entry<String, Double> node: entries.getValue().entrySet()) {
                System.out.print("  Value: " + node.getKey() + "  Rate: " + node.getValue());
            }
            System.out.println();
        }

        double[] res = new double[queries.size()];

        for (int i=0; i<queries.size(); ++i) {
            List<String> query = queries.get(i);
            double value = getValue(query.get(0), query.get(1), graph, new HashSet<String>(), 1.0);
            res[i] = value;
        }
        return res;
    }

    private double getValue(String cur, String dest, Map<String, Map<String, Double>> graph, Set<String> visited, double rate) {
        if (visited.contains(cur) || !graph.containsKey(cur)) {
            return -1.0;
        }
        if (cur.equals(dest)) {
            return rate;
        }
        visited.add(cur);
        double defaultV = -1.0;
        for (Map.Entry<String, Double> edge: graph.get(cur).entrySet()) {
            if (!visited.contains(edge.getKey())) {
                double curR = rate * edge.getValue();
                double value = getValue(edge.getKey(), dest, graph, visited, curR);
                if (value - defaultV > 0.0001) {
                    return value;
                }
            }
        }
        return defaultV;
    }

    //Most Stones Removed with Same Row or Column
//    Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
//    Output: 5
    // graph with strong components.
    public int removeStones(int[][] stones) {
        int N = stones.length;
        if (N == 0 || stones[0].length == 0) {
            return 0;
        }

        int[][] graph = new int[N][N];

        for (int i=0; i<N; ++i) {
            for (int j=i+1; j<N; ++j) {
                if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                }
            }
        }

        int res = 0;
        boolean visited[] = new boolean[N];

        for(int i=0; i<N; ++i) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            Stack<Integer> stack = new Stack();
            stack.push(i);
            res--;
            while(!stack.isEmpty()) {
                int node = stack.pop();
                res++;
                int neighbours[] = graph[node];
                for (int j=0; j<neighbours.length; ++j) {
                    int neighbour = neighbours[j];
                    if (visited[j] == false && neighbour == 1) {
                        stack.push(j);
                        visited[j] = true;
                    }
                }
            }
        }
        return res;
    }

    //Flip Equivalent Binary Trees
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 == null || root2 == null) {
            return false;
        } else if (root1.val != root2.val) {
            return false;
        } else {
            return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)
                    || flipEquiv(root1.right, root2.left) && flipEquiv(root1.left, root2.right);
        }
    }

    //Word Squares
//    Input:
//            ["area","lead","wall","lady","ball"]
//
//    Output:
//            [
//            [ "wall",
//            "area",
//            "lead",
//            "lady"
//            ],
//            [ "ball",
//            "area",
//            "lead",
//            "lady"
//            ]
//            ]
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> res = new ArrayList();
        if (words.length == 0 || words[0].length() == 0) {
            return res;
        }
        int wordLength = words[0].length();
        Set<String> visited = new HashSet();
        iterate(wordLength, res, new ArrayList<String>(), words, visited);
        return res;
    }

    private void iterate(int length, List<List<String>> res, List<String> curList, String words[], Set<String> visited) {
        if (curList.size() == length) {
            res.add(new ArrayList(curList));
            return;
        }
        int rows = curList.size();
        for (int i=0; i<words.length; ++i) {
            if (visited.contains(words[i])) {
                continue;
            }

            if (checkValue(words[i], curList, rows)) {
                visited.add(words[i]);
                curList.add(words[i]);
                iterate(length, res, curList, words, visited);
                visited.remove(words[i]);
                curList.remove(words[i]);
            }

        }
    }

    private boolean checkValue(String cur, List<String> rec, int rows) {
        if (rec.size() == 0) {
            return true;
        }
        for (int i=0; i<=rows/2; ++i) {
            if (cur.charAt(i) != rec.get(i).charAt(rows)) {
                return false;
            }
        }
        return true;
    }

    //Android Unlock Patterns
//    Given an Android 3x3 key lock screen and two integers m and n, where 1 ≤ m ≤ n ≤ 9, count the total number of unlock patterns of the Android lock screen, which consist of minimum of m keys and maximum n keys.
    boolean used[] = new boolean[9];
    public int numberOfPatterns(int m, int n) {
        int total = 0;
        for (int i=m; i<=n; ++i) {
            total += calc(-1, i);
            used = new boolean[9];
        }
        return total;
    }

    public int calc(int last, int len) {
        if (len == 0) { return 1; }
        int sum = 0;
        //it becomes
        // 0, 1, 2
        // 3, 4, 5,
        // 6, 7, 8
        for (int i=0; i<9; ++i) {
            if (isValid(i, last)) {
                used[i] = true;
                sum += calc(i, len-1);
                used[i] = false;
            }
        }
        return sum;
    }

    public boolean isValid(int cur, int last) {
        if (used[cur]) { return false; }
        if (last == -1) { return true; }
        //knight moves or adjacent cells
        if ((cur+last) % 2 == 1) { return true; }
        int mid = (cur + last) / 2;
        if (mid == 4) {
            return used[mid];
        }
        if ((cur % 3 != last % 3) && (cur / 3 != last / 3 )) { return true; }
        return used[mid];
    }

    //generate parenthesis
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList();
        generate(0, 0, n, res, new StringBuilder());
        return res;
    }

    public void generate(int l, int r, int total, List<String> res, StringBuilder sbuilder) {
        if (l == total && r == total) {
            res.add(sbuilder.toString());
            return;
        }
        if (l+1 <= total) {
            generate(l+1, r, total, res, sbuilder.append('('));
            sbuilder.setLength(sbuilder.length()-1);
        }
        if (r+1 <= total && r<l) {
            generate(l, r+1, total, res, sbuilder.append(')'));
            sbuilder.setLength(sbuilder.length()-1);
        }
        return;
    }

    //Find First and Last Position of Element in Sorted Array
//    Input: nums = [5,7,7,8,8,10], target = 8
//    Output: [3,4]
    private int extremeInsertionIndex(int[] nums, int target, boolean left) {
        int lo = 0;
        int hi = nums.length;

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] > target || (left && target == nums[mid])) {
                hi = mid;
            }
            else {
                lo = mid+1;
            }
        }

        return lo;
    }

    public int[] searchRange(int[] nums, int target) {
        int[] targetRange = {-1, -1};

        int leftIdx = extremeInsertionIndex(nums, target, true);

        // assert that `leftIdx` is within the array bounds and that `target`
        // is actually in `nums`.
        if (leftIdx == nums.length || nums[leftIdx] != target) {
            return targetRange;
        }

        targetRange[0] = leftIdx;
        targetRange[1] = extremeInsertionIndex(nums, target, false)-1;

        return targetRange;
    }

    //Merge Intervals
//    Input: [[1,3],[2,6],[8,10],[15,18]]
//    Output: [[1,6],[8,10],[15,18]]
//    Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }

        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int a[], int b[]) {
                return a[0] - b[0];
            }
        });

        int endIndex=1;

        int cur[] = intervals[0];
        List<int[]> res = new ArrayList();
        res.add(cur);

        while (endIndex < intervals.length) {
            int next[] = intervals[endIndex];
            int last[] = res.get(res.size()-1);

            if (next[0] <= last[1]) {
                last[1] = last[1] > next[1] ? last[1] : next[1];
            } else {
                res.add(next);
            }
            endIndex++;
        }

        int r[][] = new int[res.size()][2];
        res.toArray(r);

        return r;
    }

//    Redundant Connection
//    Input: [[1,2], [1,3], [2,3]]
//    Output: [2,3]
//    Explanation: The given undirected graph will be like this:
//            1
//            / \
//            2 - 3
    public int[] findRedundantConnection(int[][] edges) {
        Map<Integer, Set<Integer>> graph = new HashMap();
        for (int i=0; i<edges.length; ++i) {
            int nodeA = edges[i][0];
            int nodeB = edges[i][1];
            //traverse and check first
            if (isDupe(graph, edges[i][0], edges[i][1], new HashSet())) {
                return edges[i];
            }
            if (!graph.containsKey(nodeA)) {
                graph.put(nodeA, new HashSet<Integer>());
            }
            if (!graph.containsKey(nodeB)) {
                graph.put(nodeB, new HashSet<Integer>());
            }
            graph.get(nodeA).add(nodeB);
            graph.get(nodeB).add(nodeA);
        }

        return new int[2];
    }

    private boolean isDupe(Map<Integer, Set<Integer>> graph, int start, int target, Set<Integer> visited) {
        if (start == target) {
            return true;
        }
        visited.add(start);
        if (graph.containsKey(start)) {
            for (int neighbour : graph.get(start)) {
                if (!visited.contains(neighbour)) {
                    if (isDupe(graph, neighbour, target, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // prev graph has direction
//    1.A node having two parents;
//    including corner case: e.g. [[4,2],[1,5],[5,2],[5,3],[2,4]]
//    2.A circle exists
//    If we can remove exactly 1 edge to achieve the tree structure, a single node can have at most two parents. So my solution works in two steps.
//
//    1.Check whether there is a node having two parents.
//    If so, store them as candidates A and B, and set the second edge invalid.
//    2.Perform normal union find.
//    If the tree is now valid
//- simply return candidate B
//else if candidates not existing
//- we find a circle, return current edge;
//else
//        - remove candidate A instead of B.
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] candidate1 = {-1, -1};
        int[] candidate2 = {-1, -1};
        int[] parent = new int[n+1]; //parent[i] = j means j is parent of i.

        for (int[] edge: edges) {
            int from = edge[0];
            int to = edge[1];
            if (parent[to] > 0) {
                candidate1 = new int[] {parent[to], to};
                candidate2 = new int[] {from, to};
                edge[1] = -1; //set this edge to be invalid
                break;
            } else {
                parent[to] = from;
            }
        }
        // prepare for union find, clear parent array
        Arrays.fill(parent, -1);

        for (int[] edge : edges) {
            if(edge[1] == -1) {
                continue;
            }

            int i = edge[0];
            int j = edge[1];
            int iset = find(parent, i);
            int jset = find(parent, j);

            if (iset != jset) {
                union(parent, iset, jset);
            } else {
                if (candidate1[0] == -1) {
                    return edge;
                } else {
                    return candidate1;
                }
            }
        }
        return candidate2;
    }

    private int find(int[] parent, int i) {
        if (parent[i] == -1) {
            return i;
        }
        parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    private void union(int[] parent, int i, int j) {
        parent[i] = j;
    }

    //Insert Interval
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // init data
        int newStart = newInterval[0], newEnd = newInterval[1];
        int idx = 0, n = intervals.length;
        ArrayList<int[]> output = new ArrayList<int[]>();

        // add all intervals before newInterval
        while (idx < n && intervals[idx][1] < newStart)
            output.add(intervals[idx++]);

        // merge newInterval
        int[] interval = new int[2];
        while(idx < n && intervals[idx][0] <= newEnd)
        {
            newStart = Math.min(newStart, intervals[idx][0]);
            newEnd = Math.max(newEnd, intervals[idx][1]);
            ++idx;
        }
        output.add(new int[]{newStart, newEnd});

        // add all intervals after newInterval
        while (idx < n)
            output.add(intervals[idx++]);

        return output.toArray(new int[output.size()][2]);
    }

    //Split Array Largest Sum
//    Input:
//    nums = [7,2,5,10,8]
//    m = 2
//
//    Output:
//            18
//
//    Explanation:
//    There are four ways to split nums into two subarrays.
//    The best way is to split it into [7,2,5] and [10,8],
//    where the largest sum among the two subarrays is only 18.

    public int splitArray(int[] nums, int m) {
        //0~j into i groups
        //d[i][j] = min{max(d[i-1][k], sum(k+1...j))}
        //d[1][j] = sum(0...j)

        int[] sums = new int[nums.length];

        for (int i=0; i<nums.length; ++i) {
            if (i==0) {
                sums[i] = nums[i];
            } else {
                sums[i] = sums[i-1] + nums[i];
            }
        }

        int[][] d = new int[m+1][nums.length];

        for (int i=1; i<=m; ++i) {
            for (int j=0; j<nums.length; ++j) {
                if (i == 1) {
                    d[i][j] = sums[j];
                } else {
                    int minVal = Integer.MAX_VALUE;
                    for (int k=0; k<j; ++k) {
                        minVal = Math.min(Math.max(d[i-1][k], sums[j] - sums[k]), minVal);
                    }
                    d[i][j] = minVal;
                }
            }
        }
        return d[m][nums.length-1];
    }

    //Maximum Product Subarray
//    Input: [2,3,-2,4]
//    Output: 6
//    Explanation: [2,3] has the largest product 6.
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        int curProduct = nums[0];
        int curMin = nums[0];
        int max = nums[0];
        for (int i=1; i<nums.length; ++i) {
            int prevProduct = curProduct;
            curProduct = Math.max(nums[i], Math.max(nums[i] * curProduct, nums[i] * curMin));
            curMin = Math.min(nums[i], Math.min(curMin * nums[i], prevProduct * nums[i]));
            max = Math.max(curProduct, max);
        }
        return max;
    }

    //Coin Change
//    Input: coins = [1, 2, 5], amount = 11
//    Output: 3
//    Explanation: 11 = 5 + 5 + 1
    public int coinChange(int[] coins, int amount) {
        if (coins.length == 0 || amount == 0) {
            return 0;
        }
        //dp[i][j] : amount j, first i types
        //dp[i][0] = 0
        //dp[i][j] = min(dp[i-1][j-coins[i]] + 1, dp[i][j])
        //dp[n][m]
        int n = coins.length;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE-1);
        dp[0] = 0;
        for (int i=0; i<n; ++i) {
            for (int j=1; j<=amount; ++j) {
                if (j - coins[i] >= 0) {
                    dp[j] = Math.min(dp[j-coins[i]] + 1, dp[j]);
                }
            }
        }
        if (dp[amount] == Integer.MAX_VALUE-1) {
            dp[amount] = -1;
        }
        return dp[amount];
    }

    //LRU cache
    static class LRUCache {
        int capacity;
        int counter;
        LinkedList<Integer> list;
        Map<Integer, Integer> dic;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            list = new LinkedList();
            dic = new HashMap();
        }

        public int get(int key) {
            if (dic.containsKey(key)) {
                list.remove(new Integer(key));
                list.offerFirst(new Integer(key));
                return dic.get(key);
            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (!dic.containsKey(key)) {
                if (counter >= capacity) {
                    int lastRec = list.getLast();
                    list.remove(new Integer(lastRec));
                    dic.remove(new Integer(lastRec));
                    counter--;
                }
                counter++;
            } else {
                list.remove(new Integer(key));
            }
            list.offerFirst(key);
            dic.put(key, value);
        }
    }

    //Candy
//    There are N children standing in a line. Each child is assigned a rating value.
//
//    You are giving candies to these children subjected to the following requirements:
//
//    Each child must have at least one candy.
//    Children with a higher rating get more candies than their neighbors.
//    What is the minimum candies you must give?
//    Input: [1,0,2]
//    Output: 5
//    Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
    public int candy(int[] ratings) {
        if (ratings.length == 0) {
            return 0;
        }
        int[] tracker = new int[ratings.length];
        int total = 1;
        int counter = 1;
        for (int i=1; i<ratings.length; ++i) {
            if (ratings[i] > ratings[i-1]) {
                counter += 1;
            } else if (ratings[i] == ratings[i-1]){
                counter = 1;
            } else if (ratings[i] < ratings[i-1]) {
                if (counter == 1) {
                    int runner = i-1;
                    counter++;
                    while(runner >= 0 && ratings[runner] > ratings[runner + 1] && counter > tracker[runner]) {
                        tracker[runner] = counter;
                        runner--;
                        counter++;
                        total++;
                    }
                }
                counter = 1;
            }
            tracker[i] = counter;
            total += counter;
        }
        return total;
    }
    // Better solution: have 2 arrays one leftToRight, one rightToLeft and pick max at each index of both.


    //Isomorphic Strings
//    Input: s = "egg", t = "add"
//    Output: true
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> tracker = new HashMap();

        if (s.length() != t.length()) {
            return false;
        }

        for (int i=0; i<s.length(); ++i) {
            char fst = s.charAt(i);
            char snd = t.charAt(i);
            if (tracker.containsKey(fst) && !tracker.get(fst).equals(snd)) {
                return false;
            }
            tracker.put(fst, snd);
        }
        Set<Character> unique = new HashSet(tracker.values());
        if (unique.size() != tracker.size()) {
            return false;
        }
        return true;
    }

    //Bulls and Cows
//    Input: secret = "1807", guess = "7810"
//    Output: "1A3B"
//    Explanation: 1 bull and 3 cows. The bull is 8, the cows are 0, 1 and 7.
    public String getHint(String secret, String guess) {
        Map<Character, Integer> tracker = new HashMap();

        int bulls = 0;
        int cows = 0;
        int n = secret.length();

        for (int i=0; i<n; ++i) {
            char s= secret.charAt(i);
            char g= guess.charAt(i);

            if (s == g) {
                bulls++;
            } else {
                //found secret char match previous guess
                if (tracker.getOrDefault(s, 0) < 0) {
                    cows++;
                }
                //found guess char match previous secret
                if (tracker.getOrDefault(g, 0) > 0) {
                    cows++;
                }

                tracker.put(s, tracker.getOrDefault(s, 0) + 1);
                tracker.put(g, tracker.getOrDefault(g, 0) - 1);
            }
        }
        return Integer.valueOf(bulls) + "A" + Integer.valueOf(cows) + "B";
    }

    //Range Sum Query 2D - Mutable
    int[][] sumMatrix;
    int[][] updateMatrix;
    int[][] rawMatrix;

    public void construct(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            sumMatrix = new int[0][0];
            updateMatrix = new int[0][0];
            rawMatrix = new int[0][0];
            return;
        }

        rawMatrix = matrix.clone();
        sumMatrix = new int[matrix.length][matrix[0].length];

        sumMatrix[0][0] = matrix[0][0];
        for (int i=1; i<matrix[0].length; ++i) {
            sumMatrix[0][i] = sumMatrix[0][i-1] + matrix[0][i];
        }
        for (int i=1; i<matrix.length; ++i) {
            sumMatrix[i][0] = sumMatrix[i-1][0] + matrix[i][0];
        }

        for (int i=1; i<matrix.length; ++i) {
            for (int j=1; j<matrix[0].length; ++j) {
                sumMatrix[i][j] = sumMatrix[i-1][j] + sumMatrix[i][j-1] - sumMatrix[i-1][j-1] + matrix[i][j];
            }
        }

        updateMatrix = new int[matrix.length][matrix[0].length];
    }

    public void update(int row, int col, int val) {
        updateMatrix[row][col] = val;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int left = 0;
        int upper = 0;
        int inter = 0;
        if (row1 > 0) {
            upper = sumMatrix[row1-1][col2];
        }
        if (col1 > 0) {
            left = sumMatrix[row2][col1-1];
        }
        if (row1 > 0 && col1 > 0) {
            inter = sumMatrix[row1-1][col1-1];
        }
        int rawValue = sumMatrix[row2][col2]  - upper - left + inter;

        for (int i=row1; i<=row2; ++i) {
            for (int j=col1; j<=col2; ++j) {
                if (updateMatrix[i][j] != 0) {
                    rawValue += updateMatrix[i][j] - rawMatrix[i][j];
                }
            }
        }
        return rawValue;
    }

    //My Calendar II
    TreeMap<Integer, Integer> delta;

    public void constructCalendar() {
        delta = new TreeMap();
    }

    public boolean book(int start, int end) {
        delta.put(start, delta.getOrDefault(start, 0) + 1);
        delta.put(end, delta.getOrDefault(end, 0) - 1);

        int active = 0;
        for (Map.Entry<Integer, Integer> entry: delta.entrySet()) {
            int d = entry.getValue();
            active += d;
            if (active >= 3) {
                delta.put(start, delta.get(start) - 1);
                delta.put(end, delta.get(end) + 1);
                if (delta.get(start) == 0)
                    delta.remove(start);
                return false;
            }
            if (end < entry.getKey()) {
                break;
            }
        }
        return true;
    }

//    Swap Adjacent in LR String
//    Input: start = "RXXLRXRXL", end = "XRLXXRRLX"
//    Output: True
//    Explanation:
//    We can transform start to end following these steps:
//    RXXLRXRXL ->
//    XRXLRXRXL ->
//    XRLXRXRXL ->
//    XRLXXRRXL ->
//    XRLXXRRLX
    public boolean canTransform(String start, String end) {
        int n = start.length();

        // count X in start and end --> should be the same
        int count = 0;
        for (int i = 0; i < n; ++i) {
            if (start.charAt(i) == 'X') count++;
            if (end.charAt(i) == 'X') count--;
        }
        if (count != 0) return false;

        int i = 0, j = 0;
        while (i < n && j < n) {
            while (i < n && start.charAt(i) == 'X') i++;
            while (j < n && end.charAt(j) == 'X') j++;

            // i and j are the indices representing the next
            // occurrences of non-X characters
            if (i == n || j == n)
                return i == n && j == n;

            if (start.charAt(i) != end.charAt(j)) return false;
            if (start.charAt(i) == 'L' && i < j) return false;
            if (start.charAt(i) == 'R' && i > j) return false;

            i++;
            j++;
        }

        return true;
    }

    class Master {
        public int guess(String s) { return 1;}
    }

//    Guess the Word
    public void findSecretWord(String[] wordlist, Master master) {
        List<String> target = Arrays.asList(wordlist);
        for (int i=0; i<10; ++i) {
            Random rand = new Random();
            int index = rand.nextInt(target.size());
            String pivot = target.get(index);
            int sim = master.guess(pivot);
            if (sim == 6) {
                return;
            }
            List<String> tmpRes = new ArrayList();
            for (int j=0; j<target.size(); ++j) {
                if (target.get(j).equals(pivot)) {
                    continue;
                }
                int tmpSim = similarity(pivot, target.get(j));
                if (sim == tmpSim) {
                    tmpRes.add(target.get(j));
                }
            }
            target = tmpRes;
        }

    }

    public int similarity(String a, String b) {
        int sim = 0;
        for (int i=0; i<a.length(); ++i) {
            if (a.charAt(i) == b.charAt(i)) {
                sim++;
            }
        }
        return sim;
    }


    //Minimum Area Rectangle
//    Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
//    Output: 2
    class MyPoint implements Comparable<MyPoint> {
        public int x;
        public int y;

        public MyPoint(int xx, int yy) {
            x = xx;
            y = yy;
        }

        @Override
        public int compareTo(MyPoint other) {
            if (x != other.x) {
                return x - other.x;
            }
            return y - other.y;
        }

        @Override
        public int hashCode(){
            return 31 * x + y;
        }

        @Override
        public boolean equals(Object other){
            if(this == other) return true;
            MyPoint tmp = (MyPoint) other;
            return tmp.x == x && tmp.y == y;
        }
    }

    public int minAreaRect(int[][] points) {
        if (points.length == 0 || points[0].length == 0) {
            return 0;
        }
        Set<MyPoint> tracker = new HashSet();
        for (int[] point : points) {
            tracker.add(new MyPoint(point[0], point[1]));
        }
        int res = Integer.MAX_VALUE;
        for (int i=0; i<points.length; ++i) {
            for (int j=i+1; j<points.length; ++j) {
                int x1 = points[i][0];
                int y1 = points[i][1];
                int x2 = points[j][0];
                int y2 = points[j][1];
                if (x1 == x2 || y1 == y2) {
                    continue;
                }
                Point p3 = new Point(x1, y2);
                Point p4 = new Point(x2, y1);
                if (tracker.contains(p3) && tracker.contains(p4)) {
                    res = Math.min(res, Math.abs((x2-x1) * (y2-y1)));
                }
            }
        }
        if (res == Integer.MAX_VALUE) {
            res = 0;
        }
        return res;
    }


    //sequence-reconstruction
    Map<Integer, Integer> indegree = new HashMap();

    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        if (org.length == 0 || seqs.size() == 0) {
            return false;
        }
        Map<Integer, Set<Integer>> graph = buildMap(seqs);
        return traverseGraph(graph, org, org[0]);
    }

    public Map<Integer, Set<Integer>> buildMap(List<List<Integer>> seqs) {
        Map<Integer, Set<Integer>> graph = new HashMap();
        for (List<Integer> ls : seqs) {
            if (ls.size() == 1) {
                indegree.putIfAbsent(ls.get(0), 0);
            } else {
                for (int i = 0; i < ls.size() - 1; ++i) {
                    int a = ls.get(i);
                    int b = ls.get(i + 1);
                    graph.putIfAbsent(a, new HashSet<Integer>());
                    indegree.putIfAbsent(a, 0);
                    indegree.putIfAbsent(b, 0);
                    if (!graph.containsKey(a) || !graph.get(a).contains(b)) {
                        indegree.put(b, indegree.get(b) + 1);
                    }
                    graph.get(a).add(b);
                }
            }
        }
        return graph;
    }

    public boolean traverseGraph(Map<Integer, Set<Integer>>graph, int[] org, int start) {
        Queue<Integer> queue = new LinkedList<Integer>();
        if (!indegree.containsKey(start)) {
            return false;
        }
        if (indegree.get(start) > 0) {
            return false;
        }
        queue.offer(start);
        int index = 0;
        while(!queue.isEmpty()) {
            if (queue.size() > 1) {
                return false;
            }
            int curNode = queue.poll();
            if (curNode != org[index]) {
                return false;
            }
            if (index == org.length - 1 && curNode == org[index]) {
                return true;
            }
            if (graph.containsKey(curNode)) {
                Set<Integer> neighbours = graph.get(curNode);
                for (int neighbour : neighbours) {
                    indegree.put(neighbour, indegree.get(neighbour) - 1);
                    if (indegree.get(neighbour) == 0) {
                        queue.add(neighbour);
                    }
                }
                ++index;
            }
        }
        return index == org.length - 1;
    }

    public static void main(String[] args) {
        GoogleInterview g = new GoogleInterview();
//        System.out.println(g.lengthOfLongestSubstringTwoDistinct("abaccc"));
//        List<String> res = g.findMissingRanges(new int[] {0,1,3,50,75}, 0, 99);
//        List<String> res = g.findMissingRanges(new int[] {-1}, -2, -1);
//        System.out.println(res);
//        int [][] input = {{13, 15}, {1, 13}};
//        System.out.println(g.minMeetingRooms(input));
//        System.out.println(g.backspaceCompare("ab##", "c#d#"));
//        int q[] = {10,20,5};
//        int w[] = {70,50,30};
//        g.mincostToHireWorkers1(q, w, 2);
//          List<String> wl = Arrays.asList("hot", "dog");
//          g.ladderLength("hot", "dog", wl);
//        int stones[][] = {{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}};
//        g.removeStones(stones);
//        String strs[] = {"abat","baba","atan","atal"};
//        g.wordSquares(strs);
//        int val[][] = {{1,2},{2,3},{3,4},{4,1},{1,5}};
//        g.findRedundantDirectedConnection(val);
//        g.coinChange(new int[]{1,2,5}, 11);

//        LRUCache c = new LRUCache(2);
//        c.put(2, 6);
//        c.put(2, 2);
//        c.get(1);
//        c.put(1, 5);
////        c.get(2);
//        c.put(1, 2);
//        c.get(1);
//        c.get(2);

        int[] a = {1};
        List<List<Integer>> b = new ArrayList<>();
        b.add(Arrays.asList(1));
        b.add(Arrays.asList(1));
        b.add(Arrays.asList(1));
        g.sequenceReconstruction(a, b);
    }
}
