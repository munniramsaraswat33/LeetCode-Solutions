# 2058. Find the Minimum and Maximum Number of Nodes Between Critical Points

> **Difficulty:** Medium  
> **Topics:** Linked List, Traversal, Two Pointers

---

## Problem Statement

You are given the head of a linked list.

A node is called a **critical point** if it is either:

- A **local maximum**: its value is strictly greater than both neighboring nodes.
- A **local minimum**: its value is strictly smaller than both neighboring nodes.

The first and last nodes cannot be critical points because they have only one neighbor.

Return an array:

```text
[minDistance, maxDistance]
```

where:

- `minDistance` is the minimum distance between any two critical points.
- `maxDistance` is the maximum distance between any two critical points.

If there are fewer than two critical points, return:

```text
[-1, -1]
```

---

## Examples

### Example 1

```text
Input:
head = [5,3,1,2,5,1,2]

Output:
[1,3]
```

### Explanation

The critical points are:

```text
3 → local minimum
5 → local maximum
1 → local minimum
```

The distances between consecutive critical points are calculated, and the minimum and maximum distances are returned.

---

### Example 2

```text
Input:
head = [1,3,2,2,3,2]

Output:
[3,3]
```

### Explanation

There are two critical points.

The distance between them is:

```text
3
```

Therefore:

```text
minDistance = 3
maxDistance = 3
```

---

### Example 3

```text
Input:
head = [1,2,3,4]

Output:
[-1,-1]
```

### Explanation

There are fewer than two critical points.

Therefore, it is impossible to calculate a distance.

```text
[-1,-1]
```

---

# Approach

We can solve this problem using a **single traversal** of the linked list.

To determine whether the current node is a critical point, we need to compare it with both its neighbors.

Therefore, we maintain three pointers:

```text
prev → curr → next
```

A node is a critical point when either:

```text
prev < curr > next
```

or:

```text
prev > curr < next
```

We don't need to store all critical points.

Instead, we only maintain:

```text
first → first critical point
last  → most recent critical point
```

Whenever we find a new critical point:

- `first` is set if this is the first one.
- The distance from `last` is calculated to update `minDistance`.
- `last` is updated to the current critical point.

At the end:

```text
maxDistance = last - first
```

because the maximum distance is always between the first and last critical points.

---

# Algorithm

1. Initialize three pointers:
   ```text
   prev = head
   curr = head.next
   next = curr.next
   ```
2. Start the node index from `2`.
3. Traverse the linked list while `next != null`.
4. Check whether `curr` is a critical point:
   - Local maximum:
     ```text
     prev.val < curr.val && next.val < curr.val
     ```
   - Local minimum:
     ```text
     prev.val > curr.val && next.val > curr.val
     ```
5. If `curr` is a critical point:
   - If it is the first critical point, store its index in `first`.
   - If another critical point already exists, calculate:
     ```text
     index - last
     ```
   - Update `minDistance`.
   - Set `last = index`.
6. Move all three pointers forward.
7. If fewer than two critical points were found, return:
   ```text
   [-1,-1]
   ```
8. Calculate:
   ```text
   maxDistance = last - first
   ```
9. Return:
   ```text
   [minDistance, maxDistance]
   ```

---

# Dry Run

Consider:

```text
head = [5,3,1,2,5,1,2]
```

Represent the list with indices:

```text
Index:  1  2  3  4  5  6  7
Value:  5  3  1  2  5  1  2
```

We start with:

```text
prev = 5
curr = 3
next = 1
```

### Position 2

```text
5 > 3 < 1
```

So `3` is a local minimum.

Therefore:

```text
first = 2
last = 2
```

---

### Position 3

```text
3 > 1 < 2
```

So `1` is a local minimum.

Distance from previous critical point:

```text
3 - 2 = 1
```

Therefore:

```text
minDistance = 1
last = 3
```

---

### Position 4

```text
1 < 2 < 5
```

The current node is neither a local minimum nor a local maximum.

So we ignore it.

---

### Position 5

```text
2 < 5 > 1
```

So `5` is a local maximum.

Distance from previous critical point:

