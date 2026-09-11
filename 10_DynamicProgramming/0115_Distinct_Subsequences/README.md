# 115. Distinct Subsequences

**LeetCode Problem:** [115. Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/)

**Difficulty:** Hard

**Primary Topic:** Dynamic Programming

**Pattern:** 2D DP / Subsequence DP

---

## Problem Statement

Given two strings `s` and `t`, return the number of distinct subsequences of `s` which equal `t`.

A **subsequence** is a sequence that can be formed by deleting some characters from a string without changing the relative order of the remaining characters.

For example, from:

```text
s = "rabbbit"
```

we can form:

```text
"rabbit"
```

by deleting one `b`.

The task is to count how many different ways `t` can be formed as a subsequence of `s`.

---

## Example 1

### Input

```text
s = "rabbbit"
t = "rabbit"
```

### Output

```text
3
```

### Explanation

There are three different ways to remove one of the three consecutive `b` characters from `"rabbbit"` to obtain `"rabbit"`.

Therefore:

```text
Answer = 3
```

---

## Example 2

### Input

```text
s = "babgbag"
t = "bag"
```

### Output

```text
5
```

### Explanation

There are five distinct ways to select characters from `s` in order to form `"bag"`.

---

# Approach

We use **2D Dynamic Programming**.

Define:

```text
dp[i][j]
```

as:

> The number of distinct ways to form `t[j...]` using `s[i...]`.

In other words:

```text
dp[i][j] = number of ways to form t.substring(j)
           from s.substring(i)
```

The solution processes both strings from **right to left**.

---

# DP State

Let:

```text
n = s.length()
m = t.length()
```

We create:

```java
int[][] dp = new int[n + 1][m + 1];
```

The extra row and column make it easier to represent empty strings.

---

# Base Case

The most important base case is:

```text
dp[i][m] = 1
```

for every `i`.

Why?

When `j == m`, we have reached the end of `t`.

That means:

```text
t[j...] = ""
```

An empty string can always be formed from any remaining part of `s` by choosing nothing.

Therefore, there is exactly **one way** to form the empty string:

```text
choose nothing
```

So:

```java
dp[i][m] = 1;
```

---

# Recurrence

For every position `i` in `s` and `j` in `t`:

First, we can always choose to **skip** `s[i]`.

Therefore:

```text
dp[i][j] = dp[i + 1][j]
```

If:

```text
s[i] == t[j]
```

we have another possibility.

We can use `s[i]` to match `t[j]`.

Then we move both pointers forward:

```text
dp[i + 1][j + 1]
```

So:

```text
dp[i][j] = dp[i + 1][j] + dp[i + 1][j + 1]
```

when the characters match.

If they do not match:

```text
dp[i][j] = dp[i + 1][j]
```

---

# Intuition

At every character of `s`, we have a choice.

Suppose:

```text
s[i] == t[j]
```

Then there are two possibilities:

### Choice 1: Skip `s[i]`

We do not use this character.

```text
dp[i + 1][j]
```

We still need to form `t[j...]`.

---

### Choice 2: Use `s[i]`

Since:

```text
s[i] == t[j]
```

we can use this character to match `t[j]`.

Then both positions move forward:

```text
dp[i + 1][j + 1]
```

Therefore:

```text
dp[i][j] =
    skip s[i]
    +
    use s[i]
```

or:

```text
dp[i][j] = dp[i + 1][j] + dp[i + 1][j + 1]
```

---

# When Characters Do Not Match

Suppose:

```text
s[i] != t[j]
```

Then `s[i]` cannot be used to match `t[j]`.

Therefore, the only choice is to skip `s[i]`.

So:

```text
dp[i][j] = dp[i + 1][j]
```

---

# Algorithm

1. Let:
   ```text
   n = s.length()
   m = t.length()
   ```
2. Create a DP table:
   ```java
   int[][] dp = new int[n + 1][m + 1];
   ```
3. Set:
   ```java
   dp[i][m] = 1
   ```
   for all `i`.
4. Traverse `s` from right to left.
5. For every `i`, traverse `t` from right to left.
6. First assume we skip `s[i]`:
   ```java
   dp[i][j] = dp[i + 1][j];
   ```
7. If:
   ```text
   s[i] == t[j]
   ```
   add:
   ```java
   dp[i + 1][j + 1]
   ```
8. Return:
   ```java
   dp[0][0]
   ```

---

# Dry Run

Consider:

```text
s = "babgbag"
t = "bag"
```

We want to count the number of ways to form:

```text
"bag"
```

from:

```text
"babgbag"
```

The DP state is:

```text
dp[i][j]
```

where:

```text
i → position in s
j → position in t
```

---

## Base Case

`t` has length `3`.

