# 482. License Key Formatting

> **Difficulty:** Easy  
> **Topics:** String, Simulation

---

## Problem Statement

You are given a license key represented as a string `s` consisting of:

- English letters
- Digits
- Dashes (`-`)

You are also given an integer `k`.

Reformat the license key such that:

- Each group contains exactly `k` characters except the first group, which may contain fewer characters.
- Groups are separated by a single dash (`-`).
- All lowercase letters are converted to uppercase.

Return the reformatted license key.

---

## Example 1

### Input

```text
s = "5F3Z-2e-9-w"
k = 4
```

### Output

```text
5F3Z-2E9W
```

### Explanation

After removing unnecessary dashes:

```text
5F3Z2E9W
```

Group into 4 characters:

```text
5F3Z-2E9W
```

---

## Example 2

### Input

```text
s = "2-5g-3-J"
k = 2
```

### Output

```text
2-5G-3J
```

### Explanation

After removing dashes:

```text
25G3J
```

Grouping from the end gives:

```text
2-5G-3J
```

---

# Approach

Instead of processing the string from left to right, iterate **from right to left**.

This makes grouping much easier because every group after the first must contain exactly `k` characters.

For every character:

- Ignore dashes.
- Convert letters to uppercase.
- Add a dash whenever the current group reaches size `k`.
- Finally, reverse the constructed string.

---

# Algorithm

1. Create an empty `StringBuilder`.
2. Traverse the string from right to left.
3. Skip every dash.
4. Convert lowercase letters to uppercase.
5. If the current group already contains `k` characters:
   - Append a dash.
   - Reset the counter.
6. Append the current character.
7. Reverse the final string.
8. Return the result.

---

# Dry Run

### Input

```text
s = "2-5g-3-J"
k = 2
```

Traverse from right to left:

| Character | Result |
|-----------|--------|
| J | J |
| 3 | J3 |
| g | J3-5G |
| 5 | J3-5G |
| 2 | J3-5G-2 |

Reverse

```text
2-5G-3J
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

The string is traversed once, and reversing the `StringBuilder` also takes linear time.

---

### Space Complexity

```text
O(n)
```

A new string is created to store the reformatted license key.

---

# Java Solution

```java
class Solution {

    public String licenseKeyFormatting(String s, int k) {

        StringBuilder sb = new StringBuilder();

        int count = 0;

        for (int i = s.length() - 1; i >= 0; i--) {

            char ch = s.charAt(i);

            if (ch == '-') {
                continue;
            }

            if (count == k) {
                sb.append('-');
                count = 0;
            }

            sb.append(Character.toUpperCase(ch));
            count++;
        }

        return sb.reverse().toString();
    }
}
```

---

# Key Concepts

- String Manipulation
- StringBuilder
- Reverse Traversal
- Character Conversion

---

# Constraints

- `1 <= s.length <= 10⁵`
- `1 <= k <= 10⁴`
- `s` contains English letters, digits, and dashes.

---

# Learning Outcome

This problem demonstrates how traversing a string **from right to left** can simplify fixed-size grouping. By ignoring existing dashes, converting letters to uppercase, and inserting new separators after every `k` characters, we obtain a clean **O(n)** solution without complex index calculations.