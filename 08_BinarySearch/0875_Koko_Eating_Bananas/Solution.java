class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;
        for(int pile : piles){
            right = Math.max(right, pile);
        }
        while(left < right){
            int mid = left + (right-left)/2;
            if(isvalid(piles, mid, h)){
                right = mid;
            }
            else{
                left = mid+1;
            }
        }
        return left;
    }
    public boolean isvalid(int[] piles, int k, int h){
        long hour = 0;
        for(int pile : piles){
            hour += (pile+k-1)/k;
            if(hour > h){
                return false;
            }
        }
        return true;
    }
}