```text
5 - 3 = 2
```

The minimum remains:

```text
minDistance = 1
```

Update:

```text
last = 5
```

---

### Position 6

```text
5 > 1 < 2
```

So `1` is a local minimum.

Distance:

```text
6 - 5 = 1
```

Therefore:

```text
minDistance = 1
last = 6
```

---

### Final Calculation

First critical point:

```text
first = 2
```

Last critical point:

```text
last = 6
```

Maximum distance:

```text
last - first
= 6 - 2
= 4
```

Therefore:

```text
Answer = [1,4]
```

---

# Java Solution

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) {
 *         this.val = val;
 *         this.next = next;
 *     }
 * }
 */

class Solution {

    public int[] nodesBetweenCriticalPoints(ListNode head) {

        ListNode prev = head;
        ListNode curr = head.next;
        ListNode next = curr.next;

        int index = 2;

        int first = -1;
        int last = -1;

        if (next == null) {
            return new int[]{first, last};
        }

        int minDistance = Integer.MAX_VALUE;

        while (next != null) {

            // Check whether current node is a critical point
            if ((prev.val < curr.val && next.val < curr.val)
                    || (prev.val > curr.val && next.val > curr.val)) {

                // Store the first critical point
                if (first == -1) {
                    first = index;
                }

                // Calculate distance from previous critical point
                if (last != -1) {
                    minDistance =
                        Math.min(minDistance, index - last);
                }

                // Update the latest critical point
                last = index;
            }

            // Move the pointers
            prev = curr;
            curr = next;
            next = next.next;

            index++;
        }

        // Fewer than two critical points
        if (first == last) {
            return new int[]{-1, -1};
        }

        // Maximum distance is between
        // the first and last critical points
        int maxDistance = last - first;

        return new int[]{minDistance, maxDistance};
    }
}
```

---

# Code Explanation

### Three Pointers

```java
ListNode prev = head;
ListNode curr = head.next;
ListNode next = curr.next;
```

We need the previous, current, and next nodes to determine whether `curr` is a critical point.

---

### Detect Local Maximum

```java
prev.val < curr.val && next.val < curr.val
```

This represents:

```text
prev < curr > next
```

So `curr` is a local maximum.

---

### Detect Local Minimum

```java
prev.val > curr.val && next.val > curr.val
```

This represents:

```text
prev > curr < next
```

So `curr` is a local minimum.

---

### Store First Critical Point

```java
if (first == -1) {
    first = index;
}
```

The first critical point is required to calculate the maximum distance later.

---

### Calculate Minimum Distance

```java
minDistance =
    Math.min(minDistance, index - last);
```

Whenever we find a new critical point, we calculate its distance from the previous critical point.

Only consecutive critical points are needed to find the minimum distance.

---

### Calculate Maximum Distance

```java
int maxDistance = last - first;
```

The maximum distance is always the distance between the first and last critical points.

---

# Complexity Analysis

Let `n` be the number of nodes in the linked list.

### Time Complexity

```text
O(n)
```

The linked list is traversed only once.

### Space Complexity

```text
O(1)
```

Only a constant number of pointers and variables are used.

---

# Key Concepts

### 1. Linked List Traversal

The linked list is processed in a single pass.

### 2. Three-Pointer Technique

The `prev`, `curr`, and `next` pointers allow us to compare a node with both neighbors.

### 3. Local Maximum

```text
prev < curr > next
```

### 4. Local Minimum

```text
prev > curr < next
```

### 5. Consecutive Critical Points

The distance between consecutive critical points is used to find the minimum distance.

### 6. First and Last Critical Points

The first and last critical points determine the maximum distance.

---

# Constraints

- `2 <= number of nodes <= 100000`
- `1 <= Node.val <= 100000`
- The first and last nodes cannot be critical points.

---

# Learning Outcome

After solving this problem, you should understand:

- How to use three pointers while traversing a linked list.
- How to detect local minima and local maxima.
- How to calculate minimum distance without storing all critical points.
- Why the first and last critical points give the maximum distance.
- How to solve the problem in `O(n)` time and `O(1)` extra space.