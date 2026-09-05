# 334. Increasing Triplet Subsequence

**LeetCode Problem:** [334. Increasing Triplet Subsequence](https://leetcode.com/problems/increasing-triplet-subsequence/)

**Difficulty:** Medium

**Primary Topic:** Arrays

**Pattern:** Greedy

---

## Problem Statement

Given an integer array `nums`, return `true` if there exists a triplet of indices:

```text
i < j < k
```

such that:

```text
nums[i] < nums[j] < nums[k]
```

In other words, we need to determine whether the array contains an **increasing subsequence of length 3**.

The three elements do not need to be adjacent.

If such a triplet exists, return:

```text
true
```

Otherwise, return:

```text
false
```

---

## Example 1

### Input

```text
nums = [1, 2, 3, 4, 5]
```

### Output

```text
true
```

### Explanation

We can choose:

```text
1 < 2 < 3
```

Therefore, an increasing triplet exists.

---

## Example 2

### Input

```text
nums = [5, 4, 3, 2, 1]
```

### Output

```text
false
```

### Explanation

The array is decreasing, so there cannot be three elements satisfying:

```text
nums[i] < nums[j] < nums[k]
```

---

## Example 3

### Input

```text
nums = [2, 1, 5, 0, 4, 6]
```

### Output

```text
true
```

### Explanation

One possible increasing triplet is:

```text
0 < 4 < 6
```

The elements occur in the correct order, so the answer is `true`.

---

# Approach

We maintain two values:

```text
first
second
```

They represent the smallest possible values we have found for the first and second elements of an increasing subsequence.

Initially:

```java
first = Integer.MAX_VALUE;
second = Integer.MAX_VALUE;
```

For every number in the array, we consider three cases.

### Case 1: Current number can be the first element

If:

```java
first >= nums[i]
```

we update:

```java
first = nums[i];
```

We always want `first` to be as small as possible.

---

### Case 2: Current number can be the second element

If the current number is greater than `first`, but can improve `second`:

```java
second >= nums[i]
```

we update:

```java
second = nums[i];
```

Now we have:

```text
first < second
```

---

### Case 3: Current number is greater than both

If:

```text
first < second < nums[i]
```

then we have found an increasing triplet.

Therefore:

```java
return true;
```

If we finish the entire array without finding such a number, return:

```java
false;
```

---

# Intuition

The main idea is to keep the smallest possible candidates for the first two elements.

Suppose:

```text
nums = [2, 1, 5, 0, 4, 6]
```

We process the array from left to right.

Initially:

```text
first = ∞
second = ∞
```

### Process `2`

`2` is smaller than `first`.

```text
first = 2
second = ∞
```

### Process `1`

`1` is smaller than `first`.

```text
first = 1
second = ∞
```

This is useful because a smaller `first` gives us a better chance of finding a larger second and third element later.

### Process `5`

`5 > first`, so it can become `second`.

```text
first = 1
second = 5
```

### Process `0`

`0` is smaller than `first`.

```text
first = 0
second = 5
```

### Process `4`

`4 > first` and `4 < second`.

So:

```text
first = 0
second = 4
```

### Process `6`

Now:

```text
first = 0
second = 4
6 > 4
```

Therefore:

```text
0 < 4 < 6
```

We found an increasing triplet.

Return:

```text
true
```

---

# Why Do We Keep the Smallest `first` and `second`?

Suppose we have:

```text
first = 2
second = 8
```

and later find:

```text
5
```

We can replace `second`:

```text
first = 2
second = 5
```

This is better because we now only need to find a number greater than `5` instead of greater than `8`.

Similarly, if we find a smaller `first`, we replace it.

For example:

```text
first = 5
```

and then we find:

```text
2
```

We update:

```text
first = 2
```

A smaller `first` makes it easier to form:

```text
first < second < third
```

---

# Algorithm

1. Initialize:
   ```java
   first = Integer.MAX_VALUE;
   second = Integer.MAX_VALUE;
   ```
2. Traverse every element of `nums`.
3. If:
   ```java
   first >= nums[i]
   ```
   update:
   ```java
   first = nums[i];
   ```
4. Otherwise, if:
   ```java
   second >= nums[i]
   ```
   update:
   ```java
   second = nums[i];
   ```
5. Otherwise, the current number is greater than both `first` and `second`.
6. Therefore, an increasing triplet exists and we return `true`.
7. If the loop finishes, return `false`.

---

# Dry Run

Consider:

```text
nums = [2, 1, 5, 0, 4, 6]
```

Initial values:

```text
first = ∞
second = ∞
```

| Current | `first` | `second` | Action |
|---:|---:|---:|---|
| 2 | 2 | ∞ | Update `first` |
| 1 | 1 | ∞ | Update `first` |
| 5 | 1 | 5 | Update `second` |
| 0 | 0 | 5 | Update `first` |
| 4 | 0 | 4 | Update `second` |
| 6 | 0 | 4 | `6 > 4`, found triplet |

At the last element:

```text
first = 0
second = 4
nums[i] = 6
```

Therefore:

```text
0 < 4 < 6
```

So:

```text
return true;
```

---

# Another Dry Run

Consider:

```text
nums = [5, 4, 3, 2, 1]
```

Initial:

```text
first = ∞
second = ∞
```

### `5`

```text
first = 5
```

### `4`

```text
first = 4
```

### `3`

```text
first = 3
```

### `2`

```text
first = 2
```

### `1`

```text
first = 1
```

We never get a value that is greater than `first` and `second` in the required way.

Therefore:

```text
return false;
```

---

# Java Solution

```java
class Solution {

    public boolean increasingTriplet(int[] nums) {

        int first = Integer.MAX_VALUE;

        int second = Integer.MAX_VALUE;

        for(int i = 0; i < nums.length; i++){

            if(first >= nums[i]){

                first = nums[i];

            }

            else if(second >= nums[i]){

                second = nums[i];

            }

            else{

                return true;

            }

        }

        return false;
    }
}
```

---

# Code Explanation

## 1. Initialize `first`

```java
int first = Integer.MAX_VALUE;
```

`first` stores the smallest possible first element of the increasing subsequence.

We initialize it to the largest possible integer so that the first number we encounter can replace it.

---

## 2. Initialize `second`

```java
int second = Integer.MAX_VALUE;
```

`second` stores the smallest possible second element that is greater than `first`.

Initially, we do not have a second element, so we use `Integer.MAX_VALUE`.

---

## 3. Traverse the Array

```java
for(int i = 0; i < nums.length; i++){
```

We process each number from left to right.

This is important because a subsequence must maintain:

```text
i < j < k
```

---

## 4. Update `first`

```java
if(first >= nums[i]){
    first = nums[i];
}
```

If the current number is smaller than or equal to `first`, we make it the new `first`.

For example:

```text
first = 5
nums[i] = 2
```

We update:

```text
first = 2
```

A smaller first value is better.

---

## 5. Update `second`

```java
else if(second >= nums[i]){
    second = nums[i];
}
```

This condition is reached only when:

```text
nums[i] > first
```

So the current value can potentially become the second element.

We store the smallest possible such value.

---

## 6. Find the Third Element

```java
else{
    return true;
}
```

If neither of the previous conditions is true, then:

```text
nums[i] > first
```

and:

```text
nums[i] > second
```

Since `second` was created after `first`, we have:

```text
first < second < nums[i]
```

Therefore, an increasing triplet exists.

---

## 7. Return False

```java
return false;
```

If no third element is found after processing the entire array, then no increasing triplet exists.

---

# Important Observation

The algorithm does **not** store the actual triplet.

It only stores:

```text
first
second
```

This is enough because we only need to know whether some third element exists.

For example:

```text
first = 2
second = 4
```

If we later encounter:

```text
7
```

we immediately know:

```text
2 < 4 < 7
```

So the triplet exists.

---

# Why Does Updating `first` Not Break the Answer?

Consider:

```text
nums = [5, 1, 2, 3]
```

Processing:

```text
5 → first = 5
1 → first = 1
2 → second = 2
3 → third found
```

We get:

```text
1 < 2 < 3
```

The smaller `first` gives us a better candidate.

The algorithm only needs the existence of a valid increasing sequence, not a fixed choice of the earliest possible first element.

---

# Greedy Idea

This is a **greedy** approach.

At every step, we try to keep:

```text
first = smallest possible first value
second = smallest possible second value
```

Why?

Because smaller values give us more opportunities to find a third value.

For example:

```text
first = 2
second = 6
```

is better than:

```text
first = 2
second = 10
```

because more future numbers can be greater than `6` than `10`.

Therefore, we greedily minimize the first two values.

---

# Complexity Analysis

Let `n` be the length of `nums`.

## Time Complexity

We traverse the array exactly once.

```text
O(n)
```

---

## Space Complexity

We only use two variables:

```text
first
second
```

Therefore:

```text
O(1)
```

---

# Brute Force Approach

A straightforward approach would check every possible triplet:

```text
i < j < k
```

and test:

```text
nums[i] < nums[j] < nums[k]
```

This would require three nested loops:

```text
O(n³)
```

which is inefficient for large arrays.

---

# Better Approach

We can improve the solution using prefix information, but the optimal solution uses the greedy two-variable technique.

Instead of storing many values, we maintain only:

```text
first
second
```

This gives:

```text
Time:  O(n)
Space: O(1)
```

---

# Key Concepts / Patterns

## 1. Greedy

Always try to keep the smallest possible values for the first two positions.

```text
Smallest first
      ↓
Smallest second
      ↓
Easier to find third
```

---

## 2. Subsequence

The three elements do not have to be adjacent.

For:

```text
[2, 10, 1, 3, 5]
```

we can choose:

```text
1, 3, 5
```

because their indices are increasing.

---

## 3. One-Pass Array Processing

The entire array is processed only once:

```text
left → right
```

This results in:

```text
O(n)
```

time complexity.

---

## 4. Constant Extra Space

Only two variables are maintained:

```java
int first;
int second;
```

Therefore, the algorithm uses:

```text
O(1)
```

extra space.

---

# Common Mistakes

## Mistake 1: Using `>` Instead of `>=`

The code uses:

```java
if(first >= nums[i])
```

and:

```java
else if(second >= nums[i])
```

This allows equal values to replace the current candidate.

For an increasing triplet, the final condition must still be strictly increasing:

```text
first < second < third
```

---

## Mistake 2: Thinking the Elements Must Be Adjacent

They do not need to be adjacent.

For example:

```text
[1, 100, 2, 200, 3]
```

contains:

```text
1 < 2 < 3
```

even though the elements are not consecutive.

---

## Mistake 3: Using Sorting

Sorting would destroy the original index order.

The problem requires:

```text
i < j < k
```

so we must process the original array order.

---

## Mistake 4: Returning True When Only Two Values Exist

Having:

```text
first < second
```

is not enough.

We need a third value:

```text
first < second < third
```

The `else` block is reached only when such a third value is found.

---

# Learning Outcome

After solving this problem, you should understand:

- How to detect an increasing subsequence of length 3.
- How a greedy strategy can avoid storing the entire subsequence.
- Why maintaining the smallest `first` and `second` values is useful.
- How to solve an array problem in one pass.
- How to achieve `O(n)` time and `O(1)` extra space.

---

# Summary

We maintain two variables:

```text
first
second
```

For every number:

```text
If number <= first:
    update first

Else if number <= second:
    update second

Else:
    increasing triplet exists
```

The important invariant is:

```text
first < second
```

When we find a number greater than `second`, we have:

```text
first < second < third
```

and therefore an increasing triplet exists.

### Final Complexity

```text
Time:  O(n)
Space: O(1)
```

**Primary Pattern: Greedy + One-Pass Array**