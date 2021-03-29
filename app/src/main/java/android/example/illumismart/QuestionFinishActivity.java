package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class QuestionFinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_finish);

        View v = findViewById(R.id.question_finish_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#7AB5A5"));

    }
}