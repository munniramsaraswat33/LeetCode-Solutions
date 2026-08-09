# 4014. Minimum Total Price After Applying Discounts

> **Difficulty:** Medium  
> **Topics:** Greedy, Sorting, Arrays

---

## Problem Statement

You are given two integer arrays:

- `prices` — the prices of items.
- `discounts` — discount percentages.

Each discount can be applied to **at most one item**, and each item can receive **at most one discount**.

If a discount of `d%` is applied to an item with price `p`, the final price is:

```text
p × (100 - d) / 100
```

The final price is not rounded.

Return the **minimum possible total price** after assigning discounts optimally.

---

## Example 1

### Input

```text
prices = [10,30,21]
discounts = [50,60]
```

### Output

```text
32.50000
```

### Explanation

Sort the prices and discounts:

```text
Prices:
[10,21,30]

Discounts:
[50,60]
```

Apply the largest discount to the most expensive item:

```text
30 × (100 - 60) / 100
= 12
```

Apply the second largest discount:

```text
21 × (100 - 50) / 100
= 10.5
```

The remaining item costs:

```text
10
```

Total:

```text
12 + 10.5 + 10
= 32.5
```

---

## Example 2

### Input

```text
prices = [100,70]
discounts = [10,40,50]
```

### Output

```text
92.00000
```

### Explanation

Only two items exist, so we use the two most useful discounts.

```text
100 × (100 - 50) / 100 = 50

70 × (100 - 40) / 100 = 42
```

Total:

```text
50 + 42 = 92
```

The `10%` discount is not needed.

---

## Example 3

### Input

```text
prices = [7,3,9]
discounts = [100,100]
```

### Output

```text
3.00000
```

### Explanation

Apply the two `100%` discounts to the two most expensive items:

```text
9 → 0
7 → 0
```

The remaining item:

```text
3
```

Total:

```text
3
```

---

# Approach

This problem can be solved using a **Greedy Algorithm**.

The key observation is:

> A larger discount should be applied to a more expensive item.

For an item with price `p` and discount `d`, the amount saved is:

```text
p × d / 100
```

To maximize the total savings, we should pair:

```text
Largest price ↔ Largest discount
Second largest price ↔ Second largest discount
...
```

Therefore:

1. Sort `prices` in ascending order.
2. Sort `discounts` in ascending order.
3. Start from the largest price and largest discount.
4. Apply discounts while both arrays still have elements.
5. Any remaining items receive no discount.

---

# Why Greedy Works

Consider two prices:

```text
p1 > p2
```

and two discounts:

```text
d1 > d2
```

Compare the two possible assignments.

### Assignment 1

```text
p1 → d1
p2 → d2
```

Total savings:

```text
p1 × d1 + p2 × d2
```

### Assignment 2

```text
p1 → d2
p2 → d1
```

Total savings:

```text
p1 × d2 + p2 × d1
```

The difference is:

```text
(p1 × d1 + p2 × d2)
-
(p1 × d2 + p2 × d1)

= (p1 - p2)(d1 - d2)
```

Since both differences are positive:

```text
(p1 - p2)(d1 - d2) > 0
```

Therefore, pairing the larger price with the larger discount always gives greater savings.

Hence, sorting both arrays and pairing them from largest to largest is optimal.

---

# Algorithm

1. Sort `prices`.
2. Sort `discounts`.
3. Initialize:

```text
ans = 0
```

4. Start from the last elements of both arrays.
5. While both arrays have unused elements:
   - Apply the current largest discount to the current largest price.
   - Add the discounted price to `ans`.
6. Add all remaining prices without discounts.
7. Return `ans`.

---

# Dry Run

### Input

```text
prices = [10,30,21]
discounts = [50,60]
```

After sorting:

```text
prices    = [10,21,30]
discounts = [50,60]
```

### Step 1

```text
price = 30
discount = 60
```

Final price:

```text
30 × 40 / 100 = 12
```

---

### Step 2

```text
price = 21
discount = 50
```

Final price:

```text
21 × 50 / 100 = 10.5
```

---

### Remaining Item

```text
10
```

No discount is available.

---

### Total

```text
12 + 10.5 + 10
= 32.5
```

Answer:

```text
32.50000
```

---

# Complexity Analysis

Let:

```text
n = prices.length
m = discounts.length
```

### Time Complexity

Sorting the two arrays takes:

```text
O(n log n + m log m)
```

The matching process takes:

```text
O(min(n,m))
```

Therefore, the overall complexity is:

```text
O(n log n + m log m)
```

---

### Space Complexity

The solution sorts the arrays in place and uses only a few variables.

```text
O(1)
```

extra space, excluding the sorting implementation's internal stack/overhead.

---

# Java Solution

```java
class Solution {

    public double minPrice(int[] prices, int[] discounts) {

        Arrays.sort(prices);
        Arrays.sort(discounts);

        double ans = 0.0;

        int i = prices.length - 1;
        int j = discounts.length - 1;

        while (i >= 0 && j >= 0) {

            ans += (prices[i] * (100 - discounts[j])) / 100.0;

            i--;
            j--;
        }

        while (i >= 0) {

            ans += prices[i];

            i--;
        }

        return ans;
    }
}
```

---

# Key Concepts

- Greedy Algorithm
- Sorting
- Arrays
- Maximizing Savings
- Pairing Strategy

---

# Constraints

- `1 <= prices.length, discounts.length <= 10⁵`
- `1 <= prices[i] <= 10⁵`
- `1 <= discounts[j] <= 100`

---

# Learning Outcome

This problem demonstrates an important **Greedy pairing strategy**.

The key insight is that the discount amount saved is proportional to both the **item price** and the **discount percentage**:

```text
Savings = price × discount / 100
```

Therefore, pairing the largest prices with the largest discounts maximizes total savings and minimizes the final total price.

The solution achieves:

```text
O(n log n + m log m)
```

time complexity with constant extra space.