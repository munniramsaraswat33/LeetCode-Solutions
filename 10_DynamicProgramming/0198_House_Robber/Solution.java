class Solution {
    public int rob(int[] nums) {
        int m = nums.length;
        if(m==1) return nums[0];
        int[] dp = new int[m+1];
        dp[0] = 0;
        dp[1] = nums[0];
        for(int i=2; i<=m; i++){
            dp[i] = Math.max(dp[i-1], nums[i-1]+dp[i-2]);
        }
        return dp[m];
    }
}