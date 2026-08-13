# 69. Sqrt(x)

> **Difficulty:** Easy  
> **Topics:** Math, Binary Search

---

## Problem Statement

Given a non-negative integer `x`, return the **integer square root** of `x`.

The result should be rounded down to the nearest integer.

In other words:

```text
floor(sqrt(x))
```

You cannot use built-in exponentiation functions such as:

```text
pow(x, 0.5)
```

or:

```text
x ** 0.5
```

---

## Example 1

### Input

```text
x = 4
```

### Output

```text
2
```

### Explanation

```text
sqrt(4) = 2
```

Therefore:

```text
answer = 2
```

---

## Example 2

### Input

```text
x = 8
```

### Output

```text
2
```

### Explanation

```text
sqrt(8) ≈ 2.828
```

Since we need to round down:

```text
answer = 2
```

---

# Approach

This problem can be solved using **Binary Search**.

We are looking for the largest integer `mid` such that:

```text
mid × mid <= x
```

For example, if:

```text
x = 8
```

we check:

```text
1 × 1 = 1  <= 8
2 × 2 = 4  <= 8
3 × 3 = 9  > 8
```

Therefore:

```text
answer = 2
```

---

# Search Space

For:

```text
x > 1
```

we can search between:

```text
1 and x / 2
```

because for every `x >= 2`:

```text
sqrt(x) <= x / 2
```

So:

```java
int start = 1;
int end = x / 2;
```

Special cases:

```java
if (x == 0 || x == 1) {
    return x;
}
```

---

# Binary Search Logic

There are three cases.

### Case 1: Exact Square Root

If:

```java
x == mid * mid
```

then `mid` is exactly the square root.

Return:

```java
return mid;
```

---

### Case 2: `mid² < x`

If:

```java
mid * mid < x
```

then `mid` is a possible answer.

But there may be a larger valid value.

So store:

```java
ans = mid;
```

and search to the right:

```java
start = mid + 1;
```

---

### Case 3: `mid² > x`

If:

```java
mid * mid > x
```

then `mid` is too large.

Search to the left:

```java
end = mid - 1;
```

---

# Why Do We Need `ans`?

Suppose:

```text
x = 8
```

Eventually we find:

```text
mid = 2
```

and:

```text
2 × 2 = 4 < 8
```

So:

```text
ans = 2
```

Then we try a larger value:

```text
mid = 3
```

but:

```text
3 × 3 = 9 > 8
```

So we move left.

At the end, we need to remember the last valid value:

```text
ans = 2
```

Therefore, `ans` stores the largest integer whose square is less than or equal to `x`.

---

# Important: Why Use `long`?

This is very important.

If we write:

```java
int pro = mid * mid;
```

the multiplication can overflow the `int` range.

For example, `x` can be as large as:

```text
2³¹ - 1
```

and `mid * mid` can become larger than the maximum value of an `int`.

Therefore, your solution correctly uses:

```java
long pro = (long) mid * mid;
```

The cast to `long` happens before multiplication, so the multiplication is performed using `long`.

---

# Dry Run

### Input

```text
x = 8
```

Initial:

```text
start = 1
end = 4
ans = 0
```

---

### Step 1

```text
mid = 1 + (4 - 1) / 2
    = 2
```

Calculate:

```text
2 × 2 = 4
```

Since:

```text
4 < 8
```

`2` is a valid candidate.

```text
ans = 2
start = 3
```

---

### Step 2

Now:

```text
start = 3
end = 4
```

Calculate:

```text
mid = 3
```

Square:

```text
3 × 3 = 9
```

Since:

```text
9 > 8
```

move left:

```text
end = 2
```

Now:

```text
start = 3
end = 2
```

Loop ends.

Return:

```text
ans = 2
```

---

# Algorithm

1. Handle `0` and `1`.
2. Set:
   ```text
   start = 1
   end = x / 2
   ans = 0
   ```
3. While `start <= end`:
   - Calculate `mid`.
   - Calculate `mid * mid` using `long`.
4. If `mid² == x`, return `mid`.
5. If `mid² < x`:
   - Store `mid` in `ans`.
   - Search right.
6. If `mid² > x`:
   - Search left.
7. Return `ans`.

---

# Java Solution

```java
class Solution {

    public int mySqrt(int x) {

        if (x == 0 || x == 1) {
            return x;
        }

        int start = 1;
        int end = x / 2;
        int ans = 0;

        while (start <= end) {

            int mid = start + (end - start) / 2;

            long pro = (long) mid * mid;

            if (x == pro) {

                return mid;

            } else if (x > pro) {

                ans = mid;
                start = mid + 1;

            } else {

                end = mid - 1;
            }
        }

        return ans;
    }
}
```

---

# Complexity Analysis

Let `x` be the input value.

### Time Complexity

Binary search cuts the search space approximately in half each iteration:

```text
O(log x)
```

---

### Space Complexity

Only a few variables are used:

```text
O(1)
```

---

# Key Concepts

- Binary Search
- Integer Square Root
- Search Space Reduction
- Overflow Handling
- `long` Arithmetic

---

# Constraints

```text
0 <= x <= 2³¹ - 1
```

---

# Learning Outcome

This problem is a great example of **Binary Search on the answer**.

We are not searching for an element directly.

Instead, we are searching for the largest value satisfying:

```text
mid² <= x
```

The important pattern is:

```java
if (pro < x) {
    ans = mid;
    start = mid + 1;
} else {
    end = mid - 1;
}
```

This same idea can be used in many **binary-search-on-answer** problems.

### Complexity

```text
Time:  O(log x)
Space: O(1)
```