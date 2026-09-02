class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int freq[] = new int[26];
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()){
            freq[c-'a']++;
        }
        for(int i=0; i<target.length(); i++){
            int curr = target.charAt(i)-'a';
            if(freq[curr] == 0){
                for(int j=curr+1; j<26; j++){
                    if(freq[j] >0){
                        sb.append((char)(j+'a'));
                        freq[j]--;
                        appendRemaining(sb, freq);
                        return sb.toString();
                    }
                }
                break;
            }
            sb.append(target.charAt(i));
            freq[curr]--;
        }
        for(int i=sb.length()-1; i>=0; i--){
            int curr = sb.charAt(i)-'a';
            freq[curr]++;
            sb.deleteCharAt(i);
            for(int j = curr+1; j<26; j++){
                if(freq[j] > 0){
                    sb.append((char)(j+'a'));
                    freq[j]--;
                    appendRemaining(sb, freq);
                    return sb.toString();
                }
            }
        }
        return "";
    }
    public void appendRemaining(StringBuilder sb, int[] freq){
        for(int i=0; i<26; i++){
            while(freq[i] > 0){
                sb.append((char)(i+'a'));
                freq[i]--;
            }
        }
    }
}