package com.rummysolver.engine;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class LineManagerOfMainActivity {

    private static final int RED = 0;
    private static final int YELLOW = 1;
    private static final int BLUE = 2;
    private static final int BLACK = 3;
    private static final int horiLineMaxNumber = 13;
    private static final int FIELD_MY = 0;
    private static final int FIELD_COMMON = 1;

    public Line myCardLine[];
    public ArrayList<Line> commonCardLine;
    public int commonLineNumber;

    public class Line {
        public TableRow line;
        public int cardNumber;

        public Line() {
            line = null;
            cardNumber = 0;
        }

        public void addCard(final int color, final int number, Context context, final RummiKubSolve rumi, final int field) {
            cardNumber++;
            if (line == null) {
                line = new TableRow(context);
            }
            final ImageButton newButton = new ImageButton(context);
            setButton(color, number, newButton);
            line.addView(newButton);
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardNumber--;
                    if (number == 0) {
                        rumi.subJoker();
                    } else {
                        if (color == RED)
                            rumi.subCard(0, number);
                        else if (color == YELLOW)
                            rumi.subCard(1, number);
                        else if (color == BLUE)
                            rumi.subCard(2, number);
                        else if (color == BLACK)
                            rumi.subCard(3, number);
                    }
                    line.removeView(newButton);
                }
            });
        }
    }

    public LineManagerOfMainActivity() {
        myCardLine = new Line[2];
        myCardLine[0] = new Line();
        myCardLine[1] = new Line();
        commonCardLine = new ArrayList<>();
        commonLineNumber = 0;
    }

    public void setButton(int color, int number, ImageButton button) {
        button.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        button.setBackground(null);
        button.setMaxHeight(130);
        button.setPadding(0, 0, 0, 0);
        button.setScaleType(ImageButton.ScaleType.FIT_XY);
        button.setAdjustViewBounds(true);

        if (number == 0) {
            button.setImageResource(R.drawable.joker_black2);
        } else if (number == 1) {
            if (color == RED)
                button.setImageResource(R.drawable.red1);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow1);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue1);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black1);
        } else if (number == 2) {
            if (color == RED)
                button.setImageResource(R.drawable.red2);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow2);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue2);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black2);

        } else if (number == 3) {
            if (color == RED)
                button.setImageResource(R.drawable.red3);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow3);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue3);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black3);
        } else if (number == 4) {
            if (color == RED)
                button.setImageResource(R.drawable.red4);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow4);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue4);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black4);
        } else if (number == 5) {
            if (color == RED)
                button.setImageResource(R.drawable.red5);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow5);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue5);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black5);
        } else if (number == 6) {
            if (color == RED)
                button.setImageResource(R.drawable.red6);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow6);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue6);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black6);
        } else if (number == 7) {
            if (color == RED)
                button.setImageResource(R.drawable.red7);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow7);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue7);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black7);
        } else if (number == 8) {
            if (color == RED)
                button.setImageResource(R.drawable.red8);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow8);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue8);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black8);
        } else if (number == 9) {
            if (color == RED)
                button.setImageResource(R.drawable.red9);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow9);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue9);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black9);
        } else if (number == 10) {
            if (color == RED)
                button.setImageResource(R.drawable.red10);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow10);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue10);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black10);
        } else if (number == 11) {
            if (color == RED)
                button.setImageResource(R.drawable.red11);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow11);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue11);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black11);
        } else if (number == 12) {
            if (color == RED)
                button.setImageResource(R.drawable.red12);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow12);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue12);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black12);
        } else if (number == 13) {
            if (color == RED)
                button.setImageResource(R.drawable.red13);
            else if (color == YELLOW)
                button.setImageResource(R.drawable.yellow13);
            else if (color == BLUE)
                button.setImageResource(R.drawable.blue13);
            else if (color == BLACK)
                button.setImageResource(R.drawable.black13);
        }
    }

    public void addMyLine(int color, int number, Context context, RummiKubSolve rumi) {
        int allCardNumber = myCardLine[0].cardNumber + myCardLine[1].cardNumber;

        if (allCardNumber >= horiLineMaxNumber * 2) {
            int currLineNumber = myCardLine[0].cardNumber > myCardLine[1].cardNumber ? 1 : 0;
            myCardLine[currLineNumber].addCard(color, number, context, rumi, FIELD_MY);
        } else if (allCardNumber >= horiLineMaxNumber) {
            myCardLine[1].addCard(color, number, context, rumi, FIELD_MY);
        } else {
            myCardLine[0].addCard(color, number, context, rumi, FIELD_MY);
        }
    }

    public void addCommonLine(int color, int number, TableLayout tableLayout, Context context, RummiKubSolve rumi) {
        if (commonCardLine.isEmpty()) {
            commonLineNumber++;
            Line newLine = new Line();
            newLine.addCard(color, number, context, rumi, FIELD_COMMON);
            newLine.line.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            commonCardLine.add(newLine);
            tableLayout.addView(newLine.line);
        } else {
            Line currLine = commonCardLine.get(commonCardLine.size() - 1);

            if (currLine.cardNumber >= horiLineMaxNumber) {
                commonLineNumber++;
                Line newLine = new Line();
                newLine.addCard(color, number, context, rumi, FIELD_COMMON);
                newLine.line.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                commonCardLine.add(newLine);
                tableLayout.addView(newLine.line);
            } else {
                currLine.addCard(color, number, context, rumi, FIELD_COMMON);
            }
        }
    }
}
