package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.entity.dataItem;
import android.example.illumismart.viewmodel.IlluminanceViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
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
    private static final String ITEM_NAME = "Illuminance";
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
    private static final long COUNT_DOWN_TIME = 10000;
    private static final long COUNT_DOWN_INTERVAL = 1000;

    private float minLux;
    private float maxLux;

    private IlluminanceViewModel illuminanceViewModel;
    private dataItemViewModel dataItemViewModel;
    private Illuminance illuminanceEntityInstance;
    private dataItem dataItemEntityInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level);

        initializeViews();

        // for illuminance save
        illuminanceViewModel = new ViewModelProvider(this,
                ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(IlluminanceViewModel.class);
        // for data item(illuminance) save
        dataItemViewModel = new ViewModelProvider(this,
                ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(dataItemViewModel.class);

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

        SharedPreferences mPrefs = getSharedPreferences("Illuminance", MODE_PRIVATE);
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

        luxMeasurementTimer = new CountDownTimer(COUNT_DOWN_TIME, COUNT_DOWN_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                lightLevelPlay.setClickable(false);
                lightLevelReset.setClickable(false);
                lightLevelSave.setClickable(false);
                lightLevelPlay.setImageResource(R.drawable.lux_play_pressed);
                lightLevelReset.setImageResource(R.drawable.lux_reset_pressed);
                lightLevelSave.setImageResource(R.drawable.lux_save_pressed);

                luxMeasurementRemainTime.setVisibility(View.VISIBLE);
                luxMeasurementRemainTime.setText(
                        String.valueOf(millisUntilFinished / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                luxMeasurementActivated = false;
                lightLevelAnalysis();
                luxMeasurementHeader.setVisibility(View.INVISIBLE);
                luxMeasurementRemainTime.setVisibility(View.INVISIBLE);
                luxMeasurementAverage.setText(illuminanceEntityInstance.getAverage());

                lightLevelPlay.setClickable(true);
                lightLevelReset.setClickable(true);
                lightLevelSave.setClickable(true);

                lightLevelPlay.setImageResource(R.drawable.light_level_play);
                lightLevelReset.setImageResource(R.drawable.light_level_reset);
                lightLevelSave.setImageResource(R.drawable.light_level_save);
            }
        };

        lightLevelPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize tmp list for lux
                luxMeasurementTmpList.clear();
                // Clear previous average lux display
                luxMeasurementAverage.setText(" ");
                // Countdown text display
                luxMeasurementHeader.setVisibility(View.VISIBLE);
                // Start timer
                luxMeasurementTimer.start();
                // Flag on: insert lux to the tmp list
                luxMeasurementActivated = true;
            }
        });

        lightLevelPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (luxMeasurementActivated) {
                    luxMeasurementTimer.cancel();
                    luxMeasurementTimer.onFinish();
                }
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
                    illuminanceViewModel.insert(illuminanceEntityInstance);
                    dataItemViewModel.insert(dataItemEntityInstance);
                    luxMeasurementTmpList.clear();
                    Toast.makeText(LightLevelActivity.this,
                            "Illuminance record saved successfully!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LightLevelActivity.this,
                            "No data to save. Click play!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void lightLevelAnalysis() {
        DecimalFormat df = new DecimalFormat("0.00");
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).
                format(new Date());
        float luxMeasurementSum = 0;
        float luxMeasurementAvg = 0;
        if (luxMeasurementTmpList.size() == 0) {
            minLux = 0;
            maxLux = 0;
        } else {
            minLux = luxMeasurementTmpList.get(0);
            maxLux = luxMeasurementTmpList.get(0);
            for (float lux : luxMeasurementTmpList) {
                luxMeasurementSum += lux;
                minLux = Math.min(minLux, lux);
                maxLux = Math.max(maxLux, lux);
            }
            luxMeasurementAvg = luxMeasurementSum / luxMeasurementTmpList.size();
        }
        Log.d("luxMeasurementSum: ", df.format(luxMeasurementSum));
        illuminanceEntityInstance = new Illuminance
                (timeStamp, df.format(minLux) + " Lux", df.format(maxLux) + " Lux",
                        df.format(luxMeasurementAvg) + " Lux");
        dataItemEntityInstance = new dataItem(timeStamp, ITEM_NAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null && lightSensor != null) {
            sensorManager.unregisterListener(lightListener, lightSensor);
        }
        SharedPreferences preferences = getSharedPreferences("Illuminance", MODE_PRIVATE);
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