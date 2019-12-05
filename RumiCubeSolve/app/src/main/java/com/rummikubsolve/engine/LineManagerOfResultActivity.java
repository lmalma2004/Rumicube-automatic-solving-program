package com.rummikubsolve.engine;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class LineManagerOfResultActivity {
    private static final int RED    = 0;
    private static final int YELLOW = 1;
    private static final int BLUE   = 2;
    private static final int BLACK  = 3;
    private static final int horiLineMaxGroupNumber = 3;

    public ArrayList<Line> commonCardLine;
    public int commonLineNumber;


    public class Line{
        public TableRow line;
        public int cardNumber;
        public int groupNumber;
        public Line(){
            line = null;
            cardNumber = 0;
            groupNumber = 0;
        }
        public void addCard(final int color, final int number, Context context){
            cardNumber++;
            if(line == null) {
                line = new TableRow(context);
            }
            final ImageButton newButton = new ImageButton(context);
            setButton(color, number, newButton);
            line.addView(newButton);
        }
        public void addFail(Context context){
            line = new TableRow(context);
            final TextView newText = new TextView(context);
            newText.setText("There is no answer with the current card combination.");
            line.addView(newText);
            line.setGravity(1);
        }
        public void addTransparentCard(Context context){
            final ImageButton newButton = new ImageButton(context);
            newButton.setBackground(null);
            newButton.setMinimumWidth(10);
            newButton.setScaleType(ImageButton.ScaleType.FIT_XY);
            newButton.setAdjustViewBounds(true);
            line.addView(newButton);
        }
        public void addGroup(final RummiKubSolve.CardGroup group, Context context){
            for(int i=0; i<group.cards_.size(); i++) {
                if(group.cards_.get(i).joker_)
                    addCard(group.cards_.get(i).color_, 0, context);
                else
                    addCard(group.cards_.get(i).color_, group.cards_.get(i).number_, context);
            }
            addTransparentCard(context);
            groupNumber++;
        }
    }
    public void setButton(int color, int number, ImageButton button){
        button.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        button.setBackground(null);
        button.setMaxHeight(120);
        button.setPadding(0,0,0,0);
        button.setScaleType(ImageButton.ScaleType.FIT_XY);
        button.setAdjustViewBounds(true);

        if(number == 0){
            button.setImageResource(R.drawable.joker_black);
        }
        else if(number == 1){
            if(color == RED)
                button.setImageResource(R.drawable.red1);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow1);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue1);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black1);
        }
        else if(number == 2){
            if(color == RED)
                button.setImageResource(R.drawable.red2);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow2);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue2);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black2);

        }
        else if(number == 3){
            if(color == RED)
                button.setImageResource(R.drawable.red3);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow3);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue3);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black3);
        }
        else if(number == 4){
            if(color == RED)
                button.setImageResource(R.drawable.red4);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow4);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue4);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black4);
        }
        else if(number == 5){
            if(color == RED)
                button.setImageResource(R.drawable.red5);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow5);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue5);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black5);
        }
        else if(number == 6){
            if(color == RED)
                button.setImageResource(R.drawable.red6);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow6);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue6);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black6);
        }
        else if(number == 7){
            if(color == RED)
                button.setImageResource(R.drawable.red7);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow7);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue7);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black7);
        }
        else if(number == 8){
            if(color == RED)
                button.setImageResource(R.drawable.red8);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow8);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue8);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black8);
        }
        else if(number == 9){
            if(color == RED)
                button.setImageResource(R.drawable.red9);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow9);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue9);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black9);
        }
        else if(number == 10){
            if(color == RED)
                button.setImageResource(R.drawable.red10);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow10);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue10);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black10);
        }
        else if(number == 11){
            if(color == RED)
                button.setImageResource(R.drawable.red11);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow11);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue11);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black11);
        }
        else if(number == 12){
            if(color == RED)
                button.setImageResource(R.drawable.red12);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow12);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue12);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black12);
        }
        else if(number == 13){
            if(color == RED)
                button.setImageResource(R.drawable.red13);
            else if(color == YELLOW)
                button.setImageResource(R.drawable.yellow13);
            else if(color == BLUE)
                button.setImageResource(R.drawable.blue13);
            else if(color == BLACK)
                button.setImageResource(R.drawable.black13);
        }
    }

    public LineManagerOfResultActivity(){
        commonCardLine = new ArrayList<>();
        commonLineNumber = 0;
    }
    public void setWeightLine(Line currLine){
        for(int i=0; i<currLine.line.getChildCount(); i++){
            currLine.line.getChildAt(i).setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        }
    }
    //LineManagerOfMainActivity와 다름
    public void addResultLine(RummiKubSolve.CardGroup cardGroup, TableLayout tableLayout, Context context){
        if(commonCardLine.isEmpty()){
            commonLineNumber++;
            Line newLine = new Line();
            newLine.addGroup(cardGroup, context);
            newLine.line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            commonCardLine.add(newLine);
            tableLayout.addView(newLine.line);
        }
        else{
            Line currLine = commonCardLine.get(commonCardLine.size() - 1);
            if(currLine.groupNumber >= horiLineMaxGroupNumber){
                commonLineNumber++;
                Line newLine = new Line();
                newLine.addGroup(cardGroup, context);
                newLine.line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                commonCardLine.add(newLine);
                tableLayout.addView(newLine.line);
            }
            else{
                currLine.addGroup(cardGroup, context);
                if(currLine.groupNumber >= horiLineMaxGroupNumber)
                    setWeightLine(currLine);
            }
        }
    }
    public void addFailLine(TableLayout tableLayout, Context context){
        Line newLine = new Line();
        newLine.addFail(context);

        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
        params.topMargin = 300;

        tableLayout.setLayoutParams(params);
        tableLayout.addView(newLine.line);
    }

}
