# 33. Search in Rotated Sorted Array

> **Difficulty:** Medium  
> **Topics:** Array, Binary Search

---

## Problem Statement

You are given an array `nums` that was originally sorted in ascending order with **distinct values**.

The array may have been rotated at an unknown position.

For example:

```text
Original:
[0,1,2,4,5,6,7]

After rotation:
[4,5,6,7,0,1,2]
```

Given the rotated array and a `target`, return the index of `target`.

If the target does not exist, return:

```text
-1
```

The algorithm must run in:

```text
O(log n)
```

---

## Example 1

### Input

```text
nums = [4,5,6,7,0,1,2]
target = 0
```

### Output

```text
4
```

---

## Example 2

### Input

```text
nums = [4,5,6,7,0,1,2]
target = 3
```

### Output

```text
-1
```

`3` does not exist in the array.

---

## Example 3

### Input

```text
nums = [1]
target = 0
```

### Output

```text
-1
```

---

# Approach

A normal binary search works directly on a sorted array.

However, this array may be rotated:

```text
[4,5,6,7,0,1,2]
```

The important observation is:

> **At every step, at least one half of the current search range is guaranteed to be sorted.**

For example:

```text
[4,5,6,7,0,1,2]
     ↑
    mid
```

The left half:

```text
[4,5,6,7]
```

is sorted.

The right half:

```text
[7,0,1,2]
```

is not sorted.

We determine which half is sorted and then check whether the target belongs to that half.

---

# Modified Binary Search

Maintain:

```java
left = 0;
right = nums.length - 1;
```

Calculate:

```java
int mid = left + (right - left) / 2;
```

First check:

```java
if (nums[mid] == target)
```

If true, return `mid`.

Otherwise, determine which half is sorted.

---

# Case 1: Left Half is Sorted

We check:

```java
if (nums[left] <= nums[mid])
```

This means:

```text
left → mid
```

is sorted.

For example:

```text
[4,5,6,7,0,1,2]
 ↑     ↑
left  mid
```

The left half is:

```text
[4,5,6,7]
```

which is sorted.

Now we check whether the target lies inside this sorted range:

```java
if (target >= nums[left] && target < nums[mid])
```

If yes, search the left half:

```java
right = mid - 1;
```

Otherwise, search the right half:

```java
left = mid + 1;
```

---

# Case 2: Right Half is Sorted

If:

```java
nums[left] > nums[mid]
```

then the left half contains the rotation point.

Therefore, the right half must be sorted.

For example:

```text
[6,7,0,1,2,4,5]
 ↑     ↑       ↑
left  mid    right
```

The right half:

```text
[1,2,4,5]
```

is sorted.

Now check whether the target belongs to the sorted right half:

```java
if (target > nums[mid] && target <= nums[right])
```

If yes:

```java
left = mid + 1;
```

Otherwise:

```java
right = mid - 1;
```

---

# Dry Run

### Input

```text
nums = [4,5,6,7,0,1,2]
target = 0
```

Initial:

```text
left = 0
right = 6
```

---

## Step 1

Calculate:

```text
mid = 3
```

Value:

```text
nums[3] = 7
```

Target:

```text
0
```

Not equal.

Now check:

```text
nums[left] <= nums[mid]

4 <= 7
```

True.

Therefore, the left half is sorted:

```text
[4,5,6,7]
```

Does target `0` belong here?

```text
0 >= 4 && 0 < 7
```

False.

Therefore search the right half:

```text
left = mid + 1
     = 4
```

---

## Step 2

Now:

```text
left = 4
right = 6
```

Calculate:

```text
mid = 5
```

Value:

```text
nums[5] = 1
```

Not equal to target.

Check left half:

```text
nums[left] <= nums[mid]

0 <= 1
```

True.

So:

```text
[0,1]
```

is sorted.

Does target `0` belong here?

```text
target >= nums[left]
0 >= 0
```

and:

```text
target < nums[mid]
0 < 1
```

Yes.

Therefore:

```text
right = mid - 1
     = 4
```

---

## Step 3

Now:

```text
left = 4
right = 4
```

Therefore:

```text
mid = 4
```

And:

```text
nums[4] = 0
```

Target found.

Return:

```text
4
```

---

# Algorithm

1. Set:
   ```text
   left = 0
   right = n - 1
   ```
2. While:
   ```text
   left <= right
   ```
3. Calculate `mid`.
4. If `nums[mid] == target`, return `mid`.
5. Determine which half is sorted:
   - If `nums[left] <= nums[mid]`, left half is sorted.
   - Otherwise, right half is sorted.
6. Check whether the target belongs to the sorted half.
7. Eliminate the other half.
8. If the target is not found, return `-1`.

---

# Java Solution

```java
class Solution {

    public int search(int[] nums, int target) {

        if (nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // Left half is sorted
            if (nums[left] <= nums[mid]) {

                if (target >= nums[left] &&
                    target < nums[mid]) {

                    right = mid - 1;

                } else {

                    left = mid + 1;
                }
            }

            // Right half is sorted
            else {

                if (target > nums[mid] &&
                    target <= nums[right]) {

                    left = mid + 1;

                } else {

                    right = mid - 1;
                }
            }
        }

        return -1;
    }
}
```

---

# Why Does This Work?

A rotated sorted array looks like:

```text
[4,5,6,7,0,1,2]
```

Although the entire array is not sorted, **at least one half around `mid` is always sorted**.

We use that sorted half to determine where the target could possibly exist.

For every iteration:

```text
One half is sorted
        ↓
Check if target lies in that range
        ↓
Keep that half OR discard it
        ↓
Search only half of the remaining elements
```

Therefore, we still get binary-search performance.

---

# Important Condition

### Left half sorted

```java
nums[left] <= nums[mid]
```

Then:

```java
target >= nums[left] &&
target < nums[mid]
```

means target is in the left half.

---

### Right half sorted

Otherwise:

```java
target > nums[mid] &&
target <= nums[right]
```

means target is in the right half.

---

# Edge Cases

### No rotation

```text
nums = [1,2,3,4,5]
target = 3
```

The normal binary search logic still works.

---

### Single element

```text
nums = [1]
target = 1
```

Output:

```text
0
```

---

### Target does not exist

```text
nums = [4,5,6,7,0,1,2]
target = 3
```

Output:

```text
-1
```

---

### Target at rotation point

```text
nums = [4,5,6,7,0,1,2]
target = 0
```

Output:

```text
4
```

---

# Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

Each iteration eliminates approximately half of the search space:

```text
O(log n)
```

### Space Complexity

Only a few variables are used:

```text
O(1)
```

---

# Key Concepts

- Binary Search
- Modified Binary Search
- Rotated Sorted Array
- Search Space Reduction
- Sorted Half Detection

---

# Constraints

```text
1 <= nums.length <= 5000
-10⁴ <= nums[i] <= 10⁴
```

All values are unique.

---

# Learning Outcome

The most important pattern to remember is:

```text
Find mid
   ↓
Is left half sorted?
   ↓
YES ────────────── NO
 ↓                  ↓
Check target       Right half
in left range      is sorted
 ↓                  ↓
Choose half        Check target
to search          in right range
```

The core idea is:

> **Even though the entire array is rotated, at least one half is always sorted. Use that sorted half to decide which side can contain the target.**

### Complexity

```text
Time:  O(log n)
Space: O(1)
```