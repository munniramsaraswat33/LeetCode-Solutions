# 450. Delete Node in a BST

**Difficulty:** Medium  
**Topics:** Tree, Binary Search Tree, Recursion

---

## Problem Statement

Given the root of a Binary Search Tree (BST) and a key, delete the node with the given key from the BST.

Return the root of the modified BST.

The BST property must remain valid after deletion:

```text
Left subtree  <  Root  <  Right subtree
```

There are three possible cases when deleting a node:

1. The node has no children.
2. The node has one child.
3. The node has two children.

---

## Example

### Input

```text
root = [5,3,6,2,4,null,7]
key = 3
```

### Output

```text
[5,4,6,2,null,null,7]
```

### Explanation

The node `3` has two children.

Its inorder successor is `4`.

Replace `3` with `4`, then delete the original `4` from the right subtree.

The resulting BST is:

```text
        5
       / \
      4   6
     /     \
    2       7
```

---

# Approach

We use the **BST property** to search for the node efficiently.

For every node:

- If `key < root.val`, the key must be in the left subtree.
- If `key > root.val`, the key must be in the right subtree.
- If `key == root.val`, we found the node that needs to be deleted.

After finding the node, handle the three deletion cases.

---

# Three Cases of Deletion

## Case 1: No Left Child

```java
if(root.left == null){
    return root.right;
}
```

If the node has no left child, return its right child.

This also handles the case where both children are `null`.

---

## Case 2: No Right Child

```java
else if(root.right == null){
    return root.left;
}
```

If the node has no right child, return its left child.

---

## Case 3: Two Children

If the node has both left and right children, we cannot simply remove it.

We find the **smallest node in the right subtree**.

This node is called the **inorder successor**.

```java
TreeNode minNode = minNode(root.right);
```

Then replace the current node's value with the successor's value:

```java
root.val = minNode.val;
```

Finally, delete the duplicate successor node from the right subtree:

```java
root.right = deleteNode(root.right, root.val);
```

---

# Intuition

Consider:

```text
        5
       / \
      3   6
     / \   \
    2   4   7
```

Suppose we want to delete `3`.

Node `3` has two children:

```text
2 and 4
```

We choose the smallest value from its right subtree.

The right subtree of `3` contains:

```text
4
```

So the inorder successor is `4`.

Replace:

```text
3 → 4
```

Then remove the original `4`.

The tree becomes:

```text
        5
       / \
      4   6
     /     \
    2       7
```

The BST property is still maintained.

---

# Algorithm

1. If `root == null`, return `null`.
2. If `key < root.val`, recursively delete from the left subtree.
3. If `key > root.val`, recursively delete from the right subtree.
4. Otherwise, the current node is the node to delete.
5. If the node has no left child, return its right child.
6. If the node has no right child, return its left child.
7. If it has two children:
   - Find the minimum node in the right subtree.
   - Copy its value into the current node.
   - Delete that successor from the right subtree.
8. Return `root`.

---

# Dry Run

Consider:

```text
root = [5,3,6,2,4,null,7]
key = 3
```

Tree:

```text
        5
       / \
      3   6
     / \   \
    2   4   7
```

### Step 1

Start at `5`.

```text
key = 3
root.val = 5
```

Since:

```text
3 < 5
```

Move to the left subtree.

---

### Step 2

Now:

```text
root = 3
key = 3
```

We found the node.

Node `3` has two children.

---

### Step 3

Find the minimum node in the right subtree:

```text
right subtree = [4]
```

Minimum:

```text
4
```

---

### Step 4

Replace `3` with `4`.

```text
        5
       / \
      4   6
     / \   \
    2   4   7
```

There are now two nodes with value `4`.

---

### Step 5

Delete the duplicate `4` from the right subtree.

Final tree:

```text
        5
       / \
      4   6
     /     \
    2       7
```

---

# Java Solution

```java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root == null){
            return null;
        }

        if(key < root.val){
            root.left = deleteNode(root.left, key);
        }
        else if(key > root.val){
            root.right = deleteNode(root.right, key);
        }
        else{
            if(root.left == null){
                return root.right;
            }
            else if(root.right == null){
                return root.left;
            }

            TreeNode minNode = minNode(root.right);
            root.val = minNode.val;
            root.right = deleteNode(root.right, root.val);
        }

        return root;
    }

    public TreeNode minNode(TreeNode root){
        while(root.left != null){
            root = root.left;
        }

        return root;
    }
}
```

---

# Code Explanation

### Search for the Node

```java
if(key < root.val){
    root.left = deleteNode(root.left, key);
}
else if(key > root.val){
    root.right = deleteNode(root.right, key);
}
```

The BST property allows us to decide which subtree can contain the key.

---

### Node Found

```java
else{
```

When:

```text
key == root.val
```

we have found the node to delete.

---

### No Left Child

```java
if(root.left == null){
    return root.right;
}
```

The right child replaces the current node.

---

### No Right Child

```java
else if(root.right == null){
    return root.left;
}
```

The left child replaces the current node.

---

### Two Children

```java
TreeNode minNode = minNode(root.right);
```

Find the smallest node in the right subtree.

Then:

```java
root.val = minNode.val;
```

Copy the successor's value into the current node.

Finally:

```java
root.right = deleteNode(root.right, root.val);
```

Remove the original successor node.

---

### Finding Minimum

```java
public TreeNode minNode(TreeNode root){
    while(root.left != null){
        root = root.left;
    }
    return root;
}
```

In a BST, the smallest value is always the **leftmost node**.

So we keep moving left until there is no more left child.

---

# Complexity Analysis

Let `h` be the height of the BST.

### Time Complexity

```text
O(h)
```

We follow only one path while searching for the node.

Finding the minimum node also takes at most `O(h)`.

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

For a balanced tree:

```text
O(log n)
```

For a skewed tree:

```text
O(n)
```

---

# Key Concepts / Patterns

- Binary Search Tree
- Recursion
- BST Search
- Inorder Successor
- Tree Deletion
- Binary Tree

---

# Important Point

When deleting a node with two children, use the **inorder successor**:

```text
Smallest node in the right subtree
```

Alternatively, we could use the inorder predecessor:

```text
Largest node in the left subtree
```

In this solution, we use the inorder successor.

---

# Learning Outcome

- Understand how deletion works in a Binary Search Tree.
- Learn the three cases of BST node deletion.
- Understand how the BST property helps reduce the search space.
- Learn how to find the inorder successor.
- Practice recursive tree modification.
- Understand why a balanced BST gives `O(log n)` search/deletion complexity.