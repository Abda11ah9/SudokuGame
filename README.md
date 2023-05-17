# *Sudoku Solver:*
**I have always found joy with playing board games especially solving puzzles, and Sudoku was one of my favorite games. Therefore, I'm very interested in implementing a *Sudoku solver* for all the puzzlers/decoders out there ! 
This project will challenge me on understanding how the computer manages to find the possible solutions, or even the only possible solution for a sudoku game.**

## Design specifications *(User stories)*:
- As a user, I want to be able to see the missing & given positions on the board.
- As a user, I want to be able to select a position on the board and insert a number.
- As a user, I want to be able to see the updates on the board.
- As a user, I want to see if the input I give is wrong.
- As a user, I want to be able quit the game at any given point and having it saved where I stopped.
- As user, I want to be able to retrieve at the point where I previously stopped.

## Phase 4 Task 2:
- inserted 2 at 1st row, 2nd column
- inserted 4 at 1st row, 3rd column
- inserted 1 at 2nd row, 2nd column

Console: 
-Fri Dec 02 18:57:41 PST 2022
User added 2 at position [1, 2]
Fri Dec 02 18:57:42 PST 2022
User added 4 at position [1, 3]
Fri Dec 02 18:57:43 PST 2022
User added 1 at position [2, 2]


## Phase 4 Task 3:

**Problem 1** I could have made the BoardSolution as interface that's implemented by SudokuBoard.

**Problem 2** In the GUI, I should have made the Grid class as an Abstract that would be implemented by other subclasses,
where I would make a class for the login window, make an interface for the user to write notes in the cell, have a timer running, etc.



