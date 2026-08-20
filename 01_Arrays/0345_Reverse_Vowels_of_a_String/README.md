# 345. Reverse Vowels of a String

> **Difficulty:** Easy  
> **Topics:** String, Two Pointers

---

## Problem Statement

Given a string `s`, reverse only the **vowels** in the string and return the resulting string.

The vowels are:

```text
a, e, i, o, u
```

They can appear in both lowercase and uppercase.

All characters that are not vowels must remain at their original positions.

---

## Example 1

### Input

```text
s = "IceCreAm"
```

### Output

```text
"AceCreIm"
```

### Explanation

The vowels in the string are:

```text
I, e, e, A
```

After reversing them:

```text
A, e, e, I
```

The resulting string is:

```text
"AceCreIm"
```

Only the vowels are changed. All consonants remain at their original positions.

---

## Example 2

### Input

```text
s = "leetcode"
```

### Output

```text
"leotcede"
```

The vowels are:

```text
e, e, o, e
```

After reversing:

```text
e, o, e, e
```

The resulting string is:

```text
"leotcede"
```

---

# Approach

Use the **Two Pointer** technique.

We maintain two pointers:

```text
i → starts from the beginning
j → starts from the end
```

The goal is to find vowels from both sides and swap them.

### Process

1. Convert the string into a `StringBuilder` because Java `String` is immutable.
2. Set:
   ```text
   i = 0
   j = s.length() - 1
   ```
3. Move `i` forward until it points to a vowel.
4. Move `j` backward until it points to a vowel.
5. Swap the vowels at `i` and `j`.
6. Move both pointers:
   ```text
   i++
   j--
   ```
7. Continue until `i >= j`.
8. Return the modified string.

---

# Algorithm

1. Create a `StringBuilder` from `s`.
2. Initialize:
   ```text
   i = 0
   j = s.length() - 1
   ```
3. While `i < j`:
   - Move `i` forward while `s[i]` is not a vowel.
   - Move `j` backward while `s[j]` is not a vowel.
   - Swap the characters at `i` and `j`.
   - Increment `i`.
   - Decrement `j`.
4. Return the `StringBuilder` as a string.

---

# Dry Run

Input:

```text
s = "IceCreAm"
```

Initial:

```text
I c e C r e A m
↑             ↑
i             j
```

### Step 1

`I` is a vowel.

Move `j` backward:

```text
m → not vowel
A → vowel
```

So:

```text
i = 0
j = 6
```

Swap:

```text
I ↔ A
```

String becomes:

```text
"AceCreIm"
```

---

### Step 2

Move pointers:

```text
i++
j--
```

Now:

```text
i = 1
j = 5
```

Move `i`:

```text
c → not vowel
e → vowel
```

`j` is already at:

```text
e
```

Both are vowels.

Swap:

```text
e ↔ e
```

No visible change.

---

### Final Result

```text
"AceCreIm"
```

---

# Understanding the Code

## Convert String to StringBuilder

```java
StringBuilder sb = new StringBuilder(s);
```

Java `String` is immutable, so we cannot directly modify its characters.

`StringBuilder` allows us to change characters using:

```java
setCharAt()
```

---

## Initialize Two Pointers

```java
int i = 0;
int j = s.length() - 1;
```

`i` starts from the left.

`j` starts from the right.

---

## Main Loop

```java
while(i < j){
```

We continue until the two pointers meet or cross.

---

## Find Left Vowel

```java
while(i < j && !isVowel(sb.charAt(i))){
    i++;
}
```

If the current character is not a vowel, move `i` forward.

For example:

```text
c → r → e
```

The pointer stops when it finds `e`.

---

## Find Right Vowel

```java
while(i < j && !isVowel(sb.charAt(j))){
    j--;
}
```

Similarly, move `j` backward until it finds a vowel.

---

## Swap the Vowels

```java
char temp = sb.charAt(i);

sb.setCharAt(i, sb.charAt(j));

sb.setCharAt(j, temp);
```

This swaps the vowels at the two pointer positions.

For example:

```text
I ... A
```

becomes:

```text
A ... I
```

---

## Move Both Pointers

```java
i++;
j--;
```

After swapping, both vowels have been processed, so we move towards the center.

---

# `isVowel()` Function

```java
public boolean isVowel(char c){
    return c=='a' || c=='e' || c=='i' || c=='o' || c=='u' ||
           c=='A' || c=='E' || c=='I' || c=='O' || c=='U';
}
```

This function checks whether a character is a vowel.

It handles both:

```text
lowercase
```

and:

```text
uppercase
```

---

# Why Two Pointers?

We need to reverse the vowels.

For example:

```text
I c e C r e A m
```

The first vowel should be swapped with the last vowel:

```text
I ↔ A
```

Then the second vowel with the second-last vowel:

```text
e ↔ e
```

This naturally suggests:

```text
Left Pointer  → first vowel
Right Pointer → last vowel
```

So the **Two Pointer** approach is efficient because we process the string from both ends.

---

# Complexity Analysis

### Time Complexity

Each pointer moves only forward/backward through the string.

Therefore:

```text
O(n)
```

where `n` is the length of the string.

---

### Space Complexity

The `StringBuilder` stores the modified string:

```text
O(n)
```

Apart from the output string, the two pointers use:

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public String reverseVowels(String s) {

        StringBuilder sb = new StringBuilder(s);

        int i = 0;
        int j = s.length() - 1;

        while(i < j){

            while(i < j && !isVowel(sb.charAt(i))){
                i++;
            }

            while(i < j && !isVowel(sb.charAt(j))){
                j--;
            }

            char temp = sb.charAt(i);

            sb.setCharAt(i, sb.charAt(j));

            sb.setCharAt(j, temp);

            i++;
            j--;
        }

        return sb.toString();
    }

    public boolean isVowel(char c){

        return c=='a' || c=='e' || c=='i' || c=='o' || c=='u' ||
               c=='A' || c=='E' || c=='I' || c=='O' || c=='U';
    }
}
```

---

# Key Concepts

- String
- StringBuilder
- Two Pointers
- Character Checking
- Swapping
- `setCharAt()`
- Left and Right Traversal

---

# Constraints

- `1 <= s.length <= 3 * 10^5`
- `s` consists of printable ASCII characters.

---

# Learning Outcome

This problem demonstrates the **Two Pointer** pattern for processing characters from both ends of a string.

The important idea is:

```text
Find left vowel
      ↓
Find right vowel
      ↓
Swap them
      ↓
Move both pointers
```

The core pattern is:

```java
while(i < j){

    while(i < j && !isVowel(sb.charAt(i))){
        i++;
    }

    while(i < j && !isVowel(sb.charAt(j))){
        j--;
    }

    // swap

    i++;
    j--;
}
```

This allows all vowels to be reversed in **O(n)** time.