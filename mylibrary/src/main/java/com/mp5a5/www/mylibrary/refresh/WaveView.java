package com.mp5a5.www.mylibrary.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * 绘制贝塞尔来绘制波浪形
 */
public class WaveView extends View {

  private int waveHeight;
  private int headHeight;
  private int mWaveViewColor = Color.rgb(43, 43, 43);
  Path path;
  Paint paint;

  public WaveView(Context context) {
    this(context, null, 0);
  }

  public WaveView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    path = new Path();
    paint = new Paint();
    paint.setAntiAlias(true);
  }

  public int getHeadHeight() {
    return headHeight;
  }

  public void setHeadHeight(int headHeight) {
    this.headHeight = headHeight;
  }

  public int getWaveHeight() {
    return waveHeight;
  }

  public void setWaveHeight(int waveHeight) {
    this.waveHeight = waveHeight;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    paint.setColor(mWaveViewColor);
    //重置画笔
    path.reset();
    path.lineTo(0, headHeight);
    //绘制贝塞尔曲线
    path.quadTo(getMeasuredWidth() / 2, headHeight + waveHeight, getMeasuredWidth(), headHeight);
    path.lineTo(getMeasuredWidth(), 0);
    canvas.drawPath(path, paint);
  }


  public void setWaveViewAnimStart() {

    ValueAnimator animator = ValueAnimator.ofInt(getWaveHeight(), 0, -300, 0, -100, 0);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        waveHeight = ((int) animation.getAnimatedValue());
        postInvalidate();
      }
    });
    animator.setInterpolator(new BounceInterpolator());
    animator.setDuration(1000);
    animator.start();
  }

  public void setWaveViewColor(int waveViewColor) {
    this.mWaveViewColor = waveViewColor;
  }

  /**
   * drawable 转换成bitmap
   *
   * @param drawable drawable
   */
  static Bitmap drawableToBitmap(Drawable drawable) {
    // 取drawable的长宽
    int width = drawable.getIntrinsicWidth();
    int height = drawable.getIntrinsicHeight();
    // 取drawable的颜色格式
    Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config
        .RGB_565;
    // 建立对应bitmap
    Bitmap bitmap = Bitmap.createBitmap(width, height, config);
    // 建立对应bitmap的画布
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, width, height);
    // 把drawable内容画到画布中
    drawable.draw(canvas);
    return bitmap;
  }

}
