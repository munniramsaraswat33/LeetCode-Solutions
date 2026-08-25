# 649. Dota2 Senate

> **Difficulty:** Medium  
> **Topics:** String, Queue, Simulation, Greedy

---

## Problem Statement

In the Dota2 senate, there are two parties:

- **Radiant** → represented by `R`
- **Dire** → represented by `D`

The senate members are arranged in a given order.

Each senator can exercise one of two rights:

1. Ban one senator from the opposite party.
2. Declare victory if all remaining senators belong to their own party.

Each senator acts in the given order.

A senator who has already been banned cannot use their right.

The process continues until only one party remains.

Return:

```text
"Radiant"
```

if Radiant wins, otherwise return:

```text
"Dire"
```

---

## Example 1

### Input

```text
senate = "RD"
```

### Output

```text
"Radiant"
```

### Explanation

The Radiant senator acts first and bans the Dire senator.

Only Radiant remains.

Therefore:

```text
Radiant
```

wins.

---

## Example 2

### Input

```text
senate = "RDD"
```

### Output

```text
"Dire"
```

### Explanation

The first Radiant senator bans one Dire senator.

The remaining Dire senator can then ban the Radiant senator.

Only Dire remains.

Therefore:

```text
Dire
```

wins.

---

## Example 3

### Input

```text
senate = "RD"
```

### Output

```text
"Radiant"
```

The Radiant senator appears before the Dire senator, so Radiant gets the first opportunity to ban the opposing senator.

---

# Approach

Use **two Queues**:

```text
rqueue → positions of Radiant senators
dqueue → positions of Dire senators
```

The queue stores the original index of every senator.

For example:

```text
senate = "RDD"
```

Initially:

```text
rqueue = [0]
dqueue = [1,2]
```

The senator with the smaller index gets to act first.

When a senator bans an opponent, that opponent is removed from the corresponding queue.

The winning senator is placed back into the queue with:

```text
index + n
```

This represents the senator getting another turn in the next round.

---

# Why Use Queue?

A queue follows **FIFO (First In, First Out)**.

This is exactly what the problem requires because senators act according to their order.

For example:

```text
rqueue = [0,3,7]
```

The senator at index `0` acts first.

After acting, if they survive, they are placed at:

```text
0 + n
```

This moves them to the next round.

---

# Algorithm

1. Create two queues:
   ```text
   rqueue
   dqueue
   ```
2. Traverse the senate string.
3. Store the index of every `R` in `rqueue`.
4. Store the index of every `D` in `dqueue`.
5. While both queues are not empty:
   - Remove the first Radiant index.
   - Remove the first Dire index.
6. Compare their positions.
7. If:
   ```text
   r < d
   ```
   Radiant acts first and survives.
8. Put Radiant back with:
   ```text
   r + n
   ```
9. Otherwise, Dire survives and is placed back with:
   ```text
   d + n
   ```
10. Continue until one queue becomes empty.
11. If `dqueue` is empty, return `"Radiant"`.
12. Otherwise, return `"Dire"`.

---

# Dry Run

Input:

```text
senate = "RDD"
```

### Step 1: Create Queues

Indices:

```text
R → 0
D → 1
D → 2
```

Therefore:

```text
rqueue = [0]
dqueue = [1,2]
```

---

### Step 2: First Round

Remove:

```text
r = 0
d = 1
```

Since:

```text
0 < 1
```

Radiant acts first.

Radiant bans the Dire senator at index `1`.

Radiant survives and is placed into the next round:

```text
rqueue = [0 + 3]
       = [3]
```

Dire queue:

```text
dqueue = [2]
```

---

### Step 3: Second Round

Remove:

```text
r = 3
d = 2
```

Now:

```text
3 > 2
```

So Dire acts first.

Dire bans Radiant.

Dire survives and moves to the next round:

```text
dqueue = [2 + 3]
       = [5]
```

Radiant queue:

```text
rqueue = []
```

Since Radiant's queue is empty:

```text
Dire wins
```

Answer:

```text
"Dire"
```

---

# Understanding the Code

## Create Queues

```java
Queue<Integer> rqueue = new LinkedList<>();
Queue<Integer> dqueue = new LinkedList<>();
```

The queues store the positions of Radiant and Dire senators.

---

## Store Senator Positions

