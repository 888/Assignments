//Alan Chiang

#define ENEMIES 9
#define TIME 30 
#define LIVES 3 
#define BOTTOM 110
#define TOP 15
#define WIN_SCORE 15 
#define ARCHERY_ROW 119 
#define ARCHERY_COL 95	

enum {START, GAME, WIN, LOSE, SHOOT};

typedef struct {
	int row;
	int col;
	int rd;
	int cd;
	const unsigned short* image;
}MOVOBJ;

void moveRight(int *col);
void moveLeft(int *col);
void moveUp(int *row);
void moveDown(int *row);
int hitTarget(int row, int col, MOVOBJ *b, MOVOBJ *ob, int n);
