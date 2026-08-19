# 1386. Cinema Seat Allocation

> **Difficulty:** Medium  
> **Topics:** HashMap, HashSet, Greedy, Array

---

## Problem Statement

A cinema has `n` rows of seats, numbered from `1` to `n`. Each row has `10` seats, numbered from `1` to `10`.

You are given a 2D integer array `reservedSeats`, where:

```text
reservedSeats[i] = [rowi, seati]
```

means that seat `seati` in row `rowi` is already reserved.

A four-person group must be assigned to four seats in the **same row**.

A group can be seated in one of the following blocks:

```text
[2,3,4,5]
[4,5,6,7]
[6,7,8,9]
```

A block can be used only if none of its seats are reserved.

Return the **maximum number of four-person groups** that can be assigned.

---

## Example 1

### Input

```text
n = 3
reservedSeats = [[1,2],[1,3],[1,8],[2,6],[3,1],[3,10]]
```

### Output

```text
4
```

### Explanation

The maximum number of four-person groups that can be seated is `4`.

---

## Example 2

### Input

```text
n = 2
reservedSeats = [[2,1],[1,8],[2,6]]
```

### Output

```text
2
```

---

## Example 3

### Input

```text
n = 4
reservedSeats = [[4,3],[1,4],[4,6],[1,7]]
```

### Output

```text
4
```

---

# Approach

Use a **HashMap** with a **HashSet** to store the reserved seats for each row.

The important observation is that `n` can be as large as `10^9`, so we cannot iterate through all rows.

Instead, we only process the rows that actually contain reserved seats.

For every row, check the three possible blocks:

```text
Left   = [2,3,4,5]
Middle = [4,5,6,7]
Right  = [6,7,8,9]
```

If both `left` and `right` are available, we can place **2 groups** because these two blocks do not overlap.

Otherwise, if any one of the three blocks is available, we can place **1 group**.

Rows with no reserved seats can always accommodate **2 groups**.

---

# Algorithm

1. Create a `HashMap<Integer, Set<Integer>>`.
2. Store all reserved seats according to their row.
3. Calculate the number of rows without reservations:

```text
n - map.size()
```

4. Every completely free row contributes `2` groups.
5. For every row having reservations:
   - Check the left block.
   - Check the middle block.
   - Check the right block.
6. If `left && right`, add `2`.
7. Otherwise, if `left || middle || right`, add `1`.
8. Return the total number of groups.

---

# Dry Run

Input:

```text
n = 3
reservedSeats = [[1,2],[1,3],[1,8],[2,6],[3,1],[3,10]]
```

### Step 1

Store reserved seats:

```text
Row 1 -> {2,3,8}
Row 2 -> {6}
Row 3 -> {1,10}
```

All `3` rows contain reservations.

```text
n - map.size()
= 3 - 3
= 0
```

So initially:

```text
ans = 0
```

### Row 1

```text
Reserved = {2,3,8}
```

```text
Left   [2,3,4,5] -> Not available
Middle [4,5,6,7] -> Available
Right  [6,7,8,9] -> Not available
```

Therefore:

```text
ans = 1
```

### Row 2

```text
Reserved = {6}
```

```text
Left   [2,3,4,5] -> Available
Middle [4,5,6,7] -> Not available
Right  [6,7,8,9] -> Not available
```

Therefore:

```text
ans = 2
```

### Row 3

```text
Reserved = {1,10}
```

```text
Left  [2,3,4,5] -> Available
Right [6,7,8,9] -> Available
```

Both blocks are available:

```text
ans += 2
```

Final:

```text
ans = 4
```

---

# Complexity Analysis

### Time Complexity

Let `m` be the number of reserved seats.

Building the HashMap takes:

```text
O(m)
```

For every affected row, we check only a constant number of seats.

Therefore:

```text
O(m)
```

---

### Space Complexity

The HashMap and HashSets store the reserved seats:

```text
O(m)
```

where `m` is the number of reserved seats.

---

# Java Solution

```java
class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Set<Integer>> map = new HashMap<>();

        for(int[] seat : reservedSeats){
            map.putIfAbsent(seat[0], new HashSet<>());
            map.get(seat[0]).add(seat[1]);
        }

        int ans = (n - map.size()) * 2;

        for(Set<Integer> set : map.values()){

            boolean left =
                    !set.contains(2) &&
                    !set.contains(3) &&
                    !set.contains(4) &&
                    !set.contains(5);

            boolean middle =
                    !set.contains(4) &&
                    !set.contains(5) &&
                    !set.contains(6) &&
                    !set.contains(7);

            boolean right =
                    !set.contains(6) &&
                    !set.contains(7) &&
                    !set.contains(8) &&
                    !set.contains(9);

            if(left && right){
                ans += 2;
            }
            else if(left || middle || right){
                ans += 1;
            }
        }

        return ans;
    }
}
```

---

# Key Concepts

- HashMap
- HashSet
- Greedy
- Array
- Grouping data by row
- `HashSet.contains()`
- Handling large constraints

---

# Constraints

- `1 <= n <= 10^9`
- `1 <= reservedSeats.length <= min(10 * n, 10^4)`
- `1 <= rowi <= n`
- `1 <= seati <= 10`
- All reserved seat positions are unique.

---

# Learning Outcome

This problem demonstrates how a **HashMap + HashSet** can efficiently handle a very large number of rows when only a small number of rows contain reserved seats.

The key idea is to avoid checking all `n` rows and process only the rows present in `reservedSeats`.

The main conditions are:

```text
left && right -> 2 groups
left || middle || right -> 1 group
none -> 0 groups
```

This gives an efficient solution with:

```text
Time  : O(m)
Space : O(m)
```

where `m` is the number of reserved seats.