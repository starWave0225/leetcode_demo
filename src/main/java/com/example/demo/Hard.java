package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import javax.print.DocFlavor.STRING;

public class Hard {
    // 1605. Find Valid Matrix Given Row and Column Sums
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        // int m = rowSum.length, n = colSum.length;
        // int[][] res = new int[m][n];
        // boolean flag = true;
        // while (flag) {
        // flag = false;
        // int minM = -1, minN = -1;
        // for (int i = 0; i < m; i++) {
        // if (rowSum[i] > 0 && (minM == -1 || rowSum[i] <= rowSum[minM])) {
        // minM = i;
        // }
        // }
        // for (int i = 0; i < n; i++) {
        // if (colSum[i] > 0 && (minN == -1 || colSum[i] <= colSum[minN])) {
        // minN = i;
        // }
        // }
        // if(minM == -1){
        // break;
        // }
        // int temp = Math.min(rowSum[minM], colSum[minN]);
        // res[minM][minN] = temp;
        // rowSum[minM] -= temp;
        // colSum[minN] -= temp;
        // }
        // return res;
        int r = rowSum.length;
        int c = colSum.length;
        int[][] ans = new int[r][c];

        int i = 0, j = 0;

        while (i < r && j < c) {
            int val = Math.min(rowSum[i], colSum[j]);
            ans[i][j] = val;
            rowSum[i] -= val;
            colSum[j] -= val;

            if (rowSum[i] == 0) {
                i++;
            }
            if (colSum[j] == 0) {
                j++;
            }
        }
        return ans;
    }

    // 564. Find the Closest Palindrome
    public String nearestPalindromic(String n) {
        int len = n.length();
        long number = Long.parseLong(n);
        if (number <= 10) {
            return Integer.toString((int) number - 1);
        } else if (number == 11) {
            return "9";
        }
        long[] candidates = new long[5];
        boolean odd = (len % 2 == 1);
        String leftHalfStr = n.substring(0, (len + 1) / 2);
        long leftHalf = Long.parseLong(leftHalfStr);
        candidates[0] = (long) Math.pow(10, len) + 1;
        candidates[1] = (long) Math.pow(10, len - 1) - 1;
        candidates[2] = nearestPalindromicGenerate(leftHalf, odd);
        candidates[3] = nearestPalindromicGenerate(leftHalf - 1, odd);
        candidates[4] = nearestPalindromicGenerate(leftHalf + 1, odd);

        long res = 0, minDiff = Long.MAX_VALUE;
        for (long candidate : candidates) {
            if (candidate != number) {
                long diff = Math.abs(candidate - number);
                if (minDiff > diff) {
                    minDiff = diff;
                    res = candidate;
                } else if (minDiff == diff) {
                    res = Math.min(res, candidate);
                }
            }
        }

        return Long.toString(res);
    }

    public long nearestPalindromicGenerate(long half, boolean odd) {
        long res = half;
        if (odd) {
            half /= 10;
        }
        while (half > 0) {
            res = res * 10 + half % 10;
            half /= 10;
        }
        return res;
    }

    // 2192. All Ancestors of a Node in a Directed Acyclic Graph
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        // Step 1: Create the adjacency list and reverse adjacency list
        List<Set<Integer>> adjList = new ArrayList<>();
        List<Set<Integer>> reverseAdjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new HashSet<>());
            reverseAdjList.add(new HashSet<>());
        }

        // Populate the adjacency lists
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adjList.get(u).add(v);
            reverseAdjList.get(v).add(u);
        }

        // Step 2: Use a topological sort
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new ArrayList<>());
        }

        // Find all nodes with no incoming edges
        Queue<Integer> queue = new LinkedList<>();
        boolean[] inDegreeZero = new boolean[n];
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            inDegree[i] = reverseAdjList.get(i).size();
            if (inDegree[i] == 0) {
                queue.add(i);
                inDegreeZero[i] = true;
            }
        }

        // Perform topological sort and accumulate ancestors
        while (!queue.isEmpty()) {
            int node = queue.poll();

            // For each node, propagate its ancestors to its descendants
            Set<Integer> ancestors = new HashSet<>(reverseAdjList.get(node));
            for (int ancestor : reverseAdjList.get(node)) {
                ancestors.addAll(result.get(ancestor));
            }
            result.get(node).addAll(ancestors);

            // Update the queue and in-degree for nodes that are dependent on the current
            // node
            for (int neighbor : adjList.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Convert result sets to lists and sort
        for (List<Integer> list : result) {
            Collections.sort(list);
        }

        return result;
    }

    // 1745. Palindrome Partitioning IV
    public boolean checkPartitioning(String s) {
        int len = s.length();
        boolean[][] canCut = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j <= 1 || canCut[j + 1][i - 1])) {
                    canCut[j][i] = true;
                }
            }
        }
        int i = 0, j = 1;
        while (i < len - 2) {
            while (i < len - 2 && !canCut[0][i]) {
                i++;
            }
            j = i + 1;
            while (j < len - 1) {
                while (j < len - 1 && !canCut[i + 1][j]) {
                    j++;
                }
                if (j < len - 1 && canCut[j + 1][len - 1]) {
                    return true;
                }
                j++;
            }
            i++;
        }
        return false;
    }

    // 4. Median of Two Sorted Arrays
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        int p1 = 0, p2 = 0;
        int m = 0, lastM = 0;
        while (p1 + p2 <= (n1 + n2) / 2) {
            lastM = m;
            if (p1 < n1 && p2 < n2) {
                if (nums1[p1] > nums2[p2]) {
                    m = nums2[p2];
                    p2++;
                } else {
                    m = nums1[p1];
                    p1++;
                }
            } else if (p1 < n1) {
                m = nums1[p1];
                p1++;
            } else {
                m = nums2[p2];
                p2++;
            }
        }
        if ((n1 + n2) % 2 == 1) {
            return m;
        } else {
            return ((double) m + (double) lastM) / 2.0;
        }
    }

    // 2732. Find a Good Subset of the Matrix
    public List<Integer> goodSubsetofBinaryMatrix(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        List<Integer> res = new ArrayList<>();
        boolean allZero;
        for (int i = 0; i < m; i++) {
            allZero = true;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                res.add(i);
                return res;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int ii = i + 1; ii < m; ii++) {
                allZero = true;
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1 && grid[ii][j] == 1) {
                        allZero = false;
                        break;
                    }
                }
                if (allZero) {
                    res.add(i);
                    res.add(ii);
                    return res;
                }
            }
        }
        return res;
    }

    // 2146. K Highest Ranked Items Within a Price Range
    public List<List<Integer>> highestRankedKItems(int[][] grid, int[] pricing, int[] start, int k) {
        int m = grid.length, n = grid[0].length;
        int price1 = pricing[0], price2 = pricing[1];
        int[][] steps = new int[m][n];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
                (b, a) -> {
                    if (steps[a[0]][a[1]] != steps[b[0]][b[1]]) {
                        return steps[a[0]][a[1]] - steps[b[0]][b[1]];
                    }
                    int v1 = grid[a[0]][a[1]];
                    int v2 = grid[b[0]][b[1]];
                    if (v1 != v2) {
                        return v1 - v2;
                    }
                    if (a[0] != b[0]) {
                        return a[0] - b[0];
                    }
                    return a[1] - b[1];
                });
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        boolean[][] visited = new boolean[m][n];
        visited[start[0]][start[1]] = true;
        int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int p = grid[cur[0]][cur[1]];
            if (p > 1 && price1 <= p && price2 >= p) {
                priorityQueue.add(cur);
                if (priorityQueue.size() > k) {
                    priorityQueue.poll();
                }
            }
            for (int dir = 0; dir < 4; dir++) {
                int ni = cur[0] + directions[dir][0];
                int nj = cur[1] + directions[dir][1];
                if (ni < 0 || nj < 0 || ni >= m || nj >= n || visited[ni][nj] || grid[ni][nj] == 0) {
                    continue;
                }
                visited[ni][nj] = true;
                queue.add(new int[] { ni, nj });
                steps[ni][nj] = steps[cur[0]][cur[1]] + 1;
            }
        }
        List<List<Integer>> res = new ArrayList<>();
        while (!priorityQueue.isEmpty() && k > 0) {
            List<Integer> temp = new ArrayList<>();
            int[] cur = priorityQueue.poll();
            temp.add(cur[0]);
            temp.add(cur[1]);
            res.addFirst(temp);
            k--;
        }
        return res;
    }

    // 2101. Detonate the Maximum Bombs
    public int maximumDetonation(int[][] bombs) {
        int len = bombs.length;
        int res = 0;
        for (int i = 0; i < len; i++) {
            res = Math.max(res, maximumDetonationDfs(bombs, i, new boolean[len]));
        }
        return res;

    }

    int maximumDetonationDfs(int[][] bombs, int x, boolean[] visited) {
        int res = 1;
        visited[x] = true;
        int[] bombx = bombs[x];
        long r = bombx[2];
        for (int i = 0; i < bombs.length; i++) {
            int[] bombi = bombs[i];
            long x0 = bombx[0] - bombi[0], y0 = bombx[1] - bombi[1];
            if (!visited[i] && x0 * x0 + y0 * y0 <= r * r) {
                res += maximumDetonationDfs(bombs, i, visited);
            }
        }
        return res;
    }

    // 25. Reverse Nodes in k-Group
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummyhead = new ListNode();
        dummyhead.next = head;
        ListNode p = dummyhead, p2 = dummyhead, next = dummyhead, lashH = head;
        int curN = 0;
        while (p != null) {
            if (curN == k) {
                next = p.next;
                p.next = null;
                ListNode temp = reverseKNodeHelper(lashH);
                p2.next = temp;
                p2 = lashH;
                lashH = next;
                p = next;
                curN = 1;
            } else {
                p = p.next;
                curN++;
            }
        }
        if (next != null) {
            p2.next = next;
        }
        return dummyhead.next;
    }

    public ListNode reverseKNodeHelper(ListNode head) {
        ListNode pre = null;
        ListNode cur = head, next = head;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // 87. Scramble String
    Map<String, Boolean> isScrambleMap = new HashMap<>();

    public boolean isScramble(String s1, String s2) {
        int n = s1.length();
        if (s1.equals(s2))
            return true;
        int[] a = new int[26], b = new int[26], c = new int[26];
        if (isScrambleMap.containsKey(s1 + s2)) {
            return isScrambleMap.get(s1 + s2);
        }
        for (int i = 1; i < n; i++) {
            int j = n - i;
            a[s1.charAt(i - 1) - 'a']++;
            b[s2.charAt(i - 1) - 'a']++;
            c[s2.charAt(j) - 'a']++;
            // check if the current substring has the same characters
            if (Arrays.equals(a, b) && isScramble(s1.substring(0, i), s2.substring(0, i))
                    && isScramble(s1.substring(i), s2.substring(i))) {
                // if the substrings are scrambled versions of each other, return true
                isScrambleMap.put(s1 + s2, true);
                return true;
            }
            if (Arrays.equals(a, c) && isScramble(s1.substring(0, i), s2.substring(j))
                    && isScramble(s1.substring(i), s2.substring(0, j))) {
                // if the substrings are scrambled versions of each other, return true
                isScrambleMap.put(s1 + s2, true);
                return true;
            }
        }
        isScrambleMap.put(s1 + s2, false);
        return false;
    }

    // 214. Shortest Palindrome
    public String shortestPalindrome(String s) {
        Utils utils = new Utils();
        String reverse = new StringBuilder(s).reverse().toString();
        int[] pi = utils.kmp(reverse, s);
        return new StringBuilder(s.substring(pi[pi.length - 1])).reverse().toString() + s;
    }

    // 440. K-th Smallest in Lexicographical Order
    // 前缀树处理
    public int findKthNumber(int n, int k) {
        long cur = 1;
        int i = 1;
        while (i < k) {
            long steps = 0;
            long first = cur;
            long next = cur + 1;
            while (first <= n) {
                steps += Math.min(n + 1, next) - first;
                first *= 10;
                next *= 10;
            }
            if (steps + i <= k) {
                cur++;
                i += steps;
            } else {
                cur *= 10;
                i++;
            }
        }
        return (int) cur;
    }

    // 2416. Sum of Prefix Scores of Strings
    public int[] sumPrefixScores(String[] words) {
        int len = words.length;
        int[] res = new int[len];
        Trie trie = new Trie();
        for (String word : words) {
            Trie p = trie;
            for (char ch : word.toCharArray()) {
                if (p.children[ch - 'a'] == null) {
                    p.children[ch - 'a'] = new Trie();
                }
                p.children[ch - 'a'].score++;
                p = p.children[ch - 'a'];
            }
        }
        for (int i = 0; i < len; i++) {
            Trie p = trie;
            int count = 0;
            for (char ch : words[i].toCharArray()) {
                count += p.children[ch - 'a'].score;
                p = p.children[ch - 'a'];
            }
            res[i] = count;
        }
        return res;
    }
}
