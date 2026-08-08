class Solution {
    public String maskPII(String s) {
        StringBuilder sb = new StringBuilder();
        if(Character.isLetter(s.charAt(0))){
            sb.append(Character.toLowerCase(s.charAt(0)));
            sb.append("*****");
            int i=1;
            while(s.charAt(i) != '@'){
                i++;
            }
            for(int j=i-1; j<s.length(); j++){
                sb.append(Character.toLowerCase(s.charAt(j)));
            }
            return sb.toString();
        }
        else{
            Stack<Character> stack = new Stack<>();
            for(int i=0; i<s.length(); i++){
                char ch = s.charAt(i);
                if(Character.isDigit(ch)){
                    stack.push(ch);
                }
            }
            int n = stack.size();
            int count = 0;
            while(count != 4){
                sb.append(stack.pop());
                count++;
            }
            sb.append("-***-***");
            if(n == 11){
                sb.append("-*+");
            }
            else if(n == 12){
                sb.append("-**+");
            }
            else if(n==13){
                sb.append("-***+");
            }
            return sb.reverse().toString();
        }
    }
}