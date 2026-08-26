# 2542. Maximum Subsequence Score

> **Difficulty:** Medium  
> **Topics:** Array, Greedy, Sorting, Heap, Priority Queue

---

## Problem Statement

You are given two integer arrays `nums1` and `nums2` of equal length `n`, and an integer `k`.

Choose a subsequence of indices of length exactly `k`.

For the selected indices, the score is calculated as:

```text
sum(nums1[selected indices]) × min(nums2[selected indices])
```

Return the **maximum possible score**.

---

## Example 1

### Input

```text
nums1 = [1,3,3,2]
nums2 = [2,1,3,4]
k = 3
```

### Output

```text
12
```

### Explanation

Choose indices:

```text
[0,2,3]
```

Selected values:

```text
nums1 → [1,3,2]
nums2 → [2,3,4]
```

Sum of selected `nums1` values:

```text
1 + 3 + 2 = 6
```

Minimum selected `nums2` value:

```text
min(2,3,4) = 2
```

Score:

```text
6 × 2 = 12
```

---

## Example 2

### Input

```text
nums1 = [4,2,3,1,1]
nums2 = [7,5,10,9,6]
k = 1
```

### Output

```text
30
```

### Explanation

For `k = 1`, the score for an index is:

```text
nums1[i] × nums2[i]
```

The maximum is:

```text
3 × 10 = 30
```

---

# Approach

Use **Greedy + Sorting + Priority Queue**.

The score is:

```text
sum(nums1) × minimum(nums2)
```

The difficult part is the `minimum(nums2)`.

We sort the pairs:

```text
(nums2[i], nums1[i])
```

in descending order of `nums2`.

After sorting, when we are processing a pair with value:

```text
nums2 = x
```

all previously processed elements have `nums2 >= x`.

Therefore, if we choose `k` elements from the processed elements, the minimum `nums2` value is exactly:

```text
x
```

Now we only need to maximize:

```text
sum(nums1)
```

for those `k` elements.

To maintain the largest possible `k` values of `nums1`, use a **Min Heap (`PriorityQueue`)**.

---

# Algorithm

1. Create pairs:
   ```text
   [nums2[i], nums1[i]]
   ```
2. Sort the pairs in descending order of `nums2`.
3. Create a Min Heap for selected `nums1` values.
4. Maintain:
   ```text
   sum = sum of selected nums1 values
   ```
5. Traverse the sorted pairs.
6. Add the current `nums1` value to the heap and `sum`.
7. If heap size becomes greater than `k`:
   - Remove the smallest `nums1`.
   - Subtract it from `sum`.
8. When heap size is exactly `k`:
   - Current `nums2` value is the minimum `nums2`.
   - Calculate:
     ```text
     sum × current nums2
     ```
   - Update the maximum answer.
9. Return the maximum score.

---

# Why Sort by `nums2`?

Suppose after sorting we have:

```text
nums2 = [10,9,7,5,3]
```

When we reach `7`, all previously processed values are:

```text
10,9
```

So if we select any `3` elements from:

```text
[10,9,7]
```

the minimum `nums2` value is guaranteed to be:

```text
7
```

Therefore, at every step we can treat the current `nums2` as the minimum.

This converts the problem into:

```text
Maximize sum(nums1)
while fixing minimum(nums2)
```

---

# Why Use a Min Heap?

For a fixed minimum `nums2`, we want the largest possible sum of `k` values from `nums1`.

A Min Heap allows us to keep the largest `k` values.

For example, suppose:

```text
nums1 values = [5,8,2,10]
k = 3
```

We want:

```text
[5,8,10]
```

because these are the largest three values.

The heap contains the selected values.

If we add another value and the heap size becomes greater than `k`, we remove the smallest value:

```java
pq.poll()
```

This ensures that the heap always contains the best `k` values.

---

# Dry Run

Input:

```text
nums1 = [1,3,3,2]
nums2 = [2,1,3,4]
k = 3
```

Create pairs:

```text
[2,1]
[1,3]
[3,3]
[4,2]
```

Sort by `nums2` descending:

```text
[4,2]
[3,3]
[2,1]
[1,3]
```

---

### Step 1

Process:

```text
[4,2]
```

Heap:

```text
[2]
```

Sum:

```text
2
```

Heap size is less than `3`.

---

### Step 2

Process:

```text
[3,3]
```

Heap:

```text
[2,3]
```

Sum:

```text
5
```

Still less than `k`.

---

### Step 3

Process:

```text
[2,1]
```

Heap:

```text
[1,2,3]
```

Sum:

```text
6
```

Heap size is `3`.

Current minimum `nums2`:

```text
2
```

Score:

```text
6 × 2 = 12
```

So:

```text
res = 12
```

---

### Step 4

Process:

```text
[1,3]
```

Add `3`:

```text
Heap = [1,2,3,3]
```

Sum:

```text
9
```

Heap size is now greater than `k`.

Remove the smallest value:

```text
1
```

Now:

```text
Heap = [2,3,3]
```

Sum:

```text
8
```

Current minimum `nums2`:

```text
1
```

Score:

```text
8 × 1 = 8
```

The previous answer was better:

