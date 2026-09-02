class Solution {
    public String lexPalindromicPermutation(String s, String target){
        int[] count = new int[26];

        for(char c : s.toCharArray()){
            count[c-'a']++;
        }

        int odd = 0;
        char mid = 0;

        for(int i=0; i<26; i++){
            if(count[i]%2 == 1){
                mid = (char)('a' + i);
                odd++;
            }

            if(odd > 1){
                return "";
            }
            count[i] = count[i]/2;
        }
        StringBuilder half = new StringBuilder();
        StringBuilder answer = new StringBuilder();
        if(solve(0, half, count, target, mid, false, answer)){
            return answer.toString();
        }
        return "";
    }

    public boolean solve(int i, StringBuilder half, int[] count, String target, char mid, boolean flag, StringBuilder answer){
        int halfLen = target.length()/2;
        if(i == halfLen){
            StringBuilder p = new StringBuilder(half);
            if(target.length()%2 == 1){
                p.append(mid);
            }
            p.append(new StringBuilder(half).reverse());
            if(p.toString().compareTo(target)>0){
                answer.append(p);
                return true; 
            }
            return false;
        }

        for(int j=0; j<26; j++){
            if(count[j] == 0){
                continue;
            }

            char ch = (char)('a' + j);
            if(!flag && ch<target.charAt(i)){
                continue;
            }

            count[j]--;
            half.append(ch);
            boolean newFlag = flag || ch > target.charAt(i);

            if(solve(i+1, half, count, target, mid, newFlag, answer)){
                return true;
            }
            half.deleteCharAt(half.length()-1);
            count[j]++;
        }
        return false;
    }
}