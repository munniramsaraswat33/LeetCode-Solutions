class Solution {
    public int equalPairs(int[][] grid) {
        int count = 0;
        int m = grid.length;
        for(int i=0; i<m; i++){
            for(int j=0; j<m; j++){
                if(grid[i][0] == grid[0][j]){
                    int n = 0;
                    while(n<m){
                        if(grid[i][n] != grid[n][j]){
                            break;
                        }
                        n++;
                    }
                    if(n == m){
                        count++;
                    }
                }
            }
        }
        return count;
    }
}