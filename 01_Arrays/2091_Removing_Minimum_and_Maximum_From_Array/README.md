# 2091. Removing Minimum and Maximum From Array

> **Difficulty:** Medium  
> **Topics:** Array, Greedy

---

## Problem Statement

You are given an integer array `nums`.

You need to remove **both the minimum element and the maximum element** from the array.

In one operation, you can remove an element from either:

- The beginning of the array
- The end of the array

Return the **minimum number of deletions** required to remove both the minimum and maximum elements.

---

## Examples

### Example 1

```text
Input:
nums = [2,10,7,5,4,1,8,6]

Output:
5
```

### Explanation

The minimum element is:

```text
1
```

at index `5`.

The maximum element is:

```text
10
```

at index `1`.

There are three possible strategies:

1. Remove elements from the front until both are removed.
2. Remove elements from the back until both are removed.
3. Remove some elements from the front and the rest from the back.

The minimum number of deletions is:

```text
5
```

---

### Example 2

```text
Input:
nums = [0,-4,19,1,8,-2,-3,5]

Output:
6
```

### Explanation

The minimum element is:

```text
-4
```

and the maximum element is:

```text
19
```

We compare all possible ways of removing both elements and choose the minimum number of operations.

---

### Example 3

```text
Input:
nums = [3,1]

Output:
2
```

### Explanation

The minimum and maximum are both inside the array.

Both elements must be removed, requiring:

```text
2 deletions
```

---

# Approach

The important observation is that we do **not** need to actually remove elements.

We only need to know the positions of:

- Minimum element
- Maximum element

Suppose:

```text
minIndex = 2
maxIndex = 6
```

We define:

```text
left  = min(minIndex, maxIndex)
right = max(minIndex, maxIndex)
```

There are exactly **three possible strategies**.

---

## Case 1: Remove From the Front

If we remove elements only from the beginning, we must remove everything up to `right`.

Number of deletions:

```text
right + 1
```

So:

```java
int fromFront = right + 1;
```

---

## Case 2: Remove From the Back

If we remove elements only from the end, we must remove everything from `left` to the end.

Number of deletions:

```text
n - left
```

So:

```java
int fromBack = n - left;
```

---

## Case 3: Remove From Both Ends

We can remove:

- `left + 1` elements from the front
- `n - right` elements from the back

Total:

```text
(left + 1) + (n - right)
```

So:

```java
int fromBoth = left + 1 + n - right;
```

Finally, take the minimum of the three possibilities.

---

# Algorithm

1. Find the minimum value in the array.
2. Find the maximum value in the array.
3. Find the index of the minimum element.
4. Find the index of the maximum element.
5. Let:
   ```text
   left = min(minIndex, maxIndex)
   right = max(minIndex, maxIndex)
   ```
6. Calculate deletions if removing from the front:
   ```text
   right + 1
   ```
7. Calculate deletions if removing from the back:
   ```text
   n - left
   ```
8. Calculate deletions if removing from both ends:
   ```text
   left + 1 + n - right
   ```
9. Return the minimum of these three values.

---

# Dry Run

Consider:

```text
nums = [2,10,7,5,4,1,8,6]
```

### Step 1: Find Minimum and Maximum

```text
Minimum = 1
Maximum = 10
```

Their indices are:

```text
10 → index 1
1  → index 5
```

Therefore:

```text
minIndex = 5
maxIndex = 1
```

---

### Step 2: Determine Left and Right

```java
left = Math.min(5, 1);
right = Math.max(5, 1);
```

So:

```text
left = 1
right = 5
```

---

### Step 3: Remove From Front

To remove both elements from the front:

```text
right + 1
= 5 + 1
= 6
```

So:

```text
fromFront = 6
```

---

### Step 4: Remove From Back

To remove both elements from the back:

```text
n - left
= 8 - 1
= 7
```

So:

```text
fromBack = 7
```

---

### Step 5: Remove From Both Ends

Remove from the front:

```text
left + 1
= 2
```

Remove from the back:

```text
n - right
= 8 - 5
= 3
```

Total:

```text
2 + 3 = 5
```

So:

```text
fromBoth = 5
```

---

### Step 6: Find Minimum

```text
fromFront = 6
fromBack  = 7
fromBoth  = 5
```

Therefore:

```text
Answer = 5
```

---

# Java Solution

```java
class Solution {

    public int minimumDeletions(int[] nums) {

        int n = nums.length;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // Find minimum and maximum values
        for (int num : nums) {
            min = Math.min(num, min);
            max = Math.max(num, max);
        }

        int minIndex = 0;
        int maxIndex = 0;

        // Find their indices
        for (int i = 0; i < n; i++) {

            if (nums[i] == min) {
                minIndex = i;
            }

            if (nums[i] == max) {
                maxIndex = i;
            }
        }

        // Smaller and larger index
        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        // Case 1: Remove from the front
        int fromFront = right + 1;

        // Case 2: Remove from the back
        int fromBack = n - left;

        // Case 3: Remove from both ends
        int fromBoth = left + 1 + n - right;

        // Return the minimum number of deletions
        return Math.min(
            fromFront,
            Math.min(fromBack, fromBoth)
        );
    }
}
```

---

# Code Explanation

### Finding Minimum and Maximum

```java
int min = Integer.MAX_VALUE;
int max = Integer.MIN_VALUE;
```

We initialize `min` with the largest possible integer and `max` with the smallest possible integer.

Then scan the array:

```java
for (int num : nums) {
    min = Math.min(num, min);
    max = Math.max(num, max);
}
```

---

### Finding Their Indices

```java
if (nums[i] == min) {
    minIndex = i;
}

if (nums[i] == max) {
    maxIndex = i;
}
```

We store the positions of the minimum and maximum elements.

---

### Normalize Their Positions

```java
int left = Math.min(minIndex, maxIndex);
int right = Math.max(minIndex, maxIndex);
```

This allows us to handle both elements without caring which one is the minimum or maximum.

---

### Three Possible Strategies

#### From Front

```java
int fromFront = right + 1;
```

We remove everything from index `0` through `right`.

---

#### From Back

```java
int fromBack = n - left;
```

We remove everything from index `left` through `n - 1`.

---

#### From Both Ends

```java
int fromBoth = left + 1 + n - right;
```

We remove:

```text
0 → left
```

from the front and:

```text
right → n-1
```

from the back.

---

### Choose Minimum

```java
return Math.min(
    fromFront,
    Math.min(fromBack, fromBoth)
);
```

There are only three possible strategies, so we simply return the smallest one.

---

# Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

We traverse the array twice:

```text
O(n) + O(n)
```

Therefore:

```text
O(n)
```

### Space Complexity

Only a few variables are used.

```text
O(1)
```

---

# Key Concepts

### 1. Array Traversal

We scan the array to find the minimum, maximum, and their positions.

### 2. Greedy / Case Analysis

Instead of trying every possible sequence of deletions, we evaluate the only three meaningful strategies.

### 3. Index Manipulation

The answer depends only on the positions of the minimum and maximum elements.

### 4. Minimum Optimization

We calculate all possible deletion counts and choose the smallest one.

---

# Constraints

- `2 <= nums.length <= 100000`
- `-100000 <= nums[i] <= 100000`
- The minimum and maximum elements are distinct.

---

# Learning Outcome

After solving this problem, you should understand:

- How to solve array problems using index-based reasoning.
- How to avoid simulating unnecessary deletion operations.
- How to reduce a problem to a small number of cases.
- How to compare front, back, and two-sided operations.
- How greedy case analysis can produce an `O(n)` solution.