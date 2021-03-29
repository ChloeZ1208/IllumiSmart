package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question10Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 10;
    private SelfAssessmentExtra selfAssessmentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question10);

        MaterialCardView question10A = findViewById(R.id.question10_a);
        MaterialCardView question10B = findViewById(R.id.question10_b);
        MaterialCardView question10Back = findViewById(R.id.question10_back);
        MaterialToolbar topAppBar = findViewById(R.id.question10_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );

        question10A.setOnClickListener(this);
        question10B.setOnClickListener(this);
        question10Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question10Activity.this,
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
        if(id == R.id.question10_a) {
            Intent intent = new Intent(Question10Activity.this,
                    QuestionFinishActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question10_b) {
            arr.add("question10suggest_b_keyword");
            Intent intent = new Intent(Question10Activity.this,
                    QuestionSuggestActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, "question10suggest_b_keyword")
            );
            startActivity(intent);
        } else if(id == R.id.question10_back) {
            onBackPressed();
        }
    }

}