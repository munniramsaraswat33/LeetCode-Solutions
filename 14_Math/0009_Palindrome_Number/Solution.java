import java.util.*;
class Solution {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int original = x;
        int reverse = 0;
        while(x!=0){
            int val = x%10;
            reverse = reverse*10+val;
            x/=10;
        }
        if(original!=reverse){
            return false;
        }
        return true;
    }
}