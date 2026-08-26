# 435. Non-overlapping Intervals

> **Difficulty:** Medium  
> **Topics:** Array, Greedy, Sorting, Intervals

---

## Problem Statement

Given an array of intervals:

```text
intervals[i] = [starti, endi]
```

return the **minimum number of intervals you need to remove** to make the rest of the intervals **non-overlapping**.

Two intervals are considered non-overlapping if they do not share any common points.

---

## Example 1

### Input

```text
intervals = [[1,2],[2,3],[3,4],[1,3]]
```

### Output

```text
1
```

### Explanation

Remove:

```text
[1,3]
```

The remaining intervals are:

```text
[1,2]
[2,3]
[3,4]
```

These intervals do not overlap.

Therefore:

```text
Minimum removals = 1
```

---

## Example 2

### Input

```text
intervals = [[1,2],[1,2],[1,2]]
```

### Output

```text
2
```

### Explanation

Only one of the three identical intervals can remain.

Therefore, we need to remove:

```text
2 intervals
```

---

## Example 3

### Input

```text
intervals = [[1,2],[2,3]]
```

### Output

```text
0
```

### Explanation

The intervals only touch at the endpoint:

```text
[1,2]
[2,3]
```

They are considered non-overlapping.

Therefore:

```text
0 intervals need to be removed.
```

---

# Approach

Use a **Greedy Algorithm**.

The goal is to remove the minimum number of intervals.

Instead of directly deciding which intervals to remove, we can think of the problem as:

> Keep the maximum number of non-overlapping intervals.

For overlapping intervals, we should keep the interval with the **smaller ending point**.

Why?

Because an interval that ends earlier leaves more space for future intervals.

---

# Greedy Strategy

First, sort the intervals by their starting point.

Then process them from left to right.

Maintain:

```text
end
```

which represents the ending point of the interval we are currently keeping.

For every next interval:

### Case 1: No Overlap

If:

```text
end <= intervals[i][0]
```

then the current interval does not overlap with the previous interval.

We can keep it:

```text
end = intervals[i][1]
```

---

### Case 2: Overlap

If:

```text
end > intervals[i][0]
```

the intervals overlap.

We need to remove one of them.

To make the best greedy choice, keep the interval with the smaller ending point:

```text
end = min(end, intervals[i][1])
```

and increase the removal count:

```text
count++
```

---

# Algorithm

1. Sort intervals by their starting point.
2. Initialize:
   ```text
   count = 0
   ```
3. Set:
   ```text
   end = intervals[0][1]
   ```
4. Traverse the remaining intervals.
5. If:
   ```text
   end > intervals[i][0]
   ```
   then the intervals overlap:
   - Increase `count`.
   - Keep the interval with the smaller ending point.
6. Otherwise:
   - The intervals do not overlap.
   - Update `end`.
7. Return `count`.

---

# Dry Run

Input:

```text
intervals = [[1,2],[2,3],[3,4],[1,3]]
```

---

### Step 1: Sort by Starting Point

After sorting:

```text
[[1,2],[1,3],[2,3],[3,4]]
```

---

### Step 2: Initialize

Take the first interval:

```text
[1,2]
```

So:

```text
count = 0
end = 2
```

---

### Step 3: Process `[1,3]`

Check:

```text
end > start
2 > 1
```

So the intervals overlap:

```text
[1,2]
[1,3]
```

We need to remove one.

Compare their ending points:

```text
2 < 3
```

Keep:

```text
[1,2]
```

Therefore:

```text
count = 1
end = 2
```

---

### Step 4: Process `[2,3]`

Check:

```text
end > start
2 > 2
```

This is false.

Therefore, they do not overlap.

Keep:

```text
[2,3]
```

Update:

```text
end = 3
```

---

### Step 5: Process `[3,4]`

Check:

```text
3 > 3
```

False.

So there is no overlap.

Update:

```text
end = 4
```

---

Final:

```text
count = 1
```

Therefore:

```text
Answer = 1
```

---

# Understanding the Code

