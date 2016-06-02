package com.dalong.rotatetextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 倾斜的TextView -45度
 * Created by zhouweilong on 16/6/1.
 */

public class RotateTextView  extends TextView {

    public RotateTextView(Context context) {
        this(context,null);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-45, getMeasuredWidth()/2, getMeasuredHeight()/2);
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawText("long",10,10,paint);
        super.onDraw(canvas);
    }
}
