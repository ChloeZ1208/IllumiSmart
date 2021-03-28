package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question4Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        MaterialCardView question4A = findViewById(R.id.question4_a);
        MaterialCardView question4B = findViewById(R.id.question4_b);
        MaterialCardView question4Back = findViewById(R.id.question4_back);
        MaterialToolbar topAppBar = findViewById(R.id.question4_toolbar);

        question4A.setOnClickListener(this);
        question4B.setOnClickListener(this);
        question4Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question4Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.question4_a) {
            startActivity(new Intent(Question4Activity.this, Question5Activity.class));
        } else if(id == R.id.question4_b) {
            //TODO: goto suggestion curtains
        } else if(id == R.id.question4_back) {
            onBackPressed();
        }
    }
}