package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_home);
        MaterialCardView enterLightLevel = findViewById(R.id.home_light_level);
        MaterialCardView enterLightFlicker = findViewById(R.id.home_light_flicker);

        bottomNav.getMenu().findItem(R.id.home_page).setChecked(true);

        enterLightLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,
                        LightLevelActivity.class));
            }
        });

        enterLightFlicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,
                        FlickerActivity.class));
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.data_page:
                        startActivity(new Intent(HomeActivity.this, DataActivity.class));
                        break;
                    case R.id.info_page:
                        // TODO: info
                        break;
                    case R.id.setting_page:
                        // TODO: profile
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        break;
                }
                return false;
            }
        });


    }
}