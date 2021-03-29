package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Question1Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int QUESTION_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        MaterialCardView question1A = findViewById(R.id.question1_a);
        MaterialCardView question1B = findViewById(R.id.question1_b);
        MaterialToolbar topAppBar = findViewById(R.id.question1_toolbar);

        question1A.setOnClickListener(this);
        question1B.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question1Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        ArrayList<String> arr = new ArrayList<String>();
        if(id == R.id.question1_a) {
            intent = new Intent(Question1Activity.this, Question2Activity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID, arr, null)
            );
        } else if(id == R.id.question1_b) {
            intent = new Intent(Question1Activity.this,
                    QuestionFinishActivity.class);
            intent.putExtra(
                    getString(R.string.self_assessment_extra_name),
                    new SelfAssessmentExtra(QUESTION_ID, arr, null)
            );
        }
        startActivity(intent);
    }


}