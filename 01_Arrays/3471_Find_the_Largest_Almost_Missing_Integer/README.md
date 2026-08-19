# 3471. Find the Largest Almost Missing Integer

> **Difficulty:** Easy  
> **Topics:** Array, Subarray, Sliding Window, Brute Force

---

## Problem Statement

You are given an integer array `nums` and an integer `k`.

An integer `x` is called **almost missing** if `x` appears in **exactly one subarray of size `k`**.

Return the **largest almost missing integer**.

If no such integer exists, return:

```text
-1
```

---

## Example 1

### Input

```text
nums = [3,9,2,1,7]
k = 3
```

Subarrays of size `3` are:

```text
[3,9,2]
[9,2,1]
[2,1,7]
```

Now check each number:

```text
3 → appears in 1 subarray
9 → appears in 2 subarrays
2 → appears in 3 subarrays
1 → appears in 2 subarrays
7 → appears in 1 subarray
```

Almost missing integers:

```text
3, 7
```

Largest:

```text
7
```

### Output

```text
7
```

---

## Example 2

### Input

```text
nums = [3,9,7,2,1,7]
k = 4
```

Subarrays:

```text
[3,9,7,2]
[9,7,2,1]
[7,2,1,7]
```

Frequency across the subarrays:

```text
3 → 1 subarray
9 → 2 subarrays
7 → 3 subarrays
2 → 3 subarrays
1 → 2 subarrays
```

Only `3` appears in exactly one subarray.

### Output

```text
3
```

---

## Example 3

### Input

```text
nums = [0,0]
k = 1
```

Subarrays:

```text
[0]
[0]
```

`0` appears in both subarrays.

Therefore, no almost missing integer exists.

### Output

```text
-1
```

---

# Approach

We need to find the **largest number** that appears in exactly one subarray of size `k`.

Because:

```text
nums.length <= 50
```

we can use a straightforward **brute-force** approach.

For every number in `nums`:

1. Check every possible subarray of size `k`.
2. Check whether the current number exists in that subarray.
3. If it exists, increase its subarray count.
4. After checking all subarrays:
   - If count is exactly `1`, it is an almost missing integer.
   - Update the answer using `Math.max()`.

---

# Java Solution

```java
class Solution {

    public int largestInteger(int[] nums, int k) {

        int ans = -1;

        for(int num : nums){

            int count = 0;

            for(int i = 0; i <= nums.length - k; i++){

                boolean found = false;

                for(int j = i; j < i + k; j++){

                    if(nums[j] == num){
                        found = true;
                        break;
                    }
                }

                if(found){
                    count++;
                }
            }

            if(count == 1){
                ans = Math.max(ans, num);
            }
        }

        return ans;
    }
}
```

---

# Code Explanation

## 1. Answer variable

```java
int ans = -1;
```

Initially assume that no almost missing integer exists.

If we find one, we update `ans`.

---

## 2. Try every number

```java
for(int num : nums)
```

We take every element as a candidate.

For example:

```text
nums = [3,9,2,1,7]
```

The candidates are:

```text
3
9
2
1
7
```

---

## 3. Count how many subarrays contain the number

```java
int count = 0;
```

For each candidate, we count the number of size-`k` subarrays in which it appears.

---

## 4. Generate every subarray of size `k`

```java
for(int i = 0; i <= nums.length - k; i++)
```

`i` represents the starting index of the subarray.

For:

```text
nums = [3,9,2,1,7]
k = 3
```

we get:

```text
i = 0 → [3,9,2]
i = 1 → [9,2,1]
i = 2 → [2,1,7]
```

Why:

```java
i <= nums.length - k
```

Because the last possible starting position is:

```text
n - k
```

---

## 5. Check elements inside the subarray

```java
boolean found = false;

for(int j = i; j < i + k; j++){
```

We scan the current subarray.

For example:

```text
[3,9,2]
```

If:

```java
num = 3
```

then we find it and set:

```java
found = true;
```

---

## 6. Break after finding the number

```java
if(nums[j] == num){
    found = true;
    break;
}
```

This `break` is important.

We only care whether `num` appears in the **subarray**, not how many times it appears inside that subarray.

For example:

```text
[7,2,1,7]
```

For `num = 7`, this counts as:

```text
1 subarray
```

not:

```text
2 subarrays
```

because we count each subarray only once.

---

