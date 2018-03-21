package com.serjardovic.radzone;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

class GameThread extends Thread {

    private int FPS = 9;
    private double averageFPS;
    private long startTime;
    private long timeMillis;
    private long waitTime;
    private long totalTime;
    private int frameCount;
    private long targetTime;
    private final SurfaceHolder surfaceHolder;
    private GameSurface gameSurface;
    private boolean running;
    private static Canvas canvas;

    GameThread(SurfaceHolder surfaceHolder, GameSurface gameSurface) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameSurface = gameSurface;
    }

    @Override
    public void run() {
        targetTime = 1000 / FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            } catch (Exception e) {e.printStackTrace();}

            finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {e.printStackTrace();}

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == FPS) {
                averageFPS = 1000 / ((totalTime/frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    void setRunning(boolean b) {
        running = b;
    }
}
