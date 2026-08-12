class Solution {
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int ans = 0;
        for(int j=0; j<s.length(); j++){
            if(s.charAt(j) == '('){
                stack.push(j);
            }
            else{
                stack.pop();
                if(stack.isEmpty()){
                    stack.push(j);
                }
                else{
                    ans = Math.max(ans, j-stack.peek());
                }
            }
        }
        return ans;
    }
}