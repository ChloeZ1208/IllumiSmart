package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FlickerGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker_guide);

        Button backFlicker = findViewById(R.id.nav_flicker);
        backFlicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView flickerReadMore = findViewById(R.id.flicker_guide_read_more);
        flickerReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils utils = new Utils();
                utils.openURL("https://www.ccohs.ca/oshanswers/ergonomics/lighting_flicker.html", getResources().getColor(R.color.flicker, null), FlickerGuideActivity.this);
            }
        });
    }
}