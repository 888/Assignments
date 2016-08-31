---
title: hw-histogram
---

## Important! Read Very Carefully

This homework with be the first homework in which you will make use of the gradle tool to do more than submit - you will be using it for Checkstyle. *It is very important that you read and understand the next few instructions as they apply not only to this homework, but to all future homeworks.*

---
<br>

- This homework has several files contained within it. Some you are supposed to change, some must not be altered whatsoever.
  - `build.gradle`: This file controls the gradle commands. *Do not move or modify this file!*
  - `cs1331-checkstyle.xml`: This file controls the checkstyle rules that check your code. *Do not move or modify this file!*
  - `README.md`: This file contains a very brief overview of the topics covered in this homework.
  - `index.md`: This file contains what you're reading right now.
  - `src`: This **directory** contains another directory `main` which in turn contains two more directories `java` and `resources` which are described below:
  
      - `src/main/java`: This directory contains all of the Java source code files that you will write (This homework only has one source file, `GradeHistogram.java`).
      - `src/main/resources`: This directory contains all of the files that may be used by your program, but are *not* more source code files (This homework has one resource file, `grades.csv`).
  - `build`: This **directory** is not contained in your homework's project root directory when you start work. You will find though, that after you use Checkstyle, it appears. You should ignore this directory. *Do not move or modify this directory!*

---

#### STOP!
If you have not read the above or don't *fully* understand what every file and direcotry in your homework is there for and whether or not you can modify them, please read the above again.

---
<br>

- You need to understand what "*project root*" means. This phrase refers to the directory that contains the `build.gradle`, `cs1331-checkstyle.xml`, `README.md` and `index.md` as well as the `src/` directory. The name of this folder is the name of the homework followed by `-master` (in the case of this homework it will be named `hw-histogram-master`). When you are asked to run a command "from the project root" that means that your command line should be in this directory when you run the command. 
- You will be running two commands from the project root for this homework: `gradle -q submit` and `gradle -q checkstyle`. Running a command from the project root of your homework may look something like this:

        Windows:
        C:\...\cs1331\hw-historgram-master> gradle -q submit

        Mac/Linux:
        ~/.../cs1331/hw-histogram-master $ gradle -q submit

- You also need to understand how to compile and run your code. You will be compiling your code with the command `javac` (in this homework, this will look like `javac GradeHistogram.java`). You will then run the program with the command `java` (in this homework, this will look like `java GradeHistogram`). 
- Now, here's the important part: *you will be running both of these commands in the* `src/main/java` *directory, not the project root of your homework!* Running a command from the `src/main/java` directory of your homework may look something like this:

        Windows:
        C:\...\cs1331\hw-histogram-master\src\main\java> javac GradeHistogram.java

        Mac/Linux:
        ~/.../cs1331/hw-histogram-master/src/main/java $ javac GradeHistogram.java

**In other words, you will be running some commands from the _project root_ of your homework and others from the** `src/main/java` **directory!** This means that if a command fails, the *first* thing you should check is if you ran it from the correct directory.

---

#### STOP!
If you have not read the above or don't *fully* understand how and where to run the following commands: `gradle -q submit`, `gradle -q checkstyle`, `javac`, and `java`, then please read the above again.

---
<br>

- As mentioned above, you will be using `javac` to compile your code. This command creates a `.class` file for every `.java` file that is being compiled. These `.class` files are the ones that are run by the `java` command. Remember that the `java` command is called with the name of the `.class` file that corresponds to the `.java` file that contains the `main` method, *not* the `.class` file itself (`java GradeHistogram ../resources/grades.csv` is correct, `java GradeHistogram.class ../resources/grades.csv` is not).
- The `.class` files can interfere with the submit and Checkstyle commands so both commands are set to remove the .class files automatically when they are run. This means if you run the Checkstyle or submit commands, you must recompile your code before you can run your program.

- You will be using Checkstyle to check your source code. Checkstyle makes sure your source code conforms with the style guide available on the course website. For each instance that your source code is in violation of the style guide, Checkstyle will generate an error stating what is wrong. 

