# 373. Find K Pairs with Smallest Sums

> **Difficulty:** Medium  
> **Topics:** Heap (Priority Queue), Arrays, Binary Heap

---

## Problem Statement

You are given two sorted integer arrays `nums1` and `nums2`, and an integer `k`.

A pair consists of one element from `nums1` and one element from `nums2`.

Return the **k pairs with the smallest sums**.

---

## Example 1

### Input

```text
nums1 = [1,7,11]
nums2 = [2,4,6]
k = 3
```

### Output

```text
[[1,2],[1,4],[1,6]]
```

### Explanation

The pairs sorted by sum are:

| Pair | Sum |
|------|----:|
| (1,2) | 3 |
| (1,4) | 5 |
| (1,6) | 7 |
| (7,2) | 9 |
| (7,4) | 11 |
| (11,2) | 13 |
| (7,6) | 13 |
| (11,4) | 15 |
| (11,6) | 17 |

The first **3** pairs are returned.

---

## Example 2

### Input

```text
nums1 = [1,1,2]
nums2 = [1,2,3]
k = 2
```

### Output

```text
[[1,1],[1,1]]
```

---

# Approach

Since both arrays are already **sorted**, we don't need to generate every possible pair.

We use a **Min Heap (Priority Queue)**.

### Idea

- Initially, pair every element of `nums1` with the **first element** of `nums2`.
- Store these pairs in a Min Heap according to their sum.
- Remove the smallest pair.
- Add it to the answer.
- Insert the next pair from the same row (same index in `nums1`, next index in `nums2`).
- Repeat until we obtain `k` pairs.

This avoids generating all `m × n` pairs.

---

# Algorithm

1. Create a Min Heap ordered by pair sum.
2. Push `(i,0)` for every index of `nums1`.
3. While `k > 0`:
   - Remove the smallest pair.
   - Add it to the answer.
   - If another element exists in the same row of `nums2`,
     push `(i,j+1)` into the heap.
4. Return the result.

---

# Dry Run

Input

```text
nums1 = [1,7,11]
nums2 = [2,4,6]
k = 3
```

### Initial Heap

| Pair | Sum |
|------|----:|
| (1,2) | 3 |
| (7,2) | 9 |
| (11,2) | 13 |

---

### Pop 1

```
(1,2)
```

Answer

```text
[[1,2]]
```

Push

```
(1,4)
```

---

### Heap

| Pair | Sum |
|------|----:|
| (1,4) | 5 |
| (7,2) | 9 |
| (11,2) | 13 |

---

### Pop 2

```
(1,4)
```

Answer

```text
[[1,2],[1,4]]
```

Push

```
(1,6)
```

---

### Heap

| Pair | Sum |
|------|----:|
| (1,6) | 7 |
| (7,2) | 9 |
| (11,2) | 13 |

---

### Pop 3

```
(1,6)
```

Answer

```text
[[1,2],[1,4],[1,6]]
```

Stop because `k = 3`.

---

# Complexity Analysis

Let

- `m = nums1.length`
- `n = nums2.length`

### Time Complexity

Building Heap

```text
O(m log m)
```

Each extraction/insertion

```text
O(log m)
```

Performed `k` times.

Overall

```text
O((m + k) log m)
```

---

### Space Complexity

Priority Queue stores at most `m` elements.

```text
O(m)
```

---

# Java Solution

```java
class Solution {

    class Pair {
        int i;
        int j;
        int sum;

        Pair(int i, int j, int sum) {
            this.i = i;
            this.j = j;
            this.sum = sum;
        }
    }

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {

        List<List<Integer>> ans = new ArrayList<>();

        if (nums1.length == 0 || nums2.length == 0 || k == 0)
            return ans;

        PriorityQueue<Pair> pq =
                new PriorityQueue<>((a, b) -> a.sum - b.sum);

        for (int i = 0; i < nums1.length; i++) {
            pq.offer(new Pair(i, 0, nums1[i] + nums2[0]));
        }

        while (k > 0 && !pq.isEmpty()) {

            Pair cur = pq.poll();

            ans.add(Arrays.asList(nums1[cur.i], nums2[cur.j]));

            if (cur.j + 1 < nums2.length) {
                pq.offer(new Pair(
                        cur.i,
                        cur.j + 1,
                        nums1[cur.i] + nums2[cur.j + 1]
                ));
            }

            k--;
        }

        return ans;
    }
}
```

---

# Key Concepts

- Priority Queue (Min Heap)
- Heap
- Sorted Arrays
- K Smallest Elements
- Greedy

---

# Constraints

- `1 <= nums1.length, nums2.length <= 10⁵`
- `-10⁹ <= nums1[i], nums2[i] <= 10⁹`
- Arrays are sorted.
- `1 <= k <= 10⁴`

---

# Learning Outcome

This problem demonstrates how a **Min Heap** can efficiently generate the **k smallest pairs** without enumerating all possible combinations. By leveraging the sorted nature of the arrays, we only explore the next most promising pair, reducing the complexity from **O(m × n)** to **O((m + k) log m)**.