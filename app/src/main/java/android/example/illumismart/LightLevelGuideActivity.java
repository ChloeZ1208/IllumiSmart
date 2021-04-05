package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LightLevelGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level_guide);

        Button backLightLevel = findViewById(R.id.nav_lightlevel);
        backLightLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView lightLevelReadMore = findViewById(R.id.light_level_guide_read_more);
        lightLevelReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: read more about light level
            }
        });
    }
}