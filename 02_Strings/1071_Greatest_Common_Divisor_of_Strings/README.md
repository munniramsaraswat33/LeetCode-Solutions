# 1071. Greatest Common Divisor of Strings

**LeetCode Problem:** [1071. Greatest Common Divisor of Strings](https://leetcode.com/problems/greatest-common-divisor-of-strings/)

**Difficulty:** Easy

**Primary Topic:** Strings, Math

---

## Problem Statement

For two strings `str1` and `str2`, we say that string `t` **divides** both strings if:

```text
str1 = t + t + t + ...
str2 = t + t + t + ...
```

In other words, `t` can be repeated one or more times to construct both `str1` and `str2`.

We need to return the **largest string `t`** that divides both `str1` and `str2`.

If no such string exists, return:

```text
""
```

---

## Example 1

### Input

```text
str1 = "ABCABC"
str2 = "ABC"
```

### Output

```text
"ABC"
```

### Explanation

`"ABC"` can construct both strings:

```text
ABCABC = ABC + ABC
ABC    = ABC
```

Therefore, the greatest common divisor string is:

```text
ABC
```

---

## Example 2

### Input

```text
str1 = "ABABAB"
str2 = "ABAB"
```

### Output

```text
"AB"
```

### Explanation

The string `"AB"` can construct both:

```text
ABABAB = AB + AB + AB
ABAB   = AB + AB
```

Therefore:

```text
Answer = "AB"
```

---

## Example 3

### Input

```text
str1 = "LEET"
str2 = "CODE"
```

### Output

```text
""
```

### Explanation

There is no string that can repeatedly construct both strings.

Therefore, the answer is:

```text
""
```

---

# Approach

The solution uses two important observations.

### Observation 1: Concatenation Check

If a common divisor string exists, then:

```text
str1 + str2
```

must be equal to:

```text
str2 + str1
```

For example:

```text
str1 = "ABAB"
str2 = "AB"
```

Then:

```text
str1 + str2 = "ABABAB"
str2 + str1 = "ABABAB"
```

They are equal.

But consider:

```text
str1 = "ABC"
str2 = "AB"
```

Then:

```text
str1 + str2 = "ABCAB"
str2 + str1 = "ABABC"
```

They are different.

Therefore, no common divisor string exists.

---

### Observation 2: Use GCD of Lengths

If a common divisor string exists, its length must divide both string lengths.

So the maximum possible length is:

```text
gcd(str1.length(), str2.length())
```

For example:

```text
str1.length() = 6
str2.length() = 4
```

Then:

```text
gcd(6, 4) = 2
```

Therefore, the answer must have length `2`.

We simply take the first `2` characters of `str1`.

---

# Intuition

This problem is similar to the mathematical **Greatest Common Divisor (GCD)**.

For numbers:

```text
gcd(6, 4) = 2
```

For strings, we want the largest repeating pattern that can construct both strings.

For example:

```text
str1 = "ABABAB"
str2 = "ABAB"
```

Lengths:

```text
6 and 4
```

Their GCD is:

```text
gcd(6, 4) = 2
```

The first `2` characters are:

```text
"AB"
```

And:

```text
AB + AB + AB = ABABAB
AB + AB       = ABAB
```

So `"AB"` is the greatest common divisor string.

---

# Why Do We Check `str1 + str2 == str2 + str1`?

This is the key trick in the problem.

Suppose:

```text
str1 = "ABCABC"
str2 = "ABC"
```

Both strings are made from the same repeating pattern:

```text
ABC
```

Therefore:

```text
str1 + str2
= ABCABCABC
```

and:

```text
str2 + str1
= ABCABCABC
```

They are equal.

Now consider:

```text
str1 = "AB"
str2 = "ABC"
```

Then:

```text
str1 + str2 = "ABABC"
str2 + str1 = "ABCAB"
```

They are not equal.

Therefore, they cannot have a common repeating divisor string.

---

# Algorithm

1. Check whether:
   ```text
   str1 + str2 == str2 + str1
   ```
2. If they are not equal, return `""`.
3. Find:
   ```text
   gcd(str1.length(), str2.length())
   ```
4. Take the first `gcd` characters of `str1`.
5. Return that substring.

---

# Dry Run

Consider:

```text
str1 = "ABABAB"
str2 = "ABAB"
```

### Step 1: Concatenation Check

Calculate:

```text
str1 + str2
= "ABABABABAB"
```

Calculate:

```text
str2 + str1
= "ABABABABAB"
```

They are equal.

So a common divisor string exists.

---

### Step 2: Find GCD of Lengths

```text
str1.length() = 6
str2.length() = 4
```

Calculate:

```text
gcd(6, 4)
```

Using Euclidean algorithm:

```text
6 % 4 = 2
4 % 2 = 0
```

Therefore:

```text
gcd = 2
```

---

### Step 3: Get Substring

Take the first `2` characters of `str1`:

```text
str1 = "ABABAB"
          ↑↑
```

So:

```text
str1.substring(0, 2)
```

returns:

```text
"AB"
```

### Final Answer

```text
"AB"
```

---

# Java Solution

```java
class Solution {
    public String gcdOfStrings(String str1, String str2) {
        if(!(str1 + str2).equals(str2 + str1)){
            return "";
        }

        int gcd = gcd(str1.length(), str2.length());

        return str1.substring(0, gcd);
    }

    public int gcd(int a, int b){
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

---

# Code Explanation

## 1. Check Concatenation

```java
if(!(str1 + str2).equals(str2 + str1)){
    return "";
}
```

We compare:

```text
str1 + str2
```

with:

```text
str2 + str1
```

If they are different, there is no common divisor string.

So we return:

```java
return "";
```

---

## 2. Calculate GCD of Lengths

```java
int gcd = gcd(str1.length(), str2.length());
```

The length of the greatest common divisor string must divide both string lengths.

Therefore, we calculate the GCD of their lengths.

For example:

```text
lengths = 6 and 4
gcd = 2
```

---

## 3. Get the Common String

```java
return str1.substring(0, gcd);
```

The first `gcd` characters represent the greatest common divisor string.

For:

```text
str1 = "ABABAB"
gcd = 2
```

we get:

```text
"AB"
```

---

# GCD Function Explanation

The code uses the **Euclidean Algorithm**:

```java
public int gcd(int a, int b){
    return b == 0 ? a : gcd(b, a % b);
}
```

The mathematical rule is:

```text
gcd(a, b) = gcd(b, a % b)
```

until:

```text
b == 0
```

Then `a` is the GCD.

---

## Example

For:

```text
gcd(6, 4)
```

First:

```text
gcd(6, 4)
→ gcd(4, 6 % 4)
→ gcd(4, 2)
```

Then:

```text
gcd(4, 2)
→ gcd(2, 4 % 2)
→ gcd(2, 0)
```

Now:

```text
b == 0
```

So:

```text
gcd = 2
```

---

# Why Does the GCD of Lengths Work?

Suppose the common divisor string has length `k`.

For it to construct `str1`:

```text
str1.length() % k == 0
```

For it to construct `str2`:

```text
str2.length() % k == 0
```

Therefore, `k` must be a common divisor of both lengths.

The **largest possible** value of `k` is:

```text
gcd(str1.length(), str2.length())
```

The concatenation check guarantees that the strings actually follow the same repeating pattern.

Therefore, the first `gcd` characters of `str1` are the answer.

---

# Complexity Analysis

Let:

```text
n = str1.length()
m = str2.length()
```

### Time Complexity

The concatenation comparison requires:

```text
O(n + m)
```

The GCD calculation takes:

```text
O(log(min(n, m)))
```

The substring creation takes up to:

```text
O(gcd(n, m))
```

Overall:

```text
O(n + m)
```

### Space Complexity

The concatenated strings require additional space:

```text
O(n + m)
```

Therefore:

```text
Space = O(n + m)
```

---

# Key Concepts / Patterns

## 1. String Concatenation Trick

The important string property is:

```text
str1 + str2 == str2 + str1
```

This tells us whether both strings are generated by the same repeating pattern.

---

## 2. Greatest Common Divisor

The problem combines strings with the mathematical GCD concept.

Instead of finding a divisor directly from the strings, we find the GCD of their lengths.

```text
GCD of lengths
       ↓
Maximum possible divisor length
       ↓
Take prefix of that length
```

---

## 3. Euclidean Algorithm

The recursive GCD function uses:

```text
gcd(a, b) = gcd(b, a % b)
```

This is the standard Euclidean algorithm.

---

# Common Mistakes

## Mistake 1: Only Checking String Lengths

It is not enough that the lengths have a common divisor.

For example:

```text
str1 = "AB"
str2 = "AC"
```

Both lengths are `2`, but there is no common repeating string.

That's why we need:

```java
(str1 + str2).equals(str2 + str1)
```

---

## Mistake 2: Returning the Shorter String

The shorter string is not always the answer.

Example:

```text
str1 = "ABABAB"
str2 = "ABAB"
```

The shorter string is:

```text
"ABAB"
```

But the correct answer is:

```text
"AB"
```

because `"AB"` is the largest string that can repeatedly construct both.

---

## Mistake 3: Using `substring(0, gcd)` Before Validation

We first need to verify:

```java
(str1 + str2).equals(str2 + str1)
```

Otherwise, the GCD of the lengths alone does not guarantee that the prefix is a valid divisor.

---

# Learning Outcome

After solving this problem, you should understand:

- How to identify repeating patterns in strings.
- The useful concatenation property:
  ```text
  str1 + str2 == str2 + str1
  ```
- How mathematical GCD can be applied to strings.
- How the Euclidean algorithm works.
- How to combine string and mathematical observations to get an efficient solution.

---

# Summary

The solution uses two key ideas:

### Step 1

Check whether both strings follow the same repeating pattern:

```java
(str1 + str2).equals(str2 + str1)
```

If not:

```text
return ""
```

### Step 2

Find the GCD of their lengths:

```java
gcd(str1.length(), str2.length())
```

Then return that many characters from the beginning of `str1`.

For example:

```text
str1 = "ABABAB"
str2 = "ABAB"

GCD(6, 4) = 2

Answer = "AB"
```

### Final Complexity

```text
Time:  O(n + m)
Space: O(n + m)
```

**Primary Pattern: String + GCD / Mathematical Pattern**