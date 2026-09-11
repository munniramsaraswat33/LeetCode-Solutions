# 2187. Minimum Time to Complete Trips

**LeetCode:** [2187. Minimum Time to Complete Trips](https://leetcode.com/problems/minimum-time-to-complete-trips/)

**Difficulty:** Medium

**Primary Topic:** Binary Search

**Pattern:** Binary Search on Answer

---

## Problem Statement

You are given an integer array `time`.

Each element:

```text
time[i]
```

represents the time taken by the `i-th` bus to complete **one trip**.

All buses operate independently and can make multiple trips.

Given:

```text
totalTrips
```

return the **minimum amount of time** required for all buses to complete at least `totalTrips` trips.

---

## Example 1

### Input

```text
time = [1,2,3]
totalTrips = 5
```

### Output

```text
3
```

### Explanation

In `3` units of time:

```text
Bus 1: 3 / 1 = 3 trips
Bus 2: 3 / 2 = 1 trip
Bus 3: 3 / 3 = 1 trip
```

Total:

```text
3 + 1 + 1 = 5 trips
```

Therefore, the minimum time is:

```text
3
```

---

## Example 2

### Input

```text
time = [2]
totalTrips = 1
```

### Output

```text
2
```

The only bus takes `2` units of time to complete one trip.

---

# Approach

This problem asks for the **minimum time** required to satisfy a condition.

We can use:

```text
Binary Search on Answer
```

Instead of trying every possible time, we guess a time `mid` and check whether all buses can complete at least `totalTrips` trips within that time.

For a bus that takes `t` time for one trip:

```text
trips = mid / t
```

Therefore, the total number of trips completed by all buses is:

```text
Σ(mid / time[i])
```

If:

```text
trips >= totalTrips
```

then `mid` is sufficient.

Otherwise, `mid` is too small.

---

# Intuition

Suppose:

```text
time = [1,2,3]
totalTrips = 5
```

Try:

```text
mid = 3
```

Trips completed:

```text
3 / 1 = 3
3 / 2 = 1
3 / 3 = 1
```

Total:

```text
5
```

So `3` is sufficient.

Now try a smaller time:

```text
mid = 2
```

Trips:

```text
2 / 1 = 2
2 / 2 = 1
2 / 3 = 0
```

Total:

```text
3
```

Only 3 trips are completed, so `2` is not enough.

Therefore, the answer is:

```text
3
```

---

# Binary Search Range

The minimum possible time is:

```text
1
```

Therefore:

```java
long left = 1;
```

For the maximum time, consider the fastest bus.

If the fastest bus takes:

```text
mintime
```

then it can complete all `totalTrips` by itself in:

```text
mintime × totalTrips
```

So:

```java
long right = (long)mintime * totalTrips;
```

This is a valid upper bound.

---

# Why Use the Minimum Bus Time?

The fastest bus is the bus with the smallest value in `time`.

If:

```text
mintime = min(time)
```

then even if only that bus is used, it can complete:

```text
totalTrips
```

in:

```text
mintime × totalTrips
```

Therefore, the answer cannot be greater than this value.

---

# Feasibility Check

For a candidate time `mid`:

```java
long trip = 0;

for(int t : time){
    trip += mid / t;
}
```

Each bus contributes the number of complete trips it can finish within `mid` time.

For example:

```text
mid = 10
t = 3
```

Then:

```text
10 / 3 = 3
```

So that bus can complete `3` complete trips.

---

# Algorithm

1. Find the minimum bus time.
2. Set:
   ```text
   left = 1
   right = minTime × totalTrips
   ```
3. Perform binary search.
4. Calculate:
   ```text
   mid = left + (right - left) / 2
   ```
5. Count the total trips that can be completed in `mid` time.
6. If:
   ```text
   trip < totalTrips
   ```
   then `mid` is insufficient:
   ```text
   left = mid + 1
   ```
7. Otherwise, `mid` is sufficient:
   ```text
   right = mid - 1
   ```
8. When binary search finishes, `left` is the minimum valid time.
9. Return `left`.

---

# Dry Run

Consider:

```text
time = [1,2,3]
totalTrips = 5
```

First find:

```text
mintime = 1
```

Therefore:

```text
left = 1
right = 1 × 5 = 5
```

---

## Iteration 1

```text
left = 1
right = 5

mid = 3
```

Calculate trips:

```text
3 / 1 = 3
3 / 2 = 1
3 / 3 = 1
```

Total:

```text
3 + 1 + 1 = 5
```

Since:

```text
5 >= 5
```

`3` is sufficient.

Search for a smaller time:

```text
right = 2
```

---

## Iteration 2

```text
left = 1
right = 2

mid = 1
```

Trips:

```text
1 / 1 = 1
1 / 2 = 0
1 / 3 = 0
```

Total:

```text
1
```

Since:

```text
1 < 5
```

`1` is insufficient.

Move right:

```text
left = 2
```

---

## Iteration 3

```text
left = 2
right = 2

mid = 2
```

Trips:

```text
2 / 1 = 2
2 / 2 = 1
2 / 3 = 0
```

Total:

```text
3
```

Since:

```text
3 < 5
```

`2` is insufficient.

Therefore:

```text
left = 3
```

Now:

```text
left > right
```

Binary search ends.

Final answer:

```text
3
```

---

# Java Solution

```java
class Solution {
    public long minimumTime(int[] time, int totalTrips) {

        long left = 1;

        int mintime = Integer.MAX_VALUE;

        for(int t : time){
            mintime = Math.min(mintime, t);
        }

        long right = (long)mintime * totalTrips;

        while(left <= right){

            long mid = left + (right - left) / 2;

            long trip = 0;

            for(int t : time){
                trip += mid / t;
            }

            if(trip < totalTrips){
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }

        return left;
    }
}
```

---

# Code Explanation

## 1. Initialize Left Boundary

```java
long left = 1;
```

The minimum possible positive time is `1`.

---

## 2. Find the Fastest Bus

```java
int mintime = Integer.MAX_VALUE;

for(int t : time){
    mintime = Math.min(mintime, t);
}
```

Find the smallest value in the `time` array.

For example:

```text
time = [5,2,3]
```

then:

```text
mintime = 2
```

---

## 3. Calculate Right Boundary

```java
long right = (long)mintime * totalTrips;
```

The fastest bus can complete all trips by itself.

Therefore:

```text
maximum required time
= fastest bus time × totalTrips
```

The cast to `long` is important because the multiplication can exceed the `int` range.

---

# Binary Search

```java
while(left <= right){
```

Continue searching while the range is valid.

---

## Calculate Middle Time

```java
long mid = left + (right - left) / 2;
```

`mid` represents a candidate amount of time.

---

# Count Completed Trips

```java
long trip = 0;

for(int t : time){
    trip += mid / t;
}
```

For each bus:

```text
trips completed = mid / time[i]
```

Add all bus contributions.

---

# Candidate Is Too Small

```java
if(trip < totalTrips){
    left = mid + 1;
}
```

If fewer than `totalTrips` trips can be completed, we need more time.

Therefore, search the right half.

---

# Candidate Is Sufficient

```java
else{
    right = mid - 1;
}
```

If at least `totalTrips` trips can be completed, the current time works.

But we want the **minimum** possible time.

Therefore, search for a smaller value.

---

# Why Does `left` Contain the Answer?

The search maintains this idea:

```text
Too Small | Valid
```

When binary search finishes:

```text
left
```

points to the first valid time.

Therefore:

```java
return left;
```

returns the minimum time that allows at least `totalTrips` trips.

---

# Monotonic Property

Binary search works because the feasibility condition is monotonic.

If a time `x` is sufficient:

```text
x → totalTrips or more
```

then every time greater than `x` is also sufficient.

For example:

```text
Time:       1  2  3  4  5  6 ...
Possible:   ❌  ❌  ✅  ✅  ✅  ✅ ...
```

We need to find the **first possible** value.

This is a classic binary-search-on-answer problem.

---

# Why Integer Division Works

Suppose:

```text
mid = 10
bus time = 3
```

The bus can complete:

```text
10 / 3 = 3
```

complete trips.

The remaining:

```text
10 % 3 = 1
```

unit of time is not enough to complete another trip.

Therefore integer division gives exactly the number of complete trips.

---

# Complexity Analysis

Let:

- `n` = number of buses.
- `T` = upper bound on the answer.

Finding the minimum bus time takes:

```text
O(n)
```

Each binary-search iteration checks every bus:

```text
O(n)
```

The binary search requires:

```text
O(log T)
```

iterations.

Therefore:

```text
Time Complexity = O(n log T)
```

More specifically, with:

```text
T = mintime × totalTrips
```

the complexity is:

```text
O(n log(mintime × totalTrips))
```

Space used is:

```text
O(1)
```

---

# Key Concepts

## 1. Binary Search on Answer

We are not searching for an element in the `time` array.

We are searching for the minimum time that satisfies a condition.

---

## 2. Feasibility Check

For a candidate time `mid`:

```text
Σ(mid / time[i]) >= totalTrips
```

means the candidate is valid.

---

## 3. Monotonicity

If `mid` is enough, every larger time is also enough.

If `mid` is not enough, every smaller time is also not enough.

---

## 4. First Valid Value

The problem asks for the minimum valid time.

Therefore, binary search finds the first value satisfying:

```text
trips >= totalTrips
```

---

# Pattern Recognition

When you see a problem asking:

```text
Minimum time required
```

and you can calculate how much work can be completed within a guessed time, think:

```text
Binary Search on Answer
```

The general pattern is:

```text
Guess answer = mid
        ↓
Check if mid is feasible
        ↓
      /     \
    Yes      No
     ↓        ↓
Search left  Search right
```

For this problem:

```text
Time = mid
   ↓
Count trips
   ↓
trips >= totalTrips?
```

---

# Common Mistakes

## Mistake 1: Simulating Every Unit of Time

Increasing time one by one is inefficient.

Instead, use binary search over the possible time range.

---

## Mistake 2: Using the Maximum Bus Time for the Upper Bound

The maximum bus time is not the important value for constructing the tight upper bound.

The fastest bus can complete all trips by itself, so:

```text
right = minimum bus time × totalTrips
```

is a valid upper bound.

---

## Mistake 3: Using `int` for Time Calculations

The answer can be large.

Therefore, use:

```java
long
```

for:

```text
left
right
mid
trip
```

and cast before multiplication:

```java
(long)mintime * totalTrips
```

---

## Mistake 4: Searching the Wrong Direction

If:

```java
trip < totalTrips
```

we need **more** time:

```java
left = mid + 1;
```

If:

```java
trip >= totalTrips
```

we need to try **less** time:

```java
right = mid - 1;
```

---

# Edge Cases

### One Bus

```text
time = [5]
totalTrips = 4
```

The bus needs:

```text
5 × 4 = 20
```

time.

Answer:

```text
20
```

---

### Multiple Fast Buses

```text
time = [1,1,1]
totalTrips = 3
```

In one unit of time:

```text
1 + 1 + 1 = 3 trips
```

Answer:

```text
1
```

---

### Very Large Values

The answer can become large, so using `long` prevents integer overflow during:

```java
mintime * totalTrips
```

and during trip counting.

---

# Learning Outcome

After solving this problem, you should understand:

- How to identify binary search on answer.
- How to construct a feasibility function.
- How to count completed work using integer division.
- How to establish a valid search range.
- Why the feasibility condition is monotonic.
- How to find the minimum valid value using binary search.
- Why `long` is important for large numerical calculations.

---

# Final Takeaway

The core idea is:

```text
Guess a time = mid
        ↓
Calculate trips completed by all buses
        ↓
If trips >= totalTrips
        ↓
mid is sufficient
        ↓
Try a smaller time
```

Otherwise:

```text
trips < totalTrips
        ↓
mid is insufficient
        ↓
Try a larger time
```

So the complete pattern is:

```text
Binary Search on Answer
        +
Feasibility Check
```

**Time Complexity:** `O(n log(mintime × totalTrips))`

**Space Complexity:** `O(1)`