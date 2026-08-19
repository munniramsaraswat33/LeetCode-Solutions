class Solution {
    public int minPenalty(int period, int[] lights, int[] arrivalTime) {
        int maxGreen = 0;
        for(int light : lights){
            maxGreen = Math.max(maxGreen, light);
        }

        int[][] velunoraxi = { {period}, lights, arrivalTime };
        int ans = 0;
        for(int i=0; i<arrivalTime.length; i++){
            int val = arrivalTime[i]%period;
            int waiting;
            if(val < maxGreen){
                waiting = 0;
            }
            else{
                waiting = period - val;
            }
            ans = Math.max(ans, waiting);
        }
        return ans;
    }
}