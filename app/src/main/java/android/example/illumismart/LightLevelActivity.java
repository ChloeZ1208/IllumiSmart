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
    private TextView luxMeasurementAverageHeader;
    private TextView luxMeasurementRemainTime;
    private TextView luxMeasurementRemainTimeHeader;
    private TextView luxMeasurementMin;
    private TextView luxMeasurementMax;
    private ArrayList<Float> luxMeasurementTmpList;
    private Boolean luxMeasurementActivated;

    private CountDownTimer luxMeasurementTimer;
    private static final long COUNT_DOWN_TIME = 60000;
    private static final long COUNT_DOWN_INTERVAL = 1000;

    private float minLux;
    private float maxLux;
    private float avgLux;
    private DecimalFormat df;

    private IlluminanceViewModel illuminanceViewModel;
    private dataItemViewModel dataItemViewModel;
    private Illuminance illuminanceEntityInstance;
    private dataItem dataItemEntityInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_level);

        initializeViews();
        df = new DecimalFormat("0.00");

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
                    lightLevelAnalysis();
                    luxMeasurementAverage.setText(df.format(avgLux));
                    luxMeasurementMin.setText("Min: ");
                    luxMeasurementMax.setText("Max: ");
                    luxMeasurementMin.append(df.format(minLux));
                    luxMeasurementMax.append(df.format(maxLux));
                    luxMeasurementAverage.append(" Lux");
                    luxMeasurementMin.append(" Lux");
                    luxMeasurementMax.append(" Lux");
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
                luxMeasurementRemainTime.setText(
                        String.valueOf((COUNT_DOWN_TIME - millisUntilFinished) /
                                COUNT_DOWN_INTERVAL));
                luxMeasurementRemainTime.append("s");
            }

            @Override
            public void onFinish() {
                luxMeasurementActivated = false;
                lightLevelEntityPrep();
                luxMeasurementRemainTimeHeader.setVisibility(View.VISIBLE);
                luxMeasurementRemainTime.setVisibility(View.VISIBLE);
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
                // Clear previous realtime lux display
                luxMeasurementAverage.setText("0.00 Lux");
                luxMeasurementMin.setText("Min: 0.00 Lux");
                luxMeasurementMax.setText("Max: 0.00 Lux");
                luxMeasurementAverageHeader.setVisibility(View.VISIBLE);
                luxMeasurementAverage.setVisibility(View.VISIBLE);
                luxMeasurementMin.setVisibility(View.VISIBLE);
                luxMeasurementMax.setVisibility(View.VISIBLE);
                // Elapsed text hide
                luxMeasurementRemainTimeHeader.setVisibility(View.INVISIBLE);
                luxMeasurementRemainTime.setVisibility(View.INVISIBLE);
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
                luxMeasurementAverageHeader.setVisibility(View.INVISIBLE);
                luxMeasurementAverage.setVisibility(View.INVISIBLE);
                luxMeasurementRemainTimeHeader.setVisibility(View.INVISIBLE);
                luxMeasurementRemainTime.setVisibility(View.INVISIBLE);
                luxMeasurementMin.setVisibility(View.INVISIBLE);
                luxMeasurementMax.setVisibility(View.INVISIBLE);
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
        float luxMeasurementSum = 0;
        if (luxMeasurementTmpList.size() == 0) {
            minLux = 0;
            maxLux = 0;
            avgLux = 0;
        } else {
            minLux = luxMeasurementTmpList.get(0);
            maxLux = luxMeasurementTmpList.get(0);
            for (float lux : luxMeasurementTmpList) {
                luxMeasurementSum += lux;
                minLux = Math.min(minLux, lux);
                maxLux = Math.max(maxLux, lux);
            }
            avgLux = luxMeasurementSum / luxMeasurementTmpList.size();
        }
    }

    private void lightLevelEntityPrep() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).
                format(new Date());
        illuminanceEntityInstance = new Illuminance
                (timeStamp, df.format(minLux) + " Lux", df.format(maxLux) + " Lux",
                        df.format(avgLux) + " Lux");
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
        luxMeasurementAverageHeader = findViewById(R.id.average_txt);
        luxMeasurementAverage = findViewById(R.id.average_light_level);
        luxMeasurementRemainTimeHeader = findViewById(R.id.clicks_txt);
        luxMeasurementRemainTime = findViewById(R.id.clicks_time);
        luxMeasurementMin = findViewById(R.id.min_txt);
        luxMeasurementMax = findViewById(R.id.max_txt);
    }

}