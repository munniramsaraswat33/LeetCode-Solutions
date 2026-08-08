class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            int complement = target-nums[i];

            Integer index = hm.get(complement);
            if (index != null) {
                return new int[]{index, i};
            }

            hm.put(nums[i], i);
        }
        return new int[]{};
    }
}