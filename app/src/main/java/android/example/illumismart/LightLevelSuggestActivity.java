package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class LightLevelSuggestActivity extends AppCompatActivity {
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level_suggest);
        View v = findViewById(R.id.light_level_suggest_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#FAFAFA"));

        topAppBar = findViewById(R.id.lux_sugg_top_app_bar);

        // Set navigation back
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Set user guidance
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.nav_guide) {
                startActivity(new Intent(LightLevelSuggestActivity.this,
                        LightLevelGuideActivity.class));
                return true;
            }
            return false;
        });


    }
}