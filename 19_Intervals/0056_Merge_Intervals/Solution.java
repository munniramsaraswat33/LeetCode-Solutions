class Solution {
    public int[][] merge(int[][] interval) {
        Arrays.sort(interval, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> list = new ArrayList<>(); 
        int start = interval[0][0];
        int end = interval[0][1];
        for(int i = 1; i<interval.length; i++){
            if(interval[i][0] <= end){
                end = Math.max(end, interval[i][1]);
            }
            else{
                list.add(new int[]{start, end});
                start = interval[i][0];
                end = interval[i][1];
            }
        }
        list.add(new int[]{start, end});
        return list.toArray(new int[list.size()][]);
    }
}