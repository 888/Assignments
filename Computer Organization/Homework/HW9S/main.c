/*
Name: Xiying Huang
Date: 07/18/2016
*/

#include "myLib.h"
#include "gameLogic.h"
#include "gameImages.h"
#include <stdio.h>
#include <stdlib.h>


int main()
{
	// Initialize the state of the game 
	int state = START;

	while (1)
	{
		switch (state)
		{
			case START :
				start();
				state = GAME;
				break;
			case GAME :
				state = game();
				break;
			case WIN :
				win();
				state = START;
				break;
			case LOSE :
				lose();
				state = START;
				break;
			default:
				break;
		}
	}
	return 0;
}

/* state = START
	Display the titile screen of the game
	Wait for the player to press start to start the game
*/
void start()
{
	REG_DISPCTL = MODE3 | BG2_ENABLE;
	WaitForVblank();
	drawTitleScreen();
	while(!KEY_DOWN_NOW(BUTTON_START));
	while(KEY_DOWN_NOW(BUTTON_START));
}

/* state = WIN
	Display the winning screen of the game
	Wait for the player to press start to restart the game
*/
void win()
{
	WaitForVblank();
	drawWinScreen();
	while(!KEY_DOWN_NOW(BUTTON_START));
	while(KEY_DOWN_NOW(BUTTON_START));
}

/* state = LOSE
	Display the losing screen of the game
	Wait for the player to press start to restart the game
*/
void lose()
{
	WaitForVblank();
	drawGameOverScreen();
	while(!KEY_DOWN_NOW(BUTTON_START));
	while(KEY_DOWN_NOW(BUTTON_START));
}

