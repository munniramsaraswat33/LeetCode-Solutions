# 875. Koko Eating Bananas

**Difficulty:** Medium  
**Topics:** Array, Binary Search

---

## Problem Statement

Koko loves eating bananas.

There are `n` piles of bananas, where `piles[i]` represents the number of bananas in the `i-th` pile.

Koko can choose an integer eating speed `k`, meaning she eats `k` bananas per hour.

For each pile:

- If the pile contains fewer than `k` bananas, Koko finishes it in one hour.
- Otherwise, she continues eating `k` bananas per hour until the pile is empty.

Koko wants to eat all bananas within `h` hours.

Return the **minimum integer eating speed `k`** that allows Koko to finish all bananas within `h` hours.

---

## Example 1

### Input

```text
piles = [3,6,7,11]
h = 8
```

### Output

```text
4
```

### Explanation

At speed `k = 4`:

```text
3  → 1 hour
6  → 2 hours
7  → 2 hours
11 → 3 hours
```

Total:

```text
1 + 2 + 2 + 3 = 8 hours
```

So the minimum eating speed is:

```text
4
```

---

## Example 2

### Input

```text
piles = [30,11,23,4,20]
h = 5
```

### Output

```text
30
```

### Explanation

Koko has exactly 5 hours and there are 5 piles.

Therefore, she must finish one pile every hour.

The largest pile contains `30` bananas, so the minimum possible speed is:

```text
30
```

---

# Approach

This problem can be solved using **Binary Search on the Answer**.

The possible eating speed `k` lies between:

```text
1
```

and:

```text
maximum pile size
```

So:

```java
left = 1;
right = maximum pile
```

For every possible speed, we calculate how many hours Koko needs.

If a speed is valid, we try a smaller speed.

If a speed is not valid, we need a larger speed.

This gives us a monotonic search space, which is perfect for binary search.

---

# Intuition

Suppose:

```text
piles = [3, 6, 7, 11]
h = 8
```

Possible speeds behave like this:

```text
Speed 1  → Too slow
Speed 2  → Too slow
Speed 3  → Too slow
Speed 4  → Valid
Speed 5  → Valid
Speed 6  → Valid
...
Speed 11 → Valid
```

Once a speed becomes valid, every larger speed is also valid.

So the pattern is:

```text
False False False True True True True
                  ↑
             Minimum valid
```

Binary search finds this first `true`.

---

# Calculating Required Hours

For a pile of size `pile` and speed `k`, the required hours are:

```text
ceil(pile / k)
```

In Java, this can be calculated as:

```java
(pile + k - 1) / k
```

For example:

```text
pile = 11
k = 4

(11 + 4 - 1) / 4
= 14 / 4
= 3
```

So 3 hours are required.

---

# Algorithm

1. Set `left = 1`.
2. Find the maximum pile and set it as `right`.
3. While `left < right`:
   - Calculate:
     ```text
     mid = left + (right - left) / 2
     ```
   - Check whether speed `mid` allows Koko to finish within `h` hours.
4. If `mid` is valid:
   ```text
   right = mid
   ```
   because we try to find a smaller valid speed.
5. Otherwise:
   ```text
   left = mid + 1
   ```
6. When `left == right`, return `left`.

---

# Dry Run

Consider:

```text
piles = [3, 6, 7, 11]
h = 8
```

Maximum pile:

```text
right = 11
```

Initial search range:

```text
left = 1
right = 11
```

### Iteration 1

```text
mid = 6
```

Required hours:

```text
3  → 1
6  → 1
7  → 2
11 → 2
```

Total:

```text
6 hours
```

Since:

```text
6 <= 8
```

speed `6` is valid.

Search smaller speeds:

```text
left = 1
right = 6
```

---

### Iteration 2

```text
mid = 3
```

Required hours:

```text
3  → 1
6  → 2
7  → 3
11 → 4
```

Total:

```text
10 hours
```

Since:

```text
10 > 8
```

speed `3` is invalid.

Search larger speeds:

```text
left = 4
right = 6
```

---

### Iteration 3

```text
mid = 5
```

Required hours:

```text
3  → 1
6  → 2
7  → 2
11 → 3
```

Total:

```text
8 hours
```

Valid.

```text
left = 4
right = 5
```

---

### Iteration 4

```text
mid = 4
```

Required hours:

```text
3  → 1
6  → 2
7  → 2
11 → 3
```

Total:

```text
8 hours
```

Valid.

```text
right = 4
```

Now:

```text
left = right = 4
```

Therefore:

```text
Answer = 4
```

---

# Java Solution

```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;

        for(int pile : piles){
            right = Math.max(right, pile);
        }

        while(left < right){
            int mid = left + (right - left) / 2;

            if(isvalid(piles, mid, h)){
                right = mid;
            }
            else{
                left = mid + 1;
            }
        }

        return left;
    }

    public boolean isvalid(int[] piles, int k, int h){
        long hour = 0;

        for(int pile : piles){
            hour += (pile + k - 1) / k;

            if(hour > h){
                return false;
            }
        }

        return true;
    }
}
```

---

# Code Explanation

### Search Range

```java
int left = 1;
int right = 0;

for(int pile : piles){
    right = Math.max(right, pile);
}
```

The minimum possible speed is `1`.

The maximum necessary speed is the largest pile because Koko can finish that pile in one hour at that speed.

---

### Binary Search

```java
while(left < right){
    int mid = left + (right - left) / 2;
```

We test the middle eating speed.

---

### Check Whether Speed Is Valid

```java
if(isvalid(piles, mid, h)){
    right = mid;
}
```

If Koko can finish within `h` hours, `mid` is a valid answer.

We try to find an even smaller valid speed.

---

### Invalid Speed

```java
else{
    left = mid + 1;
}
```

If Koko needs more than `h` hours, the speed is too slow.

Therefore, we must increase the speed.

---

### Calculate Hours

```java
hour += (pile + k - 1) / k;
```

This calculates:

```text
ceil(pile / k)
```

without using floating-point arithmetic.

---

### Early Stop

```java
if(hour > h){
    return false;
}
```

If the required hours already exceed `h`, the current speed cannot be valid.

There is no need to process the remaining piles.

---

# Why Binary Search Works

The validity of the eating speed is monotonic.

If speed `k` is enough:

```text
k → Valid
```

then every speed greater than `k` is also valid.

For example:

```text
1  2  3  4  5  6  7  8
F  F  F  T  T  T  T  T
         ↑
      Answer
```

Binary search finds the first valid speed efficiently.

---

# Complexity Analysis

Let:

- `n` = number of piles
- `M` = maximum number of bananas in a pile

### Time Complexity

Finding the maximum:

```text
O(n)
```

Binary search performs:

```text
O(log M)
```

iterations.

Each validity check takes:

```text
O(n)
```

Therefore:

```text
O(n log M)
```

### Space Complexity

```text
O(1)
```

Only a few variables are used.

---

# Key Concepts / Patterns

- Binary Search
- Binary Search on Answer
- Monotonic Search Space
- Ceiling Division
- Greedy Validation
- Array Traversal

---

# Learning Outcome

- Learn how to apply binary search to an answer range instead of an array.
- Understand monotonic conditions.
- Learn how to calculate ceiling division using integers.
- Practice designing a `isValid()` function for binary search.
- Understand why the minimum valid value can be found using binary search.