# 877. Stone Game

> **Difficulty:** Medium  
> **Topics:** Math, Dynamic Programming, Game Theory

---

## Problem Statement

Alice and Bob play a game with an even number of stone piles arranged in a row.

On each turn:

- A player removes the **entire leftmost** or **rightmost** pile.
- Alice always moves first.
- Both players play optimally.

Return `true` if Alice wins; otherwise, return `false`.

---

## Example 1

### Input

```text
piles = [5,3,4,5]
```

### Output

```text
true
```

---

## Example 2

### Input

```text
piles = [3,7,2,3]
```

### Output

```text
true
```

---

# Key Observation

The number of piles is always **even**.

Alice can observe the sums of:

- Even-indexed piles
- Odd-indexed piles

Before making the first move.

She can always force herself to pick piles from whichever parity (even or odd indices) has the larger total.

Since:

- the total number of stones is **odd**, and
- there are no ties,

Alice is guaranteed to collect more stones than Bob.

Therefore, **Alice always wins**.

---

# Approach

No simulation or dynamic programming is actually required.

Simply return:

```text
true
```

because Alice always has a winning strategy.

---

# Proof

Suppose there are:

```text
n = even
```

piles.

The piles alternate between:

```text
Even indices
Odd indices
```

On her first move, Alice chooses either the first or last pile.

This choice determines which parity of indices she will continue to collect throughout the game.

She simply chooses the parity with the larger total sum.

Since the total number of stones is odd, the two parity sums cannot be equal.

Hence Alice is guaranteed to obtain strictly more stones.

---

# Complexity Analysis

### Time Complexity

```text
O(1)
```

---

### Space Complexity

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public boolean stoneGame(int[] piles) {
        return true;
    }
}
```

---

# Key Concepts

- Game Theory
- Mathematical Observation
- Optimal Strategy

---

# Constraints

- `2 <= piles.length <= 500`
- `piles.length` is even.
- `1 <= piles[i] <= 500`
- `sum(piles)` is odd.

---

# Learning Outcome

This problem is a classic example where understanding the game's mathematical properties is more important than implementing a complex algorithm. Although tagged under **Dynamic Programming**, recognizing the parity strategy leads to an elegant **O(1)** solution.