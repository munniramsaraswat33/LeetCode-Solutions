# 1372. Longest ZigZag Path in a Binary Tree

**LeetCode:** https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/

**Difficulty:** Medium

**Topics:** Binary Tree, Depth-First Search, Dynamic Programming, Tree Traversal

---

## Problem Statement

You are given the root of a binary tree.

A **ZigZag path** for a binary tree is defined as follows:

- Choose any node in the tree and a direction:
  - Right
  - Left
- If the current direction is **Right**, move to the right child.
- If the current direction is **Left**, move to the left child.
- After every move, the direction must be changed.
- The path stops when there is no child in the required direction.

The length of a ZigZag path is the number of **edges** in the path.

Return the length of the longest ZigZag path in the binary tree.

---

## Example 1

### Input

```text
root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1]
```

### Output

```text
3
```

### Explanation

The longest ZigZag path contains 3 edges.

---

## Example 2

### Input

```text
root = [1,1,1,null,1,null,null,1,1,null,1]
```

### Output

```text
4
```

### Explanation

The longest ZigZag path contains 4 edges.

---

## Example 3

### Input

```text
root = [1]
```

### Output

```text
0
```

### Explanation

There are no edges, so the longest ZigZag path has length `0`.

---

# Approach

We solve this problem using **Depth-First Search (DFS)** with information returned from each subtree.

For every node, we need to know three things:

1. The longest ZigZag path starting from this node and going **Left**.
2. The longest ZigZag path starting from this node and going **Right**.
3. The longest ZigZag path found anywhere inside this subtree.

We store these three values in an integer array:

```text
[leftLength, rightLength, maximum]
```

---

# Intuition

Consider a node `root`.

If we move from `root` to its **left child**, the next move must be to the **right**.

Therefore:

```text
leftLength = right child's ZigZag information + 1
```

More precisely, if `root.left` exists, after moving left we must continue right from that child.

Similarly, if we move from `root` to its **right child**, the next move must be to the **left**.

Therefore:

```text
rightLength = left child's ZigZag information + 1
```

The important observation is that we can calculate these values from the information returned by the children.

---

# DFS State

For every node, `dfs(root)` returns:

```text
result[0] = longest ZigZag path starting at root by moving LEFT
result[1] = longest ZigZag path starting at root by moving RIGHT
result[2] = longest ZigZag path anywhere in root's subtree
```

So:

```text
dfs(root) = {leftLength, rightLength, maximum}
```

---

# Why Does the Direction Switch?

Suppose we start from a node and move left:

```text
root
  \
   ...
```

After taking a left edge, the next edge must be right.

Therefore, the left-path length depends on the **right direction** value of the left child.

Similarly:

```text
root
   \
    right
```

After taking a right edge, the next edge must be left.

Therefore, the right-path length depends on the **left direction** value of the right child.

This gives:

```text
leftLength = dfs(root.left)[1] + 1
rightLength = dfs(root.right)[0] + 1
```

---

# Base Case

When `root == null`, there is no edge.

The code returns:

```java
return new int[]{-1, -1, -1};
```

Why `-1`?

Because when the parent adds `1`:

```text
-1 + 1 = 0
```

This correctly represents that there is no ZigZag edge when the required child does not exist.

For example, if a node has no left child:

```text
dfs(null)[1] + 1
= -1 + 1
= 0
```

So the ZigZag length becomes `0`.

---

# Calculating the Answer

For the current node:

```java
int ans = Math.max(
    Math.max(left[1], right[0]) + 1,
    Math.max(left[2], right[2])
);
```

There are two possibilities.

### 1. ZigZag path passes through the current node

We can extend:

```text
left child's right-direction path
```

or

```text
right child's left-direction path
```

So:

```text
max(left[1], right[0]) + 1
```

The `+1` represents the edge connecting the current node to its child.

### 2. Best path is already completely inside a child subtree

It could already exist in either subtree:

```text
max(left[2], right[2])
```

Therefore:

