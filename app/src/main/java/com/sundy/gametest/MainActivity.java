package com.sundy.gametest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sundy.gametest.manager.MediaPlayerManager;
import com.sundy.gametest.ui.TouchActivity;
import com.sundy.gametest.view.MySurfaceView;

public class MainActivity extends AppCompatActivity {
     SoundPool sp;
     SparseArray<Integer> source=new SparseArray<>();
     MediaPlayerManager manager;
     int cuttentStreamId;
     MySurfaceView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //initSoundPool();
       // manager=new MediaPlayerManager(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView=new MySurfaceView(this);
        setContentView(gameView);
        Intent intent =new Intent(this, TouchActivity.class);
        startActivity(intent);
    }

    /**
     * 初始化游戏播放器
     */
    public void initSoundPool() {
        sp=new SoundPool(4,AudioManager.STREAM_MUSIC,0);
        source.put(0,sp.load(this,R.raw.musictest,1));

    }

    public void btnStart(View view) {
          //playSound(0,1);
        manager.onPlayMusic();
    }

    private void playSound(int sound, int loop) {
        AudioManager am= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float streamVolumecurrent=am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume=streamVolumecurrent/streamVolumeMax;
        cuttentStreamId=sp.play(source.get(sound),volume,volume,1,loop,1.0f);
    }

    public void btnStop(View view) {
       // sp.stop(cuttentStreamId);
        manager.onPauseMusic();
    }

    private static class Samp1leView extends View {
        private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG;

        private Paint mPaint;

        public Samp1leView(Context context) {
            super(context);
            setFocusable(true);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);

        }

        public Samp1leView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public Samp1leView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @SuppressLint({"DrawAllocation", "WrongConstant"})
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas.translate(10, 10);
            mPaint.setColor(Color.RED);
            canvas.drawCircle(75, 75, 75, mPaint);
            canvas.saveLayerAlpha(0, 0, 200, 200, 0x88, LAYER_FLAGS);
            mPaint.setColor(Color.BLUE);
            canvas.drawCircle(125, 125, 75, mPaint);
            canvas.restore();
//			Paint background = new Paint();
//			background.setColor(Color.WHITE);
//
//			canvas.saveLayer(75, 55, 500, 500, background, LAYER_FLAGS);
////			Paint layoutPaint = new Paint();
////			layoutPaint.setColor(Color.GREEN);
////			layoutPaint.setTextSize(50);
////			canvas.drawText("²âÊÔÎÄ×Ö", 100, 100, layoutPaint);
//			canvas.restore();
////			Paint textpaint = new Paint();
////			textpaint.setColor(Color.YELLOW);
////			canvas.drawCircle(75,75, 50, textpaint);

        }
    }

}
