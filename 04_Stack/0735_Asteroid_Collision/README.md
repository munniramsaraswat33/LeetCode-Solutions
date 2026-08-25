# 735. Asteroid Collision

> **Difficulty:** Medium  
> **Topics:** Array, Stack, Simulation

---

## Problem Statement

We are given an array `asteroids` where each integer represents an asteroid.

- The **absolute value** represents the size of the asteroid.
- The **sign** represents its direction.
  - Positive → moving to the right.
  - Negative → moving to the left.

All asteroids move at the same speed.

When two asteroids collide:

- The smaller asteroid explodes.
- If both have the same size, both explode.
- Asteroids moving in the same direction never collide.

Return the state of the asteroids after all collisions.

---

## Example 1

### Input

```text
asteroids = [5,10,-5]
```

### Output

```text
[5,10]
```

### Explanation

`10` and `-5` collide.

```text
10 > 5
```

Therefore, `-5` is destroyed.

The remaining asteroids are:

```text
[5,10]
```

---

## Example 2

### Input

```text
asteroids = [8,-8]
```

### Output

```text
[]
```

### Explanation

The two asteroids have the same size:

```text
8 == 8
```

Therefore, both are destroyed.

---

## Example 3

### Input

```text
asteroids = [10,2,-5]
```

### Output

```text
[10]
```

### Explanation

First:

```text
2 and -5
```

collide.

Since:

```text
5 > 2
```

the asteroid `2` is destroyed.

Now:

```text
10 and -5
```

collide.

Since:

```text
10 > 5
```

the asteroid `-5` is destroyed.

Final result:

```text
[10]
```

---

# Approach

Use a **Stack** to simulate the collisions.

The important observation is that a collision can happen only when:

```text
stack.peek() > 0
```

and the current asteroid is moving left:

```text
current < 0
```

For example:

```text
[5, 10, -5]
```

Here:

```text
10 → moving right
-5 → moving left
```

so they can collide.

But:

```text
[-5, 10]
```

cannot collide because they are moving away from each other.

---

# Algorithm

1. Create an empty stack.
2. Traverse every asteroid.
3. If the asteroid is positive:
   - Push it into the stack.
4. If the asteroid is negative:
   - Compare it with the top positive asteroid.
   - While the top asteroid is smaller, remove it.
   - If the stack is empty or the top asteroid is negative, push the current asteroid.
   - If both asteroids have the same size, remove the top asteroid.
5. Convert the stack into an array.
6. Return the result.

---

# Dry Run

Input:

```text
asteroids = [5,10,-5]
```

### Step 1

Current asteroid:

```text
5
```

It is positive, so push it.

```text
Stack = [5]
```

---

### Step 2

Current asteroid:

```text
10
```

It is positive.

```text
Stack = [5,10]
```

---

### Step 3

Current asteroid:

```text
-5
```

It is moving left.

Top of stack:

```text
10
```

Both are moving toward each other.

Compare:

```text
10 > 5
```

So `-5` is destroyed.

```text
Stack = [5,10]
```

Final result:

```text
[5,10]
```

---

# Dry Run with Multiple Collisions

Input:

```text
asteroids = [10,2,-5]
```

### Process `10`

```text
Stack = [10]
```

### Process `2`

```text
Stack = [10,2]
```

### Process `-5`

Top:

```text
2
```

Compare:

```text
2 < 5
```

So remove `2`.

```text
Stack = [10]
```

Now compare `-5` with `10`.

```text
10 > 5
```

So `-5` is destroyed.

Final:

```text
[10]
```

---

# Understanding the Code

## Create Stack

```java
Stack<Integer> stack = new Stack<>();
```

The stack stores asteroids that are still alive.

---

## Traverse the Asteroids

```java
for(int a : asteroids){
```

Process every asteroid one by one.

---

## Positive Asteroid

```java
if(a > 0){
    stack.push(a);
}
```

A positive asteroid moves right.

It can potentially collide with a future negative asteroid, so we store it.

---

## Negative Asteroid

```java
else{
```

A negative asteroid moves left.

It may collide with a positive asteroid already present on the stack.

---

