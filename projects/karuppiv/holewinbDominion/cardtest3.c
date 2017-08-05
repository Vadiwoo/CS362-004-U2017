#include "assert.h"
#include "dominion.h"
#include <stdio.h>
#include "rngs.h"
#include <stdlib.h>

// unit test for village card

int main(int argc, char** argv) {

	struct gameState G;
	int choice1 = 0, choice2 = 0, choice3 = 0;
	int temp;
	int actions;

	int k[10] = { adventurer, gardens, embargo, village, minion, mine, cutpurse,
		sea_hag, steward, smithy };


	initializeGame(2, k, 2, &G);

	actions = G.numActions;

	temp = cardEffect(village, choice1, choice2, choice3, &G, NULL);

	//assert(G.numActions == actions + 2);
	if(G.numActions == actions + 2)
		printf("Village: TEST SUCCESSFULLY COMPLETED\n");
	else
		printf("Village : TEST FAILED\n");
		
	//assert(temp == 0);

//	printf("Village: TEST SUCCESSFULLY COMPLETED\n");
	return 0;

}