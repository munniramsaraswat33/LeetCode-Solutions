# 3718. Smallest Missing Multiple of K

> **Difficulty:** Easy  
> **Topics:** Array, Hash Table, HashSet

---

## Problem Statement

Given an integer array `nums` and an integer `k`, find the **smallest positive multiple of `k`** that is not present in `nums`.

A multiple of `k` has the form:

```text
k, 2k, 3k, 4k, ...
```

Return the smallest multiple of `k` that does not appear in the array.

---

## Example 1

### Input

```text
nums = [8,2,3,4,6]
k = 2
```

### Output

```text
10
```

### Explanation

The positive multiples of `2` are:

```text
2, 4, 6, 8, 10, ...
```

The values:

```text
2, 4, 6, 8
```

are present in `nums`.

The first missing multiple is:

```text
10
```

Therefore:

```text
10
```

---

## Example 2

### Input

```text
nums = [1,4,7,10]
k = 3
```

### Output

```text
3
```

### Explanation

The multiples of `3` start with:

```text
3,6,9,12,...
```

Since `3` is not present in `nums`, it is the smallest missing multiple.

Therefore:

```text
3
```

---

# Approach

Use a **HashSet** to store all elements of `nums`.

A `HashSet` provides fast membership checking.

We start with:

```text
ans = k
```

because `k` is the smallest positive multiple of `k`.

Then continuously check whether `ans` exists in the set.

If it exists:

```text
ans = ans + k
```

This moves to the next multiple.

We stop as soon as we find a multiple that is not present.

---

# Algorithm

1. Create a `HashSet`.
2. Insert every element of `nums` into the set.
3. Initialize:
   ```text
   ans = k
   ```
4. While `ans` exists in the set:
   ```text
   ans += k
   ```
5. Return `ans`.

---

# Dry Run

Input:

```text
nums = [2,4,6,8]
k = 2
```

### Step 1: Create HashSet

```text
set = {2,4,6,8}
```

---

### Step 2: Start with First Multiple

```text
ans = 2
```

Check:

```text
set.contains(2) → true
```

So:

```text
ans = 2 + 2
    = 4
```

---

### Step 3

Check:

```text
set.contains(4) → true
```

So:

```text
ans = 6
```

---

### Step 4

Check:

```text
set.contains(6) → true
```

So:

```text
ans = 8
```

---

### Step 5

Check:

```text
set.contains(8) → true
```

So:

```text
ans = 10
```

---

### Step 6

Check:

```text
set.contains(10) → false
```

Stop.

Final answer:

```text
10
```

---

# Understanding the Code

## Create HashSet

```java
Set<Integer> set = new HashSet<>();
```

The HashSet stores all numbers from the array.

---

## Insert Array Elements

```java
for(int num : nums){
    set.add(num);
}
```

Every element is inserted into the set.

Duplicates, if any, are automatically ignored.

---

## Start with `k`

```java
int ans = k;
```

`k` is always the smallest positive multiple of `k`.

For example, if:

```text
k = 5
```

the multiples are:

```text
5,10,15,20,...
```

So we start with:

```text
ans = 5
```

---

## Find Missing Multiple

```java
while(set.contains(ans)){
    ans += k;
}
```

If the current multiple exists:

```text
ans
```

move to the next multiple by adding `k`.

For example:

```text
ans = 5
```

then:

```text
ans = 10
```

then:

```text
ans = 15
```

and so on.

The loop stops when:

```java
set.contains(ans) == false
```

---

## Return Answer

```java
return ans;
```

At this point, `ans` is the smallest positive multiple of `k` that is missing from the array.

---

# Why Use HashSet?

We need to repeatedly check:

```java
set.contains(ans)
```

A `HashSet` provides approximately:

```text
O(1)
```

average-time lookup.

Without a HashSet, we would have to search the entire array for every multiple.

Using a HashSet makes the membership checking efficient.

---

# Important Pattern

This problem follows the simple pattern:

```text
Array
  ↓
HashSet
  ↓
Start from k
  ↓
Check k
  ↓
Check 2k
  ↓
Check 3k
  ↓
...
  ↓
First missing multiple
```

The key code is:

```java
int ans = k;

while(set.contains(ans)){
    ans += k;
}
```

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

Creating the HashSet:

```text
O(n)
```

Checking the multiples takes:

```text
O(m)
```

where `m` is the number of consecutive multiples of `k` present in the array.

Therefore:

```text
O(n + m)
```

---

### Space Complexity

The HashSet stores the elements of the array.

Therefore:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int missingMultiple(int[] nums, int k) {

        Set<Integer> set = new HashSet<>();

        for(int num : nums){
            set.add(num);
        }

        int ans = k;

        while(set.contains(ans)){
            ans += k;
        }

        return ans;
    }
}
```

---

# Key Concepts

- Array
- Hash Table
- HashSet
- Membership Checking
- Multiples
- Incremental Search

---

# Constraints

- `1 <= nums.length <= 100`
- `1 <= nums[i], k <= 100`

---

# Learning Outcome

This problem demonstrates how a **HashSet** can be used for fast membership checking.

The main idea is:

```text
Store all numbers in HashSet
        ↓
Start from k
        ↓
Check if current multiple exists
        ↓
If yes → add k
        ↓
If no → return it
```

The most important code is:

```java
while(set.contains(ans)){
    ans += k;
}
```

This allows us to find the smallest missing multiple of `k` efficiently.

The solution achieves:

```text
Time  → O(n + m)
Space → O(n)
```