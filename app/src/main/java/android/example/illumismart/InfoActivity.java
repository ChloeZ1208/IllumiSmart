package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        MaterialCardView infoCard1 = findViewById(R.id.info_card1);
        MaterialCardView infoCard2 = findViewById(R.id.info_card2);
        MaterialCardView infoCard3 = findViewById(R.id.info_card3);
        MaterialCardView infoCard4 = findViewById(R.id.info_card4);

        infoCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ccohs.ca/oshanswers/ergonomics/lighting_flicker.html"));
                startActivity(browserIntent);
            }
        });


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