# 2300. Successful Pairs of Spells and Potions

**LeetCode:** https://leetcode.com/problems/successful-pairs-of-spells-and-potions/

**Difficulty:** Medium

**Topics:** Array, Binary Search, Sorting

---

## Problem Statement

You are given two positive integer arrays:

- `spells`, where `spells[i]` represents the strength of the `i-th` spell.
- `potions`, where `potions[j]` represents the strength of the `j-th` potion.

You are also given an integer `success`.

A spell and potion form a **successful pair** if:

```text
spells[i] * potions[j] >= success
```

For every spell, return the number of potions that can form a successful pair with that spell.

The answer should be returned as an array where:

```text
answer[i] = number of successful potions for spells[i]
```

---

## Example 1

### Input

```text
spells = [5,1,3]
potions = [1,2,3,4,5]
success = 7
```

### Output

```text
[4,0,3]
```

### Explanation

For `spell = 5`:

```text
5 * 1 = 5   ❌
5 * 2 = 10  ✅
5 * 3 = 15  ✅
5 * 4 = 20  ✅
5 * 5 = 25  ✅
```

So there are `4` successful potions.

For `spell = 1`:

```text
1 * potion >= 7
```

No potion satisfies this condition.

So the answer is `0`.

For `spell = 3`:

```text
3 * 1 = 3   ❌
3 * 2 = 6   ❌
3 * 3 = 9   ✅
3 * 4 = 12  ✅
3 * 5 = 15  ✅
```

So there are `3` successful potions.

Therefore:

```text
[4,0,3]
```

---

## Example 2

### Input

```text
spells = [3,1,2]
potions = [8,5,8]
success = 16
```

### Output

```text
[2,0,2]
```

### Explanation

For `spell = 3`:

```text
3 * 5 = 15  ❌
3 * 8 = 24  ✅
3 * 8 = 24  ✅
```

There are `2` successful potions.

For `spell = 1`:

```text
1 * 5 = 5
1 * 8 = 8
1 * 8 = 8
```

None reaches `16`.

For `spell = 2`:

```text
2 * 5 = 10  ❌
2 * 8 = 16  ✅
2 * 8 = 16  ✅
```

There are `2` successful potions.

Therefore:

```text
[2,0,2]
```

---

# Approach

The main idea is:

1. Sort the `potions` array.
2. For each spell, calculate the **minimum potion strength required** to achieve `success`.
3. Use **binary search** to find the first potion whose strength is greater than or equal to this required value.
4. Every potion from that position to the end of the sorted array is successful.

This is a classic **Binary Search on a Sorted Array** problem.

---

# Intuition

For a particular spell:

```text
spell * potion >= success
```

We want:

```text
potion >= success / spell
```

Because potion strength is an integer, we need the smallest integer potion strength that satisfies the condition.

For example:

```text
spell = 3
success = 7
```

We need:

```text
3 * potion >= 7
```

Therefore:

```text
potion >= 7 / 3
```

Mathematically:

```text
potion >= 2.333...
```

Since potion strength is an integer:

```text
potion >= 3
```

So the minimum required potion strength is `3`.

---

# Avoiding Floating Point

Instead of calculating:

```text
ceil(success / spell)
```

using floating-point arithmetic, the solution uses:

```java
long require = (success + spells[i] - 1) / spells[i];
```

This calculates the ceiling of:

```text
success / spells[i]
```

using integer arithmetic.

The formula is:

```text
ceil(a / b) = (a + b - 1) / b
```

For example:

```text
success = 7
spell = 3

require = (7 + 3 - 1) / 3
        = 9 / 3
        = 3
```

So we need a potion with strength at least `3`.

---

# Why Sorting Helps

Suppose:

```text
potions = [1,2,3,4,5]
```

and for a particular spell:

```text
require = 3
```

The successful potions are:

```text
3,4,5
```

Because the array is sorted, once we find the first potion satisfying:

```text
potions[index] >= require
```

all elements after it will also satisfy the condition.

So instead of checking every potion, we only need to find the first valid index.

---

# Binary Search

We use two pointers:

```text
l = 0
r = n - 1
```

At every step:

```text
mid = l + (r - l) / 2
```

Then check:

```java
if(potions[mid] >= require)
```

### Case 1: Potion is sufficient

If:

```text
potions[mid] >= require
```

then `mid` can be a valid answer.

But there might be an earlier valid potion.

Therefore, search the left half:

```text
r = mid - 1
```

### Case 2: Potion is too weak

If:

```text
potions[mid] < require
```

then this potion cannot work.

