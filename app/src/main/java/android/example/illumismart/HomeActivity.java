package android.example.illumismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this,
                        PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.d(LOG_TAG,permissions[i] + "：" + grantResults[i]);
            }
        }
    }
}