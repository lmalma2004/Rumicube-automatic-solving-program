package com.rummikubsolve.engine;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class RummiKubSolve {
    private static final int RED    = 0;
    private static final int YELLOW = 1;
    private static final int BLUE   = 2;
    private static final int BLACK  = 3;

    public static class Card implements Serializable, Parcelable{
        int count_;
        int number_;
        int color_;
        boolean joker_;

        Card(){
            count_ = 0;
            joker_ = false;
        }
        Card(int number, int color, boolean joker){
            number_ = number;
            color_ = color;
            joker_ = joker;
            count_ = 0;
        }

        protected Card(Parcel in) {
            count_ = in.readInt();
            number_ = in.readInt();
            color_ = in.readInt();
            joker_ = in.readByte() != 0;
        }

        public static final Creator<Card> CREATOR = new Creator<Card>() {
            @Override
            public Card createFromParcel(Parcel in) {
                return new Card(in);
            }

            @Override
            public Card[] newArray(int size) {
                return new Card[size];
            }
        };

        void addCard(){
            count_++;
        }
        void subCard(){
            count_--;
        }
        Card clone_(){
            Card ret = new Card();
            ret.count_ = count_;
            ret.number_ = number_;
            ret.color_ = color_;
            ret.joker_ = joker_;
            return ret;
        }
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(count_);
            parcel.writeInt(number_);
            parcel.writeInt(color_);
            parcel.writeByte((byte) (joker_ ? 1 : 0));
        }
    }

    public static class CardGroup implements Parcelable{
        ArrayList<Card> cards_;

        public CardGroup(){

        }

        protected CardGroup(Parcel in) {
            cards_ = in.createTypedArrayList(Card.CREATOR);
        }

        public static final Creator<CardGroup> CREATOR = new Creator<CardGroup>() {
            @Override
            public CardGroup createFromParcel(Parcel in) {
                return new CardGroup(in);
            }

            @Override
            public CardGroup[] newArray(int size) {
                return new CardGroup[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(cards_);
        }
    }

    public Card cards[][] = new Card[4][14];
    public int colorCnt[] = new int[4];
    public int numberCnt[] = new int[14];
    public int jokerCnt;
    public int allCardCnt;

    public RummiKubSolve() {
        init();
    }
    boolean isMake(int color, int number, ArrayList<CardGroup> groups, int remainCard){
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
                        ArrayList<Card> card = new ArrayList<Card>();
                        Card currCard = new Card(currNumber, currColor, true);
                        card.add(currCard);
                        if (makeSameNum(gSize, card, groups, remainCard))
                            return true;
                    }
                    //다른숫자, 같은색 조합시도 (min : 3 , max : 13개)
                    for (int nSize = 3; nSize < 6; nSize++) {
                        //nSize - 1 인 이유 : gSize의 설명과 같음.
                        if (colorCnt[currColor] < nSize - jokerCnt)
                            break;
                        ArrayList<Card> card = new ArrayList<Card>();
                        Card currCard = new Card(currNumber, currColor, true);
                        card.add(currCard);
                        if (makeDiffNum(nSize, card, groups, remainCard))
                            return true;
                    }
                }
            }
        }
        else {
            for (int gSize = 3; gSize < 5; gSize++) {
                if (numberCnt[number] + jokerCnt < gSize)
                    break;
                ArrayList<Card> card = new ArrayList<Card>();
                Card currCard = new Card(number, color, false);
                card.add(currCard);
                if (makeSameNum(gSize, card, groups, remainCard))
                    return true;
            }
            //다른숫자, 같은색 조합시도 (min : 3 , max : 13개)
            for (int nSize = 3; nSize < 6; nSize++) {
                if (colorCnt[color] + jokerCnt < nSize)
                    break;
                ArrayList<Card> card = new ArrayList<Card>();
                Card currCard = new Card(number, color, false);
                card.add(currCard);
                if (makeDiffNum(nSize, card, groups, remainCard))
                    return true;
            }
        }

        cards[color][number].addCard();
        remainCard++;
        return false;
    }
    boolean makeSameNum(int grpSize, ArrayList<Card> card, ArrayList<CardGroup> grp, int remainCard){
        if (card.size() == grpSize) {
            if(remainCard != 0 && remainCard < 3)
                return false;
            CardGroup newGrp = new CardGroup();
            newGrp.cards_ = card;
            grp.add(newGrp);

            if (remainCard == 0)
                return true;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 14; j++) {
                    //color : i / number : j 인 카드로 조합을 해본다.
                    if (cards[i][j].count_ != 0) {
                        //solve()단계에서 i,j로 시작해서 만드는 모든 그룹을 시도하기 때문에
                        //실패하면 바로 리턴한다.
                        if (isMake(i, j, grp, remainCard))
                            return true;
                        else {
                            grp.remove(grp.size() - 1);
                            return false;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            boolean isExist = false;
            for (int j = 0; j < card.size(); j++) {
                if (i == card.get(j).color_) {
                    isExist = true;
                    break;
                }
            }
            //현재 카드조합에 없는 색을 찾음
            if (!isExist) {
                //같은숫자, 없는 색의 카드를 찾는다.
                int cardNumber = card.get(0).number_;
                int cardColor = i;
                //조커를 찾는다면 숫자와 색을 정해줌
                if (cards[0][0].count_ > 0) {
                    cards[0][0].subCard();
                    Card addCard = new Card(cardNumber, cardColor, true);
                    card.add(addCard);
                    if (makeSameNum(grpSize, card, grp, remainCard - 1))
                        return true;
                    card.remove(card.size() - 1);
                    cards[0][0].addCard();
                }
                if (cards[cardColor][cardNumber].count_ > 0) {
                    cards[cardColor][cardNumber].subCard();
                    Card addCard = new Card(cardNumber, cardColor, false);
                    card.add(addCard);
                    if (makeSameNum(grpSize, card, grp, remainCard - 1))
                        return true;
                    card.remove(card.size() - 1);
                    cards[cardColor][cardNumber].addCard();
                }
            }
        }
        return false;

    }
    boolean makeDiffNum(int numSize, ArrayList<Card> card, ArrayList<CardGroup> grp, int remainCard){
        //조합을 마친경우
        if (card.size() == numSize) {
            if(remainCard != 0 && remainCard < 3)
                return false;
            CardGroup newGrp = new CardGroup();
            newGrp.cards_ = card;
            grp.add(newGrp);

            if (remainCard == 0)
                return true;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 14; j++) {
                    //color : i / number : j 인 카드로 조합을 해본다.
                    if (cards[i][j].count_ != 0) {
                        //solve()단계에서 i,j로 시작해서 만드는 모든 그룹을 시도하기 때문에
                        //실패하면 바로 리턴한다.
                        if (isMake(i, j, grp, remainCard))
                            return true;
                        else {
                            grp.remove(grp.size() - 1);
                            return false;
                        }
                    }
                }
            }
        }

        //다른숫자, 같은 색의 카드를 찾는다.
        int cardNumber = card.get(card.size() - 1).number_;
        int cardColor = card.get(card.size() - 1).color_;
        int nextNumber = cardNumber + 1;

        if (nextNumber == 14)
            return false;

        //조커를 찾는다면 숫자와 색을 nextNumber와 cardColor로 정해준다.
        if (cards[0][0].count_ > 0) {
            cards[0][0].subCard();
            Card addCard = new Card(nextNumber, cardColor, true);
            card.add(addCard);
            if (makeDiffNum(numSize, card, grp, remainCard - 1))
                return true;
            card.remove(card.size() - 1);
            cards[0][0].addCard();
        }
        if (cards[cardColor][nextNumber].count_ > 0) {
            cards[cardColor][nextNumber].subCard();
            Card addCard = new Card(nextNumber, cardColor, false);
            card.add(addCard);
            if (makeDiffNum(numSize, card, grp, remainCard - 1))
                return true;
            card.remove(card.size() - 1);
            cards[cardColor][nextNumber].addCard();
        }
        return false;

    }
    void init(){
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card[14];
            for (int j = 0; j < 14; j++) {
                cards[i][j] = new Card();
            }
        }
        for (int i = 0; i < 4; i++)
            colorCnt[i] = 0;
        for (int i = 0; i < 14; i++)
            numberCnt[i] = 0;
        jokerCnt = 0;
        allCardCnt = 0;
    }
    ArrayList<CardGroup> solve(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                //color : i / number : j 인 카드로 조합을 해본다.
                if (cards[i][j].count_ != 0) {
                    ArrayList<CardGroup> groups = new ArrayList<CardGroup>();
                    if (isMake(i, j, groups, allCardCnt)) {
                        //printResult(groups);
                        return groups;
                    }
                }
            }
        }
        //printFail();
        return null;
    }
    void addCard(int color, int number){
        cards[color][number].number_ = number;
        cards[color][number].color_ = color;
        cards[color][number].addCard();
        numberCnt[number]++;
        colorCnt[color]++;
        allCardCnt++;
    }
    void addJoker(){
        cards[0][0].joker_ = true;
        cards[0][0].addCard();
        jokerCnt++;
        allCardCnt++;
    }
    void subCard(int color, int number){
        cards[color][number].subCard();
        numberCnt[number]--;
        colorCnt[color]--;
        allCardCnt--;
    }
    void subJoker(){
        cards[0][0].subCard();
        if(cards[0][0].count_ == 0)
            cards[0][0].joker_ = false;
        jokerCnt--;
        allCardCnt--;
    }

    RummiKubSolve clone_(){
        RummiKubSolve ret = new RummiKubSolve();
        for(int i=0; i<4; i++)
            for(int j=0; j<14; j++)
                ret.cards[i][j] = cards[i][j].clone_();

        for(int i=0; i<4; i++)
            ret.colorCnt[i] = colorCnt[i];
        for(int i=0; i<14; i++)
            ret.numberCnt[i] = numberCnt[i];
        ret.jokerCnt = jokerCnt;
        ret.allCardCnt = allCardCnt;
        return ret;
    }
    void printResult(final ArrayList<CardGroup> groups){
        System.out.println(groups.size());
        for (int i = 0; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).cards_.size(); j++) {
                if (groups.get(i).cards_.get(j).joker_) {
                    System.out.print("(joker) /");
                    continue;
                }
                if (groups.get(i).cards_.get(j).color_ == RED)
                    System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: RED)");
                else if (groups.get(i).cards_.get(j).color_ == YELLOW)
                    System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: YELLOW)");
                else if (groups.get(i).cards_.get(j).color_ == BLUE)
                    System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: BLUE)");
                else if (groups.get(i).cards_.get(j).color_ == BLACK)
                    System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: BLACK)");
            }
            System.out.println();
        }

    }
    void printFail(){
        System.out.println("Impossible");
    }
}