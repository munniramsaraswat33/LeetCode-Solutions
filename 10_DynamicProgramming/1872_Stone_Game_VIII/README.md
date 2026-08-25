# 1872. Stone Game VIII

> **Difficulty:** Medium  
> **Topics:** Array, Dynamic Programming, Prefix Sum, Game Theory

---

## Problem Statement

Alice and Bob play a game with an array of stones.

They start with the complete array `stones`.

On each turn:

1. A player chooses an index `i` such that `i >= 1`.
2. The player takes the first `i + 1` stones.
3. These stones are replaced by their sum.
4. The player gains the sum of the removed stones as their score.

The game continues until only two stones remain.

Alice goes first.

Both players play optimally.

Return the **maximum difference in score** that Alice can achieve over Bob.

---

## Example 1

### Input

```text
stones = [-1,2,-3,4,-5]
```

### Output

```text
5
```

---

# Approach

Use **Prefix Sum + Dynamic Programming**.

The important observation is that after combining the first `i + 1` stones, the value of those stones becomes:

```text
prefix[i]
```

where:

```text
prefix[i] = stones[0] + stones[1] + ... + stones[i]
```

So we first calculate the prefix sum array.

Then use dynamic programming to determine the best score difference that the current player can achieve from every possible state.

---

# Prefix Sum

For:

```text
stones = [-1,2,-3,4,-5]
```

the prefix sums are:

```text
prefix[0] = -1
prefix[1] = 1
prefix[2] = -2
prefix[3] = 2
prefix[4] = -3
```

The final prefix sum:

```text
prefix[4] = -3
```

represents the sum of all stones.

---

# Dynamic Programming

Define:

```text
f[i]
```

as the maximum score difference the current player can obtain when considering the state represented by `prefix[i]`.

For the final state:

```java
f[n-1] = pre[n-1];
```

Then work backwards.

For every `i`:

```java
f[i] = Math.max(f[i+1], pre[i] - f[i+1]);
```

There are two possibilities:

### Option 1

The current player keeps the result represented by:

```text
f[i+1]
```

### Option 2

The current player takes the current prefix sum:

```text
pre[i]
```

and the opponent can then achieve:

```text
f[i+1]
```

So the current player's score difference becomes:

```text
pre[i] - f[i+1]
```

Therefore:

```text
f[i] = max(f[i+1], pre[i] - f[i+1])
```

---

# Algorithm

1. Create a prefix sum array `pre`.
2. Calculate the cumulative sum of all stones.
3. Create a DP array `f`.
4. Initialize:
   ```text
   f[n-1] = pre[n-1]
   ```
5. Traverse from `n-2` down to `1`.
6. For every index:
   ```text
   f[i] = max(f[i+1], pre[i] - f[i+1])
   ```
7. Return:
   ```text
   f[1]
   ```

---

# Dry Run

Input:

```text
stones = [-1,2,-3,4,-5]
```

### Step 1: Prefix Sum

```text
pre = [-1,1,-2,2,-3]
```

---

### Step 2: Initialize DP

```text
f[4] = pre[4]
     = -3
```

So:

```text
f = [_,_,_,-,-3]
```

---

### Step 3: Calculate `f[3]`

```text
f[3] = max(f[4], pre[3] - f[4])
```

Substitute:

```text
f[3] = max(-3, 2 - (-3))
     = max(-3, 5)
     = 5
```

---

### Step 4: Calculate `f[2]`

```text
f[2] = max(f[3], pre[2] - f[3])
```

```text
f[2] = max(5, -2 - 5)
     = max(5, -7)
     = 5
```

---

### Step 5: Calculate `f[1]`

```text
f[1] = max(f[2], pre[1] - f[2])
```

```text
f[1] = max(5, 1 - 5)
     = max(5, -4)
     = 5
```

Therefore:

```text
Answer = f[1] = 5
```

---

# Understanding the Code

## Create Prefix Sum Array

```java
int[] pre = new int[n];

pre[0] = stones[0];

for(int i = 1; i < n; i++){
    pre[i] = pre[i-1] + stones[i];
}
```

Each `pre[i]` stores the sum of stones from index `0` to `i`.

---

## Create DP Array

```java
int[] f = new int[n];
```

The DP array stores the best score difference for each state.

---

## Base Case

```java
f[n-1] = pre[n-1];
```

At the final state, the score difference is the total prefix sum.

---

## Fill DP from Right to Left

```java
for(int i = n-2; i >= 1; i--){
```

We calculate the states backwards because `f[i]` depends on:

```text
f[i+1]
```

---

## DP Transition

```java
f[i] = Math.max(f[i+1], pre[i] - f[i+1]);
```

The current player chooses the better of:

```text
f[i+1]
```

or:

```text
pre[i] - f[i+1]
```

This represents optimal play between Alice and Bob.

---

## Return Answer

```java
return f[1];
```

The game effectively starts from the state represented by index `1`, so `f[1]` gives the maximum score difference Alice can achieve.

---

# Why Prefix Sum?

At every move, the first several stones are combined into one stone.

Instead of repeatedly calculating:

```text
stones[0] + stones[1] + ... + stones[i]
```

we store these sums in:

```text
pre[i]
```

Therefore, each prefix sum can be accessed in:

```text
O(1)
```

time.

---

# Why Dynamic Programming?

The game contains overlapping states.

For each possible prefix, the best score difference depends on the best result of the next state:

```text
f[i] → f[i+1]
```

Therefore, we can calculate the states once and reuse them.

The game-theory idea is:

```text
Current player's score
        -
Opponent's best score
```

which gives:

```text
pre[i] - f[i+1]
```

---

# Complexity Analysis

### Time Complexity

Prefix sum calculation:

```text
O(n)
```

DP calculation:

```text
O(n)
```

Overall:

```text
O(n)
```

---

### Space Complexity

We use:

```text
prefix array → O(n)
DP array     → O(n)
```

Therefore:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int stoneGameVIII(int[] stones) {

        int n = stones.length;

        int[] pre = new int[n];

        pre[0] = stones[0];

        for(int i = 1; i < n; i++){
            pre[i] = pre[i - 1] + stones[i];
        }

        int[] f = new int[n];

        f[n - 1] = pre[n - 1];

        for(int i = n - 2; i >= 1; i--){
            f[i] = Math.max(
                f[i + 1],
                pre[i] - f[i + 1]
            );
        }

        return f[1];
    }
}
```

---

# Key Concepts

- Dynamic Programming
- Prefix Sum
- Game Theory
- Array
- Optimal Strategy
- Bottom-Up DP

---

# Constraints

- `2 <= stones.length <= 10^5`
- `-10^4 <= stones[i] <= 10^4`

---

# Learning Outcome

This problem demonstrates how **Prefix Sum + Dynamic Programming** can simplify a two-player game.

The main idea is:

```text
Stones
  ↓
Prefix Sum
  ↓
Calculate best score difference
  ↓
DP from right to left
  ↓
Return f[1]
```

The important transition is:

```java
f[i] = Math.max(f[i + 1], pre[i] - f[i + 1]);
```

It represents the two choices available to the current player while assuming the opponent also plays optimally.

The solution achieves:

```text
Time  → O(n)
Space → O(n)
```