/* state = GAME
	Display the main screen of the game
	The top of the screen are time, score number of lives player has
	The middle of the screen has 6 to 9 bees plying around
	The bottom part of the screen is where the archery located, it can
	either goes left or right
*/
int game()
{
	// Initialize time and life
	int t = TIME;
	int l = LIVES;

	// Initalize the score
	int score = 0;

	//Initialize the counter to update the time
	int count = 0;
	int i;

	// Initialize buffer array to hold string to write
	// the time, score, and lives to the main screen
	char buffer[50];

	// Initialize the moving objects of the game
	MOVOBJ bee[BEES];

	// Initiialize the old objects
	MOVOBJ oldbee[BEES];

	//initialize the pointer to a current moving object
	MOVOBJ *cur;

	// Initilize the array that hold the images of objects
	const unsigned short *images[9] = {blue, green, orange, pink, red, yellow, heart, bonus, yellowJacket};

	// Initialize the array of deltas, rows and columns
	int dels[] = {-8, -6, -4, -2, 2, 4, 6, 8, 10};
	int rows[] = {rand()%55 + 15, rand()%50+ 15, 20, 35, 40, 45, 50, 55, 60};
	int cols[] = {rand()%10, rand()%40, 55, 80, 110, 140, 170, 200};

	//Put the information for each object in the structure
	//Each object has row, column, row delta, column delta, and the image.
	for (i = 0; i < BEES; i++)
	{ 
		bee[i].row = rows[rand() % 9];
		bee[i].col = cols[rand() % 9];
		bee[i].rd = dels[rand() % 9];
		bee[i].cd = dels[rand() % 9];
		bee[i].image = images[i];
		oldbee[i] = bee[i];
	}

	// Initialize the column position of the archery
	int acol = ARCHERY_COL;
	int old_acol = acol;

	// Draw the main game screen start the game and the archery
	// to start the game,
	WaitForVblank();
	drawGameScreen();
	drawArchery(ARCHERY_ROW, ARCHERY_COL);

	// The string of time, socre and lives to the game screen	
	sprintf(buffer, "Time:%d  Score:%d  Lives:%d", t, score, l);
	drawString(5, 5, buffer, RED);

	// The Game start from here

	while (1) {

		// Increment the counter
		count++;

		// Lose when ran out of time or lives
		if (t == 0 || l == 0)
		{
			return LOSE;
		}

		// Win if player has enough score
		if (score >= WIN_SCORE)
		{
			return WIN;
		}

		// Decrement the time everytime the counter reach 50
		// Re-draw the time. score and lives
		if (count % 50 == 0)
		{
			t--;
			//Update time
			eraseImage3(5, 35, 18, 15, WHITE);
			sprintf(buffer, "%d", t);
			drawString(5, 35, buffer, RED);
		}

		// Reset the game to the title screen
		// AT ANY TIME using the select key (backspace)
		if (KEY_DOWN_NOW(BUTTON_SELECT))
		{
			return START;
		}

		// Show 6 regular bees and a bee with a heart
		// when t > TIME - 25 or t <= 10
		// The player lose 1 life if shoot the bee with the heart

		if (t > TIME - 5 || t <= 10)
		{
			WaitForVblank();
			for (i = 0; i < BEES - 3; i++)
			{
				cur = bee + i;
				drawImage3(bee->row, bee->col, 25, 25, bee->image);
			}
		}

		// Show 7 bees as before and a bonus bee
		// When TIME - 15 <= t <= TIME - 25
		// The player get 5 points if shoot the bonus bee
		if (t >= TIME - 15 && t <= TIME - 5)
		{
			WaitForVblank();
			for (i = 0; i < BEES - 1; i++)
			{
				cur = bee + i;
				drawImage3(bee->row, bee->col, 25, 25, bee->image);
			}
		}

		// Show 8 bees as before and a yellow jacket
		// When 10 < t < TIME - 15
		// Player win the game if shoot the yellow jacket
		if (t > 10 && t < TIME - 15)
		{
			WaitForVblank();
			for (i = 0; i < BEES; i++)
			{
				cur = bee + i;
				drawImage3(bee->row, bee->col, 25, 25, bee->image);
			}
		}
		

		// Update the position of the bee when the counter % 10 == 0
		if (count % 10 == 0)
		{
			if (t > TIME - 5 || t <= 10)
			{
				for (i = 0; i < BEES - 3; i++)
				{
					cur = bee + i;
					cur->row = cur->row + cur->rd;
					cur->col = cur->col + cur->cd;
					boundsCheck(&cur->row, &cur->col, &cur->rd, &cur->cd);
				}
				WaitForVblank();
				for (i = 0; i < BEES; i++)
				{
					eraseImage3(oldbee[i].row, oldbee[i].col, 25, 25, WHITE);
				}
				for (i = 0; i < BEES - 3; i++)
				{
					cur = bee + i;
					drawImage3(cur->row, cur->col, 25, 25, cur->image);
					oldbee[i] = bee[i];
				}
			}

			if (t >= TIME - 15 && t <= TIME - 5)
			{
				for (i = 0; i < BEES - 1; i++)
				{
					cur = bee + i;
					cur->row = cur->row + cur->rd;
					cur->col = cur->col + cur->cd;
					boundsCheck(&cur->row, &cur->col, &cur->rd, &cur->cd);
				}
				WaitForVblank();
				for (i = 0; i < BEES; i++)
				{
					eraseImage3(oldbee[i].row, oldbee[i].col, 25, 25, WHITE);
				}
				for (i = 0; i < BEES - 1; i++)
				{
					cur = bee + i;
					drawImage3(cur->row, cur->col, 25, 25, cur->image);
					oldbee[i] = bee[i];
				}
			}

			if (t > 10 && t < TIME - 15)
			{
				for (i = 0; i < BEES; i++)
				{
					cur = bee + i;
					cur->row = cur->row + cur->rd;
					cur->col = cur->col + cur->cd;
					boundsCheck(&cur->row, &cur->col, &cur->rd, &cur->cd);
				}
				WaitForVblank();
				for (i = 0; i < BEES; i++)
				{
					eraseImage3(oldbee[i].row, oldbee[i].col, 25, 25, WHITE);
				}
				for (i = 0; i < BEES; i++)
				{
					cur = bee + i;
					drawImage3(cur->row, cur->col, 25, 25, cur->image);
					oldbee[i] = bee[i];
				}
			}
		}	

		// Move the archery to the right when right button is pressed
		if (KEY_DOWN_NOW(BUTTON_RIGHT))
		{
			moveRight(&acol);
			eraseArchery(ARCHERY_ROW, old_acol);
			drawArchery(ARCHERY_ROW, acol);
			old_acol = acol;
		}

		// Move the archery to the left when the left button is pressed
		if (KEY_DOWN_NOW(BUTTON_LEFT))
		{
			moveLeft(&acol);
			eraseArchery(ARCHERY_ROW, old_acol);
			drawArchery(ARCHERY_ROW, acol);
			old_acol = acol;
		}

		// Shoot the arrow point and check for the score and life when
		// the button A or B is pressed
		if (KEY_DOWN_NOW(BUTTON_A) | KEY_DOWN_NOW(BUTTON_B))
		{
			int r = ARCHERY_ROW; // the row of the top left corner of the archery
			int oldr;	// Initialize the old row of the arrow shooting point
			int c = acol; // column of the top left corner of the archery
			int p = 0;	// Intialize the earing point

			// Check if the arrow shooting hit any target until it reach the 
			// the top border
			while (r > 15)
			{
				oldr = r; // Update the old row
				drawShootingPoint(r, c); // draw the shooting point
				if (t > TIME - 5 || t <= 10)
				{
					p = hitTarget(r, c + 25, bee, oldbee, 3);
				}
				if (t >= TIME - 15 && t <= TIME - 5)
				{
					p = hitTarget(r, c + 25, bee, oldbee, 1);
				}
				if (t > 10 && t < TIME - 15)
				{
					p = hitTarget(r, c + 25, bee, oldbee, 0);
				}
				// Wait to shoot the target
				WaitForVblank(); 
				

				/*If hit the target, p is not 0
				Update the score and lives
				p == -1: player lose 1 point
				p ==2: Player win the game
				Anything else will be added to the score
				*/
				if (p != 0) {
					r = 15; // If hit any target, stop drawing the shooting point
					if (p == -1)
					{
						l = l - 1;
						// Update lives
						sprintf(buffer, "%d", l);
						eraseImage3(5, 150, 18, 15, WHITE);
						drawString(5, 150, buffer, RED);
					}
					else if (p == 2)
					{
						return WIN;
					}
					else
					{
						score = score + p; // Update the score
						// Update score
						sprintf(buffer, "%d", score);
						eraseImage3(5, 95, 18, 15, WHITE);
						drawString(5, 95, buffer, RED);
					}
				}
				// Erase the old shooting point
				setPixel(oldr , c + 25, WHITE);
				r--;
			}
		}
	}
	return 0;
}