Therefore:

```text
dp[i][3] = 1
```

for every `i`.

This represents:

```text
Forming an empty suffix of t
```

There is exactly one way: choose nothing.

---

## Processing Characters

Suppose:

```text
s[i] = 'b'
t[j] = 'b'
```

The characters match.

So we have two choices:

```text
1. Skip this 'b'
2. Use this 'b' to match the target 'b'
```

Therefore:

```text
dp[i][j]
=
dp[i + 1][j]
+
dp[i + 1][j + 1]
```

---

## If Characters Do Not Match

Suppose:

```text
s[i] = 'a'
t[j] = 'b'
```

They do not match.

We cannot use `a` to form `b`.

Therefore:

```text
dp[i][j] = dp[i + 1][j]
```

---

## Final Result

After filling the complete DP table:

```text
dp[0][0]
```

contains the number of ways to form the entire `t` from the entire `s`.

For:

```text
s = "babgbag"
t = "bag"
```

the result is:

```text
5
```

---

# Java Solution

```java
class Solution {
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();

        int dp[][] = new int[n + 1][m + 1];

        for(int i = 0; i <= n; i++){
            dp[i][m] = 1;
        }

        for(int i = n - 1; i >= 0; i--){
            for(int j = m - 1; j >= 0; j--){
                dp[i][j] = dp[i + 1][j];

                if(s.charAt(i) == t.charAt(j)){
                    dp[i][j] += dp[i + 1][j + 1];
                }
            }
        }

        return dp[0][0];
    }
}
```

---

# Code Explanation

## 1. Store String Lengths

```java
int n = s.length();
int m = t.length();
```

Here:

```text
n = length of s
m = length of t
```

These values determine the size of the DP table.

---

## 2. Create DP Table

```java
int dp[][] = new int[n + 1][m + 1];
```

The extra row and column handle the cases where one of the strings has reached its end.

The meaning is:

```text
dp[i][j]
```

= number of ways to form `t[j...]` from `s[i...]`.

---

## 3. Initialize Empty Target

```java
for(int i = 0; i <= n; i++){
    dp[i][m] = 1;
}
```

When:

```text
j = m
```

we have reached the end of `t`.

The remaining target is empty.

There is exactly one way to form it:

```text
choose nothing
```

Therefore:

```text
dp[i][m] = 1
```

---

## 4. Traverse `s` Backward

```java
for(int i = n - 1; i >= 0; i--){
```

We process `s` from right to left because:

```text
dp[i][j]
```

depends on:

```text
dp[i + 1][j]
dp[i + 1][j + 1]
```

Both states belong to the next position of `s`.

---

## 5. Traverse `t` Backward

```java
for(int j = m - 1; j >= 0; j--){
```

We also process `t` from right to left.

This allows the current state to use already computed states involving:

```text
j + 1
```

---

## 6. Skip Current Character

```java
dp[i][j] = dp[i + 1][j];
```

This represents the choice:

```text
Don't use s[i]
```

We simply move to:

```text
i + 1
```

while keeping the same target position `j`.

---

## 7. Match Current Character

```java
if(s.charAt(i) == t.charAt(j)){
    dp[i][j] += dp[i + 1][j + 1];
}
```

If the current characters match, we can use `s[i]` to match `t[j]`.

After using them:

```text
i → i + 1
j → j + 1
```

So we add:

```text
dp[i + 1][j + 1]
```

---

## 8. Return Final State

```java
return dp[0][0];
```

This represents:

```text
Form all of t using all of s
```

Therefore, it is the final answer.

---

# DP Recurrence

The complete recurrence can be written as:

```text
If s[i] != t[j]:

    dp[i][j] = dp[i + 1][j]
```

If:

```text
s[i] == t[j]
```

then:

```text
dp[i][j] = dp[i + 1][j] + dp[i + 1][j + 1]
```

The two terms represent:

```text
dp[i + 1][j]
        ↓
Skip s[i]

dp[i + 1][j + 1]
        ↓
Use s[i] to match t[j]
```

---

# Why Do We Add Both Choices?

Suppose:

```text
s = "aaa"
t = "aa"
```

There are three different ways to choose two `a`s:

```text
Positions (0, 1)
Positions (0, 2)
Positions (1, 2)
```

When characters match, we cannot simply choose one option.

We must count both:

```text
Skip current character
```

and:

```text
Use current character
```

That is why the recurrence contains addition.

---

# Important Example

Consider:

```text
s = "rabbbit"
t = "rabbit"
```

The target requires:

```text
r a b b i t
```

The source contains three `b`s:

```text
r a b b b i t
    ↑ ↑ ↑
```

We need to choose exactly two of them.

There are:

```text
3
```