**You will lose points based on the number of errors Checkstyle reports!**

As stated previously, the Checkstyle command `gradle -q checkstyle` is run from the *project root* of your homework as shown below:

        Windows:
        C:\...\cs1331\hw-historgram-master> gradle -q checkstyle

        Mac/Linux:
        ~/.../cs1331/hw-histogram-master $ gradle -q checkstyle

This command will check your `.java` files contained in the `src/main/java` directory of your homework for style errors and generate a report in the form of an HTML file which will be automatically opened in your default web browser. If the file fails to open, but the command says it completed without failing, the HTML report can be found at `hw-histogram-master/build/reports/checkstyle/main.html`.

**The total number of errors displayed in the table at the very top of this report is the number of points that you can lose. (Some assignments may have a Checkstyle deduction cap which can be found at the bottom of the instuctions).**

---

#### STOP!
If you have not read the above or don't *fully* understand that running the submit or Checkstyle commands will remove all `.class` files form the `src/main/java` directory or how Checkstyle is run and the impact it can have on your grade, then please read the above again.

---
<br>

Just as a final review of the directory structure of homeworks, here is a graphical representation of a homework:

        hw-histogram        <-- The *project root*, all "gradle" commands should be run from here.
        |
        +--- README.md        <--- Contains a general overview of the homework.
        |
        +--- build.gradle        <--- Controls gradle commands, should not be modified or moved.
        |
        +--- cs1331-checkstyle.xml        <--- Controls Checkstyle, should not be modified or moved.
        |
        +--- index.md        <--- Contains these instructions.
        |
        +--- /src
        |    |
        |    +--- /main
        |         |
        |         +--- /java        <--- Contains all java source files. "javac" and "java" commands should be run from here.
        |         |    |
        |         |    +--- GradeHistogram.java        <--- A java source code file.
        |         |
        |         +--- /resources        <--- Contains all files needed for your program that aren't source code files.
        |              |
        |              +--- grades.csv        <--- A resource file used by your program.
        |
        +--- /build        <--- Contains data created by "gradle" commands, should not be modified or moved.


#### You made it! Now you know everything you need to breeze through this homework and any future ones!

<br><br>


