# 3069. Distribute Elements Into Two Arrays I

> **Difficulty:** Easy  
> **Topics:** Array, Simulation

---

## Problem Statement

You are given a **1-indexed** array of **distinct** integers `nums`.

You need to distribute all elements of `nums` into two arrays:

```text
arr1
arr2
```

using the following rules:

- In the first operation, append `nums[1]` to `arr1`.
- In the second operation, append `nums[2]` to `arr2`.
- For every next element:
  - If the last element of `arr1` is **greater** than the last element of `arr2`, append the current element to `arr1`.
  - Otherwise, append the current element to `arr2`.

Finally, concatenate `arr1` and `arr2` to form the `result` array.

Return the resulting array.

---

## Example 1

### Input

```text
nums = [2,1,3]
```

### Output

```text
[2,3,1]
```

### Explanation

Initially:

```text
arr1 = [2]
arr2 = [1]
```

Now consider `3`.

The last elements are:

```text
arr1 -> 2
arr2 -> 1
```

Since:

```text
2 > 1
```

we add `3` to `arr1`.

Therefore:

```text
arr1 = [2,3]
arr2 = [1]
```

Concatenating both arrays:

```text
[2,3] + [1]
```

gives:

```text
[2,3,1]
```

---

## Example 2

### Input

```text
nums = [5,4,3,8]
```

### Output

```text
[5,3,4,8]
```

### Explanation

Initially:

```text
arr1 = [5]
arr2 = [4]
```

For `3`:

```text
5 > 4
```

so:

```text
arr1 = [5,3]
arr2 = [4]
```

For `8`:

The last elements are:

```text
arr1 -> 3
arr2 -> 4
```

Since:

```text
3 > 4
```

is false, `8` is added to `arr2`.

Therefore:

```text
arr1 = [5,3]
arr2 = [4,8]
```

Final result:

```text
[5,3,4,8]
```

---

# Approach

Use **two ArrayLists** to simulate the distribution process.

We maintain:

```text
arr1
arr2
```

The first two elements are directly placed:

```java
arr1.add(nums[0]);
arr2.add(nums[1]);
```

Then start from index `2`.

For every element:

```java
if(last element of arr1 > last element of arr2)
```

add it to `arr1`.

Otherwise, add it to `arr2`.

After processing all elements, copy `arr1` followed by `arr2` into the original `nums` array.

---

# Algorithm

1. Create two `ArrayList<Integer>`:
   - `arr1`
   - `arr2`
2. Add `nums[0]` to `arr1`.
3. Add `nums[1]` to `arr2`.
4. Traverse the array from index `2`.
5. Compare the last elements of `arr1` and `arr2`.
6. If:

```text
last(arr1) > last(arr2)
```

add the current element to `arr1`.

7. Otherwise, add the current element to `arr2`.
8. Copy all elements of `arr1` into `nums`.
9. Copy all elements of `arr2` after them.
10. Return `nums`.

---

# Dry Run

Input:

```text
nums = [5,4,3,8]
```

### Step 1

First element goes to `arr1`:

```text
arr1 = [5]
```

Second element goes to `arr2`:

```text
arr2 = [4]
```

---

### Step 2

Current element:

```text
3
```

Compare:

```text
last(arr1) = 5
last(arr2) = 4
```

Since:

```text
5 > 4
```

add `3` to `arr1`.

```text
arr1 = [5,3]
arr2 = [4]
```

---

### Step 3

Current element:

```text
8
```

Compare:

```text
last(arr1) = 3
last(arr2) = 4
```

Since:

```text
3 > 4
```

is false, add `8` to `arr2`.

```text
arr1 = [5,3]
arr2 = [4,8]
```

---

### Final Result

Concatenate:

```text
arr1 + arr2
```

```text
[5,3] + [4,8]
```

Result:

```text
[5,3,4,8]
```

---

# Understanding the Code

## Create Two Arrays

```java
ArrayList<Integer> arr1 = new ArrayList<>();
ArrayList<Integer> arr2 = new ArrayList<>();
```

These two lists store the elements according to the given rules.

---

## First Two Elements

```java
arr1.add(nums[0]);
arr2.add(nums[1]);
```

The first element always goes into `arr1`.

The second element always goes into `arr2`.

---

## Process Remaining Elements

```java
for(int i=2; i<nums.length; i++){
```

We start from index `2` because indices `0` and `1` have already been handled.

---

## Get Last Element

```java
arr1.get(arr1.size()-1)
```

`arr1.size()-1` gives the index of the last element.

Similarly:

```java
arr2.get(arr2.size()-1)
```

gives the last element of `arr2`.

---

## Compare Last Elements

```java
if(arr1.get(arr1.size()-1) > arr2.get(arr2.size()-1)){
```

If the last element of `arr1` is greater, add the current element to `arr1`.

```java
arr1.add(nums[i]);
```

Otherwise:

```java
arr2.add(nums[i]);
```

---

# Creating the Final Result

After distribution, we need:

```text
result = arr1 + arr2
```

The variable:

```java
int k = 0;
```

keeps track of the position in `nums`.

First copy `arr1`:

```java
for(int num : arr1){
    nums[k] = num;
    k++;
}
```

Then copy `arr2`:

```java
for(int num : arr2){
    nums[k] = num;
    k++;
}
```

Now `nums` itself contains the final result.

---

# Complexity Analysis

### Time Complexity

We traverse `nums` once to distribute the elements:

```text
O(n)
```

Then we copy the elements of `arr1` and `arr2` back into `nums`:

```text
O(n)
```

Overall:

```text
O(n)
```

---

### Space Complexity

We use two `ArrayList`s containing all `n` elements:

```text
O(n)
```

---

# Java Solution

```java
class Solution {
    public int[] resultArray(int[] nums) {

        ArrayList<Integer> arr1 = new ArrayList<>();
        ArrayList<Integer> arr2 = new ArrayList<>();

        arr1.add(nums[0]);
        arr2.add(nums[1]);

        for(int i = 2; i < nums.length; i++){

            if(arr1.get(arr1.size() - 1) >
               arr2.get(arr2.size() - 1)){

                arr1.add(nums[i]);

            }
            else{

                arr2.add(nums[i]);

            }
        }

        int k = 0;

        for(int num : arr1){
            nums[k] = num;
            k++;
        }

        for(int num : arr2){
            nums[k] = num;
            k++;
        }

        return nums;
    }
}
```

---

# Key Concepts

- Array
- ArrayList
- Simulation
- Traversal
- Comparing Last Elements
- Two-Pointer-like State Tracking

---

# Constraints

- `3 <= n <= 50`
- `1 <= nums[i] <= 100`
- All elements of `nums` are distinct.

---

# Learning Outcome

This problem is mainly a **simulation** problem.

The important idea is to carefully follow the rules given in the problem and maintain the current state of both arrays.

The main pattern is:

```text
Initialize arr1 and arr2
        ↓
Process each remaining element
        ↓
Compare last elements
        ↓
Choose arr1 or arr2
        ↓
Concatenate arr1 + arr2
```

The key Java concepts used are:

```java
ArrayList<Integer>
```

for dynamic storage and:

```java
arr1.get(arr1.size() - 1)
```

to access the last element of an `ArrayList`.