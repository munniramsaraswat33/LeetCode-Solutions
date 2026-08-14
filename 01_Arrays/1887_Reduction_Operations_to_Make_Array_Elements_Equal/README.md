# 1887. Reduction Operations to Make the Array Elements Equal

> **Difficulty:** Medium  
> **Topics:** Array, Sorting, Greedy

---

## Problem Statement

You are given an integer array `nums`.

The goal is to make **all elements equal**.

In one operation:

1. Find the largest value in the array.
2. Find the next largest value that is **strictly smaller** than the largest.
3. Reduce one occurrence of the largest value to that next largest value.

Return the minimum number of operations required to make all elements equal.

---

## Example 1

### Input

```text
nums = [5,1,3]
```

### Output

```text
3
```

### Explanation

Start:

```text
[5,1,3]
```

#### Operation 1

Largest:

```text
5
```

Next largest:

```text
3
```

Reduce `5 → 3`:

```text
[3,1,3]
```

#### Operation 2

Largest:

```text
3
```

Next largest:

```text
1
```

Reduce one `3 → 1`:

```text
[1,1,3]
```

#### Operation 3

Reduce the remaining `3 → 1`:

```text
[1,1,1]
```

Total:

```text
3 operations
```

---

## Example 2

### Input

```text
nums = [1,1,1]
```

### Output

```text
0
```

All elements are already equal.

---

## Example 3

### Input

```text
nums = [1,1,2,2,3]
```

### Output

```text
4
```

The distinct values are:

```text
1 < 2 < 3
```

The operations are:

```text
3 → 2
2 → 1
2 → 1
2 → 1
```

Total:

```text
4
```

---

# Approach

The important observation is that after sorting the array, we don't actually need to simulate every operation.

Consider:

```text
nums = [1,1,2,2,3]
```

After sorting:

```text
[1,1,2,2,3]
```

There are three distinct levels:

```text
1
2
3
```

---

# Key Observation

Every element at a higher level needs one operation for **each distinct smaller level below it**.

For:

```text
[1,1,2,2,3]
```

### Elements equal to `2`

There is one distinct value smaller than `2`:

```text
1
```

So every `2` requires:

```text
1 operation
```

There are two `2`s:

```text
2 × 1 = 2
```

---

### Element equal to `3`

There are two distinct values smaller than `3`:

```text
1, 2
```

Therefore `3` requires:

```text
2 operations
```

Total:

```text
2 + 2 = 4
```

---

# Counting Distinct Levels

After sorting:

```text
[1,1,2,2,3]
```

we scan from left to right.

Whenever:

```java
nums[i] != nums[i - 1]
```

we have found a new distinct value.

So:

```java
count++;
```

The variable `count` represents:

> The number of distinct values smaller than the current value.

---

# Why `ans += count`?

For every element at the current level, we need `count` operations.

For example:

```text
[1,1,2,2,3]
```

### Index 1

```text
nums[1] = 1
```

No new value.

```text
count = 0
ans += 0
```

---

### Index 2

```text
nums[2] = 2
```

New distinct value.

```text
count = 1
```

Therefore:

```text
ans += 1
```

---

### Index 3

```text
nums[3] = 2
```

Same value.

```text
count = 1
```

Therefore:

```text
ans += 1
```

---

### Index 4

```text
nums[4] = 3
```

New distinct value.

```text
count = 2
```

Therefore:

```text
ans += 2
```

Total:

```text
0 + 0 + 1 + 1 + 2 = 4
```

---

# Dry Run

### Input

```text
nums = [5,1,3]
```

After sorting:

```text
[1,3,5]
```

Initial:

```text
count = 0
ans = 0
```

---

### `i = 1`

```text
nums[1] = 3
nums[0] = 1
```

They are different.

So:

```text
count = 1
```

Then:

```text
ans += count
     = 0 + 1
     = 1
```

---

### `i = 2`

```text
nums[2] = 5
nums[1] = 3
```

They are different.

So:

```text
count = 2
```

Then:

```text
ans += count
     = 1 + 2
     = 3
```

Final answer:

```text
3
```

---

# Why We Don't Simulate the Operations

A direct simulation could repeatedly:

```text
find maximum
find next maximum
change maximum
```

This would be inefficient.

Instead, sorting gives us the order of all values:

```text
smallest → largest
```

Once we know how many distinct levels exist below each element, we can calculate the total number of operations directly.

This is a **Greedy + Counting** observation.

---

# Algorithm

1. Sort the array.
2. Initialize:
   ```text
   count = 0
   ans = 0
   ```
3. Traverse the sorted array starting from index `1`.
4. If the current value differs from the previous value:
   ```text
   count++
   ```
5. Add `count` to `ans`.
6. Return `ans`.

---

# Java Solution

```java
class Solution {

    public int reductionOperations(int[] nums) {

        Arrays.sort(nums);

        int count = 0;
        int ans = 0;

        for (int i = 1; i < nums.length; i++) {

            // Found a new distinct value
            if (nums[i] != nums[i - 1]) {
                count++;
            }

            // Current element needs 'count' reductions
            ans += count;
        }

        return ans;
    }
}
```

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

Sorting takes:

```text
O(n log n)
```

The traversal takes:

```text
O(n)
```

Therefore:

```text
O(n log n)
```

---

### Space Complexity

Apart from the sorting implementation:

```text
O(1)
```

extra space is used by the algorithm.

---

# Key Concepts

- Sorting
- Greedy Approach
- Counting Distinct Values
- Frequency / Levels
- Array Traversal

---

# Constraints

```text
1 <= nums.length <= 5 * 10⁴
1 <= nums[i] <= 5 * 10⁴
```

---

# Learning Outcome

The main idea of this problem is:

> **Every element needs one operation for every distinct value smaller than it.**

After sorting:

```text
[1,1,2,2,3]
```

we can think of the values as levels:

```text
Level 0 → 1
Level 1 → 2
Level 2 → 3
```

Therefore:

```text
1 → 0 operations
2 → 1 operation
2 → 1 operation
3 → 2 operations
```

Total:

```text
0 + 0 + 1 + 1 + 2 = 4
```

The key code is:

```java
if (nums[i] != nums[i - 1]) {
    count++;
}

ans += count;
```

### Complexity

```text
Time:  O(n log n)
Space: O(1)  // excluding sorting/output
```