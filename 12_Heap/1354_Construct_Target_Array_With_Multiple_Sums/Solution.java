class Solution {
    public boolean isPossible(int[] target) {
        if(target.length == 1){
            return target[0] == 1;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        long totalSum = 0;
        for(int num : target){
            totalSum += num;
            pq.offer(num);
        }

        while(pq.peek() > 1){
            long maxElement = pq.poll();
            long restSum = totalSum - maxElement;
            if(restSum == 1){
                return true;
            }

            if(restSum == 0 || maxElement <= restSum){
                return false;
            }

            long updateElement = maxElement % restSum;
            if(updateElement == 0){
                return false;
            }

            totalSum = restSum + updateElement;
            pq.offer((int)updateElement);
        }
        return true;
    }
}