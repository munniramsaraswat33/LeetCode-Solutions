class Solution {
    public int maxRepeating(String sequence, String word) {
        String current = word;
        int count = 0;
        while(sequence.contains(current)){
            count++;
            current += word;
        }
        return count;
    }
}