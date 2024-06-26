package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

public class Solution {
    Logger logger = Logger.getLogger(getClass().getName());
    int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

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
        if (node == null)
            return 0;
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

        if (root == null)
            return;
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
        if (node == null)
            return;
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
        stack.push(new int[] { i, j });
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
                    stack.push(new int[] { nx, ny });
                    minX = Math.min(minX, nx);
                    minY = Math.min(minY, ny);
                    maxX = Math.max(maxX, nx);
                    maxY = Math.max(maxY, ny);
                }
            }
        }
        return new int[] { minX, minY, maxX, maxY };
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
    public boolean isPalindrome(String s){
        int left = 0, right = s.length() - 1;
        while(left < right){
            if(s.charAt(left) == s.charAt(right)){
                left++;right--;
            }
            else{
                return false;
            }
        }
        return true;
    }

    private void partitionFindPalindrome(String s, int left, List<List<String>> res, List<String> curList){
        if (left >= s.length()) {
            res.add(new ArrayList<>(curList));
            return;
        }
        for(int i= left; i < s.length(); i++){
            String cur  = s.substring(left, i+1);
            if(isPalindrome(cur)){
                curList.add(cur);
                partitionFindPalindrome(s, i+1, res, curList);
                curList.remove(curList.size()-1);
            }
        }
    }
    
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        partitionFindPalindrome(s, 0, res, new ArrayList<>());
        return res;
    }
}
