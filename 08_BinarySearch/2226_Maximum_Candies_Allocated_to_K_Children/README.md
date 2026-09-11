# 2226. Maximum Candies Allocated to K Children

**LeetCode:** [2226. Maximum Candies Allocated to K Children](https://leetcode.com/problems/maximum-candies-allocated-to-k-children/)

**Difficulty:** Medium

**Primary Topic:** Binary Search

**Pattern:** Binary Search on Answer

---

## Problem Statement

You are given an array `candies`, where:

```text
candies[i]
```

represents the number of candies in the `i-th` pile.

You need to distribute candies to exactly `k` children.

Each child must receive the **same number of candies**.

A pile can be split among multiple children, but each child can receive candies from only one pile.

Return the **maximum number of candies** that each child can receive.

If it is impossible to give every child at least one candy, return:

```text
0
```

---

## Example 1

### Input

```text
candies = [5,8,6]
k = 3
```

### Output

```text
5
```

### Explanation

We can give:

```text
5 candies → Child 1
5 candies → Child 2
5 candies → Child 3
```

The maximum possible amount per child is:

```text
5
```

---

## Example 2

### Input

```text
candies = [2,5]
k = 3
```

### Output

```text
2
```

### Explanation

We can divide the piles as:

```text
2 → Child 1
2 → Child 2
2 → Child 3
```

So every child can receive `2` candies.

---

# Approach

This problem asks for the **maximum possible value** that satisfies a condition.

That is a classic:

```text
Binary Search on Answer
```

pattern.

Instead of directly finding the maximum candies per child, we guess an amount `mid` and check whether it is possible to give at least `k` children exactly `mid` candies.

For a pile containing `candies[i]` candies, if each child needs `mid` candies, that pile can serve:

```text
candies[i] / mid
```

children.

Therefore, the total number of children that can be served is:

```text
Σ(candies[i] / mid)
```

If:

```text
children >= k
```

then `mid` is possible.

Otherwise, `mid` is too large.

---

# Intuition

Suppose:

```text
candies = [5, 8, 6]
k = 3
```

Try:

```text
mid = 5
```

Number of children we can serve:

```text
5 / 5 = 1
8 / 5 = 1
6 / 5 = 1
```

Total:

```text
3 children
```

So `5` is possible.

Now try a larger amount:

```text
mid = 6
```

Then:

```text
5 / 6 = 0
8 / 6 = 1
6 / 6 = 1
```

Total:

```text
2 children
```

Only 2 children can be served, so `6` is not possible.

Therefore, the answer is:

```text
5
```

---

# Binary Search Range

The minimum possible amount is:

```text
1
```

So:

```java
long left = 1;
```

For the maximum possible amount, all candies could theoretically be divided among `k` children.

Therefore:

```text
total candies / k
```

is an upper bound.

```java
long right = total / k;
```

So the search space is:

```text
[1, total / k]
```

---

# Why `total / k` Is an Upper Bound

Suppose there are:

```text
100 candies
```

and:

```text
k = 4
```

The average number of candies per child cannot exceed:

```text
100 / 4 = 25
```

Even if the candies are distributed perfectly.

Therefore, no answer greater than:

```text
total / k
```

is possible.

---

# Feasibility Check

For a candidate value `mid`:

```java
long child = 0;

for(int candie : candies){
    child += candie / mid;
}
```

Each pile contributes the number of complete groups of size `mid`.

For example:

```text
pile = 17
mid = 5
```

Then:

```text
17 / 5 = 3
```

So this pile can provide `5` candies to 3 children.

The remaining:

```text
2
```

candies cannot be used to form another group of size `5`.

---

# Algorithm

1. Calculate the total number of candies.
2. Set:
   ```text
   left = 1
   right = total / k
   ```
3. Perform binary search.
4. Calculate:
   ```text
   mid = left + (right - left) / 2
   ```
5. Count how many children can receive `mid` candies:
   ```text
   child += candies[i] / mid
   ```
6. If:
   ```text
   child >= k
   ```
   then `mid` is feasible:
   - Store it as the current answer.
   - Search for a larger value.
7. Otherwise:
   - Search for a smaller value.
8. Return the maximum feasible value.

---

# Dry Run

Consider:

```text
candies = [5,8,6]
k = 3
```

Total:

```text
5 + 8 + 6 = 19
```

Search range:

```text
left = 1
right = 19 / 3 = 6
```

---

## Iteration 1

```text
left = 1
right = 6
mid = 3
```

Check:

```text
5 / 3 = 1
8 / 3 = 2
6 / 3 = 2
```

Total:

```text
5 children
```

Since:

```text
5 >= 3
```

`3` is possible.

So:

```text
ans = 3
left = 4
```

---

## Iteration 2

```text
left = 4
right = 6
mid = 5
```

Check:

```text
5 / 5 = 1
8 / 5 = 1
6 / 5 = 1
```

Total:

```text
3 children
```

Since:

```text
3 >= 3
```

`5` is possible.

Update:

```text
ans = 5
left = 6
```

---

## Iteration 3

```text
left = 6
right = 6
mid = 6
```

Check:

```text
5 / 6 = 0
8 / 6 = 1
6 / 6 = 1
```

Total:

```text
2 children
```

Since:

```text
2 < 3
```

`6` is not possible.

So:

```text
right = 5
```

Now:

```text
left > right
```

Binary search ends.

Final answer:

```text
5
```

---

# Java Solution

```java
class Solution {
    public int maximumCandies(int[] candies, long k) {

        long left = 1;
        long total = 0;

        for(int candie : candies){
            total += candie;
        }

        long right = total / k;

        int ans = 0;

        while(left <= right){

            long mid = left + (right - left) / 2;

            long child = 0;

            for(int candie : candies){
                child += candie / mid;
            }

            if(child >= k){
                left = mid + 1;
                ans = (int)mid;
            }
            else{
                right = mid - 1;
            }
        }

        return ans;
    }
}
```

---

# Code Explanation

## 1. Initialize Search Range

```java
long left = 1;
```

Each child must receive at least one candy for a positive answer.

---

## 2. Calculate Total Candies

```java
long total = 0;

for(int candie : candies){
    total += candie;
}
```

Calculate the total number of candies in all piles.

---

## 3. Calculate Upper Bound

```java
long right = total / k;
```

No child can receive more than the average number of candies available across all children.

Therefore:

```text
right = total / k
```

is a safe upper bound.

---

## 4. Store the Best Answer

```java
int ans = 0;
```

If no positive amount can be distributed to all `k` children, the answer remains `0`.

---

# Binary Search

```java
while(left <= right){
```

Continue searching while a valid candidate range exists.

---

## Calculate Middle

```java
long mid = left + (right - left) / 2;
```

This avoids potential overflow compared with:

```text
(left + right) / 2
```

---

# Count Children

```java
long child = 0;

for(int candie : candies){
    child += candie / mid;
}
```

For every pile, determine how many groups of size `mid` it can create.

Each group represents one child receiving `mid` candies.

---

# When `mid` Is Possible

```java
if(child >= k){
    left = mid + 1;
    ans = (int)mid;
}
```

If at least `k` children can receive `mid` candies, then `mid` is feasible.

But we want the **maximum** possible amount.

Therefore, search the larger half:

```text
left = mid + 1
```

Store:

```java
ans = (int)mid;
```

---

# When `mid` Is Too Large

```java
else{
    right = mid - 1;
}
```

If fewer than `k` children can receive `mid` candies, then `mid` is impossible.

Any larger value will also be impossible.

Therefore, search the smaller half.

---

# Monotonic Property

The reason binary search works is that feasibility is monotonic.

Suppose:

```text
x candies per child is possible
```

Then every smaller value is also possible.

For example:

```text
5 is possible
```

means:

```text
1, 2, 3, 4, 5
```

are possible.

But:

```text
6, 7, 8, ...
```

may become impossible.

So the search looks like:

```text
Possible Possible Possible Possible Impossible Impossible
   1       2       3       4        5          6
```

Binary search finds the largest possible value.

---

# Why Use `long`?

The number of candies and the number of children can be large.

Therefore, these calculations are safer using `long`:

```java
long total;
long left;
long right;
long mid;
long child;
```

In particular:

```java
child += candie / mid;
```

can accumulate values from many piles.

---

# Important Observation

We do **not** need to actually distribute the candies.

We only need to determine whether a candidate amount `mid` is possible.

For every pile:

```text
number of children served = pile / mid
```

Then:

```text
total children served >= k
```

means the candidate is valid.

This converts the distribution problem into a simple feasibility check.

---

# Complexity Analysis

Let:

- `n` = number of candy piles.
- `S` = total number of candies.

The binary search range is at most:

```text
[1, S / k]
```

Therefore, the number of binary-search iterations is:

```text
O(log(S / k))
```

Each iteration scans all `n` piles.

Therefore:

```text
Time Complexity = O(n log(S / k))
```

The extra space used is:

```text
O(1)
```

---

# Key Concepts

## 1. Binary Search on Answer

We are not searching for an element in an array.

Instead, we are searching for the maximum value that satisfies a condition.

This is called:

```text
Binary Search on Answer
```

---

## 2. Feasibility Function

For a candidate `x`:

```text
canDistribute(x)
```

is true if:

```text
Σ(candies[i] / x) >= k
```

---

## 3. Monotonicity

If `x` is feasible, every value smaller than `x` is also feasible.

If `x` is not feasible, every value larger than `x` is also not feasible.

This monotonic behavior enables binary search.

---

# Pattern Recognition

Whenever a problem asks:

```text
Maximum possible minimum value
```

or:

```text
Minimum possible maximum value
```

and you can check whether a candidate value is feasible, consider:

```text
Binary Search on Answer
```

For this problem:

```text
Maximum candies per child
          ↓
Guess candies = mid
          ↓
Count children possible
          ↓
child >= k ?
       /       \
     Yes        No
      ↓          ↓
 search right   search left
```

---

# Common Mistakes

## Mistake 1: Setting `right` to the Maximum Pile

A common approach is:

```text
right = max(candies)
```

This can work, but:

```text
right = total / k
```

is a tighter upper bound.

---

## Mistake 2: Using `mid` as an Array Index

`mid` represents:

```text
candies per child
```

It is not an index.

---

## Mistake 3: Forgetting Integer Division

The number of children a pile can serve is:

```java
candie / mid
```

because only complete groups of `mid` candies can be used.

---

## Mistake 4: Searching the Wrong Half

If:

```java
child >= k
```

the current value works, but we want a larger answer.

Therefore:

```java
left = mid + 1;
```

If:

```java
child < k
```

the value is too large:

```java
right = mid - 1;
```

---

## Mistake 5: Returning Immediately When a Value Works

Finding one valid value is not enough.

We need the **maximum** valid value.

So we continue searching after finding a valid `mid`.

---

# Edge Cases

### Total Candies Less Than `k`

For example:

```text
candies = [1,1]
k = 3
```

Then:

```text
total = 2
right = 2 / 3 = 0
```

There is no positive candidate.

The answer remains:

```text
0
```

---

### Exactly Enough Candies

```text
candies = [3,3,3]
k = 3
```

Each child can receive:

```text
3
```

Answer:

```text
3
```

---

### One Large Pile

```text
candies = [10]
k = 5
```

The single pile can be divided into:

```text
5 groups of 2
```

Answer:

```text
2
```

---

# Learning Outcome

After solving this problem, you should understand:

- How to identify binary search on answer.
- How to construct a feasibility check.
- How integer division can count complete groups.
- Why `total / k` is a useful upper bound.
- How monotonicity makes binary search possible.
- How to maximize a feasible value efficiently.

---

# Final Takeaway

The core idea is:

```text
Guess candies per child = mid
            ↓
Count how many children can receive mid
            ↓
If children >= k
            ↓
mid is possible → search larger
```

Otherwise:

```text
children < k
      ↓
mid is impossible
      ↓
search smaller
```

The complete pattern is:

```text
Binary Search on Answer
        +
Feasibility Check
```

**Time Complexity:** `O(n log(S / k))`

**Space Complexity:** `O(1)`