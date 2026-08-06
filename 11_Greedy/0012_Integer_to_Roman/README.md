# 12. Integer to Roman

> **Difficulty:** Medium  
> **Topics:** Greedy, String, Math

---

## Problem Statement

Roman numerals are represented by seven symbols:

| Symbol | Value |
|:------:|------:|
| I | 1 |
| V | 5 |
| X | 10 |
| L | 50 |
| C | 100 |
| D | 500 |
| M | 1000 |

Given an integer `num`, convert it into its corresponding **Roman numeral**.

Roman numerals follow these special subtractive rules:

| Number | Roman |
|--------:|:-----:|
| 4 | IV |
| 9 | IX |
| 40 | XL |
| 90 | XC |
| 400 | CD |
| 900 | CM |

---

## Example 1

### Input

```text
num = 3749
```

### Output

```text
MMMDCCXLIX
```

### Explanation

```text
3000 → MMM
700  → DCC
40   → XL
9    → IX

Answer = MMMDCCXLIX
```

---

## Example 2

### Input

```text
num = 58
```

### Output

```text
LVIII
```

### Explanation

```text
50 → L
5  → V
3  → III

Answer = LVIII
```

---

## Example 3

### Input

```text
num = 1994
```

### Output

```text
MCMXCIV
```

### Explanation

```text
1000 → M
900  → CM
90   → XC
4    → IV

Answer = MCMXCIV
```

---

# Approach

Use a **Greedy Algorithm**.

Store all Roman numeral values in descending order along with their symbols.

For each value:

- While the current number is greater than or equal to that value:
  - Append the corresponding Roman symbol.
  - Subtract the value from the number.

Repeat until the number becomes zero.

Since subtractive forms (IV, IX, XL, XC, CD, CM) are already included in the arrays, they are handled automatically.

---

# Algorithm

1. Create two arrays:
   - Integer values (largest to smallest).
   - Corresponding Roman symbols.
2. Traverse the arrays.
3. While the current value is less than or equal to the number:
   - Append the Roman symbol.
   - Subtract the value.
4. Return the generated string.

---

# Dry Run

### Input

```text
num = 58
```

| Value | Roman | Remaining Number |
|------:|:-----:|-----------------:|
| 50 | L | 8 |
| 5 | V | 3 |
| 1 | I | 2 |
| 1 | I | 1 |
| 1 | I | 0 |

Answer

```text
LVIII
```

---

## Another Dry Run

### Input

```text
num = 1994
```

| Value | Roman | Remaining |
|------:|:-----:|----------:|
|1000|M|994|
|900|CM|94|
|90|XC|4|
|4|IV|0|

Answer

```text
MCMXCIV
```

---

# Why Greedy Works

At every step we always choose the **largest Roman value** that does not exceed the remaining number.

Since Roman numerals have fixed symbols and predefined subtractive combinations, this choice is always optimal and produces the correct representation.

---

# Complexity Analysis

### Time Complexity

There are only **13 Roman values** to check.

Overall complexity:

```text
O(1)
```

(The maximum input is 3999, so the number of iterations is bounded.)

---

### Space Complexity

```text
O(1)
```

Only fixed-size arrays and a `StringBuilder` are used.

---

# Java Solution

```java
class Solution {

    public String intToRoman(int num) {

        int[] values = {
                1000,900,500,400,
                100,90,50,40,
                10,9,5,4,1
        };

        String[] roman = {
                "M","CM","D","CD",
                "C","XC","L","XL",
                "X","IX","V","IV","I"
        };

        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < values.length; i++) {

            while (num >= values[i]) {
                ans.append(roman[i]);
                num -= values[i];
            }
        }

        return ans.toString();
    }
}
```

---

# Key Concepts

- Greedy Algorithm
- StringBuilder
- Roman Numeral Conversion
- Array Traversal

---

# Constraints

- `1 <= num <= 3999`

---

# Learning Outcome

This problem demonstrates how a **Greedy Algorithm** can be applied when the system has predefined optimal choices. By always selecting the largest possible Roman numeral value first, we efficiently construct the correct Roman numeral representation without backtracking or recursion.