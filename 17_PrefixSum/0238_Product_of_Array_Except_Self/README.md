# 238. Product of Array Except Self

> **Difficulty:** Medium  
> **Topics:** Array, Prefix Sum, Suffix Product

---

## Problem Statement

Given an integer array `nums`, return an array `answer` such that:

```text
answer[i] = product of all elements of nums except nums[i]
```

The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer.

The solution must run in:

```text
O(n)
```

time complexity.

---

## Example 1

### Input

```text
nums = [1,2,3,4]
```

### Output

```text
[24,12,8,6]
```

### Explanation

For each index:

```text
Index 0 → 2 × 3 × 4 = 24
Index 1 → 1 × 3 × 4 = 12
Index 2 → 1 × 2 × 4 = 8
Index 3 → 1 × 2 × 3 = 6
```

Therefore:

```text
[24,12,8,6]
```

---

## Example 2

### Input

```text
nums = [-1,1,0,-3,3]
```

### Output

```text
[0,0,9,0,0]
```

### Explanation

For index `2`, the element is `0`.

The product of all other elements is:

```text
(-1) × 1 × (-3) × 3 = 9
```

Therefore:

```text
answer[2] = 9
```

---

# Approach

Use **Prefix Product and Suffix Product**.

For every index `i`, we need:

```text
Product of elements before i
×
Product of elements after i
```

Instead of calculating these products repeatedly, calculate them efficiently using two passes.

### Prefix Product

For every index, store the product of all elements to its left in `arr`.

For example:

```text
nums = [1,2,3,4]
```

Prefix products stored in `arr`:

```text
[1,1,2,6]
```

Here:

```text
arr[0] = 1
arr[1] = 1
arr[2] = 1 × 2 = 2
arr[3] = 1 × 2 × 3 = 6
```

Then calculate the suffix product while traversing from right to left.

---

# Algorithm

1. Create an array `arr` of the same size as `nums`.
2. Set:
   ```java
   arr[0] = 1;
   ```
3. Build the prefix products:
   ```text
   arr[i] = arr[i-1] × nums[i-1]
   ```
4. Initialize:
   ```java
   suffix = nums[n-1];
   ```
5. Traverse from right to left.
6. Multiply the prefix product with the current suffix product:
   ```text
   arr[i] = arr[i] × suffix
   ```
7. Update the suffix product:
   ```text
   suffix = suffix × nums[i]
   ```
8. Return `arr`.

---

# Dry Run

Input:

```text
nums = [1,2,3,4]
```

### Step 1: Prefix Products

Initialize:

```text
arr[0] = 1
```

For `i = 1`:

```text
arr[1] = arr[0] × nums[0]
       = 1 × 1
       = 1
```

For `i = 2`:

```text
arr[2] = arr[1] × nums[1]
       = 1 × 2
       = 2
```

For `i = 3`:

```text
arr[3] = arr[2] × nums[2]
       = 2 × 3
       = 6
```

So:

```text
arr = [1,1,2,6]
```

---

### Step 2: Suffix Products

Initialize:

```text
suffix = nums[3] = 4
```

For `i = 2`:

```text
arr[2] = 2 × 4
       = 8
```

Update:

```text
suffix = 4 × 3
       = 12
```

For `i = 1`:

```text
arr[1] = 1 × 12
       = 12
```

Update:

```text
suffix = 12 × 2
       = 24
```

For `i = 0`:

```text
arr[0] = 1 × 24
       = 24
```

Final result:

```text
[24,12,8,6]
```

---

# Understanding the Code

## Create Result Array

```java
int n = nums.length;
int arr[] = new int[n];
```

The result array stores the product of all elements except the current element.

---

## Initialize Prefix Product

```java
arr[0] = 1;
```

There are no elements to the left of index `0`.

Therefore, the left product is:

```text
1
```

---

## Build Prefix Products

```java
for(int i=1; i<n; i++){
    arr[i] = arr[i-1] * nums[i-1];
}
```

At every index, `arr[i]` contains the product of all elements before `i`.

For example:

```text
nums = [1,2,3,4]

arr = [1,1,2,6]
```

---

## Initialize Suffix Product

```java
int suffix = nums[n-1];
```

Start from the last element and maintain the product of elements to the right.

---

## Multiply Prefix and Suffix

```java
for(int i=n-2; i>=0; i--){
    arr[i] = arr[i] * suffix;
    suffix *= nums[i];
}
```

At each index:

```text
answer[i]
=
left product × right product
```

The left product is already stored in `arr[i]`.

The right product is maintained by `suffix`.

---

# Why Prefix and Suffix?

For any index `i`:

```text
nums = [1, 2, 3, 4]
              ↑
             index 2
```

We need:

```text
1 × 2 × 4
```

This can be separated into:

```text
Left Product  = 1 × 2
Right Product = 4
```

Therefore:

```text
answer[2] = Left Product × Right Product
           = 2 × 4
           = 8
```

This is exactly what the prefix and suffix passes calculate.

---

# Handling Zero

This approach also works correctly when the array contains zero.

For example:

```text
nums = [1,2,0,4]
```

The prefix and suffix products naturally make the answer:

```text
[0,0,8,0]
```

There is no need for special zero handling.

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

The result array requires:

```text
O(n)
```

space.

Apart from the output array, only a few variables are used:

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public int[] productExceptSelf(int[] nums) {

        int n = nums.length;

        int arr[] = new int[n];

        arr[0] = 1;

        // Prefix product
        for(int i = 1; i < n; i++){
            arr[i] = arr[i - 1] * nums[i - 1];
        }

        // Suffix product
        int suffix = nums[n - 1];

        for(int i = n - 2; i >= 0; i--){

            arr[i] = arr[i] * suffix;

            suffix *= nums[i];
        }

        return arr;
    }
}
```

---

# Key Concepts

- Array
- Prefix Product
- Suffix Product
- Two Passes
- Running Product
- Prefix and Suffix Technique

---

# Constraints

- `2 <= nums.length <= 10^5`
- `-30 <= nums[i] <= 30`
- The product of any prefix or suffix is guaranteed to fit in a 32-bit integer.
- Division cannot be used.

---

# Learning Outcome

This problem demonstrates how **Prefix and Suffix Products** can be combined to solve an array problem efficiently without using division.

The main idea is:

```text
Prefix Product
      ↓
Store left-side product
      ↓
Suffix Product
      ↓
Multiply with right-side product
      ↓
Final Answer
```

The important formulas are:

```java
arr[i] = arr[i - 1] * nums[i - 1];
```

and:

```java
arr[i] = arr[i] * suffix;
```

The solution runs in:

```text
Time  → O(n)
Space → O(n) including the output
```

with:

```text
O(1)
```

extra space apart from the result array.