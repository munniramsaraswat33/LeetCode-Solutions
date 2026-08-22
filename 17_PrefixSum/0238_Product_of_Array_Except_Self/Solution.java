class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int arr[] = new int[n];
        arr[0] = 1;
        for(int i=1; i<n; i++){
            arr[i] = arr[i-1] * nums[i-1];
        }

        int suffix = nums[n-1];
        for(int i=n-2; i>=0; i--){
            arr[i] = arr[i] * suffix;
            suffix *= nums[i];
        }

        return arr;
    }
}