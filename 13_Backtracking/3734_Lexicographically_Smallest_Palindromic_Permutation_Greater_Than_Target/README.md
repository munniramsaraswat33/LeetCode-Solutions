# 3734. Lexicographically Smallest Palindromic Permutation Greater Than Target

> **Difficulty:** Medium  
> **Topics:** String, Backtracking, Greedy, Counting

---

## Problem Statement

You are given two strings `s` and `target`.

You need to rearrange the characters of `s` to form a **palindromic permutation** that is:

1. A valid permutation of the characters of `s`.
2. A palindrome.
3. Lexicographically greater than `target`.
4. The lexicographically smallest among all valid palindromic permutations.

If no such permutation exists, return an empty string `""`.

---

## Examples

### Example 1

```text
Input:
s = "baba"
target = "abba"

Output:
"baab"
```

### Explanation

The characters of `s` can form palindromes such as:

```text
abba
baab
```

`"abba"` is equal to the target, so it is not valid.

`"baab"` is greater than `"abba"` and is the smallest valid palindromic permutation.

---

### Example 2

```text
Input:
s = "aabb"
target = "aaaa"

Output:
"abba"
```

### Explanation

The palindrome `"abba"` can be constructed using all characters of `s`.

Since:

```text
"abba" > "aaaa"
```

it is a valid answer.

---

### Example 3

```text
Input:
s = "abc"
target = "abc"

Output:
""
```

### Explanation

A palindrome can be formed only when at most one character has an odd frequency.

Here:

```text
a → 1
b → 1
c → 1
```

There are three characters with odd frequencies.

Therefore, no palindromic permutation is possible.

---

# Approach

The key observation is that a palindrome is completely determined by its **first half** and its optional middle character.

For example:

```text
Palindrome = "abcba"

First half = "ab"
Middle     = "c"
Second half = reverse("ab") = "ba"
```

So instead of generating all permutations of the complete string, we only need to construct the first half.

---

## Step 1: Count Character Frequencies

We create a frequency array:

```java
int[] count = new int[26];
```

For every character in `s`, increase its frequency.

For example:

```text
s = "aabbc"

a → 2
b → 2
c → 1
```

---

## Step 2: Check Whether a Palindrome Is Possible

A string can form a palindrome only when **at most one character has an odd frequency**.

For example:

```text
a → 2
b → 2
c → 1
```

Only `c` has an odd frequency.

Therefore:

```text
middle = 'c'
```

For the first half, we only need half of every character's frequency.

```java
count[i] = count[i] / 2;
```

---

## Step 3: Build Only the First Half

Suppose:

```text
s = "aabbc"
```

The first half contains:

```text
ab
```

Once we know the first half, the complete palindrome is automatically:

```text
ab + c + ba
```

which gives:

```text
abcba
```

Therefore, the backtracking process only needs to construct the first half.

---

## Step 4: Use Backtracking

At every position, try characters from:

```text
'a' → 'z'
```

This ensures that smaller characters are considered first.

```java
for(int j = 0; j < 26; j++)
```

If a character is available, place it in the current half and recursively continue.

If the choice does not lead to a valid answer, remove it and try the next character.

---

## Step 5: Make the Result Greater Than Target

While constructing the first half, we compare it with the corresponding characters of `target`.

If the current character is already greater than the target character, then the resulting palindrome will eventually be greater.

If the current character is smaller while we are still equal to the target prefix, we cannot use it.

```java
if(!flag && ch < target.charAt(i)){
    continue;
}
```

Here:

```text
flag = true
```

means that the constructed prefix is already greater than the corresponding target prefix.

---

## Step 6: Construct the Complete Palindrome

When the first half is completely constructed:

```java
StringBuilder p = new StringBuilder(half);
```

If the original string has odd length, append the middle character.

```java
if(target.length() % 2 == 1){
    p.append(mid);
}
```

Then append the reverse of the first half.

