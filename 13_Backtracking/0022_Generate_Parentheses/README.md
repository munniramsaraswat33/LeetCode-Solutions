# 22. Generate Parentheses

> **Difficulty:** Medium  
> **Topics:** String, Backtracking

---

## Problem Statement

Given `n` pairs of parentheses, generate **all combinations of well-formed parentheses**.

A combination is valid when:

- Every opening parenthesis `(` has a matching closing parenthesis `)`.
- At no point can the number of closing parentheses exceed the number of opening parentheses.
- The total number of opening parentheses is exactly `n`.
- The total number of closing parentheses is exactly `n`.

---

## Example 1

### Input

```text
n = 3
```

### Output

```text
["((()))","(()())","(())()","()(())","()()()"]
```

There are `5` valid combinations for `n = 3`.

---

## Example 2

### Input

```text
n = 1
```

### Output

```text
["()"]
```

---

# Approach

This problem is solved using **Backtracking**.

At every step, we have two possible choices:

1. Add an opening parenthesis `(`.
2. Add a closing parenthesis `)`.

However, we cannot add them randomly.

### Rule 1: Add `(`

We can add an opening parenthesis if:

```text
open < n
```

This ensures that we never use more than `n` opening parentheses.

### Rule 2: Add `)`

We can add a closing parenthesis only if:

```text
close < open
```

This is important because we cannot close a parenthesis that has not been opened.

For example:

```text
)(
```

is invalid because the first character is a closing parenthesis.

---

# Backtracking Idea

We build the answer character by character.

For example, when:

```text
n = 3
```

we start with:

```text
""
```

Then we can add:

```text
"("
```

From there:

```text
"(("
```

and so on.

Whenever a choice is made, we explore it recursively.

After returning from the recursive call, we remove the last character:

```java
sb.deleteCharAt(sb.length() - 1);
```

This is the **backtracking step**.

---

# Algorithm

1. Create an empty result list.
2. Create an empty `StringBuilder`.
3. Start the backtracking function with:
   ```text
   open = 0
   close = 0
   ```
4. If the current string length is `2 * n`:
   - Add it to the result.
   - Return.
5. If `open < n`:
   - Add `(`.
   - Recursively continue.
   - Remove `(` using backtracking.
6. If `close < open`:
   - Add `)`.
   - Recursively continue.
   - Remove `)` using backtracking.
7. Return the result list.

---

# Dry Run

### Input

```text
n = 2
```

Start:

```text
""
open = 0
close = 0
```

### Step 1

Add `(`:

```text
"("
open = 1
close = 0
```

We can add another `(`:

```text
"(("
open = 2
close = 0
```

Now only `)` can be added:

```text
"(()"
open = 2
close = 1
```

Then:

```text
"(())"
open = 2
close = 2
```

Length is `4`, so add:

```text
"(())"
```

---

Backtrack and explore another possibility:

```text
"()"
```

Then:

```text
"()("
```

Then:

```text
"()()"
```

Add:

```text
"()()"
```

Final result:

```text
["(())","()()"]
```

---

# Important Backtracking Condition

The most important condition is:

```java
if (close < open)
```

Why?

Because the number of closing parentheses can never be greater than the number of opening parentheses.

For example:

```text
"())"
```

is invalid because:

```text
open  = 1
close = 2
```

The condition prevents this invalid state.

---

# Why `StringBuilder`?

A `StringBuilder` is used because we repeatedly:

```text
append a character
```

and then:

```text
remove the last character
```

The backtracking operations are:

```java
sb.append('(');
...
sb.deleteCharAt(sb.length() - 1);
```

This allows us to reuse the same `StringBuilder` instead of creating a new string at every recursive call.

---

# Complexity Analysis

The number of valid parentheses combinations is the `n`th **Catalan number**:

```text
Cₙ = 1 / (n + 1) × C(2n, n)
```

Since every valid combination has length `2n`, the total output size is approximately:

```text
O(Cₙ × n)
```

Therefore, the overall time complexity is:

```text
O(Cₙ × n)
```

where `Cₙ` is the `n`th Catalan number.

---

### Space Complexity

The recursion depth is at most:

```text
O(2n) = O(n)
```

The `StringBuilder` also stores at most `2n` characters.

Excluding the output list:

```text
O(n)
```

auxiliary space.

Including the output:

```text
O(Cₙ × n)
```

---

# Java Solution

```java
class Solution {

    public List<String> generateParenthesis(int n) {

        List<String> list = new ArrayList<>();

        backtrack(list, new StringBuilder(), 0, 0, n);

        return list;
    }

    public void backtrack(
        List<String> list,
        StringBuilder sb,
        int open,
        int close,
        int n
    ) {

        if (sb.length() == 2 * n) {

            list.add(sb.toString());

            return;
        }

        // Add opening parenthesis
        if (open < n) {

            sb.append('(');

            backtrack(list, sb, open + 1, close, n);

            sb.deleteCharAt(sb.length() - 1);
        }

        // Add closing parenthesis
        if (close < open) {

            sb.append(')');

            backtrack(list, sb, open, close + 1, n);

            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
```

---

# Key Concepts

- Backtracking
- Recursion
- StringBuilder
- Valid Parentheses
- Catalan Numbers
- State Space Search

---

# Constraints

- `1 <= n <= 8`

---

# Learning Outcome

This problem is one of the most important examples of **backtracking**.

The key idea is to build the answer step by step while maintaining two counts:

```text
open  → number of '(' used
close → number of ')' used
```

The two important conditions are:

```java
open < n
```

to add `(`, and:

```java
close < open
```

to add `)`.

The general backtracking pattern is:

```text
Choose
   ↓
Explore
   ↓
Undo choice
```

In this problem:

```text
append character
   ↓
recursive call
   ↓
delete character
```

This pattern is extremely useful for problems involving **combinations, permutations, subsets, and constraint-based generation**.