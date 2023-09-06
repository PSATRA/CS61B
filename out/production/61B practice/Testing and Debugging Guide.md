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

## 1. Setting Line Breakpoints  
You can set customize the condition. Your condition can be any 
True/False statement that would compile at this point in the code.

The line is about to execute, not having executed.   

Notice: If you want to debug a program with unit test, set the 
breakpoint in the **program** (first line of the buggy method), then
click debug in its **unit test**.
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
### Resume (F9)
Continues the program until(before) it hits the next breakpoint.

If it meets the condition in the breakpoint, it will automatically stop.


***
# JUnit and Unit Testing
[Lab2](https://sp21.datastructur.es/materials/lab/lab2/lab2#recap-debugging)

JUnit tests are short-circuiting – as soon as one of the asserts in a method fails, it will output the failure and move on to the next test.     

Try clicking on the `ArithmeticTest.java:27` in the window at the bottom of the screen and IntelliJ will take you straight to the line which caused the test to fail. This can come in handy when running your own tests on later projects.

***
# Randomized Testing
See random testing for methods in [lab3](https://sp21.datastructur.es/materials/lab/lab3/lab3). In this lab, we are going to see how to:
1. Empirically measure the time it takes to construct a data structure.
2. Empirically measure the runtime of a data structure’s methods as a function of the size of the data structure.
3. Perform a comparison test between two implementations of a class.
4. Randomly call methods inside a class.
5. Perform random comparison tests between two implementations of a class.
6. Use the resume button in IntelliJ.
7. Add a condition to a breakpoint.
8. Create an execution breakpoint.

Note: Randomized tests should not be used 
as a replacement for well-designed unit tests! 
I personally generally lean towards non-random 
tests where possible, and think of randomized tests as 
a supplemental testing approach. See [this thread](https://news.ycombinator.com/item?id=24349522) for 
a debate on this issue.


