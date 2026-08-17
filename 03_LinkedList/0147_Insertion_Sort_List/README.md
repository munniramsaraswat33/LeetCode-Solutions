# 147. Insertion Sort List

> **Difficulty:** Medium  
> **Topics:** Linked List, Sorting, Insertion Sort

---

## Problem Statement

Given the `head` of a singly linked list, sort the list using **Insertion Sort** and return the sorted list's head.

Insertion Sort works by maintaining a **sorted portion** of the list and inserting each new node into its correct position.

---

## Example 1

### Input

```text
head = [4,2,1,3]
```

### Output

```text
[1,2,3,4]
```

---

## Example 2

### Input

```text
head = [-1,5,3,4,0]
```

### Output

```text
[-1,0,3,4,5]
```

---

# Approach

We use **Insertion Sort** directly on the linked list.

Instead of creating a new list of values, we rearrange the existing nodes.

A dummy node is used to make insertion easier, especially when a node needs to be inserted at the beginning.

```java
ListNode dummy = new ListNode(Integer.MIN_VALUE);
```

The dummy node represents the beginning of the sorted list.

---

# How It Works

Suppose:

```text
4 → 2 → 1 → 3
```

Initially:

```text
Sorted:
4

Remaining:
2 → 1 → 3
```

---

### Insert `2`

Compare `2` with `4`.

Since:

```text
2 < 4
```

insert `2` before `4`.

```text
2 → 4
```

---

### Insert `1`

Since:

```text
1 < 2
```

insert it at the beginning.

```text
1 → 2 → 4
```

---

### Insert `3`

`3` belongs between `2` and `4`.

```text
1 → 2 → 3 → 4
```

Final result:

```text
[1,2,3,4]
```

---

# Algorithm

For every node in the original list:

1. Save the next node.
2. Start from the beginning of the sorted list.
3. Find the position where the current node should be inserted.
4. Insert the current node.
5. Move to the next node.

The important part is:

```java
ListNode next = curr.next;
```

We save the next node **before changing `curr.next`**, because the current node is going to be moved into the sorted list.

---

# Java Solution

```java
class Solution {

    public ListNode insertionSortList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(Integer.MIN_VALUE);

        ListNode curr = head;

        while (curr != null) {

            // Save next node before changing curr.next
            ListNode next = curr.next;

            // Find insertion position
            ListNode prev = dummy;

            while (prev.next != null && prev.next.val <= curr.val) {
                prev = prev.next;
            }

            // Insert current node
            curr.next = prev.next;
            prev.next = curr;

            // Move to next original node
            curr = next;
        }

        return dummy.next;
    }
}
```

---

# Important Part of the Code

## 1. Dummy Node

```java
ListNode dummy = new ListNode(Integer.MIN_VALUE);
```

The dummy node simplifies insertion.

Without a dummy node, we would need special handling when inserting a node before the current head.

For example:

```text
1 → 3 → 5
```

If we need to insert `0`:

```text
0 → 1 → 3 → 5
```

The dummy node makes this operation uniform.

---

## 2. Save the Next Node

```java
ListNode next = curr.next;
```

This is very important.

Suppose:

```text
4 → 2 → 1 → 3
```

When processing `2`, we move it into the sorted portion.

If we don't save:

```java
curr.next
```

before changing the links, we can lose access to the remaining nodes.

---

## 3. Find Correct Position

```java
ListNode prev = dummy;

while (prev.next != null && prev.next.val <= curr.val) {
    prev = prev.next;
}
```

We move `prev` until:

```text
prev.val <= curr.val
```

and the next node is greater than `curr`.

For example:

```text
1 → 2 → 4
```

For:

```text
curr = 3
```

we stop at `2`.

So `3` is inserted between `2` and `4`.

---

## 4. Insert the Node

```java
curr.next = prev.next;
prev.next = curr;
```

Before:

```text
prev → next
```

After:

```text
prev → curr → next
```

---

# Dry Run

Input:

```text
4 → 2 → 1 → 3
```

### Initial

```text
Sorted List:
dummy

Current:
4 → 2 → 1 → 3
```

Insert `4`:

```text
4
```

---

### Insert `2`

```text
2 → 4
```

---

### Insert `1`

```text
1 → 2 → 4
```

---

### Insert `3`

```text
1 → 2 → 3 → 4
```

Return:

```text
dummy.next
```

---

# Why `dummy.next`?

The dummy node itself is not part of the answer.

For example:

```text
dummy → 1 → 2 → 3 → 4
```

Therefore:

```java
return dummy.next;
```

returns:

```text
1 → 2 → 3 → 4
```

---

# Complexity Analysis

Let `n` be the number of nodes.

For every node, we may have to traverse the sorted portion to find its correct position.

### Time Complexity

Worst case:

```text
O(n²)
```

For example, when the linked list is in descending order.

### Space Complexity

We only use a few pointers and one dummy node:

```text
O(1)
```

No additional array or collection is required.

---

# Why Insertion Sort for Linked Lists?

Insertion Sort is useful for linked lists because inserting a node does not require shifting elements.

For an array:

```text
[1,3,5,7]
```

inserting `2` requires shifting elements.

For a linked list:

```text
1 → 3 → 5 → 7
```

we can simply change pointers:

```text
1 → 2 → 3 → 5 → 7
```

The actual node movement is `O(1)` once the insertion position is found.

---

# Key Concepts

- Linked List
- Insertion Sort
- Dummy Node
- Pointer Manipulation
- In-place Sorting
- Singly Linked List

---

# Key Pattern

The main pattern is:

```text
Original List
     ↓
Take one node
     ↓
Find correct position
     ↓
Insert into sorted list
     ↓
Take next node
     ↓
Repeat
```

Visual representation:

```text
4 → 2 → 1 → 3
    ↓
2 → 4
    ↓
1 → 2 → 4
    ↓
1 → 2 → 3 → 4
```

---

# Learning Outcome

The main idea to remember is:

> **Maintain a sorted linked list and insert each remaining node into its correct position.**

The two most important lines are:

```java
ListNode next = curr.next;
```

and:

```java
curr.next = prev.next;
prev.next = curr;
```

These allow us to safely rearrange the linked-list nodes without using extra storage.

### Complexity

```text
Time:  O(n²)
Space: O(1)
```