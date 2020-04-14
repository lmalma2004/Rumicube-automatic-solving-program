package com.rummysolver.engine;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class RummiKubSolve {
    private static final int RED    = 0;
    private static final int YELLOW = 1;
    private static final int BLUE   = 2;
    private static final int BLACK  = 3;
    int cntForTesting = 0;

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
            cards_ = new ArrayList<Card>();
        }
        public CardGroup clone_(){
            CardGroup newGroup = new CardGroup();
            for(int i=0; i<cards_.size(); i++){
                Card newCard = cards_.get(i).clone_();
                newGroup.cards_.add(newCard);
            }
            return newGroup;
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
    boolean canMakeGroup(int color, int number, boolean visited[][]){
        int preNumber1 = number - 2; int preCnt1;
        int preNumber2 = number - 1; int preCnt2;
        int nextNumber1 = number + 1; int nextCnt1;
        int nextNumber2 = number + 2; int nextCnt2;
        preCnt1 = preNumber1 < 0 ? 0 : (cards[color][preNumber1].count_ > 0 ? 1 : 0);
        preCnt2 = preNumber2 < 0 ? 0 : (cards[color][preNumber2].count_ > 0 ? 1 : 0);
        nextCnt1 = nextNumber1 > 13 ? 0 : (cards[color][nextNumber1].count_ > 0 ? 1 : 0);
        nextCnt2 = nextNumber2 > 13 ? 0 : (cards[color][nextNumber2].count_ > 0 ? 1 : 0);
        if(number >= 3 && number <= 11){
            if(nextCnt1 + nextCnt2 + jokerCnt >= 2) { //맨앞
                visited[color][number] = true;
                visited[color][nextCnt1] = true;
                visited[color][nextCnt2] = true;
                return true;
            }
            if(preCnt2 + nextCnt1 + jokerCnt>= 2) { //중간
                visited[color][number] = true;
                visited[color][preCnt1] = true;
                visited[color][nextCnt1] = true;
                return true;
            }
            if(preCnt1 + preCnt2 + jokerCnt>= 2) { //맨뒤
                visited[color][number] = true;
                visited[color][preCnt1] = true;
                visited[color][preCnt2] = true;
                return true;
            }
        }
        else if(number < 3){
            if(number == 1){
                if(nextCnt1 + nextCnt2 + jokerCnt>= 2) {
                    visited[color][number] = true;
                    visited[color][nextCnt1] = true;
                    visited[color][nextCnt2] = true;
                    return true;
                }
            }
            else if(number == 2){
                if(nextCnt1 + nextCnt2 + jokerCnt>= 2) {
                    visited[color][number] = true;
                    visited[color][nextCnt1] = true;
                    visited[color][nextCnt2] = true;
                    return true;
                }
                if(preCnt2 + nextCnt1 + jokerCnt>= 2) {
                    visited[color][number] = true;
                    visited[color][preCnt2] = true;
                    visited[color][nextCnt1] = true;
                    return true;
                }
            }
        }
        else if(number > 11){
            if(number == 12){
                if(preCnt1 + preCnt2 + jokerCnt>= 2) {
                    visited[color][number] = true;
                    visited[color][preCnt1] = true;
                    visited[color][preCnt2] = true;
                    return true;
                }
                if(preCnt2 + nextCnt1 + jokerCnt>= 2) {
                    visited[color][number] = true;
                    visited[color][preCnt2] = true;
                    visited[color][nextCnt1] = true;
                    return true;
                }
            }
            else if(number == 13){
                if(preCnt1 + preCnt2 + jokerCnt>= 2) {
                    visited[color][number] = true;
                    visited[color][preCnt1] = true;
                    visited[color][preCnt2] = true;
                    return true;
                }
            }
        }
        int diffColorSum = 0;
        for(int i=0; i<4; i++){
            if(i == color)
                continue;
            if(cards[i][number].count_ > 0)
                diffColorSum++;
        }
        if(diffColorSum + jokerCnt < 2)
            return false;
        return true;
    }

    boolean isPossible(){
        boolean visited[][] = new boolean[4][14];
        for(int i=0; i<4; i++)
            for(int j=0; j<14; j++)
                visited[i][j] = false;

        for(int i=0; i<4; i++){
            for(int j=1; j<14; j++){
                int color = i;
                int number = j;
                if(visited[color][number])
                    continue;
                if(cards[i][j].count_ > 0) {
                    if (canMakeGroup(color, number, visited))
                        continue;
                    else
                        return false;
                }
            }
        }
        return true;
    }

    boolean searchColor(CardGroup group, int color){
        for(int i=0; i<group.cards_.size(); i++){
            if(group.cards_.get(i).color_ == color)
                return true;
        }
        return false;
    }

    void getSameNumGroups(CardGroup group, ArrayList<CardGroup> retGroup, int turnOfColor){
        if(group.cards_.size() >= 6)
            return;
        if(group.cards_.size() >= 3) {
            CardGroup addGroup = group.clone_();
            retGroup.add(addGroup);
        }
        for(int i = turnOfColor; i < 4; i++){
            int currColor = i;
            if(searchColor(group, currColor)) continue;
            int number = group.cards_.get(0).number_;
            if(cards[currColor][number].count_ == 0) continue;
            subCard(currColor, number);
            Card card = new Card(number, currColor, false);
            group.cards_.add(card);
            getSameNumGroups(group, retGroup, currColor);
            addCard(currColor, number);
            group.cards_.remove(card);
        }
        if(jokerCnt > 0){
            for(int i=0; i<4; i++){
                int currColor = i;
                if(searchColor(group, currColor)) continue;
                int number = group.cards_.get(0).number_;
                subCard(0, 0);
                Card card = new Card(number, currColor, true);
                group.cards_.add(card);
                getSameNumGroups(group, retGroup, currColor);
                addCard(0, 0);
                group.cards_.remove(card);
            }
        }
    }
    void getDiffNumGroups(CardGroup group, ArrayList<CardGroup> retGroup){
        if(group.cards_.size() >= 6)
            return;
        if(group.cards_.size() >= 3){
            CardGroup addGroup = group.clone_();
            retGroup.add(addGroup);
        }
        int nextNumber = group.cards_.get(group.cards_.size() - 1).number_ + 1;
        if(nextNumber >= 14)
            return;

        int color = group.cards_.get(0).color_;
        if(cards[color][nextNumber].count_ > 0){
            subCard(color, nextNumber);
            Card card = new Card(nextNumber, color, false);
            group.cards_.add(card);
            getDiffNumGroups(group, retGroup);
            addCard(color, nextNumber);
            group.cards_.remove(card);
        }
        if(jokerCnt > 0){
            subCard(0, 0);
            Card card = new Card(nextNumber, color, true);
            group.cards_.add(card);
            getDiffNumGroups(group, retGroup);
            addCard(0, 0);
            group.cards_.remove(card);
        }
    }
    //같은숫자로 만들어진 그룹, 다른숫자로 만들어진 그룹들을 반환해야함
    ArrayList<CardGroup> getIncludeCardGroups(int color, int number, boolean joker, CardGroup group){
        Card card = new Card(number, color, joker);
        group.cards_.add(card);
        ArrayList<CardGroup> retGroups = new ArrayList<CardGroup>();
        getSameNumGroups(group, retGroups, 0);
        getDiffNumGroups(group, retGroups);
        return retGroups;
    }

    class ComparatorGroup implements Comparator<CardGroup>{
        @Override
        public int compare(CardGroup o1, CardGroup o2) {
            Integer o1Size = o1.cards_.size();
            Integer o2Size = o2.cards_.size();
            return o2Size.compareTo(o1Size);
        }
    }
    ArrayList<CardGroup> process(int color, int number, ArrayList<CardGroup> groups){
        subCard(color, number);
        CardGroup group = new CardGroup();
        ArrayList<CardGroup> nextGroups = getIncludeCardGroups(color, number, false, group);
        addCard(color, number);
        Collections.sort(nextGroups, new ComparatorGroup());
        for(int g = 0; g < nextGroups.size(); g++){
            CardGroup currGroup = nextGroups.get(g);
            subGroup(currGroup);
            groups.add(currGroup);
            ArrayList<CardGroup> retGroup = getMakeGroups(groups);
            if(retGroup != null)
                return retGroup;
            groups.remove(currGroup);
            addGroup(currGroup);
        }
        return null;
    }
    ArrayList<CardGroup> processJoker(int color, int number, ArrayList<CardGroup> groups){
        subCard(0, 0);
        CardGroup group = new CardGroup();
        ArrayList<CardGroup> nextGroups = getIncludeCardGroups(color, number, true, group);
        addCard(0, 0);
        Collections.sort(nextGroups, new ComparatorGroup());
        for(int g = 0; g < nextGroups.size(); g++){
            CardGroup currGroup = nextGroups.get(g);
            subGroup(currGroup);
            groups.add(currGroup);
            ArrayList<CardGroup> retGroup = getMakeGroups(groups);
            if(retGroup != null)
                return retGroup;
            groups.remove(currGroup);
            addGroup(currGroup);
        }
        return null;
    }


    ArrayList<CardGroup> getMakeGroups(ArrayList<CardGroup> groups){
        if(allCardCnt == 0){
            return groups;
        }
        if(!isPossible()){
            return null;
        }
        for(int i=0; i<4; i++){
            for(int j=0; j<14; j++){
                if(cards[i][j].count_ > 0){
                    if(j == 0){
                        for(int k=0; k<4; k++){
                            for(int h=1; h<14; h++){
                                int nextColor = k;
                                int nextNumber = h;
                                ArrayList<CardGroup> ret = processJoker(nextColor, nextNumber, groups);
                                if(ret != null)
                                    return ret;
                            }
                        }
                    }
                    else{
                        int nextColor = i;
                        int nextNumber = j;
                        ArrayList<CardGroup> ret = process(nextColor, nextNumber, groups);
                        if(ret != null)
                            return ret;
                    }
                }
            }
        }
        return null;
    }

    ArrayList<CardGroup> solve(){
        ArrayList<CardGroup> groups = new ArrayList<CardGroup>();
        groups = getMakeGroups(groups);
        return groups;
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
    void subGroup(CardGroup group){
        for(int i=0; i<group.cards_.size(); i++){
            boolean joker = group.cards_.get(i).joker_;
            if(joker) {
                subCard(0, 0);
                continue;
            }
            int color = group.cards_.get(i).color_;
            int number = group.cards_.get(i).number_;
            subCard(color, number);
        }
    }
    void addGroup(CardGroup group){
        for(int i=0; i<group.cards_.size(); i++){
            boolean joker = group.cards_.get(i).joker_;
            if(joker) {
                addCard(0, 0);
                continue;
            }
            int color = group.cards_.get(i).color_;
            int number = group.cards_.get(i).number_;
            addCard(color, number);
        }
    }
    void addCard(int color, int number){
        if(number == 0){
            addJoker();
            return;
        }
        cards[color][number].number_ = number;
        cards[color][number].color_ = color;
        cards[color][number].addCard();
        numberCnt[number]++;
        colorCnt[color]++;
        allCardCnt++;
    }
    void subCard(int color, int number){
        if(number == 0){
            subJoker();
            return;
        }
        cards[color][number].subCard();
        numberCnt[number]--;
        colorCnt[color]--;
        allCardCnt--;
    }
    void addJoker(){
        cards[0][0].joker_ = true;
        cards[0][0].addCard();
        jokerCnt++;
        allCardCnt++;
    }
    void subJoker(){
        cards[0][0].subCard();
        if(cards[0][0].count_ == 0)
            cards[0][0].joker_ = false;
        jokerCnt--;
        allCardCnt--;
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

    int getThresHolding(int cnt){
        return cnt > 0 ? 1 : 0;
    }
    //정당성 검증이 필요함
    boolean canMakeGroup2(int color, int number){
        int preNumber1 = number - 2; int preCnt1;
        int preNumber2 = number - 1; int preCnt2;
        int nextNumber1 = number + 1; int nextCnt1;
        int nextNumber2 = number + 2; int nextCnt2;
        int remainCnt = cards[color][number].count_;

        preCnt1 = preNumber1 < 0 ? 0 : cards[color][preNumber1].count_;
        preCnt2 = preNumber2 < 0 ? 0 : cards[color][preNumber2].count_;
        nextCnt1 = nextNumber1 > 13 ? 0 : cards[color][nextNumber1].count_;
        nextCnt2 = nextNumber2 > 13 ? 0 : cards[color][nextNumber2].count_;

        while(remainCnt > 0){
            if(number >= 3 && number <= 11){
                if(getThresHolding(nextCnt1) + getThresHolding(nextCnt2) + jokerCnt >= 2) { //맨앞
                    nextCnt1--;
                    nextCnt2--;
                    remainCnt--;
                    continue;
                }
                if(getThresHolding(preCnt2) + getThresHolding(nextCnt1) + jokerCnt>= 2) { //중간
                    preCnt2--;
                    nextCnt1--;
                    remainCnt--;
                    continue;
                }
                if(getThresHolding(preCnt1) + getThresHolding(preCnt2) + jokerCnt>= 2) { //맨뒤
                    preCnt1--;
                    preCnt2--;
                    remainCnt--;
                    continue;
                }
            }
            else if(number < 3){
                if(number == 1){
                    if(getThresHolding(nextCnt1) + getThresHolding(nextCnt2) + jokerCnt>= 2) {
                        nextCnt1--;
                        nextCnt2--;
                        remainCnt--;
                        continue;
                    }
                }
                else if(number == 2){
                    if(getThresHolding(nextCnt1) + getThresHolding(nextCnt2) + jokerCnt>= 2) {
                        nextCnt1--;
                        nextCnt2--;
                        remainCnt--;
                        continue;
                    }
                    if(getThresHolding(preCnt2) + getThresHolding(nextCnt1) + jokerCnt>= 2) {
                        preCnt2--;
                        nextCnt1--;
                        remainCnt--;
                        continue;
                    }
                }
            }
            else if(number > 11){
                if(number == 12){
                    if(getThresHolding(preCnt1) + getThresHolding(preCnt2) + jokerCnt>= 2) {
                        preCnt1--;
                        preCnt2--;
                        remainCnt--;
                        continue;
                    }
                    if(getThresHolding(preCnt2) + getThresHolding(nextCnt1) + jokerCnt>= 2) {
                        preCnt2--;
                        nextCnt1--;
                        remainCnt--;
                        continue;
                    }
                }
                else if(number == 13){
                    if(getThresHolding(preCnt1) + getThresHolding(preCnt2) + jokerCnt>= 2) {
                        preCnt1--;
                        preCnt2--;
                        remainCnt--;
                        continue;
                    }
                }
            }
            int diffColorSum = 0;
            for(int i=0; i<4; i++){
                if(i == color)
                    continue;
                if(cards[i][number].count_ > 0)
                    diffColorSum++;
            }
            if(diffColorSum + jokerCnt >= 2) {
                remainCnt--;
                continue;
            }
            return false;
        }
        return true;
    }
}