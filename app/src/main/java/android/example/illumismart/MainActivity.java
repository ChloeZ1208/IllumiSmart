package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.service.autofill.UserData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        if(isFirstTime()) {
            startActivity(new Intent(MainActivity.this, ProfileOccupationActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }
        finish();
    }
    private boolean isFirstTime() {
        SharedPreferences preferences = getSharedPreferences("Profile",MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.apply();
        }
        return !ranBefore;
    }
}