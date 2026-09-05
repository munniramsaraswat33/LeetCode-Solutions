# 3876. Construct Uniform Parity Array II

**Difficulty:** Medium  
**Topics:** Array, Math, Greedy

---

## Problem Statement

You are given an integer array `nums`.

You need to determine whether it is possible to construct a **uniform parity array** using the allowed operations from the problem.

A uniform parity array means that all elements are either:

- Even, or
- Odd.

Return `true` if it is possible, otherwise return `false`.

---

## Approach

The important observation is based on the **minimum element** of the array.

First, find the minimum value:

```java
int min = Integer.MAX_VALUE;
```

Then update it for every element.

If the minimum value is **odd**, the construction is always possible.

So:

```java
if(min % 2 == 1){
    return true;
}
```

If the minimum value is even, then every number must also be even.

If we find even one odd number, the construction is impossible:

```java
if(num % 2 == 1){
    return false;
}
```

If all numbers are even, return `true`.

---

# Intuition

The minimum element is important because it determines whether we can make the required parity uniform.

There are two main cases.

### Case 1: Minimum is Odd

Suppose:

```text
nums = [2, 5, 7]
```

The minimum is:

```text
2
```

Actually, here the minimum is even, so we need to check the remaining elements.

Consider:

```text
nums = [3, 5, 8]
```

The minimum is:

```text
3
```

Since the minimum is odd, the answer is immediately:

```text
true
```

---

### Case 2: Minimum is Even

Suppose:

```text
nums = [2, 4, 6]
```

The minimum is even and every element is also even.

Therefore:

```text
true
```

But if:

```text
nums = [2, 3, 6]
```

the minimum is even, but `3` is odd.

Therefore:

```text
false
```

---

# Algorithm

1. Find the minimum element of `nums`.
2. If the minimum element is odd, return `true`.
3. Otherwise, traverse the array.
4. If any element is odd, return `false`.
5. If all elements are even, return `true`.

---

# Dry Run

Consider:

```text
nums = [2, 4, 6, 8]
```

### Step 1: Find Minimum

```text
min = 2
```

`2` is even.

So we cannot immediately return `true`.

---

### Step 2: Check Every Element

```text
2 → Even
4 → Even
6 → Even
8 → Even
```

No odd element is found.

Therefore:

```text
Answer = true
```

---

## Another Example

Consider:

```text
nums = [2, 3, 6]
```

### Step 1

Minimum:

```text
min = 2
```

`2` is even.

---

### Step 2

Check the elements:

```text
2 → Even
3 → Odd
```

An odd element is found.

Therefore:

```text
Answer = false
```

---

# Java Solution

```java
class Solution {
    public boolean uniformArray(int[] nums) {
        int min = Integer.MAX_VALUE;

        for(int num : nums){
            min = Math.min(min, num);
        }

        if(min % 2 == 1){
            return true;
        }

        for(int num : nums){
            if(num % 2 == 1){
                return false;
            }
        }

        return true;
    }
}
```

---

# Code Explanation

### Find Minimum

```java
int min = Integer.MAX_VALUE;

for(int num : nums){
    min = Math.min(min, num);
}
```

We traverse the array and find its smallest element.

---

### Check Minimum's Parity

```java
if(min % 2 == 1){
    return true;
}
```

If the minimum is odd, a valid uniform parity array can be constructed.

So we immediately return `true`.

---

### Check for Odd Elements

```java
for(int num : nums){
    if(num % 2 == 1){
        return false;
    }
}
```

If the minimum is even, we need every element to be even.

If any odd element exists, return `false`.

---

### All Elements Are Even

```java
return true;
```

If the second loop finishes without finding an odd number, all elements are even.

Therefore, the answer is `true`.

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

We traverse the array twice in the worst case.

Since constant factors are ignored:

```text
O(n)
```

### Space Complexity

```text
O(1)
```

Only the `min` variable is used.

---

# Key Concepts / Patterns

- Array Traversal
- Minimum Element
- Parity
- Even and Odd Numbers
- Mathematical Observation
- Greedy Observation

---

# Learning Outcome

- Learn how to use the minimum element to simplify a parity problem.
- Understand how to check whether numbers are even or odd.
- Practice multiple array traversals.
- Learn how a simple mathematical observation can lead to an efficient solution.
- Understand an `O(n)` time and `O(1)` space solution.