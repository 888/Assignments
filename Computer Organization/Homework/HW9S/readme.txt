Bee Shooting Game

There 9 total of 9 bees in the game. They are randonly fying on the screen.

There is a archery at the bottom of the screen. This archery can either go to the left if the left button is pressed or go to the right if the right button is pressed

Use either button z or x to shoot the bees.
Hit backspace to go back to the title screen at anytime.

The game starts with:

	If time > TIME - 5 or t <= 10
		6 regular bees in different color will be display. Player get 1 point for each bee he or she shoots.

	If time >= TIME - 15 or time <= TIME - 5
		6 regular bees and a bee with a heart and a bobus bee will be display. Player gets 1 point if he or she shoots any of the regular bee,
			   loses 1 life if he or she shoots the bee with the heart
			   gets 5 points if he or she shoots the bonus bee.

	If time > 10 and time < TIME - 15 
		6 regular bees and a bee with a heart, a bobus bee and a yellow jacket will be display.
		Player gets 1 point if he shoots any of the regular bee,
			   loses 1 life if he or she the bee with the heart
			   gets 5 points if he or she shoots the bonus bee.
			   wins the game if he or she shoots the yellow jacket

	They player wins the game if he gets 15 points
				lose the game if he or she ran out of lives
				lose the game if he or she ran out of time

There a counter for the game loop
	the time is only update when the counter mod 50 is 0
	the position of the bee only update when the counter mod 10 i 0
