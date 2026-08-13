# 57. Insert Interval

> **Difficulty:** Medium  
> **Topics:** Array, Intervals

---

## Problem Statement

You are given an array of **non-overlapping intervals** sorted by their starting values.

You are also given a `newInterval`.

Insert `newInterval` into the existing intervals such that:

- The intervals remain sorted by their starting value.
- No intervals overlap.
- Overlapping intervals are merged.

Return the resulting intervals.

---

## Example 1

### Input

```text
intervals = [[1,3],[6,9]]
newInterval = [2,5]
```

### Output

```text
[[1,5],[6,9]]
```

### Explanation

`[2,5]` overlaps with `[1,3]`.

Merge them:

```text
[1,3] + [2,5]
      ↓
[1,5]
```

The final result is:

```text
[[1,5],[6,9]]
```

---

## Example 2

### Input

```text
intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]
newInterval = [4,8]
```

### Output

```text
[[1,2],[3,10],[12,16]]
```

### Explanation

`[4,8]` overlaps with:

```text
[3,5]
[6,7]
[8,10]
```

All overlapping intervals are merged:

```text
[3,5]
[4,8]
[6,7]
[8,10]
```

becomes:

```text
[3,10]
```

Therefore:

```text
[[1,2],[3,10],[12,16]]
```

---

# Approach

Since the intervals are already sorted by their starting values, we can process them from left to right.

For every interval `slot`, there are **three possible cases**.

---

## Case 1: New Interval Comes Before Current Interval

Condition:

```java
newInterval[1] < slot[0]
```

This means:

```text
newInterval ends before slot starts
```

For example:

```text
newInterval = [2,4]
slot        = [6,9]
```

There is no overlap.

So we add `newInterval` to the result:

```java
result.add(newInterval);
```

Then the current `slot` becomes the interval that we need to process next:

```java
newInterval = slot;
```

This is a clever part of the solution because instead of creating another variable, we simply continue using `newInterval`.

---

## Case 2: Current Interval Comes Before New Interval

Condition:

```java
slot[1] < newInterval[0]
```

This means:

```text
slot ends before newInterval starts
```

For example:

```text
slot        = [1,2]
newInterval = [4,8]
```

There is no overlap.

So we can safely add `slot`:

```java
result.add(slot);
```

---

## Case 3: Intervals Overlap

If neither of the previous conditions is true, the intervals overlap.

For example:

```text
slot        = [3,5]
newInterval = [4,8]
```

Since they overlap, merge them.

The new start should be the smaller start:

```java
newInterval[0] =
    Math.min(newInterval[0], slot[0]);
```

The new end should be the larger end:

```java
newInterval[1] =
    Math.max(newInterval[1], slot[1]);
```

So:

```text
[4,8] + [3,5]
     ↓
[3,8]
```

---

# Algorithm

1. Create an empty result list.
2. Traverse every interval `slot`.
3. If `newInterval` comes before `slot`:
   - Add `newInterval`.
   - Set `newInterval = slot`.
4. Else if `slot` comes before `newInterval`:
   - Add `slot`.
5. Otherwise, the intervals overlap:
   - Merge them into `newInterval`.
6. After the loop, add the final `newInterval`.
7. Convert the list into a 2D array and return it.

---

# Dry Run

### Input

```text
intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]
newInterval = [4,8]
```

---

### Step 1

```text
slot = [1,2]
newInterval = [4,8]
```

Check:

```text
slot[1] < newInterval[0]
2 < 4
```

True.

So:

```text
result = [[1,2]]
```

---

### Step 2

```text
slot = [3,5]
newInterval = [4,8]
```

They overlap.

Merge:

```text
start = min(4,3) = 3
end   = max(8,5) = 8
```

Now:

```text
newInterval = [3,8]
```

---

### Step 3

```text
slot = [6,7]
newInterval = [3,8]
```

They overlap.

Merge:

