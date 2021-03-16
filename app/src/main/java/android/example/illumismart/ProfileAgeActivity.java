package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class ProfileAgeActivity extends AppCompatActivity implements View.OnClickListener{
    private MaterialCardView ageBelow40Card;
    private MaterialCardView age4050Card;
    private MaterialCardView age5065Card;
    private MaterialCardView ageAbove65Card;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_age);

        ageBelow40Card = findViewById(R.id.age_below40_btn);
        age4050Card = findViewById(R.id.age_4050_btn);
        age5065Card = findViewById(R.id.age_5065_btn);
        ageAbove65Card = findViewById(R.id.age_above65_btn);

        preferences = getSharedPreferences("ProfileInfo",MODE_PRIVATE);

        ageBelow40Card.setOnClickListener(this);
        age4050Card.setOnClickListener(this);
        age5065Card.setOnClickListener(this);
        ageAbove65Card.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        editor = preferences.edit();
        if (id == R.id.age_below40_btn) {
            editor.putString("Age", "below40");
        } else if (id == R.id.age_4050_btn) {
            editor.putString("Age", "4050");
        } else if (id == R.id.age_5065_btn) {
            editor.putString("Age", "5065");
        } else if (id == R.id.age_above65_btn) {
            editor.putString("Age", "above65");
        }
        editor.apply();
        startActivity(new Intent(ProfileAgeActivity.this, ProfileActivity.class));
    }
}