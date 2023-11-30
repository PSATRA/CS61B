# Notes of CS61B Spring 2021.
[Course Website](https://sp21.datastructur.es/)    
[Reading](https://joshhug.gitbooks.io/hug61b/content/)   
[CS61B Spring 2018](https://sp18.datastructur.es/)     
[CS61C Summer 2023](https://inst.eecs.berkeley.edu/~cs61c/su23/)   
[2023 textbook](https://cs61b-2.gitbook.io/cs61b-textbook/)  

## Optimization Philosophy
1. Our final purpose is to design for users, regardless of what's 
happening under the hood. That's why we design `sentinel`, 
AList with 100 boxes, fake indices, etc.


2. Learn to **actively _analyze_** and **optimize** your own code: no matter it is a 
single method or data structure. --Try your best to find the **_small ineffectiveness_**.


3. Find the  most appropriate abstract data type (list / set / map) that for the requirement.

## Design Philosophy
1. Reduction(Decomposition)  
    1. Boil the case down to an appropriate **data structure**, like the three dog problem
    can be boiled down to a BST. 
    2. Boil the case down to an appropriate **algorithm**, like the process 
    of distinguishing the dogs are actually a sort and permutation process.


2. If you need to optimize an algorithm or something elsa, don't think from
a high level. Break it down, and **scrutinize** which part leads to high
complexity, and what tweak can I make upon this snag?

3. Write more _TODO_ command, since the work is often **non-linear**.
## Exercises
[lab1](https://sp21.datastructur.es/materials/lab/lab1/lab1) Introduction and set up  
[lab2](https://sp21.datastructur.es/materials/lab/lab2/lab2) JUnit Test and Debugging  
[lab3](https://sp21.datastructur.es/materials/lab/lab3/lab3) Timing Tests and Randomized Comparison Tests   
[lab4](https://sp21.datastructur.es/materials/lab/lab4/lab4) Git and Debugging  
[lab5](https://sp21.datastructur.es/materials/lab/lab5/lab5) Project 1 Peer Code Review  
[lab6](https://sp21.datastructur.es/materials/lab/lab6/lab6) Getting Started on Project 2   
[lab6_2019](https://sp19.datastructur.es/materials/lab/lab6/lab6) Disjoint Sets     
[lab6_2019_Challenge](https://sp19.datastructur.es/materials/clab/clab6/clab6) Falling Bubbles (TODO)   
[lab7](https://sp21.datastructur.es/materials/lab/lab7/lab7) BSTMap   
[lab8](https://sp21.datastructur.es/materials/lab/lab8/lab8) HashMap  
[lab12](https://sp21.datastructur.es/materials/lab/lab12/lab12) Getting Started on Project 3  
[lab13]() Getting Started on Project 3, Phase 2 

[project 0](https://sp21.datastructur.es/materials/proj/proj0/proj0) 2048   
[project 1](https://sp21.datastructur.es/materials/proj/proj1/proj1) Data Structures   
[project 2](https://sp21.datastructur.es/materials/proj/proj2/proj2) Gitlet  
[project 3](https://sp21.datastructur.es/materials/proj/proj3/proj3) CS61BYoW  
[project 3](https://sp21.datastructur.es/materials/proj/proj3/proj3GameSharing) Game Sharing  

## Java Guide
[Memory Usage Estimation 1](http://blog.kiyanpro.com/2016/10/07/system_design/memory-usage-estimation-in-java/)     
[Memory Usage Estimation 2](https://www.javamex.com/tutorials/memory/object_memory_usage.shtml)    
## Guide
[Command Line Programming, Git, Project 2 Preview](https://www.youtube.com/watch?v=fvhqn5PeU_Q)     
[Using Git](https://sp19.datastructur.es/materials/guides/using-git)    
[Git WTFS](https://sp19.datastructur.es/materials/guides/git-wtfs)   
[Git Intro - Part 1](https://www.youtube.com/watch?v=yWBzCAY_5UI)    
[Git Intro - Part 2](https://www.youtube.com/watch?v=CnMpARAOhFg)     
[Git Intro - Part 3](https://www.youtube.com/watch?v=t0tzTcZESWk)     
[Git Intro - Part 4](https://www.youtube.com/watch?v=ca1oCEMQGRQ)     
[Git Intro - Part 5](https://www.youtube.com/watch?v=dZbj9gjjYv8)    
[Git Intro - Part 6](https://www.youtube.com/watch?v=r0oHi0vXhLE)    

[Git](https://blog.csdn.net/rory_wind/article/details/108374879)   
[Guide 1](https://www.1point3acres.com/bbs/thread-908806-1-1.html)   
[Guide 2](https://zhuanlan.zhihu.com/p/434144861)    
[Guide 3](https://docs.google.com/document/d/1lh1GyJfP4d99Kd2ubFWcHtzMgwW4M3aMDLqafMCGO7I/edit)      


## BOO
[V2ray](https://github.com/2dust/v2rayN)
## Course Code
2018: MNXYKX    
2021: MB7ZPY  
School: UC Berkeley  

## Reading
[Algorithms](https://algs4.cs.princeton.edu/home/)

## Q&A
[Lecture 2 QA](https://www.youtube.com/watch?v=M5LUOLo4k3Y)   
[Lecture 3 QA](https://www.youtube.com/watch?v=51YjFL6nBFo)   
[Lecture 4 QA](https://www.youtube.com/watch?v=20ZhW106838)   
[Lecture 5 QA](https://www.youtube.com/watch?v=46DJBZC5Yvc)   
[Lecture 6 QA](https://www.youtube.com/watch?v=IIZitaB3AVE)   
[Lecture 7 QA](https://www.youtube.com/watch?v=bN_nbaZIPfU)   
[Lecture 8 QA](https://www.youtube.com/watch?v=GGzoibmx9uY)   
[Lecture 9 QA](https://www.youtube.com/watch?v=GzrokKOAxjw)   
[Lecture 10 QA](https://www.youtube.com/watch?v=7T8eEzmPGT8)   
[Lecture 11 QA](https://www.youtube.com/watch?v=7bKEipkOj_4)    
[Lecture 14 QA](https://www.youtube.com/watch?v=Vkz2BDbcAKM)   
[Lecture 15 QA](https://www.youtube.com/watch?v=Wsb9kP59VS4)   
[Lecture 16 QA](https://www.youtube.com/watch?v=wTAFtYZ4wdY) 

[Live Lecture 12](https://www.youtube.com/watch?v=fvhqn5PeU_Q)  
[Live Lecture 17](https://www.youtube.com/watch?v=0uiVyTt8A1E)  
[Live Lecture 33](https://www.youtube.com/watch?v=KvgSAIhGn8A)   
[Live Lecture 40](https://www.youtube.com/watch?v=5VH8k7n1520)   

[sp-17 mt](https://hkn.eecs.berkeley.edu/examfiles/cs61b_sp17_mt1.pdf#page=5)
