package android.example.illumismart;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FlickerActivity extends AppCompatActivity {
    private static final String LOG_TAG = FlickerActivity.class.getSimpleName();

    private Button flickerButtonStart;
    private TextView flickerTextTimeRemaining;
    private TextView flickerTextInfo;
    private TextView flickerTextRealtimeLux;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker);
        initializeViews();
        initializeLightSensor();
        initializeCountDownTimer();
        flickerDetectionActivated = false;
        flickerDetectionTmpList = new ArrayList<Float>();

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
        flickerButtonStart = findViewById(R.id.flicker_start);
        flickerTextTimeRemaining = findViewById(R.id.flicker_text_time_remain);
        flickerTextInfo = findViewById(R.id.flicker_text_info);
        flickerTextRealtimeLux = findViewById(R.id.flicker_text_realtime_lux);

        flickerButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flickerTextTimeRemaining.setVisibility(View.VISIBLE);
                flickerTextInfo.setVisibility(View.INVISIBLE);
                flickerDetectionTmpList.clear();
                flickerButtonStart.setClickable(false);
                flickerDetectionTimer.start();
                flickerDetectionActivated = true;
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
                flickerTextRealtimeLux.setText("Real time lux: ");
                flickerTextRealtimeLux.append(String.valueOf(event.values[0]));
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
                flickerTextTimeRemaining.setText("Seconds remaining: ");
                flickerTextTimeRemaining.
                        append(String.valueOf(millisUntilFinished / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                // Finish collecting samples
                flickerDetectionActivated = false;
                flickerTextTimeRemaining.setVisibility(View.INVISIBLE);
                flickerTextInfo.setText("Analyzing ...");
                flickerTextInfo.setVisibility(View.VISIBLE);
                // Analyzing samples
                flickerDetectionAnalysis();
                flickerButtonStart.setClickable(true);
            }
        };
    }

    private void flickerDetectionAnalysis() {
        int flickerEventCount = 0;
        if (flickerDetectionTmpList.size() < FLICKER_WINDOW_SIZE) {
            Log.d(LOG_TAG, "flickerDetectionTmpList size < 3: light source stable?");
            flickerTextInfo.setText("Flicker events: ");
            flickerTextInfo.append(String.valueOf(flickerEventCount));
            return;
        }
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
        flickerTextInfo.setText("Flicker events: ");
        flickerTextInfo.append(String.valueOf(flickerEventCount));
    }
}
