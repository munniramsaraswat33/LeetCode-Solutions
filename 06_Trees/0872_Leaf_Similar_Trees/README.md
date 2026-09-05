# 872. Leaf-Similar Trees

**Difficulty:** Easy  
**Topics:** Tree, Depth-First Search, Binary Tree

---

## Problem Statement

Consider all the leaves of a binary tree from left to right.

The **leaf value sequence** is the sequence of values of all leaf nodes, ordered from left to right.

Two binary trees are called **leaf-similar** if their leaf value sequences are the same.

Return `true` if the two given binary trees are leaf-similar, otherwise return `false`.

A leaf node is a node whose:

```text
left == null
right == null
```

---

## Example 1

### Input

```text
root1 = [3,5,1,6,2,9,8,null,null,7,4]
root2 = [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8]
```

### Output

```text
true
```

### Explanation

The leaf sequence of `root1` is:

```text
[6, 7, 4, 9, 8]
```

The leaf sequence of `root2` is:

```text
[6, 7, 4, 9, 8]
```

Both sequences are identical.

Therefore:

```text
true
```

---

## Example 2

### Input

```text
root1 = [1,2,3]
root2 = [1,3,2]
```

### Output

```text
false
```

### Explanation

Leaf sequence of `root1`:

```text
[2, 3]
```

Leaf sequence of `root2`:

```text
[3, 2]
```

The sequences are different.

Therefore:

```text
false
```

---

# Approach

We use **Depth-First Search (DFS)** to traverse both trees.

During traversal, whenever we find a leaf node, we add its value to an `ArrayList`.

We create two lists:

```text
list1 → leaf sequence of root1
list2 → leaf sequence of root2
```

Finally, compare both lists using:

```java
list1.equals(list2)
```

If the sequences are the same, return `true`.

---

# Intuition

Consider:

```text
        3
       / \
      5   1
     / \   \
    6   2   9
       / \
      7   4
```

The leaf nodes from left to right are:

```text
6 → 7 → 4 → 9
```

So the leaf sequence is:

```text
[6, 7, 4, 9]
```

We perform the same DFS traversal on both trees and compare their leaf sequences.

The internal structure of the trees does not matter.

Only the values of the leaves and their left-to-right order matter.

---

# Algorithm

1. Create two empty lists.
2. Perform DFS on `root1`.
3. Whenever a leaf node is found, add its value to `list1`.
4. Perform DFS on `root2`.
5. Whenever a leaf node is found, add its value to `list2`.
6. Compare `list1` and `list2`.
7. Return the result.

---

# Dry Run

Consider:

```text
root1 = [3,5,1,6,2,9,8,null,null,7,4]
```

Its leaf nodes from left to right are:

```text
6 → 7 → 4 → 9 → 8
```

So:

```text
list1 = [6, 7, 4, 9, 8]
```

Suppose the second tree produces:

```text
list2 = [6, 7, 4, 9, 8]
```

Now:

```java
list1.equals(list2)
```

becomes:

```text
[6,7,4,9,8].equals([6,7,4,9,8])
```

which is:

```text
true
```

Therefore, the trees are leaf-similar.

---

# Java Solution

```java
class Solution {
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();

        leafNode(root1, list1);
        leafNode(root2, list2);

        return list1.equals(list2);
    }

    public void leafNode(TreeNode root, ArrayList<Integer> list) {
        if(root == null){
            return;
        }

        if(root.left == null && root.right == null){
            list.add(root.val);
            return;
        }

        if(root.left != null){
            leafNode(root.left, list);
        }

        if(root.right != null){
            leafNode(root.right, list);
        }
    }
}
```

---

# Code Explanation

### Create Lists

```java
ArrayList<Integer> list1 = new ArrayList<>();
ArrayList<Integer> list2 = new ArrayList<>();
```

These lists store the leaf values of the two trees.

---

### Traverse Both Trees

```java
leafNode(root1, list1);
leafNode(root2, list2);
```

The `leafNode()` method performs DFS and stores every leaf value.

---

### Check Leaf Node

```java
if(root.left == null && root.right == null){
    list.add(root.val);
    return;
}
```

A node is a leaf if it has no left and right child.

Its value is added to the list.

---

### Traverse Left and Right

```java
if(root.left != null){
    leafNode(root.left, list);
}

if(root.right != null){
    leafNode(root.right, list);
}
```

We visit the left subtree first and then the right subtree.

Therefore, leaf values are stored in **left-to-right order**.

---

### Compare Lists

```java
return list1.equals(list2);
```

`ArrayList.equals()` compares both:

- Size
- Elements
- Order

So it correctly checks whether the two leaf sequences are identical.

---

# Complexity Analysis

Let `n` and `m` be the number of nodes in the two trees.

### Time Complexity

```text
O(n + m)
```

Every node in both trees is visited once.

Comparing the two leaf lists also takes linear time.

### Space Complexity

```text
O(n + m)
```

The lists store the leaf nodes, and recursion uses the tree height.

---

# Key Concepts / Patterns

- Binary Tree
- Depth-First Search (DFS)
- Recursion
- Leaf Nodes
- ArrayList
- Tree Traversal

---

# Important Point

The trees do **not** need to have the same structure.

Only their leaf sequences need to be the same.

For example:

```text
Tree 1 leaves → [4, 5, 6]
Tree 2 leaves → [4, 5, 6]
```

Even if their internal structures are different, they are leaf-similar.

---

# Learning Outcome

- Learn how to identify leaf nodes in a binary tree.
- Practice DFS and recursive tree traversal.
- Understand how to preserve left-to-right traversal order.
- Learn how to compare two sequences using `ArrayList.equals()`.
- Understand that two trees can have different structures but still be leaf-similar.