```text
ans =
max(
    path passing through current node,
    best path already found below
)
```

---

# Algorithm

1. Start DFS from the root.
2. For every node:
   - Recursively calculate information for the left subtree.
   - Recursively calculate information for the right subtree.
3. Calculate:
   - Longest path beginning with a left move.
   - Longest path beginning with a right move.
   - Maximum ZigZag path in the subtree.
4. Return these three values.
5. The third value returned for the root is the final answer.

---

# Dry Run

Consider a simple tree:

```text
        1
         \
          2
         /
        3
         \
          4
```

The path is:

```text
1 → 2 → 3 → 4
```

Directions are:

```text
Right → Left → Right
```

So the ZigZag length is:

```text
3
```

---

## Step 1: Node 4

Node `4` is a leaf.

Both children are `null`.

```text
left  = {-1, -1, -1}
right = {-1, -1, -1}
```

Calculate:

```text
leftLength = -1 + 1 = 0
rightLength = -1 + 1 = 0
```

The best path is:

```text
0
```

So:

```text
dfs(4) = {0, 0, 0}
```

---

## Step 2: Node 3

Node `3` has a right child `4`.

For node `3`:

```text
left = {-1, -1, -1}
right = {0, 0, 0}
```

The longest path starting by moving right is:

```text
left[0] + 1
= 0 + 1
= 1
```

Therefore:

```text
dfs(3) = {0, 1, 1}
```

---

## Step 3: Node 2

Node `2` has a left child `3`.

The left subtree gives:

```text
left = {0, 1, 1}
```

The right subtree is null:

```text
right = {-1, -1, -1}
```

Because we move left from node `2`, the next move must be right.

Therefore:

```text
leftLength = left[1] + 1
           = 1 + 1
           = 2
```

So:

```text
dfs(2) = {2, 0, 2}
```

The ZigZag path is:

```text
2 → 3 → 4
```

with 2 edges.

---

## Step 4: Node 1

Node `1` has a right child `2`.

For the right move, the next move must be left.

Therefore:

```text
rightLength = left[0] + 1
            = 2 + 1
            = 3
```

So:

```text
dfs(1) = {0, 3, 3}
```

The final answer is:

```text
3
```

---

# Java Solution

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int longestZigZag(TreeNode root) {
        return dfs(root)[2];
    }

    public int[] dfs(TreeNode root) {
        if(root == null){
            return new int[]{-1, -1, -1};
        }

        int left[] = dfs(root.left);
        int right[] = dfs(root.right);

        int ans = Math.max(
            Math.max(left[1], right[0]) + 1,
            Math.max(left[2], right[2])
        );

        return new int[]{left[1] + 1, right[0] + 1, ans};
    }
}
```

---

# Code Explanation

## 1. Main Function

```java
public int longestZigZag(TreeNode root) {
    return dfs(root)[2];
}
```

We call DFS on the root.

The DFS returns three values:

```text
{leftLength, rightLength, maximum}
```

The third value is the maximum ZigZag path found in the entire tree.

Therefore:

```java
dfs(root)[2]
```

is the final answer.

---

## 2. Base Case

```java
if(root == null){
    return new int[]{-1, -1, -1};
}
```

For a null node, there is no path.

Returning `-1` allows the parent to add `1` and obtain `0`.

---

## 3. Process Left and Right Subtrees

```java
int left[] = dfs(root.left);
int right[] = dfs(root.right);
```

We recursively calculate the required information for both children.

---

## 4. Calculate the Maximum Answer

```java
int ans = Math.max(
    Math.max(left[1], right[0]) + 1,
    Math.max(left[2], right[2])
);
```

There are two cases:

### Path starts through the current node

```text
max(left[1], right[0]) + 1
```

This extends a ZigZag path by using the edge from the current node to one of its children.

### Path already exists below

```text
max(left[2], right[2])
```

The best path might already be completely inside either subtree.

We take the maximum of both possibilities.

---

## 5. Return State

```java
return new int[]{left[1] + 1, right[0] + 1, ans};
```

The returned values represent:

```text
index 0 → left-first ZigZag length
index 1 → right-first ZigZag length
index 2 → maximum ZigZag length
```

The direction alternates, so:

```text
left-first  → use child's right-first value
right-first → use child's left-first value
```

---

# Complexity Analysis

Let `n` be the number of nodes in the binary tree.

## Time Complexity

```text
O(n)
```

Every node is visited exactly once.

At each node, only constant-time calculations are performed.

Therefore:

```text
Time = O(n)
```

## Space Complexity

The DFS uses recursion.

In the worst case, the tree can be completely skewed:

```text
1
 \
  2
   \
    3
     \
      4
