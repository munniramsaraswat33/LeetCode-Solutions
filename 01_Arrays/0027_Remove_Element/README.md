# 27. Remove Element

> **Difficulty:** Easy  
> **Topics:** Array, Two Pointers

---

## Problem Statement

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` **in-place**.

Return the number of elements:

```text
k
```

that are **not equal to `val`**.

After the operation:

- The first `k` elements of `nums` must contain all elements that are not equal to `val`.
- The order of these elements does not matter.
- Elements after index `k - 1` can be ignored.
- No extra array should be used.

---

## Example 1

### Input

```text
nums = [3,2,2,3]
val = 3
```

### Output

```text
2
```

The first two elements should contain:

```text
[2,2]
```

---

## Example 2

### Input

```text
nums = [0,1,2,2,3,0,4,2]
val = 2
```

### Output

```text
5
```

The first five elements should contain:

```text
[0,1,3,0,4]
```

The order does not matter.

---

# Approach

We can solve this using a **Two Pointer / Write Pointer** technique.

We maintain:

```text
k
```

which represents the position where the next valid element should be placed.

We scan the array using:

```text
i
```

For every element:

### If `nums[i] == val`

This element needs to be removed.

We simply skip it.

### If `nums[i] != val`

This element should remain in the array.

Copy it to position `k`:

```java
nums[k] = nums[i];
```

Then increment:

```java
k++;
```

At the end:

```text
k = number of elements not equal to val
```

---

# Java Solution

```java
class Solution {
    public int removeElement(int[] nums, int val) {

        int k = 0;

        for(int i = 0; i < nums.length; i++){

            if(nums[i] != val){

                nums[k] = nums[i];
                k++;
            }
        }

        return k;
    }
}
```

---

# Dry Run

Consider:

```text
nums = [0,1,2,2,3,0,4,2]
val = 2
```

Initially:

```text
k = 0
```

---

### `i = 0`

```text
nums[i] = 0
```

`0 != 2`, so keep it.

```java
nums[k] = nums[i];
```

Array:

```text
[0,1,2,2,3,0,4,2]
 ↑
 k
```

Then:

```text
k = 1
```

---

### `i = 1`

```text
nums[i] = 1
```

Keep it.

```text
[0,1,2,2,3,0,4,2]
   ↑
   k
```

```text
k = 2
```

---

### `i = 2`

```text
nums[i] = 2
```

This equals `val`.

Skip it.

```text
k = 2
```

---

### `i = 3`

Again:

```text
nums[i] = 2
```

Skip it.

```text
k = 2
```

---

### `i = 4`

```text
nums[i] = 3
```

Keep it.

Copy to `nums[k]`:

```text
[0,1,3,2,3,0,4,2]
     ↑
     k
```

```text
k = 3
```

---

### `i = 5`

```text
nums[i] = 0
```

Keep it.

```text
[0,1,3,0,3,0,4,2]
       ↑
       k
```

```text
k = 4
```

---

### `i = 6`

```text
nums[i] = 4
```

Keep it.

```text
[0,1,3,0,4,0,4,2]
         ↑
         k
```

```text
k = 5
```

---

### `i = 7`

```text
nums[i] = 2
```

Equals `val`.

Skip it.

---

## Final Result

```text
k = 5
```

The first five elements are:

```text
[0,1,3,0,4]
```

Everything after index `4` is irrelevant.

---

# Why This Works

The important idea is:

```text
i → scans every element
k → stores the next valid element
```

Whenever we find an element that should stay:

```java
nums[k] = nums[i];
k++;
```

Therefore, after the complete traversal:

```text
nums[0 ... k-1]
```

contains only elements that are not equal to `val`.

And since `k` is incremented exactly once for every valid element:

```text
k = number of elements != val
```

---

# Visual Pattern

For:

```text
[3,2,2,3]
```

and:

```text
val = 3
```

Think of:

```text
i → scanning
k → writing
```

```text
3  2  2  3
   ↑
   k
      ↑
      i
```

When `i` finds `2`:

```text
nums[k] = nums[i]
```

The valid elements are moved toward the beginning:

```text
2  2  _  _
```

Return:

```text
k = 2
```

---

# Why We Don't Actually Delete Elements

In an array, deleting an element would normally require shifting many elements.

Instead, we overwrite the array.

For example:

```text
[3,2,2,3]
```

becomes:

```text
[2,2,2,3]
```

after copying valid values.

But only the first `k` elements matter:

```text
[2,2 | 2,3]
 ↑────↑
  valid
```

So the remaining elements don't need to be modified.

---

# Complexity Analysis

Let:

```text
n = nums.length
```

We traverse the array exactly once.

### Time Complexity

```text
O(n)
```

### Space Complexity

```text
O(1)
```

Only the pointer `k` is used.

---

# Key Pattern to Remember

This is a very important **in-place array filtering** pattern.

```java
int k = 0;

for(int i = 0; i < nums.length; i++){

    if(nums[i] != val){

        nums[k] = nums[i];
        k++;
    }
}

return k;
```

Think:

```text
i = READ pointer
k = WRITE pointer
```

### READ

```text
i
```

checks every element.

### WRITE

```text
k
```

places only the elements we want to keep.

---

# Difference From Problem 26

### Problem 26 — Remove Duplicates

Because the array is sorted:

```java
if(nums[j] != nums[i])
```

is used to detect a new unique value.

### Problem 27 — Remove Element

We simply check:

```java
if(nums[i] != val)
```

to decide whether the element should be kept.

Both use the same important pattern:

```text
Read Pointer + Write Pointer
```

---

## Key Concepts

- Arrays
- Two Pointers
- In-place Modification
- Write Pointer
- Array Filtering
- O(1) Extra Space

---

## Your Solution

Your solution is **correct and optimal**.

The key part:

```java
if(nums[i] != val){
    nums[k] = nums[i];
    k++;
}
```

efficiently moves all valid elements to the beginning of the array.

Complexity:

```text
Time  → O(n)
Space → O(1)
```

This is the optimal approach for the problem.