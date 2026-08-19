# 15. 3Sum

> **Difficulty:** Medium  
> **Topics:** Array, Two Pointers, Sorting

---

## Problem Statement

Given an integer array `nums`, find all unique triplets:

```text
[nums[i], nums[j], nums[k]]
```

such that:

```text
i != j
i != k
j != k
```

and:

```text
nums[i] + nums[j] + nums[k] == 0
```

The result must not contain duplicate triplets.

---

## Example

### Input

```text
nums = [-1,0,1,2,-1,-4]
```

### Output

```text
[[-1,-1,2],[-1,0,1]]
```

The valid triplets are:

```text
-1 + -1 + 2 = 0
-1 + 0 + 1 = 0
```

---

## Approach

The main idea is:

1. Sort the array.
2. Fix one element using index `i`.
3. Use two pointers:
   - `st = i + 1`
   - `end = n - 1`
4. Find two numbers whose sum is:

```text
target = -nums[i]
```

If:

```text
nums[st] + nums[end] < target
```

move `st` forward.

If:

```text
nums[st] + nums[end] > target
```

move `end` backward.

If they are equal, we found a valid triplet.

---

## Why Sorting Helps

After sorting:

```text
[-4,-1,-1,0,1,2]
```

the two-pointer technique becomes possible.

If the current sum is too small:

```text
sum < target
```

we increase `st` to get a larger value.

If the current sum is too large:

```text
sum > target
```

we decrease `end` to get a smaller value.

---

## Handling Duplicates

There are two places where duplicates must be handled.

### Duplicate First Element

```java
if(i > 0 && nums[i] == nums[i-1]){
    continue;
}
```

This prevents generating the same triplets for the same first number.

---

### Duplicate Left Values

After finding a valid triplet:

```java
while(st < end && nums[st] == nums[st-1]){
    st++;
}
```

This skips duplicate values on the left.

---

### Duplicate Right Values

Similarly:

```java
while(st < end && nums[end] == nums[end+1]){
    end--;
}
```

This skips duplicate values on the right.

---

## Java Solution

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> Mlist = new ArrayList<>();

        Arrays.sort(nums);

        int n = nums.length;

        for(int i = 0; i < n - 2; i++){

            // Skip duplicate first elements
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }

            int target = -nums[i];

            int st = i + 1;
            int end = n - 1;

            while(st < end){

                int sum = nums[st] + nums[end];

                if(sum < target){
                    st++;
                }
                else if(sum > target){
                    end--;
                }
                else{

                    Mlist.add(
                        Arrays.asList(
                            nums[i],
                            nums[st],
                            nums[end]
                        )
                    );

                    st++;
                    end--;

                    // Skip duplicate left values
                    while(st < end && nums[st] == nums[st - 1]){
                        st++;
                    }

                    // Skip duplicate right values
                    while(st < end && nums[end] == nums[end + 1]){
                        end--;
                    }
                }
            }
        }

        return Mlist;
    }
}
```

---

## Dry Run

### Input

```text
nums = [-1,0,1,2,-1,-4]
```

First sort:

```text
[-4,-1,-1,0,1,2]
```

---

### `i = 0`

```text
nums[i] = -4
target = 4
```

Pointers:

```text
st = 1 → -1
end = 5 → 2
```

Sum:

```text
-1 + 2 = 1
```

Since:

```text
1 < 4
```

move `st`.

Eventually no valid pair is found.

---

### `i = 1`

```text
nums[i] = -1
target = 1
```

Pointers:

```text
st = 2 → -1
end = 5 → 2
```

Sum:

```text
-1 + 2 = 1
```

We found:

```text
[-1,-1,2]
```

Add it to the result.

---

Continue searching:

```text
st = 3 → 0
end = 4 → 1
```

Sum:

```text
0 + 1 = 1
```

We found:

```text
[-1,0,1]
```

Add it.

---

### `i = 2`

```text
nums[2] = -1
```

But:

```text
nums[2] == nums[1]
```

Therefore:

```java
continue;
```

This prevents duplicate triplets.

---

### Final Result

```text
[[-1,-1,2],[-1,0,1]]
```

---

## Example: All Zeros

### Input

```text
nums = [0,0,0]
```

After sorting:

```text
[0,0,0]
```

For:

```text
i = 0
```

we have:

```text
target = 0
```

Two pointers:

```text
st = 1
end = 2
```

```text
0 + 0 = 0
```

So:

```text
[0,0,0]
```

is added.

Duplicate values are skipped, so the result contains only one triplet.

### Output

```text
[[0,0,0]]
```

---

## Why the Two-Pointer Approach Works

After sorting, for a fixed `nums[i]`:

- If the sum is too small, increasing `st` can increase the sum.
- If the sum is too large, decreasing `end` can decrease the sum.
- If the sum equals the target, we found a valid triplet.

Thus, we can search all pairs for a fixed `i` in:

```text
O(n)
```

instead of using two nested loops.

---

## Complexity Analysis

Sorting takes:

```text
O(n log n)
```

For every element, the two-pointer traversal takes:

```text
O(n)
```

for a total of:

```text
O(n²)
```

Therefore:

### Time Complexity

```text
O(n²)
```

### Space Complexity

```text
O(1)
```

excluding the output list.

---

## Key Concepts

- Sorting
- Two Pointers
- Duplicate Handling
- Array Traversal
- Target Sum

---

## Key Takeaway

The important pattern is:

```text
Sort
  ↓
Fix one element
  ↓
Use two pointers for the remaining two elements
  ↓
Skip duplicates
```

For each `i`:

```java
int target = -nums[i];
int st = i + 1;
int end = n - 1;
```

Then:

```java
if(sum < target)
    st++;
else if(sum > target)
    end--;
else
    // valid triplet
```

This reduces the brute-force `O(n³)` solution to:

```text
Time:  O(n²)
Space: O(1) excluding output
```