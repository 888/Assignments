//Alan Chiang

#include "myLib.h"
#include "gameImages.h"
#include "gameLogic.h"
#include <stdio.h>
#include <stdlib.h>

#define MOVE 5


/*
	Move the player 5 columns to the right when the right button is pressed
	Only move the player its column + its width is less than 240
*/
void moveRight(int *col)
{
	if (*col < 185)
	{
		*col = *col + MOVE;
	}
}

/*
	Move the arrow 5 columns to the left when the left button is pressed
	Only move the player its column + the MOVE is greater than 0
*/
void moveLeft(int *col)
{
	if (*col > 4)
	{
		*col = *col - MOVE;
	}
}
/*
void moveUp(int *row) //implement if necessary
{
	if (*row > 20)
	{
		*row = *row + MOVE;
	}
}

void moveDown(int *row) //implement if necessary
{
	if (*row < 159) 
	{
		*row = *row - MOVE;
	}
}
*/

/*
	Check if the projectile hit any object
	If it did, erase that object
	and update it row and col; update the old object;
	return the an int 
	The projectile hit the target if it lies in anywhere in
	the bottom border line of the object
	* @param row row where the projectile is 
	* @param col column where the projectile is at
	* @param *b pointer to the objects in the game
	* @param *ob pointer to the old objects in the game
	* @param n integer is the number old objects we are not going to check
			 n depends on the time time t
			 n = 3 when t > TIME - 5 || t <= 10
			 n = 1 when t >= TIME - 15 && t <= TIME - 5
			 n = 0 when t > 10 && t < TIME - 15
	* @return int 0: did not hit any target
				  1: player gets 1 point
				  -1: player loses immediately
				  2: player wins immediately
				  5: player earns 5 points and loses a life
*/
int hitTarget(int row, int col, MOVOBJ *b, MOVOBJ *ob, int n)
{
	for (int i = 0; i < ENEMIES - n; i++)
	{
		if (row == b[i].row + 25 && col >= b[i].col && col <= b[i].col + 25)
		{
			if (i == 6)
			{
				eraseImage3(b[6].row, b[6].col, 25, 25, WHITE);
				b[6].row = rand() % BOTTOM + TOP;
				b[6].col = rand() % 100 + 80;
				ob[6] = b[6];
				return -1;
			}
			if (i == 7)
			{
				eraseImage3(b[7].row, b[7].col, 25, 25, WHITE);
				b[7].row = rand() % BOTTOM + TOP;
				b[7].col = rand() % 100;
				ob[7] = b[7];
				return 5;
			}
			if (i == 8)
			{
				eraseImage3(b[8].row, b[8].col, 25, 25, WHITE);
				b[8].row = rand() % BOTTOM + TOP;
				b[8].col = rand() % 50;
				ob[8] = b[8];
				return 2;
			}
			eraseImage3(b[i].row, b[i].col, 25, 25, WHITE);
			b[i].row = rand() % BOTTOM + TOP;
			b[i].col = rand() % (i*20) + 50;
			ob[i] = b[i];
			return 1;
		}
	}
	return 0;
}
