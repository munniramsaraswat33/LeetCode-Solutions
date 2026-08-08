# 2. Add Two Numbers

> **Difficulty:** Medium  
> **Topics:** Linked List, Math

---

## Problem Statement

You are given two non-empty linked lists representing two non-negative integers.

The digits are stored in **reverse order**, and each node contains a single digit.

Add the two numbers and return the sum as a linked list.

The result should also be stored in reverse order.

---

## Example 1

### Input

```text
l1 = [2,4,3]
l2 = [5,6,4]
```

### Output

```text
[7,0,8]
```

### Explanation

The linked lists represent:

```text
342 + 465 = 807
```

Since the digits are stored in reverse order:

```text
807 → [7,0,8]
```

---

## Example 2

### Input

```text
l1 = [0]
l2 = [0]
```

### Output

```text
[0]
```

---

## Example 3

### Input

```text
l1 = [9,9,9,9,9,9,9]
l2 = [9,9,9,9]
```

### Output

```text
[8,9,9,9,0,0,0,1]
```

---

# Approach

Since the digits are stored in **reverse order**, we can add the two linked lists from left to right just like normal column addition.

We maintain a `carry` value for sums greater than `9`.

For every pair of nodes:

```text
sum = l1.val + l2.val + carry
```

The digit stored in the result is:

```text
sum % 10
```

The carry for the next position is:

```text
sum / 10
```

We continue until:

- `l1` is exhausted,
- `l2` is exhausted, and
- there is no remaining carry.

---

# Algorithm

1. Create a dummy node to simplify result-list construction.
2. Maintain a pointer `curr` to the last node of the result.
3. Initialize:

```text
carry = 0
```

4. While either list has nodes or a carry remains:
   - Start with the current `carry`.
   - Add `l1.val` if `l1` is not null.
   - Add `l2.val` if `l2` is not null.
   - Calculate the new carry.
   - Create a new node containing the current digit.
5. Move `curr` forward.
6. Return `dummy.next`.

---

# Dry Run

### Input

```text
l1 = [2,4,3]
l2 = [5,6,4]
```

### Step 1

```text
2 + 5 = 7
```

Digit:

```text
7
```

Carry:

```text
0
```

Result:

```text
[7]
```

---

### Step 2

```text
4 + 6 = 10
```

Digit:

```text
0
```

Carry:

```text
1
```

Result:

```text
[7,0]
```

---

### Step 3

```text
3 + 4 + 1 = 8
```

Digit:

```text
8
```

Carry:

```text
0
```

Result:

```text
[7,0,8]
```

Therefore:

```text
342 + 465 = 807
```

---

# Why Do We Need a Dummy Node?

Without a dummy node, we would need special handling for the first node of the result.

Using:

```text
ListNode dummy = new ListNode(0);
ListNode curr = dummy;
```

allows every new digit to be added using the same operation:

```text
curr.next = new ListNode(digit);
curr = curr.next;
```

Finally:

```text
return dummy.next;
```

returns the actual result list.

---

# Complexity Analysis

Let `n` and `m` be the lengths of `l1` and `l2`.

### Time Complexity

```text
O(max(n, m))
```

Each node is visited once.

---

### Space Complexity

```text
O(max(n, m))
```

The result linked list contains at most:

```text
max(n, m) + 1
```

nodes because of a possible final carry.

---

# Java Solution

```java
class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {

            int sum = carry;

            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            carry = sum / 10;

            curr.next = new ListNode(sum % 10);

            curr = curr.next;
        }

        return dummy.next;
    }
}
```

---

# Key Concepts

- Linked List
- Carry Handling
- Digit-by-Digit Addition
- Dummy Node
- Iterative Traversal

---

# Constraints

- Number of nodes in each linked list: `1 <= n <= 100`
- `0 <= Node.val <= 9`
- Numbers do not contain leading zeros unless the number itself is `0`.

---

# Learning Outcome

This problem demonstrates how to perform arithmetic directly on **linked lists** without converting the lists into actual integers.

The most important ideas are:

1. Process digits from least significant to most significant.
2. Maintain a `carry`.
3. Use a **dummy node** to simplify linked-list construction.
4. Continue processing while a final carry remains.

This produces an efficient **O(max(n, m))** solution.