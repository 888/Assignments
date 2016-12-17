/*
Name: Xiying Huang
Date: 07/26/2016
*/

#define BEES 9
#define TIME 30 //Initialize the timer
#define LIVES 3 //Initialize the lives
#define BOTTOM 110
#define TOP 15
#define WIN_SCORE 15 //Initialize the win score
#define ARCHERY_ROW 119 //top left corner row of the archery
#define ARCHERY_COL 95	//top left corner column of the archery

// State of the game
enum {START, GAME, WIN, LOSE, SHOOT};

//Structure for moving objects in the game
typedef struct {
	int row;
	int col;
	int rd;
	int cd;
	const unsigned short* image;
}MOVOBJ;

void moveRight(int *col);
void moveLeft(int *col);
int hitTarget(int row, int col, MOVOBJ *b, MOVOBJ *ob, int n);
