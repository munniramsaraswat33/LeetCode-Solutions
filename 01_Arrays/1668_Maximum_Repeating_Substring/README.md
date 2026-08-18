# 1668. Maximum Repeating Substring

> **Difficulty:** Easy  
> **Topics:** String, String Matching

---

## Problem Statement

You are given two strings:

```text
sequence
word
```

A string `word` is called **k-repeating** if:

```text
word + word + ... + word
```

is a substring of `sequence`, where `word` is repeated exactly `k` times.

We need to find the **maximum value of `k`**.

If `word` is not present in `sequence`, return:

```text
0
```

---

## Example 1

### Input

```text
sequence = "ababc"
word = "ab"
```

We can form:

```text
"ab"
"abab"
"ababab"
```

Now:

```text
"ab"      → substring
"abab"    → substring
"ababab"  → not a substring
```

Therefore:

```text
k = 2
```

### Output

```text
2
```

---

## Approach

Start with:

```java
current = word;
```

Then repeatedly check whether `current` is present inside `sequence`.

If it is present:

1. Increment `count`.
2. Append another `word` to `current`.
3. Check again.

For example:

```text
word = "ab"
```

The values of `current` become:

```text
"ab"
"abab"
"ababab"
"abababab"
...
```

We stop as soon as `current` is no longer a substring of `sequence`.

The number of successful checks is the answer.

---

## Java Solution

```java
class Solution {
    public int maxRepeating(String sequence, String word) {

        String current = word;
        int count = 0;

        while(sequence.contains(current)){
            count++;
            current += word;
        }

        return count;
    }
}
```

---

## Dry Run

### Input

```text
sequence = "ababc"
word = "ab"
```

Initially:

```text
current = "ab"
count = 0
```

### Iteration 1

Check:

```text
"ababc".contains("ab")
```

Yes.

```text
count = 1
current = "abab"
```

---

### Iteration 2

Check:

```text
"ababc".contains("abab")
```

Yes.

```text
count = 2
current = "ababab"
```

---

### Iteration 3

Check:

```text
"ababc".contains("ababab")
```

No.

Loop stops.

Therefore:

```text
answer = 2
```

---

## Example 2

```text
sequence = "ababc"
word = "ba"
```

Initially:

```text
current = "ba"
```

`"ba"` exists in `"ababc"`.

Therefore:

```text
count = 1
```

Now:

```text
current = "baba"
```

But:

```text
"baba"
```

is not a substring of:

```text
"ababc"
```

So we stop.

### Output

```text
1
```

---

## Example 3

```text
sequence = "ababc"
word = "ac"
```

Check:

```text
"ababc".contains("ac")
```

False.

Therefore the loop never executes.

```text
count = 0
```

### Output

```text
0
```

---

## Why Does This Work?

We always start with one copy of `word`:

```text
word
```

If it exists, we try two copies:

```text
word + word
```

If that exists, we try three copies:

```text
word + word + word
```

We continue until the repeated string is no longer a substring.

Because we check every possible repetition in increasing order, the last successful `count` is exactly the maximum `k`.

---

## Complexity Analysis

Let:

```text
n = sequence.length()
m = word.length()
```

At most:

```text
n / m
```

repetitions can fit inside `sequence`.

Java's `contains()` performs substring searching, so the overall complexity is approximately:

```text
O((n/m) * n)
```

in the worst case with the repeated-string construction also contributing to the work.

Given the constraints:

```text
sequence.length <= 100
word.length <= 100
```

this approach is easily fast enough.

### Space Complexity

```text
O(n)
```

because `current` stores the repeated string.

---

## Key Concepts

- String Matching
- `contains()`
- String Concatenation
- Iterative Approach

---

## Key Takeaway

The simple trick is:

```java
String current = word;
```

Keep adding `word`:

```java
current += word;
```

as long as the resulting string is present in `sequence`:

```java
while(sequence.contains(current)){
    count++;
    current += word;
}
```

The final `count` is the maximum repeating value.

### Complexity

```text
Time:  O((n/m) * n) approximately
Space: O(n)
```