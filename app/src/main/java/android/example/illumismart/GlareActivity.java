package android.example.illumismart;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import org.opencv.imgproc.Imgproc;

public class GlareActivity extends AppCompatActivity implements
        CameraBridgeViewBase.CvCameraViewListener2{
    private static final String LOG_TAG = GlareActivity.class.getSimpleName();
    private CameraBridgeViewBase mCVCamera;
    private Mat mRgba;


    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(LOG_TAG, "OpenCV loaded successfully");
                    mCVCamera.enableView();
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glare);

        mCVCamera = (CameraBridgeViewBase) findViewById(R.id.glare_cv_camera);
        mCVCamera.setCvCameraViewListener(this);
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
        if(mCVCamera != null) {
            mCVCamera.disableView();
        }
        super.onDestroy();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        Mat grayScaleGaussianBlur = new Mat();
        Imgproc.cvtColor(mRgba , grayScaleGaussianBlur, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(grayScaleGaussianBlur, grayScaleGaussianBlur,
                new Size(7, 7), 0);

        Core.MinMaxLocResult minMaxLocResultBlur = Core.minMaxLoc(grayScaleGaussianBlur);

        final double maxVal = minMaxLocResultBlur.maxVal;
        final double minVal = minMaxLocResultBlur.minVal;

        if (maxVal >= 250.0 && minVal <= 0.0) {
            Log.w(LOG_TAG, "glare?");
            Point maxValPoint = minMaxLocResultBlur.maxLoc;
            double leftX = Math.max(0, maxValPoint.x - 50);
            double leftY = Math.max(0, maxValPoint.y - 50);
            double rightX = Math.min(mRgba.width(), maxValPoint.x + 50);
            double rightY = Math.min(mRgba.height(), maxValPoint.y + 50);
            Imgproc.rectangle(mRgba, new Point(leftX,leftY), new Point(rightX,rightY),
                    new Scalar( 255, 0, 0 ), 2);
        } else {
            Log.w(LOG_TAG, "normal?");
        }
        return mRgba;
    }
}