## Remove Smaller Asteroids

```java
while(!stack.isEmpty() &&
      stack.peek() > 0 &&
      stack.peek() < -a){
    stack.pop();
}
```

Suppose:

```text
stack.peek() = 3
a = -5
```

Then:

```text
3 < 5
```

So the positive asteroid `3` is destroyed.

We continue checking because the new negative asteroid may collide with another asteroid behind it.

---

## Current Asteroid Survives

```java
if(stack.isEmpty() || stack.peek() < 0){
    stack.push(a);
}
```

If:

- the stack is empty, or
- the top asteroid is negative,

then no collision is possible.

So the current asteroid survives.

---

## Equal Size Collision

```java
if(stack.peek() == -a){
    stack.pop();
}
```

If:

```text
5
```

and:

```text
-5
```

collide, both are destroyed.

The current asteroid is not pushed, and the positive asteroid is removed.

---

# Why Use a Stack?

A stack is useful because only the **most recent surviving asteroid** can collide with the current asteroid.

For example:

```text
[5,10,-20]
```

When `-20` arrives, it first interacts with:

```text
10
```

If `10` is destroyed, it then interacts with:

```text
5
```

This is exactly the **Last In, First Out (LIFO)** behavior of a stack.

---

# Important Collision Condition

A collision happens only when:

```text
positive asteroid + negative asteroid
```

For example:

```text
[5,-3]    → collision
[5,3]     → no collision
[-5,3]    → no collision
[-5,-3]   → no collision
```

Therefore, the important condition is:

```java
stack.peek() > 0 && a < 0
```

---

# Collision Cases

Suppose:

```text
positive = 10
negative = -5
```

### Case 1: Positive is Larger

```text
10 > 5
```

Negative asteroid is destroyed.

---

### Case 2: Negative is Larger

```text
10 < 15
```

Positive asteroid is destroyed.

The negative asteroid may then collide with another asteroid.

---

### Case 3: Same Size

```text
10 == 10
```

Both asteroids are destroyed.

---

# Convert Stack to Array

```java
int[] res = new int[stack.size()];
```

Create the result array.

Then:

```java
int i = res.length - 1;

while(!stack.isEmpty()){
    res[i--] = stack.pop();
}
```

Since a stack removes elements from the top, we fill the result array from the end to preserve the original order.

---

# Complexity Analysis

### Time Complexity

Every asteroid is:

- Pushed at most once.
- Popped at most once.

Therefore:

```text
O(n)
```

---

### Space Complexity

In the worst case, all asteroids can remain in the stack.

Therefore:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stack = new Stack<>();

        for(int a : asteroids){

            if(a > 0){
                stack.push(a);
            }
            else{

                while(!stack.isEmpty() &&
                      stack.peek() > 0 &&
                      stack.peek() < -a){

                    stack.pop();
                }

                if(stack.isEmpty() || stack.peek() < 0){
                    stack.push(a);
                }

                if(!stack.isEmpty() && stack.peek() == -a){
                    stack.pop();
                }
            }
        }

        int[] res = new int[stack.size()];

        int i = res.length - 1;

        while(!stack.isEmpty()){
            res[i--] = stack.pop();
        }

        return res;
    }
}
```

---

# Key Concepts

- Array
- Stack
- LIFO
- Simulation
- Collision Handling
- Monotonic-like Stack Processing
- Push and Pop

---

# Constraints

- `2 <= asteroids.length <= 10^4`
- `-1000 <= asteroids[i] <= 1000`
- `asteroids[i] != 0`
- Asteroids have unique positions based on their order in the array.

---

# Learning Outcome

This problem demonstrates how a **Stack** can be used to simulate objects interacting with each other.

The main idea is:

```text
Process asteroid
      ↓
Positive → Push
      ↓
Negative → Check stack
      ↓
Smaller positive → Pop
      ↓
Equal size → Both destroyed
      ↓
No collision → Push
```

The most important condition is:

```java
stack.peek() > 0 && a < 0
```

because only a positive asteroid moving right and a negative asteroid moving left can collide.

The solution achieves:

```text
Time  → O(n)
Space → O(n)
```
```