ways to choose which two `b`s are used.

Therefore:

```text
Answer = 3
```

The DP naturally counts these different choices.

---

# Why Is This a Subsequence Problem?

The order of characters must remain unchanged.

For example:

```text
s = "abcde"
```

Possible subsequences include:

```text
ace
abd
bde
```

But:

```text
eca
```

is not a subsequence because the order has changed.

In this problem, we are specifically counting subsequences that are exactly equal to `t`.

---

# Difference Between Substring and Subsequence

### Substring

Characters must be consecutive.

Example:

```text
s = "abcdef"
```

`"bcd"` is a substring.

---

### Subsequence

Characters do not need to be consecutive.

Example:

```text
s = "abcdef"
```

`"ace"` is a subsequence.

For this problem, we are dealing with **subsequences**.

---

# Why Dynamic Programming?

A brute-force approach would try every possible subsequence of `s`.

For a string of length `n`, there can be up to:

```text
2^n
```

subsequences.

That becomes extremely expensive.

Dynamic programming avoids recalculating the same states.

Each state:

```text
dp[i][j]
```

is calculated only once.

---

# State Dependency

The current state:

```text
dp[i][j]
```

depends only on:

```text
dp[i + 1][j]
dp[i + 1][j + 1]
```

Visualization:

```text
                dp[i][j]
                /      \
               /        \
              ↓          ↓
       dp[i+1][j]   dp[i+1][j+1]
```

This is why we process `i` from right to left.

---

# Complexity Analysis

Let:

```text
n = s.length()
m = t.length()
```

## Time Complexity

We have two nested loops:

```text
n iterations
×
m iterations
```

Therefore:

```text
Time = O(n * m)
```

---

## Space Complexity

The DP table contains:

```text
(n + 1) × (m + 1)
```

elements.

Therefore:

```text
Space = O(n * m)
```

---

# Common Mistakes

## Mistake 1: Using `dp[i][j] = dp[i+1][j+1]` Only

When characters match, we have **two choices**:

```text
Skip
Use
```

So we need:

```text
dp[i + 1][j] + dp[i + 1][j + 1]
```

not just:

```text
dp[i + 1][j + 1]
```

---

## Mistake 2: Forgetting the Empty Target Base Case

We need:

```java
dp[i][m] = 1;
```

because an empty target can always be formed by choosing nothing.

Without this base case, the recurrence will not produce the correct counts.

---

## Mistake 3: Processing in the Wrong Direction

The recurrence depends on:

```text
i + 1
j + 1
```

Therefore, we process from:

```text
right → left
```

so those states are already calculated.

---

## Mistake 4: Treating It Like a Substring Problem

The selected characters do not have to be adjacent.

Only their relative order matters.

---

# Key Concepts / Patterns

## 1. 2D Dynamic Programming

The state depends on two positions:

```text
i → position in s
j → position in t
```

Therefore, a 2D DP table is natural.

---

## 2. Subsequence DP

This is a classic subsequence dynamic programming pattern.

At every character:

```text
Use it
or
Skip it
```

---

## 3. Include / Exclude Pattern

When:

```text
s[i] == t[j]
```

we have:

```text
Include s[i]
+
Exclude s[i]
```

This gives:

```text
dp[i + 1][j + 1]
+
dp[i + 1][j]
```

---

## 4. Reverse Traversal

Because the recurrence uses future indices:

```text
i + 1
j + 1
```

we calculate the table from bottom-right toward top-left.

---

# Learning Outcome

After solving this problem, you should understand:

- How to count subsequences using dynamic programming.
- How to define a 2D DP state using two strings.
- How to derive an include/exclude recurrence.
- Why matching characters create two choices.
- Why the empty target has exactly one way to be formed.
- How reverse traversal can be used when a state depends on `i + 1` and `j + 1`.
- How DP reduces exponential subsequence enumeration to `O(n * m)`.

---

# Summary

The key state is:

```text
dp[i][j]
```

which represents the number of ways to form:

```text
t[j...]
```

from:

```text
s[i...]
```

If the characters do not match:

```text
dp[i][j] = dp[i + 1][j]
```

If they match:

```text
dp[i][j] = dp[i + 1][j] + dp[i + 1][j + 1]
```

because we can either:

```text
Skip s[i]
```

or:

```text
Use s[i] to match t[j]
```

The base case is:

```text
dp[i][m] = 1
```

because there is exactly one way to form an empty target.

Finally:

```text
dp[0][0]
```

gives the number of distinct subsequences of `s` equal to `t`.

### Final Complexity

```text
Time:  O(n * m)
Space: O(n * m)
```

**Primary Pattern: 2D Dynamic Programming + Subsequence / Include-Exclude**