# 4025. Minimize the Maximum Waiting Time at Synchronized Traffic Lights

> **Difficulty:** Medium  
> **Topics:** Array, Math, Greedy

---

## Problem Statement

You are given an integer `period` and an integer array `lights`.

Each:

```text
lights[i]
```

represents the duration of the **green phase** of the `ith` traffic light.

All traffic lights are synchronized:

- Every traffic light starts its cycle at time `0`.
- Every cycle lasts exactly `period` seconds.
- After the green phase, the remaining time is the red phase.

You are also given an integer array `arrivalTime`, where:

```text
arrivalTime[j]
```

represents the arrival time of the `jth` car.

Each car can be assigned to **any one traffic light**.

The goal is to minimize the **maximum waiting time** among all cars.

---

## Important Observation

For a traffic light with green duration:

```text
lights[i]
```

and a car arriving at time:

```text
t
```

we calculate:

```text
r = t % period
```

Here `r` represents the car's position inside the current traffic-light cycle.

If:

```text
r < lights[i]
```

the light is green, so:

```text
waiting time = 0
```

Otherwise, the light is red and the car has to wait until the next cycle:

```text
waiting time = period - r
```

---

## Key Idea

Every car can choose **any traffic light**.

Therefore, the best traffic light to consider is the one having the **largest green duration**.

For example:

```text
lights = [2, 3, 5, 4]
```

The light with:

```text
maxGreen = 5
```

is always at least as good as the other lights.

If a car can pass through the light with green duration `5`, it can pass with zero waiting time.

If even this light is red, then every other light is also red at that same point in the cycle because their green phases end earlier.

Therefore, we only need to find:

```text
maxGreen = maximum value in lights
```

---

## Algorithm

1. Find the maximum green duration from `lights`.
2. For every arrival time:
   - Calculate `arrivalTime[i] % period`.
   - If the remainder is less than `maxGreen`, waiting time is `0`.
   - Otherwise, waiting time is `period - remainder`.
3. Keep the maximum waiting time.
4. Return the maximum waiting time.

---

## Java Solution

```java
class Solution {
    public int minPenalty(int period, int[] lights, int[] arrivalTime) {

        int maxGreen = 0;

        for(int light : lights){
            maxGreen = Math.max(maxGreen, light);
        }

        int[][] velunoraxi = { {period}, lights, arrivalTime };

        int ans = 0;

        for(int i = 0; i < arrivalTime.length; i++){

            int val = arrivalTime[i] % period;

            int waiting;

            if(val < maxGreen){
                waiting = 0;
            }
            else{
                waiting = period - val;
            }

            ans = Math.max(ans, waiting);
        }

        return ans;
    }
}
```

---

## Dry Run

### Example 1

```text
period = 8
lights = [2, 3]
arrivalTime = [2, 5, 8, 11]
```

### Step 1: Find maximum green duration

```text
lights = [2, 3]

maxGreen = 3
```

Now process every car.

---

### Car 1

```text
arrivalTime = 2
```

Calculate:

```text
r = 2 % 8
  = 2
```

Since:

```text
2 < 3
```

the car arrives during the green phase.

```text
waiting = 0
```

---

### Car 2

```text
arrivalTime = 5
```

```text
r = 5 % 8
  = 5
```

Since:

```text
5 >= 3
```

the car arrives during the red phase.

Therefore:

```text
waiting = 8 - 5
        = 3
```

---

### Car 3

```text
arrivalTime = 8
```

```text
r = 8 % 8
  = 0
```

Since:

```text
0 < 3
```

the car arrives at the beginning of a new cycle.

```text
waiting = 0
```

---

### Car 4

```text
arrivalTime = 11
```

```text
r = 11 % 8
  = 3
```

Since:

```text
3 >= 3
```

the green phase has already ended.

Therefore:

```text
waiting = 8 - 3
        = 5
```

---

### Maximum Waiting Time

```text
max(0, 3, 0, 5)
= 5
```

### Output

```text
5
```

---

## Example 2

```text
period = 10
lights = [3, 6, 8]
arrivalTime = [4, 9, 15]
```

Maximum green duration:

```text
maxGreen = 8
```

### Arrival = 4

```text
4 % 10 = 4
```

Since:

```text
4 < 8
```

waiting:

```text
0
```

### Arrival = 9

```text
9 % 10 = 9
```

Since:

```text
9 >= 8
```

waiting:

```text
10 - 9 = 1
```

### Arrival = 15

```text
15 % 10 = 5
```

Since:

```text
5 < 8
```

waiting:

```text
0
```

Maximum:

```text
max(0, 1, 0) = 1
```

### Output

```text
1
```

---

## Important Edge Case

If:

```text
r == maxGreen
```

the car **must wait**.

The condition is:

```java
r < maxGreen
```

not:

```java
r <= maxGreen
```

For example:

```text
period = 5
maxGreen = 2
arrivalTime = 2
```

Then:

```text
r = 2 % 5
  = 2
```

Since:

```text
2 < 2
```

is false, the car is already in the red phase.

Therefore:

```text
waiting = 5 - 2
        = 3
```

---

## Why We Don't Check Every Traffic Light

A straightforward solution might check every car against every traffic light.

That would take:

```text
O(arrivalTime.length * lights.length)
```

which can be too slow because:

```text
arrivalTime.length <= 10^5
lights.length <= 10^4
```

Instead, we notice that the traffic light with the **largest green duration** is always the best choice.

So we calculate `maxGreen` only once.

This reduces the solution to a single pass through the arrival times.

---

## Correctness

For every car, consider the traffic light with:

```text
maxGreen = maximum(lights)
```

If:

```text
r < maxGreen
```

then this traffic light is green and the car can wait `0` seconds, which is the minimum possible waiting time.

If:

```text
r >= maxGreen
```

then every traffic light is red because every other light has a green duration less than or equal to `maxGreen`.

Therefore, no traffic light can provide a smaller waiting time than:

```text
period - r
```

Hence, the calculated waiting time for every car is the minimum possible waiting time for that car.

Taking the maximum of these minimum waiting times gives the minimum possible penalty.

---

## Complexity Analysis

Let:

```text
L = lights.length
A = arrivalTime.length
```

Finding the maximum green duration:

```text
O(L)
```

Processing all cars:

```text
O(A)
```

Therefore:

### Time Complexity

```text
O(L + A)
```

### Space Complexity

```text
O(1)
```

---

## Key Concepts

- Array Traversal
- Modulo Operator
- Greedy Approach
- Mathematical Observation
- Maximum Value
- Simulation

---

## Key Takeaway

The main trick is to realize that we don't need to try every traffic light for every car.

Since every car can choose any traffic light, the traffic light with the **maximum green duration** is always optimal.

So:

```java
int maxGreen = 0;

for(int light : lights){
    maxGreen = Math.max(maxGreen, light);
}
```

Then for every car:

```java
int r = arrivalTime[i] % period;

if(r < maxGreen){
    waiting = 0;
}
else{
    waiting = period - r;
}
```

Finally, keep the maximum waiting time.

```text
Time:  O(L + A)
Space: O(1)
```