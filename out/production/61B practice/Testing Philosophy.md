# Testing Philosophy
We should definitely write tests but only when they might be useful! Taking inspiration from TDD, writing your tests before writing code can also be very helpful in some cases.
## 1. ADD (Autograder driven development)
Very bad habit, time-wasting, inefficient.
## 2. TDD (Test driven development)
### Unit test (JUnit)
Steps:
1. Identify a new feature.
2. Write a unit test for the feature.
3. **Run the test, it should fail.**
4. Coding.
5. Optional: Refactor code to make it faster, cleaner, etc.    

Only write when they might be useful!
### Integration Testing
Not JUnit's focus, but JUnit still can do this.  
Used for large module interaction or system, since Unit tests are great but we should also make sure these units work properly together.    
Shortcoming:
1. Tedious to do manually. 
2. Challenging to automate.
3. Testing at highest level of abstraction may miss subtle or rare errors.

