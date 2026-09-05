# 2336. Smallest Number in Infinite Set

**Difficulty:** Medium  
**Topics:** Heap, HashSet, Priority Queue, Design

---

## Problem Statement

You have an infinite set of positive integers:

```text
{1, 2, 3, 4, 5, ...}
```

Implement the `SmallestInfiniteSet` class with two operations:

### `popSmallest()`

Removes and returns the smallest integer in the set.

### `addBack(num)`

Adds the positive integer `num` back into the set if it is not already present.

---

## Example

### Input

```text
["SmallestInfiniteSet", "addBack", "popSmallest",
 "popSmallest", "popSmallest", "addBack",
 "popSmallest", "popSmallest", "popSmallest"]
[[], [2], [], [], [], [1], [], [], []]
```

### Output

```text
[null, null, 1, 2, 3, null, 1, 4, 5]
```

### Explanation

Initially:

```text
{1, 2, 3, 4, 5, ...}
```

`popSmallest()`:

```text
1
```

Set becomes:

```text
{2, 3, 4, 5, ...}
```

`popSmallest()`:

```text
2
```

Set becomes:

```text
{3, 4, 5, ...}
```

`popSmallest()`:

```text
3
```

Then `addBack(1)` adds `1` again.

The next smallest number becomes:

```text
1
```

---

# Approach

The infinite set contains all positive integers, so we cannot actually store the entire set.

Instead, we maintain two parts:

1. `current` → the smallest number that has never been removed from the natural sequence.
2. `PriorityQueue` → numbers that were removed earlier and then added back.

We also use a `HashSet` to prevent duplicate numbers from being inserted into the priority queue.

---

# Intuition

Initially:

```text
current = 1
```

The smallest numbers can be generated naturally:

```text
1, 2, 3, 4, 5, ...
```

When `popSmallest()` is called, we return:

```text
current++
```

However, if a previously removed number is added back, it may be smaller than `current`.

For example:

```text
current = 5
```

and `2` is added back.

Now the smallest available number is `2`, not `5`.

So we store `2` in a min-heap.

Therefore:

```text
PriorityQueue → restored small numbers
current       → untouched numbers
```

---

# Data Structures

### 1. `current`

```java
int current;
```

Stores the next number that has not yet been popped from the natural sequence.

Initially:

```text
current = 1
```

---

### 2. PriorityQueue

```java
PriorityQueue<Integer> pq;
```

Java's `PriorityQueue` is a min-heap by default.

It always gives us the smallest restored number.

---

### 3. HashSet

```java
Set<Integer> set;
```

The set keeps track of numbers already present in the priority queue.

This prevents duplicate entries.

---

# Algorithm

## Constructor

1. Set:
   ```java
   current = 1;
   ```
2. Create an empty priority queue.
3. Create an empty hash set.

---

## `popSmallest()`

1. If the priority queue is not empty:
   - Remove its smallest element.
   - Remove that number from the set.
   - Return it.
2. Otherwise:
   - Return `current`.
   - Increment `current`.

---

## `addBack(num)`

A number should only be added back if:

```text
num < current
```

because numbers greater than or equal to `current` have not been popped yet.

Also check:

```text
!set.contains(num)
```

to avoid duplicates.

If both conditions are true:

```text
Add num to priority queue.
Add num to set.
```

---

# Dry Run

Initially:

```text
current = 1
pq = []
set = {}
```

### Operation 1

```text
popSmallest()
```

Priority queue is empty.

Return:

```text
1
```

Now:

```text
current = 2
```

---

### Operation 2

```text
popSmallest()
```

Again, priority queue is empty.

Return:

```text
2
```

Now:

```text
current = 3
```

---

### Operation 3

```text
addBack(1)
```

Since:

```text
1 < 3
```

and `1` is not already in the set, add it.

```text
pq = [1]
set = {1}
```

---

### Operation 4

```text
popSmallest()
```

Priority queue contains:

```text
[1]
```

So return:

```text
1
```

After removing:

```text
pq = []
set = {}
```

`current` remains:

```text
3
```

---

### Operation 5

```text
popSmallest()
```

Priority queue is empty.

Return:

```text
3
```

Then:

```text
current = 4
```

---

# Java Solution

```java
class SmallestInfiniteSet {

    int current;
    PriorityQueue<Integer> pq;
    Set<Integer> set;

    public SmallestInfiniteSet() {
        current = 1;
        pq = new PriorityQueue<>();
        set = new HashSet<>();
    }

    public int popSmallest() {
        if(!pq.isEmpty()){
            int smallest = pq.poll();
            set.remove(smallest);
            return smallest;
        }

        return current++;
    }

    public void addBack(int num) {
        if(num < current && !set.contains(num)){
            pq.offer(num);
            set.add(num);
        }
    }
}
```

---

# Code Explanation

### Constructor

```java
public SmallestInfiniteSet() {
    current = 1;
    pq = new PriorityQueue<>();
    set = new HashSet<>();
}
```

The set initially contains all positive integers conceptually.

We only store the numbers that need special handling.

---

### `popSmallest()`

```java
if(!pq.isEmpty()){
    int smallest = pq.poll();
    set.remove(smallest);
    return smallest;
}
```

If there are restored numbers, the priority queue contains the smallest one.

So we remove and return it.

Otherwise:

```java
return current++;
```

returns the next untouched positive integer.

---

### `addBack()`

```java
if(num < current && !set.contains(num)){
    pq.offer(num);
    set.add(num);
}
```

`num < current` means that the number was already removed earlier.

The `HashSet` prevents adding the same number to the priority queue multiple times.

---

# Why Do We Need a HashSet?

Consider:

```text
addBack(2)
addBack(2)
```

Without a set, the priority queue could become:

```text
[2, 2]
```

Then `popSmallest()` could return `2` twice.

The set prevents this:

```java
!set.contains(num)
```

Only numbers not already in the priority queue are inserted.

---

# Why `num < current`?

Suppose:

```text
current = 5
```

The numbers:

```text
5, 6, 7, 8, ...
```

have not been removed yet.

Therefore, calling:

```text
addBack(6)
```

does nothing because `6` is already in the infinite set.

Only numbers smaller than `current` could have been removed previously.

So:

```java
num < current
```

is necessary.

---

# Complexity Analysis

Let `n` be the number of operations.

### `popSmallest()`

When using the priority queue:

```text
O(log n)
```

When the priority queue is empty:

```text
O(1)
```

### `addBack()`

HashSet lookup:

```text
O(1)
```

Priority queue insertion:

```text
O(log n)
```

So overall:

```text
Time: O(log n) per heap operation
Space: O(n)
```

---

# Key Concepts / Patterns

- Priority Queue
- Min Heap
- HashSet
- Design
- Lazy Representation
- Infinite Set Simulation

---

# Learning Outcome

- Learn how to simulate an infinite set without storing all its elements.
- Understand how a min-heap can maintain restored smallest values.
- Learn why a `HashSet` is useful for avoiding duplicates.
- Understand how `current` represents the untouched portion of an infinite sequence.
- Practice designing a data structure with multiple supporting operations.