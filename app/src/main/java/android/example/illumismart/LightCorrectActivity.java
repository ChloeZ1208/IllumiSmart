package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class LightCorrectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_correct);
        View v = findViewById(R.id.light_level_correct_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#FAFAFA"));

        MaterialToolbar topAppBar = findViewById(R.id.light_correct_top_app_bar);
        // Set navigation back
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}