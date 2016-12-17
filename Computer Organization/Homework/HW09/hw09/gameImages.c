//Alan Chiang

#include "myLib.h"
#include "gameImages.h"


// Draw the tile screen of the game
void drawTitleScreen()
{
	drawImage3(0, 0, 240, 160, title);
	drawString(22, 60, "PRESS ENTER TO START", CYAN);
}

// Draw the game over screen
void drawGameOverScreen()
{
	drawImage3(0, 0, 240, 160, gameover);
	drawString(140, 60, "PRESS ENTER TO RESTART", CYAN);
}

// Draw the winning screen
void drawWinScreen()
{
	drawImage3(0, 0, 240, 160, victory);
	drawString(140, 60, "PRESS ENTER TO RESTART", CYAN);
}

// Draw the game screen
void drawGameScreen()
{
	volatile u16 bgcolor = WHITE;
	DMA[3].src = &bgcolor;
	DMA[3].dst = videoBuffer;
	DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_FIXED;
}

// Draw the bow
void drawArchery(int row, int col)
{
	drawImage3(row, col, 50, 40, arrow);
}


// Erase the old bow
void eraseArchery(int row, int col)
{
	eraseImage3(row, col, 50, 40, WHITE);
}

// Draw the shooting point
void drawShootingPoint(int archery_row, int archery_col)
{
	setPixel(archery_row, archery_col + 25, BLACK);
}
