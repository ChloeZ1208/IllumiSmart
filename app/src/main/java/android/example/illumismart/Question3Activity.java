package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question3Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        MaterialCardView question3A = findViewById(R.id.question3_a);
        MaterialCardView question3B = findViewById(R.id.question3_b);
        MaterialCardView question3Back = findViewById(R.id.question3_back);
        MaterialToolbar topAppBar = findViewById(R.id.question3_toolbar);

        question3A.setOnClickListener(this);
        question3B.setOnClickListener(this);
        question3Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question3Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.question3_a) {
            startActivity(new Intent(Question3Activity.this, Question4Activity.class));
        } else if(id == R.id.question3_b) {
            startActivity(new Intent(Question3Activity.this, Question5Activity.class));
        } else if(id == R.id.question3_back) {
            onBackPressed();
        }
    }
}