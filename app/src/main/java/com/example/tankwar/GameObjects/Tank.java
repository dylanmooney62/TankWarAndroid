package com.example.tankwar.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.tankwar.MainActivity;
import com.example.tankwar.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.tankwar.TankWarView.fps;

public abstract class Tank extends GameObject {

    private CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private Context context;
    private TankType type;
    private boolean disposed = false;
    protected float speed;
    private int scaledWidth = 18;

    public Tank(Context context, TankType type, float positionX, float positionY) {
        super(context, positionX, positionY, true);
        this.context = context;
        this.type = type;

        setBitmap(getTankBitmapId(type), scaledWidth);
    }

    protected void stop() {
        setPositionX(getPositionX() - (calculateRadianX() * speed / fps));
        setPositionY(getPositionY() + (calculateRadianY() * speed / fps));
    }

    public void fire() {
        Bullet bullet = new Bullet(context, this);
        bullets.add(bullet);
    }

    public void destroy() {
        // Delay the removing of a tank so the explosion takes place first
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                setBitmap(R.drawable.tank_empty, scaledWidth);
                dispose();
            }
        }, 150);
    }

    public void dispose() {
        disposed = true;
    }

    public boolean isDisposed() {
        return disposed;
    }

    public void setBullets(CopyOnWriteArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }

    public TankType getType() {
        return type;
    }

    private int getTankBitmapId(TankType type) {
        switch (type) {
            case BLUE:
                return R.drawable.tank_blue;
            case BLACK:
                return R.drawable.tank_dark;
        }
        return 0;
    }

    protected float getSpeed() {
        return this.speed;
    }

    protected float calculateRadianX() {
        return (float) Math.cos(getDegrees() * Math.PI / 180);
    }

    protected float calculateRadianY() {
        return (float) Math.sin(getDegrees() * Math.PI / 180);
    }

}
