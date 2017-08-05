//random tester for village
#include "dominion.h"
#include "dominion_helpers.h"
#include "rngs.h"
#include <string.h>
#include <stdio.h>
#include <math.h>
#include <assert.h>

int main()
{
	int passedTests, deckCount, remainderCard, handCount, state, numPlayers, curPlayer, numTests, seed, status = 0;
	struct gameState G, prevG;
	int k[10] = { adventurer, gardens, embargo, village, minion, mine, cutpurse, sea_hag, tribute, smithy };

	SelectStream(2);
	PutSeed(3);

	numTests = 1000;
	for (int x = 0; x < numTests; x++)
	{
		status = 0;
		numPlayers = floor(Random() * (MAX_PLAYERS - 1)) + 2; //random number of players 2 to MAX_PLAYERS
														
		seed = floor(Random() * 2000);

		initializeGame(numPlayers, k, seed, &G);

		curPlayer = floor(Random() * numPlayers);	//selecting current player at random
		G.whoseTurn = curPlayer;

		//filling up players hands
		for (int i = 0; i < numPlayers; i++)
		{
			handCount = floor(Random() * MAX_HAND); //random hand size 0 to MAX_HAND
			G.handCount[i] = handCount;
			for (int j = 0; j < handCount; j++)
			{ //for each card in hand, select random card
				G.hand[i][j] = floor(Random() * (treasure_map + 1));
			}
		}

		//filling up player's decks
		for (int i = 0; i < G.numPlayers; i++) {
			remainderCard = MAX_DECK - G.handCount[i]; //maximum possible cards left for deck
			deckCount = floor(Random() * remainderCard);	//random number of cards in deck 0 to remainderCard
			G.deckCount[i] = deckCount; 
			for (int j = 0; j < deckCount; j++) //for each card in deck, select random card
			{
				G.deck[i][j] = floor(Random() * (treasure_map + 1));
			}
		}

		//add adventurer card to player
		gainCard(village, &G, 2, curPlayer);

		//preserve game state
		memcpy(&prevG, &G, sizeof(struct gameState));

		//play village card
		//state = cardEffect(village, 0, 0, 0, &G, 0, NULL);
		state = villageCall(&G, handCount);

		if (state != 0) {
			printf("Card Play Failed\n");
		}

		printf("Village Card: Test %d..\n", x + 1);
		/*
		After the player plays the Village card Player should have gained 1 cards from deck */

		if (prevG.deckCount[curPlayer] - 1 != G.deckCount[curPlayer]) {
			printf("TEST FAILED: Deck count error. Expected %d, Actual %d\n", prevG.deckCount[curPlayer] - 1, G.deckCount[curPlayer]);
			status = 1;
		}

		//after playing village card, player should earn 2 actions
		if (prevG.numActions + 2 != G.numActions)
		{
			printf("TEST FAILED: Number of actions did not increase properly.Expected %d, Actual %d\n", (prevG.numActions ,G.numActions));
			status = 1;
		}
		

		if (status == 0)
		{
			printf("TEST SUCCEEDED\n");
			++passedTests;
		}
	}

	printf("%d TEST ARE PASSED\n", passedTests);

	return 0;
}