Because the array is sorted, every potion before it is also too weak.

Therefore:

```text
l = mid + 1
```

At the end:

```text
l
```

points to the first potion that is strong enough.

---

# Counting Successful Potions

Suppose:

```text
potions = [1,2,3,4,5]
```

and binary search finds:

```text
l = 2
```

Then:

```text
potions[2] = 3
potions[3] = 4
potions[4] = 5
```

All three are successful.

Therefore:

```text
number of successful potions = n - l
```

For `n = 5`:

```text
5 - 2 = 3
```

---

# Algorithm

1. Store the sizes of `spells` and `potions`.
2. Sort the `potions` array.
3. Create an answer array of size `spells.length`.
4. For every spell:
   - Calculate the minimum potion strength required:

     ```text
     require = ceil(success / spell)
     ```

   - Perform binary search on `potions`.
   - Find the first index where:

     ```text
     potions[index] >= require
     ```

   - The number of successful potions is:

     ```text
     n - index
     ```

5. Return the answer array.

---

# Dry Run

Consider:

```text
spells = [5,1,3]
potions = [1,2,3,4,5]
success = 7
```

First sort the potions:

```text
[1,2,3,4,5]
```

---

## Spell = 5

Calculate the required potion:

```text
require = (7 + 5 - 1) / 5
        = 11 / 5
        = 2
```

We need:

```text
potion >= 2
```

Binary search:

```text
l = 0
r = 4
```

### Iteration 1

```text
mid = 2
potions[2] = 3
```

Since:

```text
3 >= 2
```

move left:

```text
r = 1
```

### Iteration 2

```text
mid = 0
potions[0] = 1
```

Since:

```text
1 < 2
```

move right:

```text
l = 1
```

### Iteration 3

```text
mid = 1
potions[1] = 2
```

Since:

```text
2 >= 2
```

move left:

```text
r = 0
```

Now:

```text
l = 1
r = 0
```

Binary search ends.

The first valid potion is at index `1`.

Therefore:

```text
answer = 5 - 1
       = 4
```

---

## Spell = 1

Calculate:

```text
require = (7 + 1 - 1) / 1
        = 7
```

We need:

```text
potion >= 7
```

No potion satisfies this.

Binary search ends with:

```text
l = 5
```

Therefore:

```text
answer = 5 - 5
       = 0
```

---

## Spell = 3

Calculate:

```text
require = (7 + 3 - 1) / 3
        = 9 / 3
        = 3
```

We need:

```text
potion >= 3
```

The first valid potion is:

```text
index = 2
```

Therefore:

```text
answer = 5 - 2
       = 3
```

Final result:

```text
[4,0,3]
```

---

# Java Solution

```java
class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int m = spells.length;
        int n = potions.length;

        Arrays.sort(potions);

        int[] ans = new int[m];

        for(int i = 0; i < m; i++){
            long require = (success + spells[i] - 1) / spells[i];

            int l = 0;
            int r = n - 1;

            while(l <= r){
                int mid = l + (r - l) / 2;

                if(potions[mid] >= require){
                    r = mid - 1;
                }
                else{
                    l = mid + 1;
                }
            }

            ans[i] = n - l;
        }

        return ans;
    }
}
```

---

# Code Explanation

## 1. Store Array Sizes

```java
int m = spells.length;
int n = potions.length;
```

`m` represents the number of spells.

`n` represents the number of potions.

---

## 2. Sort Potions

```java
Arrays.sort(potions);
```

Binary search requires the array to be sorted.

After sorting:

```text
potions[0] <= potions[1] <= ... <= potions[n-1]
```

This allows us to find the first valid potion efficiently.

---

## 3. Process Every Spell

```java
for(int i = 0; i < m; i++){
```

We need a separate answer for every spell.

---

## 4. Calculate Required Potion Strength

```java
long require = (success + spells[i] - 1) / spells[i];
```

This calculates:

```text
ceil(success / spells[i])
```

It tells us the minimum potion strength required for the current spell to form a successful pair.

For example:

```text
success = 7
spell = 3

require = 3
```

So any potion with strength at least `3` is successful.

---

## 5. Initialize Binary Search

```java
int l = 0;
int r = n - 1;
```

The binary search covers the complete sorted potion array.

---

## 6. Binary Search

```java
while(l <= r){
    int mid = l + (r - l) / 2;
```

We calculate the middle index.

The expression:

```text
l + (r - l) / 2
```

is preferred over:

```text
(l + r) / 2
```

because it avoids potential integer overflow.

---

## 7. Potion Is Strong Enough

