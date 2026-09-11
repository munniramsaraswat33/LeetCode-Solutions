# 3. Longest Substring Without Repeating Characters

**LeetCode:** [3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

**Difficulty:** Medium

**Primary Topic:** Sliding Window

**Pattern:** Variable Size Sliding Window + HashSet

---

## Problem Statement

Given a string `s`, find the length of the longest substring without repeating characters.

A substring is a contiguous sequence of characters from the string.

The substring must contain every character at most once.

---

## Example 1

### Input

```text
s = "abcabcbb"
```

### Output

```text
3
```

### Explanation

The longest substring without repeating characters is:

```text
"abc"
```

Its length is:

```text
3
```

---

## Example 2

### Input

```text
s = "bbbbb"
```

### Output

```text
1
```

### Explanation

The longest substring without repeating characters is:

```text
"b"
```

---

## Example 3

### Input

```text
s = "pwwkew"
```

### Output

```text
3
```

### Explanation

The longest substring without repeating characters is:

```text
"wke"
```

Its length is:

```text
3
```

---

# Approach

Use a **Variable Size Sliding Window** with a `HashSet`.

The window contains characters that are currently unique.

We maintain:

```text
left
```

as the left boundary of the window.

The right boundary is represented by the current character in the enhanced `for` loop.

The `HashSet` stores all characters currently inside the window.

---

# Intuition

We want the longest contiguous substring containing no duplicate characters.

Suppose the current character already exists in the window.

For example:

```text
Window = "abc"
Current character = 'b'
```

We cannot add another `b` while keeping the substring valid.

So we shrink the window from the left until the previous `b` is removed.

```text
"abc"
 ↑
left
```

Remove:

```text
a
```

Then:

```text
"bc"
```

The duplicate `b` still exists, so remove `b`.

Now we can add the new `b`.

This is exactly the sliding-window pattern:

```text
Expand → Duplicate found → Shrink → Expand again
```

---

# Algorithm

1. Create a `HashSet<Character>` to store characters in the current window.
2. Initialize:
   ```text
   ans = 0
   sum = 0
   left = 0
   ```
3. Traverse the string character by character.
4. If the current character already exists in the set:
   - Remove characters from the left.
   - Move `left` forward.
   - Decrease `sum`.
5. Add the current character to the set.
6. Increase `sum`.
7. Update the maximum:
   ```text
   ans = Math.max(ans, sum)
   ```
8. Return `ans`.

---

# Dry Run

Consider:

```text
s = "abcabcbb"
```

Initially:

```text
set = {}
left = 0
sum = 0
ans = 0
```

### Character `a`

```text
set = {a}
sum = 1
ans = 1
```

Window:

```text
"a"
```

---

### Character `b`

```text
set = {a,b}
sum = 2
ans = 2
```

Window:

```text
"ab"
```

---

### Character `c`

```text
set = {a,b,c}
sum = 3
ans = 3
```

Window:

```text
"abc"
```

---

### Character `a`

`a` already exists.

Remove characters from the left.

Remove:

```text
a
```

Now:

```text
set = {b,c}
left = 1
sum = 2
```

Add the new `a`:

```text
set = {a,b,c}
sum = 3
```

Window:

```text
"bca"
```

Maximum remains:

```text
ans = 3
```

---

### Character `b`

`b` is already present.

Remove from the left until `b` is removed.

Window becomes:

```text
"cab"
```

Length:

```text
3
```

---

The maximum length throughout the traversal is:

```text
3
```

Therefore:

```text
Answer = 3
```

---

# Java Solution

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int ans = 0;
        int sum = 0;
        int left = 0;

        for(char c : s.toCharArray()){

            while(set.contains(c)){
                char ch = s.charAt(left);
                set.remove(ch);
                left++;
                sum--;
            }

            set.add(c);
            sum++;

            ans = Math.max(ans, sum);
        }

        return ans;
    }
}
```

---

# Code Explanation

## 1. HashSet

```java
Set<Character> set = new HashSet<>();
```

The set stores the characters currently present in the sliding window.

Because a set cannot contain duplicate elements, it helps us detect repeated characters.

---

## 2. Maximum Answer

```java
int ans = 0;
```

Stores the maximum length found so far.

---

## 3. Current Window Length

```java
int sum = 0;
```

`sum` represents the number of characters currently present in the window.

Since the window always contains unique characters, `sum` is its length.

---

## 4. Left Pointer

```java
int left = 0;
```

`left` represents the starting index of the current window.

The right side of the window is the current character being processed.

---

# Traversing the String

```java
for(char c : s.toCharArray()){
```

Process every character from left to right.

This effectively moves the right boundary of the sliding window forward.

---

# Detecting Duplicate Characters

```java
while(set.contains(c)){
```

If the current character already exists in the window, the current window is invalid.

For example:

```text
Window = "abc"
Current = 'b'
```

The character `b` already exists.

So we must shrink the window.

---

# Removing From the Left

```java
char ch = s.charAt(left);
set.remove(ch);
left++;
sum--;
```

Remove the character at the left boundary.

Then move:

```text
left → left + 1
```

And decrease the current window length.

The `while` loop continues until the duplicate character is no longer inside the window.

---

# Add Current Character

```java
set.add(c);
sum++;
```

Once there is no duplicate, the current character can safely be added to the window.

---

# Update Maximum

```java
ans = Math.max(ans, sum);
```

Compare the current window length with the best answer found so far.

---

# Why Use `while` Instead of `if`?

We use:

```java
while(set.contains(c))
```

instead of:

```java
if(set.contains(c))
```

because we may need to remove multiple characters from the left before the duplicate is eliminated.

For example:

```text
Window = "abc"
Current = 'c'
```

We need to remove characters until the old `c` is gone.

The `while` loop guarantees that the window becomes valid before adding the new character.

---

# Sliding Window Representation

For:

```text
s = "abcabcbb"
```

The window changes approximately like:

```text
[a]
[ab]
[abc]
[bca]
[cab]
[abc]
[cb]
[b]
```

At every point, the window contains unique characters.

We keep the largest window length.

---

# Why Is This Efficient?

A brute-force approach could generate every substring and check whether it contains duplicate characters.

That can take:

```text
O(n²)
```

or worse depending on how uniqueness is checked.

The sliding window avoids repeatedly processing the same characters.

Each character is:

- Added to the set at most once.
- Removed from the set at most once.

Therefore, the overall traversal is linear.

---

# Complexity Analysis

Let `n` be the length of the string.

## Time Complexity

```text
O(n)
```

Although there is a `while` loop inside the `for` loop, each character can be removed from the set only once.

Therefore, across the entire execution:

```text
Each character enters the window once.
Each character leaves the window once.
```

So the total work is:

```text
O(n)
```

---

## Space Complexity

```text
O(min(n, character_set_size))
```

The `HashSet` stores only the characters currently inside the window.

For the standard ASCII/character constraints, this is effectively bounded by the character set size.

---

# Key Concepts

## 1. Sliding Window

A sliding window maintains a contiguous portion of the string while expanding and shrinking it according to a condition.

Here the condition is:

```text
No duplicate characters
```

---

## 2. Variable Size Window

The window does not have a fixed length.

It expands when characters are unique:

```text
left stays
right moves
```

It shrinks when a duplicate appears:

```text
left moves
```

---

## 3. HashSet

The `HashSet` provides efficient membership checking:

```java
set.contains(c)
```

and removal:

```java
set.remove(c)
```

This makes it suitable for maintaining unique characters.

---

# Pattern Recognition

Whenever a problem asks for:

- Longest substring
- Shortest substring
- Maximum/minimum length
- Contiguous sequence
- A condition that must remain valid

consider:

```text
Sliding Window
```

If the condition involves duplicates or frequencies, consider:

```text
Sliding Window + HashSet / HashMap
```

For this problem:

```text
Longest Substring
        ↓
No Repeating Characters
        ↓
Variable Sliding Window
        ↓
HashSet
```

---

# Common Mistakes

## Mistake 1: Resetting the Entire Window

When a duplicate is found, do not reset the entire window.

Instead, move `left` gradually until the duplicate is removed.

---

## Mistake 2: Using `if` Instead of `while`

The window may need to remove multiple characters.

Therefore:

```java
while(set.contains(c))
```

is required.

---

## Mistake 3: Forgetting to Move `left`

When removing a character:

```java
set.remove(ch);
```

we must also move:

```java
left++;
```

Otherwise, the window boundaries will become incorrect.

---

## Mistake 4: Updating Answer Before Removing Duplicates

The answer should be updated only after the current window is valid.

The correct order is:

```text
Remove duplicates
      ↓
Add current character
      ↓
Update maximum
```

---

# Edge Cases

### Empty String

```text
s = ""
```

Answer:

```text
0
```

The loop does not execute.

---

### One Character

```text
s = "a"
```

Answer:

```text
1
```

---

### All Characters Same

```text
s = "aaaa"
```

The window always contains only one `a`.

Answer:

```text
1
```

---

### All Characters Unique

```text
s = "abcdef"
```

The entire string is the answer.

```text
6
```

---

# Learning Outcome

After solving this problem, you should understand:

- How to identify a sliding-window problem.
- How to maintain a variable-size window.
- How to use a `HashSet` to detect duplicates.
- How to shrink a window when a condition becomes invalid.
- Why the nested `while` loop still gives `O(n)` time.
- How two-pointer techniques optimize substring problems.

---

# Final Takeaway

The main idea is:

```text
Maintain a window containing only unique characters.
```

When a duplicate appears:

```text
Shrink from the left
        ↓
Remove the duplicate
        ↓
Add the current character
        ↓
Update the maximum length
```

The complete pattern is:

```text
Sliding Window + HashSet
```

with:

```text
Time Complexity  = O(n)
Space Complexity = O(min(n, character_set_size))
```