# 2958. Length of Longest Subarray With at Most K Frequency

> **Difficulty:** Medium  
> **Topics:** Array, Hash Table, Sliding Window

---

## Problem Statement

Given an integer array `nums` and an integer `k`, find the length of the **longest subarray** in which every element appears at most `k` times.

A subarray is a contiguous, non-empty part of an array.

---

## Example 1

### Input

```text
nums = [1,2,3,1,2,3,1,2]
k = 2
```

### Output

```text
6
```

### Explanation

The longest valid subarray is:

```text
[1,2,3,1,2,3]
```

Frequencies:

```text
1 → 2 times
2 → 2 times
3 → 2 times
```

Every element appears at most `2` times.

Therefore:

```text
answer = 6
```

---

## Example 2

### Input

```text
nums = [1,2,1,2,1,2,1,2]
k = 1
```

### Output

```text
2
```

### Explanation

When `k = 1`, every element can appear only once.

The longest valid subarray is:

```text
[1,2]
```

Length:

```text
2
```

---

## Example 3

### Input

```text
nums = [5,5,5,5,5,5,5]
k = 4
```

### Output

```text
4
```

### Explanation

The number `5` can appear at most `4` times.

Therefore:

```text
[5,5,5,5]
```

is the longest valid subarray.

---

# Approach

This problem is solved using the **Sliding Window** technique with a `HashMap`.

We maintain a window:

```text
[l ... r]
```

where:

- `l` is the left boundary.
- `r` is the right boundary.

The `HashMap` stores the frequency of every element inside the current window.

---

# Sliding Window Idea

For every new element at index `r`:

1. Add `nums[r]` to the frequency map.
2. If its frequency becomes greater than `k`, the current window is invalid.
3. Move the left pointer `l` forward until the frequency becomes valid again.
4. Update the maximum window length.

The important condition is:

```java
while (map.get(nums[r]) > k)
```

This means the newly added element has appeared too many times.

---

# Why Do We Only Check `nums[r]`?

When we add:

```text
nums[r]
```

only its frequency increases.

All other elements keep the same frequency.

Therefore, if the window becomes invalid, the only element that can have caused the violation is:

```text
nums[r]
```

So we only need to check:

```java
map.get(nums[r]) > k
```

---

# Algorithm

1. Initialize:
   ```text
   ans = 0
   l = 0
   ```
2. Create a `HashMap` to store frequencies.
3. Move `r` from `0` to `n - 1`.
4. Add `nums[r]` to the frequency map.
5. While:
   ```text
   frequency(nums[r]) > k
   ```
   remove `nums[l]` from the window and move `l` forward.
6. Calculate the current window length:
   ```text
   r - l + 1
   ```
7. Update `ans`.
8. Return `ans`.

---

# Dry Run

### Input

```text
nums = [1,2,1,2,1,2]
k = 1
```

Initially:

```text
l = 0
ans = 0
```

### `r = 0`

Window:

```text
[1]
```

Frequency:

```text
1 → 1
```

Valid.

```text
ans = 1
```

---

### `r = 1`

Window:

```text
[1,2]
```

Frequency:

```text
1 → 1
2 → 1
```

Valid.

```text
ans = 2
```

---

### `r = 2`

Add `1`:

```text
[1,2,1]
```

Frequency:

```text
1 → 2
2 → 1
```

Since:

```text
2 > k
```

the window is invalid.

Remove `nums[l]`:

```text
remove 1
l = 1
```

Now:

```text
[2,1]
```

Frequency:

```text
1 → 1
2 → 1
```

Valid again.

Window length:

```text
2
```

---

### `r = 3`

Add `2`:

```text
[2,1,2]
```

Frequency:

```text
2 → 2
```

Invalid.

Remove from left:

```text
remove 2
l = 2
```

Window becomes:

```text
[1,2]
```

Valid.

Maximum remains:

```text
2
```

Final answer:

```text
2
```

---

# Why Sliding Window Works

The important property is that once a window becomes invalid, we move the left pointer forward until it becomes valid again.

The window always maintains:

```text
frequency of every element <= k
```

Therefore, whenever we calculate:

```java
r - l + 1
```

we know that the current window is valid.

Since `l` and `r` only move forward, no element is unnecessarily processed again.

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

The right pointer moves from left to right once.

The left pointer also moves from left to right at most once across the entire algorithm.

Therefore:

```text
O(n)
```

---

### Space Complexity

The `HashMap` can contain up to `n` distinct elements:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int maxSubarrayLength(int[] nums, int k) {

        int ans = 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        int l = 0;

        for (int r = 0; r < nums.length; r++) {

            // Add current element to the window
            map.put(
                nums[r],
                map.getOrDefault(nums[r], 0) + 1
            );

            // Shrink window if frequency exceeds k
            while (map.get(nums[r]) > k) {

                map.put(
                    nums[l],
                    map.get(nums[l]) - 1
                );

                l++;
            }

            // Update maximum valid window length
            ans = Math.max(ans, r - l + 1);
        }

        return ans;
    }
}
```

---

# Key Concepts

- Sliding Window
- Two Pointers
- HashMap
- Frequency Counting
- Subarray
- Variable-Size Window

---

# Constraints

- `1 <= nums.length <= 10⁵`
- `1 <= nums[i] <= 10⁹`
- `1 <= k <= nums.length`

---

# Learning Outcome

This problem is an important example of a **variable-size Sliding Window**.

The general pattern is:

```text
Expand right
     ↓
Check condition
     ↓
Invalid?
     ↓
Move left until valid
     ↓
Update answer
```

The core template is:

```java
for (int r = 0; r < nums.length; r++) {

    // Add nums[r]

    while (window_is_invalid) {

        // Remove nums[l]
        l++;
    }

    ans = Math.max(ans, r - l + 1);
}
```

For this problem, the invalid condition is:

```java
map.get(nums[r]) > k
```

and the final complexity is:

```text
Time:  O(n)
Space: O(n)
```