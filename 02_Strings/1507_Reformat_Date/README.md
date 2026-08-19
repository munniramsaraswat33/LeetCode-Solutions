# 1507. Reformat Date

> **Difficulty:** Easy  
> **Topics:** String, HashMap

---

## Problem Statement

You are given a date in the format:

```text
Day Month Year
```

The day contains a number followed by:

```text
st, nd, rd, th
```

The month is represented using its first three letters:

```text
Jan Feb Mar Apr May Jun
Jul Aug Sep Oct Nov Dec
```

The year is a four-digit number.

Convert the date into:

```text
YYYY-MM-DD
```

### Example

```text
Input:
"20th Oct 2052"

Output:
"2052-10-20"
```

---

## Approach

We can solve the problem using the following steps:

1. Create a `HashMap` to map each month abbreviation to its two-digit month number.
2. Split the date using spaces.
3. Extract the day by removing the last two characters (`st`, `nd`, `rd`, or `th`).
4. Convert the month using the `HashMap`.
5. Take the year directly.
6. If the day contains only one digit, add a leading `0`.
7. Construct the final date in `YYYY-MM-DD` format.

---

## Java Solution

```java
class Solution {
    public String reformatDate(String date) {

        HashMap<String, String> map = new HashMap<>();

        map.put("Jan", "01");
        map.put("Feb", "02");
        map.put("Mar", "03");
        map.put("Apr", "04");
        map.put("May", "05");
        map.put("Jun", "06");
        map.put("Jul", "07");
        map.put("Aug", "08");
        map.put("Sep", "09");
        map.put("Oct", "10");
        map.put("Nov", "11");
        map.put("Dec", "12");

        String words[] = date.split(" ");

        String day = words[0].substring(0, words[0].length() - 2);
        String month = map.get(words[1]);
        String year = words[2];

        if(day.length() == 1){
            day = "0" + day;
        }

        return year + "-" + month + "-" + day;
    }
}
```

---

## Dry Run

### Input

```text
date = "6th Jun 1933"
```

### Step 1: Split the String

```text
words = ["6th", "Jun", "1933"]
```

Therefore:

```text
words[0] = "6th"
words[1] = "Jun"
words[2] = "1933"
```

---

### Step 2: Extract Day

Remove the last two characters:

```text
"6th"
 ↓
"6"
```

So:

```text
day = "6"
```

Since the day has only one digit:

```java
day = "0" + day;
```

Now:

```text
day = "06"
```

---

### Step 3: Convert Month

Using the `HashMap`:

```text
Jun → 06
```

So:

```text
month = "06"
```

---

### Step 4: Extract Year

```text
year = "1933"
```

---

### Step 5: Construct Answer

```text
year + "-" + month + "-" + day
```

becomes:

```text
1933-06-06
```

### Output

```text
"1933-06-06"
```

---

## Another Example

### Input

```text
date = "20th Oct 2052"
```

After splitting:

```text
["20th", "Oct", "2052"]
```

Day:

```text
20th → 20
```

Month:

```text
Oct → 10
```

Year:

```text
2052
```

Final result:

```text
2052-10-20
```

---

## Why Remove the Last Two Characters?

Every valid day ends with one of:

```text
st
nd
rd
th
```

For example:

```text
1st  → 1
2nd  → 2
3rd  → 3
4th  → 4
20th → 20
31st → 31
```

Therefore:

```java
words[0].substring(0, words[0].length() - 2)
```

removes the suffix and leaves only the numeric day.

---

## Complexity Analysis

Let `n` be the length of the date string.

### Time Complexity

```text
O(n)
```

We process the date string once.

### Space Complexity

```text
O(1)
```

The month map always contains exactly 12 entries.

---

## Key Concepts

- String Splitting
- `substring()`
- HashMap
- String Formatting
- Leading Zero

---

## Key Takeaway

The main idea is to split the date into three parts:

```text
Day → remove suffix
Month → convert using HashMap
Year → use directly
```

Then format them as:

```text
YYYY-MM-DD
```

### Complexity

```text
Time:  O(n)
Space: O(1)
```