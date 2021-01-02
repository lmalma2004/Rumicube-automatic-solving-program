package com.rummysolver.engine;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private LinearLayout allOfResultCardsOfParent;
    private TableLayout allOfResultCards;
    private LottieAnimationView animationOfResult;

    private ArrayList<RummiKubSolve.CardGroup> result;
    private LineManagerOfResultActivity lineManagerOfResultActivity = new LineManagerOfResultActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        allOfResultCardsOfParent = (LinearLayout) findViewById(R.id.ResultCardLayoutOfParent);

        animationOfResult = new LottieAnimationView(this);
        animationOfResult.setLayoutParams(new LinearLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));
        animationOfResult.setAnimation("correct.json");

        allOfResultCardsOfParent.addView(animationOfResult);
        animationOfResult.playAnimation();
        animationOfResult.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //애니메이션 뷰를 지워줘야 정답 뷰 가 보임
                allOfResultCardsOfParent.removeView(animationOfResult);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        allOfResultCards = new TableLayout(this);
        allOfResultCards.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        allOfResultCardsOfParent.addView(allOfResultCards);

        Intent intent = getIntent();
        result = intent.getParcelableArrayListExtra("Result");

        if (result != null) {
            for (int i = 0; i < result.size(); i++)
                lineManagerOfResultActivity.addResultLine(result.get(i), allOfResultCards, this);
        } else {
            lineManagerOfResultActivity.addFailLine(allOfResultCards, this);
        }
    }
}
