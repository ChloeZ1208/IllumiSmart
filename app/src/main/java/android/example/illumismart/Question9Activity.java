package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question9Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question9);

        MaterialCardView question9A = findViewById(R.id.question9_a);
        MaterialCardView question9B = findViewById(R.id.question9_b);
        MaterialCardView question9C = findViewById(R.id.question9_c);
        MaterialCardView question9Back = findViewById(R.id.question9_back);
        MaterialToolbar topAppBar = findViewById(R.id.question9_toolbar);

        question9A.setOnClickListener(this);
        question9B.setOnClickListener(this);
        question9C.setOnClickListener(this);
        question9Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question9Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.question9_a) {
            //TODO: goto suggestion light bulb a
        } else if(id == R.id.question9_b) {
            //TODO: goto suggestion light bulb b
        } else if(id == R.id.question9_c){
            //TODO: goto suggestion light bulb c
        } else if(id == R.id.question9_back) {
            onBackPressed();
        }
    }

}