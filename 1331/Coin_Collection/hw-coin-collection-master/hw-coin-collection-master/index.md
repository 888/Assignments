---
title: hw-coin-collection
---

# Coin Collection
<hr>
<br>
[Download Zip](https://github.gatech.edu/cs1331-spring2016/hw-coin-collection/archive/master.zip)
<br>
## Problem Description
You are a Software Engineer in the year 2035, and in order to fill up your free time you decide to pick up the action-packed hobby of coin collecting.  You quickly grow your collection using all the money you're making from your lucrative programming job, but as your collection grows it becomes harder to keep track of.  To make matters worse, the government began circulation of "Magic" coins in 2029 which have a randomly assigned value anywhere from 26¢ to $1.00.

You want to be able to keep track of all the coins in your collection, and also sort them by year and amount.  For quarters, you also want to be able to sort by the state they commemorate.

## Solution Description

The classes below have been provided to you. Most are missing functionality. This means you should add what is necessary to the specified classes in order to make them match the specfication below. This also means that from the start, this homework *will not compile*. This is by design. You must add everything needed first to make it compile, and then to make it work *correctly*.


###  MySet
You'll be creating an implementation of Java's `Set` interface to help you keep track of your coins, called `MySet`.
However, `MySet` should not be hardcoded to only allow `Coin`s to be stored. Instead you should use Java's Generics to allow your set to work with any objects you desire.

- `MySet` should implement the following methods from Java's `Set` interface:
   - `public boolean add(E e)`
   - `public boolean addAll(Collection<? extends E> c)`
   - `public void clear()`
   - `public boolean contains(Object o)`
   - `public boolean containsAll(Collection<?> c)`
   - `public boolean equals(Object o)`
   - `public boolean isEmpty()`
   - `public boolean remove(Object o)`
   - `public boolean removeAll(Collection<?> c)`
   - `public boolean retainAll(Collection<?> c)`
   - `public int size()`
   - `public Object[] toArray()`
- You'll notice that the `Set` interface has a few more methods listed than the ones we're requiring you to write. We have provided you with `public Iterator<E> iterator()`, `public <T> T[] toArray(T[] a)`, and `public int hashCode()`. You should not modify these methods.
- You can find a description of these methods and what they do [in the Javadocs for Set](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html).
- Carefully read through the Javadocs as you write these methods, your methods *must* meet the standards set by the Javadocs for `Set`.

####Generics:
- An example of using generics can be seen [here](https://docs.oracle.com/javase/tutorial/java/generics/types.html)
- Directly creating an array of type `T[]` in Java using `new` isn't possible.  Instead, you'll need to use `(T[]) new Object[desired_length];`. Ignore the cast warning.

### MySetTester
- A `MySetTester` class has been provided to you, with a handful of tests already written.
- At the bare minimum, you **MUST** complete the `MySetTester` class so that it calls each of the above methods that you had to write and does a basic verification that they are working.
- This portion of the assignment is fairly open-ended, but the more testing you do, the more likely you are to catch mistakes in MySet that could otherwise lead to point deductions.

### Coin
- `Coin` is an `abstract` class representing all types of coins. Coins are [Comparable](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)!
- `Coin` has the following instance variables:
    - An `int` `value` representing the value of a coin in cents.
    - An `int` `year` representing the year of production.
- `Coin` has a single contructor which takes in both `value` and `year` (in that order).
- `Coin` has the following methods:
    - Getter methods for both `value` and `year` (remember to use standard getter naming).
    - A `toString()` method that **returns** a `String` comprised of the `value` and `year`.
        - For example: `Coin: 25 1995`
    - `compareTo(Coin other)` which overrides `compareTo`.
        - A coin is considered greater than another coin if it has larger value. When coins have the same `value`, a newer coin is considered greater.
    - `equals(Object other)`
        - A `Coin` is equal to another object if that object is not null, is a `Coin`, and has the same `value` and `year`.

### Different types of coins
- You cannot create just a `Coin`. It must be either a `Penny`, a `Nickel`, a `Dime`, a `Quarter`, or a `MagicCoin` (all of which have an Is-A relationship with `Coin`).
- You must complete these five classes accoreding to the specifications below.
- `Penny`, `Nickel` and `Dime` have fixed `value`, so think about the number of parameters their constructors need.
    - You want to override `toString` to show the type of coin.
        - For example: A `Penny`'s `toString` method would return `Penny: 1 1994`
    - They have no special functionality that `Coin` does not have. Do you think you need to override any other method?
- `Quarter` has an aditional property, `state` of type `State`, as you want to collect the 50 State quarter series.
    - `State` is an enum that is given to you.
    - This additional field means you must override `equals`, `compareTo` and `toString` to include the new field.
        - A `Quarter` is equal to an object if that object is not null, is a `Quarter`, and has same `year` and `state`.
        - A `Quarter` is considered to be greater than another `Quarter` if its `State` came earlier in the alphabet, if the `State` is the same, the newer `Quarter` is considered to be greater.
    - Note that the value of quarter is also fixed, so how many parameters should your constructor take in?
- `MagicCoin` is a MAGIC coin.
    - It can have value between 26 cents to 100 cents inclusive.
    - You can construct a magic coin with a `value` and a `year`, or you can just make it with a `year` and let the coin maker decide the exact value (which implies that you need two constructors). This value can be randomly chosed on defaulted to a specific valid number.
    - You must also override `toString` to distinguish `MagicCoin` from other `Coin`s.

Note: we will be **very strict** about duplicate code. Think hard about how to reuse the methods in superclass and only override when necessary. Do not add extra parameter to constructors when unnecessary. Do not write unnecessary public methods. Again, we **WILL** take off points if you write unnecessary code in these classes.

### CoinBook
- Here comes the important part. You will write a `CoinBook` to keep track of and display all your coins.
- Instance Variables:
    - You do not want duplicate coins in your collection, so all the coins will be saved in a private field `coinBook` of type `Set` which can only contain `Coin`. (Generic type!)
- `CoinBook`'s constructor should initialize `coinBook` in the constructor.
    - `coinBook` can either be a `MySet` that you just created, (or a `HashSet` if for some reason you were unable to complete your `MySet`). `coinBook` must still be declared as a `Set`, however you should initialize it as a `MySet` or `HashSet`.
- `CoinBook` should contain the following methods:
    - `public boolean addCoin(Coin c)`
        - add a `Coin` to `coinBook`.
        - return `true` of the Coin is added successfully, or `false` otherwise.
    - `public boolean removeCoin(Coin c)`
        - remove the `Coin` from `coinBook`.
        - return `true` if the Coin is removed successfully, for `false` otherwise.
    - `public List<Coin> sortByValue()`
        - return a List which contains all the coins in `coinBook` and is sorted by `value` (and then by `State` if the Coin is a `Quarter`), and then by `year`.
    - `public List<Coin> sortByYear()`
        - return a List which contains all the coins in `coinBook` and is sorted by `year` and then by `value`, (and then by `State` if the Coin is a `Quarter`).
    - `toString()`
        - returns a `String` comprised of the `toString`s of all of the Coins in `coinBook`.

### CoinBookTester

This is the place for you to test your Coins and CoinBook. We have already written some testing code for you, and you do not have to write more code here, but feel free to test more here and have fun collecting coins!

### Tips

Here are some general tips for this assignment!

- The slides about [Collections](http://cs1331.gatech.edu/slides/collections.pdf) and [Collections Algorithm](http://cs1331.gatech.edu/slides/collections-algorithms.pdf) can be helpful for writing this homework.
- You are not allowed to use the `java.util.Arrays` class for this assignment.
- You are not allowed to use `break` or `continue` for this assignment.
- Any time you override a method, it is good to use the `@Override` tag. If you include this, checkstyle will excuse you from having to write a Javadoc comment for that method.
- PLEASE START EARLY. There is a lot to do!


<hr>

## Submission

You will need to have [gradle version 2.10+](http://gradle.org/gradle-download/) installed to submit your homework. Once you have it installed, submit your assignment by running from the root of your homework directory:

```bash
gradle -q submit
```

Remember to check that your files were submitted successfully! They will be located in a repository on your github.gatech.edu account with the name hw-coin-collection after you have submitted them. You can submit as many times as you want, we will take your last submission prior to the time the assignment is due. Also note that java source files will appear inside `src/main` on GitHub - this is normal, just click on the folder name to be taken to your submitted java source files.


## Grading

###Files to Submit

This assignment will require that all files in your `src/main/java` directory are submitted. *If you have extra files that you don't use, but are submitted and do not compile, you will receive a zero!*

### Checkstyle
- Run checkstyle from the root directory of your homework using the following command:

```bash
gradle -q checkstyle
```

- You are now required to write Javadoc comments, checkstyle will show errors where Javadocs are missing.
    - Every class should have a class level Javadoc that includes `@author <GT Username>`.
    - Every method should have a Javadoc explaining what the method does and includes any of the following tags if applicable.
        - `@param <parameter name> <brief description of parameter>`
        - `@returns <brief description of what is returned>`
        - `@throws <Exception> <brief explanation of when the given exception is thrown>`
    - See the [CS 1331 Style Guide](http://cs1331.gatech.edu/cs1331-style-guide.html) section on Javadoc comments for examples as well as the provided code.
- You will be graded based on the results of this command, not any separate jar!
- If you encounter trouble running checkstyle, check Piazza for a solution and/or ask a TA as soon as you can!
- Cap for this assignment:
    - **100** Points

### Remember!

**Submissions that do not compile will receive a zero!**

This means the entire submission. Make sure every Java file that is submitted compiles successfully!
