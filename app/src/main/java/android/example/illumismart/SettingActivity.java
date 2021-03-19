package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private MaterialCardView profileCard;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bottomNav = findViewById(R.id.bottom_navigation_setting);
        bottomNav.getMenu().findItem(R.id.setting_page).setChecked(true);

        profileCard = findViewById(R.id.setting_profile_card);

        profileCard.setOnClickListener(this);

        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.home_page) {
                            startActivity(new Intent(SettingActivity.this,
                                    HomeActivity.class));
                        } else if (itemId == R.id.info_page) {// TODO: info
                        } else if (itemId == R.id.data_page) {
                            startActivity(new Intent(SettingActivity.this,
                                    DataActivity.class));
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.setting_profile_card) {
            startActivity(new Intent(SettingActivity.this, ProfileActivity.class));
        }
    }
}