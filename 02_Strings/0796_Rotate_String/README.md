# 796. Rotate String

> **Difficulty:** Easy  
> **Topics:** String, Simulation

---

## Problem Statement

Given two strings `s` and `goal`, return `true` if and only if `s` can become `goal` after performing some number of left shifts.

A shift moves the **leftmost character** of `s` to the **rightmost position**.

For example:

```text
s = "abcde"
```

After one shift:

```text
"bcdea"
```

After two shifts:

```text
"cdeab"
```

---

## Example 1

### Input

```text
s = "abcde"
goal = "cdeab"
```

### Output

```text
true
```

### Explanation

Starting with:

```text
abcde
```

Perform shifts:

```text
abcde
 ↓
bcdea
 ↓
cdeab
```

The result equals `goal`.

---

## Example 2

### Input

```text
s = "abcde"
goal = "abced"
```

### Output

```text
false
```

### Explanation

No sequence of left shifts can transform `"abcde"` into `"abced"`.

---

# Approach

The solution simulates every possible rotation of `s`.

First, check whether both strings are already equal.

Then:

1. Move the first character of `s` to the end.
2. Compare the resulting string with `goal`.
3. Repeat for all possible rotations.
4. If any rotation matches `goal`, return `true`.
5. Otherwise, return `false`.

A `StringBuilder` is used to efficiently modify the string during the simulation.

---

# Algorithm

1. If `s.equals(goal)`, return `true`.
2. Create a `StringBuilder` using `s`.
3. Repeat `s.length()` times:
   - Store the first character.
   - Remove the first character.
   - Append it to the end.
   - Compare the rotated string with `goal`.
4. If a match is found, return `true`.
5. Return `false`.

---

# Dry Run

### Input

```text
s = "abcde"
goal = "cdeab"
```

Initial:

```text
abcde
```

### Rotation 1

```text
bcdea
```

Not equal to `goal`.

### Rotation 2

```text
cdeab
```

Matches `goal`.

Therefore:

```text
true
```

---

# Complexity Analysis

Let `n = s.length()`.

There can be `n` rotations.

For each rotation:

- Removing the first character takes `O(n)` because the remaining characters need to shift.
- Converting the `StringBuilder` to a string takes `O(n)`.
- Comparing strings takes up to `O(n)`.

Therefore, the overall complexity is approximately:

```text
O(n²)
```

---

### Space Complexity

```text
O(n)
```

The `StringBuilder` stores the current rotation.

---

# Java Solution

```java
class Solution {

    public boolean rotateString(String s, String goal) {

        if (s.equals(goal)) {
            return true;
        }

        StringBuilder sb = new StringBuilder(s);

        for (int i = 0; i < s.length(); i++) {

            char ch = sb.charAt(0);

            sb.deleteCharAt(0);

            sb.append(ch);

            if (goal.equals(sb.toString())) {
                return true;
            }
        }

        return false;
    }
}
```

---

# Key Concepts

- String Manipulation
- StringBuilder
- Simulation
- String Rotation

---

# Constraints

- `1 <= s.length, goal.length <= 100`
- `s` and `goal` consist of lowercase English letters.

---

# Learning Outcome

This problem demonstrates how to simulate repeated **string rotations** using `StringBuilder`. The important idea is that there are only `n` unique rotation positions to check, where `n` is the length of the string.

---

# Alternative Optimization

A more concise solution can use the property that a string `goal` is a rotation of `s` if and only if:

```text
s.length() == goal.length()
```

and

```text
goal
```

is a substring of:

```text
s + s
```

For example:

```text
s = "abcde"

s + s = "abcdeabcde"

goal = "cdeab"
```

Since `"cdeab"` appears inside `"abcdeabcde"`, it is a valid rotation.

This approach can be implemented in:

```java
return s.length() == goal.length()
        && (s + s).contains(goal);
```

The simulation solution above is perfectly fine for the given constraints and is useful for understanding how string rotation works.