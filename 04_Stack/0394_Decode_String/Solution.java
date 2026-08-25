class Solution {
    public String decodeString(String s) {
        Stack<Integer> intStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        int currNum = 0;

        for(char x : s.toCharArray()){
            if(Character.isDigit(x)){
                currNum = currNum*10 + x-'0';
            }
            else{
                if(x == '['){
                    intStack.push(currNum);
                    currNum = 0;
                    strStack.push(String.valueOf(x));
                }
                else if(x == ']'){
                    String temp = "";
                    while(!strStack.isEmpty() && !strStack.peek().equals("[")){
                        temp = strStack.pop() + temp;
                    }
                    strStack.pop();
                    int num = intStack.pop();
                    StringBuilder sb = new StringBuilder("");
                    for(int i=0; i<num; i++){
                        sb.append(temp);
                    }
                    strStack.push(sb.toString());
                }
                else{
                    strStack.push(String.valueOf(x));
                }
            }
        }

        String ans = "";
        while(!strStack.isEmpty()){
            ans = strStack.pop() + ans;
        }
        return ans;
    }
}