package android.example.illumismart;

import android.content.Intent;
import android.example.illumismart.entity.GlareItem;
import android.example.illumismart.viewmodel.GlareItemViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;

public class GlareRecordActivity extends AppCompatActivity {
    private Utils utils;
    private GlareItemViewModel glareItemViewModel;
    private dataItemViewModel dataItemViewModel;

    private String timeStamp;

    private TextView glareMaxPixelVal;
    private TextView glareTimestamp;
    private TextView glareEvent;
    private ImageView glareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glare_record);
        View v = findViewById(R.id.glare_record_screen);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#E5E5E5"));
        utils = new Utils();

        glareMaxPixelVal = findViewById(R.id.glare_record_pixel);
        glareEvent = findViewById(R.id.glare_record_event);
        glareImage = findViewById(R.id.glare_record_image);
        glareTimestamp = findViewById(R.id.glare_record_time);

        MaterialToolbar topAppBar = findViewById(R.id.glare_record_app_bar);

        glareItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(GlareItemViewModel.class);
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
        glareItemViewModel.getGlareItem(timeStamp)
                .observe(this, new Observer<GlareItem>() {
                    @Override
                    public void onChanged(GlareItem item) {
                        if (item != null) {
                            glareMaxPixelVal.setText(item.getMaxPixelVal());
                            glareEvent.setText(item.getGlareEvent());
                            glareTimestamp.setText(utils.getParsedTimestamp(timeStamp));
                            Bitmap bitmap = BitmapFactory.decodeFile(item.getImgPath());
                            glareImage.setImageBitmap(bitmap);
                        }
                    }
                });
    }

    private void deleteRecord() {
        glareItemViewModel.delete(timeStamp);
        dataItemViewModel.delete(timeStamp);
        onBackPressed();
    }
}
