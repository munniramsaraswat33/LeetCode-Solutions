# 3870. Count Commas in Range

**LeetCode:** [3870. Count Commas in Range](https://leetcode.com/problems/count-commas-in-range/)

**Difficulty:** Easy

**Primary Topic:** Math

**Pattern:** Mathematical Observation

---

## Problem Statement

Given an integer `n`, count the total number of commas that appear when writing every integer from `1` to `n` in standard decimal notation.

A comma is used as a thousands separator.

For example:

```text
1,000
10,000
100,000
```

Each of these numbers contains one comma.

---

## Example 1

### Input

```text
n = 1000
```

### Output

```text
1
```

### Explanation

Only:

```text
1,000
```

contains a comma.

Therefore:

```text
Answer = 1
```

---

## Example 2

### Input

```text
n = 999
```

### Output

```text
0
```

### Explanation

All numbers from `1` to `999` contain no commas.

---

## Approach

The key observation is that a number gets its first comma when it reaches:

```text
1000
```

Therefore:

- If `n < 1000`, the answer is `0`.
- If `n >= 1000`, every number from `1000` through `n` contributes exactly one comma.

The number of integers in the range:

```text
[1000, n]
```

is:

```text
n - 1000 + 1
```

So:

```text
answer = n - 999
```

---

# Intuition

Consider:

```text
1 → 999
```

No number contains a comma.

Starting from:

```text
1000
```

every number up to the given `n` contains at least one comma.

For example, if:

```text
n = 1005
```

the numbers containing commas are:

```text
1000
1001
1002
1003
1004
1005
```

There are:

```text
1005 - 1000 + 1 = 6
```

numbers.

Therefore the answer is `6`.

---

# Algorithm

1. If `n < 1000`, return `0`.
2. Otherwise, count the integers from `1000` to `n`.
3. Use the inclusive range formula:

```text
n - 1000 + 1
```

4. Return the result.

---

# Dry Run

Suppose:

```text
n = 1005
```

Since:

```text
1005 >= 1000
```

we calculate:

```text
1005 - 1000 + 1
= 6
```

The six numbers are:

```text
1000
1001
1002
1003
1004
1005
```

Each contributes exactly one comma.

Therefore:

```text
Answer = 6
```

---

# Java Solution

```java
class Solution {
    public int countCommas(int n) {
        if(n < 1000){
            return 0;
        }

        return n - 1000 + 1;
    }
}
```

---

# Code Explanation

## Check Numbers Below 1000

```java
if(n < 1000){
    return 0;
}
```

Numbers from `1` to `999` do not contain commas.

Therefore, if `n` is less than `1000`, the answer is immediately `0`.

---

## Count Numbers Starting From 1000

```java
return n - 1000 + 1;
```

The range is:

```text
1000 → n
```

The number of integers in an inclusive range `[L, R]` is:

```text
R - L + 1
```

Here:

```text
L = 1000
R = n
```

Therefore:

```text
n - 1000 + 1
```

---

# Why `+1`?

The range is inclusive.

For example:

```text
1000, 1001, 1002
```

contains `3` numbers.

Using:

```text
1002 - 1000 = 2
```

would miss one number.

So we use:

```text
1002 - 1000 + 1 = 3
```

---

# Important Observation

The entire problem can be reduced to one threshold:

```text
1000
```

Before `1000`:

```text
No comma
```

From `1000` onward:

```text
At least one comma
```

Under the problem's constraints, each number in the relevant range contributes exactly one comma, so simply counting the numbers from `1000` to `n` gives the answer.

---

# Complexity Analysis

## Time Complexity

```text
O(1)
```

Only a constant number of operations are performed.

---

## Space Complexity

```text
O(1)
```

No additional data structures are used.

---

# Key Concepts

- Mathematical Observation
- Inclusive Range Counting
- Threshold-Based Logic
- Constant-Time Solution

---

# Common Mistakes

## Mistake 1: Looping From 1 to n

A brute-force solution might check every number individually.

That is unnecessary.

The mathematical observation allows the answer to be calculated directly.

---

## Mistake 2: Forgetting the Inclusive Range

The correct formula is:

```text
n - 1000 + 1
```

not:

```text
n - 1000
```

because `1000` itself contains a comma.

---

## Mistake 3: Returning 1 for n = 1000

For:

```text
n = 1000
```

the formula gives:

```text
1000 - 1000 + 1 = 1
```

which is correct.

---

# Edge Cases

### `n < 1000`

```text
n = 999
```

Answer:

```text
0
```

---

### `n = 1000`

```text
1000
```

Answer:

```text
1
```

---

### `n = 1001`

Numbers containing commas:

```text
1000
1001
```

Answer:

```text
2
```

---

# Pattern Recognition

When a problem asks you to count elements in a continuous range, look for a mathematical formula instead of iterating.

For an inclusive range:

```text
[L, R]
```

the number of elements is:

```text
R - L + 1
```

Here the important threshold is:

```text
1000
```

So:

```text
n < 1000
    ↓
0

n >= 1000
    ↓
n - 1000 + 1
```

---

# Learning Outcome

After solving this problem, you should understand:

- How to identify a useful threshold.
- How to replace unnecessary iteration with a mathematical formula.
- How to count elements in an inclusive range.
- How a simple observation can reduce a problem to `O(1)` time.

---

# Final Takeaway

The key observation is:

```text
Numbers 1 to 999 → 0 commas
Numbers 1000 to n → 1 comma each
```

Therefore:

```text
if n < 1000:
    answer = 0
else:
    answer = n - 1000 + 1
```

**Time Complexity:** `O(1)`

**Space Complexity:** `O(1)`