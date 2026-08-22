class Solution {
    public boolean checkDivisibility(int n) {
        int k = n;
        long sum = 0;
        long product = 1;
        while(k != 0){
            int val = k%10;
            k /= 10;
            sum += val;
            product *= val;
        }
        return n % (sum + product) == 0;
    }
}