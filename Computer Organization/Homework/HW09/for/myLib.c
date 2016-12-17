#include "myLib.h"


void drawImage3 (int r, int c, int width, int height, const u16 * image) 
{
	for (int i = 0; i < height; i++) 
	{
	REG_DMA3SAD = image[OFFSET(r + i, c, 240)];
	REG_DMA3DAD = (u32)(videoBuffer[OFFSET(r + i, c, 240)]);
	REG_DMA3CNT = width | DMA_ON | DMA_SOURCE_FIXED;
	}

}
//(u32)(image[OFFSET(i, c, width)])
