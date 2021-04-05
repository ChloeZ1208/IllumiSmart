package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GlareGuideActivity extends AppCompatActivity {
    private Button backGlare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glare_guide);

        backGlare = findViewById(R.id.nav_glare);
        backGlare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView glareReadMore = findViewById(R.id.glare_guide_read_more);
        glareReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: read more about the glare
            }
        });
    }
}