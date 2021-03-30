package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question6Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 6;
    private SelfAssessmentExtra selfAssessmentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question6);

        MaterialCardView question6A = findViewById(R.id.question6_a);
        MaterialCardView question6B = findViewById(R.id.question6_b);
        MaterialCardView question6C = findViewById(R.id.question6_c);
        MaterialCardView question6D = findViewById(R.id.question6_d);
        MaterialCardView question6Back = findViewById(R.id.question6_back);
        MaterialToolbar topAppBar = findViewById(R.id.question6_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );
        selfAssessmentExtra.setQuestionId(QUESTION_ID);

        question6A.setOnClickListener(this);
        question6B.setOnClickListener(this);
        question6C.setOnClickListener(this);
        question6D.setOnClickListener(this);
        question6Back.setOnClickListener(this);
        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question6Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        ArrayList<String> arr = selfAssessmentExtra.getIssues();
        if(id == R.id.question6_a) {
            Intent intent = new Intent(Question6Activity.this,
                    Question7Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question6_b) {
            arr.add("question_6_suggest_b_keyword");
            Intent intent = new Intent(Question6Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question_6_suggest_b_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question6_c) {
            arr.add("question_6_suggest_c_keyword");
            Intent intent = new Intent(Question6Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question_6_suggest_c_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question6_d) {
            Intent intent = new Intent(Question6Activity.this,
                    Question7Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question6_back) {
            Utils utils = new Utils();
            Intent intent = new Intent(Question6Activity.this,
                    Question5Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    utils.getOnBackPressedExtra(selfAssessmentExtra)
            );
            startActivity(intent);
        }
    }

}