```java
if(potions[mid] >= require){
    r = mid - 1;
}
```

If the current potion is sufficient, we try to find an even earlier sufficient potion.

This is what makes the binary search find the **first valid index**.

---

## 8. Potion Is Too Weak

```java
else{
    l = mid + 1;
}
```

If:

```text
potions[mid] < require
```

then `mid` and everything before it are invalid.

So we search the right half.

---

## 9. Count Successful Potions

```java
ans[i] = n - l;
```

After binary search, `l` is the first index satisfying:

```text
potions[l] >= require
```

Therefore all positions from `l` through `n - 1` are successful.

The number of such elements is:

```text
n - l
```

---

# Why `n - l` Works

Suppose:

```text
potions = [1,2,3,4,5]
```

and:

```text
l = 2
```

Then valid potions are:

```text
index 2 → 3
index 3 → 4
index 4 → 5
```

Number of elements:

```text
5 - 2 = 3
```

So:

```java
ans[i] = n - l;
```

correctly counts them.

---

# Complexity Analysis

Let:

- `m` = number of spells
- `n` = number of potions

## Time Complexity

Sorting the potions takes:

```text
O(n log n)
```

For every spell, we perform binary search:

```text
O(log n)
```

For `m` spells:

```text
O(m log n)
```

Therefore total time complexity is:

```text
O(n log n + m log n)
```

or:

```text
O((n + m) log n)
```

---

## Space Complexity

The answer array requires:

```text
O(m)
```

Additional algorithmic space apart from the output is:

```text
O(1)
```

Therefore:

```text
Auxiliary Space = O(1)
Output Space = O(m)
```

---

# Key Concepts / Patterns

## 1. Binary Search on Sorted Array

The primary technique is **Binary Search**.

We search for the first potion satisfying:

```text
potions[i] >= require
```

This is essentially a **lower bound** search.

---

## 2. Sorting + Binary Search

The combination is:

```text
Sort
  ↓
Find first valid element
  ↓
Count remaining elements
```

This is a very common pattern in array problems.

---

## 3. Lower Bound

The binary search implemented here finds the first index where:

```text
potions[index] >= require
```

This is known as a **lower bound**.

The general structure is:

```java
while(l <= r){
    int mid = l + (r - l) / 2;

    if(array[mid] >= target){
        r = mid - 1;
    }
    else{
        l = mid + 1;
    }
}
```

After the loop:

```text
l = first index where array[l] >= target
```

---

## 4. Ceiling Division

The expression:

```java
(success + spells[i] - 1) / spells[i]
```

calculates:

```text
ceil(success / spell)
```

without using floating-point arithmetic.

This is useful whenever we need the smallest integer `x` satisfying:

```text
spell * x >= success
```

---

# Important Observation

The original condition is:

```text
spell * potion >= success
```

Instead of checking every potion, transform it into:

```text
potion >= ceil(success / spell)
```

This converts the problem into:

```text
Find how many sorted potions are >= required value
```

That is exactly what binary search is good at.

---

# Pattern Recognition

When you see a problem with:

```text
Sorted array
+
Find first/last element satisfying a condition
+
Count elements after/before it
```

think:

```text
Binary Search
```

For this problem:

```text
spell × potion >= success
```

becomes:

```text
potion >= required
```

Then:

```text
Find first valid potion
```

and:

```text
number of valid potions = n - firstIndex
```

---

# Constraints

The important constraints make the binary-search approach necessary because there can be a large number of spells and potions.

The product:

```text
spells[i] * potions[j]
```

can exceed the range of a Java `int`, so the solution uses `long` for:

```java
long success
long require
```

This prevents overflow while calculating the required potion strength.

---

# Learning Outcome

After solving this problem, you should understand:

- How sorting can enable binary search.
- How to find the first valid element using lower-bound binary search.
- How to convert a multiplication condition into a minimum required value.
- How to use ceiling division with integers.
- How to count all valid elements after finding their first index.
- How to choose `long` when calculations may exceed `int` range.
- How to combine sorting and binary search efficiently.

---

# Final Takeaway

The brute-force approach would check every spell against every potion:

```text
O(m × n)
```

which can be too slow.

The optimized approach is:

```text
Sort potions
     ↓
For each spell
     ↓
Calculate minimum required potion
     ↓
Binary search first valid potion
     ↓
Count remaining potions
```

The final complexity is:

```text
O(n log n + m log n)
```

with:

```text
O(1)
```

auxiliary space apart from the output array.

The core pattern to remember is:

```text
Sort + Lower Bound Binary Search
```