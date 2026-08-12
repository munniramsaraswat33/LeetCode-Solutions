# 41. First Missing Positive

> **Difficulty:** Hard  
> **Topics:** Array, Sorting

---

## Problem Statement

Given an unsorted integer array `nums`, return the **smallest positive integer** that is not present in the array.

The optimal solution is required to run in:

```text
O(n) time
O(1) auxiliary space
```

---

## Example 1

### Input

```text
nums = [1,2,0]
```

### Output

```text
3
```

### Explanation

The positive integers present are:

```text
1, 2
```

Therefore, the smallest missing positive integer is:

```text
3
```

---

## Example 2

### Input

```text
nums = [3,4,-1,1]
```

### Output

```text
2
```

### Explanation

The array contains:

```text
1, 3, 4
```

The smallest positive integer missing is:

```text
2
```

---

## Example 3

### Input

```text
nums = [7,8,9,11,12]
```

### Output

```text
1
```

### Explanation

`1` is not present in the array, so it is the smallest missing positive integer.

---

# Approach

The current solution uses **sorting**.

After sorting the array, all positive numbers appear in increasing order.

We maintain a variable:

```text
key = 1
```

which represents the smallest positive integer we are currently looking for.

For every element:

- Ignore negative numbers and zero.
- Ignore numbers smaller than `key`.
- If the current number is exactly `key`, increment `key`.
- If the current number is greater than `key`, then `key` is missing.

At the end, if all required positive integers were found, `key` is the answer.

---

# Algorithm

1. Sort the array.
2. Initialize:
   ```text
   key = 1
   ```
3. Traverse the sorted array.
4. For each number:
   - If `nums[i] < 1`, skip it.
   - If `nums[i] < key`, skip it because it is either a duplicate or already processed.
   - If `nums[i] != key`, return `key`.
   - Otherwise increment `key`.
5. If the traversal finishes, return `key`.

---

# Dry Run

### Input

```text
nums = [3,4,-1,1]
```

After sorting:

```text
[-1,1,3,4]
```

Initially:

```text
key = 1
```

### Step 1

```text
-1
```

Negative → ignore.

```text
key = 1
```

### Step 2

```text
1
```

Matches `key`.

```text
key = 2
```

### Step 3

```text
3
```

Now:

```text
3 != 2
```

Therefore:

```text
2
```

is missing.

Answer:

```text
2
```

---

# Handling Duplicates

Consider:

```text
nums = [1,1,2,2,3]
```

After sorting:

```text
[1,1,2,2,3]
```

The first `1` increases:

```text
key = 2
```

The second `1` is smaller than `key`, so it is ignored.

Then:

```text
2 → key becomes 3
```

Duplicate `2` is ignored.

Then:

```text
3 → key becomes 4
```

Therefore:

```text
answer = 4
```

---

# Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

Sorting takes:

```text
O(n log n)
```

The subsequent traversal takes:

```text
O(n)
```

Therefore, the overall complexity is:

```text
O(n log n)
```

---

### Space Complexity

The array is sorted **in-place**, and only a few variables are used.

```text
O(1)
```

auxiliary space, excluding the internal space used by the sorting implementation.

---

# Java Solution

```java
class Solution {

    public int firstMissingPositive(int[] nums) {

        Arrays.sort(nums);

        int key = 1;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] < 1 || nums[i] < key) {
                continue;
            }

            if (nums[i] != key) {
                return key;
            }

            key++;
        }

        return key;
    }
}
```

---

# Key Concepts

- Array
- Sorting
- Positive Integer Tracking
- Duplicate Handling
- In-Place Processing

---

# Constraints

- `1 <= nums.length <= 10⁵`
- `-2³¹ <= nums[i] <= 2³¹ - 1`

---

# Important Note

The problem specifically asks for:

```text
O(n) time
O(1) auxiliary space
```

The current solution uses sorting, so its actual time complexity is:

```text
O(n log n)
```

Therefore, it does **not satisfy the optimal time-complexity requirement**.

The optimal approach uses the array itself as a form of hash table by placing each positive number `x` at index `x - 1`.

That approach achieves:

```text
Time:  O(n)
Space: O(1)
```

---

# Learning Outcome

This problem is a good example of the difference between a **working solution** and an **optimal solution**.

The sorting approach is simple and easy to understand:

```text
Sort → Find the first missing positive
```

But the required optimal solution requires using the array indices to track which positive numbers are present.

For interview preparation, it is important to understand both approaches.