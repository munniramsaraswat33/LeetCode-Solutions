# 4020. Elevator Requests I

> **Difficulty:** Easy  
> **Topics:** Array, Simulation, Math

---

## Problem Statement

You are given an integer `n` representing the number of floors in a building.

The floors are numbered from:

```text
0 to n - 1
```

You are also given an integer array `requests`, where each element represents a requested floor.

The elevator:

- Starts at floor `0`.
- Moves one floor per second.
- Serves requests in the given order.
- Takes no time if it is already on the requested floor.

Return the **total time** required to serve all requests.

---

## Example 1

### Input

```text
n = 5
requests = [2,1,4,3]
```

### Movement

```text
Start at 0

0 → 2 = 2 seconds
2 → 1 = 1 second
1 → 4 = 3 seconds
4 → 3 = 1 second
```

Total:

```text
2 + 1 + 3 + 1 = 7
```

### Output

```text
7
```

---

## Example 2

### Input

```text
n = 3
requests = [2,0,0]
```

### Movement

```text
0 → 2 = 2 seconds
2 → 0 = 2 seconds
0 → 0 = 0 seconds
```

Total:

```text
2 + 2 + 0 = 4
```

### Output

```text
4
```

---

# Approach

The elevator starts at floor `0`.

For the first request:

```text
time = requests[0] - 0
```

Since requests are non-negative, this is simply:

```java
requests[0]
```

For every following request, the elevator moves from the previous requested floor to the current requested floor.

The required time is the absolute difference:

```java
Math.abs(requests[i] - requests[i - 1])
```

Therefore, the total time is:

```text
distance from 0 to first request
+
distance between every consecutive request
```

---

# Formula

If:

```text
requests = [r1, r2, r3, ..., rk]
```

then:

```text
Total Time =
|r1 - 0|
+ |r2 - r1|
+ |r3 - r2|
+ ...
+ |rk - r(k-1)|
```

---

# Java Solution

```java
class Solution {

    public int elevatorRequests(int n, int[] requests) {

        int ans = requests[0];

        int i = 1;

        while (i != requests.length) {

            ans += Math.abs(requests[i] - requests[i - 1]);

            i++;
        }

        return ans;
    }
}
```

---

# Dry Run

Consider:

```text
requests = [2,1,4,3]
```

Initially:

```text
ans = requests[0]
    = 2
```

The elevator moves:

```text
0 → 2
```

Time:

```text
2
```

---

### Request `1`

```java
Math.abs(1 - 2)
```

```text
= 1
```

Total:

```text
2 + 1 = 3
```

---

### Request `4`

```java
Math.abs(4 - 1)
```

```text
= 3
```

Total:

```text
3 + 3 = 6
```

---

### Request `3`

```java
Math.abs(3 - 4)
```

```text
= 1
```

Final:

```text
6 + 1 = 7
```

Answer:

```text
7
```

---

# Why `Math.abs()`?

The elevator can move either **up or down**.

For example:

```text
2 → 5
```

takes:

```text
|5 - 2| = 3
```

And:

```text
5 → 2
```

also takes:

```text
|2 - 5| = 3
```

Therefore, we use:

```java
Math.abs(requests[i] - requests[i - 1])
```

---

# Important Observation

The value of `n` does not actually need to be used in the calculation.

It only guarantees that every requested floor is valid:

```text
0 <= requests[i] < n
```

The answer depends only on the sequence of requests.

---

# Algorithm

1. Start the elevator at floor `0`.
2. Add the distance from floor `0` to the first request.
3. Traverse the remaining requests.
4. For every request, add the absolute difference from the previous request.
5. Return the total time.

---

# Complexity Analysis

Let:

```text
m = requests.length
```

We traverse the requests exactly once.

### Time Complexity

```text
O(m)
```

### Space Complexity

```text
O(1)
```

Only one variable is used to store the total time.

---

# Key Concepts

- Array Traversal
- Simulation
- Absolute Difference
- Greedy Observation
- Prefix Movement / Consecutive Differences

---

# Key Pattern

The main pattern is:

```text
Current Floor
      ↓
Next Request
      ↓
Distance = |current - next|
      ↓
Add to total
      ↓
Move to next floor
```

For example:

```text
0 → 2 → 1 → 4 → 3
```

Distances:

```text
 2   1   3   1
```

Total:

```text
7
```

---

# Learning Outcome

The key idea is:

> **When an object moves through a sequence of positions, the total movement is the sum of absolute differences between consecutive positions.**

For this problem:

```java
ans = requests[0];

for(int i = 1; i < requests.length; i++){
    ans += Math.abs(requests[i] - requests[i - 1]);
}
```

### Complexity

```text
Time:  O(m)
Space: O(1)
```