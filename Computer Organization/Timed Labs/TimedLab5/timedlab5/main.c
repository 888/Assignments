// Name: Alan Chiang

#include "myLib.h"
#include "moon.h"
#include "lor.h"

u16* videoBuffer = (unsigned short*) 0x6000000;

/* Image 1 */
#define NUM_ROW_CELL 4
#define NUM_COL_CELL 2
const unsigned short* image = moon;
const CELL cellA = {0,2};
const CELL cellB = {1,1};

/* Image 2 */
/*
#define NUM_ROW_CELL 2
#define NUM_COL_CELL 2
const unsigned short* image = lor;
const CELL cellA = {0,1};
const CELL cellB = {1,1};
*/

int main() {
	// TODO: Set REG_DISPCNT appropriately for drawing in mode 3
	REG_DISPCNT = MODE3 | BG2_ENABLE;
	
	// TODO: Call your functions to draw the swapped image onto the screen
	drawFullScreenImage(image);
	drawCell(image, cellA, cellB);
	drawCell(image, cellB, cellA);
	
	// Wait after drawing
	while (1);
	return 0;
}


/**
* Draws the whole image to videoBuffer.
*/
void drawFullScreenImage(const unsigned short* image) {
	//TODO: Implement this function
	for (int i=0; i<160; i++)
	{
		DMA[3].src = (&image[OFFSET(i, 0)]);
		DMA[3].dst = (&videoBuffer[OFFSET(i, 0)]);
		DMA[3].cnt = (240 | DMA_ON | DMA_SOURCE_INCREMENT | DMA_DESTINATION_INCREMENT);
	}
}


/**
* Draws srcCell from the image to dstCell in videoBuffer.
*/

void drawCell(const unsigned short* image, CELL srcCell, CELL dstCell) {
	// TODO: Implement this function
	int cellWidth = 240 / NUM_ROW_CELL;
	int cellHeight = 160 / NUM_COL_CELL;
	
	int aRow = cellWidth * srcCell.col;
	int aCol = cellHeight * srcCell.row;
	int bRow = cellWidth * dstCell.col;
	int bCol = cellHeight * dstCell.row;

	for (int i=aCol, j = bCol; i<aCol + cellHeight; i++, j++)
	{
		DMA[3].src = (&image[OFFSET(i, aRow)]);
		DMA[3].dst = (&videoBuffer[OFFSET(j, bRow)]);
		DMA[3].cnt = (cellWidth | DMA_ON | DMA_SOURCE_INCREMENT | DMA_DESTINATION_INCREMENT);
	}
} 

