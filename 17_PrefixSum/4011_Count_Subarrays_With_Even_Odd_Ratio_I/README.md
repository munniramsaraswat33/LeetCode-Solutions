# 4011. Count Subarrays With Even Odd Ratio I

> **Difficulty:** Medium  
> **Topics:** Array, Prefix Sum, Brute Force, Math

---

## Problem Statement

You are given an integer array `nums` and two integers `a` and `b`.

For every subarray:

- Let **x** be the number of **even** elements.
- Let **y** be the number of **odd** elements.

A subarray is considered **valid** if:

1. `y > 0`
2. `x / y <= a / b`

Return the total number of valid subarrays.

---

## Examples

### Example 1

**Input**

```text
nums = [1,2,1,2]
a = 3
b = 2
```

**Output**

```text
7
```

**Explanation**

The valid subarrays are:

| Subarray | Even | Odd | Ratio |
|----------|-----:|----:|-------|
| [1] | 0 | 1 | 0/1 |
| [1,2] | 1 | 1 | 1/1 |
| [1,2,1] | 1 | 2 | 1/2 |
| [1,2,1,2] | 2 | 2 | 2/2 |
| [2,1] | 1 | 1 | 1/1 |
| [1] | 0 | 1 | 0/1 |
| [1,2] | 1 | 1 | 1/1 |

Total valid subarrays = **7**

---

### Example 2

**Input**

```text
nums = [2,2,1]
a = 2
b = 1
```

**Output**

```text
3
```

---

### Example 3

**Input**

```text
nums = [2,2,2]
a = 1
b = 1
```

**Output**

```text
0
```

**Explanation**

Every subarray contains **0 odd numbers**, so none satisfy the condition `y > 0`.

---

## Approach

Since `nums.length ≤ 1000`, a **brute-force** approach is efficient enough.

For every possible starting index:

1. Extend the subarray one element at a time.
2. Maintain:
   - Number of even elements.
   - Number of odd elements.
3. Ignore subarrays having zero odd elements.
4. Instead of comparing fractions directly, use **cross multiplication** to avoid floating-point errors:

```text
even / odd ≤ a / b

⇔ even × b ≤ odd × a
```

If the condition holds, increment the answer.

---

## Algorithm

1. Initialize `answer = 0`.
2. Iterate through every starting index `i`.
3. Reset `even = 0` and `odd = 0`.
4. Extend the subarray to every ending index `j`.
5. Update the even/odd count.
6. If:
   - `odd > 0`
   - `even × b ≤ odd × a`
   
   increment the answer.
7. Return the final count.

---

## Dry Run

Input

```text
nums = [1,2,1]
a = 3
b = 2
```

| Subarray | Even | Odd | Valid |
|----------|-----:|----:|:-----:|
| [1] | 0 | 1 | ✅ |
| [1,2] | 1 | 1 | ✅ |
| [1,2,1] | 1 | 2 | ✅ |
| [2] | 1 | 0 | ❌ |
| [2,1] | 1 | 1 | ✅ |
| [1] | 0 | 1 | ✅ |

Answer = **5**

---

## Complexity Analysis

### Time Complexity

There are **O(n²)** possible subarrays.

For each subarray, only constant-time operations are performed.

```text
O(n²)
```

---

### Space Complexity

Only a few variables are used.

```text
O(1)
```

---

## Java Solution

```java
class Solution {
    public int countRatioSubarrays(int[] nums, int a, int b) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            int even = 0;
            int odd = 0;

            for (int j = i; j < n; j++) {

                if (nums[j] % 2 == 0)
                    even++;
                else
                    odd++;

                if (odd > 0 && 1L * even * b <= 1L * odd * a)
                    ans++;
            }
        }

        return ans;
    }
}
```

---

## Key Concepts

- Brute Force
- Subarrays
- Counting Even and Odd Elements
- Cross Multiplication
- Integer Arithmetic

---

## Constraints

- `1 ≤ nums.length ≤ 1000`
- `1 ≤ nums[i] ≤ 1000`
- `1 ≤ a, b ≤ 1000`

---

## Learning Outcome

This problem demonstrates how **cross multiplication** can be used to compare ratios without floating-point arithmetic. It also shows that a brute-force solution can be acceptable when the input constraints are small, while maintaining running counts of even and odd elements efficiently.