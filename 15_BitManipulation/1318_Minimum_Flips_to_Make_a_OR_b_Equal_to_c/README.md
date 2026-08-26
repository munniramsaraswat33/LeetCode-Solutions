# 1318. Minimum Flips to Make a OR b Equal to c

> **Difficulty:** Medium  
> **Topics:** Bit Manipulation

---

## Problem Statement

Given three integers:

```text
a
b
c
```

You can flip any bit in `a` or `b`.

A **bit flip** changes:

```text
0 → 1
```

or:

```text
1 → 0
```

Return the minimum number of bit flips required so that:

```text
a OR b = c
```

where `OR` is the bitwise OR operation.

---

## Example 1

### Input

```text
a = 2
b = 6
c = 5
```

Binary representation:

```text
a = 010
b = 110
c = 101
```

We need:

```text
010 OR 110 = 110
```

but the target is:

```text
101
```

At the last bit:

```text
0 OR 0 = 0
```

but `c` requires:

```text
1
```

So one flip is required.

At the middle bit:

```text
1 OR 1 = 1
```

but `c` requires:

```text
0
```

Both bits of `a` and `b` are `1`, so both must be flipped to `0`.

Therefore:

```text
Minimum flips = 3
```

---

## Example 2

### Input

```text
a = 4
b = 2
c = 7
```

Binary:

```text
a = 100
b = 010
c = 111
```

Current OR:

```text
100 OR 010 = 110
```

Only the last bit needs to become `1`.

Flip one bit:

```text
100 OR 011 = 111
```

Therefore:

```text
Output = 1
```

---

## Example 3

### Input

```text
a = 1
b = 2
c = 3
```

Binary:

```text
a = 01
b = 10
c = 11
```

Already:

```text
01 OR 10 = 11
```

Therefore:

```text
Output = 0
```

---

# Approach

Use **Bit Manipulation**.

Instead of converting the numbers to binary strings, process their bits directly using:

```java
& 1
```

and:

```java
>>= 1
```

For every bit position, extract:

```text
bitA = a & 1
bitB = b & 1
bitC = c & 1
```

Then determine how many flips are required.

There are only two important cases.

---

# Case 1: `c` Bit is `0`

If:

```text
bitC = 0
```

then we need:

```text
bitA OR bitB = 0
```

For OR to be `0`, both bits must be `0`.

Therefore:

```text
a bit = 0, b bit = 0
```

is already correct.

If either bit is `1`, that bit must be flipped to `0`.

Therefore, the number of flips is:

```text
bitA + bitB
```

Examples:

```text
a = 0, b = 0 → 0 flips
a = 0, b = 1 → 1 flip
a = 1, b = 0 → 1 flip
a = 1, b = 1 → 2 flips
```

This is implemented as:

```java
count += bitA + bitB;
```

---

# Case 2: `c` Bit is `1`

If:

```text
bitC = 1
```

then:

```text
bitA OR bitB
```

must be `1`.

There are three valid combinations:

```text
0 OR 1 = 1
1 OR 0 = 1
1 OR 1 = 1
```

Only one combination is invalid:

```text
0 OR 0 = 0
```

So if both `bitA` and `bitB` are `0`, we need exactly one flip.

```java
if(bitA == 0 && bitB == 0){
    count++;
}
```

---

# Algorithm

1. Initialize:
   ```text
   count = 0
   ```
2. Process the numbers bit by bit.
3. Extract the last bit of `a`, `b`, and `c`.
4. If `c`'s bit is `0`:
   - Every `1` in `a` or `b` needs to be flipped.
5. If `c`'s bit is `1`:
   - If both `a` and `b` have `0`, one flip is required.
6. Right shift all three numbers by one bit.
7. Continue until all bits are processed.
8. Return `count`.

---

# Dry Run

Input:

```text
a = 2
b = 6
c = 5
```

Binary:

```text
a = 010
b = 110
c = 101
```

---

### Bit 1

Last bits:

```text
bitA = 0
bitB = 0
bitC = 1
```

Since `c` requires `1`:

```text
0 OR 0 = 0
```

We need one flip.

```text
count = 1
```

---

### Bit 2

After shifting:

```text
a = 1
b = 3
c = 2
```

Last bits:

```text
bitA = 1
bitB = 1
bitC = 0
```

Since `c` requires `0`:

```text
1 OR 1 = 1
```

Both bits need to become `0`.

Therefore:

```text
count += 1 + 1
```

```text
count = 3
```

---

### Bit 3

After shifting:

```text
a = 0
b = 1
c = 1
```

