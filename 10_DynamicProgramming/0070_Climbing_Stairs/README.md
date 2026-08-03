# 70. Climbing Stairs

> **Difficulty:** Easy  
> **Topics:** Dynamic Programming, Fibonacci

---

## Problem Statement

You are climbing a staircase.

It takes **n** steps to reach the top.

Each time you can either climb:

- **1 step**
- **2 steps**

Return the number of **distinct ways** to reach the top.

---

## Example 1

### Input

```text
n = 2
```

### Output

```text
2
```

### Explanation

There are two ways:

```text
1 + 1
2
```

---

## Example 2

### Input

```text
n = 3
```

### Output

```text
3
```

### Explanation

```text
1 + 1 + 1
1 + 2
2 + 1
```

---

# Approach

This problem follows the **Dynamic Programming** pattern.

To reach the **nth** stair:

- You can come from `(n-1)` by taking **1 step**.
- You can come from `(n-2)` by taking **2 steps**.

Therefore,

```text
dp[i] = dp[i-1] + dp[i-2]
```

This is exactly the **Fibonacci sequence**.

---

# Algorithm

1. Create a DP array of size `n + 1`.
2. Initialize:
   - `dp[0] = 1`
3. Traverse from `1` to `n`.
4. If `i == 1`

```text
dp[1] = dp[0]
```

Else

```text
dp[i] = dp[i-1] + dp[i-2]
```

5. Return `dp[n]`.

---

# Dry Run

Input

```text
n = 5
```

| i | dp[i] |
|---|-------:|
| 0 | 1 |
| 1 | 1 |
| 2 | 2 |
| 3 | 3 |
| 4 | 5 |
| 5 | 8 |

Answer

```text
8
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

Only one traversal is performed.

---

### Space Complexity

```text
O(n)
```

A DP array of size `n+1` is used.

---

# Java Solution

```java
class Solution {
    public int climbStairs(int n) {

        int dp[] = new int[n + 1];

        dp[0] = 1;

        for (int i = 1; i <= n; i++) {

            if (i == 1)
                dp[i] = dp[i - 1];
            else
                dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
}
```

---

# Key Concepts

- Dynamic Programming
- Tabulation
- Fibonacci Sequence
- Bottom-Up DP

---

# Constraints

- `1 <= n <= 45`

---

# Learning Outcome

This problem is one of the most fundamental **Dynamic Programming** problems. It introduces the concept of breaking a problem into smaller subproblems and solving them iteratively using a DP table. It also demonstrates how many DP problems reduce to a **Fibonacci-style recurrence**.