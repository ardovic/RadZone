package com.serjardovic.radzone;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

abstract class StaticObject {

    private Bitmap image;

    private int x;
    int y;

    private String text = "";

    private Rect rectangle;

    StaticObject (Rect rectangle, Bitmap image){
        this.rectangle = rectangle;

        x = rectangle.centerX() - rectangle.width()/2;
        y = rectangle.centerY() - rectangle.height()/2;

        float scaleWidth = ((float) rectangle.width()) / image.getWidth();
        float scaleHeight = ((float) rectangle.height()) / image.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        this.image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);

    }

    void setText (String text){
        this.text = text;
    }
    String getText(){
        return text;
    }
    Rect getRectangle(){
        return rectangle;
    }
    int getY(){
        return y;
    }
    int getX(){
        return x;
    }
    int getWidth(){
        return rectangle.width();
    }
    int getHeight(){
        return rectangle.height();
    }
    void draw(Canvas canvas){
        canvas.drawBitmap(image, null, rectangle, null);
    }
}
