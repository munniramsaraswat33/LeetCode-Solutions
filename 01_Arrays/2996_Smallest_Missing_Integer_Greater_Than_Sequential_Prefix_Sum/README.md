# 2996. Smallest Missing Integer Greater Than Sequential Prefix Sum

> **Difficulty:** Easy  
> **Topics:** Array, Hash Set

---

## Problem Statement

You are given a `0-indexed` integer array `nums`.

A prefix is called **sequential** if every element after the first is exactly `1` greater than the previous element.

For example:

```text
[1,2,3]
```

is sequential because:

```text
2 = 1 + 1
3 = 2 + 1
```

The prefix:

```text
[1,2,3]
```

has sum:

```text
1 + 2 + 3 = 6
```

Return the **smallest integer** that:

1. Is greater than or equal to the sum of the longest sequential prefix.
2. Does not exist in `nums`.

---

## Example 1

### Input

```text
nums = [1,2,3,2,5]
```

### Output

```text
6
```

### Explanation

The longest sequential prefix is:

```text
[1,2,3]
```

Its sum is:

```text
1 + 2 + 3 = 6
```

Since `6` is not present in the array:

```text
answer = 6
```

---

## Example 2

### Input

```text
nums = [3,4,5,1,12,14,13]
```

### Output

```text
15
```

### Explanation

The longest sequential prefix is:

```text
[3,4,5]
```

Its sum is:

```text
3 + 4 + 5 = 12
```

Now check numbers starting from `12`:

```text
12 → present
13 → present
14 → present
15 → missing
```

Therefore:

```text
answer = 15
```

---

# Approach

The solution has two main steps.

### Step 1: Find the Longest Sequential Prefix

Start with:

```text
sum = nums[0]
```

Then traverse the array from index `1`.

If:

```text
nums[i] == nums[i - 1] + 1
```

the prefix is still sequential, so add `nums[i]` to `sum`.

Otherwise, stop because the longest sequential prefix has ended.

---

### Step 2: Find the Smallest Missing Integer

Store all elements of `nums` in a `HashSet`.

Then start from the calculated `sum`.

While the current value exists in the set:

```text
sum++
```

The first value not present in the set is the answer.

---

# Algorithm

1. Initialize:
   ```text
   sum = nums[0]
   ```
2. Traverse from index `1`.
3. If:
   ```text
   nums[i] == nums[i-1] + 1
   ```
   add `nums[i]` to `sum`.
4. Otherwise, stop the traversal.
5. Insert every element of `nums` into a `HashSet`.
6. While `sum` exists in the set:
   ```text
   sum++
   ```
7. Return `sum`.

---

# Dry Run

### Input

```text
nums = [3,4,5,1,12,14,13]
```

### Step 1: Find Sequential Prefix

Initially:

```text
sum = 3
```

Check `4`:

```text
4 == 3 + 1
```

Yes:

```text
sum = 3 + 4 = 7
```

Check `5`:

```text
5 == 4 + 1
```

Yes:

```text
sum = 7 + 5 = 12
```

Check `1`:

```text
1 != 5 + 1
```

Stop.

Longest sequential prefix:

```text
[3,4,5]
```

Sum:

```text
12
```

---

### Step 2: Find Missing Integer

HashSet contains:

```text
{1,3,4,5,12,13,14}
```

Start with:

```text
sum = 12
```

`12` exists → increment:

```text
13
```

`13` exists → increment:

```text
14
```

`14` exists → increment:

```text
15
```

`15` does not exist.

Therefore:

```text
answer = 15
```

---

# Why Use HashSet?

We need to quickly check whether a number exists in `nums`.

A `HashSet` provides average:

```text
O(1)
```

lookup time.

So instead of repeatedly scanning the entire array, we can simply use:

```java
hs.contains(sum)
```

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

Finding the sequential prefix:

```text
O(n)
```

Building the HashSet:

```text
O(n)
```

Checking missing values:

```text
O(n)
```

In the worst case, we may increment `sum` through values that exist in the array.

Therefore, overall:

```text
O(n)
```

---

### Space Complexity

The HashSet stores all elements of the array:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int missingInteger(int[] nums) {

        int sum = nums[0];

        // Find the longest sequential prefix
        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Store all elements for fast lookup
        HashSet<Integer> hs = new HashSet<>();

        for (int num : nums) {
            hs.add(num);
        }

        // Find the smallest missing integer
        while (hs.contains(sum)) {
            sum++;
        }

        return sum;
    }
}
```

---

# Key Concepts

- Array Traversal
- Sequential Prefix
- HashSet
- Fast Lookup
- Prefix Sum
- Missing Element

---

# Constraints

- `1 <= nums.length <= 50`
- `1 <= nums[i] <= 50`

---

# Learning Outcome

This problem combines two simple techniques:

### 1. Sequential Prefix Detection

```java
nums[i] == nums[i - 1] + 1
```

### 2. Fast Missing-Number Detection

```java
HashSet
```

The overall solution runs in:

```text
Time:  O(n)
Space: O(n)
```

The important idea is to first calculate the sum of the **longest sequential prefix**, and then move forward until finding the first number that is not present in the array.