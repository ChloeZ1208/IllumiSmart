package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.example.illumismart.entity.SelfAssessmentItem;
import android.example.illumismart.entity.dataItem;
import android.example.illumismart.viewmodel.SelfAssessmentItemViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class QuestionFinishActivity extends AppCompatActivity {

    private static final String ITEM_NAME = "Self Assessment";
    private Button selfAssessmentSaveButton;
    private SelfAssessmentExtra selfAssessmentExtra;
    private ArrayList<String> issues;
    private Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_finish);

        View v = findViewById(R.id.question_finish_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#7AB5A5"));

        MaterialToolbar topAppBar = findViewById(R.id.question_finish_toolbar);
        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(QuestionFinishActivity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
        utils = new Utils();
        selfAssessmentSaveButton = findViewById(R.id.save_self_assessment);
        selfAssessmentExtra = (SelfAssessmentExtra) getIntent().getSerializableExtra(
                getString(R.string.self_assessment_extra_name)
        );
        issues = selfAssessmentExtra.getIssues();

        // Workaround for q1 suggestion: Unsupport type of career
        if (selfAssessmentExtra.getQuestionId() == 1) {
            TextView selfAssessmentFinishedText = findViewById(R.id.question_finish_txt);
            selfAssessmentFinishedText.setText(R.string.question1suggest);
            selfAssessmentSaveButton.setVisibility(View.INVISIBLE);
        }

        // Self Assessment
        SelfAssessmentItemViewModel selfAssessmentItemViewModel =
                new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(SelfAssessmentItemViewModel.class);
        // DataItem(Self Assessment) save
        dataItemViewModel dataItemViewModel = new ViewModelProvider(this,
                ViewModelProvider.
                        AndroidViewModelFactory.
                        getInstance(this.getApplication())).get(dataItemViewModel.class);

        selfAssessmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (issues.size() == 0) {
                    Toast.makeText(QuestionFinishActivity.this,
                            "No assessment data to save.", Toast.LENGTH_SHORT).show();
                } else {
                    String timestamp = utils.getSpecifiedTimestamp();
                    dataItem dataItemEntityInstance = new dataItem(timestamp, ITEM_NAME);
                    SelfAssessmentItem selfAssessmentEntityInstance = new SelfAssessmentItem(timestamp,
                            utils.serializeSelfAssessmentIssues(selfAssessmentExtra.getIssues()));
                    selfAssessmentItemViewModel.insert(selfAssessmentEntityInstance);
                    dataItemViewModel.insert(dataItemEntityInstance);
                    issues.clear();
                    Toast.makeText(QuestionFinishActivity.this,
                            "Self assessment data saved!.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}