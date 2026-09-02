class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int num : nums){
            min = Math.min(num, min);
            max = Math.max(num, max);
        }
        int minIndex = 0;
        int maxIndex = 0;
        for(int i=0; i<n; i++){
            if(nums[i] == min){
                minIndex = i;
            }
            if(nums[i] == max){
                maxIndex = i;
            }
        }
        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        int fromFront = right+1;

        int fromBack = n-left;

        int fromBoth = left+1 + n-right;

        return Math.min(fromFront, Math.min(fromBack, fromBoth));
    }
}