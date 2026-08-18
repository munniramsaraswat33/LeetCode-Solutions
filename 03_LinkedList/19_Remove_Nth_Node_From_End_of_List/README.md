# 19. Remove Nth Node From End of List

> **Difficulty:** Medium  
> **Topics:** Linked List, Two Pointers

---

## Problem Statement

Given the head of a singly linked list, remove the `nth` node from the **end** of the list and return the head of the modified list.

The follow-up asks us to solve the problem in **one pass**.

---

## Example 1

### Input

```text
head = [1,2,3,4,5]
n = 2
```

### Output

```text
[1,2,3,5]
```

### Explanation

The 2nd node from the end is `4`.

```text
1 → 2 → 3 → 4 → 5
            ↑
          remove
```

After removing it:

```text
1 → 2 → 3 → 5
```

---

## Example 2

### Input

```text
head = [1]
n = 1
```

### Output

```text
[]
```

The only node is the 1st node from the end, so it is removed.

---

## Example 3

### Input

```text
head = [1,2]
n = 1
```

### Output

```text
[1]
```

The last node `2` is removed.

---

# Approach 1: Your Current Approach

Your solution works by finding the length of the linked list first.

### Step 1

Traverse the entire list to calculate its length:

```text
1 → 2 → 3 → 4 → 5

length = 5
```

### Step 2

If:

```text
length == n
```

then the node to remove is the head.

For example:

```text
length = 5
n = 5
```

Remove:

```text
1 → 2 → 3 → 4 → 5
↑
remove
```

Result:

```text
2 → 3 → 4 → 5
```

### Step 3

Otherwise, move to the node just before the node that needs to be deleted.

Then:

```java
t.next = t.next.next;
```

removes the target node.

---

# Approach 2: Optimal Two-Pointer Approach

The follow-up asks:

> Could you do this in one pass?

Yes.

We use two pointers:

```text
fast
slow
```

and a dummy node.

Initially:

```text
dummy → 1 → 2 → 3 → 4 → 5
  ↑
 slow

fast
```

Move `fast` ahead by `n + 1` positions.

For:

```text
n = 2
```

we maintain a gap of 3 nodes between `slow` and `fast`.

Eventually:

```text
dummy → 1 → 2 → 3 → 4 → 5 → null
          ↑           ↑
         slow        fast
```

When `fast` becomes `null`, `slow.next` is exactly the node that needs to be removed.

So:

```java
slow.next = slow.next.next;
```

removes it.

---

## Why Do We Use a Dummy Node?

The dummy node makes the head-removal case much easier.

Consider:

```text
[1,2,3]
n = 3
```

We need to remove the head.

Without a dummy node, we need a special condition.

With a dummy node:

```text
dummy → 1 → 2 → 3
```

`slow` will stop at:

```text
dummy
```

Then:

```java
slow.next = slow.next.next;
```

automatically removes `1`.

Result:

```text
2 → 3
```

Therefore, no special head condition is required.

---

# Java Solution

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // Move fast n + 1 steps ahead
        for(int i = 0; i <= n; i++){
            fast = fast.next;
        }

        // Move both pointers
        while(fast != null){
            fast = fast.next;
            slow = slow.next;
        }

        // Remove nth node from the end
        slow.next = slow.next.next;

        return dummy.next;
    }
}
```

---

# Dry Run

Consider:

```text
head = [1,2,3,4,5]
n = 2
```

Initially:

```text
dummy → 1 → 2 → 3 → 4 → 5 → null
  ↑
 slow
 fast
```

Move `fast` `n + 1 = 3` steps:

```text
dummy → 1 → 2 → 3 → 4 → 5 → null
  ↑                   ↑
 slow                fast
```

Now move both together.

### First move

```text
dummy → 1 → 2 → 3 → 4 → 5 → null
         ↑                   ↑
        slow                fast
```

### Second move

```text
dummy → 1 → 2 → 3 → 4 → 5 → null
             ↑                   ↑
            slow                fast
```

### Third move

```text
dummy → 1 → 2 → 3 → 4 → 5 → null
                 ↑                   ↑
                slow                fast
```

Now `fast == null`.

Therefore:

```text
slow = 3
slow.next = 4
```

Remove node `4`:

```java
slow.next = slow.next.next;
```

Result:

```text
1 → 2 → 3 → 5
```

---

# Why `n + 1` Steps?

This is the most important part.

We want `slow` to reach the node **before** the node that needs to be deleted.

For:

```text
1 → 2 → 3 → 4 → 5
```

and:

```text
n = 2
```

we want:

```text
slow → 3
```

because:

```text
3 → 4 → 5
    ↑
   delete
```

Using a dummy node, the initial distance between `fast` and `slow` must be:

```text
n + 1
```

Therefore:

```java
for(int i = 0; i <= n; i++){
    fast = fast.next;
}
```

moves `fast` exactly `n + 1` positions.

---

# Complexity Analysis

### Time Complexity

We traverse the linked list only once:

```text
O(n)
```

### Space Complexity

Only two pointers and one dummy node are used:

```text
O(1)
```

---

# Comparison With Your Solution

| Approach | Time | Extra Space | Passes |
|---|---:|---:|---:|
| Your length-based approach | O(n) | O(1) | 2 |
| Two-pointer approach | O(n) | O(1) | 1 |

Your solution is **correct**, but the two-pointer solution is better because it satisfies the follow-up requirement of doing it in **one pass**.

---

# Key Pattern to Remember

Whenever a linked-list problem asks for:

> `nth` node from the end

think:

```text
Two Pointers
     ↓
Create a gap of n nodes
     ↓
Move both together
     ↓
Slow reaches required position
```

For deletion, use:

```java
slow.next = slow.next.next;
```

And using a dummy node makes edge cases much easier.

---

## Important Linked List Pattern

```text
dummy → head

fast = dummy
slow = dummy

Move fast n + 1 steps

while(fast != null) {
    fast = fast.next;
    slow = slow.next;
}

slow.next = slow.next.next;

return dummy.next;
```

This pattern is useful for many **"nth from end"** linked-list problems.