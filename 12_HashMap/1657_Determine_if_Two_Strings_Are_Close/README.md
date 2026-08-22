# 1657. Determine if Two Strings Are Close

> **Difficulty:** Medium  
> **Topics:** String, Hash Table, Sorting, Frequency Count

---

## Problem Statement

Two strings are considered **close** if we can transform one string into the other using the following operations:

1. Swap any two existing characters.
2. Transform every occurrence of one existing character into another existing character, and transform every occurrence of the second character into the first character.

Given two strings `word1` and `word2`, return `true` if they are close strings. Otherwise, return `false`.

---

## Example 1

### Input

```text
word1 = "abc"
word2 = "bca"
```

### Output

```text
true
```

### Explanation

The characters can be rearranged using swaps.

Both strings contain:

```text
a, b, c
```

with frequencies:

```text
1, 1, 1
```

Therefore, they are close.

---

## Example 2

### Input

```text
word1 = "a"
word2 = "aa"
```

### Output

```text
false
```

### Explanation

The lengths are different:

```text
word1.length() = 1
word2.length() = 2
```

Therefore, they cannot be close.

---

## Example 3

### Input

```text
word1 = "cabbba"
word2 = "abbccc"
```

### Output

```text
true
```

### Explanation

Frequency of characters in `word1`:

```text
a → 2
b → 3
c → 1
```

Frequency of characters in `word2`:

```text
a → 1
b → 2
c → 3
```

The same characters are present in both strings, and their frequency sets are the same:

```text
[1,2,3]
```

Therefore, the strings are close.

---

# Approach

Use **Frequency Counting**.

For two strings to be close, two important conditions must be satisfied.

### Condition 1: Same Length

The strings must contain the same number of characters.

```text
word1.length() == word2.length()
```

If not, return `false`.

### Condition 2: Same Set of Characters

Both strings must contain exactly the same characters.

For example:

```text
word1 = "aabbc"
word2 = "aabcc"
```

Both contain:

```text
a, b, c
```

So the character sets are the same.

But if one string contains a character that the other does not, they cannot be close.

### Condition 3: Same Frequency Distribution

The exact frequency of each character can change between characters.

For example:

```text
word1:
a → 2
b → 3
c → 1
```

and:

```text
word2:
a → 1
b → 2
c → 3
```

The frequencies are:

```text
[1,2,3]
```

in both strings.

Therefore, sorting the frequency arrays allows us to compare their frequency distributions.

---

# Algorithm

1. If the lengths of `word1` and `word2` are different, return `false`.
2. Create two frequency arrays of size `26`.
3. Count the frequency of every character in `word1`.
4. Count the frequency of every character in `word2`.
5. Check whether both strings contain the same set of characters.
6. Sort both frequency arrays.
7. Compare the two sorted frequency arrays.
8. If they are equal, return `true`.
9. Otherwise, return `false`.

---

# Dry Run

Input:

```text
word1 = "cabbba"
word2 = "abbccc"
```

### Step 1: Count Frequencies

For `word1`:

```text
a → 2
b → 3
c → 1
```

For `word2`:

```text
a → 1
b → 2
c → 3
```

---

### Step 2: Check Character Presence

For both strings:

```text
a → present
b → present
c → present
```

No character exists in only one string.

Therefore, the character sets are equal.

---

### Step 3: Sort Frequencies

For `word1`:

```text
[1,2,3,...]
```

For `word2`:

```text
[1,2,3,...]
```

The sorted frequency arrays are equal.

Therefore:

```text
true
```

---

# Understanding the Code

## Check Length

```java
if(word1.length() != word2.length()){
    return false;
}
```

A close operation never changes the total number of characters.

Therefore, different lengths immediately mean the strings are not close.

---

## Create Frequency Arrays

```java
int freq1[] = new int[26];
int freq2[] = new int[26];
```

Since the strings contain lowercase English letters, we only need `26` positions.

The index is calculated using:

```java
ch - 'a'
```

For example:

```text
'a' - 'a' = 0
'b' - 'a' = 1
'c' - 'a' = 2
```

