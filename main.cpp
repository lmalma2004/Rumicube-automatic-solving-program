#include <iostream>
#include <vector>

#define RED         0
#define YELLOW      1
#define BLUE        2
#define BLACK       3
using namespace std;
int T, N;

class Card {
public:
	int count_;
	int number_;
	int color_;
	bool joker_;
public:
	Card() {
		count_ = 0;
		joker_ = false;
	}
	Card(int number, int color, bool joker) {
		number_ = number;
		color_ = color;
		joker_ = joker;
		count_ = 0;
	}
	void addCard() {
		count_++;
	}
	void subCard() {
		count_--;
	}
};

class CardGroup {
public:
	vector<Card> cards;
};

//[0][0] 은 조커
Card cards[4][14];

//색들이필요
bool makeSameNum(int grpSize, vector<Card>& card, vector<CardGroup>& grp) {

	if (card.size() == grpSize) {
		CardGroup newGrp;
		newGrp.cards = card;
		grp.push_back(newGrp);
		card.pop_back();
		return;
	}

	for (int i = 0; i < 4; i++) {
		bool isExist = false;
		for (int j = 0; j < colors.size(); j++) {
			if (i == colors[j]) {
				isExist = true;
				break;
			}
		}
		if (!isExist) {

		}
	}
}
bool makeDiffNum(int numSize, vector<Card>& card, vector<CardGroup>& grp) {

}

//color, number 카드로 시작해서 remainCard로 조합해서 답을 낼 수 있는가
bool isMake(int color, int number, vector<CardGroup>& groups, int remainCard) {
	if (remainCard == 0)
		return true;

	cards[color][number].subCard();
	//조커인 경우
	if (number == 0) {
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 14; j++) {
				int currColor = i;
				int currNumber = j;
				//같은숫자, 다른색 조합시도
				for (int gSize = 3; gSize < 5; gSize++) {
					vector<CardGroup> nextGroups = groups;
					vector<Card> card;
					Card currCard(currNumber, currColor, false);
					card.push_back(currCard);
					if (makeSameNum(gSize, card, nextGroups))
						return true;
				}
				//다른숫자, 같은색 조합시도 (min : 3 , max : 13개)
				for (int nSize = 3; nSize < 14; nSize++) {
					vector<CardGroup> nextGroups = groups;
					vector<Card> card;
					Card currCard(currNumber, currColor, false);
					card.push_back(currCard);
					if (makeDiffNum(nSize, card, nextGroups))
						return true;
				}
			}
		}
	}
	else {
		for (int gSize = 3; gSize < 5; gSize++) {
			vector<CardGroup> nextGroups = groups;
			vector<Card> card;
			Card currCard(number, color, false);
			card.push_back(currCard);
			if (makeSameNum(gSize, card, nextGroups))
				return true;
		}
		//다른숫자, 같은색 조합시도 (min : 3 , max : 13개)
		for (int nSize = 3; nSize < 14; nSize++) {
			vector<CardGroup> nextGroups = groups;
			vector<Card> card;
			Card currCard(number, color, false);
			card.push_back(currCard);
			if (makeDiffNum(nSize, card, nextGroups))
				return true;
		}
	}

	return false;
}

void solve() {
	int remainCard = N;
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 14; j++) {
			if (cards[i][j].count_ != 0) {
				vector<CardGroup> groups;
				if (isMake(i, j, groups, remainCard)) {
					printResult();
					return;
				}
			}
		}
	}
	printFail();
}

int main() {
	scanf("%d", &T);
	for (int tc = 1; tc <= T; tc++) {
		scanf("%d", &N);
		for (int i = 0; i < N; i++) {
			//input card info
			//number, color  /  joker number is 0
			int number;
			int color;
			scanf("%d", &number);
			if (number == 0) {
				cards[0][0].joker_ = true;
				cards[0][0].addCard();
				continue;
			}
			scanf("%d", &color);
			cards[color][number].number_ = number;
			cards[color][number].color_ = color;
			cards[color][number].addCard();
		}
		printf("#%d\n", tc);
		solve();
	}
	return 0;
}
