# Testing Philosophy
[Course source](https://joshhug.gitbooks.io/hug61b/content/chap3/chap31.html)   
We should definitely write tests but only when they might be useful! Taking inspiration from TDD, writing your tests before writing code can also be very helpful in some cases.
## 1. ADD (Autograder driven development)
Very bad habit, time-wasting, inefficient.
## 2. TDD (Test driven development)
### 1. Unit test (JUnit)
Note: More detailed guide is in the third part.  
Steps:
1. Identify a new feature.
2. Write a unit test for the feature.
3. **Run the test, it should FAIL.**
4. Coding.
5. Optional: Refactor code to make it faster, cleaner, etc.    

Only write when they might be useful!
### 2. Integration Testing
Not JUnit's focus, but JUnit still can do this.  
Used for large module interaction or system, since Unit tests are great, 
but we should also make sure these units work properly together.    
Shortcomings:
1. Tedious to do manually. 
2. Challenging to automate.
3. Testing at highest level of abstraction may miss subtle or rare errors.



***
# Debugging Guide
[Course source](https://sp21.datastructur.es/materials/guides/debugging-guide.html)    
[Debugging Your First Application](https://www.jetbrains.com/help/idea/debugging-your-first-java-application.html)    
[Setting Breakpoints](https://www.jetbrains.com/help/idea/using-breakpoints.html)   
[Stepping Through a Program](https://www.jetbrains.com/help/idea/stepping-through-the-program.html)

## 1. Setting Breakpoints  
1. You can set customize the condition. Your condition can be any 
True/False statement that would compile at this point in the code.
2. The line is about to execute, not having executed.
## 2. Press Shift + F9
Shift + F10 is Running.
## 3. Stepping Through Code
**Before pressing F7, just remember to FORMULATE a HYPOTHESIS !!!**
### Step Out 
If you are in a function or loop, this allows you to skip 
the rest of the frame, essentially bringing you out to wherever this 
function was called.
### Drop Frame
This allows you to reset the current frame by returning to the previous frame where it was called. This is useful if you
missed the part of a function you were trying to see by essentially letting you rewind time.
### Resume Program
Continues the program until it hits the next breakpoint.


***
# JUnit and Unit Testing
[Lab2](https://sp21.datastructur.es/materials/lab/lab2/lab2#recap-debugging)    





