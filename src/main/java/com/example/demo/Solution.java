package com.example.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

public class Solution {
    Logger logger = Logger.getLogger(getClass().getName());

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
        if(depth == 1){
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
        int range = len/4;
        for(int i = 0; i + range < len; i++){
            int right = i + range;
            if(arr[i] == arr[right]){
                return arr[i];
            }
        }
        return -1;
    }
}
