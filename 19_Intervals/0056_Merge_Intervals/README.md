# 56. Merge Intervals

> **Difficulty:** Medium  
> **Topics:** Array, Sorting, Intervals, Greedy

---

## Problem Statement

You are given an array of intervals where:

```text
intervals[i] = [starti, endi]
```

Merge all **overlapping intervals** and return an array containing the non-overlapping intervals that cover all the intervals from the input.

Two intervals are considered overlapping if they share at least one point.

---

## Example 1

### Input

```text
intervals = [[1,3],[2,6],[8,10],[15,18]]
```

### Output

```text
[[1,6],[8,10],[15,18]]
```

### Explanation

The intervals:

```text
[1,3]
[2,6]
```

overlap because:

```text
2 <= 3
```

Therefore, merge them:

```text
[1,6]
```

The other intervals do not overlap.

Final result:

```text
[[1,6],[8,10],[15,18]]
```

---

## Example 2

### Input

```text
intervals = [[1,4],[4,5]]
```

### Output

```text
[[1,5]]
```

### Explanation

The intervals share the point `4`.

Therefore, they are considered overlapping:

```text
[1,4] + [4,5]
      ↓
[1,5]
```

---

## Example 3

### Input

```text
intervals = [[4,7],[1,4]]
```

### Output

```text
[[1,7]]
```

### Explanation

After sorting:

```text
[[1,4],[4,7]]
```

They overlap at `4`, so they become:

```text
[1,7]
```

---

# Approach

The main idea is:

> **Sort the intervals by their starting point, then merge overlapping intervals from left to right.**

For example:

```text
[[1,3],[8,10],[2,6],[15,18]]
```

After sorting:

```text
[[1,3],[2,6],[8,10],[15,18]]
```

Now the intervals can be processed sequentially.

---

# Why Sort First?

Without sorting, intervals can appear in any order.

For example:

```text
[8,10]
[1,3]
[2,6]
```

It is difficult to determine which intervals should be merged.

After sorting by start:

```text
[1,3]
[2,6]
[8,10]
```

Now we can process them from left to right.

---

# Variables Used

The solution maintains the current merged interval using:

```java
int start
int end
```

Initially:

```java
int start = interval[0][0];
int end = interval[0][1];
```

So:

```text
start = current interval's start
end   = current interval's end
```

---

# Checking for Overlap

For the current interval:

```text
[currentStart, currentEnd]
```

we check:

```java
if (interval[i][0] <= end)
```

This means:

```text
currentStart <= currentEnd
```

so the intervals overlap.

For example:

```text
Current merged interval:
[1,6]

Next interval:
[4,8]
```

Since:

```text
4 <= 6
```

they overlap.

---

# Merging Intervals

When intervals overlap, the start remains the same and we take the maximum end:

```java
end = Math.max(end, interval[i][1]);
```

Example:

```text
[1,6]
[4,8]
```

becomes:

```text
[1,8]
```

---

# What If They Don't Overlap?

Suppose:

```text
Current:
[1,6]

Next:
[8,10]
```

Since:

```text
8 > 6
```

there is no overlap.

Therefore, the current interval is complete.

We add it to the result:

```java
list.add(new int[]{start, end});
```

Then start a new current interval:

```java
start = interval[i][0];
end = interval[i][1];
```

---

# Dry Run

### Input

```text
intervals = [[1,3],[2,6],[8,10],[15,18]]
```

After sorting:

```text
[1,3]
[2,6]
[8,10]
[15,18]
```

Initial:

```text
start = 1
end = 3
```

---

## Step 1: `[2,6]`

Check:

```text
2 <= 3
```

They overlap.

Update:

```text
end = max(3,6)
    = 6
```

Current merged interval:

```text
[1,6]
```

---

## Step 2: `[8,10]`

Check:

```text
8 <= 6
```

False.

So `[1,6]` is complete.

Add:

```text
[1,6]
```

Start a new interval:

```text
start = 8
end = 10
```

---

## Step 3: `[15,18]`

Check:

```text
15 <= 10
```

False.

Add:

```text
[8,10]
```

Start:

```text
start = 15
end = 18
```

---

## After the Loop

The last interval has not yet been added.

So:

```java
list.add(new int[]{start, end});
```

Add:

```text
[15,18]
```

Final result:

```text
[[1,6],[8,10],[15,18]]
```

---

# Important Edge Case: Touching Intervals

The problem considers intervals that share a boundary as overlapping.

For example:

```text
[1,4]
[4,5]
```

They overlap because both contain `4`.

Therefore, the condition must be:

```java
interval[i][0] <= end
```

and **not**:

```java
interval[i][0] < end
```

Using `<` would incorrectly treat:

```text
[1,4]
[4,5]
```

as separate intervals.

---

# Algorithm

1. Sort intervals by their starting values.
2. Initialize `start` and `end` using the first interval.
3. Traverse the remaining intervals.
4. If the current interval overlaps:
   ```java
   interval[i][0] <= end
   ```
   merge it by updating:
   ```java
   end = Math.max(end, interval[i][1]);
   ```
5. Otherwise:
   - Add the current merged interval to the result.
   - Start a new interval.
6. Add the final interval after the loop.
7. Convert the list to a 2D array and return it.

---

# Java Solution

```java
class Solution {

    public int[][] merge(int[][] interval) {

        Arrays.sort(
            interval,
            (a, b) -> Integer.compare(a[0], b[0])
        );

        List<int[]> list = new ArrayList<>();

        int start = interval[0][0];
        int end = interval[0][1];

        for (int i = 1; i < interval.length; i++) {

            // Overlapping interval
            if (interval[i][0] <= end) {

                end = Math.max(
                    end,
                    interval[i][1]
                );
            }

            // Non-overlapping interval
            else {

                list.add(
                    new int[]{start, end}
                );

                start = interval[i][0];
                end = interval[i][1];
            }
        }

        // Add the last interval
        list.add(new int[]{start, end});

        return list.toArray(
            new int[list.size()][]
        );
    }
}
```

---

# Complexity Analysis

Let `n` be the number of intervals.

### Time Complexity

Sorting:

```text
O(n log n)
```

Traversing:

```text
O(n)
```

Overall:

```text
O(n log n)
```

---

### Space Complexity

The result contains the merged intervals:

```text
O(n)
```

excluding the output, the algorithm uses constant extra working space apart from the sorting implementation.

---

# Key Concepts

- Arrays
- Sorting
- Intervals
- Greedy Algorithm
- Two Pointers / Current Interval
- Interval Merging

---

# Constraints

```text
1 <= intervals.length <= 10⁴
intervals[i].length == 2
0 <= starti <= endi <= 10⁴
```

---

# Learning Outcome

The main pattern to remember is:

```text
1. Sort by start
2. Keep current [start, end]
3. If next.start <= end → MERGE
4. Otherwise → SAVE current and start a new interval
```

The core logic is:

```java
if (interval[i][0] <= end) {
    end = Math.max(end, interval[i][1]);
}
else {
    list.add(new int[]{start, end});

    start = interval[i][0];
    end = interval[i][1];
}
```

### Complexity

```text
Time:  O(n log n)
Space: O(n)  // output
```