//Alan Chiang

#include "myLib.h"
#include "gameLogic.h"
#include "gameImages.h"
#include <stdio.h>
#include <stdlib.h>


int main()
{
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

void start()
{
	REG_DISPCTL = MODE3 | BG2_ENABLE;
	WaitForVblank();
	drawTitleScreen();
	while(!KEY_DOWN_NOW(BUTTON_START));
	while(KEY_DOWN_NOW(BUTTON_START));
}

void win()
{
	WaitForVblank();
	drawWinScreen();
	while(!KEY_DOWN_NOW(BUTTON_START));
	while(KEY_DOWN_NOW(BUTTON_START));
}

void lose()
{
	WaitForVblank();
	drawGameOverScreen();
	while(!KEY_DOWN_NOW(BUTTON_START));
	while(KEY_DOWN_NOW(BUTTON_START));
}

int game()
{
	int t = TIME;
	int l = LIVES;

	int score = 0;

	int count = 0;
	int i;

	char buffer[50];
	MOVOBJ bee[ENEMIES];
	MOVOBJ oldbee[ENEMIES];
	MOVOBJ *cur;
	const unsigned short *images[9] = {red, blue, orange, pink, green, yellow, heart, bonus, yellowJacket};

	int dels[] = {-8, -6, -4, -2, 2, 4, 6, 8, 10};
	int rows[] = {rand()%55 + 15, rand()%50+ 15, 20, 35, 40, 45, 50, 55, 60};
	int cols[] = {rand()%10, rand()%40, 55, 80, 110, 140, 170, 200};
	for (i = 0; i < ENEMIES; i++)
	{ 
		bee[i].row = rows[rand() % 9];
		bee[i].col = cols[rand() % 9];
		bee[i].rd = dels[rand() % 9];
		bee[i].cd = dels[rand() % 9];
		bee[i].image = images[i];
		oldbee[i] = bee[i];
	}

	int acol = ARCHERY_COL;
	int old_acol = acol;
	//int arow = ARCHERY_ROW;
	//int old_arow = arow;

	WaitForVblank();
	drawGameScreen();
	drawArchery(ARCHERY_ROW, ARCHERY_COL);	
	sprintf(buffer, "Time:%d  Score:%d  Lives:%d", t, score, l);
	drawString(5, 85, buffer, BLUE);

	while (1) {

		count++; //counter for time and lives

		if (t == 0 || l == 0)
		{
			return LOSE;
		}

		if (score >= WIN_SCORE)
		{
			return WIN;
		}

		if (count % 50 == 0)
		{
			t--;
			eraseImage3(5, 115, 18, 15, WHITE);
			sprintf(buffer, "%d", t);
			drawString(5, 115, buffer, BLUE);
		}

		if (KEY_DOWN_NOW(BUTTON_SELECT))
		{
			return START;
		}

		if (t > TIME - 5 || t <= 10)
		{
			WaitForVblank();
			for (i = 0; i < ENEMIES - 3; i++)
			{
				cur = bee + i;
				drawImage3(bee->row, bee->col, 25, 25, bee->image);
			}
		}

		if (t >= TIME - 15 && t <= TIME - 5)
		{
			WaitForVblank();
			for (i = 0; i < ENEMIES - 1; i++)
			{
				cur = bee + i;
				drawImage3(bee->row, bee->col, 25, 25, bee->image);
			}
		}

		if (t > 10 && t < TIME - 15)
		{
			WaitForVblank();
			for (i = 0; i < ENEMIES; i++)
			{
				cur = bee + i;
				drawImage3(bee->row, bee->col, 25, 25, bee->image);
			}
		}
		
		if (count % 10 == 0)
		{
			if (t > TIME - 5 || t <= 10)
			{
				for (i = 0; i < ENEMIES - 3; i++)
				{
					cur = bee + i;
					cur->row = cur->row + cur->rd;
					cur->col = cur->col + cur->cd;
					boundsCheck(&cur->row, &cur->col, &cur->rd, &cur->cd);
				}
				WaitForVblank();
				for (i = 0; i < ENEMIES; i++)
				{
					eraseImage3(oldbee[i].row, oldbee[i].col, 25, 25, WHITE);
				}
				for (i = 0; i < ENEMIES - 3; i++)
				{
					cur = bee + i;
					drawImage3(cur->row, cur->col, 25, 25, cur->image);
					oldbee[i] = bee[i];
				}
			}

			if (t >= TIME - 15 && t <= TIME - 5)
			{
				for (i = 0; i < ENEMIES - 1; i++)
				{
					cur = bee + i;
					cur->row = cur->row + cur->rd;
					cur->col = cur->col + cur->cd;
					boundsCheck(&cur->row, &cur->col, &cur->rd, &cur->cd);
				}
				WaitForVblank();
				for (i = 0; i < ENEMIES; i++)
				{
					eraseImage3(oldbee[i].row, oldbee[i].col, 25, 25, WHITE);
				}
				for (i = 0; i < ENEMIES - 1; i++)
				{
					cur = bee + i;
					drawImage3(cur->row, cur->col, 25, 25, cur->image);
					oldbee[i] = bee[i];
				}
			}

			if (t > 10 && t < TIME - 15)
			{
				for (i = 0; i < ENEMIES; i++)
				{
					cur = bee + i;
					cur->row = cur->row + cur->rd;
					cur->col = cur->col + cur->cd;
					boundsCheck(&cur->row, &cur->col, &cur->rd, &cur->cd);
				}
				WaitForVblank();
				for (i = 0; i < ENEMIES; i++)
				{
					eraseImage3(oldbee[i].row, oldbee[i].col, 25, 25, WHITE);
				}
				for (i = 0; i < ENEMIES; i++)
				{
					cur = bee + i;
					drawImage3(cur->row, cur->col, 25, 25, cur->image);
					oldbee[i] = bee[i];
				}
			}
		}	

		if (KEY_DOWN_NOW(BUTTON_RIGHT))
		{
			moveRight(&acol);
			eraseArchery(ARCHERY_ROW, old_acol);
			drawArchery(ARCHERY_ROW, acol);
			old_acol = acol;
		}

		if (KEY_DOWN_NOW(BUTTON_LEFT))
		{
			moveLeft(&acol);
			eraseArchery(ARCHERY_ROW, old_acol);
			drawArchery(ARCHERY_ROW, acol);
			old_acol = acol;
		}

		/*
		if (KEY_DOWN_NOW(BUTTON_DOWN))
		{
			moveDown(&arow);
			eraseArchery(old_arow, ARCHERY_COL);
			drawArchery(arow, ARCHERY_COL);
			old_arow = arow;
		}

		if (KEY_DOWN_NOW(BUTTON_UP))
		{
			moveUp(&arow);
			eraseArchery(old_arow, ARCHERY_COL);
			drawArchery(arow, ARCHERY_COL);
			old_arow = arow;
		}
		*/

		if (KEY_DOWN_NOW(BUTTON_A) | KEY_DOWN_NOW(BUTTON_B))
		{
			int r = ARCHERY_ROW;
			int oldr;	
			int c = acol; 
			int p = 0;
			

			while (r > 15)
			{
				oldr = r; 
				drawShootingPoint(r, c); 
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
			
				WaitForVblank(); 
				

				if (p != 0) {
					r = 15; 
					if (p == -1)
					{
						l = 0;
						sprintf(buffer, "%d", l);
						eraseImage3(5, 230, 18, 15, WHITE);
						drawString(5, 230, buffer, BLUE);
					}
					else if (p == 2)
					{
						return WIN;
					}
					else if (p == 5)
					{
						l = l - 1;
						sprintf(buffer, "%d", l);
						eraseImage3(5, 230, 18, 15, WHITE);
						drawString(5, 230, buffer, BLUE);
						score = score + p;
						sprintf(buffer, "%d", score);
						eraseImage3(5, 175, 18, 15, WHITE);
						drawString(5, 175, buffer, BLUE);
					}
					else
					{
						score = score + p;
						sprintf(buffer, "%d", score);
						eraseImage3(5, 175, 18, 15, WHITE);
						drawString(5, 175, buffer, BLUE);
					}
				}
				setPixel(oldr , c + 25, WHITE);
				r--;
			}
		}
	}
	return 0;
}