```java
p.append(new StringBuilder(half).reverse());
```

For example:

```text
half = "ab"
mid  = "c"
```

The palindrome becomes:

```text
ab + c + ba
= abcba
```

Finally, check:

```java
p.toString().compareTo(target) > 0
```

If it is greater than the target, we have found a valid answer.

Because characters are tried in ascending order, the first valid answer is the lexicographically smallest one.

---

# Algorithm

1. Create a frequency array of size `26`.
2. Count the frequency of every character in `s`.
3. Count the number of characters having odd frequency.
4. If more than one character has an odd frequency, return `""`.
5. Store the odd-frequency character as the middle character.
6. Divide every frequency by `2` because only the first half needs to be constructed.
7. Use backtracking to construct the first half.
8. At every position:
   - Try characters from `'a'` to `'z'`.
   - Skip characters that are unavailable.
   - If the prefix is still equal to `target`, do not choose a character smaller than `target[i]`.
   - Place the character and recursively continue.
9. Once the first half is complete:
   - Add the middle character if necessary.
   - Add the reverse of the first half.
10. Check whether the palindrome is greater than `target`.
11. If yes, return it.
12. Otherwise, backtrack and try another possibility.
13. If no valid palindrome can be created, return `""`.

---

# Dry Run

Consider:

```text
s = "aabb"
target = "abba"
```

### Step 1: Frequency

```text
a → 2
b → 2
```

No character has an odd frequency.

So:

```text
mid = none
```

Half frequencies:

```text
a → 1
b → 1
```

The first half must contain:

```text
"ab"
```

---

### Step 2: First Position

Target:

```text
a b b a
↑
```

Try:

```text
'a'
```

It is equal to the target character.

Current half:

```text
"a"
```

---

### Step 3: Second Position

Target:

```text
a b b a
  ↑
```

Try:

```text
'b'
```

It is equal to the target character.

Current half:

```text
"ab"
```

---

### Step 4: Construct Palindrome

The half is:

```text
"ab"
```

Reverse:

```text
"ba"
```

Complete palindrome:

```text
"ab" + "ba"
= "abba"
```

But:

```text
"abba" == "abba"
```

Therefore, it is not greater than the target.

So backtracking is required.

---

### Step 5: Try a Larger Choice

The available choices cannot produce another valid half using the same prefix.

The algorithm backtracks and searches for a position where the character can be increased.

If a larger valid character can be placed, the remaining characters are used to construct the smallest possible suffix.

This continues until a palindrome greater than the target is found.

If no such palindrome exists, the method returns:

```text
""
```

---

# Java Solution

```java
class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int[] count = new int[26];

        // Count frequency of every character
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int odd = 0;
        char mid = 0;

        // Check whether palindrome is possible
        for (int i = 0; i < 26; i++) {

            if (count[i] % 2 == 1) {
                mid = (char) ('a' + i);
                odd++;
            }

            // More than one odd frequency
            // means palindrome is impossible
            if (odd > 1) {
                return "";
            }

            // Only half of each frequency is needed
            // for constructing the first half
            count[i] = count[i] / 2;
        }

        StringBuilder half = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        // Build the first half using backtracking
        if (solve(0, half, count, target, mid, false, answer)) {
            return answer.toString();
        }

        return "";
    }

    public boolean solve(
            int i,
            StringBuilder half,
            int[] count,
            String target,
            char mid,
            boolean flag,
            StringBuilder answer) {

        int halfLen = target.length() / 2;

        // First half is completely constructed
        if (i == halfLen) {

            StringBuilder p = new StringBuilder(half);

            // Add middle character for odd length
            if (target.length() % 2 == 1) {
                p.append(mid);
            }

            // Add reverse of first half
            p.append(new StringBuilder(half).reverse());

            // Check if palindrome is greater than target
            if (p.toString().compareTo(target) > 0) {
                answer.append(p);
                return true;
            }

            return false;
        }

        // Try characters in lexicographical order
        for (int j = 0; j < 26; j++) {

            if (count[j] == 0) {
                continue;
            }

            char ch = (char) ('a' + j);

            // If prefix is still equal to target,
            // we cannot choose a smaller character
            if (!flag && ch < target.charAt(i)) {
                continue;
            }

            // Choose
            count[j]--;
            half.append(ch);

            // Once ch is greater than target[i],
            // the complete palindrome can be greater
            boolean newFlag =
                    flag || ch > target.charAt(i);

            // Explore
            if (solve(
                    i + 1,
                    half,
                    count,
                    target,
                    mid,
                    newFlag,
                    answer)) {

                return true;
            }

            // Backtrack
            half.deleteCharAt(half.length() - 1);
            count[j]++;
        }

        return false;
    }
}
```

