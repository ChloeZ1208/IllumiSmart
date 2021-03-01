package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.viewmodel.IlluminanceViewModel;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LightLevelActivity extends AppCompatActivity {
    private static final String LOG_TAG = LightLevelActivity.class.getSimpleName();
    private MaterialToolbar topAppBar;
    private TextView mlightLevel;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightListener;
    private ImageButton lightLevelPlay;
    private ImageButton lightLevelPause;
    private ImageButton lightLevelReset;
    private ImageButton lightLevelSave;
    private TextView luxMeasurementAverage;
    private TextView luxMeasurementRemainTime;
    private TextView luxMeasurementHeader;
    private ArrayList<Float> luxMeasurementTmpList;
    private Boolean luxMeasurementActivated;
    private CountDownTimer luxMeasurementTimer;
    private float minLux;
    private float maxLux;
    private String timeStamp;

    private IlluminanceViewModel illuminanceViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level);

        initializeViews();

        timeStamp = "yyyyMMddHHmm";
        IlluminanceViewModel.Factory factory = new IlluminanceViewModel.Factory(
                this.getApplication(), timeStamp);
        illuminanceViewModel = new ViewModelProvider(this, factory)
                .get(IlluminanceViewModel.class);

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
                startActivity(new Intent(LightLevelActivity.this,
                        LightLevelGuideActivity.class));
                return true;
            }
            return false;
        });

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        luxMeasurementTmpList = new ArrayList<Float>();
        luxMeasurementActivated = false;

        // get average lux (when user navigate back to this page)
        /*
         *  TODO: get view details info
         */
        SharedPreferences mPrefs = getSharedPreferences("average", MODE_PRIVATE);
        String average = mPrefs.getString("averageLux", "");
        luxMeasurementAverage.setText(average);
        maxLux = mPrefs.getFloat("maxLux", Float.MIN_VALUE);
        minLux = mPrefs.getFloat("minLux", Float.MAX_VALUE);


        lightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                mlightLevel.setText(String.valueOf(event.values[0]));
                if (luxMeasurementActivated) {
                    luxMeasurementTmpList.add(event.values[0]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO
            }
        };

        luxMeasurementTimer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO: alter play button background to notify click banned
                lightLevelPlay.setClickable(false);
                lightLevelReset.setClickable(false);
                lightLevelSave.setClickable(false);
                luxMeasurementRemainTime.setVisibility(View.VISIBLE);
                luxMeasurementRemainTime.setText(
                        String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                luxMeasurementActivated = false;
                luxMeasurementHeader.setVisibility(View.INVISIBLE);
                luxMeasurementRemainTime.setVisibility(View.INVISIBLE);
                luxMeasurementAverage.setText(getLuxMeasurementAverage());
                luxMeasurementAverage.append(" Lux");
                // save the time
                timeStamp = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA).
                        format(new Date());
                // TODO: alter play button background to notify click unlock
                lightLevelPlay.setClickable(true);
                lightLevelReset.setClickable(true);
                lightLevelSave.setClickable(true);
            }
        };

        lightLevelPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize tmp list for lux
                luxMeasurementTmpList.clear();
                // Flag on: insert lux to the tmp list
                luxMeasurementActivated = true;
                // Clear previous average lux display
                luxMeasurementAverage.setText(" ");
                // Countdown text display
                luxMeasurementHeader.setVisibility(View.VISIBLE);
                // Start timer
                luxMeasurementTimer.start();
            }
        });

        lightLevelPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luxMeasurementTimer.cancel();
                luxMeasurementTimer.onFinish();
            }
        });

        lightLevelReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luxMeasurementAverage.setText(" ");
            }
        });

        lightLevelSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  TODO: save minLux, maxLux, average_lux to database
                 */
                if (luxMeasurementTmpList.size() != 0) {
                    Illuminance illuminance = new Illuminance
                            (timeStamp, String.valueOf(minLux), String.valueOf(maxLux),
                                    luxMeasurementAverage.getText().toString());
                    illuminanceViewModel.insert(illuminance);
                } else {
                    Toast.makeText(LightLevelActivity.this,
                            "No data to save. Click play!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String getLuxMeasurementAverage() {
        DecimalFormat df = new DecimalFormat("0.00");
        float luxMeasurementSum = 0;
        if (luxMeasurementTmpList.size() == 0) {
            minLux = 0;
            maxLux = 0;
            return df.format(luxMeasurementSum);
        }
        minLux = luxMeasurementTmpList.get(0);
        maxLux = luxMeasurementTmpList.get(0);
        for (float lux : luxMeasurementTmpList) {
            luxMeasurementSum += lux;
            minLux = Math.min(minLux, lux);
            maxLux = Math.max(maxLux, lux);
        }
        Log.d("luxMeasurementSum: ", df.format(luxMeasurementSum));
        return df.format(luxMeasurementSum/luxMeasurementTmpList.size());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null && lightSensor != null) {
            sensorManager.unregisterListener(lightListener, lightSensor);
        }
        SharedPreferences preferences = getSharedPreferences("average", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String average = luxMeasurementAverage.getText().toString();
        editor.putString("averageLux", average);
        editor.putFloat("maxLux", maxLux);
        editor.putFloat("minLux", minLux);

        /*
         *  TODO: save view details info to preferences
         */
        editor.apply();
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        if (sensorManager != null && lightSensor != null) {
            sensorManager.registerListener(lightListener, lightSensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Log.w(LOG_TAG, "Unable to register light sensor.");
        }
    }


    private void initializeViews() {
        topAppBar = findViewById(R.id.top_app_bar);
        mlightLevel = findViewById(R.id.light_level);
        lightLevelPlay = findViewById(R.id.light_level_start);
        lightLevelPause = findViewById(R.id.light_level_pause);
        lightLevelReset = findViewById(R.id.light_level_reset);
        lightLevelSave = findViewById(R.id.light_level_save);
        luxMeasurementAverage = findViewById(R.id.average_light_level);
        luxMeasurementHeader = findViewById(R.id.clicks_txt);
        luxMeasurementRemainTime = findViewById(R.id.clicks_num);
    }

}