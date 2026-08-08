# 1. Two Sum

> **Difficulty:** Easy  
> **Topics:** Array, Hash Table

---

## Problem Statement

You are given an integer array `nums` and an integer `target`.

Return the **indices of the two numbers** such that their sum is equal to `target`.

You may assume:

- Each input has exactly one solution.
- The same element cannot be used twice.
- The answer can be returned in any order.

---

## Example 1

### Input

```text
nums = [2,7,11,15]
target = 9
```

### Output

```text
[0,1]
```

### Explanation

```text
nums[0] + nums[1]
= 2 + 7
= 9
```

Therefore, the answer is:

```text
[0,1]
```

---

## Example 2

### Input

```text
nums = [3,2,4]
target = 6
```

### Output

```text
[1,2]
```

### Explanation

```text
nums[1] + nums[2]
= 2 + 4
= 6
```

---

## Example 3

### Input

```text
nums = [3,3]
target = 6
```

### Output

```text
[0,1]
```

---

# Approach

A brute-force solution would check every possible pair, resulting in:

```text
O(n²)
```

However, we can solve the problem in **O(n)** using a **HashMap**.

### Key Idea

For every number `nums[i]`, calculate the value that we need:

```text
complement = target - nums[i]
```

If this complement has already been seen, we have found the required pair.

The HashMap stores:

```text
number → index
```

This allows us to find the required complement in approximately **O(1)** time.

---

# Algorithm

1. Create a HashMap.
2. Traverse the array from left to right.
3. For every element:
   - Calculate:

```text
complement = target - nums[i]
```

4. Check whether `complement` already exists in the HashMap.
5. If it exists:
   - Return the stored index and current index.
6. Otherwise:
   - Store the current number and its index.
7. Return an empty array if no pair is found.

---

# Dry Run

### Input

```text
nums = [2,7,11,15]
target = 9
```

### Step 1

Current number:

```text
2
```

Complement:

```text
9 - 2 = 7
```

`7` is not in the HashMap.

Store:

```text
2 → 0
```

---

### Step 2

Current number:

```text
7
```

Complement:

```text
9 - 7 = 2
```

`2` exists in the HashMap.

```text
2 → index 0
```

Current index:

```text
1
```

Therefore:

```text
[0,1]
```

---

# Why HashMap Works

Instead of searching the entire array for every required number, we store previously visited numbers in a HashMap.

For each element:

```text
Required value = target - current value
```

If the required value was already encountered, we immediately have the answer.

This reduces the time complexity from:

```text
O(n²)
```

to:

```text
O(n)
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

The array is traversed only once, and HashMap lookup takes average **O(1)** time.

---

### Space Complexity

```text
O(n)
```

In the worst case, the HashMap stores all elements before finding the answer.

---

# Java Solution

```java
class Solution {

    public int[] twoSum(int[] nums, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            int complement = target - nums[i];

            Integer index = map.get(complement);

            if (index != null) {
                return new int[]{index, i};
            }

            map.put(nums[i], i);
        }

        return new int[]{};
    }
}
```

---

# Key Concepts

- Array
- HashMap
- Complement Technique
- One-Pass Algorithm
- Hash Table Lookup

---

# Constraints

- `2 <= nums.length <= 10⁴`
- `-10⁹ <= nums[i] <= 10⁹`
- `-10⁹ <= target <= 10⁹`
- Exactly one valid answer exists.

---

# Learning Outcome

This problem is one of the most important introductory problems for understanding **HashMap-based optimization**.

The key technique is to transform:

```text
nums[i] + nums[j] = target
```

into:

```text
nums[j] = target - nums[i]
```

By storing previously visited values in a HashMap, we can find the required pair in **O(n)** time instead of **O(n²)**.