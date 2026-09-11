class Solution {
    public int bagOfTokensScore(int[] tokens, int power) {
        Arrays.sort(tokens);
        int left = 0;
        int right = tokens.length-1;
        int score = 0;
        int maxScore = 0;
        while(left <= right){
            if(power >= tokens[left]){
                score++;
                power -= tokens[left];
                left++;

                maxScore = Math.max(score , maxScore);
            }
            else if(score > 0 && left < right){
                power += tokens[right];
                right--;
                score--;
            }
            else{
                break;
            }
        }
        return maxScore;
    }
}