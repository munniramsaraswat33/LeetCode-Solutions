# 82. Remove Duplicates from Sorted List II

> **Difficulty:** Medium  
> **Topics:** Linked List, Two Pointers

---

## Problem Statement

Given the `head` of a **sorted linked list**, remove **all nodes that have duplicate numbers**.

Only numbers that appear **exactly once** should remain in the linked list.

Return the resulting linked list.

---

## Important Difference

This problem is different from **LeetCode 83 - Remove Duplicates from Sorted List**.

### LeetCode 83

Duplicates are kept once:

```text
1 → 1 → 2 → 3 → 3
```

becomes:

```text
1 → 2 → 3
```

### LeetCode 82

All duplicated values are completely removed:

```text
1 → 1 → 2 → 3 → 3
```

becomes:

```text
2
```

---

## Example 1

### Input

```text
head = [1,2,3,3,4,4,5]
```

### Output

```text
[1,2,5]
```

### Explanation

The values:

```text
3
4
```

appear more than once, so **all their nodes are removed**.

Remaining values:

```text
1 → 2 → 5
```

---

## Example 2

### Input

```text
head = [1,1,1,2,3]
```

### Output

```text
[2,3]
```

### Explanation

`1` occurs three times, so all `1` nodes are removed.

---

# Approach

Because the linked list is already **sorted**, all duplicate values will appear next to each other.

For example:

```text
1 → 2 → 3 → 3 → 4 → 4 → 5
          ↑    ↑
        duplicate
```

Therefore, when we find:

```java
curr.val == curr.next.val
```

we know that `curr.val` is a duplicate.

We then skip **all nodes having that value**.

---

# Why Use a Dummy Node?

We create:

```java
ListNode dummy = new ListNode(0);
dummy.next = head;
```

The dummy node makes it easier to handle duplicates at the beginning of the list.

For example:

```text
1 → 1 → 2 → 3
```

If `1` is duplicated, we need to remove the original head.

Without a dummy node, changing the head requires special handling.

With a dummy node:

```text
dummy → 1 → 1 → 2 → 3
```

we can simply do:

```java
prev.next = curr;
```

and remove the duplicate section.

---

# Three Important Pointers / Variables

### `dummy`

```java
ListNode dummy = new ListNode(0);
```

Points before the actual head.

```text
dummy → 1 → 2 → 3
```

---

### `prev`

```java
ListNode prev = dummy;
```

`prev` points to the **last confirmed unique node**.

---

### `curr`

```java
ListNode curr = head;
```

`curr` is used to scan the linked list.

---

# Main Logic

We check:

```java
if (curr.next != null && curr.val == curr.next.val)
```

This means:

```text
curr and curr.next have the same value
```

Therefore, this value is duplicated.

Store the duplicate value:

```java
int duplicate = curr.val;
```

Then skip every node with that value:

```java
while (curr != null && curr.val == duplicate) {
    curr = curr.next;
}
```

After this loop, `curr` points to the first node with a different value.

Then:

```java
prev.next = curr;
```

removes the entire duplicate section.

---

# What Happens When There Is No Duplicate?

If:

```java
curr.val != curr.next.val
```

then `curr` is a unique node.

So we move `prev` forward:

```java
prev = curr;
```

and move `curr` forward:

```java
curr = curr.next;
```

---

# Dry Run

Consider:

```text
1 → 2 → 3 → 3 → 4 → 4 → 5
```

Initial:

```text
dummy → 1 → 2 → 3 → 3 → 4 → 4 → 5
        ↑
       curr

prev = dummy
```

---

## Step 1: Value `1`

```text
curr = 1
curr.next = 2
```

They are different.

So:

```java
prev = curr;
curr = curr.next;
```

Now:

```text
prev → 1
curr → 2
```

---

## Step 2: Value `2`

`2` and `3` are different.

Move forward:

```text
prev → 2
curr → 3
```

---

## Step 3: Value `3`

