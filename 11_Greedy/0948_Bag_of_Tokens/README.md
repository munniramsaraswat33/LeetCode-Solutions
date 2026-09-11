# 948. Bag of Tokens

**LeetCode:** [948. Bag of Tokens](https://leetcode.com/problems/bag-of-tokens/)

**Difficulty:** Medium

**Primary Topic:** Greedy

**Pattern:** Sorting + Two Pointers + Greedy

---

## Problem Statement

You are given an integer array `tokens` and an integer `power`.

Each token can be played in one of two ways.

### Face Up

If you have enough power:

```text
power >= tokens[i]
```

you can play the token face up:

```text
power -= tokens[i]
score += 1
```

### Face Down

If you have at least one score:

```text
score > 0
```

you can play a token face down:

```text
power += tokens[i]
score -= 1
```

Return the maximum score you can achieve.

---

## Example 1

### Input

```text
tokens = [100]
power = 50
```

### Output

```text
0
```

### Explanation

The only token costs `100` power, but we have only `50`.

Therefore, we cannot play it face up.

Answer:

```text
0
```

---

## Example 2

### Input

```text
tokens = [100,200]
power = 150
```

### Output

```text
1
```

### Explanation

Play `100` face up:

```text
power = 150 - 100 = 50
score = 1
```

The remaining token costs `200`, which cannot be played face up.

We can stop with:

```text
score = 1
```

---

## Example 3

### Input

```text
tokens = [100,200,300,400]
power = 200
```

### Output

```text
2
```

---

# Approach

Use a **Greedy + Two Pointer** approach.

First, sort the tokens:

```java
Arrays.sort(tokens);
```

Then maintain two pointers:

```text
left  → smallest remaining token
right → largest remaining token
```

The greedy strategy is:

### If we have enough power

Play the **smallest token** face up.

```text
power -= tokens[left]
score++
left++
```

This gives us one score while spending the minimum possible power.

### If we do not have enough power

If we already have some score, play the **largest token** face down.

```text
power += tokens[right]
score--
right--
```

This sacrifices one score but gives us the maximum possible power.

This strategy maximizes our ability to continue playing tokens.

---

# Intuition

Suppose:

```text
tokens = [100,200,300,400]
power = 200
```

The sorted array is already:

```text
[100,200,300,400]
```

Start with:

```text
power = 200
score = 0
```

We can afford `100`, so play the smallest token face up:

```text
power = 100
score = 1
```

Now we can afford `200` only if we had enough power, but we have only `100`.

We already have one score, so we can sacrifice one score and take the largest token:

```text
power += 400
power = 500
score = 0
```

Now we can again use small tokens to build score.

The important greedy idea is:

```text
Gain score → use smallest token
Need power → sacrifice score using largest token
```

---

# Why Smallest Token for Face Up?

When we want to increase our score, we should spend as little power as possible.

Suppose:

```text
tokens = [100,200,300]
power = 250
```

We could use `100` or `200`.

Using `100` gives:

```text
power = 150
score = 1
```

Using `200` gives:

```text
power = 50
score = 1
```

Both give one score, but using the smaller token preserves more power.

Therefore:

```text
Face Up → Smallest Token
```

is the greedy choice.

---

# Why Largest Token for Face Down?

When we need more power, we must sacrifice one score.

We want to gain as much power as possible from that sacrifice.

Suppose:

```text
tokens = [100,200,400]
```

If we play a token face down:

```text
+100 power
```

or:

```text
+200 power
```

or:

```text
+400 power
```

The largest token gives the maximum power.

Therefore:

```text
Face Down → Largest Token
```

is the greedy choice.

---

# Algorithm

1. Sort the `tokens` array.
2. Initialize:
   ```text
   left = 0
   right = n - 1
   score = 0
   maxScore = 0
   ```
3. While `left <= right`:
   - If current power is enough for `tokens[left]`:
     - Play the smallest token face up.
     - Increase score.
     - Move `left`.
     - Update `maxScore`.
   - Otherwise, if we have score and there are at least two pointers:
     - Play `tokens[right]` face down.
     - Increase power.
     - Decrease score.
     - Move `right`.
   - Otherwise, stop.
4. Return `maxScore`.

---

# Dry Run

Consider:

```text
tokens = [100,200,300,400]
power = 200
```

After sorting:

```text
[100,200,300,400]
```

Initially:

```text
left = 0
right = 3
power = 200
score = 0
maxScore = 0
```

---

## Step 1

Smallest token:

```text
tokens[left] = 100
```

We have enough power:

```text
200 >= 100
```

Play face up.

```text
power = 200 - 100 = 100
score = 1
left = 1
maxScore = 1
```

---

## Step 2

Smallest remaining token:

```text
200
```

But:

```text
100 < 200
```

We cannot play it face up.

We have:

```text
score = 1
```

so we can sacrifice one score.

Take the largest token:

```text
400
```

Play it face down:

```text
power = 100 + 400 = 500
score = 0
right = 2
```

---

## Step 3

Smallest remaining token:

```text
200
```

Now:

```text
500 >= 200
```

Play face up:

```text
power = 500 - 200 = 300
score = 1
left = 2
maxScore = 1
```

---

## Step 4

Next token:

```text
300
```

We have:

```text
300 >= 300
```

Play face up:

```text
power = 0
score = 2
left = 3
maxScore = 2
```

---

## Step 5

Now:

```text
left > right
```

All relevant tokens have been processed.

Final:

```text
maxScore = 2
```

Answer:

```text
2
```

---

# Java Solution

```java
class Solution {
    public int bagOfTokensScore(int[] tokens, int power) {

        Arrays.sort(tokens);

        int left = 0;
        int right = tokens.length - 1;

        int score = 0;
        int maxScore = 0;

        while(left <= right){

            if(power >= tokens[left]){
                score++;
                power -= tokens[left];
                left++;

                maxScore = Math.max(score, maxScore);
            }

            else if(score > 0 && left < right){
                power += tokens[right];
                right--;
                score--;
            }

            else{
                break;
            }
        }

        return maxScore;
    }
}
```

---

# Code Explanation

## 1. Sort the Tokens

```java
Arrays.sort(tokens);
```

Sorting allows us to efficiently choose:

- The smallest token using `left`.
- The largest token using `right`.

---

## 2. Initialize Two Pointers

```java
int left = 0;
int right = tokens.length - 1;
```

Initially:

```text
left  → smallest token
right → largest token
```

---

## 3. Track Score

```java
int score = 0;
int maxScore = 0;
```

`score` is the current score.

`maxScore` stores the highest score achieved during the process.

---

# Face Up Operation

```java
if(power >= tokens[left]){
```

If we can afford the smallest remaining token, use it face up.

```java
score++;
power -= tokens[left];
left++;
```

This:

- Increases score by `1`.
- Decreases power.
- Removes the token from consideration.

---

## Update Maximum Score

```java
maxScore = Math.max(score, maxScore);
```

It is important to store the maximum score.

The current score may later decrease when we play a token face down.

For example:

```text
score = 2
```

could later become:

```text
score = 1
```

but the maximum achieved was still `2`.

---

# Face Down Operation

```java
else if(score > 0 && left < right){
```

If we cannot afford the smallest token:

```text
power < tokens[left]
```

we can sacrifice one score if:

```text
score > 0
```

Then:

```java
power += tokens[right];
score--;
right--;
```

We use the largest remaining token to obtain the maximum possible power.

---

# Why `left < right`?

The code checks:

```java
left < right
```

before using a token face down.

This ensures that there is still another token available after the sacrifice, allowing the gained power to be useful for another face-up operation.

If the same final token would simply be sacrificed without producing a better score, the algorithm stops instead.

---

# Breaking the Loop

```java
else{
    break;
}
```

There are two possible reasons:

1. We do not have enough power to play the smallest token.
2. We cannot sacrifice a score in a useful way.

At this point, no further improvement is possible under the greedy strategy.

---

# Why Does the Greedy Strategy Work?

There are two competing resources:

```text
Power
Score
```

A face-up operation:

```text
Power ↓
Score ↑
```

A face-down operation:

```text
Power ↑
Score ↓
```

Whenever we want score, the best choice is to spend the minimum power:

```text
smallest token
```

Whenever we need power, the best choice is to gain the maximum power:

```text
largest token
```

Therefore:

```text
Face Up   → Smallest
Face Down → Largest
```

This locally optimal strategy leads to the maximum possible score.

---

# Two Pointer Pattern

After sorting:

```text
[smallest ... largest]
     ↑          ↑
    left       right
```

The pointers move toward each other.

### Face Up

```text
tokens[left]
left++
```

### Face Down

```text
tokens[right]
right--
```

Therefore, every token is processed at most once.

---

# Complexity Analysis

Let:

```text
n = tokens.length
```

## Sorting

```text
O(n log n)
```

---

## Two-Pointer Traversal

Each pointer moves at most `n` positions.

Therefore:

```text
O(n)
```

---

## Total Time Complexity

```text
O(n log n)
```

The sorting operation dominates the traversal.

---

## Space Complexity

Ignoring the space used internally by Java's sorting implementation:

```text
O(1)
```

additional space is used by the algorithm itself.

---

# Key Concepts

## 1. Greedy

Make the best local choice at every step:

```text
Need score → cheapest token
Need power → most expensive token
```

---

## 2. Sorting

Sorting makes it possible to quickly access the smallest and largest tokens.

---

## 3. Two Pointers

Use:

```text
left
right
```

to process tokens from both ends.

---

## 4. Resource Management

The problem balances:

```text
Power ↔ Score
```

Face-up trades power for score.

Face-down trades score for power.

---

# Pattern Recognition

When you see a problem where you can:

- Gain points by spending a resource.
- Give up points to recover the resource.
- Want to maximize the final score.

Think about:

```text
Greedy + Sorting + Two Pointers
```

The common strategy is:

```text
Use the cheapest item to gain points.
Use the most expensive item to recover resources.
```

---

# Common Mistakes

## Mistake 1: Always Using the Largest Token

Using the largest token face up wastes power.

Instead:

```text
Face Up → Smallest Token
```

---

## Mistake 2: Using the Smallest Token Face Down

If we sacrifice a score, we want maximum power.

Therefore:

```text
Face Down → Largest Token
```

---

## Mistake 3: Returning `score` Instead of `maxScore`

The score can decrease after a face-down operation.

For example:

```text
score = 2
```

then:

```text
score = 1
```

The maximum score is still:

```text
2
```

Therefore, maintain:

```java
maxScore
```

---

## Mistake 4: Forgetting to Sort

Without sorting, we cannot efficiently choose the smallest and largest tokens.

---

# Edge Cases

### No Power

```text
tokens = [100]
power = 0
```

No token can be played.

Answer:

```text
0
```

---

### Enough Power for Every Token

If the initial power is large enough, play tokens face up one by one.

The maximum score becomes the number of tokens.

---

### One Token

If:

```text
power >= tokens[0]
```

we can get:

```text
score = 1
```

Otherwise:

```text
score = 0
```

---

### Sacrifice Is Not Useful

If we cannot afford the smallest token and there is no useful way to sacrifice a score, the loop terminates.

---

# Learning Outcome

After solving this problem, you should understand:

- How sorting enables greedy decisions.
- How two pointers can process elements from both ends.
- Why the smallest value should be used to gain score.
- Why the largest value should be used to regain power.
- How to maintain the maximum score separately from the current score.
- How greedy decisions can optimize resource usage.

---

# Final Takeaway

The core greedy rule is:

```text
If you have enough power:
    Use the smallest token.
    Gain 1 score.

Otherwise, if you have score:
    Use the largest token.
    Gain power but lose 1 score.
```

In short:

```text
Face Up   → Smallest Token → Gain Score
Face Down → Largest Token  → Gain Power
```

Combined with:

```text
Sorting + Two Pointers
```

this gives an efficient solution.

**Time Complexity:** `O(n log n)`

**Space Complexity:** `O(1)` auxiliary space