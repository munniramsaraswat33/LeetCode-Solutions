# 831. Masking Personal Information

> **Difficulty:** Medium  
> **Topics:** String, Simulation

---

## Problem Statement

You are given a string `s` representing either an **email address** or a **phone number**.

Return the appropriately **masked personal information** according to the given rules.

---

## Email Address

For an email address:

- Convert all uppercase letters to lowercase.
- Keep the first and last characters of the name.
- Replace all middle characters of the name with exactly five asterisks:

```text
*****
```

### Example

```text
Input:
LeetCode@LeetCode.com

Output:
l*****e@leetcode.com
```

---

## Phone Number

A phone number contains between **10 and 13 digits**.

The last 10 digits are the local phone number.

Any remaining digits form the country code.

The output format is:

### No country code

```text
***-***-XXXX
```

### 1-digit country code

```text
+*-***-***-XXXX
```

### 2-digit country code

```text
+**-***-***-XXXX
```

### 3-digit country code

```text
+***-***-***-XXXX
```

where `XXXX` represents the last four digits.

---

# Examples

## Example 1

### Input

```text
s = "LeetCode@LeetCode.com"
```

### Output

```text
"l*****e@leetcode.com"
```

---

## Example 2

### Input

```text
s = "AB@qq.com"
```

### Output

```text
"a*****b@qq.com"
```

Even though the name contains only two characters, exactly five asterisks are used.

---

## Example 3

### Input

```text
s = "1(234)567-890"
```

### Output

```text
"***-***-7890"
```

After removing the separators, there are exactly 10 digits, so there is no country code.

---

# Approach

The solution first determines whether the input is an **email** or a **phone number**.

We can identify this by checking the first character:

```text
Letter → Email
Digit  → Phone
```

---

## Email Approach

1. Convert the first character to lowercase.
2. Add five asterisks.
3. Find the `@` symbol.
4. Add the last character of the name.
5. Add `@`.
6. Convert the entire domain to lowercase.

The result follows:

```text
first*****last@domain
```

---

## Phone Approach

1. Extract only the digits from the input.
2. Count the total number of digits.
3. The last four digits remain visible.
4. Mask the remaining local-number digits.
5. If there is a country code, add the appropriate number of `*` characters after `+`.
6. Format the result using `-`.

---

# Dry Run

### Email

Input:

```text
LeetCode@LeetCode.com
```

Name:

```text
LeetCode
```

First character:

```text
l
```

Last character:

```text
e
```

Domain:

```text
leetcode.com
```

Final result:

```text
l*****e@leetcode.com
```

---

### Phone

Input:

```text
1(234)567-890
```

Remove separators:

```text
1234567890
```

Number of digits:

```text
10
```

Last four digits:

```text
7890
```

Final result:

```text
***-***-7890
```

---

# Algorithm

### If the input is an email:

```text
1. Take first character.
2. Convert it to lowercase.
3. Append "*****".
4. Find '@'.
5. Append the last character of the name.
6. Append the rest of the email in lowercase.
```

### If the input is a phone number:

```text
1. Extract all digits.
2. Count the digits.
3. Keep the last four digits visible.
4. Mask the remaining digits.
5. Add country-code masking if necessary.
6. Return the formatted result.
```

---

# Complexity Analysis

Let `n` be the length of the input string.

### Time Complexity

```text
O(n)
```

The input is scanned a constant number of times.

### Space Complexity

```text
O(n)
```

A `StringBuilder` and digit storage are used to construct the result.

---

# Java Solution

```java
class Solution {

    public String maskPII(String s) {

        StringBuilder sb = new StringBuilder();

        // Email
        if (Character.isLetter(s.charAt(0))) {

            int at = s.indexOf('@');

            sb.append(Character.toLowerCase(s.charAt(0)));
            sb.append("*****");
            sb.append(Character.toLowerCase(s.charAt(at - 1)));
            sb.append('@');

            for (int i = at + 1; i < s.length(); i++) {
                sb.append(Character.toLowerCase(s.charAt(i)));
            }

            return sb.toString();
        }

        // Phone
        StringBuilder digits = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                digits.append(ch);
            }
        }

        int n = digits.length();

        String lastFour = digits.substring(n - 4);

        if (n == 10) {
            return "***-***-" + lastFour;
        }

        int countryDigits = n - 10;

        StringBuilder result = new StringBuilder();

        result.append('+');

        for (int i = 0; i < countryDigits; i++) {
            result.append('*');
        }

        result.append("-***-***-");
        result.append(lastFour);

        return result.toString();
    }
}
```

---

# Key Concepts

- String Manipulation
- Character Handling
- StringBuilder
- Email Parsing
- Phone Number Formatting
- Simulation

---

# Constraints

- `8 <= s.length <= 40` for email.
- `10 <= s.length <= 20` for phone number.
- Phone numbers contain 10 to 13 digits.
- Email addresses contain uppercase and lowercase English letters.
- Phone numbers may contain digits and separator characters.

---

# Learning Outcome

This problem demonstrates how to handle different input formats using **string parsing and simulation**. It also reinforces the use of `StringBuilder`, character classification, and careful formatting rules to produce the required output.