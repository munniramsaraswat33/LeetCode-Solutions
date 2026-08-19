# 78. Subsets

> **Difficulty:** Medium  
> **Topics:** Array, Backtracking

---

## Problem Statement

Given an integer array `nums` of **unique** elements, return all possible **subsets** of the array.

The solution set must not contain duplicate subsets.

The order of the returned subsets does not matter.

A subset can contain:

- No elements
- One element
- Multiple elements
- All elements

---

## Example 1

### Input

```text
nums = [1,2,3]
```

### Output

```text
[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```

### Explanation

All possible subsets of `[1,2,3]` are:

```text
[]
[1]
[2]
[3]
[1,2]
[1,3]
[2,3]
[1,2,3]
```

There are `2^3 = 8` possible subsets.

---

## Example 2

### Input

```text
nums = [0]
```

### Output

```text
[[],[0]]
```

---

# Approach

Use **Backtracking** to generate all possible subsets.

For every element, we have two choices:

```text
1. Include the element
2. Do not include the element
```

The `start` variable tells us where to start selecting the next element.

At every recursive call, the current `subset` is added to the answer because **every state of the backtracking tree represents a valid subset**.

After adding an element, we recursively explore further choices.

Then we remove that element using **backtracking** so that the next choice can be explored.

---

# Algorithm

1. Create an empty result list.
2. Call the `backtrack()` function with:
   - Empty subset
   - `start = 0`
3. Add a copy of the current subset to the result.
4. Run a loop from `start` to `nums.length - 1`.
5. Add `nums[i]` to the current subset.
6. Recursively call `backtrack()` with `i + 1`.
7. Remove the last element from the current subset.
8. Continue until all possibilities are explored.
9. Return the result.

---

# Dry Run

Input:

```text
nums = [1,2,3]
```

The backtracking process looks like:

```text
                    []
              /      |      \
            [1]     [2]     [3]
           /   \
       [1,2]  [1,3]
          |
       [1,2,3]
```

More precisely, the recursion explores:

```text
[]
├── [1]
│   ├── [1,2]
│   │   └── [1,2,3]
│   └── [1,3]
├── [2]
│   └── [2,3]
└── [3]
```

Therefore the answer contains:

```text
[]
[1]
[1,2]
[1,2,3]
[1,3]
[2]
[2,3]
[3]
```

The order can be different from the example because the problem allows any order.

---

# Understanding the Code

## Main Function

```java
public List<List<Integer>> subsets(int[] nums) {
```

This function returns all possible subsets.

---

### Create Result List

```java
List<List<Integer>> ans = new ArrayList<>();
```

This stores all generated subsets.

---

### Start Backtracking

```java
backtrack(ans, nums, new ArrayList<>(), 0);
```

Initially:

```text
subset = []
start = 0
```

So we start from the first element.

---

# Backtracking Function

```java
public void backtrack(
    List<List<Integer>> ans,
    int[] nums,
    List<Integer> subset,
    int start
)
```

The parameters are:

```text
ans    -> stores all subsets
nums   -> original array
subset -> current subset
start  -> index from where we can choose the next element
```

---

## Add Current Subset

```java
ans.add(new ArrayList<>(subset));
```

This is very important.

Every current `subset` is a valid answer.

For example:

```text
[]
[1]
[1,2]
[1,2,3]
```

All of them must be added.

We create a new `ArrayList` because `subset` is continuously modified during backtracking.

---

## Loop Through Choices

```java
for(int i=start; i<nums.length; i++){
```

This tries every possible next element.

For example, if:

```text
start = 1
```

then we can choose:

```text
nums[1], nums[2], ...
```

---

## Choose an Element

```java
subset.add(nums[i]);
```

This means:

```text
Choose nums[i]
```

For example:

```text
subset = []
nums[i] = 1
```

After adding:

```text
subset = [1]
```

---

## Recursive Call

```java
backtrack(ans, nums, subset, i+1);
```

Now we continue choosing elements **after** `i`.

For example:

```text
nums = [1,2,3]
```

After choosing `1`:

```text
i = 0
```

we call:

```text
backtrack(..., 1)
```

So the next elements can only be:

```text
2 or 3
```

This prevents duplicate subsets and maintains the original order.

---

## Backtracking

```java
subset.remove(subset.size()-1);
```

After exploring all subsets that start with the selected element, we remove it.

For example:

```text
subset = [1,2]
```

After recursion finishes:

```text
subset = [1]
```

Then we can try:

```text
[1,3]
```

This is the actual **backtracking step**.

---

# Why `i + 1`?

This is important:

```java
backtrack(ans, nums, subset, i+1);
```

We use `i + 1` because every element can be used at most once and we only want to consider elements to the right.

For:

```text
nums = [1,2,3]
```

after choosing `1`, we can choose:

```text
2
3
```

but we should not go back and choose `1` again.

Therefore:

```text
start = i + 1
```

---

# Why Backtracking Is Used?

This problem has a **choice at every element**.

For each element:

```text
Take it
   OR
Don't take it
```

This naturally forms a decision tree.

For example:

```text
        []
       /  \
     [1]   []
     / \   / \
 [1,2] [1] [2] []
```

Whenever a problem asks us to generate **all possible combinations, subsets, or arrangements**, backtracking is often a good approach.

---

# Complexity Analysis

### Time Complexity

There are:

```text
2^n
```

possible subsets.

For each subset, copying it can take up to `O(n)` time.

Therefore:

```text
O(n * 2^n)
```

---

### Space Complexity

The recursion depth can be at most:

```text
O(n)
```

The result itself contains:

```text
2^n
```

subsets.

Considering the output:

```text
O(n * 2^n)
```

space is required.

Auxiliary recursion space is:

```text
O(n)
```

---

# Java Solution

```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();

        backtrack(ans, nums, new ArrayList<>(), 0);

        return ans;
    }

    public void backtrack(
        List<List<Integer>> ans,
        int[] nums,
        List<Integer> subset,
        int start
    ) {

        ans.add(new ArrayList<>(subset));

        for(int i = start; i < nums.length; i++){

            subset.add(nums[i]);

            backtrack(ans, nums, subset, i + 1);

            subset.remove(subset.size() - 1);
        }
    }
}
```

---

# Key Concepts

- Array
- Backtracking
- Recursion
- Subsets
- Decision Tree
- `start` index
- Choose → Explore → Undo
- `2^n` possible subsets

---

# Constraints

- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`
- All numbers in `nums` are unique.

---

# Learning Outcome

This problem is one of the most important **Backtracking** problems.

The main pattern to remember is:

```text
Choose
   ↓
Recursive Call
   ↓
Undo
```

In code:

```java
subset.add(nums[i]);

backtrack(ans, nums, subset, i + 1);

subset.remove(subset.size() - 1);
```

The important idea is that **every state of the recursion represents a valid subset**, so we add the current subset before exploring further choices.

The total number of subsets is:

```text
2^n
```

which makes this a fundamental problem for understanding **Backtracking and Recursion**.