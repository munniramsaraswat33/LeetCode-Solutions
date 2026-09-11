# 17. Letter Combinations of a Phone Number

**LeetCode:** https://leetcode.com/problems/letter-combinations-of-a-phone-number/

**Difficulty:** Medium

**Topics:** String, Backtracking, Recursion

---

## Problem Statement

Given a string containing digits from `2-9`, return all possible letter combinations that the number could represent.

The mapping of digits to letters is the same as on a telephone keypad:

```text
2 → abc
3 → def
4 → ghi
5 → jkl
6 → mno
7 → pqrs
8 → tuv
9 → wxyz
```

Return the answer in any order.

If the input string is empty, return an empty list.

---

## Example 1

### Input

```text
digits = "23"
```

### Output

```text
["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

### Explanation

Digit `2` can represent:

```text
a, b, c
```

Digit `3` can represent:

```text
d, e, f
```

Therefore, all possible combinations are:

```text
ad
ae
af
bd
be
bf
cd
ce
cf
```

---

## Example 2

### Input

```text
digits = ""
```

### Output

```text
[]
```

### Explanation

There are no digits, so there are no possible combinations.

---

## Example 3

### Input

```text
digits = "2"
```

### Output

```text
["a","b","c"]
```

### Explanation

Digit `2` represents:

```text
a, b, c
```

So there are three possible combinations.

---

# Approach

This problem is a classic **Backtracking** problem.

Each digit represents multiple possible characters.

For every digit, we choose one character and continue processing the next digit.

For example:

```text
digits = "23"
```

The decision tree looks like:

```text
                ""
             /   |   \
            a    b    c
          /|\   /|\   /|\
         ad ae af bd be bf cd ce cf
```

Every path from the root to a leaf represents one complete combination.

---

# Intuition

At every position in the input string, we have several choices.

For:

```text
digits = "23"
```

the first digit is `2`, so we can choose:

```text
a
b
c
```

Suppose we choose `a`.

Now we process the next digit `3`:

```text
d
e
f
```

This produces:

```text
ad
ae
af
```

Then we go back and choose `b`:

```text
bd
be
bf
```

Then choose `c`:

```text
cd
ce
cf
```

This process of:

```text
Choose
→ Explore
→ Return
→ Choose another option
```

is called **backtracking**.

---

# Why Backtracking?

We need to generate **all possible combinations**.

At every digit, we have multiple choices.

Backtracking is suitable because it systematically explores every possible choice.

The general pattern is:

```text
Choose a character
        ↓
Add it to current combination
        ↓
Recursively process next digit
        ↓
Remove/undo the choice
        ↓
