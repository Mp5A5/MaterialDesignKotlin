package com.mp5a5.www.mylibrary.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.mp5a5.www.mylibrary.R;


public class RotateRefreshLayout extends RefreshLayout {

  private float waveHeight = 180;
  private float headHeight = 100;
  private WaveView waveView;
  private RippleView rippleView;
  private RoundDotView roundDotView;
  private RotateCircleView rotateCircleView;

  public RotateRefreshLayout(Context context) {
    this(context, null, 0);
  }

  public RotateRefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RotateRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {

    View headView = LayoutInflater.from(getContext()).inflate(R.layout.view_head, null);
    waveView = (WaveView) headView.findViewById(R.id.wv_wave);
    rippleView = (RippleView) headView.findViewById(R.id.rv_ripple);
    roundDotView = (RoundDotView) headView.findViewById(R.id.rdv_round);
    waveView.setWaveViewColor(Color.GRAY);
    roundDotView.setCircleColor(Color.BLUE);
    rotateCircleView = (RotateCircleView) headView.findViewById(R.id.rcv_rotate);
    rotateCircleView.setVisibility(View.GONE);
    //设置波浪的高度
    setWaveHeight(DensityUtil.dip2px(getContext(), waveHeight));
    //设置headView的高度
    setHeaderHeight(DensityUtil.dip2px(getContext(), headHeight));
    //设置headView
    setHeaderView(headView);

    initListener();
  }

  private void initListener() {

    rippleView.setRippleListener(() -> {
      if (mListener != null) {
        mListener.onRefresh(RotateRefreshLayout.this);
      }
    });


    //监听波浪变化监听
    setPullWaveListener(new PullWaveListener() {
      @Override
      public void onPulling(RefreshLayout refreshLayout, float fraction) {
        float headW = DensityUtil.dip2px(getContext(), waveHeight);
        waveView.setHeadHeight((int) (DensityUtil.dip2px(getContext(), headHeight) * limitValue(1, fraction)));
        waveView.setWaveHeight((int) (headW * Math.max(0, fraction - 1)));
        waveView.setWaveViewAnimStart();


        roundDotView.startReveal(fraction);
        roundDotView.setVisibility(View.VISIBLE);

        rotateCircleView.setVisibility(View.GONE);
        rotateCircleView.animate().scaleX((float) 0.1);
        rotateCircleView.animate().scaleY((float) 0.1);
      }

      @Override
      public void onPullReleasing(RefreshLayout refreshLayout, float fraction) {
        if (!refreshLayout.isRefreshing) {
          roundDotView.startReveal(fraction);
        }
      }
    });

    //松开后的监听
    setPullToRefreshListener(new PullToRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshLayout) {
        waveView.setHeadHeight((int) (DensityUtil.dip2px(getContext(), headHeight)));
        waveView.setWaveViewAnimStart();
        initOtherViewAnimator();
      }
    });

  }


  private void initOtherViewAnimator() {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
    valueAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        roundDotView.setVisibility(GONE);
        rotateCircleView.setVisibility(View.VISIBLE);
        rotateCircleView.animate().scaleX((float) 1.0);
        rotateCircleView.animate().scaleY((float) 1.0);
        rotateCircleView.postDelayed(() -> rotateCircleView.setRotateListener(() -> {
          rotateCircleView.animate().scaleX((float) 0.0);
          rotateCircleView.animate().scaleY((float) 0.0);
          rippleView.startReveal();
        }), 300);
      }
    });

    valueAnimator.addUpdateListener(animation -> {
      float value = (float) animation.getAnimatedValue();
      roundDotView.startReveal(-value);
    });
    valueAnimator.setInterpolator(new AccelerateInterpolator());
    valueAnimator.setDuration(300);
    valueAnimator.start();
  }


  /**
   * 限定值
   */
  public float limitValue(float a, float b) {
    float valve = 0;
    final float min = Math.min(a, b);
    final float max = Math.max(a, b);
    valve = valve > min ? valve : min;
    valve = valve < max ? valve : max;
    return valve;
  }

  public interface RotateRefreshListener {
    /**
     * 下拉刷新
     *
     * @param refreshLayout 刷新
     */
    void onRefresh(RotateRefreshLayout refreshLayout);
  }

  private RotateRefreshListener mListener;

  public void setRotateRefreshListener(RotateRefreshListener listener) {
    this.mListener = listener;
  }
}
