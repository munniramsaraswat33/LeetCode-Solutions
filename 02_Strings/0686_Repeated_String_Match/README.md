# 686. Repeated String Match

> **Difficulty:** Medium  
> **Topics:** String, String Matching

---

## Problem Statement

Given two strings `a` and `b`, return the **minimum number of times** string `a` needs to be repeated so that `b` becomes a substring of the resulting string.

If it is impossible, return:

```text
-1
```

For example:

```text
"abc" repeated 1 time → "abc"
"abc" repeated 2 times → "abcabc"
"abc" repeated 3 times → "abcabcabc"
```

---

## Example 1

### Input

```text
a = "abcd"
b = "cdabcdab"
```

### Output

```text
3
```

### Explanation

Repeat `a` three times:

```text
"abcdabcdabcd"
```

The string `b`:

```text
"cdabcdab"
```

is a substring of it.

Therefore:

```text
3
```

---

## Example 2

### Input

```text
a = "a"
b = "aa"
```

### Output

```text
2
```

### Explanation

Repeat `"a"` twice:

```text
"aa"
```

which contains `b`.

---

# Approach

Start with one copy of `a` and keep appending `a` until the constructed string has at least the same length as `b`.

At that point, check whether `b` is a substring.

However, `b` may start near the end of the current repeated string and continue into the **next repetition** of `a`.

Therefore, if `b` is not found, append **one additional copy** of `a` and check again.

If it is still not found, return `-1`.

---

# Why One Extra Repetition Is Enough

Suppose the repeated string has reached a length greater than or equal to `b.length()`.

If `b` exists across a boundary between two copies of `a`, it can extend into at most one additional copy.

Therefore, after reaching the required length, we only need to check:

```text
current repetitions
```

and

```text
current repetitions + 1
```

If `b` is not found in either case, it cannot be formed by repeating `a`.

---

# Algorithm

1. Initialize a `StringBuilder` with one copy of `a`.
2. Set:

```text
count = 1
```

3. While the constructed string is shorter than `b`:
   - Append `a`.
   - Increment `count`.
4. Check whether `b` is a substring.
5. If yes, return `count`.
6. Append one more copy of `a`.
7. Increment `count`.
8. Check again.
9. If `b` is still not found, return `-1`.

---

# Dry Run

### Input

```text
a = "abcd"
b = "cdabcdab"
```

### Step 1

```text
sb = "abcd"
count = 1
```

Length is smaller than `b`.

---

### Step 2

Append `a`:

```text
sb = "abcdabcd"
count = 2
```

Still shorter than `b`.

---

### Step 3

Append `a`:

```text
sb = "abcdabcdabcd"
count = 3
```

Now:

```text
"cdabcdab"
```

is found inside:

```text
"abcdabcdabcd"
```

Therefore:

```text
answer = 3
```

---

# Another Example

### Input

```text
a = "a"
b = "aa"
```

Initial:

```text
"a"
```

Length is smaller than `b`.

Append:

```text
"aa"
```

Now:

```text
count = 2
```

`"aa"` contains `b`.

Answer:

```text
2
```

---

# Complexity Analysis

Let:

```text
n = a.length()
m = b.length()
```

The string is repeated until its length is at least `m`, with at most one additional repetition.

The constructed string has length:

```text
O(m + n)
```

The `indexOf()` substring search can take up to:

```text
O((m + n) × m)
```

in the worst case depending on the underlying string-search implementation.

For the given constraints, this approach is efficient enough.

---

### Space Complexity

The `StringBuilder` stores the repeated string:

```text
O(m + n)
```

---

# Java Solution

```java
class Solution {

    public int repeatedStringMatch(String a, String b) {

        StringBuilder sb = new StringBuilder(a);

        int count = 1;

        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        if (sb.indexOf(b) >= 0) {
            return count;
        }

        sb.append(a);
        count++;

        if (sb.indexOf(b) >= 0) {
            return count;
        }

        return -1;
    }
}
```

---

# Key Concepts

- String Manipulation
- StringBuilder
- Substring Search
- Repeated Strings
- Boundary Handling

---

# Constraints

- `1 <= a.length, b.length <= 10⁴`
- `a` and `b` consist of lowercase English letters.

---

# Learning Outcome

This problem demonstrates an important technique for **repeated string matching**. Instead of repeatedly checking every possible number of repetitions, we first build the string until it reaches the required length and then check only one additional repetition. This works because any valid occurrence of `b` can cross at most one repetition boundary beyond the initial required length.