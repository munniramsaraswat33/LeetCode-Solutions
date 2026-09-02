# 1448. Count Good Nodes in Binary Tree

> **Difficulty:** Medium  
> **Topics:** Binary Tree, DFS, Recursion

---

## Problem Statement

Given the root of a binary tree, a node `X` in the tree is called a **good node** if there is no node with a value greater than `X.val` on the path from the root to `X`.

In other words, a node is good if its value is greater than or equal to the maximum value encountered on the path from the root to that node.

Return the number of good nodes in the binary tree.

---

## Examples

### Example 1

```text
Input:
root = [3,1,4,3,null,1,5]

Output:
4
```

### Explanation

The good nodes are:

```text
3 → root
4 → greater than 3
3 → equal to maximum on its path
5 → greater than all previous values
```

Therefore:

```text
Answer = 4
```

---

### Example 2

```text
Input:
root = [3,3,null,4,2]

Output:
3
```

### Explanation

The good nodes are:

```text
3 → root
3 → equal to the maximum value on its path
4 → greater than all previous values
```

Therefore:

```text
Answer = 3
```

---

### Example 3

```text
Input:
root = [1]

Output:
1
```

### Explanation

The root has no nodes before it, so it is always a good node.

---

# Approach

The important observation is that whether a node is good depends only on the **maximum value on the path from the root to that node**.

For every node, we maintain:

```text
max
```

which represents the maximum value encountered from the root to the current node.

When visiting a node:

```java
if(max <= root.val)
```

the node is good because its value is greater than or equal to every previous value on the path.

Then we update:

```java
max = root.val;
```

if the current node becomes the new maximum.

We then recursively visit the left and right subtrees.

---

# Algorithm

1. Start DFS traversal from the root.
2. Initialize the maximum value as:
   ```text
   Integer.MIN_VALUE
   ```
3. For each node:
   - If `node.val >= max`, increment the good-node count.
   - Update `max` to `node.val` if necessary.
4. Recursively traverse the left subtree.
5. Recursively traverse the right subtree.
6. Pass the updated maximum value to both children.
7. Return the total count.

---

# Dry Run

Consider:

```text
root = [3,1,4,3,null,1,5]
```

Tree:

```text
        3
       / \
      1   4
     /   / \
    3   1   5
```

### Step 1: Root

Current node:

```text
3
```

Initial maximum:

```text
-∞
```

Since:

```text
3 >= -∞
```

`3` is good.

```text
count = 1
max = 3
```

---

### Step 2: Left Child

Current node:

```text
1
```

Maximum on path:

```text
3
```

Check:

```text
1 >= 3
```

False.

So `1` is not good.

```text
count = 1
max = 3
```

---

### Step 3: Left Grandchild

Current node:

```text
3
```

Maximum on path:

```text
3
```

Check:

```text
3 >= 3
```

True.

So this node is good.

```text
count = 2
```

---

### Step 4: Right Child of Root

Current node:

```text
4
```

Maximum:

```text
3
```

Check:

```text
4 >= 3
```

True.

So:

```text
count = 3
max = 4
```

---

### Step 5: Node `1`

Path:

```text
3 → 4 → 1
```

Maximum:

```text
4
```

Since:

```text
1 < 4
```

it is not good.

---

### Step 6: Node `5`

Path:

```text
3 → 4 → 5
```

Maximum:

```text
4
```

Since:

```text
5 >= 4
```

it is good.

```text
count = 4
```

Therefore:

```text
Answer = 4
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

    public int goodNodes(TreeNode root) {

        int max = Integer.MIN_VALUE;

        int[] count = {0};

        traverse(max, root, count);

        return count[0];
    }

    public void traverse(
            int max,
            TreeNode root,
            int[] count) {

        if (root == null) {
            return;
        }

        // Check whether current node is good
        if (max <= root.val) {
            max = root.val;
            count[0]++;
        }

        // Traverse left subtree
        if (root.left != null) {
            traverse(max, root.left, count);
        }

        // Traverse right subtree
        if (root.right != null) {
            traverse(max, root.right, count);
        }
    }
}
```

---

# Code Explanation

### Initialize Maximum

```java
int max = Integer.MIN_VALUE;
```

Before visiting the root, there is no value on the path.

Using `Integer.MIN_VALUE` ensures that the root will always satisfy:

```text
root.val >= max
```

and therefore the root is always counted as a good node.

---

### Count Good Node

```java
if(max <= root.val){
    max = root.val;
    count[0]++;
}
```

If the current value is greater than or equal to the maximum value seen so far, the current node is good.

We then update `max`.

---

### Why Use `int[] count`?

Java passes primitive `int` values by value.

If we used:

```java
int count
```

inside the recursive method, changes would not be reflected outside the method.

Using:

```java
int[] count = {0};
```

allows all recursive calls to modify the same array element:

```java
count[0]++;
```

---

### Recursive DFS

```java
traverse(max, root.left, count);
traverse(max, root.right, count);
```

The updated maximum is passed to both children.

Each child therefore knows the maximum value that occurred on its path from the root.

---

# Complexity Analysis

Let `n` be the number of nodes in the binary tree.

### Time Complexity

```text
O(n)
```

Every node is visited exactly once.

### Space Complexity

```text
O(h)
```

where `h` is the height of the tree.

This space is used by the recursion stack.

For a balanced tree:

```text
O(log n)
```

For a completely skewed tree:

```text
O(n)
```

---

# Key Concepts

### 1. Binary Tree DFS

We visit every node using Depth-First Search.

### 2. Recursion

The recursive function processes the current node and then recursively processes its children.

### 3. Path Maximum

For every node, we keep track of the maximum value encountered from the root to that node.

### 4. Good Node

A node is good when:

```text
node.val >= maximum value on its path
```

### 5. Passing State Through Recursion

The `max` value represents information about the current root-to-node path and is passed to the children.

---

# Constraints

- The number of nodes in the tree is between `1` and `100000`.
- `-10000 <= Node.val <= 10000`.

---

# Learning Outcome

After solving this problem, you should understand:

- How to perform DFS on a binary tree.
- How to maintain information about the current root-to-node path.
- How to identify nodes based on the maximum value seen so far.
- How recursion can carry state from parent to child.
- How to solve tree traversal problems in `O(n)` time.
- How the recursion stack affects space complexity.