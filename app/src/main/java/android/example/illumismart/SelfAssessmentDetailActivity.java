package android.example.illumismart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelfAssessmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_assessment_detail);
        View v = findViewById(R.id.self_assessment_detail_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#7AB5A5"));

        Utils utils = new Utils();
        TextView selfAssessmentDetailHeader = findViewById(R.id.self_assessment_detail_header);
        TextView selfAssessmentDetailText = findViewById(R.id.self_assessment_detail_txt);
        String keyword = getIntent().getStringExtra("keyword");
        selfAssessmentDetailHeader.setText(utils.getKeywordStringId(keyword));
        selfAssessmentDetailText.setText(utils.getSuggestStringId(keyword));
    }
}
