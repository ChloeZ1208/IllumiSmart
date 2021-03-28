package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question5Activity extends AppCompatActivity implements View.OnClickListener {
    private MaterialToolbar topAppBar;
    private MaterialCardView question5A;
    private MaterialCardView question5B;
    private MaterialCardView question5Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        MaterialCardView question5A = findViewById(R.id.question5_a);
        MaterialCardView question5B = findViewById(R.id.question5_b);
        MaterialCardView question5Back = findViewById(R.id.question5_back);
        MaterialToolbar topAppBar = findViewById(R.id.question5_toolbar);

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
        if(id == R.id.question5_a) {
            startActivity(new Intent(Question5Activity.this, Question6Activity.class));
        } else if(id == R.id.question5_b) {
            startActivity(new Intent(Question5Activity.this, Question7Activity.class));
        } else if(id == R.id.question5_back) {
            onBackPressed();
        }
    }
}