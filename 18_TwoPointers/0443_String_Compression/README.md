# 443. String Compression

> **Difficulty:** Medium  
> **Topics:** String, Two Pointers

---

## Problem Statement

Given an array of characters `chars`, compress it using the following rules:

- For each group of consecutive repeating characters, write the character once.
- If the character appears more than once consecutively, write its frequency immediately after the character.
- If the frequency is `1`, do not write the number.
- The compressed result must be stored **in-place** in the `chars` array.
- Return the new length of the compressed array.

The frequency can be greater than `9`, so its digits must be written individually.

---

## Example 1

### Input

```text
chars = ["a","a","b","b","c","c","c"]
```

### Output

```text
6
```

### Modified Array

```text
["a","2","b","2","c","3"]
```

### Explanation

The groups are:

```text
aa → a2
bb → b2
ccc → c3
```

Therefore:

```text
"aabbccc" → "a2b2c3"
```

The compressed length is:

```text
6
```

---

## Example 2

### Input

```text
chars = ["a"]
```

### Output

```text
1
```

### Modified Array

```text
["a"]
```

### Explanation

The character appears only once, so no frequency is written.

---

## Example 3

### Input

```text
chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
```

### Output

```text
4
```

### Modified Array

```text
["a","b","1","2"]
```

### Explanation

There are:

```text
1 → a
12 → b
```

So the compressed result is:

```text
"a b12"
```

and its length is:

```text
4
```

---

# Approach

Use the **Two Pointer** technique.

We use two pointers:

```text
i     → reads the original characters
index → writes the compressed characters
```

For every group of consecutive equal characters:

1. Store the current character.
2. Count how many times it occurs consecutively.
3. Write the character at `index`.
4. If the count is greater than `1`, convert the count into a string.
5. Write each digit of the count into the array.
6. Continue with the next group.

This modifies the original array directly without creating another character array.

---

# Algorithm

1. Initialize:
   ```text
   i = 0
   index = 0
   ```
2. While `i < chars.length`:
   - Store:
     ```text
     current = chars[i]
     ```
   - Set:
     ```text
     count = 0
     ```
3. Count all consecutive occurrences of `current`.
4. Write `current` at `chars[index]`.
5. Increment `index`.
6. If `count > 1`:
   - Convert `count` to a string.
   - Write every digit into `chars`.
7. Continue until all characters are processed.
8. Return `index`.

---

# Dry Run

Input:

```text
chars = ['a','a','b','b','c','c','c']
```

Initially:

```text
i = 0
index = 0
```

### Group 1

Current character:

```text
'a'
```

Occurrences:

```text
'a','a'
```

So:

```text
count = 2
```

Write:

```text
chars[index++] = 'a'
```

Then write:

```text
'2'
```

Array becomes:

```text
['a','2',...]
```

---

### Group 2

Current character:

```text
'b'
```

Count:

```text
2
```

Write:

```text
'b','2'
```

Now:

```text
['a','2','b','2',...]
```

---

### Group 3

Current character:

```text
'c'
```

Count:

```text
3
```

Write:

```text
'c','3'
```

Final compressed array:

```text
['a','2','b','2','c','3',...]
```

Returned length:

```text
6
```

---

# Understanding the Code

## Initialize Pointers

```java
int i = 0;
int index = 0;
```

`i` is used to read the input array.

`index` is used to write the compressed result.

---

## Find Current Group

```java
char current = chars[i];
int count = 0;
```

`current` stores the character of the current group.

`count` stores how many consecutive times it occurs.

---

## Count Consecutive Characters

```java
while(i < chars.length && chars[i] == current){
    count++;
    i++;
}
```

This loop moves `i` until the current group ends.

For example:

```text
a a a b
↑ ↑ ↑
```

The loop counts:

```text
count = 3
```

and stops when it reaches `b`.

---

## Write Character

```java
chars[index++] = current;
```

The current character is written once in the compressed array.

For:

```text
aaa
```

we write:

```text
a
```

---

## Write Frequency

```java
if(count > 1){
    String str = Integer.toString(count);
```

If the character appears more than once, its frequency must be written.

For example:

```text
aaa → a3
```

---

## Write Digits Individually

```java
for(int j = 0; j < str.length(); j++){
    chars[index++] = str.charAt(j);
}
```

This is important for counts greater than `9`.

For example:

```text
aaaaaaaaaa → a10
```

The count:

```text
10
```

contains two characters:

```text
'1'
'0'
```

So both digits are written separately.

---

# Why Two Pointers?

The original array and compressed result use the same array.

We need one pointer to **read** the groups and another pointer to **write** the compressed result.

```text
i
↓
a a a b b c
```

and:

```text
index
↓
a 3 b 2 c
```

The reading pointer moves through every original character, while the writing pointer only moves when something is added to the compressed result.

---

# In-Place Modification

The problem requires modifying the array in-place.

Our solution does not create another character array.

We directly modify:

```java
chars[index]
```

Therefore, the first `index` positions contain the compressed result.

---

# Complexity Analysis

### Time Complexity

Every character is processed while finding groups, and the frequency digits are written into the same array.

Therefore:

```text
O(n)
```

where `n` is the length of `chars`.

---

### Space Complexity

Apart from the temporary string used to represent the count, no additional array is created.

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public int compress(char[] chars) {

        int i = 0;
        int index = 0;

        while(i < chars.length){

            char current = chars[i];
            int count = 0;

            while(i < chars.length && chars[i] == current){
                count++;
                i++;
            }

            chars[index++] = current;

            if(count > 1){

                String str = Integer.toString(count);

                for(int j = 0; j < str.length(); j++){
                    chars[index++] = str.charAt(j);
                }
            }
        }

        return index;
    }
}
```

---

# Key Concepts

- String
- Character Array
- Two Pointers
- In-Place Modification
- Group Counting
- Frequency Encoding
- Array Traversal

---

# Constraints

- `1 <= chars.length <= 2000`
- `chars[i]` is an English letter, digit, or symbol.
- The compressed array will always fit within the original array.

---

# Learning Outcome

This problem demonstrates how the **Two Pointer** technique can be used to compress data directly inside an array.

The main pattern is:

```text
Read a group
    ↓
Count consecutive characters
    ↓
Write character once
    ↓
Write frequency if count > 1
    ↓
Move to next group
```

The important idea is separating the pointers:

```text
i      → Read pointer
index  → Write pointer
```

The solution runs in:

```text
Time  → O(n)
Space → O(1)
```

and performs the compression **in-place**.