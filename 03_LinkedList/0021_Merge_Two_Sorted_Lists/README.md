# 21. Merge Two Sorted Lists

> **Difficulty:** Easy  
> **Topics:** Linked List, Two Pointers

---

## Problem Statement

You are given the heads of two sorted linked lists:

```text
list1
list2
```

Merge both lists into **one sorted linked list**.

The merged list should be created by **splicing together the existing nodes** of the two lists.

Return the head of the merged linked list.

---

## Example 1

### Input

```text
list1 = [1,2,4]
list2 = [1,3,4]
```

### Output

```text
[1,1,2,3,4,4]
```

### Explanation

Both lists are already sorted:

```text
1 → 2 → 4
1 → 3 → 4
```

Compare the current nodes one by one and attach the smaller node.

Result:

```text
1 → 1 → 2 → 3 → 4 → 4
```

---

## Example 2

### Input

```text
list1 = []
list2 = []
```

### Output

```text
[]
```

Both lists are empty.

---

## Example 3

### Input

```text
list1 = []
list2 = [0]
```

### Output

```text
[0]
```

If one list is empty, simply return the other list.

---

# Approach

Since both linked lists are already sorted, we can use a **two-pointer technique**.

We maintain:

```text
list1
list2
```

and compare their current values.

At every step:

- If `list1.val < list2.val`, attach `list1`.
- Otherwise, attach `list2`.

Then move the selected pointer forward.

---

## Dummy Node

We create a dummy node:

```text
dummy → ?
```

and maintain another pointer:

```text
temp
```

Initially:

```text
dummy
  ↓
 -1
```

`temp` points to the last node of the merged list.

Using a dummy node avoids special handling for the first node.

At the end:

```java
return dummy.next;
```

because `dummy` itself is not part of the answer.

---

# Algorithm

### Step 1

Create a dummy node:

```java
ListNode dummy = new ListNode(-1);
```

Set:

```java
ListNode temp = dummy;
```

---

### Step 2

While both lists contain nodes:

```java
while(list1 != null && list2 != null)
```

compare their values.

If:

```java
list1.val < list2.val
```

attach `list1`:

```java
temp.next = list1;
list1 = list1.next;
```

Otherwise attach `list2`:

```java
temp.next = list2;
list2 = list2.next;
```

Then move:

```java
temp = temp.next;
```

---

### Step 3

One list may still contain nodes.

For example:

```text
list1 → 7 → 8 → 9
list2 → null
```

Since `list1` is already sorted, we can directly attach the remaining part:

```java
temp.next = list1;
```

Similarly for `list2`.

---

# Java Solution

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;

        while(list1 != null && list2 != null){

            if(list1.val < list2.val){
                temp.next = list1;
                list1 = list1.next;
            }
            else{
                temp.next = list2;
                list2 = list2.next;
            }

            temp = temp.next;
        }

        // Attach remaining nodes
        if(list1 != null){
            temp.next = list1;
        }

        if(list2 != null){
            temp.next = list2;
        }

        return dummy.next;
    }
}
```

---

# Dry Run

Consider:

```text
list1 = 1 → 2 → 4
list2 = 1 → 3 → 4
```

Initially:

```text
dummy → null
temp
 ↓
dummy
```

---

### Comparison 1

```text
list1 = 1
list2 = 1
```

Since:

```text
1 < 1
```

is false, choose `list2`.

```text
dummy → 1
```

Move `list2`.

---

### Comparison 2

```text
list1 = 1
list2 = 3
```

Choose `list1`.

```text
dummy → 1 → 1
```

Move `list1`.

---

### Comparison 3

```text
list1 = 2
list2 = 3
```

Choose `list1`.

```text
dummy → 1 → 1 → 2
```

---

### Comparison 4

```text
list1 = 4
list2 = 3
```

Choose `list2`.

```text
dummy → 1 → 1 → 2 → 3
```

---

### Comparison 5

```text
list1 = 4
list2 = 4
```

Choose `list2`.

```text
dummy → 1 → 1 → 2 → 3 → 4
```

Now:

```text
list2 = null
```

---

### Attach Remaining Nodes

`list1` still contains:

```text
4
```

So:

```java
temp.next = list1;
```

Final list:

```text
1 → 1 → 2 → 3 → 4 → 4
```

Return:

```java
dummy.next
```

---

# Why This Works

Both lists are sorted.

Therefore, at any point, the smallest remaining element must be either:

```text
list1.val
```

or:

```text
list2.val
```

By choosing the smaller one, we guarantee that the merged list remains sorted.

Once one list becomes empty, all remaining nodes of the other list are already sorted, so they can be attached directly.

---

# Complexity Analysis

Let:

```text
m = number of nodes in list1
n = number of nodes in list2
```

Every node is visited at most once.

### Time Complexity

```text
O(m + n)
```

### Space Complexity

```text
O(1)
```

We do not create new nodes for the merged list. We reuse the existing nodes.

---

# Important Linked List Pattern

This problem teaches an important pattern:

```text
Two Sorted Lists
       ↓
Compare current nodes
       ↓
Attach smaller node
       ↓
Move that pointer
       ↓
Repeat
       ↓
Attach remaining list
```

The core code is:

```java
while(list1 != null && list2 != null){

    if(list1.val < list2.val){
        temp.next = list1;
        list1 = list1.next;
    }
    else{
        temp.next = list2;
        list2 = list2.next;
    }

    temp = temp.next;
}
```

Then:

```java
if(list1 != null){
    temp.next = list1;
}

if(list2 != null){
    temp.next = list2;
}
```

---

## Key Concepts

- Linked List
- Two Pointers
- Dummy Node
- Sorted Lists
- Pointer Manipulation
- In-place List Merging

---

## Your Solution

Your solution is **correct and optimal**.

One small observation:

```java
if(list1.val < list2.val)
```

uses `<`, so when both values are equal, you select `list2`.

That's completely fine because both nodes have the same value and the resulting list is still sorted.

Your solution achieves:

```text
Time  → O(m + n)
Space → O(1)
```

which is the optimal solution for this problem.