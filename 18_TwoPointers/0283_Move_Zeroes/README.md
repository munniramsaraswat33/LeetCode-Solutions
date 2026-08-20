# 283. Move Zeroes

> **Difficulty:** Easy  
> **Topics:** Array, Two Pointers

---

## Problem Statement

Given an integer array `nums`, move all `0`'s to the **end** of the array while maintaining the **relative order** of the non-zero elements.

The operation must be performed **in-place**, meaning we should not create a copy of the array.

---

## Example 1

### Input

```text
nums = [0,1,0,3,12]
```

### Output

```text
[1,3,12,0,0]
```

### Explanation

The non-zero elements are:

```text
1, 3, 12
```

Their relative order must remain the same.

After moving all zeroes to the end:

```text
[1,3,12,0,0]
```

---

## Example 2

### Input

```text
nums = [0]
```

### Output

```text
[0]
```

There is only one element and it is already a zero.

---

# Approach

Use a **Two Pointer / Position Tracking** approach.

We maintain a variable:

```java
count
```

which represents the number of zeroes encountered so far.

For every element:

- If it is `0`, increase `count`.
- If it is non-zero, move it `count` positions to the left.

The expression:

```java
nums[i-count] = nums[i];
```

places every non-zero element in its correct position while maintaining its original relative order.

After all non-zero elements have been moved to the front, the remaining positions are filled with zeroes.

---

# Algorithm

1. Initialize:

```text
count = 0
```

2. Traverse the array.
3. If `nums[i] == 0`:
   - Increment `count`.
4. Otherwise:
   - Move the current non-zero element to:
     ```text
     i - count
     ```
5. After the traversal, `count` represents the total number of zeroes.
6. Fill the last `count` positions with `0`.
7. The array is now modified in-place.

---

# Dry Run

Input:

```text
nums = [0,1,0,3,12]
```

Initially:

```text
count = 0
```

### Index 0

```text
nums[0] = 0
```

So:

```text
count = 1
```

Array:

```text
[0,1,0,3,12]
```

---

### Index 1

```text
nums[1] = 1
```

Non-zero.

Place it at:

```text
i - count
= 1 - 1
= 0
```

So:

```text
nums[0] = 1
```

Array becomes:

```text
[1,1,0,3,12]
```

---

### Index 2

```text
nums[2] = 0
```

Increase:

```text
count = 2
```

---

### Index 3

```text
nums[3] = 3
```

Place it at:

```text
3 - 2 = 1
```

So:

```text
nums[1] = 3
```

Array:

```text
[1,3,0,3,12]
```

---

### Index 4

```text
nums[4] = 12
```

Place it at:

```text
4 - 2 = 2
```

So:

```text
nums[2] = 12
```

Array:

```text
[1,3,12,3,12]
```

At this point, all non-zero elements are correctly placed at the beginning.

We have:

```text
count = 2
```

meaning there are two zeroes.

---

### Fill Remaining Positions

The first zero should be placed at:

```text
nums.length - count
= 5 - 2
= 3
```

So:

```text
nums[3] = 0
```

Then:

```text
count = 1
```

Next:

```text
nums[4] = 0
```

Final array:

```text
[1,3,12,0,0]
```

---

# Understanding the Code

## Count Zeroes

```java
int count = 0;
```

`count` stores how many zeroes have appeared before the current index.

---

## Traverse the Array

```java
for(int i=0; i<nums.length; i++){
```

We inspect every element once.

---

## If Current Element Is Zero

```java
if(nums[i] == 0){
    count++;
}
```

We don't move the zero immediately.

We simply remember that one zero has been found.

---

## If Current Element Is Non-Zero

```java
else{
    nums[i-count] = nums[i];
}
```

If `count` zeroes have appeared before this element, then the element should move `count` positions to the left.

For example:

```text
[0,0,5]
```

For `5`:

```text
i = 2
count = 2
```

So:

```text
i - count = 2 - 2 = 0
```

Therefore:

```text
nums[0] = 5
```

---

# Why `i - count`?

Suppose:

```text
nums = [0,1,0,3,12]
```

Before `3`, two positions contain zeroes:

```text
[0,1,0,3,12]
 ↑   ↑
 2 zeroes
```

So `3` needs to move two positions to the left:

```text
3 - 2 = 1
```

Therefore:

```java
nums[1] = 3;
```

The same logic works for every non-zero element.

---

# Fill Zeroes at the End

After moving all non-zero elements, we know the number of zeroes from `count`.

```java
while(count != 0){
    nums[nums.length-count] = 0;
    count--;
}
```

The zeroes are placed starting from:

```text
nums.length - count
```

until the end.

---

# Why Relative Order Is Maintained?

We process the original array from **left to right**.

Every non-zero element is copied to the next available position in the same order.

For:

```text
[0,1,0,3,12]
```

the non-zero elements are processed as:

```text
1 → 3 → 12
```

So they remain:

```text
[1,3,12]
```

Their relative order does not change.

---

# Complexity Analysis

### Time Complexity

We traverse the array once and then fill the zeroes.

```text
O(n)
```

---

### Space Complexity

No extra array or data structure is used.

```text
O(1)
```

The operation is performed in-place.

---

# Java Solution

```java
class Solution {

    public void moveZeroes(int[] nums) {

        int count = 0;

        for(int i = 0; i < nums.length; i++){

            if(nums[i] == 0){
                count++;
            }
            else{
                nums[i - count] = nums[i];
            }
        }

        while(count != 0){

            nums[nums.length - count] = 0;
            count--;
        }
    }
}
```

---

# Key Concepts

- Array
- Two Pointers
- In-place Modification
- Position Tracking
- Maintaining Relative Order
- Zero Handling

---

# Constraints

- `1 <= nums.length <= 10^4`
- `-2^31 <= nums[i] <= 2^31 - 1`

---

# Follow Up

**Could you minimize the total number of operations done?**

The solution above already avoids unnecessary swapping of every zero and directly shifts each non-zero element to its correct position.

---

# Learning Outcome

This problem demonstrates how to rearrange an array **in-place** while preserving the relative order of selected elements.

The main idea is to keep track of how many zeroes have been encountered:

```java
count
```

and move every non-zero element to:

```java
i - count
```

After all non-zero elements are placed, fill the remaining positions with zeroes.

The important pattern is:

```text
Count unwanted elements
        ↓
Move wanted elements forward
        ↓
Fill remaining positions
```

Complexity:

```text
Time  → O(n)
Space → O(1)
```