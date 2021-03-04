package android.example.illumismart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.example.illumismart.entity.FlickerItem;
import android.example.illumismart.entity.Illuminance;
import android.example.illumismart.viewmodel.FlickerItemViewModel;
import android.example.illumismart.viewmodel.IlluminanceViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class FlickerRecordActivity extends AppCompatActivity {
    private Utils utils;
    private FlickerItemViewModel flickerItemViewModel;
    private dataItemViewModel dataItemViewModel;

    private String timeStamp;

    private TextView flickerCounts;
    private TextView flickerFreq;
    private TextView luxMin;
    private TextView luxMax;
    private TextView flickerTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker_record);
        utils = new Utils();

        flickerCounts = findViewById(R.id.flicker_count);
        flickerFreq = findViewById(R.id.flicker_freq);
        luxMin = findViewById(R.id.flicker_lux_min);
        luxMax = findViewById(R.id.flicker_lux_max);
        flickerTimestamp = findViewById(R.id.flicker_timestamp_txt);

        MaterialToolbar topAppBar = findViewById(R.id.flicker_app_bar);

        flickerItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(FlickerItemViewModel.class);
        dataItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(dataItemViewModel.class);

        Intent intent = getIntent();
        timeStamp = intent.getStringExtra("timestamp");

        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.delete_item) {
                deleteRecord();
                return true;
            }
            return false;
        });

        setRecord();
    }

    private void setRecord() {
        flickerItemViewModel.getFlickerItem(timeStamp)
                .observe(this, new Observer<FlickerItem>() {
            @Override
            public void onChanged(FlickerItem item) {
                flickerCounts.setText(item.getFlickerCounts());
                flickerFreq.setText(item.getFluctuationFreq());
                luxMin.setText(item.getMinLux());
                luxMax.setText(item.getMaxLux());
                flickerTimestamp.setText(utils.getParsedTimestamp(timeStamp));
            }
        });
    }

    private void deleteRecord() {
        flickerItemViewModel.delete(timeStamp);
        dataItemViewModel.delete(timeStamp);
    }
}
