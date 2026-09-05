# 3904. Smallest Stable Index II

**LeetCode Problem:** [3904. Smallest Stable Index II](https://leetcode.com/problems/smallest-stable-index-ii/)

**Difficulty:** Medium

**Primary Topic:** Arrays

---

## Problem Statement

Given an integer array `nums` and an integer `k`, find the **smallest stable index**.

For an index `i`, consider:

- The maximum value in the prefix `nums[0...i]`
- The minimum value in the suffix `nums[i...n-1]`

An index `i` is considered stable if:

```text
max(nums[0...i]) - min(nums[i...n-1]) <= k
```

Return the smallest index satisfying this condition.

If no such index exists, return:

```text
-1
```

---

# Approach

We need two pieces of information for every index `i`:

1. The maximum element from the beginning of the array up to `i`.
2. The minimum element from `i` to the end of the array.

We can calculate the suffix minimum values first and store them in an array called `right`.

Then, while traversing from left to right, we maintain the maximum value seen so far using a single variable called `left`.

For every index:

```text
left = maximum of nums[0...i]
right[i] = minimum of nums[i...n-1]
```

Then check:

```text
left - right[i] <= k
```

The first index satisfying this condition is the answer.

---

# Intuition

Suppose:

```text
nums = [2, 5, 3, 4, 1]
k = 4
```

For each index, we need:

```text
Prefix Maximum
Suffix Minimum
```

The prefix maximum values are:

```text
[2, 5, 5, 5, 5]
```

The suffix minimum values are:

```text
[1, 1, 1, 1, 1]
```

At index `0`:

```text
max prefix = 2
min suffix = 1

2 - 1 = 1
```

Since:

```text
1 <= 4
```

index `0` is stable.

Therefore, the smallest stable index is:

```text
0
```

---

# Why Do We Need a Suffix Minimum Array?

When we are checking index `i`, we need the minimum value from:

```text
i to n - 1
```

For example:

```text
nums = [5, 7, 3, 6, 2]
```

The suffix minimum array is:

```text
index:       0  1  2  3  4
nums:        5  7  3  6  2
suffix min:  2  2  2  2  2
```

For every index, we can quickly get the required suffix minimum using:

```java
right[i]
```

Instead of scanning the suffix again and again.

---

# Algorithm

1. Let `n = nums.length`.
2. Create an array `right` of size `n`.
3. Set:
   ```java
   right[n - 1] = nums[n - 1];
   ```
4. Traverse from right to left.
5. For every index `i`, calculate:
   ```java
   right[i] = Math.min(right[i + 1], nums[i]);
   ```
6. Initialize:
   ```java
   left = 0;
   ```
7. Traverse the array from left to right.
8. Update the prefix maximum:
   ```java
   left = Math.max(left, nums[i]);
   ```
9. Check:
   ```java
   left - right[i] <= k
   ```
10. If the condition is true, return `i`.
11. If no index satisfies the condition, return `-1`.

---

# Dry Run

Consider:

```text
nums = [5, 2, 6, 3, 4]
k = 3
```

## Step 1: Build Suffix Minimum

Start from the right.

```text
nums = [5, 2, 6, 3, 4]
```

At the last index:

```text
right[4] = 4
```

Move to index `3`:

```text
right[3] = min(right[4], nums[3])
         = min(4, 3)
         = 3
```

Index `2`:

```text
right[2] = min(3, 6)
         = 3
```

Index `1`:

```text
right[1] = min(3, 2)
         = 2
```

Index `0`:

```text
right[0] = min(2, 5)
         = 2
```

So:

```text
right = [2, 2, 3, 3, 4]
```

---

## Step 2: Traverse From Left

Initially:

```text
left = 0
```

### Index 0

```text
nums[0] = 5

left = max(0, 5)
     = 5

right[0] = 2
```

Check:

```text
left - right[0]
= 5 - 2
= 3
```

Since:

```text
3 <= k
```

index `0` is stable.

Therefore:

```text
answer = 0
```

---

# Another Dry Run

Consider:

```text
nums = [10, 8, 7, 1]
k = 2
```

### Suffix Minimum

```text
right = [1, 1, 1, 1]
```

### Index 0

```text
left = max(0, 10)
     = 10

10 - 1 = 9
```

Not stable.

---

### Index 1

```text
left = max(10, 8)
     = 10

10 - 1 = 9
```

Not stable.

---

### Index 2

```text
left = max(10, 7)
     = 10

10 - 1 = 9
```

Not stable.

---

### Index 3

```text
left = max(10, 1)
     = 10

10 - 1 = 9
```

Not stable.

No stable index exists.

Therefore:

```text
answer = -1
```

---

# Java Solution

```java
class Solution {

    public int firstStableIndex(int[] nums, int k) {

        int n = nums.length;

        int[] right = new int[n];

        right[n - 1] = nums[n - 1];

        for(int i = n - 2; i >= 0; i--){

            right[i] = Math.min(right[i + 1], nums[i]);

        }

        int left = 0;

        for(int i = 0; i < n; i++){

            left = Math.max(left, nums[i]);

            if(left - right[i] <= k){

                return i;

            }

        }

        return -1;
    }
}
```

---

# Code Explanation

## 1. Store Array Length

```java
int n = nums.length;
```

`n` stores the number of elements in the array.

---

## 2. Create Suffix Minimum Array

```java
int[] right = new int[n];
```

The `right` array stores the minimum value from each index to the end.

For example:

```text
nums  = [5, 7, 3, 6, 2]
right = [2, 2, 2, 2, 2]
```

---

## 3. Initialize Last Element

```java
right[n - 1] = nums[n - 1];
```

For the last index, the suffix contains only one element.

Therefore:

```text
right[n - 1] = nums[n - 1]
```

---

## 4. Build Suffix Minimum

```java
for(int i = n - 2; i >= 0; i--){

    right[i] = Math.min(right[i + 1], nums[i]);

}
```

We traverse from right to left.

At every index:

```text
right[i]
```

is the minimum between:

```text
nums[i]
```

and:

```text
right[i + 1]
```

So:

```java
right[i] = Math.min(right[i + 1], nums[i]);
```

---

## 5. Maintain Prefix Maximum

```java
int left = 0;
```

Instead of creating another array for prefix maximum, we store it in one variable.

During the left-to-right traversal:

```java
left = Math.max(left, nums[i]);
```

Therefore, `left` always represents:

```text
max(nums[0...i])
```

---

## 6. Check Stability

```java
if(left - right[i] <= k){
    return i;
}
```

At index `i`:

```text
left = maximum of nums[0...i]

right[i] = minimum of nums[i...n-1]
```

Therefore:

```text
left - right[i]
```

is exactly the required difference.

If it is at most `k`, the index is stable.

Because we scan from left to right, the first valid index is automatically the **smallest stable index**.

---

## 7. Return `-1`

```java
return -1;
```

If the loop finishes without finding a stable index, no valid index exists.

---

# Why We Traverse From Right to Left?

The suffix minimum depends on values to the right.

For:

```text
nums = [5, 4, 7, 2]
```

At index `1`, we need:

```text
min(4, 7, 2)
```

If we process from right to left, we already know the answer for the next position.

For example:

```text
right[3] = 2

right[2] = min(7, 2) = 2

right[1] = min(4, 2) = 2
```

This allows every suffix minimum to be calculated in `O(1)` using the previously calculated value.

---

# Why We Traverse From Left to Right for the Answer?

We need the **smallest** stable index.

So we must check indices in increasing order:

```text
0 → 1 → 2 → 3 → ...
```

As soon as:

```text
left - right[i] <= k
```

becomes true, we can immediately return `i`.

There is no need to check later indices.

---

# Prefix Maximum and Suffix Minimum

This problem is based on two useful array techniques.

### Prefix Maximum

```text
nums = [5, 2, 8, 3]

prefix max:
[5, 5, 8, 8]
```

Each position stores the maximum value seen from the beginning.

### Suffix Minimum

```text
nums = [5, 2, 8, 3]

suffix min:
[2, 2, 3, 3]
```

Each position stores the minimum value from that position to the end.

Together:

```text
Prefix Maximum
      +
Suffix Minimum
      ↓
Check stability at every index
```

---

# Complexity Analysis

Let `n` be the length of `nums`.

## Time Complexity

Building the suffix minimum array takes:

```text
O(n)
```

Checking every index takes:

```text
O(n)
```

Therefore:

```text
Total Time = O(n)
```

---

## Space Complexity

We create the `right` array of size `n`.

Therefore:

```text
Space = O(n)
```

The `left` variable uses only constant space.

---

# Key Concepts / Patterns

## 1. Prefix Maximum

Maintain the maximum value seen so far:

```java
left = Math.max(left, nums[i]);
```

---

## 2. Suffix Minimum

Precompute minimum values from right to left:

```java
right[i] = Math.min(right[i + 1], nums[i]);
```

---

## 3. Two-Direction Processing

The problem requires information from both sides of the current index.

```text
Left side:
maximum

Right side:
minimum
```

We efficiently handle this by:

```text
Right → Left
    ↓
Build suffix minimum

Left → Right
    ↓
Find first stable index
```

---

## 4. First Valid Index

Because we traverse from `0` to `n - 1`, the first index satisfying:

```text
left - right[i] <= k
```

is automatically the smallest valid index.

---

# Common Mistakes

## Mistake 1: Calculating the Suffix Minimum From Left to Right

The suffix depends on elements to the right.

Therefore, it should be calculated from:

```text
right → left
```

not:

```text
left → right
```

---

## Mistake 2: Using Only `nums[i]`

The condition does not compare only:

```text
nums[i]
```

with the suffix.

We need:

```text
maximum of nums[0...i]
```

and:

```text
minimum of nums[i...n-1]
```

---

## Mistake 3: Returning the Last Valid Index

The question asks for the **smallest** stable index.

Therefore, scan from left to right and immediately return the first valid index.

---

## Mistake 4: Recalculating Maximum and Minimum for Every Index

A brute-force approach could calculate the maximum prefix and minimum suffix repeatedly.

That could lead to:

```text
O(n²)
```

time complexity.

Using prefix/suffix preprocessing reduces the solution to:

```text
O(n)
```

---

# Optimization in This Solution

We don't create a separate prefix maximum array.

Instead, we use:

```java
int left = 0;
```

and update it while traversing:

```java
left = Math.max(left, nums[i]);
```

So we only need one extra array:

```text
right[]
```

This keeps the implementation simple.

---

# Learning Outcome

After solving this problem, you should understand:

- How to calculate suffix minimum values.
- How to maintain a prefix maximum.
- How to process an array from both directions.
- How preprocessing can reduce repeated work.
- How to find the first index satisfying a condition.
- How to reduce a potential `O(n²)` solution to `O(n)`.

---

# Summary

For every index `i`, we need:

```text
Maximum of nums[0...i]
```

and:

```text
Minimum of nums[i...n-1]
```

We precompute the suffix minimum:

```java
right[i] = Math.min(right[i + 1], nums[i]);
```

Then maintain the prefix maximum:

```java
left = Math.max(left, nums[i]);
```

Finally, check:

```java
if(left - right[i] <= k){
    return i;
}
```

Since we scan from left to right, the first valid index is the smallest stable index.

### Final Complexity

```text
Time:  O(n)
Space: O(n)
```

**Primary Pattern: Prefix Maximum + Suffix Minimum**