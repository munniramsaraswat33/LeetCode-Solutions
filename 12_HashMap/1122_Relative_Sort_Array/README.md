# 1122. Relative Sort Array

> **Difficulty:** Easy  
> **Topics:** Array, Hash Table, Counting Sort, Sorting

---

## Problem Statement

Given two arrays `arr1` and `arr2`, sort the elements of `arr1` according to the following rules:

1. The elements that appear in `arr2` must appear in `arr1` in the **same relative order as they appear in `arr2`**.
2. The elements that are present in `arr1` but **not present in `arr2`** must be placed at the end.
3. The remaining elements at the end must be sorted in **ascending order**.

Return the resulting array.

---

## Example 1

### Input

```text
arr1 = [2,3,1,3,2,4,6,7,9,2,19]
arr2 = [2,1,4,3,9,6]
```

### Output

```text
[2,2,2,1,4,3,3,9,6,7,19]
```

### Explanation

The elements appearing in `arr2` are placed first according to the order of `arr2`:

```text
2 → 2,2,2
1 → 1
4 → 4
3 → 3,3
9 → 9
6 → 6
```

So we get:

```text
[2,2,2,1,4,3,3,9,6]
```

The remaining elements of `arr1` are:

```text
7,19
```

They are sorted in ascending order:

```text
[7,19]
```

Final result:

```text
[2,2,2,1,4,3,3,9,6,7,19]
```

---

## Example 2

### Input

```text
arr1 = [28,6,22,8,44,17]
arr2 = [22,28,8,6]
```

### Output

```text
[22,28,8,6,17,44]
```

### Explanation

Elements present in `arr2` are placed according to:

```text
22,28,8,6
```

The remaining elements are:

```text
17,44
```

Sorted ascending:

```text
17,44
```

Therefore:

```text
[22,28,8,6,17,44]
```

---

# Approach

Use **Counting Sort**.

The values of `arr1` are limited to:

```text
0 <= arr1[i] <= 1000
```

Therefore, instead of using a HashMap or sorting the entire array, we can create a frequency array:

```java
int[] count = new int[1001];
```

For every value in `arr1`, store its frequency.

Then:

1. Process every value in `arr2`.
2. Place all its occurrences into `arr1`.
3. Decrease its frequency.
4. After processing `arr2`, traverse the frequency array from smallest to largest.
5. Place all remaining values into `arr1`.

This automatically puts the remaining elements in ascending order.

---

# Algorithm

1. Create a frequency array of size `1001`.
2. Count the frequency of every element in `arr1`.
3. Store the elements of `arr2` in a HashMap with their positions.
4. Traverse the elements of `arr2` in their given order.
5. For each value:
   - Get its frequency from `count`.
   - Add that value to `arr1` that many times.
   - Decrease its frequency to `0`.
6. Traverse the frequency array from `0` to `1000`.
7. Add every remaining value according to its frequency.
8. Return `arr1`.

---

# Dry Run

Input:

```text
arr1 = [2,3,1,3,2,4,6,7,9,2,19]
arr2 = [2,1,4,3,9,6]
```

### Step 1: Frequency Count

The important frequencies are:

```text
1 → 1
2 → 3
3 → 2
4 → 1
6 → 1
7 → 1
9 → 1
19 → 1
```

---

### Step 2: Process `arr2`

`arr2` is:

```text
[2,1,4,3,9,6]
```

#### Process `2`

Frequency:

```text
count[2] = 3
```

Add:

```text
2,2,2
```

Result:

```text
[2,2,2]
```

---

#### Process `1`

Frequency:

```text
count[1] = 1
```

Add:

```text
1
```

Result:

```text
[2,2,2,1]
```

---

#### Process `4`

Add:

```text
4
```

Result:

```text
[2,2,2,1,4]
```

---

#### Process `3`

Frequency:

```text
count[3] = 2
```

Add:

```text
3,3
```

Result:

```text
[2,2,2,1,4,3,3]
```

---

#### Process `9`

Add:

```text
9
```

---

#### Process `6`

Add:

```text
6
```

Now:

```text
[2,2,2,1,4,3,3,9,6]
```

---

### Step 3: Process Remaining Elements

The remaining values are:

```text
7,19
```

Since we traverse the frequency array from smallest to largest, they are automatically placed as:

```text
7,19
```

Final result:

```text
[2,2,2,1,4,3,3,9,6,7,19]
```

---

# Understanding the Code

## Create Frequency Array

```java
int count[] = new int[1001];
```

Because the values are between `0` and `1000`, we can directly use the value as an index.

---

## Count Elements of `arr1`

```java
for(int arr : arr1){
    count[arr]++;
}
```

For every value in `arr1`, increase its frequency.

