package com.example.demo;

import javax.print.DocFlavor.STRING;

public class Hard {
    // 1605. Find Valid Matrix Given Row and Column Sums
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        // int m = rowSum.length, n = colSum.length;
        // int[][] res = new int[m][n];
        // boolean flag = true;
        // while (flag) {
        //     flag = false;
        //     int minM = -1, minN = -1;
        //     for (int i = 0; i < m; i++) {
        //         if (rowSum[i] > 0 && (minM == -1 || rowSum[i] <= rowSum[minM])) {
        //             minM = i;
        //         }
        //     }
        //     for (int i = 0; i < n; i++) {
        //         if (colSum[i] > 0 && (minN == -1 || colSum[i] <= colSum[minN])) {
        //             minN = i;
        //         }
        //     }
        //     if(minM == -1){
        //         break;
        //     }
        //     int temp = Math.min(rowSum[minM], colSum[minN]);
        //     res[minM][minN] = temp;
        //     rowSum[minM] -= temp;
        //     colSum[minN] -= temp;
        // }
        // return res;
        public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
            int r = rowSum.length;
            int c = colSum.length;
            int[][] ans = new int [r][c];
    
            int i=0,j=0;
    
            while(i<r && j<c){
                int val = Math.min(rowSum[i], colSum[j]);
                ans[i][j] = val;
                rowSum[i] -= val;
                colSum[j] -= val;
    
                if(rowSum[i] == 0){
                    i++;
                }
                if(colSum[j] == 0){
                    j++;
                }
            }
            return ans;
        }
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
}
