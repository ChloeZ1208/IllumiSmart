package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class FlickerSuggestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker_suggest);
        View v = findViewById(R.id.flicker_suggest_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#FAFAFA"));

        MaterialToolbar topAppBar = findViewById(R.id.lux_sugg_top_app_bar);
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
                startActivity(new Intent(FlickerSuggestActivity.this,
                        FlickerGuideActivity.class));
                return true;
            }
            return false;
        });

    }
}