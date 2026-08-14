# 11. Container With Most Water

> **Difficulty:** Medium  
> **Topics:** Array, Two Pointers, Greedy

---

## Problem Statement

You are given an integer array `height`.

Each element represents the height of a vertical line at that index.

Two lines together with the x-axis form a container.

The amount of water the container can hold is:

```text
Area = width × height
```

where:

```text
width = rightIndex - leftIndex
height = min(height[leftIndex], height[rightIndex])
```

Return the **maximum amount of water** that can be stored.

The container cannot be slanted.

---

## Example 1

### Input

```text
height = [1,8,6,2,5,4,8,3,7]
```

### Output

```text
49
```

### Explanation

Choose:

```text
left = 1
right = 8
```

Heights:

```text
height[1] = 8
height[8] = 7
```

The effective height is:

```text
min(8,7) = 7
```

Width:

```text
8 - 1 = 7
```

Therefore:

```text
Area = 7 × 7
     = 49
```

This is the maximum possible area.

---

## Example 2

### Input

```text
height = [1,1]
```

### Output

```text
1
```

Calculation:

```text
height = min(1,1) = 1
width = 1
```

Therefore:

```text
Area = 1 × 1 = 1
```

---

# Approach

A brute-force solution would check every pair of lines.

For `n` lines, that would take:

```text
O(n²)
```

time.

Instead, we use the **Two Pointer** technique.

Start with:

```text
left = 0
right = n - 1
```

This gives us the maximum possible width.

At every step:

1. Calculate the current area.
2. Update the maximum area.
3. Move the pointer corresponding to the **shorter line**.

---

# Area Formula

For two pointers:

```text
left
right
```

the width is:

```java
int wt = right - left;
```

The container height is limited by the shorter line:

```java
int ht = Math.min(height[left], height[right]);
```

Therefore:

```java
int area = ht * wt;
```

---

# Why Move the Smaller Height?

This is the most important idea in the problem.

Suppose:

```text
height[left] < height[right]
```

The current container is limited by:

```text
height[left]
```

If we move the `right` pointer:

```text
right--
```

the width becomes smaller.

But the limiting height can never become greater than `height[left]` because the left line is still the same.

So moving the taller line cannot help us find a better area.

Instead, we move the shorter line:

```java
left++;
```

This gives the possibility of finding a taller boundary.

---

# Example

Suppose:

```text
left height  = 3
right height = 8
```

Current container height:

```text
min(3,8) = 3
```

If we move the right pointer:

```text
3 |-------------| 8
                  ↓
                move
```

the width decreases, but the left height is still `3`.

So the maximum possible height is still `3`.

There is no chance of improving the area because:

```text
width decreases
height <= 3
```

Instead, move the shorter side:

```text
3 → next line
```

Now we might find a height greater than `3`.

---

# Algorithm

1. Set:
   ```text
   left = 0
   right = n - 1
   ```
2. Initialize:
   ```text
   maxArea = 0
   ```
3. While `left < right`:
   - Calculate the current height:
     ```text
     min(height[left], height[right])
     ```
   - Calculate the width:
     ```text
     right - left
     ```
   - Calculate the current area.
   - Update `maxArea`.
   - If the left height is smaller:
     ```text
     left++
     ```
   - Otherwise:
     ```text
     right--
     ```
4. Return `maxArea`.

---

# Dry Run

### Input

```text
height = [1,8,6,2,5,4,8,3,7]
```

Initially:

```text
left = 0
right = 8
```

Heights:

```text
1 and 7
```

Height:

```text
min(1,7) = 1
```

Width:

```text
8 - 0 = 8
```

Area:

```text
1 × 8 = 8
```

Maximum:

```text
maxArea = 8
```

Since:

```text
height[left] < height[right]
1 < 7
```

move:

```text
left++
```

---

Continue moving the shorter side and calculating the area.

Eventually:

```text
left = 1
right = 8
```

Heights:

```text
8 and 7
```

Height:

```text
7
```

Width:

```text
8 - 1 = 7
```

Area:

```text
7 × 7 = 49
```

So:

```text
maxArea = 49
```

No other pair produces a larger area.

---

# Java Solution

```java
class Solution {

    public int maxArea(int[] height) {

        int mw = 0;

        int si = 0;
        int ei = height.length - 1;

        while (si != ei) {

            int ht = Math.min(
                height[si],
                height[ei]
            );

            int wt = ei - si;

            int cw = ht * wt;

            mw = Math.max(mw, cw);

            // Move the shorter line
            if (height[si] < height[ei]) {
                si++;
            } else {
                ei--;
            }
        }

        return mw;
    }
}
```

---

# Why Does the Algorithm Work?

At every step, the current area is:

```text
min(leftHeight, rightHeight) × width
```

The width always decreases as the pointers move inward.

Therefore, to potentially improve the area, we need to increase the limiting height.

The limiting height is the **shorter line**.

So:

```text
shorter line → move
taller line   → keep
```

This allows us to eliminate many impossible pairs without checking them individually.

---

# Complexity Analysis

Let `n` be the number of lines.

### Time Complexity

Each pointer moves only toward the other pointer.

Therefore, each element is processed at most once:

```text
O(n)
```

---

### Space Complexity

Only a few variables are used:

```text
O(1)
```

---

# Key Concepts

- Two Pointers
- Greedy Approach
- Array
- Maximum Area
- Space Optimization

---

# Constraints

```text
2 <= n <= 10⁵
0 <= height[i] <= 10⁴
```

---

# Learning Outcome

The main pattern to remember is:

```text
Start from both ends
        ↓
Calculate current area
        ↓
Move the shorter height
        ↓
Repeat
```

The most important line is:

```java
if (height[si] < height[ei]) {
    si++;
} else {
    ei--;
}
```

### Why?

Because the shorter line limits the amount of water.

To improve the area, we need a chance to find a **taller shorter boundary**, so we move the shorter pointer.

### Complexity

```text
Time:  O(n)
Space: O(1)
```