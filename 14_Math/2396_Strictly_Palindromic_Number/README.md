# 2396. Strictly Palindromic Number

> **Difficulty:** Medium  
> **Topics:** Math, Number Theory

---

## Problem Statement

An integer `n` is **strictly palindromic** if, for **every** base `b` where:

```text
2 ≤ b ≤ n − 2
```

the representation of `n` in base `b` is a palindrome.

Return **`true`** if `n` is strictly palindromic; otherwise, return **`false`**.

---

## Example 1

### Input

```text
n = 9
```

### Output

```text
false
```

### Explanation

Representation of `9` in different bases:

| Base | Representation | Palindrome |
|-----:|---------------|:----------:|
| 2 | 1001 | ✅ |
| 3 | 100 | ❌ |

Since it is **not** palindromic in base **3**, the answer is:

```text
false
```

---

## Example 2

### Input

```text
n = 4
```

### Output

```text
false
```

### Explanation

Only base to check:

```text
Base 2

4 = 100
```

```text
100 ≠ 001
```

Hence,

```text
false
```

---

# Key Observation

A strictly palindromic number **does not exist** for any valid input.

Why?

For every integer:

```text
n ≥ 4
```

consider the base:

```text
b = n − 2
```

The representation of `n` in this base is always:

```text
12
```

because

```text
n = 1 × (n − 2) + 2
```

The string

```text
12
```

is **never** a palindrome.

Therefore, **no integer** can satisfy the definition of a strictly palindromic number.

---

# Mathematical Proof

Choose

```text
b = n − 2
```

Then

```text
n = (n − 2) × 1 + 2
```

So the representation becomes

```text
12
```

Example

For

```text
n = 9
```

Base

```text
7
```

Representation

```text
12
```

which is clearly **not** a palindrome.

Hence every valid integer fails the condition.

---

# Approach

Since every valid integer fails the definition,

simply return

```text
false
```

---

# Algorithm

1. Read `n`.
2. Return `false`.

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
    public boolean isStrictlyPalindromic(int n) {
        return false;
    }
}
```

---

# Key Concepts

- Mathematics
- Number Theory
- Base Conversion
- Mathematical Observation

---

# Constraints

- `4 <= n <= 10⁵`

---

# Learning Outcome

This problem is a great example of replacing simulation with mathematical reasoning. Although the statement suggests checking every base, a simple proof shows that **no integer greater than or equal to 4 can ever be strictly palindromic**. As a result, the optimal solution is an elegant **O(1)** implementation that always returns `false`.