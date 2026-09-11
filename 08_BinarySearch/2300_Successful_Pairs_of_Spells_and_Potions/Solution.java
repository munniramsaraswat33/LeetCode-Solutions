class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int m = spells.length;
        int n = potions.length;
        Arrays.sort(potions);
        int[] ans = new int[m];
        for(int i=0; i<m; i++){
            long require = (success + spells[i] - 1)/spells[i];
            int l = 0;
            int r = n-1;
            while(l<=r){
                int mid = l + (r - l)/2;
                if(potions[mid] >= require){
                    r = mid-1;
                }
                else{
                    l = mid+1;
                }
            }
            ans[i] = n - l;
        }
        return ans;
    }
}