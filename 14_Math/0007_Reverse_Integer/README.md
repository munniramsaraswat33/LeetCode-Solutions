# 7. Reverse Integer

> **Difficulty:** Medium  
> **Topics:** Math, Integer Manipulation

---

## Problem Statement

Given a signed 32-bit integer `x`, return the integer with its digits reversed.

If reversing the digits causes the result to go outside the signed 32-bit integer range:

```text
[-2³¹, 2³¹ - 1]
```

return:

```text
0
```

The solution must handle overflow without using a 64-bit integer.

---

## Example 1

### Input

```text
x = 123
```

### Output

```text
321
```

### Explanation

```text
123 → 321
```

---

## Example 2

### Input

```text
x = -123
```

### Output

```text
-321
```

### Explanation

The negative sign is preserved while the digits are reversed.

```text
-123 → -321
```

---

## Example 3

### Input

```text
x = 120
```

### Output

```text
21
```

### Explanation

The trailing zero disappears after reversal:

```text
120 → 021 → 21
```

---

# Approach

The number can be reversed digit by digit using:

```text
digit = x % 10
```

Then remove the last digit:

```text
x = x / 10
```

Build the reversed number using:

```text
reverse = reverse × 10 + digit
```

The important part of this problem is **integer overflow**.

Since the environment does not allow storing a 64-bit integer, overflow must be detected **before** performing:

```text
reverse × 10 + digit
```

---

# Overflow Detection

The maximum 32-bit signed integer is:

```text
2147483647
```

The minimum is:

```text
-2147483648
```

Before adding a digit, check whether multiplying `reverse` by `10` would exceed the integer range.

For positive overflow:

```text
reverse > Integer.MAX_VALUE / 10
```

or

```text
reverse == Integer.MAX_VALUE / 10
&& digit > 7
```

For negative overflow:

```text
reverse < Integer.MIN_VALUE / 10
```

or

```text
reverse == Integer.MIN_VALUE / 10
&& digit < -8
```

If either condition occurs, return `0`.

---

# Algorithm

1. Initialize:

```text
reverse = 0
```

2. Remove unnecessary trailing zeros from the input.
3. While `x != 0`:
   - Extract the last digit.
   - Check for overflow.
   - Add the digit to `reverse`.
   - Remove the last digit from `x`.
4. Return `reverse`.

---

# Dry Run

### Input

```text
x = 123
```

### Step 1

```text
digit = 3
reverse = 0 × 10 + 3
        = 3
```

Remaining:

```text
x = 12
```

---

### Step 2

```text
digit = 2
reverse = 3 × 10 + 2
        = 32
```

Remaining:

```text
x = 1
```

---

### Step 3

```text
digit = 1
reverse = 32 × 10 + 1
        = 321
```

Remaining:

```text
x = 0
```

Answer:

```text
321
```

---

# Negative Number Example

Input:

```text
x = -123
```

Digits are extracted as:

```text
-3
-2
-1
```

Therefore:

```text
reverse = -321
```

Result:

```text
-321
```

---

# Complexity Analysis

### Time Complexity

```text
O(log₁₀ |x|)
```

The algorithm processes each digit exactly once.

For a 32-bit integer, this is effectively:

```text
O(1)
```

---

### Space Complexity

```text
O(1)
```

Only a few integer variables are used.

---

# Java Solution

```java
class Solution {

    public int reverse(int x) {

        int reverse = 0;

        if (x == 0) {
            return 0;
        }

        while (x % 10 == 0) {
            x /= 10;
        }

        while (x != 0) {

            int digit = x % 10;

            // Positive overflow
            if (reverse > Integer.MAX_VALUE / 10 ||
                (reverse == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }

            // Negative overflow
            if (reverse < Integer.MIN_VALUE / 10 ||
                (reverse == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }

            reverse = reverse * 10 + digit;

            x /= 10;
        }

        return reverse;
    }
}
```

---

# Key Concepts

- Integer Manipulation
- Digit Extraction
- Modulo Operator
- Integer Overflow
- 32-bit Integer Range

---

# Constraints

```text
-2³¹ <= x <= 2³¹ - 1
```

---

# Important Edge Cases

| Input | Output | Reason |
|------:|-------:|--------|
| `123` | `321` | Normal reversal |
| `-123` | `-321` | Negative number |
| `120` | `21` | Leading zero disappears |
| `0` | `0` | Zero |
| `1534236469` | `0` | Positive overflow |
| `-2147483648` | `0` | Reversal overflows |

---

# Learning Outcome

This problem is an important example of **safe integer manipulation**.

The main challenge is not reversing the digits, but detecting **32-bit integer overflow before it happens**. By checking the current value against `Integer.MAX_VALUE / 10` and `Integer.MIN_VALUE / 10`, the solution avoids using a 64-bit integer and safely handles all overflow cases.