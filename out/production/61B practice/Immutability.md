# The Idea of Immutability
## String
String are immutable, if you make a tiny modification to a string, a new string 
will be created, and the original string just remains the same, so reduce 
the modification times as much as possible!!!

Otherwise, the program will take up a lot of memory space and time!!!

Specific solutions:
1. Reduce the modification times.
2. Change the type from `String` to `StringBuilder`, and `new` the String with 
the constructor `StringBuilder()`. Then use `.append(String)` instead of `+=`.
The runtime of this method is **linear**, since StringBuilder is mutable.
3. Use`.jion`, see this in W6, `ArraySet`.