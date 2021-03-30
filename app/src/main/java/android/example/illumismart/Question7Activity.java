package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question7Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 7;
    private SelfAssessmentExtra selfAssessmentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question7);

        MaterialCardView question7A = findViewById(R.id.question7_a);
        MaterialCardView question7B = findViewById(R.id.question7_b);
        MaterialCardView question7Back = findViewById(R.id.question7_back);
        MaterialToolbar topAppBar = findViewById(R.id.question7_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );
        selfAssessmentExtra.setQuestionId(QUESTION_ID);

        question7A.setOnClickListener(this);
        question7B.setOnClickListener(this);
        question7Back.setOnClickListener(this);
        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question7Activity.this,
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
        if(id == R.id.question7_a) {
            arr.add("question_7_suggest_a_keyword");
            Intent intent = new Intent(Question7Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question_7_suggest_a_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question7_b) {
            Intent intent = new Intent(Question7Activity.this,
                    Question8Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question7_back) {
            Utils utils = new Utils();
            Intent intent = new Intent(Question7Activity.this,
                    Question6Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    utils.getOnBackPressedExtra(selfAssessmentExtra)
            );
            startActivity(intent);
        }
    }
}