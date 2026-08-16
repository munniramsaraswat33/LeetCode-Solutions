# 215. Kth Largest Element in an Array

> **Difficulty:** Medium  
> **Topics:** Array, Heap, Priority Queue

---

## Problem Statement

You are given an integer array `nums` and an integer `k`.

Return the **kth largest element** in the array.

The answer is based on sorted order, so duplicate values are counted separately.

For example:

```text
nums = [3,2,3,1,2,4,5,5,6]
k = 4
```

Sorted in descending order:

```text
[6,5,5,4,3,3,2,2,1]
         ↑
       4th
```

Therefore:

```text
answer = 4
```

The challenge asks whether we can solve the problem **without sorting the entire array**.

---

## Example 1

### Input

```text
nums = [3,2,1,5,6,4]
k = 2
```

Sorted in descending order:

```text
[6,5,4,3,2,1]
```

The 2nd largest element is:

```text
5
```

### Output

```text
5
```

---

## Example 2

### Input

```text
nums = [3,2,3,1,2,4,5,5,6]
k = 4
```

Sorted in descending order:

```text
[6,5,5,4,3,3,2,2,1]
```

The 4th largest element is:

```text
4
```

### Output

```text
4
```

---

# Approach

Instead of sorting the entire array, we use a **Min Heap**.

In Java, a:

```java
PriorityQueue<Integer>
```

is a **Min Heap** by default.

The idea is to maintain only the **k largest elements** seen so far.

For every number:

1. Add it to the min heap.
2. If the heap contains more than `k` elements, remove the smallest element.
3. After processing the entire array, the heap contains exactly the `k` largest elements.
4. The smallest element among those `k` elements is the **kth largest element**.

---

# Why a Min Heap?

Suppose:

```text
nums = [3,2,1,5,6,4]
k = 2
```

We only need to keep:

```text
[5,6]
```

the two largest elements.

A Min Heap allows us to quickly remove the smallest element whenever we have more than `k` elements.

For example:

```text
Heap:
[3,5,6]
```

Since:

```text
size > k
```

remove the minimum:

```text
3
```

Now:

```text
[5,6]
```

The smallest element in this heap is:

```text
5
```

which is the 2nd largest element overall.

---

# Key Observation

After processing all elements:

```text
Heap size = k
```

and the heap contains:

```text
k largest elements
```

Since it is a **Min Heap**, the smallest of these `k` elements is at the root.

Therefore:

```java
pq.peek()
```

gives the kth largest element.

---

# Dry Run

### Input

```text
nums = [3,2,1,5,6,4]
k = 2
```

Initially:

```text
PriorityQueue = []
```

---

### Add `3`

```text
[3]
```

Size:

```text
1
```

No removal.

---

### Add `2`

```text
[2,3]
```

Size:

```text
2
```

No removal.

---

### Add `1`

```text
[1,2,3]
```

Size:

```text
3
```

But:

```text
k = 2
```

So remove the smallest:

```text
remove 1
```

Heap:

```text
[2,3]
```

---

### Add `5`

```text
[2,3,5]
```

Remove minimum:

```text
2
```

Heap:

```text
[3,5]
```

---

### Add `6`

```text
[3,5,6]
```

Remove minimum:

```text
3
```

Heap:

```text
[5,6]
```

---

### Add `4`

```text
[4,5,6]
```

Remove minimum:

```text
4
```

Final heap:

```text
[5,6]
```

The root is:

```text
5
```

Therefore:

```text
answer = 5
```

---

# Algorithm

1. Create a Min Heap:
   ```java
   PriorityQueue<Integer> pq = new PriorityQueue<>();
   ```
2. Traverse every element in `nums`.
3. Add the current element to the heap.
4. If:
   ```text
   pq.size() > k
   ```
   remove the smallest element.
5. After processing all elements, return:
   ```java
   pq.peek()
   ```

---

# Java Solution

```java
class Solution {

    public int findKthLargest(int[] nums, int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : nums) {

            pq.add(num);

            if (pq.size() > k) {
                pq.remove();
            }
        }

        return pq.peek();
    }
}
```

---

# Why Does This Work?

At any point, the heap contains the largest `k` elements encountered so far.

Whenever a new element is added:

```text
heap size = k + 1
```

The smallest element among these `k + 1` elements cannot be part of the `k` largest elements.

So we remove it.

Therefore:

```text
Heap always contains the k largest elements
```

At the end:

```text
Heap = k largest elements in nums
```

Since the heap is a Min Heap:

```text
pq.peek()
```

is the smallest among them.

That is exactly the:

```text
kth largest element
```

---

# Handling Duplicates

The problem asks for the kth largest element in **sorted order**, not kth distinct element.

Our heap naturally handles duplicates.

For example:

```text
nums = [3,2,3,1,2,4,5,5,6]
k = 4
```

The four largest elements are:

```text
[4,5,5,6]
```

The smallest among them is:

```text
4
```

So the answer is:

```text
4
```

---

# Why Not Use a Max Heap?

A Max Heap could also be used, but we would need to remove the maximum `k-1` times.

That approach would require keeping all `n` elements:

```text
O(n)
```

heap space.

With a Min Heap of size `k`, we only maintain:

```text
O(k)
```

elements.

Therefore, the Min Heap approach is more space-efficient when `k` is small.

---

# Complexity Analysis

Let:

```text
n = nums.length
```

and:

```text
k = required position
```

For each element, insertion/removal in the heap takes:

```text
O(log k)
```

There are `n` elements.

Therefore:

### Time Complexity

```text
O(n log k)
```

### Space Complexity

The heap contains at most `k` elements:

```text
O(k)
```

---

# Comparison With Sorting

### Sorting Approach

```java
Arrays.sort(nums);
return nums[nums.length - k];
```

Complexity:

```text
Time:  O(n log n)
Space: O(1) / depends on sorting
```

### Min Heap Approach

```text
Time:  O(n log k)
Space: O(k)
```

The heap approach is particularly useful when:

```text
k << n
```

because we don't need to keep or sort the entire array.

---

# Key Concepts

- Priority Queue
- Min Heap
- Top K Elements
- Heap
- Streaming Approach
- Sorting Alternative

---

# Constraints

```text
1 <= k <= nums.length <= 10⁵
-10⁴ <= nums[i] <= 10⁴
```

---

# Learning Outcome

The most important pattern from this problem is:

> **To find the kth largest element, maintain a Min Heap of size `k`.**

Pattern:

```text
For every number:
        ↓
    Add to heap
        ↓
  size > k ?
      /    \
    YES     NO
     ↓       ↓
Remove min  Continue
        ↓
After all elements
        ↓
     pq.peek()
        ↓
   kth largest
```

The core code is:

```java
pq.add(num);

if (pq.size() > k) {
    pq.remove();
}
```

and finally:

```java
return pq.peek();
```

### Complexity

```text
Time:  O(n log k)
Space: O(k)
```