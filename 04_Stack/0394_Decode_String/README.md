# 394. Decode String

> **Difficulty:** Medium  
> **Topics:** String, Stack, Recursion

---

## Problem Statement

Given an encoded string `s`, decode it and return the decoded string.

The encoding rule is:

```text
k[encoded_string]
```

where `encoded_string` inside the square brackets is repeated exactly `k` times.

You may assume that the input string is always valid.

There are no extra spaces, and the brackets are properly matched.

---

## Example 1

### Input

```text
s = "3[a]2[bc]"
```

### Output

```text
"aaabcbc"
```

### Explanation

```text
3[a]  → "aaa"
2[bc] → "bcbc"
```

Therefore:

```text
"aaa" + "bcbc" = "aaabcbc"
```

---

## Example 2

### Input

```text
s = "3[a2[c]]"
```

### Output

```text
"accaccacc"
```

### Explanation

First decode:

```text
2[c] → "cc"
```

Therefore:

```text
a2[c] → "acc"
```

Then:

```text
3[acc] → "accaccacc"
```

---

## Example 3

### Input

```text
s = "2[abc]3[cd]ef"
```

### Output

```text
"abcabccdcdcdef"
```

### Explanation

```text
2[abc] → "abcabc"
3[cd]  → "cdcdcd"
```

Adding `ef`:

```text
"abcabc" + "cdcdcd" + "ef"
```

Result:

```text
"abcabccdcdcdef"
```

---

# Approach

Use **two Stacks**:

1. `intStack` → stores the repetition numbers.
2. `strStack` → stores characters and intermediate decoded strings.

We process the string from left to right.

### Cases

#### Digit

Build the complete number.

For example:

```text
12
```

is processed as:

```text
1 → 12
```

using:

```java
currNum = currNum * 10 + digit;
```

---

#### Opening Bracket `[`

When we find `[`, push:

- the current number into `intStack`
- `[` into `strStack`

Then reset the current number.

---

#### Closing Bracket `]`

When we find `]`:

1. Pop characters from `strStack` until `[` is found.
2. Remove `[`.
3. Get the repetition count from `intStack`.
4. Repeat the decoded string `num` times.
5. Push the resulting string back into `strStack`.

---

#### Character

If the character is a normal letter, push it into `strStack`.

---

# Algorithm

1. Create:
   ```text
   intStack
   strStack
   ```
2. Initialize:
   ```text
   currNum = 0
   ```
3. Traverse every character of `s`.
4. If the character is a digit:
   - Build `currNum`.
5. If the character is `[`:
   - Push `currNum` into `intStack`.
   - Reset `currNum`.
   - Push `[` into `strStack`.
6. If the character is `]`:
   - Build the string inside the brackets.
   - Remove `[`.
   - Pop the repetition count.
   - Repeat the string.
   - Push the decoded string back.
7. Otherwise, push the character into `strStack`.
8. Finally, combine everything remaining in `strStack`.
9. Return the decoded string.

---

# Dry Run

Input:

```text
s = "3[a2[c]]"
```

### Step 1

Read:

```text
3
```

Build:

```text
currNum = 3
```

---

### Step 2

Read:

```text
[
```

Push:

```text
intStack = [3]
strStack = ["["]
```

Reset:

```text
currNum = 0
```

---

### Step 3

Read:

```text
a
```

Push into string stack:

```text
strStack = ["[", "a"]
```

---

### Step 4

Read:

```text
2
```

Build:

```text
currNum = 2
```

Then read:

```text
[
```

Push:

```text
intStack = [3,2]
strStack = ["[","a","["]
```

---

### Step 5

Read:

```text
c
```

```text
strStack = ["[","a","[","c"]
```

---

### Step 6

Read:

```text
]
```

Pop until `[`:

```text
temp = "c"
```

Remove `[`.

Get:

```text
num = 2
```

Repeat:

```text
"c" + "c" = "cc"
```

Push back:

```text
strStack = ["[","a","cc"]
```

---

### Step 7

Read final:

```text
]
```

Pop until `[`:

```text
temp = "acc"
```

Get:

```text
num = 3
```

Repeat:

```text
"accaccacc"
```

Final answer:

```text
"accaccacc"
```

---

# Understanding the Code

## Create Two Stacks

```java
Stack<Integer> intStack = new Stack<>();
Stack<String> strStack = new Stack<>();
```

`intStack` stores repetition counts.

`strStack` stores characters and decoded strings.

---

## Build the Number

```java
if(Character.isDigit(x)){
    currNum = currNum * 10 + x - '0';
}
```

This also handles multi-digit numbers.

For:

```text
12
```

the calculation becomes:

```text
0 → 1 → 12
```

---

## Handle Opening Bracket

