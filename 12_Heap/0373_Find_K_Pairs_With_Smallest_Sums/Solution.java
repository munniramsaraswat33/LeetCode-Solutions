class Solution {
    class Pair{
        int i;
        int j;
        int sum;
        public Pair(int i, int j, int sum){
            this.i = i;
            this.j = j;
            this.sum = sum;
        }
    }
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> arr = new ArrayList<>();

        if(nums1.length==0 || nums2.length==0 || k == 0){
            return arr;
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)->a.sum-b.sum);

        for(int i=0; i<nums1.length; i++){
            pq.offer(new Pair(i, 0, nums1[i]+nums2[0]));
        }

        while(k!=0 && !pq.isEmpty()){
            Pair curr = pq.remove();
            arr.add(Arrays.asList(nums1[curr.i], nums2[curr.j]));

            

            if(curr.j+1 < nums2.length){
                pq.offer(new Pair(curr.i, curr.j+1, nums1[curr.i]+nums2[curr.j+1]));
            }

            k--;
        }
        return arr;
    }
}