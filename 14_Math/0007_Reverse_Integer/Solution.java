class Solution {
    public int reverse(int x) {
        int original = x;
        int reverse = 0;
        if(x==0) return 0;
        while(x%10==0){
            x/=10;
        }
        while(x!=0){
            int digit = x%10;
            if(reverse>Integer.MAX_VALUE/10 || reverse==Integer.MAX_VALUE && digit>7){
                return 0;
            }
            if(reverse<Integer.MIN_VALUE/10 || reverse==Integer.MIN_VALUE && digit<-8){
                return 0;
            }
            reverse =reverse*10 + digit;
            x /= 10;
        }
        return reverse;        
    }
}