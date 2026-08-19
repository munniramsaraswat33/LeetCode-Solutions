class Solution {
    public int largestInteger(int[] nums, int k) {
        int ans = -1;
        for(int num : nums){
            int count = 0;
            for(int i=0; i<=nums.length-k; i++){
                boolean found = false;
                for(int j=i; j<i+k; j++){
                    if(nums[j] == num){
                        found = true;
                        break;
                    }
                }
                if(found){
                    count++;
                }
            }
            if(count == 1){
                ans = Math.max(ans, num);
            }
        }
        return ans;
    }
}