package com.rummysolver.engine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity_Demo extends AppCompatActivity {

    private static final int RED = 0;
    private static final int YELLOW = 1;
    private static final int BLUE = 2;
    private static final int BLACK = 3;
    private static final int LINECARDNUM = 7; //라인하나에 보이는 최대 카드 개수
    private static Button cards[] = new Button[14];
    private static Button color;
    private static Button solve;
    private static Button state;

    public static RummiKubSolve rumi = new RummiKubSolve();
    public static int currentColor = BLACK;
    public LinearLayout allOfResultCards;
    public ArrayList<LinearLayout> lineOfResultCards = new ArrayList<LinearLayout>();
    public LinearLayout lineOfCurrentCards[] = new LinearLayout[15];
    public Runnable r;
    public Thread threadOfSolve;
    public LineManagerOfMainActivity lineManagerOfMainActivity;

    class solveThread implements Runnable {
        public void run() {
            lineOfResultCards.clear();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    state.setText("계산 중");
                }
            });

            RummiKubSolve copyOfRumi = rumi.clone_();
            ArrayList<RummiKubSolve.CardGroup> result = copyOfRumi.solve();

            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    LinearLayout lineOfResultCard = new LinearLayout(getApplicationContext());
                    lineOfResultCard.setOrientation(LinearLayout.HORIZONTAL);
                    for (int j = 0; j < result.get(i).cards_.size(); j++) {
                        if (result.get(i).cards_.get(j).joker_) {
                            addButtonOfResultCardView(lineOfResultCard, 0, 0);
                            //System.out.print("(joker) /");
                            continue;
                        }
                        if (result.get(i).cards_.get(j).color_ == RED)
                            addButtonOfResultCardView(lineOfResultCard,
                                    result.get(i).cards_.get(j).number_, RED);
                            //System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: RED)");
                        else if (result.get(i).cards_.get(j).color_ == YELLOW)
                            addButtonOfResultCardView(lineOfResultCard,
                                    result.get(i).cards_.get(j).number_, YELLOW);
                            //System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: YELLOW)");
                        else if (result.get(i).cards_.get(j).color_ == BLUE)
                            addButtonOfResultCardView(lineOfResultCard,
                                    result.get(i).cards_.get(j).number_, BLUE);
                            //System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: BLUE)");
                        else if (result.get(i).cards_.get(j).color_ == BLACK)
                            addButtonOfResultCardView(lineOfResultCard,
                                    result.get(i).cards_.get(j).number_, BLACK);
                        //System.out.println("(num: " + groups.get(i).cards_.get(j).number_ + ", color: BLACK)");
                    }
                    lineOfResultCards.add(lineOfResultCard);
                    //System.out.println();
                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    state.setText("계산 완료");
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        createLayouts();
        createButtons();
        initButtonColor();
        createEventOfCardButtons();
        createEventOfColorButton();
        createEventOfSolveButton();
        createEventOfStateButton();
    }

    protected void createLayouts() {
        lineOfCurrentCards[0] = (LinearLayout) findViewById(R.id.line0OfCurrentCard);
        lineOfCurrentCards[1] = (LinearLayout) findViewById(R.id.line1OfCurrentCard);
        lineOfCurrentCards[2] = (LinearLayout) findViewById(R.id.line2OfCurrentCard);
        lineOfCurrentCards[3] = (LinearLayout) findViewById(R.id.line3OfCurrentCard);
        lineOfCurrentCards[4] = (LinearLayout) findViewById(R.id.line4OfCurrentCard);
        lineOfCurrentCards[5] = (LinearLayout) findViewById(R.id.line5OfCurrentCard);
        lineOfCurrentCards[6] = (LinearLayout) findViewById(R.id.line6OfCurrentCard);
        lineOfCurrentCards[7] = (LinearLayout) findViewById(R.id.line7OfCurrentCard);
        lineOfCurrentCards[8] = (LinearLayout) findViewById(R.id.line8OfCurrentCard);
        lineOfCurrentCards[9] = (LinearLayout) findViewById(R.id.line9OfCurrentCard);
        lineOfCurrentCards[10] = (LinearLayout) findViewById(R.id.line10OfCurrentCard);
        lineOfCurrentCards[11] = (LinearLayout) findViewById(R.id.line11OfCurrentCard);
        lineOfCurrentCards[12] = (LinearLayout) findViewById(R.id.line12OfCurrentCard);
        lineOfCurrentCards[13] = (LinearLayout) findViewById(R.id.line13OfCurrentCard);
        lineOfCurrentCards[14] = (LinearLayout) findViewById(R.id.line14OfCurrentCard);

        allOfResultCards = (LinearLayout) findViewById(R.id.allOfresultCards);
    }

    protected void createButtons() {
        cards[0] = (Button) findViewById(R.id.cardJ);
        cards[1] = (Button) findViewById(R.id.card1);
        cards[2] = (Button) findViewById(R.id.card2);
        cards[3] = (Button) findViewById(R.id.card3);
        cards[4] = (Button) findViewById(R.id.card4);
        cards[5] = (Button) findViewById(R.id.card5);
        cards[6] = (Button) findViewById(R.id.card6);
        cards[7] = (Button) findViewById(R.id.card7);
        cards[8] = (Button) findViewById(R.id.card8);
        cards[9] = (Button) findViewById(R.id.card9);
        cards[10] = (Button) findViewById(R.id.card10);
        cards[11] = (Button) findViewById(R.id.card11);
        cards[12] = (Button) findViewById(R.id.card12);
        cards[13] = (Button) findViewById(R.id.card13);
        color = (Button) findViewById(R.id.color);
        solve = (Button) findViewById(R.id.solve);
        state = (Button) findViewById(R.id.state);
    }

    protected void createEventOfStateButton() {
        final TextView impossible = new TextView(this);
        impossible.setText("현재 상황에선 답이 없습니다.");
        impossible.setTextColor(Color.RED);
        impossible.setGravity(Gravity.CENTER);

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allOfResultCards.removeAllViews();
                if (lineOfResultCards.size() != 0) {
                    for (int i = 0; i < lineOfResultCards.size(); i++)
                        allOfResultCards.addView(lineOfResultCards.get(i));
                } else {
                    allOfResultCards.addView(impossible);
                }
            }
        });
    }

    protected void createEventOfSolveButton() {
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r = new solveThread();
                threadOfSolve = new Thread(r);
                threadOfSolve.start();
            }
        });
    }

    protected void createEventOfColorButton() {
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                if (currentColor == BLACK)
                    Toast.makeText(getApplicationContext(), "Current Color : BLACK", Toast.LENGTH_SHORT).show();
                else if (currentColor == RED)
                    Toast.makeText(getApplicationContext(), "Current Color : RED", Toast.LENGTH_SHORT).show();
                else if (currentColor == YELLOW)
                    Toast.makeText(getApplicationContext(), "Current Color : YELLOW", Toast.LENGTH_SHORT).show();
                else if (currentColor == BLUE)
                    Toast.makeText(getApplicationContext(), "Current Color : BLUE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void createEventOfCardButtons() {
        cards[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addJoker();
                addButtonOfCurrentCardView(0);
                Toast.makeText(getApplicationContext(), "Joker Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 1);
                addButtonOfCurrentCardView(1);
                Toast.makeText(getApplicationContext(), "1 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 2);
                addButtonOfCurrentCardView(2);
                Toast.makeText(getApplicationContext(), "2 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 3);
                addButtonOfCurrentCardView(3);
                Toast.makeText(getApplicationContext(), "3 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 4);
                addButtonOfCurrentCardView(4);
                Toast.makeText(getApplicationContext(), "4 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 5);
                addButtonOfCurrentCardView(5);
                Toast.makeText(getApplicationContext(), "5 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 6);
                addButtonOfCurrentCardView(6);
                Toast.makeText(getApplicationContext(), "6 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 7);
                addButtonOfCurrentCardView(7);
                Toast.makeText(getApplicationContext(), "7 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 8);
                addButtonOfCurrentCardView(8);
                Toast.makeText(getApplicationContext(), "8 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 9);
                addButtonOfCurrentCardView(9);
                Toast.makeText(getApplicationContext(), "9 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 10);
                addButtonOfCurrentCardView(10);
                Toast.makeText(getApplicationContext(), "10 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 11);
                addButtonOfCurrentCardView(11);
                Toast.makeText(getApplicationContext(), "11 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 12);
                addButtonOfCurrentCardView(12);
                Toast.makeText(getApplicationContext(), "12 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rumi.addCard(currentColor, 13);
                addButtonOfCurrentCardView(13);
                Toast.makeText(getApplicationContext(), "13 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void createEventOfCurrCardButtons(final Button button, final int lineNumber) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence currNumber = button.getText();
                ColorStateList currColor = button.getTextColors();

                if (currNumber.toString() == "J") {
                    rumi.subJoker();
                } else {
                    if (currColor.getDefaultColor() == Color.RED)
                        rumi.subCard(0, Integer.parseInt(currNumber.toString()));
                    else if (currColor.getDefaultColor() == Color.YELLOW)
                        rumi.subCard(1, Integer.parseInt(currNumber.toString()));
                    else if (currColor.getDefaultColor() == Color.BLUE)
                        rumi.subCard(2, Integer.parseInt(currNumber.toString()));
                    else if (currColor.getDefaultColor() == Color.BLACK)
                        rumi.subCard(3, Integer.parseInt(currNumber.toString()));
                }
                lineOfCurrentCards[lineNumber].removeView(button);
            }
        });
    }

    protected void initButtonColor() {
        for (int i = 1; i < 14; i++)
            cards[i].setTextColor(Color.BLACK);
    }

    protected void changeColor() {
        currentColor = (currentColor + 1) % 4;
        if (currentColor == RED) {
            for (int i = 1; i < 14; i++)
                cards[i].setTextColor(Color.RED);
        } else if (currentColor == YELLOW) {
            for (int i = 1; i < 14; i++)
                cards[i].setTextColor(Color.YELLOW);
        } else if (currentColor == BLUE) {
            for (int i = 1; i < 14; i++)
                cards[i].setTextColor(Color.BLUE);
        } else if (currentColor == BLACK) {
            for (int i = 1; i < 14; i++)
                cards[i].setTextColor(Color.BLACK);
        }
    }

    void setButton(Button button, int number) {

        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f));

        if (number == 0) {
            button.setText("J");
            return;
        }

        if (currentColor == RED)
            button.setTextColor(Color.RED);
        else if (currentColor == YELLOW)
            button.setTextColor(Color.YELLOW);
        else if (currentColor == BLUE)
            button.setTextColor(Color.BLUE);
        else if (currentColor == BLACK)
            button.setTextColor(Color.BLACK);

        button.setText(Integer.toString(number));
    }

    void setButton(Button button, int number, int color) {

        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f));

        if (number == 0) {
            button.setText("J");
            return;
        }

        if (color == RED)
            button.setTextColor(Color.RED);
        else if (color == YELLOW)
            button.setTextColor(Color.YELLOW);
        else if (color == BLUE)
            button.setTextColor(Color.BLUE);
        else if (color == BLACK)
            button.setTextColor(Color.BLACK);

        button.setText(Integer.toString(number));
    }

    protected void addButtonOfResultCardView(LinearLayout view, int number, int color) {
        Button addButton = new Button(this);
        setButton(addButton, number, color);
        view.addView(addButton);
    }

    protected void addButtonOfCurrentCardView(final int number) {
        final int lineNumber;

        if (rumi.allCardCnt % LINECARDNUM == 0)
            lineNumber = rumi.allCardCnt / LINECARDNUM - 1;
        else
            lineNumber = rumi.allCardCnt / LINECARDNUM;

        Button addButton = new Button(this);
        setButton(addButton, number);
        createEventOfCurrCardButtons(addButton, lineNumber);
        lineOfCurrentCards[lineNumber].addView(addButton);
    }
}
















