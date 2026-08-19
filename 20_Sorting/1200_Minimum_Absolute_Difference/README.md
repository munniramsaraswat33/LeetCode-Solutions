# 1200. Minimum Absolute Difference

> **Difficulty:** Easy  
> **Topics:** Array, Sorting

---

## Problem Statement

Given an array of **distinct integers** `arr`, find all pairs of elements having the **minimum absolute difference** among any two elements.

For every pair `[a, b]`:

- `a` and `b` must come from `arr`.
- `a < b`.
- `b - a` must be equal to the minimum absolute difference.
- The resulting pairs must be returned in ascending order.

---

## Example 1

### Input

```text
arr = [4,2,1,3]
```

### Output

```text
[[1,2],[2,3],[3,4]]
```

### Explanation

After sorting:

```text
[1,2,3,4]
```

Adjacent differences:

```text
2 - 1 = 1
3 - 2 = 1
4 - 3 = 1
```

The minimum difference is:

```text
1
```

Therefore all three pairs are valid:

```text
[1,2]
[2,3]
[3,4]
```

---

## Example 2

### Input

```text
arr = [1,3,6,10,15]
```

After sorting:

```text
[1,3,6,10,15]
```

Differences:

```text
3 - 1   = 2
6 - 3   = 3
10 - 6  = 4
15 - 10 = 5
```

The minimum difference is:

```text
2
```

Therefore:

```text
[[1,3]]
```

---

## Example 3

### Input

```text
arr = [3,8,-10,23,19,-4,-14,27]
```

After sorting:

```text
[-14,-10,-4,3,8,19,23,27]
```

Adjacent differences:

```text
4
6
7
5
11
4
4
```

The minimum difference is:

```text
4
```

Therefore the valid pairs are:

```text
[-14,-10]
[19,23]
[23,27]
```

---

# Approach

The key observation is:

> After sorting the array, the minimum absolute difference must occur between two **adjacent elements**.

For example:

```text
[1,4,6,10]
```

If we compare non-adjacent elements:

```text
6 - 1 = 5
10 - 1 = 9
10 - 4 = 6
```

there is always an element between them that can produce an equal or smaller difference.

Therefore, after sorting, we only need to check:

```text
arr[i] - arr[i-1]
```

---

# Why Sorting Helps

Consider:

```text
arr = [4,2,1,3]
```

Without sorting, finding the minimum difference would require comparing many pairs.

After sorting:

```text
[1,2,3,4]
```

we only need to check:

```text
1 → 2
2 → 3
3 → 4
```

This reduces the problem to checking adjacent elements.

---

# Maintaining the Minimum Difference

We maintain:

```java
int ans = Integer.MAX_VALUE;
```

This stores the smallest difference found so far.

For every adjacent pair:

```java
int diff = arr[j] - arr[j-1];
```

There are three possibilities.

---

## Case 1: New Difference Is Smaller

If:

```java
diff < ans
```

we found a new minimum.

The previously stored pairs are no longer valid.

Therefore:

```java
ans = diff;
list.clear();
list.add(Arrays.asList(arr[j-1], arr[j]));
```

Example:

```text
Current minimum = 5

New difference = 2
```

Then:

```text
minimum = 2
```

and we clear the previous result.

---

## Case 2: Difference Is Equal

If:

```java
diff == ans
```

then this pair also has the minimum difference.

So we add it:

```java
list.add(Arrays.asList(arr[j-1], arr[j]));
```

For example:

```text
[1,2,3,4]
```

If:

```text
ans = 1
```

then:

```text
[1,2]
[2,3]
[3,4]
```

are all valid.

---

## Case 3: Difference Is Larger

If:

```text
diff > ans
```

this pair cannot be part of the answer.

We simply ignore it.

---

# Dry Run

### Input

```text
arr = [4,2,1,3]
```

### Step 1: Sort

```text
[1,2,3,4]
```

Initial:

```text
ans = ∞
list = []
```

---

### Compare `1` and `2`

```text
diff = 2 - 1 = 1
```

Since:

```text
1 < ∞
```

we update:

```text
ans = 1
list = [[1,2]]
```

---

### Compare `2` and `3`

```text
diff = 3 - 2 = 1
```

Since:

```text
diff == ans
```

add:

```text
list = [[1,2],[2,3]]
```

---

### Compare `3` and `4`

```text
diff = 4 - 3 = 1
```

Again:

```text
diff == ans
```

Add:

```text
list = [[1,2],[2,3],[3,4]]
```

Final:

```text
ans = 1
```

```text
result = [[1,2],[2,3],[3,4]]
```

---

# Important Observation

You don't need to compare every pair.

A brute-force solution would check:

```text
arr[0] with arr[1], arr[2], ...
arr[1] with arr[2], arr[3], ...
...
```

which takes:

```text
O(n²)
```

After sorting, only adjacent elements need to be checked.

Therefore:

```text
Sorting       → O(n log n)
One traversal → O(n)
```

Overall:

```text
O(n log n)
```

---

# Java Solution

```java
class Solution {

    public List<List<Integer>> minimumAbsDifference(int[] arr) {

        Arrays.sort(arr);

        List<List<Integer>> list = new ArrayList<>();

        int ans = Integer.MAX_VALUE;

        for (int j = 1; j < arr.length; j++) {

            int diff = arr[j] - arr[j - 1];

            // Found a smaller difference
            if (diff < ans) {

                ans = diff;

                list.clear();

                list.add(
                    Arrays.asList(arr[j - 1], arr[j])
                );
            }

            // Found another pair with the same minimum
            else if (diff == ans) {

                list.add(
                    Arrays.asList(arr[j - 1], arr[j])
                );
            }
        }

        return list;
    }
}
```

---

# Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

Sorting takes:

```text
O(n log n)
```

Traversing the sorted array takes:

```text
O(n)
```

Therefore:

```text
O(n log n)
```

---

### Space Complexity

The result list can contain up to `O(n)` pairs.

Apart from the output, the algorithm uses:

```text
O(1)
```

extra space, ignoring the space used internally by the sorting implementation.

---

# Key Concepts

- Arrays
- Sorting
- Adjacent Elements
- Minimum Difference
- Greedy Observation
- Result Reset

---

# Constraints

```text
2 <= arr.length <= 10⁵
-10⁶ <= arr[i] <= 10⁶
```

All elements are distinct.

---

# Learning Outcome

The main idea of this problem is:

> **Sort first, then compare adjacent elements.**

Once the array is sorted:

```text
arr[i] - arr[i-1]
```

is enough to find the minimum absolute difference.

The important pattern is:

```java
if (diff < ans) {
    ans = diff;
    list.clear();
    list.add(pair);
}
else if (diff == ans) {
    list.add(pair);
}
```

This pattern is useful whenever you need to:

1. Find a minimum value.
2. Store all elements/pairs producing that minimum.
3. Clear previous results when a smaller value is found.

### Complexity

```text
Time:  O(n log n)
Space: O(n)  // output
```