class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> Mlist = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        for(int i=0; i<n-2; i++){
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int target = -nums[i];
            int st = i+1; int end = n-1;
            while(st<end){
                int sum = nums[st] + nums[end];
                if(sum<target){
                    st++;
                }else if(sum>target){
                    end--;
                }else{
                    Mlist.add(Arrays.asList(nums[i], nums[st], nums[end]));
                    st++;
                    end--;
                    while(st<end && nums[st]==nums[st-1]){
                        st++;
                    }
                    while(st<end && nums[end]==nums[end+1]){
                        end--;
                    }
                }
            }
        }
        return Mlist;
    }
}