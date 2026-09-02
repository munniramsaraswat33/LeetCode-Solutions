# 236. Lowest Common Ancestor of a Binary Tree

> **Difficulty:** Medium  
> **Topics:** Binary Tree, DFS, Recursion

---

## Problem Statement

Given the root of a binary tree and two nodes `p` and `q`, find their **Lowest Common Ancestor (LCA)**.

The Lowest Common Ancestor of two nodes `p` and `q` is the lowest node in the tree that has both `p` and `q` as descendants.

A node can also be a descendant of itself.

---

## Examples

### Example 1

```text
Input:
root = [3,5,1,6,2,0,8,null,null,7,4]
p = 5
q = 1

Output:
3
```

### Explanation

The tree is:

```text
          3
        /   \
       5     1
      / \   / \
     6   2 0   8
        / \
       7   4
```

Node `5` is in the left subtree of `3` and node `1` is in the right subtree.

Therefore:

```text
LCA(5,1) = 3
```

---

### Example 2

```text
Input:
root = [3,5,1,6,2,0,8,null,null,7,4]
p = 5
q = 4

Output:
5
```

### Explanation

Node `4` is inside the subtree of node `5`.

Therefore, node `5` is the lowest node containing both `5` and `4`.

```text
LCA(5,4) = 5
```

---

### Example 3

```text
Input:
root = [1,2]
p = 1
q = 2

Output:
1
```

### Explanation

Node `1` is the parent of node `2`.

Therefore:

```text
LCA(1,2) = 1
```

---

# Approach

We use **Depth-First Search (DFS)** with recursion.

For every node, there are three important cases.

### Case 1: Current Node Is `null`

If:

```java
root == null
```

there is nothing to search.

Return:

```java
null
```

---

### Case 2: Current Node Is `p` or `q`

If the current node is either `p` or `q`:

```java
if(p == root || q == root){
    return root;
}
```

we return the current node.

This is important because one of `p` or `q` can itself be the LCA.

---

### Case 3: Search Both Subtrees

We recursively search:

```java
TreeNode left = lowestCommonAncestor(root.left, p, q);
TreeNode right = lowestCommonAncestor(root.right, p, q);
```

There are three possible outcomes.

#### Both Left and Right Are Not Null

```java
if(left != null && right != null){
    return root;
}
```

This means:

```text
p is in one subtree
q is in the other subtree
```

Therefore, the current node is their Lowest Common Ancestor.

---

#### Only Right Is Not Null

```java
if(left == null){
    return right;
}
```

This means both nodes are found in the right subtree, so we return the right result.

---

#### Only Left Is Not Null

```java
return left;
```

This means both nodes are found in the left subtree.

---

# Algorithm

1. Start DFS from the root.
2. If the current node is `null`, return `null`.
3. If the current node is `p` or `q`, return the current node.
4. Recursively search the left subtree.
5. Recursively search the right subtree.
6. If both left and right return a non-null node:
   - `p` and `q` are located in different subtrees.
   - Return the current node.
7. If only the left subtree returns a node, return it.
8. If only the right subtree returns a node, return it.
9. Continue until the Lowest Common Ancestor is found.

---

# Dry Run

Consider:

```text
root = [3,5,1,6,2,0,8,null,null,7,4]

p = 5
q = 1
```

Tree:

```text
          3
        /   \
       5     1
      / \   / \
     6   2 0   8
        / \
       7   4
```

### Step 1: Start at Node 3

```text
root = 3
```

Node `3` is neither `p` nor `q`.

So search both subtrees.

---

### Step 2: Search Left Subtree

Current node:

```text
5
```

Node `5` is `p`.

Therefore:

```text
left = 5
```

The recursion returns node `5`.

---

### Step 3: Search Right Subtree

Current node:

```text
1
```

Node `1` is `q`.

Therefore:

```text
right = 1
```

The recursion returns node `1`.

---

### Step 4: Back at Node 3

We now have:

```text
left  = 5
right = 1
```

Both are non-null:

```java
if(left != null && right != null)
```

Therefore:

```text
return 3
```

So:

```text
LCA(5,1) = 3
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
 *     TreeNode(int x) { val = x; }
 * }
 */

class Solution {

    public TreeNode lowestCommonAncestor(
            TreeNode root,
            TreeNode p,
            TreeNode q) {

        // If tree is empty
        if (root == null) {
            return null;
        }

        // If current node is p or q,
        // it can be the Lowest Common Ancestor
        if (p == root || q == root) {
            return root;
        }

        // Search in the left subtree
        TreeNode left =
                lowestCommonAncestor(root.left, p, q);

        // Search in the right subtree
        TreeNode right =
                lowestCommonAncestor(root.right, p, q);

        // p and q are found in different subtrees
        if (left != null && right != null) {
            return root;
        }

        // Return whichever subtree contains p or q
        if (left == null) {
            return right;
        }

        return left;
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

If we reach an empty subtree, there is no node to search.

---

### Check `p` or `q`

```java
if(p == root || q == root){
    return root;
}
```

If the current node itself is one of the target nodes, return it.

This also handles cases where one target node is an ancestor of the other.

---

### Search Left and Right

```java
TreeNode left =
    lowestCommonAncestor(root.left, p, q);

TreeNode right =
    lowestCommonAncestor(root.right, p, q);
```

We recursively search both subtrees.

Each recursive call returns:

- `null` if neither target was found.
- A target node if one target was found.
- The LCA if both targets were found below that subtree.

---

### Both Sides Return a Node

```java
if(left != null && right != null){
    return root;
}
```

If both sides contain a target, then the current node is where their paths meet.

Therefore:

```text
Current node = LCA
```

---

### Only One Side Returns a Node

```java
if(left == null){
    return right;
}

return left;
```

If only one subtree contains a target or an already-found LCA, propagate that result upward.

---

# Complexity Analysis

Let `n` be the number of nodes in the binary tree.

### Time Complexity

```text
O(n)
```

Each node is visited at most once.

### Space Complexity

```text
O(h)
```

where `h` is the height of the tree.

The space is used by the recursive call stack.

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

The tree is explored recursively using Depth-First Search.

### 2. Lowest Common Ancestor

The LCA is the lowest node that contains both target nodes in its subtree.

### 3. Recursive Return Values

Each recursive call communicates information back to its parent.

```text
null      → target not found
p or q    → one target found
LCA node  → both targets found below
```

### 4. Two Subtree Search

Searching both left and right subtrees allows us to determine whether `p` and `q` lie on different sides of the current node.

### 5. Ancestor Case

If the current node is itself `p` or `q`, it can be the LCA.

---

# Constraints

- The number of nodes in the tree is between `2` and `100000`.
- `-10^9 <= Node.val <= 10^9`
- All node values are unique.
- `p` and `q` are different nodes in the tree.
- Both `p` and `q` exist in the binary tree.

---

# Learning Outcome

After solving this problem, you should understand:

- How to find the Lowest Common Ancestor using DFS.
- How recursive return values can carry information from children to parents.
- How to identify when two target nodes are located in different subtrees.
- Why checking `root == p || root == q` handles the ancestor case.
- How to solve the LCA problem in `O(n)` time.
- How recursion can simplify binary tree relationship problems.