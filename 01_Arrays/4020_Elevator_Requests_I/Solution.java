class Solution {
    public int elevatorRequests(int n, int[] requests) {
        int ans = requests[0];
        int i=1;
        while(i != requests.length){
            ans += Math.abs(requests[i] - requests[i-1]);
            i++;
        }
        return ans;
    }
}