#include "RumiCubeSolve.h"

RumiCubeSolve::RumiCubeSolve() {

}
RumiCubeSolve::~RumiCubeSolve() {

}

bool RumiCubeSolve::isMake(int color, int number, vector<CardGroup>& groups, int remainCard) {
	if (remainCard == 0)
		return true;
	cards[color][number].subCard();
	remainCard--;
	//조커인 경우
	//조커인 경우 모든 카드로 시도하지않고 후보들을 추린다면 시간복잡도가 줄것..
	//후보들을 어떻게 추릴까
	if (number == 0) {
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 14; j++) {
				int currColor = i;
				int currNumber = j;
				//같은숫자, 다른색 조합시도
				for (int gSize = 3; gSize < 5; gSize++) {
					//gSize - jokerCnt 인 이유 : 현재여긴 조커인 경우의 구문, 조커 포함그룹은 gSize보다 1만큼 작은 숫자들을 가지고 있어도 됨
					//예시: 그룹이 joker / 1 blue / 1 red 라면 gSize는 3이지만 1의 개수는 2개로 조합이 가능하다.
					if (numberCnt[currNumber] < gSize - jokerCnt)
						break;
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
					//nSize - 1 인 이유 : gSize의 설명과 같음.
					if (colorCnt[currColor] < nSize - jokerCnt)
						break;
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
			if (numberCnt[number] + jokerCnt < gSize)
				break;
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
			if (colorCnt[color] + jokerCnt < nSize)
				break;
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
bool RumiCubeSolve::makeSameNum(int grpSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard) {
	if (card.size() == grpSize) {
		CardGroup newGrp;
		newGrp.cards_ = card;
		grp.push_back(newGrp);

		if (remainCard == 0)
			return true;
		if (remainCard < 3)
			return false;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 14; j++) {
				//color : i / number : j 인 카드로 조합을 해본다.
				if (cards[i][j].count_ != 0) {
					//solve()단계에서 i,j로 시작해서 만드는 모든 그룹을 시도하기 때문에 
					//실패하면 바로 리턴한다.
					if (isMake(i, j, grp, remainCard))
						return true;
					else {
						grp.pop_back();
						return false;
					}
				}
			}
		}
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
			if (cards[cardColor][cardNumber].count_ > 0) {
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
bool RumiCubeSolve::makeDiffNum(int numSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard) {
	//조합을 마친경우
	if (card.size() == numSize) {
		CardGroup newGrp;
		newGrp.cards_ = card;
		grp.push_back(newGrp);

		if (remainCard == 0)
			return true;
		if (remainCard < 3)
			return false;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 14; j++) {
				//color : i / number : j 인 카드로 조합을 해본다.
				if (cards[i][j].count_ != 0) {
					//solve()단계에서 i,j로 시작해서 만드는 모든 그룹을 시도하기 때문에 
					//실패하면 바로 리턴한다.
					if (isMake(i, j, grp, remainCard))
						return true;
					else {
						grp.pop_back();
						return false;
					}
				}
			}
		}
	}

	//다른숫자, 같은 색의 카드를 찾는다.
	int cardNumber = card.back().number_;
	int cardColor = card.back().color_;
	int nextNumber = cardNumber + 1;

	if (nextNumber == 14)
		return false;

	//조커를 찾는다면 숫자와 색을 nextNumber와 cardColor로 정해준다.
	if (cards[0][0].count_ > 0) {
		cards[0][0].subCard();
		Card addCard(nextNumber, cardColor, true);
		card.push_back(addCard);
		if (makeDiffNum(numSize, card, grp, remainCard - 1))
			return true;
		card.pop_back();
		cards[0][0].addCard();
	}
	if (cards[cardColor][nextNumber].count_ > 0) {
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
void RumiCubeSolve::printResult(const vector<CardGroup>& groups) {
	for (int i = 0; i < groups.size(); i++) {
		for (int j = 0; j < groups[i].cards_.size(); j++) {
			if (groups[i].cards_[j].joker_) {
				cout << "(joker) /";
				continue;
			}
			if (groups[i].cards_[j].color_ == RED)
				cout << "(num: " << groups[i].cards_[j].number_ << ", color: RED) /";
			else if (groups[i].cards_[j].color_ == YELLOW)
				cout << "(num: " << groups[i].cards_[j].number_ << ", color: YELLOW) /";
			else if (groups[i].cards_[j].color_ == BLUE)
				cout << "(num: " << groups[i].cards_[j].number_ << ", color: BLUE) /";
			else if (groups[i].cards_[j].color_ == BLACK)
				cout << "(num: " << groups[i].cards_[j].number_ << ", color: BLACK) /";
		}
		cout << endl;
	}
}
void RumiCubeSolve::printFail() {
	cout << "impossible" << endl;
}
void RumiCubeSolve::init() {
	for (int i = 0; i < 4; i++)
		colorCnt[i] = 0;
	for (int i = 0; i < 14; i++)
		numberCnt[i] = 0;
	jokerCnt = 0;
	allCardCnt = 0;
}
void RumiCubeSolve::solve() {
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 14; j++) {
			//color : i / number : j 인 카드로 조합을 해본다.
			if (cards[i][j].count_ != 0) {
				vector<CardGroup> groups;
				if (isMake(i, j, groups, allCardCnt)) {
					printResult(groups);
					return;
				}
			}
		}
	}
	printFail();
}