# 1493. Longest Subarray of 1's After Deleting One Element

> **Difficulty:** Medium  
> **Topics:** Array, Sliding Window, Two Pointers

---

## Problem Statement

Given a binary array `nums`, you must delete **exactly one element** from the array.

Return the length of the longest non-empty subarray containing only `1`s after deleting one element.

Since one element must always be deleted, the maximum length of the valid subarray is effectively:

```text
window length - 1
```

The deleted element can be either:

- a `0`, or
- a `1`

depending on the window.

---

## Example 1

### Input

```text
nums = [1,1,0,1]
```

### Output

```text
3
```

### Explanation

Delete the `0`:

```text
[1,1,1]
```

The longest subarray of `1`s has length:

```text
3
```

---

## Example 2

### Input

```text
nums = [0,1,1,1,0,1,1,0,1]
```

### Output

```text
5
```

### Explanation

Consider:

```text
[1,1,1,0,1,1]
```

It contains exactly one zero.

Delete that zero:

```text
[1,1,1,1,1]
```

Therefore, the answer is:

```text
5
```

---

## Example 3

### Input

```text
nums = [1,1,1]
```

### Output

```text
2
```

### Explanation

We must delete exactly one element.

```text
[1,1,1]
```

After deleting one `1`:

```text
[1,1]
```

Therefore, the answer is:

```text
2
```

---

# Approach

Use the **Sliding Window** technique.

Maintain a window containing **at most one zero**.

Why?

Because we are allowed to delete exactly one element.

If the window contains:

```text
0 zeros
```

we can delete one `1`.

If the window contains:

```text
1 zero
```

we can delete that zero.

Therefore, every valid window can produce a sequence of only `1`s after deleting exactly one element.

The length after deletion is:

```text
right - left
```

instead of:

```text
right - left + 1
```

because one element must be deleted.

---

# Algorithm

1. Initialize:
   ```text
   left = 0
   zeros = 0
   maxLength = 0
   ```
2. Traverse the array using `right`.
3. If `nums[right] == 0`, increase `zeros`.
4. If the window contains more than one zero:
   ```text
   zeros > 1
   ```
   move `left` forward until there is at most one zero.
5. Calculate the valid length after deleting one element:
   ```text
   right - left
   ```
6. Update `maxLength`.
7. Return `maxLength`.

---

# Dry Run

Input:

```text
nums = [1,1,0,1]
```

Initially:

```text
left = 0
zeros = 0
maxLength = 0
```

### Right = 0

```text
nums[0] = 1
```

Window:

```text
[1]
```

Zeros:

```text
0
```

Length after deleting one element:

```text
1 - 0 = 0
```

---

### Right = 1

Window:

```text
[1,1]
```

Zeros:

```text
0
```

Length:

```text
2 - 0 = 2
```

Update:

```text
maxLength = 2
```

Since there is no zero, one `1` will be deleted.

---

### Right = 2

```text
nums[2] = 0
```

Window:

```text
[1,1,0]
```

Zeros:

```text
1
```

The window is valid.

Length after deleting the zero:

```text
3 - 0 = 3
```

Update:

```text
maxLength = 3
```

---

### Right = 3

```text
nums[3] = 1
```

Window:

```text
[1,1,0,1]
```

Zeros:

```text
1
```

Delete the zero:

```text
[1,1,1]
```

Length:

```text
4 - 0 = 4
```

But one element must be deleted, so:

```text
4 - 1 = 3
```

This is exactly what:

```java
right - left
```

calculates.

Final answer:

```text
3
```

---

# Understanding the Code

## Initialize Variables

```java
int left = 0;
int maxLength = 0;
int zeros = 0;
```

Here:

```text
left       → left boundary of the window
right      → right boundary of the window
zeros      → number of zeros inside the window
maxLength  → maximum length after deleting one element
```

---

## Expand the Window

```java
for(int right = 0; right < nums.length; right++){
```

The `right` pointer expands the window one element at a time.

---

## Count Zeros

```java
if(nums[right] == 0){
    zeros++;
}
```

Whenever a zero enters the window, increase the zero count.

---

## Keep At Most One Zero

```java
while(zeros > 1){
```

We can delete only one element.

Therefore, the window cannot contain more than one zero.

If there are two zeros, move `left` forward.

```java
if(nums[left] == 0){
    zeros--;
}

left++;
```

---

## Update Maximum Length

```java
maxLength = Math.max(maxLength, right-left);
```

Normally, the window length is:

```text
right - left + 1
```

But here we must delete exactly one element.

Therefore:

```text
(right - left + 1) - 1
```

becomes:

```text
right - left
```

This is why the code uses:

```java
right - left
```

---

# Why Do We Allow One Zero?

Suppose the window is:

```text
[1,1,0,1,1]
```

There is exactly one zero.

Delete it:

```text
[1,1,1,1]
```

So this is a valid window.

Now suppose the window is:

```text
[1,1,0,1,0,1]
```

There are two zeros.

Deleting only one zero still leaves another zero:

```text
[1,1,1,0,1]
```

Therefore, the window is invalid.

That's why we maintain:

```text
zeros <= 1
```

---

# Important Difference from 1004

This problem is similar to **1004. Max Consecutive Ones III**, but there is an important difference.

### 1004. Max Consecutive Ones III

We can flip at most `k` zeros.

```text
zeros <= k
```

Window length is:

```text
right - left + 1
```

### 1493. Longest Subarray of 1's After Deleting One Element

We can delete exactly one element.

Therefore, we allow:

```text
zeros <= 1
```

but subtract one element from the window:

```text
right - left
```

---

# Sliding Window Pattern

The pattern is:

```text
Expand right
      ↓
Count zeros
      ↓
If zeros > 1
      ↓
Shrink from left
      ↓
Calculate window length - 1
      ↓
Update maximum
```

This is a **variable-size sliding window** problem.

---

# Complexity Analysis

### Time Complexity

Each element is visited by the `right` pointer once.

The `left` pointer also moves forward at most `n` times.

Therefore:

```text
O(n)
```

---

### Space Complexity

Only a few variables are used.

Therefore:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public int longestSubarray(int[] nums) {

        int left = 0;
        int maxLength = 0;
        int zeros = 0;

        for(int right = 0; right < nums.length; right++){

            if(nums[right] == 0){
                zeros++;
            }

            while(zeros > 1){

                if(nums[left] == 0){
                    zeros--;
                }

                left++;
            }

            maxLength = Math.max(maxLength, right - left);
        }

        return maxLength;
    }
}
```

---

# Key Concepts

- Array
- Sliding Window
- Two Pointers
- Variable-Size Window
- Zero Counting
- Longest Subarray
- Window Shrinking

---

# Constraints

- `1 <= nums.length <= 10^5`
- `nums[i]` is either `0` or `1`
- At least one element must be deleted.

---

# Learning Outcome

This problem demonstrates how a **Sliding Window** can be used when a subarray is allowed to contain a limited number of invalid elements.

The important observation is:

```text
At most one zero
        ↓
Delete that zero
        ↓
All remaining elements are 1
```

If the window contains no zero, we still have to delete one `1`, which is why the answer is calculated using:

```java
right - left
```

The main pattern is:

```text
Expand → Count → Shrink if invalid → Delete one → Update answer
```

The solution achieves:

```text
Time  → O(n)
Space → O(1)
```