class Solution {
    public int distinctSubseqII(String s) {
        long sum = 0;
        int MOD = (int)1e9 + 7;
        long[] count = new long[26];
        for(char c : s.toCharArray()){
            long total = (1 + sum + MOD)%MOD;
            sum += total-count[c-'a'];
            count[c-'a'] = total;
        }
        return (int)(sum % MOD);
    }
}