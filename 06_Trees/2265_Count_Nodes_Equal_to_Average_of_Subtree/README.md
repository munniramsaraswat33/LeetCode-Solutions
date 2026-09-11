# 2265. Count Nodes Equal to Average of Subtree

**LeetCode:** [2265. Count Nodes Equal to Average of Subtree](https://leetcode.com/problems/count-nodes-equal-to-average-of-subtree/)

**Difficulty:** Easy

**Primary Topic:** Trees

**Pattern:** Postorder DFS + Subtree Sum and Count

---

## Problem Statement

Given the root of a binary tree, return the number of nodes whose value is equal to the average of the values in their subtree.

For every node:

- The subtree includes the node itself.
- The average is calculated using integer division.

The average is:

```text
sum of values in subtree / number of nodes in subtree
```

If the integer average is equal to the current node's value, that node is counted.

---

## Example

### Input

```text
root = [4,8,5,0,1,null,6]
```

The tree is:

```text
        4
       / \
      8   5
     / \   \
    0   1   6
```

For each node, calculate the sum and number of nodes in its subtree.

For example, for node `5`:

```text
Subtree = [5,6]

sum = 11
count = 2

average = 11 / 2
        = 5
```

Because integer division gives:

```text
5 == node value 5
```

node `5` is counted.

The final answer is:

```text
5
```

---

# Approach

Use **Postorder DFS**.

For every node, we need two pieces of information from its subtree:

```text
1. Sum of all values
2. Number of nodes
```

Therefore, the DFS function returns:

```text
int[] {sum, count}
```

For the current node:

```text
subtree sum =
left sum + right sum + current value

subtree count =
left count + right count + 1
```

Then calculate:

```text
sum / count
```

and check whether it equals:

```text
root.val
```

---

# Why Postorder DFS?

We cannot calculate the average of a node's subtree until we know the information about:

```text
left subtree
right subtree
```

Therefore, we first process both children and then process the current node.

This is exactly:

```text
Left
Right
Root
```

which is **Postorder Traversal**.

---

# Intuition

Consider:

```text
        4
       / \
      8   5
     / \   \
    0   1   6
```

Start from the bottom.

For leaf node `0`:

```text
sum = 0
count = 1
average = 0
```

So node `0` is counted.

For leaf node `1`:

```text
sum = 1
count = 1
average = 1
```

So node `1` is counted.

Now node `8` receives information from both children:

```text
left  = {0,1}
right = {1,1}
```

Therefore:

```text
sum = 0 + 1 + 8
    = 9

count = 1 + 1 + 1
      = 3

average = 9 / 3
        = 3
```

Since:

```text
3 != 8
```

node `8` is not counted.

This bottom-up process continues until the root.

---

# Algorithm

1. Initialize a global variable:
   ```text
   ans = 0
   ```
2. Start DFS from the root.
3. If the current node is `null`, return:
   ```text
   {0, 0}
   ```
4. Recursively calculate information for the left subtree.
5. Recursively calculate information for the right subtree.
6. Calculate the current subtree's:
   - Sum
   - Number of nodes
7. Calculate:
   ```text
   sum / count
   ```
8. If the average equals the current node's value:
   ```text
   ans++
   ```
9. Return:
   ```text
   {sum, count}
   ```
10. Return `ans`.

---

# Dry Run

Consider:

```text
        4
       / \
      8   5
     / \   \
    0   1   6
```

---

## Node 0

```text
sum = 0
count = 1

average = 0 / 1
        = 0
```

```text
0 == 0
```

Count:

```text
ans = 1
```

Return:

```text
{0, 1}
```

---

## Node 1

```text
sum = 1
count = 1

average = 1 / 1
        = 1
```

```text
1 == 1
```

Count:

```text
ans = 2
```

Return:

```text
{1, 1}
```

---

## Node 8

Left subtree:

```text
{0, 1}
```

Right subtree:

```text
{1, 1}
```

Current node:

```text
8
```

Calculate:

```text
sum = 0 + 1 + 8
    = 9

count = 1 + 1 + 1
      = 3
```

Average:

```text
9 / 3 = 3
```

Since:

```text
3 != 8
```

do not increment `ans`.

Return:

```text
{9, 3}
```

---

## Node 6

Leaf node:

```text
sum = 6
count = 1

average = 6
```

So:

```text
ans = 3
```

Return:

```text
{6, 1}
```

---

## Node 5

Left subtree:

```text
{0, 0}
```

Right subtree:

```text
{6, 1}
```

Current node:

```text
5
```

Calculate:

```text
sum = 0 + 6 + 5
    = 11

count = 0 + 1 + 1
      = 2
```

Average:

```text
11 / 2 = 5
```

Because integer division is used:

```text
5 == 5
```

So:

```text
ans = 4
```

Return:

```text
{11, 2}
```

---

## Node 4

Left subtree:

```text
{9, 3}
```

Right subtree:

```text
{11, 2}
```

Current node:

```text
4
```

Calculate:

```text
sum = 9 + 11 + 4
    = 24

count = 3 + 2 + 1
      = 6
```

Average:

```text
24 / 6 = 4
```

Since:

```text
4 == 4
```

count:

```text
ans = 5
```

Final answer:

```text
5
```

---

# Java Solution

```java
class Solution {
    int ans = 0;

    public int averageOfSubtree(TreeNode root) {
        averageOftree(root);
        return ans;
    }

    public int[] averageOftree(TreeNode root) {
        if(root == null){
            return new int[]{0, 0};
        }

        int sum = 0;
        int count = 0;

        int[] left = averageOftree(root.left);
        int[] right = averageOftree(root.right);

        sum += left[0] + right[0] + root.val;
        count += left[1] + right[1] + 1;

        if(sum / count == root.val){
            ans++;
        }

        return new int[]{sum, count};
    }
}
```

---

# Code Explanation

## Global Answer

```java
int ans = 0;
```

This variable stores the total number of nodes satisfying:

```text
subtree average == node value
```

---

# Main Function

```java
public int averageOfSubtree(TreeNode root) {
    averageOftree(root);
    return ans;
}
```

Start the DFS from the root.

The DFS itself calculates the subtree information and updates `ans`.

---

# DFS Function

```java
public int[] averageOftree(TreeNode root)
```

The function returns an array containing:

```text
[0] → subtree sum
[1] → subtree node count
```

For example:

```text
{24, 6}
```

means:

```text
subtree sum   = 24
subtree count = 6
```

---

# Base Case

```java
if(root == null){
    return new int[]{0, 0};
}
```

A null subtree has:

```text
sum = 0
count = 0
```

This makes it easy to combine left and right subtree information.

---

# Process Left Subtree

```java
int[] left = averageOftree(root.left);
```

The recursive call returns:

```text
left[0] → left subtree sum
left[1] → left subtree count
```

---

# Process Right Subtree

```java
int[] right = averageOftree(root.right);
```

Similarly:

```text
right[0] → right subtree sum
right[1] → right subtree count
```

---

# Calculate Current Subtree Sum

```java
sum += left[0] + right[0] + root.val;
```

The current subtree consists of:

```text
left subtree
+
right subtree
+
current node
```

Therefore:

```text
sum = left sum + right sum + root value
```

---

# Calculate Current Subtree Count

```java
count += left[1] + right[1] + 1;
```

The `+1` represents the current node.

Therefore:

```text
count = left count + right count + 1
```

---

# Check Average

```java
if(sum / count == root.val){
    ans++;
}
```

Calculate the integer average:

```text
sum / count
```

If it equals the current node's value, increment the answer.

---

# Return Subtree Information

```java
return new int[]{sum, count};
```

The parent node needs this information to calculate its own subtree average.

This is the key idea of the recursive solution:

```text
Child
 ↓
Returns sum + count
 ↓
Parent uses them
```

---

# Why Integer Division Is Correct

The problem uses the integer average.

For example:

```text
sum = 11
count = 2
```

Then:

```text
11 / 2 = 5
```

in Java integer division.

So if the node value is:

```text
5
```

the condition:

```java
sum / count == root.val
```

is true.

---

# Tree DP Perspective

This solution can also be viewed as a simple form of **Tree DP**.

Each node computes information about its subtree:

```text
State returned by DFS:

(sum, count)
```

The parent combines the states of its children:

```text
parent sum =
left sum + right sum + parent value

parent count =
left count + right count + 1
```

Then the parent determines whether it satisfies the condition.

---

# Complexity Analysis

Let:

```text
n = number of nodes in the tree
```

## Time Complexity

Every node is visited exactly once.

At every node, we perform constant work.

Therefore:

```text
O(n)
```

---

## Space Complexity

The recursive DFS uses the call stack.

In the worst case, the tree can be completely skewed:

```text
1
 \
  2
   \
    3
     \
      4
```

The recursion depth becomes `n`.

Therefore:

```text
O(n)
```

auxiliary space in the worst case.

---

# Key Concepts

## 1. Postorder DFS

The order is:

```text
Left
Right
Root
```

We need the children first because their sum and count are required to calculate the parent's subtree average.

---

## 2. Subtree Sum

For every node:

```text
subtree sum =
left sum + right sum + node value
```

---

## 3. Subtree Count

For every node:

```text
subtree count =
left count + right count + 1
```

---

## 4. Returning Multiple Values

The DFS returns:

```java
int[]
```

containing:

```text
sum
count
```

This allows the parent to receive all information needed from the child.

---

# Pattern Recognition

Whenever a binary-tree problem asks about something involving the **entire subtree**, consider:

```text
Postorder DFS
```

If the parent needs information calculated from its children, return that information from DFS.

Common examples include:

```text
Subtree Sum
Subtree Size
Subtree Average
Maximum Path Information
Height
Balance Information
```

For this problem:

```text
Subtree Average
       ↓
Need Sum + Count
       ↓
Postorder DFS
```

---

# Common Mistakes

## Mistake 1: Using Preorder DFS

You cannot calculate the subtree average before processing the children.

The children contain part of the subtree.

Therefore, postorder traversal is natural.

---

## Mistake 2: Forgetting the Current Node

The subtree includes the current node itself.

So:

```java
sum += root.val;
```

and:

```java
count += 1;
```

are required.

---

## Mistake 3: Counting Only Leaves

Every node can be a candidate.

The condition must be checked for every node:

```java
if(sum / count == root.val)
```

---

## Mistake 4: Returning Only the Sum

The average requires both:

```text
sum
count
```

Knowing only the sum is not enough.

---

# Edge Cases

## Single Node

```text
root = [5]
```

Subtree:

```text
sum = 5
count = 1
average = 5
```

So the answer is:

```text
1
```

---

## Null Root

If the root is null, there are no nodes.

The DFS returns:

```text
{0,0}
```

and no node is counted.

---

## Leaf Node

Every leaf has:

```text
sum = node value
count = 1
average = node value
```

Therefore, every leaf node satisfies the condition and is counted.

---

# Learning Outcome

After solving this problem, you should understand:

- How to use postorder DFS for subtree problems.
- How to calculate subtree sum and size recursively.
- How to return multiple values from a DFS function.
- How to perform integer average calculations.
- How bottom-up tree processing works.
- How a tree problem can be viewed as a simple Tree DP.

---

# Final Takeaway

The main idea is:

```text
For every node:

Get information from left subtree
          +
Get information from right subtree
          +
Current node
          ↓
Calculate subtree sum and count
          ↓
Calculate average
          ↓
Check if average == node value
```

The DFS returns:

```text
{sum, count}
```

and the parent uses this information to solve its own subtree.

**Time Complexity:** `O(n)`

**Space Complexity:** `O(n)` worst-case recursion stack.