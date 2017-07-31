package com.sundy.gametest.ui;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.sundy.gametest.R;
import com.sundy.gametest.gesture.MoveGestureDetector;
import com.sundy.gametest.gesture.RotateGestureDetector;
import com.sundy.gametest.gesture.ShoveGestureDetector;

public class TouchActivity extends AppCompatActivity implements View.OnTouchListener{
    private static final String TAG = "TouchActivity";
    private Matrix mMatrix = new Matrix();
    private float mScaleFactor = .4f;
    private float mRotationDegrees = 0.f;
    private float mFocusX = 0.f;
    private float mFocusY = 0.f;
    private int mAlpha = 255;
    private int mImageHeight, mImageWidth;

    private ScaleGestureDetector mScaleDetector;
    private RotateGestureDetector mRotateDetector;
    private MoveGestureDetector mMoveDetector;
    private ShoveGestureDetector mShoveDetector;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        // Determine the center of the screen to center 'earth'
        Display display = getWindowManager().getDefaultDisplay();
        mFocusX = display.getWidth()/2f;
        mFocusY = display.getHeight()/2f;

        // Set this class as touchListener to the ImageView
        ImageView view = (ImageView) findViewById(R.id.imageView);
        view.setOnTouchListener(this);

        // Determine dimensions of 'earth' image
        Drawable d 		= this.getResources().getDrawable(R.drawable.earth);
        mImageHeight 	= d.getIntrinsicHeight();
        mImageWidth 	= d.getIntrinsicWidth();

        // View is scaled and translated by matrix, so scale and translate initially
        float scaledImageCenterX = (mImageWidth*mScaleFactor)/2;
        float scaledImageCenterY = (mImageHeight*mScaleFactor)/2;

        mMatrix.postScale(mScaleFactor, mScaleFactor);
        mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY - scaledImageCenterY);
        view.setImageMatrix(mMatrix);

        // Setup Gesture Detectors
        mScaleDetector 	= new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
        mRotateDetector = new RotateGestureDetector(getApplicationContext(), new RotateListener());
        mMoveDetector 	= new MoveGestureDetector(getApplicationContext(), new MoveListener());
        mShoveDetector 	= new ShoveGestureDetector(getApplicationContext(), new ShoveListener());
    }

    @SuppressWarnings("deprecation")
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        mRotateDetector.onTouchEvent(event);
        mMoveDetector.onTouchEvent(event);
        mShoveDetector.onTouchEvent(event);

        float scaledImageCenterX = (mImageWidth*mScaleFactor)/2;
        float scaledImageCenterY = (mImageHeight*mScaleFactor)/2;

        mMatrix.reset();
        mMatrix.postScale(mScaleFactor, mScaleFactor);
        mMatrix.postRotate(mRotationDegrees,  scaledImageCenterX, scaledImageCenterY);
        mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY - scaledImageCenterY);

        ImageView view = (ImageView) v;
        view.setImageMatrix(mMatrix);
        view.setAlpha(mAlpha);

        return true; // indicate event was handled
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor(); // scale change since previous event

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            Log.i(TAG, "onScale: ="+mScaleFactor);
            return true;
        }
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotationDegrees -= detector.getRotationDegreesDelta();
            Log.i(TAG, "onRotate: ="+mRotationDegrees);
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX += d.x;
            mFocusY += d.y;
            Log.i(TAG, "onMove: ="+mFocusX+","+mFocusY);
            // mFocusX = detector.getFocusX();
            // mFocusY = detector.getFocusY();
            return true;
        }
    }

    private class ShoveListener extends ShoveGestureDetector.SimpleOnShoveGestureListener {
        @Override
        public boolean onShove(ShoveGestureDetector detector) {
            mAlpha += detector.getShovePixelsDelta();
            if (mAlpha > 255)
                mAlpha = 255;
            else if (mAlpha < 0)
                mAlpha = 0;
            Log.i(TAG, "onShove: ="+mAlpha);
            return true;
        }
    }
}
