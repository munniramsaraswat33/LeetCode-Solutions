# 199. Binary Tree Right Side View

**Difficulty:** Medium  
**Topics:** Tree, Binary Tree, Breadth-First Search

---

## Problem Statement

Given the root of a binary tree, imagine yourself standing on the **right side** of it.

Return the values of the nodes you can see ordered from top to bottom.

For every level of the binary tree, the rightmost node is visible from the right side.

---

## Example 1

### Input

```text
root = [1,2,3,null,5,null,4]
```

### Output

```text
[1,3,4]
```

### Explanation

The binary tree is:

```text
        1
       / \
      2   3
       \   \
        5   4
```

From the right side, we can see:

```text
Level 1 → 1
Level 2 → 3
Level 3 → 4
```

Therefore:

```text
[1, 3, 4]
```

---

## Example 2

### Input

```text
root = [1,2,3,4,null,null,null,5]
```

### Output

```text
[1,3,4,5]
```

---

## Example 3

### Input

```text
root = []
```

### Output

```text
[]
```

### Explanation

The tree is empty, so there are no nodes visible.

---

# Approach

We use **Breadth-First Search (BFS)**, also called **level-order traversal**.

BFS processes the tree one level at a time.

For every level:

1. Find the number of nodes in that level.
2. Process all nodes of the level.
3. The **last node** processed at that level is the rightmost node.
4. Add its value to the answer.

We use a queue to store the nodes.

---

# Intuition

Consider this tree:

```text
        1
       / \
      2   3
     /     \
    4       5
```

BFS processes:

```text
Level 1 → [1]
Level 2 → [2, 3]
Level 3 → [4, 5]
```

The last node of each level is:

```text
1
3
5
```

Therefore, the right-side view is:

```text
[1, 3, 5]
```

Since we add the **last node of every level**, we get exactly the nodes visible from the right side.

---

# Algorithm

1. Create an empty result list `ans`.
2. If `root == null`, return the empty list.
3. Create a queue and add the root.
4. While the queue is not empty:
   - Store the current level size.
   - Process all nodes of that level.
   - If the current node is the last node of the level, add its value to `ans`.
   - Add its left and right children to the queue.
5. Return `ans`.

---

# Dry Run

Consider:

```text
root = [1,2,3,null,5,null,4]
```

Tree:

```text
        1
       / \
      2   3
       \   \
        5   4
```

### Level 1

Queue:

```text
[1]
```

`size = 1`

The only node is also the last node.

Add:

```text
1
```

Answer:

```text
[1]
```

---

### Level 2

Queue:

```text
[2, 3]
```

`size = 2`

Process `2`:

```text
i = 0
```

It is not the last node.

Process `3`:

```text
i = 1
size - 1 = 1
```

It is the last node.

Add:

```text
3
```

Answer:

```text
[1, 3]
```

---

### Level 3

Queue:

```text
[5, 4]
```

Process `5`.

It is not the last node.

Process `4`.

It is the last node.

Add:

```text
4
```

Answer:

```text
[1, 3, 4]
```

---

# Java Solution

```java
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();

        if(root == null){
            return ans;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()){
            int size = q.size();

            for(int i = 0; i < size; i++){
                TreeNode curr = q.remove();

                if(i == size - 1){
                    ans.add(curr.val);
                }

                if(curr.left != null){
                    q.add(curr.left);
                }

                if(curr.right != null){
                    q.add(curr.right);
                }
            }
        }

        return ans;
    }
}
```

---

# Code Explanation

### Result List

```java
List<Integer> ans = new ArrayList<>();
```

Stores the rightmost node from every level.

---

### Empty Tree

```java
if(root == null){
    return ans;
}
```

If the tree is empty, return an empty list.

---

### Queue

```java
Queue<TreeNode> q = new LinkedList<>();
q.add(root);
```

The queue is used for level-order traversal.

---

### Get Level Size

```java
int size = q.size();
```

This tells us how many nodes belong to the current level.

---

### Find the Rightmost Node

```java
if(i == size - 1){
    ans.add(curr.val);
}
```

The last node processed in each level is the rightmost node.

Therefore, we add its value to the answer.

---

### Add Children

```java
if(curr.left != null){
    q.add(curr.left);
}

if(curr.right != null){
    q.add(curr.right);
}
```

The children are added to the queue for processing in the next level.

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
O(n)
```

The queue can contain up to `O(n)` nodes in the worst case.

---

# Key Concepts / Patterns

- Binary Tree
- Breadth-First Search (BFS)
- Level Order Traversal
- Queue
- Rightmost Node of Each Level

---

# Learning Outcome

- Learn how to perform level-order traversal using BFS.
- Understand how to identify the last node of every tree level.
- Learn how a queue can be used to process a binary tree level by level.
- Recognize that the right-side view can be obtained by selecting the rightmost node from each level.