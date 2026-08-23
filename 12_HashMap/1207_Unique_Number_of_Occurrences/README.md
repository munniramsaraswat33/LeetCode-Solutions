# 1207. Unique Number of Occurrences

> **Difficulty:** Easy  
> **Topics:** Array, Hash Table, HashSet, Counting

---

## Problem Statement

Given an integer array `arr`, return `true` if the number of occurrences of each value in the array is **unique**.

Otherwise, return `false`.

In other words, no two different values should appear the same number of times.

---

## Example 1

### Input

```text
arr = [1,2,2,1,1,3]
```

### Output

```text
true
```

### Explanation

The frequencies are:

```text
1 → 3 times
2 → 2 times
3 → 1 time
```

All frequencies are unique:

```text
[3,2,1]
```

Therefore:

```text
true
```

---

## Example 2

### Input

```text
arr = [1,2]
```

### Output

```text
false
```

### Explanation

The frequencies are:

```text
1 → 1 time
2 → 1 time
```

Both numbers have the same frequency.

Therefore, the occurrences are not unique.

---

## Example 3

### Input

```text
arr = [-3,0,1,-3,1,1,1,-3,10,0]
```

### Output

```text
true
```

### Explanation

The frequencies are:

```text
-3 → 3
0  → 2
1  → 4
10 → 1
```

All frequencies are different.

Therefore:

```text
true
```

---

# Approach

Use a **HashMap** and a **HashSet**.

### HashMap

First, use a `HashMap` to count how many times each number occurs.

For example:

```text
arr = [1,2,2,1,1,3]
```

The map becomes:

```text
1 → 3
2 → 2
3 → 1
```

### HashSet

Then store all frequency values in a `HashSet`.

```text
[3,2,1]
```

becomes:

```text
{3,2,1}
```

If all frequencies are unique:

```text
map.size() == set.size()
```

Otherwise, the set removes duplicate frequencies and its size becomes smaller.

---

# Algorithm

1. Create a `HashMap<Integer, Integer>`.
2. Traverse the array.
3. Store the frequency of every number in the map.
4. Create a `HashSet` using the values of the map.
5. Compare:
   ```text
   map.size()
   ```
   and
   ```text
   set.size()
   ```
6. If both sizes are equal, all frequencies are unique.
7. Otherwise, return `false`.

---

# Dry Run

Input:

```text
arr = [1,2,2,1,1,3]
```

### Step 1: Count Frequencies

Initially:

```text
map = {}
```

After processing the array:

```text
map = {
    1 → 3,
    2 → 2,
    3 → 1
}
```

So:

```text
map.values() = [3,2,1]
```

---

### Step 2: Create HashSet

```java
Set<Integer> set = new HashSet<>(map.values());
```

The set becomes:

```text
{3,2,1}
```

No duplicate frequency exists.

Therefore:

```text
map.size() = 3
set.size() = 3
```

Since:

```text
3 == 3
```

the answer is:

```text
true
```

---

# Dry Run with Duplicate Frequency

Input:

```text
arr = [1,2]
```

Frequency map:

```text
1 → 1
2 → 1
```

Therefore:

```text
map.values() = [1,1]
```

HashSet removes the duplicate:

```text
set = {1}
```

Now:

```text
map.size() = 2
set.size() = 1
```

Since:

```text
2 != 1
```

the answer is:

```text
false
```

---

# Understanding the Code

## Create HashMap

```java
Map<Integer, Integer> map = new HashMap<>();
```

The map stores:

```text
number → frequency
```

---

## Count Frequencies

```java
for(int num : arr){
    if(map.containsKey(num)){
        map.put(num, map.get(num) + 1);
    }
    else{
        map.put(num, 1);
    }
}
```

If the number already exists:

```java
map.get(num) + 1
```

increases its frequency.

Otherwise:

```java
map.put(num, 1);
```

starts its frequency at `1`.

---

## Create HashSet from Frequencies

```java
Set<Integer> set = new HashSet<>(map.values());
```

`map.values()` contains all frequencies.

For example:

```text
[3,2,1]
```

The `HashSet` keeps only unique frequencies.

If we have:

```text
[2,2,3]
```

the set becomes:

```text
{2,3}
```

---

## Compare Sizes

```java
if(map.size() == set.size()){
    return true;
}
```

`map.size()` represents the number of different numbers.

`set.size()` represents the number of different frequencies.

If both are equal, every number has a different frequency.

Otherwise:

```java
return false;
```

---

# Why Does Comparing Sizes Work?

Suppose:

```text
map = {
    1 → 3,
    2 → 2,
    3 → 1
}
```

There are:

```text
3 different numbers
```

and:

```text
3 different frequencies
```

So:

```text
map.size() = set.size()
```

But suppose:

```text
map = {
    1 → 2,
    2 → 2,
    3 → 1
}
```

There are:

```text
3 different numbers
```

but frequencies are:

```text
[2,2,1]
```

The HashSet contains:

```text
{2,1}
```

Therefore:

```text
map.size() = 3
set.size() = 2
```

This tells us that at least two numbers have the same frequency.

---

# HashMap + HashSet Pattern

This problem uses two important Hash Table concepts:

### HashMap

Used for:

```text
Counting frequency
```

Pattern:

```java
map.put(num, map.getOrDefault(num, 0) + 1);
```

### HashSet

Used for:

```text
Checking uniqueness
```

Pattern:

```java
Set<Integer> set = new HashSet<>(map.values());
```

This combination is useful in many frequency-based problems.

---

# Complexity Analysis

### Time Complexity

Counting frequencies:

```text
O(n)
```

Creating the HashSet:

```text
O(n)
```

Overall:

```text
O(n)
```

---

### Space Complexity

The HashMap stores the frequency of each distinct number.

The HashSet stores the distinct frequencies.

Therefore:

```text
O(n)
```

in the worst case.

---

# Java Solution

```java
class Solution {

    public boolean uniqueOccurrences(int[] arr) {

        Map<Integer, Integer> map = new HashMap<>();

        for(int num : arr){

            if(map.containsKey(num)){
                map.put(num, map.get(num) + 1);
            }
            else{
                map.put(num, 1);
            }
        }

        Set<Integer> set = new HashSet<>(map.values());

        if(map.size() == set.size()){
            return true;
        }

        return false;
    }
}
```

---

# Key Concepts

- Array
- HashMap
- HashSet
- Frequency Counting
- Duplicate Detection
- Uniqueness Checking

---

# Constraints

- `1 <= arr.length <= 1000`
- `-1000 <= arr[i] <= 1000`

---

# Learning Outcome

This problem demonstrates how **HashMap + HashSet** can be used together to solve frequency and uniqueness problems.

The main pattern is:

```text
Array
  ↓
HashMap
  ↓
Count frequency of each number
  ↓
HashSet
  ↓
Store unique frequencies
  ↓
Compare sizes
```

The important condition is:

```java
map.size() == set.size()
```

If the sizes are equal, every number has a unique frequency.

The solution achieves:

```text
Time  → O(n)
Space → O(n)
```