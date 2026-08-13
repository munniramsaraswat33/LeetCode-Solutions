# 1664. Ways to Make a Fair Array

> **Difficulty:** Medium  
> **Topics:** Array, Prefix Sum

---

## Problem Statement

You are given an integer array `nums`.

You must choose **exactly one index** and remove that element.

After removing the element, the remaining elements shift their indices.

An array is called **fair** if:

```text
sum of elements at even indices
=
sum of elements at odd indices
```

Return the number of indices that can be removed so that the resulting array is fair.

---

## Example 1

### Input

```text
nums = [2,1,6,4]
```

### Output

```text
1
```

### Explanation

Try removing each index.

#### Remove index `0`

```text
[1,6,4]
```

Even-index sum:

```text
1 + 4 = 5
```

Odd-index sum:

```text
6
```

Not fair.

---

#### Remove index `1`

```text
[2,6,4]
```

Even-index sum:

```text
2 + 4 = 6
```

Odd-index sum:

```text
6
```

Fair.

Therefore, index `1` is a valid removal.

---

#### Remove index `2`

```text
[2,1,4]
```

Even-index sum:

```text
2 + 4 = 6
```

Odd-index sum:

```text
1
```

Not fair.

---

#### Remove index `3`

```text
[2,1,6]
```

Even-index sum:

```text
2 + 6 = 8
```

Odd-index sum:

```text
1
```

Not fair.

Therefore:

```text
answer = 1
```

---

## Example 2

### Input

```text
nums = [1,1,1]
```

### Output

```text
3
```

Removing any element produces:

```text
[1,1]
```

whose even and odd sums are both `1`.

Therefore, all three indices are valid.

---

## Example 3

### Input

```text
nums = [1,2,3]
```

### Output

```text
0
```

No single removal produces a fair array.

---

# Approach

A brute-force solution would remove every element and calculate the even and odd sums again.

That would take:

```text
O(n²)
```

time.

We can do better using **prefix sums / left-right sums**.

The main difficulty is that after removing an element, all elements to its right **change their parity**.

For example:

```text
Original:
index:  0 1 2 3 4
        A B C D E

Remove index 2:

index:  0 1 2 3
        A B D E
```

Notice:

```text
D: index 3 → index 2
E: index 4 → index 3
```

So every element on the right switches between even and odd index.

---

# Main Idea

Maintain four sums:

```text
leftEven
leftOdd
totalEven
totalOdd
```

Where:

- `leftEven` = sum of even-indexed elements before `i`
- `leftOdd` = sum of odd-indexed elements before `i`
- `totalEven` = sum of even-indexed elements after removing `nums[i]`
- `totalOdd` = sum of odd-indexed elements after removing `nums[i]`

---

# Why Do Right-Side Sums Swap?

Suppose we remove index `i`.

All elements to the right shift one position to the left.

Therefore:

```text
old even index → new odd index
old odd index  → new even index
```

So after removing `nums[i]`:

```text
newEven = leftEven + totalOdd
newOdd  = leftOdd + totalEven
```

This is the key idea of the solution.

---

# Algorithm

### Step 1: Calculate Total Even and Odd Sums

First calculate:

```text
totalEven
totalOdd
```

for the entire array.

---

### Step 2: Traverse the Array

For every index `i`:

First remove `nums[i]` from its corresponding total sum.

If `i` is even:

```java
totalEven -= nums[i];
```

Otherwise:

```java
totalOdd -= nums[i];
```

Now `totalEven` and `totalOdd` represent only the elements **to the right of `i`**.

---

### Step 3: Calculate New Even and Odd Sums

After removing `nums[i]`, the right side changes parity.

Therefore:

```java
int newEven = leftEven + totalOdd;
int newOdd = leftOdd + totalEven;
```

If:

```java
newEven == newOdd
```

then removing index `i` creates a fair array.

Increment:

```java
ans++;
```

---

### Step 4: Add Current Element to Left

After checking index `i`, move it into the left side.

If `i` is even:

```java
leftEven += nums[i];
```

Otherwise:

```java
leftOdd += nums[i];
```

---

# Dry Run

### Input

