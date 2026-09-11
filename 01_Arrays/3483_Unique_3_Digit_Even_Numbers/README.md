# 3483. Unique 3-Digit Even Numbers

**LeetCode:** [3483. Unique 3-Digit Even Numbers](https://leetcode.com/problems/unique-3-digit-even-numbers/)

**Difficulty:** Easy

**Primary Topic:** Arrays

**Pattern:** Frequency Array + Enumeration

---

## Problem Statement

You are given an array `digits` containing digits.

Using these digits, form **unique 3-digit even numbers**.

The number must satisfy:

- It must contain exactly 3 digits.
- The first digit cannot be `0`.
- The last digit must be even.
- Each digit can be used only as many times as it appears in the input array.
- Count each resulting number only once.

Return the total number of unique 3-digit even numbers that can be formed.

---

## Example 1

### Input

```text
digits = [1,2,3,4]
```

### Output

```text
6
```

Possible numbers include:

```text
124
132
142
214
234
314
```

and so on, considering the available digits and the even-number requirement.

---

# Approach

Use a **frequency array** to keep track of how many times each digit occurs.

Since the digits are only from:

```text
0 to 9
```

we can create:

```java
int[] freq = new int[10];
```

Then construct the number digit by digit:

```text
First digit  → 1 to 9
Second digit → 0 to 9
Third digit  → even digits
```

The third digit must be one of:

```text
0, 2, 4, 6, 8
```

The code temporarily decreases the frequency when a digit is selected and restores it after the current choice is finished.

This makes sure that the same input digit is not used more times than it is available.

---

# Intuition

A 3-digit number has three positions:

```text
Hundreds  Tens  Units
   ↓       ↓      ↓
   i       j      k
```

Each position has different restrictions.

### First Position

It cannot be zero:

```text
1 → 9
```

### Second Position

It can be any digit:

```text
0 → 9
```

### Third Position

The number must be even, so the last digit must be:

```text
0, 2, 4, 6, 8
```

Therefore, we enumerate all possible first and second digits and count the available even digits for the last position.

---

# Frequency Array

Suppose:

```text
digits = [1,2,2,4]
```

The frequency array becomes:

```text
digit:  0 1 2 3 4 5 6 7 8 9
freq:   0 1 2 0 1 0 0 0 0 0
```

This allows us to quickly determine whether a digit is still available.

For example:

```java
if(freq[2] > 0)
```

means at least one `2` is available.

---

# Algorithm

1. Create a frequency array of size `10`.
2. Count the occurrences of every digit.
3. Initialize:
   ```text
   count = 0
   ```
4. Choose the first digit from `1` to `9`.
5. Temporarily decrease its frequency.
6. Choose the second digit from `0` to `9`.
7. Temporarily decrease its frequency.
8. Check every even digit:
   ```text
   0, 2, 4, 6, 8
   ```
9. If the frequency of an even digit is still positive, a valid number can be formed.
10. Restore the second digit's frequency.
11. Restore the first digit's frequency.
12. Return `count`.

---

# Dry Run

Consider:

```text
digits = [1,2,3,4]
```

Frequency:

```text
1 → 1
2 → 1
3 → 1
4 → 1
```

---

## Choose First Digit

Suppose:

```text
i = 1
```

Use `1` as the hundreds digit.

Remaining digits:

```text
2,3,4
```

---

## Choose Second Digit

Suppose:

```text
j = 2
```

Use `2` as the tens digit.

Remaining:

```text
3,4
```

Now check the even digits:

```text
0 → unavailable
2 → unavailable
4 → available
6 → unavailable
8 → unavailable
```

So:

```text
124
```

is one valid number.

Increment:

```text
count++
```

---

## Restore Digits

After checking all possible last digits, restore:

```java
freq[j]++;
```

Then continue checking other possible second digits.

After all second digits are processed, restore:

```java
freq[i]++;
```

Then try another first digit.

---

# Java Solution

```java
class Solution {
    public int totalNumbers(int[] digits) {

        int[] freq = new int[10];

        for(int d : digits){
            freq[d]++;
        }

        int count = 0;

        for(int i = 1; i <= 9; i++){

            if(freq[i] == 0){
                continue;
            }

            freq[i]--;

            for(int j = 0; j <= 9; j++){

                if(freq[j] == 0){
                    continue;
                }

                freq[j]--;

                for(int k = 0; k < 9; k += 2){

                    if(freq[k] > 0){
                        count++;
                    }
                }

                freq[j]++;
            }

            freq[i]++;
        }

        return count;
    }
}
```

---

# Code Explanation

## 1. Create Frequency Array

```java
int[] freq = new int[10];
```

There are exactly ten possible digits:

```text
0,1,2,3,4,5,6,7,8,9
```

So a size `10` array is enough.

---

## 2. Count Digit Frequencies

```java
for(int d : digits){
    freq[d]++;
}
```

For every digit in the input, increase its frequency.

For:

```text
digits = [1,2,2,4]
```

we get:

```text
freq[1] = 1
freq[2] = 2
freq[4] = 1
```

---

# Choosing the First Digit

```java
for(int i = 1; i <= 9; i++){
```

The first digit starts from `1` because:

```text
0
```

cannot be the first digit of a 3-digit number.

---

## Check Availability

```java
if(freq[i] == 0){
    continue;
}
```

If the digit is not present, it cannot be used.

---

## Use the First Digit

```java
freq[i]--;
```

Temporarily consume one occurrence of the first digit.

This is important when a digit appears multiple times.

---

# Choosing the Second Digit

```java
for(int j = 0; j <= 9; j++){
```

The second digit can be any digit from:

```text
0 → 9
```

provided it is available.

---

## Check Availability

```java
if(freq[j] == 0){
    continue;
}
```

If no copy of `j` remains, skip it.

---

## Consume Second Digit

```java
freq[j]--;
```

Now one occurrence of `j` is reserved for the second position.

---

# Choosing the Last Digit

```java
for(int k = 0; k < 9; k += 2){
```

This generates:

```text
0
2
4
6
8
```

These are exactly the even digits.

---

## Check Availability

```java
if(freq[k] > 0){
    count++;
}
```

If an even digit is still available, it can be placed in the last position.

Therefore, one unique 3-digit number can be formed.

---

# Why Don't We Actually Construct the Number?

The problem asks only for the **count** of unique numbers.

We do not need to create strings or integers such as:

```text
124
126
128
...
```

Instead, once the first two digits are fixed, every available even digit represents exactly one unique number.

So we can simply:

```java
count++;
```

for every available even digit.

---

# Restoring Frequencies

After processing all possible last digits:

```java
freq[j]++;
```

Restore the second digit.

This allows the same digit to be considered for another second-position choice.

After finishing all second-digit choices:

```java
freq[i]++;
```

Restore the first digit.

This allows it to be used in another combination.

This technique is called:

```text
Choose → Modify State → Explore → Restore State
```

---

# Why Does This Count Unique Numbers?

The loops represent the three positions of the number:

```text
i → hundreds
j → tens
k → units
```

Every valid combination of these three positions represents exactly one 3-digit number.

The frequency array prevents using a digit more times than it occurs in the input.

Therefore, each valid number is counted exactly once.

---

# Important Observation

The last digit does not need to be explicitly removed and restored.

The code only checks:

```java
if(freq[k] > 0){
    count++;
}
```

because the last digit is used only once to count a possible number.

There is no deeper recursion or further position that needs the modified frequency.

Therefore, simply checking availability is sufficient.

---

# Example With Duplicate Digits

Consider:

```text
digits = [1,1,2]
```

We can form:

```text
112
```

but we cannot form:

```text
111
```

because there is only one `1` available after using one `1` for the hundreds position and another `1` for the tens position.

The frequency array correctly handles this.

---

# Complexity Analysis

The digit range is fixed:

```text
0 → 9
```

The algorithm has three small loops:

```text
9 × 10 × 5
```

at most.

Therefore, the practical time complexity is:

```text
O(1)
```

because the number of possible digits is constant.

Building the frequency array takes:

```text
O(n)
```

where `n` is the length of `digits`.

So overall:

```text
Time Complexity = O(n)
```

and after frequency construction, the enumeration itself is constant.

---

## Space Complexity

The frequency array contains only 10 elements:

```text
O(10) = O(1)
```

Therefore:

```text
Space Complexity = O(1)
```

---

# Key Concepts

## 1. Frequency Array

Use an array to store the count of every digit.

```text
freq[digit]
```

---

## 2. Enumeration

There are only three positions, so we can enumerate all possibilities directly.

---

## 3. Digit Restrictions

```text
First digit → 1 to 9
Second digit → 0 to 9
Last digit → 0,2,4,6,8
```

---

## 4. State Restoration

When a digit is selected:

```java
freq[digit]--;
```

After finishing that choice:

```java
freq[digit]++;
```

This allows us to reuse the same frequency array for other combinations.

---

# Pattern Recognition

When a problem involves:

- Digits from `0` to `9`
- Limited digit occurrences
- Constructing a small number
- Restrictions on certain positions
- Counting valid numbers

consider:

```text
Frequency Array + Enumeration
```

The general pattern is:

```text
Build frequencies
       ↓
Choose each position
       ↓
Check availability
       ↓
Restore frequency
       ↓
Count valid combinations
```

---

# Common Mistakes

## Mistake 1: Allowing Zero as the First Digit

A number like:

```text
024
```

is not a 3-digit number.

Therefore:

```java
for(int i = 1; i <= 9; i++)
```

starts from `1`.

---

## Mistake 2: Forgetting the Even Condition

The last digit must be one of:

```text
0, 2, 4, 6, 8
```

---

## Mistake 3: Ignoring Duplicate Digits

If a digit appears only once, it cannot be used twice.

The frequency array handles this automatically.

---

## Mistake 4: Not Restoring Frequencies

After trying a particular first or second digit, restore its frequency.

Otherwise, later combinations would incorrectly see fewer digits than actually exist.

---

# Edge Cases

### Only One Digit

```text
digits = [5]
```

Cannot form a 3-digit number.

Answer:

```text
0
```

---

### All Digits Are Zero

```text
digits = [0,0,0]
```

Zero cannot be the first digit.

Answer:

```text
0
```

---

### Repeated Same Digit

```text
digits = [2,2,2]
```

Only:

```text
222
```

can be formed.

Answer:

```text
1
```

---

### No Even Digit Available

If the input contains only odd digits, no 3-digit even number can be formed.

Answer:

```text
0
```

---

# Learning Outcome

After solving this problem, you should understand:

- How to use a frequency array for digits.
- How to handle repeated elements while constructing numbers.
- How to enumerate a small fixed search space.
- How position-specific constraints can simplify enumeration.
- How to modify and restore state safely.
- How to count possibilities without explicitly constructing every number.

---

# Final Takeaway

The key is to treat the number as three positions:

```text
Hundreds   Tens   Units
   ↓        ↓       ↓
  1-9      0-9    Even
```

Use a frequency array to ensure that every digit is used only as many times as it appears.

The pattern is:

```text
Frequency Array
      +
Enumeration
      +
Position Constraints
```

**Time Complexity:** `O(n)`

**Space Complexity:** `O(1)`