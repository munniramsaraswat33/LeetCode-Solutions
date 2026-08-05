# 1354. Construct Target Array With Multiple Sums

> **Difficulty:** Hard  
> **Topics:** Greedy, Heap (Priority Queue), Math

---

## Problem Statement

You are given an integer array `target` of length `n`.

Initially, there is an array `arr` of length `n` where every element is `1`.

You can repeatedly perform the following operation:

1. Compute the sum of all elements in the current array.
2. Choose any index `i`.
3. Replace `arr[i]` with the current sum.

Return **`true`** if it is possible to construct the given `target` array from the initial array of all `1`s; otherwise, return **`false`**.

---

## Example 1

### Input

```text
target = [9,3,5]
```

### Output

```text
true
```

### Explanation

```text
Start

[1,1,1]

Sum = 3

↓

[1,3,1]

Sum = 5

↓

[1,3,5]

Sum = 9

↓

[9,3,5]
```

---

## Example 2

### Input

```text
target = [1,1,1,2]
```

### Output

```text
false
```

---

## Example 3

### Input

```text
target = [8,5]
```

### Output

```text
true
```

---

# Intuition

Constructing the target array directly is difficult because many different choices are possible.

Instead, think **in reverse**.

The last operation always makes one element equal to the **sum of the entire previous array**.

Therefore, in the target array:

- the **largest element** must have been updated last.
- we repeatedly recover its previous value until every element becomes `1`.

---

# Approach

Use a **Max Heap (Priority Queue)** to efficiently obtain the largest element.

For every iteration:

- Remove the largest element.
- Compute the sum of the remaining elements.

```text
restSum = totalSum - largest
```

The previous value of the largest element is

```text
largest % restSum
```

instead of repeatedly subtracting `restSum`.

Special cases:

- If `restSum == 1`, construction is always possible.
- If `restSum == 0`, return `false`.
- If `largest <= restSum`, return `false`.
- If `largest % restSum == 0`, return `false`.

Otherwise:

- Replace the largest element with its previous value.
- Update the total sum.
- Continue.

---

# Algorithm

1. If the array has only one element, return whether it is `1`.
2. Insert every element into a Max Heap.
3. Compute the total sum.
4. While the largest element is greater than `1`:
   - Remove the largest element.
   - Compute the remaining sum.
   - Check invalid cases.
   - Compute the previous value using modulo.
   - Push it back into the heap.
   - Update the total sum.
5. Return `true`.

---

# Dry Run

Input

```text
target = [9,3,5]
```

Initial

```text
Heap = [9,5,3]

Sum = 17
```

### Iteration 1

```text
Largest = 9

Remaining Sum = 8

Previous Value = 9 % 8 = 1
```

Heap

```text
[5,3,1]
```

New Sum

```text
9
```

---

### Iteration 2

```text
Largest = 5

Remaining Sum = 4

Previous Value = 5 % 4 = 1
```

Heap

```text
[3,1,1]
```

New Sum

```text
5
```

---

### Iteration 3

```text
Largest = 3

Remaining Sum = 2

Previous Value = 3 % 2 = 1
```

Heap

```text
[1,1,1]
```

All elements become `1`.

Return

```text
true
```

---

# Correctness

At every step:

- The largest value must be the element produced by the most recent operation.
- Replacing it with `largest % restSum` restores its previous value.
- If any invalid condition occurs, no valid previous array exists.

Thus the algorithm correctly determines whether the target array can be constructed.

---

# Complexity Analysis

Let **n** be the size of the array.

### Time Complexity

Building the heap:

```text
O(n)
```

Each heap operation:

```text
O(log n)
```

Overall:

```text
O(n log n)
```

---

### Space Complexity

```text
O(n)
```

for storing the Max Heap.

---

# Java Solution

```java
class Solution {
    public boolean isPossible(int[] target) {

        if (target.length == 1) {
            return target[0] == 1;
        }

        PriorityQueue<Integer> pq =
                new PriorityQueue<>(Collections.reverseOrder());

        long totalSum = 0;

        for (int num : target) {
            totalSum += num;
            pq.offer(num);
        }

        while (pq.peek() > 1) {

            long maxElement = pq.poll();

            long restSum = totalSum - maxElement;

            if (restSum == 1) {
                return true;
            }

            if (restSum == 0 || maxElement <= restSum) {
                return false;
            }

            long updateElement = maxElement % restSum;

            if (updateElement == 0) {
                return false;
            }

            totalSum = restSum + updateElement;

            pq.offer((int) updateElement);
        }

        return true;
    }
}
```

---

# Key Concepts

- Greedy Algorithm
- Max Heap (Priority Queue)
- Reverse Simulation
- Mathematical Observation
- Modulo Operation

---

# Constraints

- `1 <= n <= 5 × 10⁴`
- `1 <= target[i] <= 10⁹`

---

# Learning Outcome

This problem illustrates how **reverse thinking** can simplify a seemingly difficult construction problem. By always processing the largest element with a **Max Heap** and restoring its previous value using the **modulo operation**, we achieve an efficient solution that handles very large numbers without repeated subtraction.