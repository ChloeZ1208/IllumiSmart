package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LightLevelActivity extends AppCompatActivity {

    private MaterialToolbar topAppBar;
    private TextView mlightLevel;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightListener;
    private ImageButton lightLevelPlay;
    private ImageButton lightLevelPause;
    private ImageButton lightLevelReset;
    private TextView averageLux;
    private List<Float> lightlux;
    private int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level);

        initializeViews();

        // set navigation back
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightlux = new ArrayList();
        cnt = 0;

        lightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                mlightLevel.setText(String.valueOf(event.values[0]));

                // click the button five times and record the light levels.
                lightLevelPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cnt < 5) {
                            lightlux.add(event.values[0]);
                            cnt++;
                        } else {
                            Toast.makeText(LightLevelActivity.this, "Stats enough", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //Toast.makeText(LightLevelActivity.this, "accuracy changed!", Toast.LENGTH_SHORT).show();
            }
        };

        lightLevelPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cnt == 5) {
                    float average = 0;
                    float sum = 0;
                    for (float l: lightlux) {
                        sum += l;
                    }
                    average = sum / 5;
                    Log.d("average", String.valueOf(average));
                    averageLux.setText(String.valueOf(average));
                    averageLux.append(" Lux");
                } else if (cnt < 5){
                    Toast.makeText(LightLevelActivity.this, "Click play to collect more stats", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lightLevelReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt = 0;
                averageLux.setText(" ");
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightListener, lightSensor);
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(lightListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }


    private void initializeViews() {
        topAppBar = findViewById(R.id.top_app_bar);
        mlightLevel = findViewById(R.id.light_level);
        lightLevelPlay = findViewById(R.id.light_level_start);
        lightLevelPause = findViewById(R.id.light_level_pause);
        lightLevelReset = findViewById(R.id.light_level_reset);
        averageLux = findViewById(R.id.average_light_level);
    }

}