class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = buildGraph(equations, values);
        double[] finalans = new double[queries.size()];
        for(int i=0; i<finalans.length; i++){
            String u = queries.get(i).get(0);
            String v = queries.get(i).get(1);
            if(!graph.containsKey(u) || !graph.containsKey(v)){
                finalans[i] = -1.0;
            }
            else{
                Set<String> visited = new HashSet<>();
                double temp = 1.0;
                double[] ans = {-1.0};
                dfs(u, v, ans, graph, temp, visited);
                finalans[i] = ans[0];
            }
        }
        return finalans;
        
    }

    public void dfs(String u, String v, double[] ans, Map<String, Map<String, Double>> graph, double temp, Set<String> visited){
        if(visited.contains(u)){
            return;
        }
        visited.add(u);
        if(u.equals(v)){
            ans[0] = temp;
            return;
        }
        for(Map.Entry<String, Double> entry : graph.get(u).entrySet()){
            String key = entry.getKey();
            double val = entry.getValue();
            dfs(key, v, ans, graph, temp*val, visited);
        }
    }
    public Map<String, Map<String, Double>> buildGraph(List<List<String>> equations, double[] values){
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for(int i=0; i<equations.size(); i++){
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);

            graph.putIfAbsent(u, new HashMap<>());
            graph.putIfAbsent(v, new HashMap<>());

            double value = values[i];
            graph.get(u).put(v, value);
            graph.get(v).put(u, 1.0/value);
        }
        return graph;
    }
}