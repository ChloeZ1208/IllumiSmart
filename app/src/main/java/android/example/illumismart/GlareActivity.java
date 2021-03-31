package android.example.illumismart;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.example.illumismart.entity.GlareItem;
import android.example.illumismart.entity.dataItem;
import android.example.illumismart.viewmodel.GlareItemViewModel;
import android.example.illumismart.viewmodel.dataItemViewModel;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.DecimalFormat;

public class GlareActivity extends AppCompatActivity implements
        CameraBridgeViewBase.CvCameraViewListener2{
    private static final String LOG_TAG = GlareActivity.class.getSimpleName();
    private static final String ITEM_NAME = "Glare";
    private CameraBridgeViewBase glareCVCamera;

    private Mat glareFrame;
    private float glareMaxPixelVal;
    private boolean glareEvent;
    private TextView glareTextViewResult;
    private Button glareButtonSave;
    private Button glareButtonBack;
    private Utils utils;
    private DecimalFormat df;
    private ImageView glareGuidance;

    private GlareItemViewModel glareItemViewModel;
    private dataItemViewModel dataItemViewModel;
    private GlareItem glareEntityInstance;
    private dataItem dataItemEntityInstance;


    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(LOG_TAG, "OpenCV loaded successfully");
                    glareCVCamera.enableView();
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glare);
        utils = new Utils();
        df = new DecimalFormat("0.00");
        glareMaxPixelVal = 0;
        glareEvent = false;
        // GlareItem save
        glareItemViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.
                getInstance(this.getApplication())).get(GlareItemViewModel.class);
        // DataItem(Glare) save
        dataItemViewModel = new ViewModelProvider(this,
                ViewModelProvider.
                        AndroidViewModelFactory.
                        getInstance(this.getApplication())).get(dataItemViewModel.class);

        glareTextViewResult = findViewById(R.id.glare_text_result);
        glareTextViewResult.setTextColor(Color.RED);
        glareCVCamera = (CameraBridgeViewBase) findViewById(R.id.glare_cv_camera);
        glareCVCamera.setCvCameraViewListener(this);
        glareButtonSave = findViewById(R.id.glare_button_save);
        glareButtonBack = findViewById(R.id.glare_button_back);
        glareButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (glareFrame != null && !glareFrame.empty()) {
                    // Get image
                    String timestamp = utils.getSpecifiedTimestamp();
                    Mat inter = new Mat(glareFrame.width(), glareFrame.height(), CvType.CV_8UC4);
                    Imgproc.cvtColor(glareFrame, inter, Imgproc.COLOR_RGBA2BGR);
                    float glareMaxPixelStored = glareMaxPixelVal;

                    // Store image
                    File sdDir;
                    boolean sdCardExist = Environment.getExternalStorageState().
                            equals(Environment.MEDIA_MOUNTED);
                    if(sdCardExist) {
                        sdDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        if (!sdDir.exists()) {
                            boolean ret = sdDir.mkdirs();
                            if (!ret) {
                                Toast.makeText(GlareActivity.this,
                                        "Failed to create dir", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    } else {
                        Toast.makeText(GlareActivity.this,
                                "Failed to store due to device", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String filePath = sdDir + "/" + timestamp + ".png";
                    Imgcodecs.imwrite(filePath, inter);
                    inter.release();

                    // DAO
                    dataItemEntityInstance = new dataItem(timestamp, ITEM_NAME);
                    glareEntityInstance = new GlareItem(timestamp,
                            df.format(glareMaxPixelStored), filePath, String.valueOf(glareEvent));
                    glareItemViewModel.insert(glareEntityInstance);
                    dataItemViewModel.insert(dataItemEntityInstance);
                    Toast.makeText(GlareActivity.this,
                            "Glare image store path: "+ filePath, Toast.LENGTH_SHORT).show();
                }
            }
        });
        glareButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        glareGuidance = findViewById(R.id.glare_guidance);
        glareGuidance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GlareActivity.this, GlareGuideActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            if (OpenCVLoader.initDebug()) {
                Log.d(LOG_TAG, "openCV loaded!");
                mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            } else {
                Log.d(LOG_TAG, "openCV cannot be loaded");
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(glareCVCamera != null) {
            glareCVCamera.disableView();
        }
        super.onDestroy();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        glareFrame = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        glareFrame.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        glareFrame = inputFrame.rgba();
        Mat grayScaleGaussianBlur = new Mat();
        Imgproc.cvtColor(glareFrame , grayScaleGaussianBlur, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(grayScaleGaussianBlur, grayScaleGaussianBlur,
                new Size(7, 7), 0);

        Core.MinMaxLocResult minMaxLocResultBlur = Core.minMaxLoc(grayScaleGaussianBlur);

        final double maxVal = minMaxLocResultBlur.maxVal;
        final double minVal = minMaxLocResultBlur.minVal;
        glareMaxPixelVal = (float)minMaxLocResultBlur.maxVal;

        if (maxVal >= 253.0 && minVal <= 0.0) {
            Log.w(LOG_TAG, "glare?");
            Point maxValPoint = minMaxLocResultBlur.maxLoc;
            double leftX = Math.max(0, maxValPoint.x - 100);
            double leftY = Math.max(0, maxValPoint.y - 100);
            double rightX = Math.min(glareFrame.width(), maxValPoint.x + 100);
            double rightY = Math.min(glareFrame.height(), maxValPoint.y + 100);
            Imgproc.rectangle(glareFrame, new Point(leftX,leftY), new Point(rightX,rightY),
                    new Scalar( 255, 0, 0 ), 2);
            setGlareTextThread(true);
        } else {
            Log.w(LOG_TAG, "normal?");
            setGlareTextThread(false);
        }
        grayScaleGaussianBlur.release();
        System.gc();
        return glareFrame;
    }


    private void setGlareTextThread(Boolean glareDetected) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(glareDetected) {
                    glareTextViewResult.setText(R.string.glare_event_true);
                    glareEvent = true;
                } else {
                    glareTextViewResult.setText(R.string.glare_event_false);
                    glareEvent = false;
                }
            }
        });
    }

}
