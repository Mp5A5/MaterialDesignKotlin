package com.mp5a5.www.mylibrary.refresh;

/**
 * wave回调监听
 * @author nangongyun
 */
public interface PullWaveListener {
    /**
     * 下拉中
     * @param refreshLayout
     * @param fraction
     */
    void onPulling(RefreshLayout refreshLayout, float fraction);

    /**
     * 下拉松开
     * @param refreshLayout
     * @param fraction
     */
    void onPullReleasing(RefreshLayout refreshLayout, float fraction);
}
