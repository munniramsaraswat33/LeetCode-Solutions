# 18. 4Sum

> **Difficulty:** Medium  
> **Topics:** Array, Sorting, Two Pointers

---

## Problem Statement

Given an integer array `nums` and an integer `target`, find all **unique quadruplets**:

```text
[nums[a], nums[b], nums[c], nums[d]]
```

such that:

```text
nums[a] + nums[b] + nums[c] + nums[d] == target
```

All four indices must be distinct.

The answer must not contain duplicate quadruplets.

---

## Example 1

### Input

```text
nums = [1,0,-1,0,-2,2]
target = 0
```

### Output

```text
[[-2,-1,1,2],
 [-2,0,0,2],
 [-1,0,0,1]]
```

---

## Example 2

### Input

```text
nums = [2,2,2,2,2]
target = 8
```

### Output

```text
[[2,2,2,2]]
```

---

## Approach

A brute-force solution would use four nested loops:

```text
O(n⁴)
```

which is too slow.

We can optimize it using:

1. Sort the array.
2. Fix the first element using `left`.
3. Fix the second element using `right`.
4. Use two pointers for the remaining two elements.
5. Skip duplicates at every level.

The structure becomes:

```text
Sort
  ↓
Fix first element
  ↓
Fix second element
  ↓
Two pointers for remaining two elements
```

---

## Why Sorting Helps

After sorting, we can use the two-pointer technique.

For example:

```text
nums = [1,0,-1,0,-2,2]
```

After sorting:

```text
[-2,-1,0,0,1,2]
```

For two fixed elements, we need:

```text
nums[i] + nums[j] = remaining
```

We use:

```text
i = right + 1
j = n - 1
```

If:

```text
nums[i] + nums[j] < remaining
```

we need a larger sum, so:

```text
i++
```

If:

```text
nums[i] + nums[j] > remaining
```

we need a smaller sum, so:

```text
j--
```

If equal, we found a valid quadruplet.

---

## Important: Use `long`

The values can be as large as:

```text
10⁹
```

Adding four values can exceed the `int` range.

Therefore, this is important:

```java
long remaining = (long) target - nums[left] - nums[right];
```

It prevents integer overflow.

---

## Handling Duplicates

Duplicate quadruplets are not allowed.

### Duplicate First Element

```java
if(left > 0 && nums[left] == nums[left - 1]){
    continue;
}
```

Example:

```text
[-2,-2,-1,0,1,2]
```

We don't need to process `-2` twice as the first element.

---

### Duplicate Second Element

```java
if(right > left + 1 && nums[right] == nums[right - 1]){
    continue;
}
```

This prevents duplicate combinations for the second element.

---

### Duplicate Third Element

After finding a valid quadruplet:

```java
while(i < j && nums[i] == nums[i - 1]){
    i++;
}
```

---

### Duplicate Fourth Element

Similarly:

```java
while(i < j && nums[j] == nums[j + 1]){
    j--;
}
```

---

## Java Solution

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {

        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        int n = nums.length;

        for(int left = 0; left < n - 3; left++){

            // Skip duplicate first elements
            if(left > 0 && nums[left] == nums[left - 1]){
                continue;
            }

            for(int right = left + 1; right < n - 2; right++){

                // Skip duplicate second elements
                if(right > left + 1 && nums[right] == nums[right - 1]){
                    continue;
                }

                long remaining =
                    (long) target - nums[left] - nums[right];

                int i = right + 1;
                int j = n - 1;

                while(i < j){

                    long sum = (long) nums[i] + nums[j];

                    if(sum < remaining){
                        i++;
                    }
                    else if(sum > remaining){
                        j--;
                    }
                    else{

                        result.add(
                            Arrays.asList(
                                nums[left],
                                nums[right],
                                nums[i],
                                nums[j]
                            )
                        );

                        i++;
                        j--;

                        // Skip duplicate third elements
                        while(i < j && nums[i] == nums[i - 1]){
                            i++;
                        }

                        // Skip duplicate fourth elements
                        while(i < j && nums[j] == nums[j + 1]){
                            j--;
                        }
                    }
                }
            }
        }

        return result;
    }
}
```

---

## Dry Run

### Input

```text
nums = [1,0,-1,0,-2,2]
target = 0
```

After sorting:

```text
[-2,-1,0,0,1,2]
```

### First element

```text
left = 0
nums[left] = -2
```

### Second element

```text
right = 1
nums[right] = -1
```

Required sum:

```text
remaining = 0 - (-2) - (-1)
          = 3
```

Pointers:

```text
i = 2 → 0
j = 5 → 2
```

Current sum:

```text
0 + 2 = 2
```

Since:

```text
2 < 3
```

move `i`.

Now:

```text
i = 4 → 1
j = 5 → 2
```

```text
1 + 2 = 3
```

Found:

```text
[-2,-1,1,2]
```

---

### Next second element

```text
right = 2
nums[right] = 0
```

Required:

```text
remaining = 0 - (-2) - 0
          = 2
```

Two pointers find:

```text
0 + 2 = 2
```

So:

```text
[-2,0,0,2]
```

is added.

---

### Next first element

```text
left = 1
nums[left] = -1
```

The algorithm eventually finds:

```text
[-1,0,0,1]
```

---

## Final Result

```text
[[-2,-1,1,2],
 [-2,0,0,2],
 [-1,0,0,1]]
```

---

## Why Two Pointers Work

For fixed `left` and `right`, the remaining problem is a **2Sum** problem.

We need:

```text
nums[i] + nums[j] = remaining
```

Because the array is sorted:

### Sum too small

```text
nums[i] + nums[j] < remaining
```

Move `i` right:

```text
i++
```

This increases the sum.

### Sum too large

```text
nums[i] + nums[j] > remaining
```

Move `j` left:

```text
j--
```

This decreases the sum.

Therefore, the remaining pair can be found in:

```text
O(n)
```

instead of `O(n²)`.

---

## Complexity Analysis

Sorting:

```text
O(n log n)
```

First loop:

```text
O(n)
```

Second loop:

```text
O(n)
```

Two-pointer traversal:

```text
O(n)
```

Overall:

### Time Complexity

```text
O(n³)
```

### Space Complexity

```text
O(1)
```

excluding the output list and sorting implementation space.

---

## Key Concepts

- Sorting
- Two Pointers
- Nested Loops
- Duplicate Handling
- Overflow Prevention
- `long` for large sums

---

## Pattern to Remember

4Sum follows the same pattern as 3Sum.

### 3Sum

```text
Fix 1 element
+
Two pointers
```

### 4Sum

```text
Fix 2 elements
+
Two pointers
```

So:

```text
4Sum
 ↓
Sort
 ↓
First loop
 ↓
Second loop
 ↓
Two pointers
 ↓
Skip duplicates
```

### Complexity

```text
3Sum → O(n²)
4Sum → O(n³)
```