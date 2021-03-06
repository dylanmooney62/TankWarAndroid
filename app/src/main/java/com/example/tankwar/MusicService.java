package com.example.tankwar;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    private static MediaPlayer player;

    public static boolean isActive() {
        return player != null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = MediaPlayer.create(this, R.raw.game_music);
        player.start();
        player.setLooping(true);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
    }
}

