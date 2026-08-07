# 8. String to Integer (atoi)

> **Difficulty:** Medium  
> **Topics:** String, Simulation

---

## Problem Statement

Implement the function `myAtoi(String s)` that converts a string into a **32-bit signed integer**.

The conversion follows these rules:

1. Ignore leading whitespace.
2. Check for an optional `'+'` or `'-'` sign.
3. Read consecutive digits.
4. Stop reading when a non-digit character is encountered.
5. If no digits are found, return `0`.
6. Clamp the result within the 32-bit signed integer range:
   - Minimum: `-2³¹`
   - Maximum: `2³¹ - 1`

---

## Example 1

### Input

```text
s = "42"
```

### Output

```text
42
```

---

## Example 2

### Input

```text
s = "   -042"
```

### Output

```text
-42
```

---

## Example 3

### Input

```text
s = "1337c0d3"
```

### Output

```text
1337
```

---

## Example 4

### Input

```text
s = "0-1"
```

### Output

```text
0
```

---

## Example 5

### Input

```text
s = "words and 987"
```

### Output

```text
0
```

---

# Approach

The solution processes the string step by step according to the problem statement.

### Steps

1. Skip all leading spaces.
2. Determine the sign (`+` or `-`).
3. Read digits one by one.
4. Build the number using:

```text
number = number × 10 + digit
```

5. Check for integer overflow after every digit.
6. Return the final signed integer.

A `long` variable is used while building the number to safely detect overflow before converting it to an `int`.

---

# Algorithm

1. If the string is empty, return `0`.
2. Skip leading whitespaces.
3. If the next character is `+` or `-`, determine the sign.
4. Read consecutive digits.
5. Update the current number.
6. If overflow occurs:
   - Return `Integer.MAX_VALUE`
   - or `Integer.MIN_VALUE`
7. Return the final signed value.

---

# Dry Run

### Input

```text
s = "   -042"
```

After removing leading spaces

```text
-042
```

Sign

```text
Negative
```

Read digits

```text
0 → 4 → 2
```

Number

```text
42
```

Apply sign

```text
-42
```

Answer

```text
-42
```

---

### Another Dry Run

Input

```text
s = "1337c0d3"
```

Digits read

```text
1337
```

Next character

```text
'c'
```

Stop reading.

Answer

```text
1337
```

---

# Overflow Handling

Suppose

```text
s = "91283472332"
```

While building the number:

```text
91283472332 > Integer.MAX_VALUE
```

Return

```text
2147483647
```

Similarly,

```text
s = "-91283472332"
```

Return

```text
-2147483648
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

where `n` is the length of the string.

---

### Space Complexity

```text
O(1)
```

Only a few variables are used.

---

# Java Solution

```java
class Solution {

    public int myAtoi(String s) {

        if (s.length() == 0) {
            return 0;
        }

        int i = 0;
        long ans = 0;
        int sign = 1;

        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
        }

        if (i == s.length()) {
            return 0;
        }

        if (s.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if (s.charAt(i) == '+') {
            i++;
        }

        while (i < s.length() && Character.isDigit(s.charAt(i))) {

            int digit = s.charAt(i) - '0';

            ans = ans * 10 + digit;

            if (sign == 1 && ans > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            if (sign == -1 && -ans < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }

            i++;
        }

        return (int) (sign * ans);
    }
}
```

---

# Key Concepts

- String Parsing
- Character Processing
- Overflow Detection
- Simulation
- Integer Range Handling

---

# Constraints

- `0 <= s.length <= 200`
- The string contains English letters, digits, spaces, `'+'`, `'-'`, and `'.'`.

---

# Learning Outcome

This problem demonstrates how to manually parse a string while carefully handling whitespace, optional signs, digit extraction, invalid characters, and integer overflow. It is a classic simulation problem that reinforces robust input parsing and edge-case handling.