#include <stdlib.h>
#include <stdio.h>
#include "assert.h"
#include "dominion.h"
#include "rngs.h"
#include <time.h>

//Unit Test for whoseTurn()

int main() {

	struct gameState G;

	int currentTurn = 0;
	int testingTurn = 0;


		G.whoseTurn = currentTurn;
		testingTurn = whoseTurn(&G);

		assert(testingTurn == currentTurn);

	printf("whoseTurn : TEST SUCCESSFULLY COMPLETED\n");

	return 0;
}