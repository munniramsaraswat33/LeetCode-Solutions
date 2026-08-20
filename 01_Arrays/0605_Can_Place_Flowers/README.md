# 605. Can Place Flowers

> **Difficulty:** Easy  
> **Topics:** Array, Greedy

---

## Problem Statement

You have a long flowerbed in which some plots are already planted and some are empty.

Flowers **cannot be planted in adjacent plots**.

You are given:

- An integer array `flowerbed`
- `0` means the plot is empty.
- `1` means the plot already contains a flower.
- An integer `n`, representing the number of new flowers that need to be planted.

Return `true` if `n` new flowers can be planted without violating the no-adjacent-flowers rule.

Otherwise, return `false`.

---

## Example 1

### Input

```text
flowerbed = [1,0,0,0,1]
n = 1
```

### Output

```text
true
```

### Explanation

We can plant a flower at index `2`:

```text
[1,0,1,0,1]
```

There are no adjacent flowers, so one flower can be planted.

---

## Example 2

### Input

```text
flowerbed = [1,0,0,0,1]
n = 2
```

### Output

```text
false
```

### Explanation

Only one new flower can be planted:

```text
[1,0,1,0,1]
```

We cannot plant another flower without making two flowers adjacent.

Therefore, the answer is `false`.

---

# Approach

Use a **Greedy** approach.

Traverse the flowerbed from left to right.

At every position, check whether a flower can be planted.

A flower can be planted at index `i` only when:

```text
current plot is empty
AND
left plot is empty or does not exist
AND
right plot is empty or does not exist
```

In code:

```java
f[i] == 0
```

and

```java
i == 0 || f[i-1] == 0
```

and

```java
i == f.length-1 || f[i+1] == 0
```

If all three conditions are satisfied, plant a flower there.

After planting:

```java
f[i] = 1;
n--;
```

We can modify the array because placing the flower immediately affects the next position.

---

# Algorithm

1. If `n == 0`, return `true`.
2. Traverse the flowerbed from left to right.
3. For every index `i`, check:
   - `flowerbed[i] == 0`
   - `i == 0` or left neighbor is `0`
   - `i == flowerbed.length - 1` or right neighbor is `0`
4. If all conditions are satisfied:
   - Place a flower.
   - Decrease `n`.
5. If `n` becomes `0`, return `true`.
6. After traversing the complete flowerbed, return `false`.

---

# Dry Run

Input:

```text
flowerbed = [1,0,0,0,1]
n = 1
```

### Index 0

```text
flowerbed[0] = 1
```

Already occupied.

```text
[1,0,0,0,1]
```

No change.

---

### Index 1

```text
flowerbed[1] = 0
```

Left:

```text
1
```

So we cannot plant here.

---

### Index 2

Current:

```text
0
```

Left:

```text
0
```

Right:

```text
0
```

Therefore, we can plant.

```text
[1,0,1,0,1]
```

Now:

```text
n = 0
```

So return:

```text
true
```

---

# Understanding the Code

## Check `n == 0`

```java
if(n == 0){
    return true;
}
```

If we don't need to plant any flowers, the answer is immediately `true`.

---

## Traverse the Array

```java
for(int i = 0; i < f.length; i++){
```

We check every plot from left to right.

---

## Check Current Plot

```java
f[i] == 0
```

The current plot must be empty.

If:

```text
f[i] == 1
```

a flower is already present, so we cannot plant another one.

---

## Check Left Neighbor

```java
i == 0 || f[i-1] == 0
```

If `i == 0`, there is no left neighbor, so the condition is automatically true.

Otherwise, the left plot must be empty.

---

## Check Right Neighbor

```java
i == f.length-1 || f[i+1] == 0
```

If `i` is the last position, there is no right neighbor.

Otherwise, the right plot must be empty.

---

## Plant the Flower

```java
f[i] = 1;
n--;
```

Once all conditions are satisfied, we place a flower and decrease the number of flowers still needed.

---

## Early Return

```java
if(n == 0){
    return true;
}
```

As soon as all required flowers have been planted, we don't need to traverse the remaining array.

---

# Why Greedy Works?

Whenever we find a valid position, we immediately plant a flower there.

Planting at the earliest possible position is safe because:

```text
current = 0
left = 0 or doesn't exist
right = 0 or doesn't exist
```

So placing a flower there does not violate the rule.

We continue from left to right and make the best possible local choice at every position.

This is a common **Greedy** pattern:

```text
Find a valid choice
       ↓
Take it immediately
       ↓
Update the state
       ↓
Continue
```

---

# Complexity Analysis

### Time Complexity

We traverse the flowerbed once:

```text
O(n)
```

where `n` is the length of the flowerbed.

---

### Space Complexity

No extra data structure is used.

```text
O(1)
```

The input array is modified in-place.

---

# Java Solution

```java
class Solution {

    public boolean canPlaceFlowers(int[] f, int n) {

        if(n == 0){
            return true;
        }

        for(int i = 0; i < f.length; i++){

            if(f[i] == 0 &&
               (i == 0 || f[i-1] == 0) &&
               (i == f.length-1 || f[i+1] == 0)){

                f[i] = 1;
                n--;
            }

            if(n == 0){
                return true;
            }
        }

        return false;
    }
}
```

---

# Key Concepts

- Array
- Greedy
- Array Traversal
- In-place Modification
- Neighbor Checking
- Boundary Conditions

---

# Constraints

- `1 <= flowerbed.length <= 2 * 10^4`
- `flowerbed[i]` is either `0` or `1`.
- There are no two adjacent flowers initially.
- `0 <= n <= flowerbed.length`

---

# Learning Outcome

This problem demonstrates a simple **Greedy Array** technique.

The main idea is to scan from left to right and place a flower whenever the current position and its neighbors allow it.

The important condition is:

```java
f[i] == 0 &&
(i == 0 || f[i-1] == 0) &&
(i == f.length-1 || f[i+1] == 0)
```

If the condition is true:

```java
f[i] = 1;
n--;
```

The solution works in:

```text
Time:  O(n)
Space: O(1)
```

This is a useful pattern for problems where we make the **earliest valid local choice** while maintaining a constraint.