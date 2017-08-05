#include "assert.h"
#include "dominion.h"
#include <stdio.h>
#include "rngs.h"
#include <stdlib.h>

// unit Test for smithy card

int main(int argc, char** argv) {

	struct gameState G;
	int choice1 = 0, choice2 = 0, choice3 = 0;
	int temp;

	int k[10] = { adventurer, gardens, embargo, village, minion, mine, cutpurse,
		sea_hag, tribute, smithy };


	initializeGame(2, k, 2, &G);

	temp = numHandCards(&G);
	cardEffect(smithy, choice1, choice2, choice3, &G, NULL);
	//make sure 3 cards were drawn

	//assert(numHandCards(&G) == temp + 2);
	if (numHandCards(&G) == temp + 2)
		printf("Smithy : TEST SUCCESSFULLY COMPLETED\n");
	else 
		printf("Smithy : TEST FAILED\n");

	return 0;

}