# 20. Valid Parentheses

> **Difficulty:** Easy  
> **Topics:** String, Stack

---

## Problem Statement

Given a string `s` containing only:

```text
( ) { } [ ]
```

determine whether the string contains valid parentheses.

A string is valid if:

1. Every opening bracket has a corresponding closing bracket.
2. Brackets are closed using the same type.
3. Brackets are closed in the correct order.

---

## Example 1

### Input

```text
s = "()"
```

### Output

```text
true
```

---

## Example 2

### Input

```text
s = "()[]{}"
```

### Output

```text
true
```

---

## Example 3

### Input

```text
s = "(]"
```

### Output

```text
false
```

`(` cannot be closed by `]`.

---

## Example 4

### Input

```text
s = "([])"
```

### Output

```text
true
```

The brackets are correctly nested:

```text
(
    [
    ]
)
```

---

## Example 5

### Input

```text
s = "([)]"
```

### Output

```text
false
```

Although all brackets have matching types, they are not closed in the correct order.

---

# Approach

This problem is a classic application of a **Stack**.

A stack follows:

```text
LIFO
Last In → First Out
```

This is exactly what we need because the most recently opened bracket must be closed first.

For example:

```text
([{}])
```

When we encounter:

```text
(
[
{
```

we push them into the stack:

```text
Top
 ↓
{
[
(
```

When `}` appears, the top of the stack must be `{`.

Then:

```text
}
```

matches `{`, so we pop it.

This continues until the stack becomes empty.

---

# Algorithm

For every character in the string:

### Case 1: Opening bracket

If the character is:

```text
(
{
[
```

push it into the stack.

```java
st.push(ch);
```

---

### Case 2: Closing bracket

If the character is:

```text
)
}
]
```

first check whether the stack is empty.

If it is empty:

```text
false
```

because there is no opening bracket to match it.

Otherwise, check whether the top opening bracket matches the current closing bracket.

Valid pairs are:

```text
( → )
{ → }
[ → ]
```

If they match:

```java
st.pop();
```

Otherwise:

```text
return false;
```

---

### Final Check

After processing the complete string, the stack must be empty.

If it is empty:

```text
true
```

Otherwise:

```text
false
```

because some opening brackets were never closed.

---

# Java Solution

```java
class Solution {
    public boolean isValid(String s) {

        Stack<Character> st = new Stack<>();

        for(int i = 0; i < s.length(); i++){

            char ch = s.charAt(i);

            // Opening bracket
            if(ch == '(' || ch == '{' || ch == '['){
                st.push(ch);
            }

            // Closing bracket
            else{

                // No opening bracket available
                if(st.isEmpty()){
                    return false;
                }

                // Check matching pair
                if((st.peek() == '(' && ch == ')') ||
                   (st.peek() == '{' && ch == '}') ||
                   (st.peek() == '[' && ch == ']')){

                    st.pop();

                }else{
                    return false;
                }
            }
        }

        return st.isEmpty();
    }
}
```

---

# Dry Run

Consider:

```text
s = "([])"
```

### Step 1

Character:

```text
(
```

Opening bracket → push.

```text
Stack: (
```

---

### Step 2

Character:

```text
[
```

Opening bracket → push.

```text
Stack:
[
(
```

---

### Step 3

Character:

```text
]
```

Top is:

```text
[
```

`[` matches `]`.

Pop:

```text
Stack:
(
```

---

### Step 4

Character:

```text
)
```

Top is:

```text
(
```

`(` matches `)`.

Pop:

```text
Stack: empty
```

At the end:

```text
stack.isEmpty() == true
```

Therefore:

```text
true
```

---

# Invalid Example

Consider:

```text
s = "([)]"
```

### Step 1

```text
(
```

Stack:

```text
(
```

### Step 2

```text
[
```

Stack:

```text
[
(
```

### Step 3

```text
)
```

Top of stack is:

```text
[
```

But `[` does not match `)`.

Therefore:

```text
false
```

---

# Why Stack Works

The important rule is:

> The most recently opened bracket must be closed first.

For:

```text
([{}])
```

Opening order:

```text
(
[
{
```

Closing order must be:

```text
}
]
)
```

This is exactly **Last In First Out**, which is the behavior of a Stack.

---

# Complexity Analysis

Let:

```text
n = s.length()
```

We visit every character exactly once.

### Time Complexity

```text
O(n)
```

### Space Complexity

In the worst case, all characters are opening brackets:

```text
(((((((
```

The stack can contain `n` characters.

Therefore:

```text
O(n)
```

---

# Important Edge Cases

### Empty stack before closing bracket

```text
s = ")"
```

Return:

```text
false
```

---

### Unclosed opening bracket

```text
s = "((("
```

Stack is not empty at the end.

Return:

```text
false
```

---

### Different bracket types

```text
s = "(]"
```

Return:

```text
false
```

---

### Wrong nesting

```text
s = "([)]"
```

Return:

```text
false
```

---

### Correct nesting

```text
s = "([]{})"
```

Return:

```text
true
```

---

# Key Pattern to Remember

Whenever a problem says:

- matching brackets
- nested brackets
- last opened, first closed
- balanced parentheses

think:

```text
STACK
```

Basic pattern:

```text
Opening bracket
      ↓
    push

Closing bracket
      ↓
Check top
      ↓
   matching?
   /       \
 yes       no
 ↓          ↓
pop       false
```

At the end:

```text
Empty Stack → Valid
Non-empty   → Invalid
```

---

## Your Solution

Your solution uses the correct **Stack + matching pair** approach.

The final:

```java
if(st.isEmpty()){
    return true;
}else{
    return false;
}
```

can simply be written as:

```java
return st.isEmpty();
```

So your solution can be slightly cleaner without changing the logic.