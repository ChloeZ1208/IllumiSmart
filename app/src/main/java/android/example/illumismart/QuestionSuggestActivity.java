package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class QuestionSuggestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_suggest);

        MaterialToolbar topAppBar = findViewById(R.id.question_suggest_toolbar);
        MaterialCardView questionNext = findViewById(R.id.question_next);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(QuestionSuggestActivity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });

        questionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:start next activity
            }
        });


    }
}