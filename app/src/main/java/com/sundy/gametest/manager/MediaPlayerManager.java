package com.sundy.gametest.manager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.sundy.gametest.MusicListener;
import com.sundy.gametest.R;

import java.io.IOException;

/**
 * 音乐管理播放（可以播放大文件）
 * 还有事件监听
 * Created by David on 2017/7/23.
 */

public class MediaPlayerManager implements MusicListener, MediaPlayer.OnCompletionListener{
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    private int maxVolume;
    private int currVolume;
    private int stepVolume;
    private Context mContext;

    /**
     * 默认音乐资源构造
     * @param context
     */
    public MediaPlayerManager(Context context) {
        this.mContext = context;
        mediaPlayer = MediaPlayer.create(context, R.raw.musictest);
        initAudio();
    }

    public MediaPlayerManager(Context context, int rawId) {
        mediaPlayer = MediaPlayer.create(context, rawId);
        initAudio();
    }

    public MediaPlayerManager(Context context, String filePath) {
        initAudio();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAudio() {
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        stepVolume = maxVolume / 6;

    }

    /**
     * 开始播放
     */
    @Override
    public void onPlayMusic() {
        mediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    @Override
    public void onPauseMusic() {
        mediaPlayer.pause();
    }

    /**
     * 停止播放
     */
    @Override
    public void onStopMusic() {
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    /**
     * 增加音量
     */
    public void addStreamVolume() {
       currVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
       int tmpVolume=currVolume+stepVolume;
       currVolume=tmpVolume<maxVolume?tmpVolume:maxVolume;
       audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currVolume,AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 减少音量
     */
    public void minusStreamVolume() {
        currVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int tmpVolume=currVolume-stepVolume;
        currVolume=tmpVolume<0?tmpVolume:0;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currVolume,AudioManager.FLAG_PLAY_SOUND);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
