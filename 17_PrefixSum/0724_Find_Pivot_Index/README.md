# 724. Find Pivot Index

> **Difficulty:** Easy  
> **Topics:** Array, Prefix Sum

---

## Problem Statement

Given an integer array `nums`, find the **pivot index** of the array.

The pivot index is the index where the sum of all elements to the **left** is equal to the sum of all elements to the **right**.

The leftmost index should be returned if there are multiple pivot indices.

For an index `i`:

```text
Left Sum = nums[0] + nums[1] + ... + nums[i-1]
```

and

```text
Right Sum = nums[i+1] + nums[i+2] + ... + nums[n-1]
```

The pivot index is valid when:

```text
Left Sum == Right Sum
```

If no pivot index exists, return:

```text
-1
```

---

## Example 1

### Input

```text
nums = [1,7,3,6,5,6]
```

### Output

```text
3
```

### Explanation

At index `3`:

```text
Left Sum = 1 + 7 + 3 = 11
```

```text
Right Sum = 5 + 6 = 11
```

Since both sums are equal:

```text
Pivot Index = 3
```

---

## Example 2

### Input

```text
nums = [1,2,3]
```

### Output

```text
-1
```

### Explanation

There is no index where the left sum and right sum are equal.

---

## Example 3

### Input

```text
nums = [2,1,-1]
```

### Output

```text
0
```

### Explanation

At index `0`:

```text
Left Sum = 0
```

```text
Right Sum = 1 + (-1) = 0
```

Therefore:

```text
Pivot Index = 0
```

---

# Approach

Use the **Prefix Sum** concept.

Instead of calculating the left and right sums separately for every index, first calculate the total sum of the entire array.

Then maintain a variable:

```text
leftSum
```

which represents the sum of elements before the current index.

For the current index `i`:

```text
rightSum = total - leftSum - nums[i]
```

Now compare:

```text
leftSum == rightSum
```

If they are equal, `i` is the pivot index.

After checking the current index, add `nums[i]` to `leftSum` before moving to the next index.

---

# Algorithm

1. Calculate the total sum of all elements.
2. Initialize:
   ```text
   leftSum = 0
   ```
3. Traverse the array from left to right.
4. For every index `i`, calculate:
   ```text
   rightSum = total - leftSum - nums[i]
   ```
5. If:
   ```text
   leftSum == rightSum
   ```
   return `i`.
6. Otherwise, update:
   ```text
   leftSum += nums[i]
   ```
7. If no pivot index is found, return `-1`.

---

# Dry Run

Input:

```text
nums = [1,7,3,6,5,6]
```

### Step 1: Calculate Total Sum

```text
total = 1 + 7 + 3 + 6 + 5 + 6
      = 28
```

Initially:

```text
leftSum = 0
```

### Step 2: Check Each Index

| Index | `nums[i]` | Left Sum | Right Sum | Result |
|------:|----------:|---------:|----------:|--------|
| 0 | 1 | 0 | 27 | Not Pivot |
| 1 | 7 | 1 | 20 | Not Pivot |
| 2 | 3 | 8 | 17 | Not Pivot |
| 3 | 6 | 11 | 11 | **Pivot** |

At index `3`:

```text
leftSum = 11
rightSum = 11
```

Therefore:

```text
Answer = 3
```

---

# Understanding the Code

## Calculate Total Sum

```java
int total = 0;

for(int num : nums){
    total += num;
}
```

This calculates the sum of the entire array.

For:

```text
[1,7,3,6,5,6]
```

we get:

```text
total = 28
```

---

## Initialize Left Sum

```java
int leftSum = 0;
```

Before index `0`, there are no elements on the left.

Therefore:

```text
leftSum = 0
```

---

## Calculate Right Sum

```java
int rightSum = total - leftSum - nums[i];
```

The total sum contains:

```text
Left elements + Current element + Right elements
```

Therefore:

```text
Right Sum = Total Sum - Left Sum - Current Element
```

---

## Check Pivot Condition

```java
if(leftSum == rightSum){
    return i;
}
```

If both sums are equal, the current index is the pivot index.

Since we traverse from left to right, the first valid index is automatically the **leftmost pivot index**.

---

## Update Left Sum

```java
leftSum += nums[i];
```

After checking index `i`, the current element becomes part of the left side for the next index.

---

# Why This Approach?

A brute-force approach would calculate the left and right sums separately for every index.

That can take:

```text
O(n²)
```

time.

Using the total sum, the right sum can be calculated in `O(1)`:

```text
rightSum = total - leftSum - nums[i]
```

Therefore, the complete solution only requires one additional traversal.

---

# Complexity Analysis

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

Only a few variables are used:

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public int pivotIndex(int[] nums) {

        int total = 0;

        for(int num : nums){
            total += num;
        }

        int leftSum = 0;

        for(int i = 0; i < nums.length; i++){

            int rightSum = total - leftSum - nums[i];

            if(leftSum == rightSum){
                return i;
            }

            leftSum += nums[i];
        }

        return -1;
    }
}
```

---

# Key Concepts

- Array
- Prefix Sum
- Running Sum
- Left Sum
- Right Sum
- Linear Traversal

---

# Constraints

- `1 <= nums.length <= 10^4`
- `-1000 <= nums[i] <= 1000`

---

# Learning Outcome

This problem demonstrates how the **Prefix Sum / Running Sum** technique can avoid repeated calculations.

The important formula is:

```text
rightSum = total - leftSum - nums[i]
```

The main pattern is:

```text
Calculate Total Sum
       ↓
Maintain Left Sum
       ↓
Calculate Right Sum
       ↓
Compare Left and Right
       ↓
Move to Next Index
```

The solution runs in:

```text
Time  → O(n)
Space → O(1)
```