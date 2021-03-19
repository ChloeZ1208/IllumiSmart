package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[]{Manifest.permission.CAMERA}, 203);
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_home);
        MaterialCardView enterLightLevel = findViewById(R.id.home_light_level);
        MaterialCardView enterLightFlicker = findViewById(R.id.home_light_flicker);
        MaterialCardView enterLightGlare = findViewById(R.id.home_light_glare);

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

        enterLightGlare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,
                        GlareActivity.class));
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.data_page) {
                    startActivity(new Intent(HomeActivity.this, DataActivity.class));
                } else if (itemId == R.id.info_page) {// TODO: info
                } else if (itemId == R.id.setting_page) {
                    startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                }
                return false;
            }
        });


    }
}