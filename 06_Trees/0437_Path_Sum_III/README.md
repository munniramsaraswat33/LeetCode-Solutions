# 437. Path Sum III

> **Difficulty:** Medium  
> **Topics:** Binary Tree, DFS, Recursion

---

## Problem Statement

Given the root of a binary tree and an integer `targetSum`, return the number of paths where:

- The path starts at any node.
- The path follows parent-to-child connections.
- The path can end at any node.
- The sum of all node values in the path is equal to `targetSum`.

The path does **not** need to start at the root or end at a leaf.

---

## Examples

### Example 1

```text
Input:
root = [10,5,-3,3,2,null,11,3,-2,null,1]
targetSum = 8

Output:
3
```

### Explanation

The three valid paths are:

```text
5 → 3
5 → 2 → 1
-3 → 11
```

Their sums are:

```text
5 + 3 = 8
5 + 2 + 1 = 8
-3 + 11 = 8
```

Therefore:

```text
Answer = 3
```

---

### Example 2

```text
Input:
root = [5,4,8,11,null,13,4,7,2,null,null,5,1]
targetSum = 22

Output:
3
```

### Explanation

There are three downward paths whose sum is `22`.

Therefore:

```text
Answer = 3
```

---

### Example 3

```text
Input:
root = [1]
targetSum = 1

Output:
1
```

### Explanation

The only path is:

```text
1
```

Its sum is equal to `targetSum`.

Therefore:

```text
Answer = 1
```

---

# Approach

The important observation is that a valid path can start at **any node**.

So for every node, we need to answer:

> How many downward paths starting from this node have sum equal to `targetSum`?

We use two recursive functions.

### `pathSum()`

This function considers every node as a possible starting point.

```java
return countPath(root, targetSum)
     + pathSum(root.left, targetSum)
     + pathSum(root.right, targetSum);
```

It:

1. Counts valid paths starting from the current node.
2. Recursively starts paths from the left subtree.
3. Recursively starts paths from the right subtree.

---

### `countPath()`

This function counts paths that **must start at the current node**.

If:

```java
root.val == targetSum
```

then the current path is valid.

After including the current node, we need:

```text
targetSum - root.val
```

from its children.

So we recursively search:

```java
countPath(root.left, targetSum - root.val);
countPath(root.right, targetSum - root.val);
```

---

# Algorithm

1. If the root is `null`, return `0`.
2. Count all valid paths starting from the current node using `countPath()`.
3. Recursively search for paths starting from the left child.
4. Recursively search for paths starting from the right child.
5. In `countPath()`:
   - If the current node is `null`, return `0`.
   - If `root.val == targetSum`, increment the count.
   - Subtract `root.val` from `targetSum`.
   - Recursively search both children.
6. Add all counts and return the result.

---

# Dry Run

Consider:

```text
root = [10,5,-3,3,2,null,11,3,-2,null,1]
targetSum = 8
```

Tree:

```text
             10
            /  \
           5   -3
          / \    \
         3   2    11
        / \   \
       3  -2   1
```

### Start at Node 10

We look for paths starting from `10`.

```text
10
```

Since:

```text
10 != 8
```

we continue searching.

No valid path starting at `10` is found in this traversal.

---

### Start at Node 5

Now:

```text
targetSum = 8
```

Current node:

```text
5
```

Remaining sum:

```text
8 - 5 = 3
```

Go to node `3`.

```text
5 + 3 = 8
```

So one valid path is:

```text
5 → 3
```

Count:

```text
1
```

---

### Another Path Starting at 5

Take:

```text
5 → 2 → 1
```

Sum:

```text
5 + 2 + 1 = 8
```

So this is another valid path.

Count:

```text
2
```

---

### Start at Node -3

Now consider:

```text
-3 → 11
```

Sum:

```text
-3 + 11 = 8
```

So another valid path is found.

Count:

```text
3
```

Therefore:

```text
Answer = 3
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

    public int pathSum(TreeNode root, int targetSum) {

        if (root == null) {
            return 0;
        }

        // Count paths starting from the current node
        // and paths starting from both subtrees
        return countPath(root, (long) targetSum)
                + pathSum(root.left, targetSum)
                + pathSum(root.right, targetSum);
    }

    public int countPath(TreeNode root, long targetSum) {

        if (root == null) {
            return 0;
        }

        int count = 0;

        // Current path reaches target sum
        if (root.val == targetSum) {
            count++;
        }

        // Continue path through left subtree
        count += countPath(
                root.left,
                targetSum - root.val
        );

        // Continue path through right subtree
        count += countPath(
                root.right,
                targetSum - root.val
        );

        return count;
    }
}
```

---

# Code Explanation

### `pathSum()`

```java
public int pathSum(TreeNode root, int targetSum)
```

This function makes sure that every node gets a chance to become the starting point of a path.

```java
countPath(root, targetSum)
```

checks paths starting from the current node.

Then:

```java
pathSum(root.left, targetSum)
```

checks paths starting somewhere in the left subtree.

And:

```java
pathSum(root.right, targetSum)
```

checks paths starting somewhere in the right subtree.

---

### `countPath()`

```java
public int countPath(TreeNode root, long targetSum)
```

This function only continues downward from the current starting node.

If:

```java
root.val == targetSum
```

then we found one valid path.

```java
count++;
```

---

### Reduce the Remaining Target

After using the current node:

```java
targetSum - root.val
```

is the amount that the remaining path must contribute.

For example:

```text
target = 8
current node = 5
```

Remaining:

```text
8 - 5 = 3
```

So the children need to form a path with sum `3`.

---

### Why `long` Is Used

```java
countPath(root, (long) targetSum)
```

The remaining target can change repeatedly as values are subtracted.

Using `long` helps avoid integer overflow during recursive subtraction.

---

# Complexity Analysis

Let `n` be the number of nodes in the binary tree.

### Time Complexity

```text
O(n²)
```

In the worst case, `countPath()` can traverse many nodes for every possible starting node.

For a highly skewed tree, this can result in:

```text
n + (n-1) + (n-2) + ... + 1
```

which is:

```text
O(n²)
```

### Space Complexity

```text
O(h)
```

where `h` is the height of the tree.

This space is used by the recursive call stack.

For a balanced tree:

```text
O(log n)
```

For a skewed tree:

```text
O(n)
```

---

# Key Concepts

### 1. Binary Tree DFS

The tree is explored using Depth-First Search.

### 2. Recursive Path Exploration

`countPath()` explores all downward paths beginning at a specific node.

### 3. Any Node Can Be a Starting Point

Unlike normal root-to-leaf path problems, the path can start at any node.

### 4. Remaining Target

Instead of maintaining the complete path sum, we continuously reduce the required target:

```text
remaining = targetSum - currentNode.val
```

### 5. Two-Level Recursion

There are two recursive processes:

```text
pathSum()
    ↓
chooses starting node

countPath()
    ↓
explores paths from that starting node
```

---

# Constraints

- The number of nodes in the tree is between `0` and `1000`.
- `-10^9 <= Node.val <= 10^9`
- `-1000 <= targetSum <= 1000`

---

# Learning Outcome

After solving this problem, you should understand:

- How to find paths that can start from any node in a binary tree.
- How to use DFS to explore downward paths.
- How to use recursive functions for different responsibilities.
- How to maintain a remaining target while traversing a path.
- Why this straightforward DFS solution has `O(n²)` worst-case time complexity.
- How tree recursion can be structured into an **outer traversal + inner path search**.