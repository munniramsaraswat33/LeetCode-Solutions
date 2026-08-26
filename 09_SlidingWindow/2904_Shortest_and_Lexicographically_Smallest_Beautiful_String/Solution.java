class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int ones = 0;    
        for(char c : s.toCharArray()){
            if(c == '1'){
                ones++;
            }
        }
        if(ones < k){
            return "";
        }

        int left = 0;
        int count = 0;
        String ans = s;
        for(int right = 0; right<s.length(); right++){
            if(s.charAt(right) == '1'){
                count++;
            }
            while(count > k){
                if(s.charAt(left) == '1'){
                    count--;
                }
                left++;
            }
            if(count == k){
                while(s.charAt(left) == '0'){
                    left++;
                }
                String t = s.substring(left, right+1);
                if(t.length() < ans.length() || (t.length() == ans.length() && t.compareTo(ans) < 0)){
                    ans = t;
                }
            }
        }
        return ans;
    }
}