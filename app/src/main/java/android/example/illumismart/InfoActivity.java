package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        MaterialCardView infoCard1 = findViewById(R.id.info_card1);
        MaterialCardView infoCard2 = findViewById(R.id.info_card2);
        MaterialCardView infoCard3 = findViewById(R.id.info_card3);
        MaterialCardView infoCard4 = findViewById(R.id.info_card4);
        infoCard1.setOnClickListener(this);
        infoCard2.setOnClickListener(this);
        infoCard3.setOnClickListener(this);
        infoCard4.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.info_card1) {
            openURL("https://www.ccohs.ca/oshanswers/ergonomics/lighting_general.html");
        } else if (id == R.id.info_card2) {
            openURL("https://www.ccohs.ca/oshanswers/ergonomics/lighting_survey.html");
        } else if (id == R.id.info_card3) {
            openURL("https://www.ccohs.ca/oshanswers/ergonomics/lighting_flicker.html");
        } else if (id == R.id.info_card4) {
        }
    }

    public void openURL(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        //int coolorInt = Color.parseColor("#7AB5A5"); //green
        CustomTabColorSchemeParams params = new CustomTabColorSchemeParams.Builder()
                .setNavigationBarColor(getResources().getColor(R.color.white, null))
                .setToolbarColor(getResources().getColor(R.color.app_green, null))
                .build();
        builder.setDefaultColorSchemeParams(params);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}