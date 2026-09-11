# 136. Single Number

**LeetCode Problem:** [136. Single Number](https://leetcode.com/problems/single-number/)

**Difficulty:** Easy

**Primary Topic:** Bit Manipulation

**Pattern:** XOR

---

## Problem Statement

Given a non-empty integer array `nums`, every element appears **twice** except for one element that appears exactly once.

Return the element that appears only once.

The solution must run in:

```text
O(n)
```

time and use:

```text
O(1)
```

extra space.

---

## Example 1

### Input

```text
nums = [2, 2, 1]
```

### Output

```text
1
```

### Explanation

`2` appears twice, while `1` appears only once.

---

## Example 2

### Input

```text
nums = [4, 1, 2, 1, 2]
```

### Output

```text
4
```

### Explanation

`1` and `2` appear twice, while `4` appears only once.

---

## Example 3

### Input

```text
nums = [1]
```

### Output

```text
1
```

---

# Approach

We use the **XOR (`^`) operator**.

The most important properties of XOR are:

```text
a ^ a = 0
a ^ 0 = a
```

XOR is also associative and commutative:

```text
a ^ b ^ c = c ^ a ^ b
```

Therefore, when we XOR every element of the array:

```text
nums[0] ^ nums[1] ^ nums[2] ^ ...
```

all numbers that appear twice cancel each other.

The only number left is the number that appears once.

---

# Intuition

Consider:

```text
nums = [4, 1, 2, 1, 2]
```

XOR all elements:

```text
4 ^ 1 ^ 2 ^ 1 ^ 2
```

Because XOR can be rearranged:

```text
4 ^ (1 ^ 1) ^ (2 ^ 2)
```

Using:

```text
1 ^ 1 = 0
2 ^ 2 = 0
```

we get:

```text
4 ^ 0 ^ 0
```

And:

```text
4 ^ 0 = 4
```

Therefore:

```text
Answer = 4
```

---

# XOR Properties

## Property 1: Same Numbers Cancel

```text
a ^ a = 0
```

For example:

```text
5 ^ 5 = 0
```

This is the main reason XOR works for this problem.

---

## Property 2: XOR With Zero

```text
a ^ 0 = a
```

For example:

```text
7 ^ 0 = 7
```

After all duplicate numbers cancel, the single number is XORed with `0`, so it remains unchanged.

---

## Property 3: Order Does Not Matter

XOR is commutative:

```text
a ^ b = b ^ a
```

and associative:

```text
(a ^ b) ^ c = a ^ (b ^ c)
```

Therefore, we can process the array in any order.

---

# Algorithm

1. Initialize:
   ```java
   int single = 0;
   ```
2. Traverse every element of `nums`.
3. XOR the current element with `single`:
   ```java
   single ^= nums[i];
   ```
4. All duplicate values cancel each other.
5. The remaining value is the unique number.
6. Return `single`.

---

# Dry Run

Consider:

```text
nums = [4, 1, 2, 1, 2]
```

Initially:

```text
single = 0
```

### Step 1

```text
single = 0 ^ 4
       = 4
```

### Step 2

```text
single = 4 ^ 1
       = 5
```

### Step 3

```text
single = 5 ^ 2
       = 7
```

### Step 4

```text
single = 7 ^ 1
       = 6
```

### Step 5

```text
single = 6 ^ 2
       = 4
```

Final:

```text
single = 4
```

Therefore:

```text
Answer = 4
```

---

# Visual Cancellation

We can conceptually rearrange:

```text
4 ^ 1 ^ 2 ^ 1 ^ 2
```

as:

```text
4 ^ (1 ^ 1) ^ (2 ^ 2)
```

Then:

```text
4 ^ 0 ^ 0
```

Finally:

```text
4
```

So all duplicate elements disappear.

---

# Java Solution

```java
class Solution {
    public int singleNumber(int[] nums) {
        int n = nums.length;

        if(n == 1){
            return nums[0];
        }

        int single = 0;

        for(int i = 0; i < n; i++){
            single ^= nums[i];
        }

        return single;
    }
}
```

---

# Code Explanation

## 1. Store Array Length

```java
int n = nums.length;
```

This stores the number of elements in the array.

---

## 2. Handle One Element

```java
if(n == 1){
    return nums[0];
}
```

If the array contains only one element, that element is obviously the single number.

For example:

```text
nums = [7]
```

The answer is:

```text
7
```

This check is not strictly necessary because the XOR loop would also correctly return `7`, but it is included in the given solution.

---

## 3. Initialize XOR Result

```java
int single = 0;
```

We start with `0` because:

```text
0 ^ x = x
```

Therefore, the first number can be XORed directly with `0`.

---

## 4. XOR Every Element

```java
for(int i = 0; i < n; i++){
    single ^= nums[i];
}
```

This is equivalent to:

```java
single = single ^ nums[i];
```

As we process the array, duplicate values cancel:

```text
x ^ x = 0
```

The unique number remains.

---

## 5. Return the Result

```java
return single;
```

After processing every element, `single` contains the number that appeared only once.

---

# Why Does This Work?

Suppose the array contains:

```text
[a, b, c, b, a]
```

The XOR of all elements is:

```text
a ^ b ^ c ^ b ^ a
```

Because XOR is associative and commutative, we can rearrange:

```text
(a ^ a) ^ (b ^ b) ^ c
```

Now:

```text
0 ^ 0 ^ c
```

becomes:

```text
c
```

Therefore, only the unique element remains.

---

# Why Not Use HashMap?

A HashMap solution could count the frequency of every number.

For example:

```text
4 → 1
1 → 2
2 → 2
```

Then find the number with frequency `1`.

That approach works, but requires:

```text
O(n)
```

extra space.

The XOR approach only uses:

```text
O(1)
```

extra space.

Therefore, XOR is the optimal approach under the problem's constraints.

---

# HashMap vs XOR

| Approach | Time | Space |
|---|---:|---:|
| HashMap | O(n) | O(n) |
| Sorting | O(n log n) | Depends on sorting |
| XOR | O(n) | O(1) |

The XOR solution is the most space-efficient.

---

# Important XOR Pattern

This problem is a classic example of the pattern:

```text
Every element appears twice
+
One element appears once
+
Need O(1) extra space
        ↓
       XOR
```

Whenever you see this combination of conditions, XOR should be one of the first techniques you consider.

---

# Edge Case

Consider:

```text
nums = [1]
```

There are no duplicate values.

The XOR calculation is:

```text
0 ^ 1 = 1
```

So the answer is:

```text
1
```

---

# Another Example

Consider:

```text
nums = [7, 3, 5, 3, 5]
```

XOR:

```text
7 ^ 3 ^ 5 ^ 3 ^ 5
```

Rearrange:

```text
7 ^ (3 ^ 3) ^ (5 ^ 5)
```

Cancel duplicates:

```text
7 ^ 0 ^ 0
```

Therefore:

```text
7
```

is the single number.

---

# Complexity Analysis

Let `n` be the number of elements in `nums`.

## Time Complexity

We traverse the array exactly once.

```text
O(n)
```

---

## Space Complexity

Only one extra variable is used:

```java
int single;
```

Therefore:

```text
O(1)
```

extra space.

---

# Common Mistakes

## Mistake 1: Using Addition/Subtraction

Trying to find the unique number using the sum of all values can cause problems because the values can be large and the sum can overflow.

XOR avoids this issue.

---

## Mistake 2: Using a Set Without Considering Space

A Set can find the unique element, but it requires:

```text
O(n)
```

extra space.

The XOR solution requires only:

```text
O(1)
```

space.

---

## Mistake 3: Forgetting XOR Cancellation

The key identity is:

```text
a ^ a = 0
```

This allows all duplicate elements to disappear.

---

## Mistake 4: Using `&` Instead of `^`

Bitwise AND:

```text
&
```

does not have the required cancellation property.

The correct operator is XOR:

```text
^
```

---

# Bit-Level Understanding

XOR works bit by bit.

The XOR truth table is:

```text
0 ^ 0 = 0
0 ^ 1 = 1
1 ^ 0 = 1
1 ^ 1 = 0
```

Notice:

```text
1 ^ 1 = 0
```

and:

```text
0 ^ 0 = 0
```

So when the same integer is XORed twice, every corresponding bit becomes `0`.

Example:

```text
5 = 101
5 = 101
---------
^   000
```

Therefore:

```text
5 ^ 5 = 0
```

---

# Key Concepts / Patterns

## 1. Bit Manipulation

The primary technique is bitwise XOR.

```text
^
```

---

## 2. Duplicate Cancellation

The fundamental property is:

```text
x ^ x = 0
```

---

## 3. Identity Element

XOR with zero does not change a number:

```text
x ^ 0 = x
```

---

## 4. One-Pass Algorithm

The entire array can be processed in a single traversal:

```text
left → right
```

---

## 5. Constant Space

No additional data structure is required.

Only:

```text
single
```

is maintained.

---

# Learning Outcome

After solving this problem, you should understand:

- How the XOR operator works.
- Why `x ^ x = 0`.
- Why `x ^ 0 = x`.
- How duplicate values can be cancelled using XOR.
- How to solve a problem in `O(n)` time and `O(1)` space.
- How to recognize the classic "every element twice except one" pattern.

---

# Summary

The key idea is to XOR every number in the array.

For example:

```text
[4, 1, 2, 1, 2]
```

becomes:

```text
4 ^ 1 ^ 2 ^ 1 ^ 2
```

Rearrange:

```text
4 ^ (1 ^ 1) ^ (2 ^ 2)
```

Cancel duplicates:

```text
4 ^ 0 ^ 0
```

Result:

```text
4
```

Therefore, the XOR of all elements gives the single number.

### Final Complexity

```text
Time:  O(n)
Space: O(1)
```

**Primary Pattern: Bit Manipulation + XOR**