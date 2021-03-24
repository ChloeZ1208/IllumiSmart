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
import java.util.ArrayList;

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
    private TextView lightLevelSuggestion;
    private TextView lightLevelViewDetails;
    private ArrayList<Float> luxMeasurementTmpList;
    private Boolean luxMeasurementActivated;
    private int luxRangeMin;
    private int luxRangeMax;

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

        // check if the user is the first time enter this page
        if (isFirstTime()) {
            startActivity(new Intent(LightLevelActivity.this, LightLevelGuideActivity.class));
        }

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

        lightLevelReset.setClickable(false);
        lightLevelPause.setClickable(false);
        lightLevelSave.setClickable(false);
        lightLevelReset.setImageResource(R.drawable.lux_reset_pressed);
        lightLevelPause.setImageResource(R.drawable.lux_pause_pressed);
        lightLevelSave.setImageResource(R.drawable.lux_save_pressed);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        luxMeasurementTmpList = new ArrayList<Float>();
        luxMeasurementActivated = false;

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
                                COUNT_DOWN_INTERVAL + 1));
                luxMeasurementRemainTime.append(" s");
            }

            @Override
            public void onFinish() {
                luxMeasurementActivated = false;
                lightLevelEntityPrep();
                luxMeasurementAverage.setText(illuminanceEntityInstance.getAverage());

                lightLevelReset.setClickable(true);
                lightLevelSave.setClickable(true);
                lightLevelPause.setClickable(false);

                lightLevelReset.setImageResource(R.drawable.light_level_reset);
                lightLevelSave.setImageResource(R.drawable.light_level_save);
                lightLevelPause.setImageResource(R.drawable.lux_pause_pressed);
                provideSuggestions();
            }
        };

        lightLevelPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enable pause button
                lightLevelPause.setClickable(true);
                lightLevelPause.setImageResource(R.drawable.light_level_pause);
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
                // Start timer
                luxMeasurementTimer.start();
                // Show elapsed text
                luxMeasurementRemainTimeHeader.setVisibility(View.VISIBLE);
                luxMeasurementRemainTime.setVisibility(View.VISIBLE);
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
                lightLevelSuggestion.setVisibility(View.INVISIBLE);
                lightLevelViewDetails.setVisibility(View.INVISIBLE);

                luxMeasurementTmpList.clear();

                lightLevelPlay.setClickable(true);
                lightLevelPlay.setImageResource(R.drawable.light_level_play);

                lightLevelReset.setClickable(false);
                lightLevelPause.setClickable(false);
                lightLevelSave.setClickable(false);
                lightLevelReset.setImageResource(R.drawable.lux_reset_pressed);
                lightLevelPause.setImageResource(R.drawable.lux_pause_pressed);
                lightLevelSave.setImageResource(R.drawable.lux_save_pressed);
            }
        });

        lightLevelSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  save minLux, maxLux, average_lux to database
                 */
                if (luxMeasurementTmpList.size() != 0) {
                    illuminanceViewModel.insert(illuminanceEntityInstance);
                    dataItemViewModel.insert(dataItemEntityInstance);
                    luxMeasurementTmpList.clear();
                    lightLevelSave.setClickable(false);
                    lightLevelSave.setImageResource(R.drawable.lux_save_pressed);
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

    private boolean isFirstTime() {
        SharedPreferences preferences = getSharedPreferences("LightLevel",MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.apply();
        }
        return !ranBefore;
    }

    private void luxRangeCalculate() {
        SharedPreferences preferences = getSharedPreferences("ProfileInfo",MODE_PRIVATE);
        String occupation = preferences.getString("Occupation", null);
        String age = preferences.getString("Age", null);
        if (occupation == null) {
            luxRangeMin = 0;
            luxRangeMax = 0;
        } else if (occupation.equals("GeneralWork")) {
            luxRangeMin = 80;
            luxRangeMax = 170;
        } else if (occupation.equals("ModerateWork")) {
            luxRangeMin = 200;
            luxRangeMax = 250;
        } else if (occupation.equals("PreciseWork")) {
            luxRangeMin = 250;
            luxRangeMax = 300;
        } else if (occupation.equals("FineWork")) {
            luxRangeMin = 500;
            luxRangeMax = 700;
        } else if (occupation.equals("VeryFineWork")) {
            luxRangeMin = 1000;
            luxRangeMax = 2000;
        }
        double ageFactor = 0;
        if (age == null) {
        } else if (age.equals("below40")) {
            ageFactor = 1;
        } else if (age.equals("4050")) {
            ageFactor = 1.2;
        } else if (age.equals("5065")) {
            ageFactor = 1.6;
        } else if (age.equals("above65")) {
            ageFactor = 2.7;
        }
        luxRangeMin = (int) ageFactor * luxRangeMin;
        luxRangeMax = (int) ageFactor * luxRangeMax;
    }

    private void provideSuggestions() {
        luxRangeCalculate();
        if (luxRangeMin != 0 && luxRangeMax != 0){
            if (avgLux < luxRangeMin) {
                lightLevelSuggestion.setText(R.string.light_level_guidance_below);
            } else if (avgLux > luxRangeMax)
                lightLevelSuggestion.setText(R.string.light_level_guidance_above);
            else {
                lightLevelSuggestion.setText(R.string.light_level_guidance_among);
            }
            lightLevelSuggestion.setVisibility(View.VISIBLE);
            lightLevelViewDetails.setVisibility(View.VISIBLE);
        }
    }

    public void viewDetails(View view) {
        Intent i = new Intent(LightLevelActivity.this, LightLevelSuggestActivity.class);
        i.putExtra("luxRangeMin", String.valueOf(luxRangeMin));
        i.putExtra("luxRangeMax", String.valueOf(luxRangeMax));
        startActivity(i);
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
        Utils utils = new Utils();
        String timeStamp = utils.getSpecifiedTimestamp();
        illuminanceEntityInstance = new Illuminance(timeStamp,
                df.format(minLux) + " Lux",
                df.format(maxLux) + " Lux",
                df.format(avgLux) + " Lux",
                luxMeasurementRemainTime.getText().toString());
        dataItemEntityInstance = new dataItem(timeStamp, ITEM_NAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null && lightSensor != null) {
            sensorManager.unregisterListener(lightListener, lightSensor);
        }
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
        topAppBar = findViewById(R.id.lux_top_app_bar);
        mlightLevel = findViewById(R.id.lux_record_light_level);
        lightLevelPlay = findViewById(R.id.light_level_start);
        lightLevelPause = findViewById(R.id.light_level_pause);
        lightLevelReset = findViewById(R.id.light_level_reset);
        lightLevelSave = findViewById(R.id.light_level_save);
        luxMeasurementAverageHeader = findViewById(R.id.lux_average_header);
        luxMeasurementAverage = findViewById(R.id.average_light_level);
        luxMeasurementRemainTimeHeader = findViewById(R.id.lux_elapsed_time_header);
        luxMeasurementRemainTime = findViewById(R.id.lux_elapsed_time);
        luxMeasurementMin = findViewById(R.id.light_level_min);
        luxMeasurementMax = findViewById(R.id.light_level_max);
        lightLevelSuggestion = findViewById(R.id.light_level_suggestions);
        lightLevelViewDetails = findViewById(R.id.light_level_viewdetails);
    }

}