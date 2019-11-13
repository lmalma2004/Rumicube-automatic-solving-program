#include <iostream>
#include <vector>

#define RED         0
#define YELLOW      1
#define BLUE        2
#define BLACK       3
using namespace std;
int T, N; //N : 공용카드 + 내 카드의 개수

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

bool isMake(int color, int number, vector<CardGroup>& groups, int remainCard);
bool makeSameNum(int grpSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard);
bool makeDiffNum(int numSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard);


//색들이필요
//vector<CardGroup> 는 vector<vector<Card>>와 같은의미
bool makeSameNum(int grpSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard) {

	if (card.size() == grpSize) {
		CardGroup newGrp;
		newGrp.cards = card;
		grp.push_back(newGrp);

		if (remainCard == 0)
			return true;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 14; j++) {
				//color : i / number : j 인 카드로 조합을 해본다.
				if (cards[i][j].count_ != 0) {
					//vector<CardGroup> groups;
					if (isMake(i, j, grp, remainCard)) {
						//printResult(grp);
						return true;
					}
				}
			}
		}

		grp.pop_back();
		return false;
	}

	for (int i = 0; i < 4; i++) {
		bool isExist = false;
		for (int j = 0; j < card.size(); j++) {
			if (i == card[j].color_) {
				isExist = true;
				break;
			}
		}
		//현재 카드조합에 없는 색을 찾음
		if (!isExist) {
			//같은숫자, 없는 색의 카드를 찾는다.
			int cardNumber = card[0].number_;
			int cardColor = i;
			//조커를 찾는다면 숫자와 색을 정해줌
			if (cards[0][0].count_ > 0) {
				cards[0][0].subCard();
				Card addCard(cardNumber, cardColor, true);
				card.push_back(addCard);
				if (makeSameNum(grpSize, card, grp, remainCard - 1))
					return true;
				card.pop_back();
				cards[0][0].addCard();
			}
			else if (cards[cardColor][cardNumber].count_ > 0) {
				cards[cardColor][cardNumber].subCard();
				Card addCard(cardNumber, cardColor, false);
				card.push_back(addCard);
				if (makeSameNum(grpSize, card, grp, remainCard - 1))
					return true;
				card.pop_back();
				cards[cardColor][cardNumber].addCard();
			}
		}
	}

	return false;
}
//연속된 숫자, 같은색
bool makeDiffNum(int numSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard) {
	//조합을 마친경우
	if (card.size() == numSize) {
		CardGroup newGrp;
		newGrp.cards = card;
		grp.push_back(newGrp);

		if (remainCard == 0)
			return true;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 14; j++) {
				//color : i / number : j 인 카드로 조합을 해본다.
				if (cards[i][j].count_ != 0) {
					//vector<CardGroup> groups;
					if (isMake(i, j, grp, remainCard)) {
						//printResult(grp);
						return true;
					}
				}
			}
		}

		grp.pop_back();
		return false;
	}
	//같은숫자, 없는 색의 카드를 찾는다.
	int cardNumber = card.back().number_;
	int cardColor = card.back().color_;
	int nextNumber = cardNumber + 1;

	if (cardNumber == 13)
		return false;

	//조커를 찾는다면 숫자와 색을 정해줌
	if (cards[0][0].count_ > 0) {
		cards[0][0].subCard();
		Card addCard(nextNumber, cardColor, true);
		card.push_back(addCard);
		if (makeDiffNum(numSize, card, grp, remainCard - 1))
			return true;
		card.pop_back();
		cards[0][0].addCard();
	}
	else if (cards[cardColor][nextNumber].count_ > 0) {
		cards[cardColor][nextNumber].subCard();
		Card addCard(nextNumber, cardColor, false);
		card.push_back(addCard);
		if (makeDiffNum(numSize, card, grp, remainCard - 1))
			return true;
		card.pop_back();
		cards[cardColor][nextNumber].addCard();
	}
	return false;

}

//color, number 카드로 시작해서 remainCard로 조합해서 답을 낼 수 있는가
bool isMake(int color, int number, vector<CardGroup>& groups, int remainCard) {
	if (remainCard == 0)
		return true;

	cards[color][number].subCard();
	remainCard--;

	//조커인 경우
	if (number == 0) {
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 14; j++) {
				int currColor = i;
				int currNumber = j;
				//같은숫자, 다른색 조합시도
				for (int gSize = 3; gSize < 5; gSize++) {
					vector<CardGroup> origGroups = groups;
					vector<Card> card;
					Card currCard(currNumber, currColor, true);
					card.push_back(currCard);
					if (makeSameNum(gSize, card, groups, remainCard))
						return true;
					groups = origGroups;
				}
				//다른숫자, 같은색 조합시도 (min : 3 , max : 13개)
				for (int nSize = 3; nSize < 14; nSize++) {
					vector<CardGroup> origGroups = groups;
					vector<Card> card;
					Card currCard(currNumber, currColor, true);
					card.push_back(currCard);
					if (makeDiffNum(nSize, card, groups, remainCard))
						return true;
					groups = origGroups;
				}
			}
		}
	}
	else {
		for (int gSize = 3; gSize < 5; gSize++) {
			vector<CardGroup> origGroups = groups;
			vector<Card> card;
			Card currCard(number, color, false);
			card.push_back(currCard);
			if (makeSameNum(gSize, card, groups, remainCard))
				return true;
			groups = origGroups;
		}
		//다른숫자, 같은색 조합시도 (min : 3 , max : 13개)
		for (int nSize = 3; nSize < 14; nSize++) {
			vector<CardGroup> origGroups = groups;
			vector<Card> card;
			Card currCard(number, color, false);
			card.push_back(currCard);
			if (makeDiffNum(nSize, card, groups, remainCard))
				return true;
			groups = origGroups;
		}
	}

	cards[color][number].addCard();
	remainCard++;
	return false;
}

void printResult(const vector<CardGroup>& groups) {
	for (int i = 0; i < groups.size(); i++) {
		for (int j = 0; j < groups[i].cards.size(); j++) {
			if (groups[i].cards[j].joker_) {
				cout << "joker /";
				continue;
			}
			if (groups[i].cards[j].color_ == RED)
				cout << "(num: " << groups[i].cards[j].number_ << ", color: RED) ";
			else if (groups[i].cards[j].color_ == YELLOW)
				cout << "(num: " << groups[i].cards[j].number_ << ", color: YELLOW) ";
			else if (groups[i].cards[j].color_ == BLUE)
				cout << "(num: " << groups[i].cards[j].number_ << ", color: BLUE) ";
			else if (groups[i].cards[j].color_ == BLACK)
				cout << "(num: " << groups[i].cards[j].number_ << ", color: BLACK) ";
		}
		cout << endl;
	}
}
void printFail() {
	cout << "impossible" << endl;
}

void solve() {
	int remainCard = N;
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 14; j++) {
			//color : i / number : j 인 카드로 조합을 해본다.
			if (cards[i][j].count_ != 0) {
				vector<CardGroup> groups;
				if (isMake(i, j, groups, remainCard)) {
					printResult(groups);
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
