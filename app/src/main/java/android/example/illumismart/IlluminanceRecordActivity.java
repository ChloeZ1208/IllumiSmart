package android.example.illumismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.viewmodel.IlluminanceViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class IlluminanceRecordActivity extends AppCompatActivity {
    private IlluminanceViewModel illuminanceViewModel;
    private dataItemViewModel mdataItemViewModel;

    private String timeStamp;

    private TextView luxAverage;
    private TextView luxMin;
    private TextView luxMax;
    private TextView luxTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illuminance_record);

        luxAverage = findViewById(R.id.lux_average);
        luxMin = findViewById(R.id.lux_min);
        luxMax = findViewById(R.id.lux_max);
        luxTimestamp = findViewById(R.id.timestamp_txt);

        MaterialToolbar topAppBar = findViewById(R.id.lux_app_bar);

        illuminanceViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(IlluminanceViewModel.class);
        mdataItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(dataItemViewModel.class);


        Intent intent = getIntent();
        timeStamp = intent.getStringExtra("timestamp");
        String time = timeStamp.substring(0, 4) + "-" + timeStamp.substring(4, 6) + "-" +
                timeStamp.substring(6, 8) + ", " + timeStamp.substring(8, 10) + ":" +
                timeStamp.substring(10, 12) + ":" +
                timeStamp.substring(12, 14);
        luxTimestamp.setText(time);

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
        setLuxValue();

    }

    private void deleteRecord() {
        illuminanceViewModel.delete(timeStamp);
        mdataItemViewModel.delete(timeStamp);
    }

    private void setLuxValue() {
        illuminanceViewModel.getIlluminance(timeStamp).observe(this, new Observer<Illuminance>() {
            @Override
            public void onChanged(Illuminance illuminance) {
                Log.d("shenme", illuminance.getAverage());
                String averageLux = illuminance.getAverage();
                String minLux = illuminance.getMinLux();
                String maxLux = illuminance.getMaxLux();

                luxAverage.setText(averageLux);
                luxMin.setText(minLux);
                luxMin.append(" Lux");
                luxMax.setText(maxLux);
                luxMax.append(" Lux");
            }
        });
    }
}