---
title: hw-cookieshop
---

##Cookie Shop
---

<br>
[Download Zip](https://github.gatech.edu/cs1331-spring2016/hw-cookieshop/archive/master.zip)
<br>

##Introduction

This assignment will cover creating, throwing, and handling exceptions as well as review object-oriented programming design principles.

##Problem Description

A new cookie shop has opened up and started making deliveries. They want you to help them write software for the checkout page on their website. This system must be reliable and handle every possible problem.

They want to be the first company to start accepting Venmo as payment for cookies, so you will have to implement that. 

They already have a Server in place that handles some functionality, but unfortunately it is slightly buggy. Create the self-checkout machine software and then a demo to show off all of its features to the convenience store executives.

##Solution Description

A description of each class that you should write follows. Some have been either partially or wholly provided to you. Read through this entire list before starting so you have an understanding of how all of the classes interact. Make sure to check out the *Tips section* too.

<hr>

###Exceptions

####ServerException
- This has been provided for you, you should not modify this file.
- Is-a *checked* `Exception`.
- The *Server* (see below) throws this exception when something has gone wrong in one of its methods. Take note that this is a *checked* exception.

####ConnectionFailedException
- This has been provided for you, you should not modify this file.
- Is-a `ServerException`
- Thrown when a user doesn't have an internet connection (actually randomly occurs when the Server is "working").

####PaymentFailedException
- This has been provided for you, you should not modify this file.
- Is-a *checked* `Exception`.
- Has one constructor that takes in a String message.
- Thrown when an attempt to pay fails.

####CardExpiredException
- **You need to write this class**.
- Is-a `PaymentFailedException`.
- Should have one constructor that takes a [java.time.LocalDate](http://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html) that represents the expiration date of the card, and should create a message about expiration date, (e.g "Card expired on 2015-01-02"). This message should be used as this exception's message (You'll do this using a `super` call in the constructor).

####NotEnoughFundsException
- **You need to write this class**.
- Is-a `PaymentFailedException`.
- Should have one constructor that takes a `String` message documenting that there aren't enough funds from the specific `PaymentMethod` that threw this exception (see the section on `PaymentMethod`s). This message should be used as this exception's message (You'll do this using a `super` call in the constructor).

###InvalidCookieException
- **You need to write this class**.
- Is-a *checked* `Exception`
- Should have two constructors:
    - A constructor that takes in a `String` message and sets it to be this exception's message.
    - A no-args constructor which uses constructor chaining and sets the message to be `"This Cookie is not on our menu..."`.

<hr>

###Payment Methods

####PaymentMethod
- This has been provided for you, you should not modify this file.
- This is an *interface* that every class in this section must implement.
- Has one method `void pay(double amount) throws PaymentFailedException` that must be implemented.

####Card
- **You need to write this class**.
- Is-a `PaymentMethod`.
- Cannot be instantiated (think of what this means you must do). 
- A *Card* has two attributes, `ownerName` and `balance` of type `String` and `double` respectively. These should be private.
    - You should write a constructor which takes these in and sets them correctly.
- Its `pay` method should check if there is enough of a balance to pay the amount, and remove it from balance if there is. If there isn't enough balance to cover the payment, throw a `NotEnoughFundsException`.

####CreditCard
- **You need to write this class**.
- Is-a `Card`
- Has an additional attribute: `expirationDate` of type [java.time.LocalDate](http://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html). This should be private.
    - You should write a constructor which takes all necessary data in and sets them correctly.
- The `pay` method will throw a `CardExpiredException` if the `CreditCard` is passed expiration. 
    - You will need to compare the `expirationDate` with the current date (hint: look in the API for `LocalDate` for a method which return the current date). If it isn't expired, the `pay` method works just like that of `Card` (this doesn't mean you should copy the code from `Card`'s `pay` method however).

####Venmo
- **You need to write this class**.
- Is-a `PaymentMethod`.
- Has additional attributes: a `username`, `password`, and a `balance` of type `String`, `String`, and `double` respectively. All three should be private.
    - You should write a constructor which takes these in and sets them correctly.
- The `pay` method checks if you have enough funds, and if not will throw a `NotEnoughFundsException`.

<hr>

###Other Classes

####Server
- This has been provided for you, you should not modify this file.
- While its source is available, you may access its Javadocs to see what its methods do (loated above each method).
- You will be calling these methods from your `CheckoutPage` class.
- It will be used to read valid items from the "database" (csv file located in resources).

####Cookie
- This has been provided for you, **however, you must modify this file**.
- Has two constructors whose headers have been provided which you must complete. 
    - It has a `name`, `id`, `price` which must me initialized.
    - Make sure to utilize constructor chaining. If unspecified, the name should be `null` and price should be `0.0`.
- `Cookie` should have a getter method for price called `getPrice()`.
- It should also have a properly written `equals` method which compares items solely on their `id` and `name`.

####CheckoutPage
- **You need to write this class**.
- `CheckoutPage` has attributes `validCookies` and `cart`, both of type `ArrayList<Cookie>`. These must be private.
    - `validCookies` should be initialized in the constructor using the method `getValidCookies()` from the `Server` class. The `Server` may throw a `ServerException`, so you should keep trying until you successfully get the `ArrayList<Cookie>` of validItems.
    - `cart` should be initialized in the constructor to be a `new ArrayList<Cookie>()`.
- Has an `addToCart(Cookie cookie)` method that does not return anything but may throw an `InvalidCookieException`.
    - The `Cookie` passed in will only have a correct `id` and `name`. The `price` of the `Cookie` passed in may be incorrect.
    - You should check if the given `Cookie` is in the `ArrayList`. 
    - If it is, you should add the matching `Cookie` *from `validCookies`, not the given `Cookie` as the price may be wrong otherwise* to the `cart`. This is important because the `Cookie` added to the `cart` is guaranteed to have the right price because it is from the `Server`. 
    - If the given `Cookie` is not in `validCookies` list, this method should throw an `InvalidCookieException`.
- Has as a `getTotalPrice()` method that sums all the prices of the `Cookies` in the `cart` and returns that sum. 
- Has a `payForCart(PaymentMethod method)` method that does not return anything but may throw a `PaymentFailedException`. 
    - It tries to pay the `cart`'s total price with the specified `PaymentMethod` and if it succeeds, empties the `cart`.

####Demo
- **You need to write this class**.
- The `Demo` is a way to show those convenience store executives how your `CheckoutPage` works and that their money was well spent. You don't have to go all out -- non-technical execs are easily impressed by programs -- but show them that it handles their intermittently-functioning server and throws its own errors when needed. A simple format may be to print out what you will be testing, test it, catch the exception, and print its message to show that everything worked as expected.
- This means you should do a comprehensive test of `CheckoutPage` (this includes trying to `payForCart` with different `PaymentMethods` that will intentionally fail).

<hr>

##Tips
- Look at the Java API for [Exceptions](http://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html). Take note of what constructors an `Exception` has. Which constructors should you override, if any? Will you be overloading the constructor with new parameters? Which constructor will you be making super calls to?
- Look at the Java API for [java.time.LocalDate](http://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html). Several methods here may be useful. The static `now` method returns the current date. The static `of` method allows you to create an arbitrary date, and the methods `isBefore` or `isAfter` may be helpful as well.
- Taking a peek at the CSV file could be useful when you test your code in *Demo* (located in `src/main/resources`).

<hr>

## Submission

You will need to have [gradle version 2.10+](http://gradle.org/gradle-download/) installed to submit your homework. Once you have it installed, submit your assignment by running from the root of your homework directory:

```bash
gradle -q submit
```

Remember to check that your files were submitted successfully! They will be located in a repository on your github.gatech.edu account with the name hw-music after you have submitted them. You can submit as many times as you want, we will take your last submission prior to the time the assignment is due. Also note that java source files will appear inside src/main/java on GitHub - this is normal, just click on the folder name to be taken to your submitted java source files.


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
    - **75** Points

### Remember! 

**Submissions that do not compile will receive a zero!**

This means the entire submission. Make sure every Java file that is submitted compiles successfully!
