//random tester for adventurer
#include "dominion.h"
#include "dominion_helpers.h"
#include "rngs.h"
#include <string.h>
#include <stdio.h>
#include <math.h>
#include <assert.h>

int main()
{
	int passedTests = 0, deckCount, remainderCard, handCount, state, numPlayers, curPlayer, numTests, seed, status = 0;
	struct gameState G, prevG;
	int k[10] = { adventurer, gardens, embargo, village, minion, mine, cutpurse, sea_hag, tribute, smithy };

	SelectStream(2);
	PutSeed(3);

	numTests = 1000;
	for (int x = 0; x < numTests; x++)
	{
		status = 0;
		numPlayers = floor(Random() * (MAX_PLAYERS - 1)) + 2; //random number of players 2 to MAX_PLAYERS
		//printf("%d\n", numPlayers);
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
		gainCard(adventurer, &G, 2, curPlayer);

		//preserve game state
		memcpy(&prevG, &G, sizeof(struct gameState));

		//play adventurer card
		state = cardEffect(adventurer, 0, 0, 0, &G, 0, NULL); //calling adventurer from cardEffect
		//state = Adventurer(0, 0, 0, &G); // calling adventurer from refactored code(Adventurer)
	
		

		printf("Adventurer Card: Test %d..\n", x + 1);
		/*
		After the player plays the Adventurer card
		Player should have gained 2 treasure cards and discarded the Adventurer card	*/

		if (prevG.handCount[curPlayer] + 2 != G.handCount[curPlayer]) {
			printf("TEST FAILED: Hand count error. Expected %d, Actual %d\n", prevG.handCount[curPlayer] + 2, G.handCount[curPlayer]);
			status = 1;
		}


		//First card added should by a copper, silver, or gold
		if (G.hand[curPlayer][G.handCount[curPlayer] - 2] != copper && G.hand[curPlayer][G.handCount[curPlayer] - 2] != silver && G.hand[curPlayer][G.handCount[curPlayer] - 2] != gold) {
			//printf("TEST FAILED: Card type error. Added a non-treasure card\n");
			printf("TEST FAILED : No Treaure cards in your hand.\n");
			printf("Must gain a treasure card\n");
			status = 1;
		}

		//Second card added should be a copper, silver, or gold
		if (G.hand[curPlayer][G.handCount[curPlayer] - 1] != copper && G.hand[curPlayer][G.handCount[curPlayer] - 1] != silver && G.hand[curPlayer][G.handCount[curPlayer] - 1] != gold) {
			printf("TEST FAILED : No Treaure cards in your hand.\n");
			printf("Must gain a treasure card\n");
			status = 1;
		}

		//Deck should have at least 2 fewer cards after adventurer is played
		if (prevG.deckCount[curPlayer] - 2 < G.deckCount[curPlayer]) {
			printf("TEST FAILED: Deck count error. Didn't draw enough cards from the deck\n");
			status = 1;
		}

		
	}
	
	return 0;
}