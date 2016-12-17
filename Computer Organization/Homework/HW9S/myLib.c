/*
Name: Van Le (van8)
Date: 03/29/2015
*/

#include "myLib.h"

unsigned short *videoBuffer = (unsigned short *)0x6000000;

void setPixel(int row, int col, u16 color)
{
	videoBuffer[OFFSET(row, col, 240)] = color;
}

void drawRect(int row, int col, int height, int width, u16 color)
{
	for(int r=0; r<height; r++)
	{
		for(int c=0; c<width; c++)
		{
			setPixel(row+r, col+c, color);
		}
	}
}

void drawChar(int row, int col, char ch, u16 color)
{
	for(int r=0; r<8; r++)
	{
		for(int c=0; c<6; c++)
		{
			if(fontdata_6x8[OFFSET(r,c,6) + 48*ch])
			{
				setPixel(r + row, c + col, color);
			}
		}
	}
}

void drawString(int row, int col, char *str, u16 color)
{
	while(*str)
	{
		drawChar(row, col, *str++, color);
		col += 6;
	}
}

/* drawimage3
* A function that will draw an arbitrary sized image
* onto the screen (with DMA).
* @param r row to draw the image
* @param c column to draw the image
* @param width width of the image
* @param height height of the image
* @param image Pointer to the first element of the image.
*/
void drawImage3(int row, int col, int width, int height, const u16* image)
{
	for (int r=0; r<height; r++)
	{
		DMA[3].src = image + OFFSET(r, 0, width);
		DMA[3].dst = videoBuffer + OFFSET(r + row, col, 240);
		DMA[3].cnt = width | DMA_ON;
	}
}

/*
	Method to erase an image in the game with a color
*/
void eraseImage3(int row, int col, int width, int height, volatile u16 color)
{
	for (int r=0; r<height; r++)
	{
		DMA[3].src = &color;
		DMA[3].dst = videoBuffer + OFFSET(r + row, col, 240);
		DMA[3].cnt = width | DMA_ON | DMA_SOURCE_FIXED;
	}
}

/*
	Check if the moving object is in the region
	Row: from row 16 to row 84
	Column: from column 0 to 214
*/
void boundsCheck(int *row, int *col, int *rowdelta, int *coldelta)
{
		if(*row < 16)
		{
			*row = 16;
			*rowdelta = -*rowdelta;
		}
		if (*row > 84)
		{
			*row = 84;
			*rowdelta = -*rowdelta;
		}

		if (*col < 0)
		{
			*col = 0;
			*coldelta = -*coldelta;
		}

		if(*col > 214)
		{
			*col = 214;
			*coldelta = -*coldelta;
		}
}

void WaitForVblank()
{
	while(SCANLINECOUNTER > 160);
	while(SCANLINECOUNTER < 160);
}
