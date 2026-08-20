class Solution {
    public String reverseVowels(String s) {
        StringBuilder sb = new StringBuilder(s);
        int i =0;
        int j = s.length()-1;
        while(i < j){
            while(i<j && !isVowel(sb.charAt(i))){
                i++;
            }
            while(i<j && !isVowel(sb.charAt(j))){
                j--;
            }
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
            i++;
            j--;
        }
        return sb.toString();
    }
    public boolean isVowel(char c){
        return c=='a' || c=='e' || c=='i' || c=='o' || c=='u' || c== 'A' || c=='E' || c=='I' || c=='O' || c=='U';
    }
}