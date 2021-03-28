package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Question10Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question10);

        MaterialCardView question10A = findViewById(R.id.question10_a);
        MaterialCardView question10B = findViewById(R.id.question10_b);
        MaterialCardView question10Back = findViewById(R.id.question10_back);
        MaterialToolbar topAppBar = findViewById(R.id.question10_toolbar);

        question10A.setOnClickListener(this);
        question10B.setOnClickListener(this);
        question10Back.setOnClickListener(this);

        // Quit self assessment
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.quit) {
                startActivity(new Intent(Question10Activity.this,
                        HomeActivity.class));
                return true;
            }
            return false;
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.question10_a) {
            //TODO: finish
        } else if(id == R.id.question10_b) {
            //TODO: goto suggestion cleaning b
        } else if(id == R.id.question10_back) {
            onBackPressed();
        }
    }

}