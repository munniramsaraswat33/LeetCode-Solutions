class Solution {
    public int firstMissingPositive(int[] nums) {
        Arrays.sort(nums);
        int key = 1;
        for(int i=0; i<nums.length; i++){
            if(nums[i] < 1 || nums[i] < key){
                continue;
            }
            else if(nums[i] != key){
                return key;
            }
            else{
                key++;
            }
        }
        return key;
    }
}