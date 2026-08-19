# 784. Letter Case Permutation

> **Difficulty:** Medium  
> **Topics:** String, Backtracking, Recursion

---

## Problem Statement

Given a string `s`, you can transform every letter individually to be **lowercase** or **uppercase**.

Return a list of all possible strings that can be created.

Digits remain unchanged because only letters can be converted between lowercase and uppercase.

---

## Example 1

### Input

```text
s = "a1b2"
```

### Output

```text
["a1b2","a1B2","A1b2","A1B2"]
```

### Explanation

There are two letters:

```text
a
b
```

Each letter has two choices:

```text
lowercase
uppercase
```

Therefore:

```text
2 × 2 = 4
```

possible strings are generated.

---

## Example 2

### Input

```text
s = "3z4"
```

### Output

```text
["3z4","3Z4"]
```

The digit `3` and `4` remain unchanged.

Only `z` can be changed:

```text
z
Z
```

---

# Approach

Use **Backtracking**.

For every character:

- If it is a letter, we have **two choices**:
  - Convert it to lowercase.
  - Convert it to uppercase.
- If it is a digit, there is only **one choice**:
  - Keep it unchanged.

We modify the character array directly and recursively process the next character.

When we reach the end of the string, we have created one complete valid permutation.

---

# Algorithm

1. Convert the string into a character array using:

```java
s.toCharArray()
```

2. Start backtracking from index `0`.
3. If `start == nums.length`, add the current character array as a string to the answer.
4. If the current character is a letter:
   - Convert it to lowercase.
   - Recursively process the next character.
   - Convert it to uppercase.
   - Recursively process the next character.
5. If the current character is a digit:
   - Leave it unchanged.
   - Recursively process the next character.
6. Return the answer.

---

# Dry Run

Input:

```text
s = "a1b2"
```

The decision tree is:

```text
                 a1b2
              /       \
           a1b2       A1b2
           /  \       /  \
        a1b2 a1B2  A1b2 A1B2
```

Therefore:

```text
a1b2
a1B2
A1b2
A1B2
```

are generated.

---

# Understanding the Code

## Main Function

```java
public List<String> letterCasePermutation(String s) {
```

This function returns all possible permutations.

---

### Create Answer List

```java
List<String> ans = new ArrayList<>();
```

This stores all generated strings.

---

### Convert String to Character Array

```java
s.toCharArray()
```

Strings in Java are immutable, so we convert the string into a `char[]`.

For example:

```text
"a1b2"
```

becomes:

```text
['a', '1', 'b', '2']
```

Now individual characters can be changed during backtracking.

---

### Start Backtracking

```java
backtrack(s.toCharArray(), 0, ans);
```

The initial index is:

```text
start = 0
```

So we start processing from the first character.

---

# Backtracking Function

```java
public void backtrack(char[] nums, int start, List<String> ans)
```

Parameters:

```text
nums  -> character array
start -> current index
ans   -> stores all generated strings
```

---

## Base Case

```java
if(start == nums.length){
    ans.add(new String(nums));
    return;
}
```

When:

```text
start == nums.length
```

we have processed every character.

Therefore, the current character array represents one complete answer.

We convert it into a String:

```java
new String(nums)
```

and add it to `ans`.

---

# Checking Whether Character Is a Letter

```java
if(Character.isLetter(nums[start]))
```

If the current character is a letter, it has two possible choices.

For example:

```text
a
```

can become:

```text
a
A
```

---

# First Choice: Lowercase

```java
nums[start] = Character.toLowerCase(nums[start]);
backtrack(nums, start+1, ans);
```

We first convert the character to lowercase.

For example:

```text
A -> a
```

Then recursively process the next character.

---

# Second Choice: Uppercase

```java
nums[start] = Character.toUpperCase(nums[start]);
backtrack(nums, start+1, ans);
```

Now we convert the same character to uppercase.

For example:

```text
a -> A
```

Then recursively process the next character again.

This creates the second branch of the recursion tree.

---

# What Happens With Digits?

```java
else{
    backtrack(nums, start+1, ans);
}
```

If the current character is a digit, it cannot be changed.

For example:

```text
1
```

remains:

```text
1
```

So we simply move to the next character.

---

# Why Backtracking Is Used?

For every letter there are two choices:

```text
lowercase
     OR
uppercase
```

This creates a decision tree.

For example, `"ab"`:

```text
             ""
           /    \
          a      A
         / \    / \
       ab  aB  Ab  AB
```

Every path from the root to the leaf gives one valid answer.

This is a classic **Backtracking** pattern:

```text
Choose
   ↓
Explore
   ↓
Choose another option
```

---

# Important Backtracking Idea

In this solution, we don't create a new character array for every recursive call.

We reuse the same array:

```java
char[] nums
```

and modify the current character before each recursive call.

For example:

```java
nums[start] = Character.toLowerCase(nums[start]);
backtrack(...);

nums[start] = Character.toUpperCase(nums[start]);
backtrack(...);
```

This allows us to explore both possibilities using the same array.

---

# Complexity Analysis

Let `n` be the length of the string.

If there are `L` letters, each letter has two choices.

Therefore, the number of possible strings is:

```text
2^L
```

Since `L <= n`, the maximum number of strings is:

```text
2^n
```

### Time Complexity

Each generated string requires `O(n)` time to copy into the answer.

Therefore:

```text
O(n * 2^n)
```

---

### Space Complexity

The recursion depth is:

```text
O(n)
```

The output itself requires:

```text
O(n * 2^n)
```

space.

Auxiliary recursion space:

```text
O(n)
```

---

# Java Solution

```java
class Solution {
    public List<String> letterCasePermutation(String s) {

        List<String> ans = new ArrayList<>();

        backtrack(s.toCharArray(), 0, ans);

        return ans;
    }

    public void backtrack(char[] nums, int start, List<String> ans) {

        if(start == nums.length){
            ans.add(new String(nums));
            return;
        }

        if(Character.isLetter(nums[start])){

            nums[start] = Character.toLowerCase(nums[start]);
            backtrack(nums, start + 1, ans);

            nums[start] = Character.toUpperCase(nums[start]);
            backtrack(nums, start + 1, ans);

        }
        else{

            backtrack(nums, start + 1, ans);

        }
    }
}
```

---

# Key Concepts

- String
- Character Array
- Backtracking
- Recursion
- Decision Tree
- `Character.isLetter()`
- `Character.toLowerCase()`
- `Character.toUpperCase()`
- Choose → Explore

---

# Constraints

- `1 <= s.length <= 12`
- `s` consists of lowercase English letters, uppercase English letters, and digits.

---

# Learning Outcome

This problem demonstrates how **Backtracking** can be used when every character has multiple possible choices.

For every letter:

```text
Lowercase
   OR
Uppercase
```

For every digit:

```text
Keep unchanged
```

The core backtracking pattern is:

```java
choose option 1
backtrack()

choose option 2
backtrack()
```

The number of possible answers depends on the number of letters:

```text
2^L
```

where `L` is the number of letters in the string.