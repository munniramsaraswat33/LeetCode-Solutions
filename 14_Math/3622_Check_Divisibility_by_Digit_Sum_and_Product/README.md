# 3622. Check Divisibility by Digit Sum and Product

> **Difficulty:** Easy  
> **Topics:** Math, Number Theory, Digit Manipulation

---

## Problem Statement

You are given a positive integer `n`.

An integer `n` is divisible by its **digit sum and digit product** if:

```text
n % (digit sum + digit product) == 0
```

The digit sum is the sum of all digits of `n`.

The digit product is the product of all digits of `n`.

Return `true` if `n` is divisible by the sum of its digits plus the product of its digits. Otherwise, return `false`.

---

## Example 1

### Input

```text
n = 99
```

### Output

```text
true
```

### Explanation

Digit sum:

```text
9 + 9 = 18
```

Digit product:

```text
9 × 9 = 81
```

Sum of digit sum and digit product:

```text
18 + 81 = 99
```

Since:

```text
99 % 99 = 0
```

the answer is:

```text
true
```

---

## Example 2

### Input

```text
n = 123
```

### Output

```text
false
```

### Explanation

Digit sum:

```text
1 + 2 + 3 = 6
```

Digit product:

```text
1 × 2 × 3 = 6
```

Therefore:

```text
sum + product = 6 + 6 = 12
```

Since:

```text
123 % 12 != 0
```

the answer is:

```text
false
```

---

# Approach

Use **Digit Manipulation**.

We extract each digit of `n` using:

```text
n % 10
```

and remove the last digit using:

```text
n / 10
```

For every digit:

- Add it to `sum`.
- Multiply it with `product`.

After processing all digits, check:

```text
n % (sum + product) == 0
```

If the remainder is `0`, return `true`; otherwise return `false`.

---

# Algorithm

1. Store the original value of `n`.
2. Create a temporary variable `k = n`.
3. Initialize:
   ```text
   sum = 0
   product = 1
   ```
4. While `k != 0`:
   - Extract the last digit:
     ```text
     digit = k % 10
     ```
   - Remove the last digit:
     ```text
     k = k / 10
     ```
   - Add the digit to `sum`.
   - Multiply the digit with `product`.
5. Calculate:
   ```text
   sum + product
   ```
6. Check:
   ```text
   n % (sum + product) == 0
   ```
7. Return the result.

---

# Dry Run

Input:

```text
n = 123
```

Initialize:

```text
sum = 0
product = 1
```

### Step 1

Last digit:

```text
123 % 10 = 3
```

Update:

```text
sum = 3
product = 3
```

Remove digit:

```text
123 / 10 = 12
```

---

### Step 2

Last digit:

```text
12 % 10 = 2
```

Update:

```text
sum = 3 + 2 = 5
product = 3 × 2 = 6
```

Remove digit:

```text
12 / 10 = 1
```

---

### Step 3

Last digit:

```text
1 % 10 = 1
```

Update:

```text
sum = 5 + 1 = 6
product = 6 × 1 = 6
```

Remove digit:

```text
1 / 10 = 0
```

Loop ends.

Now:

```text
sum = 6
product = 6
```

Therefore:

```text
sum + product = 12
```

Check:

```text
123 % 12 != 0
```

Final answer:

```text
false
```

---

# Understanding the Code

## Store Original Number

```java
int k = n;
```

We need to preserve the original `n` because it is used in the final divisibility check.

---

## Initialize Sum and Product

```java
long sum = 0;
long product = 1;
```

The sum starts from `0`.

The product starts from `1` because `1` is the multiplicative identity.

---

## Extract Digits

```java
int val = k % 10;
```

This gives the last digit of the number.

For example:

```text
123 % 10 = 3
```

---

## Remove Last Digit

```java
k /= 10;
```

For example:

```text
123 / 10 = 12
```

This allows us to process the next digit.

---

## Calculate Sum and Product

```java
sum += val;
product *= val;
```

Every extracted digit is added to `sum` and multiplied into `product`.

---

## Check Divisibility

```java
return n % (sum + product) == 0;
```

If the remainder is `0`, the number is divisible by the sum of its digit sum and digit product.

---

# Complexity Analysis

### Time Complexity

Each digit of `n` is processed exactly once.

If `d` is the number of digits:

```text
O(d)
```

---

### Space Complexity

Only a few variables are used:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public boolean checkDivisibility(int n) {

        int k = n;

        long sum = 0;
        long product = 1;

        while(k != 0){

            int val = k % 10;

            k /= 10;

            sum += val;

            product *= val;
        }

        return n % (sum + product) == 0;
    }
}
```

---

# Key Concepts

- Digit Manipulation
- Modulo Operator
- Integer Division
- Digit Sum
- Digit Product
- Divisibility
- Math

---

# Constraints

- `1 <= n <= 10^9`

---

# Learning Outcome

This problem demonstrates how to process every digit of an integer without converting it into a string.

The main digit extraction pattern is:

```text
digit = n % 10
n = n / 10
```

For each digit, we maintain:

```text
sum     → sum of digits
product → product of digits
```

Finally, we check:

```text
n % (sum + product) == 0
```

The solution runs in:

```text
Time  → O(d)
Space → O(1)
```

where `d` is the number of digits in `n`.