For example:

```text
arr1 = [2,2,2,3,3]
```

gives:

```text
count[2] = 3
count[3] = 2
```

---

## Store `arr2`

```java
HashMap<Integer, Integer> map = new HashMap<>();
int i = 0;

for(int arr : arr2){
    map.put(i, arr);
    i++;
}
```

The map stores the elements of `arr2` using their positions:

```text
0 → 2
1 → 1
2 → 4
3 → 3
...
```

This allows the code to process `arr2` in its original order.

---

## Fill Elements According to `arr2`

```java
int j = 0;
i = 0;

while(i != map.size()){

    int val = map.get(i);

    while(count[val] != 0){

        arr1[j] = val;

        count[val]--;

        j++;
    }

    i++;
}
```

For each value in `arr2`, all of its occurrences are placed into `arr1`.

For example:

```text
arr2 = [2,1,4]
```

If:

```text
count[2] = 3
```

then:

```text
arr1[j] = 2
arr1[j+1] = 2
arr1[j+2] = 2
```

and:

```text
count[2] = 0
```

---

## Process Remaining Values

```java
for(int k = 0; k < count.length; k++){

    while(count[k] != 0){

        arr1[j] = k;

        j++;

        count[k]--;
    }
}
```

After all elements from `arr2` have been processed, some elements may remain.

We traverse:

```text
0 → 1000
```

Therefore, the remaining elements are automatically placed in ascending order.

---

# Why Counting Sort?

The value range is small:

```text
0 <= arr1[i] <= 1000
```

So we don't need:

```java
Arrays.sort(arr1);
```

Instead, we can use:

```java
int[] count = new int[1001];
```

This gives efficient frequency-based sorting.

The important idea is:

```text
Frequency Array
      ↓
Process arr2 order
      ↓
Process remaining values in increasing order
```

---

# Important Difference From Normal Sorting

Normal sorting would produce:

```text
[1,2,2,2,3,3,4,6,7,9,19]
```

But this is **not** the required answer.

`arr2` determines the priority order:

```text
arr2 = [2,1,4,3,9,6]
```

Therefore, the elements appearing in `arr2` must come first in exactly that order.

Only the elements not appearing in `arr2` are sorted normally.

---

# Complexity Analysis

Let:

```text
n = arr1.length
```

and the maximum value be `1000`.

### Time Complexity

Counting elements:

```text
O(n)
```

Processing `arr2`:

```text
O(arr2.length + n)
```

Traversing the frequency array:

```text
O(1001)
```

Overall:

```text
O(n + arr2.length + 1001)
```

Since `1001` is constant:

```text
O(n + arr2.length)
```

---

### Space Complexity

The frequency array has fixed size:

```text
1001
```

The HashMap stores elements of `arr2`.

Therefore:

```text
O(arr2.length)
```

The counting array itself is:

```text
O(1)
```

because its size is fixed.

---

# Java Solution

```java
class Solution {

    public int[] relativeSortArray(int[] arr1, int[] arr2) {

        int count[] = new int[1001];

        for(int arr : arr1){
            count[arr]++;
        }

        HashMap<Integer, Integer> map = new HashMap<>();

        int i = 0;

        for(int arr : arr2){
            map.put(i, arr);
            i++;
        }

        int j = 0;
        i = 0;

        while(i != map.size()){

            int val = map.get(i);

            while(count[val] != 0){

                arr1[j] = val;

                count[val]--;

                j++;
            }

            i++;
        }

        for(int k = 0; k < count.length; k++){

            while(count[k] != 0){

                arr1[j] = k;

                j++;

                count[k]--;
            }
        }

        return arr1;
    }
}
```

---

# Key Concepts

- Array
- Counting Sort
- Frequency Array
- HashMap
- Sorting
- Relative Ordering
- Frequency Counting

---

# Constraints

- `1 <= arr1.length, arr2.length <= 1000`
- `0 <= arr1[i], arr2[i] <= 1000`
- All elements of `arr2` are distinct.
- Every element of `arr2` appears in `arr1`.

---

# Learning Outcome

This problem demonstrates how **Counting Sort + Relative Ordering** can be used to sort an array according to another array.

The main idea is:

```text
Count frequencies of arr1
        ↓
Process arr2 in given order
        ↓
Place all occurrences of arr2 elements
        ↓
Traverse remaining frequencies
        ↓
Place remaining elements in ascending order
```

The most important part is that the frequency array is **not only used for sorting**. It allows us to control the order:

```text
arr2 elements → arr2 order
remaining elements → ascending order
```

The solution achieves:

```text
Time  → O(n + m)
Space → O(m)
```

where `n = arr1.length` and `m = arr2.length`.