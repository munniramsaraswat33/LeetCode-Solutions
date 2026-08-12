class Solution {
    public int largestAltitude(int[] gain) {
        int n = gain.length;
        int nums[] = new int[n+1];
        nums[0] = 0;
        int max = nums[0];
        for(int i=1; i<=n; i++){
            nums[i] = nums[i-1] + gain[i-1];
            max = Math.max(max, nums[i]);
        }
        return max;
    }
}