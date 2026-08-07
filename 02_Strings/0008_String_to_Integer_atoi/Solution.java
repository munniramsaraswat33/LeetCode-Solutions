class Solution {
    public int myAtoi(String s) {
        if(s.length() == 0){
            return 0;
        }
        int i=0;
        long ans = 0;
        int sign = 1;
        while(i<s.length() && s.charAt(i)==' '){
            i++;
        }
        if (i == s.length()) {
            return 0;
        }
        if(s.charAt(i) == '-'){
            sign = -1;
            i++;
        }else if(s.charAt(i) == '+'){
            i++;
        }
        while(i< s.length() && Character.isDigit(s.charAt(i))){
            int val = s.charAt(i) - '0';
            ans = ans*10 + val;

            if(sign == 1 && ans > Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }
            else if(sign == -1 && -ans < Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }
            i++;
        }
        return (int)(sign*ans);
    }
}