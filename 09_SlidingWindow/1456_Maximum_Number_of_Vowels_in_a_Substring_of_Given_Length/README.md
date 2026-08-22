# 1456. Maximum Number of Vowels in a Substring of Given Length

> **Difficulty:** Medium  
> **Topics:** String, Sliding Window

---

## Problem Statement

Given a string `s` and an integer `k`, return the **maximum number of vowel letters** in any substring of `s` with length `k`.

The vowel letters are:

```text
a, e, i, o, u
```

The substring must contain exactly `k` characters.

---

## Example 1

### Input

```text
s = "abciiidef"
k = 3
```

### Output

```text
3
```

### Explanation

The substring:

```text
"iii"
```

has `3` vowels.

Therefore, the maximum number of vowels is:

```text
3
```

---

## Example 2

### Input

```text
s = "aeiou"
k = 2
```

### Output

```text
2
```

### Explanation

Every substring of length `2` contains two vowels.

Therefore:

```text
2
```

is the maximum.

---

## Example 3

### Input

```text
s = "leetcode"
k = 3
```

### Output

```text
2
```

### Explanation

The substrings:

```text
"lee"
"eet"
"ode"
```

contain `2` vowels.

Therefore, the maximum number of vowels is:

```text
2
```

---

# Approach

Use the **Sliding Window** technique.

We need to find the maximum number of vowels in every substring of length `k`.

Instead of counting the vowels again for every substring, maintain the vowel count of the current window.

### Sliding Window Idea

First calculate the number of vowels in the first `k` characters.

Then move the window one position at a time.

When the window moves:

- Add the new character entering the window.
- Remove the character leaving the window.

This allows us to update the vowel count in `O(1)` time.

---

# Algorithm

1. Initialize `count = 0`.
2. Count vowels in the first `k` characters.
3. Store this count as the initial maximum:
   ```text
   max = count
   ```
4. Start sliding the window from index `k`.
5. For every new character:
   - If it is a vowel, increment `count`.
   - Check the character leaving the window.
   - If it is a vowel, decrement `count`.
6. Update:
   ```text
   max = Math.max(max, count)
   ```
7. If `max == k`, return `k` immediately because no substring can contain more than `k` vowels.
8. Return `max`.

---

# Dry Run

Input:

```text
s = "abciiidef"
k = 3
```

### First Window

```text
"abc"
```

Vowels:

```text
a
```

So:

```text
count = 1
max = 1
```

---

### Move Window

Window:

```text
"bci"
```

Add:

```text
i → vowel
```

Remove:

```text
a → vowel
```

So:

```text
count = 1
```

---

### Move Window

Window:

```text
"cii"
```

Add:

```text
i → vowel
```

Remove:

```text
b → not vowel
```

So:

```text
count = 2
max = 2
```

---

### Move Window

Window:

```text
"iii"
```

Add:

```text
i → vowel
```

Remove:

```text
c → not vowel
```

So:

```text
count = 3
max = 3
```

Since:

```text
max == k
```

we can immediately return:

```text
3
```

---

# Understanding the Code

## Count Vowels in First Window

```java
int count = 0;

for(int i = 0; i < k; i++){

    if(isVowel(s.charAt(i))){
        count++;
    }
}
```

This calculates the number of vowels in the first substring of length `k`.

---

## Store Initial Maximum

```java
int max = count;
```

The first window is initially our best answer.

---

## Slide the Window

```java
for(int i = k; i < s.length(); i++){
```

The new window starts moving one character at a time.

---

## Add New Character

```java
if(isVowel(s.charAt(i))){
    count++;
}
```

The character at index `i` enters the current window.

If it is a vowel, increase the count.

---

## Remove Old Character

```java
if(isVowel(s.charAt(i-k))){
    count--;
}
```

The character at:

```text
i - k
```

leaves the window.

If it was a vowel, decrease the count.

---

## Update Maximum

```java
max = Math.max(max, count);
```

Store the maximum number of vowels found so far.

---

## Early Return

```java
if(max == k){
    return k;
}
```

A window has exactly `k` characters.

Therefore, if all `k` characters are vowels, the maximum possible answer is already reached.

---

# Why Sliding Window?

A brute-force approach would count vowels separately in every substring.

For example:

```text
Window 1 → count vowels
Window 2 → count vowels again
Window 3 → count vowels again
...
```

This can take:

```text
O(n × k)
```

time.

With Sliding Window, only two characters need to be checked when the window moves:

```text
New character → add
Old character → remove
```

Therefore, each character is processed only a small number of times.

---

# Vowel Checking

The helper method checks whether a character is one of the five vowels:

```java
public boolean isVowel(char c){

    return c == 'a' ||
           c == 'e' ||
           c == 'i' ||
           c == 'o' ||
           c == 'u';
}
```

---

# Complexity Analysis

### Time Complexity

The first window takes:

```text
O(k)
```

The sliding window processes the remaining characters:

```text
O(n - k)
```

Overall:

```text
O(n)
```

---

### Space Complexity

Only a few variables are used.

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public int maxVowels(String s, int k) {

        int count = 0;

        for(int i = 0; i < k; i++){

            if(isVowel(s.charAt(i))){
                count++;
            }
        }

        int max = count;

        for(int i = k; i < s.length(); i++){

            if(isVowel(s.charAt(i))){
                count++;
            }

            if(isVowel(s.charAt(i - k))){
                count--;
            }

            max = Math.max(max, count);

            if(max == k){
                return k;
            }
        }

        return max;
    }

    public boolean isVowel(char c){

        return c == 'a' ||
               c == 'e' ||
               c == 'i' ||
               c == 'o' ||
               c == 'u';
    }
}
```

---

# Key Concepts

- String
- Sliding Window
- Two Pointers
- Fixed-Size Window
- Character Checking
- Vowel Counting

---

# Constraints

- `1 <= s.length <= 10^5`
- `s` consists of lowercase English letters.
- `1 <= k <= s.length`

---

# Learning Outcome

This problem demonstrates the **Fixed-Size Sliding Window** pattern.

Instead of recalculating the entire substring every time, we maintain the current window's vowel count:

```text
Add entering character
        ↓
Remove leaving character
        ↓
Update maximum
```

The main idea is:

```java
if(isVowel(s.charAt(i))){
    count++;
}

if(isVowel(s.charAt(i - k))){
    count--;
}
```

This reduces the solution from a possible `O(n × k)` approach to an efficient:

```text
Time  → O(n)
Space → O(1)
```