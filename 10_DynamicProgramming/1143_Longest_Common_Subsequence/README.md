# 1143. Longest Common Subsequence

> **Difficulty:** Medium  
> **Topics:** String, Dynamic Programming

---

## Problem Statement

Given two strings `text1` and `text2`, return the length of their **longest common subsequence**.

A **subsequence** is a string that can be formed by deleting some characters from the original string without changing the relative order of the remaining characters.

For example:

```text
"ace"
```

is a subsequence of:

```text
"abcde"
```

because we can remove `b` and `d`.

However:

```text
"aec"
```

is not a subsequence of `"abcde"` because the relative order is changed.

If there is no common subsequence, return:

```text
0
```

---

## Example 1

### Input

```text
text1 = "abcde"
text2 = "ace"
```

### Output

```text
3
```

### Explanation

The longest common subsequence is:

```text
"ace"
```

Its length is:

```text
3
```

---

## Example 2

### Input

```text
text1 = "abc"
text2 = "abc"
```

### Output

```text
3
```

### Explanation

Both strings are the same, so the entire string is the longest common subsequence.

```text
"abc"
```

Length:

```text
3
```

---

## Example 3

### Input

```text
text1 = "abc"
text2 = "def"
```

### Output

```text
0
```

### Explanation

There is no common character between the two strings.

Therefore, the longest common subsequence has length:

```text
0
```

---

# Approach

Use **Dynamic Programming**.

The important idea is to process the characters of `text2` one by one and maintain an array `dp`.

For every character of `text2`, `dp[i]` stores the best LCS length that can be obtained using characters up to position `i` of `text1`.

The solution uses only **one-dimensional DP** instead of the traditional two-dimensional DP table.

---

# DP Idea

Suppose:

```text
text1 = "abcde"
text2 = "ace"
```

When processing a character from `text2`, we compare it with every character of `text1`.

There are two possibilities.

### Case 1: Current characters are different

If:

```text
c != text1[i]
```

we keep the best answer already found before this position.

This is handled by:

```java
if(currlength < dp[i]){
    currlength = dp[i];
}
```

---

### Case 2: Current characters are equal

If:

```text
c == text1.charAt(i)
```

we can extend the previous common subsequence.

Therefore:

```java
dp[i] = currlength + 1;
```

---

# Algorithm

1. Create a DP array of size `text1.length()`.
2. Initialize:
   ```text
   longest = 0
   ```
3. Traverse every character `c` of `text2`.
4. For every character of `text1`:
   - Keep track of the best LCS length found so far using `currlength`.
   - If `dp[i]` is larger than `currlength`, update `currlength`.
   - If the current characters match:
     ```text
     dp[i] = currlength + 1
     ```
5. Update `longest`.
6. After processing both strings, return `longest`.

---

# Dry Run

Input:

```text
text1 = "abcde"
text2 = "ace"
```

Initially:

```text
dp = [0,0,0,0,0]
longest = 0
```

---

### Process `'a'`

Compare `'a'` with:

```text
a b c d e
```

At `'a'`:

```text
a == a
```

So:

```text
dp[0] = 1
```

Now:

```text
dp = [1,0,0,0,0]
```

---

### Process `'c'`

Compare `'c'` with:

```text
a b c d e
```

Before reaching `c`, the best subsequence length is:

```text
1
```

At `c`:

```text
c == c
```

Therefore:

```text
dp[2] = 1 + 1
      = 2
```

Now:

```text
dp = [1,1,2,0,0]
```

---

### Process `'e'`

Before reaching `e`, the best length is:

```text
2
```

At `e`:

```text
e == e
```

Therefore:

```text
dp[4] = 2 + 1
      = 3
```

Now:

```text
dp = [1,1,2,2,3]
```

Final answer:

```text
3
```

---

# Understanding the Code

## Create DP Array

```java
int dp[] = new int[text1.length()];
```

The array stores LCS information for positions in `text1`.

Unlike the normal LCS solution, we don't create a full:

```text
text1.length() × text2.length()
```

DP table.

---

## Process Characters of `text2`

```java
for(char c : text2.toCharArray()){
```

We process each character of `text2` one by one.

---

## Track Current Best Length

```java
int currlength = 0;
```

`currlength` stores the best subsequence length found before the current position of `text1`.

---

## Traverse `text1`

```java
for(int i = 0; i < dp.length; i++){
```

For each character of `text2`, compare it with every character of `text1`.

---

## Keep Previous Best

```java
if(currlength < dp[i]){
    currlength = dp[i];
}
```

If the value already stored in `dp[i]` is larger, update `currlength`.

This allows us to carry forward the best subsequence length.

---

## When Characters Match

```java
else if(c == text1.charAt(i)){
    dp[i] = currlength + 1;
    longest = Math.max(longest, currlength + 1);
}
```

If the characters are equal, we can add the current character to the existing common subsequence.

Therefore:

```text
new length = previous best + 1
```

---

# Why `currlength` Is Important

Consider:

```text
text1 = "abc"
text2 = "ac"
```

When processing `'c'`, the character `'a'` has already created a common subsequence of length `1`.

So when we reach `'c'`:

```text
currlength = 1
```

Since:

```text
c == c
```

we can extend it:

```text
1 + 1 = 2
```

Therefore:

```text
LCS = "ac"
```

with length:

```text
2
```

---

# Traditional DP vs This Approach

The traditional LCS solution normally uses a 2D array:

```text
dp[m+1][n+1]
```

which requires:

```text
O(m × n)
```

space.

This solution uses only:

```text
dp[text1.length()]
```

so the DP space is reduced to:

```text
O(m)
```

where `m` is the length of `text1`.

---

# Complexity Analysis

Let:

```text
m = text1.length()
n = text2.length()
```

### Time Complexity

For every character in `text2`, we traverse all characters of `text1`.

Therefore:

```text
O(m × n)
```

---

### Space Complexity

We use a one-dimensional DP array:

```text
dp[m]
```

Therefore:

```text
O(m)
```

---

# Java Solution

```java
class Solution {

    public int longestCommonSubsequence(String text1, String text2) {

        int dp[] = new int[text1.length()];
        int longest = 0;

        for(char c : text2.toCharArray()){

            int currlength = 0;

            for(int i = 0; i < dp.length; i++){

                if(currlength < dp[i]){
                    currlength = dp[i];
                }
                else if(c == text1.charAt(i)){

                    dp[i] = currlength + 1;

                    longest = Math.max(
                        longest,
                        currlength + 1
                    );
                }
            }
        }

        return longest;
    }
}
```

---

# Key Concepts

- Dynamic Programming
- String
- Subsequence
- One-Dimensional DP
- Space Optimization
- Character Matching
- Recurrence Relation

---

# Constraints

- `1 <= text1.length, text2.length <= 1000`
- `text1` and `text2` consist of only lowercase English characters.

---

# Learning Outcome

This problem demonstrates the **Longest Common Subsequence (LCS)** dynamic programming pattern.

The main idea is:

```text
Process text2 character by character
        ↓
Compare with every character of text1
        ↓
Keep the previous best length
        ↓
If characters match → extend the subsequence
        ↓
Store the result in dp[]
```

The important transition is:

```java
dp[i] = currlength + 1;
```

when:

```java
c == text1.charAt(i)
```

The solution uses **one-dimensional Dynamic Programming**, which reduces the space required compared with the traditional 2D LCS table.

The solution achieves:

```text
Time  → O(m × n)
Space → O(m)
```