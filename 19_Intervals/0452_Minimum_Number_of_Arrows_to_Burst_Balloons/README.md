# 452. Minimum Number of Arrows to Burst Balloons

> **Difficulty:** Medium  
> **Topics:** Array, Greedy, Sorting, Intervals

---

## Problem Statement

There are some spherical balloons taped onto a flat wall. Each balloon is represented by an interval:

```text
[xstart, xend]
```

where `xstart` and `xend` represent the horizontal diameter of the balloon.

An arrow can be shot vertically upward from any point on the x-axis.

If the arrow is shot at position `x`, it bursts every balloon whose interval contains `x`.

Return the **minimum number of arrows** that must be shot to burst all the balloons.

---

## Example 1

### Input

```text
points = [[10,16],[2,8],[1,6],[7,12]]
```

### Output

```text
2
```

### Explanation

We can shoot one arrow at:

```text
x = 6
```

which bursts:

```text
[2,8]
[1,6]
```

Another arrow can be shot at:

```text
x = 11
```

which bursts:

```text
[10,16]
[7,12]
```

Therefore:

```text
Minimum arrows = 2
```

---

## Example 2

### Input

```text
points = [[1,2],[3,4],[5,6],[7,8]]
```

### Output

```text
4
```

### Explanation

None of the balloons overlap.

Therefore, each balloon requires a separate arrow.

```text
Minimum arrows = 4
```

---

## Example 3

### Input

```text
points = [[1,2],[2,3],[3,4],[4,5]]
```

### Output

```text
2
```

### Explanation

An arrow at:

```text
x = 2
```

bursts:

```text
[1,2]
[2,3]
```

Another arrow at:

```text
x = 4
```

bursts:

```text
[3,4]
[4,5]
```

Therefore:

```text
Minimum arrows = 2
```

---

# Approach

Use a **Greedy Algorithm**.

First, sort the balloons according to their starting point.

Then maintain the rightmost position where the current arrow can be shot while still bursting all overlapping balloons.

For the first balloon:

```text
end = points[0][1]
```

This means we initially plan to shoot an arrow at the right endpoint of the first balloon.

For every next balloon:

### Case 1: No Overlap

If:

```text
points[i][0] > end
```

then the current balloon starts after the possible shooting position.

Therefore, the current arrow cannot burst it.

We need a new arrow:

```java
count++;
end = points[i][1];
```

---

### Case 2: Overlap

If:

```text
points[i][0] <= end
```

the current balloon overlaps with the previous balloons.

We can still use the same arrow.

However, the arrow position must be inside **both** intervals.

Therefore, update the possible shooting position to the smaller endpoint:

```java
end = Math.min(end, points[i][1]);
```

This keeps the intersection of all overlapping balloons.

---

# Algorithm

1. Sort `points` by the starting position of each balloon.
2. Set:
   ```text
   count = 1
   ```
3. Set:
   ```text
   end = points[0][1]
   ```
4. Traverse the remaining balloons.
5. If:
   ```text
   points[i][0] > end
   ```
   then there is no overlap:
   - Increment `count`.
   - Reset `end`.
6. Otherwise, the balloons overlap:
   ```text
   end = min(end, points[i][1])
   ```
7. Return `count`.

---

# Dry Run

Input:

```text
points = [[10,16],[2,8],[1,6],[7,12]]
```

### Step 1: Sort by Starting Point

After sorting:

```text
[[1,6],[2,8],[7,12],[10,16]]
```

---

### Step 2: First Balloon

```text
[1,6]
```

Initially:

```text
count = 1
end = 6
```

We can shoot the first arrow somewhere within:

```text
[1,6]
```

---

### Step 3: Balloon `[2,8]`

Check:

```text
2 <= 6
```

So the balloons overlap.

Update:

```text
end = min(6,8)
    = 6
```

The arrow can still be shot at:

```text
x = 6
```

---

### Step 4: Balloon `[7,12]`

Check:

```text
7 > 6
```

There is no overlap.

Therefore, we need another arrow:

```text
count = 2
```

Reset:

```text
end = 12
```

---

### Step 5: Balloon `[10,16]`

Check:

```text
10 <= 12
```

The balloons overlap.

Update:

```text
end = min(12,16)
    = 12
```

So the second arrow can be shot at:

