//random tester for smithy
#include "dominion.h"
#include "dominion_helpers.h"
#include "rngs.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>

int main() 
{
	int passedTests, deckCount, remainderCard, handCount, state, numPlayers, curPlayer, numTests, seed, status = 0;
	int k[10] = { adventurer, gardens, embargo, village, minion, mine, cutpurse, sea_hag, tribute, smithy };
	struct gameState G, prevG;

	SelectStream(2);
	PutSeed(3);

	numTests = 1000;
	for (int x = 0; x < numTests; x++) 
	{
		status = 0;
		numPlayers = floor(Random() * (MAX_PLAYERS - 1)) + 2; //random number of players 2 to MAX_PLAYERS
		seed = floor(Random() * 2000);

		initializeGame(numPlayers, k, seed, &G);

		curPlayer = floor(Random() * numPlayers);	//select current player at random
		G.whoseTurn = curPlayer;	

		//filling  up player's hands
		for (int i = 0; i < numPlayers; i++) {
			handCount = floor(Random() * MAX_HAND); //random hand size 0 to MAX_HAND
			G.handCount[i] = handCount;	//set hand count value		
			for (int j = 0; j < handCount; j++) { //for each card in hand, select random card
				G.hand[i][j] = floor(Random() * (treasure_map + 1));
			}
		}

		//filling up  player's decks
		for (int i = 0; i < G.numPlayers; i++) {
			remainderCard = MAX_DECK - G.handCount[i]; //maximum possible cards left for deck
			deckCount = floor(Random() * remainderCard);	
			G.deckCount[i] = deckCount; //set deck size for player i
			for (int j = 0; j < deckCount; j++) //for each card in deck, select random card
			{
				G.deck[i][j] = floor(Random() * (treasure_map + 1));
			}
		}

		//add smithy card to player
		gainCard(smithy, &G, 2, curPlayer);

		//preserve game state
		memcpy(&prevG, &G, sizeof(struct gameState));

		//play smithy card
		state = cardEffect(smithy, 0, 0, 0, &G, 0, NULL);
		//state = playCard((G.handCount[curPlayer] - 1), 0, 0, 0, &G);
		
		printf("Smithy Card: Test %d\n", x + 1);

		//Current player should have 2 more cards in their hand, 3 new cards and 1 discard
		if (prevG.handCount[curPlayer] + 2 != G.handCount[curPlayer]) {
			printf("TEST FAILED: Hand count error. Expected %d Actual %d\n", prevG.handCount[curPlayer] + 2, G.handCount[curPlayer]);
			status = 1;
		}

		//Player should have 3 fewer cards in their deck
		if (prevG.deckCount[curPlayer] - 3 != G.deckCount[curPlayer]) {
			printf("TEST FAILED: Deck count error. Expected %d Actual %d\n", prevG.deckCount[curPlayer] - 3, G.deckCount[curPlayer]);
			status = 1;
		}

		//Player's discard pile should remain unchanged
		if (prevG.discardCount[curPlayer] != G.discardCount[curPlayer]) {
			printf("TEST FAILED: Smithy altered discard pile\n");
			status = 1;
		}

		
		
		if (status == 0)
		{
			++passedTests;

			printf("TEST SUCCEEDED\n");
		}
		
	}

	printf("%d TEST ARE PASSED\n", passedTests);

	return 0;
}