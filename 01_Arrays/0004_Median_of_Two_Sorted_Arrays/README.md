# 4. Median of Two Sorted Arrays

> **Difficulty:** Hard  
> **Topics:** Array, Sorting

---

## Problem Statement

You are given two sorted integer arrays `nums1` and `nums2` of sizes `m` and `n`.

Return the **median** of the two arrays.

The problem requires an algorithm with an overall time complexity of:

```text
O(log(m + n))
```

---

## Example 1

### Input

```text
nums1 = [1,3]
nums2 = [2]
```

### Output

```text
2.00000
```

### Explanation

The combined sorted array is:

```text
[1,2,3]
```

Since there are 3 elements, the middle element is:

```text
2
```

Therefore, the median is:

```text
2.0
```

---

## Example 2

### Input

```text
nums1 = [1,2]
nums2 = [3,4]
```

### Output

```text
2.50000
```

### Explanation

The combined sorted array is:

```text
[1,2,3,4]
```

There are an even number of elements, so the median is the average of the two middle elements:

```text
(2 + 3) / 2 = 2.5
```

---

# Approach

The current solution uses a straightforward approach:

1. Create an `ArrayList`.
2. Add all elements from `nums1`.
3. Add all elements from `nums2`.
4. Sort the combined list.
5. Find the middle element(s).
6. Return the median.

Because both input arrays are sorted, a more optimized solution could merge them without sorting. However, this implementation directly uses `Collections.sort()`.

---

# Algorithm

### Step 1: Combine the arrays

Add every element from `nums1` and `nums2` into an `ArrayList`.

Example:

```text
nums1 = [1,3]
nums2 = [2]

Combined:

[1,3,2]
```

### Step 2: Sort the list

```text
[1,2,3]
```

### Step 3: Find the median

If the size is odd:

```text
median = middle element
```

If the size is even:

```text
median = (left middle + right middle) / 2
```

---

# Dry Run

### Input

```text
nums1 = [1,2]
nums2 = [3,4]
```

### Combine

```text
[1,2,3,4]
```

### Sort

```text
[1,2,3,4]
```

Size:

```text
4
```

Middle elements:

```text
2 and 3
```

Median:

```text
(2 + 3) / 2
= 2.5
```

Answer:

```text
2.5
```

---

# Complexity Analysis

Let:

```text
N = m + n
```

### Time Complexity

Adding all elements:

```text
O(m + n)
```

Sorting:

```text
O((m + n) log(m + n))
```

Therefore, the overall complexity is:

```text
O((m + n) log(m + n))
```

---

### Space Complexity

The combined `ArrayList` stores all elements:

```text
O(m + n)
```

---

# Java Solution

```java
class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        ArrayList<Integer> arr = new ArrayList<>();

        for (int x : nums1) {
            arr.add(x);
        }

        for (int x : nums2) {
            arr.add(x);
        }

        Collections.sort(arr);

        int n = arr.size();

        if (n % 2 == 0) {

            return (double) (
                arr.get(n / 2 - 1) +
                arr.get(n / 2)
            ) / 2;

        } else {

            return (double) arr.get(n / 2);
        }
    }
}
```

---

# Key Concepts

- Arrays
- ArrayList
- Sorting
- Median
- Even and Odd Length Arrays

---

# Constraints

- `nums1.length == m`
- `nums2.length == n`
- `0 <= m, n <= 1000`
- `1 <= m + n <= 2000`
- `-10⁶ <= nums1[i], nums2[i] <= 10⁶`

---

# Note on Optimal Solution

The problem specifically asks for:

```text
O(log(m + n))
```

The optimal solution uses **Binary Search** on the smaller array to find a partition such that:

```text
left side ≤ right side
```

That solution achieves:

```text
Time:  O(log(min(m,n)))
Space: O(1)
```

The current solution is simpler and easier to understand, but it does not meet the required optimal time complexity.

---

# Learning Outcome

This problem demonstrates how to calculate the median after combining two sorted arrays.

The current approach prioritizes simplicity by combining and sorting the arrays. However, the problem is an important introduction to **Binary Search on Sorted Arrays**, where the optimal solution avoids explicitly merging the arrays.