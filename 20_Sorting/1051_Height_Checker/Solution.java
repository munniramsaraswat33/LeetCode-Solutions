class Solution {
    public int heightChecker(int[] heights) {
        int[] count = new int[101];
        for(int h : heights){
            count[h]++;
        }

        int mismatch = 0;
        int val = 0;
        for(int i=0; i<heights.length; i++){
            while(count[val] == 0){
                val++;
            }

            if(heights[i] != val){
                mismatch++;
            }

            count[val]--;
        }

        return mismatch;
    }
}