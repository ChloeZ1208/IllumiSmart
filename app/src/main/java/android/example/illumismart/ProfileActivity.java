package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class ProfileActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;
    private MaterialCardView occupationCard;
    private MaterialCardView ageCard;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        View v = findViewById(R.id.profile_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#E5E5E5"));

        topAppBar = findViewById(R.id.profile_app_bar);
        occupationCard = findViewById(R.id.profile_occupation_card);
        ageCard = findViewById(R.id.profile_age_card);

        setNavigationBar();

        occupationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileOccupationActivity.class));
            }
        });

        ageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileAgeActivity.class));
            }
        });

    }

    private void setNavigationBar() {
        // Set navigation back
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SettingActivity.class));;
            }
        });

        /*
         * if user profile finished, display delete menu
         */
        preferences = getSharedPreferences("ProfileInfo",MODE_PRIVATE);
        String occupation = preferences.getString("Occupation", null);
        String age = preferences.getString("Age", null);
        if (occupation != null && age != null) {
            topAppBar.inflateMenu(R.menu.delete_menu);
            topAppBar.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.delete_item) {
                    // clear SharedPreference "Profile"
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.clear();
                    edit.apply();
                    topAppBar.getMenu().clear();
                }
                return false;
            });
        }
    }
}