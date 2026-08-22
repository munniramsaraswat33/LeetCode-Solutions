# 1431. Kids With the Greatest Number of Candies

> **Difficulty:** Easy  
> **Topics:** Array

---

## Problem Statement

You are given an integer array `candies`, where:

```text
candies[i]
```

represents the number of candies the `ith` kid has.

You are also given an integer `extraCandies`, representing the number of extra candies available.

For each kid, give **all** the `extraCandies` to that kid and check whether they will have the **greatest number of candies** among all kids.

Return a boolean list `result` where:

```text
result[i] = true
```

if the `ith` kid can have the greatest number of candies after receiving all the extra candies.

Multiple kids can have the greatest number of candies.

---

## Example 1

### Input

```text
candies = [2,3,5,1,3]
extraCandies = 3
```

### Output

```text
[true,true,true,false,true]
```

### Explanation

The maximum number of candies initially is:

```text
5
```

Now give all `3` extra candies to each kid one by one:

```text
Kid 1: 2 + 3 = 5  → true
Kid 2: 3 + 3 = 6  → true
Kid 3: 5 + 3 = 8  → true
Kid 4: 1 + 3 = 4  → false
Kid 5: 3 + 3 = 6  → true
```

Therefore:

```text
[true,true,true,false,true]
```

---

## Example 2

### Input

```text
candies = [4,2,1,1,2]
extraCandies = 1
```

### Output

```text
[true,false,false,false,false]
```

### Explanation

The current maximum is:

```text
4
```

For the first kid:

```text
4 + 1 = 5
```

which is greater than or equal to the maximum.

For the other kids, even after receiving the extra candy, they cannot reach `4`.

Therefore:

```text
[true,false,false,false,false]
```

---

## Example 3

### Input

```text
candies = [12,1,12]
extraCandies = 10
```

### Output

```text
[true,false,true]
```

The first and third kids already have the maximum number of candies.

---

# Approach

Use a simple **Array Traversal** approach.

First, find the maximum number of candies currently held by any kid.

Then traverse the array again.

For every kid, calculate:

```text
candies[i] + extraCandies
```

If this value is greater than or equal to the maximum:

```text
candies[i] + extraCandies >= max
```

then the answer for that kid is `true`.

Otherwise, it is `false`.

---

# Algorithm

1. Create an empty `List<Boolean>`.
2. Find the maximum value in `candies`.
3. Traverse the `candies` array again.
4. For every kid:
   - Add `extraCandies` to their candies.
   - Compare the result with `max`.
5. If:
   ```text
   candies[i] + extraCandies >= max
   ```
   add `true`.
6. Otherwise, add `false`.
7. Return the result list.

---

# Dry Run

Input:

```text
candies = [2,3,5,1,3]
extraCandies = 3
```

### Step 1: Find Maximum

Traverse the array:

```text
2 → max = 2
3 → max = 3
5 → max = 5
1 → max = 5
3 → max = 5
```

So:

```text
max = 5
```

---

### Step 2: Check Every Kid

| Kid | Candies | Extra | Total | `>= 5` | Result |
|---|---:|---:|---:|:---:|:---:|
| 1 | 2 | 3 | 5 | Yes | `true` |
| 2 | 3 | 3 | 6 | Yes | `true` |
| 3 | 5 | 3 | 8 | Yes | `true` |
| 4 | 1 | 3 | 4 | No | `false` |
| 5 | 3 | 3 | 6 | Yes | `true` |

Final answer:

```text
[true,true,true,false,true]
```

---

# Understanding the Code

## Create Result List

```java
List<Boolean> list = new ArrayList<>();
```

This stores the result for every kid.

---

## Find Maximum

```java
int max = candies[0];

for(int num : candies){
    max = Math.max(max, num);
}
```

We traverse the array and find the kid who currently has the maximum number of candies.

---

## Check Every Kid

```java
for(int num : candies){
```

Now we check each kid independently.

---

## Compare With Maximum

```java
if(num + extraCandies >= max){
    list.add(true);
}
else{
    list.add(false);
}
```

If the kid receives all the extra candies and reaches or exceeds the current maximum, they can have the greatest number of candies.

Therefore, we add:

```text
true
```

Otherwise:

```text
false
```

---

# Why `>=` Instead of `>`?

The problem says multiple kids can have the **greatest** number of candies.

For example:

```text
candies = [5,3]
extraCandies = 2
```

For the second kid:

```text
3 + 2 = 5
```

The maximum is also `5`.

So the second kid should be:

```text
true
```

Therefore we use:

```java
num + extraCandies >= max
```

instead of:

```java
num + extraCandies > max
```

---

# Complexity Analysis

### Time Complexity

We traverse the array twice:

```text
O(n) + O(n)
```

Therefore:

```text
O(n)
```

---

### Space Complexity

The output list contains `n` boolean values:

```text
O(n)
```

Apart from the output, the algorithm uses:

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {

        List<Boolean> list = new ArrayList<>();

        int max = candies[0];

        for(int num : candies){
            max = Math.max(max, num);
        }

        for(int num : candies){

            if(num + extraCandies >= max){
                list.add(true);
            }
            else{
                list.add(false);
            }
        }

        return list;
    }
}
```

---

# Key Concepts

- Array
- Array Traversal
- Finding Maximum
- Comparison
- `ArrayList`
- Greedy-style Checking

---

# Constraints

- `n == candies.length`
- `2 <= n <= 100`
- `1 <= candies[i] <= 100`
- `1 <= extraCandies <= 50`

---

# Learning Outcome

This problem demonstrates how to solve an array problem by first finding a **global maximum** and then comparing every element against that maximum after applying a given condition.

The main pattern is:

```text
Find maximum
     ↓
Check every element
     ↓
Add extra value
     ↓
Compare with maximum
     ↓
Store true / false
```

The solution runs in:

```text
Time  → O(n)
Space → O(n) including the output
```

The important condition is:

```java
num + extraCandies >= max
```

which determines whether each kid can have the greatest number of candies.