---

## Count Characters

```java
for(char ch : word1.toCharArray()){
    freq1[ch - 'a']++;
}
```

This stores the frequency of every character in `word1`.

Similarly:

```java
for(char ch : word2.toCharArray()){
    freq2[ch - 'a']++;
}
```

stores frequencies for `word2`.

---

## Check Character Presence

```java
for(int i=0; i<26; i++){
    if((freq1[i] == 0 && freq2[i] != 0) ||
       (freq1[i] != 0 && freq2[i] == 0)){
        return false;
    }
}
```

If a character exists in one string but not in the other, the strings cannot be close.

For example:

```text
word1 = "abc"
word2 = "abd"
```

`c` exists only in `word1` and `d` exists only in `word2`.

Therefore:

```text
false
```

---

## Sort Frequency Arrays

```java
Arrays.sort(freq1);
Arrays.sort(freq2);
```

The exact frequency can be moved between characters using the allowed character transformation operation.

Therefore, we only need to compare the collection of frequencies, not which character has which frequency.

For example:

```text
freq1 = [2,3,1]
freq2 = [1,2,3]
```

After sorting:

```text
freq1 = [1,2,3]
freq2 = [1,2,3]
```

---

## Compare Frequencies

```java
for(int i=0; i<26; i++){
    if(freq1[i] != freq2[i]){
        return false;
    }
}
```

If any frequency differs after sorting, the strings cannot be transformed into each other.

---

# Why Sorting Frequencies Works

Suppose:

```text
word1 = "aabbccc"
```

Frequencies:

```text
a → 2
b → 2
c → 3
```

and:

```text
word2 = "aaabcbc"
```

Frequencies:

```text
a → 3
b → 2
c → 2
```

The frequencies are different for individual characters, but the frequency collection is the same:

```text
[2,2,3]
```

The allowed character transformation operation lets us exchange frequencies between existing characters.

Therefore, only the sorted frequency distribution matters.

---

# Complexity Analysis

### Time Complexity

Counting characters:

```text
O(n)
```

Sorting two arrays of size `26`:

```text
O(26 log 26)
```

Therefore, overall:

```text
O(n)
```

because `26` is constant.

---

### Space Complexity

We use two arrays of fixed size `26`:

```text
O(26)
```

which is:

```text
O(1)
```

extra space.

---

# Java Solution

```java
class Solution {

    public boolean closeStrings(String word1, String word2) {

        if(word1.length() != word2.length()){
            return false;
        }

        int freq1[] = new int[26];
        int freq2[] = new int[26];

        for(char ch : word1.toCharArray()){
            freq1[ch - 'a']++;
        }

        for(char ch : word2.toCharArray()){
            freq2[ch - 'a']++;
        }

        for(int i = 0; i < 26; i++){

            if((freq1[i] == 0 && freq2[i] != 0) ||
               (freq1[i] != 0 && freq2[i] == 0)){
                return false;
            }
        }

        Arrays.sort(freq1);
        Arrays.sort(freq2);

        for(int i = 0; i < 26; i++){

            if(freq1[i] != freq2[i]){
                return false;
            }
        }

        return true;
    }
}
```

---

# Key Concepts

- String
- Frequency Array
- Hash Table Concept
- Character Frequency
- Sorting
- Character Set Comparison
- Counting

---

# Constraints

- `1 <= word1.length, word2.length <= 10^5`
- `word1` and `word2` contain only lowercase English letters.
- The strings must contain the same characters and frequency distribution to be close.

---

# Learning Outcome

This problem demonstrates how **frequency counting and sorting** can be used to compare strings based on their character structure rather than their exact order.

The main idea is:

```text
Count frequencies
       ↓
Check same characters
       ↓
Sort frequency arrays
       ↓
Compare frequencies
       ↓
Return true / false
```

The two important conditions are:

```text
Same characters
        +
Same frequency distribution
        ↓
Close Strings
```

The solution runs in:

```text
Time  → O(n)
Space → O(1)
```