# 459. Repeated Substring Pattern

> **Difficulty:** Easy  
> **Topics:** String, String Matching

---

## Problem Statement

Given a string `s`, determine whether it can be constructed by taking a substring of `s` and repeating it multiple times.

Return `true` if such a substring exists; otherwise, return `false`.

---

## Example 1

### Input

```text
s = "abab"
```

### Output

```text
true
```

### Explanation

The string can be constructed by repeating:

```text
"ab"
```

twice:

```text
"ab" + "ab" = "abab"
```

---

## Example 2

### Input

```text
s = "aba"
```

### Output

```text
false
```

### Explanation

There is no substring that can be repeated to form `"aba"`.

---

## Example 3

### Input

```text
s = "abcabcabcabc"
```

### Output

```text
true
```

### Explanation

The string can be constructed by repeating:

```text
"abc"
```

four times:

```text
"abc" + "abc" + "abc" + "abc"
```

It can also be constructed by repeating:

```text
"abcabc"
```

twice.

---

# Approach

Use a useful string property:

If a string is made by repeating a substring, then the string will appear inside:

```text
s + s
```

after removing the first and last characters.

For example:

```text
s = "abab"

s + s = "abababab"
```

Remove the first and last characters:

```text
"bababa"
```

Now:

```text
"bababa".contains("abab")
```

is `true`.

Therefore, `"abab"` is a repeated substring pattern.

---

# Why Remove the First and Last Character?

If we simply check:

```java
(s + s).contains(s)
```

every string would always match itself.

For example:

```text
s = "aba"

s + s = "abaaba"
```

The original `"aba"` appears at the beginning even though `"aba"` is **not** a repeated substring pattern.

By removing the first and last characters:

```text
abaaba
 ↓
baab
```

`"aba"` is no longer found.

Therefore, we use:

```java
(s + s).substring(1, 2 * s.length() - 1)
```

---

# Algorithm

1. If the string length is less than `2`, return `false`.
2. Create:

```text
doubled = s + s
```

3. Remove the first and last characters.
4. Check whether the remaining string contains the original `s`.
5. Return the result.

---

# Dry Run

### Input

```text
s = "abab"
```

Create:

```text
doubled = "abababab"
```

Remove first and last characters:

```text
"bababa"
```

Check:

```text
"bababa".contains("abab")
```

Result:

```text
true
```

Therefore:

```text
"abab" = "ab" + "ab"
```

---

## Another Example

### Input

```text
s = "aba"
```

Create:

```text
doubled = "abaaba"
```

Remove first and last characters:

```text
"baab"
```

Check:

```text
"baab".contains("aba")
```

Result:

```text
false
```

Therefore, `"aba"` cannot be constructed by repeating a substring.

---

# Complexity Analysis

Let `n` be the length of `s`.

### Time Complexity

Creating `s + s` takes:

```text
O(n)
```

The substring operation takes:

```text
O(n)
```

The `contains()` operation performs string searching.

Overall, with Java's string-search implementation, the practical complexity is approximately:

```text
O(n²)
```

in the worst case.

---

### Space Complexity

The doubled string requires:

```text
O(n)
```

additional space.

---

# Java Solution

```java
class Solution {

    public boolean repeatedSubstringPattern(String s) {

        if (s.length() < 2) {
            return false;
        }

        String doubled = s + s;

        return doubled
                .substring(1, doubled.length() - 1)
                .contains(s);
    }
}
```

---

# Key Concepts

- String Manipulation
- String Matching
- Concatenation
- Substring Search

---

# Constraints

- `1 <= s.length <= 10⁴`
- `s` consists of lowercase English letters.

---

# Learning Outcome

This problem demonstrates an elegant string property:

> If a string is formed by repeating a smaller substring, it will appear inside `(s + s)` after removing the first and last characters.

This technique avoids explicitly trying every possible substring length and provides a concise solution.