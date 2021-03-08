package android.example.illumismart;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.example.illumismart.entity.FlickerItem;
import android.example.illumismart.viewmodel.FlickerItemViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class FlickerRecordActivity extends AppCompatActivity {
    private Utils utils;
    private FlickerItemViewModel flickerItemViewModel;
    private dataItemViewModel dataItemViewModel;

    private String timeStamp;

    private TextView flickerFluctuationRate;
    private TextView flickerRelativeChange;
    private TextView flickerAvgLux;
    private TextView flickerTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker_record);
        utils = new Utils();

        flickerFluctuationRate = findViewById(R.id.flicker_record_fluctuation);
        flickerRelativeChange = findViewById(R.id.flicker_record_relative_change);
        flickerAvgLux = findViewById(R.id.flicker_record_avg);
        flickerTimestamp = findViewById(R.id.flicker_record_time);

        MaterialToolbar topAppBar = findViewById(R.id.flicker_record_app_bar);

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
                if (item != null) {
                    flickerFluctuationRate.setText(item.getFluctuationRate());
                    flickerRelativeChange.setText(item.getRelativeChange());
                    flickerAvgLux.setText(item.getAvgLux());
                    flickerTimestamp.setText(utils.getParsedTimestamp(timeStamp));
                }
            }
        });
    }

    private void deleteRecord() {
        flickerItemViewModel.delete(timeStamp);
        dataItemViewModel.delete(timeStamp);
        onBackPressed();
    }
}
