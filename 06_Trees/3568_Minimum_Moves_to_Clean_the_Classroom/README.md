# 3568. Minimum Moves to Clean the Classroom

**Difficulty:** Medium  
**Topics:** Array, BFS, Bit Manipulation, Grid

---

## Problem Statement

You are given a classroom represented by a 2D grid.

Each cell can contain:

- `S` → Starting position
- `L` → Litter that must be collected
- `R` → Reset cell that restores energy
- `X` → Obstacle
- `.` → Empty cell

You start at `S` with a given amount of `energy`.

Moving to an adjacent cell costs `1` unit of energy.

You can move in four directions:

```text
Up, Down, Left, Right
```

When you enter:

- `L` → the litter is collected.
- `R` → energy is restored to the initial value.
- `X` → the cell cannot be entered.

Return the minimum number of moves required to collect all litter.

If it is impossible, return `-1`.

---

## Example

### Input

```text
classroom = ["S.", "LL"]
energy = 3
```

### Output

```text
2
```

### Explanation

A possible path is:

```text
S → L → L
```

Both litter cells are collected in `2` moves.

---

# Approach

This is a **BFS + Bitmask** problem.

A BFS state contains four values:

```text
(row, column, energy, mask)
```

We need all four because reaching the same cell with different energy or different collected litter gives different possibilities.

### Bitmask

Each litter is assigned one bit.

For example, with 3 litter cells:

```text
000 → No litter collected
001 → First litter collected
101 → First and third collected
111 → All collected
```

The target mask is:

```java
(1 << litterCount) - 1
```

BFS guarantees the minimum number of moves because every move costs exactly `1`.

---

# Algorithm

1. Find the starting position `S`.
2. Find all litter cells `L` and assign each one a bit.
3. If there is no litter, return `0`.
4. Create the target mask.
5. Start BFS with:
   ```text
   (startRow, startColumn, energy, 0)
   ```
6. For every state, try four directions.
7. Ignore positions outside the grid or containing `X`.
8. Decrease energy by `1`.
9. If the new cell is `R`, restore the energy.
10. If the new cell is `L`, set its bit in the mask.
11. Store unvisited states in the queue.
12. When `mask == targetMask`, return the number of moves.
13. If BFS ends, return `-1`.

---

# Intuition

The important part is the **state**.

For example:

```text
(row=2, col=3, energy=5, mask=011)
```

is different from:

```text
(row=2, col=3, energy=1, mask=011)
```

Even though the position is the same, the first state has more energy.

Similarly:

```text
mask = 001
```

and:

```text
mask = 111
```

represent different progress.

Therefore, the visited array must track:

```text
position + energy + collected litter
```

---

# Dry Run

Suppose:

```text
classroom = [
    "S.",
    "LL"
]

energy = 3
```

There are two litter cells.

Assign:

```text
L1 → bit 0
L2 → bit 1
```

Target:

```text
11
```

Initial state:

```text
(row=0, col=0, energy=3, mask=00)
```

Move down:

```text
S → L1
```

State becomes:

```text
(row=1, col=0, energy=2, mask=01)
```

Move right:

```text
L1 → L2
```

State becomes:

```text
(row=1, col=1, energy=1, mask=11)
```

All litter is collected.

Therefore:

```text
Answer = 2
```

---

# Java Solution

```java
class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litterIndex = new int[m][n];

        for(int[] row : litterIndex){
            Arrays.fill(row, -1);
        }

        int starR = -1;
        int starC = -1;
        int litterCount = 0;

        for(int r = 0; r < m; r++){
            for(int c = 0; c < n; c++){
                char ch = classroom[r].charAt(c);

                if(ch == 'S'){
                    starR = r;
                    starC = c;
                }
                else if(ch == 'L'){
                    litterIndex[r][c] = litterCount++;
                }
            }
        }

        if(litterCount == 0){
            return 0;
        }

        int targetMask = (1 << litterCount) - 1;

        Queue<int[]> queue = new ArrayDeque<>();

        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        queue.offer(new int[]{starR, starC, energy, 0});

        int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        int moves = 0;

        while(!queue.isEmpty()){
            int size = queue.size();

            while(size-- > 0){
                int[] current = queue.poll();

                int r = current[0];
                int c = current[1];
                int currEnergy = current[2];
                int mask = current[3];

                if(mask == targetMask){
                    return moves;
                }

                if(currEnergy == 0){
                    continue;
                }

                for(int[] dir : directions){
                    int nr = r + dir[0];
                    int nc = c + dir[1];

                    if(nr < 0 || nr >= m || nc < 0 || nc >= n){
                        continue;
                    }

                    if(classroom[nr].charAt(nc) == 'X'){
                        continue;
                    }

                    int newEnergy = currEnergy - 1;
                    char cell = classroom[nr].charAt(nc);

                    if(cell == 'R'){
                        newEnergy = energy;
                    }

                    int newMask = mask;

                    if(cell == 'L'){
                        int bit = litterIndex[nr][nc];
                        newMask |= (1 << bit);
                    }

                    if(!visited[nr][nc][newEnergy][newMask]){
                        visited[nr][nc][newEnergy][newMask] = true;
                        queue.offer(
                            new int[]{nr, nc, newEnergy, newMask}
                        );
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}
```

---

# Code Explanation

### Find Start and Litter

```java
if(ch == 'S'){
    starR = r;
    starC = c;
}
else if(ch == 'L'){
    litterIndex[r][c] = litterCount++;
}
```

The starting position is stored, and every litter gets a unique bit index.

### Target Mask

```java
int targetMask = (1 << litterCount) - 1;
```

If there are 3 litter cells:

```text
targetMask = 111
```

### BFS State

```java
queue.offer(new int[]{starR, starC, energy, 0});
```

The state stores:

```text
row
column
energy
litter mask
```

### Energy

Every move costs one energy:

```java
int newEnergy = currEnergy - 1;
```

At `R`:

```java
newEnergy = energy;
```

### Collect Litter

```java
newMask |= (1 << bit);
```

This marks the litter as collected.

### Check Completion

```java
if(mask == targetMask){
    return moves;
}
```

When every litter bit is set, BFS returns the current minimum moves.

---

# Complexity Analysis

Let:

- `m` = number of rows
- `n` = number of columns
- `E` = initial energy
- `L` = number of litter cells

### Time Complexity

```text
O(m × n × E × 2^L)
```

Each state can be processed once and has at most four transitions.

### Space Complexity

```text
O(m × n × E × 2^L)
```

For the `visited` array and BFS queue.

---

# Key Concepts

- Breadth-First Search (BFS)
- Grid Traversal
- Bitmask
- State Space Search
- Multi-dimensional `visited`
- Shortest Path
- Energy/Resource Tracking

---

# Learning Outcome

- Learn how BFS can solve minimum-move problems.
- Understand how to represent multiple pieces of information as one state.
- Learn how bitmasks efficiently track collected items.
- Understand why `visited[row][column]` alone is not enough.
- Learn how to combine BFS with bit manipulation for state-space problems.