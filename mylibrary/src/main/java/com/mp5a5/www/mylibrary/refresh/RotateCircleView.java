package com.mp5a5.www.mylibrary.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mp5a5.www.mylibrary.R;


/**
 * @author ：mp5a5 on 2018/8/31 10：44
 * @describe
 * @email：wwb199055@126.com
 */
public class RotateCircleView extends View {


  private ValueAnimator valueAnimator;

  /**
   * 小圆个数
   */
  private int mSmallCircleNum = 6;

  /**
   * 大圆的半径
   */
  private float mBigCircleRadius = 50;

  /**
   * 每一个小圆的半径
   */
  private float mSmallCircleRadius = 5;
  /**
   * 小圆颜色列表
   */
  private int mCircleColors = Color.BLUE;
  /**
   * 当前旋转角度
   */
  private float mCurrentRotationAngle = 0f;
  /**
   * 当前大圆的半径
   */
  private float mCurrentCircleRadius = mBigCircleRadius;

  /**
   * 旋转时间
   */
  private long mRotateDuration = 1000;


  private Paint mpaint;

  public RotateCircleView(Context context) {
    super(context);
    init(context);
  }

  public RotateCircleView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
    initParams(context, attrs);
  }

  public RotateCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
    initParams(context, attrs);
  }

  private void initParams(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateCircleView);
    if (typedArray != null) {
      mSmallCircleNum = typedArray.getInt(R.styleable.RotateCircleView_rotateNum, 6);
      typedArray.recycle();
    }
  }

  private void init(Context context) {
    mpaint = new Paint();
    mpaint.setAntiAlias(true);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float mCenterX = getWidth() / 2f;
    float mCenterY = getHeight() / 2f;
    //得到每个小圆的间隔角度
    float rotationAngle = (float) (2 * Math.PI / mSmallCircleNum);
    //画mSmallCircleNum个小圆
    for (int i = 0; i < mSmallCircleNum; i++) {
      double angle = i * rotationAngle + mCurrentRotationAngle;
      float cx = (float) (mCurrentCircleRadius * Math.cos(angle) + mCenterX);
      float cy = (float) (mCurrentCircleRadius * Math.sin(angle) + mCenterY);
      mpaint.setColor(mCircleColors);
      canvas.drawCircle(cx, cy, mSmallCircleRadius + i, mpaint);
    }

  }


  private void setAnimStart() {
    //花mRotateDuration(ms)，计算某个时间当前的角度是多少：0~2π中的某个值
    valueAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);
    //设置插值器，主要是为了小圆点的旋转时间平均，让动画没有停顿。
    valueAnimator.setInterpolator(new LinearInterpolator());
    //回调监听
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        //得到某个时间点计算的结果 ----这个时间带你当前大圆旋转的角度
        mCurrentRotationAngle = (float) animation.getAnimatedValue();
        postInvalidate();
      }
    });
    //动画时间
    valueAnimator.setDuration(mRotateDuration);
    //不断重复
    valueAnimator.setRepeatCount(ValueAnimator.RESTART);
    //启动
    valueAnimator.start();
  }

  private void setAnimFinish() {
    valueAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        if (mRotateListener != null) {
          mRotateListener.onFinish();
        }
      }
    });
  }


  public void setBigCircleRadius(float bigCircleRadius) {
    this.mBigCircleRadius = bigCircleRadius;
  }

  public void setSmallCircleRadius(float smallCircleRadius) {
    this.mSmallCircleRadius = smallCircleRadius;
  }

  public void setCircleColors(int mCircleColors) {
    this.mCircleColors = mCircleColors;
  }

  public void setRotateDuration(long rotateDuration) {
    this.mRotateDuration = rotateDuration;
  }

  public void setSmallCircleNum(int num) {
    this.mSmallCircleNum = num;
  }

  private RotateListener mRotateListener;

  public void setRotateListener(RotateListener rotateListener) {
    this.mRotateListener = rotateListener;

    setAnimStart();

    setAnimFinish();
  }

  public interface RotateListener {
    /**
     * 结束回调
     */
    void onFinish();
  }
}
