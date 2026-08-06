class Solution {
    public String licenseKeyFormatting(String s, int k) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i=s.length()-1; i>=0; i--){
            char ch = s.charAt(i);

            if(ch == '-'){
                continue;
            }
            if(count == k){
                sb.append('-');
                count = 0;
            }
            sb.append(Character.toUpperCase(ch));
            count++;
        }
        return sb.reverse().toString();
    }
}