```text
start = min(3,6) = 3
end   = max(8,7) = 8
```

Still:

```text
newInterval = [3,8]
```

---

### Step 4

```text
slot = [8,10]
newInterval = [3,8]
```

They overlap because they share the point `8`.

Merge:

```text
start = min(3,8) = 3
end   = max(8,10) = 10
```

Now:

```text
newInterval = [3,10]
```

---

### Step 5

```text
slot = [12,16]
newInterval = [3,10]
```

Now:

```text
newInterval[1] < slot[0]

10 < 12
```

True.

So add:

```text
[3,10]
```

and make:

```text
newInterval = [12,16]
```

---

### After the Loop

Add the final interval:

```text
[12,16]
```

Final result:

```text
[[1,2],[3,10],[12,16]]
```

---

# Understanding the Three Conditions

The entire problem can be remembered using these three cases:

```text
1. newInterval before slot

   [new]       [slot]
      ↓
   add new


2. slot before newInterval

   [slot]       [new]
      ↓
   add slot


3. Overlap

   [------]
      [------]
         ↓
      merge
```

In code:

```java
if (newInterval[1] < slot[0]) {

    // newInterval comes before slot

}
else if (slot[1] < newInterval[0]) {

    // slot comes before newInterval

}
else {

    // overlap → merge
}
```

---

# Important Detail: Why `<` and Not `<=`?

The problem says two intervals overlap if they share **at least one point**.

Therefore:

```text
[1,5]
[5,8]
```

are considered overlapping.

Because they both contain:

```text
5
```

So we must merge them:

```text
[1,8]
```

That's why the conditions use:

```java
newInterval[1] < slot[0]
```

and:

```java
slot[1] < newInterval[0]
```

rather than `<=`.

---

# Java Solution

```java
class Solution {

    public int[][] insert(int[][] intervals, int[] newInterval) {

        List<int[]> result = new ArrayList<>();

        for (int[] slot : intervals) {

            // newInterval comes before slot
            if (newInterval[1] < slot[0]) {

                result.add(newInterval);

                newInterval = slot;
            }

            // slot comes before newInterval
            else if (slot[1] < newInterval[0]) {

                result.add(slot);
            }

            // Overlapping intervals
            else {

                newInterval[0] =
                    Math.min(newInterval[0], slot[0]);

                newInterval[1] =
                    Math.max(newInterval[1], slot[1]);
            }
        }

        // Add the last interval
        result.add(newInterval);

        return result.toArray(new int[result.size()][]);
    }
}
```

---

# Complexity Analysis

Let `n` be the number of intervals.

### Time Complexity

We traverse the intervals exactly once:

```text
O(n)
```

No sorting is required because the input is already sorted.

---

### Space Complexity

The result list stores the output intervals:

```text
O(n)
```

excluding the output, the algorithm uses:

```text
O(1)
```

extra working space.

---

# Key Concepts

- Arrays
- Intervals
- Interval Merging
- Greedy Approach
- Sorting Property
- Two-Pointer-like Traversal

---

# Constraints

- `0 <= intervals.length <= 10⁴`
- `intervals[i].length == 2`
- `0 <= starti <= endi <= 10⁵`
- Intervals are sorted by starting value.
- `0 <= start <= end <= 10⁵`

---

# Learning Outcome

This problem teaches how to process **sorted intervals without explicitly sorting or using complicated data structures**.

The most important pattern is:

```text
Before → add it
After  → add it
Overlap → merge it
```

In code:

```java
if (newInterval[1] < slot[0]) {
    // Before
}
else if (slot[1] < newInterval[0]) {
    // After
}
else {
    // Overlap
}
```

The key merging operation is:

```java
newInterval[0] = Math.min(newInterval[0], slot[0]);
newInterval[1] = Math.max(newInterval[1], slot[1]);
```

### Complexity

```text
Time:  O(n)
Space: O(n)   // output
```