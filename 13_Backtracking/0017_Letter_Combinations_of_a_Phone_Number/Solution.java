class Solution {
    public List<String> letterCombinations(String digits) {
        if(digits.isEmpty()){
            return Collections.emptyList();
        }
        String[] phone_map = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> output = new ArrayList<>();
        backtrack("", digits, phone_map, output);
        return output;
    }
    public void backtrack(String combi, String digits, String[] phone_map, List<String> output){
        if(digits.isEmpty()){
            output.add(combi);
        }
        else{
            String Letters = phone_map[digits.charAt(0) - '2'];
            for(char letter : Letters.toCharArray()){
                backtrack(combi+letter, digits.substring(1), phone_map, output);
            }
        }
    }
}