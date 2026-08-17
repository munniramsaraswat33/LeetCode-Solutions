# 4024. Nearest Available Drone

> **Difficulty:** Easy  
> **Topics:** Array, Math, Simulation

---

## Problem Statement

You are given a 2D integer array `drones`.

Each drone is represented as:

```text
drones[i] = [xi, yi, rangei]
```

where:

- `xi` = x-coordinate of the drone
- `yi` = y-coordinate of the drone
- `rangei` = maximum Manhattan distance the drone can travel

You are also given:

```text
target = [tx, ty]
```

A drone can reach the target if its **Manhattan distance** from the target is less than or equal to its range.

Return the **index of the reachable drone with the minimum Manhattan distance**.

If multiple drones have the same minimum distance, return the **smallest index**.

If no drone can reach the target, return:

```text
-1
```

---

## Manhattan Distance

The Manhattan distance between:

```text
(x1, y1)
```

and:

```text
(x2, y2)
```

is:

```text
|x1 - x2| + |y1 - y2|
```

For a drone:

```text
[x, y, range]
```

and target:

```text
[tx, ty]
```

the distance is:

```java
Math.abs(x - tx) + Math.abs(y - ty)
```

---

## Example 1

### Input

```text
drones = [[0,0,8],[2,2,9]]
target = [3,4]
```

### Drone 0

```text
Distance = |0 - 3| + |0 - 4|
         = 3 + 4
         = 7
```

Range:

```text
8
```

Since:

```text
7 <= 8
```

Drone `0` can reach the target.

---

### Drone 1

```text
Distance = |2 - 3| + |2 - 4|
         = 1 + 2
         = 3
```

Range:

```text
9
```

Since:

```text
3 <= 9
```

Drone `1` can reach the target.

Drone `1` has the smaller distance.

### Output

```text
1
```

---

## Example 2

### Input

```text
drones = [[2,1,5],[4,4,5],[6,6,8]]
target = [5,5]
```

Distances:

```text
Drone 0:
|2-5| + |1-5| = 7
```

Cannot reach because:

```text
7 > 5
```

---

```text
Drone 1:
|4-5| + |4-5| = 2
```

Can reach.

---

```text
Drone 2:
|6-5| + |6-5| = 2
```

Can reach.

Both drones `1` and `2` have distance `2`.

The smaller index is:

```text
1
```

### Output

```text
1
```

---

## Example 3

### Input

```text
drones = [[4,4,5]]
target = [8,6]
```

Distance:

```text
|4-8| + |4-6|
= 4 + 2
= 6
```

Range:

```text
5
```

Since:

```text
6 > 5
```

the drone cannot reach the target.

### Output

```text
-1
```

---

# Approach

We simply check every drone one by one.

For each drone:

1. Calculate its Manhattan distance from the target.
2. Check whether the distance is within its range.
3. If it can reach the target, compare its distance with the best distance found so far.
4. Store its index if it is closer.

Because we process drones from left to right, if two drones have the same distance, we automatically keep the smaller index.

---

# Java Solution

```java
class Solution {

    public int nearestDrone(int[][] drone, int[] target) {

        int val = -1;
        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < drone.length; i++) {

            int min = Math.abs(drone[i][0] - target[0])
                    + Math.abs(drone[i][1] - target[1]);

            // Drone cannot reach target
            if (min > drone[i][2]) {
                continue;
            }

            // Found a closer reachable drone
            if (min < ans) {
                val = i;
                ans = min;
            }
        }

        return val;
    }
}
```

---

# Dry Run

Consider:

```text
drones = [
    [2,1,5],
    [4,4,5],
    [6,6,8]
]

target = [5,5]
```

Initially:

```text
val = -1
ans = Integer.MAX_VALUE
```

---

### Drone 0

```text
distance = |2-5| + |1-5|
         = 3 + 4
         = 7
```

Range:

```text
5
```

Since:

```text
7 > 5
```

skip it.

---

### Drone 1

```text
distance = |4-5| + |4-5|
         = 1 + 1
         = 2
```

Range:

```text
5
```

It can reach.

Since:

```text
2 < Integer.MAX_VALUE
```

update:

```text
val = 1
ans = 2
```

---

### Drone 2

```text
distance = |6-5| + |6-5|
         = 1 + 1
         = 2
```

Range:

```text
8
```

It can reach.

But:

```text
2 < 2
```

is false.

So we don't update the answer.

Final:

```text
val = 1
```

### Answer

```text
1
```

---

# Handling Ties

Suppose:

```text
Drone 1 → distance = 2
Drone 2 → distance = 2
```

We use:

```java
if (min < ans)
```

instead of:

```java
if (min <= ans)
```

When Drone `1` is processed:

```text
ans = 2
val = 1
```

When Drone `2` is processed:

```text
min = 2
ans = 2
```

Since:

```text
2 < 2
```

is false, the answer remains:

```text
1
```

Therefore, the **smallest index is automatically preserved**.

---

# Important Code

### Manhattan Distance

```java
int min = Math.abs(drone[i][0] - target[0])
        + Math.abs(drone[i][1] - target[1]);
```

### Check Reachability

```java
if (min > drone[i][2]) {
    continue;
}
```

### Update Best Drone

```java
if (min < ans) {
    val = i;
    ans = min;
}
```

---

# Algorithm

```text
Initialize:
bestIndex = -1
bestDistance = infinity

For every drone:
        ↓
Calculate Manhattan distance
        ↓
Is distance > range?
      /       \
    YES        NO
     ↓          ↓
   Skip     Compare distance
                ↓
        Is it smaller?
           /      \
         YES       NO
          ↓         ↓
       Update     Keep old
       
Return bestIndex
```

---

# Complexity Analysis

Let:

```text
n = number of drones
```

We check each drone exactly once.

### Time Complexity

```text
O(n)
```

### Space Complexity

```text
O(1)
```

Only two variables are used to track the best result.

---

# Why This Approach Works

Every drone is independent.

There is no need to sort the drones or use any advanced data structure.

We simply need the reachable drone with the smallest distance, so a single traversal is sufficient.

Because we process indices in increasing order and only update when:

```java
min < ans
```

ties automatically keep the smaller index.

---

# Key Concepts

- Array Traversal
- Manhattan Distance
- Absolute Difference
- Simulation
- Greedy Selection
- Tie Breaking

---

# Learning Outcome

The main idea is:

> **Traverse all candidates, filter out invalid ones, and keep the best valid candidate according to the required comparison.**

For this problem:

```text
Valid candidate → minimum distance
Tie → minimum index
```

The key condition is:

```java
if (min < ans)
```

which handles both the minimum-distance requirement and the smallest-index tie-break naturally.

### Complexity

```text
Time:  O(n)
Space: O(1)
```