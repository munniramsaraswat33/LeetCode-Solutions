class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[1][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(i==0 || j==0) dp[0][j] = 1;
                else dp[0][j] += dp[0][j-1];
            }
        }
        return dp[0][n-1];
    }
}