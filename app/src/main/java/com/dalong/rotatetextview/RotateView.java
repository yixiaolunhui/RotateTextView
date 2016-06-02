package com.dalong.rotatetextview;

import android.content.Context;
import android.util.AttributeSet;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


public class RotateView extends View {
    /**
     * 控件的宽
     */
    private int mWidth;
    /**
     * 控件的高
     */
    private int mHeight;
    /**
     * 控件中的图片
     */
    private Bitmap mImage;
    /**
     * 图片的缩放模式
     */
    private int mImageScale;
    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;
    /**
     * 图片的介绍
     */
    private String mTitle=" ";
    /**
     * 字体的颜色
     */
    private int mTextColor;
    /**
     * 字体的大小
     */
    private int mTextSize;

    private Paint mPaint;
    /**
     * 对文本的约束
     */
    private Rect mTextBound;
    /**
     * 控制整体布局
     */
    private Rect rect;

    /**
     * 角度
     */
    private int rotate=-45;

    public RotateView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public RotateView(Context context){
        this(context, null);
    }

    /**
     * 初始化所特有自定义类型
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RotateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyle, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextColors:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomImageView_titleRotate:
                    rotate = a.getInt(attr, 0);
                    break;

            }
        }
        a.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            // 由字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            if(mImage!=null){
                // 由图片决定的宽
                int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
                if (specMode == MeasureSpec.AT_MOST){
                    int desire = Math.max(desireByImg, desireByTitle);
                    mWidth = Math.min(desire, specSize);
                }else{
                    mWidth=desireByTitle;
                }
            }else{
                mWidth=desireByTitle;
            }

        }

        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){// match_parent
            mHeight = specSize;
        } else {

            if(mImage!=null){
                int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight();
                int desire2 = getPaddingTop() + getPaddingBottom() + mTextBound.height();
                Log.v("888888","desire:"+desire+"  specSize:"+specSize);
                if (specMode == MeasureSpec.AT_MOST){// wrap_content
                    Log.v("888888","设置了");
                    int mHeight2 = Math.min(desire, desire2);
                    mHeight = Math.min(mHeight2, specSize);
                }else{
                    mHeight = desire;
                }
            }else{
                mHeight =  getPaddingTop() + getPaddingBottom() +mTextBound.height();
            }

        }

        Log.v("888888","mWidth:"+mWidth+"  mHeight:"+mHeight);
        setMeasuredDimension(mWidth, mHeight);

    }

    /**
     * 重写
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(rotate, getMeasuredWidth()/2, getMeasuredHeight()/2);
        if(mImage!=null){
            if (mImageScale == IMAGE_SCALE_FITXY) {
                canvas.drawBitmap(mImage, null, rect, mPaint);
            } else {
                //计算居中的矩形范围
                rect.left = mWidth / 2 - mImage.getWidth() / 2;
                rect.right = mWidth / 2 + mImage.getWidth() / 2;
                rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
                rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
                canvas.drawBitmap(mImage, null, rect, mPaint);
            }
        }

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Style.FILL);
        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            //  mTextBound.width()/1.4f/2
            if(Math.abs(rotate)>90){
                while (rotate>90){
                    rotate=Math.abs(rotate)-90;
                }
                canvas.drawText(msg, (float) (mWidth / 2-mTextBound.width()/2-Math.abs(mTextBound.width()/2*(1+Math.cos(rotate)))),
                        (float) (mHeight/2f+mTextBound.width()/2*Math.sin(rotate)-mTextBound.height()/2),
                        mPaint);
            }else{
                canvas.drawText(msg, (float) (mWidth / 2-mTextBound.width()/2-Math.abs(mTextBound.width()/2*(1-Math.abs(Math.cos(rotate))))),
                        (float) (mHeight/2f+mTextBound.width()/2*Math.sin(rotate==0?90:rotate)-mTextBound.height()/2),
                        mPaint);
            }

        } else { //正常情况，将字体居中
            if(Math.abs(rotate)>90){
                while (Math.abs(rotate)>90){
                    rotate=Math.abs(rotate)-90;
                }
                canvas.drawText(mTitle, (float) (mWidth / 2-mTextBound.width()/2),
                        (float) (mHeight/2f-mTextBound.height()/2+mTextBound.width()/2*Math.sin(rotate)),
                        mPaint);
            }else{
                canvas.drawText(mTitle, (float) (mWidth / 2-mTextBound.width()/2),
                        (float) (mHeight/2f+mTextBound.width()/2*Math.sin(rotate==0?90:rotate)-mTextBound.height()/2),
                        mPaint);
            }
        }
    }

    /**
     * 设置text
     * @param text
     */
    public void setText(String text){
        this.mTitle=text;
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
        postInvalidate();
    }

    /**
     *  设置旋转角度
     * @param rotate
     */
    public void setRotate(int rotate){
        this.rotate=rotate;
        postInvalidate();
    }
}
