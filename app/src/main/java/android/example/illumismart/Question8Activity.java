package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question8Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 8;
    private SelfAssessmentExtra selfAssessmentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question8);

        MaterialCardView question8A = findViewById(R.id.question8_a);
        MaterialCardView question8B = findViewById(R.id.question8_b);
        MaterialCardView question8Back = findViewById(R.id.question8_back);
        MaterialToolbar topAppBar = findViewById(R.id.question8_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );

        question8A.setOnClickListener(this);
        question8B.setOnClickListener(this);
        question8Back.setOnClickListener(this);
        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question8Activity.this,
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
        if(id == R.id.question8_a) {
            arr.add("question8suggest_a_keyword");
            Intent intent = new Intent(Question8Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question8suggest_a_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question8_b) {
            Intent intent = new Intent(Question8Activity.this,
                    Question9Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question8_back) {
            onBackPressed();
        }
    }
}