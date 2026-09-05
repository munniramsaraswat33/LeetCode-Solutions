# 3903. Smallest Stable Index I

**Difficulty:** Easy  
**Topics:** Array, Prefix Maximum, Suffix Minimum

---

## Problem Statement

You are given an integer array `nums` and an integer `k`.

Find the **smallest index `i`** such that the difference between the maximum value from the beginning of the array up to `i` and the minimum value from `i` to the end of the array is at most `k`.

In other words, find the smallest `i` satisfying:

```text
max(nums[0 ... i]) - min(nums[i ... n-1]) <= k
```

If no such index exists, return `-1`.

---

## Example

### Input

```text
nums = [1, 3, 2, 5, 4]
k = 2
```

### Output

```text
2
```

### Explanation

For index `2`:

```text
nums[0 ... 2] = [1, 3, 2]
maximum = 3

nums[2 ... 4] = [2, 5, 4]
minimum = 2
```

Therefore:

```text
3 - 2 = 1
```

Since:

```text
1 <= 2
```

index `2` is stable.

---

# Approach

We need two pieces of information for every index:

1. Maximum value from the beginning up to that index.
2. Minimum value from that index to the end.

We can precompute these using two arrays.

### Prefix Maximum

```java
max[i]
```

stores:

```text
maximum value in nums[0 ... i]
```

### Suffix Minimum

```java
min[i]
```

stores:

```text
minimum value in nums[i ... n-1]
```

Then for every index `i`, check:

```java
max[i] - min[i] <= k
```

The first index satisfying this condition is the answer.

---

# Intuition

Suppose:

```text
nums = [1, 4, 3, 2, 5]
```

The prefix maximum array is:

```text
max = [1, 4, 4, 4, 5]
```

The suffix minimum array is:

```text
min = [1, 2, 2, 2, 5]
```

Now we can check each index without repeatedly calculating maximum and minimum.

For example, at index `2`:

```text
max[2] = 4
min[2] = 2
```

Therefore:

```text
4 - 2 = 2
```

If `k >= 2`, index `2` is stable.

---

# Algorithm

1. Create a `min` array for suffix minimums.
2. Set:
   ```java
   min[n - 1] = nums[n - 1];
   ```
3. Create a `max` array for prefix maximums.
4. Set:
   ```java
   max[0] = nums[0];
   ```
5. Traverse from left to right and calculate prefix maximums.
6. Traverse from right to left and calculate suffix minimums.
7. Traverse the array from left to right.
8. For each index `i`, check:
   ```java
   max[i] - min[i] <= k
   ```
9. Return the first valid index.
10. If no index satisfies the condition, return `-1`.

---

# Dry Run

Consider:

```text
nums = [1, 4, 3, 2, 5]
k = 2
```

### Step 1: Prefix Maximum

```text
max[0] = 1
```

Then:

```text
max[1] = max(1, 4) = 4
max[2] = max(4, 3) = 4
max[3] = max(4, 2) = 4
max[4] = max(4, 5) = 5
```

So:

```text
max = [1, 4, 4, 4, 5]
```

---

### Step 2: Suffix Minimum

Start from the last element:

```text
min[4] = 5
```

Then:

```text
min[3] = min(2, 5) = 2
min[2] = min(3, 2) = 2
min[1] = min(4, 2) = 2
min[0] = min(1, 2) = 1
```

So:

```text
min = [1, 2, 2, 2, 5]
```

---

### Step 3: Check Each Index

#### Index 0

```text
max[0] - min[0]
= 1 - 1
= 0
```

Since:

```text
0 <= 2
```

index `0` is stable.

Therefore, the answer is:

```text
0
```

The algorithm returns immediately.

---

# Java Solution

```java
class Solution {

    public int firstStableIndex(int[] nums, int k) {

        int n = nums.length;

        int[] min = new int[n];

        min[n - 1] = nums[n - 1];

        int[] max = new int[n];

        max[0] = nums[0];

        for(int i = 1; i < n; i++){
            max[i] = Math.max(max[i - 1], nums[i]);
        }

        for(int i = n - 2; i >= 0; i--){
            min[i] = Math.min(min[i + 1], nums[i]);
        }

        for(int i = 0; i < n; i++){
            if(max[i] - min[i] <= k){
                return i;
            }
        }

        return -1;
    }
}
```

---

# Code Explanation

### Prefix Maximum Array

```java
int[] max = new int[n];
max[0] = nums[0];

for(int i = 1; i < n; i++){
    max[i] = Math.max(max[i - 1], nums[i]);
}
```

`max[i]` stores the maximum value from index `0` to `i`.

---

### Suffix Minimum Array

```java
int[] min = new int[n];
min[n - 1] = nums[n - 1];

for(int i = n - 2; i >= 0; i--){
    min[i] = Math.min(min[i + 1], nums[i]);
}
```

`min[i]` stores the minimum value from index `i` to the last index.

---

### Find Smallest Stable Index

```java
for(int i = 0; i < n; i++){
    if(max[i] - min[i] <= k){
        return i;
    }
}
```

We check indices from left to right.

The first index satisfying the condition is automatically the smallest stable index.

---

### No Valid Index

```java
return -1;
```

If the loop finishes without finding a valid index, no stable index exists.

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

We traverse the array three times.

Since each traversal is linear:

```text
O(n)
```

---

### Space Complexity

```text
O(n)
```

We use two additional arrays:

```text
max[]
min[]
```

---

# Key Concepts / Patterns

- Array
- Prefix Maximum
- Suffix Minimum
- Precomputation
- Left-to-Right Traversal
- Right-to-Left Traversal

---

# Learning Outcome

- Learn how to precompute prefix maximum values.
- Learn how to precompute suffix minimum values.
- Understand how precomputation avoids repeated calculations.
- Learn how to find the smallest index satisfying a condition.
- Understand how two auxiliary arrays can reduce a repeated range-query problem to `O(n)`.