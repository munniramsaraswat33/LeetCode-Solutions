# 3875. Construct Uniform Parity Array I

**Difficulty:** Easy  
**Topics:** Array, Math

---

## Problem Statement

You are given an array `nums1` of `n` distinct integers.

You want to construct another array `nums2` of length `n` such that the elements in `nums2` are either:

- All odd, or
- All even.

For each index `i`, you must choose exactly one of the following options:

1. `nums2[i] = nums1[i]`
2. `nums2[i] = nums1[i] - nums1[j]`, where `j != i`

The choices can be made independently and in any order.

Return `true` if it is possible to construct such an array `nums2`, otherwise return `false`.

---

## Example 1

### Input

```text
nums1 = [2, 3]
```

### Output

```text
true
```

### Explanation

We can choose:

```text
nums2[0] = nums1[0] - nums1[1]
         = 2 - 3
         = -1

nums2[1] = nums1[1]
         = 3
```

So:

```text
nums2 = [-1, 3]
```

Both `-1` and `3` are odd.

Therefore, all elements of `nums2` have the same parity.

Hence, the answer is `true`.

---

## Example 2

### Input

```text
nums1 = [4, 6]
```

### Output

```text
true
```

### Explanation

Both elements are already even.

We can simply choose:

```text
nums2[0] = nums1[0] = 4
nums2[1] = nums1[1] = 6
```

Therefore:

```text
nums2 = [4, 6]
```

All elements are even.

Hence, the answer is `true`.

---

# Approach

The main idea is to use the **parity** of the numbers.

There are two important cases.

### Case 1: All elements have the same parity

If all numbers are even, we can keep every element unchanged.

For example:

```text
nums1 = [2, 4, 6]
```

We can construct:

```text
nums2 = [2, 4, 6]
```

All elements are even.

Similarly, if all numbers are odd:

```text
nums1 = [1, 3, 5]
```

we can construct:

```text
nums2 = [1, 3, 5]
```

All elements are odd.

So the answer is `true`.

---

### Case 2: The array contains both odd and even numbers

Suppose the array contains at least one odd and one even number.

We can use an element with the opposite parity and subtract it.

The important parity rules are:

```text
Even - Odd = Odd
Odd - Even = Odd
```

Therefore, every element can be made odd by subtracting an element with the opposite parity.

For example:

```text
nums1 = [2, 3, 6, 7]
```

We can construct:

```text
2 - 3 = -1
3 - 2 = 1
6 - 3 = 3
7 - 2 = 5
```

So:

```text
nums2 = [-1, 1, 3, 5]
```

All elements are odd.

Therefore, the answer is again `true`.

---

# Intuition

The key observation is that **a valid uniform-parity array can always be constructed**.

If all elements already have the same parity, simply choose the original elements.

If both odd and even numbers exist, use the difference between numbers having different parity.

For different parity:

```text
Even - Odd = Odd
Odd - Even = Odd
```

Thus, all elements can be made odd.

Therefore, regardless of the input array, the answer is always:

```text
true
```

This means we do not actually need to construct `nums2`.

---

# Algorithm

1. Observe that `nums1` can always be transformed into a uniform-parity array.
2. If all elements already have the same parity, keep them unchanged.
3. If both odd and even elements exist, subtract an opposite-parity element from each element.
4. Every resulting element can be made odd.
5. Therefore, return `true`.

Since the construction is always possible, the implementation only needs to return `true`.

---

# Dry Run

Consider:

```text
nums1 = [2, 3, 6, 7]
```

The array contains both even and odd numbers.

### For `2`

Choose `3`:

```text
2 - 3 = -1
```

`-1` is odd.

### For `3`

Choose `2`:

```text
3 - 2 = 1
```

`1` is odd.

### For `6`

Choose `3`:

```text
6 - 3 = 3
```

`3` is odd.

### For `7`

Choose `2`:

```text
7 - 2 = 5
```

`5` is odd.

Therefore:

```text
nums2 = [-1, 1, 3, 5]
```

All elements are odd.

So:

```text
Answer = true
```

---

# Java Solution

```java
class Solution {

    public boolean uniformArray(int[] nums1) {

        return true;
    }
}
```

---

# Code Explanation

### Method Declaration

```java
public boolean uniformArray(int[] nums1)
```

This method receives the input array `nums1` and returns a boolean value.

---

### Return Statement

```java
return true;
```

The solution directly returns `true` because the required construction is always possible.

There are two possibilities:

#### Same Parity

If all elements are already even or all are already odd, we can simply choose:

```text
nums2[i] = nums1[i]
```

Therefore, `nums2` already has uniform parity.

#### Mixed Parity

If the array contains both even and odd values, we can subtract a number with the opposite parity.

For example:

```text
Even - Odd = Odd
Odd - Even = Odd
```

Thus, every element can be converted into an odd number.

Therefore, a valid `nums2` always exists.

So there is no need to traverse the array or perform any calculations.

The complete solution is:

```java
return true;
```

---

# Parity Rules

For addition:

```text
Even + Even = Even
Even + Odd  = Odd
Odd + Odd   = Even
```

For subtraction:

```text
Even - Even = Even
Odd - Odd   = Even
Even - Odd  = Odd
Odd - Even  = Odd
```

The two most important rules for this problem are:

```text
Even - Odd = Odd
Odd - Even = Odd
```

These rules allow us to make every element odd when both parities are present.

---

# Why Does `return true` Always Work?

Every possible input belongs to one of these cases.

### Case 1: All Even

```text
[2, 4, 6]
     ↓
Already uniform
     ↓
true
```

### Case 2: All Odd

```text
[1, 3, 5]
     ↓
Already uniform
     ↓
true
```

### Case 3: Mixed Parity

```text
[2, 3, 6, 7]
     ↓
Subtract opposite-parity values
     ↓
[-1, 1, 3, 5]
     ↓
All Odd
     ↓
true
```

Since every possible input belongs to one of these cases, the construction is always possible.

Therefore:

```java
return true;
```

is sufficient.

---

# Complexity Analysis

### Time Complexity

```text
O(1)
```

The array is not traversed.

The method immediately returns `true`.

### Space Complexity

```text
O(1)
```

No additional data structure is used.

---

# Key Concepts / Patterns

- Array
- Mathematics
- Parity
- Even and Odd Numbers
- Mathematical Observation
- Constant-Time Solution

---

# Constraints

- `1 <= nums1.length <= 100`
- `1 <= nums1[i] <= 100`
- `nums1` consists of distinct integers.

---

# Learning Outcome

- Understand the concept of parity.
- Learn how subtraction affects the parity of integers.
- Understand why numbers with different parity have an odd difference.
- Recognize when a mathematical observation can eliminate unnecessary computation.
- Learn to identify problems where the answer is guaranteed for every valid input.
- Understand why this problem can be solved in `O(1)` time and `O(1)` space.