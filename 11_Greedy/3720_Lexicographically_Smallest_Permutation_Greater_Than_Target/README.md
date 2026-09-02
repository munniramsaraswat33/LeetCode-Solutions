# 3720. Lexicographically Smallest Permutation Greater Than Target

> **Difficulty:** Medium  
> **Topics:** String, Greedy, Counting

---

## Problem Statement

You are given two strings `s` and `target`.

You need to rearrange the characters of `s` to form a permutation that is:

1. **Lexicographically greater than `target`**
2. **As small as possible** among all valid permutations.

If no permutation of `s` is lexicographically greater than `target`, return an empty string `""`.

### Lexicographical Order

A string `a` is lexicographically smaller than string `b` if at the first position where they differ, `a` has a smaller character.

For example:

```text
"abc" < "abd"
"abd" < "bca"
```

---

## Examples

### Example 1

```text
Input:
s = "abc"
target = "abb"

Output:
"abc"
```

### Explanation

The permutations of `"abc"` include:

```text
abc
acb
bac
bca
cab
cba
```

The smallest permutation greater than `"abb"` is:

```text
"abc"
```

---

### Example 2

```text
Input:
s = "bba"
target = "bab"

Output:
"bba"
```

### Explanation

`"bba"` is greater than `"bab"` and is the smallest valid permutation.

---

### Example 3

```text
Input:
s = "abc"
target = "cba"

Output:
""
```

### Explanation

`"cba"` is already the largest possible permutation of `"abc"`.

Therefore, there is no permutation greater than the target.

---

# Approach

The main idea is to construct the answer **greedily**.

We maintain the frequency of every character in `s`.

```java
int freq[] = new int[26];
```

Instead of generating every permutation, we compare the permutation with `target` from left to right.

There are two situations.

### Case 1: We can follow the target

If the current character of `target` is available in `s`, we use it.

This keeps our result equal to the target for as long as possible.

For example:

```text
target = "abcd"
s      = "abdc"
```

We can use:

```text
a
b
```

because both are available.

---

### Case 2: We cannot continue matching

Suppose the required target character is not available.

At this position, we need to choose the **smallest available character greater than the target character**.

For example:

```text
target character = 'c'
available        = 'a', 'd', 'e'
```

We choose:

```text
'd'
```

because it is the smallest character that makes the resulting string greater.

After choosing this character, the remaining characters should be placed in **ascending order** to make the complete string as small as possible.

---

### Case 3: We matched the entire target

If we successfully matched every character of `target`, the current prefix is equal to the target.

Therefore, we need to make the answer greater at some earlier position.

We go from right to left and try to replace a character with the smallest available character greater than it.

This is important because changing the **rightmost possible position** produces the smallest possible lexicographically greater string.

---

# Algorithm

1. Create a frequency array of size `26`.
2. Count every character in `s`.
3. Traverse `target` from left to right.
4. For each character:
   - If it is available, append it to `StringBuilder` and decrease its frequency.
   - If it is unavailable:
     - Find the smallest available character greater than the target character.
     - If found, append it.
     - Append all remaining characters in sorted order.
     - Return the result.
5. If the complete target was matched:
   - Traverse the constructed prefix from right to left.
   - Restore the removed character into `freq`.
   - Try to find the smallest available character greater than that character.
   - If found, append it and then append all remaining characters in sorted order.
6. If no position can be increased, return `""`.

---

# Dry Run

Consider:

```text
s = "abc"
target = "abb"
```

### Step 1: Frequency Count

```text
a → 1
b → 1
c → 1
```

### Step 2: Compare with Target

Target:

```text
a b b
```

First character:

```text
target = 'a'
```

`a` is available.

```text
result = "a"
freq[a] = 0
```

Second character:

```text
target = 'b'
```

`b` is available.

```text
result = "ab"
freq[b] = 0
```

Third character:

```text
target = 'b'
```

But no `b` remains.

We need a character greater than `b`.

Available:

```text
c
```

So choose:

```text
c
```

Now:

```text
result = "abc"
```

There are no remaining characters.

Therefore:

```text
Answer = "abc"
```

---

### Another Important Case

Suppose:

```text
s = "abc"
target = "abc"
```

We can completely match the target:

```text
result = "abc"
```

But the result is **equal**, not greater.

So we go from right to left.

At `'c'`:

```text
No character greater than c
```

Move to `'b'`.

Restore `b`.

Available characters are:

```text
b, c
```

Again, no character greater than `b` can be used while keeping the prefix `"a"`.

Move to `'a'`.

Restore `a`.

Now `b` is available and:

```text
b > a
```

So choose `b`.

Then append the remaining characters in ascending order:

```text
bc
```

Result:

```text
bac
```

