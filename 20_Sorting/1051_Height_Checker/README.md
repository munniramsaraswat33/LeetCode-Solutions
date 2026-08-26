# 1051. Height Checker

> **Difficulty:** Easy  
> **Topics:** Array, Counting Sort, Sorting

---

## Problem Statement

A school has a line of students represented by an integer array `heights`.

The value:

```text
heights[i]
```

It represents the height of the student at position `i`.

The students are currently standing in a certain order.

The expected order is the order obtained by sorting all heights in **non-decreasing order**.

Return the number of indices where the current height is different from the height at that index in the expected sorted order.

In other words, count how many students are standing in the wrong position.

---

## Example 1

### Input

```text
heights = [1,1,4,2,1,3]
```

### Output

```text
3
```

### Explanation

The expected order is:

```text
[1,1,1,2,3,4]
```

Compare the original and expected arrays:

```text
Original: [1,1,4,2,1,3]
Expected: [1,1,1,2,3,4]
                    ↑
```

The mismatched positions are:

```text
index 2
index 4
index 5
```

Therefore:

```text
Answer = 3
```

---

## Example 2

### Input

```text
heights = [5,1,2,3,4]
```

### Output

```text
5
```

### Explanation

Expected order:

```text
[1,2,3,4,5]
```

Every position is different from the original array.

Therefore:

```text
Answer = 5
```

---

## Example 3

### Input

```text
heights = [1,2,3,4,5]
```

### Output

```text
0
```

### Explanation

The array is already sorted.

Therefore, no student needs to move.

```text
Answer = 0
```

---

# Approach

Use **Counting Sort** instead of comparison-based sorting.

The height values are limited to:

```text
1 <= heights[i] <= 100
```

Therefore, we can create a frequency array:

```java
int[] count = new int[101];
```

For every height, store how many times it occurs.

Then reconstruct the expected sorted order by finding the next height whose frequency is greater than zero.

At each position, compare:

```text
current height
```

with:

```text
expected height
```

If they are different, increase the mismatch count.

---

# Why Counting Sort?

A normal solution could be:

```java
int[] arr = heights.clone();
Arrays.sort(arr);
```

But this requires:

```text
O(n log n)
```

time because of sorting.

Since the height values are restricted to only `1` through `100`, we can use a frequency array.

Counting Sort takes:

```text
O(n + 100)
```

time.

Since `100` is a constant:

```text
O(n)
```

---

# Algorithm

1. Create a frequency array:
   ```text
   count[101]
   ```
2. Traverse `heights`.
3. For every height:
   ```text
   count[height]++
   ```
4. Initialize:
   ```text
   mismatch = 0
   val = 0
   ```
5. Traverse every position of `heights`.
6. Find the smallest height whose count is not zero.
7. This height is the expected value at the current position.
8. Compare:
   ```text
   heights[i]
   ```
   with:
   ```text
   val
   ```
9. If they are different:
   ```text
   mismatch++
   ```
10. Decrease:
    ```text
    count[val]--
    ```
11. Return `mismatch`.

---

# Dry Run

Input:

```text
heights = [1,1,4,2,1,3]
```

### Step 1: Frequency Array

The frequencies are:

```text
1 → 3
2 → 1
3 → 1
4 → 1
```

So conceptually:

```text
count = {
    1: 3,
    2: 1,
    3: 1,
    4: 1
}
```

---

### Step 2: Expected Sorted Values

The expected sorted order is:

```text
[1,1,1,2,3,4]
```

Now compare one position at a time.

| Index | Current | Expected | Mismatch |
|------:|--------:|---------:|:--------:|
| 0 | 1 | 1 | ❌ |
| 1 | 1 | 1 | ❌ |
| 2 | 4 | 1 | ✅ |
| 3 | 2 | 2 | ❌ |
| 4 | 1 | 3 | ✅ |
| 5 | 3 | 4 | ✅ |

Therefore:

```text
mismatch = 3
```

---

# Understanding the Code

## Create Frequency Array

```java
int[] count = new int[101];
```

Because heights range from `1` to `100`, an array of size `101` is enough.

---

## Count Each Height

```java
for(int h : heights){
    count[h]++;
}
```

For every height, increase its frequency.

For:

```text
[1,1,4,2,1,3]
```

we get:

```text
1 → 3
2 → 1
3 → 1
4 → 1
```

---

## Initialize Mismatch Counter

```java
int mismatch = 0;
```

This stores the number of positions where the current array differs from the expected sorted array.

---

## Track Expected Height

```java
int val = 0;
```

`val` represents the current expected height in sorted order.

---

## Find the Next Available Height

```java
while(count[val] == 0){
    val++;
}
```

If the current height has no remaining occurrences, move to the next height.

For example:

```text
count[1] = 0
```

then:

```text
val = 2
```

This lets us generate the sorted order without actually creating another array.

---

## Compare Current and Expected Height

```java
if(heights[i] != val){
    mismatch++;
}
```

If the current height is not the expected height, this position is counted as a mismatch.

---

## Consume the Expected Height

```java
count[val]--;
```

Once one occurrence of `val` has been used, decrease its frequency.

For example:

```text
count[1] = 3
```

after using one `1`:

```text
count[1] = 2
```

---

# Important Idea

We don't actually need to create the sorted array.

Instead of:

```text
Original Array
      ↓
Sort
      ↓
Expected Array
      ↓
Compare
```

we use:

```text
Original Array
      ↓
Frequency Array
      ↓
Generate Expected Value
      ↓
Compare Directly
```

This saves the extra array and uses the limited range of heights to achieve linear time.

---

# Counting Sort Pattern

This problem is a good example of the **Counting Sort** pattern.

The general idea is:

```text
Small value range
       ↓
Frequency Array
       ↓
Count occurrences
       ↓
Reconstruct sorted order
```

Here the value range is only:

```text
1 ... 100
```

so counting sort is very efficient.

---

# Complexity Analysis

Let `n` be the number of students.

### Time Complexity

Counting all heights:

```text
O(n)
```

Finding expected heights:

```text
O(n + 100)
```

Overall:

```text
O(n + 100)
```

Since `100` is constant:

```text
O(n)
```

---

### Space Complexity

The frequency array has fixed size:

```text
101
```

Therefore:

```text
O(101)
```

which is:

```text
O(1)
```

---

# Java Solution

```java
class Solution {

    public int heightChecker(int[] heights) {

        int[] count = new int[101];

        for(int h : heights){
            count[h]++;
        }

        int mismatch = 0;
        int val = 0;

        for(int i = 0; i < heights.length; i++){

            while(count[val] == 0){
                val++;
            }

            if(heights[i] != val){
                mismatch++;
            }

            count[val]--;
        }

        return mismatch;
    }
}
```

---

# Key Concepts

- Array
- Counting Sort
- Frequency Array
- Sorting
- Mismatch Counting
- Value Range Optimization

---

# Constraints

- `1 <= heights.length <= 100`
- `1 <= heights[i] <= 100`

---

# Learning Outcome

This problem demonstrates how knowing the **range of values** can help us avoid normal sorting.

Instead of using:

```java
Arrays.sort()
```

we use a frequency array:

```java
int[] count = new int[101];
```

The main idea is:

```text
Count frequencies
      ↓
Generate sorted values
      ↓
Compare with original array
      ↓
Count mismatches
```

The important part of the solution is:

```java
while(count[val] == 0){
    val++;
}

if(heights[i] != val){
    mismatch++;
}

count[val]--;
```

This allows us to compare the original array with its expected sorted order without explicitly creating the sorted array.

The solution achieves:

```text
Time  → O(n)
Space → O(1)
```