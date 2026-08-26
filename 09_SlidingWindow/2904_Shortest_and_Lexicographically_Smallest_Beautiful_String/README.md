# 2904. Shortest and Lexicographically Smallest Beautiful String

> **Difficulty:** Medium  
> **Topics:** String, Sliding Window, Two Pointers

---

## Problem Statement

Given a binary string `s` and an integer `k`, a substring is called **beautiful** if it contains exactly `k` occurrences of `'1'`.

Return the **shortest beautiful substring** of `s`.

If there are multiple beautiful substrings having the same minimum length, return the **lexicographically smallest** one.

If no beautiful substring exists, return:

```text
""
```

---

## Example 1

### Input

```text
s = "100011001"
k = 3
```

### Output

```text
"11001"
```

### Explanation

The substring:

```text
"11001"
```

contains exactly three `1`s.

It is the shortest beautiful substring.

---

## Example 2

### Input

```text
s = "1011"
k = 2
```

### Output

```text
"11"
```

### Explanation

The substring:

```text
"11"
```

contains exactly two `1`s and has the minimum possible length.

---

## Example 3

### Input

```text
s = "000"
k = 1
```

### Output

```text
""
```

### Explanation

There are no `1`s in the string, so no substring can contain exactly one `1`.

Therefore, no beautiful substring exists.

---

# Approach

Use the **Sliding Window / Two Pointer** technique.

We need a substring containing exactly `k` ones.

Maintain a window:

```text
[left ... right]
```

and keep track of the number of `1`s inside it.

Whenever the number of ones becomes greater than `k`, move `left` forward until the window contains at most `k` ones.

When the window contains exactly `k` ones, remove unnecessary leading zeros.

This gives the **shortest possible substring for the current right boundary**.

Then compare it with the current answer:

1. Prefer the shorter substring.
2. If lengths are equal, prefer the lexicographically smaller substring.

---

# Algorithm

1. Count the total number of `1`s in `s`.
2. If the total number of ones is less than `k`, return:
   ```text
   ""
   ```
3. Initialize:
   ```text
   left = 0
   count = 0
   ans = s
   ```
4. Traverse the string using `right`.
5. If `s[right] == '1'`, increment `count`.
6. If `count > k`, move `left` forward until `count <= k`.
7. If `count == k`:
   - Move `left` over leading zeros.
   - Create the current substring.
   - Compare it with `ans`.
8. Return `ans`.

---

# Dry Run

Input:

```text
s = "100011001"
k = 3
```

We need exactly:

```text
3 ones
```

---

### Initial Window

Start:

```text
left = 0
count = 0
```

Move `right` through the string.

When we encounter the first `1`:

```text
count = 1
```

After encountering the second `1`:

```text
count = 2
```

After encountering the third `1`:

```text
count = 3
```

Now the window contains exactly `3` ones.

---

### Remove Leading Zeros

If the window starts with unnecessary zeros:

```text
00011001
```

we move:

```text
left++
```

until the first character becomes `1`.

This produces the smallest window for the current `right`.

For example:

```text
00011001
   ↓
11001
```

The substring:

```text
"11001"
```

contains exactly:

```text
3 ones
```

---

### Continue Searching

The window continues moving through the string.

Every time we have exactly `k` ones, we create a candidate substring.

For each candidate:

```text
if shorter → update answer
if same length → choose lexicographically smaller
```

Finally, the best candidate is returned.

---

# Understanding the Code

## Count Total Ones

```java
int ones = 0;

for(char c : s.toCharArray()){
    if(c == '1'){
        ones++;
    }
}
```

Before starting the sliding window, we check whether the string contains at least `k` ones.

---

## No Possible Answer

```java
if(ones < k){
    return "";
}
```

If the complete string contains fewer than `k` ones, no substring can contain `k` ones.

Therefore, return an empty string.

---

## Initialize Sliding Window

```java
int left = 0;
int count = 0;
String ans = s;
```

Here:

```text
left
```

is the left boundary of the window.

```text
count
```

stores the number of ones in the current window.

```text
ans
```

stores the best beautiful substring found so far.

---

## Expand the Window

```java
for(int right = 0; right < s.length(); right++){
```

The `right` pointer expands the window one character at a time.

---

## Count Ones

```java
if(s.charAt(right) == '1'){
    count++;
}
```

Whenever a `1` enters the window, increase the count.

---

## Shrink When Too Many Ones

```java
while(count > k){

    if(s.charAt(left) == '1'){
        count--;
    }

    left++;
}
```

If the window contains more than `k` ones, it is no longer valid.

Move `left` forward until the number of ones becomes at most `k`.

