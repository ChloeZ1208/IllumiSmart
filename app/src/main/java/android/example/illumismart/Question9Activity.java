package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question9Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 9;
    private SelfAssessmentExtra selfAssessmentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question9);

        MaterialCardView question9A = findViewById(R.id.question9_a);
        MaterialCardView question9B = findViewById(R.id.question9_b);
        MaterialCardView question9C = findViewById(R.id.question9_c);
        MaterialCardView question9Back = findViewById(R.id.question9_back);
        MaterialToolbar topAppBar = findViewById(R.id.question9_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );

        question9A.setOnClickListener(this);
        question9B.setOnClickListener(this);
        question9C.setOnClickListener(this);
        question9Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question9Activity.this,
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
        if(id == R.id.question9_a) {
            arr.add("question9suggest_a_keyword");
            Intent intent = new Intent(Question9Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question9suggest_a_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question9_b) {
            arr.add("question9suggest_b_keyword");
            Intent intent = new Intent(Question9Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question9suggest_b_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question9_c){
            arr.add("question9suggest_c_keyword");
            Intent intent = new Intent(Question9Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question9suggest_c_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question9_back) {
            onBackPressed();
        }
    }

}