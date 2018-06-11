# Chess-solver
[![Build Status](https://travis-ci.org/FrancoCorleone/chess-solver.png?branch=master)](https://travis-ci.org/FrancoCorleone/chess-solver)

## Problem description
On board of MxN one must place figures: rook, bishop, queen, king, knight. Number of every piece is taken as an input, as well size of a board is.

The rule is: **none of the figures may knock another**.

The goal is: **find all unique configurations**.
## Solution
***\*Before reading any further, take a note that full description will be presented. Try your own first!***\*



It is a classic example of recursively run function. For algorithmic purposes I have flatten up the board to one dimension so a board 4x4 could look like this:
```
    |   0   |   1   |   2   |   3   |
    ---------------------------------
    |   4   |   5   |   6   |   7   |
    ---------------------------------
    |   8   |   9   |   10  |   11  |
    ---------------------------------
    |   12  |   13  |   14  |   15  |
```
With that is relatively easy to create mathematical formulas for every piece. For details take a dive into implementation of `getMovementPositions` method of `Figure` interface.

It seems obvious that every possible solution should be checked but to spare some time let's observe two things:
1. If there are multiple pieces of the same type, there is no need to calculate them separately.
2. You can always place new figures only on the right (bigger number) of the current position. Do not put a figure on the left since it is already calculated as a separate scenario.

If you "don't feel" those rules, create a tree of different scenarios for some small chess board and see by yourself.

Using those two rules combined we get every scenario without repetitions.

### Actual algorithm
1. For every distinct (by type) figure place first from the stack on first available position (iterate trough every field as a starting point)
2. Calculate remaining free fields by subtracting the fields that are knocked by a placed figure.
3. Add taken field two taken fields array.
4. Remove placed figure from figures
5. Start over - if there are no more figures to place -> add configuration to global configurations list.


#### Example
Figures: KNIGHT, 2x KING
1. Place KNIGHT on field 0
2. Knocked fields = 6,9; Remaining free fields=1,2,3,4,5,7,8,10,....,15
3. Taken fields = 0
4. Remaining figures: 2x KING
5. Place KING on field 1 (...)

It is important to notice that there are two nested loops here: 
- go through every field as a starting field
- place every remaining figure as "a next one"
That's were those 2 rules of "no duplicates" go in.


## Example solutions
- 3x3 with 2 Kings and 1 Rook: 4 solutions, calculation time < 1ms
```
K * K   K * *   * * K   * R *
* * *   * * R   R * *   * * *
* R *   K * *   * * K   K * K
```
- 4x4 with 2 Rooks and 4 Knights: 8 solutions, calculation time ca.1ms
```
* N * N     * N * N     R * * *     * * R *
* * R *     R * * *     * N * N     * N * N
* N * N     * N * N     * * R *     R * * *
R * * *     * * R *     * N * N     * N * N

* R * *     * * * R     N * N *     N * N *
N * N *     N * N *     * * * R     * R * *
* * * R     * R * *     N * N *     N * N *
N * N *     N * N *     * R * *     * * * R      
```
- 7x7 with 2 Kings, 2 Queens, 2 Bishops, 1 Knight: 3063828 solutions, calculation time: min: 5.62s, max:10.8s
The calculation time was measured for : Intel® Core™ i7-6820HQ CPU @ 2.70GHz × 8 with 16GB of RAM. The difference is due to different levels of JVM warmup so it is good to run same algorithm few times and take notes of resulting times.
The average was around 6s. 

## Usage
As a default program shows only number of solutions and the calculation time. To show results in console pass `-p` argument and to pass results to file add `-pf` argument.

## Why so few streams?
If you look closely at my code you can see that in few places I resign from using Stream API from Java8 and even from foreach loop in favor of iterator.
Since time is crucial here I would recommend reading [THIS](https://blog.takipi.com/benchmark-how-java-8-lambdas-and-streams-can-make-your-code-5-times-slower/)

Have fun and don't hesitate to leave feedback!