Now:

```text
curr.val == curr.next.val
```

because:

```text
3 == 3
```

So:

```java
duplicate = 3;
```

Now skip all `3`s:

```text
3 → 3 → 4
↑       ↑
skip    curr
```

After the loop:

```text
curr → 4
```

Now:

```java
prev.next = curr;
```

The list becomes:

```text
1 → 2 → 4 → 4 → 5
```

The `3`s have been completely removed.

---

## Step 4: Value `4`

Again:

```text
4 == 4
```

So skip all `4`s.

After skipping:

```text
curr → 5
```

Connect:

```java
prev.next = curr;
```

Now:

```text
1 → 2 → 5
```

---

## Step 5: Value `5`

`5` has no duplicate.

So:

```java
prev = curr;
curr = curr.next;
```

Now:

```text
curr = null
```

Loop ends.

Return:

```java
dummy.next
```

Result:

```text
1 → 2 → 5
```

---

# Why `prev` Does Not Move When We Find a Duplicate

This is one of the most important parts.

Suppose:

```text
1 → 2 → 3 → 3 → 4
```

Before processing `3`:

```text
prev → 2
curr → 3
```

Since `3` is duplicated, we remove both `3`s.

We do:

```java
prev.next = curr;
```

where `curr` is now pointing to `4`.

So:

```text
2 → 4
```

Notice that `prev` **stays at `2`**.

This is necessary because `3` should not become part of the final list.

---

# Why `prev.next = curr`?

Suppose:

```text
prev → 2 → 3 → 3 → 4
```

After skipping the duplicates:

```text
prev → 2

curr → 4
```

We connect:

```java
prev.next = curr;
```

which creates:

```text
2 → 4
```

The duplicate nodes are no longer connected to the result list.

---

# Algorithm

1. Handle empty or single-node list.
2. Create a dummy node before `head`.
3. Set:
   ```text
   prev = dummy
   curr = head
   ```
4. Traverse the list.
5. If `curr` and `curr.next` have the same value:
   - Store the duplicate value.
   - Skip all nodes with that value.
   - Connect `prev.next` to `curr`.
6. Otherwise:
   - Move `prev` to `curr`.
   - Move `curr` forward.
7. Return `dummy.next`.

---

# Java Solution

```java
class Solution {

    public ListNode deleteDuplicates(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;
        ListNode curr = head;

        while (curr != null) {

            // Duplicate found
            if (curr.next != null &&
                curr.val == curr.next.val) {

                int duplicate = curr.val;

                // Skip all nodes having the duplicate value
                while (curr != null &&
                       curr.val == duplicate) {

                    curr = curr.next;
                }

                // Remove duplicate section
                prev.next = curr;

            }

            // Current node is unique
            else {

                prev = curr;
                curr = curr.next;
            }
        }

        return dummy.next;
    }
}
```

---

# Complexity Analysis

Let `n` be the number of nodes.

### Time Complexity

Every node is visited at most a constant number of times.

Therefore:

```text
O(n)
```

---

### Space Complexity

Only a few pointers are used:

```text
O(1)
```

---

# Key Concepts

- Linked List
- Dummy Node
- Two Pointers
- Sorted Linked List
- Duplicate Detection
- Pointer Manipulation

---

# Constraints

```text
0 <= number of nodes <= 300
-100 <= Node.val <= 100
```

The linked list is sorted in ascending order.

---

# Learning Outcome

The most important pattern in this problem is:

```text
prev → unique nodes
curr → node currently being checked
```

When a duplicate is found:

```text
1 → 2 → 3 → 3 → 4
        ↑       ↑
       prev    curr
```

Skip all `3`s:

```text
1 → 2 → 4
     ↑    ↑
    prev curr
```

and connect:

```java
prev.next = curr;
```

### The key rule:

> **If a value is duplicated, don't keep one copy — remove the entire group.**

### Complexity

```text
Time:  O(n)
Space: O(1)
```