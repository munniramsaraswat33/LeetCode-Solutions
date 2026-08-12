# 1732. Find the Highest Altitude

> **Difficulty:** Easy  
> **Topics:** Array, Prefix Sum

---

## Problem Statement

A biker starts a road trip at point `0` with an altitude of `0`.

You are given an integer array `gain`, where:

```text
gain[i]
```

represents the net change in altitude from point `i` to point `i + 1`.

Return the **highest altitude** reached by the biker.

---

## Example 1

### Input

```text
gain = [-5,1,5,0,-7]
```

### Output

```text
1
```

### Explanation

Starting altitude:

```text
0
```

Calculate each next altitude:

```text
0 + (-5) = -5
-5 + 1   = -4
-4 + 5   = 1
1 + 0    = 1
1 + (-7) = -6
```

Therefore, the altitudes are:

```text
[0,-5,-4,1,1,-6]
```

The highest altitude is:

```text
1
```

---

## Example 2

### Input

```text
gain = [-4,-3,-2,-1,4,3,2]
```

### Output

```text
0
```

### Explanation

The altitudes are:

```text
[0,-4,-7,-9,-10,-6,-3,-1]
```

The biker never goes above the starting altitude.

Therefore, the highest altitude is:

```text
0
```

---

# Approach

This problem can be solved using a **prefix sum**.

The biker starts at altitude:

```text
0
```

For every value in `gain`, add it to the previous altitude.

For example:

```text
currentAltitude = currentAltitude + gain[i]
```

After calculating each altitude, keep track of the maximum altitude encountered.

Your solution stores all calculated altitudes in an array:

```text
nums[]
```

where:

```text
nums[i] = altitude at point i
```

---

# Algorithm

1. Create an array of size `n + 1`.
2. Set:
   ```text
   nums[0] = 0
   ```
3. Initialize:
   ```text
   max = 0
   ```
4. Traverse the `gain` array.
5. Calculate the next altitude:
   ```text
   nums[i] = nums[i - 1] + gain[i - 1]
   ```
6. Update the maximum altitude:
   ```text
   max = Math.max(max, nums[i])
   ```
7. Return `max`.

---

# Dry Run

### Input

```text
gain = [-5,1,5,0,-7]
```

Initial:

```text
nums[0] = 0
max = 0
```

### Step 1

```text
nums[1] = nums[0] + gain[0]
        = 0 + (-5)
        = -5
```

Maximum:

```text
max = 0
```

### Step 2

```text
nums[2] = -5 + 1
        = -4
```

Maximum:

```text
max = 0
```

### Step 3

```text
nums[3] = -4 + 5
        = 1
```

Maximum:

```text
max = 1
```

### Step 4

```text
nums[4] = 1 + 0
        = 1
```

Maximum remains:

```text
1
```

### Step 5

```text
nums[5] = 1 + (-7)
        = -6
```

Final:

```text
nums = [0,-5,-4,1,1,-6]
max = 1
```

Answer:

```text
1
```

---

# Prefix Sum Concept

This problem is a simple example of a **prefix sum**.

If:

```text
gain = [g1,g2,g3,...]
```

then the altitude at each point is:

```text
0
g1
g1 + g2
g1 + g2 + g3
...
```

We simply need the maximum value among these prefix sums.

---

# Complexity Analysis

Let `n` be the length of `gain`.

### Time Complexity

We traverse the array once:

```text
O(n)
```

---

### Space Complexity

The current solution creates an additional array of size `n + 1`:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int largestAltitude(int[] gain) {

        int n = gain.length;

        int nums[] = new int[n + 1];

        nums[0] = 0;

        int max = nums[0];

        for (int i = 1; i <= n; i++) {

            nums[i] = nums[i - 1] + gain[i - 1];

            max = Math.max(max, nums[i]);
        }

        return max;
    }
}
```

---

# Space-Optimized Approach

We don't actually need to store every altitude.

We only need:

- Current altitude
- Maximum altitude

Therefore, the array can be removed.

```java
class Solution {

    public int largestAltitude(int[] gain) {

        int altitude = 0;
        int max = 0;

        for (int value : gain) {

            altitude += value;

            max = Math.max(max, altitude);
        }

        return max;
    }
}
```

This optimized version uses:

```text
Time:  O(n)
Space: O(1)
```

---

# Key Concepts

- Array
- Prefix Sum
- Running Sum
- Maximum Tracking

---

# Constraints

- `1 <= gain.length <= 100`
- `-100 <= gain[i] <= 100`

---

# Learning Outcome

This problem demonstrates the **running sum / prefix sum** technique.

The main idea is simple:

```text
current altitude
      ↓
current altitude + gain[i]
      ↓
update maximum
```

The important pattern to remember is:

```java
sum += value;
max = Math.max(max, sum);
```

This pattern appears frequently in array and prefix-sum problems.

Your submitted solution uses **O(n) extra space**, while the optimized version achieves **O(1) extra space** without changing the time complexity.