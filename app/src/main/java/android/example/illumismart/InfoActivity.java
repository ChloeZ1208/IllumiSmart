package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_info);

        bottomNav.getMenu().findItem(R.id.info_page).setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.data_page) {
                    startActivity(new Intent(InfoActivity.this, DataActivity.class));
                } else if (itemId == R.id.home_page) {
                    startActivity(new Intent(InfoActivity.this, HomeActivity.class));
                } else if (itemId == R.id.setting_page) {
                    startActivity(new Intent(InfoActivity.this, SettingActivity.class));
                }
                return false;
            }
        });
    }
}