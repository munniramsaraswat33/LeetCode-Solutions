# 3731. Find Missing Elements

> **Difficulty:** Easy  
> **Topics:** Array, Hash Table

---

## Problem Statement

You are given an integer array `nums` consisting of **unique integers**.

Originally, `nums` contained every integer within a certain range. However, some integers may have been removed.

The **smallest** and **largest** integers of the original range are still present.

Return a **sorted list** of all the missing integers within that range.

If no integers are missing, return an empty list.

---

## Example 1

### Input

```text
nums = [1,4,2,5]
```

### Output

```text
[3]
```

### Explanation

The complete range is:

```text
[1,2,3,4,5]
```

Only **3** is missing.

---

## Example 2

### Input

```text
nums = [7,8,6,9]
```

### Output

```text
[]
```

### Explanation

The complete range is:

```text
[6,7,8,9]
```

No element is missing.

---

## Example 3

### Input

```text
nums = [5,1]
```

### Output

```text
[2,3,4]
```

### Explanation

The complete range is:

```text
[1,2,3,4,5]
```

The missing integers are:

```text
2, 3, 4
```

---

# Approach

Use a **HashMap** (or HashSet) to quickly check whether a number exists.

### Steps

1. Traverse the array once.
2. Store every element in a HashMap.
3. Simultaneously find the minimum and maximum element.
4. Iterate from `min` to `max`.
5. If a number is not present in the HashMap, add it to the answer.

Since the iteration is from the smallest to the largest number, the resulting list is automatically sorted.

---

# Algorithm

1. Create an empty HashMap.
2. Initialize:

```text
min = Integer.MAX_VALUE
max = Integer.MIN_VALUE
```

3. Traverse the array:
   - Insert every number into the HashMap.
   - Update `min` and `max`.
4. Traverse from `min` to `max`.
5. If the current number is not in the HashMap, add it to the answer.
6. Return the list.

---

# Dry Run

Input

```text
nums = [1,4,2,5]
```

### Step 1

HashMap

```text
{1,2,4,5}
```

Minimum

```text
1
```

Maximum

```text
5
```

### Step 2

Traverse

| Number | Present | Result |
|-------:|:-------:|-------|
| 1 | ✅ | - |
| 2 | ✅ | - |
| 3 | ❌ | Add 3 |
| 4 | ✅ | - |
| 5 | ✅ | - |

Answer

```text
[3]
```

---

# Complexity Analysis

### Time Complexity

- Building HashMap → **O(n)**
- Traversing the range → **O(max − min + 1)**

Overall

```text
O(n + (max − min))
```

Since the constraints are small (`nums[i] ≤ 100`), this is efficient.

---

### Space Complexity

```text
O(n)
```

For storing the HashMap.

---

# Java Solution

```java
class Solution {

    public List<Integer> findMissingElements(int[] nums) {

        HashMap<Integer, Boolean> map = new HashMap<>();
        List<Integer> ans = new ArrayList<>();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : nums) {

            map.put(num, true);

            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        for (int i = min; i <= max; i++) {

            if (!map.containsKey(i))
                ans.add(i);
        }

        return ans;
    }
}
```

---

# Key Concepts

- HashMap
- Array Traversal
- Minimum & Maximum
- Membership Lookup

---

# Constraints

- `2 <= nums.length <= 100`
- `1 <= nums[i] <= 100`
- All elements are unique.

---

# Learning Outcome

This problem demonstrates how a **HashMap** enables efficient membership checking while determining missing elements within a range. By identifying the minimum and maximum values first, we only inspect the relevant interval, producing the missing numbers in sorted order with a simple linear scan.