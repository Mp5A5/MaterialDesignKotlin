package com.mp5a5.www.mylibrary.refresh;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 */
public class RefreshLayout extends FrameLayout {

  private String Tag = this.getClass().getSimpleName();
  /**
   * 波浪的高度
   */
  protected float mWaveHeight;

  //头部的高度
  protected float mHeadHeight;

  //子控件
  private View mChildView;

  //头部layout
  protected FrameLayout mHeadLayout;

  //刷新的状态
  protected boolean isRefreshing;

  //触摸获得Y的位置
  private float mTouchY;


  //当前Y的位置
  private float mCurrentY;

  //动画的变化率
  private DecelerateInterpolator decelerateInterpolator;


  public RefreshLayout(Context context) {
    this(context, null, 0);
  }

  public RefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
    Log.e(Tag, "init");
  }

  /**
   * 初始化
   */
  private void init() {
    //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
    if (isInEditMode()) {
      return;
    }

    if (getChildCount() > 1) {
      throw new RuntimeException("Only have one child view");
    }

    //在动画开始的地方快然后慢;
    decelerateInterpolator = new DecelerateInterpolator(10);
  }


  @TargetApi(Build.VERSION_CODES.KITKAT)
  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    Log.e(Tag, "onAttachedToWindow");

    //添加头部
    FrameLayout headViewLayout = new FrameLayout(getContext());
    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
    layoutParams.gravity = Gravity.TOP;
    headViewLayout.setLayoutParams(layoutParams);
    mHeadLayout = headViewLayout;
    this.addView(mHeadLayout);
    //获得子控件
    mChildView = getChildAt(0);

    if (mChildView == null) return;
    //设置速率为递减
    mChildView.animate().setInterpolator(new DecelerateInterpolator());
    //通过addUpdateListener()方法来添加一个动画的监听器
    mChildView.animate().setUpdateListener(
        animation -> {
          //获得mChildView当前y的位置
          int height = (int) mChildView.getTranslationY();
          Log.e(Tag, "mChildView.getTranslationY----------->" + height);
          mHeadLayout.getLayoutParams().height = height;
          //重绘
          mHeadLayout.requestLayout();
          if (pullWaveListener != null) {
            pullWaveListener.onPullReleasing(RefreshLayout.this, height / mHeadHeight);
          }
        }
    );

  }

  /**
   * 拦截事件
   */
  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {

    if (isRefreshing) return true;
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mTouchY = ev.getY();
        mCurrentY = mTouchY;
        break;
      case MotionEvent.ACTION_MOVE:
        float currentY = ev.getY();
        float dy = currentY - mTouchY;
        if (dy > 0 && !canChildScrollUp()) {
          return true;
        }
        break;
      default:
        break;
    }
    return super.onInterceptTouchEvent(ev);
  }

  /**
   * 响应事件
   */
  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent e) {
    if (isRefreshing) {
      return super.onTouchEvent(e);
    }

    switch (e.getAction()) {
      case MotionEvent.ACTION_MOVE:
        mCurrentY = e.getY();

        float dy = mCurrentY - mTouchY;
        dy = Math.min(mWaveHeight * 2, dy);
        dy = Math.max(0, dy);

        if (mChildView != null) {
          float offsetY = decelerateInterpolator.getInterpolation(dy / mWaveHeight / 2) * dy / 2;
          mChildView.setTranslationY(offsetY);

          mHeadLayout.getLayoutParams().height = (int) offsetY;
          mHeadLayout.requestLayout();

          if (pullWaveListener != null) {
            pullWaveListener.onPulling(RefreshLayout.this, offsetY / mHeadHeight);
          }
        }
        return true;
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        if (mChildView != null) {
          if (mChildView.getTranslationY() >= mHeadHeight) {
            mChildView.animate().translationY(mHeadHeight).start();
            isRefreshing = true;
            if (pullToRefreshPullingListener != null) {
              pullToRefreshPullingListener.onRefresh(RefreshLayout.this);
            }
          } else {
            mChildView.animate().translationY(0).start();
          }

        }
        return true;
      default:
        break;
    }
    return super.onTouchEvent(e);
  }

  /**
   * 用来判断是否可以上拉
   *
   * @return boolean
   */
  public boolean canChildScrollUp() {
    if (mChildView == null) {
      return false;
    }
    return mChildView.canScrollVertically(-1);
  }

  /**
   * 设置下拉监听
   */
  private PullToRefreshListener pullToRefreshPullingListener;

  public void setPullToRefreshListener(PullToRefreshListener pullToRefreshPullingListener) {
    this.pullToRefreshPullingListener = pullToRefreshPullingListener;
  }

  /**
   * 设置wave监听
   */
  private PullWaveListener pullWaveListener;

  public void setPullWaveListener(PullWaveListener pullWaveListener) {
    this.pullWaveListener = pullWaveListener;
  }

  /**
   * 刷新结束
   */
  public void finishRefreshing() {

    post(() -> {
      if (mChildView != null) {
        mChildView.animate().translationY(0).start();
      }
      isRefreshing = false;
    });
  }

  public void setRefreshing(boolean refreshing) {
    isRefreshing = refreshing;
  }

  /**
   * 设置头部View
   */
  public void setHeaderView(final View headerView) {
    post(() -> mHeadLayout.addView(headerView));
  }

  /**
   * 设置wave的下拉高度
   */
  public void setWaveHeight(float waveHeight) {
    this.mWaveHeight = waveHeight;
  }

  /**
   * 设置下拉头的高度
   */
  public void setHeaderHeight(float headHeight) {
    this.mHeadHeight = headHeight;
  }
}
