package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class ProfileOccupationActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialCardView generalCard;
    private MaterialCardView moderateCard;
    private MaterialCardView preciseCard;
    private MaterialCardView fineCard;
    private MaterialCardView veryFineCard;
    private Button skipHome;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_occupation);

        generalCard = findViewById(R.id.occupation_general_btn);
        moderateCard = findViewById(R.id.occupation_moderate_btn);
        preciseCard = findViewById(R.id.age_5065_btn);
        fineCard = findViewById(R.id.age_above65_btn);
        veryFineCard = findViewById(R.id.occupation_veryfine_btn);
        skipHome = findViewById(R.id.occupation_skip);

        preferences = getSharedPreferences("ProfileInfo",MODE_PRIVATE);

        generalCard.setOnClickListener(this);
        moderateCard.setOnClickListener(this);
        preciseCard.setOnClickListener(this);
        fineCard.setOnClickListener(this);
        veryFineCard.setOnClickListener(this);
        skipHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        editor = preferences.edit();
        if (id == R.id.occupation_general_btn) {
            editor.putString("Occupation", "General Work");
        } else if (id == R.id.occupation_moderate_btn) {
            editor.putString("Occupation", "Moderate Work");
        } else if (id == R.id.age_5065_btn) {
            editor.putString("Occupation", "Precise Work");
        } else if (id == R.id.age_above65_btn) {
            editor.putString("Occupation", "Fine Work");
        } else if (id == R.id.occupation_veryfine_btn) {
            editor.putString("Occupation", "Very Fine to Precise Work");
        }
        editor.apply();
        startActivity(new Intent(ProfileOccupationActivity.this, ProfileActivity.class));

        if (id == R.id.occupation_skip) {
            startActivity(new Intent(ProfileOccupationActivity.this, HomeActivity.class));
        }
    }
}