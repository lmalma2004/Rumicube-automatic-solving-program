#include <iostream>
#include <vector>
#include <map>

using namespace std;

class RumiCubeSolve {
public:
	static const int RED	= 0;
	static const int YELLOW = 1;
	static const int BLUE	= 2;
	static const int BLACK	= 3;
public:
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
		vector<Card> cards_;
	};
public:
	Card cards[4][14];
	int colorCnt[4];
	int numberCnt[4];
	int jokerCnt;
	int allCardCnt;
public:
	RumiCubeSolve();
	~RumiCubeSolve();
public:
	bool isMake(int color, int number, vector<CardGroup>& groups, int remainCard);
	bool makeSameNum(int grpSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard);
	bool makeDiffNum(int numSize, vector<Card>& card, vector<CardGroup>& grp, int remainCard);
	void init();
	void solve();
	void printResult(const vector<CardGroup>& groups);
	void printFail();
};