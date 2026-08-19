class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int x : nums1){
            arr.add(x);
        }
        for (int x : nums2){
            arr.add(x);
        }
        Collections.sort(arr);
        int n = arr.size();
        if(n % 2 == 0){
            return (double)(arr.get((int)n/2-1) + arr.get((int)n/2))/2;
        }else{
            return (double)arr.get((int)(n-1)/2);
        }
    }
}