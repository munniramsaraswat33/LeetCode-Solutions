class Solution {
    public boolean detectCapitalUse(String word) {
        boolean upper = word.equals(word.toUpperCase());
        boolean lower = word.equals(word.toLowerCase());

        if(upper == true || lower == true){
            return true;
        }

        if(Character.isUpperCase(word.charAt(0))){
            for(int i=1; i<word.length(); i++){
                if(Character.isUpperCase(word.charAt(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}