# 912. Sort an Array

> **Difficulty:** Medium  
> **Topics:** Array, Divide and Conquer, Sorting, Merge Sort

---

## Problem Statement

Given an integer array `nums`, sort the array in **ascending order** and return it.

You must solve the problem:

- Without using built-in sorting functions.
- In `O(n log n)` time complexity.
- Using the smallest possible extra space.

---

## Example 1

### Input

```text
nums = [5,2,3,1]
```

### Output

```text
[1,2,3,5]
```

---

## Example 2

### Input

```text
nums = [5,1,1,2,0,0]
```

### Output

```text
[0,0,1,1,2,5]
```

Duplicates are allowed in the array.

---

# Approach

We use **Merge Sort**.

Merge Sort follows the **Divide and Conquer** strategy.

The basic idea is:

```text
Divide
   ↓
Sort left half
   ↓
Sort right half
   ↓
Merge both sorted halves
```

For example:

```text
[5,2,3,1]
```

First divide:

```text
        [5,2,3,1]
        /       \
    [5,2]       [3,1]
    /  \        /  \
  [5]  [2]    [3]  [1]
```

Then merge:

```text
[5] + [2] → [2,5]

[3] + [1] → [1,3]
```

Finally:

```text
[2,5] + [1,3]
        ↓
   [1,2,3,5]
```

---

# Merge Sort Steps

## 1. Divide

Find the middle:

```java
int mid = si + (ei - si) / 2;
```

Then recursively divide the array:

```java
mergeSort(nums, si, mid);
mergeSort(nums, mid + 1, ei);
```

---

## 2. Base Case

When there is only one element:

```java
if(si >= ei){
    return;
}
```

A single element is already sorted.

---

## 3. Merge

After both halves are sorted, merge them.

For example:

```text
Left:  [1,5]
Right: [2,3]
```

Compare the elements:

```text
1 < 2 → take 1
5 > 2 → take 2
5 > 3 → take 3
take remaining 5
```

Result:

```text
[1,2,3,5]
```

---

# Dry Run

Consider:

```text
nums = [5,2,3,1]
```

### Step 1: Divide

```text
[5,2,3,1]
```

Split into:

```text
[5,2] [3,1]
```

Then:

```text
[5] [2] [3] [1]
```

---

### Step 2: Merge `[5]` and `[2]`

Compare:

```text
5 vs 2
```

Take `2`, then `5`.

Result:

```text
[2,5]
```

---

### Step 3: Merge `[3]` and `[1]`

Compare:

```text
3 vs 1
```

Take `1`, then `3`.

Result:

```text
[1,3]
```

---

### Step 4: Final Merge

Now:

```text
Left  = [2,5]
Right = [1,3]
```

Comparison:

```text
2 vs 1 → take 1
2 vs 3 → take 2
5 vs 3 → take 3
take remaining 5
```

Final:

```text
[1,2,3,5]
```

---

# Java Solution

```java
class Solution {

    public int[] sortArray(int[] nums) {

        mergeSort(nums, 0, nums.length - 1);

        return nums;
    }

    public static void mergeSort(int[] nums, int si, int ei) {

        if (si >= ei) {
            return;
        }

        int mid = si + (ei - si) / 2;

        mergeSort(nums, si, mid);
        mergeSort(nums, mid + 1, ei);

        merge(nums, si, mid, ei);
    }

    public static void merge(int[] nums, int si, int mid, int ei) {

        int[] temp = new int[ei - si + 1];

        int i = si;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= ei) {

            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        while (j <= ei) {
            temp[k++] = nums[j++];
        }

        for (i = si, k = 0; k < temp.length; k++, i++) {
            nums[i] = temp[k];
        }
    }
}
```

---

# How the `merge()` Function Works

The two halves are already sorted:

```text
Left Half  → nums[si ... mid]
Right Half → nums[mid+1 ... ei]
```

We use two pointers:

```java
int i = si;
int j = mid + 1;
```

`i` points to the left half.

`j` points to the right half.

Then compare:

```java
if(nums[i] < nums[j])
```

Take the smaller element and put it into `temp`.

---

# Why Do We Need `temp`?

Suppose:

```text
Left  = [2,5]
Right = [1,3]
```

We cannot directly overwrite the original array while still comparing elements that we haven't processed.

So we temporarily store the merged result:

```text
temp = [1,2,3,5]
```

Then copy it back:

```java
nums[i] = temp[k];
```

---

# Handling Remaining Elements

After the main comparison loop, one half may still contain elements.

For the left half:

```java
while(i <= mid){
    temp[k++] = nums[i++];
}
```

For the right half:

```java
while(j <= ei){
    temp[k++] = nums[j++];
}
```

Because both halves are already sorted, the remaining elements can simply be copied.

---

# Important Observation

Merge Sort repeatedly divides the array into two halves.

For `n` elements:

```text
Level 1 → n
Level 2 → n/2 + n/2
Level 3 → n/4 + n/4 + ...
```

The number of levels is:

```text
log₂(n)
```

At every level, merging takes:

```text
O(n)
```

Therefore:

```text
O(n) × O(log n)
```

=

```text
O(n log n)
```

---

# Complexity Analysis

### Time Complexity

```text
O(n log n)
```

Merge Sort guarantees `O(n log n)` time for:

- Best case
- Average case
- Worst case

### Space Complexity

The temporary array requires:

```text
O(n)
```

Additionally, recursion uses:

```text
O(log n)
```

stack space.

Overall auxiliary space:

```text
O(n)
```

---

# Why Merge Sort?

The problem requires:

```text
O(n log n)
```

time complexity.

Merge Sort satisfies this requirement without using Java's built-in sorting functions.

Comparison:

| Sorting Algorithm | Average | Worst | Extra Space |
|---|---:|---:|---:|
| Bubble Sort | O(n²) | O(n²) | O(1) |
| Selection Sort | O(n²) | O(n²) | O(1) |
| Insertion Sort | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n²) | O(log n) average |

---

# Key Concepts

- Merge Sort
- Divide and Conquer
- Recursion
- Two Pointer Technique
- Array Merging
- Sorting

---

# Important Code Pattern

### Divide

```java
int mid = si + (ei - si) / 2;

mergeSort(nums, si, mid);
mergeSort(nums, mid + 1, ei);
```

### Merge

```java
merge(nums, si, mid, ei);
```

### Base Case

```java
if(si >= ei){
    return;
}
```

---

# Learning Outcome

The main pattern to remember is:

```text
             Array
               |
            Divide
           /      \
      Left Half   Right Half
         |            |
       Sort          Sort
         \            /
          \          /
            Merge
              |
        Sorted Array
```

The key idea is:

> **Divide the array until each part contains one element, then repeatedly merge the sorted parts to obtain the final sorted array.**

### Complexity

```text
Time:  O(n log n)
Space: O(n)
```