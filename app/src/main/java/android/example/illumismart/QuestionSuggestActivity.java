package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class QuestionSuggestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_suggest);

        MaterialToolbar topAppBar = findViewById(R.id.question_suggest_toolbar);
        MaterialCardView questionNext = findViewById(R.id.question_next);
        TextView questionSuggestText = findViewById(R.id.question_suggest_txt);
        Utils utils = new Utils();

        // Get Extra
        SelfAssessmentExtra selfAssessmentExtra = (SelfAssessmentExtra) getIntent().
                getSerializableExtra(
                getString(R.string.self_assessment_extra_name)
        );

        // Set Suggestions
        questionSuggestText.
                setText(utils.getSuggestStringId(selfAssessmentExtra.getSuggestionId()));

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(QuestionSuggestActivity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });

        // Go to Next Question...
        questionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extra prepare
                SelfAssessmentExtra extra = new SelfAssessmentExtra(
                        selfAssessmentExtra.getQuestionId(),
                        selfAssessmentExtra.getIssues(),
                        null);
                // Start
                routeToNextQuestion(extra);
            }
        });


    }

    private void routeToNextQuestion(SelfAssessmentExtra extra) {
        int qid = extra.getQuestionId();
        Intent intent = null;
        if(qid == 1) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question2Activity.class);
        } else if (qid == 2) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question3Activity.class);
        } else if (qid == 3) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question4Activity.class);
        } else if (qid == 4) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question5Activity.class);
        } else if (qid == 5) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question6Activity.class);
        } else if (qid == 6) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question7Activity.class);
        } else if (qid == 7) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question8Activity.class);
        } else if (qid == 8) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question9Activity.class);
        } else if (qid == 9) {
            intent = new Intent(QuestionSuggestActivity.this,
                    Question10Activity.class);
        } else if (qid == 10) {
            intent = new Intent(QuestionSuggestActivity.this,
                    QuestionFinishActivity.class);
        }
        String extraName = getString(R.string.self_assessment_extra_name);
        intent.putExtra(extraName, extra);
        startActivity(intent);
    }
}