[Download Zip](https://github.gatech.edu/cs1331-spring2016/hw-histogram/archive/master.zip)


## Intro
The homework will cover basic Java control structures, arrays, file I/O, and string manipulation.

## Problem Description
You are a Georgia Tech professor teaching a class.
It is the end of the semester and you wish to see how your students performed, so you write a Java program that will create a histogram of the grade distribution.
You want this histogram program to be able to give you a very detailed view or a very broad view of the grade distribution.
To accomplish this, it asks the user what size bins the grades should be split into.

## Solution Description
Put your code in a file named `GradeHistogram.java`. We have provided an empty file with this name located in `src/main/java`.

We have also provided you with a CSV file named `grades.csv`, located in `src/main/resources`, that has a list of students and their grades. A CSV file is just a text file with data partitioned by commas and (in this case) newlines.
There may be any number of spaces surrounding the comma so make sure your program can handle variable spaces.
Students may have both a first name and last name or just a first name so the comma is really the only thing that will separate the names from the grades.

These grades are not sorted but they are bound between 0 and 100 (inclusive). For example, the file may look like:

        Glenn Hollingsworth,91
        Chris Simpkins, 100
        Thomas Shields,89
        Bob   , 55
        Alice,   95
        Eve, 87

**Use command-line arguments to inform your program of the name of the grades file** - see the example below for how to pass the file name in while running the program. Hardcoding any paths into your source code may result in point deductions!

Using an array, you must count the frequency of each grade value in the file and print it to the standard output as a horizontal histogram. For every grade in a particular range, print a pair of brackets, `[]`.

**You must only read each line once and not more.**
This may seem like an arbitrary restriction, but there are situations in high-performance code where file access is slow and must be minimized. This means that you cannot read the whole file into one big String or each line into an array of Strings. Doing so will be considered reading the file more than once, and a point deduction may be made.
If you have any questions about what this requirement allows please ask questions.

**You may not use ArrayList.** You are only allowed to use basic arrays. This doesn't mean you can't use Strings or Scanners (or even BufferedReaders); this rule is here just to avoid trivializing the assignment.

You must also label the range of each histogram bar and allow the user to indicate what size interval they would like the histogram to be made with. In other words, if the user specifies a size interval of 5, the first line ranges from 100 - 96, the second line ranges from 95 - 91, etc.
You must allow the user to specify this size interval in each of the following ways:

1. The size interval may be specified as an additional command line arg, e.g `java GradeHistogram ../resources/grades.csv 5`
2. If the second command line arg is not present, your program must ask the user for the interval size.

If the number of intervals does not divide evenly, the slack (the one interval that is smaller than the others) should be made up on the *lower* end.
See the example output below.

Running the program should look like this:

> **Note:** `$` is the command prompt on Unix.  On Windows it will look like `C:>`

        $ java GradeHistogram ../resources/grades.csv
        What interval size would you like?
        >>> 10
        100 - 91 | [][][][][][][][][][][][]
         90 - 81 | [][][][][][][][][][][][][][][][][][][][][][][][][][]
         80 - 71 | [][][][][][][][][][][][][][]
         70 - 61 | [][][][][][][][][][][][][][][][][]
         60 - 51 | [][][][][][][]
         50 - 41 | [][][][]
         40 - 31 | [][][][][][]
         30 - 21 | [][]
         20 - 11 | 
         10 -  1 | []
          0 -  0 | []

        $ java GradeHistogram ../resources/grades.csv 5
        100 - 96 | [][][][][][]
         95 - 91 | [][][][][][]
         90 - 86 | [][][][][][][][][][][][][][][][][]
         85 - 81 | [][][][][][][][][]
         80 - 76 | [][][][][][][][][][][][]
         75 - 71 | [][]
         70 - 66 | [][][][][][][][][][]
         65 - 61 | [][][][][][][]
         60 - 56 | [][][][][]
         55 - 51 | [][]
         50 - 46 | [][][]
         45 - 41 | []
         40 - 36 | [][][]
         35 - 31 | [][][]
         30 - 26 | []
         25 - 21 | []
         20 - 16 | 
         15 - 11 | 
         10 -  6 | []
          5 -  1 | 
          0 -  0 | []


> **Note:** The pipe characters must be aligned and your program must not exclude any subrange between 0 and 100. 0-0 is not a special range - it is the slack that was mentioned earlier. 5 and 10 happen to produce a 0-0 range but you may have other ranges at the bottom like, for example, 3-0.

## Tips
1. You may assume that you always get valid input.
2. You may assume the text file has valid integers.
3. 101 is a prime number.
4. An array is a fixed size data structure; you need to know ahead of time how big it needs to be. How do we do this?
5. You can give interpretations to the indices and contents of an array to arrive at creative solutions to problems. Code smart, not hard.
6. Creating a `Scanner` object with a file will throw a checked exception. Don't worry about what this means --- for now, just append `throws Exception` to the end of the `main` method signature wherein the file is opened.
7. 1 is a valid size interval and so is 101.
8. [`System.out.printf`](https://docs.oracle.com/javase/tutorial/essential/io/formatting.html) may be of use.

## Submission

You will need to have [gradle version 2.10+](http://gradle.org/gradle-download/) installed to submit your homework. Once you have it installed, submit your assignment by running from the project root of your homework directory:

        gradle -q submit

Remember to check that your files were submitted successfully! They will be located in a repository on your github.gatech.edu account with the name hw-histogram after you have submitted them. You can submit as many times as you want, we will take your last submission prior to the time the assignment is due. Also note that java source files will appear inside src/main/java on GitHub - this is normal, just click on the directory name to be taken to your submitted java source files.

## Grading

Files that should appear in your repository once it is submitted:
  
  - `GradeHistogram.java`

Checkstyle Cap: 
  
  - 5 Points

#### Submissions that do not compile will recieve a zero!
This means the entire submission. Make sure every Java file that is submitted compiles successfully!
