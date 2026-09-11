class Solution {
    public long minimumTime(int[] time, int totalTrips) {
        long left = 1;
        int mintime = Integer.MAX_VALUE;
        for(int t : time){
            mintime = Math.min(mintime, t);
        }
        long right = (long)mintime * totalTrips;
        while(left <= right){
            long mid = left + (right - left)/2;
            long trip = 0;
            for(int t : time){
                trip += mid/t;
            }
            if(trip < totalTrips){
                left = mid+1;
            }
            else{
                right = mid-1;
            }
        }
        return left;
    }
}