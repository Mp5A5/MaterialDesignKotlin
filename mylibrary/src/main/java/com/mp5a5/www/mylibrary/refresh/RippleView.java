package com.mp5a5.www.mylibrary.refresh;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class RippleView extends View {

  private Paint mPaint;

  private int mRadius;

  private int mRippleColor = Color.WHITE;

  private int mRippleAlpha = 155;


  public int getRadius() {
    return mRadius;
  }

  public void setRadius(int mRadius) {
    this.mRadius = mRadius;
  }

  public RippleView(Context context) {
    this(context, null, 0);
  }

  public RippleView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setColor(mRippleColor);
    mPaint.setAlpha(mRippleAlpha);
    mPaint.setStyle(Paint.Style.FILL);
  }

  @SuppressWarnings("all")
  public void startReveal() {
    new Thread(() -> {
      int bigRadius = (int) (Math.sqrt(Math.pow(getHeight(), 2) + Math.pow(getWidth(), 2)));
      while (mRadius < bigRadius) {
        mRadius += 30;
        postInvalidate();
        try {
          Thread.sleep(30);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      mRadius = 0;
      if (listener != null) {
        listener.onRippleFinish();
      }
    }).start();

  }


  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);
  }

  private RippleListener listener;

  public void setRippleListener(RippleListener listener) {
    this.listener = listener;
  }

  public interface RippleListener {
    void onRippleFinish();
  }

}