---

# Code Explanation

### Frequency Array

```java
int[] count = new int[26];
```

Stores the number of occurrences of every lowercase character.

---

### Finding the Middle Character

```java
if(count[i] % 2 == 1)
```

A palindrome can contain at most one character with an odd frequency.

That character becomes the middle character.

---

### Half Frequency

```java
count[i] = count[i] / 2;
```

Only half of each character is needed because the other half is created by reversing the first half.

---

### Backtracking

```java
solve(...)
```

constructs the first half one character at a time.

If a choice does not produce a valid palindrome, the choice is removed:

```java
half.deleteCharAt(half.length() - 1);
count[j]++;
```

This is the backtracking step.

---

### `flag`

```java
boolean flag
```

indicates whether the constructed prefix is already greater than the target.

```java
boolean newFlag = flag || ch > target.charAt(i);
```

Once the prefix becomes greater, later characters only need to make the palindrome as small as possible.

---

### Building the Palindrome

```java
p.append(new StringBuilder(half).reverse());
```

The second half is the reverse of the first half, guaranteeing that the result is a palindrome.

---

# Complexity Analysis

Let `n` be the length of `s`.

The algorithm constructs only `n / 2` characters and uses a frequency array of size `26`.

In the worst case, backtracking may explore multiple possible arrangements.

### Time Complexity

```text
O(26^(n/2))
```

in the worst case due to backtracking.

However, the search is heavily restricted by character frequencies and the lexicographical comparison.

### Space Complexity

```text
O(n)
```

The recursion depth and `StringBuilder` require space proportional to the length of the string.

The frequency array uses:

```text
O(26)
```

additional space.

---

# Key Concepts

### 1. Backtracking

Try a character, recursively continue, and undo the choice if it does not lead to a valid answer.

### 2. Greedy Ordering

Characters are tried from `'a'` to `'z'`, so smaller possibilities are explored first.

The first valid answer is therefore the lexicographically smallest valid answer.

### 3. Frequency Counting

A frequency array efficiently tracks which characters are still available.

### 4. Palindrome Construction

Only the first half needs to be generated.

The second half is simply the reverse of the first half.

### 5. Lexicographical Comparison

The result must satisfy:

```java
result.compareTo(target) > 0
```

### 6. Backtracking with Pruning

Characters that are smaller than the target at a position are skipped while the prefix is still equal.

This prevents many unnecessary recursive calls.

---

# Constraints

- `s` consists of lowercase English letters.
- `target` consists of lowercase English letters.
- A valid answer must be a permutation of `s`.
- The answer must be a palindrome.
- The answer must be lexicographically greater than `target`.
- If no valid permutation exists, return `""`.

---

# Learning Outcome

After solving this problem, you should understand:

- How to determine whether a string can form a palindrome.
- How to construct a palindrome using only its first half.
- How frequency arrays simplify permutation problems.
- How backtracking can generate valid arrangements.
- How lexicographical ordering can be combined with backtracking.
- How pruning avoids exploring impossible choices.
- How to find the **lexicographically smallest valid permutation** instead of generating every permutation.