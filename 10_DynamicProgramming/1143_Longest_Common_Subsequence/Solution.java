class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int dp[] = new int[text1.length()];
        int longest = 0;

        for(char c : text2.toCharArray()){
            int currlength = 0;
            for(int i = 0; i<dp.length; i++){
                if(currlength < dp[i]){
                    currlength = dp[i];
                }
                else if(c == text1.charAt(i)){
                    dp[i] = currlength + 1;
                    longest = Math.max(longest, currlength+1);
                }
            }
        }    
        return longest;
    }
}