## Sort Intervals

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
```

We sort the intervals according to their starting points.

For example:

```text
[3,4]
[1,2]
[1,3]
[2,3]
```

becomes:

```text
[1,2]
[1,3]
[2,3]
[3,4]
```

This allows us to process intervals from left to right.

---

## Initialize Removal Count

```java
int count = 0;
```

This stores how many intervals need to be removed.

---

## Store Current Ending Point

```java
int end = intervals[0][1];
```

Initially, we keep the first interval.

`end` stores the ending point of the interval currently being kept.

---

## Detect Overlap

```java
if(end > intervals[i][0]){
```

If:

```text
current end > next start
```

then the two intervals overlap.

For example:

```text
[1,5]
[3,7]
```

Here:

```text
5 > 3
```

so they overlap.

---

## Remove One Interval

```java
count++;
```

When two intervals overlap, one of them must be removed.

---

## Keep the Interval Ending Earlier

```java
end = Math.min(end, intervals[i][1]);
```

Suppose:

```text
current = [1,5]
next    = [3,4]
```

Both overlap.

We should keep:

```text
[3,4]
```

because it ends earlier.

Therefore:

```text
end = min(5,4)
    = 4
```

Keeping the smaller ending point gives future intervals more room.

---

## No Overlap

```java
else{
    end = intervals[i][1];
}
```

If:

```text
end <= intervals[i][0]
```

the current interval can be kept.

So we update:

```text
end
```

to its ending point.

---

# Why Greedy Works

Suppose two intervals overlap:

```text
A = [1,10]
B = [2,5]
```

We have to remove one.

If we keep:

```text
[1,10]
```

then future intervals must start after `10`.

But if we keep:

```text
[2,5]
```

future intervals only need to start at or after `5`.

Therefore, keeping the interval with the **smaller ending point** is always better for accommodating future intervals.

This is the key greedy idea.

---

# Important Condition

The code checks:

```java
if(end > intervals[i][0])
```

not:

```java
if(end >= intervals[i][0])
```

This is because intervals that only touch at the endpoint are considered non-overlapping.

For example:

```text
[1,2]
[2,3]
```

They are allowed together.

Because:

```text
end = 2
start = 2
```

and:

```text
2 > 2
```

is false.

---

# Relation With Interval Scheduling

This problem is closely related to the **Maximum Non-overlapping Intervals** problem.

Instead of:

```text
Minimum intervals to remove
```

we can think:

```text
Maximum intervals to keep
```

If there are `n` intervals and we can keep `k` non-overlapping intervals:

```text
minimum removals = n - k
```

The greedy rule of keeping the interval with the smallest ending point is the same fundamental idea used in interval scheduling.

---

# Greedy Pattern

The general pattern is:

```text
Sort intervals
      ↓
Check overlap
      ↓
If overlapping
      ↓
Keep interval with smaller end
      ↓
If not overlapping
      ↓
Keep current interval
```

The most important line is:

```java
end = Math.min(end, intervals[i][1]);
```

This ensures that the current ending point is as small as possible.

---

# Complexity Analysis

Let:

```text
n = intervals.length
```

### Time Complexity

Sorting:

```text
O(n log n)
```

Traversing the intervals:

```text
O(n)
```

Overall:

```text
O(n log n)
```

---

### Space Complexity

The algorithm uses only a few variables apart from the sorting implementation.

Therefore, extra space is:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public int eraseOverlapIntervals(int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        int count = 0;
        int end = intervals[0][1];

        for(int i = 1; i < intervals.length; i++){

            if(end > intervals[i][0]){

                count++;

                end = Math.min(
                    end,
                    intervals[i][1]
                );
            }
            else{

                end = intervals[i][1];
            }
        }

        return count;
    }
}
```

---

# Key Concepts

- Greedy Algorithm
- Array
- Intervals
- Sorting
- Interval Overlap
- Minimum Removals
- Interval Scheduling

---

# Constraints

- `1 <= intervals.length <= 10^5`
- `intervals[i].length == 2`
- `-5 * 10^4 <= starti < endi <= 5 * 10^4`

---

# Learning Outcome

This problem demonstrates the **Greedy Interval** technique.

The main idea is:

```text
Sort intervals by starting point
          ↓
Check whether current intervals overlap
          ↓
If they overlap
          ↓
Remove one interval
          ↓
Keep the one with smaller ending point
          ↓
Continue
```

The key greedy decision is:

```java
end = Math.min(end, intervals[i][1]);
```

When two intervals overlap, keeping the interval that ends earlier leaves more room for future intervals.

Therefore, this strategy minimizes the number of intervals that need to be removed.

The solution achieves:

```text
Time  → O(n log n)
Space → O(1)
```