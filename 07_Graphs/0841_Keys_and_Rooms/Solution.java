class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[rooms.size()];
        visited[0] = true;
        stack.push(0);

        while(!stack.isEmpty()){
            int curr = stack.pop();
            for(int room : rooms.get(curr)){
                if(!visited[room]){
                    visited[room] = true;
                    stack.push(room);
                }
            }
        }

        for(boolean visit : visited){
            if(visit == false){
                return false;
            }
        }
        return true;
    }
}