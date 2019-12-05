package com.rummikubsolve.engine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final int RED          = 0;
    private static final int YELLOW       = 1;
    private static final int BLUE         = 2;
    private static final int BLACK        = 3;
    private static final int FIELD_MY     = 0;
    private static final int FIELD_COMMON = 1;
    public static int currentColor = BLACK;

    public TableLayout CommonCard;
    public TableLayout MyCard;

    private static ImageButton cards[];
    private static ImageButton color;
    private static ImageButton field;
    private LottieAnimationView reset;
    private LottieAnimationView solve;
    private LottieAnimationView state;

    public static RummiKubSolve rummi;
    public ArrayList<RummiKubSolve.CardGroup> result;
    public ArrayList<LinearLayout> lineOfResultCards = new ArrayList<LinearLayout>();
    public LineManagerOfMainActivity lineManagerOfMainActivity = new LineManagerOfMainActivity();

    public int currField = FIELD_COMMON;

    class solveThread implements Runnable{
        public void run(){
            lineOfResultCards.clear();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    state.setAnimation("search.json");
                    state.playAnimation();
                }
            });

            RummiKubSolve copyOfRummi = rummi.clone_();
            result = copyOfRummi.solve();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    state.pauseAnimation();
                    state.setAnimation("answer.json");
                    state.playAnimation();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createLayouts();
        createButtons();

        init();
        createEventOfCardButtons();
        createEventOfColorButton();
        createEventOfFieldButton();
        createEventOfResetButton();
        createEventOfSolveButton();
        createEventOfStateButton();
    }
    protected void createLayouts(){
        CommonCard = (TableLayout)findViewById(R.id.CommonCardLayout);
        MyCard     = (TableLayout)findViewById(R.id.MyCardLayout);
        lineManagerOfMainActivity.myCardLine[0].line = (TableRow)findViewById(R.id.MyCardRow1);
        lineManagerOfMainActivity.myCardLine[1].line = (TableRow)findViewById(R.id.MyCardRow2);
    }
    protected void createButtons(){
        cards = new ImageButton[14];
        cards[0] = (ImageButton)findViewById(R.id.cardJ);
        cards[1] = (ImageButton)findViewById(R.id.card1);
        cards[2] = (ImageButton)findViewById(R.id.card2);
        cards[3] = (ImageButton)findViewById(R.id.card3);
        cards[4] = (ImageButton)findViewById(R.id.card4);
        cards[5] = (ImageButton)findViewById(R.id.card5);
        cards[6] = (ImageButton)findViewById(R.id.card6);
        cards[7] = (ImageButton)findViewById(R.id.card7);
        cards[8] = (ImageButton)findViewById(R.id.card8);
        cards[9] = (ImageButton)findViewById(R.id.card9);
        cards[10] = (ImageButton)findViewById(R.id.card10);
        cards[11] = (ImageButton)findViewById(R.id.card11);
        cards[12] = (ImageButton)findViewById(R.id.card12);
        cards[13] = (ImageButton)findViewById(R.id.card13);

        color = (ImageButton) findViewById(R.id.color);
        field = (ImageButton) findViewById(R.id.field);

        reset = (LottieAnimationView) findViewById(R.id.reset);
        solve = (LottieAnimationView) findViewById(R.id.solve);
        state = (LottieAnimationView) findViewById(R.id.state);
    }
    protected void init(){
        rummi = new RummiKubSolve();

        if(currentColor == RED){
            cards[1].setImageResource(R.drawable.red1);
            cards[2].setImageResource(R.drawable.red2);
            cards[3].setImageResource(R.drawable.red3);
            cards[4].setImageResource(R.drawable.red4);
            cards[5].setImageResource(R.drawable.red5);
            cards[6].setImageResource(R.drawable.red6);
            cards[7].setImageResource(R.drawable.red7);
            cards[8].setImageResource(R.drawable.red8);
            cards[9].setImageResource(R.drawable.red9);
            cards[10].setImageResource(R.drawable.red10);
            cards[11].setImageResource(R.drawable.red11);
            cards[12].setImageResource(R.drawable.red12);
            cards[13].setImageResource(R.drawable.red13);
        }
        else if(currentColor == YELLOW){
            cards[1].setImageResource(R.drawable.yellow1);
            cards[2].setImageResource(R.drawable.yellow2);
            cards[3].setImageResource(R.drawable.yellow3);
            cards[4].setImageResource(R.drawable.yellow4);
            cards[5].setImageResource(R.drawable.yellow5);
            cards[6].setImageResource(R.drawable.yellow6);
            cards[7].setImageResource(R.drawable.yellow7);
            cards[8].setImageResource(R.drawable.yellow8);
            cards[9].setImageResource(R.drawable.yellow9);
            cards[10].setImageResource(R.drawable.yellow10);
            cards[11].setImageResource(R.drawable.yellow11);
            cards[12].setImageResource(R.drawable.yellow12);
            cards[13].setImageResource(R.drawable.yellow13);
        }
        else if(currentColor == BLUE){
            cards[1].setImageResource(R.drawable.blue1);
            cards[2].setImageResource(R.drawable.blue2);
            cards[3].setImageResource(R.drawable.blue3);
            cards[4].setImageResource(R.drawable.blue4);
            cards[5].setImageResource(R.drawable.blue5);
            cards[6].setImageResource(R.drawable.blue6);
            cards[7].setImageResource(R.drawable.blue7);
            cards[8].setImageResource(R.drawable.blue8);
            cards[9].setImageResource(R.drawable.blue9);
            cards[10].setImageResource(R.drawable.blue10);
            cards[11].setImageResource(R.drawable.blue11);
            cards[12].setImageResource(R.drawable.blue12);
            cards[13].setImageResource(R.drawable.blue13);
        }
        else if(currentColor == BLACK){
            cards[1].setImageResource(R.drawable.black1);
            cards[2].setImageResource(R.drawable.black2);
            cards[3].setImageResource(R.drawable.black3);
            cards[4].setImageResource(R.drawable.black4);
            cards[5].setImageResource(R.drawable.black5);
            cards[6].setImageResource(R.drawable.black6);
            cards[7].setImageResource(R.drawable.black7);
            cards[8].setImageResource(R.drawable.black8);
            cards[9].setImageResource(R.drawable.black9);
            cards[10].setImageResource(R.drawable.black10);
            cards[11].setImageResource(R.drawable.black11);
            cards[12].setImageResource(R.drawable.black12);
            cards[13].setImageResource(R.drawable.black13);
        }

        lineManagerOfMainActivity.myCardLine[0].line.removeAllViews();
        lineManagerOfMainActivity.myCardLine[1].line.removeAllViews();
        CommonCard.removeAllViews();

        LineManagerOfMainActivity newLineManager = new LineManagerOfMainActivity();
        lineManagerOfMainActivity = newLineManager;
        lineManagerOfMainActivity.myCardLine[0].line = (TableRow)findViewById(R.id.MyCardRow1);
        lineManagerOfMainActivity.myCardLine[1].line = (TableRow)findViewById(R.id.MyCardRow2);
    }
    protected void changeColor(){
        currentColor = (currentColor + 1) % 4;
        if(currentColor == RED){
            cards[1].setImageResource(R.drawable.red1);
            cards[2].setImageResource(R.drawable.red2);
            cards[3].setImageResource(R.drawable.red3);
            cards[4].setImageResource(R.drawable.red4);
            cards[5].setImageResource(R.drawable.red5);
            cards[6].setImageResource(R.drawable.red6);
            cards[7].setImageResource(R.drawable.red7);
            cards[8].setImageResource(R.drawable.red8);
            cards[9].setImageResource(R.drawable.red9);
            cards[10].setImageResource(R.drawable.red10);
            cards[11].setImageResource(R.drawable.red11);
            cards[12].setImageResource(R.drawable.red12);
            cards[13].setImageResource(R.drawable.red13);
        }
        else if(currentColor == YELLOW){
            cards[1].setImageResource(R.drawable.yellow1);
            cards[2].setImageResource(R.drawable.yellow2);
            cards[3].setImageResource(R.drawable.yellow3);
            cards[4].setImageResource(R.drawable.yellow4);
            cards[5].setImageResource(R.drawable.yellow5);
            cards[6].setImageResource(R.drawable.yellow6);
            cards[7].setImageResource(R.drawable.yellow7);
            cards[8].setImageResource(R.drawable.yellow8);
            cards[9].setImageResource(R.drawable.yellow9);
            cards[10].setImageResource(R.drawable.yellow10);
            cards[11].setImageResource(R.drawable.yellow11);
            cards[12].setImageResource(R.drawable.yellow12);
            cards[13].setImageResource(R.drawable.yellow13);
        }
        else if(currentColor == BLUE){
            cards[1].setImageResource(R.drawable.blue1);
            cards[2].setImageResource(R.drawable.blue2);
            cards[3].setImageResource(R.drawable.blue3);
            cards[4].setImageResource(R.drawable.blue4);
            cards[5].setImageResource(R.drawable.blue5);
            cards[6].setImageResource(R.drawable.blue6);
            cards[7].setImageResource(R.drawable.blue7);
            cards[8].setImageResource(R.drawable.blue8);
            cards[9].setImageResource(R.drawable.blue9);
            cards[10].setImageResource(R.drawable.blue10);
            cards[11].setImageResource(R.drawable.blue11);
            cards[12].setImageResource(R.drawable.blue12);
            cards[13].setImageResource(R.drawable.blue13);
        }
        else if(currentColor == BLACK){
            cards[1].setImageResource(R.drawable.black1);
            cards[2].setImageResource(R.drawable.black2);
            cards[3].setImageResource(R.drawable.black3);
            cards[4].setImageResource(R.drawable.black4);
            cards[5].setImageResource(R.drawable.black5);
            cards[6].setImageResource(R.drawable.black6);
            cards[7].setImageResource(R.drawable.black7);
            cards[8].setImageResource(R.drawable.black8);
            cards[9].setImageResource(R.drawable.black9);
            cards[10].setImageResource(R.drawable.black10);
            cards[11].setImageResource(R.drawable.black11);
            cards[12].setImageResource(R.drawable.black12);
            cards[13].setImageResource(R.drawable.black13);
        }
    }
    protected void createEventOfStateButton(){
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putParcelableArrayListExtra("Result", result);
                startActivity(intent);
            }
        });
    }
    protected void createEventOfSolveButton(){
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable r = new solveThread();
                Thread threadOfSolve = new Thread(r);
                threadOfSolve.start();
            }
        });
    }
    protected void createEventOfResetButton(){
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RummiKubSolve newRumi = new RummiKubSolve();
                rummi = newRumi;

                lineManagerOfMainActivity.myCardLine[0].line.removeAllViews();
                lineManagerOfMainActivity.myCardLine[1].line.removeAllViews();
                CommonCard.removeAllViews();

                LineManagerOfMainActivity newLineManager = new LineManagerOfMainActivity();
                lineManagerOfMainActivity = newLineManager;
                lineManagerOfMainActivity.myCardLine[0].line = (TableRow)findViewById(R.id.MyCardRow1);
                lineManagerOfMainActivity.myCardLine[1].line = (TableRow)findViewById(R.id.MyCardRow2);
            }
        });
    }
    protected void createEventOfFieldButton(){
        field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currField == FIELD_COMMON) {
                    currField = FIELD_MY;
                    field.setImageResource(R.drawable.personal);
                }
                else {
                    currField = FIELD_COMMON;
                    field.setImageResource(R.drawable.common);
                }
            }
        });
    }
    protected void createEventOfColorButton(){
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                if(currentColor == BLACK)
                    Toast.makeText(getApplicationContext(), "Current Color : BLACK", Toast.LENGTH_SHORT).show();
                else if(currentColor == RED)
                    Toast.makeText(getApplicationContext(), "Current Color : RED", Toast.LENGTH_SHORT).show();
                else if(currentColor == YELLOW)
                    Toast.makeText(getApplicationContext(), "Current Color : YELLOW", Toast.LENGTH_SHORT).show();
                else if(currentColor == BLUE)
                    Toast.makeText(getApplicationContext(), "Current Color : BLUE", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void createEventOfCardButtons(){
        cards[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addJoker();
                addButtonOfCurrentCardView(0);
                Toast.makeText(getApplicationContext(), "Joker Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 1);
                addButtonOfCurrentCardView(1);
                Toast.makeText(getApplicationContext(), "1 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 2);
                addButtonOfCurrentCardView(2);
                Toast.makeText(getApplicationContext(), "2 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 3);
                addButtonOfCurrentCardView(3);
                Toast.makeText(getApplicationContext(), "3 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 4);
                addButtonOfCurrentCardView(4);
                Toast.makeText(getApplicationContext(), "4 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 5);
                addButtonOfCurrentCardView(5);
                Toast.makeText(getApplicationContext(), "5 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 6);
                addButtonOfCurrentCardView(6);
                Toast.makeText(getApplicationContext(), "6 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 7);
                addButtonOfCurrentCardView(7);
                Toast.makeText(getApplicationContext(), "7 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 8);
                addButtonOfCurrentCardView(8);
                Toast.makeText(getApplicationContext(), "8 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 9);
                addButtonOfCurrentCardView(9);
                Toast.makeText(getApplicationContext(), "9 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 10);
                addButtonOfCurrentCardView(10);
                Toast.makeText(getApplicationContext(), "10 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 11);
                addButtonOfCurrentCardView(11);
                Toast.makeText(getApplicationContext(), "11 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 12);
                addButtonOfCurrentCardView(12);
                Toast.makeText(getApplicationContext(), "12 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
        cards[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rummi.addCard(currentColor, 13);
                addButtonOfCurrentCardView(13);
                Toast.makeText(getApplicationContext(), "13 Card Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void addButtonOfCurrentCardView(final int number){
        if(currField == FIELD_COMMON){
            lineManagerOfMainActivity.addCommonLine(currentColor, number, CommonCard, this, rummi);
        }
        else if(currField == FIELD_MY){
            lineManagerOfMainActivity.addMyLine(currentColor, number,this, rummi);
        }
    }
}
















