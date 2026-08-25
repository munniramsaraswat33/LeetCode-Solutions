# 746. Min Cost Climbing Stairs

> **Difficulty:** Easy  
> **Topics:** Array, Dynamic Programming, Recursion, Memoization

---

## Problem Statement

You are given an integer array `cost` where:

```text
cost[i]
```

is the cost of stepping on the `ith` stair.

Once you pay the cost of a stair, you can climb either:

- 1 step
- 2 steps

You can start from either:

```text
index 0
```

or:

```text
index 1
```

Your goal is to reach the top of the floor.

Return the **minimum cost** required to reach the top.

---

## Example 1

### Input

```text
cost = [10,15,20]
```

### Output

```text
15
```

### Explanation

Start from stair `1`:

```text
cost[1] = 15
```

Then jump two steps directly to the top.

Total cost:

```text
15
```

---

## Example 2

### Input

```text
cost = [1,100,1,1,1,100,1,1,100,1]
```

### Output

```text
6
```

### Explanation

An optimal path is:

```text
0 → 2 → 3 → 4 → 6 → 7 → 9 → top
```

The total cost is:

```text
1 + 1 + 1 + 1 + 1 + 1 = 6
```

---

# Approach

Use **Dynamic Programming with Recursion and Memoization**.

For every stair `n`, we have two choices:

```text
Come from stair n-1
```

or:

```text
Come from stair n-2
```

Therefore:

```text
mincost(n)
=
cost[n] + min(
    mincost(n-1),
    mincost(n-2)
)
```

We store already calculated results in the `dp` array so that the same subproblem is not calculated again.

This technique is called **Memoization**.

---

# Algorithm

1. Create a DP array of size `cost.length`.
2. Fill the DP array with `-1`.
3. Define a recursive function `mincost()`:
   - If `n` is `0` or `1`, return `cost[n]`.
   - If the result is already stored in `dp[n]`, return it.
   - Otherwise calculate:
     ```text
     cost[n] + min(mincost(n-1), mincost(n-2))
     ```
   - Store the result in `dp[n]`.
4. The top can be reached from either of the last two stairs.
5. Therefore return:
   ```text
   min(
       mincost(n-1),
       mincost(n-2)
   )
   ```

---

# Dry Run

Input:

```text
cost = [10,15,20]
```

### Step 1

We create:

```text
dp = [-1,-1,-1]
```

We need:

```text
min(
    mincost(2),
    mincost(1)
)
```

---

### Step 2: Calculate `mincost(2)`

```text
cost[2] = 20
```

We can reach stair `2` from:

```text
stair 1
```

or:

```text
stair 0
```

Therefore:

```text
mincost(2)
=
20 + min(15,10)
```

```text
= 20 + 10
= 30
```

Store:

```text
dp[2] = 30
```

---

### Step 3: Calculate `mincost(1)`

Base case:

```text
mincost(1) = cost[1]
           = 15
```

---

### Step 4: Reach the Top

The top can be reached from stair `1` or stair `2`.

```text
answer = min(30,15)
       = 15
```

Therefore:

```text
Output = 15
```

---

# Understanding the Code

## Recursive Function

```java
public int mincost(int n, int[] cost, int[] dp)
```

This function calculates the minimum cost required to reach stair `n`.

---

## Base Case

```java
if(n == 1 || n == 0) return cost[n];
```

For the first two stairs, there is no previous decision required.

So we directly return their costs.

---

## Check Memoization

```java
if(dp[n] != -1) return dp[n];
```

If we have already calculated the answer for stair `n`, return it immediately.

This avoids repeated calculations.

---

## DP Transition

```java
return dp[n] = cost[n] +
    Math.min(
        mincost(n-1, cost, dp),
        mincost(n-2, cost, dp)
    );
```

To reach stair `n`, we can come from:

```text
n - 1
```

or:

```text
n - 2
```

We choose the cheaper option.

Therefore:

```text
dp[n] =
cost[n] + min(dp[n-1], dp[n-2])
```

---

## Initialize DP Array

```java
int[] dp = new int[cost.length];
Arrays.fill(dp, -1);
```

Every value is initially set to `-1`.

This means:

```text
-1 → not calculated yet
```

---

## Reach the Top

```java
return Math.min(
    mincost(cost.length-1, cost, dp),
    mincost(cost.length-2, cost, dp)
);
```

We do not have to pay a cost for the top.

The top can be reached by taking either:

```text
1 step from the last stair
```

or:

```text
2 steps from the second-last stair
```

So we take the minimum of both possibilities.

---

# Why Memoization?

Without memoization, recursion would calculate the same stair multiple times.

For example:

```text
mincost(5)
├── mincost(4)
│   ├── mincost(3)
│   └── mincost(2)
└── mincost(3)
```

Notice that:

```text
mincost(3)
```

is calculated more than once.

Using:

```java
if(dp[n] != -1) return dp[n];
```

we calculate each state only once.

---

# DP Recurrence

The main recurrence is:

```text
dp[n] = cost[n] + min(dp[n-1], dp[n-2])
```

The base cases are:

```text
dp[0] = cost[0]
dp[1] = cost[1]
```

Finally:

```text
answer = min(dp[n-1], dp[n-2])
```

---

# Recursion + Memoization Pattern

This problem follows the common pattern:

```text
Recursive Function
        ↓
Check Base Case
        ↓
Check dp[]
        ↓
Calculate Smaller Problems
        ↓
Store Answer
        ↓
Return Answer
```

This is called **Top-Down Dynamic Programming**.

---

# Complexity Analysis

### Time Complexity

Each stair is calculated only once because of memoization.

Therefore:

```text
O(n)
```

---

### Space Complexity

The DP array uses:

```text
O(n)
```

space.

The recursive call stack can also reach:

```text
O(n)
```

in the worst case.

Therefore overall:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int mincost(int n, int[] cost, int[] dp) {

        if(n == 1 || n == 0)
            return cost[n];

        if(dp[n] != -1)
            return dp[n];

        return dp[n] = cost[n] +
            Math.min(
                mincost(n - 1, cost, dp),
                mincost(n - 2, cost, dp)
            );
    }

    public int minCostClimbingStairs(int[] cost) {

        int[] dp = new int[cost.length];

        Arrays.fill(dp, -1);

        return Math.min(
            mincost(cost.length - 1, cost, dp),
            mincost(cost.length - 2, cost, dp)
        );
    }
}
```

---

# Key Concepts

- Dynamic Programming
- Recursion
- Memoization
- Array
- Top-Down DP
- Minimum Cost
- Recurrence Relation

---

# Constraints

- `2 <= cost.length <= 1000`
- `0 <= cost[i] <= 999`

---

# Learning Outcome

This problem demonstrates how **Recursion + Memoization** can be used to solve a minimum-cost optimization problem.

The main idea is:

```text
Current Stair
     ↓
Can come from n-1 or n-2
     ↓
Choose the minimum cost
     ↓
Store result in dp[]
```

The important transition is:

```java
dp[n] = cost[n] +
        Math.min(dp[n-1], dp[n-2]);
```

The solution uses **Top-Down Dynamic Programming** and avoids repeated calculations through memoization.

The solution achieves:

```text
Time  → O(n)
Space → O(n)
```