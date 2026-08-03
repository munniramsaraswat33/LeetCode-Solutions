class Solution {
    public int longestSubarray(int[] nums) {
        int n = nums.length;
        int curr = 2;
        int ans = 2;
        for(int i=2; i<n; i++){
            if(nums[i] - nums[i-1] == nums[i-2]){
                curr++;
            }
            else{
                curr = 2;
            }
            ans = Math.max(ans, curr);
        }
        return ans;
    }
}