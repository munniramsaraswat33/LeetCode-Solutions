class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        HashMap<Integer, Boolean> hm = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int num : nums){
            hm.put(num, true);
            min = Integer.min(min, num);
            max = Integer.max(max, num);
        }

        for(int i=min; i<=max; i++){
            if(!hm.containsKey(i)){
                list.add(i);
            }
        }
        return list;
    }
}