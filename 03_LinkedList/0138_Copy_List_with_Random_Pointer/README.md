# 138. Copy List with Random Pointer

> **Difficulty:** Medium  
> **Topics:** Linked List, Hash Table

---

## Problem Statement

You are given a linked list where each node contains:

- `val` — the value of the node.
- `next` — pointer to the next node.
- `random` — pointer to any node in the list or `null`.

Create a **deep copy** of the linked list.

The copied list must contain completely new nodes, and none of the pointers in the copied list should point to nodes from the original list.

---

## Example 1

### Input

```text
head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
```

### Output

```text
[[7,null],[13,0],[11,4],[10,2],[1,0]]
```

The values and pointer relationships are the same, but all nodes in the output are newly created.

---

## Example 2

### Input

```text
head = [[1,1],[2,1]]
```

### Output

```text
[[1,1],[2,1]]
```

Both nodes are copied and their `random` pointers point to the corresponding copied node.

---

## Example 3

### Input

```text
head = [[3,null],[3,0],[3,null]]
```

### Output

```text
[[3,null],[3,0],[3,null]]
```

The duplicate values do not matter because each node is treated as a separate object.

---

# What Is a Deep Copy?

A deep copy creates **completely new nodes**.

For example, suppose the original list is:

```text
Original:

A → B → C
```

If:

```text
A.random → C
```

then the copied list should be:

```text
Copy:

a → b → c
```

and:

```text
a.random → c
```

The important point is:

```text
a != A
b != B
c != C
```

The copied nodes must not reference any original nodes.

---

# Approach

The main difficulty is the `random` pointer because it can point to **any node** in the list.

We use a `HashMap` to maintain the relationship:

```text
Original Node → Copied Node
```

For example:

```text
A → a
B → b
C → c
```

This allows us to find the copied version of any original node in `O(1)` average time.

---

# Two-Pass Approach

We solve the problem in two passes.

## Pass 1: Create All Nodes

Traverse the original list and create a new node for every original node.

Store the mapping:

```text
original node → copied node
```

For example:

```text
Original: A → B → C

HashMap:

A → a
B → b
C → c
```

At this point, all new nodes exist, but their `next` and `random` pointers are not connected yet.

---

## Pass 2: Connect the Pointers

Traverse the original list again.

For every original node:

```text
curr
```

find its copied node:

```text
temp = hm.get(curr)
```

Then connect:

```text
temp.next = hm.get(curr.next)
temp.random = hm.get(curr.random)
```

Because every original node already has a corresponding copy in the HashMap, both pointers can be connected easily.

---

# Algorithm

1. If `head == null`, return `null`.
2. Create a `HashMap<Node, Node>`.
3. Traverse the original list:
   - Create a new node for each original node.
   - Store the mapping in the HashMap.
4. Traverse the original list again:
   - Set the copied node's `next` pointer.
   - Set the copied node's `random` pointer.
5. Return the copied version of `head`.

---

# Dry Run

Suppose:

```text
Original:

A → B
```

and:

```text
A.random → B
B.random → A
```

### Pass 1

Create copies:

```text
A → a
B → b
```

HashMap:

```text
A → a
B → b
```

---

### Pass 2

For `A`:

```text
a.next = hm.get(B)
       = b

a.random = hm.get(B)
         = b
```

For `B`:

```text
b.next = null

b.random = hm.get(A)
         = a
```

Final copied list:

```text
a → b
```

with:

```text
a.random → b
b.random → a
```

No copied node points back to the original list.

---

# Why HashMap Is Needed

The `random` pointer can point anywhere:

```text
A.random → C
B.random → A
C.random → B
```

When processing `A`, we need to quickly find the copied version of `C`.

The HashMap provides:

```text
hm.get(C)
```

which gives:

```text
c
```

Therefore, the random pointer can be connected directly.

---

# Complexity Analysis

Let `n` be the number of nodes.

### Time Complexity

We traverse the list twice:

```text
O(n) + O(n)
```

Therefore:

```text
O(n)
```

---

### Space Complexity

The HashMap stores one mapping for every node:

```text
O(n)
```

The copied linked list also contains `n` new nodes.

The **auxiliary space** used by the HashMap is:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public Node copyRandomList(Node head) {

        if (head == null) {
            return null;
        }

        HashMap<Node, Node> hm = new HashMap<>();

        // Pass 1: Create copied nodes
        Node curr = head;

        while (curr != null) {

            hm.put(curr, new Node(curr.val));

            curr = curr.next;
        }

        // Pass 2: Connect next and random pointers
        curr = head;

        while (curr != null) {

            Node temp = hm.get(curr);

            temp.next = hm.get(curr.next);
            temp.random = hm.get(curr.random);

            curr = curr.next;
        }

        return hm.get(head);
    }
}
```

---

# Key Concepts

- Linked List
- HashMap
- Deep Copy
- Object Mapping
- Random Pointers
- Two-Pass Traversal

---

# Constraints

- `0 <= n <= 1000`
- `-10⁴ <= Node.val <= 10⁴`
- `Node.random` is either `null` or points to a node in the linked list.

---

# Learning Outcome

This problem demonstrates how a **HashMap can be used to maintain a relationship between original and copied objects**.

The most important idea is:

```text
Original Node → Copied Node
```

Once this mapping is created, both `next` and `random` pointers can be connected easily.

The HashMap approach provides:

```text
Time:  O(n)
Space: O(n)
```

and is much easier to understand than the advanced **O(1) extra-space interleaving approach**.