---

## Handle Exactly `k` Ones

```java
if(count == k){
```

Now the current window is a valid beautiful substring.

But it may contain unnecessary leading zeros.

---

## Remove Leading Zeros

```java
while(s.charAt(left) == '0'){
    left++;
}
```

Suppose the current window is:

```text
00011001
```

The leading zeros do not help us satisfy the requirement of exactly `k` ones.

Removing them gives:

```text
11001
```

This produces the shortest valid substring ending at `right`.

---

## Create Candidate

```java
String t = s.substring(left, right + 1);
```

Now `t` is the current beautiful substring.

---

## Compare With Answer

```java
if(t.length() < ans.length() ||
   (t.length() == ans.length() && t.compareTo(ans) < 0)){
    ans = t;
}
```

There are two conditions.

### Condition 1: Shorter

```java
t.length() < ans.length()
```

If the current substring is shorter, it is always better.

---

### Condition 2: Same Length

```java
t.length() == ans.length()
```

If both have the same length, use:

```java
t.compareTo(ans) < 0
```

to check which one is lexicographically smaller.

---

# Why Sliding Window Works?

A beautiful substring must contain exactly `k` ones.

When:

```text
count < k
```

we need to expand the window.

When:

```text
count > k
```

we must shrink the window.

When:

```text
count == k
```

we have a valid candidate.

Removing leading zeros makes the current candidate as short as possible for the current `right`.

Therefore, we can find the shortest valid substring without checking every possible substring.

---

# Important Optimization

The key part is:

```java
while(s.charAt(left) == '0'){
    left++;
}
```

Once we know the window has exactly `k` ones, any leading zeros can be removed.

For example:

```text
0001011
```

contains the required ones, but:

```text
1011
```

is shorter and still contains the same number of ones.

So we always remove unnecessary leading zeros.

---

# Comparison Rule

The problem has two priorities:

```text
1. Minimum length
2. Lexicographically smallest if lengths are equal
```

Therefore:

```java
if(t.length() < ans.length()
    || (t.length() == ans.length()
    && t.compareTo(ans) < 0))
```

correctly implements both conditions.

---

# Complexity Analysis

Let:

```text
n = s.length()
```

### Time Complexity

The `left` and `right` pointers move only forward.

Therefore, the sliding window traversal is:

```text
O(n)
```

However, creating and comparing substrings can take additional time because:

```java
s.substring(left, right + 1)
```

creates a string.

With the given constraints, this is efficient.

Overall:

```text
O(n²)
```

in the worst case due to substring creation and comparison.

---

### Space Complexity

The variables used by the sliding window require:

```text
O(1)
```

excluding the temporary substring objects created during comparisons.

---

# Java Solution

```java
class Solution {

    public String shortestBeautifulSubstring(String s, int k) {

        int ones = 0;

        for(char c : s.toCharArray()){
            if(c == '1'){
                ones++;
            }
        }

        if(ones < k){
            return "";
        }

        int left = 0;
        int count = 0;
        String ans = s;

        for(int right = 0; right < s.length(); right++){

            if(s.charAt(right) == '1'){
                count++;
            }

            while(count > k){

                if(s.charAt(left) == '1'){
                    count--;
                }

                left++;
            }

            if(count == k){

                while(s.charAt(left) == '0'){
                    left++;
                }

                String t = s.substring(left, right + 1);

                if(t.length() < ans.length()
                    || (t.length() == ans.length()
                    && t.compareTo(ans) < 0)){

                    ans = t;
                }
            }
        }

        return ans;
    }
}
```

---

# Key Concepts

- String
- Sliding Window
- Two Pointers
- Substring
- Lexicographical Comparison
- Counting Ones
- Minimum Length

---

# Constraints

- `1 <= s.length <= 100`
- `1 <= k <= s.length`
- `s` consists only of `'0'` and `'1'`.

---

# Learning Outcome

This problem demonstrates how the **Sliding Window** technique can be used when a substring must satisfy an exact frequency condition.

The main idea is:

```text
Expand right
    ↓
Count number of 1s
    ↓
If ones > k → move left
    ↓
If ones == k
    ↓
Remove unnecessary leading zeros
    ↓
Compare with best answer
```

The two important parts are:

```java
while(count > k){
    if(s.charAt(left) == '1'){
        count--;
    }
    left++;
}
```

and:

```java
if(t.length() < ans.length()
    || (t.length() == ans.length()
    && t.compareTo(ans) < 0)){
    ans = t;
}
```

The first maintains a valid sliding window, while the second ensures that we return the **shortest** and, when necessary, **lexicographically smallest** beautiful substring.
```