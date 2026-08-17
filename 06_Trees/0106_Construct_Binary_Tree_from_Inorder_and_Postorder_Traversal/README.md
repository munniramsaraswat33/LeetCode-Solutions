# 106. Construct Binary Tree from Inorder and Postorder Traversal

> **Difficulty:** Medium  
> **Topics:** Array, Hash Map, Tree, Binary Tree, Recursion

---

## Problem Statement

You are given two integer arrays:

- `inorder` — the inorder traversal of a binary tree.
- `postorder` — the postorder traversal of the same binary tree.

Construct the original binary tree and return its root.

### Traversal Rules

**Inorder:**

```text
Left → Root → Right
```

**Postorder:**

```text
Left → Right → Root
```

The values in the arrays are unique.

---

## Example 1

### Input

```text
inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
```

### Output

```text
[3,9,20,null,null,15,7]
```

The constructed tree is:

```text
        3
       / \
      9   20
         /  \
        15   7
```

---

## Example 2

### Input

```text
inorder = [-1]
postorder = [-1]
```

### Output

```text
[-1]
```

---

# Approach

We use:

1. **Postorder traversal** to identify the root.
2. **Inorder traversal** to divide the tree into left and right subtrees.
3. A **HashMap** to quickly find the position of every value in the inorder array.
4. **Recursion** to construct the complete tree.

---

# Key Observation

The most important property is:

### Postorder

```text
Left → Right → Root
```

Therefore, the **last element** of postorder is always the root.

For:

```text
postorder = [9,15,7,20,3]
```

the last element is:

```text
3
```

So:

```text
Root = 3
```

---

# Finding Left and Right Subtrees

Now find `3` in inorder:

```text
inorder = [9,3,15,20,7]
             ↑
             3
```

Everything before `3` belongs to the left subtree:

```text
[9]
```

Everything after `3` belongs to the right subtree:

```text
[15,20,7]
```

So the tree becomes:

```text
        3
       / \
      9   ?
```

---

# Important Trick: Build Right Subtree First

We process postorder from **right to left**.

For:

```text
postorder = [9,15,7,20,3]
```

After taking `3`, the next element from the right is:

```text
20
```

`20` belongs to the right subtree.

Then:

```text
7
```

Then:

```text
15
```

So while processing postorder backwards, we must construct:

```text
Root → Right → Left
```

That is why the code does:

```java
root.right = helper(...);
root.left = helper(...);
```

instead of constructing the left subtree first.

---

# HashMap

We store the index of every value in the inorder array.

```java
HashMap<Integer, Integer> map = new HashMap<>();

for(int i = 0; i < inorder.length; i++){
    map.put(inorder[i], i);
}
```

For:

```text
inorder = [9,3,15,20,7]
```

the map becomes:

```text
9  → 0
3  → 1
15 → 2
20 → 3
7  → 4
```

Now we can find the position of any root in:

```text
O(1)
```

average time.

---

# `postIndex`

We maintain a pointer:

```java
postIndex = postorder.length - 1;
```

Initially:

```text
postIndex = 4
```

So:

```text
postorder[4] = 3
```

is the root.

After creating the root:

```java
postIndex--;
```

Now:

```text
postIndex = 3
```

Next:

```text
postorder[3] = 20
```

and so on.

---

# Dry Run

Given:

```text
inorder   = [9,3,15,20,7]
postorder = [9,15,7,20,3]
```

### Step 1: Root

Last postorder element:

```text
3
```

Create:

```text
    3
```

Inorder position of `3`:

```text
index = 1
```

Therefore:

```text
Left  = [9]
Right = [15,20,7]
```

---

### Step 2: Right Subtree

Next postorder element from right:

```text
20
```

Create:

```text
    3
     \
      20
```

Inorder position:

```text
20 → index 3
```

Right subtree of `20`:

```text
[7]
```

Left subtree:

```text
[15]
```

---

### Step 3: Right of `20`

Next element:

```text
7
```

Create:

```text
      20
        \
         7
```

---

### Step 4: Left of `20`

Next element:

```text
15
```

Create:

```text
      20
     /  \
    15   7
```

---

### Step 5: Left of `3`

Finally:

```text
9
```

Create:

```text
        3
       / \
      9   20
         /  \
        15   7
```

Final tree:

```text
        3
       / \
      9   20
         /  \
        15   7
```

---

# Java Solution

```java
class Solution {

    int postIndex;

    public TreeNode buildTree(int[] inorder, int[] postorder) {

        if (inorder == null || postorder == null ||
            inorder.length != postorder.length) {
            return null;
        }

        postIndex = postorder.length - 1;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return helper(postorder, map, 0, inorder.length - 1);
    }

    private TreeNode helper(
        int[] postorder,
        HashMap<Integer, Integer> map,
        int start,
        int end
    ) {

        if (start > end) {
            return null;
        }

        int treeNode = postorder[postIndex];

        TreeNode root = new TreeNode(treeNode);

        postIndex--;

        int inIndex = map.get(treeNode);

        root.right = helper(
            postorder,
            map,
            inIndex + 1,
            end
        );

        root.left = helper(
            postorder,
            map,
            start,
            inIndex - 1
        );

        return root;
    }
}
```

---

# Why Right Before Left?

This is the most important part of this solution:

```java
root.right = helper(...);
root.left = helper(...);
```

Normally, postorder is:

```text
Left → Right → Root
```

But we are reading it backwards:

```text
Root → Right → Left
```

Therefore, after creating the root, the next node belongs to the **right subtree**.

So we must build:

```text
Root
 ↓
Right
 ↓
Left
```

---

# Recursion Structure

The helper function receives the valid range of the inorder array:

```java
helper(postorder, map, start, end)
```

For example:

```text
inorder = [9,3,15,20,7]

          0       4
          ↓       ↓
        [9,3,15,20,7]
```

If root `3` is at index `1`:

```text
Left subtree:
start = 0
end   = 0

Right subtree:
start = 2
end   = 4
```

So:

```text
left  → [9]
right → [15,20,7]
```

---

# Base Case

If:

```java
start > end
```

there is no node in that subtree.

Therefore:

```java
if(start > end){
    return null;
}
```

---

# Complexity Analysis

Let:

```text
n = number of nodes
```

### Building the HashMap

We traverse the inorder array once:

```text
O(n)
```

### Tree Construction

Each node is processed exactly once.

HashMap lookup:

```text
O(1)
```

Average.

Therefore:

### Time Complexity

```text
O(n)
```

### Space Complexity

HashMap:

```text
O(n)
```

Recursion stack:

```text
O(h)
```

where `h` is the height of the tree.

Overall:

```text
O(n)
```

---

# Why HashMap?

Without a HashMap, for every root we would have to search the inorder array to find its position.

That could lead to:

```text
O(n²)
```

time complexity.

With:

```java
map.get(treeNode)
```

we find the index in approximately:

```text
O(1)
```

So the overall solution becomes:

```text
O(n)
```

---

# Key Concepts

- Binary Tree
- Inorder Traversal
- Postorder Traversal
- Recursion
- HashMap
- Divide and Conquer
- Tree Construction

---

# Important Pattern

Remember this pattern:

```text
Inorder:
Left → Root → Right

Postorder:
Left → Right → Root
```

When processing postorder from the end:

```text
Root → Right → Left
```

Therefore:

```java
root.right = helper(...);
root.left = helper(...);
```

---

# Learning Outcome

The main idea to remember is:

> **The last element of postorder is the root. Use its position in inorder to divide the tree into left and right subtrees, then recursively build the right subtree first because postorder is being processed from right to left.**

### Complexity

```text
Time:  O(n)
Space: O(n)
```