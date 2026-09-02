# 2130. Maximum Twin Sum of a Linked List

> **Difficulty:** Medium  
> **Topics:** Linked List, Two Pointers, Fast & Slow Pointers, Reversal

---

## Problem Statement

Given the head of a linked list with an **even number of nodes**, find the maximum twin sum.

For a linked list with `n` nodes:

- The first node and the last node form a pair.
- The second node and the second-last node form a pair.
- And so on.

For nodes at positions `i` and `n - 1 - i`, their sum is called a **twin sum**.

Return the maximum twin sum among all pairs.

---

## Examples

### Example 1

```text
Input:
head = [5,4,2,1]

Output:
6
```

### Explanation

The twin pairs are:

```text
(5, 1) → 5 + 1 = 6
(4, 2) → 4 + 2 = 6
```

Therefore:

```text
Maximum Twin Sum = 6
```

---

### Example 2

```text
Input:
head = [4,2,2,3]

Output:
7
```

### Explanation

The twin pairs are:

```text
(4, 3) → 7
(2, 2) → 4
```

Therefore:

```text
Maximum Twin Sum = 7
```

---

### Example 3

```text
Input:
head = [1,100]

Output:
101
```

### Explanation

There is only one twin pair:

```text
1 + 100 = 101
```

So the answer is:

```text
101
```

---

# Approach

We need to pair the first half of the linked list with the second half in reverse order.

For example:

```text
5 → 4 → 2 → 1
```

Twin pairs are:

```text
5 ↔ 1
4 ↔ 2
```

A linked list does not allow direct access from the end.

So we can solve this efficiently using:

1. **Fast and Slow Pointers** to find the middle.
2. **Reverse the second half** of the linked list.
3. Traverse the first half and reversed second half together.
4. Calculate every twin sum and keep the maximum.

---

## Step 1: Find the Middle

Use two pointers:

```text
slow
fast
```

- `slow` moves one node at a time.
- `fast` moves two nodes at a time.

```java
while(fast != null && fast.next != null){
    slow = slow.next;
    fast = fast.next.next;
}
```

For:

```text
5 → 4 → 2 → 1
```

After the traversal:

```text
slow → 2
```

So `slow` points to the beginning of the second half.

---

## Step 2: Reverse the Second Half

The second half is:

```text
2 → 1
```

Reverse it:

```text
1 → 2
```

This makes it possible to directly pair:

```text
5 → 4
↓   ↓
1 → 2
```

Now:

```text
5 + 1 = 6
4 + 2 = 6
```

---

## Step 3: Calculate Twin Sums

We maintain:

```java
head
prev
```

where:

- `head` points to the first half.
- `prev` points to the reversed second half.

Then:

```java
int sum = head.val + prev.val;
```

Update the maximum:

```java
max = Math.max(max, sum);
```

Continue until all pairs are processed.

---

# Algorithm

1. Initialize the length of the linked list.
2. Traverse the list to determine its length.
3. Use `slow` and `fast` pointers to find the beginning of the second half.
4. Reverse the second half of the linked list.
5. Set:
   - `head` → beginning of first half.
   - `prev` → beginning of reversed second half.
6. Traverse both halves simultaneously.
7. Calculate the twin sum for every pair.
8. Store the maximum sum.
9. Return the maximum twin sum.

---

# Dry Run

Consider:

```text
head = [5, 4, 2, 1]
```

### Step 1: Find Length

The code calculates the length.

Conceptually:

```text
Length = 4
```

---

### Step 2: Find Middle

Initially:

```text
slow = 5
fast = 5
```

After the first iteration:

```text
slow = 4
fast = 2
```

After the second iteration:

```text
slow = 2
fast = null
```

Therefore:

```text
slow = 2
```

The second half starts at:

```text
2 → 1
```

---

### Step 3: Reverse Second Half

Before reversal:

```text
2 → 1
```

After reversal:

```text
1 → 2
```

So we have:

```text
First half:
5 → 4

Reversed second half:
1 → 2
```

---

### Step 4: Calculate Twin Sums

First pair:

```text
5 + 1 = 6
```

```text
max = 6
```

Second pair:

```text
4 + 2 = 6
```

```text
max = 6
```

Therefore:

```text
Answer = 6
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

    public int pairSum(ListNode head) {

        // Find the length of the linked list
        int length = 1;

        ListNode start = head;

        while (start != null) {
            start = start.next;
            length++;
        }

        // Find the middle using slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse the second half
        ListNode prev = null;
        ListNode curr = slow;

        while (curr != null) {

            ListNode next = curr.next;

            curr.next = prev;

            prev = curr;
            curr = next;
        }

        // Calculate twin sums
        int max = 0;

        while (length / 2 != 0) {

            int sum = head.val + prev.val;

            max = Math.max(max, sum);

            head = head.next;
            prev = prev.next;

            length -= 2;
        }

        return max;
    }
}
```

---

# Code Explanation

### Finding the Length

```java
int length = 1;
ListNode start = head;

while(start != null){
    start = start.next;
    length++;
}
```

The code determines the number of nodes so that we know how many twin pairs need to be processed.

---

### Finding the Middle

```java
ListNode slow = head;
ListNode fast = head;
```

The slow pointer moves one step:

```java
slow = slow.next;
```

The fast pointer moves two steps:

```java
fast = fast.next.next;
```

When `fast` reaches the end, `slow` is at the beginning of the second half.

---

### Reversing the Second Half

```java
ListNode prev = null;
ListNode curr = slow;
```

The standard linked-list reversal technique is used.

```java
ListNode next = curr.next;
curr.next = prev;
prev = curr;
curr = next;
```

For example:

```text
2 → 1
```

becomes:

```text
1 → 2
```

---

### Calculating Twin Sums

```java
int sum = head.val + prev.val;
```

The first node of the first half is paired with the first node of the reversed second half.

Then both pointers move forward:

```java
head = head.next;
prev = prev.next;
```

---

### Updating Maximum

```java
max = Math.max(max, sum);
```

This keeps track of the largest twin sum encountered.

---

# Complexity Analysis

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

We traverse the linked list a constant number of times:

- Find length → `O(n)`
- Find middle → `O(n)`
- Reverse second half → `O(n)`
- Calculate twin sums → `O(n)`

Therefore:

```text
O(n)
```

### Space Complexity

```text
O(1)
```

Only a few pointers are used.

The second half is reversed **in-place**, so no additional array or list is required.

---

# Key Concepts

### 1. Fast and Slow Pointers

Used to find the middle of a linked list efficiently.

### 2. In-place Linked List Reversal

The second half is reversed without creating another linked list.

### 3. Two Pointers

After reversal, two pointers traverse the two halves simultaneously.

### 4. Twin Sum

For a list of length `n`:

```text
node[i] + node[n - 1 - i]
```

is the twin sum.

### 5. In-place Algorithm

The solution uses constant extra space by modifying the links of the second half.

---

# Constraints

- The number of nodes is even.
- `2 <= n <= 100000`
- `1 <= Node.val <= 100000`
- The linked list contains an even number of nodes.

---

# Learning Outcome

After solving this problem, you should understand:

- How to find the middle of a linked list using fast and slow pointers.
- How to reverse a linked list in-place.
- How reversing the second half allows backward-style access in a singly linked list.
- How two pointers can be used to calculate paired values.
- How to solve linked-list problems in `O(n)` time and `O(1)` extra space.