```java
if(x == '['){
    intStack.push(currNum);
    currNum = 0;
    strStack.push(String.valueOf(x));
}
```

The repetition number is saved before processing the encoded string.

---

## Handle Closing Bracket

```java
else if(x == ']'){
```

A closing bracket means that the current encoded section is complete.

---

## Extract Encoded String

```java
String temp = "";

while(!strStack.isEmpty() && !strStack.peek().equals("[")){
    temp = strStack.pop() + temp;
}
```

Characters are popped from the stack and added to `temp`.

We use:

```java
strStack.pop() + temp
```

to maintain the correct order.

---

## Remove Opening Bracket

```java
strStack.pop();
```

At this point the top of the stack is:

```text
[
```

so it is removed.

---

## Get Repetition Count

```java
int num = intStack.pop();
```

Retrieve how many times the string should be repeated.

---

## Repeat the String

```java
StringBuilder sb = new StringBuilder("");

for(int i=0; i<num; i++){
    sb.append(temp);
}
```

For example:

```text
temp = "ab"
num = 3
```

gives:

```text
"ababab"
```

---

## Push Decoded String

```java
strStack.push(sb.toString());
```

The decoded section is pushed back so that it can participate in an outer expression.

This is important for nested expressions such as:

```text
3[a2[c]]
```

---

# Why Use Stack?

A stack is ideal because encoded strings can be **nested**.

For example:

```text
3[a2[c]]
```

We must first decode:

```text
2[c]
```

and then use that result inside:

```text
3[...]
```

This follows the **Last In, First Out (LIFO)** property of a stack.

The innermost bracket is processed first.

---

# Nested String Example

Consider:

```text
2[a3[b]]
```

The decoding happens from inside to outside:

```text
3[b]
```

becomes:

```text
bbb
```

Then:

```text
a + bbb
```

becomes:

```text
abbb
```

Finally:

```text
2[abbb]
```

becomes:

```text
abbbabbb
```

---

# Important Stack Pattern

The general pattern is:

```text
number
   ↓
[
   ↓
encoded string
   ↓
]
   ↓
repeat string
   ↓
push decoded result
```

For nested expressions:

```text
Outer Stack
    ↓
Inner Stack
    ↓
Decode Inner
    ↓
Return to Outer
```

---

# Complexity Analysis

Let `n` be the length of the input and `L` be the length of the decoded string.

### Time Complexity

The algorithm processes the input and constructs the decoded result.

Therefore:

```text
O(n + L)
```

where `L` is the size of the final decoded string.

---

### Space Complexity

The stacks store characters, numbers, and intermediate strings.

Therefore:

```text
O(n + L)
```

in the worst case.

---

# Java Solution

```java
class Solution {

    public String decodeString(String s) {

        Stack<Integer> intStack = new Stack<>();
        Stack<String> strStack = new Stack<>();

        int currNum = 0;

        for(char x : s.toCharArray()){

            if(Character.isDigit(x)){

                currNum = currNum * 10 + x - '0';

            }
            else{

                if(x == '['){

                    intStack.push(currNum);
                    currNum = 0;

                    strStack.push(String.valueOf(x));
                }

                else if(x == ']'){

                    String temp = "";

                    while(!strStack.isEmpty() &&
                          !strStack.peek().equals("[")){

                        temp = strStack.pop() + temp;
                    }

                    strStack.pop();

                    int num = intStack.pop();

                    StringBuilder sb = new StringBuilder("");

                    for(int i = 0; i < num; i++){
                        sb.append(temp);
                    }

                    strStack.push(sb.toString());
                }

                else{

                    strStack.push(String.valueOf(x));
                }
            }
        }

        String ans = "";

        while(!strStack.isEmpty()){
            ans = strStack.pop() + ans;
        }

        return ans;
    }
}
```

---

# Key Concepts

- String
- Stack
- LIFO
- Nested Structures
- StringBuilder
- Simulation
- Parsing
- Multi-Digit Numbers

---

# Constraints

- `1 <= s.length <= 30`
- `s` consists of lowercase English letters, digits, and brackets.
- The input is guaranteed to be valid.
- Numbers are in the range `[1, 300]`.

---

# Learning Outcome

This problem demonstrates how **Stack** can be used to decode nested expressions.

The main idea is:

```text
Input String
     ↓
Read Number
     ↓
Store Number + "["
     ↓
Process Inner String
     ↓
"]" encountered
     ↓
Decode and Repeat
     ↓
Push Result Back
     ↓
Final Decoded String
```

The most important concepts are **Stack, nested processing, and StringBuilder**.

The solution achieves:

```text
Time  → O(n + L)
Space → O(n + L)
```

where `L` is the length of the decoded string.