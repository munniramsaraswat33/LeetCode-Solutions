# 643. Maximum Average Subarray I

> **Difficulty:** Easy  
> **Topics:** Array, Sliding Window

---

## Problem Statement

You are given an integer array `nums` consisting of `n` elements and an integer `k`.

Find a **contiguous subarray** whose length is exactly `k` and has the maximum average value.

Return the maximum average.

Answers within `10^-5` of the actual answer will be accepted.

---

## Example 1

### Input

```text
nums = [1,12,-5,-6,50,3]
k = 4
```

### Output

```text
12.75000
```

### Explanation

The subarrays of length `4` are:

```text
[1,12,-5,-6]
[12,-5,-6,50]
[-5,-6,50,3]
```

Their sums are:

```text
2
51
42
```

The maximum sum is:

```text
51
```

Therefore:

```text
51 / 4 = 12.75
```

---

## Example 2

### Input

```text
nums = [5]
k = 1
```

### Output

```text
5.00000
```

---

# Approach

Use the **Sliding Window** technique.

Since every subarray must contain exactly `k` elements, we can maintain the sum of the current window instead of calculating the sum again for every subarray.

### Main Idea

First calculate the sum of the first `k` elements.

Then slide the window one position at a time:

- Add the new element entering the window.
- Remove the element leaving the window.
- Update the maximum sum.

Finally:

```text
maximum average = maximum sum / k
```

This reduces the time complexity from `O(n*k)` to `O(n)`.

---

# Algorithm

1. Calculate the sum of the first `k` elements.
2. Store this sum as `maxSum`.
3. Start from index `k`.
4. For every new element:
   - Add `nums[i]`.
   - Remove `nums[i-k]`.
   - Update `maxSum`.
5. Return:
   ```text
   (double) maxSum / k
   ```

---

# Dry Run

Input:

```text
nums = [1,12,-5,-6,50,3]
k = 4
```

### Initial Window

First `4` elements:

```text
[1,12,-5,-6]
```

Sum:

```text
1 + 12 - 5 - 6 = 2
```

So:

```text
sum = 2
maxSum = 2
```

---

### Slide Window

Add `50` and remove `1`.

New window:

```text
[12,-5,-6,50]
```

New sum:

```text
2 + 50 - 1 = 51
```

Update:

```text
maxSum = 51
```

---

### Slide Again

Add `3` and remove `12`.

New window:

```text
[-5,-6,50,3]
```

New sum:

```text
51 + 3 - 12 = 42
```

`maxSum` remains:

```text
51
```

---

### Final Answer

```text
maxSum / k
= 51 / 4
= 12.75
```

Therefore:

```text
12.75000
```

---

# Understanding the Code

## Calculate Initial Window Sum

```java
int sum = 0;

for(int i = 0; i < k; i++){
    sum += nums[i];
}
```

This calculates the sum of the first window of size `k`.

---

## Store Maximum Sum

```java
int maxSum = sum;
```

Initially, the first window is the best window we have seen.

---

## Slide the Window

```java
for(int i = k; i < nums.length; i++){
```

Start from the first element outside the initial window.

---

## Add New Element

```java
sum += nums[i];
```

The new element enters the sliding window.

---

## Remove Old Element

```java
sum -= nums[i-k];
```

The element at position `i-k` is no longer part of the window.

Therefore, remove it from the sum.

---

## Update Maximum

```java
maxSum = Math.max(maxSum, sum);
```

Keep track of the largest window sum.

---

## Calculate Average

```java
return (double)maxSum / k;
```

The average of a window is:

```text
sum / number of elements
```

Since every window contains exactly `k` elements:

```text
maximum average = maxSum / k
```

Casting to `double` ensures that decimal division is performed.

---

# Sliding Window Pattern

The important pattern is:

```text
Create first window
       ↓
Calculate its sum
       ↓
Add new element
       ↓
Remove old element
       ↓
Update answer
       ↓
Repeat
```

Instead of recalculating every window:

```text
[1,12,-5,-6]
   ↓
[12,-5,-6,50]
```

we simply do:

```text
old sum + new element - removed element
```

This makes the solution efficient.

---

# Complexity Analysis

### Time Complexity

The first `k` elements are processed once.

Then the remaining elements are processed once.

Therefore:

```text
O(n)
```

---

### Space Complexity

Only a few variables are used.

Therefore:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public double findMaxAverage(int[] nums, int k) {

        int sum = 0;

        for(int i = 0; i < k; i++){
            sum += nums[i];
        }

        int maxSum = sum;

        for(int i = k; i < nums.length; i++){

            sum += nums[i];
            sum -= nums[i - k];

            maxSum = Math.max(maxSum, sum);
        }

        return (double) maxSum / k;
    }
}
```

---

# Key Concepts

- Array
- Sliding Window
- Fixed-Size Window
- Running Sum
- Maximum Sum
- Average Calculation

---

# Constraints

- `1 <= k <= n <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

---

# Learning Outcome

This problem demonstrates the **Fixed-Size Sliding Window** technique.

Whenever we need to find something about every contiguous subarray of a fixed size `k`, we should consider a sliding window.

The key operation is:

```java
sum += nums[i];
sum -= nums[i - k];
```

Instead of recalculating the complete sum for every window, we update it in `O(1)`.

Therefore:

```text
Time  → O(n)
Space → O(1)
```

The main pattern to remember is:

```text
Fixed Window
     ↓
Add incoming element
     ↓
Remove outgoing element
     ↓
Update answer
```