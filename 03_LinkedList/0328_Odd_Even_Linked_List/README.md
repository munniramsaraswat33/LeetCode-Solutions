# 328. Odd Even Linked List

> **Difficulty:** Medium  
> **Topics:** Linked List, Two Pointers

---

## Problem Statement

Given the `head` of a singly linked list, group all nodes at **odd indices** together followed by all nodes at **even indices**.

The first node is considered to have index `1` (odd), the second node has index `2` (even), and so on.

The relative order of the nodes within both groups must remain the same.

The solution must use:

```text
O(n) time
O(1) extra space
```

---

## Example 1

### Input

```text
head = [1,2,3,4,5]
```

### Output

```text
[1,3,5,2,4]
```

### Explanation

Odd-indexed nodes:

```text
1 → 3 → 5
```

Even-indexed nodes:

```text
2 → 4
```

Combine them:

```text
1 → 3 → 5 → 2 → 4
```

---

## Example 2

### Input

```text
head = [2,1,3,5,6,4,7]
```

### Output

```text
[2,3,6,7,1,5,4]
```

Odd-indexed nodes:

```text
2 → 3 → 6 → 7
```

Even-indexed nodes:

```text
1 → 5 → 4
```

Final list:

```text
2 → 3 → 6 → 7 → 1 → 5 → 4
```

---

# Approach

We maintain two separate chains:

- `odd` → points to the current odd-indexed node.
- `even` → points to the current even-indexed node.
- `evenhead` → stores the first even node so we can attach it after the odd nodes.

Initially:

```text
odd = head
even = head.next
evenhead = head.next
```

Then, we rearrange the links while traversing the list.

For every iteration:

```text
odd.next = even.next
odd = odd.next

even.next = odd.next
even = even.next
```

After all odd-indexed nodes are connected, attach the even list after the odd list:

```text
odd.next = evenhead
```

---

# Algorithm

1. If `head == null`, return `null`.
2. Initialize:
   ```text
   odd = head
   evenhead = head.next
   even = evenhead
   ```
3. While `even != null` and `even.next != null`:
   - Connect the current odd node to the next odd node.
   - Move `odd` forward.
   - Connect the current even node to the next even node.
   - Move `even` forward.
4. Connect the end of the odd list to the beginning of the even list.
5. Return `head`.

---

# Dry Run

### Input

```text
1 → 2 → 3 → 4 → 5
```

Initially:

```text
odd      → 1
even     → 2
evenhead → 2
```

### First iteration

Connect the next odd node:

```text
1 → 3
```

Connect the next even node:

```text
2 → 4
```

Now:

```text
odd  → 3
even → 4
```

---

### Second iteration

Connect:

```text
3 → 5
```

There is no next even node after `4`, so the loop ends.

We now have:

```text
Odd list:
1 → 3 → 5

Even list:
2 → 4
```

Attach:

```text
5 → 2
```

Final result:

```text
1 → 3 → 5 → 2 → 4
```

---

# Why This Works

The algorithm does not create any new nodes.

Instead, it changes the `next` references of the existing nodes.

The two separate lists are maintained while traversing:

```text
Odd:
1 → 3 → 5

Even:
2 → 4
```

Finally:

```text
Odd List → Even List
```

This satisfies the requirement of **O(1) extra space**.

---

# Complexity Analysis

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

Every node is visited at most once.

---

### Space Complexity

```text
O(1)
```

Only a few pointers are used, and no additional nodes or data structures are created.

---

# Java Solution

```java
class Solution {

    public ListNode oddEvenList(ListNode head) {

        if (head == null) {
            return null;
        }

        ListNode odd = head;
        ListNode evenhead = head.next;
        ListNode even = evenhead;

        while (even != null && even.next != null) {

            // Connect odd nodes
            odd.next = even.next;
            odd = odd.next;

            // Connect even nodes
            even.next = odd.next;
            even = even.next;
        }

        // Attach even list after odd list
        odd.next = evenhead;

        return head;
    }
}
```

---

# Key Concepts

- Linked List
- Two Pointers
- Pointer Manipulation
- In-Place Modification
- O(1) Extra Space

---

# Constraints

- `0 <= number of nodes <= 10⁴`
- `-10⁶ <= Node.val <= 10⁶`

---

# Learning Outcome

This problem is an excellent example of **in-place linked-list manipulation**.

The key idea is to maintain two separate chains:

```text
Odd-indexed nodes
        ↓
1 → 3 → 5

Even-indexed nodes
        ↓
2 → 4
```

and then connect them:

```text
1 → 3 → 5 → 2 → 4
```

No extra list or array is required, giving the solution:

```text
Time:  O(n)
Space: O(1)
```