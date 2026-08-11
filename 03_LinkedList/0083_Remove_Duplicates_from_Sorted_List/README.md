# 83. Remove Duplicates from Sorted List

> **Difficulty:** Easy  
> **Topics:** Linked List

---

## Problem Statement

Given the `head` of a **sorted linked list**, remove all duplicate values so that each value appears only once.

Return the resulting linked list.

Since the original linked list is already sorted, the resulting linked list will also remain sorted.

---

## Example 1

### Input

```text
head = [1,1,2]
```

### Output

```text
[1,2]
```

### Explanation

The duplicate `1` is removed:

```text
1 → 1 → 2
    ↓
1 → 2
```

---

## Example 2

### Input

```text
head = [1,1,2,3,3]
```

### Output

```text
[1,2,3]
```

### Explanation

Remove the duplicate occurrences:

```text
1 → 1 → 2 → 3 → 3
```

becomes:

```text
1 → 2 → 3
```

---

# Approach

The linked list is already sorted.

Therefore, all duplicate values will appear **next to each other**.

We use two pointers:

- `curr` — points to the last unique node.
- `temp` — scans the remaining nodes.

For every node:

- If `curr.val == temp.val`, the value is a duplicate, so move `temp` forward.
- Otherwise, connect `curr.next` to `temp` and move both pointers forward.

At the end, set:

```text
curr.next = null
```

to remove any remaining duplicate nodes from the list.

---

# Algorithm

1. If the list is empty or contains only one node, return `head`.
2. Initialize:
   ```text
   curr = head
   temp = head.next
   ```
3. Traverse the list using `temp`.
4. If:
   ```text
   curr.val == temp.val
   ```
   skip the duplicate by moving `temp`.
5. Otherwise:
   - Connect `curr.next` to `temp`.
   - Move `curr` forward.
   - Move `temp` forward.
6. After traversal, set:
   ```text
   curr.next = null
   ```
7. Return `head`.

---

# Dry Run

### Input

```text
head = [1,1,2,3,3]
```

Initial:

```text
curr → 1
temp → 1
```

### Step 1

```text
curr.val == temp.val
1 == 1
```

Duplicate found.

Move `temp`:

```text
curr → 1
temp → 2
```

---

### Step 2

```text
1 != 2
```

Connect:

```text
curr.next = temp
```

Move both:

```text
curr → 2
temp → 3
```

---

### Step 3

```text
2 != 3
```

Connect and move:

```text
curr → 3
temp → 3
```

---

### Step 4

```text
3 == 3
```

Duplicate found.

Move `temp`:

```text
temp → null
```

Finally:

```text
curr.next = null
```

Result:

```text
[1,2,3]
```

---

# Why Does This Work?

Because the linked list is **sorted**, duplicate values are always adjacent.

For example:

```text
1 → 1 → 1 → 2 → 3 → 3
```

We don't need a `HashSet` or any extra data structure.

We only need to compare the current unique value with the next nodes.

This allows the problem to be solved in **one traversal**.

---

# Complexity Analysis

Let `n` be the number of nodes in the linked list.

### Time Complexity

```text
O(n)
```

Each node is visited at most once.

---

### Space Complexity

```text
O(1)
```

Only two pointers are used.

---

# Java Solution

```java
class Solution {

    public ListNode deleteDuplicates(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode curr = head;
        ListNode temp = head.next;

        while (temp != null) {

            if (curr.val == temp.val) {

                // Skip duplicate
                temp = temp.next;

            } else {

                // Keep unique node
                curr.next = temp;
                curr = curr.next;
                temp = temp.next;
            }
        }

        // Remove remaining duplicate nodes
        curr.next = null;

        return head;
    }
}
```

---

# Key Concepts

- Linked List
- Two Pointers
- Duplicate Removal
- Sorted Data
- In-Place Modification

---

# Constraints

- `0 <= number of nodes <= 300`
- `-100 <= Node.val <= 100`
- The linked list is sorted in ascending order.

---

# Learning Outcome

This problem demonstrates how the **sorted property of a linked list** can simplify duplicate removal.

Since duplicates are adjacent, we can remove them using only two pointers without using extra data structures such as a `HashSet`.

The solution modifies the linked list **in-place** and achieves:

```text
Time:  O(n)
Space: O(1)
```