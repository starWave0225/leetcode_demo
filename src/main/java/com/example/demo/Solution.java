package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

public class Solution {

    Logger logger = Logger.getLogger(getClass().getName());
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public void log(String sth) {
        logger.info(sth);
    }

    // 1. Two Sum
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        int len = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            int other = target - nums[i];
            if (map.containsKey(other)) {
                res[0] = i;
                res[1] = map.get(other);
                return res;
            } else {
                map.put(nums[i], i);
            }
        }
        return res;
    }

    // 2. Add Two Numbers
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head1 = l1;
        ListNode head2 = l2;
        ListNode res = new ListNode();
        ListNode head = res;
        int p = 0;
        while (head1 != null || head2 != null || p != 0) {
            int temp = p;
            if (head1 != null) {
                temp += head1.val;
                head1 = head1.next;
            }
            if (head2 != null) {
                temp += head2.val;
                head2 = head2.next;
            }
            p = temp / 10;
            head.next = new ListNode(temp % 10);
            head = head.next;
        }
        return res.next;
    }

    // 129. Sum Root to Leaf Numbers
    private int sumNumbersDfs(TreeNode node, int sum) {
        if (node == null) {
            return 0;
        }
        sum *= 10;
        sum += node.val;
        if (node.left == null && node.right == null) {
            return sum;
        }
        return sumNumbersDfs(node.left, sum) + sumNumbersDfs(node.right, sum);
    }

    public int sumNumbers(TreeNode root) {
        return sumNumbersDfs(root, 0);
    }

    // 3. Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        int head = 0;
        int res = 0;
        for (int sail = 0; sail < s.length(); sail++) {
            char cur = s.charAt(sail);
            while (set.contains(cur)) {
                set.remove(s.charAt(head));
                head++;
            }
            set.add(cur);
            res = Math.max(res, sail - head + 1);
        }
        return res;
    }

    // 623. Add One Row to Tree
    private void addOneRowDfs(TreeNode root, int depth, int aim, int val) {

        if (root == null) {
            return;
        }
        if (depth == aim - 1) {
            TreeNode leftNode = root.left;
            TreeNode rightNode = root.right;
            root.left = new TreeNode(val);
            root.left.left = leftNode;
            root.right = new TreeNode(val);
            root.right.right = rightNode;
        } else {
            addOneRowDfs(root.left, depth + 1, aim, val);
            addOneRowDfs(root.right, depth + 1, aim, val);
        }
    }

    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) {
            TreeNode node = new TreeNode(val);
            node.left = root;
            return node;
        }
        addOneRowDfs(root, 1, depth, val);
        return root;
    }

    // 1287. Element Appearing More Than 25% In Sorted Array
    public int findSpecialInteger(int[] arr) {
        int len = arr.length;
        int range = len / 4;
        for (int i = 0; i + range < len; i++) {
            int right = i + range;
            if (arr[i] == arr[right]) {
                return arr[i];
            }
        }
        return -1;
    }

    // 988. Smallest String Starting From Leaf
    public void smallestFromLeafDfs(TreeNode node, StringBuilder res, StringBuilder path) {
        if (node == null) {
            return;
        }
        char temp = (char) ('a' + node.val);
        path.insert(0, temp);
        if (node.left == null && node.right == null) {
            String cur = path.toString();
            if (res.length() == 0 || cur.compareTo(res.toString()) < 0) {
                res.setLength(0);
                res.append(cur);
            }
        }
        smallestFromLeafDfs(node.left, res, path);
        smallestFromLeafDfs(node.right, res, path);
        path.deleteCharAt(0);
    }

    public String smallestFromLeaf(TreeNode root) {
        StringBuilder res = new StringBuilder();
        StringBuilder path = new StringBuilder();
        smallestFromLeafDfs(root, res, path);
        return res.toString();
    }

    // 992. Subarrays with K Different Integers
    public int subarraysWithKDistinct(int[] nums, int k) {
        int resMaxK = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int l = 0;
        int r = 0;
        while (r < nums.length) {
            int cur = nums[r];
            map.put(cur, map.getOrDefault(cur, 0) + 1);
            while (map.keySet().size() > k) {
                int temp = nums[l];
                l++;
                map.put(temp, map.get(temp) - 1);
                if (map.get(temp) == 0) {
                    map.remove(temp);
                }
            }
            resMaxK += r - l + 1;
            r++;
        }
        int resMaxKs1 = 0;
        map = new HashMap<>();
        l = 0;
        r = 0;
        while (r < nums.length) {
            int cur = nums[r];
            map.put(cur, map.getOrDefault(cur, 0) + 1);
            while (map.keySet().size() >= k) {
                int temp = nums[l];
                l++;
                map.put(temp, map.get(temp) - 1);
                if (map.get(temp) == 0) {
                    map.remove(temp);
                }
            }
            resMaxKs1 += r - l + 1;
            r++;
        }
        return resMaxK - resMaxKs1;
    }

    // 200. Number of Islands
    public void numIslandsDfs(char[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return;
        }
        if (grid[x][y] == '1') {
            grid[x][y] = '0';
            numIslandsDfs(grid, x - 1, y);
            numIslandsDfs(grid, x + 1, y);
            numIslandsDfs(grid, x, y - 1);
            numIslandsDfs(grid, x, y + 1);
        }
    }

    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    numIslandsDfs(grid, i, j);
                }
            }
        }
        return res;
    }

    // 872. Leaf-Similar Trees
    public boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    public void leafSimilarDfs(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            list.add(node.val);
        }
        leafSimilarDfs(node.left, list);
        leafSimilarDfs(node.right, list);
    }

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leaf1 = new ArrayList<>();
        List<Integer> leaf2 = new ArrayList<>();
        leafSimilarDfs(root1, leaf1);
        leafSimilarDfs(root2, leaf2);
        return leaf1.equals(leaf2);
    }

    // 463. Island Perimeter
    public int islandPerimeter(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                if (i == 0 || grid[i - 1][j] == 0) {
                    res++;
                }
                if (i == grid.length - 1 || grid[i + 1][j] == 0) {
                    res++;
                }
                if (j == 0 || grid[i][j - 1] == 0) {
                    res++;
                }
                if (j == grid[0].length - 1 || grid[i][j + 1] == 0) {
                    res++;
                }
            }
        }
        return res;
    }

    // 1992. Find All Groups of Farmland
    private int[] findFarmlandDfs(int[][] land, Set<Integer> set, int i, int j) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{i, j});
        set.add(i * 1000 + j);
        int minX = i, minY = j, maxX = i, maxY = j;
        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int cx = current[0], cy = current[1];
            for (int[] dir : directions) {
                int nx = cx + dir[0], ny = cy + dir[1];
                if (nx >= 0 && nx < land.length && ny >= 0 && ny < land[0].length
                        && land[nx][ny] == 1 && !set.contains(nx * 1000 + ny)) {
                    set.add(nx * 1000 + ny);
                    stack.push(new int[]{nx, ny});
                    minX = Math.min(minX, nx);
                    minY = Math.min(minY, ny);
                    maxX = Math.max(maxX, nx);
                    maxY = Math.max(maxY, ny);
                }
            }
        }
        return new int[]{minX, minY, maxX, maxY};
    }

    public int[][] findFarmland(int[][] land) {
        int rows = land.length;
        int cols = land[0].length;
        Set<Integer> visited = new HashSet<>();
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int temp = i * 1000 + j;
                if (land[i][j] == 1 && !visited.contains(temp)) {
                    int[] bounds = findFarmlandDfs(land, visited, i, j);
                    result.add(bounds);
                }
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    // 131. Palindrome Partitioning
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }
        return true;
    }

    private void partitionFindPalindrome(String s, int left, List<List<String>> res, List<String> curList) {
        if (left >= s.length()) {
            res.add(new ArrayList<>(curList));
            return;
        }
        for (int i = left; i < s.length(); i++) {
            String cur = s.substring(left, i + 1);
            if (isPalindrome(cur)) {
                curList.add(cur);
                partitionFindPalindrome(s, i + 1, res, curList);
                curList.remove(curList.size() - 1);
            }
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        partitionFindPalindrome(s, 0, res, new ArrayList<>());
        return res;
    }

    // 5. Longest Palindromic Substring
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int max = 0;
        String res = "";
        boolean[][] canCut = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j <= 1 || canCut[j + 1][i - 1])) {
                    canCut[j][i] = true;
                    if (i - j + 1 > max) {
                        max = i - j + 1;
                        res = s.substring(j, i + 1);
                    }
                }
            }
        }
        return res;
    }

    // 132. Palindrome Partitioning II
    public int minCut(String s) {
        int len = s.length();
        int[] cuts = new int[len];
        boolean[][] canCut = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            int minCut = i;
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j <= 1 || canCut[j + 1][i - 1])) {
                    canCut[j][i] = true;
                    if (j == 0) {
                        minCut = 0;
                        continue;
                    }
                    minCut = Math.min(minCut, cuts[j - 1] + 1);
                }
            }
            cuts[i] = minCut;
        }
        return cuts[len - 1];
    }

    // 1404. Number of Steps to Reduce a Number in Binary Representation to One
    public int numSteps(String s) {
        int res = 0;
        int carry = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            char ch = s.charAt(i);
            if (ch - '0' + carry == 1) {
                carry = 1;
                res += 2;
            } else {
                res += 2;
            }
        }
        return res + carry;
    }

    // 1894. Find the Student that Will Replace the Chalk
    public int chalkReplacer(int[] chalk, int k) {
        int n = chalk.length;
        int[] newChalk = new int[n];
        newChalk[0] = chalk[0];
        for (int i = 0; i < n - 1; i++) {
            if (newChalk[i] > k) {
                return i;
            }
            newChalk[i + 1] = chalk[i + 1] + newChalk[i];
        }
        if (newChalk[n - 1] > k) {
            return n - 1;
        }
        k %= newChalk[n - 1];
        for (int i = 0; i < n; i++) {
            if (newChalk[i] > k) {
                return i;
            }
        }
        return 0;
    }

    // 1391. Check if There is a Valid Path in a Grid
    public boolean hasValidPath(int[][] grid) {
        int[][][] dirs = {
            {{0, -1}, {0, 1}},
            {{-1, 0}, {1, 0}},
            {{0, -1}, {1, 0}},
            {{0, 1}, {1, 0}},
            {{0, -1}, {-1, 0}},
            {{0, 1}, {-1, 0}}
        };
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int i = cur[0], j = cur[1];
            for (int[] dir : dirs[grid[i][j] - 1]) {
                int newi = i + dir[0], newj = j + dir[1];
                if (newi >= 0 && newj >= 0 && newi < m && newj < n && !visited[newi][newj]) {
                    for (int[] dir2 : dirs[grid[newi][newj] - 1]) {
                        if (newi + dir2[0] == i && newj + dir2[1] == j) {
                            visited[newi][newj] = true;
                            queue.offer(new int[]{newi, newj});
                        }
                    }
                }
            }
        }
        return visited[m - 1][n - 1];
    }

    // 26. Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            while (i != nums.length - 1 && nums[i + 1] == nums[i]) {
                i++;
            }
            nums[k] = nums[i];
            k++;
        }
        return k;
    }

    // 1945. Sum of Digits of String After Convert
    public int getLucky(String s, int k) {
        int res = 0;
        for (char ch : s.toCharArray()) {
            int temp = ch - 'a' + 1;
            if (temp >= 10) {
                res += temp / 10;
                res += temp % 10;
            } else {
                res += temp;
            }
        }
        while (k != 1) {
            k--;
            int tempRes = res;
            res = 0;
            while (tempRes != 0) {
                res += tempRes % 10;
                tempRes /= 10;
            }
        }
        return res;
    }

    // 2750. Ways to Split Array Into Good Subarrays
    public int numberOfGoodSubarraySplits(int[] nums) {
        int n = nums.length;
        long res = 1;
        int zero = 0, one = 0;
        int i = 0;
        while (i < n) {
            if (nums[i] == 0) {
                zero++;
            } else {
                one++;
                if (one > 1 && zero >= 1) {
                    res *= zero + 1;
                    res %= 1e9 + 7;
                }
                zero = 0;
            }
            i++;
        }
        if (one == 0) {
            return 0;
        }
        return (int) (res);
    }

    // 2708. Maximum Strength of a Group
    public long maxStrength(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        long res = 1;
        boolean flag = false;
        int minus = 0;
        int notZero = 0;
        int min = -10;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                flag = true;
                res *= nums[i];
                notZero++;
            }
            if (nums[i] < 0) {
                minus++;
                if (min < nums[i]) {
                    min = nums[i];
                }
            }
        }
        if (!flag) {
            return 0;
        }
        if ((minus & 1) == 1) {
            if (minus == 1 && notZero == 1) {
                return 0;
            }
            res /= min;
        }
        return res;
    }

    // 874. Walking Robot Simulation
    public int robotSim(int[] commands, int[][] obstacles) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int res = 0;
        int x = 0, y = 0, dir = 0;
        for (int command : commands) {
            if (command == -1) {
                dir++;
                dir %= 4;
            } else if (command == -2) {
                dir += 3;
                dir %= 4;
            } else {
                for (int i = 0; i < command; i++) {
                    int nx = x + directions[dir][0];
                    int ny = y + directions[dir][1];
                    boolean stuck = false;
                    for (int j = 0; j < obstacles.length; j++) {
                        if (nx == obstacles[j][0] && ny == obstacles[j][1]) {
                            stuck = true;
                            break;
                        }
                    }
                    if (!stuck) {
                        x = nx;
                        y = ny;
                        res = Math.max(res, nx * nx + ny * ny);
                    }
                }
            }
        }
        return res;
    }

    // 633. Sum of Square Numbers
    public boolean judgeSquareSum(int c) {
        if (c == 0) {
            return true;
        }
        int i = 0, j = (int) Math.sqrt(c);
        if (c / j == j && c % j == 0) {
            return true;
        }
        while (i <= j) {
            long tempI = i * i;
            long tempJ = j * j;
            long temp = tempI + tempJ;
            if (temp == c) {
                return true;
            } else if (temp < c) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    // 7. Reverse Integer
    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            int temp = x % 10;
            x /= 10;
            if (Math.abs(x) == 0 && ((res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && temp > 7))
                    || (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && temp < -8)))) {
                return 0;
            }

            res *= 10;
            res += temp;
        }
        return res;
    }

    // 2028. Find Missing Observations
    public int[] missingRolls(int[] rolls, int mean, int n) {
        int m = rolls.length;
        int sum = (m + n) * mean;
        int nSum = 0;
        for (int i = 0; i < m; i++) {
            nSum += rolls[i];
        }
        int subSum = sum - nSum;
        if (subSum >= n && subSum <= 6 * n) {
            int mm = subSum / n;
            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                res[i] = mm;
            }
            int minus = subSum - mm * n;
            if (minus == 0) {
                return res;
            } else if (minus < 0) {
                int temp = 0;
                while (minus < -(mm - 1)) {
                    res[temp] = 1;
                    minus += mm - 1;
                    temp++;
                }
                res[temp] += minus;
            } else {
                int temp = 0;
                while (minus > 6 - mm) {
                    res[temp] = 6;
                    minus += mm - 6;
                    temp++;
                }
                res[temp] += minus;
            }
            return res;
        } else {
            return new int[0];
        }
    }

    // 2053. Kth Distinct String in an Array
    public String kthDistinct(String[] arr, int k) {
        int len = arr.length;
        HashSet<String> set = new HashSet<>();
        HashSet<String> dup = new HashSet<>();
        for (int i = 0; i < len; i++) {
            if (set.contains(arr[i])) {
                dup.add(arr[i]);
            } else {
                set.add(arr[i]);
            }
        }
        for (int i = 0; i < len; i++) {
            if (!dup.contains(arr[i])) {
                k--;
                if (k == 0) {
                    return arr[i];
                }
            }
        }
        return "";
    }

    // 344. Reverse String
    public void reverseString(char[] s) {
        int i = 0, j = s.length - 1;
        while (i < j) {
            char ii = s[i], jj = s[j];
            s[j] = ii;
            s[i] = jj;
            i++;
            j--;
        }
    }

    // 3217. Delete Nodes From Linked List Present in Array
    public ListNode modifiedList(int[] nums, ListNode head) {
        ListNode dammyHead = new ListNode();
        HashSet set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        ListNode p = head;
        ListNode resP = dammyHead;
        while (p != null) {
            if (!set.contains(p.val)) {
                resP.next = p;
                resP = p;
            }
            p = p.next;
        }
        resP.next = null;
        return dammyHead.next;
    }

    // 1367. Linked List in Binary Tree
    public boolean isSubPath(ListNode head, TreeNode root) {
        return isSubPathDFS(head, head, root);
    }

    public boolean isSubPathDFS(ListNode head, ListNode cur, TreeNode curTree) {
        if (cur == null) {
            return true;
        }
        if (curTree == null) {
            return false;
        }
        if (curTree.val == cur.val) {
            cur = cur.next;
        } else if (head.val == curTree.val) {
            head = head.next;
        } else {
            cur = head;
        }
        return isSubPathDFS(head, cur, curTree.left) || isSubPathDFS(head, cur, curTree.right);
    }

    // 98. Validate Binary Search Tree
    public boolean isValidBST(TreeNode root) {
        return isValidBSTDFS(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    boolean isValidBSTDFS(TreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.val <= min || root.val >= max) {
            return false;
        } else {
            return isValidBSTDFS(root.left, min, (long) root.val) && isValidBSTDFS(root.right, (long) root.val, max);
        }
    }

    // 1325. Delete Leaves With a Given Value
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);
        if (root.val == target && root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

    // 456. 132 Pattern
    public boolean find132pattern(int[] nums) {
        int len = nums.length;
        if (len < 3) {
            return false;
        }
        int min = Integer.MIN_VALUE;
        // stack记录第二大数字值的堆栈，min记录第二大数字值
        Stack<Integer> stack = new Stack<>();
        for (int i = len - 1; i >= 0; i--) {
            if (nums[i] < min) {
                return true;
            }
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                min = stack.pop();
            }
            stack.push(nums[i]);
        }
        return false;
    }

    // 725. Split Linked List in Parts
    public ListNode[] splitListToParts(ListNode head, int k) {
        int len = 0;
        ListNode p = head;
        while (p != null) {
            p = p.next;
            len++;
        }
        ListNode[] res = new ListNode[k];
        int[] resLen = new int[k];
        int avg = len / k;
        int diff = len - k * avg;
        for (int i = 0; i < k; i++) {
            if (diff > 0) {
                resLen[i] = avg + 1;
                diff--;
            } else {
                resLen[i] = avg;
            }
        }
        ListNode cur = head;
        int curI = 0;
        int curLen = 0;
        while (cur != null) {
            if (resLen[curI] == 0) {
                break;
            }
            if (curLen == 0) {
                res[curI] = cur;
            }
            curLen++;
            ListNode temp = cur.next;
            if (resLen[curI] == curLen) {
                curI++;
                curLen = 0;
                if (cur.next != null) {
                    cur.next = null;
                }
            }
            cur = temp;
        }
        return res;
    }

    // 2022. Convert 1D Array Into 2D Array
    public int[][] construct2DArray(int[] original, int m, int n) {
        int len = original.length;
        if (len != m * n) {
            return new int[0][0];
        }
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = original[i * n + j];
            }
        }
        return res;
    }

    // 2446. Determine if Two Events Have Conflict
    public boolean haveConflict(String[] event1, String[] event2) {
        int s1 = haveConflictTime(event1[0]);
        int e1 = haveConflictTime(event1[1]);
        int s2 = haveConflictTime(event2[0]);
        int e2 = haveConflictTime(event2[1]);
        return (s1 <= s2 && s2 <= e1) || (s2 <= s1 && s1 <= e2);
    }

    public int haveConflictTime(String s) {
        String num = s.substring(0, 2) + s.substring(3, 5);
        return Integer.parseInt(num);
    }

    // 2326. Spiral Matrix IV
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = -1;
            }
        }
        boolean[][] visited = new boolean[m][n];
        ListNode cur = head;
        int i = 0, j = 0;
        int newI = 0;
        int newJ = 0;
        int dir = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        visited[0][0] = true;
        res[0][0] = cur.val;
        cur = cur.next;
        while (cur != null) {
            int val = cur.val;
            cur = cur.next;
            newI = i + directions[dir][0];
            newJ = j + directions[dir][1];
            while (newI < 0 || newJ < 0 || newI >= m || newJ >= n || visited[newI][newJ]) {
                dir += 1;
                dir %= 4;
                newI = i + directions[dir][0];
                newJ = j + directions[dir][1];
            }
            i = newI;
            j = newJ;
            visited[newI][newJ] = true;
            res[newI][newJ] = val;
        }
        return res;
    }

    // 310. Minimum Height Trees
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return new ArrayList<Integer>(List.of(0));
        }
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            List l0 = map.getOrDefault(edge[0], new ArrayList<Integer>());
            l0.add(edge[1]);
            List l1 = map.getOrDefault(edge[1], new ArrayList<Integer>());
            l1.add(edge[0]);
            map.put(edge[0], l0);
            map.put(edge[1], l1);
        }
        int[] degrees = new int[n];
        Queue<Integer> leaves = new LinkedList<>();
        for (int key : map.keySet()) {
            degrees[key] = map.get(key).size();
            if (degrees[key] == 1) {
                leaves.add(key);
            }
        }
        List<Integer> roots = new ArrayList<>();
        int nodes = n;
        while (nodes > 2) {
            int leavesNum = leaves.size();
            nodes -= leavesNum;
            for (int i = 0; i < leavesNum; i++) {
                int cur = leaves.poll();
                for (int neighbor : map.get(cur)) {
                    degrees[neighbor]--;
                    if (degrees[neighbor] == 1) {
                        leaves.add(neighbor);
                    }
                }
            }
        }
        roots.addAll(leaves);
        return roots;
    }

    // 830. Positions of Large Groups
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> res = new ArrayList<>();
        int cur = 0, start = 0;
        char last = '0';
        for (int i = 0; i < s.length(); i++) {
            if (last == s.charAt(i)) {
                cur++;
            } else {
                if (cur > 2) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(start);
                    temp.add(i - 1);
                    res.add(temp);
                }
                start = i;
                cur = 1;
                last = s.charAt(i);
            }
        }
        if (cur > 2) {
            List<Integer> temp = new ArrayList<>();
            temp.add(start);
            temp.add(start + cur - 1);
            res.add(temp);
        }
        return res;
    }

    // 783. Minimum Distance Between BST Nodes
    public int minDiffInBST(TreeNode root) {
        int res = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>();
        minDiffInBSTHelper(root, list);
        Collections.sort(list);
        int[] array = list.stream().mapToInt(Integer::intValue).toArray();
        for (int i = 0; i < array.length - 1; i++) {
            res = Math.min(res, array[i + 1] - array[i]);
        }
        return res;
    }

    public void minDiffInBSTHelper(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        res.add(root.val);
        minDiffInBSTHelper(root.left, res);
        minDiffInBSTHelper(root.right, res);
    }

    // 2807. Insert Greatest Common Divisors in Linked List
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode p = head;
        ListNode pre = head;
        p = p.next;
        while (p != null) {
            int v1 = p.val;
            int preV = pre.val;
            while (v1 != 0) {
                int temp = v1;
                v1 = preV % v1;
                preV = temp;
            }
            ListNode node = new ListNode(preV);
            pre.next = node;
            node.next = p;
            pre = p;
            p = p.next;
        }
        return head;
    }

    // 1038. Binary Search Tree to Greater Sum Tree
    int bstToGstRes = 0;

    public TreeNode bstToGst(TreeNode root) {
        if (root == null) {
            return root;
        }
        bstToGst(root.right);
        bstToGstRes += root.val;
        root.val = bstToGstRes;
        bstToGst(root.left);
        return root;
    }

    // 1971. Find if Path Exists in Graph
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        boolean[] visited = new boolean[n];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            List list0 = map.getOrDefault(edge[0], new ArrayList<>());
            list0.add(edge[1]);
            map.put(edge[0], list0);
            List list1 = map.getOrDefault(edge[1], new ArrayList<>());
            list1.add(edge[0]);
            map.put(edge[1], list1);
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur == destination) {
                return true;
            }
            for (int neighbor : map.get(cur)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    // 2220. Minimum Bit Flips to Convert Number
    public int minBitFlips(int start, int goal) {
        int res = 0;
        int step = start ^ goal;
        while (step != 0) {
            res += step & 1;
            step >>= 1;
        }
        return res;
    }

    // 2816. Double a Number Represented as a Linked List
    public ListNode doubleIt(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode reverseList = doubleItReverse(head);
        ListNode p = reverseList;
        ListNode pre = null;
        int carry = 0;
        while (p != null) {
            int t = 2 * p.val + carry;
            p.val = t % 10;
            carry = t / 10;
            pre = p;
            p = p.next;
        }
        if (carry != 0) {
            ListNode extra = new ListNode(carry);
            pre.next = extra;
        }
        return doubleItReverse(reverseList);
    }

    public ListNode doubleItReverse(ListNode head) {
        ListNode pre = null;
        ListNode p = head;
        while (p != null) {
            ListNode temp = p.next;
            p.next = pre;
            pre = p;
            p = temp;
        }
        return pre;
    }

    // 2096. Step-By-Step Directions From a Binary Tree Node to Another
    public String getDirections(TreeNode root, int startValue, int destValue) {
        StringBuilder startStr = new StringBuilder();
        StringBuilder endStr = new StringBuilder();
        getDirectionsBfs(startValue, root, startStr);
        getDirectionsBfs(destValue, root, endStr);
        // 需要对root特殊处理
        int max = Math.min(startStr.length(), endStr.length());
        int i = 0;
        while (i < max && startStr.charAt(startStr.length() - 1 - i) == endStr.charAt(endStr.length() - 1 - i)) {
            i++;
        }
        StringBuilder resStr = new StringBuilder();
        for (int j = 0; j < startStr.length() - i; j++) {
            resStr.append('U');
        }
        String leftString = endStr.reverse().substring(i);
        resStr.append(leftString);
        return resStr.toString();
    }

    public boolean getDirectionsBfs(int v, TreeNode node, StringBuilder sb) {
        if (node.val == v) {
            return true;
        }
        if (node.left != null && getDirectionsBfs(v, node.left, sb)) {
            sb.append('L');
            return true;
        }
        if (node.right != null && getDirectionsBfs(v, node.right, sb)) {
            sb.append('R');
            return true;
        }
        return false;
    }

    // 1684. Count the Number of Consistent Strings
    public int countConsistentStrings(String allowed, String[] words) {
        boolean[] ch = new boolean[26];
        for (int i = 0; i < allowed.length(); i++) {
            ch[allowed.charAt(i) - 'a'] = true;
        }
        int res = 0;
        for (String str : words) {
            boolean flag = true;
            for (int i = 0; i < str.length(); i++) {
                if (ch[str.charAt(i) - 'a']) {
                    continue;
                }
                flag = false;
                break;
            }
            if (flag) {
                res++;
            }
        }
        return res;
    }

    // 2506. Count Pairs Of Similar Strings
    public int similarPairs(String[] words) {
        HashSet<Character>[] sets = new HashSet[words.length];
        for (int i = 0; i < words.length; i++) {
            sets[i] = new HashSet<>();
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                sets[i].add(word.charAt(j));
            }
        }
        int res = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (sets[i].equals(sets[j])) {
                    res++;
                }
            }
        }
        return res;
    }

    // 650. 2 Keys Keyboard, 问的是最少步数
    public int minSteps(int n) {
        if (n == 1) {
            return 0;
        }
        if (n == 2) {
            return 2;
        }

        int steps = 0;
        int factor = 2;

        while (n > 1) {
            while (n % factor == 0) {
                steps += factor;
                n /= factor;
            }
            factor++;
        }

        return steps;
    }

    // 1310. XOR Queries of a Subarray
    public int[] xorQueries(int[] arr, int[][] queries) {
        int len = arr.length;
        int[] pre = new int[len];
        pre[0] = arr[0];
        for (int i = 1; i < len; i++) {
            pre[i] = pre[i - 1] ^ arr[i];
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0], r = queries[i][1];
            if (l == 0) {
                res[i] = pre[r];
            } else {
                res[i] = pre[r] ^ pre[l - 1];
            }
        }
        return res;
    }

    // 2419. Longest Subarray With Maximum Bitwise AND
    public int longestSubarray(int[] nums) {
        int res = 1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }
        int curNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == max) {
                curNum++;
            } else {
                res = Math.max(curNum, res);
                curNum = 0;
            }
        }
        res = Math.max(curNum, res);
        return res;
    }

    // 1805. Number of Different Integers in a String
    public int numDifferentIntegers(String word) {
        int p = 0;
        int len = word.length();
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        while (p < len) {
            if (word.charAt(p) >= '0' && word.charAt(p) <= '9') {
                sb.append(word.charAt(p));
            } else if (sb.length() != 0) {
                while (sb.length() >= 2 && sb.charAt(0) == '0') {
                    sb.delete(0, 1);
                }
                set.add(sb.toString());
                sb = new StringBuilder();
            }
            p++;
        }
        if (sb.length() != 0) {
            while (sb.length() >= 2 && sb.charAt(0) == '0') {
                sb.delete(0, 1);
            }
            set.add(sb.toString());
        }
        return set.size();
    }

    // 19. Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyhead = new ListNode(), p1 = dummyhead, p2 = head;
        dummyhead.next = head;
        int i = 0;
        while (i < n) {
            p2 = p2.next;
            i++;
        }
        while (p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1.next == null) {
            return dummyhead;
        }
        p1.next = p1.next.next;
        return dummyhead.next;
    }

    // 24. Swap Nodes in Pairs
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummyhead = new ListNode();
        dummyhead.next = head;
        ListNode p1 = head, p2 = head.next, pre = dummyhead;
        while (p1 != null && p2 != null) {
            ListNode next = p2.next;
            pre.next = p2;
            p2.next = p1;
            p1.next = next;
            pre = p1;
            p1 = next;
            if (p1 == null) {
                break;
            }
            p2 = next.next;
        }
        return dummyhead.next;
    }

    // 1721. Swapping Nodes in a Linked List
    public ListNode swapNodes(ListNode head, int k) {
        ListNode dummyhead = new ListNode();
        dummyhead.next = head;
        ListNode p1 = dummyhead, pre1 = dummyhead;
        for (int i = 0; i < k; i++) {
            pre1 = p1;
            p1 = p1.next;
        }
        ListNode p2 = dummyhead, pre2 = p2, cur = p1;
        while (cur != null) {
            cur = cur.next;
            pre2 = p2;
            p2 = p2.next;
        }
        pre2.next = p1;
        pre1.next = p2;
        ListNode temp = p1.next;
        p1.next = p2.next;
        p2.next = temp;
        return dummyhead.next;
    }

    // 1371. Find the Longest Substring Containing Vowels in Even Counts
    public int findTheLongestSubstring(String s) {
        int mask = 0;
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case 'a':
                    mask ^= 1;
                    break;
                case 'e':
                    mask ^= 2;
                    break;
                case 'i':
                    mask ^= 4;
                    break;
                case 'o':
                    mask ^= 8;
                    break;
                case 'u':
                    mask ^= 16;
                    break;
                default:
                    break;
            }
            if (map.containsKey(mask)) {
                res = Math.max(res, i - map.get(mask));
            } else {
                map.put(mask, i);
            }
        }
        return res;
    }

    // 29. Divide Two Integers
    public int divide(int dividend, int divisor) {
        long sign = 1;
        if (divisor == 1) {
            return dividend;
        }
        if ((dividend < 0 && divisor >= 0) || (dividend >= 0 && divisor < 0)) {
            sign = -1;
        }
        long cur = 0;
        long dividendLong = Math.abs(dividend);
        long divisorLong = Math.abs(divisor);
        for (int i = 30; i >= 0; i--) {
            if (dividendLong >= (divisorLong << i)) {
                cur += (1 << i);
                dividendLong -= (divisorLong << i);
            }
        }
        return (int) (sign * cur);
    }

    // 67. Add Binary
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int al = a.length() - 1, bl = b.length() - 1;
        int carry = 0;
        while (al >= 0 || bl >= 0 || carry != 0) {
            if (al >= 0) {
                carry += a.charAt(al) - '0';
                al--;
            }
            if (bl >= 0) {
                carry += b.charAt(bl) - '0';
                bl--;
            }
            if (carry % 2 == 1) {
                sb.append('1');
            } else {
                sb.append('0');
            }
            carry /= 2;
        }
        sb.reverse();
        return sb.toString();
    }

    // 1334. Find the City With the Smallest Number of Neighbors at a Threshold
    // Distance
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], 10001);
        }
        for (int[] edeg : edges) {
            int from = edeg[0];
            int to = edeg[1];
            int distance = edeg[2];
            dist[from][to] = distance;
            dist[to][from] = distance;
        }
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        int res = 0;
        int minCity = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int num = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) {
                    num++;
                }
            }
            if (minCity >= num) {
                minCity = num;
                res = i;
            }
        }
        return res;
    }

    // 350. Intersection of Two Arrays II
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int num : nums2) {
            if (map.containsKey(num)) {
                int temp = map.get(num) - 1;
                if (temp == 0) {
                    map.remove(num);
                } else {
                    map.put(num, temp);
                }
                list.add(num);
            }
        }
        int[] res = new int[list.size()];
        int i = 0;
        for (int num : list) {
            res[i] = num;
            i++;
        }
        return res;
    }

    // 3099. Harshad Number
    public int sumOfTheDigitsOfHarshadNumber(int x) {
        int sum = 0;
        int temp = x;
        while (temp != 0) {
            sum += temp % 10;
            temp /= 10;
        }
        if (x % sum == 0) {
            return sum;
        }
        return -1;
    }

    // 3095. Shortest Subarray With OR at Least K I
    public int minimumSubarrayLength(int[] nums, int k) {
        int n = nums.length;
        int min = n + 1;
        for (int i = 0; i < n; i++) {
            int cur = 0;
            for (int j = i; j < n; j++) {
                cur |= nums[j];
                if (cur >= k) {
                    min = Math.min(min, j - i + 1);
                    break;
                }
            }
        }
        if (min > n) {
            return -1;
        }
        return min;
    }

    // 539. Minimum Time Difference
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        int[] times = new int[n];
        int i = 0;
        for (String time : timePoints) {
            String[] timea = time.split(":");
            int ha = Integer.parseInt(timea[0]);
            int ma = Integer.parseInt(timea[1]);
            times[i++] = ha * 60 + ma;
        }
        Arrays.sort(times);
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n - 1; j++) {
            int diff = times[j + 1] - times[j];
            res = Math.min(res, diff);
            if (res == 0) {
                return 0;
            }
        }
        int diff = 24 * 60 - times[n - 1] + times[0];
        res = Math.min(res, diff);
        return res;
    }

    // 884. Uncommon Words from Two Sentences
    public String[] uncommonFromSentences(String s1, String s2) {
        String[] ss1 = s1.split(" ");
        String[] ss2 = s2.split(" ");
        Set<String> set = new HashSet<>();
        Set<String> mulSet = new HashSet<>();
        for (int i = 0; i < ss1.length; i++) {
            if (set.contains(ss1[i])) {
                mulSet.add(ss1[i]);
            } else {
                set.add(ss1[i]);
            }
        }
        Set<String> set2 = new HashSet<>();
        for (int i = 0; i < ss2.length; i++) {
            if (set2.contains(ss2[i])) {
                mulSet.add(ss2[i]);
            } else {
                set2.add(ss2[i]);
            }
        }
        List<String> res = new ArrayList<>();
        for (String str : set) {
            if (mulSet.contains(str)) {
                continue;
            }
            if (!set2.contains(str)) {
                res.add(str);
            }
        }
        for (String str : set2) {
            if (mulSet.contains(str)) {
                continue;
            }
            if (!set.contains(str)) {
                res.add(str);
            }
        }
        return res.toArray(new String[0]);
    }

    // 36. Valid Sudoku
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            boolean[] visited = new boolean[9];
            boolean[] visited2 = new boolean[9];
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {

                    if (visited[board[i][j] - '0' - 1]) {
                        return false;
                    }
                    visited[board[i][j] - '0' - 1] = true;
                }
                if (board[j][i] != '.') {

                    if (visited2[board[j][i] - '0' - 1]) {
                        return false;
                    }
                    visited2[board[j][i] - '0' - 1] = true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boolean[] visited = new boolean[9];
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (board[i * 3 + k][j * 3 + l] == '.') {
                            continue;
                        }
                        if (visited[board[i * 3 + k][j * 3 + l] - '0' - 1]) {
                            return false;
                        }
                        visited[board[i * 3 + k][j * 3 + l] - '0' - 1] = true;
                    }
                }
            }
        }
        return true;
    }

    // 71. Simplify Path
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] files = path.split("/");
        List<String> list = Arrays.asList("", ".", "..");
        for (String file : files) {
            if (!stack.isEmpty() && file.equals("..")) {
                stack.pop();
            } else if (!list.contains(file)) {
                stack.add(file);
            }
        }
        return "/" + String.join("/", stack);
    }

    // 103. Binary Tree Zigzag Level Order Traversal
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        queue.add(root);
        if (root == null) {
            return res;
        }
        int level = 0;
        while (!queue.isEmpty()) {
            int curLength = queue.size();
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < curLength; i++) {
                TreeNode node = queue.poll();
                temp.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            if (level % 2 == 1) {
                Collections.reverse(temp);
            }
            res.add(temp);
            level++;
        }
        return res;
    }

    // 86. Partition List
    public ListNode partition(ListNode head, int x) {
        ListNode p = head;
        ListNode less = new ListNode();
        ListNode lessHead = less;
        ListNode greater = new ListNode();
        ListNode greaterHead = greater;
        greaterHead.next = greater;
        while (p != null) {
            if (p.val < x) {
                less.next = p;
                less = p;
            } else {
                greater.next = p;
                greater = p;
            }
            p = p.next;
        }
        greater.next = null;
        less.next = greaterHead.next;
        return lessHead.next;
    }

    // 96. Unique Binary Search Trees
    public int numTrees(int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 1;
        }
        int[] nums = new int[n + 1];
        nums[0] = 1;
        nums[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; ++j) {
                nums[i] += nums[j - 1] * nums[i - j];
            }
        }
        return nums[n];
    }

    // 61. Rotate List
    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null || head.next == null) {
            return head;
        }
        ListNode p = head;
        int l = 0;
        while (p != null) {
            l++;
            p = p.next;
        }
        k %= l;
        if (k == 0) {
            return head;
        }
        ListNode p1 = new ListNode();
        p1.next = head;
        ListNode p2 = new ListNode();
        l = 0;
        while (l < k) {
            p1 = p1.next;
            l++;
        }
        p2.next = head;
        while (p1.next != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
        ListNode newHead = p2.next;
        p2.next = null;
        p1.next = head;
        return newHead;
    }

    // 137. Single Number II
    public int singleNumber(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int num : map.keySet()) {
            if (map.get(num) == 1) {
                return num;
            }
        }
        return -1;
    }

    // 2583. Kth Largest Sum in a Binary Tree
    public long kthLargestLevelSum(TreeNode root, int k) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            long temp = 0;
            while (len > 0) {
                TreeNode cur = queue.poll();
                temp += cur.val;
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
                len--;

            }
            pq.add(temp);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }

    public char findKthBit(int n, int k) {
        if (n == 1) {
            return '0';
        }
        int len = (int) Math.pow(2, n) - 1;
        int half = len / 2;
        if (half == k - 1) {
            return '1';
        } else if (k <= half) {
            return findKthBit(n - 1, k);
        } else {
            char temp = findKthBit(n - 1, len - k + 1);
            if (temp == '0') {
                return '1';
            }
            return '0';
        }
        // 48. Rotate Image
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int t = 0, b = n - 1;
        while (t < b) {
            for (int i = 0; i < n; i++) {
                int temp = matrix[t][i];
                matrix[t][i] = matrix[b][i];
                matrix[b][i] = temp;
            }
            t++;
            b--;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    // 179. Largest Number
    public String largestNumber(int[] nums) {
        int n = nums.length;
        Integer[] numsInt = new Integer[n];
        boolean flag = true;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                flag = false;
            }
            numsInt[i] = nums[i]; // Auto-boxing
        }
        if (flag) {
            return "0";
        }
        Arrays.sort(numsInt, (a, b) -> {
            String stra = a.toString();
            String strb = b.toString();
            return (stra + strb).compareTo(strb + stra);
        });
        StringBuilder res = new StringBuilder();
        for (int k = n - 1; k >= 0; k--) {
            res.append(numsInt[k]);
        }
        return res.toString();
    }

    // 2487. Remove Nodes From Linked List
    public ListNode removeNodes(ListNode head) {
        // List<Integer> list = new ArrayList<>();
        // ListNode p = head;
        // while (p != null) {
        // list.add(p.val);
        // p = p.next;
        // }
        // int max = list.get(list.size() - 1);
        // List<Integer> list2 = new ArrayList<>();
        // for (int i = list.size() - 2; i >= 0; i--) {
        // if (max <= list.get(i)) {
        // max = list.get(i);
        // } else {
        // list2.add(0, list.get(i));
        // }
        // }
        // int i = 0;
        // ListNode dummyHead = new ListNode();
        // p = dummyHead;
        // ListNode cur = head;
        // while (i < list2.size()) {
        // if(cur.val != list2.get(i)){
        // p.next = cur;
        // p = p.next;
        // } else{
        // i++;
        // }
        // cur = cur.next;
        // }
        // if(cur != null){
        // p.next = cur;
        // }
        // return dummyHead.next;
        if (head.next == null) {
            return head;
        }

        ListNode next = null;
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        head = prev;
        curr = head.next;

        while (curr != null) {
            if (curr.val < prev.val) {
                curr = curr.next;
            } else {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
        }

        head.next = null;
        return prev;
    }

    // 241. Different Ways to Add Parentheses
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '+' || ch == '-' || ch == '*') {
                List<Integer> s1 = diffWaysToCompute(expression.substring(0, i));
                List<Integer> s2 = diffWaysToCompute(expression.substring(i + 1));
                for (int a : s1) {
                    for (int b : s2) {
                        if (ch == '+') {
                            res.add(a + b);
                        } else if (ch == '-') {
                            res.add(a - b);
                        } else if (ch == '*') {
                            res.add(a * b);
                        }
                    }
                }
            }
        }
        if (res.isEmpty()) {
            res.add(Integer.parseInt(expression));
        }
        return res;
    }

    // 516. Longest Palindromic Subsequence
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        String reverseS = new StringBuilder(s).reverse().toString();
        int[] dp = new int[n + 1];
        int[] de = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == reverseS.charAt(j - 1)) {
                    dp[j] = de[j - 1] + 1;
                } else {
                    dp[j] = Math.max(dp[j - 1], de[j]);
                }
            }
            de = Arrays.copyOf(dp, n + 1);
        }
        return de[n];
    }

    // 386. Lexicographical Numbers
    public List<Integer> lexicalOrder(int n) {
        List<Integer> res = new ArrayList<>();
        int cur = 1;
        for (int i = 0; i < n; i++) {
            res.add(cur);
            if (cur * 10 <= n) {
                cur *= 10;
            } else {
                while (cur >= n || cur % 10 == 9) {
                    cur /= 10;
                }
                cur++;
            }
        }
        return res;
    }

    // 41. First Missing Positive
    public int firstMissingPositive(int[] nums) {
        int res = nums.length + 1;
        int n = res - 1;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0 && nums[i] <= n) {
                visited[nums[i] - 1] = true;
            }
        }
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                return i + 1;
            }
        }
        return res;
    }

    // 268. Missing Number
    public int missingNumber(int[] nums) {
        int res = nums.length;
        int n = res;
        boolean[] visited = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            visited[nums[i]] = true;
        }
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return res;
    }

    // 2707. Extra Characters in a String
    public int minExtraChar(String s, String[] dictionary) {
        Set<String> set = new HashSet<>();
        for (String str : dictionary) {
            set.add(str);
        }
        int[] dp = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            dp[i + 1] = s.length();
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                String sub = s.substring(j, i);
                if (set.contains(sub)) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
            dp[i] = Math.min(dp[i - 1] + 1, dp[i]);
        }
        return dp[s.length()];
    }

    // 139. Word Break
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        for (String str : wordDict) {
            set.add(str);
        }
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                String sub = s.substring(j, i);
                if (dp[j] && set.contains(sub)) {
                    dp[i] = dp[j];
                }
            }
        }
        return dp[s.length()];
    }

    // 140. Word Break II
    public List<String> wordBreak2(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        for (String str : wordDict) {
            set.add(str);
        }
        List<String> res = new ArrayList<>();
        wordBreakBfs(0, s, "", set, res);
        return res;
    }

    void wordBreakBfs(int index, String s, String word, Set<String> set, List<String> res) {
        if (s.length() == index) {
            if (!word.isEmpty()) {
                res.add(word);
            }
            return;
        }
        for (int i = index + 1; i <= s.length(); i++) {
            String temp = s.substring(index, i);
            if (set.contains(temp)) {
                if (word.equals("")) {
                    wordBreakBfs(i, s, temp, set, res);
                } else {
                    wordBreakBfs(i, s, word + " " + temp, set, res);
                }
            }
        }
    }

    // 3043. Find the Length of the Longest Common Prefix
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        // int length1 = arr1.length;
        // Set<String> set = new HashSet<>();
        // for(int i = 0; i < length1; i++){
        // String temp = Integer.toString(arr1[i]);
        // for(int j = 0; j < temp.length(); j++){
        // String sub = temp.substring(0, j);
        // set.add(sub);
        // }
        // set.add(temp);
        // }
        // int length2 = arr2.length;
        // int res= 0;
        // for(int i = 0; i < length2; i++){
        // String temp = Integer.toString(arr2[i]);
        // if(set.contains(temp)){
        // res = Math.max(res, temp.length());
        // continue;
        // }
        // for(int j = temp.length()-1; j > 0; j--){
        // String sub = temp.substring(0, j);
        // if(set.contains(sub)){
        // res = Math.max(res, j);
        // break;
        // }
        // }
        // }
        // return res;
        Trie root = new Trie(10);
        for (int val : arr1) {
            Trie curr = root;
            for (char ch : String.valueOf(val).toCharArray()) {
                if (curr.children[ch - '0'] == null) {
                    curr.children[ch - '0'] = new Trie();
                }
                curr = curr.children[ch - '0'];
            }
        }

        int currCount = 0;
        int ans = 0;
        for (int val : arr2) {
            Trie curr = root;
            for (char ch : String.valueOf(val).toCharArray()) {
                if (curr.children[ch - '0'] == null) {
                    break;
                }
                currCount++;
                curr = curr.children[ch - '0'];
            }
            ans = Math.max(ans, currCount);
            currCount = 0;
        }
        return ans;
    }

    // 1497. Check If Array Pairs Are Divisible by k
    public boolean canArrange(int[] arr, int k) {
        int len = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            arr[i] %= k;
            if (arr[i] < 0) {
                arr[i] += k;
            }
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        for (int i : map.keySet()) {
            if (i == 0) {
                if (i % 2 == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            int iv = map.get(i);
            int riv = map.getOrDefault(k - i, 0);
            if (i == k - i) {
                if (iv % 2 == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (iv != riv) {
                return false;
            }
        }
        return true;
    }

    // 1331. Rank Transform of an Array
    public int[] arrayRankTransform(int[] arr) {
        int[] newArr = arr.clone();
        Arrays.sort(newArr);
        int count = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            while (i < arr.length - 1 && newArr[i] == newArr[i + 1]) {
                i++;
            }
            if (i == arr.length - 1) {
                map.put(newArr[i], count);
            } else {
                map.put(newArr[i], count);
                count++;
            }
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = map.get(arr[i]);
        }
        return res;
    }

    // 1590. Make Sum Divisible by P
    public int minSubarray(int[] nums, int p) {
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        // 计算整个数组模p的和
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i] % p;
            nums[i] = temp;
            sum += temp;
            sum %= p;
        }
        System.out.println("数组对p取模之后的和: " + sum);
        System.out.println("数组取模后的结果: " + Arrays.toString(nums));

        // 如果sum为0，表示数组本身已经满足条件
        if (sum == 0) {
            return 0;
        }

        // 先将前缀和0对应的下标放入map
        map.put(0, -1);

        int presum = 0; // 前缀和
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            presum += nums[i];
            presum %= p;
            // 保证target为正
            int target = (presum - sum + p) % p; // 寻找目标值

            System.out.println("当前下标: " + i + ", 前缀和: " + presum + ", 目标值: " + target);

            if (map.containsKey(target)) {
                res = Math.min(res, i - map.get(target));
                System.out.println("找到符合条件的子数组，当前最小长度: " + res);
            }
            map.put(presum, i); // 更新哈希表
            System.out.println("当前哈希表内容: " + map);
        }

        // 返回结果
        return res == Integer.MAX_VALUE || res == nums.length ? -1 : res;
    }

    // 2491. Divide Players Into Teams of Equal Skill
    public long dividePlayers(int[] skill) {
        long len = skill.length;
        long sum = 0;
        for (int num : skill) {
            sum += num;
        }
        long subsum = sum / (len / 2);
        boolean flag = true;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : skill) {
            if (num > subsum) {
                return -1;
            }
            num %= subsum;
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        long res = 0;
        List<Integer> visited = new ArrayList<>();
        for (int key : map.keySet()) {
            if (visited.contains(key)) {
                continue;
            }
            int value = map.get(key);
            if (key * 2 == subsum) {
                if (value % 2 != 0) {
                    flag = false;
                    break;
                } else {
                    res += (long) value * (long) key * (long) key / 2;
                    continue;
                }
            }
            int left = (int) subsum - key;
            if (map.getOrDefault(left, 0) == value) {
                res += (long) value * (long) left * (long) key;
                visited.add(left);
            } else {
                flag = false;
                break;
            }
        }
        if (!flag) {
            return -1;
        }
        return res;
    }

    // 567. Permutation in String
    public boolean checkInclusion(String s1, String s2) {
        int l1 = s1.length(), l2 = s2.length();
        int[] array1 = new int[26];
        for (char ch : s1.toCharArray()) {
            array1[ch - 'a']++;
        }
        int[] array2 = new int[26];
        for (int i = 0; i < l2; i++) {
            array2[s2.charAt(i) - 'a']++;
            if (i >= l1) {
                array2[s2.charAt(i - l1) - 'a']--;
            }
            if (Arrays.equals(array1, array2)) {
                return true;
            }
        }
        return false;
    }

    // 1813. Sentence Similarity III
    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        String[] s1 = sentence1.split(" ");
        String[] s2 = sentence2.split(" ");
        if (s1.length < s2.length) {
            String[] temp = s1;
            s1 = s2;
            s2 = temp;
        }
        int start = 0, end = 0;
        int n1 = s1.length, n2 = s2.length;
        while (start < n2 && s1[start].equals(s2[start])) {
            start++;
        }
        while (end < n2 && s1[n1 - end - 1].equals(s2[n2 - end - 1])) {
            end++;
        }
        return start + end >= n2;
    }

    // 2554. Maximum Number of Integers to Choose From a Range I
    public int maxCount(int[] banned, int n, int maxSum) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : banned) {
            set.add(i);
        }
        int sum = 0;
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (!set.contains(i)) {
                sum += i;
                if (sum <= maxSum) {
                    count++;
                } else {
                    break;
                }
            }
        }
        return count;
    }

    // 2490. Circular Sentence
    public boolean isCircularSentence(String sentence) {
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            String cur = words[i];
            String next = words[1 + i == words.length ? 0 : i + 1];
            if (cur.charAt(cur.length() - 1) != next.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    // 2696. Minimum String Length After Removing Substrings
    public int minLength(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);

            if (stack.isEmpty()) {
                stack.push(cur);
                continue;
            }

            if (cur == 'B' && stack.peek() == 'A') {
                stack.pop();
            } else if (cur == 'D' && stack.peek() == 'C') {
                stack.pop();
            } else {
                stack.push(cur);
            }
        }
        return stack.size();
    }

    // 1963. Minimum Number of Swaps to Make the String Balanced
    public int minSwaps(String s) {
        int size = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '[') {
                size++;
            } else if (size > 0) {
                size--;
            }
        }
        return (size + 1) / 2;
    }

    // 921. Minimum Add to Make Parentheses Valid
    public int minAddToMakeValid(String s) {
        int open = 0, close = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
            } else {
                if (open > 0) {
                    open--;
                } else {
                    close++;
                }
            }
        }
        return open + close;
    }

    // 962. Maximum Width Ramp
    public int maxWidthRamp(int[] nums) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();

        // Step 1: Build a decreasing stack of indices
        for (int i = 0; i < n; ++i) {
            if (stack.isEmpty() || nums[stack.peek()] > nums[i]) {
                stack.push(i);
            }
        }

        int maxWidth = 0;

        // Step 2: Traverse from the end and find maximum width ramp
        for (int j = n - 1; j >= 0; --j) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[j]) {
                maxWidth = Math.max(maxWidth, j - stack.pop());
            }
        }

        return maxWidth;
    }

    // 1942. The Number of the Smallest Unoccupied Chair
    public int smallestChair(int[][] times, int targetFriend) {
        int[] targetT = times[targetFriend];
        int n = times.length;
        Arrays.sort(times, (a, b) -> {
            return a[0] - b[0];
        });
        PriorityQueue<Integer> emptySeats = new PriorityQueue<>();
        PriorityQueue<int[]> takenSeats = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        for (int i = 0; i < n; i++) {
            emptySeats.add(i);
        }
        for (int[] time : times) {
            int arrive = time[0];
            int left = time[1];

            // Free chairs that are vacated before the current arrival time
            while (!takenSeats.isEmpty() && takenSeats.peek()[0] <= arrive) {
                emptySeats.add(takenSeats.poll()[1]);
            }

            // Assign the smallest available chair
            int chair = emptySeats.poll();

            // If this is the target friend, return their chair number
            if (arrive == targetT[0]) {
                return chair;
            }

            // Mark the chair as being used until the friend's leave time
            takenSeats.add(new int[]{left, chair});
        }
        return -1;
    }

    // 2406. Divide Intervals Into Minimum Number of Groups
    public int minGroups(int[][] intervals) {
        int[] starts = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }

        // Sort start and end times
        Arrays.sort(starts);
        Arrays.sort(ends);

        int res = 0;
        int end = 0;
        for (int i = 0; i < intervals.length; i++) {
            if (starts[i] > ends[end]) {
                end++;
            } else {
                res++;
            }
        }
        return res;
    }

    // 632. Smallest Range Covering Elements from K Lists
    public int[] smallestRange(List<List<Integer>> nums) {
        // Min-Heap: stores (value, list index, element index)
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int curMax = Integer.MIN_VALUE;

        // Initialize the heap with the first element of each list
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> temp = nums.get(i);
            minHeap.offer(new int[]{temp.get(0), i, 0});
            curMax = Math.max(curMax, temp.get(0));
        }

        // Track the smallest range
        int[] smallRange = new int[]{0, Integer.MAX_VALUE};

        while (true) {
            // Get the minimum element from the heap
            int[] curr = minHeap.poll();
            int curMin = curr[0], listIdx = curr[1], elemIdx = curr[2];

            // Update the smallest range if a better one is found
            if ((curMax - curMin < smallRange[1] - smallRange[0])
                    || (curMax - curMin == smallRange[1] - smallRange[0] && curMin < smallRange[0])) {
                smallRange[0] = curMin;
                smallRange[1] = curMax;
            }

            // Move to the next element in the same list
            if (elemIdx < nums.get(listIdx).size() - 1) {
                elemIdx++;
                int nextVal = nums.get(listIdx).get(elemIdx);
                minHeap.offer(new int[]{nextVal, listIdx, elemIdx});
                curMax = Math.max(curMax, nextVal);
            } else {
                // If any list is exhausted, stop
                break;
            }
        }
        return smallRange;
    }

    // 2530. Maximal Score After Applying K Operations
    public long maxKelements(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int num : nums) {
            pq.add(num);
        }
        long res = 0;
        while (!pq.isEmpty() && k > 0) {
            int count = pq.poll();
            res += count;
            pq.add((int) Math.ceil(count / 3.0));
            k--;
        }
        return res;
    }

    // 2938. Separate Black and White Balls
    public long minimumSteps(String s) {
        char[] chs = s.toCharArray();
        int n = chs.length;
        long res = 0;
        int black = 0;
        for (int i = 0; i < n; i++) {
            if (chs[i] == '1') {
                black++;
            } else {
                res += black;
            }
        }
        return res;
    }

    // 2641. Cousins in Binary Tree II
    public TreeNode replaceValueInTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> sumQueue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            int sum = 0;
            while (len > 0) {
                len--;
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                    sum += cur.left.val;
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                    sum += cur.right.val;
                }
            }
            sumQueue.add(sum);
        }
        queue.add(root);
        int curLevel = 0;
        while (!queue.isEmpty()) {
            int len = queue.size();
            while (len > 0) {
                len--;
                TreeNode cur = queue.poll();
                int temp = 0;
                if (cur.left != null) {
                    queue.add(cur.left);
                    temp += cur.left.val;
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                    temp += cur.right.val;
                }
                if (curLevel <= 1) {
                    cur.val = 0;
                }
                if (curLevel >= 1) {
                    if (cur.left != null) {
                        cur.left.val = sumQueue.get(curLevel) - temp;
                    }
                    if (cur.right != null) {
                        cur.right.val = sumQueue.get(curLevel) - temp;
                    }
                }
            }
            curLevel++;
        }
        return root;
    }

    // 951. Flip Equivalent Binary Trees
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 == null || root2 == null) {
            return false;
        }
        if (root1.val != root2.val) {
            return false;
        }
        if ((flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right))
                || (flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left))) {
            return true;
        }
        return false;
    }
}
