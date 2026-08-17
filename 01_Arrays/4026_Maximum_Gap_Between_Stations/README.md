# 4026. Maximum Gap Between Stations

> **Difficulty:** Medium  
> **Topics:** String, Two Pointers, Greedy

---

## Problem Statement

You are given two strings:

- `skill` — represents the skills required by workers.
- `station` — represents the skills supported by stations.

For every worker `i`, we must assign a station `j` such that:

```text
station[j] == skill[i]
```

The assigned station indices must be strictly increasing:

```text
j0 < j1 < j2 < ... < jn-1
```

The **gap** between two consecutive workers is:

```text
ji - ji-1
```

The gap of an assignment is the maximum of all these differences.

We need to return the **maximum possible gap**.

If there is only one worker, the answer is:

```text
0
```

It is guaranteed that a valid assignment exists.

---

## Key Observation

For every worker, we can find two important positions:

### `left[i]`

The **earliest possible station index** where worker `i` can be assigned while maintaining the order.

### `right[i]`

The **latest possible station index** where worker `i` can be assigned while maintaining the order.

To maximize the gap between worker `i-1` and worker `i`:

- Assign worker `i-1` as early as possible → `left[i-1]`
- Assign worker `i` as late as possible → `right[i]`

Therefore, the maximum possible gap between them is:

```text
right[i] - left[i-1]
```

We calculate this for every consecutive pair and take the maximum.

---

## Approach

We use two passes over the `station` string.

### Step 1: Build `left[]`

Traverse `skill` from left to right.

For each worker, find the first matching station after the previously selected station.

Example:

```text
skill   = "xyz"
station = "xyzz"
```

The earliest positions are:

```text
x -> 0
y -> 1
z -> 2
```

So:

```text
left = [0, 1, 2]
```

---

### Step 2: Build `right[]`

Now traverse `skill` from right to left.

For every worker, find the latest possible matching station while maintaining the required order.

For:

```text
skill   = "xyz"
station = "xyzz"
```

we get:

```text
right = [0, 1, 3]
```

because the last `z` can be assigned to station `3`.

---

### Step 3: Calculate Maximum Gap

For every `i` from `1` to `n-1`:

```text
gap = right[i] - left[i-1]
```

Take the maximum.

---

## Java Solution

```java
class Solution {
    public int maximumGap(String skill, String station) {
        int n = skill.length();
        int m = station.length();

        int left[] = new int[n];
        int right[] = new int[n];

        // Find earliest possible positions
        int j = 0;

        for(int i = 0; i < n; i++){
            while(station.charAt(j) != skill.charAt(i)){
                j++;
            }

            left[i] = j;
            j++;
        }

        // Find latest possible positions
        j = m - 1;

        for(int i = n - 1; i >= 0; i--){
            while(station.charAt(j) != skill.charAt(i)){
                j--;
            }

            right[i] = j;
            j--;
        }

        // Find maximum possible gap
        int ans = 0;

        for(int i = 1; i < n; i++){
            ans = Math.max(ans, right[i] - left[i - 1]);
        }

        return ans;
    }
}
```

---

## Dry Run

### Example 1

```text
skill   = "aa"
station = "aaaa"
```

### Left Array

Traverse from left:

```text
Worker 0 → station 0
Worker 1 → station 1
```

Therefore:

```text
left = [0, 1]
```

### Right Array

Traverse from right:

```text
Worker 1 → station 3
Worker 0 → station 2
```

Therefore:

```text
right = [2, 3]
```

Now calculate:

```text
right[1] - left[0]
= 3 - 0
= 3
```

### Output

```text
3
```

The assignment is:

```text
[0, 3]
```

---

## Dry Run: Example 2

```text
skill   = "xyz"
station = "xyzz"
```

### Left

```text
x → 0
y → 1
z → 2
```

```text
left = [0, 1, 2]
```

### Right

Starting from the end:

```text
z → 3
y → 1
x → 0
```

```text
right = [0, 1, 3]
```

Now:

```text
Gap 1:
right[1] - left[0]
= 1 - 0
= 1

Gap 2:
right[2] - left[1]
= 3 - 1
= 2
```

Maximum:

```text
2
```

### Output

```text
2
```

---

## Why Does `right[i] - left[i-1]` Work?

Suppose we want to maximize the gap between workers `i-1` and `i`.

We want:

```text
stationIndex[i] - stationIndex[i-1]
```

to be as large as possible.

Therefore:

- Put worker `i-1` at the **earliest possible** position.
- Put worker `i` at the **latest possible** position.

Those positions are exactly:

```text
left[i-1]
right[i]
```

So:

```text
maximum gap = right[i] - left[i-1]
```

Checking every consecutive pair gives the global maximum.

---

## Example 3

```text
skill   = "cbc"
station = "cbcdbc"
```

### Earliest Positions

```text
c → 0
b → 1
c → 2
```

```text
left = [0, 1, 2]
```

### Latest Positions

From right to left:

```text
c → 5
b → 4
c → 2
```

```text
right = [2, 4, 5]
```

Now:

```text
right[1] - left[0]
= 4 - 0
= 4

right[2] - left[1]
= 5 - 1
= 4
```

Therefore:

```text
answer = 4
```

---

## Complexity Analysis

Let:

```text
n = skill.length()
m = station.length()
```

The two pointers move through the `station` string only once in each direction.

### Time Complexity

```text
O(n + m)
```

### Space Complexity

```text
O(n)
```

because we store:

```text
left[n]
right[n]
```

---

## Key Concepts

- Two Pointers
- Greedy
- String Traversal
- Earliest Position
- Latest Position
- Maximum Gap

---

## Key Takeaway

The main trick is to calculate:

```text
left[i]  = earliest valid position
right[i] = latest valid position
```

Then for every consecutive pair:

```text
gap = right[i] - left[i-1]
```

and take the maximum.

This gives an efficient:

```text
Time:  O(n + m)
Space: O(n)
```