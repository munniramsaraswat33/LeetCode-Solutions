class Solution {
    public int waysToMakeFair(int[] nums) {
        int totalEven = 0;
        int totalOdd = 0;
        for(int i=0; i<nums.length; i++){
            if(i%2==0){
                totalEven+=nums[i];
            }
            else{
                totalOdd+=nums[i];
            }
        }
        int leftEven = 0;
        int leftOdd = 0;
        int ans = 0;
        for(int i=0; i<nums.length; i++){
            if(i%2==0){
                totalEven -= nums[i];
            }
            else{
                totalOdd -= nums[i];
            }

            int newEven = leftEven + totalOdd;
            int newOdd = leftOdd + totalEven;

            if(newEven == newOdd){
                ans++;
            }

            if(i%2==0){
                leftEven += nums[i];
            }
            else{
                leftOdd += nums[i];
            }
        }
        return ans;
    }
}