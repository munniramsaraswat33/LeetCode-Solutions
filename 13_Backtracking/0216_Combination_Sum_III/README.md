# 216. Combination Sum III

> **Difficulty:** Medium  
> **Topics:** Array, Backtracking, Recursion

---

## Problem Statement

Find all valid combinations of `k` numbers that add up to `n` using only numbers from:

```text
1 to 9
```

Each number can be used **at most once**.

Return a list of all possible combinations.

The solution set must not contain duplicate combinations.

---

## Example 1

### Input

```text
k = 3
n = 7
```

### Output

```text
[[1,2,4]]
```

### Explanation

The only combination of 3 distinct numbers from `1` to `9` whose sum is `7` is:

```text
1 + 2 + 4 = 7
```

---

## Example 2

### Input

```text
k = 3
n = 9
```

### Output

```text
[[1,2,6],
 [1,3,5],
 [2,3,4]]
```

### Explanation

All these combinations contain exactly 3 numbers and their sum is `9`.

```text
1 + 2 + 6 = 9
1 + 3 + 5 = 9
2 + 3 + 4 = 9
```

---

## Example 3

### Input

```text
k = 4
n = 1
```

### Output

```text
[]
```

### Explanation

The smallest possible sum of 4 different numbers is:

```text
1 + 2 + 3 + 4 = 10
```

Therefore, it is impossible to make the sum `1`.

---

# Approach

Use **Backtracking**.

We need to select exactly `k` different numbers from `1` to `9`.

At every step:

1. Choose a number.
2. Add it to the current combination.
3. Reduce `n` by that number.
4. Reduce `k` by `1`.
5. Continue selecting numbers greater than the current number.
6. If the choice does not lead to a solution, remove the number and try another choice.

We start from `1` and move forward so that:

- Numbers are never reused.
- Duplicate combinations are avoided.

---

# Algorithm

1. Create an empty result list.
2. Call the backtracking function with:
   ```text
   k
   n
   start = 1
   ```
3. In the backtracking function:
   - If `k == 0`:
     - If `n == 0`, add the current combination to the answer.
     - Return.
4. Loop from `start` to `9`.
5. If the current number is greater than `n`, stop the loop.
6. Add the current number to the combination.
7. Recursively call:
   ```text
   k - 1
   n - current number
   current number + 1
   ```
8. Remove the last number to backtrack.
9. Return the result.

---

# Dry Run

Input:

```text
k = 3
n = 7
```

Start:

```text
list = []
start = 1
```

Choose `1`:

```text
list = [1]
k = 2
n = 6
```

Choose `2`:

```text
list = [1,2]
k = 1
n = 4
```

Choose `4`:

```text
list = [1,2,4]
k = 0
n = 0
```

Both conditions are satisfied:

```text
k == 0
n == 0
```

So add:

```text
[1,2,4]
```

Then backtrack:

```text
[1,2]
```

Try another number.

Eventually all possible combinations are checked.

Final answer:

```text
[[1,2,4]]
```

---

# Understanding the Code

## Main Function

```java
public List<List<Integer>> combinationSum3(int k, int n) {
```

This function creates the result list and starts the backtracking process.

---

## Initialize Result

```java
List<List<Integer>> ans = new ArrayList<>();
```

`ans` stores all valid combinations.

---

## Start Backtracking

```java
backtrack(k, n, 1, new ArrayList<>(), ans);
```

The parameters are:

```text
k       → numbers still required
n       → sum still required
1       → starting number
list    → current combination
ans     → final result
```

---

## Base Case

```java
if(k == 0){
    if(n == 0){
        ans.add(new ArrayList<>(list));
    }
    return;
}
```

When `k` becomes `0`, exactly `k` numbers have been selected.

If the remaining sum is also `0`, the combination is valid.

A copy is added:

```java
ans.add(new ArrayList<>(list));
```

This is important because `list` is modified during backtracking.

---

## Try Numbers from Start

