package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.viewmodel.IlluminanceViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class IlluminanceRecordActivity extends AppCompatActivity {
    private Utils utils;
    private IlluminanceViewModel illuminanceViewModel;
    private dataItemViewModel dataItemViewModel;

    private String timeStamp;

    private TextView luxAverage;
    private TextView luxMin;
    private TextView luxMax;
    private TextView luxMeasuredTime;
    private TextView luxTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illuminance_record);
        utils = new Utils();

        luxAverage = findViewById(R.id.lux_record_average);
        luxMin = findViewById(R.id.lux_record_min);
        luxMax = findViewById(R.id.lux_header_max);
        luxMeasuredTime = findViewById(R.id.lux_record_measured_time);
        luxTimestamp = findViewById(R.id.lux_record_timestamp);

        MaterialToolbar topAppBar = findViewById(R.id.lux_record_app_bar);

        illuminanceViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(IlluminanceViewModel.class);
        dataItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(dataItemViewModel.class);


        Intent intent = getIntent();
        timeStamp = intent.getStringExtra("timestamp");

        // Set navigation back
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Set delete
        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.delete_item) {
                deleteRecord();
                return true;
            }
            return false;
        });

        // get average, min, max from Illuminance ("light_level" table)
        setRecord();

    }

    private void deleteRecord() {
        illuminanceViewModel.delete(timeStamp);
        dataItemViewModel.delete(timeStamp);
        onBackPressed();
    }

    private void setRecord() {
        illuminanceViewModel.getIlluminance(timeStamp).observe(this, new Observer<Illuminance>() {
            @Override
            public void onChanged(Illuminance illuminance) {
                if (illuminance != null) {
                    luxAverage.setText(illuminance.getAverage());
                    luxMin.setText(illuminance.getMinLux());
                    luxMax.setText(illuminance.getMaxLux());
                    luxMeasuredTime.setText(illuminance.getMeasuredTime());
                    luxTimestamp.setText(utils.getParsedTimestamp(timeStamp));
                }
            }
        });
    }
}