## 7. Increase subarray count

```java
if(found){
    count++;
}
```

If the number exists somewhere inside the current subarray, we increase its count.

---

## 8. Check whether it is almost missing

```java
if(count == 1)
```

The problem says:

> An integer is almost missing if it appears in exactly one subarray of size `k`.

So:

```text
count == 1
```

means it qualifies.

---

## 9. Find the largest one

```java
ans = Math.max(ans, num);
```

Suppose:

```text
almost missing = [3,7]
```

Then:

```text
ans = max(-1,3) = 3
ans = max(3,7) = 7
```

Final answer:

```text
7
```

---

# Dry Run

Consider:

```text
nums = [3,9,2,1,7]
k = 3
```

### Candidate = `3`

Subarrays:

```text
[3,9,2] → found
[9,2,1] → not found
[2,1,7] → not found
```

Therefore:

```text
count = 1
```

So:

```text
ans = 3
```

---

### Candidate = `9`

```text
[3,9,2] → found
[9,2,1] → found
[2,1,7] → not found
```

```text
count = 2
```

Not valid.

---

### Candidate = `2`

```text
[3,9,2] → found
[9,2,1] → found
[2,1,7] → found
```

```text
count = 3
```

Not valid.

---

### Candidate = `1`

```text
[3,9,2] → not found
[9,2,1] → found
[2,1,7] → found
```

```text
count = 2
```

Not valid.

---

### Candidate = `7`

```text
[3,9,2] → not found
[9,2,1] → not found
[2,1,7] → found
```

```text
count = 1
```

So:

```text
ans = max(3,7)
    = 7
```

Return:

```text
7
```

---

# Important Point

There is a subtle difference between:

```text
frequency of a number
```

and:

```text
number of subarrays containing that number
```

For example:

```text
nums = [7,2,1,7]
k = 4
```

The number `7` appears **twice** in the subarray.

But it appears in only **one subarray**.

Therefore its subarray count is:

```text
1
```

This is why the code uses:

```java
boolean found = false;
```

and:

```java
break;
```

Once the number is found inside a particular subarray, that subarray should contribute only `1` to `count`.

---

# Why This Approach Works

For every possible candidate `num`, the algorithm examines **all possible subarrays of size `k`**.

For every subarray, it checks whether `num` occurs in it.

Therefore, `count` exactly represents:

```text
Number of size-k subarrays containing num
```

Then:

```java
if(count == 1)
```

correctly identifies almost missing integers.

Finally:

```java
Math.max(ans, num)
```

returns the largest one.

---

# Complexity Analysis

Let:

```text
n = nums.length
```

There are:

```text
n
```

possible candidates.

For each candidate, there are approximately:

```text
n - k + 1
```

subarrays.

For each subarray, we can scan up to:

```text
k
```

elements.

Therefore:

```text
Time Complexity = O(n × (n-k+1) × k)
```

Worst case:

```text
O(n³)
```

Since:

```text
n <= 50
```

this brute-force solution is easily fast enough.

### Space Complexity

We use only a few variables:

```text
O(1)
```

extra space.

---

# Key Pattern

This problem teaches an important idea:

```text
Candidate
   ↓
Check every fixed-size subarray
   ↓
Does candidate exist in this subarray?
   ↓
Count subarrays
   ↓
count == 1 ?
   ↓
Update maximum
```

The most important part to remember is:

```java
boolean found = false;
```

because we need to count **subarrays containing the number**, not the total number of occurrences.

---

# Alternative Way to Think About It

For each `num`:

```text
How many windows of size k contain num?
```

If the answer is:

```text
0 → not almost missing
1 → almost missing ✅
2+ → not almost missing
```

Then among all numbers with count `1`, choose the largest.

---

# Key Concepts

- Array
- Subarray
- Fixed-size Window
- Brute Force
- Nested Loops
- Candidate Checking
- `Math.max()`
- Boolean `found` flag
- `break`

---

## Your Solution

Your solution is **correct** for the given constraints.

The important logic is:

```java
if(nums[j] == num){
    found = true;
    break;
}
```

followed by:

```java
if(found){
    count++;
}
```

This correctly ensures that a number is counted **once per subarray**, even if it occurs multiple times inside that subarray.

Complexity:

```text
Time  → O(n³) worst case
Space → O(1)
```

Given `n <= 50`, this is completely acceptable.