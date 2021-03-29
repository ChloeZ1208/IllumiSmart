package android.example.illumismart;

import android.content.Intent;
import android.example.illumismart.entity.SelfAssessmentItem;
import android.example.illumismart.viewmodel.SelfAssessmentItemViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class SelfAssessmentRecordActivity extends AppCompatActivity {
    private Utils utils;
    private SelfAssessmentItemViewModel selfAssessmentItemViewModel;
    private dataItemViewModel dataItemViewModel;

    private String timeStamp;
    private TextView selfAssessmentTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_assessment_record);
        View v = findViewById(R.id.self_assessment_record_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#E5E5E5"));
        utils = new Utils();

        selfAssessmentTimestamp = findViewById(R.id.self_assessment_record_time);

        MaterialToolbar topAppBar = findViewById(R.id.self_assessment_record_app_bar);

        selfAssessmentItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(SelfAssessmentItemViewModel.class);
        dataItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(dataItemViewModel.class);

        Intent intent = getIntent();
        timeStamp = intent.getStringExtra("timestamp");

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.delete_item) {
                deleteRecord();
                return true;
            }
            return false;
        });

        setRecord();
    }

    private void setRecord() {
        selfAssessmentItemViewModel.getSelfAssessmentItem(timeStamp)
                .observe(this, new Observer<SelfAssessmentItem>() {
                    @Override
                    public void onChanged(SelfAssessmentItem item) {
                        if (item != null) {
                            selfAssessmentTimestamp.setText(utils.getParsedTimestamp(timeStamp));
                            int [] cardViewId = {
                                    R.id.self_assessment_record_issue_1,
                                    R.id.self_assessment_record_issue_2,
                                    R.id.self_assessment_record_issue_3,
                                    R.id.self_assessment_record_issue_4,
                                    R.id.self_assessment_record_issue_5,
                                    R.id.self_assessment_record_issue_6,
                                    R.id.self_assessment_record_issue_7
                            };
                            int [] textViewId = {
                                    R.id.self_assessment_record_issue_text_1,
                                    R.id.self_assessment_record_issue_text_2,
                                    R.id.self_assessment_record_issue_text_3,
                                    R.id.self_assessment_record_issue_text_4,
                                    R.id.self_assessment_record_issue_text_5,
                                    R.id.self_assessment_record_issue_text_6,
                                    R.id.self_assessment_record_issue_text_7
                            };
                            String [] issuesArr = utils.deserializeSelfAssessmentIssues(
                                    item.getIssues()
                            );
                            for (int index = 0; index < issuesArr.length; index++) {
                                // set maximum of issues
                                if (index == 7) {
                                    break;
                                }
                                MaterialCardView cardView = findViewById(cardViewId[index]);
                                TextView textView = findViewById(textViewId[index]);
                                cardView.setVisibility(View.VISIBLE);
                                String keyword = issuesArr[index];
                                textView.setText(utils.getKeywordStringId(keyword));
                                cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(
                                                SelfAssessmentRecordActivity.this,
                                                SelfAssessmentDetailActivity.class);
                                        intent.putExtra("keyword", keyword);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }
                });
    }

    private void deleteRecord() {
        selfAssessmentItemViewModel.delete(timeStamp);
        dataItemViewModel.delete(timeStamp);
        onBackPressed();
    }
}
