/*
LeetCode 316 - Remove Duplicate Letters

Difficulty: Medium

Approach:
- Monotonic Stack
- Last Occurrence Array
- Visited Array

Time Complexity: O(n)

Space Complexity: O(1)
*/

class Solution {
    public String removeDuplicateLetters(String s) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<s.length(); i++){
            boolean isDuplicate = false;
            for(int j=i+1; j<s.length(); j++){
                if(s.charAt(i)==s.charAt(j)){
                    isDuplicate=true;
                    break;
                }
            }
            if(!isDuplicate){
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}