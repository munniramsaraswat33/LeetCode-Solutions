# 198. House Robber

> **Difficulty:** Medium  
> **Topics:** Array, Dynamic Programming

---

## Problem Statement

You are a professional robber planning to rob houses along a street.

Each house has a certain amount of money. The houses are arranged in a line.

The only restriction is that you **cannot rob two adjacent houses**, because the security systems are connected and will alert the police.

Given an integer array `nums`, where:

```text
nums[i]
```

represents the amount of money in the `ith` house, return the **maximum amount of money** you can rob without robbing two adjacent houses.

---

## Example 1

### Input

```text
nums = [1,2,3,1]
```

### Output

```text
4
```

### Explanation

Rob house `1` and house `3`:

```text
1 + 3 = 4
```

You cannot rob adjacent houses.

Therefore:

```text
Maximum = 4
```

---

## Example 2

### Input

```text
nums = [2,7,9,3,1]
```

### Output

```text
12
```

### Explanation

Rob houses:

```text
2, 9, 1
```

Total:

```text
2 + 9 + 1 = 12
```

Therefore:

```text
Maximum = 12
```

---

# Approach

Use **Dynamic Programming**.

At every house, we have two choices:

1. **Skip the current house**
2. **Rob the current house**

If we rob the current house, we cannot rob the previous house.

Therefore, for every position:

```text
dp[i] = max(
    dp[i-1],
    nums[i-1] + dp[i-2]
)
```

where `dp[i]` represents the maximum money that can be robbed from the first `i` houses.

---

# Algorithm

1. Let:
   ```text
   m = nums.length
   ```
2. If there is only one house, return its money.
3. Create a DP array of size `m + 1`.
4. Initialize:
   ```text
   dp[0] = 0
   dp[1] = nums[0]
   ```
5. For every house from `2` to `m`:
   - Skip the current house:
     ```text
     dp[i-1]
     ```
   - Rob the current house:
     ```text
     nums[i-1] + dp[i-2]
     ```
   - Take the maximum of both.
6. Return `dp[m]`.

---

# Dry Run

Input:

```text
nums = [2,7,9,3,1]
```

### Initial Values

```text
dp[0] = 0
dp[1] = 2
```

So:

```text
dp = [0,2,_,_,_,_]
```

---

### Calculate `dp[2]`

For house `2`:

Skip:

```text
dp[1] = 2
```

Rob:

```text
nums[1] + dp[0]
= 7 + 0
= 7
```

Therefore:

```text
dp[2] = max(2,7)
      = 7
```

---

### Calculate `dp[3]`

Skip:

```text
dp[2] = 7
```

Rob:

```text
nums[2] + dp[1]
= 9 + 2
= 11
```

Therefore:

```text
dp[3] = 11
```

---

### Calculate `dp[4]`

Skip:

```text
dp[3] = 11
```

Rob:

```text
nums[3] + dp[2]
= 3 + 7
= 10
```

Therefore:

```text
dp[4] = 11
```

---

### Calculate `dp[5]`

Skip:

```text
dp[4] = 11
```

Rob:

```text
nums[4] + dp[3]
= 1 + 11
= 12
```

Therefore:

```text
dp[5] = 12
```

Final DP array:

```text
[0,2,7,11,11,12]
```

Answer:

```text
12
```

---

# Understanding the Code

## Handle Single House

```java
if(m == 1) return nums[0];
```

If there is only one house, there is no adjacent house to worry about.

So we simply rob it.

---

## Create DP Array

```java
int[] dp = new int[m+1];
```

`dp[i]` represents the maximum money that can be robbed from the first `i` houses.

---

## Base Cases

```java
dp[0] = 0;
dp[1] = nums[0];
```

For zero houses:

```text
dp[0] = 0
```

For one house:

```text
dp[1] = nums[0]
```

---

## DP Transition

```java
dp[i] = Math.max(
    dp[i-1],
    nums[i-1] + dp[i-2]
);
```

There are two possibilities.

### Option 1: Skip Current House

```java
dp[i-1]
```

If we don't rob the current house, the answer remains the best answer for the previous houses.

### Option 2: Rob Current House

```java
nums[i-1] + dp[i-2]
```

If we rob the current house, we must skip the previous house.

So we add:

```text
current house money
+
best answer before previous house
```

Finally, take the larger value.

---

# Why `dp[i-2]`?

Suppose we are currently considering house `i`.

If we rob it, we cannot rob house `i-1`.

Therefore, the previous possible house is:

```text
i-2
```

So:

```text
Rob current
=
nums[i-1] + dp[i-2]
```

---

# DP Pattern

This problem follows the common **Take or Skip DP** pattern:

```text
Current Element
      |
      +---- Skip → dp[i-1]
      |
      +---- Take → value + dp[i-2]
```

Then:

```text
dp[i] = max(skip, take)
```

This pattern is useful in many problems where adjacent elements cannot both be selected.

---

# Example Decision

For:

```text
nums = [2,7,9,3,1]
```

The DP decisions are:

```text
House:  1  2  3  4  5
Money:  2  7  9  3  1
```

Best values:

```text
dp:     0  2  7 11 11 12
```

The final answer `12` corresponds to robbing:

```text
2 + 9 + 1
```

---

# Complexity Analysis

### Time Complexity

We process every house exactly once.

Therefore:

```text
O(n)
```

---

### Space Complexity

The DP array contains `n + 1` elements.

Therefore:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int rob(int[] nums) {

        int m = nums.length;

        if(m == 1)
            return nums[0];

        int[] dp = new int[m + 1];

        dp[0] = 0;
        dp[1] = nums[0];

        for(int i = 2; i <= m; i++){

            dp[i] = Math.max(
                dp[i - 1],
                nums[i - 1] + dp[i - 2]
            );
        }

        return dp[m];
    }
}
```

---

# Key Concepts

- Dynamic Programming
- Array
- Take or Skip
- Recurrence Relation
- Bottom-Up DP
- Maximum Optimization

---

# Constraints

- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 400`

---

# Learning Outcome

This problem demonstrates the classic **Take or Skip Dynamic Programming** pattern.

The main idea is:

```text
For every house:

Skip it
   ↓
dp[i-1]

OR

Rob it
   ↓
nums[i-1] + dp[i-2]

Take maximum
   ↓
dp[i]
```

The important transition is:

```java
dp[i] = Math.max(
    dp[i - 1],
    nums[i - 1] + dp[i - 2]
);
```

This ensures that two adjacent houses are never robbed.

The solution achieves:

```text
Time  → O(n)
Space → O(n)
```