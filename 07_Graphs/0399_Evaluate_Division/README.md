# 399. Evaluate Division

**LeetCode:** [399. Evaluate Division](https://leetcode.com/problems/evaluate-division/)

**Difficulty:** Medium

**Primary Topic:** Graphs

**Pattern:** Weighted Graph + DFS

---

## Problem Statement

You are given a list of equations representing relationships between variables.

Each equation:

```text
a / b = k
```

is represented as:

```text
["a", "b"]
```

with the corresponding value:

```text
k
```

You must evaluate a list of queries.

For every query:

```text
x / y
```

return the calculated value.

If the result cannot be determined, return:

```text
-1.0
```

A variable that does not appear in the equations is also considered unknown.

---

## Example

### Input

```text
equations = [["a","b"],["b","c"]]

values = [2.0,3.0]

queries = [
    ["a","c"],
    ["b","a"],
    ["a","e"],
    ["a","a"],
    ["x","x"]
]
```

### Output

```text
[6.0,0.5,-1.0,1.0,-1.0]
```

### Explanation

From:

```text
a / b = 2
b / c = 3
```

we get:

```text
a / c = (a / b) * (b / c)
      = 2 * 3
      = 6
```

Similarly:

```text
b / a = 1 / 2 = 0.5
```

`e` and `x` do not exist in the graph, so their answers are:

```text
-1.0
```

And:

```text
a / a = 1.0
```

---

# Approach

Convert the equations into a **weighted directed graph**.

For an equation:

```text
a / b = 2.0
```

create two edges:

```text
a → b = 2.0
b → a = 0.5
```

The reverse edge uses the reciprocal:

```text
1 / 2.0 = 0.5
```

Now a query such as:

```text
a / c
```

can be solved by finding a path:

```text
a → b → c
```

and multiplying all edge weights:

```text
2.0 × 3.0 = 6.0
```

DFS is used to search for such a path.

---

# Intuition

Think of every variable as a graph node.

Suppose we have:

```text
a / b = 2
b / c = 3
```

The graph becomes:

```text
      2
a --------> b
<--------   --------> 
   0.5          3
                  c
                 <---
                  1/3
```

To calculate:

```text
a / c
```

we follow:

```text
a → b → c
```

and multiply the weights:

```text
2 × 3 = 6
```

So the path multiplication represents the division result.

---

# Graph Construction

For every equation:

```text
u / v = value
```

we store:

```java
graph.get(u).put(v, value);
```

and:

```java
graph.get(v).put(u, 1.0 / value);
```

Therefore:

```text
u → v = value
v → u = 1/value
```

This allows queries to work in either direction.

---

# Algorithm

### Step 1: Build the Graph

Create:

```text
Map<String, Map<String, Double>>
```

The outer map represents each variable.

The inner map stores:

```text
neighbor → edge weight
```

---

### Step 2: Process Each Query

For every query:

```text
u / v
```

check whether both variables exist in the graph.

If either variable does not exist:

```text
answer = -1.0
```

Otherwise, perform DFS from `u` to `v`.

---

### Step 3: DFS

Start with:

```text
temp = 1.0
```

When moving from one node to another:

```text
temp = temp × edgeWeight
```

If we reach the destination:

```text
u == v
```

then `temp` is the answer.

---

### Step 4: Avoid Cycles

Use a `HashSet`:

```java
Set<String> visited
```

to prevent visiting the same variable repeatedly.

---

# Dry Run

Consider:

```text
equations = [["a","b"],["b","c"]]

values = [2.0,3.0]
```

The graph becomes:

```text
a → b = 2
b → a = 0.5

b → c = 3
c → b = 1/3
```

Now query:

```text
a / c
```

Start:

```text
u = a
v = c
temp = 1.0
```

### DFS at `a`

Mark:

```text
visited = {a}
```

Move to `b`.

Update:

```text
temp = 1 × 2
     = 2
```

---

### DFS at `b`

Mark:

```text
visited = {a,b}
```

Move to `c`.

Update:

```text
temp = 2 × 3
     = 6
```

---

### DFS at `c`

Now:

```text
u == v
```

Therefore:

```text
answer = 6
```

---

# Java Solution

```java
class Solution {
    public double[] calcEquation(
        List<List<String>> equations,
        double[] values,
        List<List<String>> queries
    ) {

        Map<String, Map<String, Double>> graph =
            buildGraph(equations, values);

        double[] finalans = new double[queries.size()];

        for(int i = 0; i < finalans.length; i++){

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

    public void dfs(
        String u,
        String v,
        double[] ans,
        Map<String, Map<String, Double>> graph,
        double temp,
        Set<String> visited
    ){

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

            dfs(key, v, ans, graph, temp * val, visited);
        }
    }

    public Map<String, Map<String, Double>> buildGraph(
        List<List<String>> equations,
        double[] values
    ){

        Map<String, Map<String, Double>> graph = new HashMap<>();

        for(int i = 0; i < equations.size(); i++){

            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);

            graph.putIfAbsent(u, new HashMap<>());
            graph.putIfAbsent(v, new HashMap<>());

            double value = values[i];

            graph.get(u).put(v, value);
            graph.get(v).put(u, 1.0 / value);
        }

        return graph;
    }
}
```

---

# Code Explanation

## 1. Build the Graph

```java
Map<String, Map<String, Double>> graph = buildGraph(equations, values);
```

The graph stores the relationship between variables.

For:

```text
a / b = 2
```

we store:

```text
a → b = 2
b → a = 0.5
```

---

# 2. Create Answer Array

```java
double[] finalans = new double[queries.size()];
```

There is one answer for every query.

---

# 3. Extract Query Variables

```java
String u = queries.get(i).get(0);
String v = queries.get(i).get(1);
```

For a query:

```text
["a", "c"]
```

we get:

```text
u = "a"
v = "c"
```

---

# 4. Check Unknown Variables

```java
if(!graph.containsKey(u) || !graph.containsKey(v)){
    finalans[i] = -1.0;
}
```

If either variable does not appear in any equation, its value cannot be calculated.

---

# 5. Initialize DFS

```java
Set<String> visited = new HashSet<>();

double temp = 1.0;
double[] ans = {-1.0};
```

`temp` stores the multiplication of weights along the current path.

The answer starts as:

```text
-1.0
```

meaning that no valid path has been found yet.

---

# Why Is `ans` an Array?

The DFS method is `void`.

Java passes primitive values like `double` by value.

Therefore, changing a local `double ans` inside DFS would not update the value outside the function.

The code uses:

```java
double[] ans = {-1.0};
```

so DFS can modify:

```java
ans[0]
```

and the updated value remains available to the caller.

---

# DFS Function

```java
public void dfs(
    String u,
    String v,
    double[] ans,
    Map<String, Map<String, Double>> graph,
    double temp,
    Set<String> visited
)
```

The important parameters are:

- `u` → current variable
- `v` → target variable
- `temp` → product of weights along the current path
- `visited` → prevents cycles
- `ans` → stores the final result

---

# Avoid Revisiting Nodes

```java
if(visited.contains(u)){
    return;
}
```

Graphs can contain cycles.

For example:

```text
a → b → c → a
```

Without a visited set, DFS could continue forever.

---

# Mark Current Node

```java
visited.add(u);
```

Once a variable is processed, mark it as visited.

---

# Destination Condition

```java
if(u.equals(v)){
    ans[0] = temp;
    return;
}
```

If the current variable is the target, the accumulated product is the required answer.

---

# Explore Neighbors

```java
for(Map.Entry<String, Double> entry : graph.get(u).entrySet()){
```

Get every neighbor of the current variable.

For each edge:

```java
String key = entry.getKey();
double val = entry.getValue();
```

`key` is the next variable and `val` is the division ratio.

---

# Multiply the Path

```java
dfs(key, v, ans, graph, temp * val, visited);
```

Suppose:

```text
a → b = 2
b → c = 3
```

Then:

```text
temp = 1
```

After moving `a → b`:

```text
temp = 1 × 2 = 2
```

After moving `b → c`:

```text
temp = 2 × 3 = 6
```

Therefore:

```text
a / c = 6
```

---

# `buildGraph()` Explanation

## Create Graph

```java
Map<String, Map<String, Double>> graph = new HashMap<>();
```

The structure is:

```text
Variable
   ↓
Map of neighboring variables and weights
```

---

## Initialize Nodes

```java
graph.putIfAbsent(u, new HashMap<>());
graph.putIfAbsent(v, new HashMap<>());
```

Make sure both variables have an entry in the graph.

---

## Add Forward Edge

```java
graph.get(u).put(v, value);
```

For:

```text
u / v = value
```

we store:

```text
u → v = value
```

---

## Add Reverse Edge

```java
graph.get(v).put(u, 1.0 / value);
```

Because:

```text
u / v = value
```

we know:

```text
v / u = 1 / value
```

Therefore:

```text
v → u = 1/value
```

---

# Example of Graph Construction

Given:

```text
a / b = 2
b / c = 3
```

The graph contains:

```text
a:
    b → 2

b:
    a → 0.5
    c → 3

c:
    b → 0.333...
```

This representation allows DFS to answer queries in either direction.

---

# Complexity Analysis

Let:

- `E` = number of equations
- `V` = number of unique variables
- `Q` = number of queries

## Graph Construction

Each equation creates two edges.

```text
Time = O(E)
Space = O(V + E)
```

---

## Query Processing

For each query, DFS can visit every variable and edge in the worst case.

Therefore:

```text
O(V + E)
```

per query.

For `Q` queries:

```text
O(Q × (V + E))
```

---

## Total Complexity

```text
Time  = O(E + Q × (V + E))
Space = O(V + E)
```

The DFS `visited` set and recursion stack can require up to `O(V)` additional space for a query.

---

# Key Concepts

## 1. Weighted Graph

Unlike a normal graph, each edge has a numerical weight.

```text
a → b = 2.0
```

The weight represents:

```text
a / b
```

---

## 2. Reverse Edge

Every equation produces two directed edges:

```text
a / b = k
```

becomes:

```text
a → b = k
b → a = 1/k
```

---

## 3. DFS

DFS is used to find a path from the query's numerator to its denominator.

---

## 4. Path Product

The answer is obtained by multiplying the edge weights along the path.

```text
a → b → c

2 × 3 = 6
```

---

## 5. Connected Components / Reachability

If there is no path between two variables, their division cannot be determined.

Therefore:

```text
No path → -1.0
```

---

# Pattern Recognition

When a problem gives equations such as:

```text
a / b = value
```

and asks for relationships between variables, think:

```text
Variables → Graph Nodes
Equations → Weighted Edges
Query → Path Finding
Answer → Product of Edge Weights
```

Typical approach:

```text
Weighted Graph
      ↓
DFS / BFS
      ↓
Multiply weights along path
```

---

# Common Mistakes

## Mistake 1: Adding Only One Direction

For:

```text
a / b = 2
```

do not store only:

```text
a → b = 2
```

You also need:

```text
b → a = 0.5
```

---

## Mistake 2: Forgetting Unknown Variables

If either variable is absent from the graph:

```java
if(!graph.containsKey(u) || !graph.containsKey(v))
```

the answer must be:

```text
-1.0
```

---

## Mistake 3: Not Using a Visited Set

Graphs can contain cycles.

Without:

```java
Set<String> visited
```

DFS can repeatedly visit the same variables.

---

## Mistake 4: Forgetting to Multiply Weights

The result is not simply the number of edges.

For:

```text
a → b = 2
b → c = 3
```

the answer is:

```text
2 × 3 = 6
```

not `2`.

---

# Edge Cases

### Same Variable

For:

```text
a / a
```

if `a` exists, the answer is:

```text
1.0
```

because every non-zero value divided by itself is `1`.

---

### Unknown Variable

For:

```text
a / x
```

if `x` does not exist:

```text
-1.0
```

---

### Disconnected Variables

Suppose:

```text
a / b = 2
c / d = 3
```

Query:

```text
a / d
```

There is no path from `a` to `d`.

Therefore:

```text
-1.0
```

---

# Learning Outcome

After solving this problem, you should understand:

- How to convert equations into a graph.
- How to create a weighted directed graph.
- Why reciprocal edges are required.
- How DFS can answer path-based queries.
- How to accumulate values along a path.
- How to use a visited set to handle graph cycles.
- How graph modeling can transform a mathematical problem into a traversal problem.

---

# Final Takeaway

The most important transformation is:

```text
a / b = k
```

into:

```text
a ──k──> b
b ──1/k──> a
```

Then a query:

```text
x / y
```

becomes a graph path problem.

If the path is:

```text
x → p → q → y
```

the answer is:

```text
weight(x,p) × weight(p,q) × weight(q,y)
```

So the complete pattern is:

```text
Equations
    ↓
Weighted Graph
    ↓
DFS Path Search
    ↓
Multiply Edge Weights
    ↓
Query Answer
```

**Time Complexity:** `O(E + Q × (V + E))`

**Space Complexity:** `O(V + E)`