Last bits:

```text
bitA = 0
bitB = 1
bitC = 1
```

We already have:

```text
0 OR 1 = 1
```

No flip is needed.

```text
count = 3
```

Final answer:

```text
3
```

---

# Understanding the Code

## Extract Last Bit

```java
int bitA = a & 1;
int bitB = b & 1;
int bitC = c & 1;
```

The operation:

```text
number & 1
```

extracts the least significant bit.

For example:

```text
6 = 110
1 = 001
```

Therefore:

```text
110 & 001 = 0
```

So the last bit of `6` is `0`.

---

## When `c` Has a `0`

```java
if(bitC == 0){
    count += bitA + bitB;
}
```

For:

```text
c = 0
```

both `a` and `b` must become `0`.

Therefore:

```text
0 + 0 = 0 flips
1 + 0 = 1 flip
0 + 1 = 1 flip
1 + 1 = 2 flips
```

---

## When `c` Has a `1`

```java
else{
    if(bitA == 0 && bitB == 0){
        count++;
    }
}
```

For:

```text
c = 1
```

we only need at least one of `a` or `b` to be `1`.

If both are `0`:

```text
0 OR 0 = 0
```

we flip either one:

```text
1 OR 0 = 1
```

Therefore, exactly one flip is required.

---

## Move to the Next Bit

```java
a >>= 1;
b >>= 1;
c >>= 1;
```

Right shifting removes the current last bit.

For example:

```text
6 = 110
```

After:

```java
6 >> 1
```

we get:

```text
3 = 11
```

This allows us to process the next bit.

---

# Bit-Level Decision Table

| `bitA` | `bitB` | `bitC` | Required Flips |
|:------:|:------:|:------:|:--------------:|
| 0 | 0 | 0 | 0 |
| 0 | 1 | 0 | 1 |
| 1 | 0 | 0 | 1 |
| 1 | 1 | 0 | 2 |
| 0 | 0 | 1 | 1 |
| 0 | 1 | 1 | 0 |
| 1 | 0 | 1 | 0 |
| 1 | 1 | 1 | 0 |

This table completely describes the solution.

---

# Why We Process Bits Individually

The condition:

```text
a OR b = c
```

works independently for every bit position.

For example:

```text
  1010
  0110
------
  1110
```

Each column can be considered separately.

Therefore, we can calculate the required flips for each bit independently and add them together.

---

# Important Bitwise Operators

### Bitwise AND

```java
a & 1
```

Used to extract the last bit.

---

### Right Shift

```java
a >>= 1
```

Used to move to the next bit.

---

### Bitwise OR

The required condition is:

```text
a | b = c
```

For each bit:

```text
0 | 0 = 0
0 | 1 = 1
1 | 0 = 1
1 | 1 = 1
```

---

# Complexity Analysis

The integers are processed bit by bit.

Since Java integers contain a fixed number of bits, the loop runs at most around:

```text
32
```

times.

Therefore:

### Time Complexity

```text
O(log(max(a,b,c)))
```

For 32-bit integers, this is effectively:

```text
O(1)
```

---

### Space Complexity

Only a few variables are used.

Therefore:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public int minFlips(int a, int b, int c) {

        int count = 0;

        while(a > 0 || b > 0 || c > 0){

            int bitA = a & 1;
            int bitB = b & 1;
            int bitC = c & 1;

            if(bitC == 0){

                count += bitA + bitB;
            }
            else{

                if(bitA == 0 && bitB == 0){
                    count++;
                }
            }

            a >>= 1;
            b >>= 1;
            c >>= 1;
        }

        return count;
    }
}
```

---

# Key Concepts

- Bit Manipulation
- Bitwise AND
- Bitwise OR
- Right Shift
- Binary Representation
- Greedy Bit Processing

---

# Constraints

- `1 <= a, b, c <= 10^9`

---

# Learning Outcome

This problem demonstrates how a bitwise equation can be solved by processing each bit independently.

The main idea is:

```text
Extract current bits
        ↓
Check required bit in c
        ↓
Calculate minimum flips
        ↓
Right shift
        ↓
Process next bit
```

The two important rules are:

```text
If c bit = 0:
    flips = bitA + bitB
```

and:

```text
If c bit = 1:
    flips = 1 only when bitA = 0 and bitB = 0
```

The solution avoids converting integers into binary strings and directly works with bits using:

```java
a & 1
```

and:

```java
a >>= 1
```

The solution achieves:

```text
Time  → O(log(max(a,b,c))) ≈ O(1)
Space → O(1)
```