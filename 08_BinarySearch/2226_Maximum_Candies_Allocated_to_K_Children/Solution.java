class Solution {
    public int maximumCandies(int[] candies, long k) {
        long left = 1;
        long total = 0;
        for(int candie : candies){
            total += candie;
        }
        long right = total/k;
        int ans = 0;
        while(left <= right){
            long mid = left + (right - left)/2;
            long child = 0;
            for(int candie : candies){
                child += candie/mid;
            }
            if(child >= k){
                left = mid+1;
                ans = (int)mid;
            }
            else{
                right = mid-1;
            }
        }
        return ans;
    }
}