package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question5Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int QUESTION_ID = 5;
    private SelfAssessmentExtra selfAssessmentExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        MaterialCardView question5A = findViewById(R.id.question5_a);
        MaterialCardView question5B = findViewById(R.id.question5_b);
        MaterialCardView question5Back = findViewById(R.id.question5_back);
        MaterialToolbar topAppBar = findViewById(R.id.question5_toolbar);

        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                        getString(R.string.self_assessment_extra_name)
                );
        selfAssessmentExtra.setQuestionId(QUESTION_ID);

        question5A.setOnClickListener(this);
        question5B.setOnClickListener(this);
        question5Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question5Activity.this,
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
        if(id == R.id.question5_a) {
            Intent intent = new Intent(Question5Activity.this,
                    Question6Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question5_b) {
            Intent intent = new Intent(Question5Activity.this,
                    Question7Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID,
                            arr, null)
            );
            startActivity(intent);
        } else if(id == R.id.question5_back) {
            Utils utils = new Utils();
            Intent intent = new Intent(Question5Activity.this,
                    Question4Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    utils.getOnBackPressedExtra(selfAssessmentExtra)
            );
            startActivity(intent);
        }
    }
}