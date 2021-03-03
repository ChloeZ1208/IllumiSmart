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
    private BottomNavigationView bottomNav;
    private MaterialCardView enterLightLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav = findViewById(R.id.bottom_navigation_home);
        enterLightLevel = findViewById(R.id.enter_light_level);

        bottomNav.getMenu().findItem(R.id.home_page).setChecked(true);

        enterLightLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LightLevelActivity.class));
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
                        break;
                }
                return false;
            }
        });


    }
}