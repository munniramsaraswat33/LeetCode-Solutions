# 841. Keys and Rooms

**LeetCode Problem:** [841. Keys and Rooms](https://leetcode.com/problems/keys-and-rooms/)

**Difficulty:** Medium

**Primary Topic:** Graphs

**Pattern:** DFS / Graph Traversal

---

## Problem Statement

There are `n` rooms labeled from `0` to `n - 1`.

Initially:

- You are in room `0`.
- All rooms are locked except room `0`.
- Each room contains a list of keys.
- A key with value `x` allows you to enter room `x`.

You can enter a room if you have its key.

Return:

```text
true
```

if you can visit **every room**.

Otherwise, return:

```text
false
```

---

## Example 1

### Input

```text
rooms = [[1],[2],[3],[]]
```

### Output

```text
true
```

### Explanation

Start from room `0`.

```text
Room 0 → key 1
Room 1 → key 2
Room 2 → key 3
Room 3 → no keys
```

So all rooms can be visited.

---

## Example 2

### Input

```text
rooms = [[1,3],[3,0,1],[2],[0]]
```

### Output

```text
false
```

### Explanation

Starting from room `0`, we can visit some rooms, but room `2` cannot be reached.

Therefore:

```text
Answer = false
```

---

# Approach

We model the rooms as a **graph**.

Each room is a node.

If room `i` contains a key to room `j`, then there is a directed edge:

```text
i → j
```

We start from room `0` and perform **Depth First Search (DFS)** using a stack.

We maintain:

```java
boolean[] visited
```

to keep track of rooms that have already been visited.

The process is:

```text
Start at room 0
      ↓
Get keys from room
      ↓
Visit unlocked rooms
      ↓
Get more keys
      ↓
Continue until stack is empty
```

Finally, if every room has been visited, return `true`.

---

# Graph Representation

For:

```text
rooms = [[1,3],[3,0,1],[2],[0]]
```

the graph can be viewed as:

```text
Room 0 → Room 1
       → Room 3

Room 1 → Room 3
       → Room 0
       → Room 1

Room 2 → Room 2

Room 3 → Room 0
```

We start from:

```text
Room 0
```

and follow all reachable keys.

---

# Intuition

The important observation is:

> A key found inside a room allows us to visit another room.

This is exactly a graph traversal problem.

For example:

```text
Room 0
  |
  | key 1
  ↓
Room 1
  |
  | key 2
  ↓
Room 2
  |
  | key 3
  ↓
Room 3
```

Starting from room `0`, we can reach every room.

Therefore:

```text
true
```

If some room is not reachable from room `0`, then we can never obtain its key.

Therefore:

```text
false
```

---

# Algorithm

1. Create a stack for DFS.
2. Create a boolean `visited` array.
3. Mark room `0` as visited.
4. Push room `0` into the stack.
5. While the stack is not empty:
   - Pop a room.
   - Visit every key inside that room.
   - If the corresponding room has not been visited:
     - Mark it as visited.
     - Push it into the stack.
6. After DFS finishes, check every value in `visited`.
7. If any room is unvisited, return `false`.
8. Otherwise, return `true`.

---

# Dry Run

Consider:

```text
rooms = [[1],[2],[3],[]]
```

Initially:

```text
visited = [true, false, false, false]
stack = [0]
```

---

## Step 1

Pop room `0`:

```text
curr = 0
```

Room `0` contains:

```text
[1]
```

Room `1` has not been visited.

So:

```text
visited = [true, true, false, false]
stack = [1]
```

---

## Step 2

Pop room `1`:

```text
curr = 1
```

Room `1` contains:

```text
[2]
```

Visit room `2`:

```text
visited = [true, true, true, false]
stack = [2]
```

---

## Step 3

Pop room `2`.

Room `2` contains:

```text
[3]
```

Visit room `3`:

```text
visited = [true, true, true, true]
stack = [3]
```

---

## Step 4

Pop room `3`.

Room `3` contains no keys.

The stack becomes empty:

```text
stack = []
```

All rooms are visited:

```text
visited = [true, true, true, true]
```

Therefore:

```text
Answer = true
```

---

# Java Solution

```java
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
```

---

# Code Explanation

## 1. Create the Stack

```java
Stack<Integer> stack = new Stack<>();
```

The stack is used to implement DFS iteratively.

It stores rooms that we have discovered but still need to process.

---

## 2. Create the Visited Array

```java
boolean[] visited = new boolean[rooms.size()];
```

For each room:

```text
visited[i] = true
```

means that room `i` has already been discovered.

Initially:

```text
visited = [false, false, false, ...]
```

---

## 3. Start From Room 0

```java
visited[0] = true;
stack.push(0);
```

Room `0` is initially unlocked, so we can start our graph traversal there.

After this:

```text
visited[0] = true
stack = [0]
```

---

## 4. Continue DFS

```java
while(!stack.isEmpty()){
```

As long as there are rooms waiting to be processed, continue the traversal.

---

## 5. Get Current Room

```java
int curr = stack.pop();
```

Remove the top room from the stack.

This gives us the next room whose keys we need to examine.

---

## 6. Process All Keys

```java
for(int room : rooms.get(curr)){
```

`rooms.get(curr)` gives the list of keys available inside the current room.

Each key represents another room that we may be able to visit.

---

## 7. Visit an Unvisited Room

```java
if(!visited[room]){
    visited[room] = true;
    stack.push(room);
}
```

If the room has not already been visited:

1. Mark it as visited.
2. Add it to the stack.

This prevents visiting the same room repeatedly.

---

# Why Mark Visited Before Pushing?

The code does:

```java
visited[room] = true;
stack.push(room);
```

rather than marking it when it is popped.

This is important because multiple rooms might contain the same key.

For example:

```text
Room 0 → key 1
Room 2 → key 1
```

If room `1` were marked only after popping, it could be added to the stack multiple times.

By marking it immediately:

```text
visited[1] = true
```

we ensure that it is pushed only once.

---

# Final Visited Check

After DFS:

```java
for(boolean visit : visited){
    if(visit == false){
        return false;
    }
}
```

We check every room.

If any room remains:

```text
false
```

then that room cannot be reached from room `0`.

Therefore, we return:

```text
false
```

If all rooms are visited:

```text
true
```

is returned.

---

# Why DFS Works

DFS explores all rooms reachable from room `0`.

Suppose:

```text
0 → 1 → 2 → 3
```

DFS will eventually visit:

```text
0
1
2
3
```

If a room is reachable through any chain of keys, DFS will eventually follow that chain.

Therefore, after DFS finishes:

```text
All rooms visited
    ↓
true

Some room unvisited
    ↓
false
```

---

# DFS Visualization

Consider:

```text
rooms = [[1,3],[3,0,1],[2],[0]]
```

Starting from room `0`:

```text
        0
       / \
      1   3
     /|\   |
    3 0 1  0
```

Room `2` is not reachable.

Therefore:

```text
visited[2] = false
```

and the answer is:

```text
false
```

---

# Why Do We Need a Visited Array?

Graphs can contain cycles.

For example:

```text
0 → 1
↑   |
|   ↓
└── 2
```

Without a `visited` array, DFS could repeatedly traverse:

```text
0 → 1 → 2 → 0 → 1 → 2 → ...
```

The `visited` array prevents this.

Once a room has been visited:

```java
visited[room] = true;
```

we don't push it again.

---

# Graph Cycle Example

Suppose:

```text
rooms = [[1],[2],[0]]
```

The graph is:

```text
0 → 1 → 2
↑       |
└───────┘
```

There is a cycle.

The traversal works correctly:

```text
Visit 0
  ↓
Visit 1
  ↓
Visit 2
  ↓
Key 0 found
  ↓
Already visited
  ↓
Ignore
```

So the traversal terminates normally.

---

# Complexity Analysis

Let:

```text
n = number of rooms
```

and let `E` be the total number of keys across all rooms.

## Time Complexity

Every room is visited at most once.

Every key is processed at most once.

Therefore:

```text
Time = O(n + E)
```

Since the total number of keys is part of the input, this is the standard graph traversal complexity.

---

## Space Complexity

The `visited` array requires:

```text
O(n)
```

The stack can contain up to:

```text
O(n)
```

rooms.

Therefore:

```text
Space = O(n)
```

excluding the input graph itself.

---

# BFS Alternative

This problem can also be solved using **BFS**.

Instead of a stack:

```java
Stack<Integer> stack;
```

we could use a queue:

```java
Queue<Integer> queue;
```

The idea would be the same:

```text
Start at room 0
      ↓
Visit all reachable rooms
      ↓
Check whether every room was visited
```

The complexity remains:

```text
Time:  O(n + E)
Space: O(n)
```

The given solution uses DFS with a stack.

---

# DFS vs BFS

| Approach | Data Structure | Time | Space |
|---|---|---:|---:|
| DFS | Stack | O(n + E) | O(n) |
| BFS | Queue | O(n + E) | O(n) |

Both correctly determine whether every room is reachable.

---

# Common Mistakes

## Mistake 1: Starting From Every Room

We should start only from:

```text
Room 0
```

because room `0` is the only initially unlocked room.

---

## Mistake 2: Forgetting the Visited Array

Without:

```java
boolean[] visited
```

cycles can cause repeated processing.

---

## Mistake 3: Not Marking Room 0

We need:

```java
visited[0] = true;
```

before starting DFS.

---

## Mistake 4: Pushing Already Visited Rooms

Always check:

```java
if(!visited[room])
```

before pushing.

Otherwise, the same room may be added to the stack many times.

---

## Mistake 5: Returning True When DFS Finishes

An empty stack does **not** automatically mean all rooms were visited.

The stack only tells us that there are no more reachable rooms to process.

We still need to check:

```java
for(boolean visit : visited)
```

to ensure every room was reached.

---

# Key Concepts / Patterns

## 1. Graph Traversal

Each room is treated as a node, and each key represents a directed edge.

```text
Room i
  |
  | key
  ↓
Room j
```

---

## 2. DFS

The solution uses iterative DFS:

```text
Stack
  ↓
Pop room
  ↓
Explore keys
  ↓
Push unvisited rooms
```

---

## 3. Reachability

The actual question is:

> Can every room be reached from room `0`?

This is a classic graph reachability problem.

---

## 4. Visited Array

The visited array prevents:

- Duplicate processing.
- Infinite loops caused by cycles.
- Unnecessary stack operations.

---

## 5. Adjacency List

The input:

```java
List<List<Integer>> rooms
```

acts as an adjacency list.

For example:

```text
rooms.get(0)
```

contains all nodes directly reachable from room `0`.

---

# Learning Outcome

After solving this problem, you should understand:

- How to convert a real-world problem into a graph.
- How keys can be represented as directed edges.
- How to implement DFS using a stack.
- Why a visited array is necessary.
- How to solve reachability problems.
- How to handle cycles in a graph.
- How to analyze graph traversal complexity.

---

# Summary

Treat each room as a graph node.

A key from room `i` to room `j` represents:

```text
i → j
```

Start DFS from room `0`.

For every room:

```text
1. Mark it visited.
2. Look at all keys.
3. Push unvisited rooms into the stack.
```

After DFS finishes, check whether every room was visited.

```text
All rooms visited
       ↓
     true

Any room unvisited
       ↓
     false
```

### Final Complexity

```text
Time:  O(n + E)
Space: O(n)
```

**Primary Pattern: Graph Traversal + DFS + Reachability**