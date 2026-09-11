class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int ans = 0;
        int sum = 0;
        int left = 0;
        for(char c : s.toCharArray()){
            while(set.contains(c)){
                char ch = s.charAt(left);
                set.remove(ch);
                left++;
                sum--;
            }
            set.add(c);
            sum++;
            ans = Math.max(ans, sum);
        }
        return ans;
    }
}