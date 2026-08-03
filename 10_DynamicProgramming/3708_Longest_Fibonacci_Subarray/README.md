# 3708. Longest Fibonacci Subarray

> **Difficulty:** Medium  
> **Topics:** Array, Dynamic Programming, Simulation

---

## Problem Statement

You are given an array of positive integers `nums`.

A **Fibonacci array** is a contiguous sequence in which every element from the third onward is equal to the sum of its previous two elements.

Return the **length of the longest Fibonacci subarray**.

> **Note:** Any subarray of length **1** or **2** is always considered a Fibonacci subarray.

---

## Example 1

### Input

```text
nums = [1,1,1,1,2,3,5,1]
```

### Output

```text
5
```

### Explanation

The longest Fibonacci subarray is:

```text
[1,1,2,3,5]
```

because

```text
1 + 1 = 2
1 + 2 = 3
2 + 3 = 5
```

---

## Example 2

### Input

```text
nums = [5,2,7,9,16]
```

### Output

```text
5
```

### Explanation

```text
5 + 2 = 7
2 + 7 = 9
7 + 9 = 16
```

Therefore, the whole array is Fibonacci.

---

## Example 3

### Input

```text
nums = [1000000000,1000000000,1000000000]
```

### Output

```text
2
```

### Explanation

No three consecutive numbers satisfy the Fibonacci condition, so the longest Fibonacci subarray has length **2**.

---

# Approach

A Fibonacci subarray must satisfy:

```text
nums[i] = nums[i-1] + nums[i-2]
```

Instead of adding numbers (which could overflow for large values), we check the equivalent condition:

```text
nums[i] - nums[i-1] == nums[i-2]
```

Traverse the array once while maintaining:

- `curr` → current Fibonacci subarray length.
- `ans` → maximum length found.

If the condition is satisfied:

```text
curr++
```

Otherwise:

```text
curr = 2
```

because every two consecutive elements form a valid Fibonacci subarray.

---

# Algorithm

1. Initialize:
   - `curr = 2`
   - `ans = 2`
2. Traverse the array from index `2`.
3. If:

```text
nums[i] - nums[i-1] == nums[i-2]
```

increase `curr`.

Otherwise,

```text
curr = 2
```

4. Update the maximum answer.
5. Return `ans`.

---

# Dry Run

Input

```text
nums = [1,1,2,3,5,8,4]
```

| Index | Value | Current Length | Maximum |
|------:|------:|---------------:|---------:|
| 0 | 1 | 2 | 2 |
| 1 | 1 | 2 | 2 |
| 2 | 2 | 3 | 3 |
| 3 | 3 | 4 | 4 |
| 4 | 5 | 5 | 5 |
| 5 | 8 | 6 | 6 |
| 6 | 4 | Reset → 2 | 6 |

Answer

```text
6
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

Only one traversal of the array is required.

---

### Space Complexity

```text
O(1)
```

Only a few variables are used.

---

# Java Solution

```java
class Solution {

    public int longestSubarray(int[] nums) {

        int n = nums.length;

        int curr = 2;
        int ans = 2;

        for (int i = 2; i < n; i++) {

            if (nums[i] - nums[i - 1] == nums[i - 2]) {
                curr++;
            } else {
                curr = 2;
            }

            ans = Math.max(ans, curr);
        }

        return ans;
    }
}
```

---

# Key Concepts

- Arrays
- Linear Traversal
- Simulation
- Fibonacci Sequence

---

# Constraints

- `3 <= nums.length <= 10⁵`
- `1 <= nums[i] <= 10⁹`

---

# Learning Outcome

This problem demonstrates how recognizing a mathematical property can simplify the implementation. By scanning the array once and maintaining the current valid segment length, we obtain an efficient **O(n)** solution with **O(1)** extra space. The subtraction-based check also avoids potential integer overflow that could occur when directly computing `nums[i-1] + nums[i-2]`.