```

The recursion depth can become `n`.

Therefore:

```text
Space = O(n)
```

for the recursion stack.

For a balanced tree, the recursion depth is `O(log n)`.

---

# Key Concepts / Patterns

## 1. Binary Tree DFS

The solution uses **Depth-First Search** to visit every node.

---

## 2. Postorder Traversal

The current node depends on information from both children.

Therefore, we first process:

```text
Left Subtree
Right Subtree
Current Node
```

This is a **postorder DFS** pattern.

---

## 3. Multiple Values Returned From DFS

Instead of returning only one value, the DFS returns:

```text
{leftLength, rightLength, maximum}
```

This is a useful tree DP technique when the parent needs multiple pieces of information from its children.

---

## 4. Direction-Based Dynamic Programming

The problem depends on the direction of the previous move.

We therefore maintain two directional states:

```text
Left
Right
```

The states alternate:

```text
Left → Right
Right → Left
```

---

## 5. Tree Dynamic Programming

The answer for a node is built from the answers of its children.

This is a classic **Tree DP** pattern:

```text
Child information
       ↓
Current node state
       ↓
Subtree answer
```

---

# Important Observation

The most important part of the solution is understanding these two transitions:

```text
left-first path
    ↓
move left
    ↓
next move must be right
    ↓
use left child's right-first value
```

and:

```text
right-first path
    ↓
move right
    ↓
next move must be left
    ↓
use right child's left-first value
```

Therefore:

```java
left[1] + 1
```

and:

```java
right[0] + 1
```

are the key transitions.

---

# Why the Answer Counts Edges

The problem asks for the **length of the ZigZag path in terms of edges**, not nodes.

For example:

```text
1 → 2 → 3 → 4
```

contains:

```text
3 edges
```

even though it contains 4 nodes.

That is why a single-node tree returns:

```text
0
```

and why the solution uses `+1` when extending a path across an edge.

---

# Learning Outcome

After solving this problem, you should understand:

- How to use DFS on a binary tree.
- How to perform postorder tree traversal.
- How to return multiple values from a recursive function.
- How to represent direction-dependent states.
- How to solve tree problems using dynamic programming.
- How to combine child states to calculate the answer for the current node.
- How to distinguish between the best path starting at the current node and the best path anywhere in the subtree.

---

# Pattern Summary

```text
Problem:
Longest ZigZag Path in a Binary Tree

Primary Pattern:
Tree DFS + Tree DP

Traversal:
Postorder DFS

State:
{left-first, right-first, maximum}

Transition:
left-first  = leftChild.right-first + 1
right-first = rightChild.left-first + 1

Answer:
maximum ZigZag path in the root subtree

Time:
O(n)

Space:
O(n) worst case
```

---

# Final Takeaway

The key idea is to let every node return enough information for its parent to continue a ZigZag path.

Instead of calculating the entire answer independently from every node, we combine the results of the left and right subtrees.

The direction alternates after every edge:

```text
Left → Right → Left → Right
```

or:

```text
Right → Left → Right → Left
```

By storing both directional lengths and the best answer in each subtree, the complete tree can be solved in a single DFS traversal.

```text
DFS + Postorder + Directional State + Tree DP
```

This makes the solution efficient with:

```text
O(n) time
O(n) worst-case recursion space
```