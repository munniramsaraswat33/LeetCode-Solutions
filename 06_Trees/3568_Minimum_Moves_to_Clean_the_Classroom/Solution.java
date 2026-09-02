class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litterIndex = new int[m][n];

        for(int[] row : litterIndex){
            Arrays.fill(row, -1);
        }

        int starR = -1;
        int starC = -1;
        int litterCount = 0;

        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                char ch = classroom[r].charAt(c);

                if(ch == 'S'){
                    starR = r;
                    starC = c;
                }
                else if(ch == 'L'){
                    litterIndex[r][c] = litterCount;
                    litterCount++;
                }
            }
        }

        if(litterCount == 0){
            return 0;
        }

        int targetMask = (1 << litterCount) - 1;

        Queue<int[]> queue = new ArrayDeque<>();

        boolean[][][][] visited = new boolean[m][n][energy+1][1 << litterCount];

        queue.offer(new int[]{starR, starC, energy, 0});

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int moves = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size-->0){
                int[] current = queue.poll();

                int r = current[0];
                int c = current[1];
                int currEnergy = current[2];
                int mask = current [3];

                if(mask == targetMask){
                    return moves;
                }

                if(currEnergy == 0){
                    continue;
                }

                for(int[] dir : directions){
                    int nr = r + dir[0];
                    int nc = c + dir[1];

                    if(nr < 0 || nr >= m || nc < 0 || nc >= n){
                        continue;
                    }

                    if(classroom[nr].charAt(nc) == 'X'){
                        continue;
                    }

                    int newEnergy = currEnergy - 1;
                    char cell = classroom[nr].charAt(nc);

                    if(cell == 'R'){
                        newEnergy = energy;
                    }

                    int newMask = mask;

                    if(cell == 'L'){
                        int bit = litterIndex[nr][nc];
                        newMask |= (1<<bit);
                    }

                    if(!visited[nr][nc][newEnergy][newMask]){
                        visited[nr][nc][newEnergy][newMask] = true;
                        queue.offer(new int[]{nr, nc, newEnergy, newMask});
                    }
                }
            }
            moves++;
        }
        return -1;
    }
}