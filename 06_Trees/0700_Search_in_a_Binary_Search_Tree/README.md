# 700. Search in a Binary Search Tree

**Difficulty:** Easy  
**Topics:** Tree, Binary Search Tree, Recursion

---

## Problem Statement

You are given the root of a Binary Search Tree (BST) and an integer `val`.

Search for the node whose value is equal to `val`.

If the node exists, return the subtree rooted at that node.

If the value does not exist in the BST, return `null`.

A Binary Search Tree follows this property:

```text
Left subtree  <  Root  <  Right subtree
```

This property allows us to decide which subtree to search.

---

## Example 1

### Input

```text
root = [4,2,7,1,3]
val = 2
```

### Output

```text
[2,1,3]
```

### Explanation

The node with value `2` is found.

The subtree rooted at `2` is:

```text
    2
   / \
  1   3
```

So we return this subtree.

---

## Example 2

### Input

```text
root = [4,2,7,1,3]
val = 5
```

### Output

```text
[]
```

### Explanation

The value `5` does not exist in the BST, so we return `null`.

---

# Approach

We use the **Binary Search Tree property**.

At every node:

- If `val == root.val`, we found the required node.
- If `val < root.val`, search in the left subtree.
- If `val > root.val`, search in the right subtree.

There is no need to search both subtrees because the BST property tells us exactly where the value can exist.

---

# Intuition

Consider:

```text
        4
       / \
      2   7
     / \
    1   3
```

Suppose:

```text
val = 3
```

Start at `4`.

Since:

```text
3 < 4
```

go left.

Now at `2`.

Since:

```text
3 > 2
```

go right.

Now at `3`.

```text
3 == 3
```

We found the required node.

Therefore, return the node `3`.

---

# Algorithm

1. If `root == null`, return `null`.
2. If `root.val == val`, return `root`.
3. If `val < root.val`, recursively search the left subtree.
4. Otherwise, recursively search the right subtree.
5. Return the result.

---

# Dry Run

Consider:

```text
root = [4,2,7,1,3]
val = 3
```

Tree:

```text
        4
       / \
      2   7
     / \
    1   3
```

### Step 1

Current node:

```text
4
```

Target:

```text
3
```

Since:

```text
3 < 4
```

search left.

---

### Step 2

Current node:

```text
2
```

Since:

```text
3 > 2
```

search right.

---

### Step 3

Current node:

```text
3
```

Since:

```text
3 == 3
```

the node is found.

Return:

```text
3
```

The returned subtree is:

```text
3
```

---

# Java Solution

```java
class Solution {
    public TreeNode searchBST(TreeNode root, int val) {
        if(root == null){
            return null;
        }

        if(val == root.val){
            return root;
        }
        else if(val < root.val){
            return searchBST(root.left, val);
        }
        else{
            return searchBST(root.right, val);
        }
    }
}
```

---

# Code Explanation

### Base Case

```java
if(root == null){
    return null;
}
```

If we reach an empty subtree, the value does not exist.

---

### Value Found

```java
if(val == root.val){
    return root;
}
```

If the current node contains the target value, return the current node.

The complete subtree rooted at this node is returned automatically.

---

### Search Left

```java
else if(val < root.val){
    return searchBST(root.left, val);
}
```

If the target is smaller than the current value, the BST property guarantees that it can only be in the left subtree.

---

### Search Right

```java
else{
    return searchBST(root.right, val);
}
```

If the target is greater than the current value, search the right subtree.

---

# Why We Don't Search Both Subtrees

In a normal binary tree, a value could exist anywhere, so we might need to search both subtrees.

But in a BST:

```text
Values smaller than root → Left
Values greater than root → Right
```

Therefore, at every step we eliminate half of the possible directions conceptually.

This makes BST search much more efficient than searching a general binary tree.

---

# Complexity Analysis

Let `h` be the height of the BST.

### Time Complexity

```text
O(h)
```

We visit at most one path from the root to the target.

For a balanced BST:

```text
O(log n)
```

For a skewed BST:

```text
O(n)
```

### Space Complexity

```text
O(h)
```

because recursion uses the call stack.

For a balanced BST:

```text
O(log n)
```

For a skewed BST:

```text
O(n)
```

---

# Key Concepts / Patterns

- Binary Search Tree
- Recursion
- BST Search
- Binary Tree
- Divide and Conquer
- Recursive Traversal

---

# Learning Outcome

- Understand how searching works in a Binary Search Tree.
- Learn how the BST property reduces unnecessary searching.
- Practice recursive tree traversal.
- Understand the difference between searching a normal binary tree and a BST.
- Learn how to return an entire subtree once the target node is found.