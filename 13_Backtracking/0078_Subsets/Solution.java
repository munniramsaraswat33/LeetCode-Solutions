class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(ans, nums, new ArrayList<>(), 0);
        return ans;
    }
    public void backtrack(List<List<Integer>> ans, int[] nums, List<Integer> subset, int start){
        ans.add(new ArrayList<>(subset));
        for(int i=start; i<nums.length; i++){
            subset.add(nums[i]);
            backtrack(ans, nums, subset, i+1);
            subset.remove(subset.size()-1);
        }
    }
}