# 2948. Make Lexicographically Smallest Array by Swapping Elements

> **Difficulty:** Medium  
> **Topics:** Array, Greedy, Sorting

---

## Problem Statement

You are given an integer array `nums` and an integer `limit`.

You can swap two elements `nums[i]` and `nums[j]` if:

```text
|nums[i] - nums[j]| <= limit
```

You can perform any number of valid swaps.

Return the **lexicographically smallest array** that can be obtained.

### Lexicographical Order

An array `a` is lexicographically smaller than array `b` if at the first position where they differ:

```text
a[i] < b[i]
```

---

## Examples

### Example 1

```text
Input:
nums = [1,5,3,9,8]
limit = 2

Output:
[1,3,5,8,9]
```

### Explanation

After sorting the values:

```text
1, 3, 5, 8, 9
```

The values:

```text
1, 3, 5
```

belong to the same group because adjacent differences are:

```text
3 - 1 = 2
5 - 3 = 2
```

So these values can be rearranged among their original positions.

Similarly:

```text
8, 9
```

form another group.

Assigning the smallest values to the smallest indices produces:

```text
[1,3,5,8,9]
```

---

### Example 2

```text
Input:
nums = [1,7,6,18,2,5]
limit = 3

Output:
[1,2,5,6,7,18]
```

### Explanation

The values:

```text
1, 2, 5, 6, 7
```

can belong to the same connected group through adjacent differences satisfying the limit.

They can therefore be rearranged among their original positions.

The value `18` cannot be connected to this group, so it remains in its own group.

---

# Approach

The important observation is that if sorted values have consecutive differences at most `limit`, they belong to the same **swappable group**.

For example:

```text
values = [1, 3, 5, 6, 10]
limit = 2
```

Differences:

```text
3 - 1 = 2
5 - 3 = 2
6 - 5 = 1
10 - 6 = 4
```

Therefore the groups are:

```text
[1, 3, 5, 6]
[10]
```

The elements inside one group can be rearranged among the original indices belonging to that group.

To obtain the lexicographically smallest result:

1. Sort `(value, original index)` pairs by value.
2. Find connected groups using the `limit`.
3. Collect the original indices of each group.
4. Sort those indices.
5. Assign the group's sorted values to the group's sorted indices.

---

# Why Does Grouping Work?

Suppose sorted values are:

```text
a1 <= a2 <= a3
```

and:

```text
a2 - a1 <= limit
a3 - a2 <= limit
```

Even if:

```text
a3 - a1 > limit
```

the elements can still be connected through valid swaps:

```text
a1 ↔ a2
a2 ↔ a3
```

Therefore, all three values belong to the same swappable component.

This is why we only need to check **adjacent differences after sorting**.

---

# Algorithm

1. Create a 2D array `arr` where:
   - `arr[i][0]` = value
   - `arr[i][1]` = original index
2. Sort `arr` by value.
3. Traverse the sorted array and create groups:
   - Start a group at `start`.
   - Continue while the difference between consecutive values is at most `limit`.
4. For each group:
   - Store all original indices.
   - Sort the original indices.
5. The values are already sorted because `arr` was sorted by value.
6. Assign the smallest value to the smallest index, the second smallest value to the second smallest index, and so on.
7. Continue until all groups are processed.
8. Return `nums`.

---

# Dry Run

Consider:

```text
nums = [1,5,3,9,8]
limit = 2
```

### Step 1: Store Values and Indices

```text
value  index

1      0
5      1
3      2
9      3
8      4
```

---

### Step 2: Sort by Value

After sorting:

```text
value  index

1      0
3      2
5      1
8      4
9      3
```

---

### Step 3: Find Groups

Check consecutive differences.

```text
3 - 1 = 2   <= 2
5 - 3 = 2   <= 2
8 - 5 = 3   >  2
9 - 8 = 1   <= 2
```

Therefore:

```text
Group 1:
values  = [1,3,5]
indices = [0,2,1]

Group 2:
values  = [8,9]
indices = [4,3]
```

---

### Step 4: Sort Original Indices

For Group 1:

```text
indices = [0,1,2]
values  = [1,3,5]
```

