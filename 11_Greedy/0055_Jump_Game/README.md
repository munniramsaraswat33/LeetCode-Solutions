# 55. Jump Game

> **Difficulty:** Medium  
> **Topics:** Greedy, Array

---

## Problem Statement

You are given an integer array `nums`.

Initially, you are positioned at the **first index**, and each element in the array represents your **maximum jump length** from that position.

Return **`true`** if you can reach the last index; otherwise, return **`false`**.

---

## Example 1

### Input

```text
nums = [2,3,1,1,4]
```

### Output

```text
true
```

### Explanation

One possible path:

```text
Index: 0 → 1 → 4

Jump: 2 → 3
```

The last index is reachable.

---

## Example 2

### Input

```text
nums = [3,2,1,0,4]
```

### Output

```text
false
```

### Explanation

No matter how you jump, you will eventually reach index **3**.

```text
nums[3] = 0
```

Since no further jump is possible, the last index cannot be reached.

---

# Approach

Use a **Greedy** strategy.

Maintain the **farthest index** that can currently be reached.

For every index:

- If the current index is greater than the farthest reachable index, the destination is impossible to reach.
- Otherwise, update the farthest reachable position.

If at any time the farthest reachable position reaches or passes the last index, return `true`.

---

# Algorithm

1. Initialize:

```text
farthest = 0
```

2. Traverse the array.

3. If

```text
i > farthest
```

return `false`.

4. Update

```text
farthest = max(farthest, i + nums[i])
```

5. If

```text
farthest >= nums.length - 1
```

return `true`.

6. If traversal completes, return `true`.

---

# Dry Run

Input

```text
nums = [2,3,1,1,4]
```

| Index | nums[i] | Farthest Reachable |
|------:|--------:|-------------------:|
| 0 | 2 | 2 |
| 1 | 3 | 4 |

Since the farthest reachable index is **4**, which is the last index, the answer is:

```text
true
```

---

## Another Dry Run

Input

```text
nums = [3,2,1,0,4]
```

| Index | nums[i] | Farthest Reachable |
|------:|--------:|-------------------:|
| 0 | 3 | 3 |
| 1 | 2 | 3 |
| 2 | 1 | 3 |
| 3 | 0 | 3 |
| 4 | - | Cannot Reach |

Since index **4** is beyond the farthest reachable position, return:

```text
false
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

The array is traversed once.

---

### Space Complexity

```text
O(1)
```

Only one variable is maintained.

---

# Java Solution

```java
class Solution {

    public boolean canJump(int[] nums) {

        int farthest = 0;

        for (int i = 0; i < nums.length; i++) {

            if (i > farthest)
                return false;

            farthest = Math.max(farthest, i + nums[i]);

            if (farthest >= nums.length - 1)
                return true;
        }

        return true;
    }
}
```

---

# Key Concepts

- Greedy Algorithm
- Array Traversal
- Reachability
- Maximum Reach

---

# Constraints

- `1 <= nums.length <= 10⁴`
- `0 <= nums[i] <= 10⁵`

---

# Learning Outcome

This problem is a classic example of a **Greedy Algorithm**. Instead of exploring every possible jump using recursion or dynamic programming, we continuously track the **farthest reachable index**. If the current position is ever beyond this limit, reaching the end is impossible. This yields an optimal solution with **O(n)** time and **O(1)** extra space.