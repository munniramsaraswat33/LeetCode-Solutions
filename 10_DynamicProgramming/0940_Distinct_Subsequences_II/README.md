# 940. Distinct Subsequences II

**LeetCode Problem:** [940. Distinct Subsequences II](https://leetcode.com/problems/distinct-subsequences-ii/)

**Difficulty:** Hard

**Primary Topic:** Dynamic Programming

**Pattern:** DP + Character Contribution

---

## Problem Statement

Given a string `s`, return the number of **distinct non-empty subsequences** of `s`.

Since the answer can be very large, return it modulo:

```text
10^9 + 7
```

A subsequence is formed by deleting zero or more characters without changing the order of the remaining characters.

---

## Example 1

### Input

```text
s = "abc"
```

### Output

```text
7
```

### Explanation

Distinct subsequences are:

```text
a, b, c, ab, ac, bc, abc
```

---

## Example 2

### Input

```text
s = "aba"
```

### Output

```text
6
```

### Explanation

Distinct subsequences are:

```text
a, b, aa, ab, ba, aba
```

---

## Approach

Use Dynamic Programming with character contribution.

Maintain:

- `sum` → total distinct subsequences so far.
- `count[26]` → subsequences that ended with each character.

For every character:

```text
newSubsequence = 1 + sum
```

- `1` → subsequence containing only the current character.
- `sum` → append current character to every previous subsequence.

If the character appeared before, subtract its previous contribution to avoid duplicates.

Formula:

```text
sum = sum + newSubsequence - count[ch]
count[ch] = newSubsequence
```

Take modulo after every update.

---

## Algorithm

1. Initialize `sum = 0`.
2. Create `count[26]`.
3. Traverse every character in `s`.
4. Compute new subsequences using `1 + sum`.
5. Remove old contribution of the same character.
6. Update `count` and `sum`.
7. Return `sum % MOD`.

---

## Dry Run

**Input**

```text
s = "aba"
```

| Character | New Subsequences | Total Sum |
|-----------|------------------|-----------|
| a | 1 | 1 |
| b | 2 | 3 |
| a | 4 − 1 = 3 | 6 |

Answer:

```text
6
```

---

## Java Solution

```java
class Solution {
    public int distinctSubseqII(String s) {
        long sum = 0;
        int MOD = (int)1e9 + 7;
        long[] count = new long[26];

        for(char c : s.toCharArray()){
            long total = (1 + sum + MOD) % MOD;

            sum = (sum + total - count[c - 'a'] + MOD) % MOD;

            count[c - 'a'] = total;
        }

        return (int)sum;
    }
}
```

---

## Code Explanation

### Step 1: Store Total Subsequences

```java
long sum = 0;
```

Stores the total number of distinct subsequences found so far.

### Step 2: Contribution Array

```java
long[] count = new long[26];
```

`count[i]` stores the number of subsequences that ended with character `i`.

### Step 3: Generate New Subsequences

```java
long total = (1 + sum) % MOD;
```

Current character creates:

- A new single-character subsequence.
- A new subsequence by appending it to every previous subsequence.

### Step 4: Remove Duplicate Contribution

```java
sum = (sum + total - count[c-'a'] + MOD) % MOD;
```

If the character appeared earlier, subtract its previous contribution to keep only distinct subsequences.

### Step 5: Update Current Contribution

```java
count[c-'a'] = total;
```

Store the latest contribution of this character.

---

## Why Subtract `count[ch]`?

Example:

```text
s = "aa"
```

After first `a`:

```text
a
```

After second `a`:

New subsequences:

```text
a
aa
```

The subsequence `"a"` already exists.

Subtracting `count['a']` removes this duplicate, leaving only `"aa"` as the new contribution.

---

## Complexity Analysis

### Time Complexity

```text
O(n)
```

Single traversal of the string.

### Space Complexity

```text
O(1)
```

Only a 26-sized array is used.

---

## Key Concepts

- Dynamic Programming
- Character Contribution DP
- Counting Distinct Subsequences
- Modulo Arithmetic
- Duplicate Removal

---

## Summary

For each character:

```text
new = 1 + totalSubsequences
answer = answer + new − previousContribution(character)
```

The `count` array remembers the previous contribution of each character, ensuring duplicate subsequences are counted only once.

**Time:** `O(n)`  
**Space:** `O(1)`