Assign:

```text
nums[0] = 1
nums[1] = 3
nums[2] = 5
```

For Group 2:

```text
indices = [3,4]
values  = [8,9]
```

Assign:

```text
nums[3] = 8
nums[4] = 9
```

Final array:

```text
[1,3,5,8,9]
```

---

# Java Solution

```java
class Solution {

    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        int n = nums.length;

        // Store value and original index
        int arr[][] = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        // Sort by value
        Arrays.sort(arr, (a, b) ->
            Integer.compare(a[0], b[0])
        );

        int start = 0;

        while (start < n) {

            int end = start;

            // Find the current group
            while (end + 1 < n &&
                   arr[end + 1][0] - arr[end][0] <= limit) {
                end++;
            }

            int size = end - start + 1;

            // Store original indices of this group
            int indices[] = new int[size];

            for (int i = 0; i < size; i++) {
                indices[i] = arr[start + i][1];
            }

            // Sort original indices
            Arrays.sort(indices);

            // Assign sorted values to sorted indices
            for (int i = 0; i < size; i++) {
                nums[indices[i]] = arr[start + i][0];
            }

            // Move to next group
            start = end + 1;
        }

        return nums;
    }
}
```

---

# Code Explanation

### 1. Store Value and Original Index

```java
int arr[][] = new int[n][2];
```

Each element stores:

```text
arr[i][0] → value
arr[i][1] → original index
```

For example:

```text
nums = [1,5,3]
```

becomes:

```text
[1,0]
[5,1]
[3,2]
```

---

### 2. Sort by Value

```java
Arrays.sort(arr, (a, b) ->
    Integer.compare(a[0], b[0])
);
```

After sorting:

```text
[1,0]
[3,2]
[5,1]
```

Now values are in increasing order.

---

### 3. Find a Group

```java
while(end + 1 < n &&
      arr[end + 1][0] - arr[end][0] <= limit)
```

As long as consecutive sorted values differ by at most `limit`, they belong to the same group.

---

### 4. Collect Original Indices

```java
indices[i] = arr[start + i][1];
```

The group values are sorted, but their original indices may not be.

So we store their original indices separately.

---

### 5. Sort Indices

```java
Arrays.sort(indices);
```

This allows us to place the smallest value at the earliest possible position.

That is the key greedy step for obtaining the lexicographically smallest array.

---

### 6. Assign Values

```java
nums[indices[i]] = arr[start + i][0];
```

Since:

```text
values → sorted
indices → sorted
```

the smallest value is placed at the smallest index.

This produces the lexicographically smallest possible arrangement for that group.

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

Sorting all `(value, index)` pairs:

```text
O(n log n)
```

Each group's indices are also sorted.

Across all groups, the total sorting cost is at most:

```text
O(n log n)
```

Therefore:

```text
O(n log n)
```

### Space Complexity

The `arr` array stores `n` pairs:

```text
O(n)
```

The temporary `indices` array can contain up to `n` elements:

```text
O(n)
```

Therefore:

```text
O(n)
```

---

# Key Concepts

### 1. Greedy Algorithm

For every swappable group, place the smallest values at the smallest indices.

### 2. Sorting

Sorting values allows us to identify connected groups efficiently.

### 3. Connected Groups

If consecutive sorted values differ by at most `limit`, they belong to the same group.

### 4. Original Indices

We must remember where every value originally occurred so that values can be reassigned correctly.

### 5. Lexicographical Minimization

To minimize the array lexicographically, smaller values should be placed at earlier indices whenever the swaps allow it.

---

# Constraints

- `1 <= nums.length <= 100000`
- `1 <= nums[i] <= 1000000000`
- `1 <= limit <= 1000000000`

---

# Learning Outcome

After solving this problem, you should understand:

- How sorting can reveal connected groups of elements.
- How to preserve original indices while sorting values.
- How greedy assignment produces the lexicographically smallest array.
- Why consecutive differences are enough to identify swappable groups.
- How to combine **sorting + grouping + greedy assignment** efficiently.
- How an array problem can primarily be a **Greedy + Sorting** problem rather than a basic Array problem.