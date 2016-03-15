---
title: hw-music
---

##Music Collection
---
<br>
[Download Zip](https://github.gatech.edu/cs1331-spring2016/hw-music/archive/master.zip)
<br>

##Introduction

In this assignment, you will be simulating a music collection system. You'll need to use classes and arrays.


##Problem Description

You are a music collector who exclusively collects vinyl (because the quality of non-physical media is atrocious, of course). However, you are having a difficult time remembering all of your music and all of your favorite songs for each album. So, in order to make things faster, you've decided to use your Object-Oriented Programming knowledge to list all your music. To accomplish this, you'll need to write a few classes: `Song.java`, `Album.java`, and `MusicCollection.java`. Each of these classes will represent a single Song, an Album of songs, and the Music Collection  which utilizes them.


##Solution Description

Three blank files have been provided for you with the correct names, you should write you code in these files.

###Song Class

Create a class named `Song` that will represent an individual song.

* Instance Variables:
    * A String `title` representing the title of the song.
    * A String `artist` for the song artist.
    * A String `genre` for the song genre.
* Constructors. You will have multiple constructors which utilize *constructor chaining*.
    * `public Song(String title, String artist, String genre)`
    * `public Song(String title, String artist)`
        * Utilizes constructor chaining and sets a default value of "Unknown" for the genre.
* Getters. Provide getters for the following variables using **standard getter notation**:
    * `title`
        * For example: `public String getTitle()`
    * `artist`
    * `genre`
* Setters. Provide a setter for the following variable using **standard setter notation**.
    * `genre`
        * For example: `public void setGenre(String genre)`
* `toString()`: A toString() method that **returns** a `String` comprised of the `artist`, `title`, and `genre`.

###Album Class

Create a class named `Album` that represents an album in your music collection. An `Album` can have any number of `Song` objects stored in an array.

* **Private** instance variables:
    * A `String` `title` representing the title of the album.
    * A `String` `artist` for the album artist.
    * A `Song[]` `songs` for the tracks of the album.
    * A `int` `numSongs` holding the number of songs the album contains.
* Constructor which initializes instance variables of `Album`.
    * `public Album(String title, String artist)`
* Getters. Provide getters for the following variables using **standard getter notation**:
    * `title`
    * `artist`
    * `songs`
    * `numSongs`
* `public void addSong(Song s)`
    * If the `songs` array has space remaining, put the provided song into the array and increment `numSongs`.
    * If it doesn't, create a temporary array that is double the size of the `songs` array, copy all elements from the current `songs` array into the new temporary array, set the temporary array to be the `songs` array, and add the song to the now larger array (remember to increment `numSongs`).
* `toString()`: A toString() method that **returns** a String comprised of the `title` and `artist`. Should also include the `toString()`s of each song in the album (see example output below).


###MusicCollection

Create a class named `MusicCollection` that represents your entire music collection. Start with at least 3 `Album`s, each of which has at least 1 `Song` (these `Album`s are just variables in your `MusicCollection` class). You can set these to albums and songs of your choice - feel free to include your favorite music! You will then print out the collection and allow the user to pick an album to perform a certain action on.

Your music collection should have a **single static** `Scanner` object that you use throughout the class to read user input.

This class will contain your `main` method which should perform the following:

* Instantiate at least 3 `Album`s and give each at least one `Song`.
* Write a loop that performs the following:
    * Prints out a numbered ordering of the album titles and artists only. You must use the `Album`'s methods to retrieve this information (title and artist)!
    * Allows the user to select an `Album`, then call the `albumOptions` method on that album.
    * The loop should terminate if the user enters -1 for the album selection, otherwise it should continue looping.

You will also have a static (why must this be static?) `albumOptions` method. The header will look as follows: `public static void albumOptions(Album albumName)`

As you can see, this method will take in an `Album` object. The method should provide the following functionality:

* Print out the album information using the `toString()` method.
* Prompt the user for the following actions:
    * *Add song*: Allow the user to input the title, artist, and (maybe) genre of a new song. Create a new `Song` from this user input and add it to that `Album`. A user should be able to input a song that only has a title and artist!
    * *Go Back*: Allow the user to return to the main loop.

## Example

```
java MusicCollection
[0] The Dark Side of the Moon - Pink Floyd
[1] Graduation - Kanye West
[2] Songs About Jane - Maroon 5
1

Graduation - Kanye West
        'Stronger' - Kanye West, Hip Hop
        'Good Life' - Kanye West, Hip Hop
        'Flashing Lights' - Kanye West, Hip Hop

[0] Add Song
[1] Go Back
1

[0] The Dark Side of the Moon - Pink Floyd
[1] Graduation - Kanye West
[2] Songs About Jane - Maroon 5
2

Songs About Jane - Maroon 5
        'Harder to Breathe' - Maroon 5, Unknown
        'This Love' - Maroon 5, Unknown
        'She Will Be Loved' - Maroon 5, Unknown

[0] Add Song
[1] Go Back

0
        Enter title, artist[, genre]: Sunday Morning, Maroon 5

[0] The Dark Side of the Moon - Pink Floyd
[1] Graduation - Kanye West
[2] Songs About Jane - Maroon 5
2

Songs About Jane - Maroon 5
        'Harder to Breathe' - Maroon 5, Unknown
        'This Love' - Maroon 5, Unknown
        'She Will Be Loved' - Maroon 5, Unknown
        'Sunday Morning' -  Maroon 5, Unknown

[0] Add Song
[1] Go Back
1

[0] The Dark Side of the Moon - Pink Floyd
[1] Graduation - Kanye West
[2] Songs About Jane - Maroon 5
0

The Dark Side of the Moon - Pink Floyd
        'Time' - Pink Floyd, Progressive Rock
        'Money' - Pink Floyd, Progressive Rock
        'Speak to Me' - Pink Floyd, Progressive Rock

[0] Add Song
[1] Go Back

0
        Enter title, artist[, genre]: Breathe, Pink Floyd, Progressive Rock

[0] The Dark Side of the Moon - Pink Floyd
[1] Graduation - Kanye West
[2] Songs About Jane - Maroon 5
0

The Dark Side of the Moon - Pink Floyd
        'Time' - Pink Floyd, Progressive Rock
        'Money' - Pink Floyd, Progressive Rock
        'Speak to Me' - Pink Floyd, Progressive Rock
        'Breathe' -  Pink Floyd,  Progressive Rock

[0] Add Song
[1] Go Back
1

[0] The Dark Side of the Moon - Pink Floyd
[1] Graduation - Kanye West
[2] Songs About Jane - Maroon 5
-1
```

## Tips

* You can assume that a valid album number will always be selected in the main loop.
* You can assume that valid `String` inputs will be given when adding `Song`s to an `Album`.


## Submission

You will need to have [gradle version 2.10+](http://gradle.org/gradle-download/) installed to submit your homework. Once you have it installed, submit your assignment by running from the root of your homework directory:

```bash
gradle -q submit
```

Remember to check that your files were submitted successfully! They will be located in a repository on your github.gatech.edu account with the name hw-music after you have submitted them. You can submit as many times as you want, we will take your last submission prior to the time the assignment is due. Also note that java source files will appear inside src/main/java on GitHub - this is normal, just click on the folder name to be taken to your submitted java source files.


## Grading

### Files that should appear in your repository once it is submitted:
* Song.java
* Album.java
* MusicCollection.java


### Checkstyle
* Run checkstyle from the root directory of your homework using the following command:

```bash
gradle -q checkstyle
```

* You will be graded based on the results of this command, not any seperate jar!
* If you encounter trouble running checkstyle, check Piazza for a solution and/or ask a TA as soon as you can!
* Cap for this assignment:
    * **25** Points


### Remember! 

**Submissions that do not compile will recieve a zero!**

This means the entire submission. Make sure every Java file that is submitted compiles successfully!
