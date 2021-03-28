package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question8Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question8);

        MaterialCardView question8A = findViewById(R.id.question8_a);
        MaterialCardView question8B = findViewById(R.id.question8_b);
        MaterialCardView question8Back = findViewById(R.id.question8_back);
        MaterialToolbar topAppBar = findViewById(R.id.question8_toolbar);

        question8A.setOnClickListener(this);
        question8B.setOnClickListener(this);
        question8Back.setOnClickListener(this);
        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question8Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.question8_a) {
            //TODO: goto suggestion vision a
        } else if(id == R.id.question8_b) {
            startActivity(new Intent(Question8Activity.this, Question9Activity.class));
        } else if(id == R.id.question8_back) {
            onBackPressed();
        }
    }
}