```java
for(int i = 0; i < n; i++){
    if(senate.charAt(i) == 'R'){
        rqueue.offer(i);
    }
    else{
        dqueue.offer(i);
    }
}
```

For each senator:

```text
R → rqueue
D → dqueue
```

---

## Process the Senators

```java
while(!rqueue.isEmpty() && !dqueue.isEmpty()){
```

As long as both parties have senators, the game continues.

---

## Get the Next Senators

```java
int r = rqueue.poll();
int d = dqueue.poll();
```

`poll()` removes and returns the front element.

Because these are queues, the earliest available senators are processed first.

---

## Radiant Acts First

```java
if(r < d){
    rqueue.offer(r+n);
}
```

If:

```text
r < d
```

Radiant appears earlier in the current round.

The Dire senator is banned.

Radiant survives and gets another turn.

So we add:

```text
r + n
```

to the Radiant queue.

---

## Dire Acts First

```java
else{
    dqueue.offer(d+n);
}
```

If:

```text
d < r
```

Dire acts first.

The Radiant senator is banned.

Dire survives and is added to the next round using:

```text
d + n
```

---

## Determine Winner

```java
return dqueue.isEmpty() ? "Radiant" : "Dire";
```

If no Dire senators remain:

```text
Radiant
```

wins.

Otherwise:

```text
Dire
```

wins.

---

# Important Idea: `index + n`

The most important trick in this solution is:

```java
rqueue.offer(r+n);
```

or:

```java
dqueue.offer(d+n);
```

Suppose:

```text
n = 5
```

and a Radiant senator originally has:

```text
index = 1
```

After surviving the current round:

```text
1 + 5 = 6
```

The new index represents that senator acting in the next cycle.

So instead of actually creating multiple rounds, we can simulate them using increasing indices.

---

# Queue Example

Suppose:

```text
rqueue = [0,4]
dqueue = [1,3]
```

Compare the front elements:

```text
R = 0
D = 1
```

Radiant acts first.

Radiant survives:

```text
rqueue = [4, 0+n]
```

The next senator in the queue is now processed.

This naturally maintains the correct order of turns.

---

# Why This Is Greedy?

At every step, the senator who gets to act first immediately bans the opponent who would otherwise act first.

Therefore:

```text
Earliest available senator
        ↓
Acts first
        ↓
Bans opposite party senator
        ↓
Survives for next round
```

This greedy decision leads to the correct winner.

---

# Complexity Analysis

Let `n` be the length of the senate string.

### Time Complexity

Each senator can be processed and reinserted across rounds.

The total number of queue operations is bounded linearly with respect to the number of senators over the simulation.

Therefore:

```text
O(n)
```

---

### Space Complexity

The two queues store senator positions.

In the worst case:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public String predictPartyVictory(String senate) {

        int n = senate.length();

        Queue<Integer> rqueue = new LinkedList<>();
        Queue<Integer> dqueue = new LinkedList<>();

        for(int i = 0; i < n; i++){

            if(senate.charAt(i) == 'R'){
                rqueue.offer(i);
            }
            else{
                dqueue.offer(i);
            }
        }

        while(!rqueue.isEmpty() && !dqueue.isEmpty()){

            int r = rqueue.poll();
            int d = dqueue.poll();

            if(r < d){
                rqueue.offer(r + n);
            }
            else{
                dqueue.offer(d + n);
            }
        }

        return dqueue.isEmpty() ? "Radiant" : "Dire";
    }
}
```

---

# Key Concepts

- String
- Queue
- FIFO
- Simulation
- Greedy Approach
- Circular Processing
- Index Tracking

---

# Constraints

- `1 <= senate.length <= 10^4`
- `senate[i]` is either `'R'` or `'D'`

---

# Learning Outcome

This problem demonstrates how **Queue + Greedy Simulation** can be used to model a process where elements act in a fixed order and surviving elements get another turn.

The main idea is:

```text
Store positions
      ↓
Compare front of both queues
      ↓
Earlier senator survives
      ↓
Opponent is removed
      ↓
Winner moves to next round using index + n
      ↓
Continue until one queue is empty
```

The most important code is:

```java
if(r < d){
    rqueue.offer(r + n);
}
else{
    dqueue.offer(d + n);
}
```

This efficiently simulates the circular order of senators.

The solution uses:

```text
Time  → O(n)
Space → O(n)
```