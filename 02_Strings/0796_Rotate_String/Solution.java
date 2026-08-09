class Solution {
    public boolean rotateString(String s, String goal) {
        if(s.equals(goal)){
            return true;
        }
        StringBuilder sb = new StringBuilder(s);
        for(int i=0; i<s.length(); i++){
            char ch = sb.charAt(0);
            sb.deleteCharAt(0);
            sb.append(ch);
            if(goal.equals(sb.toString())){
                return true;
            }
        }
        return false;
    }
}