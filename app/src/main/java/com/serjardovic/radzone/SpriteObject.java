package com.serjardovic.radzone;

import android.graphics.Bitmap;
import android.graphics.Rect;


abstract class SpriteObject {

    private Bitmap sprite;
    int columnCount;
    private int objectWidth;
    private int objectHeight;
    int x;
    int y;

    SpriteObject(Bitmap sprite, int rowCount, int columnCount, int x, int y)  {

        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.columnCount = columnCount;
        this.objectWidth = sprite.getWidth() / columnCount;
        this.objectHeight = sprite.getHeight() / rowCount;

    }

    Bitmap createSpriteObject(int row, int column)  {
        return Bitmap.createBitmap(sprite, column * objectWidth, row * objectHeight, objectWidth, objectHeight);
    }

    void setX(int x){
        this.x = x;
    }
    void setY(int y){
        this.y = y;
    }
    int getX()  {
        return this.x;
    }
    int getY()  {
        return this.y;
    }
    int getHeight() {
        return objectHeight;
    }
    int getWidth() {
        return objectWidth;
    }
    Rect getRectangle() {
        return new Rect(x, y, x + objectWidth, y + objectHeight);
    }
}
