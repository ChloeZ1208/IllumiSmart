package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LightLevelGuideActivity extends AppCompatActivity {
    private Button backLightLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level_guide);

        backLightLevel = findViewById(R.id.nav_lightlevel);
        backLightLevel.setBackgroundColor(Color.WHITE);
        backLightLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}