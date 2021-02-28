package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private ImageButton lightLevelSave;
    private TextView averageLux;
    private TextView clicksNum;
    private List<Float> lightlux;
    private int cnt;
    private float minLux;
    private float maxLux;
    private float average_lux; // average lux


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level);

        initializeViews();

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
                startActivity(new Intent(LightLevelActivity.this, LightLevelGuideActivity.class));
                return true;
            }
            return false;
        });

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightlux = new ArrayList();
        cnt = 0;

        // get average lux (when user navigate back to this page)
        /*
        *  TODO: get view details info
         */
        SharedPreferences mPrefs = getSharedPreferences("average", MODE_PRIVATE);
        String average = mPrefs.getString("averageLux", "");
        cnt = mPrefs.getInt("cnt", 0);
        clicksNum.setText(String.valueOf(5 - cnt));
        averageLux.setText(average);


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
                            clicksNum.setText(String.valueOf(5 - cnt));
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
                    //float average = 0;
                    float sum = 0;
                    minLux = Float.MAX_VALUE;
                    maxLux = Float.MIN_VALUE;
                    for (float l: lightlux) {
                        sum += l;
                        minLux = Math.min(minLux, l);
                        maxLux = Math.max(maxLux, l);
                    }
                    average_lux = sum / 5;
                    //Log.d("average", String.valueOf(average_lux));
                    averageLux.setText(String.valueOf(average_lux));
                    averageLux.append(" Lux");

                    /*
                    * TODO: View details-Provide lighting guidance (with age and work info)
                    *
                     */

                } else if (cnt < 5){
                    Toast.makeText(LightLevelActivity.this, "Click play to get more stats", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lightLevelReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt = 0;
                clicksNum.setText(String.valueOf(5 - cnt));
                averageLux.setText(" ");
            }
        });

        lightLevelSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  TODO: save minLux, maxLux, average_lux to database
                 */
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightListener, lightSensor);
        // save averagelux, viewdetails state
        SharedPreferences preferences = getSharedPreferences("average", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String average = averageLux.getText().toString();
        editor.putString("averageLux", average);
        editor.putInt("cnt", cnt);
        //editor.putString("maxLux", String.valueOf(maxLux));
        //editor.putString("minLux", String.valueOf(minLux));

        /*
         *  TODO: save view details info to preferences
         */
        editor.apply();
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
        lightLevelSave = findViewById(R.id.light_level_save);
        averageLux = findViewById(R.id.average_light_level);
        clicksNum = findViewById(R.id.clicks_num);
    }

}