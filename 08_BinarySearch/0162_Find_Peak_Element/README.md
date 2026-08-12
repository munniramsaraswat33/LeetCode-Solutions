# 162. Find Peak Element

> **Difficulty:** Medium  
> **Topics:** Array, Binary Search

---

## Problem Statement

A **peak element** is an element that is strictly greater than its neighboring elements.

Given a `0-indexed` integer array `nums`, find any peak element and return its index.

You may assume:

```text
nums[-1] = -∞
nums[n] = -∞
```

This means an element at either end of the array can also be a peak if it is greater than its only neighbor.

The solution must run in:

```text
O(log n)
```

time complexity.

---

## Example 1

### Input

```text
nums = [1,2,3,1]
```

### Output

```text
2
```

### Explanation

The element:

```text
nums[2] = 3
```

is greater than both neighbors:

```text
2 < 3 > 1
```

Therefore, index `2` is a peak.

---

## Example 2

### Input

```text
nums = [1,2,1,3,5,6,4]
```

### Output

```text
5
```

### Explanation

There are multiple peak elements:

```text
index 1 → value 2
index 5 → value 6
```

Either answer is valid.

The solution may return:

```text
5
```

because:

```text
5 < 6 > 4
```

---

# Approach

This problem can be solved using **Binary Search**.

The important observation is to compare:

```text
nums[mid]
```

with:

```text
nums[mid + 1]
```

There are two cases.

---

## Case 1: Increasing Slope

If:

```java
nums[mid] < nums[mid + 1]
```

then the array is increasing at `mid`.

This means there must be a peak somewhere to the **right**.

Why?

Because either the array continues increasing until a peak, or it eventually starts decreasing. In both cases, there is a peak on the right side.

Therefore:

```java
start = mid + 1;
```

---

## Case 2: Decreasing Slope

If:

```java
nums[mid] > nums[mid + 1]
```

then the array is decreasing at `mid`.

Therefore, a peak exists at `mid` or somewhere to the **left**.

So:

```java
end = mid;
```

We keep `mid` because it could itself be the peak.

---

# Algorithm

1. Initialize:
   ```text
   start = 0
   end = n - 1
   ```
2. While:
   ```text
   start < end
   ```
3. Calculate:
   ```text
   mid = start + (end - start) / 2
   ```
4. Compare:
   ```text
   nums[mid]
   ```
   and:
   ```text
   nums[mid + 1]
   ```
5. If:
   ```text
   nums[mid] < nums[mid + 1]
   ```
   move right:
   ```text
   start = mid + 1
   ```
6. Otherwise:
   ```text
   end = mid
   ```
7. When:
   ```text
   start == end
   ```
   return `start`.

---

# Dry Run

### Input

```text
nums = [1,2,3,1]
```

Indices:

```text
0  1  2  3
1  2  3  1
```

Initially:

```text
start = 0
end = 3
```

### Step 1

```text
mid = 0 + (3 - 0) / 2
    = 1
```

Compare:

```text
nums[1] = 2
nums[2] = 3
```

Since:

```text
2 < 3
```

we are on an increasing slope.

Move right:

```text
start = mid + 1
start = 2
```

---

### Step 2

Now:

```text
start = 2
end = 3
```

Calculate:

```text
mid = 2
```

Compare:

```text
nums[2] = 3
nums[3] = 1
```

Since:

```text
3 > 1
```

we are on a decreasing slope.

Therefore:

```text
end = mid
end = 2
```

Now:

```text
start == end == 2
```

Return:

```text
2
```

---

# Why This Works

Think of the array as a slope:

```text
Increasing slope:
      /
     /
    /
```

If:

```text
nums[mid] < nums[mid + 1]
```

we are climbing upward, so a peak must exist somewhere after `mid`.

If:

```text
nums[mid] > nums[mid + 1]
```

we are going downhill, so a peak is already at `mid` or before it.

Therefore, every comparison allows us to eliminate approximately half of the search space.

---

# Difference from Mountain Array

This problem is similar to **852. Peak Index in a Mountain Array**, but there is an important difference.

### Mountain Array

There is exactly one peak:

```text
Increasing → Peak → Decreasing
```

### Find Peak Element

There can be multiple peaks:

```text
     /\       /\
    /  \     /  \
___/    \___/    \__
```

We only need to find **any one** peak.

The same binary search idea still works.

---

# Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

Binary search reduces the search space by half at every step:

```text
O(log n)
```

---

### Space Complexity

Only a few variables are used:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public int findPeakElement(int[] arr) {

        int start = 0;
        int end = arr.length - 1;

        while (start < end) {

            int mid = start + (end - start) / 2;

            if (arr[mid] < arr[mid + 1]) {

                // Increasing slope
                start = mid + 1;

            } else {

                // Decreasing slope
                end = mid;
            }
        }

        return start;
    }
}
```

---

# Key Concepts

- Array
- Binary Search
- Peak Element
- Increasing/Decreasing Slope
- Search Space Reduction

---

# Constraints

- `1 <= nums.length <= 1000`
- `-2³¹ <= nums[i] <= 2³¹ - 1`
- `nums[i] != nums[i + 1]` for all valid `i`

---

# Learning Outcome

This problem demonstrates an important **Binary Search on a slope** technique.

The key condition is:

```java
if (arr[mid] < arr[mid + 1])
```

If true:

```text
Peak → Right
```

Otherwise:

```text
Peak → Left or Mid
```

The search continues until only one index remains:

```text
start == end
```

That index is guaranteed to be a peak.

### Complexity

```text
Time:  O(log n)
Space: O(1)
```

---

# Binary Search Pattern

The core template from this problem is:

```java
while (start < end) {

    int mid = start + (end - start) / 2;

    if (arr[mid] < arr[mid + 1]) {
        start = mid + 1;
    } else {
        end = mid;
    }
}

return start;
```

This pattern is useful for **peak finding and searching on monotonic slopes**.