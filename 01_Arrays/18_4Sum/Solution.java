class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        for(int left=0; left<n-3; left++){
            if(left>0 && nums[left]==nums[left-1]){
                continue;
            }
            for(int right=left+1; right<n-2; right++){
                if(right>left+1 && nums[right]==nums[right-1]){
                    continue;
                }
                long remaining = (long)target - nums[left] - nums[right];
                
                int i = right+1;
                int j = n-1;
                while(i<j){
                    if(nums[i]+nums[j]<remaining){
                        i++;
                    }else if(nums[i]+nums[j]>remaining){
                        j--;
                    }else{
                        result.add(Arrays.asList(nums[left], nums[right], nums[i], nums[j]));
                        i++;
                        j--;

                        while(i<j && nums[i]==nums[i-1]){
                            i++;
                        }
                        while(i<j && nums[j]==nums[j+1]){
                            j--;
                        }
                    }
                }
            }
        }
        return result;
    }
}