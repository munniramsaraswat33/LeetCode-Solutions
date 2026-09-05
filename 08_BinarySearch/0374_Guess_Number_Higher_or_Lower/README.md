# 374. Guess Number Higher or Lower

**LeetCode Problem:** [374. Guess Number Higher or Lower](https://leetcode.com/problems/guess-number-higher-or-lower/)

**Difficulty:** Easy

**Primary Topic:** Binary Search

---

## Problem Statement

We are playing a number guessing game.

The game has a hidden number `pick` between `1` and `n`.

We need to find the hidden number by making guesses.

LeetCode provides a predefined API:

```java
int guess(int num);
```

The `guess()` function returns:

- `-1` → our guessed number is **higher** than the picked number.
- `1` → our guessed number is **lower** than the picked number.
- `0` → our guessed number is **equal** to the picked number.

We need to return the picked number.

---

## Example 1

### Input

```text
n = 10, pick = 6
```

### Output

```text
6
```

### Explanation

We need to find the hidden number `6` from the range `1` to `10`.

Using binary search:

```text
Range: 1 2 3 4 5 6 7 8 9 10
```

We repeatedly check the middle number and eliminate half of the search space.

---

## Example 2

### Input

```text
n = 1, pick = 1
```

### Output

```text
1
```

---

## Example 3

### Input

```text
n = 2, pick = 1
```

### Output

```text
1
```

---

# Approach

We use **Binary Search**.

Initially, the possible answer lies between:

```text
1 and n
```

We maintain two pointers:

```text
left = 0
right = n
```

At every step, calculate the middle value:

```java
mid = left + (right - left) / 2;
```

Then call:

```java
guess(mid)
```

There are three possibilities.

### Case 1: `guess(mid) == 0`

The guessed number is exactly the picked number.

So we return:

```java
mid
```

### Case 2: `guess(mid) == -1`

This means our guess is **higher** than the picked number.

Therefore, the picked number must be on the left side.

```text
right = mid - 1
```

### Case 3: `guess(mid) == 1`

This means our guess is **lower** than the picked number.

Therefore, the picked number must be on the right side.

```text
left = mid + 1
```

We continue until the answer is found.

---

# Intuition

The important observation is that the `guess()` API tells us which half of the range contains the answer.

Suppose:

```text
n = 10
pick = 6
```

We start with:

```text
1 2 3 4 5 6 7 8 9 10
```

Middle:

```text
mid = 5
```

Since `5` is lower than `6`:

```text
guess(5) = 1
```

So we know that the answer is greater than `5`.

We discard:

```text
1 2 3 4 5
```

and continue with:

```text
6 7 8 9 10
```

Now binary search continues.

This reduces the search space by approximately half after every guess.

---

# Algorithm

1. Initialize `left = 0`.
2. Initialize `right = n`.
3. While `left <= right`:
   - Calculate `mid`.
   - Call `guess(mid)`.
   - If the result is `0`, return `mid`.
   - If the result is `-1`, move `right` to `mid - 1`.
   - Otherwise, move `left` to `mid + 1`.
4. Return `0` if the loop finishes without finding the number.

---

# Dry Run

Consider:

```text
n = 10
pick = 6
```

### Step 1

```text
left = 0
right = 10

mid = 0 + (10 - 0) / 2
    = 5
```

Check:

```text
guess(5)
```

Since `5 < 6`:

```text
guess(5) = 1
```

Therefore:

```text
left = 6
```

---

### Step 2

Now:

```text
left = 6
right = 10
```

Calculate:

```text
mid = 6 + (10 - 6) / 2
    = 8
```

Check:

```text
guess(8)
```

Since `8 > 6`:

```text
guess(8) = -1
```

Therefore:

```text
right = 7
```

---

### Step 3

Now:

```text
left = 6
right = 7
```

Calculate:

```text
mid = 6 + (7 - 6) / 2
    = 6
```

Check:

```text
guess(6)
```

Since:

```text
6 == pick
```

we get:

```text
guess(6) = 0
```

Therefore:

```text
return 6;
```

### Final Answer

```text
6
```

---

# Java Solution

```java
/** 
 * Forward declaration of guess API.
 * @param  num   your guess
 * @return        -1 if num is higher than the picked number
 *                 1 if num is lower than the picked number
 *                 otherwise return 0
 * int guess(int num);
 */

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int left = 0;
        int right = n;

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(guess(mid) == 0){
                return mid;
            }
            else if(guess(mid) == -1){
                right = mid - 1;
            }
            else{
                left = mid + 1;
            }
        }

        return 0;
    }
}
```

---

# Code Explanation

## 1. Initialize Search Range

```java
int left = 0;
int right = n;
```

The hidden number is within the given range.

We use `left` and `right` to represent the current search range.

---

## 2. Binary Search Loop

```java
while(left <= right)
```

As long as the search range is valid, we continue searching.

---

## 3. Find Middle Element

```java
int mid = left + (right - left) / 2;
```

This calculates the middle of the current range.

Instead of:

```java
(left + right) / 2
```

we use:

```java
left + (right - left) / 2
```

which is a safer standard binary-search calculation because it avoids overflow in cases where `left + right` could exceed the integer limit.

---

## 4. Check the Guess

```java
if(guess(mid) == 0){
    return mid;
}
```

If `guess(mid)` returns `0`, the guessed number is the picked number.

So we immediately return `mid`.

---

## 5. Guess Is Too High

```java
else if(guess(mid) == -1){
    right = mid - 1;
}
```

`-1` means:

```text
mid > pick
```

Therefore, the answer must be smaller than `mid`.

So we search the left half:

```text
right = mid - 1
```

---

## 6. Guess Is Too Low

```java
else{
    left = mid + 1;
}
```

The remaining case means:

```text
mid < pick
```

Therefore, the answer must be greater than `mid`.

So we search the right half:

```text
left = mid + 1
```

---

## 7. Return

```java
return 0;
```

The loop should find the picked number according to the problem conditions.

This return simply provides a fallback value if the loop terminates without finding the answer.

---

# Binary Search Visualization

Suppose:

```text
n = 10
pick = 6
```

Initial range:

```text
[1 2 3 4 5 6 7 8 9 10]
```

Check `5`:

```text
5 < 6
```

Search right:

```text
[6 7 8 9 10]
```

Check `8`:

```text
8 > 6
```

Search left:

```text
[6 7]
```

Check `6`:

```text
6 == 6
```

Answer:

```text
6
```

---

# Why Binary Search?

A normal linear search would check:

```text
1, 2, 3, 4, 5, 6, ...
```

In the worst case, it could require `n` guesses.

Binary search eliminates approximately half of the remaining possibilities after each guess.

For example:

```text
n = 100
```

Instead of potentially checking 100 numbers, binary search needs only around:

```text
log2(100) ≈ 7
```

guesses.

Therefore, binary search is much more efficient.

---

# Complexity Analysis

Let `n` be the given upper limit.

### Time Complexity

```text
O(log n)
```

Every iteration removes approximately half of the remaining search space.

### Space Complexity

```text
O(1)
```

Only a few variables such as `left`, `right`, and `mid` are used.

---

# Key Concepts / Patterns

## 1. Binary Search

The main technique used in this problem is binary search.

```text
Search Space
     ↓
Divide into two halves
     ↓
Use feedback to choose one half
     ↓
Repeat
```

---

## 2. Search on a Number Range

We are not searching through an actual array.

Instead, we are searching through the numerical range:

```text
[1, n]
```

This is an example of applying binary search directly to a **search space**.

---

## 3. Decision-Based Search

The `guess()` function gives us information about the location of the answer.

```text
guess(mid) == 0   → Found
guess(mid) == -1  → Search left
guess(mid) == 1   → Search right
```

This information allows us to discard half of the search space.

---

# Important Binary Search Pattern

This problem follows the basic binary search pattern:

```java
int left = 0;
int right = n;

while(left <= right){
    int mid = left + (right - left) / 2;

    if(condition){
        return mid;
    }
    else if(searchLeft){
        right = mid - 1;
    }
    else{
        left = mid + 1;
    }
}
```

Remember this pattern because it is used in many binary search problems.

---

# Common Mistakes

## Mistake 1: Moving in the Wrong Direction

When:

```java
guess(mid) == -1
```

it means:

```text
mid > pick
```

So we must move left:

```java
right = mid - 1;
```

Not:

```java
left = mid + 1;
```

---

## Mistake 2: Forgetting `mid - 1` and `mid + 1`

After checking `mid`, we already know that `mid` is not the answer unless `guess(mid) == 0`.

Therefore:

```java
right = mid - 1;
```

or:

```java
left = mid + 1;
```

is appropriate.

---

## Mistake 3: Using Linear Search

A solution such as:

```java
for(int i = 1; i <= n; i++){
    if(guess(i) == 0){
        return i;
    }
}
```

works conceptually but has:

```text
O(n)
```

time complexity.

Binary search improves this to:

```text
O(log n)
```

---

# Learning Outcome

After solving this problem, you should understand:

- How binary search works on a numerical range.
- How to use feedback to eliminate half of the search space.
- How to implement the standard binary search template.
- How to calculate `mid` safely.
- How binary search reduces `O(n)` searching to `O(log n)`.

---

# Summary

The hidden number lies between `1` and `n`.

Instead of checking every number, we use binary search.

At every step:

```text
guess(mid) == 0
        ↓
      Found

guess(mid) == -1
        ↓
   Guess is high
        ↓
   Search left

guess(mid) == 1
        ↓
   Guess is low
        ↓
  Search right
```

The final solution runs in:

```text
Time:  O(log n)
Space: O(1)
```

**Primary Pattern: Binary Search**