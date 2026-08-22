# 104. Maximum Depth of Binary Tree

> **Difficulty:** Easy  
> **Topics:** Binary Tree, Depth-First Search, Recursion

---

## Problem Statement

Given the `root` of a binary tree, return its **maximum depth**.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

For example:

```text
        3
       / \
      9   20
         /  \
        15   7
```

The longest path is:

```text
3 → 20 → 15
```

or:

```text
3 → 20 → 7
```

Therefore, the maximum depth is:

```text
3
```

---

## Example 1

### Input

```text
root = [3,9,20,null,null,15,7]
```

### Output

```text
3
```

### Explanation

The longest path from the root to a leaf contains `3` nodes.

```text
3 → 20 → 15
```

Therefore:

```text
Maximum Depth = 3
```

---

## Example 2

### Input

```text
root = [1,null,2]
```

### Output

```text
2
```

### Explanation

The tree is:

```text
    1
     \
      2
```

The longest path contains:

```text
1 → 2
```

Therefore:

```text
Maximum Depth = 2
```

---

## Example 3

### Input

```text
root = []
```

### Output

```text
0
```

### Explanation

The tree is empty, so its maximum depth is `0`.

---

# Approach

Use **Recursion / Depth-First Search (DFS)**.

For every node, calculate:

```text
Left Subtree Depth
Right Subtree Depth
```

The depth of the current node is:

```text
max(leftDepth, rightDepth) + 1
```

The `+1` represents the current node.

---

# Algorithm

1. If the root is `null`, return `0`.
2. Recursively find the depth of the left subtree.
3. Recursively find the depth of the right subtree.
4. Take the maximum of the two depths.
5. Add `1` for the current node.
6. Return the result.

Formula:

```text
depth(root) = max(depth(root.left), depth(root.right)) + 1
```

---

# Dry Run

Input:

```text
root = [3,9,20,null,null,15,7]
```

Tree:

```text
        3
       / \
      9   20
         /  \
        15   7
```

### Node 9

```text
left = null  → 0
right = null → 0
```

Therefore:

```text
depth(9) = max(0,0) + 1
         = 1
```

---

### Node 15

```text
depth(15) = 1
```

### Node 7

```text
depth(7) = 1
```

---

### Node 20

```text
leftDepth = 1
rightDepth = 1
```

Therefore:

```text
depth(20) = max(1,1) + 1
           = 2
```

---

### Node 3

```text
leftDepth = 1
rightDepth = 2
```

Therefore:

```text
depth(3) = max(1,2) + 1
         = 3
```

Final answer:

```text
3
```

---

# Understanding the Code

## Base Case

```java
if(root == null){
    return 0;
}
```

If there is no node, the depth is `0`.

This is also what stops the recursion when we reach beyond a leaf node.

---

## Find Left Subtree Height

```java
int leftht = maxDepth(root.left);
```

Recursively calculate the maximum depth of the left subtree.

---

## Find Right Subtree Height

```java
int rightht = maxDepth(root.right);
```

Recursively calculate the maximum depth of the right subtree.

---

## Choose the Larger Depth

```java
return Math.max(leftht, rightht) + 1;
```

We choose the deeper subtree and add `1` for the current node.

For example:

```text
Left Depth  = 1
Right Depth = 2
```

Then:

```text
max(1,2) + 1 = 3
```

---

# Why Recursion?

A binary tree is naturally recursive because every node contains two smaller trees:

```text
        Root
       /    \
    Left    Right
    Tree     Tree
```

We can solve the same problem for the left and right subtrees and combine their results.

The recursive pattern is:

```text
Solve Left
     ↓
Solve Right
     ↓
Take Maximum
     ↓
Add Current Node
```

---

# Recursion Tree

For:

```text
        3
       / \
      9   20
         /  \
        15   7
```

The recursive calls work like:

```text
maxDepth(3)
├── maxDepth(9)
│   ├── null → 0
│   └── null → 0
│
└── maxDepth(20)
    ├── maxDepth(15)
    │   ├── null → 0
    │   └── null → 0
    │
    └── maxDepth(7)
        ├── null → 0
        └── null → 0
```

The values return upward:

```text
15 → 1
7  → 1
20 → 2
9  → 1
3  → 3
```

---

# Complexity Analysis

### Time Complexity

Every node is visited exactly once.

If there are `n` nodes:

```text
O(n)
```

---

### Space Complexity

The recursion stack depends on the height of the tree.

```text
O(h)
```

where `h` is the height of the tree.

For a balanced tree:

```text
O(log n)
```

For a completely skewed tree:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int maxDepth(TreeNode root) {

        if(root == null){
            return 0;
        }

        int leftht = maxDepth(root.left);
        int rightht = maxDepth(root.right);

        return Math.max(leftht, rightht) + 1;
    }
}
```

---

# Key Concepts

- Binary Tree
- Recursion
- Depth-First Search
- Tree Height
- Divide and Conquer
- Base Case

---

# Constraints

- The number of nodes in the tree is in the range `[0, 10^4]`.
- `-100 <= Node.val <= 100`

---

# Learning Outcome

This problem demonstrates how **recursion** can be used to calculate the height or depth of a binary tree.

The main idea is:

```text
If root is null
      ↓
    return 0

Otherwise
      ↓
Find left depth
      ↓
Find right depth
      ↓
Take maximum
      ↓
Add 1
```

The important formula is:

```java
Math.max(leftht, rightht) + 1
```

The solution runs in:

```text
Time  → O(n)
Space → O(h)
```

where `h` is the height of the binary tree.