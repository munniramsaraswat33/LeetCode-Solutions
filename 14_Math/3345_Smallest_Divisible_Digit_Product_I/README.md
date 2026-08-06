# 3345. Smallest Divisible Digit Product I

> **Difficulty:** Easy  
> **Topics:** Math, Brute Force, Digit Manipulation

---

## Problem Statement

You are given two integers `n` and `t`.

Return the **smallest integer greater than or equal to `n`** such that the **product of its digits** is divisible by `t`.

---

## Example 1

### Input

```text
n = 10
t = 2
```

### Output

```text
10
```

### Explanation

Digits of `10`:

```text
1 × 0 = 0
```

Since

```text
0 % 2 = 0
```

the answer is `10`.

---

## Example 2

### Input

```text
n = 15
t = 3
```

### Output

```text
16
```

### Explanation

Digit product of `15`

```text
1 × 5 = 5
```

```text
5 % 3 ≠ 0
```

Digit product of `16`

```text
1 × 6 = 6
```

```text
6 % 3 = 0
```

Hence,

```text
16
```

is the smallest valid number.

---

# Approach

Start checking numbers from `n`.

For each number:

1. Compute the product of its digits.
2. Check whether the product is divisible by `t`.
3. If yes, return the current number.
4. Otherwise, continue with the next number.

Since the constraints are very small (`n ≤ 100`), a brute-force approach is sufficient.

---

# Algorithm

1. Initialize `x = n`.
2. Repeat:
   - Calculate the product of digits of `x`.
   - If

```text
product % t == 0
```

return `x`.

   - Otherwise increment `x`.

---

# Dry Run

### Input

```text
n = 15
t = 3
```

Check

```text
15
```

Digit Product

```text
1 × 5 = 5
```

```text
5 % 3 ≠ 0
```

---

Check

```text
16
```

Digit Product

```text
1 × 6 = 6
```

```text
6 % 3 = 0
```

Return

```text
16
```

---

# Complexity Analysis

Let **d** be the number of digits.

### Time Complexity

For each candidate number, computing the digit product takes:

```text
O(d)
```

Since the search space is very small, the overall complexity is effectively constant for the given constraints.

---

### Space Complexity

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public int smallestNumber(int n, int t) {

        int x = n;

        while (true) {

            int product = 1;
            int temp = x;

            while (temp != 0) {
                product *= temp % 10;
                temp /= 10;
            }

            if (product % t == 0) {
                return x;
            }

            x++;
        }
    }
}
```

---

# Key Concepts

- Brute Force
- Digit Manipulation
- Mathematical Computation
- Modulo Arithmetic

---

# Constraints

- `1 <= n <= 100`
- `1 <= t <= 10`

---

# Learning Outcome

This problem demonstrates how to extract digits from an integer using modulo (`%`) and division (`/`). Because the input constraints are small, a straightforward brute-force search combined with digit manipulation provides a simple and efficient solution.