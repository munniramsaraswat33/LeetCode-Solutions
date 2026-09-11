class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int count = 0;
        for(int i=0; i<n; i++){
            if(!visited[i]){
                count++;
                dfs(i, isConnected, visited);
            }
        }
        return count;
    }
    public void dfs(int curr, int[][] isConnected, boolean[] visited){
        visited[curr] = true;
        for(int i=0; i<isConnected.length; i++){
            if(isConnected[curr][i] == 1 && !visited[i]){
                dfs(i, isConnected, visited);
            }
        }
    }
}