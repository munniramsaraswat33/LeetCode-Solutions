# 1590. Make Sum Divisible by P

> **Difficulty:** Medium  
> **Topics:** Array, Hash Table, Prefix Sum, Modular Arithmetic

---

## Problem Statement

Given an array of positive integers `nums` and an integer `p`, remove the **smallest contiguous subarray** such that the sum of the remaining elements is divisible by `p`.

The removed subarray can be empty.

However, it is **not allowed to remove the entire array**.

Return the length of the smallest subarray that needs to be removed.

If it is impossible, return:

```text
-1
```

---

## Example 1

### Input

```text
nums = [3,1,4,2]
p = 6
```

### Output

```text
1
```

### Explanation

Total sum:

```text
3 + 1 + 4 + 2 = 10
```

Since:

```text
10 % 6 = 4
```

we need to remove a subarray whose sum has remainder `4`.

The subarray:

```text
[4]
```

has sum `4`.

After removing it:

```text
[3,1,2]
```

Sum:

```text
3 + 1 + 2 = 6
```

which is divisible by `6`.

Therefore:

```text
answer = 1
```

---

## Example 2

### Input

```text
nums = [6,3,5,2]
p = 9
```

### Output

```text
2
```

### Explanation

Total sum:

```text
6 + 3 + 5 + 2 = 16
```

```text
16 % 9 = 7
```

We need to remove a subarray whose sum has remainder `7`.

The subarray:

```text
[5,2]
```

has sum:

```text
7
```

After removing it:

```text
[6,3]
```

Sum:

```text
9
```

which is divisible by `9`.

Therefore:

```text
answer = 2
```

---

## Example 3

### Input

```text
nums = [1,2,3]
p = 3
```

### Output

```text
0
```

### Explanation

Total sum:

```text
1 + 2 + 3 = 6
```

Since:

```text
6 % 3 = 0
```

the sum is already divisible by `p`.

Therefore, we don't need to remove anything.

```text
answer = 0
```

---

# Key Observation

Let:

```text
total = sum(nums)
```

If:

```text
total % p = 0
```

then no removal is required.

Otherwise, let:

```text
target = total % p
```

We need to find the **smallest subarray whose sum has remainder `target` when divided by `p`**.

---

# Why?

Suppose:

```text
total % p = target
```

If we remove a subarray with sum `subarraySum`, then:

```text
remainingSum = total - subarraySum
```

We need:

```text
remainingSum % p = 0
```

Therefore:

```text
(total - subarraySum) % p = 0
```

So:

```text
subarraySum % p = total % p
```

Thus:

```text
subarraySum % p = target
```

Now the problem becomes:

> Find the shortest subarray whose sum modulo `p` equals `target`.

---

# Prefix Sum + HashMap

Let:

```text
prefix[i] = nums[0] + nums[1] + ... + nums[i]
```

We only care about the remainder modulo `p`.

Suppose:

```text
prefix[j] % p = current
```

and we want a previous prefix remainder:

```text
required
```

For a subarray from `k + 1` to `j`:

```text
subarraySum = prefix[j] - prefix[k]
```

We need:

```text
(prefix[j] - prefix[k]) % p = target
```

Therefore:

```text
prefix[k] % p = (current - target + p) % p
```

So:

```java
int required = (current - target + p) % p;
```

If this remainder exists in the HashMap, we have found a candidate subarray.

---

# Why `(current - target + p) % p`?

We need:

```text
current - previous = target
```

Therefore:

```text
previous = current - target
```

But this value can be negative.

For example:

```text
current = 2
target = 5
```

Then:

```text
current - target = -3
```

We don't want a negative remainder.

So we add `p`:

```text
(2 - 5 + 6) % 6
= 3
```

Therefore:

```java
int required = (current - target + p) % p;
```

This keeps the remainder in the range:

```text
0 ... p-1
```

---

# Algorithm

1. Calculate the total sum of the array.
2. Calculate:
   ```text
   target = total % p
   ```
3. If `target == 0`, return `0`.
4. Create a `HashMap`:
   ```text
   remainder → latest index
   ```
5. Initialize:
   ```java
   map.put(0, -1);
   ```
6. Traverse the array.
7. Calculate the current prefix remainder:
   ```text
   prefix = (prefix + nums[i]) % p
   ```
8. Calculate the required previous remainder:
   ```text
   required = (current - target + p) % p
   ```
9. If `required` exists in the map:
   - Calculate the subarray length.
   - Update the minimum answer.
10. Store the current remainder with its index.
11. If the answer is still `nums.length`, return `-1`.
12. Otherwise, return the answer.

---

# Why `map.put(0, -1)`?

This is an important prefix-sum technique.

Suppose the required subarray starts from index `0`.

For example:

```text
nums = [4, ...]
```

If the prefix from:

```text
0 → i
```

