# 1137. N-th Tribonacci Number

> **Difficulty:** Easy  
> **Topics:** Dynamic Programming, Array, Math

---

## Problem Statement

The Tribonacci sequence is defined as:

```text
T0 = 0
T1 = 1
T2 = 1
```

For `n >= 0`:

```text
Tn+3 = Tn + Tn+1 + Tn+2
```

Given an integer `n`, return the value of `Tn`.

---

## Example 1

### Input

```text
n = 4
```

### Output

```text
4
```

### Explanation

The Tribonacci sequence starts as:

```text
T0 = 0
T1 = 1
T2 = 1
T3 = 0 + 1 + 1 = 2
T4 = 1 + 1 + 2 = 4
```

Therefore:

```text
T4 = 4
```

---

## Example 2

### Input

```text
n = 25
```

### Output

```text
1389537
```

### Explanation

The sequence is calculated using:

```text
Tn = Tn-1 + Tn-2 + Tn-3
```

until `T25` is obtained.

---

# Approach

Use **Dynamic Programming**.

The current Tribonacci number depends on the previous three values:

```text
Tn = Tn-1 + Tn-2 + Tn-3
```

Instead of calculating the same values repeatedly using recursion, store the already calculated values in a DP array.

We initialize:

```text
dp[0] = 0
dp[1] = 1
dp[2] = 1
```

Then calculate every value from `3` to `n`.

---

# Algorithm

1. If `n < 2`, return `n`.
2. If `n == 2`, return `1`.
3. Create a DP array of size `n + 1`.
4. Initialize:
   ```text
   dp[0] = 0
   dp[1] = 1
   dp[2] = 1
   ```
5. Traverse from `i = 3` to `n`.
6. Calculate:
   ```text
   dp[i] = dp[i-1] + dp[i-2] + dp[i-3]
   ```
7. Return `dp[n]`.

---

# Dry Run

Input:

```text
n = 5
```

### Initial Values

```text
dp[0] = 0
dp[1] = 1
dp[2] = 1
```

So:

```text
dp = [0,1,1,_,_,_]
```

### Calculate `dp[3]`

```text
dp[3] = dp[2] + dp[1] + dp[0]
      = 1 + 1 + 0
      = 2
```

```text
dp = [0,1,1,2,_,_]
```

### Calculate `dp[4]`

```text
dp[4] = dp[3] + dp[2] + dp[1]
      = 2 + 1 + 1
      = 4
```

```text
dp = [0,1,1,2,4,_]
```

### Calculate `dp[5]`

```text
dp[5] = dp[4] + dp[3] + dp[2]
      = 4 + 2 + 1
      = 7
```

Final:

```text
dp = [0,1,1,2,4,7]
```

Therefore:

```text
Answer = 7
```

---

# Understanding the Code

## Handle Base Cases

```java
if(n < 2) return n;

if(n == 2) return 1;
```

The first three Tribonacci values are fixed:

```text
T0 = 0
T1 = 1
T2 = 1
```

So we return them directly.

---

## Create DP Array

```java
int[] dp = new int[n+1];
```

The array stores all Tribonacci values from `T0` to `Tn`.

---

## Initialize Base Values

```java
dp[0] = 0;
dp[1] = 1;
dp[2] = 1;
```

These are the starting values of the sequence.

---

## Calculate Tribonacci Values

```java
for(int i = 3; i <= n; i++){
    dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
}
```

Each value is calculated using the previous three values.

For example:

```text
dp[5] = dp[4] + dp[3] + dp[2]
```

---

## Return the Answer

```java
return dp[n];
```

After filling the DP array, `dp[n]` contains the required Tribonacci number.

---

# Why Dynamic Programming?

A recursive solution would repeatedly calculate the same Tribonacci values.

For example:

```text
T5
├── T4
│   ├── T3
│   ├── T2
│   └── T1
├── T3
└── T2
```

The same values such as `T3` and `T2` are calculated multiple times.

Dynamic Programming avoids this repeated work by storing every calculated value.

```text
Calculate once
     ↓
Store in dp[]
     ↓
Reuse later
```

---

# DP Pattern

This problem follows a simple bottom-up DP pattern:

```text
Base Cases
    ↓
T0, T1, T2
    ↓
Calculate T3
    ↓
Calculate T4
    ↓
Calculate T5
    ↓
...
    ↓
Calculate Tn
```

The transition is:

```java
dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
```

---

# Complexity Analysis

### Time Complexity

The loop runs from `3` to `n`.

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

    public int tribonacci(int n) {

        if(n < 2) return n;

        if(n == 2) return 1;

        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;

        for(int i = 3; i <= n; i++){

            dp[i] = dp[i - 1]
                  + dp[i - 2]
                  + dp[i - 3];
        }

        return dp[n];
    }
}
```

---

# Key Concepts

- Dynamic Programming
- Bottom-Up DP
- Array
- Recurrence Relation
- Base Cases
- State Transition

---

# Constraints

- `0 <= n <= 37`

---

# Learning Outcome

This problem demonstrates how **Dynamic Programming** can be used to calculate a sequence efficiently.

The main idea is:

```text
T0 = 0
T1 = 1
T2 = 1
```

and:

```text
Tn = Tn-1 + Tn-2 + Tn-3
```

Instead of recalculating previous values, we store them in the DP array.

The important transition is:

```java
dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
```

The solution achieves:

```text
Time  → O(n)
Space → O(n)
```