# 1004. Max Consecutive Ones III

> **Difficulty:** Medium  
> **Topics:** Array, Sliding Window

---

## Problem Statement

Given a binary array `nums` and an integer `k`, return the maximum number of consecutive `1`s in the array if you can flip at most `k` zeros.

A zero can be changed into a one.

The goal is to find the **longest contiguous subarray** containing at most `k` zeros.

---

## Example 1

### Input

```text
nums = [1,1,1,0,0,0,1,1,1,1,0]
k = 2
```

### Output

```text
6
```

### Explanation

We can flip two zeros in the subarray:

```text
[0,1,1,1,1,0]
```

After flipping the two zeros:

```text
[1,1,1,1,1,1]
```

Therefore, the maximum length is:

```text
6
```

---

## Example 2

### Input

```text
nums = [0,0,1,1,1,0,0]
k = 0
```

### Output

```text
3
```

### Explanation

Since `k = 0`, no zeros can be flipped.

The longest consecutive sequence of ones is:

```text
[1,1,1]
```

Therefore, the answer is:

```text
3
```

---

# Approach

Use the **Sliding Window** technique.

Maintain a window `[left, right]` that contains at most `k` zeros.

As we expand the window using `right`:

- If `nums[right] == 0`, increase `zeroCount`.
- If `zeroCount > k`, the window is invalid.
- Move `left` forward until the window contains at most `k` zeros again.
- Update the maximum window length.

The window represents a subarray where all zeros can be flipped into ones.

---

# Algorithm

1. Initialize:
   ```text
   left = 0
   zeroCount = 0
   maxLength = 0
   ```
2. Traverse the array using `right`.
3. If the current element is `0`, increment `zeroCount`.
4. While:
   ```text
   zeroCount > k
   ```
   move `left` forward.
5. If the element leaving the window is `0`, decrease `zeroCount`.
6. Calculate the current window length:
   ```text
   right - left + 1
   ```
7. Update `maxLength`.
8. Return `maxLength`.

---

# Dry Run

Input:

```text
nums = [1,1,1,0,0,0,1,1,1,1,0]
k = 2
```

Initially:

```text
left = 0
zeroCount = 0
maxLength = 0
```

As `right` moves:

```text
right = 0 → window [1]          → zeros = 0
right = 1 → window [1,1]        → zeros = 0
right = 2 → window [1,1,1]      → zeros = 0
right = 3 → window [1,1,1,0]    → zeros = 1
right = 4 → window [1,1,1,0,0]  → zeros = 2
```

The window is still valid because:

```text
zeroCount <= k
2 <= 2
```

Now:

```text
right = 5
```

The window contains three zeros:

```text
[1,1,1,0,0,0]
```

So:

```text
zeroCount = 3
```

But:

```text
3 > 2
```

The window is invalid.

Move `left` forward until one zero is removed.

After shrinking:

```text
left = 3
```

The window becomes valid again.

Continue expanding and keep updating the maximum length.

The final answer is:

```text
6
```

---

# Understanding the Code

## Initialize Variables

```java
int left = 0;
int maxLength = 0;
int zeroCount = 0;
```

Here:

```text
left       → left boundary of the window
right      → right boundary of the window
zeroCount  → number of zeros inside the window
maxLength  → maximum valid window length
```

---

## Expand the Window

```java
for(int right = 0; right < nums.length; right++){
```

The `right` pointer continuously expands the sliding window.

---

## Count Zeros

```java
if(nums[right] == 0){
    zeroCount++;
}
```

Whenever a zero enters the window, increase `zeroCount`.

---

## Shrink Invalid Window

```java
while(zeroCount > k){
```

If the window contains more zeros than we are allowed to flip, it becomes invalid.

So we move `left` forward.

```java
if(nums[left] == 0){
    zeroCount--;
}

left++;
```

If a zero leaves the window, decrease `zeroCount`.

---

## Update Maximum Length

```java
maxLength = Math.max(maxLength, right-left+1);
```

At this point, the window contains at most `k` zeros and is therefore valid.

The length of the current window is:

```text
right - left + 1
```

Update the maximum.

---

# Why Sliding Window Works

The condition for a valid window is:

```text
number of zeros <= k
```

We do not need to actually flip the zeros.

If a window contains at most `k` zeros, all those zeros **can be flipped**, so the entire window can become ones.

For example:

```text
[1,0,1,1,0]
```

If:

```text
k = 2
```

there are exactly two zeros.

After flipping:

```text
[1,1,1,1,1]
```

Therefore, the window length is a valid candidate.

---

# Important Sliding Window Pattern

This problem follows the pattern:

```text
Expand right
     ↓
Count zeros
     ↓
If zeros > k
     ↓
Shrink from left
     ↓
Update maximum length
```

This is a very common **variable-size sliding window** pattern.

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

    public int longestOnes(int[] nums, int k) {

        int left = 0;
        int maxLength = 0;
        int zeroCount = 0;

        for(int right = 0; right < nums.length; right++){

            if(nums[right] == 0){
                zeroCount++;
            }

            while(zeroCount > k){

                if(nums[left] == 0){
                    zeroCount--;
                }

                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
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
- Frequency/Count Tracking
- Longest Valid Subarray

---

# Constraints

- `1 <= nums.length <= 10^5`
- `nums[i]` is either `0` or `1`
- `0 <= k <= nums.length`

---

# Learning Outcome

This problem demonstrates how to use a **variable-size Sliding Window** to find the longest subarray satisfying a condition.

The important condition is:

```text
zeroCount <= k
```

Whenever the condition becomes invalid:

```text
zeroCount > k
```

we shrink the window from the left.

The main pattern to remember is:

```text
Expand → Check Condition → Shrink if Invalid → Update Answer
```

The solution achieves:

```text
Time  → O(n)
Space → O(1)
```