# 26. Remove Duplicates from Sorted Array

> **Difficulty:** Easy  
> **Topics:** Array, Two Pointers

---

## Problem Statement

Given an integer array `nums` sorted in **non-decreasing order**, remove the duplicates **in-place** so that every unique element appears only once.

Return the number of unique elements:

```text
k
```

After removing duplicates:

- The first `k` elements of `nums` must contain the unique values.
- The unique values must remain sorted.
- Elements after index `k - 1` can be ignored.
- No extra array should be used.

---

## Example 1

### Input

```text
nums = [1,1,2]
```

### Output

```text
k = 2
nums = [1,2,_]
```

The unique elements are:

```text
[1,2]
```

---

## Example 2

### Input

```text
nums = [0,0,1,1,1,2,2,3,3,4]
```

### Output

```text
k = 5
nums = [0,1,2,3,4,_,_,_,_,_]
```

The unique elements are:

```text
[0,1,2,3,4]
```

---

# Approach

Because the array is already **sorted**, all duplicate elements are next to each other.

For example:

```text
[0,0,1,1,1,2,2,3,3,4]
```

Duplicates appear consecutively.

Therefore, we can use the **Two Pointer** technique.

We use two pointers:

```text
i → position of the last unique element
j → scans the array
```

Initially:

```text
i = 0
j = 1
```

The first element is always unique.

---

# How the Two Pointers Work

We compare:

```text
nums[j]
```

with:

```text
nums[i]
```

### Case 1: Duplicate

If:

```java
nums[j] == nums[i]
```

then `nums[j]` is a duplicate.

We don't need to do anything.

Just move:

```text
j++
```

---

### Case 2: New Unique Element

If:

```java
nums[j] != nums[i]
```

we found a new unique value.

Move `i` forward:

```java
i++;
```

Then copy the new value:

```java
nums[i] = nums[j];
```

---

# Java Solution

```java
class Solution {
    public int removeDuplicates(int[] nums) {

        if(nums.length == 0){
            return 0;
        }

        int i = 0;

        for(int j = 1; j < nums.length; j++){

            if(nums[j] != nums[i]){

                i++;
                nums[i] = nums[j];
            }
        }

        return i + 1;
    }
}
```

---

# Dry Run

Consider:

```text
nums = [0,0,1,1,1,2,2,3,3,4]
```

Initially:

```text
i = 0
j = 1
```

Array:

```text
[0,0,1,1,1,2,2,3,3,4]
 ↑ ↑
 i j
```

---

### Step 1

```text
nums[j] = 0
nums[i] = 0
```

They are equal.

Duplicate → ignore.

```text
j++
```

---

### Step 2

```text
nums[j] = 1
nums[i] = 0
```

Different → new unique element.

Move `i`:

```text
i++
```

Then:

```text
nums[i] = nums[j]
```

Array becomes:

```text
[0,1,1,1,1,2,2,3,3,4]
```

---

### Step 3

Next value:

```text
1
```

Current unique value:

```text
1
```

Duplicate → ignore.

---

### Step 4

Next value:

```text
1
```

Duplicate → ignore.

---

### Step 5

Next value:

```text
2
```

Different from current unique value `1`.

Move `i` and copy:

```text
[0,1,2,1,1,2,2,3,3,4]
```

---

Continue the same process.

Eventually:

```text
[0,1,2,3,4,_,_,_,_,_]
```

and:

```text
i = 4
```

Therefore:

```java
return i + 1;
```

returns:

```text
5
```

---

# Why This Works

Since the array is sorted:

```text
1 1 1 2 2 3 3 4
```

if the current element is different from the last unique element, it must be a **new unique value**.

We overwrite the beginning of the array with these unique values.

The `i` pointer represents:

```text
index of the latest unique element
```

while `j` represents:

```text
current scanning position
```

So after the complete traversal:

```text
nums[0 ... i]
```

contains exactly the unique elements.

The number of unique elements is:

```text
i + 1
```

---

# Important Observation

We are **not actually deleting elements** from the array.

Instead, we overwrite duplicate positions.

For example:

```text
[1,1,2,2,3]
```

becomes:

```text
[1,2,3,2,3]
```

Only the first `k` elements matter:

```text
[1,2,3]
 ↑─────↑
 valid
```

Everything after `k - 1` can be ignored.

---

# Complexity Analysis

Let:

```text
n = nums.length
```

We scan the array only once.

### Time Complexity

```text
O(n)
```

### Space Complexity

```text
O(1)
```

We modify the array in-place and use only two integer pointers.

---

# Key Pattern to Remember

For a **sorted array** where duplicates need to be removed:

```text
Two Pointers
```

Pattern:

```text
i = 0

for j = 1 → n-1:

    if nums[j] != nums[i]:

        i++
        nums[i] = nums[j]
```

Finally:

```text
return i + 1
```

---

## Visual Pattern

```text
i
↓
[unique | unique | unique | duplicates...]
                         ↑
                         j
```

`i` builds the unique portion.

`j` scans the original array.

---

## Key Concepts

- Sorted Array
- Two Pointers
- In-place Modification
- Duplicate Removal
- O(1) Extra Space

---

## Your Solution

Your solution is **correct and optimal**.

The important part is:

```java
if(nums[j] != nums[i]){
    i++;
    nums[i] = nums[j];
}
```

This keeps only the unique values at the beginning of the array.

Your complexity is:

```text
Time  → O(n)
Space → O(1)
```

which is optimal for this problem.