```text
nums = [2,1,6,4]
```

First calculate total sums.

Even indices:

```text
index 0 → 2
index 2 → 6

totalEven = 8
```

Odd indices:

```text
index 1 → 1
index 3 → 4

totalOdd = 5
```

Initially:

```text
leftEven = 0
leftOdd = 0
```

---

## Remove Index 0

`0` is even.

Remove `nums[0]` from `totalEven`:

```text
totalEven = 8 - 2 = 6
totalOdd = 5
```

Now the right side shifts parity.

New even sum:

```text
newEven = leftEven + totalOdd
        = 0 + 5
        = 5
```

New odd sum:

```text
newOdd = leftOdd + totalEven
       = 0 + 6
       = 6
```

Not fair.

Now move index `0` to the left:

```text
leftEven = 2
leftOdd = 0
```

---

## Remove Index 1

`1` is odd.

Remove it:

```text
totalOdd = 5 - 1 = 4
```

Now:

```text
totalEven = 6
```

Calculate:

```text
newEven = leftEven + totalOdd
        = 2 + 4
        = 6
```

```text
newOdd = leftOdd + totalEven
       = 0 + 6
       = 6
```

Therefore:

```text
newEven == newOdd
```

So index `1` is valid.

```text
ans = 1
```

---

# Important Formula

The most important part of this problem is:

```java
int newEven = leftEven + totalOdd;
int newOdd = leftOdd + totalEven;
```

Why?

Because after removing an element:

```text
LEFT SIDE
↓
indices don't change

RIGHT SIDE
↓
every index decreases by 1
↓
even ↔ odd
```

Therefore, the right-side sums must be swapped.

---

# Why Do We Subtract Before Checking?

For index `i`, we want:

```text
left side + right side
```

but **not `nums[i]` itself**.

So first:

```java
if(i % 2 == 0){
    totalEven -= nums[i];
}
else{
    totalOdd -= nums[i];
}
```

After this operation:

```text
left  = elements before i
right = elements after i
```

Then we can calculate the sums of the array after removing `nums[i]`.

---

# Java Solution

```java
class Solution {

    public int waysToMakeFair(int[] nums) {

        int totalEven = 0;
        int totalOdd = 0;

        // Calculate total even and odd index sums
        for (int i = 0; i < nums.length; i++) {

            if (i % 2 == 0) {
                totalEven += nums[i];
            } else {
                totalOdd += nums[i];
            }
        }

        int leftEven = 0;
        int leftOdd = 0;

        int ans = 0;

        for (int i = 0; i < nums.length; i++) {

            // Remove nums[i] from the right side
            if (i % 2 == 0) {
                totalEven -= nums[i];
            } else {
                totalOdd -= nums[i];
            }

            // Right side changes parity after removal
            int newEven = leftEven + totalOdd;
            int newOdd = leftOdd + totalEven;

            // Check if the resulting array is fair
            if (newEven == newOdd) {
                ans++;
            }

            // Move nums[i] into the left side
            if (i % 2 == 0) {
                leftEven += nums[i];
            } else {
                leftOdd += nums[i];
            }
        }

        return ans;
    }
}
```

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

We traverse the array twice:

```text
O(n) + O(n)
```

Therefore:

```text
O(n)
```

---

### Space Complexity

We only use a few variables:

```text
O(1)
```

No additional array or HashMap is required.

---

# Key Concepts

- Prefix Sum
- Left and Right Sums
- Array Traversal
- Parity
- Index Shifting
- Constant Space Optimization

---

# Constraints

```text
1 <= nums.length <= 10⁵
1 <= nums[i] <= 10⁴
```

---

# Learning Outcome

This problem teaches an important variation of **Prefix Sum**.

The key observation is:

> When an element is removed, all elements after it shift one position, so their even/odd indices are swapped.

Therefore:

```text
newEven = leftEven + rightOdd
newOdd  = leftOdd + rightEven
```

In the code:

```java
int newEven = leftEven + totalOdd;
int newOdd = leftOdd + totalEven;
```

The overall solution achieves:

```text
Time:  O(n)
Space: O(1)
```

This is much better than the brute-force `O(n²)` approach.