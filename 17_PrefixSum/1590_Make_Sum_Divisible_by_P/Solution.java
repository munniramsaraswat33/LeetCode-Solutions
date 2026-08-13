class Solution {
    public int minSubarray(int[] nums, int p) {
        long total = 0;
        for(int num : nums){
            total += num;
        }
        int target = (int)(total % p);
        if(target == 0){
            return 0;
        }
        long prefix = 0;
        int ans = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for(int i=0; i<nums.length; i++){
            prefix = (prefix + nums[i])%p;
            int current = (int)prefix;
            int required = (current - target + p)%p;
            if(map.containsKey(required)){
                int length = i-map.get(required);
                ans = Math.min(ans, length);
            }
            map.put(current, i);
        }
        return ans==nums.length ? -1 : ans;
    }
}