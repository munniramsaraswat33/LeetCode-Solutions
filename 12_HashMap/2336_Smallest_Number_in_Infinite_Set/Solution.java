class SmallestInfiniteSet {
    int current;
    PriorityQueue<Integer> pq;
    Set<Integer> set;
    public SmallestInfiniteSet() {
        current = 1;
        pq = new PriorityQueue<>();
        set = new HashSet<>();
    }
    
    public int popSmallest() {
        if(!pq.isEmpty()){
            int smallest = pq.poll();
            set.remove(smallest);
            return smallest;
        }
        return current++;
    }
    
    public void addBack(int num) {
        if(num < current && !set.contains(num)){
            pq.offer(num);
            set.add(num);
        }
    }
}

/**
 * Your SmallestInfiniteSet object will be instantiated and called as such:
 * SmallestInfiniteSet obj = new SmallestInfiniteSet();
 * int param_1 = obj.popSmallest();
 * obj.addBack(num);
 */