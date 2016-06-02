package com.dalong.rotatetextview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 倾斜的imageview -45度
 * Created by zhouweilong on 16/6/1.
 */

public class RotateImageView extends ImageView {

    public RotateImageView(Context context) {
        super(context);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-45, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }
}
