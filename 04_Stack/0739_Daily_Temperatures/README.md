# 739. Daily Temperatures

> **Difficulty:** Medium  
> **Topics:** Array, Stack, Monotonic Stack

---

## Problem Statement

Given an array `temperatures` representing the daily temperatures, return an array `answer` such that:

```text
answer[i]
```

is the number of days you have to wait after day `i` to get a warmer temperature.

If there is no future day with a warmer temperature, return:

```text
0
```

---

## Example 1

### Input

```text
temperatures = [73,74,75,71,69,72,76,73]
```

### Output

```text
[1,1,4,2,1,1,0,0]
```

### Explanation

For each day:

```text
73 → 74 = 1 day
74 → 75 = 1 day
75 → 76 = 4 days
71 → 72 = 2 days
69 → 72 = 1 day
72 → 76 = 1 day
76 → no warmer day = 0
73 → no warmer day = 0
```

---

## Example 2

### Input

```text
temperatures = [30,40,50,60]
```

### Output

```text
[1,1,1,0]
```

---

## Example 3

### Input

```text
temperatures = [30,60,90]
```

### Output

```text
[1,1,0]
```

---

# Approach

Use a **Monotonic Stack**.

The main idea is to traverse the array from **right to left**.

For every temperature, we need to find the nearest future day having a strictly greater temperature.

Instead of checking all future temperatures, maintain a stack containing indices of useful future temperatures.

The stack is maintained so that temperatures are in **strictly decreasing order** from the top perspective.

---

# Algorithm

1. Create a stack to store indices.
2. Create an answer array of the same size.
3. Traverse the temperatures from right to left.
4. For every index `i`:
   - Remove indices from the stack while their temperature is less than or equal to the current temperature.
   - If the stack is empty, there is no warmer future day:
     ```text
     answer[i] = 0
     ```
   - Otherwise, the top of the stack is the nearest warmer day:
     ```text
     answer[i] = stack.peek() - i
     ```
5. Push the current index into the stack.
6. Return the answer array.

---

# Why Do We Traverse from Right to Left?

For a particular day, we only care about temperatures **after** that day.

For example:

```text
[73, 74, 75, 71, 69, 72, 76, 73]
                         ↑
```

When processing from right to left, all future temperatures are already available in the stack.

Therefore, we can efficiently find the next warmer temperature.

---

# Dry Run

Input:

```text
temperatures = [73,74,75,71,69,72,76,73]
```

We start from the last index.

### Index 7

Temperature:

```text
73
```

Stack is empty.

Therefore:

```text
answer[7] = 0
```

Push index `7`.

```text
Stack = [7]
```

---

### Index 6

Temperature:

```text
76
```

Top temperature is:

```text
73
```

Since:

```text
73 <= 76
```

remove index `7`.

Stack becomes empty.

Therefore:

```text
answer[6] = 0
```

Push `6`.

```text
Stack = [6]
```

---

### Index 5

Temperature:

```text
72
```

Top temperature:

```text
76
```

Since `76 > 72`, we found a warmer day.

```text
answer[5] = 6 - 5
          = 1
```

Push `5`.

```text
Stack = [6,5]
```

---

### Index 4

Temperature:

```text
69
```

Top temperature:

```text
72
```

Since `72 > 69`:

```text
answer[4] = 5 - 4
          = 1
```

Push `4`.

---

Continuing the same process gives:

```text
answer = [1,1,4,2,1,1,0,0]
```

---

# Understanding the Code

## Create Stack

```java
Stack<Integer> s = new Stack<>();
```

The stack stores **indices**, not temperatures.

This is important because we need the index difference:

```text
warmerIndex - currentIndex
```

---

## Traverse from Right to Left

```java
for(int i=n-1; i>=0; i--){
```

Future temperatures are processed first.

---

## Remove Useless Temperatures

```java
while(!s.isEmpty() && temperatures[s.peek()] <= temperatures[i]){
    s.pop();
}
```

If the temperature at the top of the stack is less than or equal to the current temperature, it can never be the answer for the current day.

It is removed.

For example:

```text
current = 75
stack top = 74
```

`74` cannot be a warmer day for `75`.

Therefore:

```text
pop()
```

---

## Find the Next Warmer Day

```java
if(s.isEmpty()){
    arr[i] = 0;
}
else{
    arr[i] = s.peek() - i;
}
```

If the stack is empty:

```text
No warmer day exists.
```

Otherwise:

```text
s.peek()
```

is the nearest future day with a warmer temperature.

Therefore:

```text
answer[i] = warmerIndex - currentIndex
```

---

## Push Current Index

```java
s.push(i);
```

The current day becomes a possible warmer day for earlier temperatures.

---

# Why Do We Pop `<=`?

The problem asks for a **strictly warmer** temperature.

Therefore, an equal temperature is not useful.

For example:

```text
[70,70,75]
```

For the first `70`, the second `70` is not warmer.

So when processing `70`, we remove:

```java
temperatures[s.peek()] <= temperatures[i]
```

This ensures the stack contains only temperatures that can actually be warmer.

---

# Why Store Indices Instead of Values?

Suppose:

```text
temperatures = [73,74,75]
```

For `73`, the warmer temperature is `74`.

But the answer is not `74`.

The answer is:

```text
index difference
1 - 0 = 1
```

Therefore, we store indices:

```text
Stack → [indices]
```

rather than only temperatures.

---

# Monotonic Stack Pattern

This problem is a classic **Monotonic Stack** problem.

The general pattern is:

```text
Traverse from right
        ↓
Remove elements that cannot be useful
        ↓
Stack top becomes the next useful element
        ↓
Calculate answer
        ↓
Push current index
```

This technique is useful for problems involving:

- Next Greater Element
- Next Smaller Element
- Daily Temperatures
- Stock Span
- Largest Rectangle in Histogram

---

# Complexity Analysis

### Time Complexity

Every index is:

- Pushed into the stack once.
- Popped from the stack at most once.

Therefore:

```text
O(n)
```

---

### Space Complexity

In the worst case, the stack can contain all indices.

Therefore:

```text
O(n)
```

The answer array also requires `O(n)` space.

---

# Java Solution

```java
class Solution {

    public int[] dailyTemperatures(int[] temperatures) {

        int n = temperatures.length;

        Stack<Integer> s = new Stack<>();

        int arr[] = new int[n];

        for(int i = n - 1; i >= 0; i--){

            while(!s.isEmpty() &&
                  temperatures[s.peek()] <= temperatures[i]){

                s.pop();
            }

            if(s.isEmpty()){
                arr[i] = 0;
            }
            else{
                arr[i] = s.peek() - i;
            }

            s.push(i);
        }

        return arr;
    }
}
```

---

# Key Concepts

- Array
- Stack
- Monotonic Stack
- Next Greater Element
- Right-to-Left Traversal
- Index Tracking
- Greedy Removal

---

# Constraints

- `1 <= temperatures.length <= 10^5`
- `30 <= temperatures[i] <= 100`

---

# Learning Outcome

This problem demonstrates how a **Monotonic Stack** can reduce a problem that would normally require nested loops from `O(n²)` to `O(n)`.

The important pattern is:

```java
while(!stack.isEmpty() &&
      temperatures[stack.peek()] <= temperatures[i]){
    stack.pop();
}
```

After removing all useless temperatures:

```java
stack.peek()
```

gives the nearest future day with a warmer temperature.

The solution uses:

```text
Time  → O(n)
Space → O(n)
```

The key idea to remember is:

```text
Next Greater Element
        ↓
Monotonic Stack
        ↓
Store Indices
        ↓
Find Distance
```