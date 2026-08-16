# 704. Binary Search

> **Difficulty:** Easy  
> **Topics:** Array, Binary Search

---

## Problem Statement

You are given a sorted integer array `nums` in ascending order and an integer `target`.

Return the index of `target` if it exists in the array.

If `target` does not exist, return:

```text
-1
```

The solution must have:

```text
O(log n)
```

time complexity.

---

## Example 1

### Input

```text
nums = [-1,0,3,5,9,12]
target = 9
```

### Output

```text
4
```

### Explanation

The value `9` exists at index `4`.

```text
Index:  0   1   2   3   4   5
Value: -1   0   3   5   9  12
                         ↑
                       target
```

---

## Example 2

### Input

```text
nums = [-1,0,3,5,9,12]
target = 2
```

### Output

```text
-1
```

### Explanation

`2` does not exist in the array.

---

# Approach

Because the array is already **sorted**, we can use **Binary Search**.

Instead of checking every element one by one, binary search repeatedly divides the search range into two halves.

We maintain two pointers:

```java
start = 0
end = nums.length - 1
```

Then calculate the middle index:

```java
int mid = start + (end - start) / 2;
```

---

# Binary Search Logic

At every step, compare:

```text
nums[mid]
```

with:

```text
target
```

There are three cases.

### Case 1: Target Found

If:

```java
nums[mid] == target
```

return:

```java
mid
```

---

### Case 2: Target is Greater

If:

```java
nums[mid] < target
```

Since the array is sorted, everything from `start` through `mid` is too small.

Therefore, search the right half:

```java
start = mid + 1;
```

---

### Case 3: Target is Smaller

If:

```java
nums[mid] > target
```

Everything from `mid` through `end` is too large.

Therefore, search the left half:

```java
end = mid - 1;
```

---

# Dry Run

### Input

```text
nums = [-1,0,3,5,9,12]
target = 9
```

Initial:

```text
start = 0
end = 5
```

---

### Step 1

Calculate:

```text
mid = 0 + (5 - 0) / 2
    = 2
```

So:

```text
nums[2] = 3
```

Compare:

```text
3 < 9
```

Therefore search the right half:

```text
start = 3
```

---

### Step 2

Now:

```text
start = 3
end = 5
```

Calculate:

```text
mid = 3 + (5 - 3) / 2
    = 4
```

So:

```text
nums[4] = 9
```

Target found.

Return:

```text
4
```

---

# Why Binary Search is O(log n)

Suppose there are `n` elements.

After every iteration, the search space becomes approximately half:

```text
n
n/2
n/4
n/8
n/16
...
```

After `k` iterations:

```text
n / 2^k = 1
```

Therefore:

```text
2^k = n
```

and:

```text
k = log₂(n)
```

So the time complexity is:

```text
O(log n)
```

---

# Algorithm

1. Set:
   ```text
   start = 0
   end = n - 1
   ```
2. While:
   ```text
   start <= end
   ```
3. Calculate:
   ```text
   mid = start + (end - start) / 2
   ```
4. If `nums[mid] == target`, return `mid`.
5. If `nums[mid] < target`, search the right half.
6. Otherwise, search the left half.
7. If the loop ends, the target does not exist, so return `-1`.

---

# Java Solution

```java
class Solution {

    public int search(int[] nums, int target) {

        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {

            int mid = start + (end - start) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            else if (nums[mid] < target) {
                start = mid + 1;
            }

            else {
                end = mid - 1;
            }
        }

        return -1;
    }
}
```

---

# Why Use `start + (end - start) / 2`?

Instead of:

```java
int mid = (start + end) / 2;
```

we use:

```java
int mid = start + (end - start) / 2;
```

This avoids potential integer overflow when `start` and `end` are very large.

This is a common best practice in binary search.

---

# Edge Cases

### Target is the first element

```text
nums = [1,2,3,4,5]
target = 1
```

Output:

```text
0
```

### Target is the last element

```text
nums = [1,2,3,4,5]
target = 5
```

Output:

```text
4
```

### Target does not exist

```text
nums = [1,2,3,4,5]
target = 6
```

Output:

```text
-1
```

### Single element

```text
nums = [5]
target = 5
```

Output:

```text
0
```

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

Each iteration eliminates half of the search space:

```text
O(log n)
```

### Space Complexity

Only a few variables are used:

```text
O(1)
```

---

# Key Concepts

- Binary Search
- Divide and Conquer
- Sorted Array
- Two Pointers
- Search Space Reduction

---

# Constraints

```text
1 <= nums.length <= 10⁴
-10⁴ < nums[i], target < 10⁴
```

Additional guarantees:

```text
nums is sorted in ascending order
```

and all elements are unique.

---

# Learning Outcome

The core binary search pattern to remember is:

```text
start = 0
end = n - 1

while (start <= end)

        ↓

calculate mid

        ↓

target == nums[mid]
        → return mid

target > nums[mid]
        → search right

target < nums[mid]
        → search left
```

The three most important lines are:

```java
int mid = start + (end - start) / 2;

if (nums[mid] < target) {
    start = mid + 1;
}
else {
    end = mid - 1;
}
```

### Complexity

```text
Time:  O(log n)
Space: O(1)
```