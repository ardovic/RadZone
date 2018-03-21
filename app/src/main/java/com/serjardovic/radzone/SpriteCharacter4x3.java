package com.serjardovic.radzone;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

abstract class SpriteCharacter4x3 extends SpriteObject {

    private Random random = new Random();

    private int ai;
    private int ai_random;
    private int frames;
    private boolean aiWait;

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT_TO_RIGHT;
    private int colUsing;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;

    // Initial velocity of the character (pixel/millisecond)
    private int velocity = 10;
    private int vectorX;
    private int vectorY;
    private long lastDrawNanoTime = -1;
    private boolean moving;

    private String text = "";


    SpriteCharacter4x3(Bitmap sprite, int x, int y) {

        super(sprite, 4, 3, x, y);

        this.topToBottoms = new Bitmap[columnCount];
        this.rightToLefts = new Bitmap[columnCount];
        this.leftToRights = new Bitmap[columnCount];
        this.bottomToTops = new Bitmap[columnCount];

        for (int col = 0; col < this.columnCount; col++) {
            this.topToBottoms[col] = this.createSpriteObject(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col] = this.createSpriteObject(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSpriteObject(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col] = this.createSpriteObject(ROW_BOTTOM_TO_TOP, col);
        }
    }

    private Bitmap[] getMoveBitmaps() {
        switch (rowUsing) {
            case ROW_BOTTOM_TO_TOP:
                return this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
        }
    }

    private Bitmap getCurrentMoveBitmap() {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }

    void update() {

        if (moving) {
            this.colUsing++;
            if (colUsing >= this.columnCount) this.colUsing = 0;
        } else {
            this.colUsing = 1;
            this.velocity = 0;
        }

        long now = System.nanoTime();

        if (lastDrawNanoTime == -1) lastDrawNanoTime = now;

        int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);

        // Distance moves
        float distance = (float) velocity / 100 * deltaTime;

        double vectorLength = Math.sqrt(vectorX * vectorX + vectorY * vectorY);

        // Calculate the new position of the game character.
        this.x = x + (int) (distance * vectorX / vectorLength);
        this.y = y + (int) (distance * vectorY / vectorLength);

        // rowUsing

        if (vectorX > 0) {
            if (vectorY >= 0 && Math.abs(vectorX) < Math.abs(vectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            } else if (vectorY < 0 && Math.abs(vectorX) < Math.abs(vectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            } else {
                this.rowUsing = ROW_LEFT_TO_RIGHT;
            }
        } else {
            if (vectorY > 0 && Math.abs(vectorX) < Math.abs(vectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            } else if (vectorY < 0 && Math.abs(vectorX) < Math.abs(vectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            } else {
                this.rowUsing = ROW_RIGHT_TO_LEFT;
            }
        }
        if (ai == 1) {
            frames++;
            if (frames < 20) {
                moving = false;
                vectorX = 0;
                vectorY = 1;
            } else if (frames >= 20 && frames < 40) {
                moving = true;
                velocity = 10;
                vectorX = 1;
                vectorY = 0;
            } else if (frames >= 40 && frames < 60) {
                moving = false;
                vectorX = 0;
                vectorY = 1;
            } else if (frames >= 60 && frames < 79) {
                moving = true;
                velocity = 10;
                vectorX = -1;
                vectorY = 0;
            } else {
                frames = 0;
            }
        }
        if (ai == 2) {
            frames++;
            if (frames < 20) {
                moving = false;
                vectorX = 0;
                vectorY = 1;
            } else if (frames >= 20 && frames < 40) {
                moving = true;
                velocity = 10;
                vectorX = 0;
                vectorY = 1;
            } else if (frames >= 40 && frames < 60) {
                moving = false;
                vectorX = 0;
                vectorY = 1;
            } else if (frames >= 60 && frames < 79) {
                moving = true;
                velocity = 10;
                vectorX = 0;
                vectorY = -1;
            } else {
                frames = 0;
            }
        }
        if (ai == 3) {
            frames++;
            if (frames < 30) {
                if (frames == 1) {
                    System.out.println("YES");
                    ai_random = random.nextInt(10) + 5;
                    switch (ai_random % 4) {
                        case 0:
                            vectorX = 1;
                            vectorY = 0;
                            break;
                        case 1:
                            vectorX = 0;
                            vectorY = 1;
                            break;
                        case 2:
                            vectorX = -1;
                            vectorY = 0;
                            break;
                        case 3:
                            vectorX = 0;
                            vectorY = -1;
                            break;
                    }
                }
                if(!aiWait) {
                    moving = true;
                    velocity = ai_random;
                }

            } else if (frames >= 30 && frames < 40) {

                aiWait = false;

                if (frames == 30) {
                    System.out.println("NO");
                    ai_random = random.nextInt(10) + 5;
                    switch (ai_random % 4) {
                        case 0:
                            vectorX = 1;
                            vectorY = 0;
                            break;
                        case 1:
                            vectorX = 0;
                            vectorY = 1;
                            break;
                        case 2:
                            vectorX = -1;
                            vectorY = 0;
                            break;
                        case 3:
                            vectorX = 0;
                            vectorY = -1;
                            break;
                    }
                }
                moving = false;

            } else {
                frames = 0;
            }
        }

    }

    void draw(Canvas canvas) {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
        // Last draw time.
        this.lastDrawNanoTime = System.nanoTime();
    }

    void setMoving(boolean moving, int velocity) {
        this.moving = moving;
        this.velocity = velocity;
    }

    void setMoving(boolean moving) {
        this.moving = moving;
        this.velocity = 10;
    }

    void setMoving(int velocity) {
        this.moving = true;
        this.velocity = velocity;
    }

    void setVector(int vectorX, int vectorY) {
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    void setText(String text) {
        this.text = text;
    }

    void setAi(int aiID) {
        switch (aiID) {
            case 1:
                ai = 1;
                break;
            case 2:
                ai = 2;
                break;
            case 3:
                ai = 3;
                break;
        }
    }

    void setAiWait(boolean aiWait){
        this.aiWait = aiWait;
    }

    String getText() {
        return text;
    }
}
