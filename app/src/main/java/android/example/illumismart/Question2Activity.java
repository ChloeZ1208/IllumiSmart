package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 2;
    private SelfAssessmentExtra selfAssessmentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        MaterialCardView question2A = findViewById(R.id.question2_a);
        MaterialCardView question2B = findViewById(R.id.question2_b);
        MaterialCardView question2Back = findViewById(R.id.question2_back);
        MaterialToolbar topAppBar = findViewById(R.id.question2_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );

        question2A.setOnClickListener(this);
        question2B.setOnClickListener(this);
        question2Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question2Activity.this,
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
        if(id == R.id.question2_a) {
            arr.add("question2suggest_a_keyword");
            Intent intent = new Intent(Question2Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question2suggest_a_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question2_b) {
            Intent intent = new Intent(Question2Activity.this,
                    Question3Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question2_back) {
            onBackPressed();
        }
    }
}