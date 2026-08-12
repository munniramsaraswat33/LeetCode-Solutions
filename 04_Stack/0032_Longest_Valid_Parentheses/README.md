# 32. Longest Valid Parentheses

> **Difficulty:** Hard  
> **Topics:** String, Stack, Dynamic Programming

---

## Problem Statement

Given a string containing only:

```text
'(' and ')'
```

return the length of the **longest valid (well-formed) parentheses substring**.

A valid parentheses substring must have properly matched opening and closing parentheses.

---

## Example 1

### Input

```text
s = "(()"
```

### Output

```text
2
```

### Explanation

The longest valid substring is:

```text
"()"
```

Its length is:

```text
2
```

---

## Example 2

### Input

```text
s = ")()())"
```

### Output

```text
4
```

### Explanation

The longest valid substring is:

```text
"()()"
```

Its length is:

```text
4
```

---

## Example 3

### Input

```text
s = ""
```

### Output

```text
0
```

There are no parentheses, so the longest valid substring has length `0`.

---

# Approach

This solution uses a **Stack of indices**.

Instead of storing the parentheses characters, we store their **indices**.

The key idea is to keep track of the position immediately before the current valid substring.

---

# Why Push `-1` Initially?

We start with:

```java
stack.push(-1);
```

The `-1` acts as a **base index**.

For example:

```text
s = "()"
```

Indices:

```text
0 1
( )
```

Initially:

```text
stack = [-1]
```

After processing `(`:

```text
stack = [-1, 0]
```

After processing `)`:

```text
stack = [-1]
```

Now:

```text
length = 1 - (-1)
       = 2
```

Therefore, the valid substring has length `2`.

---

# Algorithm

For every character at index `j`:

### Case 1: Opening Parenthesis

If:

```text
s[j] == '('
```

push its index:

```java
stack.push(j);
```

---

### Case 2: Closing Parenthesis

If:

```text
s[j] == ')'
```

first pop the matching opening parenthesis:

```java
stack.pop();
```

Then there are two possibilities.

### Stack Becomes Empty

If:

```java
stack.isEmpty()
```

there is no valid opening parenthesis available to match the current `)`.

So the current index becomes the new boundary:

```java
stack.push(j);
```

---

### Stack Is Not Empty

If the stack still contains an index, we have a valid parentheses substring ending at `j`.

Its length is:

```text
j - stack.peek()
```

Update:

```java
ans = Math.max(ans, j - stack.peek());
```

---

# Dry Run

### Input

```text
s = ")()())"
```

Indices:

```text
0 1 2 3 4 5
) ( ) ( ) )
```

Initially:

```text
stack = [-1]
ans = 0
```

### Index 0: `)`

Pop:

```text
stack = []
```

Stack is empty, so push current index:

```text
stack = [0]
```

---

### Index 1: `(`

Push:

```text
stack = [0,1]
```

---

### Index 2: `)`

Pop:

```text
stack = [0]
```

Calculate:

```text
2 - 0 = 2
```

So:

```text
ans = 2
```

---

### Index 3: `(`

Push:

```text
stack = [0,3]
```

---

### Index 4: `)`

Pop:

```text
stack = [0]
```

Calculate:

```text
4 - 0 = 4
```

Update:

```text
ans = 4
```

---

### Index 5: `)`

Pop:

```text
stack = []
```

Stack is empty, so:

```text
stack.push(5)
```

Final:

```text
ans = 4
```

Therefore:

```text
"()()"
```

is the longest valid substring.

---

# Important Idea

The most important expression in this solution is:

```java
j - stack.peek()
```

Why does this give the length?

The stack contains the index of the position **before the current valid substring**.

For example:

```text
) ( ) ( )
0 1 2 3 4
```

At index `4`:

```text
stack.peek() = 0
```

Therefore:

```text
4 - 0 = 4
```

which represents:

```text
indices 1 to 4
```

or:

```text
"()()"
```

---

# Why Do We Reset the Boundary?

Consider:

```text
")()"
```

At index `0`, we encounter:

```text
')'
```

There is no matching `(` before it.

Therefore, index `0` cannot be part of any valid substring that starts after it.

So we store:

```text
stack.push(0)
```

This makes index `0` the new boundary.

When we later reach index `2`:

```text
2 - 0 = 2
```

giving the valid substring:

```text
"()"
```

---

# Complexity Analysis

Let `n` be the length of `s`.

### Time Complexity

Each character is pushed and popped from the stack at most once.

Therefore:

```text
O(n)
```

---

### Space Complexity

In the worst case, the stack can contain all opening parentheses:

```text
O(n)
```

---

# Java Solution

```java
class Solution {

    public int longestValidParentheses(String s) {

        Stack<Integer> stack = new Stack<>();

        // Base index
        stack.push(-1);

        int ans = 0;

        for (int j = 0; j < s.length(); j++) {

            if (s.charAt(j) == '(') {

                stack.push(j);

            } else {

                stack.pop();

                if (stack.isEmpty()) {

                    // Current ')' becomes the new boundary
                    stack.push(j);

                } else {

                    // Calculate valid substring length
                    ans = Math.max(ans, j - stack.peek());
                }
            }
        }

        return ans;
    }
}
```

---

# Key Concepts

- Stack
- Parentheses Matching
- Index Tracking
- String Traversal
- Boundary Tracking

---

# Constraints

- `0 <= s.length <= 3 × 10⁴`
- `s[i]` is either `'('` or `')'`.

---

# Learning Outcome

This problem is an important example of using a **stack of indices** instead of a stack of characters.

The three most important ideas are:

### 1. Start with a Base Index

```java
stack.push(-1);
```

### 2. Push Opening Parenthesis Indices

```java
stack.push(j);
```

### 3. Calculate Valid Length

```java
j - stack.peek()
```

When the stack becomes empty after processing a closing parenthesis, the current index becomes a new boundary:

```java
stack.push(j);
```

This gives an efficient:

```text
Time:  O(n)
Space: O(n)
```

solution.