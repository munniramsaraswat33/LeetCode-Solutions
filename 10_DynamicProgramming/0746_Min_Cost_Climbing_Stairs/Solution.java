class Solution {
    public int mincost(int n, int[] cost, int[] dp){
        if(n==1 || n==0) return cost[n];
        if(dp[n]!=-1) return dp[n];
        return dp[n] = cost[n] + Math.min(mincost(n-1, cost, dp), mincost(n-2, cost, dp)); 
    }
    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length];
        Arrays.fill(dp, -1);
        return Math.min(mincost(cost.length-1, cost, dp), mincost(cost.length-2, cost, dp));
    }
}