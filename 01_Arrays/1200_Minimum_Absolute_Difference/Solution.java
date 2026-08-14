class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> list = new ArrayList<>();
        if(arr.length == 2){
            list.add(Arrays.asList(arr[0], arr[1]));
        }

        int ans = Integer.MAX_VALUE;
        for(int j=1; j<arr.length; j++){
            int diff = arr[j] - arr[j-1];
            if(diff == ans){
                list.add(Arrays.asList(arr[j-1], arr[j]));
            }
            else if(diff < ans){
                ans = diff;
                list.clear();
                list.add(Arrays.asList(arr[j-1], arr[j]));
            }
        }
        return list;
    }
}