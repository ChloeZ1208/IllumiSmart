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
    private TextView luxTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illuminance_record);
        utils = new Utils();

        luxAverage = findViewById(R.id.lux_average);
        luxMin = findViewById(R.id.lux_min);
        luxMax = findViewById(R.id.lux_max);
        luxTimestamp = findViewById(R.id.timestamp_txt);

        MaterialToolbar topAppBar = findViewById(R.id.lux_app_bar);

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
    }

    private void setRecord() {
        illuminanceViewModel.getIlluminance(timeStamp).observe(this, new Observer<Illuminance>() {
            @Override
            public void onChanged(Illuminance illuminance) {
                String averageLux = illuminance.getAverage();
                String minLux = illuminance.getMinLux();
                String maxLux = illuminance.getMaxLux();

                luxAverage.setText(averageLux);
                luxMin.setText(minLux);
                luxMax.setText(maxLux);
                luxTimestamp.setText(utils.getParsedTimestamp(timeStamp));
            }
        });
    }
}