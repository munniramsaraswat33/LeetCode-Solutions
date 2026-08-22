# 392. Is Subsequence

> **Difficulty:** Easy  
> **Topics:** Two Pointers, String

---

## Problem Statement

Given two strings `s` and `t`, return `true` if `s` is a **subsequence** of `t`, otherwise return `false`.

A **subsequence** is a new string formed by deleting some characters from the original string without changing the relative order of the remaining characters.

For example:

```text
"ace" is a subsequence of "abcde"
```

because we can remove `b` and `d`.

But:

```text
"aec" is not a subsequence of "abcde"
```

because the relative order of `e` and `c` is different.

---

## Example 1

### Input

```text
s = "abc"
t = "ahbgdc"
```

### Output

```text
true
```

### Explanation

We can find:

```text
a → b → c
```

in `t` while maintaining the same order.

Therefore, `"abc"` is a subsequence of `"ahbgdc"`.

---

## Example 2

### Input

```text
s = "axc"
t = "ahbgdc"
```

### Output

```text
false
```

### Explanation

We can find `a` and `c`, but there is no `x` after `a`.

Therefore, `"axc"` is not a subsequence of `"ahbgdc"`.

---

# Approach

Use the **Two Pointer** technique.

We need to check whether all characters of `s` appear in `t` in the same order.

Maintain:

```text
j → pointer for string s
i → pointer for string t
```

We traverse `t` from left to right.

Whenever:

```text
s[j] == t[i]
```

we found the next required character of `s`, so we move:

```text
j++
```

We continue until either:

- All characters of `s` are matched.
- We reach the end of `t`.

If:

```text
j == s.length()
```

then every character of `s` was found in the correct order.

---

# Algorithm

1. If `s` is empty, return `true`.
2. Initialize:
   ```text
   j = 0
   ```
3. Traverse `t` using `i`.
4. If:
   ```text
   s[j] == t[i]
   ```
   increment `j`.
5. Continue while:
   ```text
   i < t.length()
   ```
   and
   ```text
   j < s.length()
   ```
6. At the end:
   - If `j == s.length()`, return `true`.
   - Otherwise, return `false`.

---

# Dry Run

Input:

```text
s = "abc"
t = "ahbgdc"
```

Initially:

```text
j = 0
```

### Step 1

`t[0] = 'a'`

Compare:

```text
s[0] = 'a'
t[0] = 'a'
```

They match.

Move:

```text
j = 1
```

---

### Step 2

`t[1] = 'h'`

Compare:

```text
s[1] = 'b'
t[1] = 'h'
```

Not equal.

Continue.

---

### Step 3

`t[2] = 'b'`

Compare:

```text
s[1] = 'b'
t[2] = 'b'
```

They match.

Move:

```text
j = 2
```

---

### Step 4

`t[3] = 'g'`

```text
s[2] = 'c'
t[3] = 'g'
```

Not equal.

Continue.

---

### Step 5

`t[4] = 'd'`

Not equal.

Continue.

---

### Step 6

`t[5] = 'c'`

```text
s[2] = 'c'
t[5] = 'c'
```

Match.

Move:

```text
j = 3
```

Now:

```text
j == s.length()
```

Therefore:

```text
true
```

---

# Understanding the Code

## Empty String Case

```java
if(s.length() == 0){
    return true;
}
```

An empty string is a subsequence of every string because we can delete all characters from `t`.

---

## Initialize Pointer

```java
int j = 0;
```

`j` points to the current character we need to find in `s`.

---

## Traverse String `t`

```java
for(int i=0; i<t.length() && j<s.length(); i++){
```

We scan `t` from left to right.

The second condition:

```java
j < s.length()
```

allows us to stop immediately when all characters of `s` have been matched.

---

## Compare Characters

```java
if(s.charAt(j) == t.charAt(i)){
    j++;
}
```

If the current characters match, we have successfully found the next required character of `s`.

So we move `j` to the next character.

If they don't match, we simply continue moving through `t`.

---

## Final Check

```java
return j == s.length();
```

If `j` has reached the end of `s`, every character was successfully matched.

Therefore:

```text
true
```

Otherwise:

```text
false
```

---

# Why Two Pointers?

We don't need to compare every character of `s` with every character of `t`.

We only need to find the characters of `s` **in order** inside `t`.

For example:

```text
s = a b c
    ↑
    j

t = a h b g d c
    ↑
    i
```

Once `a` is found, we never need to look backward in `t`.

We simply continue searching for `b`, then `c`.

This makes the solution efficient.

---

# Important Idea

The order of characters must be maintained.

For:

```text
s = "ace"
t = "abcde"
```

we find:

```text
a → c → e
```

so it is valid.

But:

```text
s = "aec"
```

would require:

```text
a → e → c
```

and `c` comes before `e` in the remaining part of `t`.

Therefore it is not a subsequence.

---

# Complexity Analysis

### Time Complexity

We traverse string `t` only once:

```text
O(n)
```

where `n = t.length()`.

---

### Space Complexity

Only one pointer is used apart from the input strings.

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public boolean isSubsequence(String s, String t) {

        if(s.length() == 0){
            return true;
        }

        int j = 0;

        for(int i = 0; i < t.length() && j < s.length(); i++){

            if(s.charAt(j) == t.charAt(i)){
                j++;
            }
        }

        return j == s.length();
    }
}
```

---

# Key Concepts

- String
- Two Pointers
- Subsequence
- Character Matching
- Relative Order
- Linear Traversal

---

# Constraints

- `0 <= s.length <= 100`
- `0 <= t.length <= 10^4`
- `s` and `t` consist only of lowercase English letters.

---

# Follow Up

Suppose there are a very large number of incoming strings:

```text
s1, s2, s3, ..., sk
```

and we need to repeatedly check whether each one is a subsequence of the same string `t`.

In that situation, repeatedly scanning `t` from the beginning can be inefficient.

A better approach is to preprocess the positions of each character in `t`, so that for every next character of `s`, we can quickly find the next available position in `t`.

This is useful when the same `t` is queried many times.

---

# Learning Outcome

This problem demonstrates the basic **Two Pointer** pattern for checking whether one sequence can be obtained from another while preserving relative order.

The main idea is:

```text
Move through t
     ↓
Match current character of s
     ↓
If matched → move s pointer
     ↓
Continue until s is completely matched
```

The important code pattern is:

```java
if(s.charAt(j) == t.charAt(i)){
    j++;
}
```

Finally:

```java
return j == s.length();
```

The solution runs in:

```text
Time  → O(n)
Space → O(1)
```