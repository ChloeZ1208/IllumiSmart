package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question4Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 4;
    private SelfAssessmentExtra selfAssessmentExtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        MaterialCardView question4A = findViewById(R.id.question4_a);
        MaterialCardView question4B = findViewById(R.id.question4_b);
        MaterialCardView question4Back = findViewById(R.id.question4_back);
        MaterialToolbar topAppBar = findViewById(R.id.question4_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );
        selfAssessmentExtra.setQuestionId(QUESTION_ID);

        question4A.setOnClickListener(this);
        question4B.setOnClickListener(this);
        question4Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question4Activity.this,
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
        if(id == R.id.question4_a) {
            Intent intent = new Intent(Question4Activity.this,
                    Question5Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question4_b) {
            arr.add("question_4_suggest_b_keyword");
            Intent intent = new Intent(Question4Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question_4_suggest_b_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question4_back) {
            Utils utils = new Utils();
            Intent intent = new Intent(Question4Activity.this,
                    Question3Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    utils.getOnBackPressedExtra(selfAssessmentExtra)
            );
            startActivity(intent);
        }
    }
}