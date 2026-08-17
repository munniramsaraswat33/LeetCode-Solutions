class Solution {
    public int nearestDrone(int[][] drone, int[] target) {
        int val = -1;
        int ans = Integer.MAX_VALUE;
        for(int i=0; i<drone.length; i++){
            int min = Math.abs(drone[i][0] - target[0]) + Math.abs(drone[i][1] - target[1]);
            if(min > drone[i][2]){
                continue;
            }
            if(min < ans){
                val = i;
                ans = min;
            }
        }
        return val;
    }
}