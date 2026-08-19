class Solution {
    public List<String> letterCasePermutation(String s) {
        List<String> ans = new ArrayList<>();
        backtrack(s.toCharArray(), 0, ans);
        return ans;
    }
    public void backtrack(char[] nums, int start, List<String> ans){
        if(start == nums.length){
            ans.add(new String(nums));
            return;
        }
        if(Character.isLetter(nums[start])){
            nums[start] = Character.toLowerCase(nums[start]);
            backtrack(nums, start+1, ans);

            nums[start] = Character.toUpperCase(nums[start]);
            backtrack(nums, start+1, ans);
        }
        else{
            backtrack(nums, start+1, ans);
        }
    }
}