itself has the required remainder, we need a previous index of:

```text
-1
```

Then:

```text
length = i - (-1)
       = i + 1
```

which is exactly the length of the subarray from index `0` to `i`.

Therefore:

```java
map.put(0, -1);
```

is necessary.

---

# Dry Run

### Input

```text
nums = [3,1,4,2]
p = 6
```

Total sum:

```text
3 + 1 + 4 + 2 = 10
```

Therefore:

```text
target = 10 % 6 = 4
```

We need a subarray with:

```text
sum % 6 = 4
```

Initial map:

```text
{0 : -1}
```

---

### Index 0

```text
prefix = (0 + 3) % 6
       = 3
```

Current remainder:

```text
current = 3
```

Required:

```text
required = (3 - 4 + 6) % 6
         = 5
```

`5` is not in the map.

Store:

```text
map = {0:-1, 3:0}
```

---

### Index 1

```text
prefix = (3 + 1) % 6
       = 4
```

Required:

```text
required = (4 - 4 + 6) % 6
         = 0
```

`0` exists at index `-1`.

Therefore:

```text
length = 1 - (-1)
       = 2
```

Candidate:

```text
[3,1]
```

But we continue searching because we want the **smallest** subarray.

Store:

```text
map = {0:-1, 3:0, 4:1}
```

---

### Index 2

```text
prefix = (4 + 4) % 6
       = 2
```

Required:

```text
required = (2 - 4 + 6) % 6
         = 4
```

Remainder `4` exists at index `1`.

Therefore:

```text
length = 2 - 1
       = 1
```

The corresponding subarray is:

```text
[4]
```

Now:

```text
ans = 1
```

---

### Index 3

```text
prefix = (2 + 2) % 6
       = 4
```

Required:

```text
required = (4 - 4 + 6) % 6
         = 0
```

`0` exists at `-1`.

Length:

```text
3 - (-1) = 4
```

This would mean removing the entire array, which is **not allowed**.

Since:

```text
ans = 1
```

we keep the answer as `1`.

Final answer:

```text
1
```

---

# Important: Why Store the Latest Index?

The code uses:

```java
map.put(current, i);
```

This replaces the previous index with the latest one.

For example, if:

```text
remainder = 4
```

appears at indices:

```text
1, 3, 5
```

and we are currently at index `6`, we want:

```text
6 - 5 = 1
```

rather than:

```text
6 - 1 = 5
```

because we need the **shortest** subarray.

Therefore, storing the latest index helps minimize the subarray length.

---

# Why We Cannot Remove the Whole Array

The problem explicitly says:

```text
It is not allowed to remove the whole array.
```

The entire array has length:

```text
nums.length
```

Therefore, we initialize:

```java
int ans = nums.length;
```

If the only possible candidate has length `nums.length`, it is invalid.

At the end:

```java
return ans == nums.length ? -1 : ans;
```

---

# Java Solution

```java
class Solution {

    public int minSubarray(int[] nums, int p) {

        long total = 0;

        // Calculate total sum
        for (int num : nums) {
            total += num;
        }

        int target = (int) (total % p);

        // Already divisible
        if (target == 0) {
            return 0;
        }

        long prefix = 0;
        int ans = nums.length;

        HashMap<Integer, Integer> map = new HashMap<>();

        // Remainder 0 before the array starts
        map.put(0, -1);

        for (int i = 0; i < nums.length; i++) {

            prefix = (prefix + nums[i]) % p;

            int current = (int) prefix;

            int required =
                (current - target + p) % p;

            if (map.containsKey(required)) {

                int length =
                    i - map.get(required);

                ans = Math.min(ans, length);
            }

            // Store latest index
            map.put(current, i);
        }

        return ans == nums.length ? -1 : ans;
    }
}
```

---

# Complexity Analysis

Let `n` be the length of `nums`.

### Time Complexity

We traverse the array once.

HashMap operations take average `O(1)` time.

Therefore:

```text
O(n)
```

---

### Space Complexity

The HashMap can contain up to `n` different remainders:

```text
O(n)
```

---

# Key Concepts

- Prefix Sum
- HashMap
- Modular Arithmetic
- Subarray
- Remainder Technique
- Shortest Subarray
- Two-Pointer-like Prefix Technique

---

# Constraints

- `1 <= nums.length <= 10⁵`
- `1 <= nums[i] <= 10⁹`
- `1 <= p <= 10⁹`

---

# Learning Outcome

This problem is an important example of combining:

```text
Prefix Sum
      +
Modulo
      +
HashMap
```

The key transformation is:

```text
Find subarray whose sum % p == total % p
```

Then use prefix remainders to find that subarray efficiently.

The most important formula is:

```java
int required = (current - target + p) % p;
```

And the important initialization is:

```java
map.put(0, -1);
```

The overall complexity is:

```text
Time:  O(n)
Space: O(n)
```