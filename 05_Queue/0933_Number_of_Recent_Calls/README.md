# 933. Number of Recent Calls

**LeetCode Problem:** [933. Number of Recent Calls](https://leetcode.com/problems/number-of-recent-calls/)

**Difficulty:** Easy

**Primary Topic:** Queue

**Pattern:** Sliding Window

---

## Problem Statement

You need to implement a `RecentCounter` class that counts the number of requests received within the most recent `3000` milliseconds.

The class has a method:

```java
int ping(int t)
```

A request is made at time `t`.

After adding the new request, return the number of requests that have happened in the time interval:

```text
[t - 3000, t]
```

The interval is **inclusive**, meaning both endpoints are included.

The requests are guaranteed to be made with increasing values of `t`.

---

## Example 1

### Input

```text
["RecentCounter", "ping", "ping", "ping", "ping"]
[[], [1], [100], [3001], [3002]]
```

### Output

```text
[null, 1, 2, 3, 3]
```

### Explanation

Initially, there are no requests.

### `ping(1)`

The valid range is:

```text
[1 - 3000, 1]
= [-2999, 1]
```

Only request `1` exists.

```text
Queue = [1]
Answer = 1
```

---

### `ping(100)`

The valid range is:

```text
[100 - 3000, 100]
= [-2900, 100]
```

Both `1` and `100` are inside the range.

```text
Queue = [1, 100]
Answer = 2
```

---

### `ping(3001)`

The valid range is:

```text
[3001 - 3000, 3001]
= [1, 3001]
```

Requests:

```text
1, 100, 3001
```

are all valid.

```text
Queue = [1, 100, 3001]
Answer = 3
```

---

### `ping(3002)`

The valid range is:

```text
[3002 - 3000, 3002]
= [2, 3002]
```

Request `1` is now outside the range.

So we remove it.

```text
Queue = [100, 3001, 3002]
Answer = 3
```

---

# Approach

We use a **Queue**.

A queue follows:

```text
FIFO
First In → First Out
```

This is perfect for this problem because requests arrive in increasing order of time.

The oldest request is always at the front of the queue.

For every `ping(t)`:

1. Add `t` to the queue.
2. Remove all requests that are older than `3000` milliseconds.
3. Return the current queue size.

The condition for an outdated request is:

```text
request < t - 3000
```

So we repeatedly remove:

```java
q.poll();
```

while:

```java
q.peek() < t - 3000
```

---

# Intuition

Suppose the requests are:

```text
1, 100, 3001, 3002
```

When we receive:

```text
ping(3002)
```

we only care about requests in:

```text
[2, 3002]
```

The request at:

```text
1
```

is outside this range.

Because requests arrive in increasing order, if the oldest request is too old, we can safely remove it.

And because the queue stores requests in chronological order:

```text
Front                         Back
  ↓                             ↓
[oldest, ... , newest]
```

we only need to check the front.

---

# Algorithm

For the `RecentCounter` class:

### Constructor

1. Create an empty queue.

### `ping(t)`

1. Add `t` to the queue.
2. While the oldest request is outside the interval:
   ```text
   q.peek() < t - 3000
   ```
   remove it.
3. Return the size of the queue.

---

# Dry Run

Consider:

```text
ping(1)
ping(100)
ping(3001)
ping(3002)
```

---

## Step 1: `ping(1)`

Add `1`:

```text
Queue:
[1]
```

Valid range:

```text
[-2999, 1]
```

`1` is valid.

```text
Answer = 1
```

---

## Step 2: `ping(100)`

Add `100`:

```text
Queue:
[1, 100]
```

Valid range:

```text
[-2900, 100]
```

Both requests are valid.

```text
Answer = 2
```

---

## Step 3: `ping(3001)`

Add `3001`:

```text
Queue:
[1, 100, 3001]
```

Valid range:

```text
[1, 3001]
```

The oldest request is:

```text
1
```

Check:

```text
1 < 1
```

This is false.

So we keep it.

```text
Answer = 3
```

---

## Step 4: `ping(3002)`

Add `3002`:

```text
Queue:
[1, 100, 3001, 3002]
```

Valid range:

```text
[2, 3002]
```

Check the oldest request:

```text
1 < 2
```

This is true.

Remove `1`:

```text
Queue:
[100, 3001, 3002]
```

Now:

```text
100 < 2
```

is false.

So we stop removing.

Final:

```text
Answer = 3
```

---

# Java Solution

```java
class RecentCounter {
    Queue<Integer> q;

    public RecentCounter() {
        q = new LinkedList<>();
    }
    
    public int ping(int t) {
        q.add(t);

        while(q.peek() < t - 3000){
            q.poll();
        }

        return q.size();
    }
}
```

---

# Code Explanation

## 1. Declare the Queue

```java
Queue<Integer> q;
```

The queue stores the timestamps of recent requests.

Since requests arrive in increasing order, the queue will also be ordered from oldest to newest.

For example:

```text
Front                    Back
  ↓                        ↓
[1, 100, 3001, 3002]
```

---

## 2. Initialize the Queue

```java
public RecentCounter() {
    q = new LinkedList<>();
}
```

The constructor creates an empty queue.

Initially:

```text
Queue = []
```

---

## 3. Add the Current Request

```java
q.add(t);
```

Whenever `ping(t)` is called, the current timestamp is added to the queue.

For example:

```text
Queue = [100, 3001]
```

After:

```text
ping(3002)
```

we get:

```text
Queue = [100, 3001, 3002]
```

---

## 4. Remove Old Requests

```java
while(q.peek() < t - 3000){
    q.poll();
}
```

`q.peek()` gives us the oldest timestamp.

The valid interval is:

```text
[t - 3000, t]
```

Therefore, any timestamp smaller than:

```text
t - 3000
```

is outside the valid range.

We remove it using:

```java
q.poll();
```

---

## 5. Return Queue Size

```java
return q.size();
```

After removing all outdated requests, every timestamp remaining in the queue belongs to:

```text
[t - 3000, t]
```

Therefore, the queue size is exactly the number of recent calls.

---

# Why Queue?

A queue is the ideal data structure because timestamps arrive in increasing order.

The requests are stored as:

```text
Oldest → Newest
```

For example:

```text
Front
  ↓
[100, 500, 1200, 3000]
                  ↑
                Back
```

If `100` becomes too old, it will always be the first element that needs to be removed.

So we can simply use:

```java
q.peek()
```

to inspect the oldest request.

Then:

```java
q.poll()
```

to remove it.

---

# Why Don't We Remove From the Middle?

Because timestamps are added in increasing order.

Suppose:

```text
Queue = [100, 500, 1000, 2000]
```

If `1000` is too old, then `500` and `100` must also be too old.

Therefore, outdated elements always form a prefix of the queue.

We only need to remove elements from the front.

---

# Sliding Window Concept

This problem can also be understood as a **sliding window** problem.

At time `t`, the valid window is:

```text
[t - 3000, t]
```

For example, at:

```text
t = 5000
```

the valid window is:

```text
[2000, 5000]
```

Any request before `2000` is removed.

As `t` increases, the window moves forward:

```text
Earlier Window
[1000 -------- 4000]

          ↓ moves forward

New Window
[2000 -------- 5000]
```

The queue stores exactly the timestamps currently inside this sliding window.

---

# Important Observation

The interval is:

```text
[t - 3000, t]
```

It is inclusive.

Therefore, a timestamp exactly equal to:

```text
t - 3000
```

is still valid.

For example:

```text
t = 3001
```

Then:

```text
t - 3000 = 1
```

Timestamp `1` is valid.

That's why the condition is:

```java
q.peek() < t - 3000
```

and **not**:

```java
q.peek() <= t - 3000
```

---

# Complexity Analysis

Let `n` be the total number of calls to `ping`.

## Time Complexity

Each timestamp is:

- Added to the queue once.
- Removed from the queue at most once.

Therefore, although there is a `while` loop, each element is processed only a constant number of times.

Overall:

```text
O(n)
```

for `n` calls.

The amortized time complexity of each `ping()` is:

```text
O(1)
```

---

## Space Complexity

In the worst case, all requests can remain within the last `3000` milliseconds.

Therefore, the queue can contain up to `n` timestamps.

```text
O(n)
```

---

# Common Mistakes

## Mistake 1: Using `<=`

Incorrect:

```java
while(q.peek() <= t - 3000)
```

The timestamp exactly equal to `t - 3000` is still valid.

Correct:

```java
while(q.peek() < t - 3000)
```

---

## Mistake 2: Removing Only One Old Request

There may be multiple outdated requests.

For example:

```text
Queue = [1, 2, 3, 5000]
```

If the current time is `5000`, the valid range is:

```text
[2000, 5000]
```

Multiple elements may need to be removed.

Therefore, we use:

```java
while(...)
```

instead of:

```java
if(...)
```

---

## Mistake 3: Using Stack

A stack follows:

```text
LIFO
Last In → First Out
```

But we need to remove the **oldest** timestamps first.

Therefore, a queue is more appropriate.

---

## Mistake 4: Recalculating the Count Every Time

We don't need to scan all previous requests.

The queue already contains only the requests inside the current time window.

Therefore:

```java
return q.size();
```

gives the answer directly.

---

# Key Concepts / Patterns

## 1. Queue

The main data structure is:

```text
Queue
```

because requests arrive chronologically and old requests are removed from the front.

---

## 2. Sliding Window

The valid range is:

```text
[t - 3000, t]
```

This range moves forward as new requests arrive.

---

## 3. FIFO

Queue follows:

```text
First In → First Out
```

The oldest request is always removed first.

---

## 4. Monotonic Time

The problem guarantees that calls to `ping()` use increasing timestamps.

This is what makes the queue solution possible.

---

## 5. Amortized Analysis

Although the `ping()` method contains a `while` loop, we don't get `O(n)` for every call.

Every timestamp is:

```text
Added once
Removed at most once
```

Therefore, across all calls, the total number of removals is at most `n`.

So the total work is:

```text
O(n)
```

---

# Alternative Visualization

Imagine a timeline:

```text
1 ---- 100 -------- 3001 ---- 3002
|_______________________________|
              Requests
```

For `t = 3002`, the valid window is:

```text
2 ---------------------------- 3002
```

Therefore:

```text
1
```

falls outside the window.

Remove it:

```text
100 -------- 3001 ---- 3002
```

The remaining requests are counted.

---

# Learning Outcome

After solving this problem, you should understand:

- How to implement a queue in Java.
- How FIFO behavior helps remove old data.
- How to maintain a sliding time window.
- How to remove multiple outdated elements efficiently.
- Why the `while` loop still gives an amortized `O(1)` operation.
- How to solve streaming-data problems using a queue.

---

# Summary

The problem asks us to count requests inside:

```text
[t - 3000, t]
```

We store timestamps in a queue.

For every new timestamp:

```text
1. Add timestamp
2. Remove timestamps < t - 3000
3. Return queue size
```

The core code is:

```java
q.add(t);

while(q.peek() < t - 3000){
    q.poll();
}

return q.size();
```

Because timestamps arrive in increasing order, the oldest timestamp is always at the front of the queue.

### Final Complexity

```text
Time:  O(n) total / O(1) amortized per ping
Space: O(n)
```

**Primary Pattern: Queue + Sliding Window**