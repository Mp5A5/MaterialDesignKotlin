package com.mp5a5.www.mylibrary.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.View;

/**
 */
public class RoundDotView extends View {

  private Paint mPaint;

  private float mCircleRadius = 15;

  private int mCircleNum = 7;

  private int mCircleColor = Color.rgb(114, 114, 114);


  private @FloatRange(from = 0.1F, to = 1.0F)
  float mCircleAlpha = 1;

  private float mFraction;

  public RoundDotView(Context context) {
    this(context, null, 0);
  }

  public RoundDotView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RoundDotView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setColor(mCircleColor);
    int height = getHeight();
    int width = getWidth();
    int centerX = width / 2;
    int centerY = height / 2;


    float x = DensityUtil.px2dp(height);
    //y1 = f*(w/n)-(f>1)*((f-1)*(w/n)/f)
    float wide = (width / mCircleNum) * mFraction - ((mFraction > 1) ? ((mFraction - 1) * (width / mCircleNum) / mFraction) : 0);
    //y2 = x - (f>1)*((f-1)*x/f);
    float high = height - ((mFraction > 1) ? ((mFraction - 1) * centerY / mFraction) : 0);

    for (int i = 0; i < mCircleNum; i++) {
      //y3 = (x + 1) - (n + 1)/2; 居中 index 变量：0 1 2 3 4 结果： -2 -1 0 1 2
      float index = 1f + i - (1f + mCircleNum) / 2;
      //y4 = m * ( 1 - 2 * abs(y3) / n); 横向 alpha 差
      float alpha = 255 * (1 - (2 * (Math.abs(index) / mCircleNum)));
      //y5 = y4 * (1-1/((x/800+1)^15));竖直 alpha 差
      mPaint.setAlpha((int) (mCircleAlpha * alpha * (1d - 1d / Math.pow((x / 800d + 1d), 15))));
      //y6 = mDotRadius*(1-1/(x/10+1));半径
      float radius = mCircleRadius * (1 - 1 / ((x / 10 + 1)));
      canvas.drawCircle(centerX - radius / 2 + wide * index, high / 2, radius, mPaint);
    }

  }

  public void setCircleAlpha(@FloatRange(from = 0.1F, to = 1.0F) float dotAlpha) {
    this.mCircleAlpha = dotAlpha;
  }

  public void setCircleNum(int circleNum) {
    this.mCircleNum = circleNum;
  }

  public void startReveal(float fraction) {
    mFraction = fraction;
    invalidate();
  }

  public void setCircleColor(int circleColor) {
    this.mCircleColor = circleColor;
  }

  public void setFraction(int x) {
    this.mFraction = x;
  }

  public void setCircleRadius(float circleRadius) {
    this.mCircleRadius = circleRadius;
  }

}
