# 62. Unique Paths

**LeetCode Problem:** [62. Unique Paths](https://leetcode.com/problems/unique-paths/)

**Difficulty:** Medium

**Primary Topic:** Dynamic Programming

**Pattern:** 1D Dynamic Programming

---

## Problem Statement

You are given an `m x n` grid.

A robot starts at the **top-left corner** of the grid:

```text
(0, 0)
```

The robot wants to reach the **bottom-right corner**:

```text
(m - 1, n - 1)
```

The robot can move only:

```text
Right
Down
```

The task is to return the number of possible unique paths from the top-left corner to the bottom-right corner.

---

## Example 1

### Input

```text
m = 3
n = 7
```

### Output

```text
28
```

### Explanation

There are `28` different paths from the top-left corner to the bottom-right corner.

---

## Example 2

### Input

```text
m = 3
n = 2
```

### Output

```text
3
```

### Explanation

The possible paths are:

```text
Right → Down → Down

Down → Right → Down

Down → Down → Right
```

Therefore:

```text
Answer = 3
```

---

# Approach

This problem can be solved using **Dynamic Programming**.

For every cell, the number of ways to reach that cell is the sum of:

```text
Ways from the cell above
+
Ways from the cell on the left
```

So the basic recurrence is:

```text
dp[i][j] = dp[i-1][j] + dp[i][j-1]
```

However, the given solution uses only **one row** instead of a complete 2D DP table.

The code creates:

```java
int[][] dp = new int[1][n];
```

So only one row of `n` values is stored.

---

# Intuition

Consider a grid:

```text
3 x 3
```

Initially, the first row can only be reached by moving right:

```text
1 1 1
```

Similarly, the first column can only be reached by moving down.

The complete DP table would be:

```text
1 1 1
1 2 3
1 3 6
```

The bottom-right cell contains:

```text
6
```

So there are `6` unique paths.

The important recurrence is:

```text
current cell = top cell + left cell
```

---

# 2D DP Representation

If we used a normal 2D DP array:

```text
dp[i][j] = number of ways to reach cell (i, j)
```

Then:

```text
dp[i][j] = dp[i-1][j] + dp[i][j-1]
```

For a `3 x 3` grid:

```text
      j
      ↓
    0 1 2
  0 1 1 1
  1 1 2 3
  2 1 3 6
```

The answer is:

```text
dp[2][2] = 6
```

---

# Space Optimization

Notice that to calculate the current row, we only need:

```text
Previous row
+
Current row
```

We do not need to keep every previous row.

The given solution goes one step further and stores only **one row**.

```java
int[][] dp = new int[1][n];
```

Here:

```text
dp[0][j]
```

represents the number of paths to the current position in column `j`.

---

# How the 1D DP Works

The key line is:

```java
dp[0][j] += dp[0][j-1];
```

Before updating `dp[0][j]`:

```text
dp[0][j]
```

represents the value from the previous row.

And:

```text
dp[0][j-1]
```

represents the current row's left cell.

Therefore:

```text
dp[0][j] =
previous row value
+
current row left value
```

which is exactly:

```text
top + left
```

---

# Algorithm

1. Create a one-row DP array:
   ```java
   int[][] dp = new int[1][n];
   ```
2. Traverse every row from `0` to `m - 1`.
3. Traverse every column from `0` to `n - 1`.
4. For the first row or first column:
   ```java
   dp[0][j] = 1;
   ```
   because there is only one possible way to reach those cells.
5. For all other cells:
   ```java
   dp[0][j] += dp[0][j-1];
   ```
6. Return:
   ```java
   dp[0][n-1];
   ```

---

# Dry Run

Consider:

```text
m = 3
n = 3
```

We need to calculate the number of paths in a `3 x 3` grid.

---

## Initial DP

```text
dp = [0, 0, 0]
```

---

## Row 0

For `i = 0`, every cell belongs to the first row.

Therefore:

```java
if(i == 0 || j == 0)
```

sets every value to `1`.

After row `0`:

```text
dp = [1, 1, 1]
```

---

## Row 1

### Column 0

Because:

```text
j == 0
```

we set:

```text
dp[0][0] = 1
```

Array:

```text
[1, 1, 1]
```

### Column 1

Now:

```text
i != 0
j != 0
```

So:

```java
dp[0][1] += dp[0][0];
```

Current value:

```text
dp[0][1] = 1
dp[0][0] = 1
```

Therefore:

```text
dp[0][1] = 2
```

Array:

```text
[1, 2, 1]
```

### Column 2

```text
dp[0][2] += dp[0][1]
```

Therefore:

```text
1 + 2 = 3
```

Array:

```text
[1, 2, 3]
```

---

## Row 2

Start:

```text
[1, 2, 3]
```

### Column 0

First column:

```text
dp[0][0] = 1
```

Array:

```text
[1, 2, 3]
```

### Column 1

```text
dp[0][1] += dp[0][0]
```

```text
2 + 1 = 3
```

Array:

```text
[1, 3, 3]
```

### Column 2

```text
dp[0][2] += dp[0][1]
```

```text
3 + 3 = 6
```

Final:

```text
[1, 3, 6]
```

Therefore:

```text
Answer = 6
```

---

# Java Solution

```java
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[1][n];

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(i == 0 || j == 0){
                    dp[0][j] = 1;
                }
                else{
                    dp[0][j] += dp[0][j - 1];
                }
            }
        }

        return dp[0][n - 1];
    }
}
```

---

# Code Explanation

## 1. Create DP Array

```java
int[][] dp = new int[1][n];
```

Instead of creating:

```text
m x n
```

we create:

```text
1 x n
```

This saves space.

---

## 2. Traverse the Grid

```java
for(int i = 0; i < m; i++){
    for(int j = 0; j < n; j++){
```

The outer loop represents rows.

The inner loop represents columns.

---

## 3. Handle First Row and First Column

```java
if(i == 0 || j == 0){
    dp[0][j] = 1;
}
```

For the first row:

```text
→ → → →
```

There is only one way to reach each cell.

For the first column:

```text
↓
↓
↓
```

there is also only one way.

Therefore:

```text
dp = [1, 1, 1, ...]
```

for these boundary cells.

---

## 4. Calculate Remaining Cells

```java
else{
    dp[0][j] += dp[0][j - 1];
}
```

This is the most important line.

Before the update:

```text
dp[0][j]
```

contains the number of paths from the cell above.

And:

```text
dp[0][j - 1]
```

contains the number of paths from the cell on the left.

Therefore:

```text
dp[0][j] += dp[0][j - 1]
```

is equivalent to:

```text
dp[i][j] = dp[i-1][j] + dp[i][j-1]
```

---

## 5. Return the Last Cell

```java
return dp[0][n - 1];
```

The last position in the DP row contains the number of paths to the bottom-right corner.

---

# Why Does `dp[0][j]` Still Contain the Top Value?

This is the main idea behind the space optimization.

Suppose we are processing row `i`.

Before updating column `j`:

```text
dp[0][j]
```

still contains the result from the previous row.

So it represents:

```text
top
```

Meanwhile:

```text
dp[0][j - 1]
```

has already been updated for the current row.

So it represents:

```text
left
```

Therefore:

```text
dp[0][j] += dp[0][j - 1]
```

becomes:

```text
top + left
```

which is exactly the required recurrence.

---

# DP Visualization

For a `3 x 3` grid:

```text
Initial:

[0, 0, 0]
```

After first row:

```text
[1, 1, 1]
```

After second row:

```text
[1, 2, 3]
```

After third row:

```text
[1, 3, 6]
```

The final value:

```text
6
```

is the answer.

---

# Standard 2D DP vs Optimized DP

## Standard 2D DP

We could use:

```java
int[][] dp = new int[m][n];
```

with:

```java
dp[i][j] = dp[i-1][j] + dp[i][j-1];
```

Space:

```text
O(m * n)
```

---

## Optimized DP

The given solution uses:

```java
int[][] dp = new int[1][n];
```

Only one row is maintained.

Space:

```text
O(n)
```

This is a space optimization without changing the time complexity.

---

# Why Dynamic Programming?

The problem has two important DP properties.

## 1. Overlapping Subproblems

The number of paths to a cell is repeatedly used to calculate paths to later cells.

For example:

```text
paths to (1,1)
```

is used when calculating:

```text
paths to (1,2)
```

and:

```text
paths to (2,1)
```

---

## 2. Optimal Substructure

Every path to `(i, j)` must come from either:

```text
(i - 1, j)
```

or:

```text
(i, j - 1)
```

Therefore:

```text
ways(i,j) =
ways(i-1,j) + ways(i,j-1)
```

This naturally leads to dynamic programming.

---

# Recurrence Relation

The fundamental recurrence is:

```text
dp[i][j] = dp[i-1][j] + dp[i][j-1]
```

Base cases:

```text
dp[i][0] = 1
dp[0][j] = 1
```

because there is exactly one way to reach any cell in the first row or first column.

---

# Complexity Analysis

Let:

```text
m = number of rows
n = number of columns
```

## Time Complexity

We visit every cell exactly once.

There are:

```text
m * n
```

cells.

Therefore:

```text
Time = O(m * n)
```

---

## Space Complexity

The solution uses a DP array of size:

```text
1 x n
```

Therefore:

```text
Space = O(n)
```

This is better than the standard 2D DP solution:

```text
O(m * n)
```

---

# Common Mistakes

## Mistake 1: Forgetting the Base Case

The first row and first column must contain `1`.

```java
if(i == 0 || j == 0){
    dp[0][j] = 1;
}
```

Without this, the recurrence cannot work correctly.

---

## Mistake 2: Using Only the Left Value

Incorrect:

```java
dp[0][j] = dp[0][j - 1];
```

This ignores paths coming from above.

The correct recurrence is:

```text
top + left
```

which is implemented as:

```java
dp[0][j] += dp[0][j - 1];
```

---

## Mistake 3: Updating in the Wrong Direction

For this particular 1D optimization, we process columns from left to right.

That allows:

```text
dp[0][j - 1]
```

to already represent the current row's left value.

---

## Mistake 4: Using a 2D Array Unnecessarily

A full 2D array works, but it requires:

```text
O(m * n)
```

space.

The optimized solution needs only:

```text
O(n)
```

space.

---

# Alternative Mathematical Approach

This problem can also be solved using combinations.

To reach the bottom-right corner, the robot must make:

```text
m - 1
```

down moves and:

```text
n - 1
```

right moves.

Total moves:

```text
m + n - 2
```

The answer can therefore be calculated as:

```text
C(m + n - 2, m - 1)
```

However, the given solution uses dynamic programming, which is easier to understand as a grid-path DP problem.

---

# Key Concepts / Patterns

## 1. Dynamic Programming

The answer for each cell depends on previously calculated cells.

```text
Current = Top + Left
```

---

## 2. 1D DP

Instead of storing the entire grid:

```text
m x n
```

we store only:

```text
1 x n
```

---

## 3. Space Optimization

The previous row does not need to be stored separately because its values remain in the DP array until they are updated.

---

## 4. Grid DP

This is a classic grid dynamic programming pattern:

```text
      Top
       ↓
Left → Current
```

The recurrence is:

```text
Current = Top + Left
```

---

# Learning Outcome

After solving this problem, you should understand:

- How to formulate a grid problem as DP.
- How to derive the recurrence `top + left`.
- How to handle first-row and first-column base cases.
- How to optimize a 2D DP solution into 1D.
- How in-place updates can preserve the information needed for the recurrence.
- How to analyze time and space complexity of a DP solution.

---

# Summary

The number of ways to reach a cell is:

```text
ways(i, j)
=
ways(i - 1, j)
+
ways(i, j - 1)
```

The first row and first column have only one possible path.

The given solution uses a one-row DP array:

```java
int[][] dp = new int[1][n];
```

and updates it using:

```java
dp[0][j] += dp[0][j - 1];
```

This efficiently represents:

```text
Top + Left
```

### Final Complexity

```text
Time:  O(m * n)
Space: O(n)
```

**Primary Pattern: Dynamic Programming + 1D Space Optimization**