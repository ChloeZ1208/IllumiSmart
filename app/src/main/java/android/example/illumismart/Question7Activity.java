package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question7Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question7);

        MaterialCardView question7A = findViewById(R.id.question7_a);
        MaterialCardView question7B = findViewById(R.id.question7_b);
        MaterialCardView question7Back = findViewById(R.id.question7_back);
        MaterialToolbar topAppBar = findViewById(R.id.question7_toolbar);

        question7A.setOnClickListener(this);
        question7B.setOnClickListener(this);
        question7Back.setOnClickListener(this);
        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question7Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.question7_a) {
            //TODO: goto suggestion paper a
        } else if(id == R.id.question7_b) {
            startActivity(new Intent(Question7Activity.this, Question8Activity.class));
        } else if(id == R.id.question7_back) {
            onBackPressed();
        }
    }
}