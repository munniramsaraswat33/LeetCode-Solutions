class Solution {
    public String predictPartyVictory(String senate) {
        int n = senate.length();

        Queue<Integer> rqueue = new LinkedList<>();
        Queue<Integer> dqueue = new LinkedList<>();

        for(int i = 0; i<n; i++){
            if(senate.charAt(i) == 'R'){
                rqueue.offer(i);
            }
            else{
                dqueue.offer(i);
            }
        }

        while(!rqueue.isEmpty() && !dqueue.isEmpty()){
            int r = rqueue.poll();
            int d = dqueue.poll();

            if(r < d){
                rqueue.offer(r+n);
            }
            else{
                dqueue.offer(d+n);
            }
        }
        return dqueue.isEmpty()? "Radiant" : "Dire";
    }
}