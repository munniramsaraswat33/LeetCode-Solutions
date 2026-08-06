class Solution {
    public int smallestNumber(int n, int t) {
        int x = n;
        while(x >= n){
            int product = 1;
            int i = x;
            while(i != 0){
                product  *= i%10;
                i /= 10;
            }
            if(product % t == 0){
                return x;
            }
            else{
                x++;
            }
        }
        return n;
    }
}