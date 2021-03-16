package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class ProfileOccupationActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialCardView generalCard;
    private MaterialCardView moderateCard;
    private MaterialCardView preciseCard;
    private MaterialCardView fineCard;
    private MaterialCardView veryFineCard;

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

        preferences = getSharedPreferences("ProfileInfo",MODE_PRIVATE);

        generalCard.setOnClickListener(this);
        moderateCard.setOnClickListener(this);
        preciseCard.setOnClickListener(this);
        fineCard.setOnClickListener(this);
        veryFineCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        editor = preferences.edit();
        if (id == R.id.occupation_general_btn) {
            editor.putString("Occupation", "GeneralWork");
        } else if (id == R.id.occupation_moderate_btn) {
            editor.putString("Occupation", "ModerateWork");
        } else if (id == R.id.age_5065_btn) {
            editor.putString("Occupation", "PreciseWork");
        } else if (id == R.id.age_above65_btn) {
            editor.putString("Occupation", "FineWork");
        } else if (id == R.id.occupation_veryfine_btn) {
            editor.putString("Occupation", "VeryFineWork");
        }
        editor.apply();
        startActivity(new Intent(ProfileOccupationActivity.this, ProfileAgeActivity.class));
    }
}