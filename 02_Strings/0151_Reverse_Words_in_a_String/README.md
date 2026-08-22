# 151. Reverse Words in a String

> **Difficulty:** Medium  
> **Topics:** String

---

## Problem Statement

Given a string `s`, reverse the order of the words.

A **word** is defined as a sequence of non-space characters.

The returned string should:

- Contain the words in reverse order.
- Have only a single space between words.
- Not contain leading or trailing spaces.

---

## Example 1

### Input

```text
s = "the sky is blue"
```

### Output

```text
"blue is sky the"
```

### Explanation

The words are:

```text
the → sky → is → blue
```

After reversing their order:

```text
blue → is → sky → the
```

Therefore:

```text
"blue is sky the"
```

---

## Example 2

### Input

```text
s = "  hello world  "
```

### Output

```text
"world hello"
```

### Explanation

Leading and trailing spaces are ignored.

The words are:

```text
hello world
```

Reversing them gives:

```text
world hello
```

---

## Example 3

### Input

```text
s = "a good   example"
```

### Output

```text
"example good a"
```

### Explanation

Multiple spaces between words are treated as a single separator.

---

# Approach

Use **String Traversal**.

Maintain a temporary string:

```text
str
```

which stores the current word.

Maintain another string:

```text
ans
```

which stores the words in reverse order.

While traversing the input string:

- If the current character is not a space, add it to `str`.
- When a space is found and `str` is not empty, a complete word has been formed.
- Add this word to the **front** of `ans`.
- Reset `str`.
- Ignore consecutive spaces.

After the loop, process the last word if it exists.

Finally, use:

```java
ans.trim()
```

to remove the extra space at the end.

---

# Algorithm

1. Initialize:
   ```text
   str = ""
   ans = ""
   ```
2. Traverse the string character by character.
3. If the current character is not a space:
   - Add it to `str`.
4. If the current character is a space:
   - If `str` is not empty:
     - Add `str` before the current `ans`.
     - Reset `str`.
   - Otherwise, ignore the space.
5. After the loop, add the remaining word if `str` is not empty.
6. Return `ans.trim()`.

---

# Dry Run

Input:

```text
s = "the sky is blue"
```

Initially:

```text
str = ""
ans = ""
```

### Read `"the"`

Characters are added to `str`:

```text
str = "the"
```

Space is encountered.

Add the word before `ans`:

```text
ans = "the "
```

Reset:

```text
str = ""
```

---

### Read `"sky"`

```text
str = "sky"
```

After the space:

```text
ans = "sky the "
```

---

### Read `"is"`

```text
str = "is"
```

After the space:

```text
ans = "is sky the "
```

---

### Read `"blue"`

```text
str = "blue"
```

At the end of the string:

```text
ans = "blue is sky the "
```

Finally:

```java
ans.trim()
```

gives:

```text
"blue is sky the"
```

---

# Understanding the Code

## Store Current Word

```java
String str = "";
```

`str` stores the characters of the current word.

For example:

```text
t → th → the
```

---

## Store Reversed Words

```java
String ans = "";
```

Whenever a complete word is found, it is placed before the existing answer:

```java
ans = str + ' ' + ans;
```

For example:

```text
First word:
ans = "the "

Second word:
ans = "sky the "

Third word:
ans = "is sky the "
```

This automatically reverses the word order.

---

## Handle Spaces

```java
if(s.charAt(i) == ' ' && !str.equals("")){
    ans = str + ' ' + ans;
    str = "";
}
```

If a space is found after a word, the word is complete.

It is added to the beginning of `ans`.

---

## Ignore Extra Spaces

```java
else if(s.charAt(i) == ' '){
    continue;
}
```

This handles multiple consecutive spaces.

For example:

```text
"a   good"
```

The extra spaces are simply ignored.

---

## Add Last Word

```java
if(!str.equals(""))
    ans = str + ' ' + ans;
```

The last word is not followed by a space, so it must be added after the loop.

---

## Remove Extra Space

```java
return ans.trim();
```

Since a space is added after every word, there may be an extra space at the end.

`trim()` removes that extra leading or trailing space.

---

# Why This Approach?

The main idea is to process each word and place it before the previously processed words.

For example:

```text
Input:
one two three
```

Processing:

```text
one
↓
one

two
↓
two one

three
↓
three two one
```

Therefore, the words are automatically reversed.

---

# Complexity Analysis

### Time Complexity

The string is traversed once:

```text
O(n)
```

where `n` is the length of the string.

However, because the solution repeatedly creates new `String` objects using:

```java
str += s.charAt(i);
ans = str + ' ' + ans;
```

the practical cost can be higher due to Java `String` immutability.

---

### Space Complexity

The answer and temporary strings require:

```text
O(n)
```

space.

---

# Java Solution

```java
class Solution {

    public String reverseWords(String s) {

        String str = "";
        String ans = "";

        for(int i = 0; i < s.length(); i++){

            if(s.charAt(i) == ' ' && !str.equals("")){

                ans = str + ' ' + ans;
                str = "";

            }
            else if(s.charAt(i) == ' '){

                continue;

            }
            else{

                str += s.charAt(i);
            }
        }

        if(!str.equals(""))
            ans = str + ' ' + ans;

        return ans.trim();
    }
}
```

---

# Key Concepts

- String
- String Traversal
- Word Extraction
- Handling Spaces
- Reverse Order
- String Manipulation

---

# Constraints

- `1 <= s.length <= 10^4`
- `s` contains English letters, digits, and spaces.
- There is at least one word in `s`.

---

# Learning Outcome

This problem demonstrates how to process a string **word by word** while handling leading, trailing, and multiple spaces.

The main idea is:

```text
Extract Word
    ↓
Add Word Before Previous Answer
    ↓
Continue Traversing
    ↓
Trim Extra Space
```

The important line is:

```java
ans = str + ' ' + ans;
```

because it places every newly found word before the previously processed words.

The solution uses:

```text
Time  → O(n)*
Space → O(n)
```

`*` The intended traversal is linear, although repeated Java `String` concatenation can introduce additional copying overhead.