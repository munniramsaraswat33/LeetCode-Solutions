# 633. Sum of Square Numbers

> **Difficulty:** Medium  
> **Topics:** Math, Two Pointers, Binary Search

---

## Problem Statement

Given a non-negative integer `c`, determine whether there exist two integers `a` and `b` such that:

```text
a² + b² = c
```

Return `true` if such integers exist, otherwise return `false`.

---

## Example 1

### Input

```text
c = 5
```

### Output

```text
true
```

### Explanation

We can choose:

```text
a = 1
b = 2
```

Then:

```text
1² + 2²
= 1 + 4
= 5
```

Therefore:

```text
true
```

---

## Example 2

### Input

```text
c = 3
```

### Output

```text
false
```

There are no integers `a` and `b` such that:

```text
a² + b² = 3
```

---

# Approach

We need to find two non-negative integers `a` and `b` satisfying:

```text
a² + b² = c
```

Instead of trying every possible pair, we use the **Two Pointer** technique.

Set:

```text
left = 0
right = floor(sqrt(c))
```

Why?

Because if:

```text
a² + b² = c
```

then neither `a` nor `b` can be greater than:

```text
sqrt(c)
```

So both pointers start within the possible range.

---

# Two Pointer Logic

At every step calculate:

```text
sum = left² + right²
```

There are three cases.

### Case 1: `sum == c`

We found valid values:

```text
left² + right² = c
```

Return:

```text
true
```

---

### Case 2: `sum < c`

The current sum is too small.

We need a larger value.

Since `left` is the smaller pointer, increase it:

```java
left++;
```

This increases:

```text
left²
```

and therefore increases the total sum.

---

### Case 3: `sum > c`

The current sum is too large.

We need a smaller value.

Decrease the larger pointer:

```java
right--;
```

This decreases:

```text
right²
```

and therefore decreases the total sum.

---

# Dry Run

### Input

```text
c = 5
```

Initial:

```text
left = 0
right = floor(sqrt(5)) = 2
```

---

### Step 1

Calculate:

```text
sum = 0² + 2²
    = 0 + 4
    = 4
```

Since:

```text
4 < 5
```

increase `left`:

```text
left = 1
```

---

### Step 2

Calculate:

```text
sum = 1² + 2²
    = 1 + 4
    = 5
```

Since:

```text
5 == 5
```

return:

```text
true
```

---

# Another Example

### Input

```text
c = 3
```

Initial:

```text
left = 0
right = 1
```

Calculate:

```text
0² + 1² = 1
```

Since:

```text
1 < 3
```

increase `left`:

```text
left = 1
```

Now:

```text
1² + 1² = 2
```

Still:

```text
2 < 3
```

Increase `left`:

```text
left = 2
```

Now:

```text
left > right
```

The search is finished.

Return:

```text
false
```

---

# Why Start `right` at `sqrt(c)`?

The largest possible value for either number is:

```text
floor(sqrt(c))
```

because:

```text
a² <= c
```

and:

```text
b² <= c
```

Therefore:

```text
a <= sqrt(c)
b <= sqrt(c)
```

For example, if:

```text
c = 25
```

then:

```text
sqrt(25) = 5
```

So we only need to consider:

```text
0 to 5
```

---

# Why Use `long`?

The constraint allows:

```text
c <= 2³¹ - 1
```

When calculating:

```java
left * left + right * right
```

the multiplication can become large.

Using:

```java
long
```

makes the arithmetic safer:

```java
long sum = left * left + right * right;
```

The pointers are also declared as `long`:

```java
long left = 0;
long right = (long)Math.sqrt(c);
```

---

# Algorithm

1. Set:
   ```text
   left = 0
   ```
2. Set:
   ```text
   right = floor(sqrt(c))
   ```
3. While:
   ```text
   left <= right
   ```
4. Calculate:
   ```text
   sum = left² + right²
   ```
5. If `sum == c`, return `true`.
6. If `sum < c`, increment `left`.
7. If `sum > c`, decrement `right`.
8. If no pair is found, return `false`.

---

# Java Solution

```java
class Solution {

    public boolean judgeSquareSum(int c) {

        long left = 0;
        long right = (long) Math.sqrt(c);

        while (left <= right) {

            long sum = left * left + right * right;

            if (sum == c) {
                return true;
            }

            else if (sum < c) {
                left++;
            }

            else {
                right--;
            }
        }

        return false;
    }
}
```

---

# Complexity Analysis

Let:

```text
n = c
```

The two pointers move only toward each other.

The initial search range is approximately:

```text
sqrt(c)
```

Therefore:

### Time Complexity

```text
O(sqrt(c))
```

### Space Complexity

Only a few variables are used:

```text
O(1)
```

---

# Key Concepts

- Two Pointers
- Mathematics
- Perfect Squares
- Square Root
- Search Space Reduction

---

# Constraints

```text
0 <= c <= 2³¹ - 1
```

---

# Learning Outcome

The important pattern is:

```text
left = 0
right = sqrt(c)

while left <= right:

    sum = left² + right²

    if sum == c:
        true

    if sum < c:
        left++

    if sum > c:
        right--
```

The main idea is:

> **If the sum is too small, increase the smaller value. If the sum is too large, decrease the larger value.**

### Complexity

```text
Time:  O(√c)
Space: O(1)
```