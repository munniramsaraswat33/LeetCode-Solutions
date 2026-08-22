class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        Stack<Integer> s = new Stack<>();
        int arr[] = new int[n];
        for(int i=n-1; i>=0; i--){
            while(!s.isEmpty() && temperatures[s.peek()] <= temperatures[i]){
                s.pop();
            }
            if(s.isEmpty()){
                arr[i] = 0;
            }
            else{
                arr[i] = s.peek()-i;
            }
            s.push(i);
        }
        return arr;
    }
}