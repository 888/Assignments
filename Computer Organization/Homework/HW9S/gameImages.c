/*
Name: Van Le (van8)
Date: 03/29/2015
*/

#include "myLib.h"
#include "gameImages.h"


// Draw the tile screen of the game
void drawTitleScreen()
{
	drawImage3(0, 0, 240, 160, title);
	drawString(65, 20, "ARE YOU", BLACK);
	drawString(75, 15, "READY ???", BLACK);
	drawString(65, 170, "SHOOTING", BLACK);
	drawString(75, 170, "START >>", RED);
}

// Draw the losing screen 
void drawGameOverScreen()
{
	drawImage3(0, 0, 240, 160, gameover);
	drawString(30, 93, "GAME OVER", BLACK);
	drawString(50, 90, "PLAY AGAIN", BLACK);
	drawString(70, 99, "START >>", RED);
}

// Draw the winninf screen
void drawWinScreen()
{
	drawImage3(0, 0, 240, 160, gameover);
	drawString(30, 73, "CONGRATULATION !!", RED);
	drawString(40, 103, "WINNER", RED);
	drawString(60, 90, "PLAY AGAIN", BLACK);
	drawString(70, 100, "START >>", BLACK);
}

// Draw the game screen
void drawGameScreen()
{
	volatile u16 bgcolor = WHITE;
	DMA[3].src = &bgcolor;
	DMA[3].dst = videoBuffer;
	DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_FIXED;
}

// Draw the archery of the game
void drawArchery(int row, int col)
{
	drawImage3(row, col, 50, 40, arrow);
}


// Erase the old archery if player moved it
void eraseArchery(int row, int col)
{
	eraseImage3(row, col, 50, 40, WHITE);
}

// Draw the shooting point
void drawShootingPoint(int archery_row, int archery_col)
{
	setPixel(archery_row, archery_col + 25, BLACK);
}
