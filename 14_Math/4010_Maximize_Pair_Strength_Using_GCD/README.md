# 4010. Maximize Pair Strength Using GCD

> **Difficulty:** Easy  
> **Topics:** Math, Number Theory, GCD, Brute Force

---

## Problem Statement

You are given an integer array `nums`.

Choose exactly one pair of distinct indices `i` and `j`.

The **strength** of the pair is defined as:

\[
\frac{nums[i] \times nums[j]}{gcd(nums[i], nums[j])^2}
\]

Return the **maximum strength** among all possible pairs.

---

## Examples

### Example 1

**Input**

```text
nums = [2,3,5]
```

**Output**

```text
15
```

**Explanation**

- gcd(3,5) = 1
- Strength = (3 × 5) / (1²) = 15

---

### Example 2

**Input**

```text
nums = [4,6,8]
```

**Output**

```text
12
```

**Explanation**

- gcd(6,8) = 2
- Strength = (6 × 8) / (2²)
- = 48 / 4
- = 12

---

### Example 3

**Input**

```text
nums = [3,3]
```

**Output**

```text
1
```

**Explanation**

- gcd(3,3) = 3
- Strength = (3 × 3) / (3²)
- = 9 / 9
- = 1

---

## Approach

Since the maximum size of the array is only **2000**, checking every possible pair is efficient enough.

For every pair:

1. Compute the **Greatest Common Divisor (GCD)**.
2. Calculate the strength using the given formula.
3. Update the maximum strength found.

The Euclidean Algorithm is used to compute the GCD efficiently.

---

## Algorithm

1. Initialize `ans = 0`.
2. Iterate through every pair `(i, j)` where `i < j`.
3. Compute:
   - `g = gcd(nums[i], nums[j])`
   - `strength = (nums[i] × nums[j]) / (g × g)`
4. Update the maximum value.
5. Return `ans`.

---

## Dry Run

Input

```text
nums = [4,6,8]
```

| Pair | GCD | Strength |
|------|----:|---------:|
| (4,6) | 2 | 6 |
| (4,8) | 4 | 2 |
| (6,8) | 2 | 12 |

**Maximum Strength = 12**

---

## Complexity Analysis

### Time Complexity

- Checking all pairs: **O(n²)**
- GCD computation: **O(log(max(nums)))**

Overall:

```text
O(n² × log(max(nums)))
```

### Space Complexity

```text
O(1)
```

---

## Java Solution

```java
class Solution {
    public long maxPairStrength(int[] nums) {
        long ans = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                long g = gcd(nums[i], nums[j]);
                long strength = (1L * nums[i] * nums[j]) / (g * g);
                ans = Math.max(ans, strength);
            }
        }

        return ans;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
```

---

## Key Concepts

- Euclidean Algorithm
- Greatest Common Divisor (GCD)
- Brute Force
- Number Theory

---

## Constraints

- `2 <= nums.length <= 2000`
- `1 <= nums[i] <= 10⁵`

---

## Learning Outcome

This problem demonstrates how mathematical properties such as the **Greatest Common Divisor (GCD)** can simplify computations. It also reinforces the use of the **Euclidean Algorithm** and shows when a brute-force solution is acceptable based on the given constraints.