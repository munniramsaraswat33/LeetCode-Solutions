class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(k, n, 1, new ArrayList<>(), ans);
        return ans;
    }
    public static void backtrack(int k, int n, int j, ArrayList<Integer> list, List<List<Integer>> ans){
        if(k == 0){
            if(n == 0){
                ans.add(new ArrayList<>(list));
            }
            return;
        }
        for(int i=j; i<10; i++){
            if(i>n){
                break;
            }
            list.add(i);
            backtrack(k-1, n-i, i+1, list, ans);
            list.remove(list.size()-1);
        }
    }
}