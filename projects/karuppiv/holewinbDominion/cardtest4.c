#include "assert.h"
#include "dominion.h"
#include <stdio.h>
#include "rngs.h"
#include <stdlib.h>

//unit test for garden card
int main(int argc, char** argv) {

	struct gameState G;
	int result, card, choice1, choice2, choice3, handPos, bonus, value = 2;
	int k[10] = { adventurer, gardens, embargo, village, minion, mine, cutpurse,
		sea_hag, tribute, smithy };
	
	result = initializeGame(2, k, value, &G);
	assert(result == 0);

	card = 4;
	choice1 = 0;
	choice2 = 0;
	choice3 = 0;
	handPos = 4;
	bonus = 0;

	result = cardEffect(card, choice1, choice2, choice3, &G, handPos, bonus);
	assert(result == -1);
	printf("garden : TEST SUCCESSFULLY COMPLETED\n");


}