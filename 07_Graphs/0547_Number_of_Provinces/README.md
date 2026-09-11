# 547. Number of Provinces

**LeetCode:** [547. Number of Provinces](https://leetcode.com/problems/number-of-provinces/)

**Difficulty:** Medium

**Primary Topic:** Graphs

**Pattern:** DFS / Connected Components

---

## Problem Statement

There are `n` cities. Some of them are connected, while some are not.

A **province** is a group of directly or indirectly connected cities.

You are given an `n x n` matrix `isConnected` where:

- `isConnected[i][j] = 1` means city `i` and city `j` are directly connected.
- `isConnected[i][j] = 0` means city `i` and city `j` are not directly connected.

Return the total number of provinces.

---

## Example 1

### Input

```text
isConnected =
[
    [1,1,0],
    [1,1,0],
    [0,0,1]
]
```

### Output

```text
2
```

### Explanation

- City `0` is connected with city `1`.
- City `2` is separate.

Therefore, there are two provinces:

```text
Province 1 → {0, 1}
Province 2 → {2}
```

---

## Example 2

### Input

```text
isConnected =
[
    [1,0,0],
    [0,1,0],
    [0,0,1]
]
```

### Output

```text
3
```

### Explanation

No two different cities are connected.

Therefore, every city forms its own province.

---

# Approach

This problem can be treated as a **graph connected components** problem.

Each city represents a node in the graph.

If:

```text
isConnected[i][j] == 1
```

then there is an edge between city `i` and city `j`.

A province is simply a **connected component** of this graph.

We can use **DFS (Depth First Search)** to visit all cities belonging to the same province.

---

# Intuition

Suppose we start DFS from city `0`.

DFS will visit:

- city `0`
- every city directly connected to `0`
- every city indirectly connected through those cities

After DFS finishes, all cities belonging to that province are marked as visited.

Now we continue checking the remaining cities.

If we find a city that has not been visited, it must belong to a new province.

So:

```text
Unvisited city → New province → Run DFS
```

The number of times we start DFS is exactly the number of provinces.

---

# Algorithm

1. Let `n` be the number of cities.
2. Create a boolean array `visited` of size `n`.
3. Initialize `count = 0`.
4. Traverse every city from `0` to `n - 1`.
5. If the current city is not visited:
   - Increment `count`.
   - Start DFS from that city.
6. During DFS:
   - Mark the current city as visited.
   - Check every other city.
   - If the cities are connected and the other city is not visited, recursively visit it.
7. After checking all cities, return `count`.

---

# Dry Run

Consider:

```text
isConnected =
[
    [1,1,0],
    [1,1,0],
    [0,0,1]
]
```

Initially:

```text
visited = [false, false, false]
count = 0
```

### Step 1

`i = 0`

City `0` is not visited.

```text
count = 1
```

Start DFS from city `0`.

City `0` is connected to city `1`.

So DFS visits:

```text
0 → 1
```

Now:

```text
visited = [true, true, false]
```

---

### Step 2

`i = 1`

City `1` is already visited.

Skip it.

---

### Step 3

`i = 2`

City `2` is not visited.

Therefore:

```text
count = 2
```

Start DFS from city `2`.

City `2` has no connection to another unvisited city.

Now:

```text
visited = [true, true, true]
```

Final answer:

```text
2
```

---

# Java Solution

```java
class Solution {

    public int findCircleNum(int[][] isConnected) {

        int n = isConnected.length;

        boolean[] visited = new boolean[n];

        int count = 0;

        for(int i = 0; i < n; i++){

            if(!visited[i]){

                count++;

                dfs(i, isConnected, visited);
            }
        }

        return count;
    }

    public void dfs(int curr, int[][] isConnected, boolean[] visited){

        visited[curr] = true;

        for(int i = 0; i < isConnected.length; i++){

            if(isConnected[curr][i] == 1 && !visited[i]){

                dfs(i, isConnected, visited);
            }
        }
    }
}
```

---

# Code Explanation

## 1. Get Number of Cities

```java
int n = isConnected.length;
```

The matrix is `n x n`, so there are `n` cities.

---

## 2. Create Visited Array

```java
boolean[] visited = new boolean[n];
```

This array keeps track of whether each city has already been visited.

Initially:

```text
visited = [false, false, false, ...]
```

---

## 3. Count Provinces

```java
int count = 0;
```

`count` stores the number of connected components found.

---

## 4. Traverse All Cities

```java
for(int i = 0; i < n; i++){
```

We check every city.

---

## 5. Start DFS for an Unvisited City

```java
if(!visited[i]){
    count++;
    dfs(i, isConnected, visited);
}
```

If a city has not been visited, it belongs to a new province.

Therefore:

```java
count++;
```

Then DFS visits the entire province.

---

# DFS Function

```java
public void dfs(int curr, int[][] isConnected, boolean[] visited)
```

The parameters are:

- `curr` → current city
- `isConnected` → connection matrix
- `visited` → visited cities

---

## Mark Current City

```java
visited[curr] = true;
```

Once we enter a city, mark it as visited.

This prevents visiting the same city repeatedly.

---

## Check All Cities

```java
for(int i = 0; i < isConnected.length; i++){
```

For the current city, check whether it is connected to every other city.

---

## Visit Connected Cities

```java
if(isConnected[curr][i] == 1 && !visited[i]){
    dfs(i, isConnected, visited);
}
```

There are two conditions:

### Condition 1

```java
isConnected[curr][i] == 1
```

The current city and city `i` are connected.

### Condition 2

```java
!visited[i]
```

City `i` has not been visited yet.

If both conditions are true, recursively visit city `i`.

---

# Why Does Counting DFS Calls Give the Answer?

Every DFS starts from a city that has not been visited before.

That means the DFS explores one complete connected component.

Therefore:

```text
1 DFS start = 1 province
```

So the total number of DFS starts is the number of provinces.

---

# Graph Representation

The matrix:

```text
isConnected
```

acts as an **adjacency matrix**.

For example:

```text
[
    [1,1,0],
    [1,1,0],
    [0,0,1]
]
```

can be represented as:

```text
0 ---- 1

2
```

There are two connected components:

```text
{0, 1}
{2}
```

Therefore:

```text
Number of Provinces = 2
```

---

# Complexity Analysis

Let `n` be the number of cities.

## Time Complexity

```text
O(n²)
```

For every visited city, DFS scans the entire row of the adjacency matrix.

In the worst case, we examine approximately:

```text
n × n
```

connections.

Therefore:

```text
Time = O(n²)
```

---

## Space Complexity

The `visited` array requires:

```text
O(n)
```

The recursive DFS can also go as deep as `n` cities.

Therefore, the auxiliary space is:

```text
O(n)
```

---

# Key Concepts

## 1. Connected Components

A province is equivalent to a connected component in a graph.

Whenever we find an unvisited node and start DFS, we discover one new connected component.

---

## 2. DFS

DFS explores a node and recursively explores all reachable nodes.

General pattern:

```text
DFS(node)
    mark node visited

    for every neighbor
        if not visited
            DFS(neighbor)
```

---

## 3. Visited Array

The `visited` array prevents:

- Infinite recursion
- Repeated traversal
- Processing the same city multiple times

---

## 4. Adjacency Matrix

Here, the graph is represented using an adjacency matrix.

```java
isConnected[i][j]
```

tells whether city `i` and city `j` are directly connected.

---

# Important Observation

The problem does not require finding the actual cities inside each province.

We only need the **number of provinces**.

Therefore, whenever we encounter an unvisited city:

```java
count++;
```

and use DFS to mark its entire connected component.

---

# Common Mistakes

## Mistake 1: Counting Every Connection

Do not increment the answer for every:

```java
isConnected[i][j] == 1
```

because multiple connections can belong to the same province.

We need to count **connected components**, not edges.

---

## Mistake 2: Forgetting the Visited Array

Without `visited`, DFS can repeatedly process the same cities.

---

## Mistake 3: Incrementing Count Inside DFS

The province count should be incremented when starting DFS from an unvisited city:

```java
if(!visited[i]){
    count++;
    dfs(i, isConnected, visited);
}
```

Not every time another connected city is discovered.

---

# Alternative Approach

This problem can also be solved using:

- BFS
- Union-Find / Disjoint Set Union (DSU)

For example, with BFS:

```text
Unvisited city
      ↓
Start BFS
      ↓
Visit complete province
      ↓
count++
```

The DFS solution is simple and directly matches the connected-components idea.

---

# Pattern Recognition

When you see a problem asking for:

- Number of groups
- Number of provinces
- Number of connected components
- Number of isolated networks
- Number of disconnected regions

Think:

```text
Graph
  ↓
Connected Components
  ↓
DFS / BFS / Union-Find
```

For this problem:

```text
Cities = Nodes
Connections = Edges
Province = Connected Component
```

---

# Learning Outcome

After solving this problem, you should understand:

- How to represent a graph using an adjacency matrix.
- How DFS explores connected nodes.
- How to count connected components.
- Why a visited array is necessary.
- How a real-world grouping problem can be converted into a graph problem.

---

# Final Takeaway

The key idea is very simple:

```text
Every unvisited city starts a new province.
DFS visits every city belonging to that province.
```

Therefore:

```text
Answer = Number of times DFS is started
```

This is a fundamental **Graph + DFS + Connected Components** problem.