Try the next character
```

In this implementation, the current combination is represented by the `String combi` parameter.

---

# Phone Mapping

The solution uses:

```java
String[] phone_map = {
    "abc",
    "def",
    "ghi",
    "jkl",
    "mno",
    "pqrs",
    "tuv",
    "wxyz"
};
```

The array starts with digit `2`.

Therefore:

```text
phone_map[0] → "abc" → digit 2
phone_map[1] → "def" → digit 3
phone_map[2] → "ghi" → digit 4
phone_map[3] → "jkl" → digit 5
phone_map[4] → "mno" → digit 6
phone_map[5] → "pqrs" → digit 7
phone_map[6] → "tuv" → digit 8
phone_map[7] → "wxyz" → digit 9
```

---

# Converting Digit to Array Index

The code uses:

```java
digits.charAt(0) - '2'
```

For example:

```text
'2' - '2' = 0
'3' - '2' = 1
'4' - '2' = 2
```

Therefore:

```java
phone_map[digits.charAt(0) - '2']
```

returns the correct letters for the current digit.

---

# Algorithm

1. Check if `digits` is empty.
2. If it is empty, return an empty list.
3. Create the phone keypad mapping.
4. Create an empty result list.
5. Start backtracking with:
   - An empty current combination.
   - The complete digits string.
   - The phone mapping.
   - The result list.
6. In the recursive function:
   - If no digits remain, add the current combination to the result.
   - Otherwise:
     - Get the letters corresponding to the first digit.
     - Try every possible letter.
     - Add the letter to the current combination.
     - Recursively process the remaining digits.
7. Return the result list.

---

# Dry Run

Consider:

```text
digits = "23"
```

Initially:

```text
combi = ""
digits = "23"
```

The first digit is `2`.

```text
2 → abc
```

So we try:

```text
a
b
c
```

---

## Choice 1: `a`

Current combination:

```text
a
```

Remaining digits:

```text
3
```

Digit `3` maps to:

```text
def
```

### Choose `d`

```text
combi = "ad"
```

No digits remain.

Add:

```text
"ad"
```

to the result.

### Choose `e`

```text
combi = "ae"
```

Add:

```text
"ae"
```

### Choose `f`

```text
combi = "af"
```

Add:

```text
"af"
```

So far:

```text
["ad","ae","af"]
```

---

## Choice 2: `b`

Now choose `b` for digit `2`.

```text
combi = "b"
```

Process digit `3`:

```text
d → "bd"
e → "be"
f → "bf"
```

Result becomes:

```text
["ad","ae","af","bd","be","bf"]
```

---

## Choice 3: `c`

Now choose `c`.

```text
combi = "c"
```

Process digit `3`:

```text
d → "cd"
e → "ce"
f → "cf"
```

Final result:

```text
["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

---

# Recursion Tree

For `digits = "23"`:

```text
                         ""
                    /     |     \
                   a      b      c
                 / | \   / | \  / | \
                d  e  f d  e  f d  e  f
                |  |  | |  |  | |  |  |
               ad ae af bd be bf cd ce cf
```

Every leaf is a complete answer.

---

# Base Case

The recursive function contains:

```java
if(digits.isEmpty()){
    output.add(combi);
}
```

When there are no digits left, the current combination is complete.

For example:

```text
combi = "ad"
digits = ""
```

Since there are no remaining digits:

```text
"ad"
```

is added to the result.

This is the stopping condition of the recursion.

---

# Recursive Case

If digits are still remaining:

```java
String Letters = phone_map[digits.charAt(0) - '2'];
```

We get all possible letters for the current digit.

Then:

```java
for(char letter : Letters.toCharArray()){
    backtrack(
        combi + letter,
        digits.substring(1),
        phone_map,
        output
    );
}
```

For every possible letter:

1. Append it to the current combination.
2. Remove the first digit from the remaining input.
3. Recursively solve the smaller problem.

---

# Java Solution

```java
class Solution {
    public List<String> letterCombinations(String digits) {
        if(digits.isEmpty()){
            return Collections.emptyList();
        }

        String[] phone_map = {
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
        };

        List<String> output = new ArrayList<>();

        backtrack("", digits, phone_map, output);

        return output;
    }

    public void backtrack(
        String combi,
        String digits,
        String[] phone_map,
        List<String> output
    ){
        if(digits.isEmpty()){
            output.add(combi);
        }
        else{
            String Letters = phone_map[digits.charAt(0) - '2'];

            for(char letter : Letters.toCharArray()){
                backtrack(
                    combi + letter,
                    digits.substring(1),
                    phone_map,
                    output
                );
            }
        }
    }
}
```

---

# Code Explanation

## 1. Empty Input Check

```java
if(digits.isEmpty()){
    return Collections.emptyList();
}
```

If the input contains no digits, there are no combinations.

Therefore, we immediately return an empty list.

---

## 2. Create Phone Mapping

```java
String[] phone_map = {
    "abc",
    "def",
    "ghi",
    "jkl",
    "mno",
    "pqrs",
    "tuv",
    "wxyz"
};
```

This represents the letters associated with digits `2` through `9`.

---

## 3. Create Result List

```java
List<String> output = new ArrayList<>();
```

This list stores every valid combination generated by backtracking.

---

## 4. Start Backtracking

```java
backtrack("", digits, phone_map, output);
```

The initial combination is empty:

```text
combi = ""
```

The recursion then builds the combination one character at a time.

---

## 5. Get Letters for Current Digit

```java
String Letters = phone_map[digits.charAt(0) - '2'];
```

Suppose:

```text
digits.charAt(0) = '7'
```

Then:

```text
'7' - '2' = 5
```

So:

```text
phone_map[5] = "pqrs"
```

---

## 6. Try Every Possible Character

```java
for(char letter : Letters.toCharArray()){
```

If the current digit is `7`, the loop tries:

```text
p
q
r
s
```

Each character creates a separate recursive branch.

---

## 7. Recursive Call

```java
backtrack(
    combi + letter,
    digits.substring(1),
    phone_map,
    output
);
```

The selected letter is appended to the current combination.

The first digit is removed from the remaining input.

For example:

```text
combi = "a"
digits = "3"
```

After choosing `d`:

```text
combi = "ad"
digits = ""
```

Since there are no digits remaining, `"ad"` is added to the answer.

---

# Backtracking Pattern

This problem follows the general backtracking pattern:

```text
function backtrack(state):
    if state is complete:
        add answer
        return

    for every possible choice:
        choose
        backtrack(next state)
        undo choice
```

In this particular implementation, the "undo" is handled implicitly because:

```java
combi + letter
```

creates a new `String` instead of modifying a shared mutable object.

---

# Complexity Analysis

Let `n` be the number of digits.

Each digit can represent either 3 or 4 letters.

The maximum number of combinations is:

```text
4^n
```

## Time Complexity

We generate every possible combination.

Therefore the output itself can contain:

```text
O(4^n)
```

strings.

Each generated string has length `n`.

So the total work for constructing the output is approximately:

```text
O(n × 4^n)
```

The use of:

```java
digits.substring(1)
```

and:

```java
combi + letter
```

also creates new strings during recursion.

Thus, considering string construction, the practical complexity is:

```text
O(n × 4^n)
```

---

## Space Complexity

The recursion depth is at most `n`.

Therefore the recursion stack uses:

```text
O(n)
```

The output itself requires:

```text
O(n × 4^n)
```

space because there can be up to `4^n` combinations, each of length `n`.

Therefore:

```text
Auxiliary recursion space = O(n)

Output space = O(n × 4^n)
```

---

# Key Concepts / Patterns

## 1. Backtracking

The primary technique used in this problem is **Backtracking**.

We explore every possible character choice for every digit.

---

## 2. Recursion

Each recursive call processes one fewer digit:

```text
"23"
 ↓
"3"
 ↓
""
```

This naturally creates a recursive solution.

---

## 3. Decision Tree

Every digit creates multiple branches.

For example:

```text
2 → a,b,c
3 → d,e,f
```

creates:

```text
a → d,e,f
b → d,e,f
c → d,e,f
```

This forms a decision tree.

---

## 4. String Construction

The current combination is maintained using:

```java
combi + letter
```

At every recursive level, one character is added.

---

## 5. Base Case + Recursive Case

The recursion has two clear cases:

### Base Case

```java
if(digits.isEmpty())
```

The combination is complete.

### Recursive Case

```java
for(char letter : Letters.toCharArray())
```

Try every possible letter.

---

# Important Observation

The number of combinations depends on the number of letters mapped to each digit.

Digits:

```text
2,3,4,5,6,8
```

have 3 letters.

Digits:

```text
7,9
```

have 4 letters.

Therefore, the maximum number of combinations for `n` digits is:

```text
4^n
```

This is why generating all combinations inherently requires exponential time.

---

# Why This Belongs in Backtracking

Although the problem involves strings, the primary technique is not string manipulation.

The main operation is:

```text
Choose one option
→ recursively explore
→ try another option
```

Therefore, the correct primary classification is:

```text
13_Backtracking
```

---

# Pattern Recognition

Whenever a problem asks you to:

- Generate all possible combinations.
- Generate all possible arrangements.
- Try multiple choices at every step.
- Explore a decision tree.
- Find all valid configurations.

you should consider:

```text
Backtracking
```

The common structure is:

```text
                    Start
                   /  |  \
                Choice Choice Choice
                 / \    ...
             Choice Choice
                 |
              Complete
```

This problem is a direct example of that pattern.

---

# Learning Outcome

After solving this problem, you should understand:

- How to implement backtracking using recursion.
- How to generate all combinations.
- How to represent a decision tree recursively.
- How to use a mapping array for keypad characters.
- How to identify a recursion base case.
- How to build the answer one character at a time.
- Why the number of combinations grows exponentially.
- How to analyze output-dependent complexity.

---

# Final Takeaway

The core idea is to treat every digit as a set of choices.

For each digit:

```text
Choose one character
        ↓
Move to the next digit
        ↓
Repeat
        ↓
When all digits are processed
        ↓
Store the combination
```

For example:

```text
digits = "23"

2 → a,b,c
3 → d,e,f
```

produces:

```text
ad ae af
bd be bf
cd ce cf
```

The solution therefore uses:

```text
Backtracking + Recursion + Decision Tree
```

with:

```text
Time Complexity:  O(n × 4^n)
Auxiliary Space: O(n)
Output Space:    O(n × 4^n)
```

The most important pattern to remember is:

```text
Choose → Explore → Complete → Backtrack
```