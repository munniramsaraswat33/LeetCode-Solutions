# 520. Detect Capital

> **Difficulty:** Easy  
> **Topics:** String

---

## Problem Statement

We define the usage of capitals in a word to be correct if one of the following conditions is satisfied:

1. All letters are uppercase.
2. All letters are lowercase.
3. Only the first letter is uppercase.

Given a string `word`, return **`true`** if the capitalization is correct; otherwise, return **`false`**.

---

## Example 1

### Input

```text
word = "USA"
```

### Output

```text
true
```

### Explanation

All letters are uppercase.

---

## Example 2

### Input

```text
word = "FlaG"
```

### Output

```text
false
```

### Explanation

The capitalization does not follow any valid pattern.

---

## Valid Cases

| Word | Valid |
|------|:-----:|
| USA | ✅ |
| leetcode | ✅ |
| Google | ✅ |
| FlaG | ❌ |
| JAVAa | ❌ |

---

# Approach

There are three valid capitalization patterns:

- Entire word is uppercase.
- Entire word is lowercase.
- Only the first character is uppercase and the remaining characters are lowercase.

The algorithm first checks whether the whole word is uppercase or lowercase using Java's built-in string methods.

If neither condition is satisfied, it checks whether:

- the first character is uppercase, and
- every remaining character is lowercase.

If all these conditions fail, the word has incorrect capitalization.

---

# Algorithm

1. Check whether the entire word is uppercase.
2. Check whether the entire word is lowercase.
3. If either condition is true, return `true`.
4. Otherwise:
   - Verify the first character is uppercase.
   - Check that every remaining character is lowercase.
5. Return the result.

---

# Dry Run

### Input

```text
word = "Google"
```

- Entire uppercase? ❌
- Entire lowercase? ❌
- First letter uppercase? ✅
- Remaining letters lowercase? ✅

Result

```text
true
```

---

### Input

```text
word = "FlaG"
```

- Entire uppercase? ❌
- Entire lowercase? ❌
- First letter uppercase? ✅
- Remaining letters lowercase?

```text
l ✔
a ✔
G ✘
```

Result

```text
false
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
    public boolean detectCapitalUse(String word) {

        boolean upper = word.equals(word.toUpperCase());
        boolean lower = word.equals(word.toLowerCase());

        if (upper || lower) {
            return true;
        }

        if (Character.isUpperCase(word.charAt(0))) {

            for (int i = 1; i < word.length(); i++) {

                if (Character.isUpperCase(word.charAt(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
```

---

# Key Concepts

- String Manipulation
- Character Checking
- Uppercase & Lowercase Validation

---

# Constraints

- `1 <= word.length <= 100`
- The word contains only English uppercase and lowercase letters.

---

# Learning Outcome

This problem demonstrates how to validate string patterns using Java's built-in string methods and character utilities. It highlights the importance of checking different valid cases systematically while maintaining an efficient **O(n)** solution.