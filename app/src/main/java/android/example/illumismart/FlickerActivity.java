package android.example.illumismart;

import android.content.Intent;
import android.example.illumismart.viewmodel.FlickerItemViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.example.illumismart.entity.FlickerItem;
import android.example.illumismart.entity.dataItem;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class FlickerActivity extends AppCompatActivity {
    private static final String LOG_TAG = FlickerActivity.class.getSimpleName();
    private static final String ITEM_NAME = "Flicker";

    private ImageButton flickerButtonStart;
    private ImageButton flickerButtonSave;
    private ImageButton flickerButtonReset;
    private TextView flickerTextTimeRemaining;
    private TextView flickerTextInfo;
    private TextView flickerTextRealtimeLux;
    private MaterialToolbar topAppBar;
    private TextView flickerTextFreq;
    private TextView lightLevelTxt;
    private TextView unitLuxHz;
    private TextView relativeChangeTxt;
    private TextView relativeChangeValue;

    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private SensorEventListener mSensorEventListener;

    private CountDownTimer flickerDetectionTimer;
    private static final long COUNT_DOWN_TIME = 10000;
    private static final long COUNT_DOWN_INTERVAL = 1000;

    private Boolean flickerDetectionActivated;
    private ArrayList<Float> flickerDetectionTmpList;
    private static final int FLICKER_WINDOW_SIZE = 3;
    private static final int FLICKER_THRESHOLD = 5;

    private int flickerEventCount;
    private float flickerFreq;
    private String relativeChange;

    private FlickerItemViewModel flickerItemViewModel;
    private dataItemViewModel dataItemViewModel;
    private FlickerItem flickerEntityInstance;
    private dataItem dataItemEntityInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker);
        initializeViews();
        initializeLightSensor();
        initializeCountDownTimer();
        flickerDetectionActivated = false;
        flickerDetectionTmpList = new ArrayList<Float>();

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
                startActivity(new Intent(FlickerActivity.this,
                        FlickerGuideActivity.class));
                return true;
            }
            return false;
        });

        // FlickerItem save
        flickerItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(FlickerItemViewModel.class);
        // DataItem(Flicker) save
        dataItemViewModel = new ViewModelProvider(this,
                ViewModelProvider.
                        AndroidViewModelFactory.
                        getInstance(this.getApplication())).get(dataItemViewModel.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null && mLightSensor != null) {
            mSensorManager.unregisterListener(mSensorEventListener, mLightSensor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager != null && mLightSensor != null) {
            mSensorManager.registerListener(mSensorEventListener, mLightSensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Log.w(LOG_TAG, "Unable to register light sensor (Flicker).");
        }
    }

    private void initializeViews() {
        flickerTextRealtimeLux = findViewById(R.id.flicker_text_realtime_lux);
        flickerTextFreq = findViewById(R.id.flicker_fluctuation);
        lightLevelTxt = findViewById(R.id.light_level_txt);
        unitLuxHz = findViewById(R.id.unit_lux_hz);
        relativeChangeTxt = findViewById(R.id.relative_change_txt);
        relativeChangeValue = findViewById(R.id.relative_change);

        flickerButtonStart = findViewById(R.id.flicker_start);
        flickerButtonReset = findViewById(R.id.flicker_reset);
        flickerButtonSave = findViewById(R.id.flicker_save);
        flickerTextTimeRemaining = findViewById(R.id.flicker_text_time_remain);
        flickerTextInfo = findViewById(R.id.flicker_text_info);

        topAppBar = findViewById(R.id.flicker_app_bar);

        flickerButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flickerTextTimeRemaining.setVisibility(View.VISIBLE);
                //flickerTextInfo.setVisibility(View.INVISIBLE);
                //flickerTextFreq.setVisibility(View.INVISIBLE);
                flickerDetectionTmpList.clear();
                flickerButtonStart.setImageResource(R.drawable.lux_play_pressed);
                flickerButtonReset.setImageResource(R.drawable.lux_reset_pressed);
                flickerButtonSave.setImageResource(R.drawable.lux_save_pressed);
                flickerButtonStart.setClickable(false);
                flickerButtonReset.setClickable(false);
                flickerButtonSave.setClickable(false);
                flickerDetectionTimer.start();
                flickerDetectionActivated = true;
            }
        });

        flickerButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flickerButtonStart.setClickable(true);
                flickerButtonStart.setImageResource(R.drawable.flicker_play);

                flickerDetectionTmpList.clear();
                lightLevelTxt.setText("Instantaneous Level");
                unitLuxHz.setText("Lux");
                flickerTextRealtimeLux.setVisibility(View.VISIBLE);
                flickerTextFreq.setVisibility(View.INVISIBLE);
                relativeChangeTxt.setVisibility(View.INVISIBLE);
                relativeChangeValue.setVisibility(View.INVISIBLE);
                flickerTextInfo.setVisibility(View.INVISIBLE);
            }
        });

        flickerButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flickerDetectionTmpList.size() != 0) {
                    flickerItemViewModel.insert(flickerEntityInstance);
                    dataItemViewModel.insert(dataItemEntityInstance);
                    flickerDetectionTmpList.clear();
                    Toast.makeText(FlickerActivity.this,
                            "Flicker record saved successfully!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FlickerActivity.this,
                            "No data to save. Click play!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeLightSensor() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (mSensorManager != null) {
            mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                flickerTextRealtimeLux.setText(String.valueOf(event.values[0]));
                if (flickerDetectionActivated) {
                    flickerDetectionTmpList.add(event.values[0]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO
            }
        };
    }

    private void initializeCountDownTimer() {
        flickerDetectionTimer = new CountDownTimer(COUNT_DOWN_TIME, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                flickerTextTimeRemaining.setText("Time remaining: ");
                flickerTextTimeRemaining.
                        append(String.valueOf(millisUntilFinished / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                // Finish collecting samples
                flickerDetectionActivated = false;
                flickerTextTimeRemaining.setVisibility(View.INVISIBLE);
                flickerDetectionAnalysis();
                // set guidance result based on flicker count
                if (flickerEventCount == 0) {
                    flickerTextInfo.setText(R.string.flicker_guidance_0);
                } else if (flickerEventCount >= 1 && flickerEventCount <= 3) {
                    flickerTextInfo.setText(R.string.flicker_guidance_1to3);
                } else if (flickerEventCount > 3) {
                    flickerTextInfo.setText(R.string.flicker_guidance_3);
                }

                lightLevelTxt.setText("Fluctuation Rate");
                flickerTextFreq.setText(String.valueOf(flickerFreq));
                unitLuxHz.setText("Hz");
                Log.d("relative change", String.valueOf(relativeChange));
                relativeChangeValue.setText(relativeChange + "%");

                flickerTextRealtimeLux.setVisibility(View.INVISIBLE);
                relativeChangeTxt.setVisibility(View.VISIBLE);
                relativeChangeValue.setVisibility(View.VISIBLE);
                flickerTextInfo.setVisibility(View.VISIBLE);
                flickerTextFreq.setVisibility(View.VISIBLE);
                flickerButtonReset.setClickable(true);
                flickerButtonSave.setClickable(true);
                flickerButtonReset.setImageResource(R.drawable.flicker_reset);
                flickerButtonSave.setImageResource(R.drawable.flicker_save);
            }
        };
    }

    private void flickerDetectionAnalysis() {
        DecimalFormat df = new DecimalFormat("0.0");
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).
                format(new Date());
        flickerEventCount = 0;
        flickerFreq = 0;
        float minLux = 0;
        float maxLux = 0;
        if (flickerDetectionTmpList.size() < FLICKER_WINDOW_SIZE) {
            Log.d(LOG_TAG, "flickerDetectionTmpList size < 3: light source stable?");
        } else {
            for (int i = 0; i <= flickerDetectionTmpList.size() - FLICKER_WINDOW_SIZE; ++i) {
                Float[] luxWindow = {
                        flickerDetectionTmpList.get(i),
                        flickerDetectionTmpList.get(i+1),
                        flickerDetectionTmpList.get(i+2)};
                float luxMin = Collections.min(Arrays.asList(luxWindow));
                float luxMax = Collections.max(Arrays.asList(luxWindow));
                if ((luxMax - luxMin) > FLICKER_THRESHOLD) {
                    flickerEventCount += 1;
                }
            }
            if (flickerEventCount != 0) {
                flickerFreq = (float)flickerEventCount /
                        (float)((int)COUNT_DOWN_TIME / (int)COUNT_DOWN_INTERVAL);
            }
        }
        minLux = Collections.min(flickerDetectionTmpList);
        maxLux = Collections.max(flickerDetectionTmpList);
        relativeChange = df.format((minLux / maxLux) * 100);
        dataItemEntityInstance = new dataItem(timeStamp, ITEM_NAME);
        flickerEntityInstance = new FlickerItem(timeStamp,
                                                df.format(flickerFreq)+" Hz",
                                                String.valueOf(flickerEventCount),
                                                df.format(maxLux)+" Hz",
                                                df.format(minLux)+" Hz");
    }
}
