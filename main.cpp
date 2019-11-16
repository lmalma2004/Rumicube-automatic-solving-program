#include "RumiCubeSolve.h"

int T, N; //N : 공용카드 + 내 카드의 개수

int main() {
	RumiCubeSolve test;

	scanf("%d", &T);
	for (int tc = 1; tc <= T; tc++) {
		scanf("%d", &N);
		test.init();
		for (int i = 0; i < N; i++) {
			//input card info
			//number, color  /  joker number is 0
			int number;
			int color;
			scanf("%d", &number);
			if (number == 0) {
				test.cards[0][0].joker_ = true;
				test.cards[0][0].addCard();
				test.jokerCnt++;
				continue;
			}
			scanf("%d", &color);
			test.cards[color][number].number_ = number;
			test.cards[color][number].color_ = color;
			test.cards[color][number].addCard();
			test.numberCnt[number]++;
			test.colorCnt[color]++;
		}
		test.allCardCnt = N;
		printf("#%d\n", tc);
		test.solve();
	}
	return 0;
}
