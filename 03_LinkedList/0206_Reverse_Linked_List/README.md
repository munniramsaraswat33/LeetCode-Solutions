# 206. Reverse Linked List

> **Difficulty:** Easy  
> **Topics:** Linked List, Recursion

---

## Problem Statement

Given the `head` of a singly linked list, reverse the linked list and return the new head.

---

## Example 1

### Input

```text
head = [1,2,3,4,5]
```

### Output

```text
[5,4,3,2,1]
```

### Explanation

The original list:

```text
1 → 2 → 3 → 4 → 5
```

After reversing:

```text
5 → 4 → 3 → 2 → 1
```

---

## Example 2

### Input

```text
head = [1,2]
```

### Output

```text
[2,1]
```

---

## Example 3

### Input

```text
head = []
```

### Output

```text
[]
```

---

# Approach

We can reverse the linked list **in-place** using three pointers:

- `prev` → points to the previous node.
- `curr` → points to the current node.
- `next` → temporarily stores the next node.

Initially:

```text
prev = null
curr = head
```

For every node, we reverse its `next` pointer:

```text
curr.next = prev
```

Before changing the pointer, we save the original next node so that we don't lose the rest of the list.

---

# Algorithm

1. Initialize:
   ```text
   curr = head
   prev = null
   ```
2. While `curr != null`:
   - Store the next node:
     ```text
     next = curr.next
     ```
   - Reverse the current pointer:
     ```text
     curr.next = prev
     ```
   - Move `prev` forward:
     ```text
     prev = curr
     ```
   - Move `curr` forward:
     ```text
     curr = next
     ```
3. After the loop, `prev` is the new head.
4. Return `prev`.

---

# Dry Run

### Input

```text
1 → 2 → 3 → 4 → 5
```

Initially:

```text
prev = null
curr = 1
```

### Step 1

Save:

```text
next = 2
```

Reverse:

```text
1 → null
```

Move pointers:

```text
prev = 1
curr = 2
```

---

### Step 2

Reverse:

```text
2 → 1
```

Pointers:

```text
prev = 2
curr = 3
```

---

### Step 3

```text
3 → 2 → 1
```

Pointers:

```text
prev = 3
curr = 4
```

---

### Step 4

```text
4 → 3 → 2 → 1
```

Pointers:

```text
prev = 4
curr = 5
```

---

### Step 5

```text
5 → 4 → 3 → 2 → 1
```

Now:

```text
curr = null
prev = 5
```

Therefore:

```text
return prev
```

Result:

```text
[5,4,3,2,1]
```

---

# Why Do We Need `next`?

This line is very important:

```java
ListNode next = curr.next;
```

Suppose we have:

```text
1 → 2 → 3
```

If we directly do:

```java
curr.next = prev;
```

we would lose access to:

```text
2 → 3
```

By storing:

```text
next = curr.next
```

we preserve the remaining list before changing the pointer.

---

# Complexity Analysis

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

Every node is visited exactly once.

---

### Space Complexity

```text
O(1)
```

Only three pointers are used.

---

# Java Solution

```java
class Solution {

    public ListNode reverseList(ListNode head) {

        ListNode curr = head;
        ListNode prev = null;

        while (curr != null) {

            ListNode next = curr.next;

            curr.next = prev;

            prev = curr;
            curr = next;
        }

        return prev;
    }
}
```

---

# Recursive Approach

The problem also allows a recursive solution.

The idea is to reverse the rest of the list first and then attach the current node at the end.

```java
class Solution {

    public ListNode reverseList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseList(head.next);

        head.next.next = head;
        head.next = null;

        return newHead;
    }
}
```

### Recursive Complexity

```text
Time:  O(n)
Space: O(n)
```

The extra space comes from the recursion call stack.

Therefore, the **iterative solution is more space-efficient**.

---

# Key Concepts

- Linked List
- Pointer Manipulation
- In-Place Reversal
- Iteration
- Recursion

---

# Constraints

- `0 <= number of nodes <= 5000`
- `-5000 <= Node.val <= 5000`

---

# Learning Outcome

This is one of the most important linked-list problems because it teaches the fundamental technique of **changing pointers safely**.

The core pattern is:

```text
next = curr.next
curr.next = prev
prev = curr
curr = next
```

Once this pattern is understood, many other linked-list problems become much easier.

The iterative solution achieves:

```text
Time:  O(n)
Space: O(1)
```