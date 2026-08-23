# 2215. Find the Difference of Two Arrays

> **Difficulty:** Easy  
> **Topics:** Array, Hash Table, HashSet

---

## Problem Statement

Given two integer arrays `nums1` and `nums2`, return a list containing two lists:

- The first list contains all **distinct integers** that are present in `nums1` but not in `nums2`.
- The second list contains all **distinct integers** that are present in `nums2` but not in `nums1`.

The order of the elements does not matter.

---

## Example 1

### Input

```text
nums1 = [1,2,3]
nums2 = [2,4,6]
```

### Output

```text
[[1,3],[4,6]]
```

### Explanation

Elements present in `nums1` but not `nums2`:

```text
[1,3]
```

Elements present in `nums2` but not `nums1`:

```text
[4,6]
```

Therefore:

```text
[[1,3],[4,6]]
```

---

## Example 2

### Input

```text
nums1 = [1,2,3,3]
nums2 = [1,1,2,2]
```

### Output

```text
[[3],[]]
```

### Explanation

The distinct elements of `nums1` are:

```text
[1,2,3]
```

The distinct elements of `nums2` are:

```text
[1,2]
```

Only `3` exists in `nums1` but not in `nums2`.

There is no element in `nums2` that does not exist in `nums1`.

Therefore:

```text
[[3],[]]
```

---

# Approach

Use **HashSet**.

A `HashSet` stores only unique elements and provides efficient lookup.

We create two sets:

```text
set1 → unique elements of nums1
set2 → unique elements of nums2
```

Then:

- Traverse `set1` and add elements not present in `set2`.
- Traverse `set2` and add elements not present in `set1`.

---

# Algorithm

1. Create `set1` and `set2`.
2. Insert all elements of `nums1` into `set1`.
3. Insert all elements of `nums2` into `set2`.
4. Create the answer list.
5. Traverse `set1`:
   - If the element is not present in `set2`, add it to `list1`.
6. Add `list1` to the answer.
7. Traverse `set2`:
   - If the element is not present in `set1`, add it to `list2`.
8. Add `list2` to the answer.
9. Return the answer.

---

# Dry Run

Input:

```text
nums1 = [1,2,3]
nums2 = [2,4,6]
```

### Step 1: Create Sets

```text
set1 = {1,2,3}
set2 = {2,4,6}
```

---

### Step 2: Find Elements Only in `nums1`

Check every element of `set1`:

| Element | Present in `set2`? | Result |
|--------:|:------------------:|--------|
| 1 | ❌ | Add `1` |
| 2 | ✅ | Ignore |
| 3 | ❌ | Add `3` |

Therefore:

```text
list1 = [1,3]
```

---

### Step 3: Find Elements Only in `nums2`

Check every element of `set2`:

| Element | Present in `set1`? | Result |
|--------:|:------------------:|--------|
| 2 | ✅ | Ignore |
| 4 | ❌ | Add `4` |
| 6 | ❌ | Add `6` |

Therefore:

```text
list2 = [4,6]
```

---

### Final Answer

```text
[[1,3],[4,6]]
```

---

# Understanding the Code

## Create HashSets

```java
Set<Integer> set1 = new HashSet<>();
Set<Integer> set2 = new HashSet<>();
```

These sets store unique values from the two arrays.

---

## Store Elements of `nums1`

```java
for(int num : nums1){
    set1.add(num);
}
```

If duplicates exist, `HashSet` automatically removes them.

For example:

```text
[1,2,2,3,3]
```

becomes:

```text
{1,2,3}
```

---

## Store Elements of `nums2`

```java
for(int num : nums2){
    set2.add(num);
}
```

Now both arrays have been converted into sets.

---

## Find Difference of `nums1`

```java
for(int num : set1){
    if(!set2.contains(num)){
        list1.add(num);
    }
}
```

If an element exists in `set1` but not in `set2`, it belongs to the first answer list.

---

## Find Difference of `nums2`

```java
for(int num : set2){
    if(!set1.contains(num)){
        list2.add(num);
    }
}
```

If an element exists in `set2` but not in `set1`, it belongs to the second answer list.

---

# Why Use HashSet?

Without a HashSet, we could search for every element in the other array using nested loops.

That would take:

```text
O(n × m)
```

Using a `HashSet`, membership checking is approximately:

```text
O(1)
```

So the solution becomes much more efficient.

Another advantage is that `HashSet` automatically handles duplicates.

---

# HashSet Operations Used

### `add()`

```java
set1.add(num);
```

Adds an element to the set.

---

### `contains()`

```java
set2.contains(num);
```

Checks whether an element exists in the set.

---

### Why `!contains()`?

```java
if(!set2.contains(num))
```

means:

```text
if num does NOT exist in set2
```

Therefore, `num` belongs only to `nums1`.

---

# Complexity Analysis

Let:

```text
n = nums1.length
m = nums2.length
```

### Time Complexity

Creating both sets:

```text
O(n + m)
```

Finding the differences:

```text
O(n + m)
```

Overall:

```text
O(n + m)
```

on average.

---

### Space Complexity

The two HashSets store the unique elements.

Therefore:

```text
O(n + m)
```

including the output lists.

---

# Java Solution

```java
class Solution {

    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        for(int num : nums1){
            set1.add(num);
        }

        for(int num : nums2){
            set2.add(num);
        }

        List<List<Integer>> ans = new ArrayList<>();

        List<Integer> list1 = new ArrayList<>();

        for(int num : set1){
            if(!set2.contains(num)){
                list1.add(num);
            }
        }

        ans.add(list1);

        List<Integer> list2 = new ArrayList<>();

        for(int num : set2){
            if(!set1.contains(num)){
                list2.add(num);
            }
        }

        ans.add(list2);

        return ans;
    }
}
```

---

# Key Concepts

- Array
- Hash Table
- HashSet
- Set Difference
- Duplicate Removal
- Membership Checking

---

# Constraints

- `1 <= nums1.length, nums2.length <= 1000`
- `-1000 <= nums1[i], nums2[i] <= 1000`
- Duplicate elements may be present.

---

# Learning Outcome

This problem demonstrates how **HashSet** can be used to find the difference between two arrays efficiently.

The main idea is:

```text
Array
  ↓
HashSet
  ↓
Remove duplicates
  ↓
Check membership
  ↓
Find set difference
```

The important pattern is:

```java
if(!set2.contains(num)){
    list1.add(num);
}
```

and:

```java
if(!set1.contains(num)){
    list2.add(num);
}
```

The solution achieves:

```text
Time  → O(n + m)
Space → O(n + m)
```

This is a basic and important **Hash Table / HashSet** pattern for finding unique elements and set differences.