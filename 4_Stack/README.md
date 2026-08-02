# 316. Remove Duplicate Letters

- **Difficulty:** Medium
- **Topic:** Stack, Greedy, Monotonic Stack, String
- **Language:** Java

## Problem Statement

Given a string `s`, remove duplicate letters so that every letter appears **once and only once**. You must make sure your result is the **smallest in lexicographical order** among all possible results.

### Example 1

**Input**

```text
s = "bcabc"
```

**Output**

```text
"abc"
```

### Example 2

**Input**

```text
s = "cbacdcbc"
```

**Output**

```text
"acdb"
```

---

## Approach

This problem can be solved using a **Monotonic Stack**.

### Key Observations

- Every character should appear only once.
- The final string should be lexicographically smallest.
- If a smaller character appears later, larger characters before it can be removed (provided they occur again later).

### Algorithm

1. Store the **last occurrence** of every character.
2. Maintain a stack to build the answer.
3. Keep a `visited` array to avoid duplicate characters.
4. For every character:
   - Skip it if it is already in the stack.
   - While:
     - the stack is not empty,
     - the top character is larger than the current character,
     - and the top character appears again later,
     remove the top character from the stack.
   - Push the current character into the stack.
5. Convert the stack into the final string.

---

## Dry Run

Input:

```text
cbacdcbc
```

| Character | Stack | Action |
|-----------|-------|--------|
| c | c | Push |
| b | b | Pop c, Push b |
| a | a | Pop b, Push a |
| c | ac | Push |
| d | acd | Push |
| c | acd | Already visited |
| b | acdb | Push |
| c | acdb | Already visited |

Final Answer

```text
acdb
```

---

## Complexity Analysis

- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)` (26 lowercase letters)

---

## Java Solution

```java
// Paste your accepted Java solution here.
```

---

## Concepts Used

- Monotonic Stack
- Greedy Algorithm
- String Processing
- Last Occurrence Technique

---

## LeetCode Link

https://leetcode.com/problems/remove-duplicate-letters/