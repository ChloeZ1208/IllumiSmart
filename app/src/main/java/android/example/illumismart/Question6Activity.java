package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question6Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question6);

        MaterialCardView question6A = findViewById(R.id.question6_a);
        MaterialCardView question6B = findViewById(R.id.question6_b);
        MaterialCardView question6C = findViewById(R.id.question6_c);
        MaterialCardView question6D = findViewById(R.id.question6_d);
        MaterialCardView question6Back = findViewById(R.id.question6_back);
        MaterialToolbar topAppBar = findViewById(R.id.question6_toolbar);

        question6A.setOnClickListener(this);
        question6B.setOnClickListener(this);
        question6C.setOnClickListener(this);
        question6D.setOnClickListener(this);
        question6Back.setOnClickListener(this);
        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question6Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.question6_a) {
            startActivity(new Intent(Question6Activity.this, Question7Activity.class));
        } else if(id == R.id.question6_b) {
            //TODO: goto suggestion monitor b
        } else if(id == R.id.question6_c) {
            //TODO: goto suggestion monitor c
        } else if(id == R.id.question6_d) {
            startActivity(new Intent(Question6Activity.this, Question7Activity.class));
        } else if(id == R.id.question6_back) {
            onBackPressed();
        }
    }

}