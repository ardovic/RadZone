package com.serjardovic.radzone;

import android.graphics.Bitmap;
import android.graphics.Rect;

class PopupElement extends StaticObject {

    PopupElement(Rect rectangle, Bitmap image) {
        super(rectangle, image);
    }

    void update() {

        this.y =100;

    }
}
