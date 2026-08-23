# 1927. Sum Game

> **Difficulty:** Medium  
> **Topics:** Math, Greedy, Game Theory

---

## Problem Statement

Alice and Bob are playing a game with a string `num` consisting of digits and question marks (`?`).

The string has an even length.

- Alice replaces every `?` in the **first half** with a digit from `0` to `9`.
- Bob replaces every `?` in the **second half** with a digit from `0` to `9`.
- Alice wins if the sum of digits in the first half is equal to the sum of digits in the second half.
- Bob wins otherwise.

Alice moves first, and both players play optimally.

Return:

```text
true
```

if Alice wins, otherwise return:

```text
false
```

---

## Example 1

### Input

```text
num = "5023"
```

### Output

```text
false
```

### Explanation

There are no question marks.

The sums are:

```text
First half  = 5 + 0 = 5
Second half = 2 + 3 = 5
```

Since the sums are already equal, Alice would win.

---

## Example 2

### Input

```text
num = "25??"
```

### Output

```text
false
```

### Explanation

Alice has no question mark in the first half.

Bob controls both question marks in the second half and can choose digits so that the two sums are not equal.

Therefore, Alice cannot guarantee a win.

---

## Example 3

### Input

```text
num = "?3295??"
```

### Output

```text
false
```

The result depends on the number of question marks and the difference between the existing digit sums.

---

# Approach

Use **Greedy + Mathematical Observation**.

Split the string into two halves.

For each half, calculate:

```text
sum of known digits
number of '?'
```

Let:

```text
s1 = sum of known digits in first half
s2 = sum of known digits in second half

c1 = number of '?' in first half
c2 = number of '?' in second half
```

The important observation is:

### Case 1: Total number of `?` is odd

If:

```text
(c1 + c2) % 2 == 1
```

then Alice always wins.

Why?

The players alternate choosing digits, and with an odd number of question marks, Alice gets the final move. She can force the sums to be different.

Therefore:

```text
return true;
```

---

### Case 2: Total number of `?` is even

Now we compare the existing sum difference with the maximum effect that the question marks can create.

Each pair of question marks contributes a maximum difference of:

```text
9
```

Therefore, the required balance is:

```text
9 * (c2 - c1) / 2
```

If:

```text
s1 - s2
```

is exactly equal to this value, Alice can be forced into a draw.

Otherwise, Alice wins.

So:

```java
return s1 - s2 != 9 * (c2 - c1) / 2;
```

---

# Algorithm

1. Initialize:
   ```text
   s1 = 0
   s2 = 0
   c1 = 0
   c2 = 0
   ```
2. Traverse the first half:
   - If the character is `?`, increment `c1`.
   - Otherwise, add its digit value to `s1`.
3. Traverse the second half:
   - If the character is `?`, increment `c2`.
   - Otherwise, add its digit value to `s2`.
4. If:
   ```text
   (c1 + c2) % 2 == 1
   ```
   return `true`.
5. Otherwise, check:
   ```text
   s1 - s2 != 9 * (c2 - c1) / 2
   ```
6. Return the result.

---

# Dry Run

Input:

```text
num = "1?2?"
```

Split into two halves:

```text
First half  = "1?"
Second half = "2?"
```

### First Half

Known sum:

```text
s1 = 1
```

Question marks:

```text
c1 = 1
```

### Second Half

Known sum:

```text
s2 = 2
```

Question marks:

```text
c2 = 1
```

So:

```text
c1 + c2 = 2
```

This is even.

Now calculate:

```text
s1 - s2 = 1 - 2 = -1
```

And:

```text
9 * (c2 - c1) / 2
= 9 * (1 - 1) / 2
= 0
```

Since:

```text
-1 != 0
```

the answer is:

```text
true
```

---

# Understanding the Code

## Initialize Variables

```java
int s1 = 0;
int s2 = 0;
int c1 = 0;
int c2 = 0;
```

Here:

```text
s1 → known digit sum of first half
s2 → known digit sum of second half

c1 → '?' count in first half
c2 → '?' count in second half
```

---

## Process First Half

```java
for(int i = 0; i < num.length()/2; i++){
```

We traverse only the first half.

If the character is `?`:

```java
if(num.charAt(i) == '?'){
    c1++;
}
```

Otherwise, convert the character into its numeric digit:

```java
s1 += num.charAt(i) - '0';
```

For example:

```text
'7' - '0' = 7
```

---

## Process Second Half

```java
for(int i = num.length()/2; i < num.length(); i++){
```

The same process is applied to the second half.

```java
if(num.charAt(i) == '?'){
    c2++;
}
else{
    s2 += num.charAt(i) - '0';
}
```

---

## Odd Number of Question Marks

```java
if((c1+c2)%2 == 1){
    return true;
}
```

If the total number of question marks is odd, Alice can force a win.

Therefore, we immediately return:

```text
true
```

---

## Mathematical Condition

```java
return s1-s2 != 9*(c2-c1)/2;
```

When the number of question marks is even, the only situation where Alice cannot force a win is when the existing sum difference exactly matches the maximum possible balancing effect of the question marks.

The value:

```text
9 * (c2 - c1) / 2
```

represents this balancing condition.

If the two values are equal:

```text
s1 - s2 == 9 * (c2 - c1) / 2
```

Alice cannot guarantee a win.

Otherwise:

```text
Alice wins
```

---

# Why `9`?

Each digit chosen for a question mark can be between:

```text
0 and 9
```

Therefore, the maximum difference between two choices is:

```text
9 - 0 = 9
```

This is why the mathematical condition contains:

```text
9
```

---

# Complexity Analysis

### Time Complexity

We traverse the string once:

```text
O(n)
```

where `n` is the length of `num`.

---

### Space Complexity

Only four integer variables are used:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public boolean sumGame(String num) {

        int s1 = 0;
        int s2 = 0;

        int c1 = 0;
        int c2 = 0;

        for(int i = 0; i < num.length() / 2; i++){

            if(num.charAt(i) == '?'){
                c1++;
            }
            else{
                s1 += num.charAt(i) - '0';
            }
        }

        for(int i = num.length() / 2; i < num.length(); i++){

            if(num.charAt(i) == '?'){
                c2++;
            }
            else{
                s2 += num.charAt(i) - '0';
            }
        }

        if((c1 + c2) % 2 == 1){
            return true;
        }

        return s1 - s2 != 9 * (c2 - c1) / 2;
    }
}
```

---

# Key Concepts

- Greedy
- Math
- Game Theory
- String Traversal
- Digit Manipulation
- Mathematical Observation

---

# Constraints

- `2 <= num.length <= 10^5`
- `num.length` is even.
- `num` consists of digits and `?`.
- Each digit is between `0` and `9`.

---

# Learning Outcome

This problem demonstrates how a seemingly complex **two-player game** can be reduced to a simple mathematical condition.

The main idea is:

```text
Split string into two halves
        ↓
Calculate digit sums
        ↓
Count '?'
        ↓
Check whether total '?' is odd
        ↓
Apply mathematical balance condition
```

The important formula is:

```text
s1 - s2 != 9 * (c2 - c1) / 2
```

The solution runs in:

```text
Time  → O(n)
Space → O(1)
```

The key takeaway is that instead of simulating every possible digit choice, we use the **maximum possible contribution of each `?` (9)** to determine the winner directly.