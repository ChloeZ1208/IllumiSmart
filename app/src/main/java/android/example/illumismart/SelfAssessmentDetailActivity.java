package android.example.illumismart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class SelfAssessmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_assessment_detail);

        Utils utils = new Utils();
        TextView selfAssessmentDetailHeader = findViewById(R.id.self_assessment_detail_header);
        TextView selfAssessmentDetailText = findViewById(R.id.self_assessment_detail_txt);
        String keyword = getIntent().getStringExtra("keyword");
        selfAssessmentDetailHeader.setText(utils.getKeywordStringId(keyword));
        selfAssessmentDetailText.setText(utils.getSuggestStringId(keyword));

        MaterialToolbar topAppBar = findViewById(R.id.self_assessment_detail_app_bar);
        // Set navigation back
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