```text
res = 12
```

Final answer:

```text
12
```

---

# Understanding the Code

## Create Pair Array

```java
int[][] ess = new int[n][2];

for(int i = 0; i < n; i++){
    ess[i] = new int[]{nums2[i], nums1[i]};
}
```

Each pair stores:

```text
[nums2[i], nums1[i]]
```

This allows us to sort `nums1` according to the corresponding `nums2`.

---

## Sort by `nums2` Descending

```java
Arrays.sort(ess, (a, b) -> b[0] - a[0]);
```

The first value in each pair is `nums2`.

So the pairs are sorted from largest `nums2` to smallest.

For example:

```text
[2,1]
[1,3]
[3,3]
[4,2]
```

becomes:

```text
[4,2]
[3,3]
[2,1]
[1,3]
```

---

## Create Min Heap

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>(k, (a, b) -> a - b);
```

The smallest `nums1` value stays at the top.

This lets us remove the smallest selected value whenever we have more than `k` elements.

---

## Maintain Sum

```java
long res = 0, sum = 0;
```

`sum` stores the sum of the selected `nums1` values.

`res` stores the maximum score found so far.

`long` is used because the multiplication can exceed the range of `int`.

---

## Add Current `nums1`

```java
pq.add(es[1]);
sum = sum + es[1];
```

The current `nums1` value is added to the heap and to the running sum.

---

## Keep Exactly `k` Elements

```java
if(pq.size() > k){
    sum -= pq.poll();
}
```

If there are more than `k` values, remove the smallest one.

Because this is a Min Heap:

```java
pq.poll()
```

returns the smallest value.

Therefore, we keep the largest `k` `nums1` values.

---

## Calculate Score

```java
if(pq.size() == k){
    res = Math.max(res, sum * es[0]);
}
```

When we have exactly `k` elements:

```text
sum = sum of selected nums1
es[0] = current minimum nums2
```

So:

```text
score = sum × minimum(nums2)
```

Update the answer if this score is larger.

---

# Important Greedy Idea

The main trick is:

```text
Sort nums2 in descending order
```

Then the current `nums2` automatically becomes the minimum of all processed candidates.

For every possible minimum:

```text
current nums2
```

we choose the best `k` values of `nums1`.

So the problem becomes:

```text
Fix minimum nums2
        ↓
Choose largest k nums1
        ↓
Calculate score
        ↓
Take maximum
```

---

# Why Long Is Required

The score is:

```text
sum(nums1) × minimum(nums2)
```

The sum can become large.

Therefore:

```java
long sum = 0;
long res = 0;
```

is safer than using `int`.

The expression:

```java
sum * es[0]
```

is calculated using `long` because `sum` is a `long`.

---

# Complexity Analysis

Let `n` be the length of the arrays.

### Time Complexity

Creating the pairs:

```text
O(n)
```

Sorting:

```text
O(n log n)
```

Heap operations:

```text
O(n log k)
```

Overall:

```text
O(n log n + n log k)
```

Since:

```text
k <= n
```

the overall complexity can be expressed as:

```text
O(n log n)
```

---

### Space Complexity

The pair array requires:

```text
O(n)
```

The Priority Queue contains at most `k` elements:

```text
O(k)
```

Overall:

```text
O(n + k)
```

Since `k <= n`:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public long maxScore(int[] nums1, int[] nums2, int k) {

        int n = nums1.length;

        int[][] ess = new int[n][2];

        for(int i = 0; i < n; i++){
            ess[i] = new int[]{nums2[i], nums1[i]};
        }

        Arrays.sort(ess, (a, b) -> b[0] - a[0]);

        PriorityQueue<Integer> pq =
            new PriorityQueue<>(k, (a, b) -> a - b);

        long res = 0;
        long sum = 0;

        for(int[] es : ess){

            pq.add(es[1]);
            sum = sum + es[1];

            if(pq.size() > k){
                sum -= pq.poll();
            }

            if(pq.size() == k){
                res = Math.max(res, sum * es[0]);
            }
        }

        return res;
    }
}
```

---

# Key Concepts

- Array
- Greedy
- Sorting
- Priority Queue
- Min Heap
- Subsequence
- Running Sum
- Optimization

---

# Constraints

- `n == nums1.length == nums2.length`
- `1 <= n <= 10^5`
- `1 <= nums1[i], nums2[i] <= 10^5`
- `1 <= k <= n`

---

# Learning Outcome

This problem demonstrates an important **Greedy + Sorting + Heap** pattern.

The main idea is:

```text
Pair nums1 and nums2
       ↓
Sort by nums2 descending
       ↓
Current nums2 = minimum
       ↓
Use Min Heap to keep largest k nums1
       ↓
Calculate sum × minimum
       ↓
Take maximum
```

The most important parts are:

```java
Arrays.sort(ess, (a, b) -> b[0] - a[0]);
```

and:

```java
if(pq.size() > k){
    sum -= pq.poll();
}
```

Sorting fixes the minimum `nums2`, while the Min Heap ensures that we always keep the best `k` values from `nums1`.

The solution achieves:

```text
Time  → O(n log n)
Space → O(n)
```