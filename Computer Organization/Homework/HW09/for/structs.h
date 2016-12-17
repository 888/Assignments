typedef struct SHIP_T {
	int width;
	int height;
	int row;
	int col;
	int deltaX;
	int deltaY;
	
}ship;

typedef struct PROJECTILE_T {
	int width;
	int height;
	int row;
	int col;
	int deltaX;
	int deltaY;
}projectile;

typedef struct ENEMY_T {
	int width;
	int height;
	int row;
	int col; 
}enemyShip;

typedef struct HARDENEMY_T {
	int width;
	int height;
	int row;
	int col; 
	int deltaX;
	int deltaY;
}hardEnemyShip;
