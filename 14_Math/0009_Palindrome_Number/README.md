# 9. Palindrome Number

> **Difficulty:** Easy  
> **Topics:** Math, Number Manipulation

---

## Problem Statement

Given an integer `x`, return `true` if `x` is a **palindrome**, otherwise return `false`.

A palindrome number reads the same from left to right and right to left.

---

## Example 1

### Input

```text
x = 121
```

### Output

```text
true
```

### Explanation

```text
121 → 121
```

The number remains the same when reversed, so it is a palindrome.

---

## Example 2

### Input

```text
x = -121
```

### Output

```text
false
```

### Explanation

A negative number cannot be a palindrome because the negative sign would appear at the opposite end after reversal:

```text
-121 → 121-
```

Therefore, the number is not a palindrome.

---

## Example 3

### Input

```text
x = 10
```

### Output

```text
false
```

### Explanation

Reversing the digits gives:

```text
10 → 01
```

which is different from the original number.

Therefore:

```text
false
```

---

# Approach

The solution checks whether the integer is equal to its reversed form.

### Steps

1. If `x` is negative, return `false`.
2. Store the original number.
3. Reverse the digits of `x`.
4. Compare the reversed number with the original number.
5. If they are equal, return `true`; otherwise return `false`.

This approach does **not convert the integer into a string**.

---

# Algorithm

1. Check if:

```text
x < 0
```

If yes, return `false`.

2. Store:

```text
original = x
```

3. Initialize:

```text
reverse = 0
```

4. Extract each digit using:

```text
digit = x % 10
```

5. Build the reversed number:

```text
reverse = reverse × 10 + digit
```

6. Remove the last digit:

```text
x = x / 10
```

7. Compare:

```text
original == reverse
```

8. Return the result.

---

# Dry Run

### Input

```text
x = 121
```

Initially:

```text
original = 121
reverse = 0
```

### Step 1

```text
digit = 121 % 10 = 1

reverse = 0 × 10 + 1
        = 1

x = 12
```

### Step 2

```text
digit = 12 % 10 = 2

reverse = 1 × 10 + 2
        = 12

x = 1
```

### Step 3

```text
digit = 1 % 10 = 1

reverse = 12 × 10 + 1
        = 121

x = 0
```

Now:

```text
original = 121
reverse  = 121
```

Therefore:

```text
true
```

---

# Negative Number

For:

```text
x = -121
```

The solution immediately returns:

```text
false
```

because a negative number cannot be a palindrome.

---

# Complexity Analysis

Let `d` be the number of digits in `x`.

### Time Complexity

```text
O(d)
```

Each digit is processed exactly once.

Since a 32-bit integer contains at most 10 digits, this is effectively:

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

    public boolean isPalindrome(int x) {

        if (x < 0) {
            return false;
        }

        int original = x;
        int reverse = 0;

        while (x != 0) {

            int digit = x % 10;

            reverse = reverse * 10 + digit;

            x /= 10;
        }

        return original == reverse;
    }
}
```

---

# Key Concepts

- Integer Manipulation
- Digit Extraction
- Modulo Operator
- Number Reversal
- Palindrome Checking

---

# Important Edge Cases

| Input | Output | Reason |
|------:|:------:|--------|
| `121` | `true` | Same forward and backward |
| `-121` | `false` | Negative number |
| `10` | `false` | Reversed number is different |
| `0` | `true` | Zero is a palindrome |
| `1221` | `true` | Same forward and backward |
| `123` | `false` | Not the same after reversal |

---

# Follow-Up

### Can this be solved without converting the integer to a string?

Yes.

This solution already solves the problem without using string conversion.

It uses mathematical operations:

```text
% 10
```

to extract digits and:

```text
/ 10
```

to remove digits.

---

# Learning Outcome

This problem demonstrates how to work with the individual digits of an integer without converting it into a string.

The main technique is:

```text
digit = x % 10
x = x / 10
```

By repeatedly extracting digits and constructing the reversed number, we can efficiently determine whether the original integer is a palindrome.