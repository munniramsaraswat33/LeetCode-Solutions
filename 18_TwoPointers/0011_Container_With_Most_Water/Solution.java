class Solution {
    public int maxArea(int[] height) {
        int mw = 0;
        int si = 0;
        int ei = height.length-1;
        while(si!=ei){
            int ht = Math.min(height[si], height[ei]);
            int wt = ei - si;
            int cw = ht * wt;
            mw = Math.max(mw, cw);
            if(height[si]<height[ei]){
                si++;
            }else{
                ei--;
            }
        }
        return mw;
    }
}