```java
for(int i=j; i<10; i++){
```

Numbers from `1` to `9` are considered.

Starting from `j` ensures that numbers are selected in increasing order.

---

## Pruning

```java
if(i > n){
    break;
}
```

If the current number is already greater than the remaining sum, larger numbers cannot work either.

So we stop the loop.

---

## Choose a Number

```java
list.add(i);
```

Add the current number to the combination.

---

## Recursive Call

```java
backtrack(k-1, n-i, i+1, list, ans);
```

After selecting `i`:

```text
k → k - 1
n → n - i
start → i + 1
```

`i + 1` ensures that the same number cannot be selected again.

---

## Backtracking

```java
list.remove(list.size()-1);
```

After exploring all combinations starting with `i`, remove it and try the next number.

This is the main backtracking step.

---

# Why `i + 1`?

The problem says each number can be used only once.

Suppose we choose:

```text
2
```

The next recursive call starts from:

```text
3
```

instead of `2`.

Therefore:

```text
2
```

cannot be selected again.

It also ensures combinations are generated in increasing order.

For example:

```text
[1,2,4]
```

is generated, but:

```text
[2,1,4]
```

is never generated.

Thus duplicate combinations are avoided.

---

# Backtracking Pattern

The general pattern used here is:

```text
Choose
  ↓
Explore
  ↓
Undo choice
```

In code:

```java
list.add(i);

backtrack(...);

list.remove(list.size()-1);
```

This pattern is commonly used in:

- Subsets
- Combinations
- Permutations
- Combination Sum
- N-Queens
- Constraint-based problems

---

# Complexity Analysis

There are only `9` possible numbers, so the search space is small.

### Time Complexity

The backtracking explores possible combinations of numbers from `1` to `9`.

In the worst case, the number of combinations is bounded by:

```text
O(2^9)
```

More specifically, only combinations of size `k` are considered:

```text
O(C(9,k))
```

---

### Space Complexity

The recursion depth is at most `k`.

The current combination also stores at most `k` numbers.

Therefore:

```text
O(k)
```

auxiliary space, excluding the output list.

---

# Java Solution

```java
class Solution {

    public List<List<Integer>> combinationSum3(int k, int n) {

        List<List<Integer>> ans = new ArrayList<>();

        backtrack(k, n, 1, new ArrayList<>(), ans);

        return ans;
    }

    public static void backtrack(
        int k,
        int n,
        int j,
        ArrayList<Integer> list,
        List<List<Integer>> ans
    ){

        if(k == 0){

            if(n == 0){
                ans.add(new ArrayList<>(list));
            }

            return;
        }

        for(int i = j; i < 10; i++){

            if(i > n){
                break;
            }

            list.add(i);

            backtrack(
                k - 1,
                n - i,
                i + 1,
                list,
                ans
            );

            list.remove(list.size() - 1);
        }
    }
}
```

---

# Key Concepts

- Backtracking
- Recursion
- ArrayList
- Combination Generation
- Recursion Tree
- Pruning
- Choose → Explore → Undo
- Avoiding Duplicate Combinations

---

# Constraints

- `2 <= k <= 9`
- `1 <= n <= 60`
- Only numbers from `1` to `9` can be used.
- Each number can be used at most once.

---

# Learning Outcome

This problem demonstrates how **Backtracking** can be used to generate combinations under multiple constraints.

The main idea is:

```text
Choose a number
      ↓
Reduce k
      ↓
Reduce required sum
      ↓
Continue from next number
      ↓
If valid → store combination
      ↓
Backtrack and try another number
```

The important code pattern is:

```java
list.add(i);
backtrack(k - 1, n - i, i + 1, list, ans);
list.remove(list.size() - 1);
```

This solution ensures:

```text
✓ Exactly k numbers
✓ Sum equals n
✓ Numbers are from 1 to 9
✓ No number is reused
✓ No duplicate combinations
```

The search space is very small because only `9` numbers are available.