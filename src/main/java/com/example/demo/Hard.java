package com.example.demo;

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
}