Thus:

```text
"bac" > "abc"
```

and it is the smallest possible valid permutation.

---

# Java Solution

```java
class Solution {

    public String lexGreaterPermutation(String s, String target) {

        int freq[] = new int[26];

        StringBuilder sb = new StringBuilder();

        // Count frequency of characters in s
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Try to match target from left to right
        for (int i = 0; i < target.length(); i++) {

            int curr = target.charAt(i) - 'a';

            // Current target character is unavailable
            if (freq[curr] == 0) {

                // Find the smallest character greater than target[i]
                for (int j = curr + 1; j < 26; j++) {

                    if (freq[j] > 0) {

                        sb.append((char) (j + 'a'));
                        freq[j]--;

                        // Put remaining characters in sorted order
                        appendRemaining(sb, freq);

                        return sb.toString();
                    }
                }

                break;
            }

            // Use the current target character
            sb.append(target.charAt(i));
            freq[curr]--;
        }

        // Target was completely matched.
        // We need to make the string greater at an earlier position.
        for (int i = sb.length() - 1; i >= 0; i--) {

            int curr = sb.charAt(i) - 'a';

            // Restore this character
            freq[curr]++;

            // Remove it from the current prefix
            sb.deleteCharAt(i);

            // Find the smallest character greater than curr
            for (int j = curr + 1; j < 26; j++) {

                if (freq[j] > 0) {

                    sb.append((char) (j + 'a'));
                    freq[j]--;

                    // Append all remaining characters
                    // in ascending order
                    appendRemaining(sb, freq);

                    return sb.toString();
                }
            }
        }

        return "";
    }

    // Append all remaining characters in sorted order
    public void appendRemaining(StringBuilder sb, int[] freq) {

        for (int i = 0; i < 26; i++) {

            while (freq[i] > 0) {

                sb.append((char) (i + 'a'));
                freq[i]--;
            }
        }
    }
}
```

---

# Code Explanation

### 1. Frequency Array

```java
int freq[] = new int[26];
```

Since the strings contain lowercase English letters, we can store the frequency of each character using an array of size `26`.

For example:

```text
s = "aabc"

freq[a] = 2
freq[b] = 1
freq[c] = 1
```

---

### 2. Matching the Target

```java
if (freq[curr] == 0)
```

If the current target character is available, we use it:

```java
sb.append(target.charAt(i));
freq[curr]--;
```

This keeps the prefix as small as possible.

---

### 3. Choosing a Greater Character

If the target character is unavailable:

```java
for (int j = curr + 1; j < 26; j++)
```

We search from the next character upward.

The first available character is the smallest possible character that makes the result greater.

---

### 4. Appending Remaining Characters

Once the result is already greater than the target, we no longer need to compare characters with the target.

To make the result as small as possible, we append all remaining characters in ascending order.

```java
appendRemaining(sb, freq);
```

---

### 5. Backtracking from Right to Left

If the target was completely matched, the current string is equal to the target.

We need to increase it at some position.

Therefore:

```java
for (int i = sb.length() - 1; i >= 0; i--)
```

We start from the rightmost position.

At each position, we restore the character and try to find a slightly larger character.

This produces the **smallest possible increase**.

---

# Complexity Analysis

Let `n` be the length of the string.

### Time Complexity

```text
O(26 × n)
```

Since there are only `26` lowercase characters, this is effectively:

```text
O(n)
```

### Space Complexity

```text
O(n + 26)
```

The `StringBuilder` requires `O(n)` space and the frequency array requires `O(26)` space.

Therefore:

```text
O(n)
```

---

# Key Concepts

### 1. Greedy Algorithm

At every position, choose the smallest possible character that allows the final answer to be greater than the target.

### 2. Frequency Counting

A frequency array tracks how many copies of each character are still available.

### 3. Lexicographical Ordering

The first position where two strings differ determines which string is larger.

### 4. Backtracking

When the target is completely matched, we move backward to find the rightmost position that can be increased.

### 5. Sorted Remaining Characters

After making the result greater, placing all remaining characters in ascending order guarantees the smallest possible suffix.

---

# Constraints

- `s` consists of lowercase English letters.
- `target` consists of lowercase English letters.
- The characters of `s` can be rearranged to form permutations.
- The answer must be lexicographically greater than `target`.
- If no valid permutation exists, return `""`.

---

# Learning Outcome

After solving this problem, you should understand:

- How to construct the **lexicographically smallest greater string**.
- How frequency arrays can efficiently manage duplicate characters.
- How greedy decisions can avoid generating all permutations.
- Why the **rightmost possible position** should be changed when the target is completely matched.
- How to combine **greedy + frequency counting + backtracking** to solve permutation problems efficiently.