```text
x = 12
```

Final:

```text
count = 2
```

Answer:

```text
2
```

---

# Understanding the Code

## Sort Intervals

```java
Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
```

We sort the balloons by their starting position.

For example:

```text
[10,16]
[2,8]
[1,6]
[7,12]
```

becomes:

```text
[1,6]
[2,8]
[7,12]
[10,16]
```

This allows us to process the intervals from left to right.

---

## Initialize Arrow Count

```java
int count = 1;
```

There is at least one balloon, so at least one arrow is required.

---

## Initial Shooting Position

```java
int end = points[0][1];
```

Initially, we choose the right endpoint of the first balloon as the possible arrow position.

---

## Check for No Overlap

```java
if(points[i][0] > end){
    count++;
    end = points[i][1];
}
```

If the current balloon starts after the current valid shooting position:

```text
current start > end
```

then the current arrow cannot burst it.

So we need a new arrow.

---

## Handle Overlapping Balloons

```java
else{
    end = Math.min(end, points[i][1]);
}
```

When balloons overlap, the arrow must lie inside their common intersection.

Suppose:

```text
[1,10]
[2,7]
```

The common area is:

```text
[2,7]
```

The rightmost possible position becomes:

```text
min(10,7) = 7
```

Keeping the rightmost possible point is useful because it gives the best chance of intersecting future balloons.

---

# Why Greedy Works

When several balloons overlap, one arrow can burst all of them if there is a common point.

We always keep the **smallest right endpoint** among the overlapping balloons.

Why?

Because this is the latest position where the current arrow can still burst all current balloons.

For example:

```text
[1,10]
[2,8]
[3,6]
```

The common intersection is:

```text
[3,6]
```

The best shooting position is:

```text
x = 6
```

If another balloon starts before `6`, the same arrow can potentially burst it.

Therefore, keeping the smallest right endpoint gives the optimal greedy choice.

---

# Important Condition

The code uses:

```java
if(points[i][0] > end)
```

not:

```java
if(points[i][0] >= end)
```

This is important because endpoints are **inclusive**.

For example:

```text
[1,2]
[2,3]
```

Both balloons contain:

```text
x = 2
```

So one arrow at `x = 2` can burst both.

Therefore:

```text
2 <= end
```

means they overlap.

---

# Greedy Pattern

This problem follows the common **Interval Greedy** pattern:

```text
Sort intervals
      ↓
Keep current valid range
      ↓
If intervals overlap
      ↓
Shrink the common range
      ↓
If no overlap
      ↓
Take a new arrow
```

The key operation is:

```java
end = Math.min(end, points[i][1]);
```

This keeps the intersection of overlapping intervals.

---

# Complexity Analysis

Let:

```text
n = points.length
```

### Time Complexity

Sorting the intervals takes:

```text
O(n log n)
```

Traversing the intervals takes:

```text
O(n)
```

Therefore:

```text
O(n log n)
```

---

### Space Complexity

The algorithm itself uses:

```text
O(1)
```

extra space apart from the sorting implementation.

---

# Java Solution

```java
class Solution {

    public int findMinArrowShots(int[][] points) {

        Arrays.sort(points,
            (a, b) -> Integer.compare(a[0], b[0]));

        int count = 1;
        int end = points[0][1];

        for(int i = 1; i < points.length; i++){

            if(points[i][0] > end){

                count++;
                end = points[i][1];

            }
            else{

                end = Math.min(
                    end,
                    points[i][1]
                );
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
- Interval Intersection
- Range Overlap
- Minimum Number of Arrows

---

# Constraints

- `1 <= points.length <= 10^5`
- `points[i].length == 2`
- `-2^31 <= xstart < xend <= 2^31 - 1`

---

# Learning Outcome

This problem demonstrates the **Greedy Interval** technique.

The main idea is:

```text
Sort intervals by starting point
          ↓
Keep the smallest ending point
          ↓
Overlapping interval
          ↓
Update end = min(end, current end)
          ↓
Non-overlapping interval
          ↓
Use a new arrow
```

The most important line is:

```java
end = Math.min(end, points[i][1]);
```

It keeps the possible shooting position inside the intersection of all currently overlapping balloons.

The solution achieves:

```text
Time  → O(n log n)
Space → O(1)
```