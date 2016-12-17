#include "structs.h"
#include "myLib.h"
#include "enemy_sprite.h"

#define ENEMY_WIDTH 10
#define ENEMY_HEIGHT 5
#define HARD_ENEMY_WIDTH 10
#define HARD_ENEMY_HEIGHT 10
#define PROJECTILE_WIDTH 2
#define PROJECTILE_HEIGHT 2
#define SHIP_WIDTH 20
#define SHIP_HEIGHT 25
#define MAX_ENEMIES 50
#define STARTING_ENEMIES 20

int main() 
{

int rowPos = 0;
int colPos = 0;
enemyShip enemies[100]; 

for (int i = 0; i < STARTING_ENEMIES; i++) {
	enemies[i].width = ENEMY_WIDTH;
	enemies[i].height = ENEMY_HEIGHT;
	enemies[i].row = rowPos;
	enemies[i].col = colPos;
	rowPos += 15;
	colPos += 15;
}

drawImage3(10, 10, ENEMY_WIDTH, ENEMY_HEIGHT, enemy_sprite);

}
