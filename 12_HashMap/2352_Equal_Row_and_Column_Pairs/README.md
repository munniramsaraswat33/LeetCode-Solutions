# 2352. Equal Row and Column Pairs

> **Difficulty:** Medium  
> **Topics:** Array, Hash Table, Matrix

---

## Problem Statement

Given an `n x n` integer matrix `grid`, return the number of pairs `(ri, cj)` such that row `ri` and column `cj` are equal.

A row and a column are considered equal if they contain the same elements in the same order.

---

## Example 1

### Input

```text
grid = [[3,2,1],
        [1,7,6],
        [2,7,7]]
```

### Output

```text
1
```

### Explanation

Row `0` is:

```text
[3,2,1]
```

Column `0` is:

```text
[3,1,2]
```

They are not equal.

The only equal row-column pair is:

```text
[2,7,7]
```

Therefore:

```text
Output = 1
```

---

## Example 2

### Input

```text
grid = [[3,1,2,2],
        [1,4,4,5],
        [2,4,2,2],
        [2,4,2,2]]
```

### Output

```text
3
```

There are three pairs where a row is exactly equal to a column.

---

# Approach

Use **Matrix Traversal**.

For every row, compare it with every column.

We can first compare:

```text
grid[i][0]
```

with:

```text
grid[0][j]
```

If these first elements are different, the row and column cannot be equal, so we skip them.

If they are equal, compare all elements:

```text
grid[i][n] == grid[n][j]
```

If every element matches, we have found one equal row-column pair.

---

# Algorithm

1. Store the size of the square matrix:
   ```text
   m = grid.length
   ```
2. Use two loops to select every row `i` and column `j`.
3. Compare their first elements.
4. If they are different, skip this pair.
5. Otherwise, traverse the entire row and column simultaneously.
6. If any element differs, stop checking that pair.
7. If all elements match, increment `count`.
8. Return `count`.

---

# Dry Run

Input:

```text
grid = [[1,2],
        [2,1]]
```

### Row 0 and Column 0

Row:

```text
[1,2]
```

Column:

```text
[1,2]
```

Compare:

```text
1 == 1
2 == 2
```

They are equal.

```text
count = 1
```

---

### Row 0 and Column 1

Row:

```text
[1,2]
```

Column:

```text
[2,1]
```

First elements:

```text
1 != 2
```

Skip.

---

### Row 1 and Column 0

Row:

```text
[2,1]
```

Column:

```text
[1,2]
```

First elements:

```text
2 != 1
```

Skip.

---

### Row 1 and Column 1

Row:

```text
[2,1]
```

Column:

```text
[2,1]
```

Compare:

```text
2 == 2
1 == 1
```

They are equal.

```text
count = 2
```

Final answer:

```text
2
```

---

# Understanding the Code

## Matrix Size

```java
int m = grid.length;
```

Since `grid` is an `n x n` matrix, `m` represents both the number of rows and columns.

---

## Select Row and Column

```java
for(int i=0; i<m; i++){
    for(int j=0; j<m; j++){
```

Here:

```text
i → row index
j → column index
```

Every possible row-column pair is checked.

---

## Quick First Element Check

```java
if(grid[i][0] == grid[0][j]){
```

The first element of row `i` is:

```text
grid[i][0]
```

The first element of column `j` is:

```text
grid[0][j]
```

If these are different, the row and column cannot be equal.

---

## Compare Complete Row and Column

```java
int n = 0;

while(n<m){
    if(grid[i][n] != grid[n][j]){
        break;
    }
    n++;
}
```

Here:

```text
grid[i][n]
```

represents an element from the selected row.

And:

```text
grid[n][j]
```

represents the corresponding element from the selected column.

---

## Count Equal Pairs

```java
if(n == m){
    count++;
}
```

If `n` reaches the matrix size, every element matched.

Therefore, the row and column are equal.

---

# Why This Works

For a row and column to be equal, every corresponding element must match.

For row `i` and column `j`:

```text
grid[i][0] == grid[0][j]
grid[i][1] == grid[1][j]
grid[i][2] == grid[2][j]
...
grid[i][n-1] == grid[n-1][j]
```

The inner loop checks exactly these conditions.

Therefore, every valid pair is counted exactly once.

---

# Complexity Analysis

### Time Complexity

There are:

```text
n × n
```

possible row-column pairs.

For each pair, we may compare up to `n` elements.

Therefore:

```text
O(n³)
```

---

### Space Complexity

Only a few variables are used.

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public int equalPairs(int[][] grid) {

        int count = 0;
        int m = grid.length;

        for(int i = 0; i < m; i++){

            for(int j = 0; j < m; j++){

                if(grid[i][0] == grid[0][j]){

                    int n = 0;

                    while(n < m){

                        if(grid[i][n] != grid[n][j]){
                            break;
                        }

                        n++;
                    }

                    if(n == m){
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
```

---

# Key Concepts

- 2D Array
- Matrix Traversal
- Row Traversal
- Column Traversal
- Nested Loops
- Element Comparison

---

# Constraints

- `1 <= n <= 200`
- `n == grid.length`
- `n == grid[i].length`
- `1 <= grid[i][j] <= 10^5`

---

# Learning Outcome

This problem demonstrates how to compare **rows and columns of a matrix** using nested loops.

The main idea is:

```text
Select a row
      ↓
Select a column
      ↓
Compare corresponding elements
      ↓
If all elements match
      ↓
Increment count
```

The solution uses:

```text
Time  → O(n³)
Space → O(1)
```

and directly works on the given matrix without using any extra data structure.
