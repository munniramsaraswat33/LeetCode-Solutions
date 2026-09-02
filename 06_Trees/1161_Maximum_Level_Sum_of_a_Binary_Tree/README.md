# 1161. Maximum Level Sum of a Binary Tree

> **Difficulty:** Medium  
> **Topics:** Binary Tree, BFS, Queue, Level Order Traversal

---

## Problem Statement

Given the root of a binary tree, return the **smallest level** that has the maximum sum of node values.

The root is considered to be at:

```text
Level 1
```

The children of the root are at:

```text
Level 2
```

and so on.

For every level, calculate the sum of all node values at that level and return the level number having the maximum sum.

If multiple levels have the same maximum sum, return the smallest level number.

---

## Examples

### Example 1

```text
Input:
root = [1,7,0,7,-8,null,null]

Output:
2
```

### Explanation

The tree is:

```text
        1
       / \
      7   0
     / \
    7  -8
```

Level sums:

```text
Level 1 → 1
Level 2 → 7 + 0 = 7
Level 3 → 7 + (-8) = -1
```

The maximum sum is:

```text
7
```

at level `2`.

Therefore:

```text
Answer = 2
```

---

### Example 2

```text
Input:
root = [989,null,10250,98693,-89388,null,null,null,-32127]

Output:
2
```

### Explanation

We calculate the sum of every level.

The maximum level sum occurs at:

```text
Level 2
```

Therefore:

```text
Answer = 2
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

There is only one level:

```text
Level 1 → 1
```

So the answer is:

```text
1
```

---

# Approach

The problem asks for the sum of nodes **level by level**.

This naturally suggests **Breadth-First Search (BFS)** or **Level Order Traversal**.

We use a `Queue` to process the tree one level at a time.

For every level:

1. Find the number of nodes currently in the queue.
2. Process exactly those nodes.
3. Calculate their sum.
4. Add their children to the queue.
5. Compare the current level sum with the maximum sum found so far.

The important part is:

```java
int size = q.size();
```

At the beginning of every iteration, `size` represents the number of nodes in the current level.

Therefore, we process exactly `size` nodes before moving to the next level.

---

# Algorithm

1. Create a queue and add the root node.
2. Initialize:
   ```text
   level = 1
   answer = 1
   maxsum = Integer.MIN_VALUE
   ```
3. While the queue is not empty:
   - Store the current level size.
   - Initialize `levelSum = 0`.
4. Process all nodes of the current level:
   - Remove a node from the queue.
   - Add its value to `levelSum`.
   - Add its left child to the queue if it exists.
   - Add its right child to the queue if it exists.
5. After processing the level:
   - If `levelSum > maxsum`, update:
     ```text
     maxsum = levelSum
     answer = level
     ```
6. Increment the level.
7. Continue until the queue becomes empty.
8. Return `answer`.

---

# Dry Run

Consider:

```text
root = [1,7,0,7,-8,null,null]
```

Tree:

```text
        1
       / \
      7   0
     / \
    7  -8
```

### Level 1

Queue:

```text
[1]
```

```text
size = 1
```

Process:

```text
1
```

Level sum:

```text
levelSum = 1
```

Since:

```text
1 > Integer.MIN_VALUE
```

update:

```text
maxsum = 1
answer = 1
```

Add children:

```text
Queue = [7, 0]
```

---

### Level 2

Queue:

```text
[7, 0]
```

```text
size = 2
```

Process both nodes:

```text
7 + 0 = 7
```

So:

```text
levelSum = 7
```

Since:

```text
7 > 1
```

update:

```text
maxsum = 7
answer = 2
```

Add children of `7`:

```text
7
-8
```

Queue becomes:

```text
[7, -8]
```

---

### Level 3

Queue:

```text
[7, -8]
```

Process:

```text
7 + (-8) = -1
```

So:

```text
levelSum = -1
```

Since:

```text
-1 < 7
```

we do not update the answer.

---

### Final Answer

The maximum level sum was:

```text
7
```

at:

```text
Level 2
```

Therefore:

```text
Answer = 2
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

    public int maxLevelSum(TreeNode root) {

        Queue<TreeNode> q = new LinkedList<>();

        q.add(root);

        int level = 1;
        int answer = 1;

        int maxsum = Integer.MIN_VALUE;

        while (!q.isEmpty()) {

            // Number of nodes in the current level
            int size = q.size();

            int levelSum = 0;

            for (int i = 0; i < size; i++) {

                TreeNode curr = q.remove();

                // Add current node's value
                levelSum += curr.val;

                // Add children for the next level
                if (curr.left != null) {
                    q.add(curr.left);
                }

                if (curr.right != null) {
                    q.add(curr.right);
                }
            }

            // Update maximum level sum
            if (maxsum < levelSum) {
                maxsum = levelSum;
                answer = level;
            }

            level++;
        }

        return answer;
    }
}
```

---

# Code Explanation

### Queue

```java
Queue<TreeNode> q = new LinkedList<>();
```

The queue is used to perform **BFS / Level Order Traversal**.

We initially add the root:

```java
q.add(root);
```

---

### Level Size

```java
int size = q.size();
```

This is the most important part of level-order traversal.

Suppose the queue contains:

```text
[7, 0]
```

Then:

```text
size = 2
```

We process exactly these two nodes.

Their children are added to the queue, but they will be processed in the **next** iteration.

---

### Calculate Level Sum

```java
levelSum += curr.val;
```

Every node belonging to the current level contributes to `levelSum`.

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

Children are inserted into the queue for processing at the next level.

---

### Update Maximum

```java
if(maxsum < levelSum){
    maxsum = levelSum;
    answer = level;
}
```

If the current level has a larger sum, update the answer.

Notice that we use:

```java
<
```

instead of:

```java
<=
```

This is important because if two levels have the same sum, we must return the **smallest level**.

Since levels are processed from top to bottom, keeping the existing answer automatically gives the smaller level.

---

# Complexity Analysis

Let `n` be the number of nodes in the binary tree.

### Time Complexity

```text
O(n)
```

Every node is added to and removed from the queue exactly once.

### Space Complexity

```text
O(w)
```

where `w` is the maximum width of the tree.

In the worst case:

```text
O(n)
```

---

# Key Concepts

### 1. Breadth-First Search

BFS processes nodes level by level.

### 2. Queue

A queue maintains the order in which tree nodes should be processed.

### 3. Level Order Traversal

The expression:

```java
int size = q.size();
```

allows us to process exactly one level at a time.

### 4. Level Sum

For every level, we calculate:

```text
sum of all node values
```

### 5. Maximum Tracking

We maintain the largest level sum and its corresponding level number.

### 6. Tie Handling

If two levels have the same sum, the first one remains the answer because we only update when:

```java
levelSum > maxsum
```

---

# Constraints

- The number of nodes in the tree is between `1` and `10000`.
- `-100000 <= Node.val <= 100000`.

---

# Learning Outcome

After solving this problem, you should understand:

- How to perform BFS on a binary tree.
- How to implement level-order traversal using a queue.
- How `q.size()` separates one level from the next.
- How to calculate values level by level.
- How to track the maximum value while traversing a tree.
- How to handle ties by keeping the smallest level.
- How to solve tree-level problems in `O(n)` time.