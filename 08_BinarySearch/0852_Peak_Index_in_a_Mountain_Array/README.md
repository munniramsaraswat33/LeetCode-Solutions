# 852. Peak Index in a Mountain Array

> **Difficulty:** Medium  
> **Topics:** Array, Binary Search

---

## Problem Statement

You are given a **mountain array** `arr`.

A mountain array has the following structure:

```text
Increasing → Peak → Decreasing
```

The array contains exactly one peak element.

Return the **index of the peak element**.

The solution must run in:

```text
O(log n)
```

time complexity.

---

## Example 1

### Input

```text
arr = [0,1,0]
```

### Output

```text
1
```

### Explanation

The array increases:

```text
0 → 1
```

and then decreases:

```text
1 → 0
```

Therefore, the peak is:

```text
arr[1] = 1
```

---

## Example 2

### Input

```text
arr = [0,2,1,0]
```

### Output

```text
1
```

The peak element is:

```text
2
```

at index `1`.

---

## Example 3

### Input

```text
arr = [0,10,5,2]
```

### Output

```text
1
```

The peak element is:

```text
10
```

at index `1`.

---

# Approach

Since the array is a **mountain array**, it first increases and then decreases.

This structure allows us to use **Binary Search**.

At every step, compare:

```text
arr[mid]
```

with:

```text
arr[mid + 1]
```

There are two possibilities.

---

## Case 1: Increasing Side

If:

```java
arr[mid] < arr[mid + 1]
```

we are on the increasing side of the mountain.

Therefore, the peak must be somewhere to the **right**.

Move:

```java
start = mid + 1;
```

---

## Case 2: Decreasing Side

If:

```java
arr[mid] >= arr[mid + 1]
```

we are on the decreasing side or at the peak.

Therefore, the peak can be at `mid` or somewhere to the **left**.

Move:

```java
end = mid;
```

Notice that we do **not** use:

```java
end = mid - 1;
```

because `mid` itself could be the peak.

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
4. If:
   ```text
   arr[mid] < arr[mid + 1]
   ```
   move to the right:
   ```text
   start = mid + 1
   ```
5. Otherwise:
   ```text
   end = mid
   ```
6. When the loop ends:
   ```text
   start == end
   ```
   This index is the peak.
7. Return `start`.

---

# Dry Run

### Input

```text
arr = [0,2,1,0]
```

Indices:

```text
0  1  2  3
0  2  1  0
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
arr[1] = 2
arr[2] = 1
```

Since:

```text
2 > 1
```

we are on the decreasing side.

Therefore:

```text
end = mid
end = 1
```

---

### Step 2

Now:

```text
start = 0
end = 1
```

Calculate:

```text
mid = 0
```

Compare:

```text
arr[0] = 0
arr[1] = 2
```

Since:

```text
0 < 2
```

we are on the increasing side.

Therefore:

```text
start = mid + 1
start = 1
```

Now:

```text
start == end == 1
```

Therefore:

```text
answer = 1
```

---

# Why Binary Search Works

The mountain array has two sections:

```text
Increasing          Decreasing
    /\
   /  \
  /    \
```

When:

```text
arr[mid] < arr[mid + 1]
```

we know we are climbing upward, so the peak must be to the right.

When:

```text
arr[mid] > arr[mid + 1]
```

we are descending, so the peak is at `mid` or to the left.

Thus, every iteration eliminates approximately half of the remaining search space.

---

# Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

Binary search reduces the search space by approximately half at every iteration:

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

    public int peakIndexInMountainArray(int[] arr) {

        int start = 0;
        int end = arr.length - 1;

        while (start < end) {

            int mid = start + (end - start) / 2;

            if (arr[mid] < arr[mid + 1]) {

                // We are on the increasing side
                start = mid + 1;

            } else {

                // We are on the decreasing side or at the peak
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
- Mountain Array
- Peak Finding
- Search Space Reduction

---

# Constraints

- `3 <= arr.length <= 10⁵`
- `0 <= arr[i] <= 10⁶`
- `arr` is guaranteed to be a mountain array.

---

# Learning Outcome

This problem demonstrates how **Binary Search can be used even when the array is not completely sorted**.

The key observation is the mountain structure:

```text
Increasing → Peak → Decreasing
```

The most important condition is:

```java
if (arr[mid] < arr[mid + 1])
```

If true:

```text
Peak is on the right
```

Otherwise:

```text
Peak is at mid or on the left
```

The final search space contains exactly one index:

```text
start == end
```

which is the peak.

### Complexity

```text
Time:  O(log n)
Space: O(1)
```